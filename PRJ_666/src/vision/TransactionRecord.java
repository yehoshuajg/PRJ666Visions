package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class TransactionRecord {
	private int transactionID;
	private Vector<Integer> productID = new Vector<Integer>();
	private Vector<Integer> quantitySold = new Vector<Integer>();
	private Vector<Double> unitPrice = new Vector<Double>();
	private Vector<Integer> returned = new Vector<Integer>();
	private Vector<String> dateReturned = new Vector<String>();
	private Vector<Integer> employeeID = new Vector<Integer>();
	private Vector<Double> unitCost = new Vector<Double>();
	
	public TransactionRecord(){
		transactionID = 0;
		productID.clear();
		quantitySold.clear();
		unitPrice.clear();
		returned.clear();
		dateReturned.clear();
		employeeID.clear();
		unitCost.clear();
	}
	public TransactionRecord(int transactionID, int productID, int quantitySold, double unitPrice, int returned, String dateReturned, int employeeID, double unitCost){
		this.transactionID = transactionID;
		this.productID.add(productID);
		this.quantitySold.add(quantitySold);
		this.unitPrice.addElement(unitPrice);
		this.returned.add(returned);
		this.dateReturned.add(dateReturned);
		this.employeeID.add(employeeID);
		this.unitCost.addElement(unitCost);
	}
	public void getTransactionRecord(int id){
		int count = 0;
		try {
			Connect connect = new Connect();
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			Statement state = null;
			
			state = con.createStatement();
			String sql;
			sql = "SELECT * FROM transactionrecord where TransactionID = '" + id + "'";
			ResultSet rs = state.executeQuery(sql);
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.transactionID = rs.getInt("TransactionID");
				this.productID.add(rs.getInt("ProductID"));
				this.quantitySold.add(rs.getInt("QuantitySold"));
				this.unitPrice.addElement(rs.getDouble("UnitPrice"));
				this.returned.add(rs.getInt("Returned"));
				this.dateReturned.add(rs.getString("DateReturned"));
				this.employeeID.add(rs.getInt("EmployeeID"));
				this.unitCost.addElement(rs.getDouble("UnitCost"));
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
	}
	public int getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	public Vector<Integer> getProductID() {
		return productID;
	}
	public void setProductID(Vector<Integer> productID) {
		this.productID = productID;
	}
	public Vector<Integer> getQuantitySold() {
		return quantitySold;
	}
	public void setQuantitySold(Vector<Integer> quantitySold) {
		this.quantitySold = quantitySold;
	}
	public Vector<Double> getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Vector<Double> unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Vector<Integer> getReturned() {
		return returned;
	}
	public void setReturned(Vector<Integer> returned) {
		this.returned = returned;
	}
	public Vector<String> getDateReturned() {
		return dateReturned;
	}
	public void setDateReturned(Vector<String> dateReturned) {
		this.dateReturned = dateReturned;
	}
	public Vector<Integer> getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(Vector<Integer> employeeID) {
		this.employeeID = employeeID;
	}
	public Vector<Double> getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(Vector<Double> unitCost) {
		this.unitCost = unitCost;
	}
	
}
