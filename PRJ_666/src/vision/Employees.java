package vision;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;

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
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.WindowConstants;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import net.proteanit.sql.DbUtils;
import org.jdatepicker.impl.*;

public class Employees extends JFrame{
	private Connect connect;
	private Connection con;

	//Employee
	private int id;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String stateProvince;
	private String postalCode;
	private String homePhone;
	private String cellPhone;
	private String email;
	private int positionID;
	private String jobType;
	private String username;
	private String password;
	private String hireDate;
	private String terminationDate;
	
	// Windowbuilder vars
	
	private JPanel contentPane;
	private JPanel contentPane_1;
	private JButton btn_Add;
    private JButton btn_Edit;
    private JButton btn_StaffList;
    private JButton btn_Details;
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
    
	
	//End Windowbuilder vars
	
	
	private ArrayList<String> table_headings = new ArrayList<>();
	
	public Employees() {
		super();
        
        try {
            connect = new Connect();
            c = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
        }
	}
	
	//Setters
	public void setID(int id){ this.id = id; }
	public void setFirstName(String firstName){ this.firstName = firstName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	public void setStreet(String street){ this.street = street;	}
	public void setCity(String city){ this.city = city; }
	public void setStateProvince(String stateProvince){ this.stateProvince = stateProvince; }
	public void setPostalCode(String postalCode){ this.postalCode = postalCode; }
	public void setHomePhone(String homePhone){ this.homePhone = homePhone; }
	public void setCellPhone(String cellPhone){ this.cellPhone = cellPhone; }
	public void setEmail(String email){ this.email = email; }
	public void setPositionID(int positionID){ this.positionID = positionID; }
	public void setJobType(String jobType){ this.jobType = jobType; }
	public void setUserame(String username){ this.username = username; }
	//No need to set password
	public void setHireDate(String hireDate){ this.hireDate = hireDate; }
	public void setTerminationDate(String terminationDate){ this.terminationDate = terminationDate; }
	
	//Getters
	public int getID(){ return this.id; }
	public String getFirstName(){ return this.firstName; }
	public String getLastName(){ return this.lastName; }
	public String getStreet(){ return this.street; }
	public String getCity(){ return this.city; }
	public String getStateProvince(){ return this.stateProvince; }
	public String getPostalCode(){ return this.postalCode; }
	public String getHomePhone(){ return this.homePhone; }
	public String getCellPhone(){ return this.cellPhone; }
	public String getEmail(){ return this.email; }
	public int getPositionID(){ return this.positionID; }
	public String getJobType(){ return this.jobType; }	
	public String getUsername(){ return this.username; }
	//No need to get password
	public String getHireDate(){ return this.hireDate; }
	public String getTerminationDate(){ return this.terminationDate; }
	
	public boolean fetchLogin(String userID, String pass) throws Exception{
		//Execute a query
		con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
		Statement state = null;
		
		state = con.createStatement();
	    String sql;
	    sql = "SELECT * FROM Employee where UserName = '" + userID + "'";
	    ResultSet rs = state.executeQuery(sql);
	    int count = 0;
	    //Extract data from result set
	    while(rs.next()){
			//Retrieve by column name
	    	this.id = rs.getInt("ID");
	    	this.firstName = rs.getString("FirstName");
	    	this.lastName = rs.getString("LastName");
	    	this.street = rs.getString("Street");
	    	this.city = rs.getString("City");
	    	this.stateProvince = rs.getString("State_Province");
	    	this.postalCode = rs.getString("PostalCode");
	    	this.homePhone = rs.getString("HomePhone");
	    	this.cellPhone = rs.getString("CellPhone");
	    	this.email = rs.getString("Email");
	    	this.positionID = rs.getInt("PositionID");
	    	this.jobType = rs.getString("JobType");
		  	this.username = rs.getString("UserName");
		  	
		  	this.hireDate = rs.getString("HireDate");
		  	this.terminationDate = rs.getString("TerminationDate");
		  	count++;
	    }
	    //Clean-up environment
	    rs.close();
	    state.close();
	    con.close();
	    if(count == 1){
	    	return true;
	    }
	    else{
	    	return false;
	    }
	}
	
	public boolean validateEmpty(String temp){
		if(temp.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean login(String name, String pass) throws Exception{
		if(fetchLogin(name,pass) == true){
			return true;
		}
		else{
			return false;
		}
	}
	private void CreateWindow(){
        jPanel1 = new javax.swing.JPanel();
        jPanel1.setBounds(17, 5, 1517, 677);
        btn_StaffList = new javax.swing.JButton();
        btn_Add = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        
        jTable1 = new JTable(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        jTable1.setRowHeight(30);
        jTable1.setBackground(Color.WHITE);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        jLabel1 = new javax.swing.JLabel();
        btn_Edit = new javax.swing.JButton();
        btn_Details = new javax.swing.JButton();
        btn_Filter = new javax.swing.JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn_StaffList.setText("Staff List");
        btn_StaffList.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                query = "select ID as 'ID', FirstName |" + " " + "| LastName as 'Name',"
                        + " JobType as 'Role', PositionID as 'Position',"
                        + " HomePhone as 'Phone', Email as 'Email'";
                        
                active = "revenue";
                
                table_headings.clear();
                table_headings.add("`ID`");
                table_headings.add("`Name`");
                table_headings.add("`Role`");
                table_headings.add("`Position`");
                table_headings.add("`Phone`");
                table_headings.add("`Email`");
                
                order = " ";
                //updateReport();    
            }
        });
        
        btn_Add.setText("Add");
        btn_Add.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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
            }
        });

        btn_Edit.setText("Edit");
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        
        btn_Details.setText("Details");
        btn_Details.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            }
        });
        
        btn_Filter.setText("Filter");
        btn_Filter.setPreferredSize(new java.awt.Dimension(75, 23));
        
        btn_Filter.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.CENTER)
        				.addComponent(btn_Details, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(btn_Edit, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        				.addComponent(btn_Add, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
        				.addComponent(btn_StaffList, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
        			.addGap(6)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 1186, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btn_Filter, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
        			.addGap(177))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(67)
        					.addComponent(btn_StaffList)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btn_Add)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btn_Edit)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btn_Details))
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGap(29)
        					.addComponent(btn_Filter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(55, Short.MAX_VALUE))
        		.addComponent(jSeparator1, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        jPanel1.setLayout(jPanel1Layout);

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
	
	public JPanel getWindow() {
		if(jPanel1 == null){
			CreateWindow();
		}
		
		return jPanel1;
	}
}
