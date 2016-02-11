/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Ambica
 */
public class Reports extends javax.swing.JFrame {
    
    private JButton btn_Inventory = null;
    private JButton btn_InvoiceList = null;
    private JButton btn_OrderList = null;
    private JButton btn_Revenue = null;
    private JButton btn_Transaction = null;
    private JButton btn_Filter = null;
    private JLabel jLabel1 = null;
    private JPanel jPanel1 = null;
    private JScrollPane jScrollPane1 = null;
    private JSeparator jSeparator1 = null;
    private JTable jTable1 = null; 
    private Connection c = null;
    
    private String query = null;
    private String order = null;
    private String active = null;
    
    private ArrayList table_headings = new ArrayList();
    
    public Reports(){
        super();
        
        try {
            Connect connect = new Connect();
            c = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
    }
    
    protected void updateReport(){
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query + order);
            
            if(rs != null){
                    jTable1.setModel(DbUtils.resultSetToTableModel(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }    
    }
    
    protected void CreateWindow(){
        jPanel1 = new javax.swing.JPanel();
        btn_Revenue = new javax.swing.JButton();
        btn_Inventory = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        
        jTable1 = new javax.swing.JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTable1.setRowHeight(30);
        jTable1.setBackground(Color.WHITE);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        jLabel1 = new javax.swing.JLabel();
        btn_OrderList = new javax.swing.JButton();
        btn_Transaction = new javax.swing.JButton();
        btn_InvoiceList = new javax.swing.JButton();
        btn_Filter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_Revenue.setText("Revenue");
        btn_Revenue.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            /*    query = "select Year(CreateDate) as 'Year', MonthName(CreateDate) as 'Month', "
                        + "sum(ROUND(subTotal, 2)) as 'Before Tax', sum(ROUND(Tax, 2)) as 'Tax', "
                        + "sum(ROUND(Total, 2)) as 'Total' from Transaction "
                        + "where TransactionType = 'Sale'group by Year, Month";
            */
                query = "select Year(CreateDate) as 'Year', MonthName(CreateDate) as 'Month',"
                        + " IFNULL(i.AmountDue, 0) as 'Order Cost', IFNULL(i.AmountPaid, 0) as 'Order Paid',"
                        + " sum(ROUND(subTotal, 2)) as 'Sales Before Tax', sum(ROUND(Tax, 2)) as 'Sales Tax',"
                        + " sum(ROUND(Total, 2)) as 'Sales Total', ROUND((SELECT IFNULL(SUM(UnitCost * QuantitySold), 0)"
                        + " FROM TransactionRecord tr WHERE tr.TransactionId = Transaction.ID), 2) as 'Product Cost',"
                        + " ROUND(sum(ROUND(Total, 2)) - (IFNULL(i.AmountDue, 0) + (SELECT IFNULL(SUM(UnitCost * QuantitySold), 0)"
                        + " FROM TransactionRecord tr WHERE tr.TransactionId = Transaction.ID) - IFNULL(i.AmountPaid, 0)), 2)"
                        + " as 'Revenue' from Transaction Left join Invoice i"
                        + " ON Year(i.ReceivedDate) = Year(CreateDate)"
                        + " AND MonthName(i.ReceivedDate) = MonthName(CreateDate)"
                        + " where TransactionType = 'Sale' "
                        + " group by Year, Month";
                active = "revenue";
                
                table_headings.clear();
                table_headings.add("`Year`");
                table_headings.add("`Month`");
                table_headings.add("`Order Cost`");
                table_headings.add("`Order Paid`");
                table_headings.add("`Sales Before Tax`");
                table_headings.add("`Sales Tax`");
                table_headings.add("`Sales Total`");
                table_headings.add("`Product Cost`");
                table_headings.add("`Revenue`");
                
                order = " ";
                updateReport();    
            }
        });
        
        btn_Inventory.setText("Inventory");
        btn_Inventory.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "select p.Name as 'Product', p.Description as 'Product Description', c1.Name as 'Category',"
                        + " c2.Name as 'SubCategory', ROUND(AVG(ps.UnitCost), 2) as 'Unit Cost', ROUND(p.SalePrice, 2) "
                        + " as 'Sale Price', p.Quantity as 'Quantity' from Product p "
                        + " left outer join storedb.category as c1 on p.CategoryID = c1.ID "
                        + " left outer join storedb.category as c2 on p.SubCategoryID = c2.ID"
                        + " join product_supplier as ps on p.ID = ps.ProductID group by ps.ProductID";
                
                active = "inventory";
                
                table_headings.clear();
                table_headings.add("`Product`");
                table_headings.add("`Product Description`");
                table_headings.add("`Category`");
                table_headings.add("`SubCategory`");
                table_headings.add("`Unit Cost`");
                table_headings.add("`Sale Price`");
                table_headings.add("`Quantity`");
                
                order = " ";
                updateReport();
            }
        });
        
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTable1.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
        jScrollPane1.setViewportView(jTable1);
        
        jTable1.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = jTable1.columnAtPoint(e.getPoint());
                
                order = " order by " + table_headings.get(col);
                updateReport();
            }
        });

        btn_OrderList.setText("Order List");
        btn_OrderList.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "select s.Name as 'Supplier', o.CreateDate as 'Date Created', o.ReceivedDate as 'Date Received'," 
                        + " o.Cost as 'Cost', IFNULL(o.AmountPaid, 0) as 'Amount Paid'" 
                        + " from `order` o, supplier s" 
                        + " where o.SupplierID = s.ID";
                
                active = "orderlist";
                
                table_headings.clear();
                table_headings.add("`Supplier`");
                table_headings.add("`Date Created`");
                table_headings.add("`Date Received`");
                table_headings.add("`Cost`");
                table_headings.add("`Amount Paid`");
                
                order = " ";
                updateReport();
            }
        });
        
        btn_Transaction.setText("Transaction");
        btn_Transaction.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "select t.ID as 'ID', CreateDate as 'Date Created', TransactionType as 'Transaction Type'," 
                        + " Method as 'Payment Type', CONCAT(e.FirstName, ' ', e.LastName) as 'Employee',"
                        + " SubTotal, Tax, Total from `Transaction` t, Employee e" 
                        + " where t.EmployeeID = e.ID";
                
                active = "transaction";
                
                table_headings.clear();
                table_headings.add("`ID`");
                table_headings.add("`Date Created`");
                table_headings.add("`Transaction Type`");
                table_headings.add("`Payment Type`");
                table_headings.add("`Employee`");
                table_headings.add("`SubTotal`");
                table_headings.add("`Tax`");
                table_headings.add("`Total`");
                
                order = " order by t.ID";
                updateReport();
            }
        });

        btn_InvoiceList.setText("Invoice List");
        btn_InvoiceList.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "SELECT i.ID as 'ID', s.Name as 'Name', i.ReceivedDate as 'Date Received', i.AmountDue as "
                        + "'Amount Due', ROUND(i.AmountDue - i.AmountPaid, 2) as 'Outstanding'" 
                        + " FROM storedb.invoice i, Supplier s" 
                        + " where i.SupplierID = s.ID";
                
                active = "invoicelist";
                
                table_headings.clear();
                table_headings.add("`i.ID`");
                table_headings.add("`s.Name`");
                table_headings.add("`Date Received`");
                table_headings.add("`Amount Due`");
                table_headings.add("`Outstanding`");
                
                order = " order by i.ID";
                updateReport();
            }
        });
        
        btn_Filter.setText("Filter");
        btn_Filter.setPreferredSize(new java.awt.Dimension(75, 23));
        
        btn_Filter.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Ask for the user name and say hello
                JOptionPane.showInternalMessageDialog(rootPane, "Active tab is " + active);
                
                JFrame filter = new JFrame("Filter");
                filter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btn_InvoiceList, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Transaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_OrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Inventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_Revenue, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Filter, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(177, 177, 177))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(btn_Revenue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Inventory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_OrderList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Transaction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_InvoiceList))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btn_Filter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    
    public JPanel getWindow(){
        if(jPanel1 == null)
        {
            CreateWindow();
        }
        //Performing a mouse click on revenue so report revenue report gets generated as default.
        btn_Revenue.doClick();
        return jPanel1;
    }
    
}
