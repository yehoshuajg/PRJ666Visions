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
import javax.swing.JButton;

public class InventoryFilterFrame extends JFrame {
	private JTextField tf_filter_ID;
	private JTextField tf_filter_name;
	private JTextField tf_filter_desc;
	private JTextField tf_filter_qtygt;
	private JTextField tf_filter_qtylt;
	private JTextField tf_filter_category;
	private JTextField tf_filter_subCategory;
	private JTextField tf_filter_salegt;
	private JTextField tf_filter_salelt;
	public InventoryFilterFrame() {
		
		setBounds(12, 94, 300, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel jp_filterMain = new JPanel();
		getContentPane().add(jp_filterMain, BorderLayout.CENTER);
		jp_filterMain.setLayout(null);
		
		JPanel jp_filter_options = new JPanel();
		FlowLayout fl_jp_filter_options = (FlowLayout) jp_filter_options.getLayout();
		fl_jp_filter_options.setVgap(10);
		fl_jp_filter_options.setHgap(10);
		fl_jp_filter_options.setAlignment(FlowLayout.RIGHT);
		jp_filter_options.setBounds(12, 94, 258, 304);
		jp_filterMain.add(jp_filter_options);
		
		
		JLabel lblId = new JLabel("Product ID");
		jp_filter_options.add(lblId);
		
		tf_filter_ID = new JTextField();
		jp_filter_options.add(tf_filter_ID);
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
		
		JLabel lblName = new JLabel("Name");
		jp_filter_options.add(lblName);
		
		tf_filter_name = new JTextField();
		jp_filter_options.add(tf_filter_name);
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
		jp_filter_options.add(lblDescription);
		
		tf_filter_desc = new JTextField();
		jp_filter_options.add(tf_filter_desc);
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
		jp_filter_options.add(lblGreaterThan);
		
		tf_filter_qtygt = new JTextField();
		tf_filter_qtygt.setForeground(new Color(0, 0, 0));
		jp_filter_options.add(tf_filter_qtygt);
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
		jp_filter_options.add(lblLessThan);
		
		tf_filter_qtylt = new JTextField();
		jp_filter_options.add(tf_filter_qtylt);
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
		JLabel lblCategory = new JLabel("Category");
		jp_filter_options.add(lblCategory);
		
		tf_filter_category = new JTextField();
		jp_filter_options.add(tf_filter_category);
		tf_filter_category.setColumns(13);
		tf_filter_category.getDocument().addDocumentListener(
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
		JLabel lblSubCategory = new JLabel("Sub Category");
		jp_filter_options.add(lblSubCategory);
		
		tf_filter_subCategory = new JTextField();
		jp_filter_options.add(tf_filter_subCategory);
		tf_filter_subCategory.setColumns(13);
		tf_filter_subCategory.getDocument().addDocumentListener(
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
		jp_filter_options.add(lblSalePriceGreater);
		
		tf_filter_salegt = new JTextField();
		jp_filter_options.add(tf_filter_salegt);
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
		jp_filter_options.add(lblSalePriceLess);
		
		tf_filter_salelt = new JTextField();
		jp_filter_options.add(tf_filter_salelt);
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
		JPanel jp_fiilter_explanation = new JPanel();
		FlowLayout fl_jp_fiilter_explanation = (FlowLayout) jp_fiilter_explanation.getLayout();
		fl_jp_fiilter_explanation.setAlignment(FlowLayout.LEFT);
		jp_fiilter_explanation.setBounds(12, 13, 258, 68);
		jp_filterMain.add(jp_fiilter_explanation);
		
		JLabel lblFilterWillBe = new JLabel("Filter will be applied as you type");
		jp_fiilter_explanation.add(lblFilterWillBe);
		
		JLabel lblEnterSearchKeyword = new JLabel("Enter search keyword");
		jp_fiilter_explanation.add(lblEnterSearchKeyword);
		
		JLabel lblForQuantityAnd = new JLabel("For Quantity and Sale Price, enter number");
		jp_fiilter_explanation.add(lblForQuantityAnd);
		
		JButton btn_filter_close = new JButton("Hide");
		btn_filter_close.setBounds(163, 411, 97, 25);
		jp_filterMain.add(btn_filter_close);
		
		JButton btn_clear = new JButton("Clear");
		btn_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFilter();
			}
		});
		btn_clear.setBounds(54, 411, 97, 25);
		jp_filterMain.add(btn_clear);
		btn_filter_close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
	}// end of constructor 
	
	public JTextField getID() { return tf_filter_ID; }
	public JTextField getProdName() { return tf_filter_name; }
	public JTextField getDesc() { return tf_filter_desc; }
	public JTextField getCategory() { return tf_filter_category; }
	public JTextField getSubCategory() { return tf_filter_subCategory; }
	public JTextField getQtyGt() { return tf_filter_qtygt; }
	public JTextField getQtyLt() { return tf_filter_qtylt; }
	public JTextField getSaleGt() { return tf_filter_salegt; }
	public JTextField getSaleLt() { return tf_filter_salelt; }
	public JTextField getNote() { return tf_filter_salelt; }
	
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
		tf_filter_category.setText("");
		tf_filter_subCategory.setText("");
		tf_filter_salegt.setText("");
		tf_filter_salelt.setText("");
	}
}
