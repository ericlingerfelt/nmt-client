package org.nuclearmasses.gui.viz.chart;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.border.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class is the {@link JTable} used by teh color settings frame to 
 * set binned color scales.
 */
public class VizChartColorSettingsTable extends JTable{

	/** The table model. */
	VizChartColorSettingsTableModel tableModel;
	
	/** The col names vector. */
	private Vector<String> colNamesVector;
	
	/** The parent. */
	private VizChartFrame parent;

	/**
	 * Class constructor.
	 *
	 * @param parent the {@link VizChartFrame} spawning this class
	 */
	public VizChartColorSettingsTable(VizChartFrame parent){
		
		this.parent = parent;
		
		colNamesVector = new Vector<String>();
		colNamesVector.addElement("Interval Min");
		colNamesVector.addElement("Interval Max");
		colNamesVector.addElement("Include bin?");
		colNamesVector.addElement("Color");
	
		tableModel = new VizChartColorSettingsTableModel();

		setDefaultRenderer(Color.class, new ColorRenderer(true));                      
        setDefaultEditor(Color.class, new ColorEditor());
        setDefaultRenderer(Integer.class, new VizCellRenderer(tableModel));
        setDefaultRenderer(Double.class, new VizCellRenderer(tableModel));
        setDefaultRenderer(Boolean.class, new VizBooleanCellRenderer(tableModel));
       
		setModel(tableModel);
		setRowHeight(20);
		
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer()));
		validate();
	}
	
	/**
	 * Sets the current state of this class.
	 * 
	 * @param	numBin	the current number of bins 
	 */
	public void setCurrentState(int numBin){
		tableModel.setDataVector(parent.binData, colNamesVector);
		int currentRowCount = tableModel.getRowCount();
		if(numBin>currentRowCount){
			Double newMax = (Double)((Vector)(tableModel.getDataVector().elementAt(currentRowCount-1))).elementAt(0);
			Double newMin = new Double(newMax.doubleValue());
			tableModel.addRow(new RowData(newMin.doubleValue()-1, newMax.doubleValue(), true, new Color(0,0,0)));
		}else if(numBin<currentRowCount){
			tableModel.removeRow(tableModel.getDataVector().size()-1);
		}
		parent.binData = tableModel.getDataVector();
		validate();
	}
}

class VizChartColorSettingsTableModel extends DefaultTableModel{

	public void fireTableCellUpdated(int row, int column){
		if(column==1){
			if((row-1)>=0){
				setValueAt(getValueAt(row, column), row-1, column-1);
				fireTableDataChanged();
			}
		}
	}

    public Object getValueAt(int row, int col){
        return ((Vector)getDataVector().elementAt(row)).elementAt(col);
    }

    public Class getColumnClass(int c){
        return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col){
		if((col==0 && row<(getRowCount()-1))){
			return false;
		}
		return true;
	}	

}

class ColorRenderer extends JLabel implements TableCellRenderer{
	
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;

    public ColorRenderer(boolean isBordered){
        this.isBordered = isBordered;
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table
    												, Object color
    												, boolean isSelected
    												, boolean hasFocus
    												, int row
    												, int column){											
        Color newColor = (Color)color;
        setBackground(newColor);
        
        if(isBordered){
            if(isSelected){
                if(selectedBorder == null){
                    selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getSelectionBackground());                         
                }
                setBorder(selectedBorder);
            }else{
                if(unselectedBorder == null){
                    unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getForeground());                        
                }
                setBorder(unselectedBorder);
            }
        }
        return this;
    }
}

class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
	
    Color currentColor;
    JButton button;
    JColorChooser colorChooser;
    JDialog dialog;
    protected static final String EDIT = "edit";

    public ColorEditor() {

        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);

        colorChooser = new JColorChooser();
        
        dialog = JColorChooser.createDialog(button,
                                        "Pick a Color",
                                        true,
                                        colorChooser,
                                        this,
                                        null);
    }

    public void actionPerformed(ActionEvent ae){
        if(EDIT.equals(ae.getActionCommand())){
            button.setBackground(currentColor);
            colorChooser.setColor(currentColor);
            dialog.setVisible(true);
            fireEditingStopped();
        }else{
            currentColor = colorChooser.getColor();
        }
    }

    public Object getCellEditorValue(){return currentColor;}

    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column){
                                                 	
        currentColor = (Color)value;
        return button;
        
    }
    
}

class HeaderRenderer extends JLabel implements TableCellRenderer{

	private TableCellRenderer tableCellRenderer;

	public HeaderRenderer(TableCellRenderer tableCellRenderer){
		this.tableCellRenderer = tableCellRenderer;
	}

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
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setForeground(Colors.frontColor);
			l.setBackground(Colors.backColor);
		}
		return c;
		
	}
	
}

class VizCellRenderer extends JLabel implements TableCellRenderer{
	
	private VizChartColorSettingsTableModel model;
	
	public VizCellRenderer(VizChartColorSettingsTableModel model){
		this.model = model;
		setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table
													, Object object
													, boolean isSelected
													, boolean hasFocus
													, int row
													, int column){

		setText(object.toString());
		setHorizontalAlignment(SwingConstants.RIGHT);															
		setFont(Fonts.textFont);
		setBackground(Color.white);
		setForeground(Colors.frontColor);
		if(isSelected && hasFocus){
			setBackground(table.getSelectionBackground());
		}
		if(!model.isCellEditable(row, column)){
			setBackground(new Color(230, 230, 230));
		}
		return this;												
	}
}

class VizBooleanCellRenderer extends JCheckBox implements TableCellRenderer{
	
	private DefaultTableModel model;
	
	public VizBooleanCellRenderer(DefaultTableModel model){
		this.model = model;
		setOpaque(true);
	}
	
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
    	setForeground(Colors.frontColor);
    	
    	if(isSelected && hasFocus){
    		setBackground(table.getSelectionBackground());
    	}
    	
    	if(!model.isCellEditable(row, column)){
    		setBackground(new Color(230, 230, 230));
    	}
    	
    	return this;												
   		
    }

}