package vision;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class BackupAndRestore {

	private static String dbName;
	private static String username;
	private static String password;
	private static String os;
	private static String path;
	private static String defaultPath;
	private static String fileName;
	
	/*
	To get the currently logged in user:
	System.getProperty("user.name"); //platform independent and the hostname of the machine:
	java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
	System.out.println("Hostname of local machine: " + localMachine.getHostName());
	*/
	private static String machineUser;
	BackupAndRestore(){
		super();
		dbName = null;
		username = null;
		password = null;
		os = null;
		path = null;
		defaultPath = null;
		fileName = null;
	}
	public void initialize(){
		BackupAndRestore.os = System.getProperty("os.name");
		BackupAndRestore.os = BackupAndRestore.os.toLowerCase();
		setDbName("StoreDB");
		Connect connect = new Connect();
		setUsername(connect.getUsername());
		setPassword(connect.getPassword());
		setMachineUser(System.getProperty("user.name"));
		if (isWindows()) {
			//System.out.println("This is Windows");
			setPath("");
		}
		else if (isMac()) {
			defaultPath = "/Users/"+ getMachineUser() +"/Desktop/backup.sql";
			//System.out.println("This is Mac");
			//System.out.println(path);
			if(path != null){
				setPath(path);
			}
			else{
				setPath(defaultPath);
			}
		} 
		else if (isUnix()) {
			//System.out.println("This is Unix or Linux");
		}
		else {
			//System.out.println("Your OS is not support!!");
		}
		//backupDB();
		//restoreDB();
	}
	public void restoreDB(){
		if(fileExists()){
			createDB();
			String[] executeCmd = new String[]{"/usr/local/mysql/bin/mysql", "--user=" + getUsername(), "--password=" + getPassword(), getDbName(),"-e", "source "+getPath()};
			Process runtimeProcess;
			try {
				runtimeProcess = Runtime.getRuntime().exec(executeCmd);
				int processComplete = runtimeProcess.waitFor();
		 
				if (processComplete == 0) {
					//JOptionPane.showMessageDialog(null,"Backup restored successfully.");
				} else {
					JOptionPane.showMessageDialog(null,"Could not restore the backup.");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"Backup file could not be found.");
		}
	}
	public void backupDB() {
		String executeCmd = "/usr/local/mysql/bin/mysqldump -u " + getUsername() + " -p" + getPassword() + " " + getDbName() +" -r "+ getPath();
		Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
 
            if (processComplete == 0) {
            	//JOptionPane.showMessageDialog(null,"Backup created successfully.");
            } else {
            	JOptionPane.showMessageDialog(null,"Could not create the backup.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	public void createDB(){
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306",connect.getUsername(),connect.getPassword());
			String sql = "DROP DATABASE IF EXISTS " + getDbName();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
			
			sql = "create database " + getDbName();
			ps = con.prepareStatement(sql);
			ps.executeUpdate();
			
			//Clean-up environment
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean fileExists(){
		File f = new File(this.getPath());
		if(f.exists() && !f.isDirectory()) { 
			//System.out.println("exists");
			return true;
		}
		else{
			//System.out.println("does not exist");
			return false;
		}
	}
	
	public void setBackupRestorePath(){
		File file = new File("backupPath.txt");
        
        Scanner in;

        if (file.exists()){
            try{
                in = new Scanner(file);
                while(in.hasNext()){
                    String input = in.next();
                    if(input != null){
                    	BackupAndRestore.setPath(input);
                    }
                }
                in.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
        //If file does not exist, create one and write the default location (Desktop)
        else{
        	try {
        		file.createNewFile();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
        	BackupAndRestore br = new BackupAndRestore();
        	br.initialize();
        	if (file.exists()) {
	            PrintWriter writer = null;
	            try {
	                writer = new PrintWriter(file, "UTF-8");
	            } catch (FileNotFoundException e1) {
	                e1.printStackTrace();
	            } catch (UnsupportedEncodingException e1) {
	                e1.printStackTrace();
	            }
	            writer.println(BackupAndRestore.getPath());
	            writer.close();
	        }
        	br.initialize();
			br.backupDB();
        }
	}
	
	public static String getPath() {
		return path;
	}
	public static void setPath(String path) {
		BackupAndRestore.path = path;
	}
	public static String getDbName() {
		return dbName;
	}
	public static void setDbName(String dbName) {
		BackupAndRestore.dbName = dbName;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		BackupAndRestore.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		BackupAndRestore.password = password;
	}
	public static String getOs() {
		return os;
	}
	public static void setOs(String os) {
		BackupAndRestore.os = os;
	}
	public static String getMachineUser() {
		return machineUser;
	}
	public static void setMachineUser(String machineUser) {
		BackupAndRestore.machineUser = machineUser;
	}
	//Checking which os
	public static boolean isWindows() {
		return (os.indexOf("win") >= 0);
	}
	public static boolean isMac() {
		return (os.indexOf("mac") >= 0);
	}
	public static boolean isUnix() {
		return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0 );
	}
}
