package vision;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Transaction extends JFrame {
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
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM Transaction where ID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
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
		Connection con = null;
		int generatedKey = 0;
		ResultSet rs;
		try {
			con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql;
			if(promotionID == 0){
				sql = "INSERT INTO `Transaction`(CreateDate,SubTotal,Tax,Total,TransactionType,Method,EmployeeID) "
						+ "VALUE (?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, dateString);
				ps.setDouble(2, subTotal);
				ps.setDouble(3, tax);
				ps.setDouble(4, total);
				ps.setString(5, transactionType);
				ps.setString(6, transactionMethod);
				ps.setInt(7, employeeID);
				ps.execute();
				 
				//assigns generatedKey as transaction #, which will be used to write into transactionRecord
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
				    generatedKey = rs.getInt(1);
				}
			}
			else{
				sql = "INSERT INTO `Transaction`(CreateDate,SubTotal,Tax,Total,TransactionType,Method,PromotionID,EmployeeID) "
						+ "VALUE (?,?,?,?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, dateString);
				ps.setDouble(2, subTotal);
				ps.setDouble(3, tax);
				ps.setDouble(4, total);
				ps.setString(5, transactionType);
				ps.setString(6, transactionMethod);
				ps.setInt(7, promotionID);
				ps.setInt(8, employeeID);
				ps.execute();
				 
				//assigns generatedKey as transaction #, which will be used to write into transactionRecord
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
				    generatedKey = rs.getInt(1);
				}
			}
			//System.out.println("Inserted record's ID: " + generatedKey);
			
			//Clean-up environment
			rs.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatedKey;
	}
	public void writeProductRefundTransaction(int transactionID, String dateString, double finalRefundSubtotal, double finalRefundTax, double finalRefundTotal, String transactionType, String transactionMethod, int employeeID){
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "UPDATE `Transaction` SET CreateDate = ?, Subtotal = ?, Tax = ?, Total = ?, TransactionType = ?, Method = ?, EmployeeID = ? where ID = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, dateString);
			ps.setDouble(2, finalRefundSubtotal);
			ps.setDouble(3, finalRefundTax);
			ps.setDouble(4, finalRefundTotal);
			ps.setString(5, transactionType);
			ps.setString(6, transactionMethod);
			ps.setInt(7, employeeID);
			ps.setInt(8, transactionID);
		    ps.executeUpdate();
			
			//Clean-up environment
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public JPanel getTransactionInfo(int id) {
        JPanel panel_transactiondetails = new JPanel();
        JLabel jLabel1 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable jTable1 = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        JLabel jLabel2 = new JLabel();
        JPanel jPanel1 = new JPanel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JTextField transaction_id = new JTextField();
        JTextField create_date = new JTextField();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        JLabel jLabel9 = new JLabel();
        JLabel jLabel10 = new JLabel();
        JLabel jLabel11 = new JLabel();
        JTextField sub_total = new JTextField();
        JTextField tax = new JTextField();
        JTextField total = new JTextField();
        JTextField transaction_type = new JTextField();
        JTextField payment_method = new JTextField();
        JTextField promotion_id = new JTextField();
        JTextField employee_name = new JTextField();
        
        transaction_id.setEditable(false);
        create_date.setEditable(false);
        sub_total.setEditable(false);
        tax.setEditable(false);
        total.setEditable(false);
        transaction_type.setEditable(false);
        payment_method.setEditable(false);
        promotion_id.setEditable(false);
        employee_name.setEditable(false);
        
        jLabel1.setText("List of purchased items:");
        
        boolean check = getTransactionDetails(id);
        if(!check){
            return null;
        }
        
        transaction_id.setText(Integer.toString(id));
        //create_date.setText(createDate);
        sub_total.setText(Double.toString(subTotal));
        tax.setText(Double.toString(this.tax));
        total.setText(Double.toString(this.total));
        transaction_type.setText(transactionType);
        payment_method.setText(method);
        promotion_id.setText(Integer.toString(promotionID));
        
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            Connect connect = new Connect();
            con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
            		
            state = con.createStatement();
            String sql = "select CONCAT(FirstName, ' ', LastName) from Employee where ID =  '" + employeeID + "'";
            rs = state.executeQuery(sql);
            
            //Extract data from result set
            if(rs.next()){
                employee_name.setText(rs.getString(1));
            } else {
                employee_name.setText("Employee Not Found!");
            }
            
            sql = "select @rank := @rank + 1 AS '#', ProductID, QuantitySold as 'Quantity Sold',"
                + " UnitPrice as 'Unit Price', Returned, DateReturned as 'Date Returned' "
                + " from transactionrecord, (SELECT @rank := 0) m where TransactionID = '" + id + "'";
            
            rs = state.executeQuery(sql);
            jTable1.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
            
            if(rs != null){
                if(rs.next()) {
                    rs.beforeFirst();
                    jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(1);
                    model.addRow(new Object[]{"Searched Transaction does not exits."});
                }
            }
            sql = "select DATE_FORMAT(CreateDate, '%d-%M-%Y %H:%i') from StoreDB.Transaction where ID = '" + id + "'";
            rs = state.executeQuery(sql);
            if(rs.next()) {
                create_date.setText(rs.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state != null) state.close();
                if(rs != null) rs.close();
                if(con != null) con.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        
        jTable1.setRowHeight(30);
        jTable1.setBackground(Color.WHITE);
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setText("Transaction Details:");

        jLabel3.setText("Transaction ID:");

        jLabel4.setText("Create Date:");

        jLabel5.setText("Sub-Total:");

        jLabel6.setText("Tax:");

        jLabel7.setText("Total:");

        jLabel8.setText("Transaction Type:");

        jLabel9.setText("Payment Method:");

        jLabel10.setText("Promotion ID:");

        jLabel11.setText("Employee Name:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transaction_id)
                    .addComponent(create_date)
                    .addComponent(sub_total)
                    .addComponent(tax)
                    .addComponent(total)
                    .addComponent(transaction_type)
                    .addComponent(payment_method)
                    .addComponent(promotion_id)
                    .addComponent(employee_name, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(transaction_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(create_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(sub_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(transaction_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(payment_method, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(promotion_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(employee_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panel_transactiondetailsLayout = new javax.swing.GroupLayout(panel_transactiondetails);
        panel_transactiondetails.setLayout(panel_transactiondetailsLayout);
        panel_transactiondetailsLayout.setHorizontalGroup(
            panel_transactiondetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_transactiondetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_transactiondetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_transactiondetailsLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_transactiondetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panel_transactiondetailsLayout.setVerticalGroup(
            panel_transactiondetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_transactiondetailsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel_transactiondetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_transactiondetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_transactiondetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_transactiondetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        
        return panel_transactiondetails;
    }
}
