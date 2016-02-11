package vision;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import javax.swing.table.TableCellEditor;

public class SpinnerEditor  extends AbstractCellEditor implements TableCellEditor 
{ 
        final JSpinner spinner = new JSpinner(); 

        // Initializes the spinner. 
        public SpinnerEditor(String[] items) { 
                spinner.setModel(new SpinnerListModel(java.util.Arrays.asList(items))); 
        } 

        // Prepares the spinner component and returns it. 
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)       { 
                System.out.println ("SpinnerEditor.getTableCellEditorComponent(): row: " + row + "\tcolumn: " + column);
                System.out.println ("SpinnerEditor.getTableCellEditorComponent(): value: " + value);
        
                if(value!=null)
                    spinner.setValue(value);
            else
            	spinner.setValue("0");
                return spinner; 
        } 

        // Enables the editor only for double-clicks. 
        public boolean isCellEditable(EventObject evt) { 
//              if (evt instanceof MouseEvent) { 
//                      return ((MouseEvent)evt).getClickCount() >= 2; 
//              }
//              
                return true; 
        } 
        
        // Returns the spinners current value. 
        public Object getCellEditorValue() { 
                return spinner.getValue(); 
        }
        public void setCellEditorValue(Object value){
        	this.spinner.setValue(value);
        }
}