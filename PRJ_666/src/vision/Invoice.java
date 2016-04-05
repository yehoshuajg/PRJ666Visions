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
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ambica
 */
public class Invoice extends JPanel {
    private Connect con = null;
    private Connection c = null;
    
    public Invoice(){
        try{
            con = new Connect();
            c = DriverManager.getConnection(con.getURL(),con.getUsername(),con.getPassword());            
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Cannot establish connection with MySQL Server.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }    
    }
    
    public JPanel getInvoiceInfo(int id, JFrame fr){
        JPanel panel_invoice_details = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable jTable1 = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
	};
        JPanel jPanel1 = new JPanel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JTextField invoice_id = new JTextField();
        JTextField supplier_name = new JTextField();
        JTextField create_date = new JTextField();
        JTextField amount_due = new JTextField();
        JTextField amount_paid = new JTextField();
//        JButton btn_update = new JButton();
        JButton btn_close = new JButton();
        
        invoice_id.setEditable(false);
        supplier_name.setEditable(false);
        create_date.setEditable(false);
        amount_due.setEditable(false);
        amount_paid.setEditable(false);
        
        jLabel1.setText("Billed Orders:");

        jLabel2.setText("Invoice Details:");
      
        Statement state = null;
        ResultSet rs = null;
        try {
            		
            state = c.createStatement();
            String sql = "select o.ID as 'Order ID', CONCAT(e.FirstName, ' ', e.LastName) as 'Employee', "
                    + " o.CreateDate as 'Date Created', o.ReceivedDate as 'Date Received', o.cost as 'Cost'" 
                    + " from `Order` o join Employee e on e.ID = o.EmployeeID " 
                    + " where InvoiceID = " + id;
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
            
            sql = "select i.ID, i.AmountDue, i.AmountPaid, i.ReceivedDate, s.Name " 
                + " from Invoice i join Supplier s on s.ID = i.SupplierID "
                + " where i.ID =" + id;

            rs = state.executeQuery(sql);
            if(rs != null) {
                if(rs.next()){
                    invoice_id.setText(Integer.toString(rs.getInt(1)));
                    amount_due.setText(Double.toString(rs.getDouble(2)));
                    amount_paid.setText(Double.toString(rs.getDouble(3)));
                    create_date.setText(rs.getString(4));
                    supplier_name.setText(rs.getString(5));
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

        jLabel3.setText("Invoice ID:");

        jLabel4.setText("Supplier Name:");

        jLabel5.setText("Create Date:");

        jLabel6.setText("Amount Due:");

        jLabel7.setText("Amount Paid:");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(47, 47, 47)
                        .addComponent(invoice_id, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(supplier_name)
                            .addComponent(create_date)
                            .addComponent(amount_due)
                            .addComponent(amount_paid))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(invoice_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(supplier_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(create_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(amount_due, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(amount_paid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(204, Short.MAX_VALUE))
        );

/*        btn_update.setText("Update");
        btn_update.addActionListener((java.awt.event.ActionEvent evt) -> {
            Statement s = null;
            ResultSet r = null;
            try {
                double amountdue = Double.parseDouble(amount_due.getText());
                double amountpaid = Double.parseDouble(amount_paid.getText());
                
                s = c.createStatement();
                String sql = "select sum(Cost), sum(AmountPaid) from `Order` where InvoiceID = " + id;
                r = s.executeQuery(sql);
                
                if(r.next()){
                    double ck_amountdue = r.getDouble(1);
                    double ck_amountpaid = r.getDouble(2);
                    
                    if(amountpaid < ck_amountpaid) {
                        JOptionPane.showMessageDialog(null, "You cannot deduct the amount you've already paid.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    } else if(amountdue < ck_amountdue){
                        JOptionPane.showMessageDialog(null, "You cannot have amount due less then total cost of products.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        sql = "update `Invoice` set AmountDue = " + amountdue + ", AmountPaid = " + amountpaid
                            + " where ID = " + id;
                        
                        PreparedStatement ps = c.prepareStatement(sql);
                        int dump = ps.executeUpdate();
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No orders found that is paid by invoice id " + id + ".",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            } catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(null, "Amount Due or Amount Paid is not a number.", 
            			"Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception sql){
                JOptionPane.showMessageDialog(null, "Problem connecting to MySQL server.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
*/        
        btn_close.setText("Close");
        btn_close.addActionListener((java.awt.event.ActionEvent evt) -> {
            fr.dispose();
        });
        
        GroupLayout panel_invoice_detailsLayout = new GroupLayout(panel_invoice_details);
        panel_invoice_details.setLayout(panel_invoice_detailsLayout);
        panel_invoice_detailsLayout.setHorizontalGroup(
            panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_invoice_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panel_invoice_detailsLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_invoice_detailsLayout.createSequentialGroup()
//                        .addComponent(btn_update, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_close, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panel_invoice_detailsLayout.setVerticalGroup(
            panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_invoice_detailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panel_invoice_detailsLayout.createSequentialGroup()
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(panel_invoice_detailsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                            .addComponent(btn_update)
                            .addComponent(btn_close)))
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_invoice_details, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_invoice_details, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );
        
        return panel_invoice_details;
    }
}
