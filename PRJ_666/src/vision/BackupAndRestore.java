package vision;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.security.CodeSource;

import javax.swing.JOptionPane;

public class BackupAndRestore {

	private static String dbName;
	private static String username;
	private static String password;
	private static String os;
	private static String path;
	
	/*
	To get the currently logged in user:
	System.getProperty("user.name"); //platform independent and the hostname of the machine:
	java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
	System.out.println("Hostname of local machine: " + localMachine.getHostName());
	*/
	private static String machineUser;
	BackupAndRestore(){
		super();
		os = null;
		path = null;
		dbName = null;
	}
	BackupAndRestore(String os){
		if(os != null){
			os = os.toLowerCase();
			BackupAndRestore.os = os;
			setDbName("StoreDB");
			Connect connect = new Connect();
			setUsername(connect.getUsername());
			setPassword(connect.getPassword());
			setMachineUser(System.getProperty("user.name"));
			if (isWindows()) {
				//System.out.println("This is Windows");
				setPath("");
			} else if (isMac()) {
				//System.out.println("This is Mac");
				setPath("/Users/"+ getMachineUser() +"/Desktop/backup.sql");
			} else if (isUnix()) {
				//System.out.println("This is Unix or Linux");
			}
			else {
				//System.out.println("Your OS is not support!!");
			}
			//backupDB();
			restoreDB();
		}
	}
	public void restoreDB(){
		//writeToFile();
		String[] executeCmd = new String[]{"/usr/local/mysql/bin/mysql", "--user=" + getUsername(), "--password=" + getPassword(), getDbName(),"-e", "source "+getPath()};
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
 
            if (processComplete == 0) {
                System.out.println("Backup restored successfully");
                //return true;
            } else {
                System.out.println("Could not restore the backup");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
 
        //return false;
	}
	private void writeToFile() {
		String drop = "drop database if exists StoreDB;\n";
		String updatedDrop = drop.replace("\n","\r\n");
		String create = "create database StoreDB;\n";
		String updatedCreate = create.replace("\n","\r\n");
		String use = "use StoreDB;\n";
		String updatedUse = use.replace("\n","\r\n");
		String app = "\n\n\n";
		String updatedApp = app.replace("\n","\r\n");
		RandomAccessFile f;
		try {
			f = new RandomAccessFile(new File(getPath()), "rw");
			f.seek(0); // to the beginning
			f.write(updatedDrop.getBytes());
			f.write(updatedCreate.getBytes());
			f.write(updatedUse.getBytes());
			f.write(updatedApp.getBytes());
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void backupDB() {
		//String executeCmd = "/usr/local/mysql/bin/mysqldump -u " + getUsername() + " -p" + getPassword() + " --add-drop-database -B " + getDbName() + " -r " + getPath();
		String executeCmd = "/usr/local/mysql/bin/mysqldump -u " + getUsername() + " -p" + getPassword() + " " + getDbName() +" -r "+ getPath();

		//System.out.println(executeCmd);
		Process runtimeProcess;
        try {
 
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
 
            if (processComplete == 0) {
                //System.out.println("Backup created successfully");
                //return true;
            } else {
                //System.out.println("Could not create the backup");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //return false;
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
