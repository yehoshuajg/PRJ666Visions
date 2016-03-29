package vision;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.management.modelmbean.ModelMBean;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultStyledDocument;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import vision.Reports.DateLabelFormatter;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.Border;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import javax.swing.JMenuBar;



public class Inventory extends JPanel {

private DefaultTableModel MyModel = null;
private Connection con_Inv = null; //Shigemi

private JTextField add_prodID_tf;
private JTextField add_prodName_tf;
private JTextField add_prodDesc_tf;
//private JTextField textUnitCost;
private JTextField add_prodSalePrice_tf;
private JTextField add_prodQty_tf;
private JTextArea add_prodNotes_tf;
private JComboBox add_prodCat_cb;
private JComboBox add_prodSubCat_cb;
private JLabel add_prodID_title;
private JLabel add_prodID_lbl;
private JLabel add_prodName_lbl;
private JLabel add_prodDesc_lbl;
private JLabel add_prodQty_war;
private JLabel add_prodCat_lbl;
private JLabel add_prodSubCat_lbl;
private JLabel add_prodSalePrice_lbl;
private JLabel add_prodNotes_lbl;
private JTextField edit_prodUnitCost_tf;
static JTable table_inventoryList;

private JComboBox<CbSupItem> ord_Sup_cb = new JComboBox<CbSupItem>(); 

private TableColumnModel tcm;

private JScrollPane scp_inventoryList;
private JTabbedPane tabbedPane_Inventory;	
private static TableRowSorter<MyTableModelClass> sorter;

private static  InventoryFilterFrame filterFrame = new InventoryFilterFrame();
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
private static TableModelListener tml_newSalePrice;

private boolean newSalePriceFlag;
private JButton btnConfirm;
private static int prodDetailCount;
static ProdDetail prodDetails;


private JLabel dataProdID;
private JTextArea dataCat;
private JTextArea dataSubCat;
private JTextArea dataName;
private JTextArea dataDesc;
private JLabel dataQty;
private JLabel dataToBe;
private JLabel dataUnitCost;
private JLabel dataSalePrice;
private JTextArea dataNote;
private JTable inv_sup_prod_table;
private JTextField textField;
private JTextField dataNewSalePrice;
private JTable inv_soldByTable;
private JTable inv_priceHisTable;

private JComboBox<CbSupItem> simpleSupList;

private DefaultStyledDocument doc;
private static JTable inv_orderListTable;
private static JTable inv_orderDetailTable;
private JTextField dataDeliveryCost;
private JTextField dataInvoiceNumber;
private final JPanel panel_3 = new JPanel();
private JTextField dataSupInvoiceID;
private JTextField dataAmountDue;
private static JTable invoice_Table;
private static JTable pickOrderTable;
private JTextField dataAmountPaid;
private static JScrollPane scrollP_Panel_Invoice_Center;


public Inventory() {
		setLayout(null);

setLayout(new BorderLayout(0, 0));
JPanel panel = new JPanel();
panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
add(panel, BorderLayout.NORTH);


	//*************Sub tabbled Pane for Inventory Module
			tabbedPane_Inventory = new JTabbedPane(JTabbedPane.LEFT);
			tabbedPane_Inventory.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane_Inventory.setFont(new Font("Tahoma", Font.PLAIN, 15));
			add(tabbedPane_Inventory);
									
					
			//*************JPanle for Inventory List page 
	
				    ChangeListener loadTableAtOpen = new ChangeListener() {
				    public void stateChanged(ChangeEvent CE) {
				    	
				        try{
				        	refreshInventoryTable(1);
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
							refreshInventoryTable(1);
						}catch (Exception ex){
							ex.printStackTrace();
						}
					}  // end of ActionPerformed
				});
		
		JButton btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 filterFrame.clearFilter();
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
						//jumpToEditPage(selectedRows.get(0).intValue());
						displayDetail(selectedRows.get(0).intValue());
		
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
				System.out.println("Selected IDs are" + selectedProductIDs);
				createOrderTable(selectedProductIDs);
				//createOrderSummary(selectedProductIDs); placed inside createOrderTable();
				tabbedPane_Inventory.setEnabledAt(3,true);
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
					filterFrame.setVisible(true);
					
		}});
		panel_InventoryList_North.add(btnClearFilter);
		
		JPanel jp_blankspace02 = new JPanel();
		panel_InventoryList_North.add(jp_blankspace02);
		jp_blankspace02.setPreferredSize(new Dimension(30,20));
		
		jl_filter_status = new JLabel("No filter Applied");
		panel_InventoryList_North.add(jl_filter_status);
		
		//newSalePriceFlag = false;
		JButton btnUpdateSalePrice = new JButton("Update SalePrice"); 
		btnUpdateSalePrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//if (newSalePriceFlag == false){
					updateBulkSalePrice(1);
					newSalePriceFlag = true;
					
					boolean displayFlag = false;
					btnLoadProduct.setEnabled(displayFlag);
					btnGoToOrderPage.setEnabled(displayFlag);
					btnEditDetails.setEnabled(displayFlag);
					btnUpdateSalePrice.setEnabled(displayFlag);
					
					btnConfirm.setEnabled(true);
				//}
			}
		});
		panel_InventoryList_North.add(btnUpdateSalePrice);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.setEnabled(false);
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateBulkSalePrice(2); //step 2
				
				boolean displayFlag = true;
				btnLoadProduct.setEnabled(displayFlag);
				btnGoToOrderPage.setEnabled(displayFlag);
				btnEditDetails.setEnabled(displayFlag);
				btnUpdateSalePrice.setEnabled(displayFlag);
				
				btnConfirm.setEnabled(false);
				
			}
		});
		panel_InventoryList_North.add(btnConfirm);
		
		DocumentListener docListener = new DocumentListener(){

			@Override
			public void insertUpdate(DocumentEvent e) {
				//DocumentEvent.EventType type = e.getType();
				String priceStr = dataNewSalePrice.getText();
				if ( ! Inventory.validateDouble(priceStr) ) {
					JOptionPane.showMessageDialog(null, "New Price must be a valid number");
					priceStr = dataNewSalePrice.getText();
					//dataNewSalePrice.setText(priceStr.substring(0,priceStr.length() -1));
				}
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		};
			JPanel   panel_AddProduct = new JPanel();
			panel_AddProduct.setLayout(null);
			
			// ======================================================================================
			// Components for Add Product Page ======================================================
			// ======================================================================================
			
			tabbedPane_Inventory.addTab("Add Product", null, panel_AddProduct, null);
			
			JPanel panel_7 = new JPanel();
			panel_7.setBounds(25, 13, 785, 520);
			panel_AddProduct.add(panel_7);
			GridBagLayout gbl_panel_7 = new GridBagLayout();
			gbl_panel_7.columnWidths = new int[]{0, 200, 0, 0, 0, 0};
			gbl_panel_7.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0};
			gbl_panel_7.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_7.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panel_7.setLayout(gbl_panel_7);
			
			JLabel lblAddNewProduct = new JLabel("Add New Product");
			lblAddNewProduct.setFont(new Font("Tahoma", Font.PLAIN, 18));
			GridBagConstraints gbc_lblAddNewProduct = new GridBagConstraints();
			gbc_lblAddNewProduct.anchor = GridBagConstraints.WEST;
			gbc_lblAddNewProduct.gridwidth = 2;
			gbc_lblAddNewProduct.insets = new Insets(0, 0, 5, 5);
			gbc_lblAddNewProduct.gridx = 0;
			gbc_lblAddNewProduct.gridy = 0;
			panel_7.add(lblAddNewProduct, gbc_lblAddNewProduct);
			
			JLabel add_prodInstr01_lbl = new JLabel("Please enter new production information and press \"Add New Product\" button. ");
			GridBagConstraints gbc_add_prodInstr01_lbl = new GridBagConstraints();
			gbc_add_prodInstr01_lbl.anchor = GridBagConstraints.WEST;
			gbc_add_prodInstr01_lbl.gridwidth = 5;
			gbc_add_prodInstr01_lbl.insets = new Insets(0, 0, 5, 0);
			gbc_add_prodInstr01_lbl.gridx = 0;
			gbc_add_prodInstr01_lbl.gridy = 2;
			panel_7.add(add_prodInstr01_lbl, gbc_add_prodInstr01_lbl);
			add_prodInstr01_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel add_prodInstr02_lbl = new JLabel("Required fields are marked as ***");
			GridBagConstraints gbc_add_prodInstr02_lbl = new GridBagConstraints();
			gbc_add_prodInstr02_lbl.anchor = GridBagConstraints.WEST;
			gbc_add_prodInstr02_lbl.gridwidth = 3;
			gbc_add_prodInstr02_lbl.insets = new Insets(0, 0, 5, 5);
			gbc_add_prodInstr02_lbl.gridx = 0;
			gbc_add_prodInstr02_lbl.gridy = 3;
			panel_7.add(add_prodInstr02_lbl, gbc_add_prodInstr02_lbl);
			add_prodInstr02_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
	add_prodID_title = new JLabel("     *** ID");
	add_prodID_title.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
	gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
	gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
	gbc_lblNewLabel.gridx = 0;
	gbc_lblNewLabel.gridy = 5;
	panel_7.add(add_prodID_title, gbc_lblNewLabel);
	add_prodID_title.setHorizontalAlignment(SwingConstants.RIGHT);
	
	
	add_prodID_tf = new JTextField();
	add_prodID_tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodID_tf = new GridBagConstraints();
	gbc_add_prodID_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodID_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodID_tf.gridx = 1;
	gbc_add_prodID_tf.gridy = 5;
	panel_7.add(add_prodID_tf, gbc_add_prodID_tf);
	add_prodID_tf.setColumns(15);
	
	add_prodID_lbl = new JLabel("ID must be 8 digits number");
	add_prodID_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodID_lbl = new GridBagConstraints();
	gbc_add_prodID_lbl.anchor = GridBagConstraints.SOUTHWEST;
	gbc_add_prodID_lbl.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodID_lbl.gridx = 2;
	gbc_add_prodID_lbl.gridy = 5;
	panel_7.add(add_prodID_lbl, gbc_add_prodID_lbl);
	JLabel lblName = new JLabel("*** Name");
	lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblName = new GridBagConstraints();
	gbc_lblName.anchor = GridBagConstraints.EAST;
	gbc_lblName.insets = new Insets(0, 0, 5, 5);
	gbc_lblName.gridx = 0;
	gbc_lblName.gridy = 6;
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
	add_prodName_tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodName_tf = new GridBagConstraints();
	gbc_add_prodName_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodName_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodName_tf.gridx = 1;
	gbc_add_prodName_tf.gridy = 6;
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
	add_prodName_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodName_lbl = new GridBagConstraints();
	gbc_add_prodName_lbl.anchor = GridBagConstraints.WEST;
	gbc_add_prodName_lbl.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodName_lbl.gridx = 2;
	gbc_add_prodName_lbl.gridy = 6;
	panel_7.add(add_prodName_lbl, gbc_add_prodName_lbl);
	JLabel lblDescription = new JLabel("Description");
	lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblDescription = new GridBagConstraints();
	gbc_lblDescription.anchor = GridBagConstraints.EAST;
	gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
	gbc_lblDescription.gridx = 0;
	gbc_lblDescription.gridy = 7;
	panel_7.add(lblDescription, gbc_lblDescription);
	lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
	add_prodDesc_tf = new JTextField();
	add_prodDesc_tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodDesc_tf = new GridBagConstraints();
	gbc_add_prodDesc_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodDesc_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodDesc_tf.gridx = 1;
	gbc_add_prodDesc_tf.gridy = 7;
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
	add_prodDesc_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_dataSubCat = new GridBagConstraints();
	gbc_dataSubCat.fill = GridBagConstraints.HORIZONTAL;
	gbc_dataSubCat.insets = new Insets(0, 0, 5, 5);
	gbc_dataSubCat.gridx = 2;
	gbc_dataSubCat.gridy = 7;
	panel_7.add(add_prodDesc_lbl, gbc_dataSubCat);
	/*
	JLabel lblQuantity = new JLabel("Quantity");
	lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
	gbc_lblQuantity.anchor = GridBagConstraints.EAST;
	gbc_lblQuantity.insets = new Insets(0, 0, 5, 5);
	gbc_lblQuantity.gridx = 0;
	gbc_lblQuantity.gridy = 8;
	panel_7.add(lblQuantity, gbc_lblQuantity);
	
	add_prodQty_tf = new JTextField();
	add_prodQty_tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodQty_tf = new GridBagConstraints();
	gbc_add_prodQty_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodQty_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodQty_tf.gridx = 1;
	gbc_add_prodQty_tf.gridy = 8;
	panel_7.add(add_prodQty_tf, gbc_add_prodQty_tf);
	add_prodQty_tf.setColumns(10);
	add_prodQty_tf.getDocument().addDocumentListener(
			new DocumentListener(){
				public void changedUpdate(DocumentEvent e){
					if (add_prodQty_tf.getDocument().getLength() > 11){
						add_prodQty_war.setText("Quantity must be a number less than 11 digits");
						add_prodQty_war.setBackground(new Color(255,255,0));
						add_prodQty_war.setOpaque(true);					
					}else if( !validateStrToInt(add_prodQty_tf.getText() ) ){
						add_prodQty_war.setText("Invalid Number" );
						add_prodQty_war.setBackground(new Color(255,255,0));
						add_prodQty_war.setOpaque(true);
					}else{
						add_prodQty_war.setText("valid");
						add_prodQty_war.setOpaque(false);
					}
				}
				public void insertUpdate(DocumentEvent e){
					if (add_prodQty_tf.getDocument().getLength() > 11){
						add_prodQty_war.setText("Quantity must be a number less than 11 digits");
						add_prodQty_war.setBackground(new Color(255,255,0));
						add_prodQty_war.setOpaque(true);					
					}else if( !validateStrToInt(add_prodQty_tf.getText() ) ){
						add_prodQty_war.setText("Invalid Number" );
						add_prodQty_war.setBackground(new Color(255,255,0));
						add_prodQty_war.setOpaque(true);
					}else{
						add_prodQty_war.setText("valid");
						add_prodQty_war.setOpaque(false);
					}
				}
				public void removeUpdate(DocumentEvent e){
					if (add_prodQty_tf.getDocument().getLength() > 11){
						add_prodQty_war.setText("Quantity must be a number less than 11 digits");
						add_prodQty_war.setBackground(new Color(255,255,0));
						add_prodQty_war.setOpaque(true);					
					}else if( !validateStrToInt(add_prodQty_tf.getText() ) ){
						add_prodQty_war.setText("Invalid Number" );
						add_prodQty_war.setBackground(new Color(255,255,0));
						add_prodQty_war.setOpaque(true);
					}else{
						add_prodQty_war.setText("valid");
						add_prodQty_war.setOpaque(false);
					}
				}
			}
			);
	
	
	add_prodQty_war = new JLabel("Quantity must be a number less than 11 digits");
	add_prodQty_war.setFont(new Font("Tahoma", Font.PLAIN, 15));
	*/
	GridBagConstraints gbc_dataName = new GridBagConstraints();
	gbc_dataName.anchor = GridBagConstraints.WEST;
	gbc_dataName.insets = new Insets(0, 0, 5, 5);
	gbc_dataName.gridx = 2;
	gbc_dataName.gridy = 8;
	//panel_7.add(add_prodQty_war, gbc_dataName);
	JLabel lblCategoryid = new JLabel("*** Category");
	lblCategoryid.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblCategoryid = new GridBagConstraints();
	gbc_lblCategoryid.anchor = GridBagConstraints.EAST;
	gbc_lblCategoryid.insets = new Insets(0, 0, 5, 5);
	gbc_lblCategoryid.gridx = 0;
	gbc_lblCategoryid.gridy = 9;
	panel_7.add(lblCategoryid, gbc_lblCategoryid);
	lblCategoryid.setHorizontalAlignment(SwingConstants.RIGHT);
	
	add_prodCat_cb = new JComboBox();
	add_prodCat_cb.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodCat_cb = new GridBagConstraints();
	gbc_add_prodCat_cb.fill = GridBagConstraints.HORIZONTAL;
	gbc_add_prodCat_cb.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodCat_cb.gridx = 1;
	gbc_add_prodCat_cb.gridy = 9;
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
					
					con_Inv = Connect.connectionSetup();
					String query = "Select ID, Name From Category Where ParentID = " + newProdCategoryID + " Order By Name;";
					PreparedStatement pst = con_Inv.prepareStatement(query);
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
				
					con_Inv.close();
					pst.close();
					rs.close();
	
					}catch (Exception e1){
						e1.printStackTrace();
					} //catch
				} //Action performed		
			}//Action Listner
			);
	
	add_prodCat_lbl = new JLabel("");
	GridBagConstraints gbc_dataProdID = new GridBagConstraints();
	gbc_dataProdID.anchor = GridBagConstraints.WEST;
	gbc_dataProdID.insets = new Insets(0, 0, 5, 5);
	gbc_dataProdID.gridx = 2;
	gbc_dataProdID.gridy = 9;
	panel_7.add(add_prodCat_lbl, gbc_dataProdID);
	
	JLabel lblSubcategoryid = new JLabel("Sub Category");
	lblSubcategoryid.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblSubcategoryid = new GridBagConstraints();
	gbc_lblSubcategoryid.anchor = GridBagConstraints.EAST;
	gbc_lblSubcategoryid.insets = new Insets(0, 0, 5, 5);
	gbc_lblSubcategoryid.gridx = 0;
	gbc_lblSubcategoryid.gridy = 10;
	panel_7.add(lblSubcategoryid, gbc_lblSubcategoryid);
	lblSubcategoryid.setHorizontalAlignment(SwingConstants.RIGHT);
	
	add_prodSubCat_cb = new JComboBox();
	add_prodSubCat_cb.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodSubCat_cb = new GridBagConstraints();
	gbc_add_prodSubCat_cb.fill = GridBagConstraints.HORIZONTAL;
	gbc_add_prodSubCat_cb.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodSubCat_cb.gridx = 1;
	gbc_add_prodSubCat_cb.gridy = 10;
	panel_7.add(add_prodSubCat_cb, gbc_add_prodSubCat_cb);
	add_prodSubCat_cb.setRenderer(new CbCatListRenderer());
	
	add_prodSubCat_lbl = new JLabel("            ");
	GridBagConstraints gbc_dataDesc = new GridBagConstraints();
	gbc_dataDesc.anchor = GridBagConstraints.WEST;
	gbc_dataDesc.insets = new Insets(0, 0, 5, 5);
	gbc_dataDesc.gridx = 2;
	gbc_dataDesc.gridy = 10;
	panel_7.add(add_prodSubCat_lbl, gbc_dataDesc);
	//JLabel lblUnitCost = new JLabel("Unit Cost");		
	//	   lblUnitCost.setBounds(30, 219, 51, 16);		
	JLabel lblSalePrice = new JLabel("*** Sale Price");
	lblSalePrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblSalePrice = new GridBagConstraints();
	gbc_lblSalePrice.anchor = GridBagConstraints.EAST;
	gbc_lblSalePrice.insets = new Insets(0, 0, 5, 5);
	gbc_lblSalePrice.gridx = 0;
	gbc_lblSalePrice.gridy = 11;
	panel_7.add(lblSalePrice, gbc_lblSalePrice);
	lblSalePrice.setHorizontalAlignment(SwingConstants.RIGHT);
	//textUnitCost = new JTextField();			
	//textUnitCost.setBounds(150, 215, 116, 22);			
	//textUnitCost.setColumns(10);
	add_prodSalePrice_tf = new JTextField();
	add_prodSalePrice_tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_add_prodSalePrice_tf = new GridBagConstraints();
	gbc_add_prodSalePrice_tf.anchor = GridBagConstraints.WEST;
	gbc_add_prodSalePrice_tf.insets = new Insets(0, 0, 5, 5);
	gbc_add_prodSalePrice_tf.gridx = 1;
	gbc_add_prodSalePrice_tf.gridy = 11;
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
	add_prodSalePrice_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_dataCat = new GridBagConstraints();
	gbc_dataCat.anchor = GridBagConstraints.WEST;
	gbc_dataCat.insets = new Insets(0, 0, 5, 5);
	gbc_dataCat.gridx = 2;
	gbc_dataCat.gridy = 11;
	panel_7.add(add_prodSalePrice_lbl, gbc_dataCat);
	//JLabel lblSupplierId = new JLabel("Supplier ID");	
	//	   lblSupplierId.setBounds(30, 278, 115, 16);	
	JLabel lblNote = new JLabel("Notes");
	lblNote.setVerticalAlignment(SwingConstants.TOP);
	lblNote.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_lblNote = new GridBagConstraints();
	gbc_lblNote.anchor = GridBagConstraints.NORTHEAST;
	gbc_lblNote.insets = new Insets(0, 0, 5, 5);
	gbc_lblNote.gridx = 0;
	gbc_lblNote.gridy = 12;
	panel_7.add(lblNote, gbc_lblNote);
	lblNote.setHorizontalAlignment(SwingConstants.RIGHT);
	
	JScrollPane scrollPane_5 = new JScrollPane();
	GridBagConstraints gbc_scrollPane_5 = new GridBagConstraints();
	gbc_scrollPane_5.insets = new Insets(0, 0, 5, 5);
	gbc_scrollPane_5.fill = GridBagConstraints.BOTH;
	gbc_scrollPane_5.gridx = 1;
	gbc_scrollPane_5.gridy = 12;
	panel_7.add(scrollPane_5, gbc_scrollPane_5);
	scrollPane_5.setPreferredSize(new Dimension(150, 100));
	add_prodNotes_tf = new JTextArea();
	add_prodNotes_tf.setWrapStyleWord(true);
	scrollPane_5.setViewportView(add_prodNotes_tf);
	add_prodNotes_tf.setLineWrap(true);
	add_prodNotes_tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
	add_prodNotes_tf.setColumns(10);
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
	
	add_prodNotes_lbl = new JLabel("Notes must be 80 char or less");
	add_prodNotes_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
	GridBagConstraints gbc_dataQty = new GridBagConstraints();
	gbc_dataQty.anchor = GridBagConstraints.NORTHWEST;
	gbc_dataQty.insets = new Insets(0, 0, 5, 5);
	gbc_dataQty.gridx = 2;
	gbc_dataQty.gridy = 12;
	panel_7.add(add_prodNotes_lbl, gbc_dataQty);
		
			
			JButton btnAdd = new JButton("Add New Product");
			GridBagConstraints gbc_btnAdd = new GridBagConstraints();
			gbc_btnAdd.anchor = GridBagConstraints.WEST;
			gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
			gbc_btnAdd.gridx = 1;
			gbc_btnAdd.gridy = 13;
			panel_7.add(btnAdd, gbc_btnAdd);
			
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addProduct();
					}	
			});
		
		JButton btnNewButton_1 = new JButton("Clear Form");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				clearAddProductForm();
			}
		});
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 2;
		gbc_btnNewButton_1.gridy = 13;
		panel_7.add(btnNewButton_1, gbc_btnNewButton_1);
		
			JPanel panel_editDetails = new JPanel();
			
	  tabbedPane_Inventory.addTab("Product Details", null, panel_editDetails, null);
	  tabbedPane_Inventory.setEnabledAt(2, false);
	  				
	  				Border border = BorderFactory.createLineBorder(Color.gray);
	  				panel_editDetails.setLayout(new BorderLayout(0, 0));
	  				
	  				JPanel jp_prodDetails = new JPanel();
	  				panel_editDetails.add(jp_prodDetails, BorderLayout.WEST);
	  				jp_prodDetails.setLayout(null);
	  				jp_prodDetails.setPreferredSize(new Dimension(400, 600));
	  				
	  				JPanel jp_prodDetail = new JPanel();
	  				jp_prodDetail.setBounds(12, 13, 376, 509);
	  				jp_prodDetails.add(jp_prodDetail);
	  				GridBagLayout gbl_jp_prodDetail = new GridBagLayout();
	  				gbl_jp_prodDetail.columnWidths = new int[]{0, 15, 142, 0};
	  				gbl_jp_prodDetail.rowHeights = new int[]{0, 30, 23, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0, 0, 80, 0, 0, 0};
	  				gbl_jp_prodDetail.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
	  				gbl_jp_prodDetail.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	  				jp_prodDetail.setLayout(gbl_jp_prodDetail);
	  				
	  				dataProdID = new JLabel("0");
	  				dataProdID.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  				GridBagConstraints gbc_dataProdID1 = new GridBagConstraints();
	  				gbc_dataProdID1.anchor = GridBagConstraints.WEST;
	  				gbc_dataProdID1.insets = new Insets(0, 0, 5, 0);
	  				gbc_dataProdID1.gridx = 2;
	  				gbc_dataProdID1.gridy = 2;
	  				jp_prodDetail.add(dataProdID, gbc_dataProdID1);
	  				
	  				dataCat = new JTextArea((String) null);
	  				dataCat.setBackground(SystemColor.control);
	  				dataCat.setLineWrap(true);
	  				dataCat.setEditable(false);
	  				dataCat.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  				GridBagConstraints gbc_dataCat1 = new GridBagConstraints();
	  				gbc_dataCat1.fill = GridBagConstraints.BOTH;
	  				gbc_dataCat1.anchor = GridBagConstraints.WEST;
	  				gbc_dataCat1.insets = new Insets(0, 0, 5, 0);
	  				gbc_dataCat1.gridx = 2;
	  				gbc_dataCat1.gridy = 3;
	  				jp_prodDetail.add(dataCat, gbc_dataCat1);
	  				
	  				dataSubCat = new JTextArea((String) null);
	  				dataSubCat.setBackground(SystemColor.control);
	  				dataSubCat.setEditable(false);
	  				dataSubCat.setLineWrap(true);
	  				dataSubCat.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  				GridBagConstraints gbc_dataSubCat1 = new GridBagConstraints();
	  				gbc_dataSubCat1.fill = GridBagConstraints.BOTH;
	  				gbc_dataSubCat1.anchor = GridBagConstraints.NORTHWEST;
	  				gbc_dataSubCat1.insets = new Insets(0, 0, 5, 0);
	  				gbc_dataSubCat1.gridx = 2;
	  				gbc_dataSubCat1.gridy = 4;
	  				jp_prodDetail.add(dataSubCat, gbc_dataSubCat1);
	  				
	  				dataName = new JTextArea((String) null);
	  				dataName.setEditable(false);
	  				dataName.setLineWrap(true);
	  				dataName.setBackground(SystemColor.control);
	  				dataName.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  				GridBagConstraints gbc_dataName1 = new GridBagConstraints();
	  				gbc_dataName1.fill = GridBagConstraints.BOTH;
	  				gbc_dataName1.gridheight = 2;
	  				gbc_dataName1.anchor = GridBagConstraints.NORTHWEST;
	  				gbc_dataName1.insets = new Insets(0, 0, 5, 0);
	  				gbc_dataName1.gridx = 2;
	  				gbc_dataName1.gridy = 5;
	  				jp_prodDetail.add(dataName, gbc_dataName1);
	  				
	  				dataDesc = new JTextArea((String) null);
	  				dataDesc.setBackground(SystemColor.control);
	  				dataDesc.setLineWrap(true);
	  				dataDesc.setEditable(false);
	  				dataDesc.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  				GridBagConstraints gbc_dataDesc1 = new GridBagConstraints();
	  				gbc_dataDesc1.fill = GridBagConstraints.BOTH;
	  				gbc_dataDesc1.anchor = GridBagConstraints.WEST;
	  				gbc_dataDesc1.insets = new Insets(0, 0, 5, 0);
	  				gbc_dataDesc1.gridx = 2;
	  				gbc_dataDesc1.gridy = 7;
	  				jp_prodDetail.add(dataDesc, gbc_dataDesc1);
	  				
	  					JLabel label_13 = new JLabel("Description :");
	  					label_13.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  					GridBagConstraints gbc_label_13 = new GridBagConstraints();
	  					gbc_label_13.anchor = GridBagConstraints.NORTHEAST;
	  					gbc_label_13.insets = new Insets(0, 0, 5, 5);
	  					gbc_label_13.gridx = 0;
	  					gbc_label_13.gridy = 7;
	  					jp_prodDetail.add(label_13, gbc_label_13);
	  					
	  					dataQty = new JLabel("0");
	  					dataQty.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  					GridBagConstraints gbc_dataQty1 = new GridBagConstraints();
	  					gbc_dataQty1.anchor = GridBagConstraints.WEST;
	  					gbc_dataQty1.insets = new Insets(0, 0, 5, 0);
	  					gbc_dataQty1.gridx = 2;
	  					gbc_dataQty1.gridy = 8;
	  					jp_prodDetail.add(dataQty, gbc_dataQty1);
	  					
	  					dataToBe = new JLabel("<dynamic>");
	  					dataToBe.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  					GridBagConstraints gbc_dataToBe = new GridBagConstraints();
	  					gbc_dataToBe.anchor = GridBagConstraints.WEST;
	  					gbc_dataToBe.insets = new Insets(0, 0, 5, 0);
	  					gbc_dataToBe.gridx = 2;
	  					gbc_dataToBe.gridy = 9;
	  					jp_prodDetail.add(dataToBe, gbc_dataToBe);
	  					
	  					dataSalePrice = new JLabel("0.0");
	  					dataSalePrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  					GridBagConstraints gbc_dataSalePrice = new GridBagConstraints();
	  					gbc_dataSalePrice.anchor = GridBagConstraints.WEST;
	  					gbc_dataSalePrice.insets = new Insets(0, 0, 5, 0);
	  					gbc_dataSalePrice.gridx = 2;
	  					gbc_dataSalePrice.gridy = 10;
	  					jp_prodDetail.add(dataSalePrice, gbc_dataSalePrice);
	  					
	  					// ================ labels =================
	  					JLabel lblProductDetails = new JLabel("Product Details");
	  					lblProductDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
	  					GridBagConstraints gbc_lblProductDetails = new GridBagConstraints();
	  					gbc_lblProductDetails.insets = new Insets(0, 0, 5, 5);
	  					gbc_lblProductDetails.gridx = 0;
	  					gbc_lblProductDetails.gridy = 0;
	  					jp_prodDetail.add(lblProductDetails, gbc_lblProductDetails);
	  					
	  					JLabel label_9 = new JLabel("Product ID :");
	  					label_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  					GridBagConstraints gbc_label_9 = new GridBagConstraints();
	  					gbc_label_9.anchor = GridBagConstraints.EAST;
	  					gbc_label_9.insets = new Insets(0, 0, 5, 5);
	  					gbc_label_9.gridx = 0;
	  					gbc_label_9.gridy = 2;
	  					jp_prodDetail.add(label_9, gbc_label_9);
	  					
	  					JLabel label_10 = new JLabel("Category :");
	  					label_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  					GridBagConstraints gbc_label_10 = new GridBagConstraints();
	  					gbc_label_10.anchor = GridBagConstraints.EAST;
	  					gbc_label_10.insets = new Insets(0, 0, 5, 5);
	  					gbc_label_10.gridx = 0;
	  					gbc_label_10.gridy = 3;
	  					jp_prodDetail.add(label_10, gbc_label_10);
	  					
	  						JLabel label_11 = new JLabel("Sub Category :");
	  						label_11.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  						GridBagConstraints gbc_label_11 = new GridBagConstraints();
	  						gbc_label_11.anchor = GridBagConstraints.EAST;
	  						gbc_label_11.insets = new Insets(0, 0, 5, 5);
	  						gbc_label_11.gridx = 0;
	  						gbc_label_11.gridy = 4;
	  						jp_prodDetail.add(label_11, gbc_label_11);
	  						
	  						JLabel label_12 = new JLabel("Name :");
	  						label_12.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  						GridBagConstraints gbc_label_12 = new GridBagConstraints();
	  						gbc_label_12.anchor = GridBagConstraints.EAST;
	  						gbc_label_12.insets = new Insets(0, 0, 5, 5);
	  						gbc_label_12.gridx = 0;
	  						gbc_label_12.gridy = 5;
	  						jp_prodDetail.add(label_12, gbc_label_12);
	  						
	  						JLabel label_14 = new JLabel("Quantity on Hand :");
	  						label_14.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  						GridBagConstraints gbc_label_14 = new GridBagConstraints();
	  						gbc_label_14.anchor = GridBagConstraints.EAST;
	  						gbc_label_14.insets = new Insets(0, 0, 5, 5);
	  						gbc_label_14.gridx = 0;
	  						gbc_label_14.gridy = 8;
	  						jp_prodDetail.add(label_14, gbc_label_14);
	  						
	  						JLabel label_15 = new JLabel("To be delivered :");
	  						label_15.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  						GridBagConstraints gbc_label_15 = new GridBagConstraints();
	  						gbc_label_15.anchor = GridBagConstraints.EAST;
	  						gbc_label_15.insets = new Insets(0, 0, 5, 5);
	  						gbc_label_15.gridx = 0;
	  						gbc_label_15.gridy = 9;
	  						jp_prodDetail.add(label_15, gbc_label_15);
	  						
	  							JLabel label_16 = new JLabel("Sale Price :");
	  							label_16.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  							GridBagConstraints gbc_label_16 = new GridBagConstraints();
	  							gbc_label_16.anchor = GridBagConstraints.EAST;
	  							gbc_label_16.insets = new Insets(0, 0, 5, 5);
	  							gbc_label_16.gridx = 0;
	  							gbc_label_16.gridy = 10;
	  							jp_prodDetail.add(label_16, gbc_label_16);
	  							

	  							
	  							JLabel label_18 = new JLabel("Unit Cost :");
	  							label_18.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  							GridBagConstraints gbc_label_18 = new GridBagConstraints();
	  							gbc_label_18.anchor = GridBagConstraints.EAST;
	  							gbc_label_18.insets = new Insets(0, 0, 5, 5);
	  							gbc_label_18.gridx = 0;
	  							gbc_label_18.gridy = 11;
	  							jp_prodDetail.add(label_18, gbc_label_18);
	  							
	  							dataUnitCost = new JLabel("0.0");
	  							dataUnitCost.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  							GridBagConstraints gbc_dataUnitCost = new GridBagConstraints();
	  							gbc_dataUnitCost.insets = new Insets(0, 0, 5, 0);
	  							gbc_dataUnitCost.anchor = GridBagConstraints.WEST;
	  							gbc_dataUnitCost.gridx = 2;
	  							gbc_dataUnitCost.gridy = 11;
	  							jp_prodDetail.add(dataUnitCost, gbc_dataUnitCost);
	  							
	  							JButton btnUpdate = new JButton("Update Sale Price");
	  							GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
	  							gbc_btnUpdate.insets = new Insets(0, 0, 5, 5);
	  							gbc_btnUpdate.anchor = GridBagConstraints.EAST;
	  							gbc_btnUpdate.gridx = 0;
	  							gbc_btnUpdate.gridy = 12;
	  							jp_prodDetail.add(btnUpdate, gbc_btnUpdate);
	  							btnUpdate.addActionListener(new ActionListener() {
	  								public void actionPerformed(ActionEvent e) {
	  									updateIndividualSalePrice();
	  									
	  								}
	  							});
	  							
	  							dataNewSalePrice = new JTextField();
	  							GridBagConstraints gbc_dataNewSalePrice = new GridBagConstraints();
	  							gbc_dataNewSalePrice.fill = GridBagConstraints.VERTICAL;
	  							gbc_dataNewSalePrice.anchor = GridBagConstraints.WEST;
	  							gbc_dataNewSalePrice.insets = new Insets(0, 0, 5, 0);
	  							gbc_dataNewSalePrice.gridx = 2;
	  							gbc_dataNewSalePrice.gridy = 12;
	  							jp_prodDetail.add(dataNewSalePrice, gbc_dataNewSalePrice);
	  							dataNewSalePrice.setColumns(15);
	  							dataNewSalePrice.getDocument().addDocumentListener(docListener);
	  							
	  							JButton btnUpdateNote = new JButton("Update Note");
	  							GridBagConstraints gbc_btnUpdateNote = new GridBagConstraints();
	  							gbc_btnUpdateNote.insets = new Insets(0, 0, 5, 5);
	  							gbc_btnUpdateNote.anchor = GridBagConstraints.NORTHEAST;
	  							gbc_btnUpdateNote.gridx = 0;
	  							gbc_btnUpdateNote.gridy = 14;
	  							jp_prodDetail.add(btnUpdateNote, gbc_btnUpdateNote);
	  							btnUpdateNote.addActionListener(new ActionListener() {
	  								public void actionPerformed(ActionEvent e) {
	  									
	  									updateNote();
	  								}
	  							});
	  							
	  							JScrollPane scrollPane_4 = new JScrollPane();
	  							GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
	  							gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
	  							gbc_scrollPane_4.insets = new Insets(0, 0, 5, 0);
	  							gbc_scrollPane_4.gridx = 2;
	  							gbc_scrollPane_4.gridy = 14;
	  							jp_prodDetail.add(scrollPane_4, gbc_scrollPane_4);
	  							
	  							dataNote = new JTextArea();
	  							scrollPane_4.setViewportView(dataNote);
	  							dataNote.setText((String) null);
	  							dataNote.setLineWrap(true);
	  							dataNote.setBorder(border);
	  							
	  							JButton btnBackToInventory = new JButton("Back to Inventory");
	  							btnBackToInventory.addActionListener(new ActionListener() {
	  								public void actionPerformed(ActionEvent e) {

	  											tabbedPane_Inventory.setSelectedIndex(0);

	  								}
	  							});
	  							btnBackToInventory.setHorizontalAlignment(SwingConstants.RIGHT);
	  							GridBagConstraints gbc_btnBackToInventory = new GridBagConstraints();
	  							gbc_btnBackToInventory.fill = GridBagConstraints.BOTH;
	  							gbc_btnBackToInventory.anchor = GridBagConstraints.EAST;
	  							gbc_btnBackToInventory.insets = new Insets(0, 0, 0, 5);
	  							gbc_btnBackToInventory.gridx = 0;
	  							gbc_btnBackToInventory.gridy = 16;
	  							jp_prodDetail.add(btnBackToInventory, gbc_btnBackToInventory);
	  								  				
	  								  				JPanel jp_editDetailCenter = new JPanel();
	  								  				panel_editDetails.add(jp_editDetailCenter, BorderLayout.CENTER);
	  								  				jp_editDetailCenter.setLayout(new BorderLayout(0, 0));
	  								  				
	  								  				JPanel jp_priceHistory = new JPanel();
	  								  				jp_editDetailCenter.add(jp_priceHistory, BorderLayout.CENTER);
	  								  				jp_priceHistory.setLayout(new BorderLayout(5, 5));
	  								  				
	  								  				JPanel panel_10 = new JPanel();
	  								  				FlowLayout flowLayout_6 = (FlowLayout) panel_10.getLayout();
	  								  				flowLayout_6.setAlignment(FlowLayout.LEFT);
	  								  				jp_priceHistory.add(panel_10, BorderLayout.NORTH);
	  								  				
	  								  				JLabel lblPriceChangeHistory = new JLabel("Price Change History :");
	  								  				panel_10.add(lblPriceChangeHistory);
	  								  				lblPriceChangeHistory.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  								  				
	  								  				JScrollPane scrollPane_1 = new JScrollPane();
	  								  				jp_priceHistory.add(scrollPane_1, BorderLayout.CENTER);
	  								  				
	  								  				inv_priceHisTable = new JTable();
	  								  				scrollPane_1.setViewportView(inv_priceHisTable);
	  								  				
	  								  				JPanel jp_supAssociation = new JPanel();
	  								  				jp_editDetailCenter.add(jp_supAssociation, BorderLayout.NORTH);
	  								  				jp_supAssociation.setLayout(new BorderLayout(5, 5));
	  								  				jp_supAssociation.setPreferredSize(new Dimension(200, 200));
	  								  				
	  								  				JPanel panel_8 = new JPanel();
	  								  				FlowLayout flowLayout_5 = (FlowLayout) panel_8.getLayout();
	  								  				flowLayout_5.setAlignment(FlowLayout.LEFT);
	  								  				jp_supAssociation.add(panel_8, BorderLayout.NORTH);
	  								  				
	  								  				JLabel lblThisItemIs = new JLabel("Suppliers who sell this item :");
	  								  				panel_8.add(lblThisItemIs);
	  								  				lblThisItemIs.setFont(new Font("Tahoma", Font.PLAIN, 15));
	  								  				
	  								  				JPanel panel_9 = new JPanel();
	  								  				FlowLayout flowLayout_4 = (FlowLayout) panel_9.getLayout();
	  								  				flowLayout_4.setAlignment(FlowLayout.LEFT);
	  								  				jp_supAssociation.add(panel_9, BorderLayout.SOUTH);
	  								  				
	  								  				simpleSupList = new JComboBox<CbSupItem>();
	  								  				panel_9.add(simpleSupList);
	  								  				simpleSupList.setPreferredSize(new Dimension(200, 25));
	  								  				
	  								  				JButton btn_associateSup = new JButton("Associate Supplier");
	  								  				panel_9.add(btn_associateSup);
	  								  				
	  								  				JScrollPane scrollPane = new JScrollPane();
	  								  				jp_supAssociation.add(scrollPane, BorderLayout.CENTER);
	  								  				
	  								  				inv_soldByTable = new JTable();
	  								  				scrollPane.setViewportView(inv_soldByTable);
	  								  				btn_associateSup.addActionListener(new ActionListener() {
	  								  					public void actionPerformed(ActionEvent e) {
	  								  						
	  								  					associateSupplier();
	  								  					
	  								  					}
	  								  				});
	  				
		

		
		

		
		///der page =======================================================
		// ========================================================================================
		
		JPanel panel_createOrder = new JPanel();
		tabbedPane_Inventory.addTab("Create Order", null, panel_createOrder, null);
		tabbedPane_Inventory.setEnabledAt(3, false);
		
		panel_createOrder.setLayout(new BorderLayout(0, 0));
		
		
		JPanel jp_createOrder_north = new JPanel();
		panel_createOrder.add(jp_createOrder_north, BorderLayout.NORTH);
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
		
		JPanel jp_createOrder_center = new JPanel();
		panel_createOrder.add(jp_createOrder_center, BorderLayout.CENTER);
		jp_createOrder_center.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		jp_createOrder_center.add(panel_12, BorderLayout.EAST);
		jp_createOrder_center.add(scrollPane_ceateOrder);
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 70));
		scrollPane_ceateOrder.setColumnHeaderView(panel_5);
		jp_createOrder_center.setMinimumSize(new Dimension(100, 700));
		
		
		JPanel jp_createOrder_east = new JPanel();
		panel_createOrder.add(jp_createOrder_east, BorderLayout.EAST);
		jp_createOrder_east.setLayout(new BorderLayout(0, 0));
		jp_createOrder_east.setPreferredSize(new Dimension(300, 100));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		jp_createOrder_east.add(panel_1, BorderLayout.NORTH);
		panel_1.setPreferredSize(new Dimension(100, 70));
		
		JButton btn_createOrder = new JButton("Create Order");
		btn_createOrder.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(btn_createOrder);
		btn_createOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			//stop editing otherwise null may be assigend to order quanitity
				if (table_createOrder != null ){
								if(table_createOrder.isEditing()){
					table_createOrder.getCellEditor().stopCellEditing();
								}
					//call displayOrderSheet() function
					createOrderAndOrderDetail();
				}
				
			} //end of action performed
		});
		
		JLabel lblNewLabel = new JLabel("Orders less than minimum order may be rejected");
		panel_1.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		jp_createOrder_east.add(scrollPane_createOrderSummary);
		scrollPane_createOrderSummary.setViewportView(table_createOrderSummary);
		
		scrollPane_createOrderSummary.setPreferredSize(new Dimension(200,200));
		
		JPanel panel_orderList = new JPanel();
		tabbedPane_Inventory.addTab("OrderList", null, panel_orderList, null);
		panel_orderList.setLayout(new BorderLayout(0, 0));
		
		JPanel jpOrderListNorth = new JPanel();
		FlowLayout flowLayout = (FlowLayout) jpOrderListNorth.getLayout();
		panel_orderList.add(jpOrderListNorth, BorderLayout.NORTH);
		jpOrderListNorth.setPreferredSize(new Dimension(50, 20));
	
		
		JPanel jpOrderListWest = new JPanel();
		panel_orderList.add(jpOrderListWest, BorderLayout.WEST);
		jpOrderListWest.setLayout(new BorderLayout(5, 5));
		
		JPanel panel_6 = new JPanel();
		jpOrderListWest.add(panel_6, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		jpOrderListWest.add(panel_2, BorderLayout.EAST);
		
		JLabel lblOrderList = new JLabel("Order List");
		FlowLayout flowLayout_2 = (FlowLayout) panel_3.getLayout();
		flowLayout_2.setHgap(10);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		jpOrderListWest.add(panel_3, BorderLayout.NORTH);
		panel_3.add(lblOrderList);
		lblOrderList.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNewLabel_2 = new JLabel("( double click an order to display details)");
		panel_3.add(lblNewLabel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		jpOrderListWest.add(scrollPane_2, BorderLayout.CENTER);
		
		inv_orderListTable = new JTable();
		scrollPane_2.setViewportView(inv_orderListTable);
		
		JPanel jpOrderListCenter = new JPanel();
		panel_orderList.add(jpOrderListCenter, BorderLayout.CENTER);
		jpOrderListCenter.setLayout(new BorderLayout(5, 5));
		
		JPanel panel_4 = new JPanel();
		jpOrderListCenter.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JPanel panel_11 = new JPanel();
		panel_11.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_4.add(panel_11);
		panel_11.setPreferredSize(new Dimension(320, 110));
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[]{20, 84, 100, 20, 10, 20, 30, 0, 0, 0};
		gbl_panel_11.rowHeights = new int[]{10, 0, 25, 20, 0};
		gbl_panel_11.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_11.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_11.setLayout(gbl_panel_11);
		
		JLabel lblInvoiceNumber = new JLabel("Invoice #");
		GridBagConstraints gbc_lblInvoiceNumber = new GridBagConstraints();
		gbc_lblInvoiceNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblInvoiceNumber.gridx = 3;
		gbc_lblInvoiceNumber.gridy = 1;
		panel_11.add(lblInvoiceNumber, gbc_lblInvoiceNumber);
		lblInvoiceNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvoiceNumber.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblNewLabel_1 = new JLabel("Other Cost");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 1;
		panel_11.add(lblNewLabel_1, gbc_lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		dataInvoiceNumber = new JTextField();
		GridBagConstraints gbc_dataInvoiceNumber = new GridBagConstraints();
		gbc_dataInvoiceNumber.fill = GridBagConstraints.BOTH;
		gbc_dataInvoiceNumber.insets = new Insets(0, 0, 5, 5);
		gbc_dataInvoiceNumber.gridx = 3;
		gbc_dataInvoiceNumber.gridy = 2;
		panel_11.add(dataInvoiceNumber, gbc_dataInvoiceNumber);
		dataInvoiceNumber.setColumns(7);
		
		dataDeliveryCost = new JTextField();
		GridBagConstraints gbc_dataDeliveryCost = new GridBagConstraints();
		gbc_dataDeliveryCost.fill = GridBagConstraints.BOTH;
		gbc_dataDeliveryCost.insets = new Insets(0, 0, 5, 5);
		gbc_dataDeliveryCost.gridx = 5;
		gbc_dataDeliveryCost.gridy = 2;
		panel_11.add(dataDeliveryCost, gbc_dataDeliveryCost);
		dataDeliveryCost.setColumns(7);
		dataDeliveryCost.setPreferredSize(new Dimension(40, 25));
		
		JLabel lblOrderDetails = new JLabel("Order Details");
		GridBagConstraints gbc_lblOrderDetails = new GridBagConstraints();
		gbc_lblOrderDetails.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblOrderDetails.insets = new Insets(0, 0, 0, 5);
		gbc_lblOrderDetails.gridx = 1;
		gbc_lblOrderDetails.gridy = 3;
		panel_11.add(lblOrderDetails, gbc_lblOrderDetails);
		lblOrderDetails.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblOrderDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnUpdate_2 = new JButton("Update #");
		GridBagConstraints gbc_btnUpdate_2 = new GridBagConstraints();
		gbc_btnUpdate_2.fill = GridBagConstraints.BOTH;
		gbc_btnUpdate_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate_2.gridx = 3;
		gbc_btnUpdate_2.gridy = 3;
		panel_11.add(btnUpdate_2, gbc_btnUpdate_2);
		btnUpdate_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnNewButton = new JButton("Distribute");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 3;
		panel_11.add(btnNewButton, gbc_btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JButton btnUpdate_1 = new JButton("Update Order");
		btnUpdate_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnUpdate_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnUpdate_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		btnUpdate_1.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnUpdate_1 = new GridBagConstraints();
		gbc_btnUpdate_1.fill = GridBagConstraints.BOTH;
		gbc_btnUpdate_1.anchor = GridBagConstraints.SOUTH;
		gbc_btnUpdate_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate_1.gridx = 7;
		gbc_btnUpdate_1.gridy = 3;
		panel_11.add(btnUpdate_1, gbc_btnUpdate_1);
		btnUpdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateOrderDetail();
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				distributeDeliveryCost();
			}
		});
		btnUpdate_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				updateInvoiceID();
			}
		});
		
		JScrollPane scrollPane_3 = new JScrollPane();
		jpOrderListCenter.add(scrollPane_3, BorderLayout.CENTER);
		
		inv_orderDetailTable = new JTable();
		scrollPane_3.setViewportView(inv_orderDetailTable);
		
		JPanel panel_Invoice = new JPanel();
		tabbedPane_Inventory.addTab("Invoice", null, panel_Invoice, null);
		panel_Invoice.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_Invoice_North = new JPanel();
		FlowLayout fl_panel_Invoice_North = (FlowLayout) panel_Invoice_North.getLayout();
		fl_panel_Invoice_North.setAlignment(FlowLayout.LEFT);
		panel_Invoice.add(panel_Invoice_North, BorderLayout.NORTH);
		
		
		
		JButton btn_add_invoice = new JButton("Add Invoice");
		panel_Invoice_North.add(btn_add_invoice);
		
		JButton btn_invoice_help = new JButton("Help");
		btn_invoice_help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "To add new invoice, click \"Add Invoice\"\nTo update existing invoice, double click a invoice in the list");
			}
		});
		panel_Invoice_North.add(btn_invoice_help);
		
		scrollP_Panel_Invoice_Center = new JScrollPane();
		panel_Invoice.add(scrollP_Panel_Invoice_Center, BorderLayout.CENTER);
		
		//invoice_Table = new JTable();
		//scrollP_Panel_Invoice_Center.setViewportView(invoice_Table);
		btn_add_invoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                new DialogAddNewInvoice(null).setVisible(true);
		            }
		        });

			}
		});
		
		JPanel TestingPanel = new JPanel();
		tabbedPane_Inventory.addTab("Testing", null, TestingPanel, null);
		TestingPanel.setLayout(new BorderLayout(0, 0));
		
		
		JPanel jp_invoiceCreateDialog = new JPanel();
		jp_invoiceCreateDialog.setBounds(382, 332, 326, 275);
		
		TestingPanel.add(jp_invoiceCreateDialog);
		jp_invoiceCreateDialog.setLayout(null);
		
		JPanel panel_14 = new JPanel();
		jp_invoiceCreateDialog.add(panel_14);
		panel_14.setBounds(55, 78, 358, 389);
		GridBagLayout gbl_panel_14 = new GridBagLayout();
		gbl_panel_14.columnWidths = new int[]{150, 50, 50, 50, 0, 0};
		gbl_panel_14.rowHeights = new int[]{0, 20, 25, 0, 0, 0, 150, 100, 0, 0};
		gbl_panel_14.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_14.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		panel_14.setLayout(gbl_panel_14);
		
		JLabel lblNewInvoice = new JLabel("Add New Invoice");
		GridBagConstraints gbc_lblNewInvoice = new GridBagConstraints();
		gbc_lblNewInvoice.anchor = GridBagConstraints.EAST;
		gbc_lblNewInvoice.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewInvoice.gridx = 0;
		gbc_lblNewInvoice.gridy = 0;
		panel_14.add(lblNewInvoice, gbc_lblNewInvoice);
		lblNewInvoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblInvoiceId = new JLabel("Supplier Invoice #/ ID");
		lblInvoiceId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblInvoiceId = new GridBagConstraints();
		gbc_lblInvoiceId.anchor = GridBagConstraints.EAST;
		gbc_lblInvoiceId.insets = new Insets(0, 0, 5, 5);
		gbc_lblInvoiceId.gridx = 0;
		gbc_lblInvoiceId.gridy = 2;
		panel_14.add(lblInvoiceId, gbc_lblInvoiceId);
		
		dataSupInvoiceID = new JTextField();
		GridBagConstraints gbc_dataSupInvoiceID = new GridBagConstraints();
		gbc_dataSupInvoiceID.fill = GridBagConstraints.BOTH;
		gbc_dataSupInvoiceID.gridwidth = 3;
		gbc_dataSupInvoiceID.insets = new Insets(0, 0, 5, 5);
		gbc_dataSupInvoiceID.gridx = 1;
		gbc_dataSupInvoiceID.gridy = 2;
		panel_14.add(dataSupInvoiceID, gbc_dataSupInvoiceID);
		dataSupInvoiceID.setColumns(10);
		
		JLabel lblSupplier = new JLabel("Supplier");
		GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
		gbc_lblSupplier.anchor = GridBagConstraints.EAST;
		gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
		gbc_lblSupplier.gridx = 0;
		gbc_lblSupplier.gridy = 3;
		panel_14.add(lblSupplier, gbc_lblSupplier);
		
		JComboBox<vision.CbCategoryItem> dataSupCb = new JComboBox();
		GridBagConstraints gbc_dataSupCb = new GridBagConstraints();
		gbc_dataSupCb.fill = GridBagConstraints.BOTH;
		gbc_dataSupCb.gridwidth = 3;
		gbc_dataSupCb.insets = new Insets(0, 0, 5, 5);
		gbc_dataSupCb.gridx = 1;
		gbc_dataSupCb.gridy = 3;
		panel_14.add(dataSupCb, gbc_dataSupCb);
		
		JLabel lblAmountDue = new JLabel("Amount Due");
		GridBagConstraints gbc_lblAmountDue = new GridBagConstraints();
		gbc_lblAmountDue.anchor = GridBagConstraints.EAST;
		gbc_lblAmountDue.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmountDue.gridx = 0;
		gbc_lblAmountDue.gridy = 4;
		panel_14.add(lblAmountDue, gbc_lblAmountDue);
		
		dataAmountDue = new JTextField();
		GridBagConstraints gbc_dataAmountDue = new GridBagConstraints();
		gbc_dataAmountDue.fill = GridBagConstraints.BOTH;
		gbc_dataAmountDue.gridwidth = 2;
		gbc_dataAmountDue.insets = new Insets(0, 0, 5, 5);
		gbc_dataAmountDue.gridx = 1;
		gbc_dataAmountDue.gridy = 4;
		panel_14.add(dataAmountDue, gbc_dataAmountDue);
		dataAmountDue.setColumns(10);
		
		JLabel lblAmountPaid = new JLabel("Amount  Paid");
		GridBagConstraints gbc_lblAmountPaid = new GridBagConstraints();
		gbc_lblAmountPaid.anchor = GridBagConstraints.EAST;
		gbc_lblAmountPaid.insets = new Insets(0, 0, 5, 5);
		gbc_lblAmountPaid.gridx = 0;
		gbc_lblAmountPaid.gridy = 5;
		panel_14.add(lblAmountPaid, gbc_lblAmountPaid);
		
		dataAmountPaid = new JTextField();
		GridBagConstraints gbc_dataAmountPaid = new GridBagConstraints();
		gbc_dataAmountPaid.gridwidth = 2;
		gbc_dataAmountPaid.anchor = GridBagConstraints.NORTH;
		gbc_dataAmountPaid.insets = new Insets(0, 0, 5, 5);
		gbc_dataAmountPaid.fill = GridBagConstraints.HORIZONTAL;
		gbc_dataAmountPaid.gridx = 1;
		gbc_dataAmountPaid.gridy = 5;
		panel_14.add(dataAmountPaid, gbc_dataAmountPaid);
		dataAmountPaid.setColumns(10);
		
		
		
		JLabel lblSelectOrdersTo = new JLabel("Orders to associate");
		GridBagConstraints gbc_lblSelectOrdersTo = new GridBagConstraints();
		gbc_lblSelectOrdersTo.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSelectOrdersTo.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectOrdersTo.gridx = 0;
		gbc_lblSelectOrdersTo.gridy = 6;
		panel_14.add(lblSelectOrdersTo, gbc_lblSelectOrdersTo);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_6 = new GridBagConstraints();
		gbc_scrollPane_6.gridwidth = 3;
		gbc_scrollPane_6.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_6.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_6.gridx = 1;
		gbc_scrollPane_6.gridy = 6;
		panel_14.add(scrollPane_6, gbc_scrollPane_6);
		
		pickOrderTable = new JTable();
		scrollPane_6.setViewportView(pickOrderTable);
		
		JLabel lblMessage = new JLabel("Message");
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessage.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblMessage.gridx = 0;
		gbc_lblMessage.gridy = 7;
		panel_14.add(lblMessage, gbc_lblMessage);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(SystemColor.control);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridwidth = 3;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 7;
		panel_14.add(textArea, gbc_textArea);

		

}// end of Constructor Inventory() ====================================================================================================
//======================================================================================================================================
// Begining of inventory class functions
 
    public static boolean validateStrToInt(String s){
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
    public static boolean validateStrToDouble(String s){
        try { 
            Double.parseDouble(s); 
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
	public static boolean validateDouble(String value){
	try{
		Double.parseDouble(value);
		return true;
		} catch (NumberFormatException ne){
			return false;
		}
	} //-----------------------------------
	public boolean validateStringSize(String value, int max){
		if(value.length() > max ) return false;
		return true;
	}//------------------------------------
	public void fillComboBoxCategory(JComboBox<CbCategoryItem> cb){ //*********************************************
	try{
		con_Inv = Connect.connectionSetup();
		String query = "Select ID, Name FROM category WHERE ParentID is NULL ORDER BY Name";
		PreparedStatement pst = con_Inv.prepareStatement(query);
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
		
		con_Inv.close();
		pst.close();
		rs.close();
		
		
	}catch (Exception e){
		e.printStackTrace();
	}
	}// end of fillComboBoxCategory()-----------------------------
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
	}//--------------------------------
	public void jumpToEditPage(int ID){//*********************************************
	
		/*
		try{
		con_Inv = Connect.connectionSetup();
		String query = "Select * from Product Where ID = "+ ID;
		PreparedStatement pst = con_Inv.prepareStatement(query);
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
				con_Inv.close();
				tabbedPane_Inventory.setSelectedIndex(2); //open Edit panel
			
			}
		}catch (Exception ex){  ex.printStackTrace(); 	}
	*/
	} //---------------------------------
	
	
	public void updateBulkSalePrice(int step) {
		
		if (step == 1){
			//first step is to update table to include new sale price column 
			refreshInventoryTable(2);

		} else if (step == 2){
			stopEditing(table_inventoryList);
			//if(table_inventoryList.isEditing()){
				//table_inventoryList.getCellEditor().stopCellEditing();
			
			//next step is to update database based on the user input
			
		    int response = JOptionPane.showConfirmDialog(null, "Do you want to update sale price?", "Confirm Update",
		            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		        if (response == JOptionPane.NO_OPTION) {
		          
		        } else if (response == JOptionPane.CLOSED_OPTION) {
		          
		        } else if (response == JOptionPane.YES_OPTION) {
		          
		        
		    int count=0;
		    Vector<Integer> prodIDs = new Vector<Integer>();
			

				try {
					con_Inv = Connect.connectionSetup();
					String query = "";
					
					for(int i = 0; i < table_inventoryList.getRowCount(); i++){
				
						if ( table_inventoryList.getValueAt(i, 11) != null){
							
							double newSalPrice = Double.parseDouble( (String) table_inventoryList.getValueAt(i, 11) );
							double curUniPrice = (double) table_inventoryList.getValueAt(i, 9)  ;
							int prodID = (int)table_inventoryList.getValueAt(i, 1);
								
							System.out.println("new sale price" + newSalPrice + " / curUnitPrice" + curUniPrice);
								if (newSalPrice > curUniPrice){
								query = "Update Product Set SalePrice = " + newSalPrice + " WHERE ID = " + prodID ;
								PreparedStatement pst = con_Inv.prepareStatement(query);
								pst.executeUpdate();
								count++;
								prodIDs.addElement(prodID);
								}
						} // end of If new sale price is not Null 
						
						} // end of for loop	
						
						con_Inv.close();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		
			
				if(count > 0) { 
				JOptionPane.showMessageDialog(null, "Sale Price updated");
					}else {
						JOptionPane.showMessageDialog(null, "No data has been updated");
					}
			
				
				refreshInventoryTable(1);
				Iterator it = prodIDs.iterator();
				while(it.hasNext()){
					int id = (int) it.next();
					for(int i = 0; i < table_inventoryList.getRowCount() ; i++){
						if(id == (int) table_inventoryList.getValueAt(i, 1) ){
							table_inventoryList.addRowSelectionInterval(i, i);
						}
					}
				}
			
			} // end of Yes Option
						
			}// end of step 2

	}// end of function
	
	public static void filterByMultiColumn2(){//********************************************
		RowFilter<MyTableModelClass,Object> rf = null;
		List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		filters.add(RowFilter.regexFilter(".*"+filterFrame.getID().getText()+".*",1));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getCategory().getText()+".*", 2));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getSubCategory().getText()+".*", 3));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getProdName().getText()+".*", 4));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getDesc().getText()+".*", 5));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getNote().getText()+".*", 8));
		
		if (!filterFrame.getQtyGt().getText().isEmpty() ){
	    String gt = filterFrame.getQtyGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(gt), 6));
		}
		if (!filterFrame.getQtyLt().getText().isEmpty() ){
		String lt = filterFrame.getQtyLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(lt), 6));
		}
		if (!filterFrame.getSaleGt().getText().isEmpty() ){
		String gt = filterFrame.getSaleGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(gt), 8));
		}
		if (!filterFrame.getSaleLt().getText().isEmpty() ){
		String lt = filterFrame.getSaleLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(lt), 8));
		}
		
		rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);

		
		if(
				filterFrame.getID().getText().isEmpty() &&
				filterFrame.getProdName().getText().isEmpty() &&
				filterFrame.getDesc().getText().isEmpty() &&
				filterFrame.getCategory().getText().isEmpty() &&
				filterFrame.getSubCategory().getText().isEmpty() &&
				filterFrame.getNote().getText().isEmpty() &&
				filterFrame.getQtyGt().getText().isEmpty() &&
				filterFrame.getQtyLt().getText().isEmpty() &&
				filterFrame.getSaleGt().getText().isEmpty() &&
				filterFrame.getSaleLt().getText().isEmpty() 
			){
			jl_filter_status.setOpaque(false);
			jl_filter_status.setText("Filter Not Applied");
			
		}else {
			jl_filter_status.setText("Filter Applied");
			jl_filter_status.setOpaque(true);
			jl_filter_status.setBackground(new Color(255, 0, 0));
		}
		
	}
	
	public void initiateInventory(){
		refreshInventoryTable(1);
		updateInv_orderListTable();
		createInvoiceTable();
	}
	
	public static void createInvoiceTable(){ //===============================================
		
		Connection con; 
		try {
			con = Connect.connectionSetup();
			
			String query = "Select i.ID, s.Name as supplier, i.ReceivedDate, i.AmountDue, i.AmountPaid, i.AmountDue - i.AmountPaid, GROUP_CONCAT(o.id SEPARATOR ', ')  "
					+ "FROM invoice i "
					+ "LEFT JOIN Supplier s ON i.SupplierID = s.ID "
					+ "LEFT JOIN `order` o  ON i.ID = o.invoiceID "
					+ "Group by i.ID ORDER BY i.ID DESC";
			
			 PreparedStatement pst = con.prepareStatement(query);
			 ResultSet rs = pst.executeQuery();
		 
			Vector<Object> columnNames = new Vector<Object>();
		    Vector<Object> data = new Vector<Object>();
		    
		    columnNames.addElement("Invoice ID");
		    columnNames.addElement("Supplier");
		    columnNames.addElement("Received Date");
		    columnNames.addElement("Amount Due");
		    columnNames.addElement("Amount Paid");
		    columnNames.addElement("Outstanding");
		    columnNames.addElement("Orders");
		    
		    while(rs.next()){
		    	Vector<Object> row = new Vector<Object>();
		    	for (int i = 1; i <= 7; i++){
		    		row.addElement(rs.getObject(i));
		    	}
		    	data.addElement(row);
		    }
		    
			 con.close();
			 pst.close();
		    
		    TM_basic modelForInvoice = new TM_basic(data, columnNames);
		    invoice_Table = new JTable(modelForInvoice);
		    scrollP_Panel_Invoice_Center.setViewportView(invoice_Table);
		    //invoice_Table.setModel(modelForInvoice);
		    invoice_Table.setRowHeight(30);
		    		
			Dimension tableSize = invoice_Table.getPreferredSize();
			TableColumnModel tcm = invoice_Table.getColumnModel();
		
			tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //ID
			tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); //Supplier
			tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //ReceivedDate
			tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //AmountDue
			tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //AmountPaid
			tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //Outstanding
			tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //# of orders
			
			/*				
			TableModelListener tml_invoiceTable = new TableModelListener(){
			TableModel model = invoice_Table.getModel();
			
				@Override 
				public void tableChanged(TableModelEvent e){
					if(e.getType() == TableModelEvent.UPDATE){
						
						int row = e.getFirstRow();
						int col = e.getColumn();
						//boolean validPrice = true;
						
						if (col == 11 && model.getValueAt(row, col) != null ){ //=====
							String newPrice =  (String) model.getValueAt(row, 11);
							if (!validateDouble(newPrice)){ //----
								JOptionPane.showMessageDialog(null, "new sale price must be a number");
								model.setValueAt(null, row, col);

								//validPrice = false;
							}//----
						}//=======
					}
				} // end of tableChanged(TableModelEvent e)
			}; //end of tml_
			
			invoice_Table.getModel().addTableModelListener(tml_invoiceTable);
			invoice_Table.getColumnModel().getColumn(11).setCellRenderer(new TCellRend_newSalePrice());
			*/
		
			invoice_Table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent mouseE) {
				if (mouseE.getClickCount() == 2) {
				    JTable target = (JTable)mouseE.getSource();
				    int row = target.getSelectedRow();
				    int invoiceID = (int) target.getValueAt(row, 0);
				    
			        SwingUtilities.invokeLater(new Runnable() {
			            @Override
			            public void run() {
			                new DialogEditInvoice(null,invoiceID).setVisible(true);
			            }
			        });
				    

					}
				}
			});	
			
			
		 //crate invoice table
		
		
		//add to scroll pane
		
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
}

//==============================================
	/*
public void updateInv_orderListTable(){
	
	try {
		con_Inv = Connect.connectionSetup();

	String query = "Select o.ID, CreateDate, s.Name as 'Supplier Name', ReceivedDate, InvoiceID "
			+ "FROM `order` o "
			+ "Inner JOIN Supplier s ON s.ID = o.SupplierID "
			+ "ORDER BY ID DESC;";
	
	PreparedStatement pst = con_Inv.prepareStatement(query);
	ResultSet rs = pst.executeQuery();
	
Vector<Object> columnNames = new Vector<Object>();
Vector<Object> data = new Vector<Object>();

columnNames.addElement("Order ID");
columnNames.addElement("Created");
columnNames.addElement("Supplier");
columnNames.addElement("Received Date");
columnNames.addElement("Invoice ID");


while(rs.next()){
	Vector<Object> row = new Vector<Object>();
	for (int i = 1; i <= 5; i++){
		row.addElement(rs.getObject(i));
	}
	data.addElement(row);
}

TM_basic orderModel = new TM_basic(data, columnNames);
inv_orderListTable.setModel(orderModel);
inv_orderListTable.setRowHeight(30);

Dimension tableSize = inv_orderListTable.getPreferredSize();
tcm = inv_orderListTable.getColumnModel();


	tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //check
	tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //ID
	tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); //Category
	tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //Sub Category
	tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //Name
	//tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Description
	//tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Quantity
	//tcm.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //TO be delivered
	//tcm.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Sale Price
	//tcm.getColumn(9).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Unit Cost

	inv_orderListTable.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent mouseE) {
		if (mouseE.getClickCount() == 2) {
		    JTable target = (JTable)mouseE.getSource();
		    int row = target.getSelectedRow();
		    int orderID = (int) target.getValueAt(row, 0);
		    
		    updateInv_orderDetailTable(orderID);

		}
	}
});	
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
*/	
	public void addProduct(){//=======================================
		try{
			
			
			int flag = 0;
			String prodID = add_prodID_tf.getText();
			String prodName = add_prodName_tf.getText();
			String prodDesc = add_prodDesc_tf.getText();
			//String prodQty = add_prodQty_tf.getText();
			String prodSalePrice = add_prodSalePrice_tf.getText();
			CbCategoryItem catItem = (CbCategoryItem) add_prodCat_cb.getSelectedItem();
			CbCategoryItem subCatItem = (CbCategoryItem) add_prodSubCat_cb.getSelectedItem();
			
			int subCatID = 0;
			int catID = catItem.getID();
			if(add_prodSubCat_cb.getSelectedItem()!= null){subCatID = subCatItem.getID();}
			// if user does not select category, give error
			if ( catID == 0 ) flag = 2;
			//System.out.println("category ID is /" + catID);
			//sub category throws exception if user does not select category
			//System.out.println("sub category ID is /" + subCatID);
			
		    	//validate user input 
		    if ( !validateProdID(prodID) ) flag = 1;
		    if ( !validateStringSize(prodName, sizeProdName) ) flag = 1;
		    
		    if ( prodDesc.isEmpty()){
		    	prodDesc = ""; //assign "" to product description if it is empty
		    } else {  if ( !validateStringSize(prodDesc, sizeProdDesc) ) flag = 1;}
		    if ( !validateStringSize(prodDesc, sizeProdDesc) ) flag = 1;
		    /*
		    if ( prodQty.isEmpty()){
		    	prodQty = "0"; //assign 0 to product quantity if it is empty
		    } else {  if ( !validateIntSize(prodQty, sizeProdQty) ) flag = 1;}
		    */
		    if ( !validateDouble(prodSalePrice) ) flag = 1;

		    if (flag == 1){
		    	JOptionPane.showMessageDialog(null, "Invalid input - try again");
		    }
		    else if (flag == 2 ){
		    	JOptionPane.showMessageDialog(null, "Category is required");
		    }
		    else if ( flag == 0 )
		    {
				con_Inv = Connect.connectionSetup();
				//update product table
				String query = "insert into product (ID, Name, Description, CategoryID, SubCategoryID, SalePrice, Notes)"
						+ "values ( ?,?,?,?,?,?,?)";
				PreparedStatement pst = con_Inv.prepareStatement(query);		 
				pst.setInt(1, Integer.parseInt( prodID ));
				pst.setString(2, prodName);
				pst.setString(3, prodDesc);
				//pst.setInt(4, Integer.parseInt( prodQty ) );
				pst.setInt(4, catID);
				if (subCatID != 0){
					pst.setInt(5, subCatID);
				} else {
					pst.setNull(5, java.sql.Types.INTEGER);
				}
				
				pst.setDouble(6, Double.parseDouble(prodSalePrice));
				pst.setString(7, add_prodNotes_tf.getText());
									
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "New Product Added");
					
					refreshInventoryTable(1);
					clearAddProductForm();
					tabbedPane_Inventory.setSelectedIndex(0);
					
					int index = 0; 
					for (int j = 0; j < table_inventoryList.getRowCount(); j++){
						if(Integer.parseInt(prodID) == (int)table_inventoryList.getValueAt(j, 1)){	
							index = j;
						}
					}
					JViewport viewport = (JViewport)table_inventoryList.getParent();
					Rectangle  rect = table_inventoryList.getCellRect(index, 0, true);
					Point pt = viewport.getViewPosition();
					rect.setLocation(rect.x-pt.x, rect.y-pt.y);
					table_inventoryList.scrollRectToVisible(rect);
					table_inventoryList.getSelectionModel().setSelectionInterval(index, index);
				
				
				pst.close();
				con_Inv.close();
		    }// if - validate user input 
		}
		 catch (SQLException ex) {
                if (ex.getSQLState().startsWith("23")) {
                      JOptionPane.showMessageDialog(null, "Duplicated Product ID - please try again");
                } 
		 }catch (Exception e1) {
			e1.printStackTrace();
		 }
	}
	
	public void clearAddProductForm(){
		add_prodID_tf.setText("");
		add_prodName_tf.setText("");
		add_prodDesc_tf.setText("");
		//add_prodQty_tf.setText("");
		add_prodSalePrice_tf.setText("");
		add_prodNotes_tf.setText("");
		add_prodCat_cb.setSelectedIndex(0);
		add_prodSubCat_cb.removeAllItems();
	}
	//===================================================================================================
	public void associateSupplier(){
		if (simpleSupList.getSelectedItem() == null){
			JOptionPane.showMessageDialog(null, "Please select a supplier to associate");
		}else {
			CbSupItem thisItem = (CbSupItem) simpleSupList.getSelectedItem();
			
			if (thisItem.getID() == 0){
				JOptionPane.showMessageDialog(null, "Please select a supplier to associate");
			}else if (dataProdID.getText() == "0"){
				JOptionPane.showMessageDialog(null, "Please select a product from inventory list");
			}
				CbSupItem item = (CbSupItem) simpleSupList.getSelectedItem();
				int supID = item.getID();
				String prodID = dataProdID.getText();
				
				try {
					con_Inv = Connect.connectionSetup();
					String query = "Insert Into product_supplier (ProductID, SupplierID, UnitCost) values ( "+ prodID +"," + supID + " , 0)";
					PreparedStatement pst = con_Inv.prepareStatement(query);
					pst.executeUpdate();
					
					updateInv_soldByTable();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

	//===================================================================================================
	public void createOrderTable(StringBuilder mySelection){
		
		createOrderSummary(mySelection);
	
	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    Vector<Integer> selectedRows = getSelectedRows();
    try{
    	
    	con_Inv = Connect.connectionSetup();
    	String query = "Select p.ID, c.Name as Category, sc.Name as 'Sub Category', p.Name, p.Quantity, "
      			+ " SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered'	"
    			+ " FROM Product p "
    			+ " LEFT JOIN Category as c on c.ID = p.CategoryID"
    			+ "	LEFT JOIN Category as sc on sc.ID = p.SubCategoryID "
    			+ " LEFT JOIN orderdetail as od on od.ProductID = p.ID "
    			+ " WHERE p.ID IN ( " + mySelection + " )"
    			+ "	GROUP BY p.ID";
    			
		PreparedStatement pst = con_Inv.prepareStatement(query);
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
		con_Inv.close();
		
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
	    
	    columnNames.addElement("Supplier ID"); 				
			columnNames.addElement("Supplier"); 
			columnNames.addElement("Minimum Order");
			columnNames.addElement("Order Total");
			
	    TM_CreateOrderSummary myModel_createOrderSummary = new TM_CreateOrderSummary(data, columnNames);
	    
	    table_createOrderSummary = new JTable(myModel_createOrderSummary);
	    scrollPane_createOrderSummary.setViewportView(table_createOrderSummary);
	    table_createOrderSummary.setRowHeight(30);
	}
	
	
	//===========================================================================
	public void createOrderAndOrderDetail(){
		
		int flag = 0;
		int prodID_col = 0;
		int sup_col = 6;
		int qty_col = 8;
		int prodName_col = 3;
		int cat_col = 1;
		int subCat_col = 2;
		int orderID = 0;
		int orderCount = 0; 
		
			for (int i = 0; i < table_createOrder.getRowCount(); i++){
				if (table_createOrder.getValueAt(i, sup_col) == null)flag = 1;
				if (table_createOrder.getValueAt(i, qty_col) == null)flag = 1;
			}
			if (flag == 1){
				JOptionPane.showMessageDialog(null, "Supplier and/or order quantity cannot be left blank");
			}
			else if (flag == 0){ // if both supplierID and quantity to order is filled

				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "This will create new order(s). Do you want to proceed?", "New Order will be created", dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION) {
				
				double orderTotal = 0;
				Vector<Integer> orgSupIDList = new Vector<Integer>();
				//Vector<Integer> uniSupIDList = new Vector<Integer>();
				Vector<CbSupItem> supID_list = new Vector<CbSupItem>(); //supplier list that contains duplicate values
				Set<Integer> uniSupID_list = new HashSet<Integer>(); //supplier list that contains unique values
			
			//add supplier to org list
			for (int i=0; i < table_createOrder.getRowCount(); i++ ){  
				supID_list.add( (CbSupItem) table_createOrder.getValueAt(i, sup_col));
				orgSupIDList.add(  ((CbSupItem) table_createOrder.getValueAt(i, sup_col)).getID()  );
			}
			//add orgSupIDList to Set to retrive unique values
			uniSupID_list.addAll(orgSupIDList);
			
	 try {
		   Connection connForCreateOrder = Connect.connectionSetup();
				
			//loop through each unique supplier and create order and order details for the supplier
		   		int count = 0;
			for ( Iterator<Integer> it = uniSupID_list.iterator(); it.hasNext(); ){
				count++;
				int supplierID = it.next(); 
				OrderInfo order = new OrderInfo();
				Vector<Object> detailList = new Vector<Object>();
				
				//Vector<OrderDetailsInfo> ordDetailList = new Vector<OrderDetailsInfo>();
				System.out.println("1) unique sup list - supplier ID is " + supplierID); 

				//get orderTotal from table_createOrderSummary
				for (int j = 0; j < table_createOrderSummary.getModel().getRowCount() ; j++){//#10
					if( (int)table_createOrderSummary.getValueAt(j, 0) == supplierID){
						orderTotal =  (double) table_createOrderSummary.getValueAt(j, 3) ;
					}
				} //#10
				
				
				//get new order ID
				String query2 = "SELECT Max(ID) FROM `order` ";
				PreparedStatement pst = connForCreateOrder.prepareStatement(query2);
				ResultSet rs = pst.executeQuery(); 	
				while(rs.next()){
				orderID =  (int) rs.getObject(1) + 1;	// new ID will be max id + 1		
				}
				order.setOrderID(orderID);
				
				//insert new order
				String query = "Insert into `order` (SupplierID, EmployeeID, CreateDate, Cost ) "
						                 + "VALUES (" + supplierID + ", 1, NOW(), "+ orderTotal +")";		
				pst = connForCreateOrder.prepareStatement(query);
				pst.executeUpdate(); 
				orderCount++;
						//get supplier info for supplier order sheet later 
						//fill sup
						query = "Select ID, Name, Street, City, State_Province, PostalCode, PhoneNumber, Email, Status "
								+ "From supplier "
								+ "Where ID = " + supplierID;
						pst = connForCreateOrder.prepareStatement(query);
						 rs = pst.executeQuery();
						while(rs.next()){
							order.setID( rs.getInt(1) );
							order.setName( rs.getString(2) );
							order.setStreet( rs.getString(3) );
							order.setCity(rs.getString(4));
							order.setState( rs.getString(5) );
							order.setPostalCode( rs.getString(6) );
							order.setPhone( rs.getString(7) );
							order.setEmail( rs.getString(8) );
							order.setStatus( rs.getString(9) );
						}
						
						
					//insert order details
					// need to loop same times as the size of the unique list ??
					
					for (int k = 0; k < table_createOrder.getRowCount(); k++){ //for #20
						
						CbSupItem item = (CbSupItem) table_createOrder.getValueAt(k, sup_col);
						if (supplierID == item.getID()){ //if #21
							
							//OrderDetailsInfo ordDetail = new OrderDetailsInfo();
							
							int productID =  (int)table_createOrder.getValueAt(k, prodID_col);
							int orderQty =  Integer.parseInt((String) table_createOrder.getValueAt(k, qty_col)) ;
							String prodName = (String) table_createOrder.getValueAt(k, prodName_col);
							String category = (String) table_createOrder.getValueAt(k, cat_col);
							String subCategory = (String) table_createOrder.getValueAt(k, subCat_col);
							
							query = "Insert into orderdetail (OrderID, ProductID, OrderedQuantity) VALUES (" 
									+ orderID + ", " + productID + ", "	+ orderQty +")";
																						
							pst = connForCreateOrder.prepareStatement(query);
							pst.executeUpdate();
							
							Vector<Object> detailItem = new Vector<Object>();
							detailItem.addElement(productID);
							detailItem.addElement(prodName);
							detailItem.addElement(orderQty);

							detailList.addElement(detailItem);

						} //if #21
					} //for #20
				
				pst.close();
				
				
				//create a frame for each supplier
				/*
				InventoryOrderSheetFrame orderSheet = new InventoryOrderSheetFrame(order, detailList);
				orderSheet.setSize(new Dimension(650, 750));
				Dimension screenSize = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
				Dimension windowSize = orderSheet.getPreferredSize();
				
				int offset = count * 30 - 300;
				int left = offset + screenSize.width / 2 + windowSize.width / 2;
				int top = offset + screenSize.height / 2 - windowSize.height / 2;
			
				orderSheet.setLocation(left, top);
				orderSheet.setVisible(true);
				*/
				
			} // end of for loop uniSupID_list iterator it
			
			connForCreateOrder.close();
			//JOptionPane.showMessageDialog(null, "New Order record(s) have been created");
			
			scrollPane_ceateOrder.setViewportView(null);
			tabbedPane_Inventory.setEnabledAt(3, false);
			JOptionPane.showMessageDialog(null, orderCount + " order(s) created");
			refreshInventoryTable(1);
			tabbedPane_Inventory.setSelectedIndex(4); 
			updateInv_orderListTable();
			updateInv_orderDetailTable(orderID); //displays the most recent order
				inv_orderListTable.setRowSelectionInterval(0, orderCount -1);
			
			
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // end of catch 
			} // end of Yes option 

			}// end of if flag == 0
	} // -------------------------------------------------------------------
	
	public void updateInv_priceHisTable(){
		
			Connection con;
			try {
				con = Connect.connectionSetup();
				
				String prodIDStr = dataProdID.getText();
				
				String query = "Select ProductID, NewPrice, EffectiveDate "
						+ "From pricehistory WHERE productID =" + prodIDStr + " Order by EffectiveDate Desc";
			 PreparedStatement pst = con.prepareStatement(query);
			 ResultSet rs = pst.executeQuery();
			 
				Vector<Object> columnNames = new Vector<Object>();
			    Vector<Object> data = new Vector<Object>();
			    
			    columnNames.addElement("Prodct ID");
			    columnNames.addElement("Price");
			    columnNames.addElement("Set Date");
			    
			    while(rs.next()){
			    	Vector<Object> row = new Vector<Object>();
			    	for (int i = 1; i <= 3; i++){
			    		
			    		row.addElement(rs.getObject(i));
			    		
			    	}
			    	data.addElement(row);
			    }
			    
			    TM_basic basicModelforPriceHistory = new TM_basic(data, columnNames);
			    inv_priceHisTable.setModel(basicModelforPriceHistory);
			    inv_priceHisTable.setRowHeight(30);
			 
			 con.close();
			 pst.close();
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	
	//==============================================
	public static void updateInv_orderListTable(){
		
		try {
			Connection con_Inv = Connect.connectionSetup();

		String query = "Select o.ID, CreateDate, s.Name as 'Supplier Name', ReceivedDate, InvoiceID "
				+ "FROM `order` o "
				+ "Inner JOIN Supplier s ON s.ID = o.SupplierID "
				+ "ORDER BY ID DESC;";
		
		PreparedStatement pst = con_Inv.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    columnNames.addElement("Order ID");
    columnNames.addElement("Created");
    columnNames.addElement("Supplier");
    columnNames.addElement("Received Date");
    columnNames.addElement("Invoice ID");
  
    
    while(rs.next()){
    	Vector<Object> row = new Vector<Object>();
    	for (int i = 1; i <= 5; i++){
    		row.addElement(rs.getObject(i));
    	}
    	data.addElement(row);
    }
    
    TM_basic orderModel = new TM_basic(data, columnNames);
    inv_orderListTable.setModel(orderModel);
    inv_orderListTable.setRowHeight(30);
    
	Dimension tableSize = inv_orderListTable.getPreferredSize();
	TableColumnModel tcm = inv_orderListTable.getColumnModel();
	
	
		tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //check
		tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //ID
		tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); //Category
		tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //Sub Category
		tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); //Name

    
		inv_orderListTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseE) {
			if (mouseE.getClickCount() == 2) {
			    JTable target = (JTable)mouseE.getSource();
			    int row = target.getSelectedRow();
			    int orderID = (int) target.getValueAt(row, 0);
			    
			    updateInv_orderDetailTable(orderID);

				}
			}
		});	
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}//-------------------------------------------------------
	
	public void stopEditing(JTable table){
		table.getCellEditor().stopCellEditing();
	}
	
	public static void updateInv_orderDetailTable(int orderID){
		try {
			
		Connection con_Inv = Connect.connectionSetup();

		String query = "Select od.OrderID, od.ProductID,  p.Name as 'Product Name', od.OrderedQuantity, od.ReceivedQuantity, od.Cost "
				+ "FROM orderDetail od "
				+ "Inner JOIN product p on p.id = od.productID "
				+ "WHERE od.OrderID = " + orderID;
		
		PreparedStatement pst = con_Inv.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    columnNames.addElement("Order ID");
    columnNames.addElement("Product ID");
    columnNames.addElement("Product Name");
    columnNames.addElement("Ordered Quantity");
    columnNames.addElement("Delivered");
    columnNames.addElement("Total Cost ");
    columnNames.addElement("Received Quantity");
    columnNames.addElement("Cost for this delivery");
    columnNames.addElement("Other Cost");
  
    
    while(rs.next()){
    	Vector<Object> row = new Vector<Object>();
    	for (int i = 1; i <= 6; i++){
    		row.addElement(rs.getObject(i));
    	}
    	data.addElement(row);
    }
    
    TM_orderDetail orderDetailModel = new TM_orderDetail(data, columnNames);
    inv_orderDetailTable.setModel(orderDetailModel);
    inv_orderDetailTable.setRowHeight(30);
    inv_orderDetailTable.getColumnModel().getColumn(6).setCellRenderer(new TCellRend_orderDetail());
    inv_orderDetailTable.getColumnModel().getColumn(7).setCellRenderer(new TCellRend_orderDetail());
    
    TableModelListener TListner = new TableModelListener(){
    	
    	TableModel model = inv_orderDetailTable.getModel();
    	
		@Override
		public void tableChanged(TableModelEvent e) {
			if(e.getType() == TableModelEvent.UPDATE){ //#10

				int row = e.getFirstRow();
				int col = e.getColumn();
				int toBeDelivered =0; 
				if(model.getValueAt(row, 4) != null){
					toBeDelivered = (int) model.getValueAt(row, 3) - (int)model.getValueAt(row, 4);
				}else {
					//if nothing has been delivered, to be delivered will be the ordered quantity
					toBeDelivered = (int) model.getValueAt(row, 3);
				}
				
				if (col == 6){
					String received = (String) model.getValueAt(row, col);
					if (! validateStrToInt(received)){
						JOptionPane.showMessageDialog(null, "Receive quantity must be a whole number");
						model.setValueAt("0", row, col);
					}else if (Integer.parseInt((String) model.getValueAt(row, col)) > toBeDelivered){
						JOptionPane.showMessageDialog(null, "Received quantity must be less or equal to " + toBeDelivered);
						model.setValueAt("0", row, col);
					}
				}
				if(col == 7){
					String cost = (String) model.getValueAt(row, col);
					if (! validateStrToDouble(cost)){
						JOptionPane.showMessageDialog(null, "Cost must be a number");
						model.setValueAt("", row, col);
					}
				}

			}
		}
    };
    
    inv_orderDetailTable.getModel().addTableModelListener(TListner);
    
	Dimension tableSize = inv_orderDetailTable.getPreferredSize();
	TableColumnModel tcm = inv_orderDetailTable.getColumnModel();
	
	
		tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); 
		tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); 
		tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); 
		tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); 
		tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
		tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.15f));
		tcm.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.15f));
		tcm.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
		//tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Quantity
		//tcm.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //TO be delivered
		//tcm.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Sale Price
		//tcm.getColumn(9).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Unit Cost
		
		/*
		inv_orderDetailTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseE) {
			if (mouseE.getClickCount() == 2) {
			    JTable target = (JTable)mouseE.getSource();
			    int row = target.getSelectedRow();
			    int orderID = (int) target.getValueAt(row, 1);

			}
		}
		*/
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
	
	//======================================
	public void updateInv_soldByTable(){
		

		try {
			
			String query = "Select s.id, s.name "
					+ "from supplier s "
					+ "Inner join product_supplier ps ON s.id = ps.supplierID "
					+ "Inner join product p on p.id = ps.productID "
					+ "where p.id = "+ dataProdID.getText() +" AND "
					+ "Status = 'Active'";
			
			PreparedStatement pst = con_Inv.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();
	    
	    columnNames.addElement("Supplier ID");
	    columnNames.addElement("Name");
	    String suppliers = "";
	    
	    while(rs.next()){
	    	Vector<Object> row = new Vector<Object>();
	    	for (int i = 1; i <= 2; i++){
	    		
	    		row.addElement(rs.getObject(i));
	    		System.out.println("data is" + rs.getObject(i));
	    		if(i==1){
	    			suppliers = suppliers.concat(Integer.toString((int) rs.getObject(i)) + " ,");
	    		}
	    	}
	    	data.addElement(row);
	    }
	    
	    TM_basic basicModel = new TM_basic(data, columnNames);
	    inv_soldByTable.setModel(basicModel);
	    inv_soldByTable.setRowHeight(30);
	    
	    //add elements to combobox
	    //get list of suppliers not including those already selling the product
	    if(suppliers.length() > 0){
	    suppliers = suppliers.substring(0, suppliers.length() -2);
	    String snippet = "";
		    if (suppliers.length() != 0 ){
		    	snippet = snippet.concat("WHERE ID NOT IN (" + suppliers + " )");
		    }
		    query = "Select ID, Name from Supplier "+ snippet +" Order By Name";
			pst = con_Inv.prepareStatement(query);
			rs = pst.executeQuery();
			simpleSupList.removeAllItems();
			simpleSupList.addItem(new CbSupItem(0, "--Please select a supplier--", 1,1));
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				
				CbSupItem item = new CbSupItem(id, name, 1, 1);
				simpleSupList.addItem(item);
				
			}
			
			//if currently no supplier is associated
	    }else {
	    	query = "Select ID, Name from Supplier Order By Name";
	    	pst = con_Inv.prepareStatement(query);
			rs = pst.executeQuery();
			simpleSupList.removeAllItems();
			simpleSupList.addItem(new CbSupItem(0, "--Please select a supplier--", 1,1));
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				
				CbSupItem item = new CbSupItem(id, name, 1, 1);
				simpleSupList.addItem(item);
				}
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateNote(){ //=========================================
		
		String prodID = dataProdID.getText();
		//String newPrice = dataNewSalePrice.getText();
		String newNote = dataNote.getText();
		
		
		if (! validateStringSize(dataNote.getText(), 80)){
			JOptionPane.showMessageDialog(null, "Note must be less than 80 letters");
		} else 	{
			try {
				Connection con = Connect.connectionSetup();
				String query = "Update Product Set Notes = '" + newNote + "' WHERE ID = " + prodID;
				 PreparedStatement pst = con.prepareStatement(query);
				 pst.executeUpdate();
				 
				 con.close();
				 pst.close();
				 
				 //dataSalePrice.setText(newPrice);
				 dataNote.setText(newNote);
				 //dataNewSalePrice.setText("");
				 JOptionPane.showMessageDialog(null, "Note updated");
				 refreshInventoryTable(1);
				 //updateInv_priceHisTable();
				 
				 for(int i = 0; i < table_inventoryList.getRowCount(); i ++){
						int selectedID = (int) table_inventoryList.getValueAt(i, 1);
						if (selectedID == Integer.parseInt(prodID)){
							table_inventoryList.getSelectionModel().setSelectionInterval(i, i);
						}
					 } 
				 
				 //tabbedPane_Inventory.setSelectedIndex(0);
				 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public void updateInvoiceID(){
		
		
		try {
			con_Inv = Connect.connectionSetup();
		int orderID = 0; 
		if (inv_orderDetailTable.getValueAt(0, 0) != null){
			orderID = Integer.parseInt(inv_orderDetailTable.getValueAt(0, 0).toString());
			
			if (! dataInvoiceNumber.getText().equals("")){
			String query = "Update `order` Set InvoiceID = " + dataInvoiceNumber.getText() +" Where id =" + orderID;
			PreparedStatement pst = con_Inv.prepareStatement(query);
			pst.executeUpdate();
		    }
			
			updateInv_orderListTable();
			updateInv_orderDetailTable(orderID);
			dataInvoiceNumber.setText("");
		    for(int i=0; i < inv_orderListTable.getRowCount() ; i++){
		    	if(orderID == Integer.parseInt(inv_orderListTable.getValueAt(i, 0).toString())){
		    		inv_orderListTable.setRowSelectionInterval(i, i);
		    	}
		    }
		}

		
		con_Inv.close();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void updateOrderDetail(){
		
		try {
			System.out.println("Starting updateOrderDetail-----------");
			TableModel model = inv_orderDetailTable.getModel();
			if(inv_orderDetailTable.isEditing()){
				inv_orderDetailTable.getCellEditor().stopCellEditing();
				}
			con_Inv = Connect.connectionSetup();
			PreparedStatement pst;

			boolean detailUpdated = false;
			
			int orderID = 0;
			for (int i = 0; i < model.getRowCount(); i++){
				
				int qtyToAdd = 0;
				double costToAdd = 0;
				double extraCost = 0;
				int curQty =0;
				double totalCost = 0; 
				
				int prodID = 0;
				 
				
				//both received quantity and cost for this delivery must be filled
				if( model.getValueAt(i, 6) != null && model.getValueAt(i, 7) != null){
						qtyToAdd = Integer.parseInt((String) model.getValueAt(i, 6));
						costToAdd = Integer.parseInt((String) model.getValueAt(i, 7)); 
						if(model.getValueAt(i, 8) != null ){extraCost =  (double) model.getValueAt(i, 8);}
						if(model.getValueAt(i, 4) != null){	curQty = Integer.parseInt( model.getValueAt(i, 4).toString() );}
						if(model.getValueAt(i, 5) != null){	totalCost = Double.parseDouble( model.getValueAt(i, 5).toString() );}
						
						orderID = (int) model.getValueAt(i, 0);
					    prodID = (int) model.getValueAt(i, 1);
						
						String query = "Update orderDetail Set cost = " + (totalCost + costToAdd + extraCost) +", "
								+ "ReceivedQuantity = " + ( curQty + qtyToAdd ) + " "
								+ "Where OrderID = " + orderID + " AND ProductID = " + prodID ;	
						pst = con_Inv.prepareStatement(query);
						pst.executeUpdate();
						
						//get quantity and unitcost in the product table and update it
						int qtyOnHand= 0;
						double curUnitCost = 0; 
						double prodValue = 0; 
						
						query = "Select Quantity, UnitCost FROM Product WHERE ID = " + prodID;
						pst = con_Inv.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						
						while(rs.next()){
							qtyOnHand = rs.getInt(1);
							curUnitCost = rs.getDouble(2);
							}
							//calculate current product value 
							prodValue = qtyOnHand * curUnitCost; 
							System.out.println("qtyOnHand/"+qtyOnHand + " curUnitCost/"+ curUnitCost + " prodValue/" + prodValue);
														
							//add cost to prodValue, which results in the total value for the product
							prodValue = prodValue + (costToAdd + extraCost);
							System.out.println("costToAdd/" + costToAdd + " extraCost/"+ extraCost +" mew productValue/" + prodValue);
							
							double newUnitCost = prodValue / (qtyOnHand + qtyToAdd);
							System.out.println("prodValue" + prodValue + " / " + qtyOnHand + qtyToAdd + " (new unit cost is)" + newUnitCost);
							
							
							//update unit cost and quantity on hand using the new unit cost
							query = "Update Product Set UnitCost = " + newUnitCost + ", Quantity = " + (qtyOnHand + qtyToAdd) + ""
									+ " WHERE ID = " +prodID ;
							pst = con_Inv.prepareStatement(query);
							pst.executeUpdate();
							
							
							//finally, update supplier_product table's unit cost specific to a supplier
							//first, get supplier id based on the orderID
							int supID = 0;
							query = "Select SupplierID FROM `order` WHERE ID = " + orderID;
							pst = con_Inv.prepareStatement(query);
							rs = pst.executeQuery();
							while(rs.next()){
								supID = rs.getInt(1);
							}
							// then update unit cost in the product_supplier table
							query = "Update product_supplier Set UnitCost = " + ( (costToAdd + extraCost) / qtyToAdd ) + ""
									+ " WHERE ProductID = " + prodID + " AND SupplierID = " + supID ;
							pst = con_Inv.prepareStatement(query);
							pst.executeUpdate();
								
						detailUpdated = true;
						JOptionPane.showMessageDialog(null, "Order Updated");
				}
				
				// update order "received date" if it is empty
				for (int k = 0; k < inv_orderListTable.getRowCount() ; k++){ //444444
					if (orderID == (int)inv_orderListTable.getValueAt(k, 0) ){ //333333333
						//if something is delivered, and received date is empty, update received date
						if(inv_orderListTable.getValueAt(k, 3) == null && detailUpdated){
							String query = "Update `order` Set receivedDate = sysdate() Where id =" + orderID;
							pst = con_Inv.prepareStatement(query);
							pst.executeUpdate();
							detailUpdated = true;
						}
					} // 3333333333
				}//4444444
				
			}// end of looping orderDetail table
			
			if (detailUpdated){
				
				refreshInventoryTable(1);
				updateInv_orderListTable();
				updateInv_orderDetailTable(orderID);
			    for(int i=0; i < inv_orderListTable.getRowCount() ; i++){
			    	if(orderID == Integer.parseInt(inv_orderListTable.getValueAt(i, 0).toString())){
			    		inv_orderListTable.setRowSelectionInterval(i, i);
			    	}
			    }
			}
			
		    con_Inv.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void updateIndividualSalePrice(){ //=========================================
		
		String prodID = dataProdID.getText();
		String newPrice = dataNewSalePrice.getText();
		String newNote = dataNote.getText();
		
		
		if (! Inventory.validateDouble(dataNewSalePrice.getText())){
			JOptionPane.showMessageDialog(null, "New price must be a number");
		}else if (Double.parseDouble(newPrice) < Double.parseDouble( dataUnitCost.getText() )){
			JOptionPane.showMessageDialog(null, "new price must be higher than unit cost");
		} else 	{
			try {
				Connection con = Connect.connectionSetup();
				String query = "Update Product Set SalePrice = " + newPrice + " WHERE ID = " + prodID;
				 PreparedStatement pst = con.prepareStatement(query);
				 pst.executeUpdate();
				 
				 con.close();
				 pst.close();
				 
				 dataSalePrice.setText(newPrice);
				 //dataNote.setText(newNote);
				 dataNewSalePrice.setText("");
				 
				 refreshInventoryTable(1);
				 updateInv_priceHisTable();
				 
				 for(int i = 0; i < table_inventoryList.getRowCount(); i ++){
						int selectedID = (int) table_inventoryList.getValueAt(i, 1);
						if (selectedID == Integer.parseInt(prodID)){
							table_inventoryList.getSelectionModel().setSelectionInterval(i, i);
						}
					 } 
				 
				 //tabbedPane_Inventory.setSelectedIndex(0);
				 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public void distributeDeliveryCost(){ //=====================================
		if(inv_orderDetailTable.isEditing()){
			inv_orderDetailTable.getCellEditor().stopCellEditing();
		}
		String cost = dataDeliveryCost.getText();
		if( ! validateStrToDouble(cost)){
			JOptionPane.showMessageDialog(null, "Please enter a number in Delivery Cost field");
		}else {
			
			int totalQty = 0;
			for(int i =0; i < inv_orderDetailTable.getRowCount(); i++){
				if(inv_orderDetailTable.getValueAt(i, 6) != null){
					totalQty += Integer.parseInt((String) inv_orderDetailTable.getValueAt(i, 6));	
				}
			}
			
			System.out.println("Total qty is" + totalQty);
			if(totalQty == 0){
				JOptionPane.showMessageDialog(null, "Please update Received Quantity column");
			}else {
					for(int i = 0; i < inv_orderDetailTable.getRowCount(); i++){
						if(inv_orderDetailTable.getValueAt(i, 6) != null){
						Double thisQty = Double.parseDouble((String)inv_orderDetailTable.getValueAt(i, 6));
						Double percentage = thisQty / totalQty;
						System.out.println("Percentage is" + percentage);
						double thisCost = (Double.parseDouble(cost)) * percentage;
						inv_orderDetailTable.setValueAt(thisCost, i, 8);
						}

					}
			}

		}

		
	}
	
	
	public void displayDetail(int prodID){
		
		tabbedPane_Inventory.setEnabledAt(2, true);
		
		try {
			con_Inv = Connect.connectionSetup();
			String query = 	" Select p.ID, "
					+ "c.Name as Category, "
					+ "cc.Name as 'Sub Category', "
					+ "p.Name as 'Product Name', "
					+ "p.Description, "
					+ "p.Quantity, "
					+ "SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered', "
					+ "p.SalePrice as 'Sales Price',  "
					+ "UnitCost, "
					+ "Notes "
					+ "FROM Product p "
					+ "LEFT JOIN Category c on p.CategoryID = c.ID "
					+ "LEFT JOIN Category cc on p.SubCategoryID = cc.ID "
					+ "LEFT JOIN orderdetail od on p.ID = od.ProductID "
					+ "WHERE p.ID =" + prodID ; 	
					
			PreparedStatement pst = con_Inv.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			Vector<Object> pd = new Vector<Object>();
			while(rs.next()){
				for (int i= 1; i < 11; i++){ 
					pd.addElement(rs.getObject(i));
				}
			}

			dataProdID.setText(Integer.toString(((int)pd.get(0)))); 
			dataCat.setText((String) pd.get(1));
			if (pd.get(2) !=null ){	
				dataSubCat.setText((String)pd.get(2)); 
				}else {dataSubCat.setText("");}
			dataName.setText((String)pd.get(3));
			if (pd.get(4) != null){
				dataDesc.setText((String)pd.get(4));
			}else {
				dataDesc.setText("");
			}
			dataQty.setText(Integer.toString((int)pd.get(5)));
			
			if(pd.get(6) != null ){
			BigDecimal passedToBe = (BigDecimal) pd.get(6);
			dataToBe.setText(passedToBe.toString());
			}else {dataToBe.setText("0");}
			dataSalePrice.setText(Double.toString((double)pd.get(7)));
			dataUnitCost.setText(Double.toString((double)pd.get(8)));
			if(pd.get(9) != null){
				dataNote.setText((String)pd.get(9));
			}else {
				dataNote.setText("");
			}
			
			
			//update inv_soldByTable
			updateInv_soldByTable();
			updateInv_priceHisTable();
		    //close connection
		    con_Inv.close();
			pst.close();
			rs.close();
			
			tabbedPane_Inventory.setSelectedIndex(2);


			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//=========================================================================================
	// Table Model Listener for updating prev @ price, Order Total column for table_createORder
	private void setTMLforCreateOrderTable(){
		tml_CreateTable = new TableModelListener(){
		
			TableModel model = table_createOrder.getModel();
			TableModel model_summary = table_createOrderSummary.getModel();
			
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE){ //#10

					int row = e.getFirstRow();
					int col = e.getColumn();
					double unitP = 0;
					boolean addRowToSummary = false; 
					
					CbSupItem sup = null;
					int supID=0;
					String supName="";
					int minOrder=0;
					double orderTotalforThisSup = 0;
					
						//if supplier column is updated 
					if(col == 6){ //#15  ==========================================
							//get supplier object from column 6
						if (model.getValueAt(row, col) != null){
						sup  = (CbSupItem) model.getValueAt(row, col);
						supID = sup.getID();
						supName = sup.getSupplierName();
						minOrder = (int) sup.getMinOrder();
						}
						
							//if supplier is selected, add current unit cost
						if (sup != null){ //#21
							unitP = sup.getUnitCost();
						    model.setValueAt(unitP, row, col+1);
						}
						     //if order quantity already exist, update the subtotal 
						if (model.getValueAt(row, 8) != null ){
							int qty = Integer.parseInt( (String) model.getValueAt(row, 8) );
							model.setValueAt(unitP * qty , row, 9);
							
							//perform udpate table_orderSummary function
							addRowToSummary = true; 
							
							

						
						}
					} //end of - if supplier column is updated 
					
						//if order quantity is updated =========================================
					if(col == 8){
						addRowToSummary = false;
						if (validateStrToInt((String) model.getValueAt(row, col)) == false ){
							JOptionPane.showMessageDialog(null, "Please enter a whole number, or \"0\" ");
							
						}
						else { //#30
							
								//if supplier is not selected 
							if (model.getValueAt(row, 6) == null){
								JOptionPane.showMessageDialog(null, "Please select supplier first");
								
								//if supplier is already selected, update subtotal 
							} else {
								sup = (CbSupItem) model.getValueAt(row, 6);
								unitP = (double) model.getValueAt(row, 7);
								int qty = Integer.parseInt( (String) model.getValueAt(row, 8) );
								model.setValueAt(unitP * qty, row, 9);
								
								supID = sup.getID();
								supName= sup.getSupplierName();
								minOrder= (int) sup.getMinOrder();
								//orderTotalforThisSup = (double) model.getValueAt(row, 9);
								
								addRowToSummary = true;
							}
								
						} //#30
						
					}// end of - if order quantity is updated ====================
					
						//check if all the cells are filled before updating summary 
					/*
						boolean allCellFilled = false; 
						
						for (int t = 0; t < model.getRowCount(); t++){
							if (model.getValueAt(t, 6) != null && model.getValueAt(t, 8) != null ){
								allCellFilled = true; 
							}else allCellFilled = false; 
						}
					*/
					
					if (addRowToSummary == true ) {
						
						//------------------------------------
						//loop through createOrdertable and gather order summary for this supplier 
						orderTotalforThisSup = 0;
						for (int i = 0; i < table_createOrder.getRowCount(); i++){
							
							if(model.getValueAt(i, 6) != null){
								CbSupItem curSup = (CbSupItem)model.getValueAt(i, 6);
								int curSupID = curSup.getID();
								
								if(curSupID == supID && model.getValueAt(i, 8) != null){
									orderTotalforThisSup += (double)model.getValueAt(i, 9);
								}
							}

						}
						
						//remove row from summary table for the supplier 
						for(int k = 0; k < model_summary.getRowCount(); k++){
							int supIDinSummary = (int)model_summary.getValueAt(k, 0);
							if(supIDinSummary == (int)sup.getID()){
							((DefaultTableModel)model_summary).removeRow(k);
							}
						}
						//then add new row 
						((DefaultTableModel)model_summary).addRow(new Object[]{ supID, supName, minOrder, orderTotalforThisSup});
						//-------------------------------------

						
					} //end of if addRowToSummary 
						
					} //end of update 
					
				
				
			} //tableChanged()
			
		}; //TableModelListener()
		table_createOrder.getModel().addTableModelListener(tml_CreateTable);
	} //setTMLforCreateOrder()
	
	//===========================================================================
	public void refreshInventoryTable(int flag){ 

	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    try{
    	con_Inv = Connect.connectionSetup();
		
		String query = 
				" Select p.ID, "
				+ "c.Name as Category, "
				+ "cc.Name as 'Sub Category', "
				+ "p.Name as 'Product Name', "
				+ "p.Description, "
				+ "p.Quantity, "
				+ "SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered', "
				+ "p.SalePrice as 'Sales Price',  "
				+ "UnitCost, "
				+ "Notes "
				+ "FROM Product p "
				+ "LEFT JOIN Category c on p.CategoryID = c.ID "
				+ "LEFT JOIN Category cc on p.SubCategoryID = cc.ID "
				+ "LEFT JOIN orderdetail od on p.ID = od.ProductID "
				+ "GROUP BY p.ID;";	
				

		
		PreparedStatement pst = con_Inv.prepareStatement(query);
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
		columnNames.addElement("Sale Price");
		columnNames.addElement("Unit Cost");
		columnNames.addElement("Notes");
		if (flag == 2) columnNames.addElement("New Sale Price");
		
		//Get row data
		while(rs.next()){
		Vector<Object> row = new Vector<Object>(columnCount);
		row.addElement(Boolean.FALSE);
		
			for (int i = 1; i <=columnCount; i++){
				row.addElement(rs.getObject(i));
			}
		data.addElement(row);
		}
		
			pst.close();
			rs.close();
			con_Inv.close();
		
		}catch (Exception e1) {	
		e1.printStackTrace();
		}
        

    		MyTableModelClass MyModel = new MyTableModelClass(data, columnNames);
        	table_inventoryList.setModel(MyModel);
        	table_inventoryList.getTableHeader().setReorderingAllowed(false);
        	sorter = new TableRowSorter<MyTableModelClass>(MyModel);
        	table_inventoryList.setRowSorter(sorter);
        		
        	if(flag ==2){
        		/*
    			TableColumnModel colModel = table_inventoryList.getColumnModel();
    			TableColumn check = colModel.getColumn(0);
    			TableColumn unitCostCol = colModel.getColumn(9);
    			TableColumn noteCol = colModel.getColumn(10);
    			//colModel.removeColumn(check);
    			colModel.removeColumn(unitCostCol);
    			colModel.removeColumn(noteCol);
    			colModel.addColumn(unitCostCol);
    			colModel.addColumn(noteCol);	
    			*/
        	}
        	
        	table_inventoryList.addMouseListener(new MouseAdapter() {
        			public void mouseClicked(MouseEvent mouseE) {
        			if (mouseE.getClickCount() == 2) {
        			    JTable target = (JTable)mouseE.getSource();
        			    int row = target.getSelectedRow();
        			    int prodID = (Integer)target.getValueAt(row, 1);
        			    System.out.println("prodID is/"+prodID+"/");
        			    displayDetail(prodID);
        			    //jumpToEditPage((int)target.getValueAt(row, 1));

        			}
        		}
        	});
        	


		Dimension tableSize = table_inventoryList.getPreferredSize();
		tcm = table_inventoryList.getColumnModel();
		
		if (flag ==1 ){
			tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //check
			tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //ID
			tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.13f)); //Category
			tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.12f)); //Sub Category
			tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Name
			tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //Description
			tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Quantity
			tcm.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //TO be delivered
			tcm.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Sale Price
			tcm.getColumn(9).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //Unit Cost
			
			if (flag ==2 ){
				tcm.getColumn(10).setPreferredWidth(Math.round((tableSize.width)* 0.10f));//Notes
				tcm.getColumn(11).setPreferredWidth(Math.round((tableSize.width)* 0.05f));//new sale price
			} else if (flag ==1 ){
				tcm.getColumn(10).setPreferredWidth(Math.round((tableSize.width)* 0.15f));//Notes
			}
		}

		if (flag ==2){
			tml_newSalePrice = new TableModelListener(){
			
			TableModel model = table_inventoryList.getModel();
			
				@Override 
				public void tableChanged(TableModelEvent e){
					if(e.getType() == TableModelEvent.UPDATE){
						
						int row = e.getFirstRow();
						int col = e.getColumn();
						//boolean validPrice = true;
						
						if (col == 11 && model.getValueAt(row, col) != null ){ //=====
							String newPrice =  (String) model.getValueAt(row, 11);
							if (!validateDouble(newPrice)){ //----
								JOptionPane.showMessageDialog(null, "new sale price must be a number");
								model.setValueAt(null, row, col);

								//validPrice = false;
							}//----
						}//=======
						
					}
				}
			}; //tml_newSalePrice
			
			table_inventoryList.getModel().addTableModelListener(tml_newSalePrice);
			table_inventoryList.getColumnModel().getColumn(11).setCellRenderer(new TCellRend_newSalePrice());
			
			
		}//if(flag ==2) 
		
		
		
	}// end of refreshInventoryTable()
	
    //Added by Gaurav
    public void printOrderSheet() {
        if(tabbedPane_Inventory.getSelectedIndex() == 4){
            if(inv_orderDetailTable.getRowCount() != 0){
                int orderid = Integer.parseInt(inv_orderDetailTable.getModel().getValueAt(0, 0).toString());
                
                try {
					Connect connect = new Connect();
					Connection c = DriverManager.getConnection(connect.getURL(),connect.getUsername(),connect.getPassword());
							
					JasperDesign jd = JRXmlLoader.load("order.jrxml");
					String sql = "SELECT DATE_FORMAT(o.CreateDate, '%d %M %Y %H:%i')  as CreateDate, o.Cost, "
			                                + " ROUND(o.Cost * 0.13, 2) as 'Tax', ROUND(o.Cost + (o.Cost * 0.13) + s.DeliveryCost, 2) as 'Total', "
			                                + " s.Name, s.Street, s.City, s.State_Province, s.PostalCode, s.PhoneNumber, s.Email, s.DeliveryCost, "
			                                + " od.ProductID, p.Name as 'ProductName', p.Description, od.OrderedQuantity, od.Cost as 'EstimateCost' " 
			                                + " FROM  StoreDB.Order o, StoreDB.Supplier s, StoreDB.OrderDetail od, StoreDB.Product p " 
			                                + " WHERE o.ID = od.OrderID AND od.ProductID = p.ID AND o.SupplierID = s.ID AND o.ID =" + orderid;
					
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
                
            } else {
                JOptionPane.showMessageDialog(null, "Order Details is empty, please view order details for print.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Only order details on OrderList tab is allowed to print.");
        }
    }// end of print Order Sheet 
    
} // end of class inventory 
//==============================================================================================
//==============================================================================================
//==============================================================================================


//===========================================================
class TCellRend_newSalePrice extends DefaultTableCellRenderer { 
    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int col) 
	{ 
	    Component c  = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col); 
	   
	    double curSalePrice = (double) table.getValueAt(row, 8);
	    double curUnitPrice = (double) table.getValueAt(row, 9);
	    if (table.getValueAt(row, col) != null){
	    		if (Double.valueOf(table.getValueAt(row, col).toString()) <= curUnitPrice  ){
	    			setBackground(Color.RED);
	    		}else if (Double.valueOf(table.getValueAt(row, col).toString()) <= curSalePrice  ){
	    			setBackground(Color.YELLOW);
	    		}else {
	    			setBackground(Color.WHITE);
	    		}
			} else { //if no data is in the cell
				setBackground(new Color(100, 255, 255));

			}
	    return c; 
	}
} // -------------------------

//===========================================================
class TCellRend_orderDetail extends DefaultTableCellRenderer { 
  public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int col) 
	{ 
	    Component c  = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col); 
	  
	    	setBackground(new Color(100, 255, 255));

	    /*
	    if (table.getValueAt(row, col) != null){
	    		if (Double.valueOf(table.getValueAt(row, col).toString()) <= curUnitPrice  ){
	    			setBackground(Color.RED);
	    		}else if (Double.valueOf(table.getValueAt(row, col).toString()) <= curSalePrice  ){
	    			setBackground(Color.YELLOW);
	    		}else {
	    			setBackground(Color.WHITE);
	    		}
			} else { //if no data is in the cell
				setBackground(new Color(100, 255, 255));

			}
	    
	    */
	    return c; 
	}
} // -------------------------

//===========================================================
class TM_orderDetail extends DefaultTableModel{ //********************************************
	public TM_orderDetail(Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override //tells table model that columns contain object ?
	public Class getColumnClass(int column)	{
		 for (int row = 0; row < getRowCount(); row++){
		     Object o = getValueAt(row, column);
		     if (o != null) {
		         return o.getClass();
		     }
		 }
	return Object.class;
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		
		switch (columnIndex){
		case 6: return true;
		case 7: return true; //if new sale price column is inserted
		default: return false;
		
		}
	}
}// -------------------------

class MyTableModelClass extends DefaultTableModel{ //********************************************
	public MyTableModelClass(Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override //tells table model that columns contain object ?
	public Class getColumnClass(int column)	{
		 for (int row = 0; row < getRowCount(); row++){
		     Object o = getValueAt(row, column);
		     if (o != null) {
		         return o.getClass();
		     }
		 }
	return Object.class;
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		
		switch (columnIndex){
		case 0: return true;
		case 11: return true; //if new sale price column is inserted
		default: return false;
		
		}
	}
}//--------------------------

class TM_basic extends DefaultTableModel{
	public TM_basic (Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override
	public Class getColumnClass(int column){
		 for (int row = 0; row < getRowCount(); row++){
		     Object o = getValueAt(row, column);
		
		     if (o != null) {
		         return o.getClass();
		     }
		 }
	
	return Object.class;
	}
	@Override
	public boolean isCellEditable(int row, int col){

	    switch (col){
		default: return false;
	    }	
	}
}//-------------------------------------

class TM_CreateOrder extends DefaultTableModel{//********************************************
	public TM_CreateOrder (Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override
	public Class getColumnClass(int column){
		 for (int row = 0; row < getRowCount(); row++) {
		     Object o = getValueAt(row, column);
		     if (o != null) {
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
} //MyTableModelClassForCreateOrder --------------------

class TM_CreateOrderSummary extends DefaultTableModel{//********************************************
	public TM_CreateOrderSummary (Vector<Object> data, Vector<Object> columnNames) {
		super(data,columnNames);
	}
	@Override
	public Class getColumnClass(int column){
		 for (int row = 0; row < getRowCount(); row++){
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
} //MyTableModelClassForCreateOrder ---------------------

class CbCategoryItem{
	private int id;
	private String categoryName; 
	public CbCategoryItem(int idInput, String nameInput){
		this.id = idInput; 
		this.categoryName = nameInput;
	}
	public int getID(){	return this.id;}
	public String getCategoryName(){return this.categoryName;}
} //cbCategoryItem-----------------------------

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
}//CbCatListRenderer---------------------------

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

class ProdDetail {
	
	private int prodID; 
	private String Cat;
	private String SubCat;
	private String prodName;
	private String desc;
	private int qty;
	private int toBeDel;
	private double salePrice;
	private double unitCost;
	private String note;

	
	public ProdDetail(int pid, String c, String sc, String n, String d, int qt, int tobe, double sp, double uc, String not){
		prodID = pid;
		Cat = c;
		SubCat = sc;
		prodName = n;
		desc = d;
		qty = qt;
		toBeDel = tobe;
		salePrice = sp;
		unitCost = uc;
		note = not;
	}
	
	public int getID(){ return prodID; }
	public String getCat() { return Cat;}
	public String getSubCat() {return SubCat;}
	public String getName() { return prodName;}
	public String getDesc() {return desc;}
	public int getQty(){ return qty; }
	public int getToBe(){ return toBeDel;}
	public double getSaleP() {return salePrice;}
	public double getUnitC(){ return unitCost;}
	public String getNote(){ return note;}
	
}

//==========================================================
class JPanelAddNewPanel extends JPanel{
		
		JTextField dataSupInvoiceID;
		JComboBox<vision.CbSupItem> dataSupCb;
		JTextField dataAmountDue;
		JTextField dataAmountPaid;
		JTextField dataOrderID;
		JTextArea dataMessage;
		static JTable pickOrderTable;
		JDatePickerImpl datePicker;
		
		JPanelAddNewPanel(){
			//JPanel contents = new JPanel();



			super.setBounds(0, 0, 350, 500);
			GridBagLayout gbl_super = new GridBagLayout();
			gbl_super.columnWidths = new int[]{150, 50, 50, 50, 0, 0};
			gbl_super.rowHeights = new int[]{0, 20, 25, 0, 0, 0, 0, 110, 100, 0, 0};
			gbl_super.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_super.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
			super.setLayout(gbl_super);
			
			

			
			JLabel lblNewInvoice = new JLabel("Add New Invoice");
			GridBagConstraints gbc_lblNewInvoice = new GridBagConstraints();
			gbc_lblNewInvoice.anchor = GridBagConstraints.EAST;
			gbc_lblNewInvoice.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewInvoice.gridx = 0;
			gbc_lblNewInvoice.gridy = 0;
			super.add(lblNewInvoice, gbc_lblNewInvoice);
			lblNewInvoice.setFont(new Font("Tahoma", Font.PLAIN, 17));
			
			JLabel lblInvoiceId = new JLabel("Supplier Invoice #/ ID");
			GridBagConstraints gbc_lblInvoiceId = new GridBagConstraints();
			gbc_lblInvoiceId.anchor = GridBagConstraints.EAST;
			gbc_lblInvoiceId.insets = new Insets(0, 0, 5, 5);
			gbc_lblInvoiceId.gridx = 0;
			gbc_lblInvoiceId.gridy = 2;
			super.add(lblInvoiceId, gbc_lblInvoiceId);
			lblInvoiceId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataSupInvoiceID = new JTextField();
			GridBagConstraints gbc_dataSupInvoiceID = new GridBagConstraints();
			gbc_dataSupInvoiceID.fill = GridBagConstraints.BOTH;
			gbc_dataSupInvoiceID.gridwidth = 2;
			gbc_dataSupInvoiceID.insets = new Insets(0, 0, 5, 5);
			gbc_dataSupInvoiceID.gridx = 1;
			gbc_dataSupInvoiceID.gridy = 2;
			super.add(dataSupInvoiceID, gbc_dataSupInvoiceID);
			dataSupInvoiceID.setColumns(10);
			dataSupInvoiceID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblSupplier = new JLabel("Supplier");
			GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
			gbc_lblSupplier.anchor = GridBagConstraints.EAST;
			gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
			gbc_lblSupplier.gridx = 0;
			gbc_lblSupplier.gridy = 3;
			super.add(lblSupplier, gbc_lblSupplier);
			lblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataSupCb = new JComboBox();
			GridBagConstraints gbc_dataSubCb = new GridBagConstraints();
			gbc_dataSubCb.fill = GridBagConstraints.BOTH;
			gbc_dataSubCb.gridwidth = 2;
			gbc_dataSubCb.insets = new Insets(0, 0, 5, 5);
			gbc_dataSubCb.gridx = 1;
			gbc_dataSubCb.gridy = 3;
			super.add(dataSupCb, gbc_dataSubCb);
			dataSupCb.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			
			
			JLabel lblAmountDue = new JLabel("Amount Due");
			GridBagConstraints gbc_lblAmountDue = new GridBagConstraints();
			gbc_lblAmountDue.anchor = GridBagConstraints.EAST;
			gbc_lblAmountDue.insets = new Insets(0, 0, 5, 5);
			gbc_lblAmountDue.gridx = 0;
			gbc_lblAmountDue.gridy = 4;
			super.add(lblAmountDue, gbc_lblAmountDue);
			lblAmountDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			
			dataAmountDue = new JTextField();
			GridBagConstraints gbc_dataAmountDue = new GridBagConstraints();
			gbc_dataAmountDue.fill = GridBagConstraints.BOTH;
			gbc_dataAmountDue.gridwidth = 1;
			gbc_dataAmountDue.insets = new Insets(0, 0, 5, 5);
			gbc_dataAmountDue.gridx = 1;
			gbc_dataAmountDue.gridy = 4;
			super.add(dataAmountDue, gbc_dataAmountDue);
			dataAmountDue.setColumns(10);
			dataAmountDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblAmountPaid = new JLabel("Amount Paid");
			GridBagConstraints gbc_lblAmountPaid = new GridBagConstraints();
			gbc_lblAmountPaid.anchor = GridBagConstraints.EAST;
			gbc_lblAmountPaid.insets = new Insets(0, 0, 5, 5);
			gbc_lblAmountPaid.gridx = 0;
			gbc_lblAmountPaid.gridy = 5;
			super.add(lblAmountPaid, gbc_lblAmountPaid);
			lblAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataAmountPaid = new JTextField();
			GridBagConstraints gbc_dataAmountPaid = new GridBagConstraints();
			gbc_dataAmountPaid.gridwidth = 1;
			gbc_dataAmountPaid.anchor = GridBagConstraints.NORTH;
			gbc_dataAmountPaid.insets = new Insets(0, 0, 5, 5);
			gbc_dataAmountPaid.fill = GridBagConstraints.HORIZONTAL;
			gbc_dataAmountPaid.gridx = 1;
			gbc_dataAmountPaid.gridy = 5;
			super.add(dataAmountPaid, gbc_dataAmountPaid);
			dataAmountPaid.setColumns(10);
			dataAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			//------date picker components -----------
			UtilDateModel model = new UtilDateModel();
	        Properties p = new Properties();
	        p.put("text.today", "Today");
	        p.put("text.month", "Month");
	        p.put("text.year", "Year");
	        
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
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			//------end of datepicker components ---------
			
			
			GridBagConstraints dateConstraints = new GridBagConstraints();
			dateConstraints.anchor = GridBagConstraints.NORTHEAST;
			dateConstraints.insets = new Insets(0, 0, 5, 5);
			dateConstraints.gridx = 1;
			dateConstraints.gridy = 6;
			super.add(datePicker, dateConstraints);
			//lblSelectOrdersTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lbldate = new JLabel("Received Date");
			GridBagConstraints dateConstraints2 = new GridBagConstraints();
			dateConstraints2.anchor = GridBagConstraints.NORTHEAST;
			dateConstraints2.insets = new Insets(0, 0, 5, 5);
			dateConstraints2.gridx = 0;
			dateConstraints2.gridy = 6;
			super.add(lbldate, dateConstraints2);
			lbldate.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			
			JLabel lblSelectOrdersTo = new JLabel("Orders to associate");
			GridBagConstraints gbc_lblSelectOrdersTo = new GridBagConstraints();
			gbc_lblSelectOrdersTo.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblSelectOrdersTo.insets = new Insets(0, 0, 5, 5);
			gbc_lblSelectOrdersTo.gridx = 0;
			gbc_lblSelectOrdersTo.gridy = 7;
			super.add(lblSelectOrdersTo, gbc_lblSelectOrdersTo);
			lblSelectOrdersTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JScrollPane scrollPane_6 = new JScrollPane();
			GridBagConstraints gbc_scrollPane_6 = new GridBagConstraints();
			gbc_scrollPane_6.gridwidth = 3;
			gbc_scrollPane_6.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane_6.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_6.gridx = 1;
			gbc_scrollPane_6.gridy = 7;
			super.add(scrollPane_6, gbc_scrollPane_6);
			
			

			
			JLabel lblMessage = new JLabel("Message:");
			GridBagConstraints gbc_lblMessage = new GridBagConstraints();
			gbc_lblMessage.insets = new Insets(0, 0, 5, 5);
			gbc_lblMessage.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblMessage.gridx = 0;
			gbc_lblMessage.gridy = 8;
			super.add(lblMessage, gbc_lblMessage);
			lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataMessage = new JTextArea();
			dataMessage.setBackground(SystemColor.control);
			dataMessage.setEditable(false);
			dataMessage.setLineWrap(true);
			GridBagConstraints gbc_textArea = new GridBagConstraints();
			gbc_textArea.gridwidth = 3;
			gbc_textArea.insets = new Insets(0, 0, 5, 5);
			gbc_textArea.fill = GridBagConstraints.BOTH;
			gbc_textArea.gridx = 1;
			gbc_textArea.gridy = 8;
			super.add(dataMessage, gbc_textArea);
			dataMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));

			
			//----------------------
			try{
				Connection con_Inv = Connect.connectionSetup();
				String query = "Select ID, Name FROM Supplier Order BY Name";
				PreparedStatement pst = con_Inv.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				//fill supplier combobox
				CbSupItem firstItem = new CbSupItem(0, "-- Please select Supplier --",0, 0);
				dataSupCb.addItem(firstItem);
					while(rs.next()){
					int id = rs.getInt("ID");	
					String str = rs.getString("Name").trim();
					CbSupItem item = new CbSupItem(id, str,0,0);
					dataSupCb.addItem(item);	
				} 
				
				con_Inv.close();
				pst.close();
				rs.close();
				
				}catch (Exception e){
					e.printStackTrace();
				}
			
			//add action listener for supplier combo box
			dataSupCb.addActionListener(
					new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							JComboBox combo =  (JComboBox) e.getSource();
							CbSupItem item= (CbSupItem) combo.getSelectedItem();
							int supID = item.getID();
							
							//populate pickup table based on the supplier ID
							pickOrderTable = new JTable();
							createTablePickOrder(supID);
							scrollPane_6.setViewportView(pickOrderTable);
						}
					}
					);

		}// end of JPanelAddNewInvoice
		 
		public String getSupInvoiceID() {
				if(dataSupInvoiceID.getText() != null){
					return dataSupInvoiceID.getText();
				}else {return "0";}
			}
		
		public CbSupItem getSupCb(){
			if(dataSupCb.getSelectedItem() != null ){
				return (CbSupItem) dataSupCb.getSelectedItem(); 
				}else {return new CbSupItem(0, "notSelected",0,0);}
			}
		public String getAmountDue() {
				//if(dataAmountDue.getText().equals("")){
					return dataAmountDue.getText(); 
				//}else {return "0";}
			}
		public String getAmountPaid() {
			//if(dataAmountPaid.getText().equals("")){
				return dataAmountDue.getText(); 
			//}else {return "0";}
		}
		public Date getReceivedDate(){
			if(datePicker.getModel().getValue() != null ){
				return (Date) datePicker.getModel().getValue(); 
			}else {return new Date();}
		}
		public void setMessage(String str){
			dataMessage.setText(str);
		}
		
		
		public static void createTablePickOrder(int supID){
			try {
				Connection con_Inv = Connect.connectionSetup();
				String query = "Select o.ID, o.InvoiceID, s.Name as 'Supplier Name', o.CreateDate, o.ReceivedDate "
					+ "FROM `order` o "
					+ "Inner JOIN Supplier s ON s.ID = o.SupplierID "
					+ "WHERE s.ID = " + supID + " AND o.InvoiceID IS NULL ORDER BY ID DESC;";
			
				PreparedStatement pst = con_Inv.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				Vector<Object> columnNames = new Vector<Object>();
			    Vector<Object> data = new Vector<Object>();
			    
			    columnNames.addElement("Check");
			    columnNames.addElement("Order ID");
			    columnNames.addElement("Invoice ID");
			    columnNames.addElement("Supplier");
			    columnNames.addElement("Created Date");
			    columnNames.addElement("Received Date");

		    while(rs.next()){
		    	Vector<Object> row = new Vector<Object>();
		    	boolean check = false;
		    	row.addElement(check);
		    	for (int i = 1; i <= 5; i++){
		    		row.addElement(rs.getObject(i));
		    	}
		    	data.addElement(row);
		    }
		    
		    con_Inv.close();
		    //-----
		    
		    class TMforOrderPick extends DefaultTableModel{ //********************************************
		    	public TMforOrderPick(Vector<Object> data, Vector<Object> columnNames) {
		    		super(data,columnNames);
		    	}
		    	@Override //tells table model that columns contain object ?
		    	public Class getColumnClass(int column)	{
		    		 for (int row = 0; row < getRowCount(); row++){
		    		     Object o = getValueAt(row, column);
		    		     if (o != null) {
		    		         return o.getClass();
		    		     }
		    		 }
		    	return Object.class;
		    	}
		    	@Override
		    	public boolean isCellEditable(int rowIndex, int columnIndex){
		    		
		    		switch (columnIndex){
		    		case 0: return true;
		    		default: return false;
		    		}
		    	}
		    }
		    //-----
		    TMforOrderPick orderPickModel = new TMforOrderPick(data, columnNames);
		    pickOrderTable.setModel(orderPickModel);
		    pickOrderTable.setRowHeight(30);
		    
			Dimension tableSize = pickOrderTable.getPreferredSize();
			TableColumnModel tcm = pickOrderTable.getColumnModel();
			
		
			tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
			tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.05f));
			tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
			tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); 
			tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.25f));
			tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.25f));

			/*
			inv_orderListTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent mouseE) {
				if (mouseE.getClickCount() == 2) {
				    JTable target = (JTable)mouseE.getSource();
				    int row = target.getSelectedRow();
				    int orderID = (int) target.getValueAt(row, 0);
				    
				    updateInv_orderDetailTable(orderID);

					}
				}
			});	
			*/
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
		}// end of createTableOrderPick-------------------------------------------------------

	}
	
//===========================================================================================
class DialogAddNewInvoice extends JDialog implements ActionListener, PropertyChangeListener{
	
	private JOptionPane optionPane; 
	//JPanelAddNewPanel panel;
	InventoryInvoiceAddNewPanel panel;
	
	public DialogAddNewInvoice(Frame aFrame){
		super(aFrame, true);
		super.setTitle("Add Invoice");
		
		
	   //panel = new JPanelAddNewPanel();
		panel = new InventoryInvoiceAddNewPanel();
		//
		Object[] options = {"Add", "Cancel"};
		
		//create the JOptionPane
		optionPane = new JOptionPane(
				panel, /*the object to display*/
				JOptionPane.QUESTION_MESSAGE, /*type of mesage to be displayed*/
				JOptionPane.YES_NO_OPTION,/*options to display in the pane*/
				null,/*Icon - the icon image to display*/
				options,/*Object[] - the choices the user can select*/
				null /*Object - initially selected value inside the choice*/
				);
		
			//make this dialog display in CustomDialog
		setContentPane(optionPane);
		
			//Handle window closing correctly 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
        //Register an event handler that puts the text into the option pane
        //textField.addActionListener(this);

			//Register an event handler that reacts to option pane state changes
        optionPane.addPropertyChangeListener(this);
        pack();
        
        	//set the dialog at the center
        super.setLocationRelativeTo(null);

	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
        if (	isVisible() && 
        		(evt.getSource() == optionPane) && 
        		(JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))
            ) {
                   Object value = optionPane.getValue();  //return the value user has selected 
                   if (value == JOptionPane.UNINITIALIZED_VALUE) {
                       //ignore reset
                       return;
                   }
        
                   //Reset the JOptionPane's value.
                   //If you don't do this, then if the user
                   //presses the same button next time, no
                   //property change event will be fired.
                   optionPane.setValue(
                           JOptionPane.UNINITIALIZED_VALUE);
        
                   if (value == "Add") {
                	   
                	   panel.setMessage("");
                	   boolean success = true;
                	   String error = "";
                	   CbSupItem sup; 
                	   
                	   //check if supplier is selected
                	   String supInvoiceID = panel.getSupInvoiceID();
                	   if(panel.getSupCb().getID() != 0){
                		   sup = panel.getSupCb();
                		   }else{
                			   error = error.concat("Please select supplier \n");      
                			   };		   
                	   
                	   	//check if amount due is valid
                	   String dueAmount = panel.getAmountDue();
                	   String paidAmount = panel.getAmountPaid();
                	   Date pickedDate = panel.getReceivedDate();
                	   //System.out.println("picked date is " + pickedDate);
                	  // System.out.println("amountPaid is/" + paidAmount + "/");
                	   //System.out.println("amountdue is/" + dueAmount + "/");

            		   if( ! Inventory.validateStrToDouble(dueAmount) && !dueAmount.equals("")){
            		   error = error.concat("Amount Due must be a valid number\n");
            		   }
                	   	
            		   if(! Inventory.validateDouble(paidAmount) && ! paidAmount.equals("") ){ //if conversion fails
            	   				error = error.concat("Amount Paid must be a valid number\n");
            		   }
            	   		if( Inventory.validateDouble(paidAmount) && ! paidAmount.equals("") &&
            	   				Double.parseDouble(paidAmount) > Double.parseDouble(dueAmount)){
            	   			error = error.concat("Amount paid cannot exceed due amount \n");
            	   		}
            	   		
            	   		
                	    	// if "error" string is empty, operation is successful
	                	    if(error.length() < 1){
	                	    	
	                	    	//if dueAmount and paidAmount is empty, assign 0
	                	    	if(dueAmount.equals(""))dueAmount = "0";
	                	    	if(paidAmount.equals(""))paidAmount = "0";
	                	    	
	                	    	//insert new invoice to invoice table 
	                	    	try {
	                				Connection con = Connect.connectionSetup();
	                				String query = "Insert Into invoice (AmountDue, AmountPaid, ReceivedDate, SupplierID) "
	                						+ " Values (?, ?, ?, ?)"; 
	                						
	                				PreparedStatement pst = con.prepareStatement(query);
	                				pst.setDouble(1, Double.parseDouble(dueAmount));
	                				pst.setDouble(2, Double.parseDouble(paidAmount));
	                				pst.setDate(3, new java.sql.Date(pickedDate.getTime()));
	                				pst.setInt(4, panel.getSupCb().getID());
	                				
	                				pst.executeUpdate();
	                				
								
	                				//get newly created invoice ID and associate it with selected orders
	                				int newInvoiceID = 0;
									query = "Select max(ID) from Invoice";
	                				pst = con.prepareStatement(query);
	                				ResultSet rs = pst.executeQuery(query);
	                				while(rs.next()){
	                					newInvoiceID = rs.getInt(1);
	                				}
	                				

	                					//now update orders loop through pickOrder table and update orders that was selected
		                				//if no order was selected, simply create a new invoice without associating any order	 
		                				int rowCount = panel.pickOrderTable.getModel().getRowCount();
										for(int j = 0; j < rowCount; j++){  //#loop through pickOrder Table-----
											if((boolean)panel.pickOrderTable.getValueAt(j, 0) == true ){
												
												int orderID = (int) panel.pickOrderTable.getValueAt(j, 1);
												query = "Update `order` set InvoiceID = " + newInvoiceID + ""
														+ " WHERE ID = " + orderID ;
												pst = con.prepareStatement(query);
												pst.executeUpdate();

											}
										} // end of #loop through pickOrder Table----
									
									//inform user that an invoice has been created
									JOptionPane.showMessageDialog(null, "New Invoice (ID: " + newInvoiceID + ") Added");	
									
									//refresh tables and close the dialog
									Inventory.updateInv_orderListTable();
									Inventory.createInvoiceTable();
									clearPanel();
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                	    	
	                	    	}else {
	                	    		//otherwise display error message in dataMessage
	                	    		panel.setMessage(error);
	                	    	}

                       } else if (value =="Cancel") {
                    	   clearPanel();
                       }
                           //textField.requestFocusInWindow();
               }
        }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
    public void clearPanel() {
        setVisible(false);
    }


} //end of DialogAddNewInvoice ---------------------------------


//=======================================================================================
class DialogEditInvoice extends JDialog implements ActionListener, PropertyChangeListener{
	
	private JOptionPane optionPane; 
	//JPanelAddNewPanel panel;
	InventoryInvoiceEditPanel panel;
	private int invoiceID; 
	
	public DialogEditInvoice(Frame aFrame, int invoiceID){
		
		super(aFrame, true);
		super.setTitle("Edit Invoice");
		this.invoiceID = invoiceID;
		
	   //panel = new JPanelAddNewPanel();
		panel = new InventoryInvoiceEditPanel(invoiceID);
		//
		Object[] options = {"Update", "Delete", "Cancel"};
		
		//create the JOptionPane
		optionPane = new JOptionPane(
				panel, /*the object to display*/
				JOptionPane.QUESTION_MESSAGE, /*type of mesage to be displayed*/
				JOptionPane.YES_NO_OPTION,/*options to display in the pane*/
				null,/*Icon - the icon image to display*/
				options,/*Object[] - the choices the user can select*/
				null /*Object - initially selected value inside the choice*/
				);
		
			//make this dialog display in CustomDialog
		setContentPane(optionPane);
		
			//Handle window closing correctly 
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
        //Register an event handler that puts the text into the option pane
        //textField.addActionListener(this);

			//Register an event handler that reacts to option pane state changes
        optionPane.addPropertyChangeListener(this);
        pack();
        
        	//set the dialog at the center
        super.setLocationRelativeTo(null);

	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
        if (	isVisible() && 
        		(evt.getSource() == optionPane) && 
        		(JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))
            ) {
                   Object value = optionPane.getValue();  //return the value user has selected 
                   if (value == JOptionPane.UNINITIALIZED_VALUE) {
                       //ignore reset
                       return;
                   }
        
                   //Reset the JOptionPane's value.
                   //If you don't do this, then if the user
                   //presses the same button next time, no
                   //property change event will be fired.
                   optionPane.setValue(
                           JOptionPane.UNINITIALIZED_VALUE);
        
                   if (value == "Update") {
                	   
                	   panel.setMessage("");
                	   boolean success = true;
                	   String error = "";
                	   CbSupItem sup; 		   
                	   
                	   	//check if amount due is valid
                	   int invoiceID = panel.getInvoiceID();
                	   String dueAmount = panel.getAmountDue();
                	   String paidAmount = panel.getAmountPaid();
                	   Date pickedDate = panel.getReceivedDate();
                	   //System.out.println("picked date is " + pickedDate);
                	  // System.out.println("amountPaid is/" + paidAmount + "/");
                	   //System.out.println("amountdue is/" + dueAmount + "/");

            		   if( ! Inventory.validateStrToDouble(dueAmount) && !dueAmount.equals("")){
            		   error = error.concat("Amount Due must be a valid number\n");
            		   }
                	   	
            		   if(! Inventory.validateDouble(paidAmount) && ! paidAmount.equals("") ){ //if conversion fails
            	   				error = error.concat("Amount Paid must be a valid number\n");
            		   }
            	   		if( Inventory.validateDouble(paidAmount) && ! paidAmount.equals("") &&
            	   				Double.parseDouble(paidAmount) > Double.parseDouble(dueAmount)){
            	   			error = error.concat("Amount paid cannot exceed due amount \n");
            	   		}
            	   		
            	   		
                	    	// if "error" string is empty, operation is successful
	                	    if(error.length() < 1){
	                	    	
	                	    	//if dueAmount and paidAmount is empty, assign 0
	                	    	if(dueAmount.equals(""))dueAmount = "0";
	                	    	if(paidAmount.equals(""))paidAmount = "0";
	                	    	
	                	    	//insert new invoice to invoice table 
	                	    	try {
	                				Connection con = Connect.connectionSetup();
	                				String query = "Update invoice Set "
	                						+ "AmountDue = ?, "
	                						+ "AmountPaid =?, "
	                						+ "ReceivedDate = ?, "
	                						+ "SupplierID = ? "
	                						+ "WHERE ID = ? " ;
	                						
	                				PreparedStatement pst = con.prepareStatement(query);
	                				pst.setDouble(1, Double.parseDouble(dueAmount));
	                				pst.setDouble(2, Double.parseDouble(paidAmount));
	                				pst.setDate(3, new java.sql.Date(pickedDate.getTime()));
	                				pst.setInt(4, panel.getSupIDInt());
	                				pst.setInt(5, invoiceID);
	                				
	                				pst.executeUpdate();
	                				
	                				/*get newly created invoice ID and associate it with selected orders
	                				int newInvoiceID = 0;
									query = "Select max(ID) from Invoice";
	                				pst = con.prepareStatement(query);
	                				ResultSet rs = pst.executeQuery(query);
	                				while(rs.next()){
	                					newInvoiceID = rs.getInt(1);
	                				}*/

                					//now loop through pickOrder table and update orders 
	                				//if no order was selected, simply create a new invoice without associating any order	 
	                				int rowCount = panel.pickOrderTable.getModel().getRowCount();
									for(int j = 0; j < rowCount; j++){  //#loop through pickOrder Table-----
										
										boolean selected = (boolean) panel.pickOrderTable.getValueAt(j, 0); 
										//if check is marked, associate it to this invoice
										if(selected){
											int orderID = (int) panel.pickOrderTable.getValueAt(j, 1);
											query = "Update `order` set InvoiceID = " + invoiceID + ""
													+ " WHERE ID = " + orderID ;
											pst = con.prepareStatement(query);
											pst.executeUpdate();
										}else{
											int orderID = (int) panel.pickOrderTable.getValueAt(j, 1);
											query = "Update `order` set InvoiceID = NULL "
													+ " WHERE ID = " + orderID ;
											pst = con.prepareStatement(query);
											pst.executeUpdate();
										}
										
									} // end of #loop through pickOrder Table----
									
									//inform user that an invoice has been created
									JOptionPane.showMessageDialog(null, "Invoice (ID: " + invoiceID + ") Updated");	
									
									//refresh tables and close the dialog
									Inventory.updateInv_orderListTable();
									Inventory.createInvoiceTable();
									clearPanel();
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                	    	
	                	    	}else {
	                	    		//otherwise display error message in dataMessage
	                	    		panel.setMessage(error);
	                	    	}

                       } else if (value =="Cancel") {
                    	   clearPanel();
                       } else if (value == "Delete"){
                    	   
                    	    int response = JOptionPane.showConfirmDialog(null, "Are you sure to delete this invoice?", "Confirm",
                    	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    	        if (response == JOptionPane.NO_OPTION) {
                    	        } else if (response == JOptionPane.YES_OPTION) {
                    	        	String check  = panel.deleteInvoice(invoiceID);
                             	   
                    	        	if(check.equals("Success")){
                    	        		clearPanel();
                    	        		}else{
                    	        			//do not close panel
                    	        			}
                    	        	
                    	        } else if (response == JOptionPane.CLOSED_OPTION) {
                    	        }
                    	   
                       }
                           //textField.requestFocusInWindow();
               }
        }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
    public void clearPanel() {
        setVisible(false);
    }


}

