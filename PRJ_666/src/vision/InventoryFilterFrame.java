package vision;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JComboBox;

public class InventoryFilterFrame extends JFrame {
	private JTextField tf_filter_ID;
	private JTextField tf_filter_name;
	private JTextField tf_filter_desc;
	private JTextField tf_filter_qtygt;
	private JTextField tf_filter_qtylt;
	private JTextField tf_filter_salegt;
	private JTextField tf_filter_salelt;
	private JTextField tf_filter_note;
	private JComboBox<String> cb_filter_cat;
	private JComboBox<String> cb_filter_subCat;
	public InventoryFilterFrame() {
		
		setBounds(12, 94, 436, 599);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel jp_filterMain = new JPanel();
		getContentPane().add(jp_filterMain, BorderLayout.CENTER);
		jp_filterMain.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 121, 390, 335);
		jp_filterMain.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		JLabel lblId = new JLabel("Product ID");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 1;
		gbc_lblId.gridy = 0;
		panel.add(lblId, gbc_lblId);
		
		tf_filter_ID = new JTextField();
		tf_filter_ID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_ID = new GridBagConstraints();
		gbc_tf_filter_ID.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_ID.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_ID.gridx = 2;
		gbc_tf_filter_ID.gridy = 0;
		panel.add(tf_filter_ID, gbc_tf_filter_ID);
		tf_filter_ID.setColumns(13);
		tf_filter_ID.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		JLabel lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblCategory = new GridBagConstraints();
		gbc_lblCategory.anchor = GridBagConstraints.EAST;
		gbc_lblCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategory.gridx = 1;
		gbc_lblCategory.gridy = 1;
		panel.add(lblCategory, gbc_lblCategory);
		
		
		cb_filter_cat = new JComboBox<String>();
		GridBagConstraints gbc_cb_filter_cat = new GridBagConstraints();
		gbc_cb_filter_cat.insets = new Insets(0, 0, 5, 0);
		gbc_cb_filter_cat.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_filter_cat.gridx = 2;
		gbc_cb_filter_cat.gridy = 1;
		panel.add(cb_filter_cat, gbc_cb_filter_cat);
		
		fillCbCat();
		
		cb_filter_cat.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	Inventory.filterByMultiColumn2();
		    }
		});
		JLabel lblSubCategory = new JLabel("Sub Category");
		lblSubCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblSubCategory = new GridBagConstraints();
		gbc_lblSubCategory.anchor = GridBagConstraints.EAST;
		gbc_lblSubCategory.insets = new Insets(0, 0, 5, 5);
		gbc_lblSubCategory.gridx = 1;
		gbc_lblSubCategory.gridy = 2;
		panel.add(lblSubCategory, gbc_lblSubCategory);
		
		
		cb_filter_subCat = new JComboBox<String>();
		GridBagConstraints gbc_cb_filter_subCat = new GridBagConstraints();
		gbc_cb_filter_subCat.insets = new Insets(0, 0, 5, 0);
		gbc_cb_filter_subCat.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_filter_subCat.gridx = 2;
		gbc_cb_filter_subCat.gridy = 2;
		panel.add(cb_filter_subCat, gbc_cb_filter_subCat);
		
		fillCbSubCat();
		
		cb_filter_subCat.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	Inventory.filterByMultiColumn2();
		    }
		});
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 1;
		gbc_lblName.gridy = 3;
		panel.add(lblName, gbc_lblName);
		
		tf_filter_name = new JTextField();
		tf_filter_name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_name = new GridBagConstraints();
		gbc_tf_filter_name.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_name.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_name.gridx = 2;
		gbc_tf_filter_name.gridy = 3;
		panel.add(tf_filter_name, gbc_tf_filter_name);
		tf_filter_name.setColumns(13);
		tf_filter_name.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.EAST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 4;
		panel.add(lblDescription, gbc_lblDescription);

		
		tf_filter_desc = new JTextField();
		tf_filter_desc.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_desc = new GridBagConstraints();
		gbc_tf_filter_desc.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_desc.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_desc.gridx = 2;
		gbc_tf_filter_desc.gridy = 4;
		panel.add(tf_filter_desc, gbc_tf_filter_desc);
		tf_filter_desc.setColumns(13);
		tf_filter_desc.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		
		
		JLabel lblGreaterThan = new JLabel("Quantity greater than");
		lblGreaterThan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblGreaterThan = new GridBagConstraints();
		gbc_lblGreaterThan.anchor = GridBagConstraints.EAST;
		gbc_lblGreaterThan.insets = new Insets(0, 0, 5, 5);
		gbc_lblGreaterThan.gridx = 1;
		gbc_lblGreaterThan.gridy = 5;
		panel.add(lblGreaterThan, gbc_lblGreaterThan);
		
		tf_filter_qtygt = new JTextField();
		tf_filter_qtygt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_qtygt = new GridBagConstraints();
		gbc_tf_filter_qtygt.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_qtygt.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_qtygt.gridx = 2;
		gbc_tf_filter_qtygt.gridy = 5;
		panel.add(tf_filter_qtygt, gbc_tf_filter_qtygt);
		tf_filter_qtygt.setForeground(new Color(0, 0, 0));
		tf_filter_qtygt.setColumns(8);
		tf_filter_qtygt.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);

		JLabel lblLessThan = new JLabel("Quantity less than");
		lblLessThan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblLessThan = new GridBagConstraints();
		gbc_lblLessThan.anchor = GridBagConstraints.EAST;
		gbc_lblLessThan.insets = new Insets(0, 0, 5, 5);
		gbc_lblLessThan.gridx = 1;
		gbc_lblLessThan.gridy = 6;
		panel.add(lblLessThan, gbc_lblLessThan);

		
		tf_filter_qtylt = new JTextField();
		tf_filter_qtylt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_qtylt = new GridBagConstraints();
		gbc_tf_filter_qtylt.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_qtylt.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_qtylt.gridx = 2;
		gbc_tf_filter_qtylt.gridy = 6;
		panel.add(tf_filter_qtylt, gbc_tf_filter_qtylt);
		tf_filter_qtylt.setColumns(8);
		tf_filter_qtylt.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		
		
		JLabel lblSalePriceGreater = new JLabel("Sale price greater than");
		lblSalePriceGreater.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblSalePriceGreater = new GridBagConstraints();
		gbc_lblSalePriceGreater.anchor = GridBagConstraints.EAST;
		gbc_lblSalePriceGreater.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalePriceGreater.gridx = 1;
		gbc_lblSalePriceGreater.gridy = 7;
		panel.add(lblSalePriceGreater, gbc_lblSalePriceGreater);
		
		tf_filter_salegt = new JTextField();
		tf_filter_salegt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_salegt = new GridBagConstraints();
		gbc_tf_filter_salegt.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_salegt.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_salegt.gridx = 2;
		gbc_tf_filter_salegt.gridy = 7;
		panel.add(tf_filter_salegt, gbc_tf_filter_salegt);
		tf_filter_salegt.setColumns(8);
		tf_filter_salegt.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		
		JLabel lblSalePriceLess = new JLabel("Sale price less than");
		lblSalePriceLess.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblSalePriceLess = new GridBagConstraints();
		gbc_lblSalePriceLess.anchor = GridBagConstraints.EAST;
		gbc_lblSalePriceLess.insets = new Insets(0, 0, 5, 5);
		gbc_lblSalePriceLess.gridx = 1;
		gbc_lblSalePriceLess.gridy = 8;
		panel.add(lblSalePriceLess, gbc_lblSalePriceLess);

		
		tf_filter_salelt = new JTextField();
		tf_filter_salelt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_salelt = new GridBagConstraints();
		gbc_tf_filter_salelt.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_salelt.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_salelt.gridx = 2;
		gbc_tf_filter_salelt.gridy = 8;
		panel.add(tf_filter_salelt, gbc_tf_filter_salelt);
		tf_filter_salelt.setColumns(8);
		tf_filter_salelt.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		
		JLabel lblNote = new JLabel("Note");
		lblNote.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.anchor = GridBagConstraints.EAST;
		gbc_lblNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote.gridx = 1;
		gbc_lblNote.gridy = 9;
		panel.add(lblNote, gbc_lblNote);
		
		tf_filter_note = new JTextField();
		tf_filter_note.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_tf_filter_note = new GridBagConstraints();
		gbc_tf_filter_note.insets = new Insets(0, 0, 5, 0);
		gbc_tf_filter_note.anchor = GridBagConstraints.WEST;
		gbc_tf_filter_note.fill = GridBagConstraints.VERTICAL;
		gbc_tf_filter_note.gridx = 2;
		gbc_tf_filter_note.gridy = 9;
		panel.add(tf_filter_note, gbc_tf_filter_note);
		tf_filter_note.setColumns(10);
		tf_filter_note.getDocument().addDocumentListener(
				new DocumentListener(){
					public void changedUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void insertUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
					public void removeUpdate(DocumentEvent e){
						Inventory.filterByMultiColumn2();
					}
				}
				);
		

		
		
		JButton btn_filter_close = new JButton("Hide");
		btn_filter_close.setBounds(133, 501, 79, 25);
		jp_filterMain.add(btn_filter_close);
		
		JButton btn_clear = new JButton("Clear");
		btn_clear.setBounds(224, 501, 79, 25);
		jp_filterMain.add(btn_clear);
		
		JTextArea txtrFilterWillBe = new JTextArea();
		txtrFilterWillBe.setText("Filter will be applied as you type\r\nEnter search keyword\r\nFor Qauntity and Sale Price, enter number");
		txtrFilterWillBe.setBackground(SystemColor.control);
		txtrFilterWillBe.setWrapStyleWord(true);
		txtrFilterWillBe.setEditable(false);
		txtrFilterWillBe.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtrFilterWillBe.setBounds(12, 43, 296, 91);
		jp_filterMain.add(txtrFilterWillBe);
		
		JLabel lblInventoryFilter = new JLabel("Inventory Filter");
		lblInventoryFilter.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblInventoryFilter.setBounds(12, 13, 161, 25);
		jp_filterMain.add(lblInventoryFilter);
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFilter();
			}
		});
		btn_filter_close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
	}// end of constructor 
	
	public void fillCbCat(){
		
		try {
			Connection con = Connect.connectionSetup();
			String query = "Select Name FROM Category WHERE ParentID IS NULL ORDER BY NAME";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			cb_filter_cat.addItem("--- Please select category ---");
			while(rs.next()){
				cb_filter_cat.addItem(rs.getString(1));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void fillCbSubCat(){
		try {
			Connection con = Connect.connectionSetup();
			String query = "Select Name FROM Category WHERE ParentID IS NOT NULL ORDER BY NAME";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			cb_filter_subCat.addItem("--- Please select subcategory ---");
			while(rs.next()){
				cb_filter_subCat.addItem(rs.getString(1));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public JTextField getID() { return tf_filter_ID; }
	public JTextField getProdName() { return tf_filter_name; }
	public JTextField getDesc() { return tf_filter_desc; }
	//public JTextField getCategory() { return tf_filter_category; }
	//public JTextField getSubCategory() { return tf_filter_subCategory; }
	public JTextField getQtyGt() { return tf_filter_qtygt; }
	public JTextField getQtyLt() { return tf_filter_qtylt; }
	public JTextField getSaleGt() { return tf_filter_salegt; }
	public JTextField getSaleLt() { return tf_filter_salelt; }
	public JTextField getNote() { return tf_filter_note; }
	public String getCat() { 
		if(cb_filter_cat.getSelectedItem() == null || cb_filter_cat.getSelectedItem().toString().equals("--- Please select category ---") ){
			return "";
		} else {
			return cb_filter_cat.getSelectedItem().toString().trim(); 
		}
	}
	public String getSubCat() { 
		if(cb_filter_subCat.getSelectedItem() == null || cb_filter_subCat.getSelectedItem().toString().equals("--- Please select subcategory ---")){
			return "";
		} else {
			return cb_filter_subCat.getSelectedItem().toString().trim(); 
		}
	}

	
	/*
	 * 	private JTextField tf_filter_ID;
	private JTextField tf_filter_name;
	private JTextField tf_filter_desc;
	private JTextField tf_filter_qtygt;
	private JTextField tf_filter_qtylt;
	private JTextField tf_filter_category;
	private JTextField tf_filter_subCategory;
	private JTextField tf_filter_salegt;
	private JTextField tf_filter_salelt;
	 */
	public void clearFilter(){
		tf_filter_name.setText("");
		tf_filter_desc.setText("");
		tf_filter_qtygt.setText("");
		tf_filter_qtylt.setText("");
		//tf_filter_category.setText("");
		//tf_filter_subCategory.setText("");
		tf_filter_salegt.setText("");
		tf_filter_salelt.setText("");
		tf_filter_note.setText("");
		cb_filter_cat.setSelectedIndex(-1);
		cb_filter_subCat.setSelectedIndex(-1);
	}
}
