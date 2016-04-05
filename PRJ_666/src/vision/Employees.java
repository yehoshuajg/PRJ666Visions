package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import java.sql.*;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Vector;

import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class Employees extends JFrame{
	//Runtime.getRuntime().exec("osk"); //bring up the keyboard in windows
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
	
	//Error message fields
	private JTextPane textPane_error_firstName;
	private JTextPane textPane_error_lastName;
	private JTextPane textPane_error_street;
	private JTextPane textPane_error_city;
	private JTextPane textPane_error_stateProvince;
	private JTextPane textPane_error_postalCode;
	private JTextPane textPane_error_homePhone;
	private JTextPane textPane_error_cellPhone;
	private JTextPane textPane_error_email;
	private JTextPane textPane_error_positionID;
	private JTextPane textPane_error_jobType;
	private JTextPane textPane_error_username;
	private JTextPane textPane_error_password;
	private JTextPane textPane_error_hireDate;
	
	//Error message fields - details/edit
	private JTextPane textPane_error_details_firstName;
	
	//List of employees
	private Vector<String> columnName = new Vector<String>();
	private Vector<String> data = new Vector<String>();
	JTable employeeTable;
	DefaultTableModel employeeModel;
	Vector<Employees> staffList = new Vector<Employees>();
	
	//Frame for details
	private JDialog d1;
	
	//Frame for changing current password
	private JDialog d2;
	private HashMap resetUserEmail = new HashMap<>();
	private String tempResetUserEmail;
	
	//Date
	String dateDelimiter = "/";
	
	//Current logged in employee
	Employees currentEmployee;
	private JPasswordField textField_input_currentPassword;
	private JPasswordField textField_input_newPassword;
	private JPasswordField textField_input_confirmNewPassword;
	private JTextPane textField_error_newPassword;
	private JTextPane textField_error_confirmNewPassword;
	
	//Job Type
	private String[] jobtypes = {"Full Time", "Part Time","Casual"};
	private JComboBox comboBox_jobType;
	
	//Position ID
	private String[] positionids = {"1 - Sales Associate", "2 - Cashier", "3 - Manager", "4 - Owner", "5 - Accountant"};
	private JComboBox comboBox_positionID;
	
	//State/Province
	private String[] provinces = {"Alberta","British Columbia","Manitoba","New Brunswick","Newfoundland and Labrador",
			"Northwest Territories","Nova Scotia","Nunavut","Ontario","Prince Edward Island","Quebec","Saskatchewan","Yukon Territory"};
	private JComboBox comboBox_province;
	
	//Checkbox for termination
	//private JCheckBox terminateBox;
	private JCheckBox terminateCheckbox;
	
	JButton btnClear;
	
	public Employees() {
		//Removed connect connection, create an instance of connect class and use the getters to grab the url, user, name
		//Close connection when done using it. Add try/catch rather than throw exception in function prototype.
		//Look at fetchLogin() to get an idea.
		super();
		jPanel1 = new JPanel();
        jPanel1 = (JPanel) getContentPane();
        jPanel1.setBounds(0, 0, 1280, 690);
        jPanel1.setLayout(new BorderLayout(0, 0));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(6, 6, 1250, 648);
        getContentPane().add(tabbedPane);
        
        JPanel panel_staff = new JPanel();
        tabbedPane.addTab("Staff List", null, panel_staff, null);
        panel_staff.setLayout(null);
        
        JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_1.setBounds(6, 6, 1217, 606);
        panel_staff.add(tabbedPane_1);
        
        JPanel panel_listOfEmployees = new JPanel();
        tabbedPane_1.addTab("List of employees:", null, panel_listOfEmployees, null);
        panel_listOfEmployees.setLayout(null);
        
        columnName.addElement("#");
		columnName.addElement("Name");
		columnName.addElement("Cell Phone");
		columnName.addElement("Email");
		columnName.addElement("Job Type");
		columnName.addElement("Username");
		columnName.addElement("Hire Date");
		
		employeeTable = new JTable(data, columnName){
			public boolean isCellEditable(int row, int column) {
				//Return true if the column (number) is editable, else false
		        return false;
		    }
		};
		panel_listOfEmployees.add(employeeTable.getTableHeader(), BorderLayout.NORTH);
		panel_listOfEmployees.add(employeeTable, BorderLayout.CENTER);
		
		//Row Height
		employeeTable.setRowHeight(30);
	  	
	    //Column Width
	  	TableColumnModel columnModel = employeeTable.getColumnModel();
	  	columnModel.getColumn(0).setPreferredWidth(1); //#
	 	columnModel.getColumn(1).setPreferredWidth(100); //Name
	  	columnModel.getColumn(2).setPreferredWidth(30); //Cell Phone
        columnModel.getColumn(3).setPreferredWidth(30); //Email
	  	columnModel.getColumn(4).setPreferredWidth(10); //Job Type
	  	columnModel.getColumn(5).setPreferredWidth(20); //Username
	  	columnModel.getColumn(6).setPreferredWidth(20); //Hire date

	    //Columns won't be able to moved around
	  	//employeeTable.getTableHeader().setReorderingAllowed(false);
	  	
	    //Center table data 
	  	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	  	centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	  	employeeTable.getColumnModel().getColumn(0).setCellRenderer( centerRenderer ); 
	  	employeeTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer ); 
	  	employeeTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer ); 
	  	employeeTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer ); 
	  	employeeTable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer ); 
	  	employeeTable.getColumnModel().getColumn(5).setCellRenderer( centerRenderer ); 
	  	employeeTable.getColumnModel().getColumn(6).setCellRenderer( centerRenderer ); 
	  	
	    //Center table column names
	  	centerRenderer = (DefaultTableCellRenderer) employeeTable.getTableHeader().getDefaultRenderer();
	  	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	  	
		JScrollPane jsp = new JScrollPane(employeeTable);
		jsp.setBounds(0, 0, 1196, 560);
		jsp.setVisible(true);
		panel_listOfEmployees.add(jsp);
	   
		employeeModel = (DefaultTableModel) employeeTable.getModel();
		
		getStaffList();
		if(staffList.size() > 0){
			for(int i = 0; i < staffList.size(); i++){
				employeeModel.addRow(new Object[]{staffList.get(i).getID(),staffList.get(i).getFirstName() + " " + staffList.get(i).getLastName(),staffList.get(i).getCellPhone(),staffList.get(i).getEmail(),staffList.get(i).getJobType(),staffList.get(i).getUsername(),staffList.get(i).getHireDate()});
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"No staff was found in the database.");
		}
		
		//Checking for double click
		employeeTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Positions position = new Positions();
				if(position.checkPosition(currentEmployee.getPositionID()) == false){
					JOptionPane.showMessageDialog(null,"This account does not have access to this panel. The program will now exit.");
					System.exit(0);
				}
				else{
				if (e.getClickCount() == 1) {
					//System.out.println("1");
				}
				if (e.getClickCount() == 2) {
					if(!employeeTable.isEditing()){
						int row = employeeTable.getSelectedRow();
						int column = employeeTable.getSelectedColumn();
						if(column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6){
							if(row > -1){
								if(position.checkPosition(currentEmployee.getPositionID()) == false){
									JOptionPane.showMessageDialog(null,"This account does not have access to this panel. The program will now exit.");
									System.exit(0);
								}
								else{
									String tDate = getDate();
									JPanel staffDetails = new JPanel();
									staffDetails.setLayout(null);
									
									JTabbedPane tabbedPane_details = new JTabbedPane(JTabbedPane.TOP);
							        tabbedPane_details.setBounds(85, 15, 740, 551);
							        staffDetails.add(tabbedPane_details);
							        
							        JPanel panel_employeeDetails = new JPanel();
							        tabbedPane_details.addTab("Employee details:", null, panel_employeeDetails, null);
							        panel_employeeDetails.setLayout(null);
							        
							        JTextPane txtpnId_details_id = new JTextPane();
							        txtpnId_details_id.setText("ID:");
							        txtpnId_details_id.setEditable(false);
							        txtpnId_details_id.setBounds(6, 10, 18, 20);
							        txtpnId_details_id.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(txtpnId_details_id);
							        
							        JTextPane txtpn_details_FirstName = new JTextPane();
							        txtpn_details_FirstName.setText("First Name:");
							        txtpn_details_FirstName.setEditable(false);
							        txtpn_details_FirstName.setBounds(6, 42, 72, 20);
							        txtpn_details_FirstName.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(txtpn_details_FirstName);
							        
							        JTextPane txtpn_details_lastName = new JTextPane();
							        txtpn_details_lastName.setText("Last Name:");
							        txtpn_details_lastName.setEditable(false);
							        txtpn_details_lastName.setBackground(Color.decode(defaultColor));
							        txtpn_details_lastName.setBounds(6, 74, 72, 20);
							        panel_employeeDetails.add(txtpn_details_lastName);
							        
							        JTextPane txtpn_details_Street = new JTextPane();
							        txtpn_details_Street.setText("Street:");
							        txtpn_details_Street.setEditable(false);
							        txtpn_details_Street.setBackground(Color.decode(defaultColor));
							        txtpn_details_Street.setBounds(6, 106, 40, 20);
							        panel_employeeDetails.add(txtpn_details_Street);
							        
							        JTextPane txtpn_details_City = new JTextPane();
							        txtpn_details_City.setText("City:");
							        txtpn_details_City.setEditable(false);
							        txtpn_details_City.setBackground(Color.decode(defaultColor));
							        txtpn_details_City.setBounds(6, 138, 29, 20);
							        panel_employeeDetails.add(txtpn_details_City);
							        
							        JTextPane txtpn_details_Stateprovince = new JTextPane();
							        txtpn_details_Stateprovince.setText("State/Province:");
							        txtpn_details_Stateprovince.setEditable(false);
							        txtpn_details_Stateprovince.setBackground(Color.decode(defaultColor));
							        txtpn_details_Stateprovince.setBounds(6, 170, 95, 20);
							        panel_employeeDetails.add(txtpn_details_Stateprovince);
							        
							        JTextPane txtpn_details_PostalCode = new JTextPane();
							        txtpn_details_PostalCode.setText("Postal Code:");
							        txtpn_details_PostalCode.setEditable(false);
							        txtpn_details_PostalCode.setBackground(Color.decode(defaultColor));
							        txtpn_details_PostalCode.setBounds(6, 202, 78, 20);
							        panel_employeeDetails.add(txtpn_details_PostalCode);
							        
							        JTextPane txtpn_details_HomePhone = new JTextPane();
							        txtpn_details_HomePhone.setText("Home Phone:");
							        txtpn_details_HomePhone.setEditable(false);
							        txtpn_details_HomePhone.setBackground(Color.decode(defaultColor));
							        txtpn_details_HomePhone.setBounds(6, 234, 83, 20);
							        panel_employeeDetails.add(txtpn_details_HomePhone);
							        
							        JTextPane txtpn_details_CellPhone = new JTextPane();
							        txtpn_details_CellPhone.setText("Cell Phone:");
							        txtpn_details_CellPhone.setEditable(false);
							        txtpn_details_CellPhone.setBackground(Color.decode(defaultColor));
							        txtpn_details_CellPhone.setBounds(6, 266, 72, 20);
							        panel_employeeDetails.add(txtpn_details_CellPhone);
							        
							        JTextPane txtpn_details_Email = new JTextPane();
							        txtpn_details_Email.setText("Email:");
							        txtpn_details_Email.setEditable(false);
							        txtpn_details_Email.setBackground(Color.decode(defaultColor));
							        txtpn_details_Email.setBounds(6, 298, 40, 20);
							        panel_employeeDetails.add(txtpn_details_Email);
							        
							        JTextPane txtpn_details_PositionId = new JTextPane();
							        txtpn_details_PositionId.setText("Position ID:");
							        txtpn_details_PositionId.setEditable(false);
							        txtpn_details_PositionId.setBackground(Color.decode(defaultColor));
							        txtpn_details_PositionId.setBounds(6, 330, 75, 20);
							        panel_employeeDetails.add(txtpn_details_PositionId);
							        
							        JTextPane txtpn_details_JobType = new JTextPane();
							        txtpn_details_JobType.setText("Job Type:");
							        txtpn_details_JobType.setEditable(false);
							        txtpn_details_JobType.setBackground(Color.decode(defaultColor));
							        txtpn_details_JobType.setBounds(6, 362, 58, 20);
							        panel_employeeDetails.add(txtpn_details_JobType);
							        
							        JTextPane txtpn_details_Username = new JTextPane();
							        txtpn_details_Username.setText("Username:");
							        txtpn_details_Username.setEditable(false);
							        txtpn_details_Username.setBackground(Color.decode(defaultColor));
							        txtpn_details_Username.setBounds(6, 394, 70, 20);
							        panel_employeeDetails.add(txtpn_details_Username);
							        
							        JTextPane txtpn_details_HireDate = new JTextPane();
							        txtpn_details_HireDate.setText("Hire Date:");
							        txtpn_details_HireDate.setEditable(false);
							        txtpn_details_HireDate.setBackground(Color.decode(defaultColor));
							        txtpn_details_HireDate.setBounds(6, 426, 70, 20);
							        panel_employeeDetails.add(txtpn_details_HireDate);
							        
							        JTextPane txtpn_details_TerminateDate = new JTextPane();
							        txtpn_details_TerminateDate.setText("Terminate Date:");
							        txtpn_details_TerminateDate.setEditable(false);
							        txtpn_details_TerminateDate.setBackground(Color.decode(defaultColor));
							        txtpn_details_TerminateDate.setBounds(6, 455, 100, 20);
							        panel_employeeDetails.add(txtpn_details_TerminateDate);
							        
							        JTextField textPane_details_id = new JTextField();
							        textPane_details_id.setBounds(30, 10, 150, 20);
							        textPane_details_id.setEditable(false);
							        textPane_details_id.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_id);
							        
							        JTextField textPane_details_firstName = new JTextField();
							        textPane_details_firstName.setBounds(90, 42, 150, 20);
							        textPane_details_firstName.setEditable(false);
							        textPane_details_firstName.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_firstName);
							        
							        JTextField textPane_details_lastName = new JTextField();
							        textPane_details_lastName.setBounds(90, 74, 150, 20);
							        textPane_details_lastName.setEditable(false);
							        textPane_details_lastName.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_lastName);
							        
							        JTextField textPane_details_street = new JTextField();
							        textPane_details_street.setBounds(58, 106, 150, 20);
							        textPane_details_street.setEditable(false);
							        textPane_details_street.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_street);
							        
							        JTextField textPane_details_city = new JTextField();
							        textPane_details_city.setBounds(47, 138, 150, 20);
							        textPane_details_city.setEditable(false);
							        textPane_details_city.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_city);
							       
							        JComboBox comboBox_detail_province = new JComboBox(provinces);
							        comboBox_detail_province.setBounds(113, 170, 150, 20);
							        panel_employeeDetails.add(comboBox_detail_province);
							        comboBox_detail_province.setEnabled(false);
							        
							        JTextField textPane_details_postalCode = new JTextField();
							        textPane_details_postalCode.setBounds(96, 201, 150, 20);
							        textPane_details_postalCode.setEditable(false);
							        textPane_details_postalCode.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_postalCode);
							        
							        JTextField textPane_details_homePhone = new JTextField();
							        textPane_details_homePhone.setBounds(100, 234, 150, 20);
							        textPane_details_homePhone.setEditable(false);
							        textPane_details_homePhone.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_homePhone);
							        
							        JTextField textPane_details_cellPhone = new JTextField();
							        textPane_details_cellPhone.setBounds(90, 266, 150, 20);
							        textPane_details_cellPhone.setEditable(false);
							        textPane_details_cellPhone.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_cellPhone);
							        
							        JTextField textPane_details_email = new JTextField();
							        textPane_details_email.setBounds(58, 298, 150, 20);
							        textPane_details_email.setEditable(false);
							        textPane_details_email.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_email);
							        
							        JComboBox comboBox_detail_positions = new JComboBox(positionids);
							        comboBox_detail_positions.setBounds(90, 330, 150, 20);
							        panel_employeeDetails.add(comboBox_detail_positions);
							        comboBox_detail_positions.setEnabled(false);
							        
							        JComboBox comboBox_detail_jobtypes = new JComboBox(jobtypes);
							        comboBox_detail_jobtypes.setBounds(76, 362, 150, 20);
							        panel_employeeDetails.add(comboBox_detail_jobtypes);
							        comboBox_detail_jobtypes.setEnabled(false);
							        
							        JTextField textPane_details_username = new JTextField();
							        textPane_details_username.setBounds(90, 394, 150, 20);
							        textPane_details_username.setEditable(false);
							        textPane_details_username.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_username);
							        
							        JTextField textPane_details_hireDate = new JTextField();
							        textPane_details_hireDate.setBounds(90, 426, 150, 20);
							        textPane_details_hireDate.setEditable(false);
							        textPane_details_hireDate.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_hireDate);
							       
							        JTextField textPane_details_terminateDate = new JTextField();
							        textPane_details_terminateDate.setBounds(140, 455, 160, 20);
							        textPane_details_terminateDate.setEditable(false);
							        textPane_details_terminateDate.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_details_terminateDate);
							        
							        terminateCheckbox = new JCheckBox();
							        terminateCheckbox.setBounds(110, 455, 160, 20);
							        terminateCheckbox.setEnabled(false);
							        panel_employeeDetails.add(terminateCheckbox);
							        
							        if(currentEmployee.getPositionID() == 5){
							        	terminateCheckbox.setVisible(false);
							        }
							        else if(currentEmployee.getPositionID() == 4 || currentEmployee.getPositionID() == 3){
							        	terminateCheckbox.setVisible(true);
							        	terminateCheckbox.setEnabled(false);
							        }
							        
							        //Error message fields
							        JTextPane textPane_edit_error_firstName = new JTextPane();
							        textPane_edit_error_firstName.setEditable(false);
							        textPane_edit_error_firstName.setBounds(252, 42, 461, 20);
							        textPane_edit_error_firstName.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_firstName);
							        
							        JTextPane textPane_edit_error_lastName = new JTextPane();
							        textPane_edit_error_lastName.setEditable(false);
							        textPane_edit_error_lastName.setBounds(252, 74, 461, 20);
							        textPane_edit_error_lastName.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_lastName);
							        
							        JTextPane textPane_edit_error_street = new JTextPane();
							        textPane_edit_error_street.setEditable(false);
							        textPane_edit_error_street.setBounds(220, 106, 493, 20);
							        textPane_edit_error_street.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_street);
							        
							        JTextPane textPane_edit_error_city = new JTextPane();
							        textPane_edit_error_city.setEditable(false);
							        textPane_edit_error_city.setBounds(209, 138, 504, 20);
							        textPane_edit_error_city.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_city);
							        
							        JTextPane textPane_edit_error_stateProvince = new JTextPane();
							        textPane_edit_error_stateProvince.setEditable(false);
							        textPane_edit_error_stateProvince.setBounds(275, 170, 438, 20);
							        textPane_edit_error_stateProvince.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_stateProvince);
							        
							        JTextPane textPane_edit_error_postalCode = new JTextPane();
							        textPane_edit_error_postalCode.setEditable(false);
							        textPane_edit_error_postalCode.setBounds(258, 202, 455, 20);
							        textPane_edit_error_postalCode.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_postalCode);
							        
							        JTextPane textPane_edit_error_homePhone = new JTextPane();
							        textPane_edit_error_homePhone.setEditable(false);
							        textPane_edit_error_homePhone.setBounds(262, 234, 451, 20);
							        textPane_edit_error_homePhone.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_homePhone);
							        
							        JTextPane textPane_error_edit_cellPhone = new JTextPane();
							        textPane_error_edit_cellPhone.setEditable(false);
							        textPane_error_edit_cellPhone.setBounds(252, 266, 461, 20);
							        textPane_error_edit_cellPhone.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_error_edit_cellPhone);
							        
							        JTextPane textPane_edit_error_email = new JTextPane();
							        textPane_edit_error_email.setEditable(false);
							        textPane_edit_error_email.setBounds(220, 298, 493, 20);
							        textPane_edit_error_email.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_email);
							        
							        JTextPane textPane_edit_error_positionID = new JTextPane();
							        textPane_edit_error_positionID.setEditable(false);
							        textPane_edit_error_positionID.setBounds(252, 330, 461, 20);
							        textPane_edit_error_positionID.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_edit_error_positionID);
							        
							        JTextPane textPane_error_edit_jobType = new JTextPane();
							        textPane_error_edit_jobType.setEditable(false);
							        textPane_error_edit_jobType.setBounds(238, 362, 475, 20);
							        textPane_error_edit_jobType.setBackground(Color.decode(defaultColor));
							        panel_employeeDetails.add(textPane_error_edit_jobType);
							        
							        //Edit button
							        JButton btn_edit = new JButton("Edit");
							        if(currentEmployee.getPositionID() == 5){
							        	btn_edit.setVisible(false);
							        	btn_edit.setEnabled(false);
							        }
							        
							        //Save button initialize
							        JButton btn_save = new JButton("Save");
							        
							        btn_edit.setBounds(300, 570, 150, 40);
							        btn_edit.setFont(new Font("Tahoma", Font.PLAIN, 20));
							        btn_edit.setFocusable(false);
									staffDetails.add(btn_edit);
									//Edit button
									btn_edit.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											//First name
											textPane_details_firstName.setBackground(null);
											textPane_details_firstName.setEditable(true);
											
											//Last name
											textPane_details_lastName.setBackground(null);
											textPane_details_lastName.setEditable(true);
											
											//Street
											textPane_details_street.setBackground(null);
											textPane_details_street.setEditable(true);
											
											//City
											textPane_details_city.setBackground(null);
											textPane_details_city.setEditable(true);
											
											//State/Province
											comboBox_detail_province.setEnabled(true);
											
											//Postal code
											textPane_details_postalCode.setBackground(null);
											textPane_details_postalCode.setEditable(true);
												
											//Home phone
											textPane_details_homePhone.setBackground(null);
											textPane_details_homePhone.setEditable(true);
											
											//Cell phone
											textPane_details_cellPhone.setBackground(null);
											textPane_details_cellPhone.setEditable(true);
											
											//Email
											textPane_details_email.setBackground(null);
											textPane_details_email.setEditable(true);
												
											if(currentEmployee.getPositionID() == 4){
												//Position ID
												//textPane_positionID.setBackground(null);
												//comboBox_detail_positions.setEditable(true);
												comboBox_detail_positions.setEnabled(true);
											
												//Job Type
												//textPane_details_JobType.setBackground(null);
												comboBox_detail_jobtypes.setEnabled(true);
											}
											//Manager and Cashier <=
											else if(currentEmployee.getPositionID() == 3 && (comboBox_detail_positions.getSelectedIndex()+1) <= 2){
							        			comboBox_detail_jobtypes.setEnabled(true);
							        		}
											
											if(textPane_details_terminateDate.getText().isEmpty()){
												// Position ID - 4 == 4
												if(currentEmployee.getPositionID() == 4 && (comboBox_detail_positions.getSelectedIndex()+1) == 4){
													textPane_details_terminateDate.setBackground(null);
													//ID - 1 = 1
													if(currentEmployee.getID() == Integer.parseInt(textPane_details_id.getText())){
														terminateCheckbox.setEnabled(true);
													}
													else{
														terminateCheckbox.setEnabled(false);
													}
								        			textPane_details_terminateDate.setEditable(false);
								        		}
												//Position ID 4 <= 3
												else if(currentEmployee.getPositionID() == 4 && (comboBox_detail_positions.getSelectedIndex()+1) <= 3){
													textPane_details_terminateDate.setBackground(null);
													textPane_details_terminateDate.setEditable(false);
													terminateCheckbox.setEnabled(true);
												}
												else if(currentEmployee.getPositionID() == 4 && (comboBox_detail_positions.getSelectedIndex()+1) == 5){
													textPane_details_terminateDate.setBackground(null);
													textPane_details_terminateDate.setEditable(false);
													terminateCheckbox.setEnabled(true);
												}
												
												// Position ID - 3 >= 4
												if(currentEmployee.getPositionID() == 3 && (comboBox_detail_positions.getSelectedIndex()+1) >= 4){
													textPane_details_terminateDate.setBackground(null);
													textPane_details_terminateDate.setEditable(false);
													terminateCheckbox.setEnabled(false);
												}
												//Position ID - 3 <= 2
												else if(currentEmployee.getPositionID() == 3 && (comboBox_detail_positions.getSelectedIndex()+1) <= 2){
													textPane_details_terminateDate.setBackground(null);
													textPane_details_terminateDate.setEditable(false);
													terminateCheckbox.setEnabled(true);
												}
												else if(currentEmployee.getPositionID() == 3 && (comboBox_detail_positions.getSelectedIndex()+1) == 3){
													textPane_details_terminateDate.setBackground(null);
													textPane_details_terminateDate.setEditable(false);
													//ID - 1 = 1
													if(currentEmployee.getID() == Integer.parseInt(textPane_details_id.getText())){
														terminateCheckbox.setEnabled(true);
													}
													else{
														terminateCheckbox.setEnabled(false);
													}
												}
												
								        		terminateCheckbox.addActionListener(new ActionListener() {
													@Override
													public void actionPerformed(ActionEvent e) {
														AbstractButton abstractButton = (AbstractButton) e.getSource();
												        boolean selected = abstractButton.getModel().isSelected();
												        if(selected){
												        	textPane_details_terminateDate.setText(parseDate(tDate));
												        }
												        else{
												        	textPane_details_terminateDate.setText("");
												        }
													}
												});
											}
											else{
												terminateCheckbox.setEnabled(false);
											}
							        		btn_edit.setVisible(false);
											btn_save.setVisible(true);
										}
									});
									
							        //Save button
							        btn_save.setBounds(300, 570, 150, 40);
							        btn_save.setFont(new Font("Tahoma", Font.PLAIN, 20));
							        btn_save.setFocusable(false);
							        btn_save.setVisible(false);
									staffDetails.add(btn_save);
									//Save button 
									btn_save.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											//Error checking
											boolean check = true;
											boolean ok = true;
											//First name
											if(validateEmpty(textPane_details_firstName.getText()) == false){
												textPane_edit_error_firstName.setText("First name cannot be Empty.");
												check = false;
											}
											else if(checkForAlphabets(textPane_details_firstName.getText()) == false){
												textPane_edit_error_firstName.setText("First name must contain alphabets only.");
												ok = false;
											}
											else{
												textPane_edit_error_firstName.setText(null);
											}
											
											//Last name
											if(validateEmpty(textPane_details_lastName.getText()) == false){
												textPane_edit_error_lastName.setText("Last name cannot be Empty.");
												check = false;
											}
											else if(checkForAlphabets(textPane_details_lastName.getText()) == false){
												textPane_edit_error_lastName.setText("Last name must contain alphabets only.");
												ok = false;
											}
											else{
												textPane_edit_error_lastName.setText(null);
											}
											
											//Street
											if(validateEmpty(textPane_details_street.getText()) == false){
												textPane_edit_error_street.setText("Street field cannot be Empty.");
												check = false;
											}
											else{
												if(textPane_details_street.getText().trim().matches("^['#A-Za-z0-9 ]*$")){
													textPane_edit_error_street.setText(null);
												}
												else{
													textPane_edit_error_street.setText("Street can only contain alphabets, numbers, '#', '''.");
													ok = false;
												}
											}
											
											//City
											if(validateEmpty(textPane_details_city.getText()) == false){
												textPane_edit_error_city.setText("City field cannot be Empty.");
												check = false;
											}
											else if(checkForAlphabets(textPane_details_city.getText()) == false){
												textPane_edit_error_city.setText("City field must contain alphabets only.");
												ok = false;
											}
											else{
												textPane_edit_error_city.setText(null);
											}
											
											//Home phone
											if(validateEmpty(textPane_details_homePhone.getText()) == false){
												textPane_edit_error_homePhone.setText("Home phone cannot be Empty.");
												check = false;
											}
											else{
												if(textPane_details_homePhone.getText().trim().matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$")){ //999-999-9999
													textPane_edit_error_homePhone.setText(null);
												}
												else if(textPane_details_homePhone.getText().trim().matches("^([0-9]{3})[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //999 999 9999 
													textPane_edit_error_homePhone.setText(null);
												}
												else if(textPane_details_homePhone.getText().trim().matches("^([0-9]{10})$")){ //9999999999 
													textPane_edit_error_homePhone.setText(null);
												}
												else if(textPane_details_homePhone.getText().trim().matches("^[(]{1}([0-9]{3})[)]{1}[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //(999) 999 9999
													textPane_edit_error_homePhone.setText(null);
												}
												else{
													textPane_edit_error_homePhone.setText("Home phone does not have a valid phone pattern.");
													check = false;
												}
											}
											
											//Cell phone
											if(validateEmpty(textPane_details_cellPhone.getText()) == false){
												textPane_error_edit_cellPhone.setText("Cell phone cannot be Empty.");
												check = false;
											}
											else{
												if(textPane_details_cellPhone.getText().trim().matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$")){ //999-999-9999
													textPane_error_edit_cellPhone.setText(null);
												}
												else if(textPane_details_cellPhone.getText().trim().matches("^([0-9]{3})[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //999 999 9999 
													textPane_error_edit_cellPhone.setText(null);
												}
												else if(textPane_details_cellPhone.getText().trim().matches("^([0-9]{10})$")){ //9999999999 
													textPane_error_edit_cellPhone.setText(null);
												}
												else if(textPane_details_cellPhone.getText().trim().matches("^[(]{1}([0-9]{3})[)]{1}[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //(999) 999 9999
													textPane_error_edit_cellPhone.setText(null);
												}
												else{
													textPane_error_edit_cellPhone.setText("Cell phone does not have a valid phone pattern.");
													ok = false;
												}
											}
											
											//Email
											if(validateEmpty(textPane_details_email.getText()) == false){
												textPane_edit_error_email.setText("Email cannot be Empty.");
												check = false;
											}
											else{
												if(textPane_details_email.getText().trim().matches("^[A-Za-z0-9+_.-]+@(.+)$")){
													textPane_edit_error_email.setText(null);
												}
												else{
													textPane_edit_error_email.setText("Email can only contain alphabets, numbers, '+', '_', '.', '-'.");
													ok = false;
												}
											}
											if(position.checkPosition(currentEmployee.getPositionID()) == false){
												JOptionPane.showMessageDialog(null,"This account does not have access to this panel. The program will now exit.");
												System.exit(0);
											}
											else{
												if(check && ok){
													boolean go = false;
													do{
														JPanel panel = new JPanel();
														JLabel label = new JLabel("Enter a password:");
														JPasswordField pass = new JPasswordField(10);
														panel.add(label);
														panel.add(pass);
														String[] options = new String[]{"OK", "Cancel"};
														int option = JOptionPane.showOptionDialog(null, panel, "Confirm:",
														                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
														                         null, options, options[0]);
														if(option == 0) // pressing OK button
														{
														    char[] password = pass.getPassword();
														    String passwordInput = new String(password);
														    
														    Employees tempEmployee = new Employees(currentEmployee.getUsername());
															if(tempEmployee.fetchLogin(currentEmployee.getUsername(), passwordInput)){
																//First name
																textPane_details_firstName.setBackground(Color.decode(defaultColor));
																textPane_details_firstName.setEditable(false);
																
																//Last name
																textPane_details_lastName.setBackground(Color.decode(defaultColor));
																textPane_details_lastName.setEditable(false);
																
																//Street
																textPane_details_street.setBackground(Color.decode(defaultColor));
																textPane_details_street.setEditable(false);
																
																//City
																textPane_details_city.setBackground(Color.decode(defaultColor));
																textPane_details_city.setEditable(false);
						
																//State/Province
																//textPane_details_stateProvince.setBackground(Color.decode(defaultColor));
																comboBox_detail_province.setEnabled(false);
																
																//Postal code
																textPane_details_postalCode.setBackground(Color.decode(defaultColor));
																textPane_details_postalCode.setEditable(false);
																
																//Home phone
																textPane_details_homePhone.setBackground(Color.decode(defaultColor));
																textPane_details_homePhone.setEditable(false);
																
																//Cell phone
																textPane_details_cellPhone.setBackground(Color.decode(defaultColor));
																textPane_details_cellPhone.setEditable(false);
																
																//Email
																textPane_details_email.setBackground(Color.decode(defaultColor));
																textPane_details_email.setEditable(false);
																
																if(currentEmployee.getPositionID() == 4){
																	//Position ID
																	//textPane_positionID.setBackground(Color.decode(defaultColor));
																	comboBox_detail_positions.setEnabled(false);
																}
																//Job Type
																//textPane_details_JobType.setBackground(Color.decode(defaultColor));
																comboBox_detail_jobtypes.setEnabled(false);
																
																//Username
																//textPane_details_username.setBackground(Color.decode(defaultColor));
																//textPane_details_username.setEditable(false);
																
																btn_save.setVisible(false);
																btn_edit.setVisible(true);
																
																//Write to DB
																Connect connect = new Connect();
																try {
																	Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
																	
																	if(terminateCheckbox.isSelected() == false){
																		String sql = "UPDATE `Employee` SET FirstName = ?, LastName = ?, Street = ?, City = ?, State_Province = ?, PostalCode = ?, HomePhone = ?, CellPhone = ?, Email = ?, PositionID = ?, JobType = ?, AlteredBy = ? where ID = ?";
																		PreparedStatement ps = con.prepareStatement(sql);
																		ps.setString(1, textPane_details_firstName.getText().trim());
																		ps.setString(2, textPane_details_lastName.getText().trim());
																		ps.setString(3, textPane_details_street.getText().trim());
																		ps.setString(4, textPane_details_city.getText().trim());
																		ps.setString(5, provinces[comboBox_detail_province.getSelectedIndex()]);
																		ps.setString(6, textPane_details_postalCode.getText().trim());
																		ps.setString(7, textPane_details_homePhone.getText().trim());
																		ps.setString(8, textPane_details_cellPhone.getText().trim());
																		ps.setString(9, textPane_details_email.getText().trim());
																		ps.setInt(10, (comboBox_detail_positions.getSelectedIndex()+1));
																		ps.setString(11, jobtypes[comboBox_detail_jobtypes.getSelectedIndex()]);
																		ps.setString(12, currentEmployee.getUsername().trim());
																		ps.setString(13, textPane_details_id.getText().trim());
																		ps.executeUpdate();
																	}
																	
																	else if(terminateCheckbox.isSelected() == true){
																		String sql = "UPDATE `Employee` SET FirstName = ?, LastName = ?, Street = ?, City = ?, State_Province = ?, PostalCode = ?, HomePhone = ?, CellPhone = ?, Email = ?, PositionID = ?, JobType = ?, TerminationDate = ?, AlteredBy = ?, TerminatedBy = ? where ID = ?";
																		PreparedStatement ps = con.prepareStatement(sql);
																		ps.setString(1, textPane_details_firstName.getText().trim());
																		ps.setString(2, textPane_details_lastName.getText().trim());
																		ps.setString(3, textPane_details_street.getText().trim());
																		ps.setString(4, textPane_details_city.getText().trim());
																		ps.setString(5, provinces[comboBox_detail_province.getSelectedIndex()]);
																		ps.setString(6, textPane_details_postalCode.getText().trim());
																		ps.setString(7, textPane_details_homePhone.getText().trim());
																		ps.setString(8, textPane_details_cellPhone.getText().trim());
																		ps.setString(9, textPane_details_email.getText().trim());
																		ps.setInt(10, (comboBox_detail_positions.getSelectedIndex()+1));
																		ps.setString(11, jobtypes[comboBox_detail_jobtypes.getSelectedIndex()]);
																		ps.setString(12, tDate); // Termination date
																		ps.setString(13, currentEmployee.getUsername().trim());
																		ps.setString(14, currentEmployee.getUsername().trim());
																		ps.setString(15, textPane_details_id.getText().trim());
																	    ps.executeUpdate();
																	    terminateCheckbox.setEnabled(false);
																	}
																	
																	//Clean-up environment
																	con.close();
																} catch (SQLException e2) {
																	// TODO Auto-generated catch block
																	e2.printStackTrace();
																}
																
																//removing vector elements
																if(staffList != null){
																	staffList.clear();
																}
																
																//remove previous rows
																for (int i = employeeModel.getRowCount()-1; i >= 0; --i) {
																	employeeModel.removeRow(i);
																}
																//Refreshing main staff table and getting new values
																getStaffList();
																if(staffList.size() > 0){
																	for(int i = 0; i < staffList.size(); i++){
																		employeeModel.addRow(new Object[]{staffList.get(i).getID(),staffList.get(i).getFirstName() + " " + staffList.get(i).getLastName(),staffList.get(i).getCellPhone(),staffList.get(i).getEmail(),staffList.get(i).getJobType(),staffList.get(i).getUsername(),staffList.get(i).getHireDate()});
																	}
																}
																else{
																	JOptionPane.showMessageDialog(null,"No staff was found in the database.");
																}
																//Update current employee
																updateCurrentEmployee();
																
																if(terminateCheckbox.isSelected() && !textPane_details_terminateDate.getText().isEmpty() && 
																	currentEmployee.getID() == Integer.parseInt(textPane_details_id.getText())){
																	JOptionPane.showMessageDialog(null,"This account no longer has access to this panel. The program will now exit.");
																	System.exit(0);
																}
																go = true;
															}
															else{
																JOptionPane.showMessageDialog(null,"Invalid Password.","Error",JOptionPane.ERROR_MESSAGE);
															}
														}
														else{
															go = true;
														}
													}while(go == false);
													if(position.checkPosition(currentEmployee.getPositionID()) == false){
														JOptionPane.showMessageDialog(null,"This account no longer has access to this panel. The program will now exit.");
														System.exit(0);
													}
												}
											}
										}
									});
							        
									//Print button
							        /*JButton btn_print = new JButton("Print");
							        btn_print.setBounds(485, 570, 150, 40);
							        btn_print.setFont(new Font("Tahoma", Font.PLAIN, 20));
							        btn_print.setFocusable(false);
									staffDetails.add(btn_print);
									btn_print.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
												JOptionPane.showMessageDialog(null,"Please make sure the fields are not in edit mode.");
											}
										}
									});
							        */
									
									//Cancel button
							        JButton btn_cancel = new JButton("Cancel");
							        btn_cancel.setBounds(670, 570, 150, 40);
							        btn_cancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
							        btn_cancel.setFocusable(false);
									staffDetails.add(btn_cancel);
									btn_cancel.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
												//JOptionPane.showMessageDialog(null,"Edit mode is enabled. Are you sure you want to cancel?");
												Object[] options = {"Yes","No"};
												int selection = JOptionPane.showOptionDialog(null,"Edit mode is enabled. Are you sure you want to cancel?","Warning",
													JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
													
												if(selection == JOptionPane.YES_OPTION){
													d1.dispose();
												}
												else if(selection == JOptionPane.NO_OPTION){
													
												}
											}
											else{
												d1.dispose();
											}
										}
									});
									
									if(currentEmployee != null){
										int tempID = (Integer) employeeModel.getValueAt(row, 0);
										String tempUsername = (String) employeeModel.getValueAt(row,5);
										for(int i = 0; i < staffList.size(); i++){
											//Checking if user double clicked / being viewed is in vector, to get more details
											if(tempID == staffList.get(i).getID() && tempUsername.equals(staffList.get(i).getUsername())){
												//Check if the current user is the one being viewed
												if(currentEmployee.getID() == staffList.get(i).getID() && currentEmployee.getUsername().equals(staffList.get(i).getUsername())){
													//Check if the user is a manager, cashier, etc. Only managers (higher employee's) are allowed to change passwords.
													//Cashiers must change password using/requesting a manager account.
													//Positions position = new Positions();
													if(position.checkPosition(staffList.get(i).getPositionID()) == true){
														//Change Password button
												        JButton btn_chgpwd = new JButton("Change Password");
												        btn_chgpwd.setBounds(90, 570, 200, 40);
												        btn_chgpwd.setFont(new Font("Tahoma", Font.PLAIN, 20));
												        btn_chgpwd.setFocusable(false);
														staffDetails.add(btn_chgpwd);
														int tID = staffList.get(i).getID();
														String tUserName = staffList.get(i).getUsername();
														btn_chgpwd.addActionListener(new ActionListener() {
															@Override
															public void actionPerformed(ActionEvent e) {
																if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
																	JOptionPane.showMessageDialog(null,"Please make sure the fields are not in edit mode.");
																}
																else{
																	loadPasswordFrame(e,tID,tUserName);
																}
															}
														});
													}
												}
												else{
													//Check if the manager account is allowed to edit only cashier, etc positions.
													//Not higher positions, such as other managers.
													//Positions position = new Positions();
													if(position.checkPosition(staffList.get(i).getPositionID()) == false){
														if(currentEmployee.getPositionID() == 5){
															btn_edit.setVisible(false);
															//JOptionPane.showMessageDialog(null,"You are not authorized to alter employee information.","Error",JOptionPane.ERROR_MESSAGE);
														}
														if(currentEmployee.getPositionID() != 5){
															//Change Password button
													        JButton btn_chgpwd = new JButton("Reset Password");
													        btn_chgpwd.setBounds(90, 570, 200, 40);
													        btn_chgpwd.setFont(new Font("Tahoma", Font.PLAIN, 20));
													        btn_chgpwd.setFocusable(false);
															staffDetails.add(btn_chgpwd);
															int tID = staffList.get(i).getID();
															String tUserName = staffList.get(i).getUsername();
															btn_chgpwd.addActionListener(new ActionListener() {
																@Override
																public void actionPerformed(ActionEvent e) {
																	if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
																		JOptionPane.showMessageDialog(null,"Please make sure the fields are not in edit mode.");
																	}
																	else{
																		loadResetPasswordFrame(e,tID,tUserName);
																	}
																}
															});
														}
													}
													else{
														//Owners and Managers
														if(currentEmployee.getPositionID() == 4 && staffList.get(i).getPositionID() == 3){
															//Change Password button
													        JButton btn_chgpwd = new JButton("Reset Password");
													        btn_chgpwd.setBounds(90, 570, 200, 40);
													        btn_chgpwd.setFont(new Font("Tahoma", Font.PLAIN, 20));
													        btn_chgpwd.setFocusable(false);
															staffDetails.add(btn_chgpwd);
															int tID = staffList.get(i).getID();
															String tUserName = staffList.get(i).getUsername();
															btn_chgpwd.addActionListener(new ActionListener() {
																@Override
																public void actionPerformed(ActionEvent e) {
																	if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
																		JOptionPane.showMessageDialog(null,"Please make sure the fields are not in edit mode.");
																	}
																	else{
																		loadResetPasswordFrame(e,tID,tUserName);
																	}
																}
															});
														}
														//Manager and accountant
														if(currentEmployee.getPositionID() == 4 && staffList.get(i).getPositionID() == 5){
															//Change Password button
													        JButton btn_chgpwd = new JButton("Reset Password");
													        btn_chgpwd.setBounds(90, 570, 200, 40);
													        btn_chgpwd.setFont(new Font("Tahoma", Font.PLAIN, 20));
													        btn_chgpwd.setFocusable(false);
															staffDetails.add(btn_chgpwd);
															int tID = staffList.get(i).getID();
															String tUserName = staffList.get(i).getUsername();
															btn_chgpwd.addActionListener(new ActionListener() {
																@Override
																public void actionPerformed(ActionEvent e) {
																	if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
																		JOptionPane.showMessageDialog(null,"Please make sure the fields are not in edit mode.");
																	}
																	else{
																		loadResetPasswordFrame(e,tID,tUserName);
																	}
																}
															});
														}
														/*
														JButton btn_forgot = new JButton("Forgot password?");
														//For manager
														if(currentEmployee.getPositionID() == 5){
															btn_edit.setVisible(false);
															//JOptionPane.showMessageDialog(null,"You are not authorized to alter employee information.","Error",JOptionPane.ERROR_MESSAGE);
														}
														else if(currentEmployee.getPositionID() == 3 &&  staffList.get(i).getPositionID() == 4){
															btn_edit.setVisible(false);
															btn_forgot.setVisible(false);
														}
														else{
															//Forgot Password for managers
															btn_forgot.setBounds(90, 570, 200, 40);
															btn_forgot.setFont(new Font("Tahoma", Font.PLAIN, 20));
															btn_forgot.setFocusable(false);
															staffDetails.add(btn_forgot);
															tempResetUserEmail = staffList.get(i).getEmail();
															btn_forgot.addActionListener(new ActionListener() {
																@Override
																public void actionPerformed(ActionEvent e) {
																	if(btn_edit.isVisible() == false && btn_save.isVisible() == true){
																		JOptionPane.showMessageDialog(null,"Please make sure the fields are not in edit mode.");
																	}
																	else{
																		if(tempResetUserEmail != null && !tempResetUserEmail.isEmpty() && tempResetUserEmail.length() > 0){
																			Object[] options = {"Cancel","Enter Code","Send Email"};
																			int selection = JOptionPane.showOptionDialog(null,"Reset password using: ","Forgot Password?",
																				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
																				
																			//Cancel
																			if(selection == JOptionPane.YES_OPTION){
																				
																			}
																			//Enter Code
																			else if(selection == JOptionPane.NO_OPTION){
																				
																			}
																			//Send Email
																			else if(selection == JOptionPane.CANCEL_OPTION){
																				//
																				Random random = new Random();
																				//random.nextInt(max - min + 1) + min
																				int value = random.nextInt(9999 - 1000 + 1) + 1000;
																				String sValue = String.valueOf(value);
																				if(!sValue.isEmpty() && sValue.length() == 4){
																					//Send Generated code to Email
																					resetUserEmail.put(tempResetUserEmail, sValue);
																					//System.out.println("Size: " + resetUserEmail.size());
																					// Recipient's email ID needs to be mentioned.
																				    //String to = tempResetUserEmail;
																					String to = "nitish111@hotmail.com";
																					
																				    // Sender's email ID needs to be mentioned
																				    //Change to senders email (current signed in employee), or zenit
																				    String from = "nsbajaj@myseneca.com";
																				    
																				    // Assuming you are sending email from localhost
																				    //Change localhost to host
																				    String host = "localhost";
																				    
																				    // Get system properties
																				    Properties properties = System.getProperties();
																				    
																				    // Setup mail server
																				    properties.setProperty("mail.smtp.host", host);
			
																				    // Get the default Session object.
																				    Session session = Session.getDefaultInstance(properties);
																					
																				    try{
																				         // Create a default MimeMessage object.
																				         MimeMessage message = new MimeMessage(session);
			
																				         // Set From: header field of the header.
																				         message.setFrom(new InternetAddress(from));
			
																				         // Set To: header field of the header.
																				         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
																				         // Set Subject: header field
																				         message.setSubject("This is the Subject Line!");
			
																				         // Now set the actual message
																				         message.setText("This is actual message");
			
																				         // Send message
																				         Transport.send(message);
																				         System.out.println("Sent message successfully....");
																				      }catch (MessagingException mex) {
																				         mex.printStackTrace();
																				      }
																					
																					//Reset to null, just to be safe
																					tempResetUserEmail = null;
																				}
																			//}
																		}
																		else{
																			JOptionPane.showMessageDialog(null,"This account does not have an email associated with it.");
																		}
																	}
																}
															});
														}
													*/
													}
												}
												break;
											}
										}
									}
							        
							        //Filling fields
									if(staffList.size() > 0){
								        for(int i = 0; i < staffList.size(); i++){
								        	String tempID = String.valueOf(employeeModel.getValueAt(row, 0));
								        	if(tempID.equals(String.valueOf(staffList.get(i).getID()))){
								        		textPane_details_id.setText(String.valueOf(staffList.get(i).getID()));
								        		textPane_details_firstName.setText(staffList.get(i).getFirstName());
								        		textPane_details_lastName.setText(staffList.get(i).getLastName());
								        		textPane_details_street.setText(staffList.get(i).getStreet());
								        		textPane_details_city.setText(staffList.get(i).getCity());
								        		//textPane_details_stateProvince.setText(staffList.get(i).getStateProvince());
								        		for(int j = 0; j < provinces.length; j++){
								        			if(staffList.get(i).getStateProvince().equals(provinces[j])){
								        				comboBox_detail_province.setSelectedIndex(j);
								        			}
								        		}
								        		
								        		textPane_details_postalCode.setText(staffList.get(i).getPostalCode());
								        		textPane_details_homePhone.setText(staffList.get(i).getHomePhone());
								        		textPane_details_cellPhone.setText(staffList.get(i).getCellPhone());
								        		textPane_details_email.setText(staffList.get(i).getEmail());
								        		comboBox_detail_positions.setSelectedIndex(staffList.get(i).getPositionID()-1);
								        		for(int j = 0; j < jobtypes.length; j++){
								        			//System.out.println(staffList.get(i).getJobType());
								        			if(staffList.get(i).getJobType().trim().equals("Full Time")){
								        				comboBox_detail_jobtypes.setSelectedIndex(0);
								        			}
								        			else if(staffList.get(i).getJobType().trim().equals("Part Time")){
								        				comboBox_detail_jobtypes.setSelectedIndex(1);
								        			}
								        			else if(staffList.get(i).getJobType().trim().equals("Casual")){
								        				comboBox_detail_jobtypes.setSelectedIndex(2);
								        			}
								        			else{
								        				//System.out.println("Else");
								        			}
								        		}
								        		textPane_details_username.setText(staffList.get(i).getUsername());
								        		textPane_details_hireDate.setText(parseDate(staffList.get(i).getHireDate()));
								        		if(staffList.get(i).getTerminationDate() != null){
								        			textPane_details_terminateDate.setText(parseDate(staffList.get(i).getTerminationDate()));
								        			terminateCheckbox.setSelected(true);
								        		}
								        		else{
								        			textPane_details_terminateDate.setText(staffList.get(i).getTerminationDate());
								        		}
								        		break;
								        	}
								        }
							        }
							        
							        Component component = (Component) e.getSource();
									JFrame topFrame2 = (JFrame) SwingUtilities.getRoot(component);
									d1 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
									d1.getContentPane().add(staffDetails);
									d1.setSize(900, 650);
									d1.setLocationRelativeTo(null);
									d1.getRootPane().setDefaultButton(btn_cancel);
									d1.setVisible(true);
									}
								}
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
						}
					}
				}
			}
		});
        
        JPanel panel_add_staff = new JPanel();
        tabbedPane.addTab("Add", null, panel_add_staff, null);
        panel_add_staff.setLayout(null);
        
        JTabbedPane tabbedPane_add_employee = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_add_employee.setBounds(150, 6, 976, 615);
        panel_add_staff.add(tabbedPane_add_employee);
        
        JPanel panel_add_employee = new JPanel();
        tabbedPane_add_employee.addTab("Add Employee", null, panel_add_employee, null);
        panel_add_employee.setLayout(null);
        
        JTextPane textPane_firstName = new JTextPane();
        textPane_firstName.setEditable(false);
        textPane_firstName.setBounds(10, 10, 72, 20);
        panel_add_employee.add(textPane_firstName);
        textPane_firstName.setText("First Name:");
        textPane_firstName.setBackground(Color.decode(defaultColor));
        
        JTextPane txtpnLastName = new JTextPane();
        txtpnLastName.setEditable(false);
        txtpnLastName.setText("Last Name:");
        txtpnLastName.setBounds(10, 40, 72, 20);
        txtpnLastName.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnLastName);
        
        JTextPane txtpnStreet = new JTextPane();
        txtpnStreet.setEditable(false);
        txtpnStreet.setText("Street:");
        txtpnStreet.setBounds(10, 72, 40, 20);
        txtpnStreet.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnStreet);
        
        JTextPane txtpnCity = new JTextPane();
        txtpnCity.setEditable(false);
        txtpnCity.setText("City:");
        txtpnCity.setBounds(10, 104, 29, 20);
        txtpnCity.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnCity);
        
        JTextPane txtpnState_Province = new JTextPane();
        txtpnState_Province.setText("State/Province:");
        txtpnState_Province.setEditable(false);
        txtpnState_Province.setBounds(10, 136, 100, 20);
        txtpnState_Province.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnState_Province);
        
        JTextPane txtpnPostalCode = new JTextPane();
        txtpnPostalCode.setText("Postal Code:");
        txtpnPostalCode.setEditable(false);
        txtpnPostalCode.setBounds(10, 168, 78, 20);
        txtpnPostalCode.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnPostalCode);
        
        JTextPane txtpnHomePhone = new JTextPane();
        txtpnHomePhone.setText("Home Phone:");
        txtpnHomePhone.setEditable(false);
        txtpnHomePhone.setBounds(10, 200, 83, 20);
        txtpnHomePhone.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnHomePhone);
        
        JTextPane txtpnCellPhone = new JTextPane();
        txtpnCellPhone.setEditable(false);
        txtpnCellPhone.setText("Cell Phone:");
        txtpnCellPhone.setBounds(10, 232, 72, 20);
        txtpnCellPhone.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnCellPhone);
        
        JTextPane txtpnEmail = new JTextPane();
        txtpnEmail.setText("Email:");
        txtpnEmail.setEditable(false);
        txtpnEmail.setBounds(10, 264, 40, 20);
        txtpnEmail.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnEmail);
        
        JTextPane txtpnPositionId = new JTextPane();
        txtpnPositionId.setText("Position ID:");
        txtpnPositionId.setEditable(false);
        txtpnPositionId.setBounds(10, 296, 78, 20);
        txtpnPositionId.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnPositionId);
        
        JTextPane txtpnJobType = new JTextPane();
        txtpnJobType.setText("Job Type:");
        txtpnJobType.setEditable(false);
        txtpnJobType.setBounds(10, 328, 60, 20);
        txtpnJobType.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnJobType);
        
        JTextPane txtpnUsername = new JTextPane();
        txtpnUsername.setText("Username:");
        txtpnUsername.setEditable(false);
        txtpnUsername.setBounds(10, 360, 70, 20);
        txtpnUsername.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnUsername);
        
        JTextPane txtpnPassword = new JTextPane();
        txtpnPassword.setText("Password:");
        txtpnPassword.setEditable(false);
        txtpnPassword.setBounds(10, 392, 63, 20);
        txtpnPassword.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnPassword);
        
        JTextPane txtpnHireDate = new JTextPane();
        txtpnHireDate.setText("Hire Date:");
        txtpnHireDate.setEditable(false);
        txtpnHireDate.setBounds(10, 424, 68, 20);
        txtpnHireDate.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(txtpnHireDate);
        
        textField_firstName = new JTextField();
        textField_firstName.setBounds(85, 4, 150, 26);
        panel_add_employee.add(textField_firstName);
        textField_firstName.setColumns(10);
        
        textField_firstName.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(AncestorEvent event) {}
			@Override
			public void ancestorMoved(AncestorEvent event) {}
			@Override
			public void ancestorAdded(AncestorEvent event) {
				SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                textField_firstName.requestFocusInWindow();
		            }
		        });
			}
		});
        
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
        
        /*textField_positionID = new JTextField();
        textField_positionID.setBounds(90, 290, 150, 26);
        panel_add_employee.add(textField_positionID);
        textField_positionID.setColumns(10);
        */
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
        textField_hireDate.setText(parseDate(getDate()));
        textField_hireDate.setEditable(false);
        panel_add_employee.add(textField_hireDate);
        textField_hireDate.setColumns(10);
        
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(checkFields() == true){
        			//System.out.println("Valid");
        			createEmployee();
        			//clearFields();
        			//textField_firstName.requestFocus();
        		}
        		else{
        			//System.out.println("Invalid");
        		}
        	}
        });
        btnSubmit.setBounds(10, 450, 441, 75);
        panel_add_employee.add(btnSubmit);
        
        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearFields();
        		textField_firstName.requestFocus();
        	}
        });
        btnClear.setBounds(508, 450, 441, 75);
        panel_add_employee.add(btnClear);
        
        textPane_error_firstName = new JTextPane();
        textPane_error_firstName.setEditable(false);
        textPane_error_firstName.setBounds(247, 7, 489, 20);
        textPane_error_firstName.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_firstName);
        
        textPane_error_lastName = new JTextPane();
        textPane_error_lastName.setEditable(false);
        textPane_error_lastName.setBounds(247, 37, 489, 20);
        textPane_error_lastName.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_lastName);
        
        textPane_error_street = new JTextPane();
        textPane_error_street.setEditable(false);
        textPane_error_street.setBounds(267, 68, 469, 20);
        textPane_error_street.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_street);
        
        textPane_error_city = new JTextPane();
        textPane_error_city.setEditable(false);
        textPane_error_city.setBounds(207, 100, 529, 20);
        textPane_error_city.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_city);
        
        textPane_error_stateProvince = new JTextPane();
        textPane_error_stateProvince.setEditable(false);
        textPane_error_stateProvince.setBounds(272, 132, 464, 20);
        textPane_error_stateProvince.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_stateProvince);
        
        textPane_error_postalCode = new JTextPane();
        textPane_error_postalCode.setEditable(false);
        textPane_error_postalCode.setBounds(254, 165, 680, 20);
        textPane_error_postalCode.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_postalCode);
        
        textPane_error_homePhone = new JTextPane();
        textPane_error_homePhone.setEditable(false);
        textPane_error_homePhone.setBounds(262, 196, 680, 20);
        textPane_error_homePhone.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_homePhone);
        
        textPane_error_cellPhone = new JTextPane();
        textPane_error_cellPhone.setEditable(false);
        textPane_error_cellPhone.setBounds(247, 229, 700, 20);
        textPane_error_cellPhone.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_cellPhone);
        
        textPane_error_email = new JTextPane();
        textPane_error_email.setEditable(false);
        textPane_error_email.setBounds(215, 261, 650, 20);
        textPane_error_email.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_email);
        
        textPane_error_positionID = new JTextPane();
        textPane_error_positionID.setEditable(false);
        textPane_error_positionID.setBounds(254, 294, 482, 20);
        textPane_error_positionID.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_positionID);
        
        textPane_error_jobType = new JTextPane();
        textPane_error_jobType.setEditable(false);
        textPane_error_jobType.setBounds(235, 325, 501, 20);
        textPane_error_jobType.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_jobType);
        
        textPane_error_username = new JTextPane();
        textPane_error_username.setEditable(false);
        textPane_error_username.setBounds(234, 355, 502, 20);
        textPane_error_username.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_username);
        
        textPane_error_password = new JTextPane();
        textPane_error_password.setEditable(false);
        textPane_error_password.setBounds(240, 387, 709, 20);
        textPane_error_password.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_password);
        
        textPane_error_hireDate = new JTextPane();
        textPane_error_hireDate.setEditable(false);
        textPane_error_hireDate.setBounds(240, 420, 496, 20);
        textPane_error_hireDate.setBackground(Color.decode(defaultColor));
        panel_add_employee.add(textPane_error_hireDate);
        
        comboBox_jobType = new JComboBox(jobtypes);
        comboBox_jobType.setBounds(75, 321, 150, 27);
        panel_add_employee.add(comboBox_jobType);
        
        comboBox_positionID = new JComboBox(positionids);
        comboBox_positionID.setBounds(85, 289, 170, 27);
        panel_add_employee.add(comboBox_positionID);
        
        comboBox_province = new JComboBox(provinces);
        comboBox_province.setBounds(111, 129, 150, 27);
        panel_add_employee.add(comboBox_province);
	}
	public void updateCurrentEmployee(){
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM Employee Where ID = ? AND UserName = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, currentEmployee.getID());
			ps.setString(2, currentEmployee.getUsername());
			ResultSet rs = ps.executeQuery();
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				currentEmployee.id = rs.getInt("ID");
				currentEmployee.firstName = rs.getString("FirstName");
				currentEmployee.lastName = rs.getString("LastName");
				currentEmployee.street = rs.getString("Street");
				currentEmployee.city = rs.getString("City");
				currentEmployee.stateProvince = rs.getString("State_Province");
				currentEmployee.postalCode = rs.getString("PostalCode");
				currentEmployee.homePhone = rs.getString("HomePhone");
				currentEmployee.cellPhone = rs.getString("CellPhone");
				currentEmployee.email = rs.getString("Email");
				currentEmployee.positionID = rs.getInt("PositionID");
		    	currentEmployee.jobType = rs.getString("JobType");
		    	currentEmployee.username = rs.getString("UserName");
		    	currentEmployee.password = rs.getString("Password");
		    	currentEmployee.hireDate = rs.getString("HireDate");
		    	currentEmployee.terminationDate = rs.getString("TerminationDate");
		    	currentEmployee.salt = rs.getString("Salt");
			}
			//Clean-up environment
			rs.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HashMap getResetUserEmail() {
		return resetUserEmail;
	}
	public void setResetUserEmail(HashMap resetUserEmail) {
		this.resetUserEmail = resetUserEmail;
	}
	//Reset
	private void loadResetPasswordFrame(ActionEvent e, int employeeID, String username){
		JPanel chgpwd_panel = new JPanel();
		chgpwd_panel.setLayout(null);
					
		JTabbedPane tabbedPane_chgpwd = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_chgpwd.setBounds(55, 25, 790, 220);
        chgpwd_panel.add(tabbedPane_chgpwd);
        
        JPanel panel_chgpwd = new JPanel();
        tabbedPane_chgpwd.addTab("Change Password:", null, panel_chgpwd, null);
        panel_chgpwd.setLayout(null);
        
        JTextPane txtpnNewPassword = new JTextPane();
        txtpnNewPassword.setText("New Password:");
        txtpnNewPassword.setBackground(Color.decode(defaultColor));
        txtpnNewPassword.setEditable(false);
        txtpnNewPassword.setBounds(10, 25, 94, 20);
        panel_chgpwd.add(txtpnNewPassword);
        
        JTextPane txtpnConfirmNewPassword = new JTextPane();
        txtpnConfirmNewPassword.setText("Confirm New Password:");
        txtpnConfirmNewPassword.setBackground(Color.decode(defaultColor));
        txtpnConfirmNewPassword.setEditable(false);
        txtpnConfirmNewPassword.setBounds(10, 70, 149, 20);
        panel_chgpwd.add(txtpnConfirmNewPassword);
        
        textField_input_newPassword = new JPasswordField();
        textField_input_newPassword.setBounds(110, 23, 150, 26);
        panel_chgpwd.add(textField_input_newPassword);
        textField_input_newPassword.setColumns(10);
        
        textField_input_confirmNewPassword = new JPasswordField();
        textField_input_confirmNewPassword.setBounds(165, 68, 150, 26);
        panel_chgpwd.add(textField_input_confirmNewPassword);
        textField_input_confirmNewPassword.setColumns(10);
        
        textField_error_newPassword = new JTextPane();
        textField_error_newPassword.setBounds(275, 25, 489, 35);
        textField_error_newPassword.setEditable(false);
        textField_error_newPassword.setBackground(Color.decode(defaultColor));
        panel_chgpwd.add(textField_error_newPassword);
        
        textField_error_confirmNewPassword = new JTextPane();
        textField_error_confirmNewPassword.setBounds(325, 70, 430, 35);
        textField_error_confirmNewPassword.setEditable(false);
        textField_error_confirmNewPassword.setBackground(Color.decode(defaultColor));
        panel_chgpwd.add(textField_error_confirmNewPassword);
        
        JButton btnChgPassword = new JButton("Change Password");
        btnChgPassword.setBounds(10, 120, 350, 38);
        panel_chgpwd.add(btnChgPassword);
        btnChgPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean check = true;
				boolean ok = true;
				//New Password
				char[] npassword = textField_input_newPassword.getPassword();
				String newPass = new String(npassword);
				if(validateEmpty(newPass) == false){
					textField_error_newPassword.setText("Password cannot be Empty.");
					check = false;
				}
				else{
					if(newPass.trim().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{3,}")){
						textField_error_newPassword.setText(null);
					}
					else{
						textField_error_newPassword.setText("Password must contain atleast one number, alphabet (lower & upper case), special character (@#$%^&+=) and no white space.");
						ok = false;
					}
				}
				
				//Confirm New Password
				char[] cnpassword = textField_input_confirmNewPassword.getPassword();
				String cnewPass = new String(cnpassword);
				if(validateEmpty(cnewPass) == false){
					textField_error_confirmNewPassword.setText("Password cannot be Empty.");
					check = false;
				}
				else{
					if(cnewPass.trim().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{3,}")){
						textField_error_confirmNewPassword.setText(null);
					}
					else{
						textField_error_confirmNewPassword.setText("Password must contain atleast one number, alphabet (lower & upper case), special character (@#$%^&+=) and no white space.");
						ok = false;
					}
				}
				if(check && ok){
					if(newPass.equals(cnewPass)){
						Hashing hash = new Hashing();
						String salt;
						
						try {
							salt = hash.getSalt();
							String hashedPassword = hash.get_SHA_512_SecurePassword(newPass, salt);
							Connect connect = new Connect();
							try {
								Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
								String sql = "UPDATE `Employee` SET Password = ?, Salt = ? where ID = ? AND UserName = ?";
								PreparedStatement ps = con.prepareStatement(sql);
								ps.setString(1, hashedPassword);
								ps.setString(2, salt);
								ps.setInt(3, employeeID);
								ps.setString(4, username);
								ps.executeUpdate();
								
								//Clean-up environment
								con.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null,"Password successfully changed for Employee '" + username + "'.");
						d2.dispose();
					}
					else{
						textField_error_newPassword.setText("Password entered does not match confirm password.");
						textField_error_confirmNewPassword.setText("Confirm password entered does not match new password.");
					}
				}
			}
        });
        
        JButton btnCancel_chgpwd = new JButton("Cancel");
        btnCancel_chgpwd.setBounds(405, 120, 350, 38);
        panel_chgpwd.add(btnCancel_chgpwd);
        btnCancel_chgpwd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d2.dispose();
			}
		});
        
		Component component = (Component) e.getSource();
		//Call parent jframe/jdialog
		JFrame topFrame2 = (JFrame)(d1.getParent());
		d2 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
		d2.getContentPane().add(chgpwd_panel);
		d2.setSize(900, 300);
		d2.setLocationRelativeTo(null);
		d2.getRootPane().setDefaultButton(btnCancel_chgpwd);
		d2.setVisible(true);
	}
	//Forgot
	private void loadPasswordFrame(ActionEvent e, int employeeID, String username) {
		JPanel chgpwd_panel = new JPanel();
		chgpwd_panel.setLayout(null);
					
		JTabbedPane tabbedPane_chgpwd = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_chgpwd.setBounds(55, 25, 790, 220);
        chgpwd_panel.add(tabbedPane_chgpwd);
        
        JPanel panel_chgpwd = new JPanel();
        tabbedPane_chgpwd.addTab("Change Password:", null, panel_chgpwd, null);
        panel_chgpwd.setLayout(null);
        
        JTextPane txtpnCurrentPassword = new JTextPane();
        txtpnCurrentPassword.setText("Current Password:");
        txtpnCurrentPassword.setBackground(Color.decode(defaultColor));
        txtpnCurrentPassword.setEditable(false);
        txtpnCurrentPassword.setBounds(10, 10, 114, 20);
        panel_chgpwd.add(txtpnCurrentPassword);
        
        JTextPane txtpnNewPassword = new JTextPane();
        txtpnNewPassword.setText("New Password:");
        txtpnNewPassword.setBackground(Color.decode(defaultColor));
        txtpnNewPassword.setEditable(false);
        txtpnNewPassword.setBounds(10, 45, 94, 20);
        panel_chgpwd.add(txtpnNewPassword);
        
        JTextPane txtpnConfirmNewPassword = new JTextPane();
        txtpnConfirmNewPassword.setText("Confirm New Password:");
        txtpnConfirmNewPassword.setBackground(Color.decode(defaultColor));
        txtpnConfirmNewPassword.setEditable(false);
        txtpnConfirmNewPassword.setBounds(10, 80, 149, 20);
        panel_chgpwd.add(txtpnConfirmNewPassword);
        
        textField_input_currentPassword = new JPasswordField();
        textField_input_currentPassword.setBounds(130, 4, 150, 26);
        panel_chgpwd.add(textField_input_currentPassword);
        textField_input_currentPassword.setColumns(10);
        
        textField_input_newPassword = new JPasswordField();
        textField_input_newPassword.setBounds(110, 38, 150, 26);
        panel_chgpwd.add(textField_input_newPassword);
        textField_input_newPassword.setColumns(10);
        
        textField_input_confirmNewPassword = new JPasswordField();
        textField_input_confirmNewPassword.setBounds(165, 73, 150, 26);
        panel_chgpwd.add(textField_input_confirmNewPassword);
        textField_input_confirmNewPassword.setColumns(10);
        
        JTextPane textPane_error_currentPassword = new JTextPane();
        textPane_error_currentPassword.setBounds(290, 10, 465, 20);
        textPane_error_currentPassword.setEditable(false);
        textPane_error_currentPassword.setBackground(Color.decode(defaultColor));
        panel_chgpwd.add(textPane_error_currentPassword);
        
        textField_error_newPassword = new JTextPane();
        textField_error_newPassword.setBounds(270, 42, 489, 26);
        textField_error_newPassword.setEditable(false);
        textField_error_newPassword.setBackground(Color.decode(defaultColor));
        panel_chgpwd.add(textField_error_newPassword);
        
        textField_error_confirmNewPassword = new JTextPane();
        textField_error_confirmNewPassword.setBounds(325, 77, 430, 32);
        textField_error_confirmNewPassword.setEditable(false);
        textField_error_confirmNewPassword.setBackground(Color.decode(defaultColor));
        panel_chgpwd.add(textField_error_confirmNewPassword);
        
        JButton btnChgPassword = new JButton("Change Password");
        btnChgPassword.setBounds(10, 120, 350, 38);
        panel_chgpwd.add(btnChgPassword);
        btnChgPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean check = true;
				boolean ok = true;
				//Current Password
				char[] cpassword = textField_input_currentPassword.getPassword();
				String currentPass = new String(cpassword);
				if(validateEmpty(currentPass) == false){
					textPane_error_currentPassword.setText("Password cannot be Empty.");
					check = false;
				}
				else{
					//Add new regex
					if(currentPass.trim().matches("^[A-Za-z0-9-+$_.@]*$")){
						textPane_error_currentPassword.setText(null);
					}
					else{
						//Add new message according to new regex
						textPane_error_currentPassword.setText("Password can only contain alphabets, numberes, '-', '+', '$', '_', '.', '@'.");
						ok = false;
					}
				}
				
				//New Password
				char[] npassword = textField_input_newPassword.getPassword();
				String newPass = new String(npassword);
				if(validateEmpty(newPass) == false){
					textField_error_newPassword.setText("Password cannot be Empty.");
					check = false;
				}
				else{
					if(newPass.trim().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{3,}")){
						textField_error_newPassword.setText(null);
					}
					else{
						textField_error_newPassword.setText("Password must contain atleast one number, alphabet (lower & upper case), special character (@#$%^&+=) and no white space.");
						ok = false;
					}
				}
				
				//Confirm New Password
				char[] cnpassword = textField_input_confirmNewPassword.getPassword();
				String cnewPass = new String(cnpassword);
				if(validateEmpty(cnewPass) == false){
					textField_error_confirmNewPassword.setText("Password cannot be Empty.");
					check = false;
				}
				else{
					if(cnewPass.trim().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{3,}")){
						textField_error_confirmNewPassword.setText(null);
					}
					else{
						textField_error_confirmNewPassword.setText("Password must contain atleast one number, alphabet (lower & upper case), special character (@#$%^&+=) and no white space.");
						ok = false;
					}
				}
				
				if(check && ok){
					//Current password
					Employees tempEmployee = new Employees(username);
					if(tempEmployee.fetchLogin(username, currentPass)){
						if(newPass.equals(cnewPass)){
							Hashing hash = new Hashing();
							String salt;
							try {
								salt = hash.getSalt();
								String hashedPassword = hash.get_SHA_512_SecurePassword(newPass, salt);
								Connect connect = new Connect();
								try {
									Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
									String sql = "UPDATE `Employee` SET Password = ?, Salt = ? where ID = ? AND UserName = ?";
									PreparedStatement ps = con.prepareStatement(sql);
									ps.setString(1, hashedPassword);
									ps.setString(2, salt);
									ps.setInt(3, employeeID);
									ps.setString(4, username);
								    ps.executeUpdate();
									
									//Clean-up environment
									con.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} catch (NoSuchAlgorithmException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null,"Password successfully changed for Employee '" + username + "'.");
							d2.dispose();
						}
						else{
							textField_error_newPassword.setText("Password entered does not match confirm password.");
							textField_error_confirmNewPassword.setText("Confirm password entered does not match new password.");
						}
					}
					else{
						textPane_error_currentPassword.setText("Invalid Password");
					}
				}
			}
		});
        
        JButton btnCancel_chgpwd = new JButton("Cancel");
        btnCancel_chgpwd.setBounds(405, 120, 350, 38);
        panel_chgpwd.add(btnCancel_chgpwd);
        btnCancel_chgpwd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d2.dispose();
			}
		});
        
		Component component = (Component) e.getSource();
		//Call parent jframe/jdialog
		JFrame topFrame2 = (JFrame)(d1.getParent());
		d2 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
		d2.getContentPane().add(chgpwd_panel);
		d2.setSize(900, 300);
		d2.setLocationRelativeTo(null);
		d2.getRootPane().setDefaultButton(btnCancel_chgpwd);
		d2.setVisible(true);
	}
	public void setCurrentEmployee(Employees currentEmployee){
		this.currentEmployee = currentEmployee;
	}
	private String parseDate(String date) {
		String finalOutput = null;
		date = date.trim();
		if(!date.isEmpty() && date.length() > 0){
			String[] splited = date.split("\\s+");
			String newDate = splited[0];
			String year = null;
			String month = null;
			String day;
			if(newDate.contains("-")){
				//yyyy/MM/dd HH:mm:ss
				//Year
				int first = newDate.indexOf("-");
				year = newDate.substring(0, first);
				
				//Month
				newDate = newDate.substring(first+1, newDate.length());
				int second = newDate.indexOf("-");
				month = newDate.substring(0, second);
				if(month.charAt(0) == '0'){
					char c = month.charAt(1);
					month = Character.toString(c);
				}
				if(Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12){
					DateFormatSymbols dfs = new DateFormatSymbols();
					String[] allMonths = dfs.getMonths();
					int newMonth = Integer.valueOf(month) - 1;
					month = allMonths[newMonth];
				}
				//Day
				newDate = newDate.substring(second+1, newDate.length());
				day = newDate.substring(0, newDate.length());
				if(day.charAt(0) == '0'){
					char c = day.charAt(1);
					day = Character.toString(c);
				}
				//New output: dd/MM/yyyy
				finalOutput = day + dateDelimiter + month + dateDelimiter + year;
			}
			else if(newDate.contains("/")){
				//yyyy/MM/dd HH:mm:ss
				//Year
				int first = newDate.indexOf("/");
				year = newDate.substring(0, first);
				
				//Month
				newDate = newDate.substring(first+1, newDate.length());
				int second = newDate.indexOf("/");
				month = newDate.substring(0, second);
				if(month.charAt(0) == '0'){
					char c = month.charAt(1);
					month = Character.toString(c);
				}
				if(Integer.parseInt(month) >= 1 && Integer.parseInt(month) <= 12){
					DateFormatSymbols dfs = new DateFormatSymbols();
					String[] allMonths = dfs.getMonths();
					int newMonth = Integer.valueOf(month) - 1;
					month = allMonths[newMonth];
				}
				//Day
				newDate = newDate.substring(second+1, newDate.length());
				day = newDate.substring(0, newDate.length());
				if(day.charAt(0) == '0'){
					char c = day.charAt(1);
					day = Character.toString(c);
				}
				//New output: dd/MM/yyyy
				finalOutput = day + dateDelimiter + month + dateDelimiter + year;
			}
		}
		return finalOutput;
	}
	public Employees(int id,String firstName,String lastName,String street,String city,String stateProvince,String postalCode,String homePhone,
		String cellPhone,String email,int positionID,String jobType,String username,String password,String hireDate,
		String terminationDate,String salt){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.stateProvince = stateProvince;
		this.postalCode = postalCode;
		this.homePhone = homePhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.positionID = positionID;
		this.jobType = jobType;
		this.username = username;
		this.password = password;
		this.hireDate = hireDate;
		this.terminationDate = terminationDate;
		this.salt = salt;
	}
	public Employees(String username){
		
	}
	public void getStaffList(){
		Connect connect = new Connect();
		try {
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "SELECT * FROM Employee";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			//Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				int id = rs.getInt("ID");
		    	String firstName = rs.getString("FirstName");
		    	String lastName = rs.getString("LastName");
		    	String street = rs.getString("Street");
		    	String city = rs.getString("City");
		    	String stateProvince = rs.getString("State_Province");
		    	String postalCode = rs.getString("PostalCode");
		    	String homePhone = rs.getString("HomePhone");
		    	String cellPhone = rs.getString("CellPhone");
		    	String email = rs.getString("Email");
		    	int positionID = rs.getInt("PositionID");
		    	String jobType = rs.getString("JobType");
		    	String username = rs.getString("UserName");
		    	String password = rs.getString("Password");
		    	String hireDate = rs.getString("HireDate");
		    	String terminationDate = rs.getString("TerminationDate");
		    	String salt = rs.getString("Salt");
		    	
		    	Employees e = new Employees(id,firstName,lastName,street,city,stateProvince,postalCode,homePhone,cellPhone,email,positionID,jobType,username,password,hireDate,terminationDate,salt);
		    	staffList.add(e);
			}
			//Clean-up environment
			rs.close();
			con.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		//System.out.println("Date: " + dateString);
		return dateString;
	}
	public void createEmployee(){
		Connect connect = new Connect();
		Hashing hash = new Hashing();
		String salt = null;
		try {
			salt = hash.getSalt();
			String hashedPassword = hash.get_SHA_512_SecurePassword(textField_password.getText().trim(), salt);
			Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
			String sql = "INSERT INTO `Employee`(FirstName,LastName,Street,City,State_Province,PostalCode,HomePhone,CellPhone,Email,PositionID,JobType,UserName,Password,HireDate,Salt,CreatedBy) "
					+ "VALUE (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, textField_firstName.getText().trim());
			ps.setString(2, textField_lastName.getText().trim());
			ps.setString(3, textField_street.getText().trim());
			ps.setString(4, textField_city.getText().trim());
			String p = provinces[comboBox_province.getSelectedIndex()];
			ps.setString(5, p);
			ps.setString(6, textField_postalCode.getText().trim());
			ps.setString(7, textField_homePhone.getText().trim());
			ps.setString(8, textField_cellPhone.getText().trim());
			ps.setString(9, textField_email.getText().trim());
			ps.setInt(10, comboBox_positionID.getSelectedIndex()+1);
			ps.setString(11, jobtypes[comboBox_jobType.getSelectedIndex()]);
			ps.setString(12, textField_userName.getText().trim());
			ps.setString(13, hashedPassword);
			ps.setString(14, getDate());
			ps.setString(15, salt);
			ps.setString(16, currentEmployee.getUsername());
			ps.execute();
		
			//Clean-up environment
			con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Employee created.");
		btnClear.doClick();
		
		//removing vector elements
		if(staffList != null){
			staffList.clear();
		}
		//remove previous rows
		for (int i = employeeModel.getRowCount()-1; i >= 0; --i) {
			employeeModel.removeRow(i);
		}
		//Refreshing main staff table and getting new values
		getStaffList();
		if(staffList.size() > 0){
			for(int i = 0; i < staffList.size(); i++){
				employeeModel.addRow(new Object[]{staffList.get(i).getID(),staffList.get(i).getFirstName() + " " + staffList.get(i).getLastName(),staffList.get(i).getCellPhone(),staffList.get(i).getEmail(),staffList.get(i).getJobType(),staffList.get(i).getUsername(),staffList.get(i).getHireDate()});
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"No staff was found in the database.");
		}
	}
	
	public boolean checkFields(){
		boolean check = true;
		boolean ok = true;
		//First name
		if(validateEmpty(textField_firstName.getText()) == false){
			textPane_error_firstName.setText("First name cannot be Empty.");
			check = false;
		}
		else if(checkForAlphabets(textField_firstName.getText()) == false){
			textPane_error_firstName.setText("First name must contain alphabets only.");
			ok = false;
		}
		else if(textField_firstName.getText().length() > 15){
			textPane_error_firstName.setText("First name is too long. Must be 15 characters or under.");
			ok = false;
		}
		else{
			textPane_error_firstName.setText(null);
		}
		
		//Last name
		if(validateEmpty(textField_lastName.getText()) == false){
			textPane_error_lastName.setText("Last name cannot be Empty.");
			check = false;
		}
		else if(checkForAlphabets(textField_lastName.getText()) == false){
			textPane_error_lastName.setText("Last name must contain alphabets only.");
			ok = false;
		}
		else if(textField_lastName.getText().length() > 15){
			textPane_error_lastName.setText("Last name is too long. Must be 15 characters or under.");
			ok = false;
		}
		else{
			textPane_error_lastName.setText(null);
		}
		
		//Street
		if(validateEmpty(textField_street.getText().trim()) == false){
			textPane_error_street.setText("Street field cannot be Empty.");
			check = false;
		}
		else if(textField_street.getText().trim().length() > 40){
			textPane_error_street.setText("Street field is too long. Must be 40 characters or under.");
			check = false;
		}
		else{
			if(textField_street.getText().trim().matches("^['#A-Za-z0-9 ]*$")){
				textPane_error_street.setText(null);
			}
			else{
				textPane_error_street.setText("Street can only contain alphabets, numbers, '#', '''.");
				ok = false;
			}
		}
		
		//City
		if(validateEmpty(textField_city.getText().trim()) == false){
			textPane_error_city.setText("City field cannot be Empty.");
			check = false;
		}
		else if(checkForAlphabets(textField_city.getText().trim()) == false){
			textPane_error_city.setText("City field must contain alphabets only.");
			ok = false;
		}
		else if(textField_city.getText().trim().length() > 15){
			textPane_error_city.setText("City field is too long. Must be 15 characters or under.");
			ok = false;
		}
		else{
			textPane_error_city.setText(null);
		}
		
		//Provinces
		comboBox_province.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//comboBox_province.getSelectedIndex();
			}
		});
		
		//Postal code
		if(validateEmpty(textField_postalCode.getText()) == false){
			textPane_error_postalCode.setText("Postal Code cannot be Empty.");
			check = false;
		}
		else if(textField_postalCode.getText().length() > 10){
			textPane_error_postalCode.setText("Postal Code is too long. Must be 10 characters or under.");
			check = false;
		}
		else{
			if(textField_postalCode.getText().trim().matches("^([A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9])$")){ //A1B2C3
				textPane_error_postalCode.setText(null);
			}
			else if(textField_postalCode.getText().trim().matches("^([A-Za-z][0-9][A-Za-z])[ ]{1}([0-9][A-Za-z][0-9])$")){ //A1B 2C3
				textPane_error_postalCode.setText(null);
			}
			else if(textField_postalCode.getText().trim().matches("^([A-Za-z][0-9][A-Za-z])[-]{1}([0-9][A-Za-z][0-9])$")){ //A1B-2C3
				textPane_error_postalCode.setText(null);
			}
			else{
				textPane_error_postalCode.setText("Postal code does not have a valid code pattern. Example: 'A1B2C3', 'A1B 2C3', 'A1B-2C3'");
				ok = false;
			}
		}
		
		//Home phone
		if(validateEmpty(textField_homePhone.getText()) == false){
			textPane_error_homePhone.setText("Home phone cannot be Empty.");
			check = false;
		}
		else if(textField_homePhone.getText().length() > 15){
			textPane_error_homePhone.setText("Home phone is too long. Must be 15 characters or under.");
			check = false;
		}
		else{
			if(textField_homePhone.getText().trim().matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$")){ //999-999-9999
				textPane_error_homePhone.setText(null);
			}
			else if(textField_homePhone.getText().trim().matches("^([0-9]{3})[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //999 999 9999 
				textPane_error_homePhone.setText(null);
			}
			else if(textField_homePhone.getText().trim().matches("^([0-9]{10})$")){ //9999999999 
				textPane_error_homePhone.setText(null);
			}
			else if(textField_homePhone.getText().trim().matches("^[(]{1}([0-9]{3})[)]{1}[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //(999) 999 9999
				textPane_error_homePhone.setText(null);
			}
			else{
				textPane_error_homePhone.setText("Home phone does not have a valid phone pattern. Example: '9999999999','(999) 999 9999'.");
				check = false;
			}
		}
		
		//Cell phone
		if(validateEmpty(textField_cellPhone.getText()) == false){
			textPane_error_cellPhone.setText("Cell phone cannot be Empty.");
			check = false;
		}
		else if(textField_cellPhone.getText().length() > 15){
			textPane_error_cellPhone.setText("Cell phone is too long. Must be 15 characters or under.");
			check = false;
		}
		else{
			if(textField_cellPhone.getText().trim().matches("^[0-9]{3}[-][0-9]{3}[-][0-9]{4}$")){ //999-999-9999
				textPane_error_cellPhone.setText(null);
			}
			else if(textField_cellPhone.getText().trim().matches("^([0-9]{3})[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //999 999 9999 
				textPane_error_cellPhone.setText(null);
			}
			else if(textField_cellPhone.getText().trim().matches("^([0-9]{10})$")){ //9999999999 
				textPane_error_cellPhone.setText(null);
			}
			else if(textField_cellPhone.getText().trim().matches("^[(]{1}([0-9]{3})[)]{1}[ ]{1}([0-9]{3})[ ]{1}([0-9]{4})$")){ //(999) 999 9999
				textPane_error_cellPhone.setText(null);
			}
			else{
				textPane_error_cellPhone.setText("Cell phone does not have a valid phone pattern. Example: '999-999-9999','999 999 9999'.");
				ok = false;
			}
		}
		
		//Checking if home & cell are same
		if(textField_homePhone.getText().trim().equals(textField_cellPhone.getText().trim())){
			textPane_error_homePhone.setText("Home phone cannot be the same as Cell Phone.");
			textPane_error_cellPhone.setText("Cell phone cannot be the same as Home Phone.");
			check = false;
		}
		
		//Email
		if(validateEmpty(textField_email.getText()) == false){
			textPane_error_email.setText("Email cannot be Empty.");
			check = false;
		}
		else if(textField_email.getText().length() > 50){
			textPane_error_email.setText("Email is too long. Must be 50 characters or under.");
			check = false;
		}
		else{
			if(textField_email.getText().trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
				textPane_error_email.setText(null);
			}
			else{
				textPane_error_email.setText("Please enter a complete email address. Email can only contain alphabets, numbers, '_','-'.");
				ok = false;
			}
		}
		
		//Position
		comboBox_positionID.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//comboBox_positionID.getSelectedIndex();
			}
		});
		
		//Job type
		comboBox_jobType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//comboBox_jobType.getSelectedIndex();
			}
		});
		
		//Username
		if(validateEmpty(textField_userName.getText()) == false){
			textPane_error_username.setText("Username cannot be Empty.");
			check = false;
		}
		else if(textField_userName.getText().length() > 15){
			textPane_error_username.setText("Username is too long. Must be 15 characters or under.");
			check = false;
		}
		else{
			if(textField_userName.getText().trim().matches("^[A-Za-z0-9_-]*$")){
				textPane_error_username.setText(null);
			}
			else{
				textPane_error_username.setText("Username can only contain alphabets, numbers, '_', '-'.");
				ok = false;
			}
			if(ok == true){
				int count = 0;
				Connect connect = new Connect();
				try {
					Connection con = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
					String sql = "SELECT * FROM Employee Where UserName = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setString(1, textField_userName.getText().trim());
					ResultSet rs = ps.executeQuery();
					
					//Extract data from result set
					while(rs.next()){
						count++;
					}
					//Clean-up environment
					rs.close();
					con.close();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(count >= 1){
					textPane_error_username.setText("This username is unavailable. Please try another username.");
					ok = false;
				}
				else{
					textPane_error_username.setText(null);
				}
			}
			
		}
		//Password
		if(validateEmpty(textField_password.getText()) == false){
			textPane_error_password.setText("Password cannot be Empty.");
			check = false;
		}
		else{
			if(textField_password.getText().trim().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{3,}")){
				textPane_error_password.setText(null);
			}
			else{
				textPane_error_password.setText("Password must contain atleast one number, alphabet (lower & upper case), special character (@#$%^&+=) and no white space.");
				ok = false;
			}
		}
		
		if(check && ok){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean checkForNumbers(String input){
		String regexStr = "^[0-9]*$";
		if(input.trim().matches(regexStr)){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkForAlphabets(String input){
		String regexStr = "^[A-Za-z']*$";
		if(input.trim().matches(regexStr)){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean validateEmpty(String temp){
		temp = temp.trim();
		if(temp.isEmpty() || temp == null || temp.length() <= 0){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean containsSpace(String input){
		input = input.trim();
		boolean check = false;
		for(int i = 0; i < input.length(); i++){
			char c = input.charAt(i);
			if(c == ' '){
				check = true;
				break;
			}
			else{
				check = false;
			}
		}
		return check;
	}
	public void clearFields(){
		textField_firstName.setText(null);
		textField_lastName.setText(null);
		textField_street.setText(null);
		textField_city.setText(null);
		comboBox_province.setSelectedIndex(0);
		textField_postalCode.setText(null);
		textField_homePhone.setText(null);
		textField_cellPhone.setText(null);
		textField_email.setText(null);
		comboBox_positionID.setSelectedIndex(0);
		comboBox_jobType.setSelectedIndex(0);
		textField_userName.setText(null);
		textField_password.setText(null);
		textField_hireDate.setText(null);
	}
	private void CreateWindow(){
        
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
			String sql = "SELECT * FROM Employee where UserName = ? AND HireDate IS NOT NULL AND TerminationDate IS NULL";
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
	/*public boolean validateEmpty(String temp){
		if(temp.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}*/
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
