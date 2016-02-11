package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Employees {
	private Connect connect;
	private Connection con;

	//Employee
	private int id;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String homePhone;
	private String cellPhone;
	private String email;
	private int positionID;
	private String jobType;
	private String username;
	private String password;
	private String hireDate;
	private String terminationDate;
	
	public Employees() throws Exception{
		connect = new Connect();
	}
	
	//Setters
	public void setID(int id){ this.id = id; }
	public void setFirstName(String firstName){ this.firstName = firstName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	public void setStreet(String street){ this.street = street;	}
	public void setCity(String city){ this.city = city; }
	public void setStateProvince(String stateProvince){ this.stateProvince = stateProvince; }
	public void setPostalCode(String postalCode){ this.postalCode = postalCode; }
	public void setHomePhone(String homePhone){ this.homePhone = homePhone; }
	public void setCellPhone(String cellPhone){ this.cellPhone = cellPhone; }
	public void setEmail(String email){ this.email = email; }
	public void setPositionID(int positionID){ this.positionID = positionID; }
	public void setJobType(String jobType){ this.jobType = jobType; }
	public void setUserame(String username){ this.username = username; }
	//No need to set password
	public void setHireDate(String hireDate){ this.hireDate = hireDate; }
	public void setTerminationDate(String terminationDate){ this.terminationDate = terminationDate; }
	
	//Getters
	public int getID(){ return this.id; }
	public String getFirstName(){ return this.firstName; }
	public String getLastName(){ return this.lastName; }
	public String getStreet(){ return this.street; }
	public String getCity(){ return this.city; }
	public String getStateProvince(){ return this.stateProvince; }
	public String getPostalCode(){ return this.postalCode; }
	public String getHomePhone(){ return this.homePhone; }
	public String getCellPhone(){ return this.cellPhone; }
	public String getEmail(){ return this.email; }
	public int getPositionID(){ return this.positionID; }
	public String getJobType(){ return this.jobType; }	
	public String getUsername(){ return this.username; }
	//No need to get password
	public String getHireDate(){ return this.hireDate; }
	public String getTerminationDate(){ return this.terminationDate; }
	
	public boolean fetchLogin(String userID) throws Exception{
		//Execute a query
		con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
		Statement state = null;
		
		state = con.createStatement();
	    String sql;
	    sql = "SELECT * FROM Employee where UserName = '" + userID + "'";
	    ResultSet rs = state.executeQuery(sql);
	    int count = 0;
	    //Extract data from result set
	    while(rs.next()){
			//Retrieve by column name
	    	this.id = rs.getInt("ID");
	    	this.firstName = rs.getString("FirstName");
	    	this.lastName = rs.getString("LastName");
	    	this.street = rs.getString("Street");
	    	this.city = rs.getString("City");
	    	this.stateProvince = rs.getString("State_Province");
	    	this.postalCode = rs.getString("PostalCode");
	    	this.homePhone = rs.getString("HomePhone");
	    	this.cellPhone = rs.getString("CellPhone");
	    	this.email = rs.getString("Email");
	    	this.positionID = rs.getInt("PositionID");
	    	this.jobType = rs.getString("JobType");
		  	this.username = rs.getString("UserName");
		  	//No need to get password
		  	this.hireDate = rs.getString("HireDate");
		  	this.terminationDate = rs.getString("TerminationDate");
		  	count++;
	    }
	    //Clean-up environment
	    rs.close();
	    state.close();
	    con.close();
	    if(count == 1){
	    	return true;
	    }
	    else{
	    	return false;
	    }
	}
	
	public boolean validateEmpty(String temp){
		if(temp.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean login(String name, String pass) throws Exception{
		if(fetchLogin(name) == true){
			return true;
		}
		else{
			return false;
		}
	}
}