package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	
	//JDBC driver name and database URL
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/StoreDB?useSSL=false"; //DB name
	
	//Created user in mySQL and gave it root permissions
	//Database credentials
	static String username = "user";
	static String password = "pass";
	
	private Connection con;
	
	public Connect() throws Exception{
		
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
