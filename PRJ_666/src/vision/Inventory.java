package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.SwingConstants;



public class Inventory extends JPanel {

private DefaultTableModel MyModel = null;
private Connection con_Invoice = null; //Shigemi

private JTextField add_prodID_tf;
private JTextField add_prodName_tf;
private JTextField add_prodDesc_tf;
//private JTextField textUnitCost;
private JTextField add_prodSalePrice_tf;
private JTextField add_prodQty_tf;
private JTextField add_prodNotes_tf;
private JComboBox add_prodCat_cb;
private JComboBox add_prodSubCat_cb;
private JLabel add_prodID_lbl;
private JLabel add_prodName_lbl;
private JLabel add_prodDesc_lbl;
private JLabel add_prodQty_lbl;
private JLabel add_prodCat_lbl;
private JLabel add_prodSubCat_lbl;
private JLabel add_prodSalePrice_lbl;
private JLabel add_prodNotes_lbl;

private JTextField edit_prodID_tf;
private JTextField edit_prodName_tf;
private JTextField edit_prodDesc_tf;
private JTextField edit_prodQty_tf;
private JTextField edit_prodCatID_tf;
private JTextField edit_prodSubCatID_tf;
private JTextField edit_prodUnitCost_tf;
private JTextField edit_prodSalePrice_tf;
private JTextField edit_prodNotes_tf;
private JTable table_inventoryList;

private JComboBox<CbSupItem> ord_Sup_cb = new JComboBox<CbSupItem>(); 

private TableColumnModel tcm;

private JScrollPane scp_inventoryList;
private JTabbedPane tabbedPane_Inventory;	
private static TableRowSorter<MyTableModelClass> sorter;

private static  InventoryFilterFrame filter = new InventoryFilterFrame();
private static  JLabel jl_filter_status;
private JTable table_createOrder;
private JTable table_createOrderSummary;
private JScrollPane scrollPane_ceateOrder = new JScrollPane();
private JScrollPane scrollPane_createOrderSummary = new JScrollPane();
private int newProdCategoryID;
private int newProdSubCategoryID;
	
private int sizeProdName = 50;
private int sizeProdDesc = 80;
private int sizeProdNotes = 80;
private int sizeProdQty = 11;

private TableModelListener tml_CreateTable;



public Inventory() {
		setLayout(null);

setLayout(new BorderLayout(0, 0));
JPanel panel = new JPanel();
panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
add(panel, BorderLayout.NORTH);


	//*************Sub tabbled Pane for Inventory Module
			tabbedPane_Inventory = new JTabbedPane(JTabbedPane.LEFT);
			tabbedPane_Inventory.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane_Inventory.setFont(new Font("Tahoma", Font.PLAIN, 13));
			add(tabbedPane_Inventory);
				   
			JPanel panel_Inv_EditDetails = new JPanel();
				   panel_Inv_EditDetails.setLayout(null);
							
							
				/********************<< Update Product Details >>*************************/
						
				JButton btnUpdateInventory = new JButton("Update");
					    btnUpdateInventory.setBounds(279, 442, 97, 25);
					    btnUpdateInventory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try{
							//System.out.println(tfEditDescription.getText().length());
							
							Connection connectionInvoice = Connect.connectionSetup();
							String query = "Update Product Set "
									+ "Name = '"+edit_prodName_tf.getText()+"',"
									+ "Description = '"+edit_prodDesc_tf.getText()+"',"
								    + "Quantity='"+edit_prodQty_tf.getText()+"',"
								    + "CategoryID='"+edit_prodCatID_tf.getText()+"',"
								    + "SubCategoryID='"+edit_prodSubCatID_tf.getText()+"',"
								    //+ "UnitCost='"+tfEditUnitCost.getText()+"',"
								   	+ "SalePrice='"+edit_prodSalePrice_tf.getText()+"',"
								   	//+ "SupplierID='"+tfEditSupplierID.getText()+"',"
								   	+ "Notes='"+edit_prodNotes_tf.getText()+"' "
								   	+ "WHERE ID= '"+edit_prodID_tf.getText()+"'";					
														
							PreparedStatement pst = connectionInvoice.prepareStatement(query);

							
							pst.execute();
							connectionInvoice.close();
							pst.close();
							JOptionPane.showMessageDialog(null, "Data Updated");


						}catch (Exception e1) {
							e1.printStackTrace();
						}
						refreshInventoryTable();
					}
				});
				
				panel_Inv_EditDetails.add(btnUpdateInventory);
									
					
			//*************JPanle for Inventory List page 
	
				    ChangeListener loadTableAtOpen = new ChangeListener() {
				    public void stateChanged(ChangeEvent CE) {
				    	
				        try{
				        	refreshInventoryTable();
				        }catch(Exception ex){
				        	ex.printStackTrace();
				        }
				        //JTabbedPane sourceTabbedPane = (JTabbedPane) CE.getSource();
				        //int index = sourceTabbedPane.getSelectedIndex();
							
				    };//ChangeListener
				    }; 



		JPanel panel_InventoryList = new JPanel();
		panel_InventoryList.setLayout(new BorderLayout(0, 0));
		
		table_inventoryList = new JTable();
		table_inventoryList.setRowHeight(30);
		//table_2.setBackground();

	   scp_inventoryList = new JScrollPane();
	   scp_inventoryList.setViewportView(table_inventoryList);
		panel_InventoryList.add(scp_inventoryList);
		tabbedPane_Inventory.addTab("Inventory List", null, panel_InventoryList, null);
		
		JPanel panel_InventoryList_North = new JPanel();
		panel_InventoryList.add(panel_InventoryList_North, BorderLayout.NORTH);
		panel_InventoryList_North.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton btnLoadProduct = new JButton("Refresh Table");
		panel_InventoryList_North.add(btnLoadProduct);
		btnLoadProduct.addActionListener(new ActionListener() {
				   
					public void actionPerformed(ActionEvent e) {
						
						try {
							refreshInventoryTable();
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}  // end of ActionPerformed
				});
		
		JButton btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 filter.clearFilter();
			}
		});
		JButton btnEditDetails = new JButton("Edit Details");
		panel_InventoryList_North.add(btnEditDetails);
		btnEditDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				int selectedID;
				
				try{
					Vector<Integer> selectedRows = getSelectedRows();
					if (selectedRows.size() > 1){
						JOptionPane.showMessageDialog(null, "Please select only one item");
					}
					else {
						jumpToEditPage(selectedRows.get(0).intValue());
		
					} // end else
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Select product ID from list");
					e1.printStackTrace();
				}   
			}
		});
		
		JButton btnGoToOrderPage = new JButton("Prepare Order Sheets");
		panel_InventoryList_North.add(btnGoToOrderPage);
		btnGoToOrderPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Vector<Integer> selectedRows = getSelectedRows();
				if (selectedRows.size() == 0){
					JOptionPane.showMessageDialog(null, "Please check mark at least one product");
				} else {
					
				StringBuilder selectedProductIDs = new StringBuilder();
				for (int i = 0; i < selectedRows.size()-1; i++)
					{
						selectedProductIDs.append(selectedRows.get(i).toString()).append(", ");
					}
				selectedProductIDs.append(selectedRows.get(selectedRows.size()-1).toString());
				
				createOrderTable(selectedProductIDs);
				//createOrderSummary(selectedProductIDs); placed inside createOrderTable();
				tabbedPane_Inventory.setSelectedIndex(3);
				}
			} // end of action performed
		});
		
		JPanel jp_blankspace01 = new JPanel();
		panel_InventoryList_North.add(jp_blankspace01);
		jp_blankspace01.setPreferredSize(new Dimension(100, 20));
		
	
		
		JButton btn_openFilter = new JButton("Apply Filter");
		panel_InventoryList_North.add(btn_openFilter);
		btn_openFilter.addActionListener
		(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					filter.setVisible(true);
					
		}});
		panel_InventoryList_North.add(btnClearFilter);
		
		JPanel jp_blankspace02 = new JPanel();
		panel_InventoryList_North.add(jp_blankspace02);
		jp_blankspace02.setPreferredSize(new Dimension(30,20));
		
		jl_filter_status = new JLabel("No filter Applied");
		panel_InventoryList_North.add(jl_filter_status);

	JPanel panel_AddProduct = new JPanel();
	panel_AddProduct.setLayout(null);
	
	// ======================================================================================
	// Components for Add Product Page ======================================================
	// ======================================================================================
	
	tabbedPane_Inventory.addTab("Add Product", null, panel_AddProduct, null);
	
	JLabel add_prodInstr02_lbl = new JLabel("Required fields are marked as ***");
	add_prodInstr02_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	add_prodInstr02_lbl.setBounds(25, 41, 508, 16);
	panel_AddProduct.add(add_prodInstr02_lbl);
	
	JLabel add_prodInstr01_lbl = new JLabel("Please enter new production information and press \"Add New Product\" button. ");
	add_prodInstr01_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	add_prodInstr01_lbl.setBounds(25, 13, 546, 25);
	panel_AddProduct.add(add_prodInstr01_lbl);
	
	JPanel panel_7 = new JPanel();
	panel_7.setBounds(23, 88, 630, 309);
	panel_AddProduct.add(panel_7);
	GridBagLayout gbl_panel_7 = new GridBagLayout();
	gbl_panel_7.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
	gbl_panel_7.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	gbl_panel_7.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	gbl_panel_7.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	panel_7.setLayout(gbl_panel_7);
		   
	add_prodID_lbl = new JLabel("     *** ID");
	GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
	gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
	gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
	gbc_lblNewLabel.gridx = 0;
	gbc_lblNewLabel.gridy = 0;
	panel_7.add(add_prodID_lbl, gbc_lblNewLabel);
	add_prodID_lbl.setHorizontalAlignment(SwingConstants.RIGHT);
	
	
	add_prodID_tf = new JTextField();
	GridBagConstraints gbc_add_prodID_tf = new GridBagConstraints();
	gbc_add_prodID_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodID_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodID_tf.gridx = 1;
	gbc_add_prodID_tf.gridy = 0;
	panel_7.add(add_prodID_tf, gbc_add_prodID_tf);
	add_prodID_tf.setColumns(15);
	
	add_prodID_lbl = new JLabel("ID must be 8 digits number");
	GridBagConstraints gbc_add_prodID_lbl = new GridBagConstraints();
	gbc_add_prodID_lbl.anchor = GridBagConstraints.SOUTHWEST;
	gbc_add_prodID_lbl.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodID_lbl.gridx = 2;
	gbc_add_prodID_lbl.gridy = 0;
	panel_7.add(add_prodID_lbl, gbc_add_prodID_lbl);
	JLabel lblName = new JLabel("*** Name");
	GridBagConstraints gbc_lblName = new GridBagConstraints();
	gbc_lblName.anchor = GridBagConstraints.EAST;
	gbc_lblName.insets = new Insets(0, 0, 5, 5);
	gbc_lblName.gridx = 0;
	gbc_lblName.gridy = 1;
	panel_7.add(lblName, gbc_lblName);
	lblName.setHorizontalAlignment(SwingConstants.RIGHT);
	
	add_prodID_tf.getDocument().addDocumentListener(
			new DocumentListener(){

				public void changedUpdate(DocumentEvent e){
					String str = add_prodID_tf.getText();
					int len = add_prodID_tf.getDocument().getLength();
					if( !validateStrToInt(str)){
						add_prodID_lbl.setText("Invalid number" );
						add_prodID_lbl.setBackground(new Color(255,255,0));
						add_prodID_lbl.setOpaque(true);
					}else if( len != 8 ){
						add_prodID_lbl.setText("ID must be 8 digits number - currently " + len +" digits" );
						add_prodID_lbl.setBackground(new Color(255,255,0));
						add_prodID_lbl.setOpaque(true);
					}else{
						add_prodID_lbl.setText("valid");
						add_prodID_lbl.setOpaque(false);
					}
				}
				public void insertUpdate(DocumentEvent e){
					String str = add_prodID_tf.getText();
					int len = add_prodID_tf.getDocument().getLength();
					if( !validateStrToInt(str)){
						add_prodID_lbl.setText("Invalid number" );
						add_prodID_lbl.setBackground(new Color(255,255,0));
						add_prodID_lbl.setOpaque(true);
					}else if( len != 8 ){
						add_prodID_lbl.setText("ID must be 8 digits number - currently " + len +" digits" );
						add_prodID_lbl.setBackground(new Color(255,255,0));
						add_prodID_lbl.setOpaque(true);
					}else{
						add_prodID_lbl.setText("valid");
						add_prodID_lbl.setOpaque(false);
					}
				}
				public void removeUpdate(DocumentEvent e){
					String str = add_prodID_tf.getText();
					int len = add_prodID_tf.getDocument().getLength();
					if( !validateStrToInt(str)){
						add_prodID_lbl.setText("Invalid number" );
						add_prodID_lbl.setBackground(new Color(255,255,0));
						add_prodID_lbl.setOpaque(true);
					}else if( len != 8 ){
						add_prodID_lbl.setText("ID must be 8 digits number - currently " + len +" digits" );
						add_prodID_lbl.setBackground(new Color(255,255,0));
						add_prodID_lbl.setOpaque(true);
					}else{
						add_prodID_lbl.setText("valid");
						add_prodID_lbl.setOpaque(false);
					}
				}
			}
			);
	
	add_prodName_tf = new JTextField();
	GridBagConstraints gbc_add_prodName_tf = new GridBagConstraints();
	gbc_add_prodName_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodName_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodName_tf.gridx = 1;
	gbc_add_prodName_tf.gridy = 1;
	panel_7.add(add_prodName_tf, gbc_add_prodName_tf);
	add_prodName_tf.setColumns(15);
	add_prodName_tf.getDocument().addDocumentListener(
			new DocumentListener(){

				public void changedUpdate(DocumentEvent e){
					int len = add_prodName_tf.getDocument().getLength();
					 if((len <= 50 && len > 0)){
						add_prodName_lbl.setText("valid - currently " + len +" letters");
						add_prodName_lbl.setOpaque(false);
					 }else{
						add_prodName_lbl.setText("Name must be 50 letters or less - currently " + len +" letters" );
						add_prodName_lbl.setBackground(new Color(255,255,0));
						add_prodName_lbl.setOpaque(true);
					 }
				}
				public void insertUpdate(DocumentEvent e){
					int len = add_prodName_tf.getDocument().getLength();
					 if((len <= 50 && len > 0)){
						add_prodName_lbl.setText("valid - currently " + len +" letters");
						add_prodName_lbl.setOpaque(false);
					 }else{
						add_prodName_lbl.setText("Name must be 50 letters or less - currently " + len +" letters" );
						add_prodName_lbl.setBackground(new Color(255,255,0));
						add_prodName_lbl.setOpaque(true);
					 }
				}
				public void removeUpdate(DocumentEvent e){
					int len = add_prodName_tf.getDocument().getLength();
					 if((len <= 50 && len > 0)){
						add_prodName_lbl.setText("valid - currently " + len +" letters");
						add_prodName_lbl.setOpaque(false);
					 }else{
						add_prodName_lbl.setText("Name must be 50 letters or less - currently " + len +" letters" );
						add_prodName_lbl.setBackground(new Color(255,255,0));
						add_prodName_lbl.setOpaque(true);
					 }
				}
			}
			);
	
	add_prodName_lbl = new JLabel("Name must be 50 letters or less");
	GridBagConstraints gbc_add_prodName_lbl = new GridBagConstraints();
	gbc_add_prodName_lbl.anchor = GridBagConstraints.WEST;
	gbc_add_prodName_lbl.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodName_lbl.gridx = 2;
	gbc_add_prodName_lbl.gridy = 1;
	panel_7.add(add_prodName_lbl, gbc_add_prodName_lbl);
	JLabel lblDescription = new JLabel("Description");
	GridBagConstraints gbc_lblDescription = new GridBagConstraints();
	gbc_lblDescription.anchor = GridBagConstraints.EAST;
	gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
	gbc_lblDescription.gridx = 0;
	gbc_lblDescription.gridy = 2;
	panel_7.add(lblDescription, gbc_lblDescription);
	lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
	add_prodDesc_tf = new JTextField();
	GridBagConstraints gbc_add_prodDesc_tf = new GridBagConstraints();
	gbc_add_prodDesc_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodDesc_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodDesc_tf.gridx = 1;
	gbc_add_prodDesc_tf.gridy = 2;
	panel_7.add(add_prodDesc_tf, gbc_add_prodDesc_tf);
	add_prodDesc_tf.setColumns(15);
	add_prodDesc_tf.getDocument().addDocumentListener(
			new DocumentListener(){
				
				public void changedUpdate(DocumentEvent e){
					int len = add_prodDesc_tf.getDocument().getLength();
					if(len > 80 ){
						add_prodDesc_lbl.setText("Description must be 80 letters or less - currently " + len +" letters" );
						add_prodDesc_lbl.setBackground(new Color(255,255,0));
						add_prodDesc_lbl.setOpaque(true);
					}else{
						add_prodDesc_lbl.setText("valid - currently " + len +" letters");
						add_prodDesc_lbl.setOpaque(true);
					}
				}
				public void insertUpdate(DocumentEvent e){
					int len = add_prodDesc_tf.getDocument().getLength();
					if(len > 80 ){
						add_prodDesc_lbl.setText("Description must be 80 letters or less - currently " + len +" letters" );
						add_prodDesc_lbl.setBackground(new Color(255,255,0));
						add_prodDesc_lbl.setOpaque(true);
					}else{
						add_prodDesc_lbl.setText("valid - currently " + len +" letters");
						add_prodDesc_lbl.setOpaque(true);
					}
				}
				public void removeUpdate(DocumentEvent e){
					int len = add_prodDesc_tf.getDocument().getLength();
					if(len > 80 ){
						add_prodDesc_lbl.setText("Description must be 80 letters or less - currently " + len +" letters" );
						add_prodDesc_lbl.setBackground(new Color(255,255,0));
						add_prodDesc_lbl.setOpaque(true);
					}else{
						add_prodDesc_lbl.setText("valid - currently " + len +" letters");
						add_prodDesc_lbl.setOpaque(true);
					}
				}
			}
			);
	
	
	add_prodDesc_lbl = new JLabel("Description must be 80 char or less");
	GridBagConstraints gbc_label_2 = new GridBagConstraints();
	gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
	gbc_label_2.insets = new Insets(0, 0, 5, 5);
	gbc_label_2.gridx = 2;
	gbc_label_2.gridy = 2;
	panel_7.add(add_prodDesc_lbl, gbc_label_2);
	JLabel lblQuantity = new JLabel("Quantity");
	GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
	gbc_lblQuantity.anchor = GridBagConstraints.EAST;
	gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
	gbc_lblQuantity.gridx = 0;
	gbc_lblQuantity.gridy = 3;
	panel_7.add(lblQuantity, gbc_lblQuantity);
	
	add_prodQty_tf = new JTextField();
	GridBagConstraints gbc_add_prodQty_tf = new GridBagConstraints();
	gbc_add_prodQty_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodQty_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodQty_tf.gridx = 1;
	gbc_add_prodQty_tf.gridy = 3;
	panel_7.add(add_prodQty_tf, gbc_add_prodQty_tf);
	add_prodQty_tf.setColumns(10);
	add_prodQty_tf.getDocument().addDocumentListener(
			new DocumentListener(){
				public void changedUpdate(DocumentEvent e){
					if (add_prodQty_tf.getDocument().getLength() > 11){
						add_prodQty_lbl.setText("Quantity must be a number less than 11 digits");
						add_prodQty_lbl.setBackground(new Color(255,255,0));
						add_prodQty_lbl.setOpaque(true);					
					}else if( !validateStrToInt(add_prodQty_tf.getText() ) ){
						add_prodQty_lbl.setText("Invalid Number" );
						add_prodQty_lbl.setBackground(new Color(255,255,0));
						add_prodQty_lbl.setOpaque(true);
					}else{
						add_prodQty_lbl.setText("valid");
						add_prodQty_lbl.setOpaque(false);
					}
				}
				public void insertUpdate(DocumentEvent e){
					if (add_prodQty_tf.getDocument().getLength() > 11){
						add_prodQty_lbl.setText("Quantity must be a number less than 11 digits");
						add_prodQty_lbl.setBackground(new Color(255,255,0));
						add_prodQty_lbl.setOpaque(true);					
					}else if( !validateStrToInt(add_prodQty_tf.getText() ) ){
						add_prodQty_lbl.setText("Invalid Number" );
						add_prodQty_lbl.setBackground(new Color(255,255,0));
						add_prodQty_lbl.setOpaque(true);
					}else{
						add_prodQty_lbl.setText("valid");
						add_prodQty_lbl.setOpaque(false);
					}
				}
				public void removeUpdate(DocumentEvent e){
					if (add_prodQty_tf.getDocument().getLength() > 11){
						add_prodQty_lbl.setText("Quantity must be a number less than 11 digits");
						add_prodQty_lbl.setBackground(new Color(255,255,0));
						add_prodQty_lbl.setOpaque(true);					
					}else if( !validateStrToInt(add_prodQty_tf.getText() ) ){
						add_prodQty_lbl.setText("Invalid Number" );
						add_prodQty_lbl.setBackground(new Color(255,255,0));
						add_prodQty_lbl.setOpaque(true);
					}else{
						add_prodQty_lbl.setText("valid");
						add_prodQty_lbl.setOpaque(false);
					}
				}
			}
			);
	
	
	add_prodQty_lbl = new JLabel("Quantity must be a number less than 11 digits");
	GridBagConstraints gbc_label_3 = new GridBagConstraints();
	gbc_label_3.anchor = GridBagConstraints.WEST;
	gbc_label_3.insets = new Insets(0, 0, 5, 5);
	gbc_label_3.gridx = 2;
	gbc_label_3.gridy = 3;
	panel_7.add(add_prodQty_lbl, gbc_label_3);
	JLabel lblCategoryid = new JLabel("*** Category");
	GridBagConstraints gbc_lblCategoryid = new GridBagConstraints();
	gbc_lblCategoryid.anchor = GridBagConstraints.EAST;
	gbc_lblCategoryid.insets = new Insets(0, 0, 5, 5);
	gbc_lblCategoryid.gridx = 0;
	gbc_lblCategoryid.gridy = 4;
	panel_7.add(lblCategoryid, gbc_lblCategoryid);
	lblCategoryid.setHorizontalAlignment(SwingConstants.RIGHT);
	
	add_prodCat_cb = new JComboBox();
	GridBagConstraints gbc_add_prodCat_cb = new GridBagConstraints();
	gbc_add_prodCat_cb.fill = GridBagConstraints.HORIZONTAL;
	gbc_add_prodCat_cb.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodCat_cb.gridx = 1;
	gbc_add_prodCat_cb.gridy = 4;
	panel_7.add(add_prodCat_cb, gbc_add_prodCat_cb);
	add_prodCat_cb.setMaximumRowCount(15);
	
		//populate combobox for category and sub category
	
	fillComboBoxCategory(add_prodCat_cb);
	
	add_prodCat_cb.setRenderer(new CbCatListRenderer());
		add_prodCat_cb.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
					
						try{
						JComboBox combo = (JComboBox)e.getSource();
						CbCategoryItem item = (CbCategoryItem) combo.getSelectedItem();
						newProdCategoryID = item.getID();
						
						con_Invoice = Connect.connectionSetup();
						String query = "Select ID, Name From Category Where ParentID = " + newProdCategoryID + " Order By Name;";
						PreparedStatement pst = con_Invoice.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						
							//clean cbSubCategory before adding new sub categories
						add_prodSubCat_cb.removeAllItems(); 
						boolean rowExist = false;
						
						CbCategoryItem firstItem = new CbCategoryItem(0, "-- please select sub category --");

							//if no row returned, rs.next() returns false
						while(rs.next()){
							String str = rs.getString("Name").trim();
							int id = rs.getInt("ID");
							
							add_prodSubCat_cb.addItem(new CbCategoryItem(id, str));
							rowExist = true;
						}
						if(rowExist){
							add_prodSubCat_cb.insertItemAt(new CbCategoryItem(0, "-- please select sub category --"), 0);
							CbCategoryItem firstitem = (CbCategoryItem)add_prodSubCat_cb.getItemAt(0);
							add_prodSubCat_cb.setSelectedItem(firstitem);
							add_prodSubCat_cb.setEnabled(true);
	
						} else {
							add_prodSubCat_cb.insertItemAt(new CbCategoryItem(0, "-- No sub category available --"), 0);
							add_prodSubCat_cb.setEnabled(false);
								//at least one item must be added to the combobox, or null pointer exception occurs
						}
					
						con_Invoice.close();
						pst.close();
						rs.close();
	
						}catch (Exception e1){
							e1.printStackTrace();
						} //catch
					} //Action performed		
				}//Action Listner
				);
	
	add_prodCat_lbl = new JLabel("");
	GridBagConstraints gbc_label = new GridBagConstraints();
	gbc_label.anchor = GridBagConstraints.WEST;
	gbc_label.insets = new Insets(0, 0, 5, 5);
	gbc_label.gridx = 2;
	gbc_label.gridy = 4;
	panel_7.add(add_prodCat_lbl, gbc_label);
	
	JLabel lblSubcategoryid = new JLabel("Sub Category");
	GridBagConstraints gbc_lblSubcategoryid = new GridBagConstraints();
	gbc_lblSubcategoryid.anchor = GridBagConstraints.EAST;
	gbc_lblSubcategoryid.insets = new Insets(0, 0, 5, 5);
	gbc_lblSubcategoryid.gridx = 0;
	gbc_lblSubcategoryid.gridy = 5;
	panel_7.add(lblSubcategoryid, gbc_lblSubcategoryid);
	lblSubcategoryid.setHorizontalAlignment(SwingConstants.RIGHT);
	
	add_prodSubCat_cb = new JComboBox();
	GridBagConstraints gbc_add_prodSubCat_cb = new GridBagConstraints();
	gbc_add_prodSubCat_cb.fill = GridBagConstraints.HORIZONTAL;
	gbc_add_prodSubCat_cb.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodSubCat_cb.gridx = 1;
	gbc_add_prodSubCat_cb.gridy = 5;
	panel_7.add(add_prodSubCat_cb, gbc_add_prodSubCat_cb);
	add_prodSubCat_cb.setRenderer(new CbCatListRenderer());
	
	add_prodSubCat_lbl = new JLabel("            ");
	GridBagConstraints gbc_label_4 = new GridBagConstraints();
	gbc_label_4.anchor = GridBagConstraints.WEST;
	gbc_label_4.insets = new Insets(0, 0, 5, 5);
	gbc_label_4.gridx = 2;
	gbc_label_4.gridy = 5;
	panel_7.add(add_prodSubCat_lbl, gbc_label_4);
	//JLabel lblUnitCost = new JLabel("Unit Cost");		
	//	   lblUnitCost.setBounds(30, 219, 51, 16);		
	JLabel lblSalePrice = new JLabel("*** Sale Price");
	GridBagConstraints gbc_lblSalePrice = new GridBagConstraints();
	gbc_lblSalePrice.anchor = GridBagConstraints.EAST;
	gbc_lblSalePrice.insets = new Insets(0, 0, 5, 5);
	gbc_lblSalePrice.gridx = 0;
	gbc_lblSalePrice.gridy = 6;
	panel_7.add(lblSalePrice, gbc_lblSalePrice);
	lblSalePrice.setHorizontalAlignment(SwingConstants.RIGHT);
	//textUnitCost = new JTextField();			
	//textUnitCost.setBounds(150, 215, 116, 22);			
	//textUnitCost.setColumns(10);
	add_prodSalePrice_tf = new JTextField();
	GridBagConstraints gbc_add_prodSalePrice_tf = new GridBagConstraints();
	gbc_add_prodSalePrice_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodSalePrice_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodSalePrice_tf.gridx = 1;
	gbc_add_prodSalePrice_tf.gridy = 6;
	panel_7.add(add_prodSalePrice_tf, gbc_add_prodSalePrice_tf);
	add_prodSalePrice_tf.setColumns(10);
	add_prodSalePrice_tf.getDocument().addDocumentListener(
			new DocumentListener(){
				public void changedUpdate(DocumentEvent e){
					
					if( !validateDouble(add_prodSalePrice_tf.getText())){
						add_prodSalePrice_lbl.setText("Invalid Number" );
						add_prodSalePrice_lbl.setBackground(new Color(255,255,0));
						add_prodSalePrice_lbl.setOpaque(true);
						
					}else{
						add_prodSalePrice_lbl.setText("valid");
						add_prodSalePrice_lbl.setOpaque(false);
					}
				}
				public void insertUpdate(DocumentEvent e){
					if( !validateDouble(add_prodSalePrice_tf.getText())){
						add_prodSalePrice_lbl.setText("Invalid Number" );
						add_prodSalePrice_lbl.setBackground(new Color(255,255,0));
						add_prodSalePrice_lbl.setOpaque(true);
						
					}else{
						add_prodSalePrice_lbl.setText("valid");
						add_prodSalePrice_lbl.setOpaque(false);
					}
				}
				public void removeUpdate(DocumentEvent e){
					if( !validateDouble(add_prodSalePrice_tf.getText())){
						add_prodSalePrice_lbl.setText("Invalid Number" );
						add_prodSalePrice_lbl.setBackground(new Color(255,255,0));
						add_prodSalePrice_lbl.setOpaque(true);
						
					}else{
						add_prodSalePrice_lbl.setText("valid");
						add_prodSalePrice_lbl.setOpaque(false);
					}
				}
			}
			);
	
	add_prodSalePrice_lbl = new JLabel("Sale Price must be a number");
	GridBagConstraints gbc_label_1 = new GridBagConstraints();
	gbc_label_1.anchor = GridBagConstraints.WEST;
	gbc_label_1.insets = new Insets(0, 0, 5, 5);
	gbc_label_1.gridx = 2;
	gbc_label_1.gridy = 6;
	panel_7.add(add_prodSalePrice_lbl, gbc_label_1);
	//JLabel lblSupplierId = new JLabel("Supplier ID");	
	//	   lblSupplierId.setBounds(30, 278, 115, 16);	
	JLabel lblNote = new JLabel("Notes");
	GridBagConstraints gbc_lblNote = new GridBagConstraints();
	gbc_lblNote.anchor = GridBagConstraints.EAST;
	gbc_lblNote.insets = new Insets(0, 0, 5, 5);
	gbc_lblNote.gridx = 0;
	gbc_lblNote.gridy = 7;
	panel_7.add(lblNote, gbc_lblNote);
	lblNote.setHorizontalAlignment(SwingConstants.RIGHT);
	add_prodNotes_tf = new JTextField();
	GridBagConstraints gbc_add_prodNotes_tf = new GridBagConstraints();
	gbc_add_prodNotes_tf.fill = GridBagConstraints.BOTH;
	gbc_add_prodNotes_tf.gridheight = 3;
	gbc_add_prodNotes_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodNotes_tf.gridx = 1;
	gbc_add_prodNotes_tf.gridy = 7;
	panel_7.add(add_prodNotes_tf, gbc_add_prodNotes_tf);
	add_prodNotes_tf.setColumns(10);
	
	add_prodNotes_lbl = new JLabel("Notes must be 80 char or less");
	GridBagConstraints gbc_label_5 = new GridBagConstraints();
	gbc_label_5.anchor = GridBagConstraints.WEST;
	gbc_label_5.insets = new Insets(0, 0, 5, 5);
	gbc_label_5.gridx = 2;
	gbc_label_5.gridy = 7;
	panel_7.add(add_prodNotes_lbl, gbc_label_5);
	add_prodNotes_tf.getDocument().addDocumentListener(
			new DocumentListener(){
				
				public void changedUpdate(DocumentEvent e){
					int len = add_prodNotes_tf.getDocument().getLength();
					if(len > 80 ){
						add_prodNotes_lbl.setText("Note must be 80 letters or less - currently " + len +" letters" );
						add_prodNotes_lbl.setBackground(new Color(255,255,0));
						add_prodNotes_lbl.setOpaque(true);
					}else{
						add_prodNotes_lbl.setText("valid - currently " + len +" letters");
						add_prodNotes_lbl.setOpaque(true);
					}
				}
				public void insertUpdate(DocumentEvent e){
					int len = add_prodDesc_tf.getDocument().getLength();
					if(len > 80 ){
						add_prodDesc_lbl.setText("Description must be 80 letters or less - currently " + len +" letters" );
						add_prodDesc_lbl.setBackground(new Color(255,255,0));
						add_prodDesc_lbl.setOpaque(true);
					}else{
						add_prodDesc_lbl.setText("valid - currently " + len +" letters");
						add_prodDesc_lbl.setOpaque(true);
					}
				}
				public void removeUpdate(DocumentEvent e){
					int len = add_prodDesc_tf.getDocument().getLength();
					if(len > 80 ){
						add_prodDesc_lbl.setText("Description must be 80 letters or less - currently " + len +" letters" );
						add_prodDesc_lbl.setBackground(new Color(255,255,0));
						add_prodDesc_lbl.setOpaque(true);
					}else{
						add_prodDesc_lbl.setText("valid - currently " + len +" letters");
						add_prodDesc_lbl.setOpaque(true);
					}
				}
			}
			);

	
	JButton btnAdd = new JButton("Add New Product");
	GridBagConstraints gbc_btnAdd = new GridBagConstraints();
	gbc_btnAdd.anchor = GridBagConstraints.WEST;
	gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
	gbc_btnAdd.gridx = 1;
	gbc_btnAdd.gridy = 10;
	panel_7.add(btnAdd, gbc_btnAdd);
	
	btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try{
				int flag = 0;
				String prodID = add_prodID_tf.getText();
				String prodName = add_prodName_tf.getText();
				String prodDesc = add_prodDesc_tf.getText();
				String prodQty = add_prodQty_tf.getText();
				String prodSalePrice = add_prodSalePrice_tf.getText();
				CbCategoryItem catItem = (CbCategoryItem) add_prodCat_cb.getSelectedItem();
				CbCategoryItem subCatItem = (CbCategoryItem) add_prodSubCat_cb.getSelectedItem();
				
				
				int catID = catItem.getID();
				int subCatID = 0;
				// if user does not select category, give error
				if ( catID == 0 ) flag = 2;
				System.out.println("category ID is /" + catID);
				//sub category throws exception if user does not select category
				System.out.println("sub category ID is /" + subCatID);
				
			    	//validate user input 
			    if ( !validateProdID(prodID) ) flag = 1;
			    if ( !validateStringSize(prodName, sizeProdName) ) flag = 1;
			    
			    if ( prodDesc.isEmpty()){
			    	prodDesc = ""; //assign "" to product description if it is empty
			    } else {  if ( !validateStringSize(prodDesc, sizeProdDesc) ) flag = 1;}
			    if ( !validateStringSize(prodDesc, sizeProdDesc) ) flag = 1;
			    if ( prodQty.isEmpty()){
			    	prodQty = "0"; //assign 0 to product quantity if it is empty
			    } else {  if ( !validateIntSize(prodQty, sizeProdQty) ) flag = 1;}
			    
			    if ( !validateDouble(prodSalePrice) ) flag = 1;

			    if (flag == 1){
			    	JOptionPane.showMessageDialog(null, "Invalid input - try again");
			    }
			    else if (flag == 2 ){
			    	JOptionPane.showMessageDialog(null, "Category is required");
			    }
			    else if ( flag == 0 )
			    {
					con_Invoice = Connect.connectionSetup();
					//update product table
					String query = "insert into product (ID, Name, Description, Quantity, CategoryID, SubCategoryID, SalePrice, Notes)"
							+ "values ( ?,?,?,?,?,?,?,?)";
					PreparedStatement pst = con_Invoice.prepareStatement(query);		 
					pst.setInt(1, Integer.parseInt( prodID ));
					pst.setString(2, prodName);
					pst.setString(3, prodDesc);
					pst.setInt(4, Integer.parseInt( prodQty ) );
					pst.setInt(5, catID);
					if (subCatID != 0){
						pst.setInt(6, subCatID);
					} else {
						pst.setNull(6, java.sql.Types.INTEGER);
					}
					
					pst.setDouble(7, Double.parseDouble(prodSalePrice));
					pst.setString(8, add_prodNotes_tf.getText());
					
												
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "New Product Added");
					
					
					pst.close();
					con_Invoice.close();
			    }// if - validate user input 
			}
			 catch (SQLException ex) {
                    if (ex.getSQLState().startsWith("23")) {
                          JOptionPane.showMessageDialog(null, "Duplicated Product ID - please try again");
                    } 
			 }catch (Exception e1) {
				e1.printStackTrace();
			}
			refreshInventoryTable();
		}
	});




JPanel panel_3 = new JPanel();
	   panel_3.setBounds(159, 72, 445, 357);
		
	 
	  panel_Inv_EditDetails.add(panel_3);
	   
	  tabbedPane_Inventory.addTab("Edit Details", null, panel_Inv_EditDetails, null);

		

		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 1.0};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel label_Edit_ID = new JLabel("ID");
		GridBagConstraints gbc_label_Edit_ID = new GridBagConstraints();
		gbc_label_Edit_ID.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_ID.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_ID.gridx = 1;
		gbc_label_Edit_ID.gridy = 0;
		panel_3.add(label_Edit_ID, gbc_label_Edit_ID);
		
		edit_prodID_tf = new JTextField();
		edit_prodID_tf.setColumns(10);
		GridBagConstraints gbc_tfEditID = new GridBagConstraints();
		gbc_tfEditID.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditID.gridx = 2;
		gbc_tfEditID.gridy = 0;
		panel_3.add(edit_prodID_tf, gbc_tfEditID);
		
		JLabel label_Edit_Name = new JLabel("Name");
		GridBagConstraints gbc_label_Edit_Name = new GridBagConstraints();
		gbc_label_Edit_Name.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Name.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Name.gridx = 1;
		gbc_label_Edit_Name.gridy = 1;
		panel_3.add(label_Edit_Name, gbc_label_Edit_Name);
		
		edit_prodName_tf = new JTextField();
		edit_prodName_tf.setColumns(10);
		GridBagConstraints gbc_tfEditName = new GridBagConstraints();
		gbc_tfEditName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEditName.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditName.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditName.gridx = 2;
		gbc_tfEditName.gridy = 1;
		panel_3.add(edit_prodName_tf, gbc_tfEditName);
		
		JLabel label_Edit_Desc = new JLabel("Description");
		GridBagConstraints gbc_label_Edit_Desc = new GridBagConstraints();
		gbc_label_Edit_Desc.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_Edit_Desc.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Desc.gridx = 1;
		gbc_label_Edit_Desc.gridy = 2;
		panel_3.add(label_Edit_Desc, gbc_label_Edit_Desc);
		
		edit_prodDesc_tf = new JTextField(); 
		edit_prodDesc_tf.setColumns(10);
		GridBagConstraints gbc_tfEditDescription = new GridBagConstraints();
		gbc_tfEditDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEditDescription.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditDescription.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditDescription.gridx = 2;
		gbc_tfEditDescription.gridy = 2;
		panel_3.add(edit_prodDesc_tf, gbc_tfEditDescription);
		
		JLabel label_Edit_Quantity = new JLabel("Quantity");
		GridBagConstraints gbc_label_Edit_Quantity = new GridBagConstraints();
		gbc_label_Edit_Quantity.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Quantity.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Quantity.gridx = 1;
		gbc_label_Edit_Quantity.gridy = 3;
		panel_3.add(label_Edit_Quantity, gbc_label_Edit_Quantity);
		
		edit_prodQty_tf = new JTextField();
		edit_prodQty_tf.setColumns(10);
		GridBagConstraints gbc_tfEditQuantity = new GridBagConstraints();
		gbc_tfEditQuantity.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditQuantity.gridx = 2;
		gbc_tfEditQuantity.gridy = 3;
		panel_3.add(edit_prodQty_tf, gbc_tfEditQuantity);
		
		JLabel label_Edit_Category = new JLabel("CategoryID");
		GridBagConstraints gbc_label_Edit_Category = new GridBagConstraints();
		gbc_label_Edit_Category.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Category.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Category.gridx = 1;
		gbc_label_Edit_Category.gridy = 4;
		panel_3.add(label_Edit_Category, gbc_label_Edit_Category);
		
		edit_prodCatID_tf = new JTextField();
		edit_prodCatID_tf.setColumns(10);
		GridBagConstraints gbc_tfEditCategoryID = new GridBagConstraints();
		gbc_tfEditCategoryID.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditCategoryID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditCategoryID.gridx = 2;
		gbc_tfEditCategoryID.gridy = 4;
		panel_3.add(edit_prodCatID_tf, gbc_tfEditCategoryID);
		
		JLabel label_Edit_SubCategory = new JLabel("SubCategoryID");
		GridBagConstraints gbc_label_Edit_SubCategory = new GridBagConstraints();
		gbc_label_Edit_SubCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_Edit_SubCategory.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_SubCategory.gridx = 1;
		gbc_label_Edit_SubCategory.gridy = 5;
		panel_3.add(label_Edit_SubCategory, gbc_label_Edit_SubCategory);
		
		edit_prodSubCatID_tf = new JTextField();
		edit_prodSubCatID_tf.setColumns(10);
		GridBagConstraints gbc_tfEditSubCategoryID = new GridBagConstraints();
		gbc_tfEditSubCategoryID.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditSubCategoryID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditSubCategoryID.gridx = 2;
		gbc_tfEditSubCategoryID.gridy = 5;
		panel_3.add(edit_prodSubCatID_tf, gbc_tfEditSubCategoryID);
		
		/*
		JLabel label_Edit_UnitCost = new JLabel("Unit Cost");
		GridBagConstraints gbc_label_Edit_UnitCost = new GridBagConstraints();
		gbc_label_Edit_UnitCost.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_UnitCost.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_UnitCost.gridx = 1;
		gbc_label_Edit_UnitCost.gridy = 6;
		panel_3.add(label_Edit_UnitCost, gbc_label_Edit_UnitCost);
		*/
		//tfEditUnitCost = new JTextField();
		//tfEditUnitCost.setColumns(10);
		//GridBagConstraints gbc_tfEditUnitCost = new GridBagConstraints();
		//gbc_tfEditUnitCost.anchor = GridBagConstraints.NORTHWEST;
		//gbc_tfEditUnitCost.insets = new Insets(0, 0, 5, 0);
		//gbc_tfEditUnitCost.gridx = 2;
		//gbc_tfEditUnitCost.gridy = 6;
		//panel_3.add(tfEditUnitCost, gbc_tfEditUnitCost);
		
		JLabel label_Edit_SalePrice = new JLabel("Sale Price");
		GridBagConstraints gbc_label_Edit_SalePrice = new GridBagConstraints();
		gbc_label_Edit_SalePrice.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_Edit_SalePrice.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_SalePrice.gridx = 1;
		gbc_label_Edit_SalePrice.gridy = 7;
		panel_3.add(label_Edit_SalePrice, gbc_label_Edit_SalePrice);
		
		edit_prodSalePrice_tf = new JTextField();
		edit_prodSalePrice_tf.setColumns(10);
		GridBagConstraints gbc_tfEditSalePrice = new GridBagConstraints();
		gbc_tfEditSalePrice.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditSalePrice.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditSalePrice.gridx = 2;
		gbc_tfEditSalePrice.gridy = 7;
		panel_3.add(edit_prodSalePrice_tf, gbc_tfEditSalePrice);
		/*
		JLabel label_Edit_SupplierID = new JLabel("Supplier ID");
		GridBagConstraints gbc_label_Edit_SupplierID = new GridBagConstraints();
		gbc_label_Edit_SupplierID.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_Edit_SupplierID.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_SupplierID.gridx = 1;
		gbc_label_Edit_SupplierID.gridy = 8;
		panel_3.add(label_Edit_SupplierID, gbc_label_Edit_SupplierID);
		*/
		
		/*
		tfEditSupplierID = new JTextField();
		tfEditSupplierID.setText("");
		tfEditSupplierID.setColumns(10);
		GridBagConstraints gbc_tfEditSupplierID = new GridBagConstraints();
		gbc_tfEditSupplierID.anchor = GridBagConstraints.SOUTHWEST;
		gbc_tfEditSupplierID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditSupplierID.gridx = 2;
		gbc_tfEditSupplierID.gridy = 8;
		panel_3.add(tfEditSupplierID, gbc_tfEditSupplierID);
		*/
		JLabel label_Edit_Notes = new JLabel("Notes");
		GridBagConstraints gbc_label_Edit_Notes = new GridBagConstraints();
		gbc_label_Edit_Notes.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Notes.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Notes.gridx = 1;
		gbc_label_Edit_Notes.gridy = 9;
		panel_3.add(label_Edit_Notes, gbc_label_Edit_Notes);
		
		edit_prodNotes_tf = new JTextField();
		GridBagConstraints gbc_textEditNotes = new GridBagConstraints();
		gbc_textEditNotes.gridheight = 2;
		gbc_textEditNotes.fill = GridBagConstraints.BOTH;
		gbc_textEditNotes.gridx = 2;
		gbc_textEditNotes.gridy = 9;
		panel_3.add(edit_prodNotes_tf, gbc_textEditNotes);
		edit_prodNotes_tf.setColumns(10);
		
		
		
		// ============== Create Order page =======================================================
		// ========================================================================================
		
		JPanel jp_createOrder = new JPanel();
		tabbedPane_Inventory.addTab("Create Order", null, jp_createOrder, null);
		jp_createOrder.setLayout(new BorderLayout(0, 0));
		
		JPanel jp_createOrder_north = new JPanel();
		jp_createOrder.add(jp_createOrder_north, BorderLayout.NORTH);
		FlowLayout fl_jp_createOrder_north = (FlowLayout) jp_createOrder_north.getLayout();
		fl_jp_createOrder_north.setAlignment(FlowLayout.LEFT);
		
		JButton btn_backToInventory = new JButton("Back to Inventory");
		btn_backToInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabbedPane_Inventory.setSelectedIndex(0); 
			}
		});
		jp_createOrder_north.add(btn_backToInventory);
		
		ord_Sup_cb.setRenderer(new CbSupListRenderer());
		
		JButton btn_createOrderSheets = new JButton("Create Order Sheet(s)");
		btn_createOrderSheets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "This will create new order(s). Do you want to proceed?", "New Order will be created", dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION) {
					
				//stop editing otherwise null may be assigend to order quanitity
				if(table_createOrder.isEditing()){
					table_createOrder.getCellEditor().stopCellEditing();
				}
				int flag = 0;
				
				for (int i = 0; i < table_createOrder.getRowCount(); i++){
					if (table_createOrder.getValueAt(i, 8) == null)flag = 1;
					if (table_createOrder.getValueAt(i, 9) == null)flag = 1;
				}
				
				if (flag == 0){ 
				Vector<String> sup_list = new Vector<String>();
				Vector<String> uni_list = new Vector<String>();
				String orderID = ""; 
				//String supplierID;
				
				//******get all supplier rows
				for (int i=0; i < table_createOrder.getRowCount(); i++ ){  
					sup_list.add((String) table_createOrder.getValueAt(i, 8));
				}
				Collections.sort(sup_list);//sort the vector
				
				for (int i = 0; i < sup_list.size(); i++){
					if (i == 0 ){uni_list.add(sup_list.get(i)); }
					else if ( !sup_list.get(i).equals(uni_list.lastElement()) ){
						uni_list.add(sup_list.get(i));
					}
				}// end of for
				
		    	try {
					Connection connForCreateOrder = Connect.connectionSetup();
					
				//loop through each unique supplier and create order and order details for the supplier
				for (int i = 0; i < uni_list.size(); i++ ) {
					String supplierID =  uni_list.get(i).substring(0, uni_list.get(i).indexOf("-") - 1);
					
					
					//insert new order
					String query = "Insert into `order` (SupplierID, EmployeeID, CreateDate, Cost ) "
							                 + "VALUES (" + supplierID + ", 1, NOW(), 1000)";		
					PreparedStatement pst = connForCreateOrder.prepareStatement(query);
					pst.executeUpdate(); 
					
					//get new order ID
					String query2 = "SELECT Max(ID) FROM `order` ";
					pst = connForCreateOrder.prepareStatement(query2);
					
					ResultSet rs = pst.executeQuery(); 	
					while(rs.next()){
					orderID = Integer.toString( (int) rs.getObject(1) );	
					}
					System.out.println("new order ID is " + orderID);
					
					//for loop for each new order details
					String sup_fortherow;
					Object productID ;
					Object orderQty;
					for (int k = 0; k < table_createOrder.getRowCount(); k++){
						sup_fortherow = ((String) table_createOrder.getValueAt(k, 8)).substring(0, ((String)table_createOrder.getValueAt(k,8)).indexOf("-") - 1);
						if (sup_fortherow.equals(supplierID))
						{
							productID =  table_createOrder.getValueAt(k, 0);
							orderQty =  (String)table_createOrder.getValueAt(k, 9);
					System.out.println("OrderID: " +orderID +" / productID: " + productID + " / orderQty: " + orderQty);
					
						query = "Insert into orderdetail (OrderID, ProductID, OrderedQuantity) VALUES (" 
						+ orderID + ", " + productID + ", "	+ orderQty +")";
						
						pst = connForCreateOrder.prepareStatement(query);
						pst.executeUpdate();
						

						} // end of if
							
					}// end of for loop (int k)
					pst.close();
					
				} // end of for loop (int i)
				connForCreateOrder.close();
				JOptionPane.showMessageDialog(null, "New Order record(s) have been created");
				scrollPane_ceateOrder.setViewportView(null);
				refreshInventoryTable();
				tabbedPane_Inventory.setSelectedIndex(0); 
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // end of catch 
				} // end of if (flag == 0) 
				
				else JOptionPane.showMessageDialog(null, "Supplier and/or order quantity cannot be left blank");
				}// end of Yes option
				
			} //end of action performed
		});
		jp_createOrder_north.add(btn_createOrderSheets);
		
		JPanel jp_createOrder_center = new JPanel();
		jp_createOrder.add(jp_createOrder_center, BorderLayout.CENTER);
		jp_createOrder_center.setLayout(new BorderLayout(0, 0));
		jp_createOrder_center.add(scrollPane_ceateOrder);
		jp_createOrder_center.setMinimumSize(new Dimension(100, 700));
		
		
		JPanel jp_createOrder_south = new JPanel();
		jp_createOrder.add(jp_createOrder_south, BorderLayout.SOUTH);
		jp_createOrder_south.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Orders less than minimum order may be rejected");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		jp_createOrder_south.add(lblNewLabel, BorderLayout.NORTH);
		jp_createOrder_south.add(scrollPane_createOrderSummary);
		scrollPane_createOrderSummary.setViewportView(table_createOrderSummary);
		
		scrollPane_createOrderSummary.setPreferredSize(new Dimension(200,200));

		

}// end of Constructor Inventory() =========================================================================================================


//======================================================================================================================================
// Begining of inventory class functions
 
    public boolean validateStrToInt(String s){
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
	public boolean validateProdID(String id){
		if(id.length() != 8) return false;
		for(int i = 0; i < id.length(); i++)
		{
			if(id.charAt(i) < '0' || id.charAt(i) > '9') return false;
		}
		return true;
	}
	
	public boolean validateIntSize(String value, int max){
		if( value.length() > max ) return false;
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException ne){
			return false;
		}
	}
	
	public boolean validateDouble(String value){
	try{
		Double.parseDouble(value);
		return true;
		} catch (NumberFormatException ne){
			return false;
		}
	}
	
	public boolean validateStringSize(String value, int max){
		if(value.length() > max ) return false;
		return true;
	}

	public void fillComboBoxCategory(JComboBox<CbCategoryItem> cb){ //*********************************************
	try{
		con_Invoice = Connect.connectionSetup();
		String query = "Select ID, Name FROM category WHERE ParentID is NULL ORDER BY Name";
		PreparedStatement pst = con_Invoice.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
		CbCategoryItem firstItem = new CbCategoryItem(0, "-- Please select Category --");
		cb.addItem(firstItem);
			while(rs.next()){
			String str = rs.getString("Name").trim();
			int id = rs.getInt("ID");
			CbCategoryItem item = new CbCategoryItem(id, str);
			cb.addItem(item);
				
				//  while(rs.next()){
				//	int id = rs.getInt("ID");
				//	String category = rs.getString("Name");
				//	Object[] item = new Object[]{id, category};
				//	cbCategory.addItem(item);
				//  }	
		} 

		
		con_Invoice.close();
		pst.close();
		rs.close();
		
		
	}catch (Exception e){
		e.printStackTrace();
	}
	}// end of fillComboBoxCategory()
	
	public Vector<Integer> getSelectedRows(){//*********************************************
	Vector<Integer> selectedRows = new Vector<Integer>();
	int rowCount = 	table_inventoryList.getRowCount();
	for(int i = 0; i < rowCount; i++)
	{
	if(table_inventoryList.getValueAt(i, 0) == Boolean.TRUE)
	{
		selectedRows.add((Integer) table_inventoryList.getValueAt(i, 1));
	}
	}
	return selectedRows;
	}
	
	public void jumpToEditPage(int ID){//*********************************************
	
	try{
	con_Invoice = Connect.connectionSetup();
	String query = "Select * from Product Where ID = "+ ID;
	PreparedStatement pst = con_Invoice.prepareStatement(query);
	ResultSet rs = pst.executeQuery();
	if(rs.next())
	{
		edit_prodID_tf.setText(Integer.toString(rs.getInt("ID")));
		edit_prodName_tf.setText(rs.getString("Name"));
		edit_prodDesc_tf.setText(rs.getString("Description"));
		edit_prodQty_tf.setText(Integer.toString(rs.getInt("Quantity")));
		edit_prodCatID_tf.setText(Integer.toString(rs.getInt("CategoryID")));
		edit_prodSubCatID_tf.setText(Integer.toString(rs.getInt("SubCategoryID")));
		//tfEditUnitCost.setText(Integer.toString(rs.getInt("UnitCost")));
		edit_prodSalePrice_tf.setText(Integer.toString(rs.getInt("SalePrice")));
		//tfEditSupplierID.setText(Integer.toString(rs.getInt("SupplierID")));
		edit_prodNotes_tf.setText(rs.getString("Notes"));
								
		pst.close();
		rs.close();
		con_Invoice.close();
		tabbedPane_Inventory.setSelectedIndex(2); //open Edit panel
	
	}
	}catch (Exception ex){  ex.printStackTrace(); 	}
	
	}
	
	public static void filterByMultiColumn2(){//********************************************
		RowFilter<MyTableModelClass,Object> rf = null;
		List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		filters.add(RowFilter.regexFilter(".*"+filter.getID().getText()+".*",1));
		filters.add(RowFilter.regexFilter(".*"+filter.getCategory().getText()+".*", 2));
		filters.add(RowFilter.regexFilter(".*"+filter.getSubCategory().getText()+".*", 3));
		filters.add(RowFilter.regexFilter(".*"+filter.getProdName().getText()+".*", 4));
		filters.add(RowFilter.regexFilter(".*"+filter.getDesc().getText()+".*", 5));
		filters.add(RowFilter.regexFilter(".*"+filter.getNote().getText()+".*", 8));
		
		if (!filter.getQtyGt().getText().isEmpty() ){
	    String gt = filter.getQtyGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(gt), 6));
		}
		if (!filter.getQtyLt().getText().isEmpty() ){
		String lt = filter.getQtyLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(lt), 6));
		}
		if (!filter.getSaleGt().getText().isEmpty() ){
		String gt = filter.getSaleGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(gt), 8));
		}
		if (!filter.getSaleLt().getText().isEmpty() ){
		String lt = filter.getSaleLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(lt), 8));
		}
		
		rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);
		
		if(
				filter.getID().getText().isEmpty() &&
				filter.getProdName().getText().isEmpty() &&
				filter.getDesc().getText().isEmpty() &&
				filter.getCategory().getText().isEmpty() &&
				filter.getSubCategory().getText().isEmpty() &&
				filter.getNote().getText().isEmpty() &&
				filter.getQtyGt().getText().isEmpty() &&
				filter.getQtyLt().getText().isEmpty() &&
				filter.getSaleGt().getText().isEmpty() &&
				filter.getSaleLt().getText().isEmpty() 
			){
			jl_filter_status.setOpaque(false);
			jl_filter_status.setText("Filter Not Applied");
			
		}else {
			jl_filter_status.setText("Filter Applied");
			jl_filter_status.setOpaque(true);
			jl_filter_status.setBackground(new Color(255, 0, 0));
		}
		
	}
	
	
	
	
	//===================================================================================================
	public void createOrderTable(StringBuilder mySelection){
		
		createOrderSummary(mySelection);
	
	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    Vector<Integer> selectedRows = getSelectedRows();
    try{
    	
    	con_Invoice = Connect.connectionSetup();
    	String query = "Select p.ID, c.Name as Category, sc.Name as 'Sub Category', p.Name, p.Quantity, "
      			+ " SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered'	"
    			+ " FROM Product p "
    			+ " INNER JOIN Category as c on c.ID = p.CategoryID"
    			+ "	INNER JOIN Category as sc on sc.ID = p.SubCategoryID "
    			+ " INNER JOIN orderdetail as od on od.ProductID = p.ID "
    			+ " WHERE p.ID IN ( " + mySelection + " )"
    			+ "	GROUP BY p.ID";
    			
		PreparedStatement pst = con_Invoice.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount();
		
		columnNames.addElement("ID"); 				//1
		columnNames.addElement("Category");			//2
		columnNames.addElement("Sub Category");		//3
		columnNames.addElement("Name");				//4
		//columnNames.addElement("Description");
		columnNames.addElement("Quantity on Hand");	//5
		columnNames.addElement("To be delivered");	//6
		//columnNames.addElement("Sale Price");
		columnNames.addElement("Supplier");			//7
		columnNames.addElement("Prev. @Price");		//8 Unit cost from product supplier
		columnNames.addElement("Order Quantity");	//9 user specifies order quantity
		columnNames.addElement("Sub Total");		//10 prev @price x order quantity
		

		//Get row data
		while(rs.next())
		{
		Vector<Object> row = new Vector<Object>(columnCount);
		
		for (int i = 1; i <= columnCount; i++)  //column count in the result set
		{
			row.addElement(rs.getObject(i));
		}
	
		data.addElement(row);
		}
		
		pst.close();
		rs.close();
		con_Invoice.close();
		
    }catch (Exception e1) 
	{	
	e1.printStackTrace();
	}
    
    
    TM_CreateOrder MyModel_2 = new TM_CreateOrder(data, columnNames);
    
    table_createOrder = new JTable(MyModel_2)
    {
		public TableCellEditor getCellEditor(int row, int column)
		{
			int modelColumn = convertColumnIndexToModel(column);
			int rowNumber = table_createOrder.getRowCount();
			
				// pick supplier for each product
			if (modelColumn == 6 ){
				try{
					Connection connectForSupplierList = Connect.connectionSetup();
					int ID =  (Integer) this.getModel().getValueAt(row, 0);
					for (int i=1; i <= rowNumber; i++){ //add different combobox for each row
					
					String query2 = "Select s.ID, s.Name as Supplier, s.MinimumOrderCost, ps.UnitCost "
							+ "FROM Supplier s "
							+ "INNER JOIN product_supplier ps ON s.ID = ps.SupplierID "
							+ "INNER JOIN product p ON p.ID = ps.ProductID "
							+ "WHERE p.ID = " + Integer.toString(ID);
					PreparedStatement pst2 = connectForSupplierList.prepareStatement(query2);
					ResultSet rs2 = pst2.executeQuery();
					JComboBox<CbSupItem> cbSup = new JComboBox<CbSupItem>();
					
					//ord_Sup_cb.removeAllItems();
					while(rs2.next()){
						//ord_Sup_cb.addItem(new CbSupItem(rs2.getInt(1), rs2.getString(2), rs2.getDouble(3)));
						cbSup.addItem(new CbSupItem(rs2.getInt(1), rs2.getString(2), rs2.getDouble(3), rs2.getDouble(4)));
					}
					cbSup.setRenderer(new CbSupListRenderer());
					pst2.close();
					rs2.close();
					connectForSupplierList.close();
					
					return new DefaultCellEditor( cbSup );
					}// end of for
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}// end of if
			
			if (modelColumn == 8 ){
				return new DefaultCellEditor( new JTextField() );
			}
			//else return super.getCellEditor(row, column);
			return cellEditor;
		}
	};
    
	TableRowSorter sorter_2 = new TableRowSorter<TM_CreateOrder>(MyModel_2);
	table_createOrder.setRowSorter(sorter_2); 
	table_createOrder.setRowHeight(25);
	scrollPane_ceateOrder.setViewportView(table_createOrder);
	setTMLforCreateOrderTable(); //function to add table model listner
	
	Dimension tableSize = table_createOrder.getPreferredSize();
	TableColumnModel tcm2 = table_createOrder.getColumnModel();
	tcm2.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //ID
	tcm2.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Category
	tcm2.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //SubCategory
	tcm2.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Name 
	tcm2.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Quantity
	tcm2.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //To be delivered
	tcm2.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Supplier
	tcm2.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Prev @price
	tcm2.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Order Quantity
	tcm2.getColumn(9).setPreferredWidth(Math.round((tableSize.width)* 0.07f)); //Order Total
} //createOrderTable()

	public void createOrderSummary (StringBuilder mySelection){
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();
	    Vector<Integer> selectedRows = getSelectedRows();
	    try{
	    	
	    	con_Invoice = Connect.connectionSetup();
	    	String query = 
	    			"Select Distinct s.ID, s.Name as Supplier, s.MinimumOrderCost "
	    			+ "FROM supplier s "
	    			+ "INNER JOIN product_supplier ps ON s.ID = ps.SupplierID "
	    			+ "WHERE ps.ProductID IN ( " + mySelection + " )";

	    			
			PreparedStatement pst = con_Invoice.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();
			
			columnNames.addElement("Supplier ID"); 				
			columnNames.addElement("Supplier"); 
			columnNames.addElement("Minimum Order");
			columnNames.addElement("Order Total");

			//Get row data
			/*
			while(rs.next()){
				Vector<Object> row = new Vector<Object>(columnCount);
				for (int i = 1; i <= columnCount; i++)  {
					row.addElement(rs.getObject(i));
				}
				data.addElement(row);
			} //while 
			*/
			pst.close();
			rs.close();
			con_Invoice.close();
	    }catch (Exception e1) {	
		e1.printStackTrace();
		}
	    TM_CreateOrderSummary myModel_createOrderSummary = new TM_CreateOrderSummary(data, columnNames);
	    
	    table_createOrderSummary = new JTable(myModel_createOrderSummary);
	    scrollPane_createOrderSummary.setViewportView(table_createOrderSummary);
	    table_createOrderSummary.setRowHeight(30);
	}
	

	
	//===========================================================================
	// Table Model Listener for updating prev @ price, Order Total column for table_createORder
	private void setTMLforCreateOrderTable(){
		tml_CreateTable = new TableModelListener(){
		
			TableModel model = table_createOrder.getModel();
			TableModel model_summary = table_createOrderSummary.getModel();
			CbSupItem sup;
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE){ //#10
					int row = e.getFirstRow();
					int col = e.getColumn();
					
					if(col == 6){ //#15
						sup  = (CbSupItem) model.getValueAt(row, col);
						if (sup != null){ //#21
							double unitP = sup.getUnitCost();
						    model.setValueAt(unitP, row, col+1);
						    
						      //add row to summary 
						    boolean idExist = false;
							    for(int i = 0; i < model_summary.getRowCount(); i++){ //#20
							    	if (sup.getID() == (int) model_summary.getValueAt(i, 0)  ){
							    		idExist = true; 
							    	} //if
							    } //#20
						    	if (idExist == false){
						    		((DefaultTableModel) model_summary).addRow(new Object[]{ sup.getID(), sup.getSupplierName(), sup.getMinOrder()});
						    	}
						}//#21
					}//#15
					
					if(col == 8){ //#20
						sup = (CbSupItem) model.getValueAt(row, 6);
						if(sup == null ) { //user must select supplier
							JOptionPane.showMessageDialog(null,  "Please select supplier first");
						}
						else {
							int orderQty =  Integer.parseInt( (String) model.getValueAt(row, col));
							sup  = (CbSupItem) model.getValueAt(row, 6);
							double unitP = sup.getUnitCost();
							double subTotal = unitP * orderQty;
							double orderTotal = 0;
							model.setValueAt(subTotal,  row,  col+1);
							
							int targetRow = 0;
							for (int i = 0; i < model_summary.getRowCount(); i++){ //#30
								if (sup.getID() == (int) model_summary.getValueAt(i, 0)){
									targetRow = i;
								}
							}//#30
								if (model_summary.getValueAt(targetRow, 3) == null){
										orderTotal = subTotal;
										//System.out.println("option 1 order total " + orderTotal + "/ sub total "+ subTotal);
									}else {
										orderTotal = subTotal + (double)model_summary.getValueAt(targetRow, 3) ;
										//System.out.println("option 1 order total " + orderTotal + "/ sub total "+ subTotal);
										//System.out.println("value at target / " + (double)model_summary.getValueAt(targetRow, 3));
									}
								//System.out.println("set value ");
							model_summary.setValueAt(orderTotal, targetRow, 3);


						} //else 
						
					}//#20
				}//#10
				
			} //tableChanged()
			
		}; //TableModelListener()
		table_createOrder.getModel().addTableModelListener(tml_CreateTable);
	} //setTMLforCreateOrder()
	
	//===========================================================================
	public void refreshInventoryTable(){ 

	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    try{
    	con_Invoice = Connect.connectionSetup();
		
		String query = 
				" Select p.ID, "
				+ "c.Name as Category, "
				+ "cc.Name as 'Sub Category', "
				+ "p.Name as 'Product Name', "
				+ "p.Description, "
				+ "p.Quantity, "
				+ "SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered', "
				+ "p.SalePrice as 'Sales Price',  "
				+ "Notes "
				+ "FROM Product p "
				+ "INNER JOIN Category c on p.CategoryID = c.ID "
				+ "INNER JOIN Category cc on p.SubCategoryID = cc.ID "
				+ "INNER JOIN orderdetail od on p.ID = od.ProductID "
				+ "GROUP BY p.ID;";	
				

		
		PreparedStatement pst = con_Invoice.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount();
		
		columnNames.addElement("Check");
		columnNames.addElement("ID");
		columnNames.addElement("Category");
		columnNames.addElement("Sub Category");
		columnNames.addElement("Name");
		columnNames.addElement("Description");
		columnNames.addElement("Quantity");
		columnNames.addElement("To be delivered");
		//columnNames.addElement("Unit Cost");
		columnNames.addElement("Sale Price");
		//columnNames.addElement("Supplier");
		columnNames.addElement("Notes");
		
		//Get row data
		while(rs.next())
		{
		Vector<Object> row = new Vector<Object>(columnCount);
		row.addElement(Boolean.FALSE);
		
		for (int i = 1; i <=columnCount; i++)
		{
			row.addElement(rs.getObject(i));
		}
		data.addElement(row);
		}
		
		pst.close();
		rs.close();
		con_Invoice.close();
		
		}catch (Exception e1) 
		{	
		e1.printStackTrace();
		}
        
        MyTableModelClass MyModel = new MyTableModelClass(data, columnNames);
        
		table_inventoryList.setModel(MyModel);
		sorter = new TableRowSorter<MyTableModelClass>(MyModel);
		table_inventoryList.setRowSorter(sorter);
		
		
		Dimension tableSize = table_inventoryList.getPreferredSize();
		tcm = table_inventoryList.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.04f)); //check
		tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.04f)); //ID
		tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.13f)); //Category
		tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.12f)); //Sub Category
		tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Name
		tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //Description
		tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.04f)); //
		tcm.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.04f));
		tcm.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.04f));
		tcm.getColumn(9).setPreferredWidth(Math.round((tableSize.width)* 0.20f));

		table_inventoryList.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent mouseE) {
		if (mouseE.getClickCount() == 2) {
		    JTable target = (JTable)mouseE.getSource();
		    int row = target.getSelectedRow();
		    //int column = target.getSelectedColumn();
		    jumpToEditPage((int)target.getValueAt(row, 1));
		    //JOptionPane.showMessageDialog(null, "double clicked");
		}
		}
		});
		
		}// end of refreshInventoryTable()
	
	
} // end of class inventory 
//==============================================================================================
//==============================================================================================
//==============================================================================================
//==============================================================================================
//==============================================================================================
//==============================================================================================
//==============================================================================================
//==============================================================================================











class MyTableModelClass extends DefaultTableModel{ //********************************************

	public MyTableModelClass(Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override
	public Class getColumnClass(int column)
	{
	 for (int row = 0; row < getRowCount(); row++)
	 {
	     Object o = getValueAt(row, column);
	
	     if (o != null)
	     {
	         return o.getClass();
	     }
	 }
	 return Object.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		switch (columnIndex){
		case 0: return true;
		case 1: return false;
		case 2: return false;
		default: return false;
		
		}
		
	}
}




class TM_CreateOrder extends DefaultTableModel{//********************************************

	public TM_CreateOrder (Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override
	public Class getColumnClass(int column)
	{
	 for (int row = 0; row < getRowCount(); row++)
	 {
	     Object o = getValueAt(row, column);
	
	     if (o != null)
	     {
	         return o.getClass();
	     }
	 }
	 return Object.class;
	}
	@Override
	public boolean isCellEditable(int row, int col){

	    switch (col){
	    case 6: return true;
		case 8: return true;
		default: return false;
	    }
		
	}
} //MyTableModelClassForCreateOrder

class TM_CreateOrderSummary extends DefaultTableModel{//********************************************

	public TM_CreateOrderSummary (Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override
	public Class getColumnClass(int column)
	{
	 for (int row = 0; row < getRowCount(); row++)
	 {
	     Object o = getValueAt(row, column);
	
	     if (o != null)
	     {
	         return o.getClass();
	     }
	 }
	 return Object.class;
	}
	@Override
	public boolean isCellEditable(int row, int col){

	    switch (col){
	    case 6: return true;
		case 8: return true;
		default: return false;
	    }
		
	}
} //MyTableModelClassForCreateOrder


class CbCategoryItem{
	private int id;
	private String categoryName; 
	
	public CbCategoryItem(int idInput, String nameInput){
		this.id = idInput; 
		this.categoryName = nameInput;
	}
	public int getID(){	return this.id;}
	public String getCategoryName(){return this.categoryName;}
	
} //cbCategoryItem


class CbCatListRenderer extends JLabel implements ListCellRenderer {
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
    {
		if (value != null)
		{
			CbCategoryItem item = (CbCategoryItem)value;
			setText(item.getCategoryName());
		}
		return this;
	}
}//CbCatListRenderer

class CbSupItem{
	private int id; 
	private String supplierName;
	private double minOrder;
	private double unitCost;
	
	public CbSupItem(int idInput, String nameInput, double minOrderInput, double unitCostInput){
		this.id = idInput;
		this.supplierName = nameInput;		
		this.minOrder = minOrderInput;
		this.unitCost = unitCostInput;
	}
	public int getID(){	return this.id;}
	public String getSupplierName(){return this.supplierName;}
	public double getMinOrder(){return this.minOrder;}
	public double getUnitCost(){return this.unitCost;}
	
		//return string so that when item selected, supplier name is displayed in jcombo box
	public String toString(){
		return this.supplierName;
	}
}


class CbSupListRenderer extends JLabel implements ListCellRenderer<CbSupItem> {
	@Override
	public Component getListCellRendererComponent(JList<? extends CbSupItem> list, CbSupItem value, int index,
			boolean isSelected, boolean cellHasFocus) {
		CbSupItem item = (CbSupItem)value;
		if (value != null)
		{
			setText(item.getSupplierName());
		}
		return this;
	}
}//CbSubListRenderer





