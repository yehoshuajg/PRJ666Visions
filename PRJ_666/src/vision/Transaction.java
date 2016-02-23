package vision;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {
	private int id;
	private String createDate;
	private double subTotal;
	private double tax;
	private double total;
	private String transactionType;
	private String method;
	private int promotionID;
	private int employeeID;
	
	public Transaction(){
		id = 0;
		createDate = null;
		subTotal = 0;
		tax = 0;
		total = 0;
		transactionType = null;
		method = null;
		promotionID = 0;
		employeeID = 0;
	}
	public Transaction(int id, String createDate, double subTotal, double tax, double total, String transactionType, String method, int promotionID, int employeeID){
		this.id = id;
		this.createDate = createDate;
		this.subTotal = subTotal;
		this.tax = tax;
		this.total = total;
		this.transactionType = transactionType;
		this.method = method;
		this.promotionID = promotionID;
		this.employeeID = employeeID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public int getPromotionID() {
		return promotionID;
	}
	public void setPromotionID(int promotionID) {
		this.promotionID = promotionID;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public boolean getTransactionDetails(int id){
		int count = 0;
		try {
			Connect connect = new Connect();
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			Statement state = null;
			
			state = con.createStatement();
			String sql;
			sql = "SELECT * FROM Transaction where ID = '" + id + "'";
			ResultSet rs = state.executeQuery(sql);
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.id = rs.getInt("ID");
				this.createDate = rs.getString("CreateDate");
				this.subTotal = rs.getDouble("SubTotal");
				this.tax = rs.getDouble("Tax");
				this.total = rs.getDouble("Total");
				this.transactionType = rs.getString("TransactionType");
				this.method = rs.getString("Method");
				this.promotionID = rs.getInt("PromotionID");
				this.employeeID = rs.getInt("EmployeeID");
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
			return true;
		}
		else {
			return false;
		}
	}
	public int writeTransactionCash(String dateString, double subTotal, double tax, double total, String transactionType, String transactionMethod, int promotionID, int employeeID){
		Connect connect = new Connect();
		Connection con;
		Statement state = null;
		int generatedKey = 0;
		try {
			con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			state = con.createStatement();
			String sql;
			//PromotionID causing foreign key relationship error
			/*sql = "INSERT INTO `Transaction`(CreateDate,SubTotal,Tax,Total,TransactionType,Method,PromotionID,EmployeeID) "
					+ "VALUE ('"+dateString+"','"+subTotal+"','"+tax+"',"+total+",'"+transactionType+"','"+transactionMethod+"','"+promotionID+"','"+employeeID+"')";
			*/
			
			sql = "INSERT INTO `Transaction`(CreateDate,SubTotal,Tax,Total,TransactionType,Method,EmployeeID) "
					+ "VALUE ('"+dateString+"','"+subTotal+"','"+tax+"',"+total+",'"+transactionType+"','"+transactionMethod+"','"+employeeID+"')";
			
			//state.executeUpdate(sql);
			
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.execute();
			 
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			//System.out.println("Inserted record's ID: " + generatedKey);
			
			//Clean-up environment
			state.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatedKey;
	}
}
