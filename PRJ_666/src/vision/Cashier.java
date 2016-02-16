package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Cashier {
	private Connect connect;
	private Connection con;
	
	public Cashier() throws Exception{
		connect = new Connect();
		setConnection();
	}
	public Connection setConnection() throws SQLException{
		con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
		return con;
	}
	public Product findProductID(int id) throws SQLException{
		//Execute a query
		Connection c = setConnection();
		Statement state = null;
		
		state = c.createStatement();
		String sql;
		sql = "SELECT * FROM Product where ID = '" + id + "'";
		ResultSet rs = state.executeQuery(sql);
		int count = 0;
		Product productByID = null;
		//Extract data from result set
		while(rs.next()){
			//Retrieve by column name
			int tempID = rs.getInt("ID");
			String tempName = rs.getString("Name");
			String tempDescription = rs.getString("Description");
			int tempCategoryID = rs.getInt("CategoryID");
			int tempSubCategoryID = rs.getInt("SubCategoryID");
			//double tempUnitCost = rs.getDouble("UnitCost");
			double tempSalePrice = rs.getDouble("SalePrice");
			int tempQuantity = rs.getInt("Quantity");
			//int tempSupplierID = rs.getInt("SupplierID");
			String tempNotes = rs.getString("Notes");
			
			//productByID = new Product(tempID,tempName,tempDescription,tempCategoryID,tempSubCategoryID,tempUnitCost,tempSalePrice,tempQuantity,tempSupplierID,tempNotes);
			productByID = new Product(tempID,tempName,tempDescription,tempCategoryID,tempSubCategoryID,tempSalePrice,tempQuantity,tempNotes);
			count++;
		}
		//Clean-up environment
		rs.close();
		state.close();
		c.close();
		con.close();
		return productByID;
	}
	public Product findProductName(String name) throws SQLException{
		//Execute a query
		Connection c = setConnection();
		Statement state = null;
		
		state = c.createStatement();
		String sql;
		sql = "SELECT * FROM Product where Name = '" + name + "'";
		ResultSet rs = state.executeQuery(sql);
		int count = 0;
		Product productByID = null;
		//Extract data from result set
		while(rs.next()){
			//Retrieve by column name
			int tempID = rs.getInt("ID");
			String tempName = rs.getString("Name");
			String tempDescription = rs.getString("Description");
			int tempCategoryID = rs.getInt("CategoryID");
			int tempSubCategoryID = rs.getInt("SubCategoryID");
			//double tempUnitCost = rs.getDouble("UnitCost");
			double tempSalePrice = rs.getDouble("SalePrice");
			int tempQuantity = rs.getInt("Quantity");
			//int tempSupplierID = rs.getInt("SupplierID");
			String tempNotes = rs.getString("Notes");
			
			//productByID = new Product(tempID,tempName,tempDescription,tempCategoryID,tempSubCategoryID,tempUnitCost,tempSalePrice,tempQuantity,tempSupplierID,tempNotes);
			productByID = new Product(tempID,tempName,tempDescription,tempCategoryID,tempSubCategoryID,tempSalePrice,tempQuantity,tempNotes);
			count++;
		}
		//Clean-up environment
		rs.close();
		state.close();
		c.close();
		con.close(); //Closes local DB connection
		return productByID;
	}
}
