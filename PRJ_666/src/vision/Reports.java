/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import org.jdatepicker.impl.*;

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
                if(rs.next()) {
                    jTable1.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(1);
                    model.addRow(new Object[]{"No corresponding records to your search criteria."});
                }
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
    
    private void CreateWindow(){
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
                query = "select Year(CreateDate) as 'Year', MonthName(CreateDate) as 'Month',"
                        + " IFNULL(i.AmountDue, 0) as 'Order Cost', IFNULL(i.AmountPaid, 0) as 'Order Paid',"
                        + " sum(ROUND(SubTotal, 2)) as 'SubTotal', sum(ROUND(Tax, 2)) as 'Tax',"
                        + " sum(ROUND(Total, 2)) as 'Total', "
                        + " ROUND(sum(ROUND(Total, 2)) - (IFNULL(i.AmountDue, 0) - IFNULL(i.AmountPaid, 0)), 2)"
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
                table_headings.add("`Sub-Total`");
                table_headings.add("`Tax`");
                table_headings.add("`Total`");
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
                        + " left outer join StoreDB.Category as c1 on p.CategoryID = c1.ID "
                        + " left outer join StoreDB.Category as c2 on p.SubCategoryID = c2.ID"
                        + " join Product_Supplier as ps on p.ID = ps.ProductID group by ps.ProductID";
                
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

        btn_OrderList.setText("Orders");
        btn_OrderList.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "select s.Name as 'Supplier', o.CreateDate as 'Date Created', o.ReceivedDate as 'Date Received'," 
                        + " o.Cost as 'Cost', IFNULL(o.AmountPaid, 0) as 'Amount Paid', 'View Details' as ' '" 
                        + " from `Order` o, Supplier s" 
                        + " where o.SupplierID = s.ID";
                
                active = "orders";
                
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
        
        btn_Transaction.setText("Transactions");
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

        btn_InvoiceList.setText("Invoices");
        btn_InvoiceList.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "SELECT i.ID as 'ID', s.Name as 'Name', i.ReceivedDate as 'Date Received', i.AmountDue as "
                        + "'Amount Due', ROUND(i.AmountDue - i.AmountPaid, 2) as 'Outstanding', 'View/Process Details' as ''" 
                        + " FROM StoreDB.Invoice i, Supplier s" 
                        + " where i.SupplierID = s.ID ";
                
                active = "invoice";
                
                table_headings.clear();
                table_headings.add("`ID`");
                table_headings.add("`Name`");
                table_headings.add("`Date Received`");
                table_headings.add("`Amount Due`");
                table_headings.add("`Outstanding`");
                table_headings.add("`ID`");
                
                order = " order by ID";
                updateReport();
            }
        });
        
        btn_Filter.setText("Filter");
        btn_Filter.setPreferredSize(new java.awt.Dimension(75, 23));
        
        btn_Filter.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                JFrame f = new JFrame();
                f.getContentPane().add(getFilterWindow(f));
                f.setSize(400, 350);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
                
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
    
    private JPanel getFilterRevenue(JFrame fr){
        javax.swing.ButtonGroup group = new javax.swing.ButtonGroup();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel Title = new javax.swing.JLabel();
        javax.swing.JCheckBox remove_order = new javax.swing.JCheckBox();
        javax.swing.JRadioButton yearly = new javax.swing.JRadioButton();
        javax.swing.JRadioButton monthly = new javax.swing.JRadioButton();
        javax.swing.JLabel msg1 = new javax.swing.JLabel();
        javax.swing.JButton submit = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JTextField revenue_less = new javax.swing.JTextField();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JTextField revenue_more = new javax.swing.JTextField();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();

        group.add(yearly);
        group.add(monthly);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        remove_order.setText("Remove Order details");

        yearly.setText("Yearly");

        monthly.setText("Monthly");
        monthly.setSelected(true);
        
        msg1.setText("View Revenue in:");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if(yearly.isSelected()){
                    query = "select Year(CreateDate) as 'Year', ";
                    order = "group by Year";
                    
                    table_headings.clear();
                    table_headings.add("`Year`");
                }else {
                    query = "select Year(CreateDate) as 'Year', MonthName(CreateDate) as 'Month', ";
                    order = "group by Year, Month";
                    
                    table_headings.clear();
                    table_headings.add("`Year`");
                    table_headings.add("`Month`");
                }
                
                if(!remove_order.isSelected()){
                    query += " IFNULL(i.AmountDue, 0) as 'Order Cost', IFNULL(i.AmountPaid, 0) as 'Order Paid',";
                    
                    table_headings.add("`Order Cost`");
                    table_headings.add("`Order Paid`");
                }
                
                query += " sum(ROUND(SubTotal, 2)) as 'SubTotal', sum(ROUND(Tax, 2)) as 'Tax',"
                        + " sum(ROUND(Total, 2)) as 'Total', "
                        + " (ROUND(sum(ROUND(Total, 2)) - (IFNULL(i.AmountDue, 0) - IFNULL(i.AmountPaid, 0)), 2))"
                        + " as Revenue from Transaction Left join Invoice i"
                        + " ON Year(i.ReceivedDate) = Year(CreateDate)"
                        + " AND MonthName(i.ReceivedDate) = MonthName(CreateDate)"
                        + " where TransactionType = 'Sale' ";
                
                query = query + order;
                order = " ";
                
                String revenueless = revenue_less.getText();
                String revenuemore = revenue_more.getText();
                
                if(!(revenueless.equals("") || revenuemore.equals(""))){
                    float less = Float.parseFloat(revenueless);
                    float more = Float.parseFloat(revenuemore);
                    
                    if(less > more){
                        float temp = less;
                        less = more;
                        more = temp;
                    }
                    query += " HAVING Revenue BETWEEN " + less + " AND " + more;
                    
                }
                updateReport();
                
                table_headings.add("`SubTotal`");
                table_headings.add("`Tax`");
                table_headings.add("`Total`");
                table_headings.add("`Revenue`");
                
                fr.dispose();
            }
        });

        jLabel1.setText("Find Revenue in between:");

        jLabel3.setText("And");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(remove_order)
                                .addComponent(Title)
                                .addComponent(msg1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(monthly)
                                        .addComponent(yearly)))
                                .addComponent(jLabel1)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(revenue_more, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addComponent(revenue_less))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(msg1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthly)
                .addGap(18, 18, 18)
                .addComponent(remove_order)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(revenue_less, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(revenue_more, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return jPanel1;
    }
    
    private JPanel getFilterInventory(JFrame fr){
        javax.swing.JPanel jPanel_inventory = new javax.swing.JPanel();
        javax.swing.JLabel Title = new javax.swing.JLabel();
        javax.swing.JButton submit = new javax.swing.JButton();
        javax.swing.JLabel msg1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JComboBox<String> category = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JComboBox<String> subcategory = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JComboBox<String> supplier = new javax.swing.JComboBox<>();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String cat = (String)category.getSelectedItem();
                String subcat = (String)subcategory.getSelectedItem();
                String sup = (String)supplier.getSelectedItem();
                
                query =  "select p.Name as 'Product', p.Description as 'Product Description', (select Name from Category "
                        + " where ID = p.CategoryID) as 'Category', (select Name from Category where ID = p.SubCategoryID)"
                        + " as 'SubCategory', ROUND(AVG(ps.UnitCost), 2) as 'Unit Cost', ROUND(p.SalePrice, 2) as 'Sale Price',"
                        + " p.Quantity as 'Quantity' from Product p, StoreDB.Category as c1, StoreDB.Category as c2, Supplier s,"
                        + " Product_Supplier as ps ";
                if(cat.equals("All")){
                    query += " where p.CategoryID = c1.ID ";
                } else {
                    query += " where p.CategoryID = (Select ID from Category where Name='" + cat + "') ";
                }
                
                if(subcat.equals("All")){
                    query += " AND p.SubCategoryID = c2.ID ";
                } else {
                    query += " AND p.SubCategoryID = (Select ID FROM Category where Name='" + subcat + "') ";
                }
                
                if(sup.equals("All")){
                    query += " AND  p.ID = ps.ProductID AND ps.SupplierID = s.ID  group by ps.ProductID";
                } else {
                    query += " AND  p.ID = ps.ProductID AND ps.SupplierID = (Select ID FROM Supplier "
                            + "where Name='" + sup + "') group by ps.ProductID";
                }
                
                order = " ";
                updateReport();
                
                fr.dispose();
            }
        });

        msg1.setText("Filter by Category:");

        jLabel1.setText("Category in:");
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select Name from Category where ParentID IS NULL");
            
            if(rs != null){
                category.addItem("All");
                while(rs.next()){                    
                    category.addItem(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                            "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        jLabel2.setText("SubCategory in:");
        
        try {
            stmt = c.createStatement();
                    
            subcategory.removeAllItems();
            rs = stmt.executeQuery("Select Name from Category where ParentID IS NOT NULL");
            
            if(rs != null){
                subcategory.addItem("All");
                while(rs.next()){
                    subcategory.addItem(rs.getString(1));                            
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
                JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        category.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    stmt = c.createStatement();
                    String cat = (String)event.getItem();
                    
                    if(cat.equals("All")){
                        subcategory.removeAllItems();
                        rs = stmt.executeQuery("Select Name from Category where ParentID IS NOT NULL");
                    }else{
                        subcategory.removeAllItems();
                        rs = stmt.executeQuery("Select Name FROM Category where ParentID in (select ID from Category where Name='" + cat + "')");
                    }
  
                    if(rs != null){
                        subcategory.addItem("All");
                        while(rs.next()){
                            subcategory.addItem(rs.getString(1));                            
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage()); 
                    JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if(stmt != null) stmt.close();
                        if(rs != null) rs.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        
        jLabel3.setText("Filter by Suplier:");

        jLabel4.setText("Supplied by:");
        
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select Name from Supplier");
            
            if(rs != null){
                supplier.addItem("All");
                while(rs.next()){                    
                    supplier.addItem(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                            "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
 
        javax.swing.GroupLayout jPanel_inventoryLayout = new javax.swing.GroupLayout(jPanel_inventory);
        jPanel_inventory.setLayout(jPanel_inventoryLayout);
        jPanel_inventoryLayout.setHorizontalGroup(
            jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                .addGroup(jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Title))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(msg1))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(category, 0, 130, Short.MAX_VALUE)
                            .addComponent(subcategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(135, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel_inventoryLayout.setVerticalGroup(
            jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(msg1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(subcategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_inventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(submit)
                .addGap(51, 51, 51))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_inventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_inventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return jPanel_inventory;
    }
    
    private JPanel getFilterOrder(JFrame fr){
        javax.swing.JPanel panel_order = new javax.swing.JPanel();
        javax.swing.JLabel Title = new javax.swing.JLabel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JComboBox<String> supplier = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JCheckBox outstanding = new javax.swing.JCheckBox();
        javax.swing.JButton submit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        UtilDateModel model1 = new UtilDateModel();
        model1.setValue(new Date());
        
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
        JDatePickerImpl date_from = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        
        UtilDateModel model2 = new UtilDateModel();
        model2.setValue(new Date());
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl date_to = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        
        String dateFrom = date_from.getJFormattedTextField().getText();
        String dateTo = date_to.getJFormattedTextField().getText();
        
        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        jLabel1.setText("Supplier:");
        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select Name from supplier");
            
            if(rs != null){
                supplier.addItem("All");
                while(rs.next()){                    
                    supplier.addItem(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                            "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        jLabel2.setText("Display received orders between dates:");

        jLabel3.setText("From:");
        
        jLabel4.setText("To:");

        outstanding.setText("Display orders with outstanding balance");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String sup = (String)supplier.getSelectedItem();
                
                query = "select s.Name as 'Supplier', o.CreateDate as 'Date Created', o.ReceivedDate as 'Date Received'," 
                        + " o.Cost as 'Cost', "; 
                        
                
                if(outstanding.isSelected()) {
                    query += " ROUND(o.Cost - IFNULL(o.AmountPaid, 0), 2) as 'Outstanding', 'View Details' as ' '"
                            + " from `Order` o, Supplier s" 
                            + " where o.SupplierID = s.ID";

                    table_headings.clear();
                    table_headings.add("`Supplier`");
                    table_headings.add("`Date Created`");
                    table_headings.add("`Date Received`");
                    table_headings.add("`Cost`");
                    table_headings.add("`Outstanding`");
                    
                } else {
                    query += " IFNULL(o.AmountPaid, 0) as 'Amount Paid', 'View Details' as ' '"
                            + " from `Order` o, Supplier s" 
                            + " where o.SupplierID = s.ID";
                }
                
                if(!sup.equals("All")){
                    query += " AND s.Name = '" + sup + "'";
                }
                 
                if(!(date_from.getJFormattedTextField().getText().equals(dateFrom) && 
                        date_to.getJFormattedTextField().getText().equals(dateTo))){
                    
                    String From = date_from.getJFormattedTextField().getText();
                    String To = date_to.getJFormattedTextField().getText();
                    
                    query += " AND DATE(o.ReceivedDate) BETWEEN '" + From + "' and '" + To + "'";
                }
                
                order = " ";              
                updateReport();
                
                fr.dispose();
            }
        });

        javax.swing.GroupLayout panel_orderLayout = new javax.swing.GroupLayout(panel_order);
        panel_order.setLayout(panel_orderLayout);
        panel_orderLayout.setHorizontalGroup(
            panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Title)
                    .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panel_orderLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(supplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_orderLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(outstanding)))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        panel_orderLayout.setVerticalGroup(
            panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(date_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_orderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(date_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(outstanding)
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_order, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return panel_order;
    }
    
    private JPanel getFilterInvoice(JFrame fr){
        javax.swing.JPanel panel_invoice = new javax.swing.JPanel();
        javax.swing.JLabel Title = new javax.swing.JLabel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JComboBox<String> supplier = new javax.swing.JComboBox<>();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JCheckBox outstanding = new javax.swing.JCheckBox();
        javax.swing.JButton submit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        UtilDateModel model1 = new UtilDateModel();
        model1.setValue(new Date());
        
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p);
        JDatePickerImpl date_from = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
        
        UtilDateModel model2 = new UtilDateModel();
        model2.setValue(new Date());
        JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
        JDatePickerImpl date_to = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
        
        String dateFrom = date_from.getJFormattedTextField().getText();
        String dateTo = date_to.getJFormattedTextField().getText();
        
        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        jLabel1.setText("Supplier:");
        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select Name from supplier");
            
            if(rs != null){
                supplier.addItem("All");
                while(rs.next()){                    
                    supplier.addItem(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                            "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if(stmt != null) stmt.close();
                if(rs != null) rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        jLabel2.setText("Display invoices between dates:");

        jLabel3.setText("From:");
        
        jLabel4.setText("To:");

        outstanding.setText("Display invoices with outstanding balance");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String sup = (String)supplier.getSelectedItem();
                query = "SELECT i.ID as 'ID', s.Name as 'Name', i.ReceivedDate as 'Date Received', i.AmountDue as "
                        + "'Amount Due', ROUND(i.AmountDue - i.AmountPaid, 2) as 'Outstanding', 'View Details' as ''" 
                        + " FROM StoreDB.Invoice i, Supplier s" 
                        + " where i.SupplierID = s.ID";
                        
                if(outstanding.isSelected()) {
                    query += " AND ROUND(i.AmountDue - i.AmountPaid, 2) > 0";                    
                }
                
                if(!sup.equals("All")){
                    query += " AND s.Name = '" + sup + "'";                    
                }
                 
                if(!(date_from.getJFormattedTextField().getText().equals(dateFrom) && 
                        date_to.getJFormattedTextField().getText().equals(dateTo))){
                    
                    String From = date_from.getJFormattedTextField().getText();
                    String To = date_to.getJFormattedTextField().getText();
                    
                    query += " AND DATE(i.ReceivedDate) BETWEEN '" + From + "' and '" + To + "'";
                }
                
                order = " ";              
                updateReport();
                
                fr.dispose();
            }
        });

        javax.swing.GroupLayout panel_invoiceLayout = new javax.swing.GroupLayout(panel_invoice);
        panel_invoice.setLayout(panel_invoiceLayout);
        panel_invoiceLayout.setHorizontalGroup(
            panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_invoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Title)
                    .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panel_invoiceLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(supplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_invoiceLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(outstanding)))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        panel_invoiceLayout.setVerticalGroup(
            panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_invoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(date_from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_invoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(date_to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(outstanding)
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_invoice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_invoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return panel_invoice;
    }
    
    private JPanel getFilterTransaction(JFrame fr){
        return null;
    }
    
    private JPanel getFilterWindow(JFrame fr){
        switch(active){
            case "revenue" : return getFilterRevenue(fr);
            case "inventory" : return getFilterInventory(fr);
            case "orders" : return getFilterOrder(fr);
            case "transaction": return getFilterTransaction(fr);
            case "invoice" : return getFilterInvoice(fr);
            default: return null;
        }
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
    
    class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
