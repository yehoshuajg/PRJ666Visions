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
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
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
import javax.swing.JCheckBox;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Box;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;



public class Inventory extends JPanel {

private DefaultTableModel MyModel = null;
private Connection con_Inv = null; //Shigemi

private JTextField add_prodID_tf;
private JTextField add_prodName_tf;
private JTextField add_prodDesc_tf;
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
static JTable tableInventory;

private JComboBox<CbSupItem> ord_Sup_cb = new JComboBox<CbSupItem>(); 

private TableColumnModel tcm;

private JScrollPane scp_inventoryList;
private static TableRowSorter<MyTableModelClass> sorter;

private static  InventoryFilterFrame filterFrame = new InventoryFilterFrame();
private static  JLabel jl_filter_status;
private JTable tableNewOrder;
private JTable tableNewOrderSummary;
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
private JTable tableProductSoldBy;
private JTable tablePriceHistory;

private JComboBox<CbSupItem> simpleSupList;

private DefaultStyledDocument doc;
private static JTable tableOrderList;
private static JTable tableOrderDetail;
private JTextField dataDeliveryCost;
private JTextField dataInvoiceNumber;
private final JPanel panel_3 = new JPanel();
private static JTable tableInvoice;
private static JScrollPane scrollP_Panel_Invoice_Center;
private JTable tableSetPrice;
private JTextField dataAdjustNumber;
private JTextField dataPercentage;
private JTextArea dataReason;
private JScrollPane scrollpane_7;
JPanel inventoryMainPanel;
JPanel panel_AddProduct;
JPanel panel_EditDetails;
JPanel panel_InventoryList;
JPanel panel_setPrice; 
JPanel panel_OrderList; 
JPanel panel_CreateOrder;
CardLayout cardlayout = new CardLayout();
CardLayout cardlayoutForFilter = new CardLayout();
static CardLayout cardlayoutForInvoice = new CardLayout();
static JPanel cardPanelInvoiceDetail;

int currentPage = 0;


private static JTextField dataSupInvoiceID;
private static JTextField dataAmountDue;
private static JTextField dataAmountPaid;
private static JTextField dataOrderID;
static JTable pickOrderTable;
static JDatePickerImpl datePicker;
private static JTextField dataSupplier;

private int invoiceID;
private static String amountDue; 
private static String amountPaid;
private static Date receivedDate; 
private static int supplierID;
private static String supplierName;
private static int supplierInvoiceID;
private static int orderCount = 0; 
private static JTextField dataSystemInvoiceID;

static JPanel panel_invoiceDetail;
private static JTextField dataAdditionalPayment;
private static JTable tableAssociatedOrders;
private JTextField dataQtyAdjustment;
private JTextField dataQtyAdjReason;
private JTable tableQtyHistory;

private JPanel panel_FilterCard;
private static JTextField dataOutstanding;




public Inventory() {
		setLayout(null);

setLayout(new BorderLayout(0, 0));
JPanel InventoryMainNorthPanel = new JPanel();
add(InventoryMainNorthPanel, BorderLayout.NORTH);
InventoryMainNorthPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
JMenuBar menuBar = new JMenuBar();
menuBar.setPreferredSize(new Dimension(450, 30));
menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 17));
menuBar.setMargin(new Insets(0, 0, 0, 10));
InventoryMainNorthPanel.add(menuBar);
JMenu menuInventory = new JMenu("Inventory");
menuInventory.setHorizontalAlignment(SwingConstants.CENTER);
menuInventory.setHorizontalTextPosition(SwingConstants.CENTER);
menuInventory.setPreferredSize(new Dimension(150, 24));
menuBar.add(menuInventory);
menuInventory.setBorder(new LineBorder(Color.LIGHT_GRAY));
menuInventory.setFont(new Font("Segoe UI", Font.PLAIN, 17));
menuInventory.setMargin(new Insets(0, 20, 0, 20));
JMenuItem menuInvRefresh = new JMenuItem( new AbstractAction("Display Inventory List"){

	@Override
	public void actionPerformed(ActionEvent e) {
		createTableInventory();
		cardlayout.show(inventoryMainPanel, "000");
		currentPage = 0; 
		
	}});
menuInvRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 17));

menuInventory.add(menuInvRefresh);
JMenuItem menuInvAdd = new JMenuItem( new AbstractAction("Add Product"){

	@Override
	public void actionPerformed(ActionEvent e) {
		cardlayout.show(inventoryMainPanel, "111");
		currentPage = 1; 
		
	}});
menuInvAdd.setFont(new Font("Segoe UI", Font.PLAIN, 17));
menuInventory.add(menuInvAdd);
JMenuItem menuInvEdit = new JMenuItem( new AbstractAction("Edit Product"){

	@Override
	public void actionPerformed(ActionEvent e) {
		int selectedID;
		if( currentPage != 0){
			cardlayout.show(inventoryMainPanel, "000");
			currentPage = 0;
			JOptionPane.showMessageDialog(null, "Please check mark one item and click \"Edit Product\" again");
			
		}else {
			try{
			Vector<Integer> selectedRows = inventoryGetSelectedRows();
			if (selectedRows.size() > 1){
				JOptionPane.showMessageDialog(null, "Please select only one item");
			}
			else {
				//jumpToEditPage(selectedRows.get(0).intValue());
				displayProductDetail(selectedRows.get(0).intValue());

			} // end else
		}catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Select product ID from list");
			e1.printStackTrace();
		}
		}

		
	}});
menuInvEdit.setFont(new Font("Segoe UI", Font.PLAIN, 17));

menuInventory.add(menuInvEdit);
JMenuItem menuInvSetPrice = new JMenuItem( new AbstractAction("Price Adjustment"){

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(currentPage != 0){
			cardlayout.show(inventoryMainPanel, "000");
			JOptionPane.showMessageDialog(null, "Please check mark product(s) and click \"Price Adjustment\" again");
		}else {
			createTableSetPrice();
		}
		
		
	}});
menuInvSetPrice.setFont(new Font("Segoe UI", Font.PLAIN, 17));

menuInventory.add(menuInvSetPrice);
JMenu menuOrder = new JMenu("Order");
menuOrder.setHorizontalAlignment(SwingConstants.CENTER);
menuOrder.setHorizontalTextPosition(SwingConstants.CENTER);
menuOrder.setPreferredSize(new Dimension(150, 24));
menuBar.add(menuOrder);
menuOrder.setFont(new Font("Segoe UI", Font.PLAIN, 17));
menuOrder.setBorder(new LineBorder(Color.LIGHT_GRAY));
menuOrder.setMargin(new Insets(0, 20, 0, 20));
JMenuItem menuOrdList = new JMenuItem( new AbstractAction("Display Order List"){

	@Override
	public void actionPerformed(ActionEvent e) {
		cardlayout.show(inventoryMainPanel, "444");
		currentPage = 4;
		
	}});
menuOrdList.setFont(new Font("Segoe UI", Font.PLAIN, 17));


menuOrder.add(menuOrdList);
JMenuItem menuOrdCreate = new JMenuItem( new AbstractAction("Create Order"){

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (currentPage != 0){
			cardlayout.show(inventoryMainPanel, "000");
			currentPage = 0;
			JOptionPane.showMessageDialog(null, "Please check mark product(s) and click \"Create Order\" again");
		} else {
					Vector<Integer> selectedRows = inventoryGetSelectedRows();
		if (selectedRows.size() == 0){
			JOptionPane.showMessageDialog(null, "Please check mark product(s) and click \"Create Order\" again");
		} else {
			
		StringBuilder selectedProductIDs = new StringBuilder();
		for (int i = 0; i < selectedRows.size()-1; i++)
			{
				selectedProductIDs.append(selectedRows.get(i).toString()).append(", ");
			}
		selectedProductIDs.append(selectedRows.get(selectedRows.size()-1).toString());
		System.out.println("Selected IDs are" + selectedProductIDs);
		createTableNewOrder(selectedProductIDs);
		//createOrderSummary(selectedProductIDs); placed inside createOrderTable();
		
		cardlayout.show(inventoryMainPanel, "555");
		currentPage = 5;
		//tabbedPane_Inventory.setEnabledAt(3,true);
		//tabbedPane_Inventory.setSelectedIndex(3);
		}

		}
		
	}});
menuOrdCreate.setFont(new Font("Segoe UI", Font.PLAIN, 17));


menuOrder.add(menuOrdCreate);
JMenu menuInvoice = new JMenu("Invoice");
menuInvoice.setHorizontalAlignment(SwingConstants.CENTER);
menuInvoice.setHorizontalTextPosition(SwingConstants.CENTER);
menuInvoice.setPreferredSize(new Dimension(150, 24));
menuBar.add(menuInvoice);
menuInvoice.setFont(new Font("Segoe UI", Font.PLAIN, 17));
menuInvoice.setBorder(new LineBorder(Color.LIGHT_GRAY));
menuInvoice.setMargin(new Insets(0, 20, 0, 20));
JMenuItem menuInvoiceList = new JMenuItem( new AbstractAction("Display Invoice List"){

	@Override
	public void actionPerformed(ActionEvent e) {
		cardlayout.show(inventoryMainPanel, "666");
		currentPage = 6;
		
	}});
menuInvoiceList.setFont(new Font("Segoe UI", Font.PLAIN, 17));


menuInvoice.add(menuInvoiceList);
JMenuItem menuAddInvoice = new JMenuItem( new AbstractAction("Add Invoice"){

	@Override
	public void actionPerformed(ActionEvent e) {

			cardlayout.show(inventoryMainPanel, "666");
			currentPage = 4;
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                new DialogAddNewInvoice(null).setVisible(true);
	            }
	        });
		
	}});
menuAddInvoice.setFont(new Font("Segoe UI", Font.PLAIN, 17));

menuInvoice.add(menuAddInvoice);

inventoryMainPanel = new JPanel();
add(inventoryMainPanel, BorderLayout.CENTER);
inventoryMainPanel.setLayout(cardlayout);

		panel_InventoryList = new JPanel();
		inventoryMainPanel.add(panel_InventoryList, "000");
		panel_InventoryList.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_InventoryList.add(panel_15, BorderLayout.NORTH);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_2.getLayout();
		flowLayout_7.setAlignment(FlowLayout.LEFT);
		panel_15.add(panel_2, BorderLayout.NORTH);
		
		JLabel lblNewLabel_6 = new JLabel("Inventory List");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.add(lblNewLabel_6);
		
		JPanel panel_InventoryList_North = new JPanel();
		panel_15.add(panel_InventoryList_North, BorderLayout.SOUTH);
		panel_InventoryList_North.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(20, 20));
		panel_InventoryList_North.add(verticalStrut);
		
		JCheckBox checkBoxInv = new JCheckBox("Check / uncheck All");
		checkBoxInv.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_InventoryList_North.add(checkBoxInv);
		
		checkBoxInv.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						
						AbstractButton absb = (AbstractButton)e.getSource();
						boolean check =  absb.getModel().isSelected();
						
						TableModel model = tableInventory.getModel();
						if(check){
							for(int i = 0; i < model.getRowCount(); i++){
								model.setValueAt(true, i, 0);
							}
						}else{
							for(int i = 0; i < model.getRowCount(); i++){
								model.setValueAt(false, i, 0);
							}
						}

					} // end of actionPerformed(ActionEvent e){
				}//end of new ActionListener(){
				);

		JButton btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 filterFrame.clearFilter();
			}
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
			

			
			//panel_FilterCard = new JPanel();
			//panel_15.add(panel_FilterCard, BorderLayout.CENTER);
			//panel_FilterCard.setLayout(cardlayoutForFilter);
			
			//JButton btnNewButton_4 = new JButton("Filter");
			//btnNewButton_4.addActionListener(new ActionListener() {
			//	public void actionPerformed(ActionEvent e) {
			//		panel_FilterCard.setPreferredSize(new Dimension(400,400));
			//		cardlayoutForFilter.show(panel_FilterCard, "Active");
			//	}
			//});
			//panel_InventoryList_North.add(btnNewButton_4);
			
			//JButton btnFilterInactive = new JButton("Remove Filter");
			//btnFilterInactive.addActionListener(new ActionListener() {
			//	public void actionPerformed(ActionEvent e) {
			//		panel_FilterCard.setPreferredSize(new Dimension(10,10));
			//		cardlayoutForFilter.show(panel_FilterCard, "Inactive");
			//	}
			//});
			//panel_InventoryList_North.add(btnFilterInactive);
			
			//JPanel panel_filterInactive = new JPanel();
			//panel_FilterCard.add(panel_filterInactive, "Inactive");
			
			//JPanel panel_filterActive = new JPanel();
			//panel_filterActive.setPreferredSize(new Dimension(10, 80));
			//FlowLayout fl_panel_filterActive = (FlowLayout) panel_filterActive.getLayout();
			//fl_panel_filterActive.setAlignment(FlowLayout.LEFT);
			//panel_FilterCard.add(panel_filterActive, "Active");
			
			//JLabel lblPanel = new JLabel("Panel 2");
			//lblPanel.setFont(new Font("Tahoma", Font.PLAIN, 18));
			//panel_filterActive.add(lblPanel);
			
			tableInventory = new JTable();
			tableInventory.setFont(new Font("Tahoma", Font.PLAIN, 15));
			tableInventory.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
			tableInventory.setRowHeight(30);
			//table_2.setBackground();

	   scp_inventoryList = new JScrollPane();
	   scp_inventoryList.setViewportView(tableInventory);
	   panel_InventoryList.add(scp_inventoryList);
	   
	   JButton btnNewButton_5 = new JButton("New button");
	   scp_inventoryList.setColumnHeaderView(btnNewButton_5);
	   panel_AddProduct = new JPanel();
	   inventoryMainPanel.add(panel_AddProduct, "111");
	   panel_AddProduct.setLayout(null);
	   
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
		
			panel_EditDetails = new JPanel();
			inventoryMainPanel.add(panel_EditDetails, "222");
			panel_EditDetails.setLayout(new BorderLayout(0, 0));
			
			JPanel jp_prodDetails = new JPanel();
			panel_EditDetails.add(jp_prodDetails, BorderLayout.WEST);
			jp_prodDetails.setLayout(null);
			jp_prodDetails.setPreferredSize(new Dimension(500, 600));
			
			JPanel jp_prodDetail = new JPanel();
			jp_prodDetail.setBounds(12, 13, 476, 596);
			jp_prodDetails.add(jp_prodDetail);
			GridBagLayout gbl_jp_prodDetail = new GridBagLayout();
			gbl_jp_prodDetail.columnWidths = new int[]{30, 0, 15, 142, 0, 0};
			gbl_jp_prodDetail.rowHeights = new int[]{10, 23, 0, 0, 0, 30, 0, 0, 0, 0, 80, 0, 20, 0, 0, 0, 0, 0};
			gbl_jp_prodDetail.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
			gbl_jp_prodDetail.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			jp_prodDetail.setLayout(gbl_jp_prodDetail);
			
			dataProdID = new JLabel("0");
			dataProdID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_dataProdID1 = new GridBagConstraints();
			gbc_dataProdID1.anchor = GridBagConstraints.WEST;
			gbc_dataProdID1.insets = new Insets(0, 0, 5, 5);
			gbc_dataProdID1.gridx = 3;
			gbc_dataProdID1.gridy = 1;
			jp_prodDetail.add(dataProdID, gbc_dataProdID1);
			
			dataCat = new JTextArea((String) null);
			dataCat.setBackground(SystemColor.control);
			dataCat.setLineWrap(true);
			dataCat.setEditable(false);
			dataCat.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_dataCat1 = new GridBagConstraints();
			gbc_dataCat1.fill = GridBagConstraints.BOTH;
			gbc_dataCat1.anchor = GridBagConstraints.WEST;
			gbc_dataCat1.insets = new Insets(0, 0, 5, 5);
			gbc_dataCat1.gridx = 3;
			gbc_dataCat1.gridy = 2;
			jp_prodDetail.add(dataCat, gbc_dataCat1);
			
			dataSubCat = new JTextArea((String) null);
			dataSubCat.setBackground(SystemColor.control);
			dataSubCat.setEditable(false);
			dataSubCat.setLineWrap(true);
			dataSubCat.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_dataSubCat1 = new GridBagConstraints();
			gbc_dataSubCat1.fill = GridBagConstraints.BOTH;
			gbc_dataSubCat1.anchor = GridBagConstraints.NORTHWEST;
			gbc_dataSubCat1.insets = new Insets(0, 0, 5, 5);
			gbc_dataSubCat1.gridx = 3;
			gbc_dataSubCat1.gridy = 3;
			jp_prodDetail.add(dataSubCat, gbc_dataSubCat1);
			
			dataName = new JTextArea((String) null);
			dataName.setEditable(false);
			dataName.setLineWrap(true);
			dataName.setBackground(SystemColor.control);
			dataName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_dataName1 = new GridBagConstraints();
			gbc_dataName1.fill = GridBagConstraints.BOTH;
			gbc_dataName1.anchor = GridBagConstraints.NORTHWEST;
			gbc_dataName1.insets = new Insets(0, 0, 5, 5);
			gbc_dataName1.gridx = 3;
			gbc_dataName1.gridy = 4;
			jp_prodDetail.add(dataName, gbc_dataName1);
			
			dataDesc = new JTextArea((String) null);
			dataDesc.setBackground(SystemColor.control);
			dataDesc.setLineWrap(true);
			dataDesc.setEditable(false);
			dataDesc.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_dataDesc1 = new GridBagConstraints();
			gbc_dataDesc1.fill = GridBagConstraints.BOTH;
			gbc_dataDesc1.anchor = GridBagConstraints.WEST;
			gbc_dataDesc1.insets = new Insets(0, 0, 5, 5);
			gbc_dataDesc1.gridx = 3;
			gbc_dataDesc1.gridy = 5;
			jp_prodDetail.add(dataDesc, gbc_dataDesc1);
			
				JLabel label_13 = new JLabel("Description :");
				label_13.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GridBagConstraints gbc_label_13 = new GridBagConstraints();
				gbc_label_13.anchor = GridBagConstraints.NORTHEAST;
				gbc_label_13.insets = new Insets(0, 0, 5, 5);
				gbc_label_13.gridx = 1;
				gbc_label_13.gridy = 5;
				jp_prodDetail.add(label_13, gbc_label_13);
				
				dataQty = new JLabel("0");
				dataQty.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GridBagConstraints gbc_dataQty1 = new GridBagConstraints();
				gbc_dataQty1.anchor = GridBagConstraints.WEST;
				gbc_dataQty1.insets = new Insets(0, 0, 5, 5);
				gbc_dataQty1.gridx = 3;
				gbc_dataQty1.gridy = 6;
				jp_prodDetail.add(dataQty, gbc_dataQty1);
				
				dataToBe = new JLabel("<dynamic>");
				dataToBe.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GridBagConstraints gbc_dataToBe = new GridBagConstraints();
				gbc_dataToBe.anchor = GridBagConstraints.WEST;
				gbc_dataToBe.insets = new Insets(0, 0, 5, 5);
				gbc_dataToBe.gridx = 3;
				gbc_dataToBe.gridy = 7;
				jp_prodDetail.add(dataToBe, gbc_dataToBe);
				
				dataSalePrice = new JLabel("0.0");
				dataSalePrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GridBagConstraints gbc_dataSalePrice = new GridBagConstraints();
				gbc_dataSalePrice.anchor = GridBagConstraints.WEST;
				gbc_dataSalePrice.insets = new Insets(0, 0, 5, 5);
				gbc_dataSalePrice.gridx = 3;
				gbc_dataSalePrice.gridy = 8;
				jp_prodDetail.add(dataSalePrice, gbc_dataSalePrice);
				
				JLabel label_9 = new JLabel("Product ID :");
				label_9.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GridBagConstraints gbc_label_9 = new GridBagConstraints();
				gbc_label_9.anchor = GridBagConstraints.EAST;
				gbc_label_9.insets = new Insets(0, 0, 5, 5);
				gbc_label_9.gridx = 1;
				gbc_label_9.gridy = 1;
				jp_prodDetail.add(label_9, gbc_label_9);
				
				JLabel label_10 = new JLabel("Category :");
				label_10.setFont(new Font("Tahoma", Font.PLAIN, 15));
				GridBagConstraints gbc_label_10 = new GridBagConstraints();
				gbc_label_10.anchor = GridBagConstraints.EAST;
				gbc_label_10.insets = new Insets(0, 0, 5, 5);
				gbc_label_10.gridx = 1;
				gbc_label_10.gridy = 2;
				jp_prodDetail.add(label_10, gbc_label_10);
				
					JLabel label_11 = new JLabel("Sub Category :");
					label_11.setFont(new Font("Tahoma", Font.PLAIN, 15));
					GridBagConstraints gbc_label_11 = new GridBagConstraints();
					gbc_label_11.anchor = GridBagConstraints.EAST;
					gbc_label_11.insets = new Insets(0, 0, 5, 5);
					gbc_label_11.gridx = 1;
					gbc_label_11.gridy = 3;
					jp_prodDetail.add(label_11, gbc_label_11);
					
					JLabel label_12 = new JLabel("Name :");
					label_12.setFont(new Font("Tahoma", Font.PLAIN, 15));
					GridBagConstraints gbc_label_12 = new GridBagConstraints();
					gbc_label_12.anchor = GridBagConstraints.EAST;
					gbc_label_12.insets = new Insets(0, 0, 5, 5);
					gbc_label_12.gridx = 1;
					gbc_label_12.gridy = 4;
					jp_prodDetail.add(label_12, gbc_label_12);
					
					JLabel label_14 = new JLabel("Quantity on Hand :");
					label_14.setFont(new Font("Tahoma", Font.PLAIN, 15));
					GridBagConstraints gbc_label_14 = new GridBagConstraints();
					gbc_label_14.anchor = GridBagConstraints.EAST;
					gbc_label_14.insets = new Insets(0, 0, 5, 5);
					gbc_label_14.gridx = 1;
					gbc_label_14.gridy = 6;
					jp_prodDetail.add(label_14, gbc_label_14);
					
					JLabel label_15 = new JLabel("To be delivered :");
					label_15.setFont(new Font("Tahoma", Font.PLAIN, 15));
					GridBagConstraints gbc_label_15 = new GridBagConstraints();
					gbc_label_15.anchor = GridBagConstraints.EAST;
					gbc_label_15.insets = new Insets(0, 0, 5, 5);
					gbc_label_15.gridx = 1;
					gbc_label_15.gridy = 7;
					jp_prodDetail.add(label_15, gbc_label_15);
					
						JLabel label_16 = new JLabel("Sale Price :");
						label_16.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_label_16 = new GridBagConstraints();
						gbc_label_16.anchor = GridBagConstraints.EAST;
						gbc_label_16.insets = new Insets(0, 0, 5, 5);
						gbc_label_16.gridx = 1;
						gbc_label_16.gridy = 8;
						jp_prodDetail.add(label_16, gbc_label_16);
						

						
						JLabel label_18 = new JLabel("Unit Cost :");
						label_18.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_label_18 = new GridBagConstraints();
						gbc_label_18.anchor = GridBagConstraints.EAST;
						gbc_label_18.insets = new Insets(0, 0, 5, 5);
						gbc_label_18.gridx = 1;
						gbc_label_18.gridy = 9;
						jp_prodDetail.add(label_18, gbc_label_18);
						
						dataUnitCost = new JLabel("0.0");
						dataUnitCost.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_dataUnitCost = new GridBagConstraints();
						gbc_dataUnitCost.insets = new Insets(0, 0, 5, 5);
						gbc_dataUnitCost.anchor = GridBagConstraints.WEST;
						gbc_dataUnitCost.gridx = 3;
						gbc_dataUnitCost.gridy = 9;
						jp_prodDetail.add(dataUnitCost, gbc_dataUnitCost);
						
						JLabel lblNote_1 = new JLabel("Note:");
						lblNote_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_lblNote_1 = new GridBagConstraints();
						gbc_lblNote_1.anchor = GridBagConstraints.NORTHEAST;
						gbc_lblNote_1.insets = new Insets(0, 0, 5, 5);
						gbc_lblNote_1.gridx = 1;
						gbc_lblNote_1.gridy = 10;
						jp_prodDetail.add(lblNote_1, gbc_lblNote_1);
						
						JScrollPane scrollPane_4 = new JScrollPane();
						GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
						gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
						gbc_scrollPane_4.insets = new Insets(0, 0, 5, 5);
						gbc_scrollPane_4.gridx = 3;
						gbc_scrollPane_4.gridy = 10;
						jp_prodDetail.add(scrollPane_4, gbc_scrollPane_4);
						
						Border border = BorderFactory.createLineBorder(Color.gray);
						
						dataNote = new JTextArea();
						scrollPane_4.setViewportView(dataNote);
						dataNote.setText((String) null);
						dataNote.setLineWrap(true);
						dataNote.setBorder(border);
						
						JButton btnUpdateNote = new JButton("Update Note");
						GridBagConstraints gbc_btnUpdateNote = new GridBagConstraints();
						gbc_btnUpdateNote.insets = new Insets(0, 0, 5, 5);
						gbc_btnUpdateNote.anchor = GridBagConstraints.NORTHWEST;
						gbc_btnUpdateNote.gridx = 3;
						gbc_btnUpdateNote.gridy = 11;
						jp_prodDetail.add(btnUpdateNote, gbc_btnUpdateNote);
						btnUpdateNote.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								updateNote();
							}
						});
						
						JLabel lblUpdateQtyOn = new JLabel("Update Qty On Hand");
						lblUpdateQtyOn.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_lblUpdateQtyOn = new GridBagConstraints();
						gbc_lblUpdateQtyOn.anchor = GridBagConstraints.EAST;
						gbc_lblUpdateQtyOn.insets = new Insets(0, 0, 5, 5);
						gbc_lblUpdateQtyOn.gridx = 1;
						gbc_lblUpdateQtyOn.gridy = 13;
						jp_prodDetail.add(lblUpdateQtyOn, gbc_lblUpdateQtyOn);
						
						JLabel lblAdjustment = new JLabel("Adjustment Qty:");
						lblAdjustment.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_lblAdjustment = new GridBagConstraints();
						gbc_lblAdjustment.anchor = GridBagConstraints.EAST;
						gbc_lblAdjustment.insets = new Insets(0, 0, 5, 5);
						gbc_lblAdjustment.gridx = 1;
						gbc_lblAdjustment.gridy = 14;
						jp_prodDetail.add(lblAdjustment, gbc_lblAdjustment);
						
						dataQtyAdjustment = new JTextField();
						GridBagConstraints gbc_dataQtyAdjustment = new GridBagConstraints();
						gbc_dataQtyAdjustment.anchor = GridBagConstraints.WEST;
						gbc_dataQtyAdjustment.insets = new Insets(0, 0, 5, 5);
						gbc_dataQtyAdjustment.fill = GridBagConstraints.VERTICAL;
						gbc_dataQtyAdjustment.gridx = 3;
						gbc_dataQtyAdjustment.gridy = 14;
						jp_prodDetail.add(dataQtyAdjustment, gbc_dataQtyAdjustment);
						dataQtyAdjustment.setColumns(10);
						
						JLabel lblReason_1 = new JLabel("Reason:");
						lblReason_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
						GridBagConstraints gbc_lblReason_1 = new GridBagConstraints();
						gbc_lblReason_1.anchor = GridBagConstraints.EAST;
						gbc_lblReason_1.insets = new Insets(0, 0, 5, 5);
						gbc_lblReason_1.gridx = 1;
						gbc_lblReason_1.gridy = 15;
						jp_prodDetail.add(lblReason_1, gbc_lblReason_1);
						
						dataQtyAdjReason = new JTextField();
						GridBagConstraints gbc_dataQtyAdjReason = new GridBagConstraints();
						gbc_dataQtyAdjReason.anchor = GridBagConstraints.WEST;
						gbc_dataQtyAdjReason.insets = new Insets(0, 0, 5, 5);
						gbc_dataQtyAdjReason.fill = GridBagConstraints.VERTICAL;
						gbc_dataQtyAdjReason.gridx = 3;
						gbc_dataQtyAdjReason.gridy = 15;
						jp_prodDetail.add(dataQtyAdjReason, gbc_dataQtyAdjReason);
						dataQtyAdjReason.setColumns(20);
						
						JButton btnNewButton_3 = new JButton("Update Quantity");
						btnNewButton_3.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								
								updateQty();
								
							}
						});
						GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
						gbc_btnNewButton_3.insets = new Insets(0, 0, 0, 5);
						gbc_btnNewButton_3.fill = GridBagConstraints.VERTICAL;
						gbc_btnNewButton_3.anchor = GridBagConstraints.WEST;
						gbc_btnNewButton_3.gridx = 3;
						gbc_btnNewButton_3.gridy = 16;
						jp_prodDetail.add(btnNewButton_3, gbc_btnNewButton_3);
						
						JPanel jp_editDetailCenter = new JPanel();
						panel_EditDetails.add(jp_editDetailCenter, BorderLayout.CENTER);
						jp_editDetailCenter.setLayout(new BorderLayout(0, 0));
						
						JPanel jp_priceHistory = new JPanel();
						jp_priceHistory.setMinimumSize(new Dimension(200, 200));
						jp_priceHistory.setLayout(new BorderLayout(5, 5));
						
						JPanel jp_qtyHistory = new JPanel();
						jp_qtyHistory.setMinimumSize(new Dimension(200, 200));
						jp_qtyHistory.setLayout(new BorderLayout(5, 5));
						
						JSplitPane splitPane_history = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jp_priceHistory, jp_qtyHistory );
						
						JPanel panel = new JPanel();
						FlowLayout flowLayout_9 = (FlowLayout) panel.getLayout();
						flowLayout_9.setAlignment(FlowLayout.LEFT);
						jp_qtyHistory.add(panel, BorderLayout.NORTH);
						
						JLabel lblQuantityChangeHistory = new JLabel("Quantity Change History :");
						lblQuantityChangeHistory.setFont(new Font("Tahoma", Font.PLAIN, 15));
						panel.add(lblQuantityChangeHistory);
						
						JScrollPane scrollPane_8 = new JScrollPane();
						jp_qtyHistory.add(scrollPane_8, BorderLayout.CENTER);
						
						tableQtyHistory = new JTable();
						scrollPane_8.setViewportView(tableQtyHistory);
						splitPane_history.setOneTouchExpandable(true);
						splitPane_history.setDividerLocation(300);
						splitPane_history.setDividerSize(10);
						jp_editDetailCenter.add(splitPane_history, BorderLayout.CENTER);
						
						JPanel panel_10 = new JPanel();
						FlowLayout flowLayout_6 = (FlowLayout) panel_10.getLayout();
						flowLayout_6.setAlignment(FlowLayout.LEFT);
						jp_priceHistory.add(panel_10, BorderLayout.NORTH);
						
						JLabel lblPriceChangeHistory = new JLabel("Price Change History :");
						panel_10.add(lblPriceChangeHistory);
						lblPriceChangeHistory.setFont(new Font("Tahoma", Font.PLAIN, 15));
						
						JScrollPane scrollPane_1 = new JScrollPane();
						jp_priceHistory.add(scrollPane_1, BorderLayout.CENTER);
						
						tablePriceHistory = new JTable();
						tablePriceHistory.setFont(new Font("Tahoma", Font.PLAIN, 15));
						tablePriceHistory.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
						scrollPane_1.setViewportView(tablePriceHistory);
						
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
						panel_9.setPreferredSize(new Dimension(10, 70));
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
						
						tableProductSoldBy = new JTable();
						tableProductSoldBy.setFont(new Font("Tahoma", Font.PLAIN, 15));
						tableProductSoldBy.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
						scrollPane.setViewportView(tableProductSoldBy);
		
		JPanel jp_EditDetailNorth = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) jp_EditDetailNorth.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEFT);
		panel_EditDetails.add(jp_EditDetailNorth, BorderLayout.NORTH);
		
		// ================ labels =================
		JLabel lblProductDetails = new JLabel("Product Details");
		jp_EditDetailNorth.add(lblProductDetails);
		lblProductDetails.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(50, 20));
		jp_EditDetailNorth.add(verticalStrut_1);
		
		JButton btnPrev = new JButton("< Previous");
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int curProdID = Integer.parseInt(dataProdID.getText());
				try{
				Connection con = Connect.connectionSetup();
				String query = "Select ID from Product "
						+ "WHERE ID = (Select max(ID) FROM Product WHERE ID < " + curProdID + ")";
				PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				 while(rs.next()){
					 displayProductDetail(rs.getInt(1));
				 }

				}catch (Exception e2){
					e2.printStackTrace();
				};
				
			}
		});
		
		
		jp_EditDetailNorth.add(btnPrev);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int curProdID = Integer.parseInt(dataProdID.getText());
				try{
				Connection con = Connect.connectionSetup();
				String query = "Select ID FROM Product "
						+ "WHERE ID = (Select min(ID) FROM Product WHERE ID > " + curProdID + ")";
				PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				 while(rs.next()){
					 displayProductDetail(rs.getInt(1));
				 }

				}catch (Exception e2){
					e2.printStackTrace();
				}
				
			}
		});
		jp_EditDetailNorth.add(btnNext);
		
		JButton btnBackToInventory = new JButton("Back to Inventory");
		jp_EditDetailNorth.add(btnBackToInventory);
		btnBackToInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardlayout.show(inventoryMainPanel, "000");
				currentPage = 0;
				//tabbedPane_Inventory.setSelectedIndex(0);

			}
		});
		btnBackToInventory.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel_setPrice = new JPanel();
		panel_setPrice.setLayout(new BorderLayout(0, 0));
		inventoryMainPanel.add(panel_setPrice, "333");
		
		scrollpane_7 = new JScrollPane();
		panel_setPrice.add(scrollpane_7, BorderLayout.CENTER);
		
		JPanel panel_13 = new JPanel();
		panel_setPrice.add(panel_13, BorderLayout.NORTH);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_21 = new JPanel();
		panel_13.add(panel_21, BorderLayout.EAST);
		panel_21.setPreferredSize(new Dimension(500, 90));
		GridBagLayout gbl_panel_21 = new GridBagLayout();
		gbl_panel_21.columnWidths = new int[]{10, 50, 50, 120, 0, 150, 0, 0};
		gbl_panel_21.rowHeights = new int[]{30, 30, 30, 0};
		gbl_panel_21.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_21.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_21.setLayout(gbl_panel_21);
		
		JLabel label = new JLabel("%");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel_21.add(label, gbc_label);
		
		JLabel lblNumber = new JLabel("Number");
		GridBagConstraints gbc_lblNumber = new GridBagConstraints();
		gbc_lblNumber.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumber.gridx = 2;
		gbc_lblNumber.gridy = 0;
		panel_21.add(lblNumber, gbc_lblNumber);
		
		JLabel lblReason = new JLabel("Reason");
		GridBagConstraints gbc_lblReason = new GridBagConstraints();
		gbc_lblReason.insets = new Insets(0, 0, 5, 5);
		gbc_lblReason.gridx = 3;
		gbc_lblReason.gridy = 0;
		panel_21.add(lblReason, gbc_lblReason);
		lblReason.setPreferredSize(new Dimension(60, 22));
		
		dataPercentage = new JTextField();
		GridBagConstraints gbc_dataPercentage = new GridBagConstraints();
		gbc_dataPercentage.fill = GridBagConstraints.BOTH;
		gbc_dataPercentage.insets = new Insets(0, 0, 5, 5);
		gbc_dataPercentage.gridx = 1;
		gbc_dataPercentage.gridy = 1;
		panel_21.add(dataPercentage, gbc_dataPercentage);
		dataPercentage.setColumns(5);
		
		dataAdjustNumber = new JTextField();
		GridBagConstraints gbc_dataAdjustNumber = new GridBagConstraints();
		gbc_dataAdjustNumber.fill = GridBagConstraints.BOTH;
		gbc_dataAdjustNumber.insets = new Insets(0, 0, 5, 5);
		gbc_dataAdjustNumber.gridx = 2;
		gbc_dataAdjustNumber.gridy = 1;
		panel_21.add(dataAdjustNumber, gbc_dataAdjustNumber);
		dataAdjustNumber.setColumns(5);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_6 = new GridBagConstraints();
		gbc_scrollPane_6.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_6.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_6.gridx = 3;
		gbc_scrollPane_6.gridy = 1;
		panel_21.add(scrollPane_6, gbc_scrollPane_6);
		scrollPane_6.setPreferredSize(new Dimension(150, 22));
		scrollPane_6.setAutoscrolls(true);
		
		dataReason = new JTextArea();
		dataReason.setColumns(15);
		scrollPane_6.setViewportView(dataReason);
		dataReason.setLineWrap(true);
		dataReason.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		
		JButton btnOverridePercentage = new JButton("Override");
		btnOverridePercentage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overridePercentage();
			}
		});
		GridBagConstraints gbc_btnOverridePercentage = new GridBagConstraints();
		gbc_btnOverridePercentage.insets = new Insets(0, 0, 0, 5);
		gbc_btnOverridePercentage.gridx = 1;
		gbc_btnOverridePercentage.gridy = 2;
		panel_21.add(btnOverridePercentage, gbc_btnOverridePercentage);
		btnOverridePercentage.setPreferredSize(new Dimension(85, 25));
		
		JButton btnOverrideNumber = new JButton("Override");
		btnOverrideNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overridePriceAdjustment();
			}
		});
		GridBagConstraints gbc_btnOverrideNumber = new GridBagConstraints();
		gbc_btnOverrideNumber.insets = new Insets(0, 0, 0, 5);
		gbc_btnOverrideNumber.gridx = 2;
		gbc_btnOverrideNumber.gridy = 2;
		panel_21.add(btnOverrideNumber, gbc_btnOverrideNumber);
		
		JButton btnOverrideReason = new JButton("Override");
		btnOverrideReason.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overrideReason();
			}
		});
		GridBagConstraints gbc_btnOverrideReason = new GridBagConstraints();
		gbc_btnOverrideReason.insets = new Insets(0, 0, 0, 5);
		gbc_btnOverrideReason.gridx = 3;
		gbc_btnOverrideReason.gridy = 2;
		panel_21.add(btnOverrideReason, gbc_btnOverrideReason);
		
		JButton btnUpdatePrice2 = new JButton("Update Sale Price");
		btnUpdatePrice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSalePrice();
			}
		});
		GridBagConstraints gbc_btnUpdatePrice2 = new GridBagConstraints();
		gbc_btnUpdatePrice2.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdatePrice2.gridx = 5;
		gbc_btnUpdatePrice2.gridy = 2;
		panel_21.add(btnUpdatePrice2, gbc_btnUpdatePrice2);
		
		JPanel panel_14 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_14.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		panel_14.setPreferredSize(new Dimension(280, 80));
		panel_13.add(panel_14, BorderLayout.WEST);
		
		JLabel lblNewLabel_3 = new JLabel("Price Adjustment");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setVerticalAlignment(SwingConstants.TOP);
		panel_14.add(lblNewLabel_3);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setPreferredSize(new Dimension(200, 50));
		
		JCheckBox checkAll = new JCheckBox("Check / uncheck All");
		panel_14.add(checkAll);
		checkAll.setVerticalAlignment(SwingConstants.BOTTOM);
		
		JButton btnNewButton_2 = new JButton("Reset");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model = (DefaultTableModel) tableSetPrice.getModel();
				for(int i = 0; i < model.getRowCount(); i++){
					model.setValueAt(true, i, 0);
					model.setValueAt("", i, 8);
					model.setValueAt("", i, 9);
					model.setValueAt("", i, 10);
					model.setValueAt("", i, 11);
				}
				dataPercentage.setText("");
				dataAdjustNumber.setText("");
				dataReason.setText("");
				
			}
		});
		panel_14.add(btnNewButton_2);
		
		JButton btnHelp = new JButton("Help");
		panel_14.add(btnHelp);
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "Please enter % adjustment or price adjustment in the table\n"
						+ "i.e. to increase by 10%, enter 10 under \"% Adjustment\" column \n"
						+ "to increase by $1.5, enter 1.5 in \"Price Adjustment\" column \n"
						+ "Entering negative number reduce sale price \n"
						+ "% adjustment or price adjustment can be applied to multiple rows using \"override\" button\n"
						+ "override will be applied only to those rows with check mark\n"
						+ "Sale price to be updated, new price must be set in \"New Sale Price\" column\n"
						+ "To update, click \"Update Sale Price button \"");
			}
		});
		
		//--actoin listner 
		checkAll.addActionListener(
				new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						
						AbstractButton absb = (AbstractButton)e.getSource();
						boolean check =  absb.getModel().isSelected();
						
						TableModel model = tableSetPrice.getModel();
						if(check){
							for(int i = 0; i < model.getRowCount(); i++){
								model.setValueAt(true, i, 0);
							}
						}else{
							for(int i = 0; i < model.getRowCount(); i++){
								model.setValueAt(false, i, 0);
							}
						}

					} // end of actionPerformed(ActionEvent e){
				}//end of new ActionListener(){
				);
		
		panel_OrderList = new JPanel();
		inventoryMainPanel.add(panel_OrderList, "444");
		panel_OrderList.setLayout(new BorderLayout(0, 0));
		
		JPanel jpOrderListNorth = new JPanel();
		FlowLayout flowLayout = (FlowLayout) jpOrderListNorth.getLayout();
		panel_OrderList.add(jpOrderListNorth, BorderLayout.NORTH);
		jpOrderListNorth.setPreferredSize(new Dimension(50, 20));
		
			//----
			JPanel jpOrderListWest = new JPanel();
			jpOrderListWest.setPreferredSize(new Dimension(450, 10));
			//panel_OrderList.add(jpOrderListWest, BorderLayout.WEST);
			jpOrderListWest.setLayout(new BorderLayout(5, 5));
			
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
			
			tableOrderList = new JTable();
			tableOrderList.setFont(new Font("Tahoma", Font.PLAIN, 15));
			tableOrderList.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
			scrollPane_2.setViewportView(tableOrderList);
			
			JPanel jpOrderListCenter = new JPanel();
			//panel_OrderList.add(jpOrderListCenter, BorderLayout.CENTER);
			jpOrderListCenter.setLayout(new BorderLayout(5, 5));
			
			
			JSplitPane splitPane_orderList = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jpOrderListWest, jpOrderListCenter );
			splitPane_orderList.setOneTouchExpandable(true);
			splitPane_orderList.setDividerLocation(700);
			splitPane_orderList.setDividerSize(10);
			panel_OrderList.add(splitPane_orderList, BorderLayout.CENTER);
			
			
			
			
			JPanel panel_4 = new JPanel();
			jpOrderListCenter.add(panel_4, BorderLayout.NORTH);
			panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
			
			
			
			JPanel panel_11 = new JPanel();
			panel_11.setAlignmentY(Component.BOTTOM_ALIGNMENT);
			panel_4.add(panel_11);
			panel_11.setPreferredSize(new Dimension(320, 50));
			GridBagLayout gbl_panel_11 = new GridBagLayout();
			gbl_panel_11.columnWidths = new int[]{20, 84, 58, 50, 20, 30, 0, 0, 0};
			gbl_panel_11.rowHeights = new int[]{25, 20, 0};
			gbl_panel_11.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_11.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			panel_11.setLayout(gbl_panel_11);
			
			JLabel lblOrderDetails = new JLabel("Order Details");
			GridBagConstraints gbc_lblOrderDetails = new GridBagConstraints();
			gbc_lblOrderDetails.anchor = GridBagConstraints.SOUTHWEST;
			gbc_lblOrderDetails.insets = new Insets(0, 0, 5, 5);
			gbc_lblOrderDetails.gridx = 1;
			gbc_lblOrderDetails.gridy = 0;
			panel_11.add(lblOrderDetails, gbc_lblOrderDetails);
			lblOrderDetails.setAlignmentY(Component.BOTTOM_ALIGNMENT);
			lblOrderDetails.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lblNewLabel_1 = new JLabel("Other Cost");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_1.gridx = 2;
			gbc_lblNewLabel_1.gridy = 1;
			panel_11.add(lblNewLabel_1, gbc_lblNewLabel_1);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			//dataInvoiceNumber = new JTextField();
			//GridBagConstraints gbc_dataInvoiceNumber = new GridBagConstraints();
			//gbc_dataInvoiceNumber.fill = GridBagConstraints.BOTH;
			//gbc_dataInvoiceNumber.insets = new Insets(0, 0, 5, 5);
			//gbc_dataInvoiceNumber.gridx = 3;
			//gbc_dataInvoiceNumber.gridy = 2;
			//panel_11.add(dataInvoiceNumber, gbc_dataInvoiceNumber);
			//dataInvoiceNumber.setColumns(7);
			
			dataDeliveryCost = new JTextField();
			GridBagConstraints gbc_dataDeliveryCost = new GridBagConstraints();
			gbc_dataDeliveryCost.fill = GridBagConstraints.BOTH;
			gbc_dataDeliveryCost.insets = new Insets(0, 0, 0, 5);
			gbc_dataDeliveryCost.gridx = 3;
			gbc_dataDeliveryCost.gridy = 1;
			panel_11.add(dataDeliveryCost, gbc_dataDeliveryCost);
			dataDeliveryCost.setColumns(7);
			dataDeliveryCost.setPreferredSize(new Dimension(40, 25));
			
			//JButton btnUpdate_2 = new JButton("Update #");
			//GridBagConstraints gbc_btnUpdate_2 = new GridBagConstraints();
			//gbc_btnUpdate_2.fill = GridBagConstraints.BOTH;
			//gbc_btnUpdate_2.insets = new Insets(0, 0, 0, 5);
			//gbc_btnUpdate_2.gridx = 3;
			//gbc_btnUpdate_2.gridy = 3;
			//panel_11.add(btnUpdate_2, gbc_btnUpdate_2);
			//btnUpdate_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
			//btnUpdate_2.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			JButton btnNewButton = new JButton("Distribute");
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.fill = GridBagConstraints.BOTH;
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = 4;
			gbc_btnNewButton.gridy = 1;
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
			gbc_btnUpdate_1.gridx = 6;
			gbc_btnUpdate_1.gridy = 1;
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
			//btnUpdate_2.addActionListener(new ActionListener() {
			//	public void actionPerformed(ActionEvent e) {
			//		
			//		updateInvoiceID();
			//	}
			//});
			
			JScrollPane scrollPane_3 = new JScrollPane();
			jpOrderListCenter.add(scrollPane_3, BorderLayout.CENTER);
			
			tableOrderDetail = new JTable();
			tableOrderDetail.setFont(new Font("Tahoma", Font.PLAIN, 15));
			tableOrderDetail.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
			scrollPane_3.setViewportView(tableOrderDetail);
						
		

		
		

		
		///der page =======================================================
		// ========================================================================================
		
		panel_CreateOrder = new JPanel();
		inventoryMainPanel.add(panel_CreateOrder, "555");
		
		panel_CreateOrder.setLayout(new BorderLayout(0, 0));
		
		
		JPanel jp_createOrder_north = new JPanel();
		panel_CreateOrder.add(jp_createOrder_north, BorderLayout.NORTH);
		FlowLayout fl_jp_createOrder_north = (FlowLayout) jp_createOrder_north.getLayout();
		fl_jp_createOrder_north.setAlignment(FlowLayout.LEFT);
		
		JButton btn_backToInventory = new JButton("Back to Inventory");
		btn_backToInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cardlayout.show(inventoryMainPanel, "000");
				currentPage = 0;
				//tabbedPane_Inventory.setSelectedIndex(0); 
			}
		});
		
		JLabel lblNewLabel_5 = new JLabel("Create Order");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jp_createOrder_north.add(lblNewLabel_5);
		jp_createOrder_north.add(btn_backToInventory);
		
		
		JPanel jp_createOrderSummary = new JPanel();
		//panel_CreateOrder.add(jp_createOrderSummary, BorderLayout.EAST);
		jp_createOrderSummary.setLayout(new BorderLayout(0, 0));
		jp_createOrderSummary.setPreferredSize(new Dimension(300, 100));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		jp_createOrderSummary.add(panel_1, BorderLayout.NORTH);
		panel_1.setPreferredSize(new Dimension(100, 70));
		
		JButton btn_createOrder = new JButton("Create Order");
		btn_createOrder.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(btn_createOrder);
		btn_createOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			//stop editing otherwise null may be assigend to order quanitity
				if (tableNewOrder != null ){
								if(tableNewOrder.isEditing()){
					tableNewOrder.getCellEditor().stopCellEditing();
								}
					
					createOrderAndOrderDetail();
				}
				
			} //end of action performed
		});
		
		JLabel lblNewLabel = new JLabel("Orders less than minimum order may be rejected");
		panel_1.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		jp_createOrderSummary.add(scrollPane_createOrderSummary);
		scrollPane_createOrderSummary.setViewportView(tableNewOrderSummary);
		
		scrollPane_createOrderSummary.setPreferredSize(new Dimension(200,200));
		
		JPanel jp_createOrder = new JPanel();
		//panel_CreateOrder.add(jp_createOrder, BorderLayout.CENTER);
		jp_createOrder.setLayout(new BorderLayout(5, 5));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jp_createOrder, jp_createOrderSummary );
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(900);
		splitPane.setDividerSize(10);
		panel_CreateOrder.add(splitPane, BorderLayout.CENTER);
		jp_createOrder.add(scrollPane_ceateOrder);
		
		JPanel panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(10, 70));
		scrollPane_ceateOrder.setColumnHeaderView(panel_5);
		jp_createOrder.setMinimumSize(new Dimension(100, 700));
			
			JPanel panel_Invoice = new JPanel();
			inventoryMainPanel.add(panel_Invoice, "666");
			panel_Invoice.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_Invoice_North = new JPanel();
			FlowLayout fl_panel_Invoice_North = (FlowLayout) panel_Invoice_North.getLayout();
			fl_panel_Invoice_North.setAlignment(FlowLayout.LEFT);
			panel_Invoice.add(panel_Invoice_North, BorderLayout.NORTH);
			
			JLabel lblInvoiceList = new JLabel("Invoice List");
			lblInvoiceList.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_Invoice_North.add(lblInvoiceList);
			
			JLabel lblNewLabel_4 = new JLabel("");
			panel_Invoice_North.add(lblNewLabel_4);
			
			
			
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
			scrollP_Panel_Invoice_Center.setMinimumSize(new Dimension(400, 500));
			scrollP_Panel_Invoice_Center.setPreferredSize(new Dimension(800, 500));
			//panel_Invoice.add(panel, BorderLayout.SOUTH);
			
			cardPanelInvoiceDetail = new JPanel();
			//panel_Invoice.add(cardPanelInvoiceDetail, BorderLayout.SOUTH);
			cardPanelInvoiceDetail.setLayout(cardlayoutForInvoice);
			
			JPanel panel_invoiceDetailDefault = new JPanel();
			cardPanelInvoiceDetail.add(panel_invoiceDetailDefault, "invoice01");
			
			JLabel lblNewLabel_7 = new JLabel("Double click an invoice record to display details");
			lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNewLabel_7.setHorizontalAlignment(SwingConstants.LEFT);
			panel_invoiceDetailDefault.add(lblNewLabel_7);
			//panel_Invoice.add(scrollP_Panel_Invoice_Center, BorderLayout.CENTER);
			
			panel_invoiceDetail = new JPanel();
			cardPanelInvoiceDetail.add(panel_invoiceDetail, "invoice02");
			panel_invoiceDetail.setMinimumSize(new Dimension(300, 500));
			panel_invoiceDetail.setPreferredSize(new Dimension(300, 500));
			
			JSplitPane splitPane_invoice = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollP_Panel_Invoice_Center, cardPanelInvoiceDetail );
		
			splitPane_invoice.setOneTouchExpandable(true);
			splitPane_invoice.setDividerLocation(700);
			splitPane_invoice.setDividerSize(10);
			panel_Invoice.add(splitPane_invoice, BorderLayout.CENTER);
			
			
			
		
			

			//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			
			GridBagLayout gbl_panel_invoiceDetail = new GridBagLayout();
			gbl_panel_invoiceDetail.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0};
			gbl_panel_invoiceDetail.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 30, 0, 100, 15, 0, 120, 100};
			gbl_panel_invoiceDetail.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0};
			gbl_panel_invoiceDetail.columnWidths = new int[]{120, 50, 50, 50, 0};
			panel_invoiceDetail.setLayout(gbl_panel_invoiceDetail);
			
			JLabel lblNewInvoice = new JLabel("Invoice Detail");
			GridBagConstraints gbc_lblNewInvoice = new GridBagConstraints();
			gbc_lblNewInvoice.anchor = GridBagConstraints.EAST;
			gbc_lblNewInvoice.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewInvoice.gridx = 0;
			gbc_lblNewInvoice.gridy = 1;
			lblNewInvoice.setFont(new Font("Tahoma", Font.PLAIN, 17));
			panel_invoiceDetail.add(lblNewInvoice,gbc_lblNewInvoice);
			
			JLabel lblSupplier = new JLabel("Supplier");
			GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
			gbc_lblSupplier.anchor = GridBagConstraints.EAST;
			gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
			gbc_lblSupplier.gridx = 0;
			gbc_lblSupplier.gridy = 3;
			lblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lblSupplier,gbc_lblSupplier);
			
			dataSupplier = new JTextField();
			dataSupplier.setEditable(false);
			dataSupplier.setBackground(SystemColor.control);
			GridBagConstraints gbc_dataSupplier = new GridBagConstraints();
			gbc_dataSupplier.gridwidth = 2;
			gbc_dataSupplier.anchor = GridBagConstraints.NORTH;
			gbc_dataSupplier.insets = new Insets(0, 0, 5, 5);
			gbc_dataSupplier.fill = GridBagConstraints.BOTH;
			gbc_dataSupplier.gridx = 1;
			gbc_dataSupplier.gridy = 3;
			dataSupplier.setColumns(10);
			dataSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(dataSupplier,gbc_dataSupplier);
			
			JLabel lblSystemInvoiceId = new JLabel("System Invoice ID");
			GridBagConstraints gbc_lblSystemInvoiceId = new GridBagConstraints();
			gbc_lblSystemInvoiceId.insets = new Insets(0, 0, 5, 5);
			gbc_lblSystemInvoiceId.anchor = GridBagConstraints.EAST;
			gbc_lblSystemInvoiceId.gridx = 0;
			gbc_lblSystemInvoiceId.gridy = 4;
			lblSystemInvoiceId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lblSystemInvoiceId,gbc_lblSystemInvoiceId);
			
			dataSystemInvoiceID = new JTextField();
			dataSystemInvoiceID.setEditable(false);
			GridBagConstraints gbc_dataSystemInvoiceID = new GridBagConstraints();
			gbc_dataSystemInvoiceID.insets = new Insets(0, 0, 5, 5);
			gbc_dataSystemInvoiceID.fill = GridBagConstraints.HORIZONTAL;
			gbc_dataSystemInvoiceID.gridx = 1;
			gbc_dataSystemInvoiceID.gridy = 4;
			dataSystemInvoiceID.setColumns(10);
			dataSystemInvoiceID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(dataSystemInvoiceID,gbc_dataSystemInvoiceID);
			
			JLabel lblInvoiceId = new JLabel("Supplier Invoice #/ ID");
			GridBagConstraints gbc_lblInvoiceId = new GridBagConstraints();
			gbc_lblInvoiceId.anchor = GridBagConstraints.EAST;
			gbc_lblInvoiceId.insets = new Insets(0, 0, 5, 5);
			gbc_lblInvoiceId.gridx = 0;
			gbc_lblInvoiceId.gridy = 5;
			lblInvoiceId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lblInvoiceId,gbc_lblInvoiceId);
			
			dataSupInvoiceID = new JTextField();
			GridBagConstraints gbc_dataSupInvoiceID = new GridBagConstraints();
			gbc_dataSupInvoiceID.fill = GridBagConstraints.BOTH;
			gbc_dataSupInvoiceID.insets = new Insets(0, 0, 5, 5);
			gbc_dataSupInvoiceID.gridx = 1;
			gbc_dataSupInvoiceID.gridy = 5;
			dataSupInvoiceID.setColumns(10);
			dataSupInvoiceID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(dataSupInvoiceID,gbc_dataSupInvoiceID);
			

			
			

			
			


			JLabel lblAmountDue = new JLabel("Amount Due");
			GridBagConstraints gbc_lblAmountDue = new GridBagConstraints();
			gbc_lblAmountDue.anchor = GridBagConstraints.EAST;
			gbc_lblAmountDue.insets = new Insets(0, 0, 5, 5);
			gbc_lblAmountDue.gridx = 0;
			gbc_lblAmountDue.gridy = 7;
			lblAmountDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lblAmountDue,gbc_lblAmountDue);
			
			
			dataAmountDue = new JTextField();
			GridBagConstraints gbc_dataAmountDue = new GridBagConstraints();
			gbc_dataAmountDue.fill = GridBagConstraints.BOTH;
			gbc_dataAmountDue.gridwidth = 1;
			gbc_dataAmountDue.insets = new Insets(0, 0, 5, 5);
			gbc_dataAmountDue.gridx = 1;
			gbc_dataAmountDue.gridy = 7;
			dataAmountDue.setColumns(10);
			dataAmountDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(dataAmountDue,gbc_dataAmountDue);
			
			JButton btnUpdateDetail = new JButton("Update Detail");
			btnUpdateDetail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateInvoiceDetail();
				}
			});
			GridBagConstraints gbc_btnUpdateDetail = new GridBagConstraints();
			gbc_btnUpdateDetail.anchor = GridBagConstraints.WEST;
			gbc_btnUpdateDetail.insets = new Insets(0, 0, 5, 5);
			gbc_btnUpdateDetail.gridx = 2;
			gbc_btnUpdateDetail.gridy = 7;
			panel_invoiceDetail.add(btnUpdateDetail, gbc_btnUpdateDetail);
			
			JLabel lblAmountPaid = new JLabel("Amount Paid");
			GridBagConstraints gbc_lblAmountPaid = new GridBagConstraints();
			gbc_lblAmountPaid.anchor = GridBagConstraints.EAST;
			gbc_lblAmountPaid.insets = new Insets(0, 0, 5, 5);
			gbc_lblAmountPaid.gridx = 0;
			gbc_lblAmountPaid.gridy = 9;
			lblAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lblAmountPaid,gbc_lblAmountPaid);
			
			dataAmountPaid = new JTextField();
			dataAmountPaid.setEditable(false);
			GridBagConstraints gbc_dataAmountPaid = new GridBagConstraints();
			gbc_dataAmountPaid.gridwidth = 1;
			gbc_dataAmountPaid.anchor = GridBagConstraints.NORTH;
			gbc_dataAmountPaid.insets = new Insets(0, 0, 5, 5);
			gbc_dataAmountPaid.fill = GridBagConstraints.HORIZONTAL;
			gbc_dataAmountPaid.gridx = 1;
			gbc_dataAmountPaid.gridy = 9;
			dataAmountPaid.setColumns(10);
			dataAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(dataAmountPaid,gbc_dataAmountPaid);
			
			JButton btnDistribute = new JButton("Distribute");
			btnDistribute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					distributeInvoicePayment();
				}
			});
			GridBagConstraints gbc_btnDistribute = new GridBagConstraints();
			gbc_btnDistribute.anchor = GridBagConstraints.WEST;
			gbc_btnDistribute.insets = new Insets(0, 0, 5, 5);
			gbc_btnDistribute.gridx = 2;
			gbc_btnDistribute.gridy = 9;
			panel_invoiceDetail.add(btnDistribute, gbc_btnDistribute);
			
			dataAdditionalPayment = new JTextField();
			GridBagConstraints gbc_dataAdditionalPayment = new GridBagConstraints();
			gbc_dataAdditionalPayment.insets = new Insets(0, 0, 5, 5);
			gbc_dataAdditionalPayment.fill = GridBagConstraints.BOTH;
			gbc_dataAdditionalPayment.gridx = 1;
			gbc_dataAdditionalPayment.gridy = 10;
			panel_invoiceDetail.add(dataAdditionalPayment, gbc_dataAdditionalPayment);
			dataAdditionalPayment.setColumns(10);
			
			JButton btnAddPayment = new JButton("Add Payment");
			btnAddPayment.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateInvoicePayment();
				}
			});
			GridBagConstraints gbc_btnAddPayment = new GridBagConstraints();
			gbc_btnAddPayment.anchor = GridBagConstraints.WEST;
			gbc_btnAddPayment.insets = new Insets(0, 0, 5, 5);
			gbc_btnAddPayment.gridx = 2;
			gbc_btnAddPayment.gridy = 10;
			panel_invoiceDetail.add(btnAddPayment, gbc_btnAddPayment);
			
			JLabel lblOutstanding = new JLabel("Outstanding");
			lblOutstanding.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_lblOutstanding = new GridBagConstraints();
			gbc_lblOutstanding.anchor = GridBagConstraints.EAST;
			gbc_lblOutstanding.insets = new Insets(0, 0, 5, 5);
			gbc_lblOutstanding.gridx = 0;
			gbc_lblOutstanding.gridy = 11;
			panel_invoiceDetail.add(lblOutstanding, gbc_lblOutstanding);
			
			dataOutstanding = new JTextField();
			dataOutstanding.setForeground(Color.RED);
			dataOutstanding.setFont(new Font("Tahoma", Font.PLAIN, 15));
			dataOutstanding.setEditable(false);
			GridBagConstraints gbc_dataOutstsanding = new GridBagConstraints();
			gbc_dataOutstsanding.insets = new Insets(0, 0, 5, 5);
			gbc_dataOutstsanding.fill = GridBagConstraints.HORIZONTAL;
			gbc_dataOutstsanding.gridx = 1;
			gbc_dataOutstsanding.gridy = 11;
			panel_invoiceDetail.add(dataOutstanding, gbc_dataOutstsanding);
			dataOutstanding.setColumns(10);
			
			JLabel lblAssociatedOrders = new JLabel("Associated Orders");
			lblAssociatedOrders.setFont(new Font("Tahoma", Font.PLAIN, 15));
			GridBagConstraints gbc_lblAssociatedOrders = new GridBagConstraints();
			gbc_lblAssociatedOrders.anchor = GridBagConstraints.EAST;
			gbc_lblAssociatedOrders.insets = new Insets(0, 0, 5, 5);
			gbc_lblAssociatedOrders.gridx = 0;
			gbc_lblAssociatedOrders.gridy = 13;
			panel_invoiceDetail.add(lblAssociatedOrders, gbc_lblAssociatedOrders);
			
			JScrollPane scrollPane_7 = new JScrollPane();
			GridBagConstraints gbc_scrollPane_7 = new GridBagConstraints();
			gbc_scrollPane_7.gridwidth = 4;
			gbc_scrollPane_7.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane_7.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_7.gridx = 0;
			gbc_scrollPane_7.gridy = 14;
			panel_invoiceDetail.add(scrollPane_7, gbc_scrollPane_7);
			
			tableAssociatedOrders = new JTable();
			scrollPane_7.setViewportView(tableAssociatedOrders);
			
			
			JLabel lblSelectOrdersTo = new JLabel("Orders to associate");
			GridBagConstraints gbc_lblSelectOrdersTo = new GridBagConstraints();
			gbc_lblSelectOrdersTo.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblSelectOrdersTo.insets = new Insets(0, 0, 5, 5);
			gbc_lblSelectOrdersTo.gridx = 0;
			gbc_lblSelectOrdersTo.gridy = 16;
			lblSelectOrdersTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lblSelectOrdersTo,gbc_lblSelectOrdersTo);
			
			JButton btnUpdateAssociation = new JButton("Update Association");
			btnUpdateAssociation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateInvoiceAssociation();
				}
			});
			GridBagConstraints gbc_btnUpdateAssociation = new GridBagConstraints();
			gbc_btnUpdateAssociation.anchor = GridBagConstraints.WEST;
			gbc_btnUpdateAssociation.insets = new Insets(0, 0, 5, 5);
			gbc_btnUpdateAssociation.gridx = 2;
			gbc_btnUpdateAssociation.gridy = 16;
			panel_invoiceDetail.add(btnUpdateAssociation, gbc_btnUpdateAssociation);
			
			JScrollPane scrollPane_61 = new JScrollPane();
			GridBagConstraints gbc_scrollPane_61 = new GridBagConstraints();
			gbc_scrollPane_61.anchor = GridBagConstraints.NORTHWEST;
			gbc_scrollPane_61.gridwidth = 4;
			gbc_scrollPane_61.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane_61.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_61.gridx = 0;
			gbc_scrollPane_61.gridy = 17;
			panel_invoiceDetail.add(scrollPane_61,gbc_scrollPane_61);
			
			
			pickOrderTable = new JTable();
			pickOrderTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
			scrollPane_61.setViewportView(pickOrderTable);
			//----------------------
			



			//------date picker components Default to today-----------
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
			
			JLabel lbldate = new JLabel("Received Date");
			GridBagConstraints dateConstraints2 = new GridBagConstraints();
			dateConstraints2.anchor = GridBagConstraints.NORTHEAST;
			dateConstraints2.insets = new Insets(0, 0, 5, 5);
			dateConstraints2.gridx = 0;
			dateConstraints2.gridy = 6;
			lbldate.setFont(new Font("Tahoma", Font.PLAIN, 15));
			panel_invoiceDetail.add(lbldate,dateConstraints2);
			datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
			
			GridBagConstraints dateConstraints = new GridBagConstraints();
			dateConstraints.fill = GridBagConstraints.BOTH;
			dateConstraints.anchor = GridBagConstraints.NORTHEAST;
			dateConstraints.insets = new Insets(0, 0, 5, 5);
			dateConstraints.gridx = 1;
			dateConstraints.gridy = 6;
			panel_invoiceDetail.add(datePicker,dateConstraints);

			//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			
			
			
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
			btn_associateSup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				associateSupplier();
				
				}
			});
									
					
			//*************JPanle for Inventory List page 
	
				    ChangeListener loadTableAtOpen = new ChangeListener() {
				    public void stateChanged(ChangeEvent CE) {
				    	
				        try{
				        	createTableInventory();
				        }catch(Exception ex){
				        	ex.printStackTrace();
				        }
							
				    };//ChangeListener
				    }; 
		
	
	GridBagConstraints gbc_dataName = new GridBagConstraints();
	gbc_dataName.anchor = GridBagConstraints.WEST;
	gbc_dataName.insets = new Insets(0, 0, 5, 5);
	gbc_dataName.gridx = 2;
	gbc_dataName.gridy = 8;
	  				
	  				
		
		ord_Sup_cb.setRenderer(new CbSupListRenderer());

		

}// end of Constructor Inventory() ====================================================================================================
//======================================================================================================================================
// Begining of inventory class functions
 
//============================================
public static void updateInvoiceDetail(){
	
	   boolean success = true;
	   String error = "";
	   CbSupItem sup; 	
	   boolean proceed = false;
	   boolean resetAmountDue = false; 
	   
	   	//check if amount due is valid
	   int invoiceID = Integer.parseInt(dataSystemInvoiceID.getText().toString());
	   String dueAmountStr = dataAmountDue.getText();
	   String paidAmountStr = dataAmountPaid.getText();
	   Date pickedDate = (Date) datePicker.getModel().getValue();
	   String supInvID = dataSupInvoiceID.getText();

	   //check amount due 
	   if(dueAmountStr.equals("")){
		   //do nothing 
	   }else if(! Inventory.validateDouble(dueAmountStr) && ! dueAmountStr.equals("")){
		   error = error.concat( "Due Amount must be a valid numbern\n" );
		   resetAmountDue = true;
	   }else if (Double.parseDouble(dueAmountStr) < 0){
		   error = error.concat( "Due Amount cannot be a negative number\n" );
		   resetAmountDue = true;
	   }else if (Double.parseDouble(dueAmountStr) < Double.parseDouble(paidAmountStr)){
		   error = error.concat( "Due Amount cannot be set to lower than Amount Paid\n" );
		   resetAmountDue = true;
	   }
	   if (resetAmountDue){
		   for(int h= 0; h < tableInvoice.getRowCount(); h++){
			   if(Integer.parseInt(tableInvoice.getValueAt(h, 0).toString()) == invoiceID){
				   String originalDue = tableInvoice.getValueAt(h,3).toString();
				   dataAmountDue.setText(originalDue);
			   }
		   }
	   }
	   
	   //check suppplier invoice id 
	   if (! Inventory.validateStrToDouble(supInvID)){
		   error = error.concat("Supplier invoice #/ID must be a number\n");
		   dataSupInvoiceID.setText("");
	   }
		
	    	// if "error" string is empty, operation is successful
	   

 	    if(error.length() > 1){
 	    	JOptionPane.showMessageDialog(null, error);
 	    }else {
 	    	//confirm user's decision
 	    	int response = JOptionPane.showConfirmDialog(null, "This will update Supplier Invoice ID, received date, and/or amount due. \nDo you want to continue?", "Confirm",
  	               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
  	           if (response == JOptionPane.NO_OPTION) {
  	        	   //do nothing
  	           } else if (response == JOptionPane.CLOSED_OPTION) {
  	        	   //do nothing
  	           } else if (response == JOptionPane.YES_OPTION) {
  	           proceed = true;   
  	           }
 	    	
 	    }

 	           if(proceed == true){
 	        	//update existing invoice
 	   	    	try {
 	   				Connection con = Connect.connectionSetup();
 	   				String query = "Update invoice Set "
 	   						+ " AmountDue = ?, "
 	   						+ " ReceivedDate = ?, "
 	   						+ " Sup_InvoiceID = ?"
 	   						+ " WHERE ID = ? " ;
 	   						
 	   				PreparedStatement pst = con.prepareStatement(query);
 	   				pst.setDouble(1, Double.parseDouble(dueAmountStr));
 	   				pst.setDate(2, new java.sql.Date(pickedDate.getTime()));
 	   				pst.setInt(3, Integer.parseInt(supInvID));
 	   				pst.setInt(4, invoiceID);
 	   				
 	   				pst.executeUpdate();
 	  					
 	  					//inform user that an invoice has been created
 	  					JOptionPane.showMessageDialog(null, "Invoice (ID: " + invoiceID + ") Updated");	
 	  					
 	  					//refresh tables 
 	  					createTableOrderList();
 	  					createTableInvoice();
 	  					createTablePickOrderForEdit(invoiceID);
 	  					createTableAssociatedOrders(invoiceID);
 	  					
 	  				} catch (Exception e) {
 	  					// TODO Auto-generated catch block
 	  					e.printStackTrace();
 	  				}
 	           }
}
//--------------------------------------------

public static void updateInvoicePayment(){
	
	boolean proceed = false;
	int invoiceID = Integer.parseInt(dataSystemInvoiceID.getText());
	double additionalPayment = 0; 
	double curDueAmount = Double.parseDouble(dataAmountDue.getText());
	double curPaidAmount = Double.parseDouble(dataAmountPaid.getText());
	double curInvoicePayment = Double.parseDouble(dataAmountPaid.getText());
	
	if(dataAdditionalPayment.getText().equals("") || dataAdditionalPayment.getText().equals("0")){
		JOptionPane.showMessageDialog(null, "Please enter additoinal payment amount" );
	}else if (!validateDouble(dataAdditionalPayment.getText())){
		JOptionPane.showMessageDialog(null, "Please enter a valid number" );
		dataAdditionalPayment.setText("");
	}else if (curDueAmount == curPaidAmount){
		JOptionPane.showMessageDialog(null, "This Invoice is fully paid. Cannot add payment" );		
	}else if (Double.parseDouble(dataAdditionalPayment.getText().toString()) < 0 ){
		JOptionPane.showMessageDialog(null, "Payment cannot be a negative value" );
		dataAdditionalPayment.setText("");
	}else if(((curDueAmount - curInvoicePayment) - Double.parseDouble(dataAdditionalPayment.getText().toString())) < 0){
		JOptionPane.showMessageDialog(null, "Amount paid cannot exceed amount due - current outstanding is $" + (curDueAmount - curInvoicePayment) );
		dataAdditionalPayment.setText("");
	}else{
		additionalPayment = Double.parseDouble(dataAdditionalPayment.getText());
		proceed = true; 
	}
	
	if (proceed == true ){
		
		Connection con; 
		
		try {
			con = Connect.connectionSetup();
			String query = "Update Invoice Set AmountPaid = ? WHERE ID = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, curPaidAmount + additionalPayment);
			pst.setInt(2, invoiceID);
			pst.executeUpdate();
			
			//once paid amount is updated, update screen
			
			dataAdditionalPayment.setText("");

			createPanelInvoiceDetail(invoiceID);
			createTableInvoice();
			
			con.close();
			pst.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//============================================
public static void distributeInvoicePayment(){
	
	boolean proceed = false;
	double distributedTotal = 0;
	double undistributed = 0;
	int invoiceID = Integer.parseInt(dataSystemInvoiceID.getText().toString()); 

	DefaultTableModel model = (DefaultTableModel) tableAssociatedOrders.getModel();
	int rowCount = model.getRowCount();
	
	double curDueAmount = Double.parseDouble(dataAmountDue.getText());
	double curInvoicePayment = Double.parseDouble(dataAmountPaid.getText());
	
	//loop through associated orders and calculate distributed total for orders
	for(int k = 0; k < model.getRowCount(); k++){
		if(model.getValueAt(k, 6) != null){
			distributedTotal = distributedTotal + Double.parseDouble(model.getValueAt(k, 6).toString());
		}
	}
	
	if(curInvoicePayment == distributedTotal || curInvoicePayment < distributedTotal ){
		JOptionPane.showMessageDialog(null, "The entire payment has been already distributed to order(s)");
	}else{
		undistributed = (curInvoicePayment - distributedTotal);
		undistributed = Math.round(undistributed * 100);
		undistributed = undistributed/100;
		
		
    	int response = JOptionPane.showConfirmDialog(null, "Currently $" + undistributed + " remains undistributed. \nDo you want to distribtue to associated orders?", "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION) {
          proceed = false;
        } else if (response == JOptionPane.CLOSED_OPTION) {
          proceed = false;
        } else if (response == JOptionPane.YES_OPTION) {
        proceed = true;   
        }

	}
	
	if(proceed == true){
		for(int j = 0; j < rowCount; j++){  //#loop throuh tableAssociated order -----
			int orderID = (int) model.getValueAt(j, 0);
			double curDue = 0;
			double curPayment = 0;
			
			if(model.getValueAt(j, 5) != null ){
				curDue = Double.parseDouble(model.getValueAt(j, 5).toString());
			}
			if(model.getValueAt(j, 6) != null ){
		        curPayment = Double.parseDouble(model.getValueAt(j, 6).toString());
			}
			double outstanding = curDue - curPayment;
			
			Connection con;
			try {
				con = Connect.connectionSetup();
				String query = "";
				PreparedStatement pst; 
				
				//if  Amount paid is greater than cost, amount paid is same as cost 
				if(undistributed >= outstanding ){
					//System.out.println("additional payment is  " + additionalPayment);
					undistributed = undistributed - outstanding; //subtract from undistributed pool
					query = "Update `order` set AmountPaid = ? WHERE ID = ?";	
					pst = con.prepareStatement(query);
					pst.setDouble(1, curPayment + outstanding);
					pst.setInt(2, orderID);
					pst.executeUpdate();
					
				}else{
					//if remaining of paidAmount is less than orderCost, then assign the remanining
					query = "Update `order` set AmountPaid = ? WHERE ID = ?";
					//System.out.println("paid amount is  " + additionalPayment );
					pst = con.prepareStatement(query);
					pst.setDouble(1, curPayment + undistributed); //add remaining 
					pst.setInt(2, orderID);
					pst.executeUpdate();
				}
				
				con.close();
				pst.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//update tables 
			createTableAssociatedOrders(invoiceID);
			createTablePickOrderForEdit(invoiceID);
			createPanelInvoiceDetail(invoiceID);
		}
			dataAdditionalPayment.setText("");
		
	}// end of if proceed = true 
}
//---------------------------------------------

//=============================================
public static void updateInvoiceAssociation(){
	
	//loop through pickOrder table and update orders 
	int invoiceID = Integer.parseInt(dataSystemInvoiceID.getText().toString());
	DefaultTableModel model = (DefaultTableModel) pickOrderTable.getModel();
	int rowCount = model.getRowCount();
	
	for(int j = 0; j < rowCount; j++){  //#loop through pickOrder Table-----
		
		int orderID = Integer.parseInt(model.getValueAt(j, 1).toString());
		boolean selected = (boolean) model.getValueAt(j, 0); 
		//if check is marked, associate it to this invoice
		
		Connection con;
		String query; 
		PreparedStatement pst; 
		
		try {
			
			con = Connect.connectionSetup();
			
			if(selected){
				query = "Update `order` set InvoiceID = " + invoiceID + ""
						+ " WHERE ID = " + orderID ;
				pst = con.prepareStatement(query);
				pst.executeUpdate();
			}else{
				
				//if this order already has paid amount under this invoice, cannot deassociate

				if(model.getValueAt(j, 7) == null ) {
				query = "Update `order` set InvoiceID = NULL "
						+ " WHERE ID = " + orderID ;
				pst = con.prepareStatement(query);
				pst.executeUpdate();
				}else {
					JOptionPane.showMessageDialog(null, "Order: "+ orderID +" is already paid - cannot deassociate");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//updateTable
		createTablePickOrderForEdit(invoiceID);
		createTableAssociatedOrders(invoiceID);
	
	}// end of for loop 
		
}

//---------------------------------------------



//=============================================================
public static void createPanelInvoiceDetail(int invoiceID){
	
	try{
		
		//using the invoiceID, get invoice information and order assocaited to it
		
		Connection con_Inv = Connect.connectionSetup();
		String query = "Select i.AmountDue, i.AmountPaid,  i.ReceivedDate, i.SupplierID, s.Name, i.Sup_InvoiceID "
				+ "FROM invoice i INNER JOIN Supplier s ON i.SupplierID = s.ID "
				+ "WHERE i.ID = " + invoiceID  ;
		PreparedStatement pst = con_Inv.prepareStatement(query);
		ResultSet rs = pst.executeQuery();

		while(rs.next()){
			amountDue = rs.getString(1);
			amountPaid = rs.getString(2);
			receivedDate = rs.getDate(3);
			supplierID = rs.getInt(4);
			supplierName = rs.getString(5);	
			supplierInvoiceID = rs.getInt(6);
		}
		con_Inv.close();
		pst.close();
		rs.close();
		
		dataAmountDue.setText(amountDue);
		dataAmountPaid.setText(amountPaid);
		
		double outstanding = Double.parseDouble(dataAmountDue.getText()) - Double.parseDouble(dataAmountPaid.getText());
		outstanding = Math.round(outstanding * 100);
		outstanding = outstanding / 100;
		dataOutstanding.setText(Double.toString(outstanding));
		
		dataSupplier.setText(supplierName);
		dataSystemInvoiceID.setText(Integer.toString(invoiceID));
		dataSupInvoiceID.setText(Integer.toString(supplierInvoiceID));
		
		//createTablePickOrderForEdit(supplierID, invoiceID);
		
		}catch (Exception e){
			e.printStackTrace();
		}
	//------date picker components -----------
	UtilDateModel model = (UtilDateModel) datePicker.getModel();
	
	Date today = receivedDate;
	Calendar cal = Calendar.getInstance();
	cal.setTime(today);
	
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	int day = cal.get(Calendar.DAY_OF_MONTH);
	model.setDate(year, month, day); //set today as the default value, but it is not displayed yet
	model.setSelected(true); //display today's date
    
	/*
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
	*/
	//------end of datepicker components ---------
	
}

public String deleteInvoice(int invoiceID){
	String error = "";
	if(!dataAmountPaid.getText().equals("")){
		error = error.concat("A payment has already made to this invoice\n");
	}
	if(orderCount > 0){
		error = error.concat("This invoice has associated order(s)");
	}
	
	if( !error.equals("")){
	    String errorMessage = "Cannot Delete This Invoice:\n";
	    errorMessage = errorMessage.concat(error);
	    JOptionPane.showMessageDialog(null, errorMessage);
	    return "Fail";
	}else{

		try {
			Connection con;
			con = Connect.connectionSetup();
			String query = "Delete FROM invoice WHERE ID = " + invoiceID;
	
			PreparedStatement pst = con.prepareStatement(query);
			pst.executeUpdate();
			
			con.close();
			
			//refresh invoice table and close the panel
		    Inventory.createTableInvoice(); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Success";
	}
}

//=======================================================================
public static void createTableAssociatedOrders(int invoiceID){
	try {
		
		int supID = 0; 
		
		Connection con_Inv = Connect.connectionSetup();
		
		String query = "Select SupplierID FROM Invoice WHERE ID = ? ";
		PreparedStatement pst = con_Inv.prepareStatement(query);
		pst.setInt(1, invoiceID);
		ResultSet rs = pst.executeQuery();
		while(rs.next()){
			supID = rs.getInt(1);
		}
		
		query = "Select o.ID, o.InvoiceID, s.Name as 'Supplier Name', o.CreateDate, o.ReceivedDate, o.Cost, o.AmountPaid "
			+ " FROM `order` o "
			+ " LEFT JOIN Supplier s ON s.ID = o.SupplierID "
			+ " WHERE s.ID = " + supID + " AND o.InvoiceID ="+ invoiceID +" ORDER BY ID DESC;";
	
		pst = con_Inv.prepareStatement(query);
		rs = pst.executeQuery();
		ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
		int colCount = md.getColumnCount();
		
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();

	    
	    //columnNames.addElement("Associate");
	    columnNames.addElement("Order ID");
	    columnNames.addElement("Invoice ID");
	    columnNames.addElement("Supplier");
	    columnNames.addElement("Created Date");
	    columnNames.addElement("Received Date");
	    columnNames.addElement("Cost");
	    columnNames.addElement("Amount Paid");

  while(rs.next()){
  	Vector<Object> row = new Vector<Object>();
  	orderCount++;

  	///boolean check = false;
  	//row.addElement(check);
  	for (int i = 1; i <= colCount; i++){

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
  tableAssociatedOrders.setModel(orderPickModel);
  tableAssociatedOrders.setRowHeight(30);
  
	Dimension tableSize = tableAssociatedOrders.getPreferredSize();
	TableColumnModel tcm = tableAssociatedOrders.getColumnModel();
	tableAssociatedOrders.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
	

	tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
	tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
	tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); 
	tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.15f));
	tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.15f));
	tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
	tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.10f));

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

//=======================================================================
public static void createTablePickOrderForEdit(int invoiceID){
	try {
		
		int supID = 0; 
		
		Connection con_Inv = Connect.connectionSetup();
		
		String query = "Select SupplierID FROM Invoice WHERE ID = ? ";
		PreparedStatement pst = con_Inv.prepareStatement(query);
		pst.setInt(1, invoiceID);
		ResultSet rs = pst.executeQuery();
		while(rs.next()){
			supID = rs.getInt(1);
		}
		
		query = "Select o.ID, o.InvoiceID, s.Name as 'Supplier Name', o.CreateDate, o.ReceivedDate, o.Cost, o.AmountPaid "
			+ " FROM `order` o "
			+ " LEFT JOIN Supplier s ON s.ID = o.SupplierID "
			+ " WHERE s.ID = " + supID + " AND (o.InvoiceID IS NULL OR o.InvoiceID ="+ invoiceID +") ORDER BY ID DESC;";
	
		pst = con_Inv.prepareStatement(query);
		rs = pst.executeQuery();
		ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
		int colCount = md.getColumnCount();
		
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();

	    
	    columnNames.addElement("Associate");
	    columnNames.addElement("Order ID");
	    columnNames.addElement("Invoice ID");
	    columnNames.addElement("Supplier");
	    columnNames.addElement("Created Date");
	    columnNames.addElement("Received Date");
	    columnNames.addElement("Cost");
	    columnNames.addElement("Amount Paid");

    while(rs.next()){
    	Vector<Object> row = new Vector<Object>();
    	orderCount++;

    	///boolean check = false;
    	//row.addElement(check);
    	for (int i = 1; i <= colCount; i++){
    		if(i == 2){
    			if(rs.getObject(i) == null){
    				boolean bool = false;
    				row.add(0, bool);
    				}else if(rs.getInt(i) == invoiceID){
    				boolean bool = true;
    				row.add(0, bool);
    				}
    		}
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
	pickOrderTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
	

	tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
	tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.05f));
	tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
	tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); 
	tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.15f));
	tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.15f));
	tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
	tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.10f));

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





















	//====================================================
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
	public Vector<Integer> inventoryGetSelectedRows(){//*********************************************
	Vector<Integer> selectedRows = new Vector<Integer>();
	int rowCount = 	tableInventory.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			if(tableInventory.getValueAt(i, 0) == Boolean.TRUE)
				{
					selectedRows.add((Integer) tableInventory.getValueAt(i, 1));
				}
		}
	return selectedRows;
	}//--------------------------------
	
	public static void filterByMultiColumn2(){//********************************************
		RowFilter<MyTableModelClass,Object> rf = null;
		List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		filters.add(RowFilter.regexFilter(".*"+filterFrame.getID().getText()+".*",1));
		//filters.add(RowFilter.regexFilter(".*"+filterFrame.getCategory().getText()+".*", 2));
		//filters.add(RowFilter.regexFilter(".*"+filterFrame.getSubCategory().getText()+".*", 3));
		if(! filterFrame.getCat().equals("")){
			filters.add(RowFilter.regexFilter(".*"+filterFrame.getCat()+".*", 2));
		}
		if(! filterFrame.getSubCat().equals("")){
			filters.add(RowFilter.regexFilter(".*"+filterFrame.getSubCat()+".*", 3));
		}
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getProdName().getText()+".*", 4));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getDesc().getText()+".*", 5));
		filters.add(RowFilter.regexFilter(".*"+filterFrame.getNote().getText()+".*", 10));
		
		if (!filterFrame.getQtyGt().getText().isEmpty() ){
	    String qtygt = filterFrame.getQtyGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(qtygt), 6));
		}
		if (!filterFrame.getQtyLt().getText().isEmpty() ){
		String qtylt = filterFrame.getQtyLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(qtylt), 6));
		}
		if (!filterFrame.getSaleGt().getText().isEmpty() ){
		String salegt = filterFrame.getSaleGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(salegt), 8));
		}
		if (!filterFrame.getSaleLt().getText().isEmpty() ){
		String salelt = filterFrame.getSaleLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(salelt), 8));
		}
		
		rf = RowFilter.andFilter(filters);
		sorter.setRowFilter(rf);

		if(
				filterFrame.getID().getText().isEmpty() &&
				filterFrame.getProdName().getText().isEmpty() &&
				filterFrame.getDesc().getText().isEmpty() &&
				//filterFrame.getCategory().getText().isEmpty() &&
				//filterFrame.getSubCategory().getText().isEmpty() &&
				filterFrame.getNote().getText().isEmpty() &&
				filterFrame.getQtyGt().getText().isEmpty() &&
				filterFrame.getQtyLt().getText().isEmpty() &&
				filterFrame.getSaleGt().getText().isEmpty() &&
				filterFrame.getSaleLt().getText().isEmpty() &&
				filterFrame.getCat().equals("") &&
				filterFrame.getSubCat().equals("")
			){
			jl_filter_status.setOpaque(false);
			jl_filter_status.setText("Filter Not Applied");
			
		}else {
			jl_filter_status.setText("Filter Applied");
			jl_filter_status.setOpaque(true);
			jl_filter_status.setBackground(new Color(255, 0, 0));
		}
		
	}
	
	//===============================
	public void initiateInventory(){
		createTableInventory();
		createTableOrderList();
		createTableInvoice();
	}
	
    //Added by Gaurav
	/*
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
    */
	
	
	
	
	
	public static void createTableInvoice(){ //===============================================
		
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
		    columnNames.addElement("Associated Orders");
		    
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
		    tableInvoice = new JTable(modelForInvoice);
		    tableInvoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		    tableInvoice.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		    scrollP_Panel_Invoice_Center.setViewportView(tableInvoice);
		    //invoice_Table.setModel(modelForInvoice);
		    tableInvoice.setRowHeight(30);
		    		
			Dimension tableSize = tableInvoice.getPreferredSize();
			TableColumnModel tcm = tableInvoice.getColumnModel();
		
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
		
			tableInvoice.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent mouseE) {
				if (mouseE.getClickCount() == 2) {
					
				    JTable target = (JTable)mouseE.getSource();
				    int row = target.getSelectedRow();
				    int invoiceID = (int) target.getValueAt(row, 0);
				    
				    cardlayoutForInvoice.show(cardPanelInvoiceDetail, "invoice02");
				    
				    createPanelInvoiceDetail(invoiceID);
				    createTablePickOrderForEdit(invoiceID);
				    createTableAssociatedOrders(invoiceID);
				    
			        //SwingUtilities.invokeLater(new Runnable() {
			        //    @Override
			        //    public void run() {
			        //        new DialogEditInvoice(null,invoiceID).setVisible(true);
			        //    }
			        //});
				    

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
					
					createTableInventory();
					clearAddProductForm();
					//tabbedPane_Inventory.setSelectedIndex(0);
					cardlayout.show(inventoryMainPanel, "000");
					currentPage = 0;
					
					int index = 0; 
					for (int j = 0; j < tableInventory.getRowCount(); j++){
						if(Integer.parseInt(prodID) == (int)tableInventory.getValueAt(j, 1)){	
							index = j;
						}
					}
					JViewport viewport = (JViewport)tableInventory.getParent();
					Rectangle  rect = tableInventory.getCellRect(index, 0, true);
					Point pt = viewport.getViewPosition();
					rect.setLocation(rect.x-pt.x, rect.y-pt.y);
					tableInventory.scrollRectToVisible(rect);
					tableInventory.getSelectionModel().setSelectionInterval(index, index);
				
				
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
					System.out.println("prodID is /" + prodID + "/SupID is /"+supID);
					PreparedStatement pst = con_Inv.prepareStatement(query);
					pst.executeUpdate();
					
					createTableSoldBy();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

	//===================================================================================================
	public void createTableNewOrder(StringBuilder mySelection){
		
		createTableNewOrderSummary(mySelection);
	
	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    try{
    	
    	Connection con = Connect.connectionSetup();
    	String query = "Select p.ID, c.Name as Category, sc.Name as 'Sub Category', p.Name, p.Quantity, "
      			+ " SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered'	"
    			+ " FROM Product p "
    			+ " LEFT JOIN Category as c on c.ID = p.CategoryID"
    			+ "	LEFT JOIN Category as sc on sc.ID = p.SubCategoryID "
    			+ " LEFT JOIN orderdetail as od on od.ProductID = p.ID "
    			+ " WHERE p.ID IN ( " + mySelection + " )"
    			+ "	GROUP BY p.ID";
    			
		PreparedStatement pst = con.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount();
		
		columnNames.addElement("ID"); 				//1
		columnNames.addElement("Category");			//2
		columnNames.addElement("Sub Category");		//3
		columnNames.addElement("Name");				//4
		columnNames.addElement("Quantity on Hand");	//5
		columnNames.addElement("To be delivered");	//6
		columnNames.addElement("Supplier");			//7
		columnNames.addElement("Prev. cost");		//8 Unit cost from product supplier
		columnNames.addElement("Order Quantity");	//9 user specifies order quantity
		columnNames.addElement("Estimate");		//10 prev @price x order quantity
		

		//Get row data
		while(rs.next()){
		Vector<Object> row = new Vector<Object>(columnCount);
			for (int i = 1; i <= columnCount; i++){
				row.addElement(rs.getObject(i));
			}
			data.addElement(row);
		}
		
		pst.close();
		rs.close();
		con.close();
		
	    }catch (Exception e1) {	
	    	e1.printStackTrace();
		}
    
    class TM_newOrder extends DefaultTableModel{//********************************************
    	public TM_newOrder (Vector<Object> data, Vector<Object> columnNames) {
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
    } //-----
    
    TM_newOrder tm_NewOrder = new TM_newOrder(data, columnNames);
    
    tableNewOrder = new JTable(tm_NewOrder)
    {
		public TableCellEditor getCellEditor(int row, int column)
		{
			int modelColumn = convertColumnIndexToModel(column);
			int rowNumber = tableNewOrder.getRowCount();
			
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
    
	TableRowSorter<TM_newOrder> sorter_2 = new TableRowSorter<TM_newOrder>(tm_NewOrder);
	tableNewOrder.setRowSorter(sorter_2); 
	tableNewOrder.setRowHeight(25);
	tableNewOrder.setFont(new Font("Tahoma", Font.PLAIN, 15));
	tableNewOrder.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
	scrollPane_ceateOrder.setViewportView(tableNewOrder);
	//setTMLforCreateOrderTable(); //function to add table model listner
	
	Dimension tableSize = tableNewOrder.getPreferredSize();
	TableColumnModel tcm2 = tableNewOrder.getColumnModel();
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
	
	TableModelListener tml_CreateTable = new TableModelListener(){
		
		TableModel model = tableNewOrder.getModel();
		TableModel model_summary = tableNewOrderSummary.getModel();
		
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
						JOptionPane.showMessageDialog(null, "Please enter a whole number in Order Quantity column");
					}else if (Double.parseDouble(model.getValueAt(row, col).toString() )< 0 ){
						JOptionPane.showMessageDialog(null, "Quantity cannot be a negative number");
						model.setValueAt("0", row, col);
					}
					else { //#30
							//if supplier is not selected 
						if (model.getValueAt(row, 6) == null){
							JOptionPane.showMessageDialog(null, "Please select a supplier");
							
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

				if (addRowToSummary == true ) {
					//------------------------------------
					int itemCount = 0; 
					//loop through createOrdertable and gather order summary for this supplier 
					orderTotalforThisSup = 0;
					for (int i = 0; i < tableNewOrder.getRowCount(); i++){
						
						if(model.getValueAt(i, 6) != null){
							CbSupItem curSup = (CbSupItem)model.getValueAt(i, 6);
							int curSupID = curSup.getID();
							
							if(curSupID == supID && model.getValueAt(i, 8) != null){
								orderTotalforThisSup += (double)model.getValueAt(i, 9);
								itemCount++;
							}
						}
					}
					
					//remove row from summary table for the supplier 
					for(int k = 0; k < model_summary.getRowCount(); k++){
						int supIDinSummary = (int)model_summary.getValueAt(k, 0);
						if(sup != null){
							if(supIDinSummary == (int)sup.getID()){
								((DefaultTableModel)model_summary).removeRow(k);
						}
						}

					}
					//then add new row 
					if(sup != null){
						((DefaultTableModel)model_summary).addRow(new Object[]{ supID, supName, itemCount, minOrder, orderTotalforThisSup});
					}
					
					//-------------------------------------
				} //end of if addRowToSummary 
				} //end of update 
		} //tableChanged()
	}; //TableModelListener()
	tableNewOrder.getModel().addTableModelListener(tml_CreateTable);
	
} //createOrderTable()------------------------------------------------------------

	//=======================================================================
	public void createTableNewOrderSummary (StringBuilder mySelection){
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();
	    
	    columnNames.addElement("Supplier ID"); 				
			columnNames.addElement("Supplier"); 
			columnNames.addElement("Item Count"); 
			columnNames.addElement("Minimum Order");
			columnNames.addElement("Order Estimate");
			
		//-----
		class TM_newOrderSummary extends DefaultTableModel{
				public TM_newOrderSummary (Vector<Object> data, Vector<Object> columnNames) {
					super(data,columnNames);
				}
				@Override
				public Class getColumnClass(int column){
					 for (int row = 0; row < getRowCount(); row++){
					     Object o = getValueAt(row, column);
					     if (o != null)  {
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
			} //	
			
			//-----
	    TM_newOrderSummary tm_NewOrderSummary = new TM_newOrderSummary(data, columnNames);
	    
	    tableNewOrderSummary = new JTable(tm_NewOrderSummary);
	    tableNewOrderSummary.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    tableNewOrderSummary.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
	    scrollPane_createOrderSummary.setViewportView(tableNewOrderSummary);
	    tableNewOrderSummary.setRowHeight(30);
	    
		Dimension tableSize = tableNewOrderSummary.getPreferredSize();
		TableColumnModel tcm = tableNewOrderSummary.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
		tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.30f));
		tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.20f));
		tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.20f)); 
		tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.20f));
	    
	}
	
	//=============================================
	public void updateSalePrice(){
		
		DefaultTableModel model = (DefaultTableModel) tableSetPrice.getModel();
		int emptyCount = 0; 
		boolean proceed = true; 
	    for(int i = 0; i < model.getRowCount(); i++){
	    	if(model.getValueAt(i, 10) == null){
	    		emptyCount++;
	    	}
	    }
		//if there are rows without new price, warn user 
	    if(emptyCount >0 ){
	    	int response = JOptionPane.showConfirmDialog(null, "There are " + emptyCount + " products without new sale price. "
	    			+ "\nTHose products' sale price will not be udpated. \nDo you want to proceed?", "Confirm",
	            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        if (response == JOptionPane.NO_OPTION) {
	          proceed = false;
	        } else if (response == JOptionPane.CLOSED_OPTION) {
	          proceed = false;
	        } else if (response == JOptionPane.YES_OPTION) {
	        proceed = true;   
	        }
	    }// end of if(emptyCount >0)

	    if(proceed){
	    	try {
				Connection con = Connect.connectionSetup();
				String query = "Insert Into pricehistory (ProductID, NewPrice, Reason) "
						+ " Values (?, ?, ?)"; 
				PreparedStatement pst = con.prepareStatement(query);
				int count = 0; 	
				for(int i = 0; i < model.getRowCount(); i++){ 
					
					//check column 10 (newprice) and update if it is not empty
					if(model.getValueAt(i, 10) != null ){
						int ID = Integer.parseInt(model.getValueAt(i, 1).toString());
						double price = Double.parseDouble(model.getValueAt(i, 10).toString());
						String reason = model.getValueAt(i, 11).toString();
						pst.setInt(1, ID );
						pst.setDouble(2, price );
						pst.setString(3, reason);
						pst.executeUpdate();
						count++; 
					}//end of if(model.getvalueat...
				} //end of for (int i = ....
				JOptionPane.showMessageDialog(null, count + " product's sale price has been updated");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	
	    	//clear the form
	    	dataPercentage.setText("");
	    	dataAdjustNumber.setText("");
	    	dataReason.setText("");
	    	
	    	//tabbedPane_Inventory.setEnabledAt(6, false);
	    	cardlayout.show(inventoryMainPanel, "000");
	    	currentPage = 0;
	    	//tabbedPane_Inventory.setSelectedIndex(0);
	    	
	    } //end of if(proceed) 

		
	}//--------------------------------------------
	
	//=============================================
	public void overrideReason(){ 
		DefaultTableModel model = (DefaultTableModel) tableSetPrice.getModel();
		String reason = dataReason.getText();
			//if user input is not valid
		if( reason.length() > 100){
			JOptionPane.showMessageDialog(null, "Reason must be less than 100 letters");
			String adjusted  = reason.substring(0, 99);
			dataReason.setText(adjusted);
		}else { //++++++++++++++
			//override percentage adjustment column only if check column is checked 
			for(int i = 0; i < model.getRowCount(); i++){
				if(! (boolean)model.getValueAt(i,0) ){
					//do nothing
				}else{
					model.setValueAt(reason, i, 11);
				}
			}
		}// end of else ++++++++
	} //--------------------------------------------
	
	//=============================================
	public void overridePriceAdjustment(){ 
		DefaultTableModel model = (DefaultTableModel) tableSetPrice.getModel();
		String adj = dataAdjustNumber.getText();
			//if user input is not valid
		if(! Inventory.validateStrToDouble(dataAdjustNumber.getText())){
			JOptionPane.showMessageDialog(null, "Please enter valid number - i.e. to increase $1.20, enter 1.2");
			dataAdjustNumber.setText("");
		}else { //++++++++++++++
			//override percentage adjustment column only if check column is checked 
			for(int i = 0; i < model.getRowCount(); i++){
				if(! (boolean)model.getValueAt(i,0) ){
					//do nothing
				}else{
					model.setValueAt("", i, 8);
					model.setValueAt(adj, i, 9);
				}
			}
		}// end of else ++++++++
	} //--------------------------------------------
	
	//=============================================
	public void overridePercentage(){ 
		DefaultTableModel model = (DefaultTableModel) tableSetPrice.getModel();
		String per = dataPercentage.getText();
			//if user input is not valid
		if(! Inventory.validateStrToDouble(dataPercentage.getText())){
			JOptionPane.showMessageDialog(null, "Please enter valid percentage. i.e. to increase 20%, enter 20");
			dataPercentage.setText("");
		}else { //++++++++++++++
			//override percentage adjustment column only if check column is checked 
			for(int i = 0; i < model.getRowCount(); i++){
				if(! (boolean)model.getValueAt(i,0) ){
					//do nothing
				}else{
					model.setValueAt("", i, 9);
					model.setValueAt(per, i, 8);
				}
			}
		}// end of else ++++++++
	} //--------------------------------------------
	
	//===========================================================================
	public void createTableSetPrice(){
		
		Vector<Integer> selectedRows = inventoryGetSelectedRows();
		if (selectedRows.size() == 0){
			JOptionPane.showMessageDialog(null, "Please check mark at least one product");
		} else {
		
		//tabbedPane_Inventory.setEnabledAt(6, true);
		String selectedProductIDs = "";
		for (int i = 0; i < selectedRows.size(); i++){
				if(i == 0 ){ 
					selectedProductIDs = selectedProductIDs.concat(selectedRows.get(i).toString());
				}else {
					selectedProductIDs = selectedProductIDs.concat(", ").concat(selectedRows.get(i).toString());
				}
			}
		
		//System.out.println("selectedPRoductIDs/" + selectedProductIDs);
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();
	    try{
	    	
	    	Connection con = Connect.connectionSetup();
	    	String query = "Select p.ID, c.Name as Category, sc.Name as 'Sub Category', p.Name, p.Quantity, "
	    			+ "p.UnitCost, p.SalePrice "
	      			//+ " SUM(od.OrderedQuantity) - SUM(od.ReceivedQuantity) as 'to be delivered'	"
	    			+ " FROM Product p "
	    			+ " LEFT JOIN Category as c on c.ID = p.CategoryID"
	    			+ "	LEFT JOIN Category as sc on sc.ID = p.SubCategoryID "
	    			+ " LEFT JOIN orderdetail as od on od.ProductID = p.ID "
	    			+ " WHERE p.ID IN ( " + selectedProductIDs + " )"
	    			+ "	GROUP BY p.ID";
	    			
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData md = rs.getMetaData();

			int columnCount = md.getColumnCount();
			
			columnNames.addElement("Check"); 	
			columnNames.addElement("ID"); 				
			columnNames.addElement("Category");			
			columnNames.addElement("Sub Category");		
			columnNames.addElement("Name");				
			columnNames.addElement("Quantity on Hand");	
			columnNames.addElement("Unit Cost");
			columnNames.addElement("Sale Price");			
			columnNames.addElement("% Adjustment");		
			columnNames.addElement("Price Adjustment");	
			columnNames.addElement("New Sale Price");	
			columnNames.addElement("Reason");
			

			//Get row data
			while(rs.next())
			{
				Vector<Object> row = new Vector<Object>(columnCount);
				boolean check = true; 
				row.addElement(check);
					for (int i = 1; i <= columnCount; i++)  //column count in the result set
					{
						row.addElement(rs.getObject(i));
					}
				data.addElement(row);
			}
			
			pst.close();
			rs.close();
			con.close();
			
	    }catch (Exception e1) 
		{	
		e1.printStackTrace();
		}
	    
	    //inner class
	    class TM_setPrice extends DefaultTableModel{//********************************************
	    	public TM_setPrice (Vector<Object> data, Vector<Object> columnNames) {
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
	    	    case 0: return true;
	    		case 8: return true;
	    		case 9: return true;
	    		case 11: return true;
	    		
	    		default: return false;
	    	    }	
	    	}
	    }
	    
	    TM_setPrice setPriceModel = new TM_setPrice(data, columnNames);
	    tableSetPrice = new JTable(setPriceModel);
	    tableSetPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
	    tableSetPrice.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
	    
	    /*{
	    	
			public TableCellEditor getCellEditor(int row, int col){
				int modelColumn = convertColumnIndexToModel(col);
				DefaultTableModel model = (DefaultTableModel) tableSetPrice.getModel();
				
					//Current sale price is in column 6
					//change in adjustment % column 
				if (modelColumn == 8 ){
					if(model.getValueAt(row, col) != null){
						double percent = Double.parseDouble(model.getValueAt(row, col).toString());
						percent = percent / 100; 
						double adjustment = Double.parseDouble(model.getValueAt(row, 6).toString()) * percent;
						double curSP = Double.parseDouble(model.getValueAt(row, 6).toString());
						model.setValueAt(curSP + adjustment, row, 10);
					}

					
				}//end of column 8
				
				if (modelColumn == 9 ){
					
				}
				//else return super.getCellEditor(row, column);
				return cellEditor;
			} 
		};*/
		
		
		//-----------------------------------------
		
		TableModelListener TMLNewSalePrice = new TableModelListener(){
			
		TableModel model = tableSetPrice.getModel();
		
			@Override 
			public void tableChanged(TableModelEvent e){
				if(e.getType() == TableModelEvent.UPDATE){
					
					int row = e.getFirstRow();
					int col = e.getColumn();
					//boolean validPrice = true;
					
						//if percentage is allocated update both adjustment amount and new price
					if (col == 8 && ! model.getValueAt(row, col).toString().equals("") ) {//777777
						
						if(!Inventory.validateStrToDouble(model.getValueAt(row, col).toString())){
							JOptionPane.showMessageDialog(null, "Please enter valid percentage - i.e. to increase 20%, enter 20");
							model.setValueAt("", row, col);
							model.setValueAt("", row, 10);
						}else if(Double.parseDouble(model.getValueAt(row, col).toString()) > 200){
							JOptionPane.showMessageDialog(null, "Price cannot be increased more than 200%");
							model.setValueAt("", row, col);
							model.setValueAt("", row, 10);
						}else if(Double.parseDouble(model.getValueAt(row, col).toString()) < -99){
							JOptionPane.showMessageDialog(null, "Price cannot set to 0");
							model.setValueAt("", row, col);
							model.setValueAt("", row, 10);
						}else{
							double adjPercentage = Double.parseDouble( model.getValueAt(row, col).toString());
							adjPercentage = adjPercentage / 100;
							double curPrice = Double.parseDouble(model.getValueAt(row, 7).toString());
							double newPrice = curPrice + (curPrice * adjPercentage);
								//clear price adjustment
							model.setValueAt("" , row, 9);
							model.setValueAt(newPrice, row, 10);
						}

					}//777777
					
					if(col == 9 && ! model.getValueAt(row, col).toString().equals("") ) {//8888888
						if(!Inventory.validateStrToDouble(model.getValueAt(row, col).toString())){
							JOptionPane.showMessageDialog(null, "Please enter valid number - i.e. to increase $1.20, enter 1.2");
							model.setValueAt("", row, col);
							model.setValueAt("", row, 10);
						}else if( Double.parseDouble(model.getValueAt(row, col).toString()) +  Double.parseDouble(model.getValueAt(row, 7).toString()) > Double.parseDouble(model.getValueAt(row, 7).toString()) * 2){
							JOptionPane.showMessageDialog(null, "New price cannot be set to more than double from current price");
							model.setValueAt("", row, col);
							model.setValueAt("", row, 10);
						}else if(   (Double.parseDouble(model.getValueAt(row, 7).toString()) + Double.parseDouble(model.getValueAt(row, col).toString())) <= 0 ){
							JOptionPane.showMessageDialog(null, "New price cannot be set to 0");
							model.setValueAt("", row, col);
							model.setValueAt("", row, 10);
						}else {
							double adjustment = Double.parseDouble(model.getValueAt(row, 9).toString());
							double curPrice = Double.parseDouble(model.getValueAt(row, 7).toString());
								//clear percentage 
							model.setValueAt("", row, 8);
							double newPrice = curPrice + adjustment;
							model.setValueAt(newPrice, row, 10);
						}
					}//88888888888
					
					
				}
			}
		}; //tml_newSalePrice
		tableSetPrice.getModel().addTableModelListener(TMLNewSalePrice);
		//table_inventoryList.getColumnModel().getColumn(11).setCellRenderer(new TCellRend_newSalePrice());
		//TableRowSorter sorter_2 = new TableRowSorter<TM_CreateOrder>(MyModel_2);
		//table_createOrder.setRowSorter(sorter_2); 
	    
		tableSetPrice.setRowHeight(30);
		
		scrollpane_7.setViewportView(tableSetPrice);
		//setTMLforCreateOrderTable(); //function to add table model listner
		
		Dimension tableSize = tableSetPrice.getPreferredSize();
		TableColumnModel tcm2 = tableSetPrice.getColumnModel();
		tcm2.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); 
		tcm2.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); 
		tcm2.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); 
		tcm2.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(8).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(9).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
		tcm2.getColumn(10).setPreferredWidth(Math.round((tableSize.width)* 0.05f));
		tcm2.getColumn(11).setPreferredWidth(Math.round((tableSize.width)* 0.05f));

		//createOrderSummary(selectedProductIDs); placed inside createOrderTable();
		//tabbedPane_Inventory.setEnabledAt(6,true);
		//tabbedPane_Inventory.setSelectedIndex(6);
		cardlayout.show(inventoryMainPanel, "333");
		currentPage = 3;
		}//else 
	}
	
	
	//===========================================================================
	public void createOrderAndOrderDetail(){
		
		String error1 = "";
		String error2 = "";
		String warning = "";
		Set<Integer> uniSupID_list = new HashSet<Integer>();
		int prodID_col = 0;
		int sup_col = 6;
		int qty_col = 8;
		int prodName_col = 3;
		int cat_col = 1;
		int subCat_col = 2;
		int orderID = 0;
		int orderCount = 0; 
		DefaultTableModel summaryModel = (DefaultTableModel) tableNewOrderSummary.getModel();
			

			// check each row in tableNewOrder and make sure that order is properly placed 
			for (int i = 0; i < tableNewOrder.getRowCount(); i++){//2222222222
				
				if ((tableNewOrder.getValueAt(i, sup_col) == null) || (tableNewOrder.getValueAt(i, qty_col) == null) ){
					error1 =  "Supplier and order quantity cannot be left blank.\n";
				}else if(Integer.parseInt(tableNewOrder.getValueAt(i, qty_col).toString()) <= 0){
					error2 = "Each product's order quantity must be at least one\n";
					tableNewOrder.setValueAt("0",i, qty_col);
				}
			}//2222222222222222222222
			
			
			if (error1.concat(error2).length() > 1){ 
				JOptionPane.showMessageDialog(null, error1.concat(error2));
			}else { //33333333333333333
				
				//check order summary table and warn user
				for (int i = 0; i < summaryModel.getRowCount(); i++){//1111111111111111
					double minOrder = Double.parseDouble(summaryModel.getValueAt(i, 3).toString());
					double orderEstimate = Double.parseDouble(summaryModel.getValueAt(i, 4).toString());
					String supName = summaryModel.getValueAt(i, 1).toString();
					int supID = Integer.parseInt(summaryModel.getValueAt(i, 0).toString());
					
					if( minOrder > orderEstimate ){
						warning = warning.concat(" - Order for " + supName + " does not meet minimum order requirement - this order will be discarded\n");
					}else {
						uniSupID_list.add(supID);
					}
				}//11111111111111111111111

			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog (null, "This will create new order(s).\n"
					+ warning + "Do you want to proceed?", "New Order(s) will be created", dialogButton);
			if(dialogResult == JOptionPane.YES_OPTION) {
				
				try{
					Connection connForCreateOrder = Connect.connectionSetup();
					PreparedStatement pst;
					//loop through each unique supplier and create order and order details for the supplier
			   	
				for ( Iterator<Integer> it = uniSupID_list.iterator(); it.hasNext(); ){
				
				int supplierID = it.next(); 
				Vector<Object> detailList = new Vector<Object>();
				
					//get new order ID
				String query2 = "SELECT Max(ID) FROM `order` ";
				pst = connForCreateOrder.prepareStatement(query2);
				ResultSet rs = pst.executeQuery(); 	
				
				while(rs.next()){
					orderID =  (int) rs.getObject(1) + 1;	// new ID will be max id + 1		
				}
					//insert new order (Cost should be removed from this sql)
				String query = "Insert into `order` (SupplierID, EmployeeID, CreateDate, Cost ) "
						                 + "VALUES (?, ?, NOW() , 0)";		
				pst = connForCreateOrder.prepareStatement(query);
				pst.setInt(1, supplierID);
				pst.setInt(2, 1);
				pst.executeUpdate(); 
				orderCount++;
				
					//insert new order details 
				for (int k = 0; k < tableNewOrder.getRowCount(); k++){ //for #20
					
					CbSupItem item = (CbSupItem) tableNewOrder.getValueAt(k, sup_col);
					if (supplierID == item.getID()){ //if #21
						
						//OrderDetailsInfo ordDetail = new OrderDetailsInfo();
						
						int productID =  (int)tableNewOrder.getValueAt(k, prodID_col);
						int orderQty =  Integer.parseInt((String) tableNewOrder.getValueAt(k, qty_col)) ;
						String prodName = (String) tableNewOrder.getValueAt(k, prodName_col);
						String category = (String) tableNewOrder.getValueAt(k, cat_col);
						String subCategory = (String) tableNewOrder.getValueAt(k, subCat_col);
						
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
				
				}//end of for Iterator <Integer> ....
			
				connForCreateOrder.close();
				
				scrollPane_ceateOrder.setViewportView(null);
				JOptionPane.showMessageDialog(null, orderCount + " order(s) created");
				createTableInventory();
				cardlayout.show(inventoryMainPanel, "444");
				currentPage = 4;
				
				createTableOrderList();
				createTableOrderDetailList(orderID); //displays the most recent order
				tableOrderList.setRowSelectionInterval(0, orderCount -1);
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} // end of Yes option 

			}//333333333333333333
	} // end of function -------------------------------------------------------------------
	
	//======================================================================
	public void createTableQtyHistory(){
		
		Connection con;
		try {
			con = Connect.connectionSetup();
			
			String prodIDStr = dataProdID.getText();
			
			String query = "Select ProductID, Quantity, ChangeDate, Reason "
					+ "From QAdjustment WHERE productID =" + prodIDStr + " Order by ChangeDate Desc";
		 PreparedStatement pst = con.prepareStatement(query);
		 ResultSet rs = pst.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int colCount = md.getColumnCount();
		 
			Vector<Object> columnNames = new Vector<Object>();
		    Vector<Object> data = new Vector<Object>();
		    
		    columnNames.addElement("Prodct ID");
		    columnNames.addElement("Qty Change");
		    columnNames.addElement("Change Date");
		    columnNames.addElement("Reason");
		    
		    while(rs.next()){
		    	Vector<Object> row = new Vector<Object>();
		    	for (int i = 1; i <= colCount; i++){
		    		row.addElement(rs.getObject(i));
		    	}
		    	data.addElement(row);
		    }
		    
		    TM_basic basicModelforQtyHistory = new TM_basic(data, columnNames);
		    tableQtyHistory.setModel(basicModelforQtyHistory);
		    tableQtyHistory.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		    tableQtyHistory.setRowHeight(30);
		 
		 con.close();
		 pst.close();
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}



//==============================================
	//===========================================
	public void createTablePriceHistory(){
		
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
			    tablePriceHistory.setModel(basicModelforPriceHistory);
			    tablePriceHistory.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
			    tablePriceHistory.setRowHeight(30);
			 
			 con.close();
			 pst.close();
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	
	//==============================================
	public static void createTableOrderList(){
		
		try {
			Connection con_Inv = Connect.connectionSetup();

		String query = "Select o.ID, s.Name as 'Supplier Name', COUNT(od.OrderID), CreateDate, ReceivedDate, InvoiceID, o.Cost, o.AmountPaid  "
				+ "FROM `order` o "
				+ "Inner JOIN Supplier s ON s.ID = o.SupplierID "
				+ "LEFT JOIN OrderDetail od ON o.ID = od.OrderID "
				+ "Group BY o.ID "
				+ "ORDER BY ID DESC;";
		
		PreparedStatement pst = con_Inv.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		int colCount = md.getColumnCount();
		
	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    columnNames.addElement("Order ID");
    columnNames.addElement("Supplier");
    columnNames.addElement("Item Count");
    columnNames.addElement("Created");
    columnNames.addElement("Received");
    columnNames.addElement("Invoice ID");
    columnNames.addElement("Order Cost");
    columnNames.addElement("Paid Amount");
  
    
    while(rs.next()){
    	Vector<Object> row = new Vector<Object>();
    	for (int i = 1; i <= colCount; i++){
    		row.addElement(rs.getObject(i));
    	}
    	data.addElement(row);
    }
    
    TM_basic orderModel = new TM_basic(data, columnNames);
    tableOrderList.setModel(orderModel);
    tableOrderList.setRowHeight(30);
    
	Dimension tableSize = tableOrderList.getPreferredSize();
	TableColumnModel tcm = tableOrderList.getColumnModel();
	
	
		tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //orderID
		tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); //supplier
		tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); //item count
		tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //created date
		tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.15f)); //received date
		tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //invoice id
		tcm.getColumn(6).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //cost 
		tcm.getColumn(7).setPreferredWidth(Math.round((tableSize.width)* 0.10f)); //paid amount 

    
		tableOrderList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseE) {
			if (mouseE.getClickCount() == 2) {
			    JTable target = (JTable)mouseE.getSource();
			    int row = target.getSelectedRow();
			    int orderID = (int) target.getValueAt(row, 0);
			    
			    createTableOrderDetailList(orderID);

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
	
	public static void createTableOrderDetailList(int orderID){
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
    tableOrderDetail.setModel(orderDetailModel);
    tableOrderDetail.setRowHeight(30);
    tableOrderDetail.getColumnModel().getColumn(6).setCellRenderer(new TCellRend_orderDetail());
    tableOrderDetail.getColumnModel().getColumn(7).setCellRenderer(new TCellRend_orderDetail());
    
    TableModelListener TListner = new TableModelListener(){
    	
    	TableModel model = tableOrderDetail.getModel();
    	
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
						model.setValueAt("0", row, col);
					}
				}

			}
		}
    };
    
    tableOrderDetail.getModel().addTableModelListener(TListner);
    
	Dimension tableSize = tableOrderDetail.getPreferredSize();
	TableColumnModel tcm = tableOrderDetail.getColumnModel();
	
	
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
	public void createTableSoldBy(){
		

		try {
			
			String query = "Select s.id, s.name "
					+ "from Supplier s "
					+ "Inner join product_supplier ps ON s.id = ps.supplierID "
					+ "Inner join product p on p.id = ps.productID "
					+ "where p.id = "+ dataProdID.getText() +" AND "
					+ "Status = 'Active'";
			
			//String query = "Select s.ID, s.Name, SUM(od.OrderedQuantity), SUM(od.ReceivedQuantity), SUM(od.OrderedQuantity -  od.ReceivedQuantity)"
			//		+ " FROM Product p "
			//		+ " LEFT JOIN Product_Supplier ps ON p.ID = ps.ProductID"
			//		+ "	LEFT JOIN Supplier s ON s.ID = ps.SupplierID"
			//		+ " LEFT JOIN OrderDetail od ON p.ID = od.ProductID"
			//		+ " WHERE p.ID = " + dataProdID.getText() + " GROUP BY s.ID ";
					
					
			PreparedStatement pst = con_Inv.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int colCount = md.getColumnCount();
			
		Vector<Object> columnNames = new Vector<Object>();
	    Vector<Object> data = new Vector<Object>();
	    
	    columnNames.addElement("Supplier ID");
	    columnNames.addElement("Supplier Name");
	    //columnNames.addElement("Ordered Qty");
	    //columnNames.addElement("Received Qty");
	    //columnNames.addElement("To be delivered");
	    String suppliers = "";
	    
	    while(rs.next()){
	    	Vector<Object> row = new Vector<Object>();
	    	for (int i = 1; i <= colCount; i++){
	    		
	    		row.addElement(rs.getObject(i));
	    		if(i==1){
	    			suppliers = suppliers.concat(Integer.toString((int) rs.getObject(i)) + " ,");
	    		}
	    	}
	    	data.addElement(row);
	    }
	    
	    TM_basic basicModel = new TM_basic(data, columnNames);
	    tableProductSoldBy.setModel(basicModel);
	    tableProductSoldBy.setRowHeight(30);
	    
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
	
	//====================================
	public void updateQty(){
		
		boolean proceed = false; 
		int prodID = Integer.parseInt(dataProdID.getText().toString());
		
		if(dataQtyAdjustment.getText().equals("") || dataQtyAdjReason.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Please fill both quantity and reason");
		}else if (!validateStrToInt( dataQtyAdjustment.getText() ) ){
			JOptionPane.showMessageDialog(null, "Please enter a valid number");
			dataQtyAdjustment.setText("");
		}else if (  Integer.parseInt(dataQty.getText()) + Integer.parseInt(dataQtyAdjustment.getText()) < 0   ){
			JOptionPane.showMessageDialog(null, "Invalid adjustment: Quantity on Hand cannot be a negative number");
		}else if (dataQtyAdjReason.getText().length() > 100 ){
			JOptionPane.showMessageDialog(null, "Reason must be 100 or less characters");
			String curReason = dataQtyAdjReason.getText();
			curReason = curReason.substring(0, 99);
			dataQtyAdjReason.setText(curReason);
		}else {
			proceed = true; 
		}
		
		if(proceed == true){
			int qtyAdjustment = Integer.parseInt(dataQtyAdjustment.getText().toString());
			int qtyOnHand = Integer.parseInt(dataQty.getText().toString());
			int updatedQtyOnHand = qtyOnHand + qtyAdjustment; 
			String reason = dataQtyAdjReason.getText();
			
			try {
				Connection con = Connect.connectionSetup();
				String query = "Update Product Set Quantity = ? WHERE ID = ?";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setInt(1, updatedQtyOnHand);
				pst.setInt(2, prodID);
				pst.executeUpdate();
				
				
				query = "Insert Into QAdjustment (ProductID, Quantity, Reason, ChangeDate) "
						+ "Values (?, ?, ?, NOW()) ";
				pst = con.prepareStatement(query);
				pst.setInt(1, prodID);
				pst.setInt(2, qtyAdjustment);
				pst.setString(3, reason);
				pst.executeUpdate();
				
				//refresh data
				createTableQtyHistory();
				createTableInventory();
				dataQtyAdjustment.setText("");
				dataQtyAdjReason.setText("");
				dataQty.setText(Integer.toString(updatedQtyOnHand));
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	//====================================
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
				 createTableInventory();
				 //updateInv_priceHisTable();
				 
				 for(int i = 0; i < tableInventory.getRowCount(); i ++){
						int selectedID = (int) tableInventory.getValueAt(i, 1);
						if (selectedID == Integer.parseInt(prodID)){
							tableInventory.getSelectionModel().setSelectionInterval(i, i);
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
		if (tableOrderDetail.getValueAt(0, 0) != null){
			orderID = Integer.parseInt(tableOrderDetail.getValueAt(0, 0).toString());
			
			if (! dataInvoiceNumber.getText().equals("")){
			String query = "Update `order` Set InvoiceID = " + dataInvoiceNumber.getText() +" Where id =" + orderID;
			PreparedStatement pst = con_Inv.prepareStatement(query);
			pst.executeUpdate();
		    }
			
			createTableOrderList();
			createTableOrderDetailList(orderID);
			dataInvoiceNumber.setText("");
		    for(int i=0; i < tableOrderList.getRowCount() ; i++){
		    	if(orderID == Integer.parseInt(tableOrderList.getValueAt(i, 0).toString())){
		    		tableOrderList.setRowSelectionInterval(i, i);
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
			//System.out.println("Starting updateOrderDetail-----------");
			TableModel model = tableOrderDetail.getModel();
			if(tableOrderDetail.isEditing()){
				tableOrderDetail.getCellEditor().stopCellEditing();
				}
			Connection con = Connect.connectionSetup();
			PreparedStatement pst;

			boolean detailUpdated = false;
			int orderID = 0;
			
			//check if 
			if(model.getRowCount() == 0){
				JOptionPane.showMessageDialog(null, "Please double click a row in Order List to display details");
			}
			
				//loop through order detail table 
			for (int i = 0; i < model.getRowCount(); i++){
				
				int qtyToAdd = 0;
				double costToAdd = 0;
				double extraCost = 0;
				int curQty =0;
				double curTotalCost = 0; 
				int prodID = 0;
				if(model.getValueAt(i, 1) != null){
					prodID = (int)model.getValueAt(i, 1);
				}
				

				//both received quantity and cost for this delivery must be filled and not zero
				if( (model.getValueAt(i, 6) == null || model.getValueAt(i, 7) == null) || 
					(Integer.parseInt(model.getValueAt(i, 6).toString()) <= 0 || Double.parseDouble(model.getValueAt(i, 7).toString()) <= 0)	)
					{
					JOptionPane.showMessageDialog(null, "Please fill received quantity and cost with positive numbers\n"
							+ "Line for ProductID: " + prodID + " will not be updated");
					}else if(
							(model.getValueAt(i, 6) != null && model.getValueAt(i, 7) != null) && 
							(Integer.parseInt(model.getValueAt(i, 6).toString()) > 0 && Double.parseDouble(model.getValueAt(i, 7).toString()) > 0)	)
							
							{
				
						qtyToAdd = Integer.parseInt((String) model.getValueAt(i, 6));
						costToAdd = Integer.parseInt((String) model.getValueAt(i, 7)); 
						if(model.getValueAt(i, 8) != null ){extraCost =  (double) model.getValueAt(i, 8);}
						if(model.getValueAt(i, 4) != null){	curQty = Integer.parseInt( model.getValueAt(i, 4).toString() );}
						if(model.getValueAt(i, 5) != null){	curTotalCost = Double.parseDouble( model.getValueAt(i, 5).toString() );}
						
						orderID = (int) model.getValueAt(i, 0);
					    prodID = (int) model.getValueAt(i, 1);
						
						String query = "Update orderDetail Set cost = " + (curTotalCost + costToAdd + extraCost) +", "
								+ "ReceivedQuantity = " + ( curQty + qtyToAdd ) + " "
								+ "Where OrderID = " + orderID + " AND ProductID = " + prodID ;	
						pst = con.prepareStatement(query);
						pst.executeUpdate();
						
						
						//get quantity and unit cost in the product table and update it
						int qtyOnHand= 0;
						double curUnitCost = 0; 
						double prodValue = 0; 
						
						query = "Select Quantity, UnitCost FROM Product WHERE ID = " + prodID;
						pst = con.prepareStatement(query);
						ResultSet rs = pst.executeQuery();
						
						while(rs.next()){
							qtyOnHand = rs.getInt(1);
							curUnitCost = rs.getDouble(2);
							}
								//calculate current product value 
							prodValue = qtyOnHand * curUnitCost; 
														
								//add cost to prodValue, which results in the total value for the product
							prodValue = prodValue + (costToAdd + extraCost);
							
							double newUnitCost = prodValue / (qtyOnHand + qtyToAdd);
							
							
							//update unit cost and quantity on hand using the new unit cost
							query = "Update Product Set UnitCost = " + newUnitCost + ", Quantity = " + (qtyOnHand + qtyToAdd) + ""
									+ " WHERE ID = " +prodID ;
							pst = con.prepareStatement(query);
							pst.executeUpdate();
							
							//update QAdjustment table 
							query = "Insert Into QAdjustment (ProductID, Quantity, Reason, ChangeDate) Values (?,?,?, NOW())";
							
							pst = con.prepareStatement(query);
							pst.setInt(1, prodID);
							pst.setInt(2, qtyToAdd);
								String reason = "Order Received - OrderID: " + orderID;
							pst.setString(3, reason);
							pst.executeUpdate();
							
							//finally, update supplier_product table's unit cost specific to a supplier
							//first, get supplier id based on the orderID
							int supID = 0;
							query = "Select SupplierID FROM `order` WHERE ID = " + orderID;
							pst = con.prepareStatement(query);
							rs = pst.executeQuery();
							while(rs.next()){
								supID = rs.getInt(1);
							}
							// then update unit cost in the product_supplier table
		
							query = "Update product_supplier Set UnitCost = " + ( (costToAdd + extraCost) / qtyToAdd ) + ""
									+ " WHERE ProductID = " + prodID + " AND SupplierID = " + supID ;
							pst = con.prepareStatement(query);
							pst.executeUpdate();
								
						detailUpdated = true;
						
							// update order "received date" if it is empty
						for (int k = 0; k < tableOrderList.getRowCount() ; k++){ //444444
							if (orderID == (int)tableOrderList.getValueAt(k, 0) ){ //333333333
								//if something is delivered, and received date is empty, update received date
								if(tableOrderList.getValueAt(k, 4) == null && detailUpdated){
									query = "Update `order` Set receivedDate = sysdate() Where id =" + orderID;
									pst = con.prepareStatement(query);
									pst.executeUpdate();
									detailUpdated = true;
								}
							} // 3333333333
						}//4444444
						
						
				}// end of if both quantity and price is entered 
				

			}// end of looping orderDetail table
			

			
			if (detailUpdated){
				
							//update order detail table
				createTableOrderDetailList(orderID);
				
				double orderTotalCost = 0; 
				//double otherCost = 0;
				for(int m = 0; m < tableOrderDetail.getRowCount(); m++){
					//if(tableOrderDetail.getValueAt(m, 8) != null){
					//	otherCost = Double.parseDouble(tableOrderDetail.getValueAt(m, 8).toString());
					//}
				    orderTotalCost = orderTotalCost + Double.parseDouble(tableOrderDetail.getValueAt(m, 5).toString());
					}
				//update order's cost using the sum of cost from each order detail
				
				String query = "Update `order` Set Cost  = ? WHERE ID = ? ";
				pst = con.prepareStatement(query);
				pst.setDouble(1, orderTotalCost);
				pst.setInt(2, orderID);
				pst.executeUpdate();
				
				
				createTableInventory();
				createTableOrderList();
				JOptionPane.showMessageDialog(null, "Order Updated");
				
			    for(int i=0; i < tableOrderList.getRowCount() ; i++){
			    	if(orderID == Integer.parseInt(tableOrderList.getValueAt(i, 0).toString())){
			    		tableOrderList.setRowSelectionInterval(i, i);
			    	}
			    }
			}

		    con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public void distributeDeliveryCost(){ //=====================================
		if(tableOrderDetail.isEditing()){
			tableOrderDetail.getCellEditor().stopCellEditing();
		}
		String cost = dataDeliveryCost.getText();
		if( ! validateStrToDouble(cost)){
			JOptionPane.showMessageDialog(null, "Please enter a number in Delivery Cost field");
		}else {
			
			int totalQty = 0;
			for(int i =0; i < tableOrderDetail.getRowCount(); i++){
				if(tableOrderDetail.getValueAt(i, 6) != null){
					totalQty += Integer.parseInt((String) tableOrderDetail.getValueAt(i, 6));	
				}
			}
			
			System.out.println("Total qty is" + totalQty);
			if(totalQty == 0){
				JOptionPane.showMessageDialog(null, "Please update Received Quantity column");
			}else {
					for(int i = 0; i < tableOrderDetail.getRowCount(); i++){
						if(tableOrderDetail.getValueAt(i, 6) != null){
						Double thisQty = Double.parseDouble((String)tableOrderDetail.getValueAt(i, 6));
						Double percentage = thisQty / totalQty;
						System.out.println("Percentage is" + percentage);
						double thisCost = (Double.parseDouble(cost)) * percentage;
						tableOrderDetail.setValueAt(thisCost, i, 8);
						}

					}
			}

		}

		
	}
	
	
	public void displayProductDetail(int prodID){
		
		//tabbedPane_Inventory.setEnabledAt(2, true);
		
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
			
			
			//update tables
			createTableSoldBy();
			createTablePriceHistory();
			createTableQtyHistory();
		    //close connection
		    con_Inv.close();
			pst.close();
			rs.close();
			
			cardlayout.show(inventoryMainPanel, "222");
			currentPage = 2;
			//tabbedPane_Inventory.setSelectedIndex(2);


			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	//=========================================================================================
	// Table Model Listener for updating prev @ price, Order Total column for table_createORder
	private void setTMLforCreateOrderTable(){
		tml_CreateTable = new TableModelListener(){
		
			TableModel model = tableNewOrder.getModel();
			TableModel model_summary = tableNewOrderSummary.getModel();
			
			
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


					
					if (addRowToSummary == true ) {
						
						//------------------------------------
						//loop through createOrdertable and gather order summary for this supplier 
						orderTotalforThisSup = 0;
						for (int i = 0; i < tableNewOrder.getRowCount(); i++){
							
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
		tableNewOrder.getModel().addTableModelListener(tml_CreateTable);
	} //setTMLforCreateOrder()
	*/
	//===========================================================================
	public void createTableInventory(){ 

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
		columnNames.addElement("Product ID");
		columnNames.addElement("Category");
		columnNames.addElement("Sub Category");
		columnNames.addElement("Name");
		columnNames.addElement("Description");
		columnNames.addElement("Quantity");
		columnNames.addElement("To be delivered");
		columnNames.addElement("Sale Price");
		columnNames.addElement("Unit Cost");
		columnNames.addElement("Notes");
		
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
        	tableInventory.setModel(MyModel);
        	tableInventory.getTableHeader().setReorderingAllowed(false);
        	sorter = new TableRowSorter<MyTableModelClass>(MyModel);
        	tableInventory.setRowSorter(sorter);
        	filterFrame.clearFilter();
        	
        	tableInventory.addMouseListener(new MouseAdapter() {
        			public void mouseClicked(MouseEvent mouseE) {
        			if (mouseE.getClickCount() == 2) {
        			    JTable target = (JTable)mouseE.getSource();
        			    int row = target.getSelectedRow();
        			    int prodID = (Integer)target.getValueAt(row, 1);
        			   
        			    displayProductDetail(prodID);
        			    

        			}
        		}
        	});

		Dimension tableSize = tableInventory.getPreferredSize();
		tcm = tableInventory.getColumnModel();
		
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
			tcm.getColumn(10).setPreferredWidth(Math.round((tableSize.width)* 0.15f));//Notes
		
	}// end of createTableInventory()
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
/*
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
							pickOrderTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
							createTablePickOrder(supID);
							scrollPane_6.setViewportView(pickOrderTable);
						}
					}
					);

		}// end of JPanelAddNewInvoice() constructor 
		 
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
			
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
		}// end of createTableOrderPick-------------------------------------------------------

	}*/
	
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
        
                   if (value == "Add") { //..................................
                	   
                	   panel.setMessage("");
                	   boolean success = true;
                	   String error = "";
                	   CbSupItem sup; 
                	   DefaultTableModel model = (DefaultTableModel) panel.pickOrderTable.getModel();
                	   
                	   //check if supplier is selected
                	   String supInvoiceID = panel.getSupInvoiceID();
                	   if(panel.getSupCb().getID() != 0){
                		   sup = panel.getSupCb();
                		   }else{
                			   error = error.concat("Please select a supplier \n");      
                			   };		   
                	   
                	   	//check if amount due is valid
                	   String dueAmount = panel.getAmountDue();
                	   //String paidAmount = panel.getAmountPaid();
                	   Date pickedDate = panel.getReceivedDate();
                	   String supInvID = panel.getSupInvoiceID();

                	   
                	   if(dueAmount.equals("")){
                		   error = error.concat("Amount Due is a required field\n");
                	   }else if( ! Inventory.validateStrToDouble(dueAmount) && !dueAmount.equals("")){
            		   error = error.concat("Amount Due must be a valid number\n");
            		   }else if(Double.parseDouble(dueAmount) <= 0){
            			   error = error.concat("Amout Due is a required field: cannot be a negative number or zero\n");
            		   }
            			 
            	   		
            	   			//check suppplier invoice id 
            	   		if(supInvID.equals("")){
            	   			error = error.concat("Supplier invoice #/ID is a required field: please enter a number\n");
            	   		}else if (! Inventory.validateStrToDouble(supInvID)){
             			   error = error.concat("Supplier invoice #/ID must be a number\n");
             		   }
            	   		 	//check if orders have been selected to associate
            	   		if(panel.getSupCb().getID() != 0 && model.getRowCount() == 0){
            	   			error = error.concat("Currently no order to associate for this supplier.\nNew Invoice cannot be created.");
            	   		}else{
            	   			
            	   			int checkedOrderCount = 0; 
            	   			for(int k = 0; k < model.getRowCount(); k++){
            	   				if(model.getValueAt(k, 0) == Boolean.TRUE){
            	   					checkedOrderCount++;
            	   				}
            	   			}
            	   			if (checkedOrderCount == 0){
            	   				error = error.concat("Please select at least one order to associate");
            	   			}
            	   			
            	   		}
            	   	
                	   
             		   
             		   // if "error" string is empty, operation is successful
	                   if(error.length() < 1){
	                	    	
	                	    	//if dueAmount  is empty, assign 0
	                	    	//if(dueAmount.equals(""))dueAmount = "0";
	                	    	//if(paidAmount.equals(""))paidAmount = "0";
	                	    	
	                	    	//insert new invoice to invoice table 
	                	    	try {
	                				Connection con = Connect.connectionSetup();
	                				String query = "Insert Into invoice (AmountDue, ReceivedDate, SupplierID, Sup_InvoiceID) "
	                						+ " Values (?, ?, ?, ?)"; 
	                						
	                				PreparedStatement pst = con.prepareStatement(query);
	                				pst.setDouble(1, Double.parseDouble(dueAmount));
	                				//pst.setDouble(2, Double.parseDouble(paidAmount));
	                				pst.setDate(2, new java.sql.Date(pickedDate.getTime()));
	                				pst.setInt(3, panel.getSupCb().getID());
	                				pst.setInt(4, Integer.parseInt(supInvID));
	                				
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
									Inventory.createTableOrderList();
									Inventory.createTableInvoice();
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
                	   String dueAmountStr = panel.getAmountDue();
                	   String paidAmountStr = panel.getAmountPaid();
                	   Date pickedDate = panel.getReceivedDate();
                	   String supInvID = panel.getSupIDString();
                	   //System.out.println("picked date is " + pickedDate);
                	  // System.out.println("amountPaid is/" + paidAmount + "/");
                	   //System.out.println("amountdue is/" + dueAmount + "/");
                	   
                	   //check amount paid 
            		   if( ! Inventory.validateStrToDouble(dueAmountStr) && !dueAmountStr.equals("")){
            		   error = error.concat("Amount Due must be a valid number\n");
            		   }else if(Double.parseDouble(dueAmountStr) < 0){
            			   error = error.concat("Amount Due cannot be a negative number");
            		   }
                	   	
            		   //check amount due 
            		   if(! Inventory.validateDouble(paidAmountStr) && ! paidAmountStr.equals("") ){ //if conversion fails
            	   				error = error.concat("Amount Paid must be a valid number\n");
            		   } else if(Double.parseDouble(paidAmountStr) < 0){
            			   error = error.concat("Paid amount cannot be a negative number");
            		   }else if( Inventory.validateDouble(paidAmountStr) && ! paidAmountStr.equals("") &&
            	   				Double.parseDouble(paidAmountStr) > Double.parseDouble(dueAmountStr)){
            	   			error = error.concat("Amount paid cannot exceed due amount \n");
            	   	   }
            	   	   
            		   //check suppplier invoice id 
            		   if (! Inventory.validateStrToDouble(supInvID)){
            			   error = error.concat("Supplier invoice #/ID must be a number");
            		   }
            	   		
                	    	// if "error" string is empty, operation is successful
	                	    if(error.length() < 1){
	                	    	
	                	    	//if dueAmount and paidAmount is empty, assign 0
	                	    	if(dueAmountStr.equals(""))dueAmountStr = "0";
	                	    	if(paidAmountStr.equals(""))paidAmountStr = "0";
	                	    	
	                	    	//update existing invoice
	                	    	try {
	                				Connection con = Connect.connectionSetup();
	                				String query = "Update invoice Set "
	                						+ " AmountDue = ?, "
	                						+ " AmountPaid =?, "
	                						+ " ReceivedDate = ?, "
	                						+ " SupplierID = ?, "
	                						+ " Sup_InvoiceID = ?"
	                						+ " WHERE ID = ? " ;
	                						
	                				PreparedStatement pst = con.prepareStatement(query);
	                				pst.setDouble(1, Double.parseDouble(dueAmountStr));
	                				pst.setDouble(2, Double.parseDouble(paidAmountStr));
	                				pst.setDate(3, new java.sql.Date(pickedDate.getTime()));
	                				pst.setInt(4, panel.getSupIDInt());
	                				pst.setInt(5, invoiceID);
	                				pst.setInt(6, Integer.parseInt(supInvID));
	                				
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
									
	                				//use amountPaid (invoice)  to fill amount paid for orders 
	                				double paidAmount = Double.parseDouble(paidAmountStr);
	                				
	                				for(int j = 0; j < rowCount; j++){  //#loop through pickOrder Table-----
	                					
	                					int orderID = (int) panel.pickOrderTable.getValueAt(j, 1);
										boolean selected = (boolean) panel.pickOrderTable.getValueAt(j, 0); 
										//if check is marked, associate it to this invoice
										if(selected){
											query = "Update `order` set InvoiceID = " + invoiceID + ""
													+ " WHERE ID = " + orderID ;
											pst = con.prepareStatement(query);
											pst.executeUpdate();
										}else{
											
											//if this order already has paid amount under this invoice, cannot deassociate
	
											if(panel.pickOrderTable.getValueAt(j, 7) == null ) {
											query = "Update `order` set InvoiceID = NULL "
													+ " WHERE ID = " + orderID ;
											pst = con.prepareStatement(query);
											pst.executeUpdate();
											}else {
												JOptionPane.showMessageDialog(null, "This order is already paid - cannot deassociate");
											}
										}
										
										//perform only checked items
										if(selected){
											System.out.println("updating amount paid for oder" + orderID);
											double orderCost = Double.parseDouble(panel.pickOrderTable.getValueAt(j, 6).toString());
											//if  Amount paid is greater than cost, amount paid is same as cost 
											if(paidAmount >= orderCost ){
												double paidAmountForOrder = orderCost; 
												System.out.println("paidAmountForthisOrderis " + paidAmountForOrder);
												paidAmount = paidAmount - orderCost; //subtract orderCost from Paid Amount
												query = "Update `order` set AmountPaid = ? WHERE ID = ?";	
												pst = con.prepareStatement(query);
												pst.setDouble(1, paidAmountForOrder);
												pst.setInt(2, orderID);
												pst.executeUpdate();
												
											}else{
												//if remaining of paidAmount is less than orderCost, then assign the remainig
												query = "Update `order` set AmountPaid = ? WHERE ID = ?";
												System.out.println("paid amount is  " + paidAmount);
												pst = con.prepareStatement(query);
												pst.setDouble(1, paidAmount);
												pst.setInt(2, orderID);
												pst.executeUpdate();
											}
										}
										
									} // end of #loop through pickOrder Table----
									
									//inform user that an invoice has been created
									JOptionPane.showMessageDialog(null, "Invoice (ID: " + invoiceID + ") Updated");	
									
									//refresh tables and close the dialog
									Inventory.createTableOrderList();
									Inventory.createTableInvoice();
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


}//---------------------------------------------




