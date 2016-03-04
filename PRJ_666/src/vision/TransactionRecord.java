package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class TransactionRecord {
	private int transactionID;
	private int productID;
	private int quantitySold;
	private double unitPrice;
	private int returned;
	private String dateReturned;
	private int employeeID;
	private double unitCost;
	
	public TransactionRecord(){
		transactionID = 0;
		productID = 0;
		quantitySold = 0;
		unitPrice = 0;
		returned = 0;
		dateReturned = null;
		employeeID = 0;
		unitCost = 0;
	}
	public TransactionRecord(int transactionID, int productID, int quantitySold, double unitPrice, int returned, String dateReturned, int employeeID, double unitCost){
		this.transactionID = transactionID;
		this.productID = productID;
		this.quantitySold = quantitySold;
		this.unitPrice = unitPrice;
		this.returned = returned;
		this.dateReturned = dateReturned;
		this.employeeID = employeeID;
		this.unitCost = unitCost;
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getQuantitySold() {
		return quantitySold;
	}
	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getReturned() {
		return returned;
	}
	public void setReturned(int returned) {
		this.returned = returned;
	}
	public String getDateReturned() {
		return dateReturned;
	}
	public void setDateReturned(String dateReturned) {
		this.dateReturned = dateReturned;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	public int getTransactionCount(int id){
		int count = 0;
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM transactionrecord where TransactionID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//Extract data from result set
			while(rs.next()){
				count++;
			}
			//Clean-up environment
			rs.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public void getTransactionRecord(int id, int row){
		int count = 0;
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM transactionrecord where TransactionID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.transactionID = rs.getInt("TransactionID");
				this.productID = rs.getInt("ProductID");
				this.quantitySold = rs.getInt("QuantitySold");
				this.unitPrice = rs.getDouble("UnitPrice");
				this.returned = rs.getInt("Returned");
				this.dateReturned = rs.getString("DateReturned");
				this.employeeID = rs.getInt("EmployeeID");
				this.unitCost = rs.getDouble("UnitCost");
				//row is coming from for loop, so it stops when it finds the record (row#) it was looking for in that transactionRecord
				//and returns it
				if(count == row){
					break;
				}
				count++;
			}
			//Clean-up environment
			rs.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertTransactionRecord(int transactionID, int productID, int productQuantity, double productSalePrice, int employeeID){
		Connect connect = new Connect();
		Connection con = null;
		try {
			con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			//same product from multiple supplier?
			double productUnitCost = getProductUnitCost(productID);
			
			//PromotionID causing foreign key relationship error
			String sql = "INSERT INTO `TransactionRecord`(TransactionID,ProductID,QuantitySold,UnitPrice,EmployeeID,UnitCost) "
			+ "VALUE (?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, transactionID);
			ps.setInt(2, productID);
			ps.setInt(3, productQuantity);
			ps.setDouble(4, productSalePrice);
			ps.setInt(5, employeeID);
			ps.setDouble(6, productUnitCost);
			ps.executeUpdate();
			
			//Clean-up environment
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Gets unit cost of a product
	public double getProductUnitCost(int id){
		int count = 0;
		double unitCost = 0;
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM product_supplier where ProductID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				unitCost = rs.getDouble("UnitCost");
				count++;
			}
			//Clean-up environment
			rs.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count == 1){
			return unitCost;
		}
		else {
			return 0;
		}
	}

	public void updateProduct(int productID, int newQuantity){
		int previousQuantity = getPreviousQuantity(productID);
		int newQuantity2 = previousQuantity - newQuantity;
		
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			//PromotionID causing foreign key relationship error
			//String sql = "UPDATE `Product` SET Quantity = '" + newQuantity2 + "'" + "where ID = '" + productID + "'";
			String sql = "UPDATE `Product` SET Quantity = ? where ID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, newQuantity2);
			ps.setInt(2, productID);
		    ps.executeUpdate();
			
			//Clean-up environment
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public int getPreviousQuantity(int id){
		int count = 0;
		int quantity = 0;
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM Product where ID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				quantity = rs.getInt("Quantity");
				count++;
			}
			//Clean-up environment
			rs.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count == 1){
			return quantity;
		}
		else {
			return 0;
		}
	}
}
