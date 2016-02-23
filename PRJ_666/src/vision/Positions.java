package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Positions {
	private int id;
	private String name;
	private String description;
	
	public Positions(){
		id = 0;
		name = null;
		description = null;
	}
	public Positions(int id, String name, String description){
		this.id = id;
		this.name = name;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean checkPosition(int positionID){
		int count = 0;
		boolean check = false;
		try {
			Connect connect = new Connect();
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			Statement state = null;
			
			state = con.createStatement();
			String sql;
			sql = "SELECT * FROM Positions where ID = '" + positionID + "'";
			ResultSet rs = state.executeQuery(sql);
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.id = rs.getInt("ID");
				this.name = rs.getString("Name");
				this.description = rs.getString("Description");
				count++;
			}
			//Clean-up environment
			rs.close();
			state.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count == 1){
			if(this.id == 3 || this.id == 4 || this.id == 5){
				check = true;
			}
			else{
				check = false;
			}
		}
		return check;
	}
}
