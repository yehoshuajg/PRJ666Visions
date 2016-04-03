package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.omg.Messaging.SyncScopeHelper;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JTextPane;
import java.awt.SystemColor;

public class Home extends JFrame implements KeyListener{
	
	private Vector<Product> productBySearch = new Vector<Product>();
	private Vector<Product> productByRefund = new Vector<Product>();
	private Vector<Product> productByLike;
	private Vector<Integer> previousValue = new Vector<Integer>();
	private Vector<Integer> previousRefundValue = new Vector<Integer>();
	private Product tempProductSearch = new Product();
	
	private int idLength = 8;
	
	JTabbedPane tabbedPane;
	private JPanel contentPane;
	private Employees employee;
	private JTextPane welcome;
	private JTextPane subtotal_textField;
	private JTextPane tax_textField;
	private JTextPane total_textField;
	private JTextField textField_productID_input;
	private JTextPane textField_name_input;
	private JTextPane textField_description_input;
	private JTextPane textField_quantity_input;
	private JTextPane textPane_productID_notFound;
	
	//Table
	private JTable table;
	private DefaultTableModel model;
	private Vector<String> columnName = new Vector<String>();
	private Vector<String> data = new Vector<String>();
	private int rowCount = 1;
	private int quantity = 1;
	private int tableListenerCount = 0;
	private JButton btnCancelItem;
	
	//Table columns
	private int indent_column = 0;
	private int id_column = 1;
	private int productName_column = 2;
	private int productQuantity_column = 3;
	private int productPrice_column = 4;
	private int productQuantityAndPrice_column = 5;
	private int productRemove_column = 6;
	
	//Sales Total
	private double subTotal = 0;
	private double tax = 0;
	private double taxValue = 0;
	private double total = 0;
	private int c = 0;
	private String stringTempPrice = null;
	private String transactionType = null;
	private String transactionMethod = null;
	private int promotionID = 0;
	
	//Discount
	private Vector<Discount> discount;
	private int detail_discountID;
	private String detail_discountType;
	private boolean oneTimeDiscountCheck = false;
	private JTextField discount_option;
	private JButton btnDiscountDetails;
	private JButton button_discount;
	private double detail_discountValue;
    
	private JButton keypad_0;
	private JButton keypad_1;
	private JButton keypad_2;
	private JButton keypad_3;
	private JButton keypad_4;
	private JButton keypad_5;
	private JButton keypad_6;
	private JButton keypad_7;
	private JButton keypad_8;
	private JButton keypad_9;
	private JButton keypad_decimal;
	private JButton keypad_enter;
	
	//Screen Dimensions
	private double screenWidth = 0;
	private double screenHeight = 0;
	private int w = (int) screenWidth;
	private int h = (int) screenHeight;
	private Dimension screenSize;
	
	//Background Color
	private String defaultColor = "#eeeeee";
	
	//Cashier Cash
	private JTabbedPane tabbedPane_7;
	private JPanel keypad_panel;
	private JTextField checkout_amount_tender_input;
	
	//JFrame/Dialog
	private JDialog d3;
	private JDialog d4;
	private JDialog d5;
	private JDialog d6;
	private JDialog d7;
	private JDialog d8;
	private JTextField textField_transaction_input;
	
	//Refund
	private JPanel panel_refund;
	private JDialog refund_confirm;
	
	//Refund Table
	private Transaction t;
	private Vector<TransactionRecord> tr = new Vector<TransactionRecord>();
	private Vector<TransactionRecord> returning = new Vector<TransactionRecord>();
	private int transactionRecordLength = 0;
	private Vector<String> refund_column_name = new Vector<String>();
	private Vector<String> refund_row_data = new Vector<String>();
	private JTable table_refund;
	private DefaultTableModel model_refund;	
	private JTextPane textField_refund_name;
	private JTextPane textField_refund_description_input;
	private JTextPane textField_refund_quantity_remaining_input;
	private JTextPane textField_refund_product_unitPrice_input;
	private JTextPane textField_refund_total_unitPrice_input;
	
	//Refund table columns
	private int refund_productID = 1;
	private int refund_QuantitySold = 2;
	private int refund_returned = 3;
	private int refund_dateReturned = 4;
	
	//Refund details:
	private JTextPane textField_transactionID;
	private JTextPane textField_createDate;
	private JTextPane textField_transaction_subtotal;
	private JTextPane textField_transaction_tax;
	private JTextPane textField_transaction_total;
	private JTextPane textField_transactionType;
	private JTextPane textField_method;
	private JTextPane textField_promotionID;
	private JTextPane textField_employeeID;
	
	
	//Refund sales
	double remainingSubtotal = 0;
	double finalRefundSubtotal = 0;
	double finalRefundTax = 0;
	double finalRefundTotal = 0;
	double refundChange = 0;
	
	//Date
	String dateDelimiter = "/";
	
	//Find by name
	JTextPane txtpnsearch_2;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public Home() throws Exception {
		//Getting size of screen/monitor
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		w = (int) screenWidth;
		h = (int) screenHeight;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1280, 720);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("Home");
		
/*		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	      setUndecorated(true);
	      
	      getContentPane().add(new JLabel("A JFrame Kiosk"), BorderLayout.NORTH);
	      JButton closeButton = new JButton("Close");
	      closeButton.addActionListener( new ActionListener()
	          {
	              public void actionPerformed( ActionEvent ae )
	              {
	                  System.out.println("Close button Pressed");
	                  Home.this.setVisible(false);
	                  System.exit(0);
	              }
	          });
	      getContentPane().add(closeButton, BorderLayout.CENTER);
*/		
		contentPane.setLayout(new BorderLayout(0, 0));
		//connect = new Connect();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 1280, 690); //main size
		contentPane.add(tabbedPane);
		
		JPanel panel_home = new JPanel();
		tabbedPane.addTab("Home", null, panel_home, null);
		panel_home.setLayout(null);
		
		welcome = new JTextPane();
		welcome.setFont(new Font("Tahoma", Font.PLAIN, 30));
		welcome.setText("Welcome, ");
		welcome.setBounds(413, 218, 492, 43);
		welcome.setBackground(Color.decode(defaultColor));
		welcome.setEditable(false);
		panel_home.add(welcome);
		//welcome.setColumns(10);
		
		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose();
					Login log = new Login();
					log.setVisible(true);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnSignOut.setBounds(521, 273, 276, 45);
		panel_home.add(btnSignOut);
		/*
		JButton btnBackupRestore = new JButton("Backup/Restore");
		btnBackupRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,35)));
				Object[] options = {"Cancel","Restore","Backup"};
				int cashCredit = JOptionPane.showOptionDialog(null,"Backup/Restore using: ","",
				    JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
				
				//Cancel
				if(cashCredit == JOptionPane.YES_OPTION){
					
				}
				//Restore
				else if(cashCredit == JOptionPane.NO_OPTION){
					boolean go = false;
					do{
						JPanel panel = new JPanel();
						JLabel label = new JLabel("Enter a password:");
						JPasswordField pass = new JPasswordField(10);
						panel.add(label);
						panel.add(pass);
						String[] options1 = new String[]{"OK", "Cancel"};
						int option = JOptionPane.showOptionDialog(null, panel, "Confirm:",
						                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
						                         null, options1, options1[0]);
						if(option == 0) // pressing OK button
						{
						    char[] password = pass.getPassword();
						    String passwordInput = new String(password);
						
							Employees tempEmployee = new Employees(employee.getUsername());
							if(tempEmployee.fetchLogin(employee.getUsername(), passwordInput)){
								BackupAndRestore br = new BackupAndRestore();
								br.setBackupRestorePath();
								br.initialize();
								br.restoreDB();
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
				}
				//Backup
				else if(cashCredit == JOptionPane.CANCEL_OPTION){
					boolean go = false;
					do{
						JPanel panel = new JPanel();
						JLabel label = new JLabel("Enter a password:");
						JPasswordField pass = new JPasswordField(10);
						panel.add(label);
						panel.add(pass);
						String[] options1 = new String[]{"OK", "Cancel"};
						int option = JOptionPane.showOptionDialog(null, panel, "Confirm:",
						                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
						                         null, options1, options1[0]);
						// pressing OK button
						if(option == 0) {
						    char[] password = pass.getPassword();
						    String passwordInput = new String(password);
	
							Employees tempEmployee = new Employees(employee.getUsername());
							if(tempEmployee.fetchLogin(employee.getUsername(), passwordInput)){
								BackupAndRestore br = new BackupAndRestore();
								br.setBackupRestorePath();
								br.initialize();
								br.backupDB();
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
				}
			}
		});
		btnBackupRestore.setBounds(501, 356, 153, 29);
		panel_home.add(btnBackupRestore);
		
		JButton btnChangeBackupAnd = new JButton("Change Backup and Restore Directory");
		btnChangeBackupAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("choosertitle");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);

			    if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			    	JPanel panel = new JPanel();
					JLabel label = new JLabel("Enter a password:");
					JPasswordField pass = new JPasswordField(10);
					panel.add(label);
					panel.add(pass);
					String[] options1 = new String[]{"OK", "Cancel"};
					int option = JOptionPane.showOptionDialog(null, panel, "Confirm:",
					                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
					                         null, options1, options1[0]);
					// pressing OK button
					if(option == 0) {
					    char[] password = pass.getPassword();
					    String passwordInput = new String(password);

						Employees tempEmployee = new Employees(employee.getUsername());
						if(tempEmployee.fetchLogin(employee.getUsername(), passwordInput)){
							//System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
					    	//System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
					    	String path = chooser.getSelectedFile() + "/backup.sql";
					    	BackupAndRestore.setPath(path);
					    	//System.out.println(path);
					    	//Write path to file, set this path as normal path
					    	File file = new File("backupPath.txt");
					        try {
					            file.createNewFile();
					        } catch (IOException e1) {
					            e1.printStackTrace();
					        }
					        if (file.exists()) {
					            PrintWriter writer = null;
					            try {
					                writer = new PrintWriter(file, "UTF-8");
					            } catch (FileNotFoundException e1) {
					                e1.printStackTrace();
					            } catch (UnsupportedEncodingException e1) {
					                e1.printStackTrace();
					            }
					            writer.println(path);
					            writer.close();
					        }
					        JOptionPane.showMessageDialog(null,"Backup and Restore location has been changed to " + path + ".");
					        BackupAndRestore br = new BackupAndRestore();
					        br.setBackupRestorePath();
					        br.initialize();
							br.backupDB();
						}
						else{
							JOptionPane.showMessageDialog(null,"Invalid Password.","Error",JOptionPane.ERROR_MESSAGE);
						}
					}	    	
			    }
			    else {
			    	//System.out.println("No Selection ");
			    }
			}
		});
		btnChangeBackupAnd.setBounds(430, 397, 320, 29);
		panel_home.add(btnChangeBackupAnd);
		*/
		//Staff
		//Employees staff = new Employees();
		//JPanel panel_staff = staff.getWindow();
		//tabbedPane.addTab("Staff", panel_staff);
		loadCashier();
	}
	public void loadCashier(){
		JPanel panel_cashier = new JPanel();
		tabbedPane.addTab("Cashier", null, panel_cashier, null);
		panel_cashier.setLayout(new BorderLayout(0,0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(0, 0, 1260, 650); //cashier tab
		panel_cashier.add(tabbedPane_1);
		
		JPanel cashier_submenu = new JPanel();
		tabbedPane_1.addTab("Cashier", null, cashier_submenu, null);
		cashier_submenu.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(0, 0, 748, 205);
		cashier_submenu.add(tabbedPane_2);
		
		JPanel item_info = new JPanel();
		tabbedPane_2.addTab("Scanned Item Details: ", null, item_info, null);
		item_info.setLayout(null);
		
		JTextPane productID = new JTextPane();
		productID.setText("Product ID: ");
		productID.setEditable(false);
		productID.setBounds(10, 11, 74, 20);
		productID.setBackground(Color.decode(defaultColor));
		item_info.add(productID);
		//productID.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 75, 727, 11);
		item_info.add(separator);
		
		JTextPane productName = new JTextPane();
		productName.setText("Product Name:");
		productName.setEditable(false);
		productName.setBounds(10, 52, 92, 20);
		productName.setBackground(Color.decode(defaultColor));
		item_info.add(productName);
		//productName.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 38, 727, 11);
		item_info.add(separator_1);
		
		JTextPane productDescription = new JTextPane();
		productDescription.setText("Product Description:");
		productDescription.setEditable(false);
		productDescription.setBounds(10, 91, 132, 20);
		productDescription.setBackground(Color.decode(defaultColor));
		item_info.add(productDescription);
		//productPrice.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 115, 727, 11);
		item_info.add(separator_2);
		
		JTextPane productQuantityRemaining = new JTextPane();
		productQuantityRemaining.setText("Quantity Remaining: ");
		productQuantityRemaining.setEditable(false);
		productQuantityRemaining.setBounds(10, 130, 132, 20);
		productQuantityRemaining.setBackground(Color.decode(defaultColor));
		item_info.add(productQuantityRemaining);
		//productQuantityPurchased.setColumns(10);
		
		textField_productID_input = new JTextField();
		textField_productID_input.setBounds(85, 9, 141, 20);;
		item_info.add(textField_productID_input);
		textField_productID_input.setColumns(10);
		
		//Places cursor in ID field as soon as page loads, like focus in html
		textField_productID_input.addAncestorListener(new AncestorListener() {
			
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void ancestorAdded(AncestorEvent event) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                textField_productID_input.requestFocusInWindow();
		            }
		        });
			}
		});
		
		//textField_productID_input.addKeyListener(this);		
		//ID listener, anything that gets entered goes here
		Thread t1 = new Thread(new Runnable(){
			public void run(){
				textField_productID_input.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						checkID();
					}
					@Override
					public void insertUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						checkID();
					}
					@Override
					public void changedUpdate(DocumentEvent e) {
						// TODO Auto-generated method stub
						checkID();
					}
					public void checkID(){
						String tempInput = textField_productID_input.getText();
						if (table.isEditing()){
							JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
							textField_productID_input.requestFocusInWindow();
						}
						/*else if(validateEmpty(tempInput) == false){
							//JOptionPane.showMessageDialog(null,"Product ID field cannot be Empty.","Error",JOptionPane.ERROR_MESSAGE);
							textField_name_input.setText(null);
							textField_description_input.setText(null);		
							textField_quantity_input.setText(null);
						}*/
						/*else if(containsSpace(tempInput) == true){
							JOptionPane.showMessageDialog(null,"Product ID cannot have space(s).","Error",JOptionPane.ERROR_MESSAGE);
							textField_productID_input.requestFocusInWindow();
							textField_name_input.setText(null);
							textField_description_input.setText(null);		
							textField_quantity_input.setText(null);
						}*/
						/*else if(checkForNumbers(tempInput) == false){
							JOptionPane.showMessageDialog(null,"Product ID entered must contain numbers only.","Error",JOptionPane.ERROR_MESSAGE);
							textField_productID_input.requestFocusInWindow();
							textField_name_input.setText(null);
							textField_description_input.setText(null);		
							textField_quantity_input.setText(null);
						}
						else if(tempInput.length() < idLength){
							textField_name_input.setText(null);
							textField_description_input.setText(null);		
							textField_quantity_input.setText(null);
						}
						else if(tempInput.length() > idLength){
							JOptionPane.showMessageDialog(null,"Length of Product ID is not valid.","Error",JOptionPane.ERROR_MESSAGE);
							textField_name_input.setText(null);
							textField_description_input.setText(null);		
							textField_quantity_input.setText(null);
						}*/
						else if(tempInput.trim().matches("^([0-9]{"+idLength+"})$")){
							int tempInt = Integer.parseInt(tempInput);
							Cashier cashierProductByID = new Cashier();
							tempProductSearch = cashierProductByID.findProductID(tempInt);
							
							Runnable doHighlight = new Runnable() {
								@Override
								public void run() {
									if(!table.isEditing()){
										if(tempProductSearch != null){
											textPane_productID_notFound.setText("");
											if(tempProductSearch.getQuantity() <= 0){
												JOptionPane.showMessageDialog(null,"" + '"'+ tempProductSearch.getName() + '"' + ", is currently out of stock.");
												textField_name_input.setText(tempProductSearch.getName());
												textField_description_input.setText(tempProductSearch.getDescription());		
												textField_quantity_input.setText(String.valueOf(tempProductSearch.getQuantity()));
											}
											else{
												//Check if the product exists in table
												//If it does, adjust the quantity and send it
												//Else send it without adjusting
												if(productBySearch.size() > 0){
													for(int j = 0; j < model.getRowCount(); j++){
														if(productBySearch.get(j).getID() == tempProductSearch.getID()){
															//System.out.println(productBySearch.get(j).getName() + " found in vector");
															tempProductSearch.setQuantity(productBySearch.get(j).getQuantity());
															//System.out.println(tempProductSearch.getQuantity());
															break;
														}
														else{
															//System.out.println("Not Found in vector");
														}
													}
												}
												
												if(tempProductSearch != null){
													if(tempProductSearch.getQuantity() <= 0){
														JOptionPane.showMessageDialog(null,"Inventory quantity for product " + '"'+ tempProductSearch.getName() + '"' + ", has reached 0. This product is no longer in stock.");
														textField_name_input.setText(tempProductSearch.getName());
														textField_description_input.setText(tempProductSearch.getDescription());		
														textField_quantity_input.setText(String.valueOf(tempProductSearch.getQuantity()));
													}
													else if(tempProductSearch.getQuantity() > 0){
														boolean qCheck = false;
														if(model.getRowCount() == 0){
															model.addRow(new Object[]{rowCount,tempProductSearch.getID(),tempProductSearch.getName(),quantity,tempProductSearch.getSalePrice(),(tempProductSearch.getSalePrice()*quantity)});
															//System.out.println("Before: " + tempProductSearch.getQuantity());
															tempProductSearch.setQuantity(tempProductSearch.getQuantity()-1);
															//System.out.println("After: " + tempProductSearch.getQuantity());
														}
														else if(model.getRowCount() > 0){
															for (int i = model.getRowCount()-1; i >= 0; --i) {
																//String tName = model.getValueAt(i, productName_column).toString();
																int tID = (int) model.getValueAt(i,id_column);
																if(!table.isEditing()){
																	String tQ = model.getValueAt(i, productQuantity_column).toString();
																	int tempQ = Integer.parseInt(tQ);
																	double tempP = tempProductSearch.getSalePrice();
																	if(tID == tempProductSearch.getID()){
																		tempProductSearch.setQuantity(tempProductSearch.getQuantity()-1);
																		tempQ++;
																		tempP = tempP * tempQ;
																		tempP = Math.round(tempP * 100.0) / 100.0;
																		model.setValueAt(tempQ, i, productQuantity_column);
																		model.setValueAt(tempP, i, productQuantityAndPrice_column);
																		rowCount--;
																		qCheck = true;
																		break;
																	}
																	else{
																		qCheck = false;
																	}
																}
															}
															if(qCheck == false){
																model.addRow(new Object[]{rowCount,tempProductSearch.getID(),tempProductSearch.getName(),quantity,tempProductSearch.getSalePrice(),(tempProductSearch.getSalePrice()*quantity)});
																//System.out.println("Before: " + tempProductSearch.getQuantity());
																tempProductSearch.setQuantity(tempProductSearch.getQuantity()-1);
																//System.out.println("After: " + tempProductSearch.getQuantity());												
															}
														}
														
														//Setting Checkbox
														TableColumn tc = table.getColumnModel().getColumn(productRemove_column);
														tc.setCellEditor(table.getDefaultEditor(Boolean.class));
														tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

														boolean eCheck = false;
														for(int i = 0; i < productBySearch.size(); i++){
															if(productBySearch.size() > 0){
																if(tempProductSearch.getID() == productBySearch.get(i).getID()){
																	productBySearch.get(i).setQuantity(tempProductSearch.getQuantity());
																	String tQ = model.getValueAt(i, productQuantity_column).toString();
																	int tempQ = Integer.parseInt(tQ);
																	previousValue.insertElementAt(tempQ, i);
																	//Moves table down as scroll bar appears and more items get added
																	table.scrollRectToVisible(table.getCellRect(i, 0, true));
																	eCheck = true;
																	break;
																}
																else{
																	eCheck = false;
																}
															}
														}
														if(eCheck == false){
															productBySearch.add(tempProductSearch);
															previousValue.add(quantity);
															
															//Moves table down as scroll bar appears and more items get added
															table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
														}
														
														textField_productID_input.setText("");
														textField_name_input.setText(tempProductSearch.getName());
														textField_description_input.setText(tempProductSearch.getDescription());		
														textField_quantity_input.setText(String.valueOf(tempProductSearch.getQuantity()));
														
														//Sales Total
														subTotal += tempProductSearch.getSalePrice();
														
														//subTotal += Double.parseDouble(s);
														subTotal = Math.round(subTotal * 100.0) / 100.0;
														subtotal_textField.setText("Subtotal: $" + subTotal);
														
														tax = subTotal * 0.13;
														tax = Math.round(tax * 100.0) / 100.0;
														tax_textField.setText("Tax: $" + tax);
														
														total = subTotal + tax;
														total = Math.round(total * 100.0) / 100.0;
														total_textField.setText("Total: $" + total);									
														
														//Moves table down as scroll bar appears and more items get added
														//table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
														
														//Leave increments at the end
														rowCount++;
														c++;
													}
												}
												else{
													textField_name_input.setText(null);
													textField_description_input.setText(null);		
													textField_quantity_input.setText(null);
												}
											}
										}
										else{
											textPane_productID_notFound.setText("- Product '" + textField_productID_input.getText().trim() + "' could not be found.");
										}
									}
								}
							};
							SwingUtilities.invokeLater(doHighlight);
						}
						/*else if(tempInput.length() == idLength){
							
						}*/
					}
				});
			}
		});
		t1.start();
		
		textField_name_input = new JTextPane();
		textField_name_input.setEditable(false);
		textField_name_input.setBounds(105, 52, 607, 20);
		textField_name_input.setBackground(Color.decode(defaultColor));
		item_info.add(textField_name_input);
		//textField_name_input.setColumns(10);
		textField_name_input.setEditable(false);
	
		textField_description_input = new JTextPane();
		textField_description_input.setEditable(false);
		textField_description_input.setBounds(142, 90, 579, 20);
		textField_description_input.setBackground(Color.decode(defaultColor));
		textField_description_input.setEditable(false);
		item_info.add(textField_description_input);
		
		textField_quantity_input = new JTextPane();
		textField_quantity_input.setEditable(false);
		textField_quantity_input.setBounds(142, 129, 579, 20);
		textField_quantity_input.setBackground(Color.decode(defaultColor));
		textField_quantity_input.setEditable(false);
		item_info.add(textField_quantity_input);
		
		textPane_productID_notFound = new JTextPane();
		textPane_productID_notFound.setEditable(false);
		textPane_productID_notFound.setBounds(238, 11, 483, 20);
		textPane_productID_notFound.setBackground(Color.decode(defaultColor));
		item_info.add(textPane_productID_notFound);
		//textField_quantity_input.setColumns(10);
		
		JTabbedPane tabbedPane_3 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_3.setBounds(962, 404, 277, 205);
		cashier_submenu.add(tabbedPane_3);
		
		JPanel cashier_commands = new JPanel();
		tabbedPane_3.addTab("Cashier Commands: ", null, cashier_commands, null);
		cashier_commands.setLayout(null);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(0, 47, 329, 20);
		cashier_commands.add(separator_4);
		
		btnCancelItem = new JButton("Cancel item");
		btnCancelItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Checkbox
				if(model.getRowCount() > 0){
					int items = 0;
					for(int i = 0; i < model.getRowCount(); i++){
						try{
							if((boolean) model.getValueAt(i, productRemove_column) == true){
								items++;
							}
						}catch(Exception e1){}
					}
					if(!table.isEditing()){
						if(items > 0){
							String message = "Are you sure you would like to delete the selected items (" + items + ")?";
							Object[] options = {"Yes","No"};
							int n = JOptionPane.showOptionDialog(null,message,"Confirm", 
									JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
							
							if (n == JOptionPane.YES_OPTION){
								if(model.getRowCount() > 0){
									for (int i = model.getRowCount()-1; i >= 0; --i) {
										try{
											boolean b = (boolean) model.getValueAt(i, productRemove_column);
											if (b == true){
												//Sales Total
												String s = table.getValueAt(i, productPrice_column).toString();
												String qCancel = table.getValueAt(i, productQuantity_column).toString();
												
												double t = Double.parseDouble(s) * Integer.parseInt(qCancel);
												
												subTotal -= t;
												subTotal = Math.round(subTotal * 100.0) / 100.0;
												subtotal_textField.setText("Subtotal: $" + subTotal);
			
												tax = subTotal * 0.13;
												tax = Math.round(tax * 100.0) / 100.0;
												tax_textField.setText("Tax: $" + tax);
													
												total = subTotal + tax;
												total = Math.round(total * 100.0) / 100.0;
												total_textField.setText("Total: $" + total);	
												
												if(productBySearch.size() > 0){
													for(int j = 0; j < productBySearch.size(); j++){
														//Convert to string, other values always cause problem due to object type
														int idValue = (int) model.getValueAt(i, id_column);
														String idValueString = String.valueOf(idValue);
														String productTempID = String.valueOf(productBySearch.get(j).getID());
														if(idValueString.equals(productTempID)){
															//Removes product from vector and its previous value
															productBySearch.remove(j);
															previousValue.remove(j);
															
														}
													}
												}
												//Delete at end & put focus back to ID field
												model.removeRow(i);
												textField_productID_input.requestFocusInWindow();
												
												//Empty the product details field
												textField_productID_input.setText("");
												textField_name_input.setText("");
												textField_description_input.setText("");		
												textField_quantity_input.setText("");
											}
										}catch(Exception e2){}
									}
									//After deleting, fixes the number order in table
									if(model.getRowCount() > 0){
										for (int i = model.getRowCount()-1; i >= 0; --i) {
							            	model.setValueAt(i+1, i, 0);
							            }
									}
									if(model.getRowCount() == 0){
										rowCount = 1;
									}
								}
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Please select atleast one item in purchase list to remove it.");
							textField_productID_input.requestFocusInWindow();
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
						textField_productID_input.requestFocusInWindow();
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Please add an item to purchase list.");
					textField_productID_input.requestFocusInWindow();
				}
			}
		});
		btnCancelItem.setBounds(6, 7, 120, 40);
		cashier_commands.add(btnCancelItem);
		
		btnDiscountDetails = new JButton("Discount Details");
		btnDiscountDetails.setVisible(false);
		
		button_discount = new JButton("Discount");
		button_discount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(oneTimeDiscountCheck == true){
					JOptionPane.showMessageDialog(null,"One time discount has been already applied for this transaction.");
				}
				else if (model.getRowCount() <= 0){
					JOptionPane.showMessageDialog(null,"Please add an item to purchase list before making a discount.");
					textField_productID_input.requestFocusInWindow();
				}
				else{
					//MOD FRAME
					if(!table.isEditing()){
						loadDiscountFrame(e);
					}
					else{
						JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
					}
				}
			}	
		});
		button_discount.setBounds(6, 58, 120, 40);
		cashier_commands.add(button_discount);
		
		//Discount details
		//Initializing Discount details button
		btnDiscountDetails.setBounds(6, 58, 120, 40);
		cashier_commands.add(btnDiscountDetails);
		btnDiscountDetails.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel_discount2 = new JPanel();
				panel_discount2.setLayout(null);
				
				//Discount details
				JTabbedPane tabbedPane_discount = new JTabbedPane(JTabbedPane.TOP);
				tabbedPane_discount.setBounds(60, 10, 400, 300);
				panel_discount2.add(tabbedPane_discount);
							
				JPanel panel_discount_details = new JPanel();
				tabbedPane_discount.addTab("Discount details:", null, panel_discount_details, null);
				panel_discount_details.setLayout(null);
							
				//Text
				JTextPane discount_message1 = new JTextPane();
				discount_message1.setText("Discount ID: ");
				discount_message1.setEditable(false);
				discount_message1.setBounds(10, 13, 83, 20);
				discount_message1.setBackground(Color.decode(defaultColor));
				panel_discount_details.add(discount_message1);
				
				JTextPane detail_discountID_message2 = new JTextPane();
				detail_discountID_message2.setText(String.valueOf(detail_discountID));
				detail_discountID_message2.setEditable(false);
				detail_discountID_message2.setBounds(100, 13, 250, 20);
				detail_discountID_message2.setBackground(Color.decode(defaultColor));
				panel_discount_details.add(detail_discountID_message2);
				
				JTextPane discount_message3 = new JTextPane();
				discount_message3.setText("Discount Type: ");
				discount_message3.setEditable(false);
				discount_message3.setBounds(10, 45, 100, 20);
				discount_message3.setBackground(Color.decode(defaultColor));
				panel_discount_details.add(discount_message3);
				
				JTextPane detail_discounttype_message4 = new JTextPane();
				detail_discounttype_message4.setText(detail_discountType);
				detail_discounttype_message4.setEditable(false);
				detail_discounttype_message4.setBounds(110, 45, 250, 20);
				detail_discounttype_message4.setBackground(Color.decode(defaultColor));
				panel_discount_details.add(detail_discounttype_message4);
				
				JTextPane discount_message5 = new JTextPane();
				discount_message5.setText("Discount Value: ");
				discount_message5.setEditable(false);
				discount_message5.setBounds(10, 80, 105, 20);
				discount_message5.setBackground(Color.decode(defaultColor));
				panel_discount_details.add(discount_message5);
				
				JTextPane detail_discountValue_message6 = new JTextPane();
				detail_discountValue_message6.setText("$" + String.valueOf(detail_discountValue));
				detail_discountValue_message6.setEditable(false);
				detail_discountValue_message6.setBounds(115, 80, 230, 20);
				detail_discountValue_message6.setBackground(Color.decode(defaultColor));
				panel_discount_details.add(detail_discountValue_message6);
				
				JButton discountdetail_remove = new JButton("Remove Discount");
				discountdetail_remove.setBounds(7, 120, 185, 29);
				panel_discount_details.add(discountdetail_remove);
				discountdetail_remove.requestFocusInWindow();
				discountdetail_remove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						d8.dispose();
						btnDiscountDetails.setVisible(false);
						button_discount.setVisible(true);
						oneTimeDiscountCheck = false;
						
						subTotal = (subTotal + detail_discountValue);
						detail_discountValue = 0;
						
						subTotal = Math.round(subTotal * 100.0) / 100.0;
						subtotal_textField.setText("Subtotal: $" + subTotal);

						tax = subTotal * 0.13;
						tax = Math.round(tax * 100.0) / 100.0;
						tax_textField.setText("Tax: $" + tax);
								
						total = subTotal + tax;
						total = Math.round(total * 100.0) / 100.0;
						total_textField.setText("Total: $" + total);
					}
				});
				
				//Cancel button
				JButton discountdetail_btnCancel = new JButton("Cancel");
				discountdetail_btnCancel.setBounds(190, 120, 185, 29);
				panel_discount_details.add(discountdetail_btnCancel);
				discountdetail_btnCancel.requestFocusInWindow();
				discountdetail_btnCancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						d8.dispose();
						textField_productID_input.requestFocusInWindow();
					}
				});
				
				Component component = (Component) e.getSource();
		        JFrame topFrame = (JFrame) SwingUtilities.getRoot(component);
				d8 = new JDialog(topFrame, "", Dialog.ModalityType.DOCUMENT_MODAL);
				d8.getContentPane().add(panel_discount2);
				d8.setBounds(0, 0, 500, 650);
				d8.setLocationRelativeTo(null);
				//d8.getRootPane().setDefaultButton(discountOption_btnEnter);
				d8.setVisible(true);
			}
		});
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 100, 329, 20);
		cashier_commands.add(separator_3);
		
		JButton btnFindByName = new JButton("Find by Name");
		btnFindByName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Find Product by Name
				try{
					if(!table.isEditing()){
						JPanel findByName_panel = new JPanel();
						findByName_panel.setLayout(null);
									
						//Product Name
						JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
						tabbedPane.setBounds(250, 10, 400, 200);
						findByName_panel.add(tabbedPane);
									
						JPanel panel_searchDetail = new JPanel();
						tabbedPane.addTab("Find Product using Name, Description, Notes:", null, panel_searchDetail, null);
						panel_searchDetail.setLayout(null);
									
						//Text
						JTextPane txtpnsearch_1 = new JTextPane();
						txtpnsearch_1.setText("Search:");
						txtpnsearch_1.setBackground(Color.decode(defaultColor));
						txtpnsearch_1.setBounds(10, 13, 50, 18);
						panel_searchDetail.add(txtpnsearch_1);
									
						//Messages
						txtpnsearch_2 = new JTextPane();
						txtpnsearch_2.setBackground(Color.decode(defaultColor));
						txtpnsearch_2.setBounds(10, 80, 370, 120);
						panel_searchDetail.add(txtpnsearch_2);
									
						//Textfield input
						JTextField textField_search_input = new JTextField();
						textField_search_input.setBounds(60, 6, 310, 30);
						panel_searchDetail.add(textField_search_input);
						textField_search_input.setColumns(10);
									
						//Search button
						JButton product_search_button = new JButton("Search");
						product_search_button.setBounds(3, 45, 190, 29);
						panel_searchDetail.add(product_search_button);
									
						//Cancel button
						JButton product_cancel_button = new JButton("Cancel");
						product_cancel_button.setBounds(190, 45, 185, 29);
						panel_searchDetail.add(product_cancel_button);
						
						//Add button
						JButton product_add_button = new JButton("Add");
						product_add_button.setBounds(38, 575, 820, 40);
						findByName_panel.add(product_add_button);
									
						//Places cursor in ID field as soon as page loads, like focus in html
						textField_search_input.addAncestorListener(new AncestorListener() {
							@Override
							public void ancestorRemoved(AncestorEvent event) {
								// TODO Auto-generated method stub
							}
							@Override
							public void ancestorMoved(AncestorEvent event) {
								// TODO Auto-generated method stub	
							}
							@Override
							public void ancestorAdded(AncestorEvent event) {
								// TODO Auto-generated method stub
							SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										textField_search_input.requestFocusInWindow();
									}
								});
							}
						});
									
						//Table
						JTabbedPane tabbedPane_search_table = new JTabbedPane(JTabbedPane.TOP);
						tabbedPane_search_table.setBounds(30, 204, 835, 375);
						findByName_panel.add(tabbedPane_search_table);
									
						JPanel panel_table_search = new JPanel(new BorderLayout());
						tabbedPane_search_table.addTab("List of suggestions: ", null, panel_table_search, null);
									
						Vector<String> search_row_data = new Vector<String>();
						Vector<String> search_column_name = new Vector<String>();
						search_column_name.addElement("#");
						search_column_name.addElement("Name");
						search_column_name.addElement("Description");
						search_column_name.addElement("Quantity Remaining");
						search_column_name.addElement("Sale Price");
						search_column_name.addElement("Notes");
						search_column_name.addElement("Add");
						
						JTable table_search = new JTable(search_row_data, search_column_name){
							public boolean isCellEditable(int row, int column) {
								//Return true if the column (number) is editable, else false
								//if(column == 3 || column == 6){ 
								if(column == 6){
									return true;
								}
								else{
									return false;
								}
							}
						};
						panel_table_search.add(table_search.getTableHeader(), BorderLayout.NORTH);
						panel_table_search.add(table_search, BorderLayout.CENTER);
						
						//Row Height
						table_search.setRowHeight(30);
						
						//Column Width
						TableColumnModel columnModel_search = table_search.getColumnModel();
						columnModel_search.getColumn(0).setPreferredWidth(1); //#
						columnModel_search.getColumn(1).setPreferredWidth(110); //Name
						columnModel_search.getColumn(2).setPreferredWidth(100); //Description
						columnModel_search.getColumn(3).setPreferredWidth(1); //Quantity 
						columnModel_search.getColumn(4).setPreferredWidth(30); //Sale Price
						columnModel_search.getColumn(5).setPreferredWidth(75); //Notes
						columnModel_search.getColumn(6).setPreferredWidth(10); //Add/Remove
						
						//Columns won't be able to moved around
						table_search.getTableHeader().setReorderingAllowed(false);
						
						//Center table data 
						DefaultTableCellRenderer centerRenderer_search = new DefaultTableCellRenderer();
						centerRenderer_search.setHorizontalAlignment( SwingConstants.CENTER );
						table_search.getColumnModel().getColumn(0).setCellRenderer( centerRenderer_search ); //ID
						table_search.getColumnModel().getColumn(1).setCellRenderer( centerRenderer_search ); //Product ID
						table_search.getColumnModel().getColumn(2).setCellRenderer( centerRenderer_search ); //Name
						table_search.getColumnModel().getColumn(3).setCellRenderer( centerRenderer_search ); //Quantity
						table_search.getColumnModel().getColumn(4).setCellRenderer( centerRenderer_search ); //Price
						table_search.getColumnModel().getColumn(5).setCellRenderer( centerRenderer_search ); //Quantity * Price
						table_search.getColumnModel().getColumn(6).setCellRenderer( centerRenderer_search ); //Remove
						
						//Center table column names
						centerRenderer_search = (DefaultTableCellRenderer) table_search.getTableHeader().getDefaultRenderer();
						centerRenderer_search.setHorizontalAlignment(JLabel.CENTER);
						
						JScrollPane jsp2 = new JScrollPane(table_search);
						jsp2.setBounds(2, 2, 810, 344);
						jsp2.setVisible(true);
						panel_table_search.add(jsp2);
						
						//Setting Checkbox
						TableColumn tc2 = table_search.getColumnModel().getColumn(productRemove_column);
						tc2.setCellEditor(table_search.getDefaultEditor(Boolean.class));
						tc2.setCellRenderer(table_search.getDefaultRenderer(Boolean.class));
						
						DefaultTableModel model_search = (DefaultTableModel) table_search.getModel();
						
						TableRowSorter<TableModel> sorter = new TableRowSorter<>(table_search.getModel());
						table_search.setRowSorter(sorter);
						
						//Disable column
						sorter.setSortable(0, false);
						sorter.setSortable(4, false);
						
						//Button listener
						product_search_button.addActionListener(new ActionListener() {
						
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								String productNameInput = textField_search_input.getText();
								productNameInput.trim();
								if(validateEmpty(productNameInput) == false){
									JOptionPane.showMessageDialog(null,"Search cannot be empty. Please enter a product name, description or notes.");		
									fillTableFindByName(model_search);
								}
								else if(productNameInput.trim().matches("^[-0-9A-Za-z.,'() ]*$")){
									Cashier cashierProductByName = new Cashier();
									
									for (int i = model_search.getRowCount()-1; i >= 0; --i) {
										model_search.removeRow(i);
									}
									productByLike = new Vector<Product>();
									cashierProductByName.findProductUsingLike(productNameInput.trim(),productByLike);
									
									if(productByLike.size() > 0){
										txtpnsearch_2.setText("");
										for(int i = 0; i < productByLike.size(); i++){
											if(productBySearch.size() > 0){
												for(int j = 0; j < productBySearch.size(); j++){
													if(productByLike.get(i).getID() == productBySearch.get(j).getID()){
														productByLike.get(i).setQuantity(productBySearch.get(j).getQuantity());
													}
													else{
														//No match
													}
												}
											}
											else{
												//Printing it in the outside loop, the value doesn't alter here, values that alter will and will print them outside
											}
										}
									}
									else{
										txtpnsearch_2.setText("No product was found in inventory.");
									}
									if(productByLike.size() > 0){
										for(int k = 0; k < productByLike.size(); k++){
											model_search.addRow(new Object[]{k+1,productByLike.get(k).getName(),productByLike.get(k).getDescription(),productByLike.get(k).getQuantity(),productByLike.get(k).getSalePrice(),productByLike.get(k).getNotes()});
										}
									}
								}
							}
						});
						
						//Cancel button closes the window
						product_cancel_button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								d5.dispose();
								textField_productID_input.requestFocusInWindow();
							}
						});
						
						//Checking for double click
						table_search.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent e) {
								if (e.getClickCount() == 1) {
									//System.out.println("1");
								}
								if (e.getClickCount() == 2) {
									//JTable target = (JTable)e.getSource();
									int row = table_search.getSelectedRow();
									int column = table_search.getSelectedColumn();
									if(column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5){
										if(row > -1){
											String n = (String) model_search.getValueAt(row, 1);
											if(productByLike.size() > 0){
												for(int i = 0; i < productByLike.size(); i++){
													if(n.equals(productByLike.get(i).getName())){ //maybe change to using ID
														int items = 0;
														try{
															boolean b = (boolean) model_search.getValueAt(i, productRemove_column);
															if(b == true){
																items++;
															}
														}catch(Exception e2){}
														if(items == 0){
															textField_productID_input.setText(String.valueOf(productByLike.get(i).getID()));
															d5.dispose();
															textField_productID_input.requestFocusInWindow();
															break;
														}
														else{
															txtpnsearch_2.setText("Please use the add button at the bottom use add multiple items at a time.");
														}
													}
												}
											}
										}
									}
									//Quantity
									else if(column == 3){
										table_search.setRowSelectionInterval(row, row);
										table_search.setColumnSelectionInterval(0, 0);
										textField_search_input.requestFocusInWindow();
									}
									//Checkbox
									else if(column == 6){
										
									}
								}
							}
						});
						
						//Add button listener
						Runnable runAdd = new Runnable() {
							@Override
							public void run() {
								product_add_button.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										Vector<Integer> searchID = new Vector<Integer>();
										if(model_search.getRowCount() > 0){
											txtpnsearch_2.setText("");
											for (int i = 0; i < model_search.getRowCount(); i++) {
												try{
													boolean b = (boolean) model_search.getValueAt(i, productRemove_column);
													if (b == true){
														for(int j = 0; j < productByLike.size(); j++){
															if(String.valueOf(model_search.getValueAt(i, 1)).equals(productByLike.get(j).getName())){
																searchID.add(productByLike.get(i).getID());
																d5.dispose();
																textField_productID_input.requestFocusInWindow();
																break;
															}
															else{
																//System.out.println("Else");
															}
														}
													}
												}catch(Exception e2){
													
												}
											}
										}
										else{
											txtpnsearch_2.setText("Table is empty. Please enter keywords into search bar.");
										}
										if(model_search.getRowCount() > 0){
											if(searchID.size() > 0){
												txtpnsearch_2.setText("");
												new Timer(1, new ActionListener() {
										            int it = 0;
										            @Override
										            public void actionPerformed(ActionEvent e2) {
										            	textField_productID_input.setText(String.valueOf(searchID.get(it)));
										                if (++it == searchID.size()){
										                    ((Timer)e2.getSource()).stop();
										                }
										            }
										        }).start();
											}
											else{
												txtpnsearch_2.setText("Please select/check a product before adding it to the table.");
											}
										}
									}
								});
							}
						};
						SwingUtilities.invokeLater(runAdd);
						
						fillTableFindByName(model_search);
						
						Component component = (Component) e.getSource();
						JFrame topFrame2 = (JFrame) SwingUtilities.getRoot(component);
						d5 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
						d5.getContentPane().add(findByName_panel);
						d5.setSize(900, 650);
						d5.setLocationRelativeTo(null);
						d5.getRootPane().setDefaultButton(product_search_button);
						d5.setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
					}
				}catch(Exception e1){}
			}
		});
		btnFindByName.setBounds(130, 7, 120, 40);
		cashier_commands.add(btnFindByName);
		
		JButton btnNewTransaction = new JButton("New Transaction");
		btnNewTransaction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Settings global variables to default
				if(productBySearch.size() > 0){
					productBySearch.clear();
				}
				if(productByLike == null){
					
				}
				else if(productByLike.size() > 0){
					previousValue.clear();
				}
				if(previousValue.size() > 0){
					previousValue.clear();
				}
				
				tempProductSearch = new Product();
				subtotal_textField.setText("Subtotal: $0.0");
				tax_textField.setText("Tax: $0.0");
				total_textField.setText("Total: $0.0");
				
				textField_productID_input.setText("");
				textField_name_input.setText("");
				textField_description_input.setText("");
				textField_quantity_input.setText("");
				textPane_productID_notFound.setText("");
				
				//Table
				//private JTable table;
				removeTableRows(model); //Clears table rows, model.
				
				if(data.size() > 0){
					data.clear();
				}
				tableListenerCount = 0;
				
				//Sales Total
				subTotal = 0;
				tax = 0;
				//taxValue = 0;
				total = 0;
				c = 0;
				stringTempPrice = null;
				transactionType = null;
				transactionMethod = null;
				promotionID = 0;
				
				//Discount
				detail_discountID = 0;
				detail_discountType = null;
				oneTimeDiscountCheck = false;
				//discount_option.setText("");
				detail_discountValue = 0;
				
				textField_productID_input.requestFocusInWindow();
			}
		});
		btnNewTransaction.setBounds(6, 110, 120, 40);
		cashier_commands.add(btnNewTransaction);
		
		JButton btnNewButton_1 = new JButton("Checkout");
		btnNewButton_1.setBounds(130, 110, 120, 40);
		cashier_commands.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!table.isEditing()){
					if(model.getRowCount() > 0){ // total > 0
						//Checkout
						//Increases button size
						UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,35)));
						Object[] options = {"Cancel","Credit","Cash"};
						int cashCredit = JOptionPane.showOptionDialog(null,"Checkout using: ","Checkout",
						    JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
						
						//Cancel
						if(cashCredit == JOptionPane.YES_OPTION){
							
						}
						//Credit
						else if(cashCredit == JOptionPane.NO_OPTION){
							
						}
						//Cash
						else if(cashCredit == JOptionPane.CANCEL_OPTION){
							loadCheckoutFrame(e);
						}
						//tabbedPane_7.setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(null,"Please add an item to purchase list before doing a checkout.");
					}
				
				}
				else{
					JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
				}
				textField_productID_input.requestFocusInWindow();
			}
		});
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		
		JTabbedPane tabbedPane_5 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_5.setBounds(760, 0, 198, 205);
		cashier_submenu.add(tabbedPane_5);
		
		JPanel sales_total = new JPanel();
		tabbedPane_5.addTab("Sales Total: ", null, sales_total, null);
		sales_total.setLayout(null);
		
		subtotal_textField = new JTextPane();
		subtotal_textField.setText("Subtotal: $" + subTotal);
		subtotal_textField.setEditable(false);
		subtotal_textField.setBounds(10, 11, 161, 20);
		subtotal_textField.setBackground(Color.decode(defaultColor));
		sales_total.add(subtotal_textField);
		//subtotal_textField.setColumns(10);
		
		tax_textField = new JTextPane();
		tax_textField.setText("Tax: $" + tax);
		tax_textField.setEditable(false);
		tax_textField.setBounds(10, 45, 103, 20);
		tax_textField.setBackground(Color.decode(defaultColor));
		sales_total.add(tax_textField);
		//tax_textField.setColumns(10);
		
		total_textField = new JTextPane();
		total_textField.setText("Total: $" + total);
		total_textField.setEditable(false);
		total_textField.setBounds(10, 80, 103, 20);
		total_textField.setBackground(Color.decode(defaultColor));
		sales_total.add(total_textField);
		//total_textField.setColumns(10);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(0, 30, 258, 20);
		sales_total.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(0, 65, 258, 20);
		sales_total.add(separator_6);
		
		JTabbedPane tabbedPane_4 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_4.setBounds(0, 205, 965, 404);
		cashier_submenu.add(tabbedPane_4);
		
		JPanel panel_table = new JPanel(new BorderLayout());
		tabbedPane_4.addTab("List of purchased items, quantity and price: ", null, panel_table, null);
		
		columnName.addElement("#");
		columnName.addElement("Product ID");
		columnName.addElement("Name");
		columnName.addElement("Quantity");
		columnName.addElement("Price");
		columnName.addElement("Quantity * Price");
		columnName.addElement("Remove");
		
		table = new JTable(data, columnName){
			public boolean isCellEditable(int row, int column) {
				//Return true if the column (number) is editable, else false
				if(column == 3 || column == 6){ 
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		panel_table.add(table.getTableHeader(), BorderLayout.NORTH);
		panel_table.add(table, BorderLayout.CENTER);
		
		//Row Height
	  	table.setRowHeight(30);
	  	
	    //Column Width
	  	TableColumnModel columnModel = table.getColumnModel();
	  	columnModel.getColumn(indent_column).setPreferredWidth(10); //ID
	 	columnModel.getColumn(id_column).setPreferredWidth(30); //Product ID
	  	columnModel.getColumn(productName_column).setPreferredWidth(240); //Name
        columnModel.getColumn(productQuantity_column).setPreferredWidth(10); //Quantity 
	  	columnModel.getColumn(productPrice_column).setPreferredWidth(30); //Price
	  	columnModel.getColumn(productQuantityAndPrice_column).setPreferredWidth(30); //Price * Quantity
	  	columnModel.getColumn(productRemove_column).setPreferredWidth(10); //Remove

	    //Columns won't be able to moved around
	  	table.getTableHeader().setReorderingAllowed(false);
	  	
	    //Center table data 
	  	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	  	centerRenderer.setHorizontalAlignment( SwingConstants.CENTER );
	  	table.getColumnModel().getColumn(indent_column).setCellRenderer( centerRenderer ); //ID
	  	table.getColumnModel().getColumn(id_column).setCellRenderer( centerRenderer ); //Product ID
	  	table.getColumnModel().getColumn(productName_column).setCellRenderer( centerRenderer ); //Name
	  	table.getColumnModel().getColumn(productQuantity_column).setCellRenderer( centerRenderer ); //Quantity
	  	table.getColumnModel().getColumn(productPrice_column).setCellRenderer( centerRenderer ); //Price
	  	table.getColumnModel().getColumn(productQuantityAndPrice_column).setCellRenderer( centerRenderer ); //Quantity * Price
	  	table.getColumnModel().getColumn(productRemove_column).setCellRenderer( centerRenderer ); //Remove
	  	
	    //Center table column names
	  	centerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
	  	centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	  	
		JScrollPane jsp = new JScrollPane(table);
		jsp.setBounds(2, 2, 810, 344);
		jsp.setVisible(true);
		panel_table.add(jsp);
	   
		model = (DefaultTableModel) table.getModel();
		
		//table.addKeyListener(this);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//Changes which product is selected in product details
				int row = table.getSelectionModel().getLeadSelectionIndex();
				if (e.getClickCount() == 1) {
					if(productBySearch.size() > 0){
		        		for(int i = 0; i < productBySearch.size(); i++){
		        			if(String.valueOf(model.getValueAt(row, id_column)).equals(String.valueOf(productBySearch.get(i).getID()))){
		        				textField_name_input.setText(productBySearch.get(i).getName());
		        				textField_description_input.setText(productBySearch.get(i).getDescription());
		        				textField_quantity_input.setText(String.valueOf(productBySearch.get(i).getQuantity()));
		        				break;
		        			}
		        		}
		        	}
				}
			}
		});
		table.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(model.getRowCount() > 0){
					//Sales Total
					int col = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
			        int row = table.getSelectionModel().getLeadSelectionIndex();
			        if(row > -1 && col > -1){
			        	if(col == productQuantity_column){
			        		Runnable run1 = new Runnable() {
								@Override
								public void run() {
									boolean check = true;
					        		//Previous Quantity
									int previousQuantity = 0;
									String nID = model.getValueAt(row, id_column).toString();
									for(int i = 0; i < productBySearch.size(); i++){
										if(nID.equals(String.valueOf(productBySearch.get(i).getID()))){
											previousQuantity = previousValue.get(i);
										}
									}
					        		if(model.getValueAt(row, col).equals("") || model.getValueAt(row, col) == null){
					        			JOptionPane.showMessageDialog(null,"The input for row #" + (row+1) + " is empty. Please enter a valid quantity.");
					        			check = false;
					        			model.setValueAt(previousQuantity, row, col);
										table.setRowSelectionInterval(row, row);
										table.setColumnSelectionInterval(0, 0);
					        		}
					        		else{
					        			String stringQuantity = model.getValueAt(row, col).toString();
					        			if(stringQuantity.length() > 8){
					        				JOptionPane.showMessageDialog(null,"The input '" + stringQuantity + "' for row #" + (row+1) + " is too long. Please enter a valid quantity.");
					        				model.setValueAt(previousQuantity, row, col);
											table.setRowSelectionInterval(row, row);
											table.setColumnSelectionInterval(0, 0);
					        				check = false;
					        			}
					        			else{
						        			for(int i = 0; i < stringQuantity.length(); i++){
						        				char c = stringQuantity.charAt(i);
						        				if(c == ' '){
						        					JOptionPane.showMessageDialog(null,"The input '" + stringQuantity + "' for row #" + (row+1) + " contains space. Please enter a valid quantity.");
						        					model.setValueAt(previousQuantity, row, col);
													table.setRowSelectionInterval(row, row);
													table.setColumnSelectionInterval(0, 0);
						        					check = false;
						        					break;
						        				}
						        			}
						        			if(check){
						        				stringQuantity = stringQuantity.trim();
						        				//Check if numbers only
						        				String regexStr = "^[0-9]+$";
						        				if(!stringQuantity.trim().matches(regexStr)){
						        					JOptionPane.showMessageDialog(null,"The input '" + stringQuantity + "' for row #" + (row+1) + " must contain numbers only. Please enter a valid quantity.");
						        					model.setValueAt(previousQuantity, row, col);
													table.setRowSelectionInterval(row, row);
													table.setColumnSelectionInterval(0, 0);
						        					check = false;
						        				}
						        				//Valid
						        				else{
						        					//Checking for first digit as 0
						        					char c = stringQuantity.charAt(0);
						        					char c2 = stringQuantity.charAt(stringQuantity.length()-1);
						        					if(c == '0' && c2 >= '0'){
						        						JOptionPane.showMessageDialog(null,"Please enter a quantity that does not begin with 0 for row #" + (row+1) + ".");
							        					model.setValueAt(previousQuantity, row, col);
														table.setRowSelectionInterval(row, row);
														table.setColumnSelectionInterval(0, 0);
						        						check = false;
						        					}
						        					// Doesn't contain 0 as first element
						        					else{
						        						int intQuantity = Integer.parseInt(stringQuantity);
						        						if(intQuantity > 0){
						        							//System.out.println("more than 0");
						        							//
						        							String stringProductPrice = model.getValueAt(row, productPrice_column).toString();
						        							
						        							double doubleProductPrice = Double.parseDouble(stringProductPrice);
						        							//doubleProductPrice = Math.round(doubleProductPrice * 100.0) / 100;
						        							
						        							//System.out.println("Price: " + doubleProductPrice);
						        							
						        							double tempTotal = (intQuantity * doubleProductPrice);
						        							tempTotal = Math.round(tempTotal * 100.0) / 100.0;
						        							
						        							//System.out.println("Sum: " + tempTotal);
						        							
						        							boolean valid = calculateInventoryQuantity();
						        							if(valid == true){
						        								model.setValueAt(tempTotal, row, productQuantityAndPrice_column); // causes it to call twice
						        							}
						        							
						        							if(tableListenerCount == 1){
						        								if(valid == true){
						        									//System.out.println("Sum: " + tempTotal);
						        									calculateSubtotal();
						        								}
															}
															else if(tableListenerCount == 0){
																stringTempPrice = model.getValueAt(row, productQuantityAndPrice_column).toString();
																tableListenerCount = 0;
															}
															else{
																tableListenerCount = 0;
															}
															tableListenerCount++;
						        						}
						        						//Enter amount more than 0
						        						else{
						        							JOptionPane.showMessageDialog(null,"Please enter a quantity above 0 for row #" + (row+1) + ".");
															model.setValueAt(1, row, col);
															table.setRowSelectionInterval(row, row);
															table.setColumnSelectionInterval(0, 0);
						        							check = false;
						        						}
						        					}
						        				}
						        			}
					        			}
					        		}
					        		//After entering value, movies selection to next column (does not leave user in same colum)
									table.setRowSelectionInterval(row, row);
									table.setColumnSelectionInterval(0, 0);
									textField_productID_input.requestFocusInWindow();
								}
							};
							SwingUtilities.invokeLater(run1);
			        	}
			        }
				}
			}
		});
		
		tabbedPane_7 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_7.setBounds(961, 0, 279, 416);
		cashier_submenu.add(tabbedPane_7);
		
		keypad_panel = new JPanel();
		tabbedPane_7.addTab("Keypad: ", null, keypad_panel, null);
		keypad_panel.setLayout(null);
		
		keypad_1 = new JButton("1");
		keypad_1.setFocusable(false);
		keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_1.setBounds(0, 0, 87, 93);
		keypad_panel.add(keypad_1);
		
		panel_refund = new JPanel();
		tabbedPane_1.addTab("Refund", null, panel_refund, null);
		panel_refund.setLayout(null);
		
		JTabbedPane tabbedPane_8 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_8.setBounds(0, 0, 673, 205);
		panel_refund.add(tabbedPane_8);
		
		JPanel panel_1 = new JPanel();
		tabbedPane_8.addTab("Find Transaction:", null, panel_1, null);
		panel_1.setLayout(null);
		
		JTextPane txtpnTransactionId = new JTextPane();
		txtpnTransactionId.setEditable(false);
		txtpnTransactionId.setText("Transaction ID:");
		txtpnTransactionId.setBounds(10, 11, 96, 16);
		txtpnTransactionId.setBackground(Color.decode(defaultColor));
		panel_1.add(txtpnTransactionId);
		
		textField_transaction_input = new JTextField();
		textField_transaction_input.setBounds(114, 9, 141, 20);
		panel_1.add(textField_transaction_input);
		textField_transaction_input.setColumns(10);
		
		//Places cursor in ID field as soon as page loads, like focus in html
		textField_transaction_input.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorRemoved(AncestorEvent event) {
				// TODO Auto-generated method stub
			}
			@Override
			public void ancestorMoved(AncestorEvent event) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void ancestorAdded(AncestorEvent event) {
				// TODO Auto-generated method stub
				SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		            	textField_transaction_input.requestFocusInWindow();
		            }
				});
			}
		});
		
		JButton transaction_search_button = new JButton("Search");
		transaction_search_button.setBounds(256, 6, 117, 29);
		panel_1.add(transaction_search_button);
		
		//panel_refund.getRootPane().setDefaultButton(transaction_search_button);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(0, 38, 727, 11);
		panel_1.add(separator_7);
		
		JTextPane productName_refund = new JTextPane();
		productName_refund.setText("Product Name:");
		productName_refund.setEditable(false);
		productName_refund.setBackground(SystemColor.window);
		productName_refund.setBounds(10, 52, 92, 20);
		panel_1.add(productName_refund);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(0, 75, 727, 11);
		panel_1.add(separator_8);
		
		textField_refund_name = new JTextPane();
		textField_refund_name.setEditable(false);
		textField_refund_name.setBackground(SystemColor.window);
		textField_refund_name.setBounds(105, 52, 616, 20);
		panel_1.add(textField_refund_name);
		
		JTextPane productDescription_refund = new JTextPane();
		productDescription_refund.setText("Product Description:");
		productDescription_refund.setEditable(false);
		productDescription_refund.setBackground(SystemColor.window);
		productDescription_refund.setBounds(10, 91, 132, 20);
		panel_1.add(productDescription_refund);
		
		textField_refund_description_input = new JTextPane();
		textField_refund_description_input.setEditable(false);
		textField_refund_description_input.setBackground(SystemColor.window);
		textField_refund_description_input.setBounds(142, 90, 579, 20);
		panel_1.add(textField_refund_description_input);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(0, 115, 727, 11);
		panel_1.add(separator_9);
		
		JTextPane txtpnProductRemaining = new JTextPane();
		txtpnProductRemaining.setText("Quantity Remaining:");
		txtpnProductRemaining.setEditable(false);
		txtpnProductRemaining.setBackground(SystemColor.window);
		txtpnProductRemaining.setBounds(10, 130, 132, 20);
		panel_1.add(txtpnProductRemaining);
		
		textField_refund_quantity_remaining_input = new JTextPane();
		textField_refund_quantity_remaining_input.setEditable(false);
		textField_refund_quantity_remaining_input.setBackground(SystemColor.window);
		textField_refund_quantity_remaining_input.setBounds(142, 129, 64, 20);
		panel_1.add(textField_refund_quantity_remaining_input);
		
		JSeparator separator_10 = new JSeparator();
		separator_10.setOrientation(SwingConstants.VERTICAL);
		separator_10.setBounds(217, 120, 12, 39);
		panel_1.add(separator_10);
		
		JTextPane txtpnProductUnitPrice = new JTextPane();
		txtpnProductUnitPrice.setText("Unit Price:");
		txtpnProductUnitPrice.setEditable(false);
		txtpnProductUnitPrice.setBackground(SystemColor.window);
		txtpnProductUnitPrice.setBounds(241, 130, 64, 20);
		panel_1.add(txtpnProductUnitPrice);
		
		textField_refund_product_unitPrice_input = new JTextPane();
		textField_refund_product_unitPrice_input.setEditable(false);
		textField_refund_product_unitPrice_input.setBackground(SystemColor.window);
		textField_refund_product_unitPrice_input.setBounds(310, 130, 104, 20);
		panel_1.add(textField_refund_product_unitPrice_input);
		
		JSeparator separator_11 = new JSeparator();
		separator_11.setOrientation(SwingConstants.VERTICAL);
		separator_11.setBounds(418, 122, 12, 39);
		panel_1.add(separator_11);
		
		JTextPane txtpnProductTotalPrice = new JTextPane();
		txtpnProductTotalPrice.setText("Row Price:");
		txtpnProductTotalPrice.setEditable(false);
		txtpnProductTotalPrice.setBackground(SystemColor.window);
		txtpnProductTotalPrice.setBounds(435, 130, 64, 20);
		panel_1.add(txtpnProductTotalPrice);
		
		textField_refund_total_unitPrice_input = new JTextPane();
		textField_refund_total_unitPrice_input.setEditable(false);
		textField_refund_total_unitPrice_input.setBackground(SystemColor.window);
		textField_refund_total_unitPrice_input.setBounds(505, 130, 141, 20);
		panel_1.add(textField_refund_total_unitPrice_input);
		
		loadTransactionTable();
		loadRefundInfo();
		
		Thread t3 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				transaction_search_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!table_refund.isEditing()){
							for (int i = model_refund.getRowCount()-1; i >= 0; --i) {
								model_refund.removeRow(i);
							}
							//Find Transaction
							String tempInput = textField_transaction_input.getText();
							if(validateEmpty(tempInput) == false){
								JOptionPane.showMessageDialog(null,"Transaction ID field cannot be Empty.","Error",JOptionPane.ERROR_MESSAGE);
								textField_transaction_input.requestFocusInWindow();
							}
							else if(checkForNumbers(tempInput) == false){
								JOptionPane.showMessageDialog(null,"Transaction ID entered must contain numbers only.","Error",JOptionPane.ERROR_MESSAGE);
								textField_transaction_input.requestFocusInWindow();
							}
							else{
								int tempTransactionID = Integer.parseInt(tempInput);
								t = new Transaction();
								if(t.getTransactionDetails(tempTransactionID) == true){
									textField_transactionID.setText(String.valueOf(t.getId()));
									textField_createDate.setText(parseDate(t.getCreateDate()));
									textField_transaction_subtotal.setText("$" + String.valueOf(t.getSubTotal()));
									textField_transaction_tax.setText("$" + String.valueOf(t.getTax()));
									textField_transaction_total.setText("$" + String.valueOf(t.getTotal()));
									textField_transactionType.setText(t.getTransactionType());
									textField_method.setText(t.getMethod());
									textField_promotionID.setText(String.valueOf(t.getPromotionID()));
									textField_employeeID.setText(String.valueOf(t.getEmployeeID()));
									
									if(productByRefund.size() > 0){
										productByRefund.clear();
									}
									if(tr.size() > 0){
										tr.clear();
									}
									TransactionRecord temp = new TransactionRecord();
									transactionRecordLength = temp.getTransactionCount(tempTransactionID);
									if(transactionRecordLength > 0){
										if(returning != null){
											returning.clear();
										}
										for(int i = 0; i < transactionRecordLength; i++){
											tr.add(temp.getTransactionRecord(tempTransactionID, i));
											model_refund.addRow(new Object[]{
											i+1,tr.get(i).getProductID(),tr.get(i).getQuantitySold(),
											tr.get(i).getReturned(),tr.get(i).getDateReturned()});
											
											//Removed tr.getEmployeeID() (reminder)
											Cashier refundCashier = new Cashier();
											productByRefund.add(refundCashier.findProductID(tr.get(i).getProductID()));
											previousRefundValue.add(tr.get(i).getReturned());
										}
									}
									else{
										JOptionPane.showMessageDialog(null,"Transaction Record for Transaction " + '"'+ tempTransactionID + '"' + ", could not be found.");
										textField_transaction_input.requestFocus();
									}
								}
								else{
									JOptionPane.showMessageDialog(null,"Transaction " + '"'+ tempTransactionID + '"' + ", could not be found.");
									textField_transaction_input.requestFocus();
								}
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
							textField_transaction_input.requestFocus();
						}
					}
				});
			}
		});
		t3.start();
		
		keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				if(textField_productID_input.hasFocus()){
					String input = textField_productID_input.getText();
					textField_productID_input.setText(input+i);
				}	
			}
		});
		
		keypad_2 = new JButton("2");
		keypad_2.setFocusable(false);
		keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_2.setBounds(86, 0, 87, 93);
		keypad_panel.add(keypad_2);
		keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_3 = new JButton("3");
		keypad_3.setFocusable(false);
		keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_3.setBounds(171, 0, 87, 93);
		keypad_panel.add(keypad_3);
		keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_4 = new JButton("4");
		keypad_4.setFocusable(false);
		keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_4.setBounds(0, 93, 87, 93);
		keypad_panel.add(keypad_4);
		keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_5 = new JButton("5");
		keypad_5.setFocusable(false);
		keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_5.setBounds(86, 93, 87, 93);
		keypad_panel.add(keypad_5);
		keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_6 = new JButton("6");
		keypad_6.setFocusable(false);
		keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_6.setBounds(171, 93, 87, 93);
		keypad_panel.add(keypad_6);
		keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_7 = new JButton("7");
		keypad_7.setFocusable(false);
		keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_7.setBounds(0, 186, 87, 93);
		keypad_panel.add(keypad_7);
		keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_8 = new JButton("8");
		keypad_8.setFocusable(false);
		keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_8.setBounds(86, 186, 87, 93);
		keypad_panel.add(keypad_8);
		keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_9 = new JButton("9");
		keypad_9.setFocusable(false);
		keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_9.setBounds(171, 186, 87, 93);
		keypad_panel.add(keypad_9);
		keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_decimal = new JButton(".");
		keypad_decimal.setFocusable(false);
		keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_decimal.setBounds(0, 278, 87, 93);
		keypad_panel.add(keypad_decimal);
		keypad_decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_0 = new JButton("0");
		keypad_0.setFocusable(false);
		keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_0.setBounds(86, 278, 87, 93);
		keypad_panel.add(keypad_0);
		keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_productID_input.getText();
				if(textField_productID_input.hasFocus()){
					textField_productID_input.setText(input+i);
				}
			}
		});
		
		keypad_enter = new JButton("Enter");
		keypad_enter.setFocusable(false);
		keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		keypad_enter.setBounds(171, 278, 87, 93);
		keypad_panel.add(keypad_enter);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Checkout using Cash: " + "\n");
		sb.append("Total: $" + total + "\n");
		sb.append("Cash Tender: $");
		String message = sb.toString();
		
		keypad_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				String i = e.getActionCommand();
				if(textField_productID_input.hasFocus()){
					String input = textField_productID_input.getText();
					textField_productID_input.setText(input+i);
				}
				*/
			}
		});
	}
	public void setEmployee(Employees e){
		employee = e;
		welcome.setText("Welcome, " + employee.getFirstName() + " " + employee.getLastName());
	
		//if(position.checkPosition(employee.getPositionID()) == true){
		Positions position = new Positions();
		position.checkPosition(employee.getPositionID());
		if(position.getId() == 1){
			//Loads Cashier panel
			loadCashier();
		}
		else if(position.getId() == 2){
			//Loads Cashier panel
			loadCashier();
		}
		else if(position.getId() == 3){
			//Loads Cashier panel
			loadCashier();
			
			//Report
			Reports report_sec = new Reports(); 
			JPanel panel_reports = report_sec.getWindow();
			tabbedPane.addTab("Reports", panel_reports);
            
			//Staff
			Employees staff = new Employees();
			JPanel panel_staff = staff.getWindow();
			staff.setCurrentEmployee(employee);
			tabbedPane.addTab("Staff", panel_staff);
			
			//Supplier
			Supplier s = new Supplier();
			JPanel panel_supplier = s.getWindow();
			tabbedPane.addTab("Supplier", panel_supplier);
				
			//Inventory
			//JPanel panel_inventory = new JPanel();
			//tabbedPane.addTab("Inventory", null, panel_inventory, null);
			Inventory inv = new Inventory();
			tabbedPane.addTab("Inventory",inv);
			inv.initiateInventory();
			
			//Menu bar added by Gaurav
            javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
            javax.swing.JMenuItem print_item = new javax.swing.JMenuItem("Print");
            print_item.addActionListener((ActionEvent ae) -> {
            	int selected = tabbedPane.getSelectedIndex();
            	if(tabbedPane.getTitleAt(selected).equalsIgnoreCase("Reports")){
                	report_sec.printReport();
            	} else if(tabbedPane.getTitleAt(selected).equalsIgnoreCase("Inventory")){
                	inv.printOrderSheet();
                } else {
                	JOptionPane.showMessageDialog(null, "Nothing to print, only works with reports and inventory tab.");
                }

            });
            mb.add(print_item);

            javax.swing.JMenuItem tax = new javax.swing.JMenuItem("Change Tax");
            tax.addActionListener((ActionEvent ae) -> {
            	String update_tax = JOptionPane.showInputDialog(null, 
            			"Enter updated tax amount (no symbols)", "Update Tax", 
            	        JOptionPane.WARNING_MESSAGE);
            	
            	String error = "";
            	try {
            		Double tax_amount = Double.parseDouble(update_tax);
            		
            		if(tax_amount > 100 || tax_amount < 1){
            			error = "Entered tax amount is invalid";
            			JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.ERROR_MESSAGE);
            		} else {
            			Connect c = new Connect();
            			boolean b = c.updateTax(tax_amount);
            			if(b){
            				error = "Sucessfully changed tax amount. For new tax to take effect program must be restarted!";
            				JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.INFORMATION_MESSAGE);
            			} else {
            				error = "An error occured while updating tax amount.";
            				JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.ERROR_MESSAGE);
            			}
            		}
            	} catch(NumberFormatException ex){
            		error = "Entered tax amount not recognize.";
            		JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.ERROR_MESSAGE);
            	}
            });
            mb.add(tax);

            getContentPane().add(mb, BorderLayout.NORTH);
            setJMenuBar(mb);
			
			//Get Help
			JPanel panel_getHelp = new JPanel();
			tabbedPane.addTab("Get Help", null, panel_getHelp, null);
		}
		else if(position.getId() == 4){
			//Loads Cashier panel
			loadCashier();
			
			//Report
			Reports report_sec = new Reports(); 
			JPanel panel_reports = report_sec.getWindow();
			tabbedPane.addTab("Reports", panel_reports);
            
			//Staff
			Employees staff = new Employees();
			JPanel panel_staff = staff.getWindow();
			staff.setCurrentEmployee(employee);
			tabbedPane.addTab("Staff", panel_staff);
			
			//Supplier
			Supplier s = new Supplier();
			JPanel panel_supplier = s.getWindow();
			tabbedPane.addTab("Supplier", panel_supplier);
				
			//Inventory
			//JPanel panel_inventory = new JPanel();
			//tabbedPane.addTab("Inventory", null, panel_inventory, null);
			Inventory inv = new Inventory();
			tabbedPane.addTab("Inventory",inv);
			inv.initiateInventory();
			
			//Menu bar added by Gaurav
            javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
            javax.swing.JMenuItem print_item = new javax.swing.JMenuItem("Print");
            print_item.addActionListener((ActionEvent ae) -> {
            	int selected = tabbedPane.getSelectedIndex();
            	if(tabbedPane.getTitleAt(selected).equalsIgnoreCase("Reports")){
                	report_sec.printReport();
            	} else if(tabbedPane.getTitleAt(selected).equalsIgnoreCase("Inventory")){
                	inv.printOrderSheet();
                } else {
                	JOptionPane.showMessageDialog(null, "Nothing to print, only works with reports and inventory tab.");
                }

            });
            mb.add(print_item);

            javax.swing.JMenuItem tax = new javax.swing.JMenuItem("Change Tax");
            tax.addActionListener((ActionEvent ae) -> {
            	String update_tax = JOptionPane.showInputDialog(null, 
            			"Enter updated tax amount (no symbols)", "Update Tax", 
            	        JOptionPane.WARNING_MESSAGE);
            	
            	String error = "";
            	try {
            		Double tax_amount = Double.parseDouble(update_tax);
            		
            		if(tax_amount > 100 || tax_amount < 1){
            			error = "Entered tax amount is invalid";
            			JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.ERROR_MESSAGE);
            		} else {
            			Connect c = new Connect();
            			boolean b = c.updateTax(tax_amount);
            			if(b){
            				error = "Sucessfully changed tax amount. For new tax to take effect program must be restarted!";
            				JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.INFORMATION_MESSAGE);
            			} else {
            				error = "An error occured while updating tax amount.";
            				JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.ERROR_MESSAGE);
            			}
            		}
            	} catch(NumberFormatException ex){
            		error = "Entered tax amount not recognize.";
            		JOptionPane.showMessageDialog (null, error, "Update Tax", JOptionPane.ERROR_MESSAGE);
            	}
            });
            mb.add(tax);
            
            getContentPane().add(mb, BorderLayout.NORTH);
            setJMenuBar(mb);
			
			//Get Help
			JPanel panel_getHelp = new JPanel();
			tabbedPane.addTab("Get Help", null, panel_getHelp, null);
		}
		else if(position.getId() == 5){
			//Report
			Reports report_sec = new Reports(); 
			JPanel panel_reports = report_sec.getWindow();
			tabbedPane.addTab("Reports", panel_reports);
            
			//Staff
			Employees staff = new Employees();
			JPanel panel_staff = staff.getWindow();
			staff.setCurrentEmployee(employee);
			tabbedPane.addTab("Staff", panel_staff);
			
			//Supplier
			Supplier s = new Supplier();
			JPanel panel_supplier = s.getWindow();
			tabbedPane.addTab("Supplier", panel_supplier);
				
			//Inventory
			//JPanel panel_inventory = new JPanel();
			//tabbedPane.addTab("Inventory", null, panel_inventory, null);
			Inventory inv = new Inventory();
			tabbedPane.addTab("Inventory",inv);
			inv.initiateInventory();
			
			//Menu bar added by Gaurav
            javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
            javax.swing.JMenuItem print_item = new javax.swing.JMenuItem("Print");
            print_item.addActionListener((ActionEvent ae) -> {
            	int selected = tabbedPane.getSelectedIndex();
            	if(tabbedPane.getTitleAt(selected).equalsIgnoreCase("Reports")){
                	report_sec.printReport();
            	} else if(tabbedPane.getTitleAt(selected).equalsIgnoreCase("Inventory")){
                	inv.printOrderSheet();
                } else {
                	JOptionPane.showMessageDialog(null, "Nothing to print, only works with reports and inventory tab.");
                }

            });
            mb.add(print_item);

            getContentPane().add(mb, BorderLayout.NORTH);
            setJMenuBar(mb);
		}
			
		//BackupAndRestore br = new BackupAndRestore();  
		//br.setBackupRestorePath();
	}
	
	/*public void calculateSalePrice(int row, Object objID){
		int id = (int) objID;
		for(int i = 0; i < productBySearch.size(); i++){
			if(id == productBySearch.get(i).getID()){
				double t = (double) model.getValueAt(row, productQuantityAndPrice_column);
				productBySearch.get(i).setSalePrice(t);
				break;
			}
		}
	}*/
	
	public void calculateCashCheckout(){
		String cashTenderInput = checkout_amount_tender_input.getText();
		if(validateEmpty(cashTenderInput) == false){
			JOptionPane.showMessageDialog(null,"Cash field cannot be Empty","Error",JOptionPane.ERROR_MESSAGE);
			checkout_amount_tender_input.setFocusable(true);
		}
		else if(!cashTenderInput.trim().matches("^[0-9.]*$")){
			String invalidInput = "Please enter numbers only.";
			JOptionPane.showMessageDialog(null,invalidInput,"Cash Tender Error",JOptionPane.ERROR_MESSAGE);
			checkout_amount_tender_input.setFocusable(true);
		}
		else if(cashTenderInput.charAt(0) == '.'){
			String invalidInput = "Invalid input. Please enter numbers.";
			JOptionPane.showMessageDialog(null,invalidInput,"Cash Tender Error",JOptionPane.ERROR_MESSAGE);
			checkout_amount_tender_input.setFocusable(true);
		}
		else{
			double cashTender = Double.parseDouble(cashTenderInput);
			cashTender = Math.round(cashTender * 100.0) / 100.0;
			
			if(cashTender < total){
				String attention = "The amount entered for cash tendered ($" + cashTender + ") is not enough to cover total amount ($" + total + ").";       
				JOptionPane.showMessageDialog(null,attention,"Cash Tender Error",JOptionPane.ERROR_MESSAGE);
			}
			else if(cashTender >= total){
				double change = 0;
				change = cashTender - total;
				change = Math.round(change * 100.0) / 100.0;
				
				Object[] optionsTransactionCash = {"Complete Transaction"};
				StringBuilder sb2 = new StringBuilder();
				sb2.append("Total: $" + total + "\n");
				sb2.append("Cash Tender: $" + cashTender + "\n");
				sb2.append("Change: $" + change + "\n");
				sb2.append("Printing receipt.");
				transactionType = "Sale";
				transactionMethod = "Cash";
				String message2 = sb2.toString();
				int cashTransaction = JOptionPane.showOptionDialog(null,message2,"Checkout using Cash",
				JOptionPane.PLAIN_MESSAGE,JOptionPane.PLAIN_MESSAGE,null,optionsTransactionCash,optionsTransactionCash[0]);
				
				Transaction transaction = new Transaction();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				String dateString = dateFormat.format(date);
				//change employeeid
				int transactionID = transaction.writeTransactionCash(dateString, subTotal, tax, total, transactionType, transactionMethod,promotionID, employee.getID());				
				TransactionRecord transactionRecord = new TransactionRecord();
				for(int i = 0; i < productBySearch.size(); i++){
					//change employeeid
					//This loop gets the product quantity bought by customer.
					//That value is not being stored in productBySearch vector, only how many quantity remaining in inventory is stored in it
					String qs = null;
					for(int j = 0; j < table.getRowCount(); j++){
						int tID = (int) model.getValueAt(j, id_column);
						if(productBySearch.get(i).getID() == tID){
							qs = String.valueOf(table.getValueAt(j, productQuantity_column));
							break;
						}
					}
					//Product quantity stored in table
					int productNewQuantity = Integer.parseInt(qs);
					transactionRecord.insertTransactionRecord(transactionID, productBySearch.get(i).getID(),productNewQuantity,productBySearch.get(i).getSalePrice(),employee.getID());
					transactionRecord.updateProduct(productBySearch.get(i).getID(),previousValue.get(i));
				}
				//Backup db after sale transaction
				/*BackupAndRestore br = new BackupAndRestore();
				br.setBackupRestorePath();
				br.initialize();
				br.backupDB();
				*/
				Object[] options = {"Yes","No"};
				int cashCredit = JOptionPane.showOptionDialog(null,"Would you like to print receipt?","",
				    JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
				
				if(cashCredit == JOptionPane.YES_OPTION){
					System.out.println("yes");
				}
				else if(cashCredit == JOptionPane.NO_OPTION){
					System.out.println("no");
				}
				/*
				Thread t2 = new Thread(new Runnable() {
					public void run() {
						try {
							Connect connect = new Connect();
							Connection c = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
							
							JasperDesign jd = JRXmlLoader.load("recipt.jrxml");
							String sql = "SELECT t.ID, t.CreateDate, t.SubTotal, t.Tax, t.Total, t.EmployeeID, tr.ProductID, p.Name, "
									+ " tr. QuantitySold, tr.UnitPrice, (tr.QuantitySold * tr.UnitPrice) as PTotal FROM StoreDB.Transaction t,"
									+ " StoreDB.TransactionRecord tr, StoreDB.Product p WHERE t.ID = tr.TransactionID AND tr.ProductID = p.ID AND t.ID = " + transactionID;
							JRDesignQuery jdq = new JRDesignQuery();
							jdq.setText(sql);
							jd.setQuery(jdq);
							
							JasperReport jr = JasperCompileManager.compileReport(jd);
							JasperPrint jp = JasperFillManager.fillReport(jr, null, c);
							
							JasperPrintManager.printReport(jp, true);
						
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null,"There is an error with printing recipt. " + e.getMessage(), 
									"Error with printing", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				t2.start();
				
				d4.dispose();
				textField_productID_input.requestFocusInWindow();
				*/
			}
		}
	}
	public void stopPrint(boolean finished){
		finished = true;
	}
	
	public void calculateOneTimeDiscount(){
		String discountInput = discount_option.getText();
		
		if(oneTimeDiscountCheck == true){
			JOptionPane.showMessageDialog(null,"One time discount has been already applied for this transaction.");
		}
		else if (validateEmpty(discountInput) == false){
			JOptionPane.showMessageDialog(null,"Promotion input entered cannot be Empty","Error",JOptionPane.ERROR_MESSAGE);
			//discount_option.setFocusable(true);
		}
		else if(discountInput.trim().matches("^[0-9$%.]*$")){
			Discount oneTimeDiscount = new Discount();
			double previous = subTotal;
			subTotal = oneTimeDiscount.calculateDiscount(subTotal,discountInput,discount);
				
			subTotal = Math.round(subTotal * 100.0) / 100.0;
			subtotal_textField.setText("Subtotal: $" + subTotal);

			tax = subTotal * 0.13;
			tax = Math.round(tax * 100.0) / 100.0;
			tax_textField.setText("Tax: $" + tax);
					
			total = subTotal + tax;
			total = Math.round(total * 100.0) / 100.0;
			total_textField.setText("Total: $" + total);
					
			if(subTotal < previous){
				//textField_discount.setText("Discount: " + oneTimeDiscount.getType() + ": $" + ((previous - subTotal) * 100.0) / 100.0);
				detail_discountID = oneTimeDiscount.getID();
				detail_discountType = oneTimeDiscount.getType();
				detail_discountValue = 0;
				detail_discountValue = (((previous - subTotal) * 100.0) / 100.0);
				if(oneTimeDiscount.getID() > 0){
					promotionID = oneTimeDiscount.getID();
				}
				else{
					promotionID = 0;
				}
				oneTimeDiscountCheck = true;
				d3.dispose();
				textField_productID_input.requestFocusInWindow();
				button_discount.setVisible(false);
				btnDiscountDetails.setVisible(true);
			}
		}
		else{
			JOptionPane.showMessageDialog(null,"Please enter valid input, such as option number, the value or the percentage.");
			discount_option.requestFocus();
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
			/*
			else{
				check = false;
			}*/
		}
		return check;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_BACK_SPACE){
			//KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
			/*Frame.getFocusOwner() (inherited from Window.getFocusOwner()) 
			ought to return a reference to the component with focus.
			getMostRecentFocusOwner() might also be of interest.
			*/
			btnCancelItem.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}
	
	public void loadDiscountFrame(ActionEvent e){
		JPanel panel_discount = new JPanel();
		panel_discount.setLayout(null);
					
		//Discount details
		JTabbedPane tabbedPane_discount = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_discount.setBounds(60, 10, 400, 215);
		panel_discount.add(tabbedPane_discount);
					
		JPanel panel_discount_details = new JPanel();
		tabbedPane_discount.addTab("Discount details:", null, panel_discount_details, null);
		panel_discount_details.setLayout(null);
					
		//Text
		JTextPane discount_message1 = new JTextPane();
		discount_message1.setFocusable(false);
		discount_message1.setEditable(false);
		discount_message1.setBounds(10, 13, 350, 96);
		discount_message1.setBackground(Color.decode(defaultColor));
		panel_discount_details.add(discount_message1);
		
		//Input
		discount_option = new JTextField();
		discount_option.setBounds(6, 110, 183, 26);
		panel_discount_details.add(discount_option);
		discount_option.setColumns(10);
		discount_option.setFocusable(true);
			
		//Enter button
		JButton discountOption_btnEnter = new JButton("Enter");
		discountOption_btnEnter.setBounds(3, 135, 190, 29);
		panel_discount_details.add(discountOption_btnEnter);
		discountOption_btnEnter.requestFocusInWindow();
		discountOption_btnEnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateOneTimeDiscount();
			}
		});
		
		//Cancel button
		JButton discountOption_btnCancel = new JButton("Cancel");
		discountOption_btnCancel.setBounds(190, 135, 185, 29);
		panel_discount_details.add(discountOption_btnCancel);
		discountOption_btnCancel.requestFocusInWindow();
		discountOption_btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d3.dispose();
				textField_productID_input.requestFocusInWindow();
			}
		});

		JTabbedPane tabbedPane_discount_keypad = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_discount_keypad.setBounds(115, 215, 279, 416);
		panel_discount.add(tabbedPane_discount_keypad);
		
		JPanel keypad_panel_checkout = new JPanel();
		tabbedPane_discount_keypad.addTab("Keypad: ", null, keypad_panel_checkout, null);
		keypad_panel_checkout.setLayout(null);
		
		JButton discount_keypad_1 = new JButton("1");
		discount_keypad_1.setBounds(0, 0, 87, 93);
		discount_keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_1.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_1);
		discount_keypad_1.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_2 = new JButton("2");
		discount_keypad_2.setBounds(86, 0, 87, 93);
		discount_keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_2.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_2);
		discount_keypad_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_3 = new JButton("3");
		discount_keypad_3.setBounds(172, 0, 87, 93);
		discount_keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_3.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_3);
		discount_keypad_3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_4 = new JButton("4");
		discount_keypad_4.setBounds(0, 93, 87, 93);
		discount_keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_4.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_4);
		discount_keypad_4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_5 = new JButton("5");
		discount_keypad_5.setBounds(86, 93, 87, 93);
		discount_keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_5.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_5);
		discount_keypad_5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton checkout_keypad_6 = new JButton("6");
		checkout_keypad_6.setBounds(172, 93, 87, 93);
		checkout_keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		checkout_keypad_6.setFocusable(false);
		keypad_panel_checkout.add(checkout_keypad_6);
		checkout_keypad_6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_7 = new JButton("7");
		discount_keypad_7.setBounds(0, 186, 87, 93);
		discount_keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_7.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_7);
		discount_keypad_7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_8 = new JButton("8");
		discount_keypad_8.setBounds(86, 186, 87, 93);
		discount_keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_8.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_8);
		discount_keypad_8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_9 = new JButton("9");
		discount_keypad_9.setBounds(172, 186, 87, 93);
		discount_keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_9.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_9);
		discount_keypad_9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		
		JButton discount_keypad_decimal = new JButton(".");
		discount_keypad_decimal.setBounds(0, 278, 87, 93);
		discount_keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_decimal.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_decimal);
		discount_keypad_decimal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_0 = new JButton("0");
		discount_keypad_0.setBounds(86, 278, 87, 93);
		discount_keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_0.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_0);
		discount_keypad_0.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = discount_option.getText();
				discount_option.setText(input+i);
			}
		});
		
		JButton discount_keypad_enter = new JButton("Enter");
		discount_keypad_enter.setBounds(172, 278, 87, 93);
		discount_keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		discount_keypad_enter.setFocusable(false);
		keypad_panel_checkout.add(discount_keypad_enter);
		discount_keypad_enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calculateOneTimeDiscount();
			}
		});
		
		Discount d = new Discount();
		int count = d.getPromotionCount();
		
		discount = new Vector<Discount>();
		for(int i = 1; i <= count; i++){
			Discount temp = new Discount();
			temp.getPromotion(i);
			discount.add(temp);
		}
		String[] options = new String[count];
		for(int i = 0; i < count; i++){
			if(discount.get(i).getDiscountValue() > 0){
				options[i] = i+1 + ".) " + discount.get(i).getType() + discount.get(i).getDiscountValue();
			}
			else if(discount.get(i).getDiscountPercent() > 0){
				options[i] = i+1 + ".) " + discount.get(i).getDiscountPercent() + discount.get(i).getType();
			}
		}
		StringBuilder sb1 = new StringBuilder();
		sb1.append("Current promotions: " + "\n");
		for(int i = 0; i < count; i++){
			sb1.append(options[i] + "\n");
		}
		String toDisplay=sb1.toString();
		discount_message1.setText(toDisplay);
		
		Component component = (Component) e.getSource();
        JFrame topFrame = (JFrame) SwingUtilities.getRoot(component);
		d3 = new JDialog(topFrame, "", Dialog.ModalityType.DOCUMENT_MODAL);
		d3.getContentPane().add(panel_discount);
		d3.setBounds(0, 0, 500, 650);
		d3.setLocationRelativeTo(null);
		d3.getRootPane().setDefaultButton(discountOption_btnEnter);
		d3.setVisible(true);
	}
	
	public void loadCheckoutFrame(ActionEvent e){
		if(!table.isEditing()){
			JPanel panel_checkout = new JPanel();
			panel_checkout.setLayout(null);
						
			//Checkout details
			JTabbedPane tabbedPane_checkout = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane_checkout.setBounds(60, 10, 400, 175);
			panel_checkout.add(tabbedPane_checkout);
						
			JPanel panel_checkout_details = new JPanel();
			tabbedPane_checkout.addTab("Checkout details:", null, panel_checkout_details, null);
			panel_checkout_details.setLayout(null);
						
			//Text
			JTextPane checkout_message1 = new JTextPane();
			StringBuilder sb = new StringBuilder();
			sb.append("Checkout using Cash: " + "\n");
			sb.append("Total: $" + total + "\n");
			sb.append("Cash Tender: $");
			String message = sb.toString();
			checkout_message1.setText(message);
			checkout_message1.setFocusable(false);
			checkout_message1.setEditable(false);
			
			checkout_message1.setBounds(10, 13, 350, 50);
			checkout_message1.setBackground(Color.decode(defaultColor));
			panel_checkout_details.add(checkout_message1);
			
			checkout_amount_tender_input = new JTextField();
			checkout_amount_tender_input.setBounds(6, 62, 183, 26);
			panel_checkout_details.add(checkout_amount_tender_input);
			checkout_amount_tender_input.setColumns(10);
			checkout_amount_tender_input.setFocusable(true);
					
			//Enter button
			JButton checkoutCash_btnEnter = new JButton("Enter");
			checkoutCash_btnEnter.setBounds(3, 90, 190, 29);
			panel_checkout_details.add(checkoutCash_btnEnter);
			checkoutCash_btnEnter.requestFocusInWindow();
			checkoutCash_btnEnter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					calculateCashCheckout();
				}
			});
			
			//Cancel button
			JButton checkoutCash_btnCancel = new JButton("Cancel");
			checkoutCash_btnCancel.setBounds(190, 90, 185, 29);
			panel_checkout_details.add(checkoutCash_btnCancel);
			checkoutCash_btnCancel.requestFocusInWindow();
			checkoutCash_btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					d4.dispose();
					textField_productID_input.requestFocusInWindow();
				}
			});
			
			JTabbedPane tabbedPane_checkout_keypad = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane_checkout_keypad.setBounds(115, 180, 279, 416);
			panel_checkout.add(tabbedPane_checkout_keypad);
			
			JPanel keypad_panel_checkout = new JPanel();
			tabbedPane_checkout_keypad.addTab("Keypad: ", null, keypad_panel_checkout, null);
			keypad_panel_checkout.setLayout(null);
			
			JButton checkout_keypad_1 = new JButton("1");
			checkout_keypad_1.setBounds(0, 0, 87, 93);
			checkout_keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_1.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_1);
			checkout_keypad_1.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
		
			JButton checkout_keypad_2 = new JButton("2");
			checkout_keypad_2.setBounds(86, 0, 87, 93);
			checkout_keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_2.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_2);
			checkout_keypad_2.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
		
			JButton checkout_keypad_3 = new JButton("3");
			checkout_keypad_3.setBounds(172, 0, 87, 93);
			checkout_keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_3.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_3);
			checkout_keypad_3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_4 = new JButton("4");
			checkout_keypad_4.setBounds(0, 93, 87, 93);
			checkout_keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_4.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_4);
			checkout_keypad_4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_5 = new JButton("5");
			checkout_keypad_5.setBounds(86, 93, 87, 93);
			checkout_keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_5.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_5);
			checkout_keypad_5.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_6 = new JButton("6");
			checkout_keypad_6.setBounds(172, 93, 87, 93);
			checkout_keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_6.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_6);
			checkout_keypad_6.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_7 = new JButton("7");
			checkout_keypad_7.setBounds(0, 186, 87, 93);
			checkout_keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_7.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_7);
			checkout_keypad_7.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_8 = new JButton("8");
			checkout_keypad_8.setBounds(86, 186, 87, 93);
			checkout_keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_8.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_8);
			checkout_keypad_8.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_9 = new JButton("9");
			checkout_keypad_9.setBounds(172, 186, 87, 93);
			checkout_keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_9.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_9);
			checkout_keypad_9.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_decimal = new JButton(".");
			checkout_keypad_decimal.setBounds(0, 278, 87, 93);
			checkout_keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_decimal.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_decimal);
			checkout_keypad_decimal.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_0 = new JButton("0");
			checkout_keypad_0.setBounds(86, 278, 87, 93);
			checkout_keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_0.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_0);
			checkout_keypad_0.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String i = e.getActionCommand();
					String input = checkout_amount_tender_input.getText();
					checkout_amount_tender_input.setText(input+i);
				}
			});
			
			JButton checkout_keypad_enter = new JButton("Enter");
			checkout_keypad_enter.setBounds(172, 278, 87, 93);
			checkout_keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
			checkout_keypad_enter.setFocusable(false);
			keypad_panel_checkout.add(checkout_keypad_enter);
			
			checkout_keypad_enter.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					calculateCashCheckout();
				}
			});
		
			Component component = (Component) e.getSource();
	        JFrame topFrame2 = (JFrame) SwingUtilities.getRoot(component);
			d4 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
			d4.getContentPane().add(panel_checkout);
			d4.setBounds(0, 0, 500, 650);
			d4.setLocationRelativeTo(null);
			d4.getRootPane().setDefaultButton(checkoutCash_btnEnter);
			d4.setVisible(true);
		}
		else{
			JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
		}
		
	}
	public void calculateSubtotal(){
		//System.out.println("CalculateSubtotal()");
		double sub = 0;
		for(int i = 0; i < model.getRowCount(); i++){
			sub += Double.parseDouble(model.getValueAt(i, productQuantityAndPrice_column).toString());
		}
		subTotal = sub;
		subTotal = Math.round(subTotal * 100.0) / 100.0;
		subtotal_textField.setText("Subtotal: $" + subTotal);
		
		tax = subTotal * 0.13;
		tax = Math.round(tax * 100.0) / 100.0;
		tax_textField.setText("Tax: $" + tax);
			
		total = subTotal + tax;
		total = Math.round(total * 100.0) / 100.0;
		total_textField.setText("Total: $" + total);	
	}
	public boolean calculateInventoryQuantity(){
		boolean check = true;
		if(model.getRowCount() > 0 && productBySearch.size() > 0 && !table.isEditing()){
			for(int i = 0; i < model.getRowCount(); i++){
				for(int j = 0; j < productBySearch.size(); j++){
					int proID = (int) model.getValueAt(i, id_column);
					if(proID == productBySearch.get(j).getID()){
						int onHand = productBySearch.get(j).getQuantity();
						
						String tempNeed = model.getValueAt(i, productQuantity_column).toString(); 
						int need = Integer.parseInt(tempNeed);
						
						int p = productBySearch.get(j).getQuantity();
						int p2 = previousValue.get(j);
						
						int difference = need - previousValue.get(j);
						int newQuantity = onHand - difference;
						productBySearch.get(j).setQuantity(newQuantity);
						
						previousValue.remove(j);
						previousValue.insertElementAt(need, j);
						
						if(productBySearch.get(j).getQuantity() < 0){
							JOptionPane.showMessageDialog(null,"Inventory quantity for product " + '"' + productBySearch.get(j).getName() + '"' + ", has reached 0. This product is no longer in stock.");
							productBySearch.get(j).setQuantity(p);
						
							previousValue.remove(j);
							previousValue.insertElementAt(p2, j);

							model.setValueAt(p2, j, productQuantity_column);
							//System.out.println("Everything not fine Check: " + check);
							check = false;
						}
						else{
							//System.out.println("Everything fine Check: " + check);
						}
						
						int row = table.getSelectionModel().getLeadSelectionIndex();
						textField_name_input.setText(productBySearch.get(row).getName());
						textField_description_input.setText(productBySearch.get(row).getDescription());		
						textField_quantity_input.setText(String.valueOf(productBySearch.get(row).getQuantity()));
						//System.out.println("end2");
						break;
					}	
				}
			}
			//System.out.println("End2");
		}
		return check;
	}
	public void loadTransactionTable(){
		JTabbedPane tabbedPane_refund_table = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_refund_table.setBounds(0, 205, 673, 405);
		panel_refund.add(tabbedPane_refund_table);
		
		JPanel panel_table_refund = new JPanel(new BorderLayout());
		tabbedPane_refund_table.addTab("List of purchased items, quantity and price: ", null, panel_table_refund, null);
		
		refund_column_name.addElement("#");
		refund_column_name.addElement("Product ID");
		refund_column_name.addElement("Quantity Sold");
		//refund_column_name.addElement("Unit Price");
		refund_column_name.addElement("Returned");
		refund_column_name.addElement("Date Returned");
		//refund_column_name.addElement("Employee ID");
		
		table_refund = new JTable(refund_row_data, refund_column_name){
			public boolean isCellEditable(int row, int column) {
				//Return true if the column (number) is editable, else false
		        if(column == 3){ 
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		panel_table_refund.add(table_refund.getTableHeader(), BorderLayout.NORTH);
		panel_table_refund.add(table_refund, BorderLayout.CENTER);
		
		//Row Height
		table_refund.setRowHeight(30);
	  	
	    //Column Width
	  	TableColumnModel columnModel_refund = table_refund.getColumnModel();
	  	columnModel_refund.getColumn(0).setPreferredWidth(10); //#
	 	columnModel_refund.getColumn(1).setPreferredWidth(30); //Product ID
	  	columnModel_refund.getColumn(2).setPreferredWidth(30); //Quantity Sold
        //columnModel_refund.getColumn(3).setPreferredWidth(30); //Unit Price 
	  	columnModel_refund.getColumn(3).setPreferredWidth(30); //Returned
	  	columnModel_refund.getColumn(4).setPreferredWidth(100); //Date Returned
	  	//columnModel_refund.getColumn(6).setPreferredWidth(10); //Employee ID

	    //Columns won't be able to moved around
	  	table_refund.getTableHeader().setReorderingAllowed(false);
	  	
	    //Center table data 
	  	DefaultTableCellRenderer centerRenderer_refund = new DefaultTableCellRenderer();
	  	centerRenderer_refund.setHorizontalAlignment( SwingConstants.CENTER );
	  	table_refund.getColumnModel().getColumn(0).setCellRenderer( centerRenderer_refund ); //#
	  	table_refund.getColumnModel().getColumn(1).setCellRenderer( centerRenderer_refund ); //Product ID
	  	table_refund.getColumnModel().getColumn(2).setCellRenderer( centerRenderer_refund ); //Quantity Sold
	  	//table_refund.getColumnModel().getColumn(3).setCellRenderer( centerRenderer_refund ); //Unit Price
	  	table_refund.getColumnModel().getColumn(3).setCellRenderer( centerRenderer_refund ); //Returned
	  	table_refund.getColumnModel().getColumn(4).setCellRenderer( centerRenderer_refund ); //Date Returned
	  	//table_refund.getColumnModel().getColumn(6).setCellRenderer( centerRenderer_refund ); //Employee ID
	  	
	    //Center table column names
	  	centerRenderer_refund = (DefaultTableCellRenderer) table_refund.getTableHeader().getDefaultRenderer();
	  	centerRenderer_refund.setHorizontalAlignment(JLabel.CENTER);
	  	
		JScrollPane jsp = new JScrollPane(table_refund);
		jsp.setBounds(2, 2, 810, 344);
		jsp.setVisible(true);
		panel_table_refund.add(jsp);
	   
		model_refund = (DefaultTableModel) table_refund.getModel();
		
		table_refund.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//Changes which product is selected in product details
				int row = table_refund.getSelectionModel().getLeadSelectionIndex();
				if (e.getClickCount() == 1) {
					if(productByRefund.size() > 0){
		        		for(int i = 0; i < productByRefund.size(); i++){
		        			//Change to TransactionRecord or add another loop to get price of product at the time the transaction took place,
		        			//Not the actual price. Else this will cause problem when a product may have been at discount and customer will get full(more) money back
		        			//ProductByRefund uses product details from product table
		        			if(String.valueOf(model_refund.getValueAt(row, id_column)).equals(String.valueOf(productByRefund.get(i).getID()))){
		        				textField_refund_name.setText(productByRefund.get(i).getName());
		        				textField_refund_description_input.setText(productByRefund.get(i).getDescription());
		        				textField_refund_quantity_remaining_input.setText(String.valueOf(productByRefund.get(i).getQuantity()));
		        				if(String.valueOf(model_refund.getValueAt(row, id_column)).equals(String.valueOf(tr.get(i).getProductID()))){
		        					double uPrice = tr.get(i).getUnitPrice();
		        					uPrice = Math.round(uPrice * 100.0) / 100.0;
		        					textField_refund_product_unitPrice_input.setText("$" + String.valueOf(uPrice));
		        					
			        				int tempQuantity = (int) model_refund.getValueAt(row, refund_QuantitySold);
			        				double rowPrice = tempQuantity * tr.get(i).getUnitPrice();
			        				rowPrice = Math.round(rowPrice * 100.0) / 100.0;
			        				textField_refund_total_unitPrice_input.setText("$" + String.valueOf(rowPrice));
		        				}
		        				break;
		        			}
		        		}
	        		}
	        	}
				else if(e.getClickCount() == 2){
					int column = table_refund.getSelectedColumn();
					if(column == 0 || column == 1 || column == 2 || column == 3 || column == 4){
						if(row > -1){
							int modelProductID = (Integer) model_refund.getValueAt(row, 1);
							if(tr.size() > 0){
								for(int i = 0; i < tr.size(); i++){
									if(modelProductID == tr.get(i).getProductID()){
										if(tr.get(i).getReturned() >= 1){
											JPanel productRefundHistory = new JPanel();
											productRefundHistory.setLayout(null);
														
											//Cancel button
											JButton refundHistoryCancel = new JButton("Cancel");
											refundHistoryCancel.setBounds(38, 380, 820, 40);
											productRefundHistory.add(refundHistoryCancel);
											
											refundHistoryCancel.addActionListener(new ActionListener() {
												@Override
												public void actionPerformed(ActionEvent e) {
													d6.dispose();
												}
											});
											
											//Table
											JTabbedPane tabbedPane_refund_table = new JTabbedPane(JTabbedPane.TOP);
											tabbedPane_refund_table.setBounds(30, 10, 835, 375);
											productRefundHistory.add(tabbedPane_refund_table);
														
											JPanel panel_table_refundHistory = new JPanel(new BorderLayout());
											tabbedPane_refund_table.addTab("Product Refund History: ", null, panel_table_refundHistory, null);
														
											Vector<String> search_row_data = new Vector<String>();
											Vector<String> search_column_name = new Vector<String>();
											search_column_name.addElement("#");
											search_column_name.addElement("Name");
											search_column_name.addElement("Quantity Sold");
											search_column_name.addElement("Returned");
											search_column_name.addElement("Return Date");
											
											JTable table_refundHistory = new JTable(search_row_data, search_column_name){
												public boolean isCellEditable(int row, int column) {
													//Return true if the column (number) is editable, else false
													return false;
												}
											};
											panel_table_refundHistory.add(table_refundHistory.getTableHeader(), BorderLayout.NORTH);
											panel_table_refundHistory.add(table_refundHistory, BorderLayout.CENTER);
											
											//Row Height
											table_refundHistory.setRowHeight(30);
											
											//Column Width
											TableColumnModel columnModel_search = table_refundHistory.getColumnModel();
											columnModel_search.getColumn(0).setPreferredWidth(1); //#
											columnModel_search.getColumn(1).setPreferredWidth(200); //Name
											columnModel_search.getColumn(2).setPreferredWidth(10); //Quantity Sold
											columnModel_search.getColumn(3).setPreferredWidth(10); //Returned
											columnModel_search.getColumn(4).setPreferredWidth(75); //Return Date
											
											//Columns won't be able to moved around
											table_refundHistory.getTableHeader().setReorderingAllowed(false);
											
											//Center table data 
											DefaultTableCellRenderer centerRenderer_search = new DefaultTableCellRenderer();
											centerRenderer_search.setHorizontalAlignment( SwingConstants.CENTER );
											table_refundHistory.getColumnModel().getColumn(0).setCellRenderer( centerRenderer_search ); //#
											table_refundHistory.getColumnModel().getColumn(1).setCellRenderer( centerRenderer_search ); //Product Name
											table_refundHistory.getColumnModel().getColumn(2).setCellRenderer( centerRenderer_search ); //Quantity Sold
											table_refundHistory.getColumnModel().getColumn(3).setCellRenderer( centerRenderer_search ); //Returned
											table_refundHistory.getColumnModel().getColumn(4).setCellRenderer( centerRenderer_search ); //Return Date
											
											//Center table column names
											centerRenderer_search = (DefaultTableCellRenderer) table_refundHistory.getTableHeader().getDefaultRenderer();
											centerRenderer_search.setHorizontalAlignment(JLabel.CENTER);
											
											JScrollPane jsp2 = new JScrollPane(table_refundHistory);
											jsp2.setBounds(2, 2, 810, 344);
											jsp2.setVisible(true);
											panel_table_refundHistory.add(jsp2);
											
											DefaultTableModel model_refundHistory = (DefaultTableModel) table_refundHistory.getModel();
											
											//Fetch's values from ProductReturned
											Vector<TransactionRecord> tempRefundHistory = new Vector<TransactionRecord>();
											TransactionRecord tNew = new TransactionRecord();
											tempRefundHistory = tNew.getProductRefundHistory(tr.get(0).getTransactionID(),tr.get(i).getProductID());
											
											//Fill table with the history of refund transactions that took place for specific product
											if(tempRefundHistory.size() > 0 && productByRefund.size() > 0){
												for(int j = 0; j < tempRefundHistory.size(); j++){
													for(int k = 0; k < productByRefund.size(); k++){
														if(tempRefundHistory.get(j).getProductID() == productByRefund.get(k).getID()){
															model_refundHistory.addRow(new Object[]{j+1,productByRefund.get(k).getName(),
															tr.get(i).getQuantitySold(),tempRefundHistory.get(j).getReturned(),tempRefundHistory.get(j).getDateReturned()});
														}
													}
												}
											}
											
											Component component = (Component) e.getSource();
											JFrame topFrame2 = (JFrame) SwingUtilities.getRoot(component);
											d6 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
											d6.getContentPane().add(productRefundHistory);
											d6.setSize(900, 465);
											d6.setLocationRelativeTo(null);
											d6.getRootPane().setDefaultButton(refundHistoryCancel);
											d6.setVisible(true);
										}
										else{
											JOptionPane.showMessageDialog(null,"This product has no refund history.");
										}
										break;
									}
								}
							}
						}
					}
				}
			}
		});
		
		table_refund.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if(model_refund.getRowCount() > 0){
					//Refund table
					int col2 = table_refund.getColumnModel().getSelectionModel().getLeadSelectionIndex();
			        int row2 = table_refund.getSelectionModel().getLeadSelectionIndex();
			        if(row2 > -1 && col2 > -1){
			        	if(col2 == refund_returned){
							Runnable run2 = new Runnable() {
								@Override
								public void run() {
									String tempQuantity = model_refund.getValueAt(row2, refund_returned).toString();
									char c = 0;
									char c2 = 0;
									if(!tempQuantity.isEmpty()){
										c = tempQuantity.charAt(0);
										c2 = tempQuantity.charAt(tempQuantity.length()-1);
									}	
									//Previous Quantity
									int previousQuantity2 = 0;
									if(model_refund.getRowCount() > 0){
										String nID = model_refund.getValueAt(row2, refund_productID).toString();
										for(int i = 0; i < productByRefund.size(); i++){
											if(nID.equals(String.valueOf(productByRefund.get(i).getID()))){
												previousQuantity2 = previousRefundValue.get(i);
											}
										}
									}
									if(validateEmpty(tempQuantity) == false){
										JOptionPane.showMessageDialog(null,"Remaining field cannot be left empty. "
												+ "Please enter a remaining quantity above 0 for row #" + (row2+1) + ".");
										model_refund.setValueAt(previousQuantity2, row2, col2);
										table_refund.setRowSelectionInterval(row2, row2);
										table_refund.setColumnSelectionInterval(0, 0);
									}
									else if(checkForNumbers(tempQuantity) == false){
										JOptionPane.showMessageDialog(null,"Remaining quantity entered for row #" + (row2+1) + 
												" must contain numbers only.","Error",JOptionPane.ERROR_MESSAGE);
										model_refund.setValueAt(previousQuantity2, row2, col2);
										table_refund.setRowSelectionInterval(row2, row2);
										table_refund.setColumnSelectionInterval(0, 0);
									}
									else if(c == '0' && c2 >= '0'){
										JOptionPane.showMessageDialog(null,"Please enter a quantity that does not begin with 0 for row #" + (row2+1) + ".");
										model_refund.setValueAt(previousQuantity2, row2, col2);
										table_refund.setRowSelectionInterval(row2, row2);
										table_refund.setColumnSelectionInterval(0, 0);
									}
									else if(tempQuantity.length() > 8){
										JOptionPane.showMessageDialog(null,"The input '" + tempQuantity + "' for row #" + (row2+1) + " is too long. Please enter a valid quantity.");
										model_refund.setValueAt(previousQuantity2, row2, col2);
										table_refund.setRowSelectionInterval(row2, row2);
										table_refund.setColumnSelectionInterval(0, 0);
									}
									else{
										int newReturn = Integer.parseInt(model_refund.getValueAt(row2, refund_returned).toString().trim());
							
										int sold = Integer.parseInt(model_refund.getValueAt(row2, refund_QuantitySold).toString().trim());
										if(newReturn > sold){
											JOptionPane.showMessageDialog(null,"Remaining quantity entered cannot be above the number of quantity bought.");
											model_refund.setValueAt(previousQuantity2, row2, col2);
											table_refund.setRowSelectionInterval(row2, row2);
											table_refund.setColumnSelectionInterval(0, 0);
										}
										else if(underReturn(newReturn, tr.get(row2)) == true){
											JOptionPane.showMessageDialog(null,"Remaining quantity entered cannot be below the previous returned quantity.");
											model_refund.setValueAt(previousQuantity2, row2, col2);
											table_refund.setRowSelectionInterval(row2, row2);
											table_refund.setColumnSelectionInterval(0, 0);
										}
										else{
											//Sets previous value for the selected row
											if(model_refund.getRowCount() > 0){
												if(productByRefund.size() > 0){
													for(int i = 0; i < productByRefund.size(); i++){
														String tableValue = model_refund.getValueAt(row2, refund_productID).toString();
														String vectorValue = String.valueOf(productByRefund.get(i).getID());
														if(tableValue.equals(vectorValue)){
															String value = model_refund.getValueAt(row2, col2).toString();
															int convertedValue = Integer.valueOf(value);
															previousRefundValue.remove(i);
															previousRefundValue.insertElementAt(convertedValue, i);
															break;
														}
													}
												}
											}	
											
											if(tr.size() > 0){
												String table_productID = model_refund.getValueAt(row2, refund_productID).toString().trim();
												for(int i = 0; i < tr.size(); i++){
													if(table_productID.equals(String.valueOf(tr.get(i).getProductID()))){
														if(newReturn == tr.get(i).getReturned()){
															if(returning.size() > 0){
																for(int j = 0; j < returning.size(); j++){
																	if(table_productID.equals(String.valueOf(returning.get(j).getProductID()))){
																		//System.out.println("Before:" + returning.size());
																		returning.remove(j);
																		//System.out.println("After:" + returning.size());
																	}
																	else{
																		//System.out.println("else");
																	}
																}
															}
															else{
																returning.clear();
															}
															
														}
														else{
															if(returning.size() > 0){
																boolean check = true;
																for(int j = 0; j < returning.size(); j++){
																	if(table_productID.equals(String.valueOf(returning.get(j).getProductID()))){
																		TransactionRecord r = new TransactionRecord();
																		r.setTransactionID(tr.get(i).getTransactionID());
																		r.setProductID(tr.get(i).getProductID());
																		r.setQuantitySold(tr.get(i).getQuantitySold());
																		r.setUnitPrice(tr.get(i).getUnitPrice());
																		r.setReturned(newReturn);
																		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
																		Date date = new Date();
																		String dateString = dateFormat.format(date);
																		r.setDateReturned(dateString);
																		//Change to current user later
																		r.setEmployeeID(1);
																		r.setUnitCost(tr.get(i).getUnitCost());
																		returning.remove(j);
																		returning.insertElementAt(r, j);	
																		check = true;
																		break;
																	}
																	else{
																		check = false;
																	}
																}
																if(check == false){
																	TransactionRecord r = new TransactionRecord();
																	r.setTransactionID(tr.get(i).getTransactionID());
																	r.setProductID(tr.get(i).getProductID());
																	r.setQuantitySold(tr.get(i).getQuantitySold());
																	r.setUnitPrice(tr.get(i).getUnitPrice());
																	r.setReturned(newReturn);
																	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
																	Date date = new Date();
																	String dateString = dateFormat.format(date);
																	r.setDateReturned(dateString);
																	//Change to current user later
																	r.setEmployeeID(1);
																	r.setUnitCost(tr.get(i).getUnitCost());
																	returning.add(r);	
																}
															}
															else{
																TransactionRecord r = new TransactionRecord();
																r.setTransactionID(tr.get(i).getTransactionID());
																r.setProductID(tr.get(i).getProductID());
																r.setQuantitySold(tr.get(i).getQuantitySold());
																r.setUnitPrice(tr.get(i).getUnitPrice());
																r.setReturned(newReturn);
																DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
																Date date = new Date();
																String dateString = dateFormat.format(date);
																r.setDateReturned(dateString);
																//Change to current user later
																r.setEmployeeID(1);
																r.setUnitCost(tr.get(i).getUnitCost());
																returning.add(r);
															}
														}
													}
												}
											}
										}
										//After entering value, movies selection to next column (does not leave user in same colum)
										table_refund.setRowSelectionInterval(row2, row2);
										table_refund.setColumnSelectionInterval(0, 0);
										textField_transaction_input.requestFocusInWindow();
										}
									}
							};
							SwingUtilities.invokeLater(run2);
			        	}
			        }
				}
			}
		});
	}
	public void loadRefundInfo(){
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(685, 0, 279, 497);
		panel_refund.add(tabbedPane);
		
		JPanel panel_transactionDetail = new JPanel();
		tabbedPane.addTab("Transaction details:", null, panel_transactionDetail, null);
		panel_transactionDetail.setLayout(null);
		
		JTextPane txtpnTransactionId_1 = new JTextPane();
		txtpnTransactionId_1.setEditable(false);
		txtpnTransactionId_1.setText("Transaction ID:");
		txtpnTransactionId_1.setBackground(Color.decode(defaultColor));
		txtpnTransactionId_1.setBounds(6, 10, 96, 18);
		panel_transactionDetail.add(txtpnTransactionId_1);
		
		JTextPane txtpnCreateDate = new JTextPane();
		txtpnCreateDate.setEditable(false);
		txtpnCreateDate.setText("Create Date:");
		txtpnCreateDate.setBackground(Color.decode(defaultColor));
		txtpnCreateDate.setBounds(6, 48, 80, 18);
		panel_transactionDetail.add(txtpnCreateDate);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 38, 452, 12);
		panel_transactionDetail.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 72, 452, 12);
		panel_transactionDetail.add(separator_1);
		
		textField_transactionID = new JTextPane();
		textField_transactionID.setBounds(105, 10, 123, 26);
		textField_transactionID.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_transactionID);
		textField_transactionID.setEditable(false);
		
		textField_createDate = new JTextPane();
		textField_createDate.setBounds(87, 48, 136, 20);
		panel_transactionDetail.add(textField_createDate);
		textField_createDate.setBackground(Color.decode(defaultColor));
		textField_createDate.setEditable(false);
		
		JTextPane txtpnSubtotal = new JTextPane();
		txtpnSubtotal.setText("Subtotal:");
		txtpnSubtotal.setEditable(false);
		txtpnSubtotal.setBounds(6, 90, 61, 18);
		txtpnSubtotal.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnSubtotal);
		
		JTextPane txtpnTax = new JTextPane();
		txtpnTax.setText("Tax:");
		txtpnTax.setEditable(false);
		txtpnTax.setBounds(6, 123, 32, 18);
		txtpnTax.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnTax);
		
		JTextPane txtpnTotal = new JTextPane();
		txtpnTotal.setText("Total:");
		txtpnTotal.setEditable(false);
		txtpnTotal.setBounds(6, 165, 41, 18);
		txtpnTotal.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnTotal);
		
		textField_transaction_subtotal = new JTextPane();
		textField_transaction_subtotal.setBounds(68, 90, 73, 20);
		textField_transaction_subtotal.setEditable(false);
		textField_transaction_subtotal.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_transaction_subtotal);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(0, 111, 452, 12);
		panel_transactionDetail.add(separator_2);
		
		textField_transaction_tax = new JTextPane();
		textField_transaction_tax.setBounds(40, 123, 88, 20);
		textField_transaction_tax.setEditable(false);
		textField_transaction_tax.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_transaction_tax);
		
		textField_transaction_total = new JTextPane();
		textField_transaction_total.setBounds(49, 163, 117, 20);
		textField_transaction_total.setEditable(false);
		textField_transaction_total.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_transaction_total);
		
		JTextPane txtpnTransactionType = new JTextPane();
		txtpnTransactionType.setText("Transaction Type:");
		txtpnTransactionType.setEditable(false);
		txtpnTransactionType.setBounds(6, 203, 112, 18);
		txtpnTransactionType.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnTransactionType);
		
		textField_transactionType = new JTextPane();
		textField_transactionType.setBounds(125, 203, 87, 20);
		textField_transactionType.setEditable(false);
		textField_transactionType.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_transactionType);
		
		JTextPane txtpnMethod = new JTextPane();
		txtpnMethod.setText("Method:");
		txtpnMethod.setEditable(false);
		txtpnMethod.setBounds(6, 247, 51, 18);
		txtpnMethod.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnMethod);
		
		textField_method = new JTextPane();
		textField_method.setBounds(65, 245, 112, 20);
		textField_method.setEditable(false);
		textField_method.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_method);
		
		JTextPane txtpnPromotionId = new JTextPane();
		txtpnPromotionId.setText("Promotion ID:");
		txtpnPromotionId.setEditable(false);
		txtpnPromotionId.setBounds(6, 288, 87, 18);
		txtpnPromotionId.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnPromotionId);
		
		textField_promotionID = new JTextPane();
		textField_promotionID.setBounds(100, 288, 102, 20);
		textField_promotionID.setEditable(false);
		textField_promotionID.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_promotionID);
		
		JTextPane txtpnEmployeeId = new JTextPane();
		txtpnEmployeeId.setText("Employee ID:");
		txtpnEmployeeId.setEditable(false);
		txtpnEmployeeId.setBounds(6, 328, 85, 18);
		txtpnEmployeeId.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(txtpnEmployeeId);
		
		textField_employeeID = new JTextPane();
		textField_employeeID.setBounds(95, 327, 117, 20);
		textField_employeeID.setEditable(false);
		textField_employeeID.setBackground(Color.decode(defaultColor));
		panel_transactionDetail.add(textField_employeeID);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(0, 144, 452, 12);
		panel_transactionDetail.add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(0, 188, 258, 12);
		panel_transactionDetail.add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(0, 225, 258, 12);
		panel_transactionDetail.add(separator_5);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(0, 273, 258, 12);
		panel_transactionDetail.add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(0, 314, 258, 12);
		panel_transactionDetail.add(separator_7);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(0, 350, 258, 12);
		panel_transactionDetail.add(separator_8);
		
		JButton btnRefundHistory = new JButton("Refund History");
		btnRefundHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!table_refund.isEditing()){
					JPanel productRefundHistory = new JPanel();
					productRefundHistory.setLayout(null);
						
					//Cancel button
					JButton refundHistoryCancel = new JButton("Cancel");
					refundHistoryCancel.setBounds(38, 380, 820, 40);
					productRefundHistory.add(refundHistoryCancel);
					
					refundHistoryCancel.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							d7.dispose();
							textField_transaction_input.requestFocusInWindow();
						}
					});
						
					//Table
					JTabbedPane tabbedPane_refund_table = new JTabbedPane(JTabbedPane.TOP);
					tabbedPane_refund_table.setBounds(30, 10, 835, 375);
					productRefundHistory.add(tabbedPane_refund_table);
					
					JPanel panel_table_refundHistory = new JPanel(new BorderLayout());
					tabbedPane_refund_table.addTab("Product Refund History: ", null, panel_table_refundHistory, null);
					
					Vector<String> search_row_data = new Vector<String>();
					Vector<String> search_column_name = new Vector<String>();
					search_column_name.addElement("Product ID");
					search_column_name.addElement("Name");
					search_column_name.addElement("Quantity Sold");
					search_column_name.addElement("Returned");
					search_column_name.addElement("Return Date");
					
					JTable table_refundHistory = new JTable(search_row_data, search_column_name){
						public boolean isCellEditable(int row, int column) {
							//Return true if the column (number) is editable, else false
							return false;
						}
					};
					panel_table_refundHistory.add(table_refundHistory.getTableHeader(), BorderLayout.NORTH);
					panel_table_refundHistory.add(table_refundHistory, BorderLayout.CENTER);
					
					//Row Height
					table_refundHistory.setRowHeight(30);
					
					//Column Width
					TableColumnModel columnModel_search = table_refundHistory.getColumnModel();
					columnModel_search.getColumn(0).setPreferredWidth(1); //#
					columnModel_search.getColumn(1).setPreferredWidth(200); //Name
					columnModel_search.getColumn(2).setPreferredWidth(10); //Quantity Sold
					columnModel_search.getColumn(3).setPreferredWidth(10); //Returned
					columnModel_search.getColumn(4).setPreferredWidth(75); //Return Date
					
					//Columns won't be able to moved around
					table_refundHistory.getTableHeader().setReorderingAllowed(false);
					
					//Center table data 
					DefaultTableCellRenderer centerRenderer_search = new DefaultTableCellRenderer();
					centerRenderer_search.setHorizontalAlignment( SwingConstants.CENTER );
					table_refundHistory.getColumnModel().getColumn(0).setCellRenderer( centerRenderer_search ); //#
					table_refundHistory.getColumnModel().getColumn(1).setCellRenderer( centerRenderer_search ); //Product Name
					table_refundHistory.getColumnModel().getColumn(2).setCellRenderer( centerRenderer_search ); //Quantity Sold
					table_refundHistory.getColumnModel().getColumn(3).setCellRenderer( centerRenderer_search ); //Returned
					table_refundHistory.getColumnModel().getColumn(4).setCellRenderer( centerRenderer_search ); //Return Date
						
					//Center table column names
					centerRenderer_search = (DefaultTableCellRenderer) table_refundHistory.getTableHeader().getDefaultRenderer();
					centerRenderer_search.setHorizontalAlignment(JLabel.CENTER);
					
					JScrollPane jsp2 = new JScrollPane(table_refundHistory);
					jsp2.setBounds(2, 2, 810, 344);
					jsp2.setVisible(true);
					panel_table_refundHistory.add(jsp2);
					
					DefaultTableModel model_refundHistory = (DefaultTableModel) table_refundHistory.getModel();
					
					if(tr.size() > 0){
						for(int i = 0; i < tr.size(); i++){
							if(tr.get(i).getReturned() >= 1){
								//Fetch's values from ProductReturned
								Vector<TransactionRecord> tempRefundHistory = new Vector<TransactionRecord>();
								TransactionRecord tNew = new TransactionRecord();
								
								tempRefundHistory = tNew.getProductRefundHistory(tr.get(0).getTransactionID(),tr.get(i).getProductID());
								
								//Fill table with the history of refund transactions that took place for specific product
								if(tempRefundHistory.size() > 0 && productByRefund.size() > 0){
									for(int j = 0; j < tempRefundHistory.size(); j++){
										for(int k = 0; k < productByRefund.size(); k++){
											if(tempRefundHistory.get(j).getProductID() == productByRefund.get(k).getID()){
												model_refundHistory.addRow(new Object[]{productByRefund.get(k).getID(),productByRefund.get(k).getName(),
												tr.get(i).getQuantitySold(),tempRefundHistory.get(j).getReturned(),tempRefundHistory.get(j).getDateReturned()});
											}
										}
									}
								}
								else{
									//JOptionPane.showMessageDialog(null,"This product has no return history.");
								}				
							}
						}
					}
					Component component = (Component) e.getSource();
					JFrame topFrame2 = (JFrame) SwingUtilities.getRoot(component);
					d7 = new JDialog(topFrame2, "", Dialog.ModalityType.DOCUMENT_MODAL);
					d7.getContentPane().add(productRefundHistory);
					d7.setSize(900, 465);
					d7.setLocationRelativeTo(null);
					d7.getRootPane().setDefaultButton(refundHistoryCancel);
					d7.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
				}
			}
		});
		btnRefundHistory.setBounds(29, 365, 200, 35);
		panel_transactionDetail.add(btnRefundHistory);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(0, 400, 258, 12);
		panel_transactionDetail.add(separator_9);
		
		JButton btnNewButton = new JButton("Clear All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Refund
				//JFrame/Dialog
				textField_transaction_input.setText("");
				
				if(productByRefund.size() > 0){
					productByRefund.clear();
				}
				if(previousRefundValue.size() > 0){
					previousRefundValue.clear();
				}
				
				//Table
				if(tr.size() > 0){
					tr.clear();
				}
				if(returning.size() > 0){
					returning.clear();
				}
				transactionRecordLength = 0;
				if(refund_row_data.size() > 0){
					refund_row_data.clear();
				}
				//private JTable table_refund;
				removeTableRows(model_refund);	
				model_refund.fireTableDataChanged(); //Tells table to update gui
				
				textField_refund_name.setText("");
				textField_refund_description_input.setText("");
				textField_refund_quantity_remaining_input.setText("");
				textField_refund_product_unitPrice_input.setText("");
				textField_refund_total_unitPrice_input.setText("");
				
				//Refund details:
				textField_transactionID.setText("");
				textField_createDate.setText("");
				textField_transaction_subtotal.setText("");
				textField_transaction_tax.setText("");
				textField_transaction_total.setText("");
				textField_transactionType.setText("");
				textField_method.setText("");
				textField_promotionID.setText("");
				textField_employeeID.setText("");
				
				//Refund sales
				remainingSubtotal = 0;
				finalRefundSubtotal = 0;
				finalRefundTax = 0;
				finalRefundTotal = 0;
				refundChange = 0;
				
				textField_transaction_input.requestFocusInWindow();
			}
		});
		btnNewButton.setBounds(29, 412, 200, 35);
		panel_transactionDetail.add(btnNewButton);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(961, 428, 279, 81);
		panel_refund.add(tabbedPane_1);
		
		JPanel panel = new JPanel();
		tabbedPane_1.addTab("Commands:", null, panel, null);
		panel.setLayout(null);
		
		JButton btnRefund = new JButton("Refund");
		btnRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!table_refund.isEditing()){
					if(t != null){
						if(tr.size() > 0){
							if(returning.size() > 0){
								JPanel panel_refund_confirm = new JPanel();
								panel_refund_confirm.setLayout(null);
										
								//Product Name
								JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
								tabbedPane.setBounds(250, 375, 400, 200);
								panel_refund_confirm.add(tabbedPane);
											
								JPanel panel_refundConfirmation = new JPanel();
								tabbedPane.addTab("Sales Total:", null, panel_refundConfirmation, null);
								panel_refundConfirmation.setLayout(null);
											
								//Subtotal
								JTextPane txtpnrefund_subtotal = new JTextPane();
								txtpnrefund_subtotal.setText("Subtotal:");
								txtpnrefund_subtotal.setBackground(Color.decode(defaultColor));
								txtpnrefund_subtotal.setBounds(10, 13, 150, 18);
								panel_refundConfirmation.add(txtpnrefund_subtotal);
								
								//Tax
								JTextPane txtpnrefund_tax = new JTextPane();
								txtpnrefund_tax.setText("Tax:");
								txtpnrefund_tax.setBackground(Color.decode(defaultColor));
								txtpnrefund_tax.setBounds(10, 37, 150, 18);
								panel_refundConfirmation.add(txtpnrefund_tax);
								
								//Total
								JTextPane txtpnrefund_total = new JTextPane();
								txtpnrefund_total.setText("Total:");
								txtpnrefund_total.setBackground(Color.decode(defaultColor));
								txtpnrefund_total.setBounds(10, 63, 150, 18);
								panel_refundConfirmation.add(txtpnrefund_total);
								
								//Change
								JTextPane txtpnrefund_change = new JTextPane();
								txtpnrefund_change.setText("Change:");
								txtpnrefund_change.setBackground(Color.decode(defaultColor));
								txtpnrefund_change.setBounds(10, 88, 150, 18);
								panel_refundConfirmation.add(txtpnrefund_change);
										
								//Cancel button
								JButton refund_cancel_confirm = new JButton("Cancel");
								refund_cancel_confirm.setBounds(448, 575, 410, 40);
								panel_refund_confirm.add(refund_cancel_confirm);
								
								//Refund button
								JButton refund_confirm_button = new JButton("Refund");
								refund_confirm_button.setBounds(38, 575, 410, 40);
								panel_refund_confirm.add(refund_confirm_button);
								
								//Table
								JTabbedPane tabbedPane_search_table = new JTabbedPane(JTabbedPane.TOP);
								tabbedPane_search_table.setBounds(30, 10, 835, 375);
								panel_refund_confirm.add(tabbedPane_search_table);
											
								JPanel panel_refund_confirm2 = new JPanel(new BorderLayout());
								tabbedPane_search_table.addTab("Confirm refund: ", null, panel_refund_confirm2, null);
											
								Vector<String> refund_confirm_row_data = new Vector<String>();
								Vector<String> refund_confirm_column_name = new Vector<String>();
								refund_confirm_column_name.addElement("Product ID");
								refund_confirm_column_name.addElement("Name");
								refund_confirm_column_name.addElement("Quantity Sold");
								refund_confirm_column_name.addElement("Returned Before");
								refund_confirm_column_name.addElement("Returned Now");
								refund_confirm_column_name.addElement("Unit Price");
								refund_confirm_column_name.addElement("Refund Price");
								
								JTable table_refund_confirm = new JTable(refund_confirm_row_data, refund_confirm_column_name){
									public boolean isCellEditable(int row, int column) {
										//Return true if the column (number) is editable, else false
										return false;
									}
								};
								panel_refund_confirm2.add(table_refund_confirm.getTableHeader(), BorderLayout.NORTH);
								panel_refund_confirm2.add(table_refund_confirm, BorderLayout.CENTER);
								
								
								//Row Height
								table_refund_confirm.setRowHeight(30);
								
								//Column Width
								TableColumnModel columnModel_search = table_refund_confirm.getColumnModel();
								columnModel_search.getColumn(0).setPreferredWidth(15); //product ID
								columnModel_search.getColumn(1).setPreferredWidth(150); //Name
								columnModel_search.getColumn(2).setPreferredWidth(10); //Quantity Sold 
								columnModel_search.getColumn(3).setPreferredWidth(20); //Returned Before
								columnModel_search.getColumn(4).setPreferredWidth(10); //Returning Now
								columnModel_search.getColumn(5).setPreferredWidth(15); //Unit Price
								columnModel_search.getColumn(6).setPreferredWidth(15); //Refund price
								
								//Columns won't be able to moved around
								table_refund_confirm.getTableHeader().setReorderingAllowed(false);
								
								//Center table data 
								DefaultTableCellRenderer centerRenderer_search = new DefaultTableCellRenderer();
								centerRenderer_search.setHorizontalAlignment( SwingConstants.CENTER );
								table_refund_confirm.getColumnModel().getColumn(0).setCellRenderer( centerRenderer_search ); //Product ID
								table_refund_confirm.getColumnModel().getColumn(1).setCellRenderer( centerRenderer_search ); //Name
								table_refund_confirm.getColumnModel().getColumn(2).setCellRenderer( centerRenderer_search ); //Quantity
								table_refund_confirm.getColumnModel().getColumn(3).setCellRenderer( centerRenderer_search ); //Price
								table_refund_confirm.getColumnModel().getColumn(4).setCellRenderer( centerRenderer_search ); //Quantity * Price
								table_refund_confirm.getColumnModel().getColumn(5).setCellRenderer( centerRenderer_search ); //Remove
								table_refund_confirm.getColumnModel().getColumn(6).setCellRenderer( centerRenderer_search ); //Total refund price
								
								//Center table column names
								centerRenderer_search = (DefaultTableCellRenderer) table_refund_confirm.getTableHeader().getDefaultRenderer();
								centerRenderer_search.setHorizontalAlignment(JLabel.CENTER);
								
								JScrollPane jsp2 = new JScrollPane(table_refund_confirm);
								jsp2.setBounds(2, 2, 810, 344);
								jsp2.setVisible(true);
								panel_refund_confirm2.add(jsp2);
								
								DefaultTableModel model_refund_confirm = (DefaultTableModel) table_refund_confirm.getModel();
								
								TableRowSorter<TableModel> sorter = new TableRowSorter<>(table_refund_confirm.getModel());
								table_refund_confirm.setRowSorter(sorter);
								
								//Disable column
								sorter.setSortable(0, false);
								sorter.setSortable(6, false);
								
								refund_cancel_confirm.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										refund_confirm.dispose();
										textField_transaction_input.requestFocusInWindow();
									}
								});
								
								//Remove rows from table
								for (int i = model_refund_confirm.getRowCount()-1; i >= 0; --i) {
									model_refund_confirm.removeRow(i);
								}
								
								//Getting all products
								if(returning.size() > 0 && tr.size() > 0){
									remainingSubtotal = 0;
									finalRefundSubtotal = 0;
									finalRefundTax = 0;
									finalRefundTotal = 0;
									refundChange = 0;
									for(int i = 0; i < returning.size(); i++){
										for(int j = 0; j < tr.size(); j++){
											if(returning.get(i).getProductID() == tr.get(j).getProductID()){
												Cashier cashTemp = new Cashier();
												Product pTemp = new Product();
												pTemp = cashTemp.findProductID(returning.get(i).getProductID());
												model_refund_confirm.addRow(new Object[]{returning.get(i).getProductID(),
												pTemp.getName(),returning.get(i).getQuantitySold(),
												tr.get(j).getReturned(),(returning.get(i).getReturned()-tr.get(j).getReturned()),(returning.get(i).getUnitPrice()),
												((returning.get(i).getReturned()-tr.get(j).getReturned())*returning.get(i).getUnitPrice())});
												
												//Calculating new Subtotal
												//Subtotal = Product of returning Unitprice * (new quantity - previous quantity of returned), so it doesn't go into negative
												remainingSubtotal += returning.get(i).getUnitPrice() * (returning.get(i).getReturned() - tr.get(j).getReturned());
												refundChange = remainingSubtotal;
												finalRefundSubtotal = (t.getSubTotal()-remainingSubtotal);
												finalRefundSubtotal = Math.round(finalRefundSubtotal * 100.0) / 100.0;
												txtpnrefund_subtotal.setText("New Subtotal: $" + finalRefundSubtotal);

												//Change
												refundChange = refundChange * 1.13;
												refundChange = Math.round(refundChange * 100.0) / 100.0;
												txtpnrefund_change.setText("Change: $" + refundChange);
												
												//Calculating new tax
												finalRefundTax = finalRefundSubtotal * 0.13;
												finalRefundTax = Math.round(finalRefundTax * 100.0) / 100.0;
												txtpnrefund_tax.setText("New Tax: $" + finalRefundTax);
														
												//Calculating new Total
												finalRefundTotal = finalRefundSubtotal + finalRefundTax;
												finalRefundTotal = Math.round(finalRefundTotal * 100.0) / 100.0;
												txtpnrefund_total.setText("New Total: $" + finalRefundTotal);
												break;
											}
										}
									}
								}
								
								refund_confirm_button.addActionListener(new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent e) {
										//Writing to Transaction
										DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
										Date date = new Date();
										String dateString = dateFormat.format(date);
										Transaction tempTransaction = new Transaction();
										//change transaction type and method from hard coded
										tempTransaction.writeProductRefundTransaction(tr.get(0).getTransactionID(), dateString,
										finalRefundSubtotal,finalRefundTax,finalRefundTotal,"Sale","Cash",1);
										
										//Writing to transaction Record
										TransactionRecord tempRecord = new TransactionRecord();
										for(int i = 0; i < returning.size(); i++){
											for(int j = 0; j < tr.size(); j++){
												if(String.valueOf(returning.get(i).getProductID()).equals(String.valueOf(tr.get(j).getProductID()))){
													tempRecord.writeProductRefund(returning.get(i),tr.get(j).getReturned());
												}
											}
										}
										//Backup db after sale transaction
										/*BackupAndRestore br = new BackupAndRestore();
										br.setBackupRestorePath();
										br.initialize();
										br.backupDB();*/
									}
								});
								
								Component component = (Component) e.getSource();
						        JFrame topFrame = (JFrame) SwingUtilities.getRoot(component);
								refund_confirm = new JDialog(topFrame, "", Dialog.ModalityType.DOCUMENT_MODAL);
								refund_confirm.getContentPane().add(panel_refund_confirm);
								refund_confirm.setSize(900, 650);
								refund_confirm.setLocationRelativeTo(null);
								refund_confirm.getRootPane().setDefaultButton(refund_cancel_confirm);
								refund_confirm.setVisible(true);
							}
							else{
								JOptionPane.showMessageDialog(null,"No changes were made to the transaction.");
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"No items found in Transaction Record.");
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"No transaction is searched.");
						textField_transaction_input.requestFocusInWindow();
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Please make sure the table is not in edit mode.");
				}
			}
		});
		btnRefund.setBounds(0, 0, 258, 35);
		panel.add(btnRefund);
		
		JTabbedPane tabbedPane_refund_keypad = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_refund_keypad.setBounds(961, 0, 279, 416);
		panel_refund.add(tabbedPane_refund_keypad);
		
		JPanel panel_refund_keypad = new JPanel();
		tabbedPane_refund_keypad.addTab("Keypad", null, panel_refund_keypad, null);
		panel_refund_keypad.setLayout(null);
		
		JButton refund_keypad_1 = new JButton("1");
		refund_keypad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_1.setBounds(0, 0, 87, 93);
		panel_refund_keypad.add(refund_keypad_1);
		
		JButton refund_keypad_2 = new JButton("2");
		refund_keypad_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_2.setBounds(86, 0, 87, 93);
		panel_refund_keypad.add(refund_keypad_2);
		
		JButton refund_keypad_3 = new JButton("3");
		refund_keypad_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_3.setBounds(171, 0, 87, 93);
		panel_refund_keypad.add(refund_keypad_3);
		
		JButton refund_keypad_4 = new JButton("4");
		refund_keypad_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_4.setBounds(0, 93, 87, 93);
		panel_refund_keypad.add(refund_keypad_4);
		
		JButton refund_keypad_5 = new JButton("5");
		refund_keypad_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_5.setBounds(86, 93, 87, 93);
		panel_refund_keypad.add(refund_keypad_5);
		
		JButton refund_keypad_6 = new JButton("6");
		refund_keypad_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_6.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_6.setBounds(171, 93, 87, 93);
		panel_refund_keypad.add(refund_keypad_6);
		
		JButton refund_keypad_7 = new JButton("7");
		refund_keypad_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_7.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_7.setBounds(0, 186, 87, 93);
		panel_refund_keypad.add(refund_keypad_7);
		
		JButton refund_keypad_8 = new JButton("8");
		refund_keypad_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_8.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_8.setBounds(86, 186, 87, 93);
		panel_refund_keypad.add(refund_keypad_8);
		
		JButton refund_keypad_9 = new JButton("9");
		refund_keypad_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_9.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_9.setBounds(171, 186, 87, 93);
		panel_refund_keypad.add(refund_keypad_9);
		
		JButton refund_keypad_decimal = new JButton(".");
		refund_keypad_decimal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_decimal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_decimal.setBounds(0, 278, 87, 93);
		panel_refund_keypad.add(refund_keypad_decimal);
		
		JButton refund_keypad_0 = new JButton("0");
		refund_keypad_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
			}
		});
		refund_keypad_0.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_0.setBounds(86, 278, 87, 93);
		panel_refund_keypad.add(refund_keypad_0);
		
		JButton refund_keypad_enter = new JButton("Enter");
		refund_keypad_enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				String i = e.getActionCommand();
				String input = textField_transaction_input.getText();
				textField_transaction_input.setText(input+i);
				*/
			}
		});
		refund_keypad_enter.setFont(new Font("Tahoma", Font.PLAIN, 20));
		refund_keypad_enter.setBounds(171, 278, 87, 93);
		panel_refund_keypad.add(refund_keypad_enter);
	}
	private String parseDate(String date) {
		String finalOutput = null;
		date = date.trim();
		if(!date.isEmpty()){
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
		}
		return finalOutput;
	}
	public boolean underReturn(int newReturnValue, TransactionRecord tr){
		if(newReturnValue < tr.getReturned()){
			return true;
		}
		else{
			return false;
		}
	}
	public void fillTableFindByName(DefaultTableModel model_search){
		//Fill table with all products
		Cashier cashierAllProductsByName = new Cashier();
		productByLike = new Vector<Product>();
		
		//Remove rows from table
		for (int i = model_search.getRowCount()-1; i >= 0; --i) {
			model_search.removeRow(i);
		}
		//Getting all products
		cashierAllProductsByName.getAllProductsByName(productByLike);
		if(productByLike.size() > 0){
			for(int i = 0; i < productByLike.size(); i++){
				if(productBySearch.size() > 0){
					for(int j = 0; j < productBySearch.size(); j++){
						if(productByLike.get(i).getID() == productBySearch.get(j).getID()){
							productByLike.get(i).setQuantity(productBySearch.get(j).getQuantity());
						}
						else{
							//No match
						}
					}
				}
				else{
					//Printing it in the outside loop, the value doesn't alter here, values that alter will and will print them outside
				}
			}
		}
		else{
			txtpnsearch_2.setText("No product was found in inventory.");
		}
		if(productByLike.size() > 0){
			for(int k = 0; k < productByLike.size(); k++){
				model_search.addRow(new Object[]{k+1,productByLike.get(k).getName(),productByLike.get(k).getDescription(),productByLike.get(k).getQuantity(),productByLike.get(k).getSalePrice(),productByLike.get(k).getNotes()});
			}
		}
	}
	public void removeTableRows(DefaultTableModel model){
		//Remove rows from table
		if(model.getRowCount() > 0){
			for (int i = model.getRowCount()-1; i >= 0; --i) {
				model.removeRow(i);
			}
		}
	}
}