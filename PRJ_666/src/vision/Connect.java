package vision;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {
	
	//JDBC driver name and database URL
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/StoreDB?useSSL=false"; //DB name
	
	//Created user in mySQL and gave it root permissions
	//Database credentials
	static String username = "user";
	static String password = "pass";
	
	public final double tax;
	
	private Connection con;
	
	public Connect() {
		Properties prop = new Properties();
		double t = 13.0;
		try{
			InputStream input = new FileInputStream("SYNG-MGMT-SYS.properties");
			prop.load(input);
			
			t = Double.parseDouble(prop.getProperty("tax"));
		} catch(IOException e) {
			try{
				//this is to save current year(2016) tax amount into new config file.
				OutputStream output = new FileOutputStream("SYNG-MGMT-SYS.properties");
				prop.setProperty("tax", "13");
				
				// save properties to project root folder
				prop.store(output, null);
			} catch(IOException ex) {
				//tax file not found so created one and entered current tax amount.
			}
		}
		tax = t;
	}
	
	public boolean updateTax(double d){
		boolean ret = true;
		Properties prop = new Properties();
		
		try{
			//this is to save current year(2016) tax amount into new config file.
			OutputStream output = new FileOutputStream("SYNG-MGMT-SYS.properties");
			String tax = String.valueOf(d);
			prop.setProperty("tax", tax);
			
			// save properties to project root folder
			prop.store(output, null);
		} catch(IOException ex) {
			//tax file not found so created one and entered current tax amount.
			ret = false;
		}
		
		return ret;
	}
	
	public static Connection connectionSetup() throws Exception{
		try{
			//Register JDBC driver
			Class.forName(driver);
			
			//Open a connection
			Connection con = DriverManager.getConnection(url,username,password);
			//System.out.println("Connected.");
			return con;
		}
		catch(Exception e){
			System.out.println(e);			
		}
		return null;
	}
	
	public Connection getConnection(){
		return con;
	}
	public void closeConnection() throws SQLException{
		con.close();
	}
	public String getURL(){
		return url;
	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
}
