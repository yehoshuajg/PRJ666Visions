/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vision;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;


/**
 *
 * @author Gaurav
 */
public class Supplier extends JFrame implements AutoCloseable {
    //Connection to database
    private Connection c = null;
    
    //Setting up GUI
    private JPanel Supplier;
    
    //For displaying detail
    JTabbedPane Supplier_Main = new JTabbedPane();
    JTextField details_supplierName = new JTextField();
    JTextField details_streetAddress = new JTextField();
    JTextField details_city = new JTextField();
    JTextField details_state_province = new JTextField();
    JTextField details_postalCode = new JTextField();
    JTextField details_orderCost = new JTextField();
    JTextField details_deliveryCost = new JTextField();
    JComboBox<String> details_status = new JComboBox<>();
    JTextField details_email = new JTextField();
    JTextField details_phoneNumber = new JTextField();
    JTable contacts = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
    int supplierID = 0; //For add contact
    
    public Supplier(){
        super();
        
        try {
            Connect connect = new Connect();
            c = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cannot establish connection with MySQL.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //returns JPanel for Report section
    public JPanel getWindow(){
        if(Supplier == null)
        {
            CreateWindow();
        }
        
        return Supplier;
    }
    
    private void CreateWindow() {
        Supplier = new JPanel();
        JPanel Suppliers = new JPanel();
        JTextField search_supplier_name = new JTextField();
        JButton btn_search = new JButton();
        JLabel jLabel1 = new JLabel();
        JButton btn_filter = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable list_of_suppliers = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        list_of_suppliers.getTableHeader().setReorderingAllowed(false);
        list_of_suppliers.setRowHeight(30);
        JSeparator jSeparator2 = new JSeparator();
        JPanel Details = new JPanel();
        JLabel jLabel2 = new JLabel();
        JComboBox<String> search_supplier_name1 = new JComboBox<>();
        JLabel jLabel3 = new JLabel();
        JSeparator jSeparator1 = new JSeparator();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JButton jButton1 = new JButton();
        JLabel jLabel8 = new JLabel();
        JLabel jLabel9 = new JLabel();
        JLabel jLabel10 = new JLabel();
        JLabel jLabel11 = new JLabel();
        JLabel jLabel12 = new JLabel();
        JLabel jLabel13 = new JLabel();
        JScrollPane jScrollPane2 = new JScrollPane();   
        JButton add_contact = new JButton();
        
        contacts.setRowHeight(30);
        contacts.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	int row = contacts.rowAtPoint(e.getPoint());
                int col = contacts.columnAtPoint(e.getPoint());
                
            	if (col == 5)
            	{
            		String fname = contacts.getValueAt(row, 0).toString();
            		String lname = contacts.getValueAt(row, 1).toString();
            		
            		int response = JOptionPane.showConfirmDialog(null, 
            			"Are you sure you want to remove contact " + fname + " " + lname, 
            			"Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            	   
            		if (response == JOptionPane.YES_OPTION) {
            			String pnumber = contacts.getValueAt(row, 2).toString();

            			String query = "DELETE FROM Contact where FirstName = '" + fname + "'"
            					+ " and LastName = '" + lname + "' and PhoneNumber = '" + pnumber 
            					+ "' and SupplierID = " + supplierID;

            			Statement stmt = null;
                        ResultSet rs = null;

                        try {
                        	stmt = c.createStatement();
                            stmt.executeUpdate(query);
                            
                            query = "Select FirstName as 'First Name', LastName as 'Last Name', PhoneNumber as 'Phone Number', "
                                    + " Email, Department, 'Delete' as '' from Contact where SupplierID = " + supplierID;
                            rs = stmt.executeQuery(query);
                            
                            if(rs.next()) {
                                rs.beforeFirst();
                                contacts.setModel(DbUtils.resultSetToTableModel(rs));
                            } else {
                                DefaultTableModel model = (DefaultTableModel) contacts.getModel();
                                model.setRowCount(0);
                                model.setColumnCount(1);
                                model.addRow(new Object[]{"No contacts found."});
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
                }
            }
        });
        
        JButton supplier_add = new JButton();
        JButton view_all = new JButton();
        
        details_supplierName.setEditable(false);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Supplier_Main.setTabPlacement(JTabbedPane.LEFT);
        Supplier_Main.setToolTipText("");

        btn_search.setText("Search");
        btn_search.addActionListener((java.awt.event.ActionEvent evt) -> {
            
            String search = search_supplier_name.getText();
            if(!search.trim().equals("")) {
                String query = "select s.Name, s.Street as 'Address', s.City, s.PhoneNumber as 'Phone Number', "
                    + " 'View Details' as '' from Supplier s where s.Name like '%" + search + "%'";

                Statement stmt = null;
                ResultSet rs = null;

                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery(query);

                    list_of_suppliers.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));

                    if(rs != null){
                        if(rs.next()) {
                            rs.beforeFirst();
                            list_of_suppliers.setModel(DbUtils.resultSetToTableModel(rs));
                        } else {
                            DefaultTableModel model = (DefaultTableModel) list_of_suppliers.getModel();
                            model.setRowCount(0);
                            model.setColumnCount(1);
                            model.addRow(new Object[]{"No suppliers found named " + search + "."});
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
        });

        jLabel1.setText("Name:");

        btn_filter.setText("Filter");

        String query = "select s.Name, s.Street as 'Address', s.City, s.PhoneNumber as 'Phone Number', "
                + " 'View Details' as '' from Supplier s";
        
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery(query);
            
            list_of_suppliers.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
            
            if(rs != null){
                if(rs.next()) {
                    rs.beforeFirst();
                    list_of_suppliers.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    DefaultTableModel model = (DefaultTableModel) list_of_suppliers.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(1);
                    model.addRow(new Object[]{"No suppliers found."});
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
        
        list_of_suppliers.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if (e.getClickCount() == 2)
                {
                    int row = list_of_suppliers.getSelectedRow();
                    String company = (String) list_of_suppliers.getModel().getValueAt(row, 0);
                    String address = (String) list_of_suppliers.getModel().getValueAt(row, 1);
                    
                    search_supplier_name1.setSelectedItem(company + " : " + address);
                    //showDetails(company, address);
                    Supplier_Main.setSelectedIndex(1);
                }
            }
        });

        jScrollPane1.setViewportView(list_of_suppliers);
        
        supplier_add.setText("Create New Supplier");
        supplier_add.addActionListener((java.awt.event.ActionEvent evt) -> {
            JFrame f = new JFrame();
            f.getContentPane().add(getCreateSupplierPanel(f));
            f.setSize(700, 350);
            f.setVisible(true);
            f.setLocationRelativeTo(null);
        });
  
        view_all.setText("View All");
        view_all.addActionListener((java.awt.event.ActionEvent evt) -> {
            String q = "select s.Name, s.Street as 'Address', s.City, s.PhoneNumber as 'Phone Number', "
                    + " 'View Details' as '' from Supplier s";

            Statement s = null;
            ResultSet r = null;

            try {
                s = c.createStatement();
                r = s.executeQuery(q);

                list_of_suppliers.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));

                if(r != null){
                    if(r.next()) {
                        r.beforeFirst();
                        list_of_suppliers.setModel(DbUtils.resultSetToTableModel(r));
                    } else {
                        DefaultTableModel model = (DefaultTableModel) list_of_suppliers.getModel();
                        model.setRowCount(0);
                        model.setColumnCount(1);
                        model.addRow(new Object[]{"No suppliers found."});
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage()); 
            } finally {
                try {
                    if(s != null) s.close();
                    if(r != null) r.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }    
        });

        javax.swing.GroupLayout SuppliersLayout = new javax.swing.GroupLayout(Suppliers);
        Suppliers.setLayout(SuppliersLayout);
        SuppliersLayout.setHorizontalGroup(
            SuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(SuppliersLayout.createSequentialGroup()
                        .addGroup(SuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(SuppliersLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(search_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_filter, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(view_all, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(supplier_add)))
                        .addGap(26, 26, 26))))
        );
        SuppliersLayout.setVerticalGroup(
            SuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SuppliersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SuppliersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(search_supplier_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_search)
                    .addComponent(jLabel1)
                    .addComponent(btn_filter)
                    .addComponent(supplier_add)
                    .addComponent(view_all))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addGap(26, 26, 26))
        );

        Supplier_Main.addTab("Suppliers", Suppliers);

        jLabel2.setText("Supplier:");
                
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("Select Name, Street from Supplier");
            
            if(rs != null){
                while(rs.next()){                    
                    search_supplier_name1.addItem(rs.getString(1) + " : " + rs.getString(2));
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
        
        search_supplier_name1.addActionListener ((ActionEvent e) -> {
            String[] selection = search_supplier_name1.getSelectedItem().toString().split(":");
            
            String company = selection[0].trim();
            String address = selection[1].trim();
            
            showDetails(company, address);
        });
                
        jLabel3.setText("Supplier:");

        jLabel4.setText("Street Address:");

        jLabel5.setText("City:");

        jLabel6.setText("State/Province:");

        jLabel7.setText("Postal/Zip Code:");

        jButton1.setText("Update Details");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            if(supplierID > 0) {
                String errors = "";
                boolean keep = true;
                
            	if(details_streetAddress.getText().trim().length() < 10 && keep){
                    errors = "Error: Supplier address is invalid must be 10 characters long.";
                    keep = false;
                } else if(details_city.getText().trim().equals("") && keep){
                    errors = "Error: City field is empty.";
                    keep = false;
                } else if(details_state_province.getText().trim().equals("") && keep){
                    errors = "Error: State/Province field is empty.";
                    keep = false;
                } else if(!(details_postalCode.getText().trim().matches("^[0-9]{5}$") || 
                		details_postalCode.getText().trim().matches("^[a-zA-Z][0-9][a-zA-Z][ ]{0,2}[0-9][a-zA-Z][0-9]$")) && keep){
                    errors = "Error: Invalid Postal/Zip code, must be north american, EX: ##### or X#X #X#.";
                    keep = false;
                } else if(!details_phoneNumber.getText().trim().matches("^[1]-[0-9]{3}-[0-9]{3}-[0-9]{4}$") && keep){
                    errors = "Error: Phone Number must be north american and numbers separated by - (Ex: 1-XXX-XXX-XXXX).";
                    keep = false;
                } else if(!details_email.getText().trim().matches("^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z0-9]){2,63})+$") && keep){
                    errors = "Error: Email field is invalid, Ex: example@example.com";
                    keep = false;
                } else if(details_orderCost.getText().trim().equals("") && keep){
                    errors = "Error: Minimum Order Cost is empty.";
                    keep = false;
                } else if(details_deliveryCost.getText().trim().equals("") && keep){
                    errors = "Error: Delivery Cost is empty.";
                    keep = false;
                }
            	
            	double ordercost = 0.0;
            	double delivercost = 0.0;
            	if(keep){
	            	try {
	            		ordercost = Double.parseDouble(details_orderCost.getText().trim());
	            		delivercost = Double.parseDouble(details_deliveryCost.getText().trim());
	            	} catch(NumberFormatException e) {
	            		errors = "Error: Minimum order Cost or Delivery Cost is not a number, do not include symbols.";
	            		keep = false;
	            	}
            	}
            	
            	if(keep){
	            	PreparedStatement s = null;
	                ResultSet r = null;
	                
	                try {
	                    String sql = "Update Supplier SET Street = '" + details_streetAddress.getText().trim() + "', "
	                            + " City = '" + details_city.getText().trim() + "', State_Province = '"
	                            + details_state_province.getText().trim() + "', PostalCode = '" 
	                            + details_postalCode.getText().trim() + "', PhoneNumber = '" 
	                            + details_phoneNumber.getText().trim() + "', Email = '" + details_email.getText().trim() 
	                            + "', Status = '" + details_status.getSelectedItem().toString().trim() + "', "
	                            + " MinimumOrderCost = '" + ordercost + "', "
	                            + " DeliveryCost = '" + delivercost + "' where ID = " + supplierID;
	                    
	                    s = c.prepareStatement(sql);
	                    int dump = s.executeUpdate();
	                    
	                    sql = "select s.Name, s.Street as 'Address', s.City, s.PhoneNumber as 'Phone Number', "
	                        + " 'View Details' as '' from Supplier s";
	                    
	                    r = s.executeQuery(sql);
	                            
	                    if(r.next()) {
	                        r.beforeFirst();
	                        list_of_suppliers.setModel(DbUtils.resultSetToTableModel(r));
	                    } else {
	                        DefaultTableModel model = (DefaultTableModel) list_of_suppliers.getModel();
	                        model.setRowCount(0);
	                        model.setColumnCount(1);
	                        model.addRow(new Object[]{"No suppliers found."});
	                    }
	                    
	                } catch (Exception e){
	                    JOptionPane.showMessageDialog(null, "Problem connecting to MySQL server.", "Error", JOptionPane.ERROR_MESSAGE);
	                } finally {
	                    try {
	                        if(s != null) s.close();
	                        if(r != null) r.close();
	                    } catch (SQLException ex) {
	                        System.out.println(ex.getMessage());
	                    }
	                }
            	} else {
            		JOptionPane.showMessageDialog(null, errors, "Error", JOptionPane.ERROR_MESSAGE);
            	}
            } else {
                JOptionPane.showMessageDialog(null, "Must have supplier details open "
                    + "(Go to: Suppliers list -> double click on supplier you wish to work with).",
                    "Warning", JOptionPane.WARNING_MESSAGE);                
            }
        });
        
        jLabel8.setText("Phone Number:");

        jLabel9.setText("Email:");

        jLabel10.setText("Status:");
        
        details_status.setModel(new DefaultComboBoxModel<>(new String[] { "Active", "InActive", "Occasional"}));

        jLabel11.setText("Minimum Order Cost:");

        jLabel12.setText("Delivery Cost:");

        jLabel13.setText("Contacts:");
        
        jScrollPane2.setViewportView(contacts);

        add_contact.setText("Add Contacts");
        add_contact.addActionListener((java.awt.event.ActionEvent evt) -> {
            if(supplierID > 0) {
                JFrame f = new JFrame();
                f.getContentPane().add(addContact(f));
                f.setSize(415, 310);
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            } else {
                JOptionPane.showMessageDialog(null, "Must have supplier details open "
                    + "(Go to: Suppliers list -> double click on supplier you wish to work with).",
                    "Warning", JOptionPane.WARNING_MESSAGE);                
            }
        });
        
        javax.swing.GroupLayout DetailsLayout = new javax.swing.GroupLayout(Details);
        Details.setLayout(DetailsLayout);
        DetailsLayout.setHorizontalGroup(
            DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DetailsLayout.createSequentialGroup()
                        .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DetailsLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(add_contact))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DetailsLayout.createSequentialGroup()
                                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DetailsLayout.createSequentialGroup()
                                        .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(DetailsLayout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(59, 59, 59)
                                                .addComponent(details_supplierName, 162, 162, 162))
                                            .addGroup(DetailsLayout.createSequentialGroup()
                                                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel4)
                                                    .addComponent(jLabel5)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel7))
                                                .addGap(18, 18, 18)
                                                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(details_postalCode)
                                                    .addComponent(details_state_province)
                                                    .addComponent(details_streetAddress)
                                                    .addComponent(details_city))))
                                        .addGap(79, 79, 79)
                                        .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8))
                                        .addGap(18, 18, 18)
                                        .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(details_phoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                            .addComponent(details_email, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(details_status, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(details_orderCost)
                                            .addComponent(details_deliveryCost)))
                                    .addGroup(DetailsLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(search_supplier_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGap(79, 79, 79)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 79, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(26, 26, 26))))
        );
        DetailsLayout.setVerticalGroup(
            DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(search_supplier_name1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DetailsLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(details_supplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DetailsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(details_phoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(details_streetAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(details_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(details_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(details_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(details_state_province, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(details_orderCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(details_postalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(details_deliveryCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(add_contact))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        Supplier_Main.addTab("Details", Details);

        javax.swing.GroupLayout SupplierLayout = new javax.swing.GroupLayout(Supplier);
        Supplier.setLayout(SupplierLayout);
        SupplierLayout.setHorizontalGroup(
            SupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 788, Short.MAX_VALUE)
            .addGroup(SupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Supplier_Main))
        );
        SupplierLayout.setVerticalGroup(
            SupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(SupplierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Supplier_Main, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Supplier, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Supplier, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
    
    private JPanel getCreateSupplierPanel(JFrame fr) {

        JPanel panel_createSupplier = new JPanel();
        JLabel jLabel1 = new JLabel();
        JTextField phoneNumber = new JTextField();
        JTextField city = new JTextField();
        JTextField status = new JTextField();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel11 = new JLabel();
        JTextField email = new JTextField();
        JLabel jLabel12 = new JLabel();
        JTextField deliveryCost = new JTextField();
        JLabel jLabel5 = new JLabel();
        JTextField state_province = new JTextField();
        JTextField supplierName = new JTextField();
        JLabel jLabel10 = new JLabel();
        JTextField orderCost = new JTextField();
        JLabel jLabel8 = new JLabel();
        JTextField streetAddress = new JTextField();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel9 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JTextField postalCode = new JTextField();
        JLabel jLabel3 = new JLabel();
        JLabel errors = new JLabel();
        JButton btn_create = new JButton();
        JButton btn_cancel = new JButton();

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("Create Supplier");

        jLabel4.setText("Street Address:");

        jLabel11.setText("Minimum Order Cost:");

        jLabel12.setText("Delivery Cost:");

        jLabel5.setText("City:");

        jLabel10.setText("Status:");
        status.setText("Active");
        status.setEditable(false);

        jLabel8.setText("Phone Number:");

        jLabel6.setText("State/Province:");

        jLabel9.setText("Email:");

        jLabel7.setText("Postal/Zip Code:");

        jLabel3.setText("Supplier:");
        
        errors.setForeground(new java.awt.Color(255, 0, 0));

        btn_create.setText("Create Supplier");
        //Checking if provided data is valid if so, adding to database.
        btn_create.addActionListener((java.awt.event.ActionEvent evt) -> {
            boolean keep = true;
            
            if(supplierName.getText().trim().equals("")){
                errors.setText("Error: Supplier Name field is empty.");
                keep = false;
            } else if(streetAddress.getText().trim().length() < 10 && keep){
                errors.setText("Error: Supplier address is invalid must be 10 characters long.");
                keep = false;
            } else if(city.getText().trim().equals("") && keep){
                errors.setText("Error: City field is empty.");
                keep = false;
            } else if(state_province.getText().trim().equals("") && keep){
                errors.setText("Error: State/Province field is empty.");
                keep = false;
            } else if(!(postalCode.getText().trim().matches("^[0-9]{5}$") || 
            		postalCode.getText().trim().matches("^[a-zA-Z][0-9][a-zA-Z][ ]{0,2}[0-9][a-zA-Z][0-9]$")) && keep){
                errors.setText("Error: Invalid Postal/Zip code, must be north american, EX: ##### or X#X #X#.");
                keep = false;
            } else if(!phoneNumber.getText().trim().matches("^[1]-[0-9]{3}-[0-9]{3}-[0-9]{4}$") && keep){
                errors.setText("Error: Phone Number must be north american and numbers separated by - (Ex: 1-XXX-XXX-XXXX).");
                keep = false;
            } else if(!email.getText().trim().matches("^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z0-9]){2,63})+$") && keep){
                errors.setText("Error: Email field is invalid, Ex: example@example.com");
                keep = false;
            } else if(orderCost.getText().trim().equals("") && keep){
                errors.setText("Error: Minimum Order Cost is empty.");
                keep = false;
            } else if(deliveryCost.getText().trim().equals("") && keep){
                errors.setText("Error: Delivery Cost is empty.");
                keep = false;
            }
            
            if(keep){
                PreparedStatement s = null;
                
                try{
                    double ordercost = Double.parseDouble(orderCost.getText().trim());
                    double deliverycost = Double.parseDouble(deliveryCost.getText().trim());
                    
                    String q = "insert into Supplier (`Name`, Street, City, State_Province, PostalCode, PhoneNumber, "
                            + " Email, `Status`, MinimumOrderCost, DeliveryCost) " 
                            + " values (?, ?, ?, ?, ?, ?, ?, 'Active', ?, ?)";

                    s = c.prepareStatement(q);
                    
                    s.setString(1, supplierName.getText().trim());
                    s.setString(2, streetAddress.getText().trim());
                    s.setString(3, city.getText().trim());
                    s.setString(4, state_province.getText().trim());
                    s.setString(5, postalCode.getText().trim());
                    s.setString(6, phoneNumber.getText().trim());
                    s.setString(7, email.getText().trim());
                    s.setDouble(8, ordercost);
                    s.setDouble(9, deliverycost);
                    
                    s.executeUpdate();
                   
                    //Closing the frame after data inserted into database.
                    fr.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Cannot establish connection with MySQL.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                } catch(NumberFormatException e){
                    errors.setText("Error: Minimum order cost or delivery cost is not a number.");
                }  finally {
                    try {
                        if(s != null) s.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            
        });
                
        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener((java.awt.event.ActionEvent evt) -> {
            fr.dispose();
        });
        
        GroupLayout panel_createSupplierLayout = new GroupLayout(panel_createSupplier);
        panel_createSupplier.setLayout(panel_createSupplierLayout);
        panel_createSupplierLayout.setHorizontalGroup(
            panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_createSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(errors)
                    .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(panel_createSupplierLayout.createSequentialGroup()
                            .addComponent(btn_create)
                            .addGap(40, 40, 40)
                            .addComponent(btn_cancel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                        .addGroup(panel_createSupplierLayout.createSequentialGroup()
                            .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addGroup(panel_createSupplierLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(supplierName, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                .addGroup(panel_createSupplierLayout.createSequentialGroup()
                                    .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(postalCode)
                                        .addComponent(state_province)
                                        .addComponent(streetAddress)
                                        .addComponent(city, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))))
                            .addGap(56, 56, 56)
                            .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel10)
                                .addComponent(jLabel9)
                                .addComponent(jLabel8))
                            .addGap(18, 18, 18)
                            .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(phoneNumber)
                                .addComponent(email, GroupLayout.Alignment.TRAILING)
                                .addComponent(status, GroupLayout.Alignment.TRAILING)
                                .addComponent(orderCost)
                                .addComponent(deliveryCost, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        panel_createSupplierLayout.setVerticalGroup(
            panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_createSupplierLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(supplierName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel_createSupplierLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 3, GroupLayout.PREFERRED_SIZE)
                        .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(phoneNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(streetAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(city, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(state_province, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(orderCost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(postalCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(deliveryCost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(errors)
                .addGap(18, 18, 18)
                .addGroup(panel_createSupplierLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cancel)
                    .addComponent(btn_create))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_createSupplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_createSupplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
        
        return panel_createSupplier;
    }
    
    private void showDetails(String company, String address){
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = c.createStatement();
            
            String query = "select `Name`, Street, City, State_Province, PostalCode, PhoneNumber, Email, "
                    + " `Status`, MinimumOrderCost, DeliveryCost, ID from Supplier where `Name` = '" + company
                    + "' and Street = '" + address + "'";

            rs = stmt.executeQuery(query);
            
            contacts.getTableHeader().setFont(new java.awt.Font("Tahoma", Font.BOLD, 12));
            
            if(rs.next()) {
                details_supplierName.setText(rs.getString(1));
                details_streetAddress.setText(rs.getString(2));
                details_city.setText(rs.getString(3));
                details_state_province.setText(rs.getString(4));
                details_postalCode.setText(rs.getString(5));
                details_phoneNumber.setText(rs.getString(6));
                details_email.setText(rs.getString(7));
                switch(rs.getString(8)) {
                    case "Active": details_status.setSelectedIndex(0); break;
                    case "InActive": details_status.setSelectedIndex(1); break;
                    default: details_status.setSelectedIndex(2); break;
                }
                //details_status.setText(rs.getString(8));
                details_orderCost.setText(rs.getString(9));
                details_deliveryCost.setText(rs.getString(10));
                supplierID = rs.getInt(11);
            } else {
                JOptionPane.showMessageDialog(null, "Supplier not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                
                supplierID = 0;
                DefaultTableModel model = (DefaultTableModel) contacts.getModel();
                model.setRowCount(0);
                model.setColumnCount(1);
                model.addRow(new Object[]{"No contacts found."});
            }
            
            if(supplierID != 0){
                query = "Select FirstName as 'First Name', LastName as 'Last Name', PhoneNumber as 'Phone Number', "
                        + " Email, Department, 'Delete' as '' from Contact where SupplierID = " + supplierID;
                rs = stmt.executeQuery(query);
                
                if(rs.next()) {
                    rs.beforeFirst();
                    contacts.setModel(DbUtils.resultSetToTableModel(rs));
                } else {
                    DefaultTableModel model = (DefaultTableModel) contacts.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(1);
                    model.addRow(new Object[]{"No contacts found."});
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
    
    private JPanel addContact(JFrame fr) {
        JPanel panel_addContact = new JPanel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel error = new JLabel();
        JTextField first_name = new JTextField();
        JTextField last_name = new JTextField();
        JTextField phone_number = new JTextField();
        JTextField email = new JTextField();
        JTextField department = new JTextField();
        JButton btn_addContact = new JButton();
        JButton btn_cancel = new JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        jLabel1.setText("New Contact");

        jLabel2.setText("First Name:");

        jLabel3.setText("Last Name:");

        jLabel4.setText("Phone Number:");

        jLabel5.setText("Email:");
        
        jLabel6.setText("Department:");
        
        error.setForeground(new java.awt.Color(255, 0, 0));
        
        btn_addContact.setText("Add Contact");
        btn_addContact.addActionListener((ActionEvent evt) -> {
            String fname = first_name.getText().trim();
            String lname = last_name.getText().trim();
            String phonenumber = phone_number.getText().trim();
            String Email = email.getText().trim();
            String departmentt = department.getText().trim();
            boolean keep = true;
            
            if(fname.equals("")){
                error.setText("Please enter valid  first name.");
                keep = false;
            } else if(lname.equals("") && keep){
                error.setText("Please enter valid  last name.");
                keep = false;
            } else if(!phonenumber.matches("^[1]-[0-9]{3}-[0-9]{3}-[0-9]{4}$") && keep){
                error.setText("Error: Phone Number must be in follwing format: 1-XXX-XXX-XXXX.");
                keep = false;
            } else if(!Email.matches("^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z0-9]){2,63})+$") && keep){
                error.setText("Error: Email field is invalid, Ex: example@example.com");
                keep = false;
            } else if(departmentt.equals("") && keep){
                error.setText("Please enter department the person assist in most.");
                keep = false;
            }
            
            if(keep) {
                PreparedStatement s = null;
                ResultSet rs = null;
                
                try {
                    String sql = "insert into Contact (FirstName, LastName, PhoneNumber, Email, Department, SupplierID)"
                            + " values ('" + fname + "', '" + lname + "', '" + phonenumber + "', '" + Email 
                            + "', '" + departmentt + "', " + supplierID + ")";
                    
                    s = c.prepareStatement(sql);
                    int r = s.executeUpdate();
                    
                    sql = "Select FirstName as 'First Name', LastName as 'Last Name', PhoneNumber as 'Phone Number', "
                        + " Email, Department, 'Delete' as '' from Contact where SupplierID = " + supplierID;
                    rs = s.executeQuery(sql);

                    if(rs.next()) {
                        rs.beforeFirst();
                        contacts.setModel(DbUtils.resultSetToTableModel(rs));
                    } else {
                        DefaultTableModel model = (DefaultTableModel) contacts.getModel();
                        model.setRowCount(0);
                        model.setColumnCount(1);
                        model.addRow(new Object[]{"No contacts found."});
                    }
                } catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Problem connecting to MySQL server.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if(s != null) s.close();
                        if(rs != null) rs.close();
                    } catch (SQLException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                
                fr.dispose();
            }
        });
        
        btn_cancel.setText("Cancel");
        btn_cancel.addActionListener((ActionEvent evt) -> {
            fr.dispose();
        });
        
        GroupLayout panel_addContactLayout = new GroupLayout(panel_addContact);
        panel_addContact.setLayout(panel_addContactLayout);
        panel_addContactLayout.setHorizontalGroup(
            panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_addContactLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(panel_addContactLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(btn_addContact, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_cancel, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
                    .addComponent(error)
                    .addGroup(panel_addContactLayout.createSequentialGroup()
                        .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(21, 21, 21)
                        .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(phone_number)
                            .addComponent(last_name, GroupLayout.Alignment.LEADING)
                            .addComponent(email)
                            .addComponent(first_name, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(department))))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        panel_addContactLayout.setVerticalGroup(
            panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_addContactLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(first_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(last_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(phone_number, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(email, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(department, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(error)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(panel_addContactLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_addContact)
                    .addComponent(btn_cancel))
                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_addContact, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panel_addContact, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        
        return panel_addContact;
    }
                    
    @Override
    public void close() throws Exception {
        if(c != null) {
            c.close();
        }
    }
}
