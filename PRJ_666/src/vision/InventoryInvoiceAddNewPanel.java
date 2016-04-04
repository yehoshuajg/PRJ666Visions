package vision;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class InventoryInvoiceAddNewPanel extends JPanel {

		
		JTextField dataSupInvoiceID;
		JComboBox<vision.CbSupItem> dataSupCb;
		JTextField dataAmountDue;
		JTextField dataAmountPaid;
		JTextField dataOrderID;
		JTextArea dataMessage;
		static JTable pickOrderTable;
		JDatePickerImpl datePicker;
		
		InventoryInvoiceAddNewPanel(){
			//JPanel contents = new JPanel();


			setBounds(0, 0, 500, 500);
			setPreferredSize(new Dimension(700, 500));
			GridBagLayout gbl_super = new GridBagLayout();
			gbl_super.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0};
			gbl_super.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 100};
			gbl_super.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0};
			gbl_super.columnWidths = new int[]{125, 50, 50, 50};
			setLayout(gbl_super);
			
			

			
			JLabel lblNewInvoice = new JLabel("Add New Invoice");
			GridBagConstraints gbc_lblNewInvoice = new GridBagConstraints();
			gbc_lblNewInvoice.anchor = GridBagConstraints.EAST;
			gbc_lblNewInvoice.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewInvoice.gridx = 0;
			gbc_lblNewInvoice.gridy = 1;
			add(lblNewInvoice, gbc_lblNewInvoice);
			lblNewInvoice.setFont(new Font("Tahoma", Font.PLAIN, 17));
			
			JLabel lblSupplier = new JLabel("Supplier");
			GridBagConstraints gbc_lblSupplier = new GridBagConstraints();
			gbc_lblSupplier.anchor = GridBagConstraints.EAST;
			gbc_lblSupplier.insets = new Insets(0, 0, 5, 5);
			gbc_lblSupplier.gridx = 0;
			gbc_lblSupplier.gridy = 3;
			add(lblSupplier, gbc_lblSupplier);
			lblSupplier.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataSupCb = new JComboBox();
			GridBagConstraints gbc_dataSubCb = new GridBagConstraints();
			gbc_dataSubCb.fill = GridBagConstraints.BOTH;
			gbc_dataSubCb.gridwidth = 2;
			gbc_dataSubCb.insets = new Insets(0, 0, 5, 5);
			gbc_dataSubCb.gridx = 1;
			gbc_dataSubCb.gridy = 3;
			add(dataSupCb, gbc_dataSubCb);
			dataSupCb.setFont(new Font("Tahoma", Font.PLAIN, 15));

			

			
			JLabel lblInvoiceId = new JLabel("Supplier Invoice #/ ID");
			GridBagConstraints gbc_lblInvoiceId = new GridBagConstraints();
			gbc_lblInvoiceId.anchor = GridBagConstraints.EAST;
			gbc_lblInvoiceId.insets = new Insets(0, 0, 5, 5);
			gbc_lblInvoiceId.gridx = 0;
			gbc_lblInvoiceId.gridy = 4;
			add(lblInvoiceId, gbc_lblInvoiceId);
			lblInvoiceId.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataSupInvoiceID = new JTextField();
			GridBagConstraints gbc_dataSupInvoiceID = new GridBagConstraints();
			gbc_dataSupInvoiceID.fill = GridBagConstraints.BOTH;
			gbc_dataSupInvoiceID.gridwidth = 2;
			gbc_dataSupInvoiceID.insets = new Insets(0, 0, 5, 5);
			gbc_dataSupInvoiceID.gridx = 1;
			gbc_dataSupInvoiceID.gridy = 4;
			add(dataSupInvoiceID, gbc_dataSupInvoiceID);
			dataSupInvoiceID.setColumns(10);
			dataSupInvoiceID.setFont(new Font("Tahoma", Font.PLAIN, 15));
			//lblSelectOrdersTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JLabel lbldate = new JLabel("Received Date");
			GridBagConstraints dateConstraints2 = new GridBagConstraints();
			dateConstraints2.anchor = GridBagConstraints.NORTHEAST;
			dateConstraints2.insets = new Insets(0, 0, 5, 5);
			dateConstraints2.gridx = 0;
			dateConstraints2.gridy = 5;
			add(lbldate, dateConstraints2);
			lbldate.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
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
			dateConstraints.fill = GridBagConstraints.BOTH;
			dateConstraints.anchor = GridBagConstraints.NORTHEAST;
			dateConstraints.insets = new Insets(0, 0, 5, 5);
			dateConstraints.gridx = 1;
			dateConstraints.gridy = 5;
			add(datePicker, dateConstraints);
			

			
			
			
			JLabel lblAmountDue = new JLabel("Amount Due");
			GridBagConstraints gbc_lblAmountDue = new GridBagConstraints();
			gbc_lblAmountDue.anchor = GridBagConstraints.EAST;
			gbc_lblAmountDue.insets = new Insets(0, 0, 5, 5);
			gbc_lblAmountDue.gridx = 0;
			gbc_lblAmountDue.gridy = 6;
			add(lblAmountDue, gbc_lblAmountDue);
			lblAmountDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			
			dataAmountDue = new JTextField();
			GridBagConstraints gbc_dataAmountDue = new GridBagConstraints();
			gbc_dataAmountDue.fill = GridBagConstraints.BOTH;
			gbc_dataAmountDue.gridwidth = 1;
			gbc_dataAmountDue.insets = new Insets(0, 0, 5, 5);
			gbc_dataAmountDue.gridx = 1;
			gbc_dataAmountDue.gridy = 6;
			add(dataAmountDue, gbc_dataAmountDue);
			dataAmountDue.setColumns(10);
			dataAmountDue.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			//JLabel lblAmountPaid = new JLabel("Amount Paid");
			//GridBagConstraints gbc_lblAmountPaid = new GridBagConstraints();
			//gbc_lblAmountPaid.anchor = GridBagConstraints.EAST;
			//gbc_lblAmountPaid.insets = new Insets(0, 0, 5, 5);
			//gbc_lblAmountPaid.gridx = 0;
			//gbc_lblAmountPaid.gridy = 7;
			//add(lblAmountPaid, gbc_lblAmountPaid);
			//lblAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			
			//dataAmountPaid = new JTextField();
			//GridBagConstraints gbc_dataAmountPaid = new GridBagConstraints();
			//gbc_dataAmountPaid.gridwidth = 1;
			//gbc_dataAmountPaid.anchor = GridBagConstraints.NORTH;
			//gbc_dataAmountPaid.insets = new Insets(0, 0, 5, 5);
			//gbc_dataAmountPaid.fill = GridBagConstraints.HORIZONTAL;
			//gbc_dataAmountPaid.gridx = 1;
			//gbc_dataAmountPaid.gridy = 7;
			//add(dataAmountPaid, gbc_dataAmountPaid);
			//dataAmountPaid.setColumns(10);
			//dataAmountPaid.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			
			
			JLabel lblSelectOrdersTo = new JLabel("Orders to associate");
			GridBagConstraints gbc_lblSelectOrdersTo = new GridBagConstraints();
			gbc_lblSelectOrdersTo.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblSelectOrdersTo.insets = new Insets(0, 0, 5, 5);
			gbc_lblSelectOrdersTo.gridx = 0;
			gbc_lblSelectOrdersTo.gridy = 8;
			add(lblSelectOrdersTo, gbc_lblSelectOrdersTo);
			lblSelectOrdersTo.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			JScrollPane scrollPane_6 = new JScrollPane();
			GridBagConstraints gbc_scrollPane_6 = new GridBagConstraints();
			gbc_scrollPane_6.anchor = GridBagConstraints.SOUTHWEST;
			gbc_scrollPane_6.gridwidth = 4;
			gbc_scrollPane_6.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane_6.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_6.gridx = 0;
			gbc_scrollPane_6.gridy = 9;
			add(scrollPane_6, gbc_scrollPane_6);
			

			
			

			
			JLabel lblMessage = new JLabel("Message:");
			GridBagConstraints gbc_lblMessage = new GridBagConstraints();
			gbc_lblMessage.insets = new Insets(0, 0, 0, 5);
			gbc_lblMessage.anchor = GridBagConstraints.NORTHEAST;
			gbc_lblMessage.gridx = 0;
			gbc_lblMessage.gridy = 10;
			add(lblMessage, gbc_lblMessage);
			lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
			
			dataMessage = new JTextArea();
			dataMessage.setBackground(SystemColor.control);
			dataMessage.setEditable(false);
			dataMessage.setLineWrap(true);
			GridBagConstraints gbc_textArea = new GridBagConstraints();
			gbc_textArea.gridwidth = 3;
			gbc_textArea.fill = GridBagConstraints.BOTH;
			gbc_textArea.gridx = 1;
			gbc_textArea.gridy = 10;
			add(dataMessage, gbc_textArea);
			dataMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));

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
			//----------------------
			try{
				Connection con_Inv = Connect.connectionSetup();
				String query = "Select ID, Name FROM Supplier Order BY Name";
				PreparedStatement pst = con_Inv.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				
				//fill supplier combobox
				CbSupItem firstItem = new CbSupItem(0, "-- Please select Supplier --",0, 0);
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
				String query = "Select o.ID, s.Name as 'Supplier Name', o.CreateDate, o.ReceivedDate ,0.Cost, o.AmountPaid"
					+ " FROM `order` o "
					+ " Inner JOIN Supplier s ON s.ID = o.SupplierID "
					+ " WHERE s.ID = " + supID + " AND o.InvoiceID IS NULL ORDER BY ID DESC;";
			
				PreparedStatement pst = con_Inv.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				int colCount = md.getColumnCount();
				
				Vector<Object> columnNames = new Vector<Object>();
			    Vector<Object> data = new Vector<Object>();
			    
			    columnNames.addElement("Check");
			    columnNames.addElement("Order ID");
			    //columnNames.addElement("Invoice ID");
			    columnNames.addElement("Supplier");
			    columnNames.addElement("Created Date");
			    columnNames.addElement("Received Date");
			    columnNames.addElement("Cost");
			    columnNames.addElement("Amount Paid");

		    while(rs.next()){
		    	Vector<Object> row = new Vector<Object>();
		    	boolean check = false;
		    	row.addElement(check);
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
		    pickOrderTable.setModel(orderPickModel);
		    pickOrderTable.setRowHeight(30);
		    pickOrderTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		    
			Dimension tableSize = pickOrderTable.getPreferredSize();
			TableColumnModel tcm = pickOrderTable.getColumnModel();
			
		
			tcm.getColumn(0).setPreferredWidth(Math.round((tableSize.width)* 0.05f)); 
			tcm.getColumn(1).setPreferredWidth(Math.round((tableSize.width)* 0.05f));
			tcm.getColumn(2).setPreferredWidth(Math.round((tableSize.width)* 0.10f));
			tcm.getColumn(3).setPreferredWidth(Math.round((tableSize.width)* 0.30f)); 
			tcm.getColumn(4).setPreferredWidth(Math.round((tableSize.width)* 0.20f));
			tcm.getColumn(5).setPreferredWidth(Math.round((tableSize.width)* 0.20f));
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

	}
	

