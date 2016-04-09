/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.WindowConstants;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.event.ChangeEvent;
import net.proteanit.sql.DbUtils;
import org.jdatepicker.impl.*;

/**
 *
 * @author Gaurav
 */
public class Reports extends JFrame implements AutoCloseable {
    //Setting up GUI
    private JPanel Reports;
    private JButton Filter_Inventory;
    private JButton Filter_Invoices;
    private JButton Filter_Orders;
    private JButton Filter_Performance;
    private JButton Filter_Revenue;
    private JButton Filter_Transaction;
    private JPanel Inventory;
    private JTable Inventory_table;
    private JPanel Invoices;
    private JTable Invoices_table;
    private JPanel Orders;
    private JTable Orders_table;
    private JPanel Performance;
    private JTable Performance_table;
    private JTabbedPane Report_tabs;
    private JPanel Revenue;
    private JTable Revenue_table;
    private JScrollPane ScrollPane_Inventory;
    private JScrollPane ScrollPane_Invoices;
    private JScrollPane ScrollPane_Orders;
    private JScrollPane ScrollPane_Performance;
    private JScrollPane ScrollPane_Revenue;
    private JScrollPane ScrollPane_Transactions;
    private JPanel Transactions;
    private JTable Transactions_table;
    private JTextField input_transactionID;
    private JLabel transaction_label;
    private JButton transaction_search;
    
    //Connection to database
    private Connection c = null;
    
    //Query to run in every table
    private String revenue_query = null;
    private String inventory_query = null;
    private String orders_query = null;
    private String transactions_query = null;
    private String invoices_query = null;
    private String performance_query = null;
    
    //Sorting table data
    private String order = null;
    private Vector<String> Revenue_headings = new Vector<>();
    private Vector<String> Inventory_headings = new Vector<>();
    private Vector<String> Orders_headings = new Vector<>();
    private Vector<String> Transactions_headings = new Vector<>();
    private Vector<String> Invoices_headings = new Vector<>();
    private Vector<String> Performance_headings = new Vector<>();
    
    //for printing apporiate table record
    private int active;
    
    public Reports(){
        super();
        
        try {
            Connect connect = new Connect();
            c = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cannot establish connection with MySQL.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    protected void updateReport(JTable jTable1, String query){
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            
            jTable1.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
            
            if(rs != null){
                if(rs.next()) {
                    rs.beforeFirst();
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
        Reports = new JPanel();
        Report_tabs = new JTabbedPane();
        Revenue = new JPanel();
        Filter_Revenue = new JButton();
        ScrollPane_Revenue = new JScrollPane();
        Revenue_table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Revenue_table.setRowHeight(30);
        Inventory = new JPanel();
        Filter_Inventory = new JButton();
        ScrollPane_Inventory = new JScrollPane();
        Inventory_table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Inventory_table.setRowHeight(30);
        Orders = new JPanel();
        Filter_Orders = new JButton();
        ScrollPane_Orders = new JScrollPane();
        Orders_table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Orders_table.setRowHeight(30);
        Transactions = new JPanel();
        Filter_Transaction = new JButton();
        ScrollPane_Transactions = new JScrollPane();
        Transactions_table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Transactions_table.setRowHeight(30);
        transaction_label = new JLabel();
        input_transactionID = new JTextField();
        transaction_search = new JButton();
        Invoices = new JPanel();
        Filter_Invoices = new JButton();
        ScrollPane_Invoices = new JScrollPane();
        Invoices_table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Invoices_table.setRowHeight(30);
        Performance = new JPanel();
        Filter_Performance = new JButton();
        ScrollPane_Performance = new JScrollPane();
	Performance_table = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        Performance_table.setRowHeight(30);
        
        Report_tabs.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
        
        Report_tabs.addChangeListener((ChangeEvent changeEvent) -> {
            active = Report_tabs.getSelectedIndex();
            //System.out.println("Tab changed to: " + Report_tabs.getTitleAt(active) + ", index is: " + active);
        });
                
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Report_tabs.setTabPlacement(JTabbedPane.LEFT);
        
        Filter_Revenue.setText("Filter");
	Filter_Revenue.addActionListener((ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getFilterRevenue(f));
            f.setSize(300, 350);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });

	revenue_query = "select Year(CreateDate) as 'Year', MonthName(CreateDate) as 'Month',"
            + " IFNULL(i.AmountDue, 0) as 'Order Cost', IFNULL(i.AmountPaid, 0) as 'Order Paid',"
            + " sum(ROUND(SubTotal, 2)) as 'SubTotal', sum(ROUND(Tax, 2)) as 'Tax',"
            + " sum(ROUND(Total, 2)) as 'Revenue', "
            + " ROUND(sum(ROUND(Total, 2)) - (IFNULL(i.AmountDue, 0) - IFNULL(i.AmountPaid, 0)), 2) - sum(ROUND(Tax, 2))"
            + " as 'Profit' from Transaction Left join Invoice i"
            + " ON Year(i.ReceivedDate) = Year(CreateDate)"
            + " AND MonthName(i.ReceivedDate) = MonthName(CreateDate)"
            + " where TransactionType = 'Sale' "
            + " group by Year, Month ";
        
		Revenue_headings.clear();
        Revenue_headings.add("`Year`");
        Revenue_headings.add("`Month`");
        Revenue_headings.add("`Order Cost`");
        Revenue_headings.add("`Order Paid`");
        Revenue_headings.add("`SubTotal`");
        Revenue_headings.add("`Tax`");
        Revenue_headings.add("`Revenue`");
        Revenue_headings.add("`Profit`");
        
	updateReport(Revenue_table, revenue_query);
	
        Revenue_table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Revenue_table.columnAtPoint(e.getPoint());
                order = " order by " + Revenue_headings.get(col);
                updateReport(Revenue_table, revenue_query + order);
            }
        });
        
        ScrollPane_Revenue.setViewportView(Revenue_table);

        javax.swing.GroupLayout RevenueLayout = new javax.swing.GroupLayout(Revenue);
        Revenue.setLayout(RevenueLayout);
        RevenueLayout.setHorizontalGroup(
            RevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RevenueLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_Revenue, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(RevenueLayout.createSequentialGroup()
                        .addComponent(Filter_Revenue)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        RevenueLayout.setVerticalGroup(
            RevenueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RevenueLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Filter_Revenue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane_Revenue, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        Report_tabs.addTab("Revenue", Revenue);
        
        Filter_Inventory.setText("Filter");
	Filter_Inventory.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getFilterInventory(f));
            f.setSize(430, 300);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });

	inventory_query = "select p.Name as 'Product', p.Description as 'Product Description', c1.Name as 'Category',"
            + " c2.Name as 'SubCategory', ROUND(AVG(ps.UnitCost), 2) as 'Unit Cost', ROUND(p.SalePrice, 2) "
            + " as 'Sale Price', p.Quantity as 'Quantity' from Product p "
            + " left outer join StoreDB.Category as c1 on p.CategoryID = c1.ID "
            + " left outer join StoreDB.Category as c2 on p.SubCategoryID = c2.ID"
            + " join Product_Supplier as ps on p.ID = ps.ProductID group by ps.ProductID ";
        
        Inventory_headings.clear();
        Inventory_headings.add("`Product`");
        Inventory_headings.add("`Product Description`");
        Inventory_headings.add("`Category`");
        Inventory_headings.add("`SubCategory`");
        Inventory_headings.add("`Unit Cost`");
        Inventory_headings.add("`Sale Price`");
        Inventory_headings.add("`Quantity`");
        
        updateReport(Inventory_table, inventory_query);
        
        Inventory_table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Inventory_table.columnAtPoint(e.getPoint());
                order = " order by " + Inventory_headings.get(col);
                updateReport(Inventory_table, inventory_query + order);
            }
        });
        
        ScrollPane_Inventory.setViewportView(Inventory_table);

        javax.swing.GroupLayout InventoryLayout = new javax.swing.GroupLayout(Inventory);
        Inventory.setLayout(InventoryLayout);
        InventoryLayout.setHorizontalGroup(
            InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_Inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(InventoryLayout.createSequentialGroup()
                        .addComponent(Filter_Inventory)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        InventoryLayout.setVerticalGroup(
            InventoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Filter_Inventory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane_Inventory, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        Report_tabs.addTab("Inventory", Inventory);

        Filter_Orders.setText("Filter");
	Filter_Orders.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getFilterOrder(f));
            f.setSize(350, 320);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });

        orders_query = "select o.ID, s.Name as 'Supplier', DATE_FORMAT(o.CreateDate, '%d-%M-%Y %H:%i') as 'Date Created', "
        	+ " DATE_FORMAT(o.ReceivedDate, '%d-%M-%Y %H:%i') as 'Date Received', o.Cost as 'Cost', "
        	+ " IFNULL(o.AmountPaid, 0) as 'Amount Paid' from `Order` o, Supplier s" 
            + " where o.SupplierID = s.ID ";
        
        Orders_headings.clear();
        Orders_headings.add("`ID`");
        Orders_headings.add("`Supplier`");
        Orders_headings.add("`Date Created`");
        Orders_headings.add("`Date Received`");
        Orders_headings.add("`Cost`");
        Orders_headings.add("`Amount Paid`");
        
	updateReport(Orders_table, orders_query);
        
        Orders_table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2)
                {
                    int row = Orders_table.getSelectedRow();
                    int id = Integer.parseInt(Orders_table.getModel().getValueAt(row, 0).toString());

                    Order t = new Order();
                    JFrame f = new JFrame();
                    JPanel p = t.getOrderInfo(id, f);
                    if(p != null){                
                        f.getContentPane().add(p);
                        f.setSize(920, 500);
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);    
                    } else {
                        JOptionPane.showMessageDialog(null, "Searched order does not exits.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        Orders_table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Orders_table.columnAtPoint(e.getPoint());
                order = " order by " + Orders_headings.get(col);
                updateReport(Orders_table, orders_query + order);
            }
        });
                
        ScrollPane_Orders.setViewportView(Orders_table);

        javax.swing.GroupLayout OrdersLayout = new javax.swing.GroupLayout(Orders);
        Orders.setLayout(OrdersLayout);
        OrdersLayout.setHorizontalGroup(
            OrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(OrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_Orders, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(OrdersLayout.createSequentialGroup()
                        .addComponent(Filter_Orders)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        OrdersLayout.setVerticalGroup(
            OrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(OrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Filter_Orders)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane_Orders, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        Report_tabs.addTab("Orders", Orders);

        Filter_Transaction.setText("Filter");
	Filter_Transaction.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getFilterTransaction(f));
            f.setSize(350, 365);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });

	transactions_query = "select t.ID as 'ID', DATE_FORMAT(CreateDate, '%d-%M-%Y %H:%i') as 'Date Created', "
			+ " TransactionType as 'Transaction Type', Method as 'Payment Type', "
			+ " CONCAT(e.FirstName, ' ', e.LastName) as 'Employee', SubTotal, Tax, Total"
			+ " from `Transaction` t, Employee e where t.EmployeeID = e.ID";
	
        Transactions_headings.clear();
        Transactions_headings.add("`ID`");
        Transactions_headings.add("`Date Created`");
        Transactions_headings.add("`Transaction Type`");
        Transactions_headings.add("`Payment Type`");
        Transactions_headings.add("`Employee`");
        Transactions_headings.add("`SubTotal`");
        Transactions_headings.add("`Tax`");
        Transactions_headings.add("`Total`");
        
	updateReport(Transactions_table, transactions_query);
	Transactions_table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2)
                {
                    int row = Transactions_table.getSelectedRow();
                    int id = (int) Transactions_table.getModel().getValueAt(row, 0);
                    
                    Transaction t = new Transaction();
                    JPanel p = t.getTransactionInfo(id);
                    if(p != null){
                        JFrame f = new JFrame();                
                        f.getContentPane().add(p);
                        f.setSize(900, 500);
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);    
                    } else {
                        JOptionPane.showMessageDialog(null, "Searched transaction does not exits.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        Transactions_table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Transactions_table.columnAtPoint(e.getPoint());
                order = " order by " + Transactions_headings.get(col);
                updateReport(Transactions_table, transactions_query + order);
            }
        });
        
        ScrollPane_Transactions.setViewportView(Transactions_table);

        transaction_label.setText("Transaction ID:");
        transaction_label.setPreferredSize(new java.awt.Dimension(74, 23));
        
        transaction_search.setText("Search");
        transaction_search.addActionListener((java.awt.event.ActionEvent evt) -> {
            int id = Integer.parseInt(input_transactionID.getText());
            if(id != 0){
                Transaction t = new Transaction();
                JPanel p = t.getTransactionInfo(id);
                if(p != null){
                    JFrame f = new JFrame();                
                    f.getContentPane().add(p);
                    f.setSize(900, 500);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);    
                } else {
                    JOptionPane.showMessageDialog(null, "Searched transaction does not exits.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Transaction ID is not recognize!, ID must be number digits",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        javax.swing.GroupLayout TransactionsLayout = new javax.swing.GroupLayout(Transactions);
        Transactions.setLayout(TransactionsLayout);
        TransactionsLayout.setHorizontalGroup(
            TransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_Transactions, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(TransactionsLayout.createSequentialGroup()
                        .addComponent(Filter_Transaction)
                        .addGap(61, 61, 61)
                        .addComponent(transaction_label, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_transactionID, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transaction_search)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        TransactionsLayout.setVerticalGroup(
            TransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TransactionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TransactionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Filter_Transaction)
                    .addComponent(transaction_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_transactionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(transaction_search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane_Transactions, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        Report_tabs.addTab("Transactions", Transactions);

        Filter_Invoices.setText("Filter");
	Filter_Invoices.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getFilterInvoice(f));
            f.setSize(350, 330);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });

	invoices_query = "SELECT i.ID as 'ID', s.Name as 'Name', DATE_FORMAT(i.ReceivedDate, '%d-%M-%Y %H:%i') as 'Date Received', "
			+ " i.AmountDue as 'Amount Due', ROUND(i.AmountDue - i.AmountPaid, 2) as 'Outstanding'" 
            + " FROM StoreDB.Invoice i, Supplier s  where i.SupplierID = s.ID ";
	
        Invoices_headings.clear();
        Invoices_headings.add("`ID`");
        Invoices_headings.add("`Name`");
        Invoices_headings.add("`Date Received`");
        Invoices_headings.add("`Amount Due`");
        Invoices_headings.add("`OutStanding`");
        
	updateReport(Invoices_table, invoices_query);
        
        Invoices_table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2)
                {
                    int row = Invoices_table.getSelectedRow();
                    int id = Integer.parseInt(Invoices_table.getModel().getValueAt(row, 0).toString());

                    Invoice i = new Invoice();
                    JFrame f = new JFrame();
                    JPanel p = i.getInvoiceInfo(id, f);
                    if(p != null){                
                        f.getContentPane().add(p);
                        f.setSize(950, 500);
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);    
                    } else {
                        JOptionPane.showMessageDialog(null, "Searched order does not exits.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        Invoices_table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Invoices_table.columnAtPoint(e.getPoint());
                order = " order by " + Invoices_headings.get(col);
                updateReport(Invoices_table, invoices_query + order);
            }
        });
        
	ScrollPane_Invoices.setViewportView(Invoices_table);

        javax.swing.GroupLayout InvoicesLayout = new javax.swing.GroupLayout(Invoices);
        Invoices.setLayout(InvoicesLayout);
        InvoicesLayout.setHorizontalGroup(
            InvoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InvoicesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InvoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_Invoices, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(InvoicesLayout.createSequentialGroup()
                        .addComponent(Filter_Invoices)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        InvoicesLayout.setVerticalGroup(
            InvoicesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InvoicesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Filter_Invoices)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane_Invoices, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        Report_tabs.addTab("Invoices", Invoices);

        Filter_Performance.setText("Filter");
	Filter_Performance.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getFilterPerformance(f));
            f.setSize(430, 390);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });
        
        performance_query = "Select c.Name as 'Name', sum(tr.QuantitySold) as 'Quantity Sold',"
            + " ROUND(sum(tr.UnitPrice * tr.QuantitySold), 2) as 'Total',"
            + " ROUND(sum((tr.UnitPrice * tr.QuantitySold) - (tr.UnitCost * tr.QuantitySold)), 2) as 'Profit'"    
            + " from Category c, TransactionRecord tr, Product p "
            + " where tr.ProductID = p.ID and (p.CategoryID = c.ID or p.SubCategoryID = c.ID) " /*and c.ParentID IS NULL */
            + " group by c.Name";

        Performance_headings.clear();
        Performance_headings.add("`Name`");
        Performance_headings.add("`Quantity Sold`");
        Performance_headings.add("`Total`");
        Performance_headings.add("`Profit`");
        
	updateReport(Performance_table, performance_query);
       
        Performance_table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = Performance_table.columnAtPoint(e.getPoint());
                order = " order by " + Performance_headings.get(col);
                updateReport(Performance_table, performance_query + order);
            }
        });
        
        ScrollPane_Performance.setViewportView(Performance_table);

        javax.swing.GroupLayout PerformanceLayout = new javax.swing.GroupLayout(Performance);
        Performance.setLayout(PerformanceLayout);
        PerformanceLayout.setHorizontalGroup(
            PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerformanceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ScrollPane_Performance, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(PerformanceLayout.createSequentialGroup()
                        .addComponent(Filter_Performance)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PerformanceLayout.setVerticalGroup(
            PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PerformanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Filter_Performance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPane_Performance, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addContainerGap())
        );

        Report_tabs.addTab("Performance", Performance);

        javax.swing.GroupLayout ReportsLayout = new javax.swing.GroupLayout(Reports);
        Reports.setLayout(ReportsLayout);
        ReportsLayout.setHorizontalGroup(
            ReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Report_tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
        );
        ReportsLayout.setVerticalGroup(
            ReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Report_tabs)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Reports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Reports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    //returns JPanel for Filtering Revenue report
    private JPanel getFilterRevenue(JFrame fr){
        ButtonGroup group = new ButtonGroup();
        JPanel jPanel1 = new JPanel();
        JLabel Title = new JLabel();
        JCheckBox remove_order = new JCheckBox();
        JRadioButton yearly = new JRadioButton();
        JRadioButton monthly = new JRadioButton();
        JLabel msg1 = new JLabel();
        JButton submit = new JButton();
        JLabel jLabel1 = new JLabel();
        JTextField revenue_less = new JTextField();
        JLabel jLabel2 = new JLabel();
        JTextField revenue_more = new JTextField();
        JLabel jLabel3 = new JLabel();

        group.add(yearly);
        group.add(monthly);
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        remove_order.setText("Remove Order details");

        yearly.setText("Yearly");

        monthly.setText("Monthly");
        monthly.setSelected(true);
        
        msg1.setText("View Revenue in:");

        submit.setText("Submit");
        submit.addActionListener((java.awt.event.ActionEvent evt) -> {
            if(yearly.isSelected()){
                revenue_query = "select Year(CreateDate) as 'Year', ";
                order = "group by Year";
                
                Revenue_headings.clear();
                Revenue_headings.add("`Year`");
                
            }else {
                revenue_query = "select Year(CreateDate) as 'Year', MonthName(CreateDate) as 'Month', ";
                order = "group by Year, Month";
                
                Revenue_headings.clear();
                Revenue_headings.add("`Year`");
                Revenue_headings.add("`Month`");
            }
            
            if(!remove_order.isSelected()){
                revenue_query += " IFNULL(i.AmountDue, 0) as 'Order Cost', IFNULL(i.AmountPaid, 0) as 'Order Paid',"
                    + " sum(ROUND(SubTotal, 2)) as 'SubTotal', sum(ROUND(Tax, 2)) as 'Tax',"
                    + " sum(ROUND(Total, 2)) as 'Revenue', "
                    + " (ROUND(sum(ROUND(Total, 2)) - (IFNULL(i.AmountDue, 0) - IFNULL(i.AmountPaid, 0)), 2)) - sum(ROUND(Tax, 2))"
                    + " as 'Profit' from Transaction Left join Invoice i"
                    + " ON Year(i.ReceivedDate) = Year(CreateDate)"
                    + " AND MonthName(i.ReceivedDate) = MonthName(CreateDate)"
                    + " where TransactionType = 'Sale' ";
                
                Revenue_headings.add("`Order Cost`");
                Revenue_headings.add("`Order Paid`");
                Revenue_headings.add("`SubTotal`");
                Revenue_headings.add("`Tax`");
                Revenue_headings.add("`Revenue`");
                Revenue_headings.add("`Profit`");
                
            } else {
                revenue_query += " sum(ROUND(SubTotal, 2)) as 'SubTotal', sum(ROUND(Tax, 2)) as 'Tax',"
                    + " sum(ROUND(Total, 2)) as 'Revenue', "
                    + " sum(ROUND(Total, 2)) - sum(ROUND(Tax, 2)) as Profit"
                    + " from Transaction Left join Invoice i"
                    + " ON Year(i.ReceivedDate) = Year(CreateDate)"
                    + " AND MonthName(i.ReceivedDate) = MonthName(CreateDate)"
                    + " where TransactionType = 'Sale' ";
                
                Revenue_headings.add("`SubTotal`");
                Revenue_headings.add("`Tax`");
                Revenue_headings.add("`Revenue`");
                Revenue_headings.add("`Profit`");
                
            }
            
            revenue_query += order;
            order = " ";
            
            String revenueless = revenue_less.getText();
            String revenuemore = revenue_more.getText();
            
            if(!(revenueless.trim().equals("") || revenuemore.trim().equals(""))){
            	try{
	                float less = Float.parseFloat(revenueless);
	                float more = Float.parseFloat(revenuemore);
	                
	                if(less > more){
	                    float temp = less;
	                    less = more;
	                    more = temp;
	                }
	                revenue_query += " HAVING Revenue BETWEEN " + less + " AND " + more;
            	} catch (NumberFormatException e){
                	//parsing number failed.. defaulted to normal result.
            		JOptionPane.showMessageDialog(null, "Entered input for revenue between amount is invalid, please do not enter symbols!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            updateReport(Revenue_table, revenue_query);
            
            fr.dispose();
        });

        jLabel1.setText("Find Revenue amount in between:");

        jLabel3.setText("And");

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(remove_order)
                                .addComponent(Title)
                                .addComponent(msg1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(monthly)
                                        .addComponent(yearly)))
                                .addComponent(jLabel1)))
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(jLabel3)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(revenue_more, GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                .addComponent(revenue_less))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(submit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(msg1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(yearly)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthly)
                .addGap(18, 18, 18)
                .addComponent(remove_order)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(revenue_less, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(revenue_more, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addGap(26, 26, 26))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return jPanel1;
    }
    
    //returns JPanel for Filtering Inventory report
    private JPanel getFilterInventory(JFrame fr){
        JPanel jPanel_inventory = new JPanel();
        JLabel Title = new JLabel();
        JButton submit = new JButton();
        JLabel msg1 = new JLabel();
        JLabel jLabel1 = new JLabel();
        JComboBox<String> category = new JComboBox<>();
        JLabel jLabel2 = new JLabel();
        JComboBox<String> subcategory = new JComboBox<>();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JComboBox<String> supplier = new JComboBox<>();
        JSeparator jSeparator1 = new JSeparator();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        submit.setText("Submit");
        submit.addActionListener((java.awt.event.ActionEvent evt) -> {
            String cat = (String)category.getSelectedItem();
            String subcat = (String)subcategory.getSelectedItem();
            String sup = (String)supplier.getSelectedItem();
            
            inventory_query =  "select p.Name as 'Product', p.Description as 'Product Description', (select Name from Category "
                    + " where ID = p.CategoryID) as 'Category', (select Name from Category where ID = p.SubCategoryID)"
                    + " as 'SubCategory', ROUND(AVG(ps.UnitCost), 2) as 'Unit Cost', ROUND(p.SalePrice, 2) as 'Sale Price',"
                    + " p.Quantity as 'Quantity' from Product p, StoreDB.Category as c1, StoreDB.Category as c2, Supplier s,"
                    + " Product_Supplier as ps ";
        
            if(cat.equals("All")){
                inventory_query += " where p.CategoryID = c1.ID ";
            } else {
                inventory_query += " where p.CategoryID = (Select ID from Category where Name='" + cat + "') ";
            }
            
            if(subcat.equals("All")){
                inventory_query += " AND p.SubCategoryID = c2.ID ";
            } else {
                inventory_query += " AND p.SubCategoryID = (Select ID FROM Category where Name='" + subcat + "') ";
            }
            
            if(sup.equals("All")){
                inventory_query += " AND  p.ID = ps.ProductID AND ps.SupplierID = s.ID  group by ps.ProductID";
            } else {
                inventory_query += " AND  p.ID = ps.ProductID AND ps.SupplierID = (Select ID FROM Supplier "
                        + "where Name='" + sup + "') group by ps.ProductID";
            }
            
            order = " ";
            updateReport(Inventory_table, inventory_query);
            
            fr.dispose();
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
        
        category.addItemListener((ItemEvent event) -> {
            Statement stmt1 = null;
            ResultSet rs1 = null;
            try {
                stmt1 = c.createStatement();
                String cat = (String)event.getItem();
                if (cat.equals("All")) {
                    subcategory.removeAllItems();
                    rs1 = stmt1.executeQuery("Select Name from Category where ParentID IS NOT NULL");
                } else {
                    subcategory.removeAllItems();
                    rs1 = stmt1.executeQuery("Select Name FROM Category where ParentID in (select ID from Category where Name='" + cat + "')");
                }
                if (rs1 != null) {
                    subcategory.addItem("All");
                    while (rs1.next()) {
                        subcategory.addItem(rs1.getString(1));
                    }
                }
            }catch (SQLException ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(null, "There seems to be error with your SQL server!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (stmt1 != null) {
                        stmt1.close();
                    }
                    if (rs1 != null) {
                        rs1.close();
                    }
                }catch (SQLException ex) {
                    System.out.println(ex.getMessage());
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
 
        GroupLayout jPanel_inventoryLayout = new GroupLayout(jPanel_inventory);
        jPanel_inventory.setLayout(jPanel_inventoryLayout);
        jPanel_inventoryLayout.setHorizontalGroup(
            jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                .addGroup(jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Title))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(submit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(msg1))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(category, 0, 130, Short.MAX_VALUE)
                            .addComponent(subcategory, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplier, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(135, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        jPanel_inventoryLayout.setVerticalGroup(
            jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_inventoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(msg1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(category, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(subcategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_inventoryLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(supplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGap(15, 15, 15)
                .addComponent(submit)
                .addGap(51, 51, 51))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_inventory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_inventory, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return jPanel_inventory;
    }
    
    //returns JPanel for Filtering Order report
    private JPanel getFilterOrder(JFrame fr){
        JPanel panel_order = new JPanel();
        JLabel Title = new JLabel();
        JLabel jLabel1 = new JLabel();
        JComboBox<String> supplier = new JComboBox<>();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JCheckBox outstanding = new JCheckBox();
        JButton submit = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
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
        submit.addActionListener((java.awt.event.ActionEvent evt) -> {
            String sup = (String)supplier.getSelectedItem();
            
            orders_query = "select o.ID, s.Name as 'Supplier', DATE_FORMAT(o.CreateDate, '%d-%M-%Y %H:%i') as 'Date Created', "
            		+ "DATE_FORMAT(o.ReceivedDate, '%d-%M-%Y %H:%i') as 'Date Received', o.Cost as 'Cost', ";
            
            Orders_headings.clear();
            Orders_headings.add("`ID`");
            Orders_headings.add("`Supplier`");
            Orders_headings.add("`Date Created`");
            Orders_headings.add("`Date Received`");
            Orders_headings.add("`Cost`");
            
            if(outstanding.isSelected()) {
                orders_query += " ROUND(o.Cost - IFNULL(o.AmountPaid, 0), 2) as 'Outstanding' "
                        + " from `Order` o, Supplier s"
                        + " where o.SupplierID = s.ID";
                
                Orders_headings.add("`Outstanding`");
            } else {
                orders_query += " IFNULL(o.AmountPaid, 0) as 'Amount Paid' "
                        + " from `Order` o, Supplier s"
                        + " where o.SupplierID = s.ID";
                
                Orders_headings.add("`Amount Paid`");
            }
            
            if(!sup.equals("All")){
                orders_query += " AND s.Name = '" + sup + "'";
            }
            
            if(!(date_from.getJFormattedTextField().getText().equals(dateFrom) &&
                    date_to.getJFormattedTextField().getText().equals(dateTo))){
                
                String From = date_from.getJFormattedTextField().getText();
                String To = date_to.getJFormattedTextField().getText();
                
                orders_query += " AND DATE(o.ReceivedDate) BETWEEN '" + From + "' and '" + To + "'";
            }
            
            order = " ";
            updateReport(Orders_table, orders_query);
            
            fr.dispose();
        });

        GroupLayout panel_orderLayout = new GroupLayout(panel_order);
        panel_order.setLayout(panel_orderLayout);
        panel_orderLayout.setHorizontalGroup(
            panel_orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(Title)
                    .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panel_orderLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(supplier, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(panel_orderLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(date_to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addComponent(outstanding)
                            .addGroup(panel_orderLayout.createSequentialGroup()
                                .addGap(80, 80, 80)
                            .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        panel_orderLayout.setVerticalGroup(
            panel_orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_orderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(supplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(date_from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_orderLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(date_to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(outstanding)
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_order, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_order, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return panel_order;
    }
    
    //returns JPanel for Filtering Invoice report
    private JPanel getFilterInvoice(JFrame fr){
        JPanel panel_invoice = new JPanel();
        JLabel Title = new JLabel();
        JLabel jLabel1 = new JLabel();
        JComboBox<String> supplier = new JComboBox<>();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JCheckBox outstanding = new JCheckBox();
        JButton submit = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
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

        outstanding.setText("Display unpaid invoices");

        submit.setText("Submit");
        submit.addActionListener((java.awt.event.ActionEvent evt) -> {
            String sup = (String)supplier.getSelectedItem();
            invoices_query = "SELECT i.ID as 'ID', s.Name as 'Name', DATE_FORMAT(i.ReceivedDate, '%d-%M-%Y %H:%i') as 'Date Received', "
            		+ " i.AmountDue as 'Amount Due', ROUND(i.AmountDue - i.AmountPaid, 2) as 'Outstanding' "
                    + " FROM StoreDB.Invoice i, Supplier s where i.SupplierID = s.ID";
            
            if(outstanding.isSelected()) {
                invoices_query += " AND ROUND(i.AmountDue - i.AmountPaid, 2) > 0";
            }
            
            if(!sup.equals("All")){
                invoices_query += " AND s.Name = '" + sup + "'";
            }
            
            if(!(date_from.getJFormattedTextField().getText().equals(dateFrom) &&
                    date_to.getJFormattedTextField().getText().equals(dateTo))){
                
                String From = date_from.getJFormattedTextField().getText();
                String To = date_to.getJFormattedTextField().getText();
                
                invoices_query += " AND DATE(i.ReceivedDate) BETWEEN '" + From + "' and '" + To + "'";
            }
            
            order = " ";
            updateReport(Invoices_table, invoices_query);
            
            fr.dispose();
        });

        GroupLayout panel_invoiceLayout = new GroupLayout(panel_invoice);
        panel_invoice.setLayout(panel_invoiceLayout);
        panel_invoiceLayout.setHorizontalGroup(
            panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_invoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(Title)
                    .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                        .addGroup(panel_invoiceLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(supplier, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(panel_invoiceLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(date_to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(date_from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addComponent(outstanding)
                            .addGroup(panel_invoiceLayout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        panel_invoiceLayout.setVerticalGroup(
            panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_invoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(supplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(date_from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_invoiceLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(date_to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(outstanding)
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_invoice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_invoice, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return panel_invoice;
    }
    
    //returns JPanel for Filtering Transaction report
    private JPanel getFilterTransaction(JFrame fr){
        JPanel panel_transaction = new JPanel();
        JLabel Title = new JLabel();
        JButton submit = new JButton();
        JLabel jLabel1 = new JLabel();
        JTextField revenue_less = new JTextField();
        JTextField revenue_more = new JTextField();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JComboBox<String> payment = new JComboBox<>();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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

        submit.setText("Submit");
        submit.addActionListener((java.awt.event.ActionEvent evt) -> {
            transactions_query = "select t.ID as 'ID', DATE_FORMAT(CreateDate, '%d-%M-%Y %H:%i') as 'Date Created', "
            		+ " TransactionType as 'Transaction Type', Method as 'Payment Type', "
            		+ " CONCAT(e.FirstName, ' ', e.LastName) as 'Employee', SubTotal, Tax, Total"
            		+ " from `Transaction` t, Employee e where t.EmployeeID = e.ID";
            
            order = " ";
            
            if(!(date_from.getJFormattedTextField().getText().equals(dateFrom) &&
                    date_to.getJFormattedTextField().getText().equals(dateTo))){
                
                String From = date_from.getJFormattedTextField().getText();
                String To = date_to.getJFormattedTextField().getText();
                
                transactions_query += " AND DATE(t.CreateDate) BETWEEN '" + From + "' and '" + To + "'";
            }
            
            String revenueless = revenue_less.getText();
            String revenuemore = revenue_more.getText();
            
            if(!(revenueless.equals("") || revenuemore.equals(""))){
             try {
            	 	float less = Float.parseFloat(revenueless);
            	 	float more = Float.parseFloat(revenuemore);
                
	                if(less > more){
	                    float temp = less;
	                    less = more;
	                    more = temp;
	                }
	                transactions_query += " AND Total BETWEEN " + less + " AND " + more;
             } catch (NumberFormatException e) {
            	 JOptionPane.showMessageDialog(null, "Entered value for Sales with total cost in between is not recognize, please enter decimal/number values.",
                         "Error", JOptionPane.ERROR_MESSAGE);
             }
            }
            
            String method = (String)payment.getSelectedItem();
            
            if(!method.equals("All")){
                transactions_query += " AND t.Method = '" + method + "'";
            }
            
            updateReport(Transactions_table, transactions_query);
            
            fr.dispose();
        });

        jLabel1.setText("View Sales with total cost in between:");

        jLabel3.setText("And");

        jLabel2.setText("Filter by Date:");

        jLabel4.setText("From:");

        jLabel5.setText("To:");

        jLabel6.setText("Payment Type:");
        payment.setModel(new DefaultComboBoxModel<>(new String[] { "All", "Cash", "Credit Card", "Master Card", "Visa Card" }));

        GroupLayout panel_transactionLayout = new GroupLayout(panel_transaction);
        panel_transaction.setLayout(panel_transactionLayout);
        panel_transactionLayout.setHorizontalGroup(
            panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_transactionLayout.createSequentialGroup()
                .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panel_transactionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(Title)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(panel_transactionLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel3)
                                .addGap(26, 26, 26)
                                .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(revenue_less, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(revenue_more, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panel_transactionLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(payment, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panel_transactionLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(date_to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(date_from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_transactionLayout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(submit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        panel_transactionLayout.setVerticalGroup(
            panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_transactionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(revenue_less, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(revenue_more, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(date_from, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(date_to, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel_transactionLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(payment, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(submit)
                .addGap(19, 19, 19))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_transaction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_transaction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return panel_transaction;
    }
    
    //returns JPanel for Filtering Performance report
    private JPanel getFilterPerformance(JFrame fr){
        JPanel jPanel_Performance = new JPanel();
        JLabel Title = new JLabel();
        JLabel  jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JComboBox<String> category = new JComboBox<>();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4  = new JLabel();
        JTextField productID = new JTextField();
        JLabel jLabel5 = new JLabel();
        JComboBox<String> productName = new JComboBox<>();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        JComboBox<String> year = new JComboBox<>();
        JButton submit = new JButton();
        JLabel jLabel9 = new JLabel();
        JSeparator jSeparator1 = new JSeparator();
        JSeparator jSeparator2 = new JSeparator();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Title.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        Title.setText("Filter");

        jLabel1.setText("View Sales by Category on Monthly basis:");

        jLabel2.setText("Category in:");
        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select Name from Category order by Name");
            
            if(rs != null){
                category.addItem("All");
                while(rs.next()){                    
                    category.addItem(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
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
        
        jLabel3.setText("View Product on Monthly basis:");

        jLabel4.setText("Product ID:");

        jLabel5.setText("OR");

        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select Name from Product order by Name");
            
            if(rs != null){
                productName.addItem("All");
                while(rs.next()){                    
                    productName.addItem(rs.getString(1));
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

        jLabel6.setText("Product Name: ");

        jLabel7.setText("View Report by Specific Year:");

        jLabel8.setText("Year: ");

        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("select DISTINCT(Year(CreateDate)) from Transaction order by Year(CreateDate)");
            
            if(rs != null){
                year.addItem("All");
                while(rs.next()){                    
                    year.addItem(rs.getString(1));
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

        submit.setText("Submit");
        submit.addActionListener((java.awt.event.ActionEvent evt) -> {
            String cat = (String)category.getSelectedItem();
            String yr = (String)year.getSelectedItem();
            String prodName = (String)productName.getSelectedItem();
            String prodID = productID.getText();
            
            if((!cat.equals("All")) && (!prodID.equals("") || !prodName.equals("All"))){
                JOptionPane.showMessageDialog(null, "Cannot have Category and Product selected at same time.",
                            "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if(prodID.equals("") && prodName.equals("All")){
                    if(!yr.equals("All")){
                        if(cat.equals("All")){
                            performance_query = "Select c.Name as 'Name', sum(tr.QuantitySold) as 'Quantity Sold',"
                                + " ROUND(sum(tr.UnitPrice * tr.QuantitySold), 2) as 'Total',"
                                + " ROUND(sum((tr.UnitPrice * tr.QuantitySold) - (tr.UnitCost * tr.QuantitySold)), 2) as 'Profit'"    
                                + " from Category c, TransactionRecord tr, Product p, Transaction t "
                                + " where tr.ProductID = p.ID and (p.CategoryID = c.ID or p.SubCategoryID = c.ID) " /*and c.ParentID IS NULL */
                                + " and t.ID = tr.TransactionID and Year(t.CreateDate) = '" + yr + "' group by c.Name";
                            
                            Performance_headings.clear();
                            Performance_headings.add("`Name`");
                            Performance_headings.add("`Quantity Sold`");
                            Performance_headings.add("`Total`");
                            Performance_headings.add("`Profit`");

                        } else {
                            performance_query = "select MonthName(t.CreateDate) as 'Month', sum(tr.QuantitySold) as 'Quantity Sold',"
                                + " ROUND(sum(tr.UnitPrice * tr.QuantitySold), 2) as 'Total',"
                                + " ROUND(sum((tr.UnitPrice * tr.QuantitySold) - (tr.UnitCost * tr.QuantitySold)), 2) as 'Profit'"
                                + " from Category c, TransactionRecord tr, Product p, Transaction t"
                                + " where tr.ProductID = p.ID and (p.CategoryID = (select ID from Category where Name='" + cat + "') "
                                + "or p.SubCategoryID = (select ID from Category where Name='" + cat + "'))"/*and c.ParentID IS NULL*/
                                + " and t.ID = tr.TransactionID"
                                + " and Year(t.CreateDate) = '" + yr + "' group by MonthName(t.CreateDate)";
                            
                            Performance_headings.clear();
                            Performance_headings.add("`Month`");
                            Performance_headings.add("`Quantity Sold`");
                            Performance_headings.add("`Total`");
                            Performance_headings.add("`Profit`");
                        }
                    } else {
                        if(cat.equals("All")){
                            performance_query = "Select c.Name, sum(tr.QuantitySold) as 'Quantity Sold', ROUND(sum(tr.UnitPrice * tr.QuantitySold), 2) as 'Total',"
                                + " ROUND(sum((tr.UnitPrice * tr.QuantitySold) - (tr.UnitCost * tr.QuantitySold)), 2) as 'Profit'"    
                                + " from Category c, TransactionRecord tr, Product p, Transaction t "
                                + " where tr.ProductID = p.ID and (p.CategoryID = c.ID or p.SubCategoryID = c.ID) " /*and c.ParentID IS NULL */
                                + " and t.ID = tr.TransactionID group by c.Name";
                            
                            Performance_headings.clear();
                            Performance_headings.add("`Name`");
                            Performance_headings.add("`Quantity Sold`");
                            Performance_headings.add("`Total`");
                            Performance_headings.add("`Profit`");

                        } else {
                            performance_query = "select MonthName(t.CreateDate) as 'Month', sum(tr.QuantitySold) as 'Quantity Sold',"
                                + " ROUND(sum(tr.UnitPrice * tr.QuantitySold), 2) as 'Total',"
                                + " ROUND(sum((tr.UnitPrice * tr.QuantitySold) - (tr.UnitCost * tr.QuantitySold)), 2) as 'Profit'"
                                + " from Category c, TransactionRecord tr, Product p, Transaction t"
                                + " where tr.ProductID = p.ID and (p.CategoryID = c.ID or p.SubCategoryID = c.ID)"/*and c.ParentID IS NULL*/
                                + " and c.Name = '" + cat + "' and t.ID = tr.TransactionID"
                                + " group by MonthName(t.CreateDate)"; 
                            
                            Performance_headings.clear();
                            Performance_headings.add("`Month`");
                            Performance_headings.add("`Quantity Sold`");
                            Performance_headings.add("`Total`");
                            Performance_headings.add("`Profit`");
                        }
                    }
                } else {
                    performance_query = "select MonthName(t.CreateDate) as 'Month', sum(tr.QuantitySold) as 'Quantity Sold',"
                            + " ROUND(tr.UnitPrice * sum(tr.QuantitySold), 2) as 'Sale Amount', " 
                            + " ROUND((tr.UnitPrice * sum(tr.QuantitySold)) - (tr.UnitCost * sum(tr.QuantitySold)), 2)"
                            + " as 'Profit' from TransactionRecord tr, Product p, Transaction t "
                            + " where tr.ProductID = p.ID and t.ID = tr.TransactionID";
                    if(prodID.equals("")){
                        performance_query += " and tr.ProductID = (select ID from Product where Name = '" + prodName + "')";
                    } else {
                        performance_query += " and tr.ProductID = '" + prodID + "' ";
                    }
                    
                    if(!yr.equals("All")){
                        performance_query += " and YEAR(t.CreateDate) = '" + yr + "'";
                    }
                    
                    performance_query += " group by YEAR(t.CreateDate), MonthName(t.CreateDate)";
                }
                updateReport(Performance_table, performance_query);
                fr.dispose();
            } 
        });

        jLabel9.setText("OR");

        javax.swing.GroupLayout jPanel_PerformanceLayout = new javax.swing.GroupLayout(jPanel_Performance);
        jPanel_Performance.setLayout(jPanel_PerformanceLayout);
        jPanel_PerformanceLayout.setHorizontalGroup(
            jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Title)
                                .addComponent(jLabel1)))
                        .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel3))
                        .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel7))
                        .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                            .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                        .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel4))
                            .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(productName, 0, 228, Short.MAX_VALUE)
                            .addComponent(productID)))
                    .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)
                        .addGap(48, 48, 48)
                        .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2))
        );
        jPanel_PerformanceLayout.setVerticalGroup(
            jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PerformanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(productID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(productName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel_PerformanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(submit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Performance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Performance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        return jPanel_Performance;
    }

    //returns JPanel for Report section
    public JPanel getWindow(){
        if(Reports == null)
        {
            CreateWindow();
        }
        
        return Reports;
    }
    
    //Prints table data for currently selected tab.
    public void printReport() {
        MessageFormat header = null;
        MessageFormat footer = new MessageFormat("Page {0, number, integer}");

        
        switch(active){
            case 0:
                header = new MessageFormat("Revenue Report");
                try {
                    Revenue_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (PrinterException e){
                    JOptionPane.showMessageDialog(null, "Error printing revenue report. " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
                
            case 1: 
                header = new MessageFormat("Inventory Report");
                try {
                    Inventory_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (PrinterException e){
                    JOptionPane.showMessageDialog(null, "Error printing inventory report. " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
                
            case 2: 
                header = new MessageFormat("Order Report");
                try {
                    Orders_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (PrinterException e){
                    JOptionPane.showMessageDialog(null, "Error printing order report. " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
                
            case 3: 
                header = new MessageFormat("Transaction Report");
                try {
                    Transactions_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (PrinterException e){
                    JOptionPane.showMessageDialog(null, "Error printing transaction report. " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            
            case 4: 
                header = new MessageFormat("Invoice Report");
                try {
                    Invoices_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (PrinterException e){
                    JOptionPane.showMessageDialog(null, "Error printing invoice report. " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            
            case 5:
                header = new MessageFormat("Performance Report");
                try {
                    Performance_table.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                } catch (PrinterException e){
                    JOptionPane.showMessageDialog(null, "Error printing performance report. " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
                
            default: break;
        }
    }

    @Override
    public void close() throws Exception {
        if(c != null) {
            c.close();
        }
    }
    
    //Class for formatting Date.
    class DateLabelFormatter extends AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

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
