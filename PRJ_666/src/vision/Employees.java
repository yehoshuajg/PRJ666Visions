package vision;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.sql.*;

import java.util.ArrayList;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;

public class Employees extends JFrame{
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
	private String salt;
	
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
    //Color
    private String defaultColor = "#eeeeee";
	
	
	private ArrayList<String> table_headings = new ArrayList<>();
	private JTextField textField_firstName;
	private JTextField textField_lastName;
	private JTextField textField_street;
	private JTextField textField_city;
	private JTextField textField_province;
	private JTextField textField_postalCode;
	private JTextField textField_homePhone;
	private JTextField textField_cellPhone;
	private JTextField textField_email;
	private JTextField textField_positionID;
	private JTextField textField_jobType;
	private JTextField textField_userName;
	private JTextField textField_password;
	private JTextField textField_hireDate;
	
	public Employees() {
		//Removed connect connection, create an instance of connect class and use the getters to grab the url, user, name
		//Close connection when done using it. Add try/catch rather than throw exception in function prototype.
		//Look at fetchLogin() to get an idea.
		super();
		jPanel1 = new javax.swing.JPanel();
        jPanel1 = (JPanel) getContentPane();
        jPanel1.setBounds(0, 0, 1280, 720);
        jPanel1.setLayout(null);
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(6, 6, 1268, 673);
        getContentPane().add(tabbedPane);
        
        //JPanel panel_staff = new JPanel();
        //tabbedPane.addTab("Staff List", null, panel_staff, null);
        
        JPanel panel_add_staff = new JPanel();
        tabbedPane.addTab("Add", null, panel_add_staff, null);
        panel_add_staff.setLayout(null);
        
        JTabbedPane tabbedPane_add_employee = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_add_employee.setBounds(350, 6, 533, 615);
        panel_add_staff.add(tabbedPane_add_employee);
        
        JPanel panel_add_employee = new JPanel();
        tabbedPane_add_employee.addTab("Add Employee", null, panel_add_employee, null);
        panel_add_employee.setLayout(null);
        
        JTextPane textPane_firstName = new JTextPane();
        textPane_firstName.setBounds(10, 10, 72, 20);
        panel_add_employee.add(textPane_firstName);
        textPane_firstName.setText("First Name:");
        textPane_firstName.setBackground(Color.decode(defaultColor));
        
        JTextPane txtpnLastName = new JTextPane();
        txtpnLastName.setText("Last Name:");
        txtpnLastName.setBounds(10, 40, 72, 20);
        txtpnLastName.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnLastName);
        
        JTextPane txtpnStreet = new JTextPane();
        txtpnStreet.setText("Street:");
        txtpnStreet.setBounds(10, 72, 40, 20);
        txtpnStreet.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnStreet);
        
        JTextPane txtpnCity = new JTextPane();
        txtpnCity.setText("City:");
        txtpnCity.setBounds(10, 104, 29, 20);
        txtpnCity.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnCity);
        
        JTextPane txtpnState_Province = new JTextPane();
        txtpnState_Province.setText("State/Province:");
        txtpnState_Province.setBounds(10, 136, 100, 20);
        txtpnState_Province.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnState_Province);
        
        JTextPane txtpnPostalCode = new JTextPane();
        txtpnPostalCode.setText("Postal Code:");
        txtpnPostalCode.setBounds(10, 168, 78, 20);
        txtpnPostalCode.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnPostalCode);
        
        JTextPane txtpnHomePhone = new JTextPane();
        txtpnHomePhone.setText("Home Phone:");
        txtpnHomePhone.setBounds(10, 200, 83, 20);
        txtpnHomePhone.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnHomePhone);
        
        JTextPane txtpnCellPhone = new JTextPane();
        txtpnCellPhone.setText("Cell Phone:");
        txtpnCellPhone.setBounds(10, 232, 72, 20);
        txtpnCellPhone.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnCellPhone);
        
        JTextPane txtpnEmail = new JTextPane();
        txtpnEmail.setText("Email:");
        txtpnEmail.setBounds(10, 264, 40, 20);
        txtpnEmail.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnEmail);
        
        JTextPane txtpnPositionId = new JTextPane();
        txtpnPositionId.setText("Position ID:");
        txtpnPositionId.setBounds(10, 296, 78, 20);
        txtpnPositionId.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnPositionId);
        
        JTextPane txtpnJobType = new JTextPane();
        txtpnJobType.setText("Job Type:");
        txtpnJobType.setBounds(10, 328, 60, 20);
        txtpnJobType.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnJobType);
        
        JTextPane txtpnUsername = new JTextPane();
        txtpnUsername.setText("Username:");
        txtpnUsername.setBounds(10, 360, 70, 20);
        txtpnUsername.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnUsername);
        
        JTextPane txtpnPassword = new JTextPane();
        txtpnPassword.setText("Password:");
        txtpnPassword.setBounds(10, 392, 63, 20);
        txtpnPassword.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnPassword);
        
        JTextPane txtpnHireDate = new JTextPane();
        txtpnHireDate.setText("Hire Date:");
        txtpnHireDate.setBounds(10, 424, 68, 20);
        txtpnHireDate.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnHireDate);
        
        textField_firstName = new JTextField();
        textField_firstName.setBounds(85, 4, 150, 26);
        panel_add_employee.add(textField_firstName);
        textField_firstName.setColumns(10);
        
        textField_lastName = new JTextField();
        textField_lastName.setBounds(85, 34, 150, 26);
        panel_add_employee.add(textField_lastName);
        textField_lastName.setColumns(10);
        
        textField_street = new JTextField();
        textField_street.setBounds(55, 66, 200, 26);
        panel_add_employee.add(textField_street);
        textField_street.setColumns(10);
        
        textField_city = new JTextField();
        textField_city.setBounds(45, 98, 150, 26);
        panel_add_employee.add(textField_city);
        textField_city.setColumns(10);
        
        textField_province = new JTextField();
        textField_province.setBounds(110, 130, 150, 26);
        panel_add_employee.add(textField_province);
        textField_province.setColumns(10);
        
        textField_postalCode = new JTextField();
        textField_postalCode.setBounds(93, 162, 150, 26);
        panel_add_employee.add(textField_postalCode);
        textField_postalCode.setColumns(10);
        
        textField_homePhone = new JTextField();
        textField_homePhone.setBounds(100, 193, 150, 26);
        panel_add_employee.add(textField_homePhone);
        textField_homePhone.setColumns(10);
        
        textField_cellPhone = new JTextField();
        textField_cellPhone.setBounds(85, 226, 150, 26);
        panel_add_employee.add(textField_cellPhone);
        textField_cellPhone.setColumns(10);
        
        textField_email = new JTextField();
        textField_email.setBounds(53, 258, 150, 26);
        panel_add_employee.add(textField_email);
        textField_email.setColumns(10);
        
        textField_positionID = new JTextField();
        textField_positionID.setBounds(90, 290, 150, 26);
        panel_add_employee.add(textField_positionID);
        textField_positionID.setColumns(10);
        
        textField_jobType = new JTextField();
        textField_jobType.setBounds(73, 322, 150, 26);
        panel_add_employee.add(textField_jobType);
        textField_jobType.setColumns(10);
        
        textField_userName = new JTextField();
        textField_userName.setBounds(80, 352, 150, 26);
        panel_add_employee.add(textField_userName);
        textField_userName.setColumns(10);
        
        textField_password = new JPasswordField();
        textField_password.setBounds(78, 384, 150, 26);
        panel_add_employee.add(textField_password);
        textField_password.setColumns(10);
        
        textField_hireDate = new JTextField();
        textField_hireDate.setBounds(78, 418, 150, 26);
        panel_add_employee.add(textField_hireDate);
        textField_hireDate.setColumns(10);
        
        JPanel panel_staff_edit = new JPanel();
        tabbedPane.addTab("Edit", null, panel_staff_edit, null);
        
        JPanel panel_details = new JPanel();
        tabbedPane.addTab("Details", null, panel_details, null);
	}
	private void CreateWindow(){
        /*jPanel1 = new javax.swing.JPanel();
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
        */
    }
	
	public JPanel getWindow() {
		if(jPanel1 == null){
			CreateWindow();
		}
		
		return jPanel1;
	}
	public boolean fetchLogin(String userID, String pass){
		//Execute a query
		Connect connect = new Connect();
		Connection con = null;
		int count = 0;
		boolean check = false;
		
		try{
			con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			/*
			Avoid SQL Injection: Use prepared statements (JDBC Library)
			1.) Put "?" instead of variable/value.
			2.) Pass it to prepare statement
			3.) Set values to "?" by using setString(parameter#,value) 
			4.) Execute the query using prepared statement, not String sql.
			*/
			String sql = "SELECT * FROM Employee where UserName = ? AND TerminationDate IS NULL";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
		    
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
			  	this.password = rs.getString("Password");
			  	this.hireDate = rs.getString("HireDate");
			  	this.terminationDate = rs.getString("TerminationDate");
			  	this.salt = rs.getString("Salt");
			  	count++;
		    }
		    //Clean-up environment
		    rs.close();
		    con.close();
		}catch(Exception e){}
		if(count == 1){
			Hashing hash = new Hashing();
			String hashString = hash.get_SHA_512_SecurePassword(pass, this.salt);
			if(hashString.equals(this.password)){
				check = true;
		       }
		    else{
		    	check = false;
		    }
	    }
		return check;
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
		public void setPassword(String password){ this.password = password; }
		public void setHireDate(String hireDate){ this.hireDate = hireDate; }
		public void setTerminationDate(String terminationDate){ this.terminationDate = terminationDate; }
		public void setSalt(){ this.salt = salt; }
		
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
		public String getPassword(){ return this.password; }
		public String getHireDate(){ return this.hireDate; }
		public String getTerminationDate(){ return this.terminationDate; }
		public String salt(){ return this.salt; }
}
