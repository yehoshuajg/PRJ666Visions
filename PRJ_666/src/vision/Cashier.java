package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
			double tempSalePrice = rs.getDouble("SalePrice");
			int tempQuantity = rs.getInt("Quantity");
			String tempNotes = rs.getString("Notes");
			
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
			double tempSalePrice = rs.getDouble("SalePrice");
			int tempQuantity = rs.getInt("Quantity");
			String tempNotes = rs.getString("Notes");
			
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
	public void findProductUsingLike(String input, Vector<Product> productByLike){
		String columnName = null;
		Connect connect = new Connect();
		Connection con = null;
		Statement state = null;
		ResultSet rs = null;
		
		try{
			con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			state = con.createStatement();
		}catch(Exception e){
			
		}
		
		//Name
		columnName = "Name"; 
		productByLike = findProductUsingTemplate(state, rs, input, columnName, productByLike);
		
		//Description
		columnName = "Description";
		productByLike = findProductUsingTemplate(state, rs, input, columnName, productByLike);
		//findProductUsingTemplate(state, rs, input, columnName, productByLike);
		
		//Notes
		columnName = "Notes";
		productByLike = findProductUsingTemplate(state, rs, input, columnName, productByLike);
		//findProductUsingTemplate(state, rs, input, columnName, productByLike);
		
		String tempInput = input;
		String[] splited = tempInput.split("\\s+");
		for(int i = 0; i < splited.length; i++){
			columnName = "Name";
			productByLike = findProductUsingTemplate(state, rs, input, columnName, productByLike);
			
			columnName = "Description";
			productByLike = findProductUsingTemplate(state, rs, input, columnName, productByLike);
			
			columnName = "Notes";
			productByLike = findProductUsingTemplate(state, rs, input, columnName, productByLike);
		}
		//Clean-up environment
		try{
			rs.close();
			state.close();
			con.close();
		}catch(Exception e){}
			
	}
	public Vector<Product> findProductUsingTemplate(Statement state, ResultSet rs, String input, String columnName, Vector<Product> productByLike){
		Product p = null;
		String sql = null;
		boolean check = false;
		try {
			sql = "SELECT * FROM Product WHERE " +  columnName + " LIKE '%" + input + "%'";
			rs = state.executeQuery(sql);
			
			//Extract data from result set
			while(rs.next()){
				p = new Product();
				p.setID(rs.getInt("ID"));
				p.setName(rs.getString("Name"));
				p.setDescription(rs.getString("Description"));
				p.setSalePrice(rs.getDouble("SalePrice"));
				p.setQuantity(rs.getInt("Quantity"));
				p.setNotes(rs.getString("Notes"));
				if(p != null){
					if(productByLike.size() > 0){
						for(int i = 0; i < productByLike.size(); i++){
							if(p.getID() == productByLike.get(i).getID()){
								//System.out.println(productByLike.get(i).getID() + " " + productByLike.get(i).getName() + " Exists");
								check = true;
								break;
							}
							else{
								check = false;
							}
						}
					}
				}
				if(check == false){
					//System.out.println(p.getID() + "Doesn't exist. adding " + p.getName());
					productByLike.add(p);
				}
			}
		}catch(Exception e){
		}
		//System.out.println(sql);
		return productByLike;
	}
}
