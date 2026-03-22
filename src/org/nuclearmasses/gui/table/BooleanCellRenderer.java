package org.nuclearmasses.gui.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class allows for proper rendering of {@link JCheckBox} objects in {@link JTable} cells.
 */
public class BooleanCellRenderer extends JCheckBox implements TableCellRenderer{
	
	/** The model. */
	private DefaultTableModel model;
	
	/**
	 * Class constructor.
	 *
	 * @param model a reference to a {@link DefaultTableModel}
	 */
	public BooleanCellRenderer(DefaultTableModel model){
		this.model = model;
		setOpaque(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table
    												, Object object
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){
    													
    	boolean flag = ((Boolean)object).booleanValue();
    	setSelected(flag);		
    	setHorizontalAlignment(SwingConstants.CENTER);													
    	setFont(Fonts.textFont);
    	setBackground(Color.white);
    	setForeground(Color.black);
    	
    	if(isSelected && hasFocus){
    		setBackground(table.getSelectionBackground());
    	}
    	
    	if(!model.isCellEditable(row, column)){
    		setBackground(new Color(204, 204, 204));
    	}
    	
    	return this;												
   		
    }

}