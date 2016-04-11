/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Gaurav
 */
public class Order extends JPanel {
    private Connect con = null;
    private Connection c = null;
    
    boolean check_received = false; //for updating if order is received or not.
    
    Map<String, Integer> updates = new HashMap<>();
    
    public Order() {
        try{
            con = new Connect();
            c = DriverManager.getConnection(con.getURL(),con.getUsername(),con.getPassword());            
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Cannot establish connection with MySQL Server.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public JPanel getOrderInfo(int id, JFrame fr){
        
        JPanel panel_orderDetails = new JPanel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel1 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable jTable1 = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //no table data is editable.
            }
	};
        jTable1.getTableHeader().setReorderingAllowed(false);
        JPanel jPanel1 = new JPanel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        JLabel jLabel9 = new JLabel();
        JLabel jLabel10 = new JLabel();
        JTextField order_id = new JTextField();
        JTextField supplier_name = new JTextField();
        JTextField employee_name = new JTextField();
        JTextField create_date = new JTextField();
        JTextField cost = new JTextField();
        JTextField amount_paid = new JTextField();
        JTextField invoice_id = new JTextField();
        JTextField received_date_box = new JTextField();
        //JButton btn_update = new JButton();
        JButton btn_close = new JButton();
        
        order_id.setEditable(false);
        supplier_name.setEditable(false);
        employee_name.setEditable(false);
        create_date.setEditable(false);
        cost.setEditable(false);
        amount_paid.setEditable(false);
        invoice_id.setEditable(false);
        received_date_box.setEditable(false);
        
        jLabel2.setText("Order Details:");

        jLabel1.setText("Ordered Items:");
        
        //Inserting data into details items..
        Statement state = null;
        ResultSet rs = null;
        try {
            		
            state = c.createStatement();
            String sql = "select p.ID, p.Name,  o.OrderedQuantity as 'Ordered', o.ReceivedQuantity "
                    + " as 'Received', o.Cost as 'Total Cost' " 
                    + " from orderdetail o join Product p on o.ProductID = p.ID " 
                    + " where orderID = " + id + "";
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
                    model.addRow(new Object[]{"Searched Order does not exits."});
                }
            } else {
                return null;
            }
            
            sql = "select o.ID, s.Name, CONCAT(e.FirstName, ' ', e.LastName), DATE_FORMAT(o.CreateDate, '%d-%M-%Y %H:%i') as CreateDate, "
            	+ "DATE_FORMAT(o.ReceivedDate, '%d-%M-%Y %H:%i')  as ReceivedDate,"
                + " o.Cost, o.AmountPaid, o.InvoiceID from `Order` o join Supplier s on o.SupplierID = s.ID" 
                + " join Employee e on o.EmployeeID = e.ID where o.ID = " + id;

            rs = state.executeQuery(sql);
            if(rs != null) {
                if(rs.next()){
                    order_id.setText(Integer.toString(rs.getInt(1)));
                    supplier_name.setText(rs.getString(2));
                    employee_name.setText(rs.getString(3));
                    create_date.setText(rs.getString(4));
                    cost.setText(Double.toString(rs.getDouble(6)));
                    amount_paid.setText(Double.toString(rs.getDouble(7)));
                    invoice_id.setText(Integer.toString(rs.getInt(8)));
                    
                    String received = rs.getString(5);
                    if(received == null || received.equals("null")){
                        //received_date_box.setSelected(false);
                    	received_date_box.setText("Not Received");
                    } else {
                        //received_date_box.setSelected(true);
                    	received_date_box.setText(received);
                        //check_received = true;
                    }
                } else {
                  return null;
                }
            } else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                if(state != null) state.close();
                if(rs != null) rs.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        jTable1.setRowHeight(30);
        jTable1.setBackground(Color.WHITE);  
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setText("Order ID:");

        jLabel4.setText("Supplier Name:");

        jLabel5.setText("Created By:");

        jLabel6.setText("Created On:");

        jLabel7.setText("Mark Received:");

        jLabel8.setText("Cost:");

        jLabel9.setText("Amount Paid:");

        jLabel10.setText("Invoice ID:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(55, 55, 55)
                        .addComponent(order_id))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(supplier_name)
                            .addComponent(employee_name)
                            .addComponent(create_date)
                            .addComponent(cost)
                            .addComponent(amount_paid)
                            .addComponent(invoice_id)
                            //.addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(received_date_box))))
                                //.addGap(0, 110, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(order_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(supplier_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(employee_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(create_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(received_date_box))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(amount_paid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(invoice_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
/*        //Storing data to update in Map/HashMap so, when update is pressed everything will be updated.
        TableModel model = jTable1.getModel();
        
        model.addTableModelListener((TableModelEvent e) -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            String productName = (String) model.getValueAt(row, 0);
                
            try{
                int order_qty = (int) model.getValueAt(row, column - 1);
                int recieve_qty = Integer.parseInt((String) model.getValueAt(row, column));
                
                if(recieve_qty < order_qty + 10){
                    updates.put(productName, recieve_qty);
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot exceed limit: ordered quantity + 10.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    model.setValueAt(Integer.toString(order_qty), row, column);
                }
            } catch (NumberFormatException ex){
                model.setValueAt("0", row, column);
                JOptionPane.showMessageDialog(null, "Number not recognized.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
/*     //Processing Order - Updating received products.
        btn_update.setText("Update");
        btn_update.addActionListener((java.awt.event.ActionEvent evt) -> {
            PreparedStatement s = null;
            try {
                for (Map.Entry<String, Integer> entry : updates.entrySet()) {
                    String sql = "update OrderDetail set ReceivedQuantity = '" + entry.getValue() + "'"
                            + " where ProductID = (Select ID from Product where Name = '" + entry.getKey() + "')"
                            + " and OrderID = '" + order_id.getText() + "'";
                    s = c.prepareStatement(sql);
                    int r = s.executeUpdate();
                }
                
                updates.clear();
                if(!check_received && received_date_box.isSelected() == true){
                    String sql = "update `Order` set ReceivedDate = NOW() where ID = '" + order_id.getText() + "'";
                    s = c.prepareStatement(sql);
                    int r = s.executeUpdate();
                }
                
            } catch (Exception sql){
                JOptionPane.showMessageDialog(null, "Problem connecting to MySQL server.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (s != null) s.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
        });
 */       
        btn_close.setText("Close");
        btn_close.addActionListener((java.awt.event.ActionEvent evt) -> {
            fr.dispose();
        });

        GroupLayout panel_orderDetailsLayout = new GroupLayout(panel_orderDetails);
        panel_orderDetails.setLayout(panel_orderDetailsLayout);
        panel_orderDetailsLayout.setHorizontalGroup(
            panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_orderDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                    .addGroup(panel_orderDetailsLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_orderDetailsLayout.createSequentialGroup()
//                        .addComponent(btn_update, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_close, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_orderDetailsLayout.setVerticalGroup(
            panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_orderDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panel_orderDetailsLayout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(panel_orderDetailsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                            .addComponent(btn_update)
                            .addComponent(btn_close))))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_orderDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_orderDetails, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        
        return panel_orderDetails;
    }
}
