package vision;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;



public class Inventory extends JPanel {

private DefaultTableModel MyModel = null;
private Connection connectionInvoice = null; //Shigemi
private JTextField textProductID;
private JTextField textProductName;
private JTextField textProductDescription;
private JTextField textCategoryID;
//private JTextField textUnitCost;
private JTextField textSalePrice;
//private JTextField textSupplierID;
private JTextField textSubCategoryID;
private JTextField textQuantity;
private JTextField textNote;

private JTextField tfEditID;
private JTextField tfEditName;
private JTextField tfEditDescription;
private JTextField tfEditQuantity;
private JTextField tfEditCategoryID;
private JTextField tfEditSubCategoryID;
private JTextField tfEditUnitCost;
private JTextField tfEditSalePrice;
//private JTextField tfEditSupplierID;
private JTextField tfEditNotes;
private JTable table_2;
private JComboBox cbCategory;
private JComboBox cbSubCategory;

private JScrollPane scrollPane_InventoryList;
private JTable table;
private JTabbedPane tabbedPane_Inventory;	
private static TableRowSorter<MyTableModelClass> sorter;

private static  InventoryFilterFrame filter = new InventoryFilterFrame();
private static  JLabel jl_filter_status;
	
public Inventory() {
		setLayout(null);
		
		//JLabel lblNewLabel_1 = new JLabel("New label");
		//lblNewLabel_1.setBounds(131, 99, 56, 16);

setLayout(new BorderLayout(0, 0));
JPanel panel = new JPanel();
panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
add(panel, BorderLayout.NORTH);


	//*************Sub tabbled Pane for Inventory Module
			tabbedPane_Inventory = new JTabbedPane(JTabbedPane.LEFT);
			tabbedPane_Inventory.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane_Inventory.setFont(new Font("Tahoma", Font.PLAIN, 13));
			add(tabbedPane_Inventory);
				   
			JPanel panel_AddProduct = new JPanel();
				   panel_AddProduct.setLayout(null);
				   
			JPanel panel_Inv_EditDetails = new JPanel();
				   panel_Inv_EditDetails.setLayout(null);
				   
			/********************<< Load Product Data >>*************************/
				   
				   
				   
			/********************<< Edit Product Details >>*************************/
					
					
					
			/********************<< Create New Product >>*************************/
			
			JButton btnCreate = new JButton("Create");	//Create New Product
			btnCreate.setBounds(30, 416, 115, 25);	panel_AddProduct.add(btnCreate);
					
					btnCreate.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							try{
								connectionInvoice = Connect.connectionSetup();
								String query = 
										"insert into product (ID, Name, Description, Quantity, CategoryID, SubCategoryID, SalePrice, Notes)"
										+ "values (?,?,?,?,?,?,?,?,?,?);"  ;
															
								PreparedStatement pst = connectionInvoice.prepareStatement(query);
								pst.setString(1, textProductID.getText() );
								pst.setString(2, textProductName.getText() );
								pst.setString(3, textProductDescription.getText() );
								pst.setString(4, textQuantity.getText());
								pst.setString(5, textCategoryID.getText() );
								pst.setString(6, textSubCategoryID.getText() );
								//pst.setString(7, textUnitCost.getText() );
								pst.setString(7, textSalePrice.getText() );
								//pst.setString(9, textSupplierID.getText() );
								pst.setString(8, textNote.getText() );
								
								pst.execute();
								JOptionPane.showMessageDialog(null, "New Product Created");
								
								
								//DbUtils comes from rs2XML.jar file
								//table_2.setModel(DbUtils.resultSetToTableModel(rs));
								
								pst.close();
								connectionInvoice.close();

							}catch (Exception e1) {
								e1.printStackTrace();
							}
							refreshInventoryTable();
						}
					});
							
							
				/********************<< Update Product Details >>*************************/
						
				JButton btnUpdateInventory = new JButton("Update");
					    btnUpdateInventory.setBounds(279, 442, 97, 25);
					    btnUpdateInventory.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						try{
							//System.out.println(tfEditDescription.getText().length());
							
							Connection connectionInvoice = Connect.connectionSetup();
							String query = "Update Product Set "
									+ "Name = '"+tfEditName.getText()+"',"
									+ "Description = '"+tfEditDescription.getText()+"',"
								    + "Quantity='"+tfEditQuantity.getText()+"',"
								    + "CategoryID='"+tfEditCategoryID.getText()+"',"
								    + "SubCategoryID='"+tfEditSubCategoryID.getText()+"',"
								    //+ "UnitCost='"+tfEditUnitCost.getText()+"',"
								   	+ "SalePrice='"+tfEditSalePrice.getText()+"',"
								   	//+ "SupplierID='"+tfEditSupplierID.getText()+"',"
								   	+ "Notes='"+tfEditNotes.getText()+"' "
								   	+ "WHERE ID= '"+tfEditID.getText()+"'";					
														
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



		
	    //tabbedPane.addTab("Inventory", null, panel_inventory_main, null);
	    
	    /*
	    tabbedPane.addChangeListener(loadTableAtOpen);
	    tabbedPane_Inventory.addChangeListener(loadTableAtOpen);
	    */
	    
			

// Components for Add Product Page 
	   
JLabel lblNewLabel = new JLabel("ID");	
	   lblNewLabel.setBounds(30, 39, 12, 16);		
JLabel lblName = new JLabel("Name");  				
	   lblName.setBounds(30, 69, 33, 16);			
JLabel lblDescription = new JLabel("Description");	
	   lblDescription.setBounds(30, 99, 115, 16);	
JLabel lblQuantity = new JLabel("Quantity");		
	   lblQuantity.setBounds(30, 129, 47, 16);		
JLabel lblCategoryid = new JLabel("CategoryID");	
	   lblCategoryid.setBounds(30, 159, 63, 16);	
JLabel lblSubcategoryid = new JLabel("SubCategoryID");	
       lblSubcategoryid.setBounds(30, 189, 115, 16);	
//JLabel lblUnitCost = new JLabel("Unit Cost");		
//	   lblUnitCost.setBounds(30, 219, 51, 16);		
JLabel lblSalePrice = new JLabel("Sale Price");		
	   lblSalePrice.setBounds(30, 248, 115, 16);	
//JLabel lblSupplierId = new JLabel("Supplier ID");	
//	   lblSupplierId.setBounds(30, 278, 115, 16);	
JLabel lblNote = new JLabel("Notes");				
	   lblNote.setBounds(30, 320, 32, 16);			
JTextPane textNotes = new JTextPane();	
          textNotes.setBounds(150, 317, 416, 88);


textProductID = new JTextField();			
textProductID.setBounds(150, 35, 116, 22);				
textProductID.setColumns(10);
textProductName = new JTextField();			
textProductName.setBounds(150, 65, 116, 22);		
textProductName.setColumns(10);
textProductDescription = new JTextField();	
textProductDescription.setBounds(150, 95, 116, 22);	
textProductDescription.setColumns(10);
textQuantity = new JTextField();			
textQuantity.setBounds(150, 125, 116, 22);				
textQuantity.setColumns(10);
textCategoryID = new JTextField();			
textCategoryID.setBounds(150, 155, 116, 22);		
textCategoryID.setColumns(10);

textSubCategoryID = new JTextField();		
textSubCategoryID.setBounds(150, 185, 116, 22);		
textSubCategoryID.setColumns(10);
//textUnitCost = new JTextField();			
//textUnitCost.setBounds(150, 215, 116, 22);			
//textUnitCost.setColumns(10);
textSalePrice = new JTextField();			
textSalePrice.setBounds(150, 244, 116, 22);			
textSalePrice.setColumns(10);
/*
textSupplierID = new JTextField();			
textSupplierID.setBounds(150, 277, 116, 22);			
textSupplierID.setText("");	
textSupplierID.setColumns(10);
*/

panel_AddProduct.add(lblNewLabel);
panel_AddProduct.add(lblName);
panel_AddProduct.add(lblDescription);
panel_AddProduct.add(lblQuantity);
panel_AddProduct.add(lblCategoryid);
panel_AddProduct.add(lblSubcategoryid);
//panel_AddProduct.add(lblUnitCost);
panel_AddProduct.add(lblSalePrice);
//panel_AddProduct.add(lblSupplierId);
panel_AddProduct.add(lblNote);
panel_AddProduct.add(textProductID);
panel_AddProduct.add(textProductName);
panel_AddProduct.add(textProductDescription);
panel_AddProduct.add(textQuantity);
panel_AddProduct.add(textCategoryID);
panel_AddProduct.add(textSubCategoryID);
//panel_AddProduct.add(textUnitCost);	
panel_AddProduct.add(textSalePrice);
//panel_AddProduct.add(textSupplierID);
panel_AddProduct.add(textNotes);


cbCategory = new JComboBox();
cbCategory.setBounds(311, 156, 132, 22);
fillComboBoxCategory();


JPanel panel_InventoryList = new JPanel();
panel_InventoryList.setLayout(new BorderLayout(0, 0));

table_2 = new JTable();
table_2.setRowHeight(25);

	   scrollPane_InventoryList = new JScrollPane();
	   scrollPane_InventoryList.setViewportView(table_2);
		panel_InventoryList.add(scrollPane_InventoryList);
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
					if (selectedRows.size() > 1)
					{
						JOptionPane.showMessageDialog(null, "Please select only one item");
					}
					else 
					{
		
						jumpToEditPage(selectedRows.get(0).intValue());
		
					} // end else
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Select product ID from list");
					e1.printStackTrace();
				}   
			}
		});
		
		JButton btnCreateOrder = new JButton("Create Order");
		panel_InventoryList_North.add(btnCreateOrder);
		btnCreateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Vector<Integer> selectedRows = getSelectedRows();
		
				StringBuilder message = new StringBuilder();
				message.append("You have selected ");
				for (int i = 0; i < selectedRows.size(); i++)
				{
					message.append(selectedRows.get(i).toString()).append("/ ");
				}
				JOptionPane.showMessageDialog(null, message);
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
					filter.setVisible(true);
					
		}});
		panel_InventoryList_North.add(btnClearFilter);
		
		JPanel jp_blankspace02 = new JPanel();
		panel_InventoryList_North.add(jp_blankspace02);
		jp_blankspace02.setPreferredSize(new Dimension(30,20));
		
		jl_filter_status = new JLabel("No filter Applied");
		panel_InventoryList_North.add(jl_filter_status);
		

cbSubCategory  = new JComboBox();
cbSubCategory.setBounds(311, 186, 132, 22);

tabbedPane_Inventory.addTab("Add Product", null, panel_AddProduct, null);
panel_AddProduct.add(cbCategory);
panel_AddProduct.add(cbSubCategory);




JPanel panel_3 = new JPanel();
	   panel_3.setBounds(159, 72, 445, 357);
		
	   
JTree tree = new JTree();
	  tree.setBounds(24, 72, 123, 426);
		
	  panel_Inv_EditDetails.add(tree);
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
		
		tfEditID = new JTextField();
		tfEditID.setColumns(10);
		GridBagConstraints gbc_tfEditID = new GridBagConstraints();
		gbc_tfEditID.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditID.gridx = 2;
		gbc_tfEditID.gridy = 0;
		panel_3.add(tfEditID, gbc_tfEditID);
		
		JLabel label_Edit_Name = new JLabel("Name");
		GridBagConstraints gbc_label_Edit_Name = new GridBagConstraints();
		gbc_label_Edit_Name.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Name.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Name.gridx = 1;
		gbc_label_Edit_Name.gridy = 1;
		panel_3.add(label_Edit_Name, gbc_label_Edit_Name);
		
		tfEditName = new JTextField();
		tfEditName.setColumns(10);
		GridBagConstraints gbc_tfEditName = new GridBagConstraints();
		gbc_tfEditName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEditName.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditName.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditName.gridx = 2;
		gbc_tfEditName.gridy = 1;
		panel_3.add(tfEditName, gbc_tfEditName);
		
		JLabel label_Edit_Desc = new JLabel("Description");
		GridBagConstraints gbc_label_Edit_Desc = new GridBagConstraints();
		gbc_label_Edit_Desc.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_Edit_Desc.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Desc.gridx = 1;
		gbc_label_Edit_Desc.gridy = 2;
		panel_3.add(label_Edit_Desc, gbc_label_Edit_Desc);
		
		tfEditDescription = new JTextField(); 
		tfEditDescription.setColumns(10);
		GridBagConstraints gbc_tfEditDescription = new GridBagConstraints();
		gbc_tfEditDescription.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfEditDescription.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditDescription.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditDescription.gridx = 2;
		gbc_tfEditDescription.gridy = 2;
		panel_3.add(tfEditDescription, gbc_tfEditDescription);
		
		JLabel label_Edit_Quantity = new JLabel("Quantity");
		GridBagConstraints gbc_label_Edit_Quantity = new GridBagConstraints();
		gbc_label_Edit_Quantity.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Quantity.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Quantity.gridx = 1;
		gbc_label_Edit_Quantity.gridy = 3;
		panel_3.add(label_Edit_Quantity, gbc_label_Edit_Quantity);
		
		tfEditQuantity = new JTextField();
		tfEditQuantity.setColumns(10);
		GridBagConstraints gbc_tfEditQuantity = new GridBagConstraints();
		gbc_tfEditQuantity.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditQuantity.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditQuantity.gridx = 2;
		gbc_tfEditQuantity.gridy = 3;
		panel_3.add(tfEditQuantity, gbc_tfEditQuantity);
		
		JLabel label_Edit_Category = new JLabel("CategoryID");
		GridBagConstraints gbc_label_Edit_Category = new GridBagConstraints();
		gbc_label_Edit_Category.anchor = GridBagConstraints.WEST;
		gbc_label_Edit_Category.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_Category.gridx = 1;
		gbc_label_Edit_Category.gridy = 4;
		panel_3.add(label_Edit_Category, gbc_label_Edit_Category);
		
		tfEditCategoryID = new JTextField();
		tfEditCategoryID.setColumns(10);
		GridBagConstraints gbc_tfEditCategoryID = new GridBagConstraints();
		gbc_tfEditCategoryID.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditCategoryID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditCategoryID.gridx = 2;
		gbc_tfEditCategoryID.gridy = 4;
		panel_3.add(tfEditCategoryID, gbc_tfEditCategoryID);
		
		JLabel label_Edit_SubCategory = new JLabel("SubCategoryID");
		GridBagConstraints gbc_label_Edit_SubCategory = new GridBagConstraints();
		gbc_label_Edit_SubCategory.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_Edit_SubCategory.insets = new Insets(0, 0, 5, 5);
		gbc_label_Edit_SubCategory.gridx = 1;
		gbc_label_Edit_SubCategory.gridy = 5;
		panel_3.add(label_Edit_SubCategory, gbc_label_Edit_SubCategory);
		
		tfEditSubCategoryID = new JTextField();
		tfEditSubCategoryID.setColumns(10);
		GridBagConstraints gbc_tfEditSubCategoryID = new GridBagConstraints();
		gbc_tfEditSubCategoryID.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditSubCategoryID.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditSubCategoryID.gridx = 2;
		gbc_tfEditSubCategoryID.gridy = 5;
		panel_3.add(tfEditSubCategoryID, gbc_tfEditSubCategoryID);
		
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
		
		tfEditSalePrice = new JTextField();
		tfEditSalePrice.setColumns(10);
		GridBagConstraints gbc_tfEditSalePrice = new GridBagConstraints();
		gbc_tfEditSalePrice.anchor = GridBagConstraints.NORTHWEST;
		gbc_tfEditSalePrice.insets = new Insets(0, 0, 5, 0);
		gbc_tfEditSalePrice.gridx = 2;
		gbc_tfEditSalePrice.gridy = 7;
		panel_3.add(tfEditSalePrice, gbc_tfEditSalePrice);
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
		
		tfEditNotes = new JTextField();
		GridBagConstraints gbc_textEditNotes = new GridBagConstraints();
		gbc_textEditNotes.gridheight = 2;
		gbc_textEditNotes.fill = GridBagConstraints.BOTH;
		gbc_textEditNotes.gridx = 2;
		gbc_textEditNotes.gridy = 9;
		panel_3.add(tfEditNotes, gbc_textEditNotes);
		tfEditNotes.setColumns(10);
		
		//ID, Name, Description, Quantity, CategoryID, SubCategoryID, UnitCost, SalePrice, SupplierID, Notes
		//tfEditID tfEditName tfEditDescription tfEditQuantity 
		//tfEditCategoryID tfEditSubCategoryID tfEditUnitCost  tfEditSalePrice tfEditSupplierID
		
	    }



// end of Main 

// Begining of functions


	public void fillComboBoxCategory(){ //*********************************************
	try{
		connectionInvoice = Connect.connectionSetup();
		String query = "Select * FROM category ORDER BY Name";
		PreparedStatement pst = connectionInvoice.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		
		while(rs.next())
		{
			cbCategory.addItem(rs.getString("Name"));
		}
		connectionInvoice.close();
		pst.close();
		rs.close();
		
	}catch (Exception e){
		e.printStackTrace();
	}
	}// end of fillComboBoxCategory()
	
	public Vector<Integer> getSelectedRows(){//*********************************************
	Vector<Integer> selectedRows = new Vector<Integer>();
	int rowCount = 	table_2.getRowCount();
	for(int i = 0; i < rowCount; i++)
	{
	if(table_2.getValueAt(i, 0) == Boolean.TRUE)
	{
		selectedRows.add((Integer) table_2.getValueAt(i, 1));
	}
	}
	return selectedRows;
	}
	
	public void jumpToEditPage(int ID){//*********************************************
	
	try{
	connectionInvoice = Connect.connectionSetup();
	String query = "Select * from Product Where ID = "+ ID;
	PreparedStatement pst = connectionInvoice.prepareStatement(query);
	ResultSet rs = pst.executeQuery();
	if(rs.next())
	{
		tfEditID.setText(Integer.toString(rs.getInt("ID")));
		tfEditName.setText(rs.getString("Name"));
		tfEditDescription.setText(rs.getString("Description"));
		tfEditQuantity.setText(Integer.toString(rs.getInt("Quantity")));
		tfEditCategoryID.setText(Integer.toString(rs.getInt("CategoryID")));
		tfEditSubCategoryID.setText(Integer.toString(rs.getInt("SubCategoryID")));
		//tfEditUnitCost.setText(Integer.toString(rs.getInt("UnitCost")));
		tfEditSalePrice.setText(Integer.toString(rs.getInt("SalePrice")));
		//tfEditSupplierID.setText(Integer.toString(rs.getInt("SupplierID")));
		tfEditNotes.setText(rs.getString("Notes"));
								
		pst.close();
		rs.close();
		connectionInvoice.close();
		tabbedPane_Inventory.setSelectedIndex(2); //open Edit panel
	
	}
	}catch (Exception ex){  ex.printStackTrace(); 	}
	
	}
	

	

	
	public static void filterByMultiColumn2(){//********************************************
		RowFilter<MyTableModelClass,Object> rf = null;
		List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);

		filters.add(RowFilter.regexFilter(filter.getID().getText(),1));
		filters.add(RowFilter.regexFilter(filter.getProdName().getText(), 2));
		filters.add(RowFilter.regexFilter(filter.getDesc().getText(), 3));
		filters.add(RowFilter.regexFilter(filter.getCategory().getText(), 5));
		filters.add(RowFilter.regexFilter(filter.getSubCategory().getText(), 6));
		filters.add(RowFilter.regexFilter(filter.getNote().getText(), 8));
		
		if (!filter.getQtyGt().getText().isEmpty() ){
	    String gt = filter.getQtyGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(gt), 4));
		}
		if (!filter.getQtyLt().getText().isEmpty() ){
		String lt = filter.getQtyLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(lt), 4));
		}
		if (!filter.getSaleGt().getText().isEmpty() ){
		String gt = filter.getSaleGt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(gt), 7));
		}
		if (!filter.getSaleLt().getText().isEmpty() ){
		String lt = filter.getSaleLt().getText().trim();
		filters.add(RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(lt), 7));
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

public void refreshInventoryTable(){ //********************************************

	Vector<Object> columnNames = new Vector<Object>();
    Vector<Object> data = new Vector<Object>();
    
    try{
    	connectionInvoice = Connect.connectionSetup();
		
		//p.UnitCost as 'Unit Cost',
		//+ "INNER JOIN Supplier s on p.supplierID = s.ID "s.Name as Supplier,
		String query = 
		"Select p.ID, p.Name as 'Product Name', p.Description, p.Quantity, "
		+ "c.Name as Category, cc.Name as 'Sub Category', "
		+ " p.SalePrice as 'Sales Price',  Notes"
		+ " from Product p "
		+ "INNER JOIN Category c on p.CategoryID = c.ID "
		+ "INNER JOIN Category cc on p.SubCategoryID = cc.ID;"  ;
		
		PreparedStatement pst = connectionInvoice.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		ResultSetMetaData md = rs.getMetaData();

		int columnCount = md.getColumnCount();
		
		columnNames.addElement("Check");
		columnNames.addElement("ID");
		columnNames.addElement("Name");
		columnNames.addElement("Description");
		columnNames.addElement("Quantity");
		columnNames.addElement("Category");
		columnNames.addElement("Sub Category");
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
		connectionInvoice.close();
		
		}catch (Exception e1) 
		{	
		e1.printStackTrace();
		}
        
        MyTableModelClass MyModel = new MyTableModelClass(data, columnNames);

		table_2.setModel(MyModel);
		sorter = new TableRowSorter<MyTableModelClass>(MyModel);
		table_2.setRowSorter(sorter);
		
		table_2.addMouseListener(new MouseAdapter() {
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
}

class MyTableModelClass extends DefaultTableModel{//********************************************

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
		return columnIndex == 0; //Or whatever column index you want to be editable
	}
}


;


