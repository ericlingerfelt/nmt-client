package org.nuclearmasses.gui.table;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import org.nuclearmasses.gui.format.Colors;

/**
 * This class allows for proper rendering of column headers for a {@link JTable}.
 */
public class HeaderRenderer extends JLabel implements TableCellRenderer{

	/** The table cell renderer. */
	private TableCellRenderer tableCellRenderer;

	/**
	 * Class constructor.
	 *
	 * @param tableCellRenderer a {@link TableCellRenderer}
	 */
	public HeaderRenderer(TableCellRenderer tableCellRenderer){
		this.tableCellRenderer = tableCellRenderer;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, 
													Object value,
													boolean isSelected, 
													boolean hasFocus,
													int row, 
													int column){

		Component c = tableCellRenderer.getTableCellRendererComponent(table, 
										value, isSelected, hasFocus, row, column);

		if(c instanceof JLabel){
			JLabel l = (JLabel)c;
			l.setHorizontalAlignment(SwingConstants.CENTER);	
			l.setBackground(Colors.backColor);
			l.setForeground(Colors.frontColor);
		}
		
		return c;
		
	}
	
}