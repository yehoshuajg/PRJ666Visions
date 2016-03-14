package vision;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class InventoryOrderSheetFrame extends JFrame {
	
	private JTable table;
	private final JPanel panel = new JPanel();
	
	public InventoryOrderSheetFrame(OrderInfo sup, Vector<Object> orderDetails ) {
		setResizable(false);
		getContentPane().setBackground(SystemColor.control);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 336, 534, 336);
		getContentPane().add(scrollPane);
		
		Vector<Object> columnNames = new Vector<Object>();
		Vector<Object> row = new Vector<Object>(3);
	    Vector<Object> data = new Vector<Object>();
	    
	    columnNames.addElement("Product ID");
	    columnNames.addElement("Product Name");
	    columnNames.addElement("Quantity");
	    
	    TM_basic myBasicModel = new TM_basic(orderDetails, columnNames);
	    
		table = new JTable(myBasicModel);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Order Request Form");
		lblNewLabel.setBounds(31, 30, 285, 29);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		getContentPane().add(lblNewLabel);
		
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date curDate = new Date();
		panel.setBounds(31, 86, 601, 237);
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {0, 200, 30, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[] {30, 30, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JTextArea textArea_1 = new JTextArea("Supplier Name");
		GridBagConstraints gbc_textArea_1 = new GridBagConstraints();
		gbc_textArea_1.anchor = GridBagConstraints.WEST;
		gbc_textArea_1.gridwidth = 2;
		gbc_textArea_1.gridheight = 2;
		gbc_textArea_1.insets = new Insets(0, 0, 5, 5);
		gbc_textArea_1.gridx = 0;
		gbc_textArea_1.gridy = 0;
		panel.add(textArea_1, gbc_textArea_1);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textArea_1.setBackground(SystemColor.control);
		textArea_1.setEditable(false);
		textArea_1.setText(sup.getName());
		
		JLabel lblNewLabel_1 = new JLabel("Street:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblAddress = new JLabel("Street");
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.WEST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 1;
		gbc_lblAddress.gridy = 2;
		panel.add(lblAddress, gbc_lblAddress);
		lblAddress.setText(sup.getStreet());
		
		JLabel lblOrderId = new JLabel("Order ID:");
		lblOrderId.setHorizontalAlignment(SwingConstants.LEFT);
		lblOrderId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblOrderId = new GridBagConstraints();
		gbc_lblOrderId.anchor = GridBagConstraints.WEST;
		gbc_lblOrderId.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrderId.gridx = 3;
		gbc_lblOrderId.gridy = 2;
		panel.add(lblOrderId, gbc_lblOrderId);
		
		JLabel lblOrderId_1 = new JLabel("order ID");
		lblOrderId_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOrderId_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblOrderId_1 = new GridBagConstraints();
		gbc_lblOrderId_1.anchor = GridBagConstraints.WEST;
		gbc_lblOrderId_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblOrderId_1.gridx = 4;
		gbc_lblOrderId_1.gridy = 2;
		panel.add(lblOrderId_1, gbc_lblOrderId_1);
		lblOrderId_1.setText( Integer.toString( sup.getOrderID() ));
		
		JLabel lblCity_1 = new JLabel("City:");
		GridBagConstraints gbc_lblCity_1 = new GridBagConstraints();
		gbc_lblCity_1.anchor = GridBagConstraints.WEST;
		gbc_lblCity_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity_1.gridx = 0;
		gbc_lblCity_1.gridy = 3;
		panel.add(lblCity_1, gbc_lblCity_1);
		
		JLabel lblCity = new JLabel("City");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.anchor = GridBagConstraints.WEST;
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 1;
		gbc_lblCity.gridy = 3;
		panel.add(lblCity, gbc_lblCity);
		lblCity.setText(sup.getCity());
		
		JLabel lblDate = new JLabel("Created:");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.anchor = GridBagConstraints.WEST;
		gbc_lblDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 3;
		panel.add(lblDate, gbc_lblDate);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JTextArea textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.anchor = GridBagConstraints.WEST;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.gridx = 4;
		gbc_textArea.gridy = 3;
		panel.add(textArea, gbc_textArea);
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textArea.setBackground(SystemColor.control);
		textArea.setText(df.format(curDate));
		
		JLabel lblStateprov = new JLabel("State/Prov:");
		GridBagConstraints gbc_lblStateprov = new GridBagConstraints();
		gbc_lblStateprov.anchor = GridBagConstraints.WEST;
		gbc_lblStateprov.insets = new Insets(0, 0, 5, 5);
		gbc_lblStateprov.gridx = 0;
		gbc_lblStateprov.gridy = 4;
		panel.add(lblStateprov, gbc_lblStateprov);
		
		JLabel lblNewLabel_2 = new JLabel("state");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 4;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		lblNewLabel_2.setText(sup.getState());
		
		JLabel lblPoscalCode = new JLabel("Poscal Code:");
		GridBagConstraints gbc_lblPoscalCode = new GridBagConstraints();
		gbc_lblPoscalCode.anchor = GridBagConstraints.WEST;
		gbc_lblPoscalCode.insets = new Insets(0, 0, 5, 5);
		gbc_lblPoscalCode.gridx = 0;
		gbc_lblPoscalCode.gridy = 5;
		panel.add(lblPoscalCode, gbc_lblPoscalCode);
		
		JLabel lblNewLabel_3 = new JLabel("Postal Code");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 5;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		lblNewLabel_3.setText(sup.getPostalCode());
		
		JLabel lblPhone_1 = new JLabel("Phone:");
		GridBagConstraints gbc_lblPhone_1 = new GridBagConstraints();
		gbc_lblPhone_1.anchor = GridBagConstraints.WEST;
		gbc_lblPhone_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone_1.gridx = 0;
		gbc_lblPhone_1.gridy = 6;
		panel.add(lblPhone_1, gbc_lblPhone_1);
		
		JLabel lblPhone = new JLabel("Phone");
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.WEST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 1;
		gbc_lblPhone.gridy = 6;
		panel.add(lblPhone, gbc_lblPhone);
		lblPhone.setText(sup.getPhone());
		
		JLabel lblEmail_1 = new JLabel("Email:");
		GridBagConstraints gbc_lblEmail_1 = new GridBagConstraints();
		gbc_lblEmail_1.anchor = GridBagConstraints.WEST;
		gbc_lblEmail_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmail_1.gridx = 0;
		gbc_lblEmail_1.gridy = 7;
		panel.add(lblEmail_1, gbc_lblEmail_1);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 7;
		panel.add(lblEmail, gbc_lblEmail);
		lblEmail.setText(sup.getEmail());
		
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(410, 37, 59, 25);
		getContentPane().add(btnPrint);
		

		

		

		

	}
}

class OrderInfo {
	
	private int supID;
	private int orderID;
	private String Name;
	private String Street; 
	private String City;
	private String State;
	private String postalCode;
	private String phone;
	private String email;
	private String Status;
	

	
	public OrderInfo(){
		
	}
	
	public int getsupID(){ return supID;}
	public int getOrderID() {return orderID;}
	public String getName(){ return Name;}
	public String getStreet(){ return Street;}
	public String getCity(){ return City;}
	public String getState(){ return State;}
	public String getPostalCode(){ return postalCode;}
	public String getPhone(){ return phone;}
	public String getEmail(){ return email;}
	public String getStatus(){ return Status;}
	
	public void setID( int id){supID = id;}
	public void setOrderID(int id){orderID = id;}
	public void setName( String n){  Name = n;}
	public void setStreet(String s){  Street = s;}
	public void setCity(String c){  City = c;}
	public void setState(String st){  State = st;}
	public void setPostalCode(String pc){  postalCode = pc;}
	public void setPhone(String ph){  phone = ph;}
	public void setEmail(String e){  email = e;}
	public void setStatus(String s){  Status = s;}

}
/*
class OrderDetailInfo{
	
	private int prodID;
	private String Name;
	private String Category;
	private String SubCategory;
	private int orderQty;
	public OrderDetailInfo(){
		
	}
	public OrderDetailInfo(int id, String name, String cat, String subcat, int qty){
		prodID = id;
		Name = name;
		Category = cat;
		SubCategory = subcat;
		orderQty = qty;
	}
	
	public int getProdID() {return prodID;}
	public String getName() {return Name;}
	public String getCategory() {return Category;}
	public String getSubCategory() {return SubCategory;}
	public int getOrderQty() {return orderQty;}
	
	public void setProdID(int id ) {prodID = id;}
	public void setName(String n) {Name = n;}
	public void setCategory(String c) { Category = c;}
	public void setSubCategory(String sc) {SubCategory = sc;}
	public void setOrderQty(int oq){orderQty = oq;}
	
}
*/