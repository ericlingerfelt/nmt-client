package org.nuclearmasses.gui;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.table.*;
import org.nuclearmasses.datastructure.*;

/**
 * This class is an implementation of JTable. It enables Users to enter 
 * the Z, N, A, MassExcess in MeV, and Uncertainty values in the rows of the table.
 */
public class MassModelTable extends JTable{

	/** The model. */
	private MassModelTableModel model;
	
	/** The col names vector. */
	private Vector<String> colNamesVector;
	
	/**
	 * Class constructor.
	 */
	public MassModelTable(){
		colNamesVector = new Vector<String>();
		colNamesVector.add("Z");
		colNamesVector.add("N");
		colNamesVector.add("A");
		colNamesVector.add("Mass Excess [MeV]");
		colNamesVector.add("Uncertainty [MeV]");
		
		model = new MassModelTableModel(colNamesVector);
		setModel(model);
		setRowHeight(18);
		setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		getTableHeader().setDefaultRenderer(new HeaderRenderer(getTableHeader().getDefaultRenderer()));
		getTableHeader().setReorderingAllowed(false);
		setDefaultEditor(Double.class, new DoubleCellEditor(new JTextField()));
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setBackground(Color.white);
		setForeground(Colors.frontColor);
	}
	
	/**
	 * Fills the rows of this table with the specified {@link TreeMap}.
	 * 
	 * @param	map				a {@link TreeMap} of {@link MassPoint}s keyed on {@link IsotopePoint}s
	 */
	public void setCurrentState(TreeMap<IsotopePoint, MassPoint> map){
		Vector<Vector> dataVector = new Vector<Vector>();
		Iterator<IsotopePoint> itr = map.keySet().iterator();
		while(itr.hasNext()){
			IsotopePoint ip = itr.next();
			Vector vector = new Vector();
			vector.add(ip.getZ());
			vector.add(ip.getN());
			vector.add(ip.getZ()+ip.getN());
			vector.add(map.get(ip).getValue());
			vector.add(map.get(ip).getUncer());
			dataVector.add(vector);
		}
		model.setDataVector(dataVector, colNamesVector);
	}
	
	/**
	 * Fills this table with the specified number of blank rows.
	 * 
	 * @param	numBlankRows	the specified number of blank rows
	 */
	public void setCurrentState(int numBlankRows){
		Vector<Vector> dataVector = new Vector<Vector>();
		for(int i=0; i<numBlankRows; i++){
			Vector vector = new Vector();
			vector.add(null);
			vector.add(null);
			vector.add(null);
			vector.add(null);
			vector.add(null);
			dataVector.add(vector);
		}
		model.setDataVector(dataVector, colNamesVector);
		model.setAllEditable(true);
	}
	
	/**
	 * Adds a row to this table.
	 * 
	 * @param	z			proton number
	 * @param	n			neutron number	
	 * @param	mass		mass excess in MeV	
	 * @param	uncer		uncertainty
	 */
	public void addRow(int z, int n, double mass, double uncer){
		IsotopePoint ip = new IsotopePoint(z, n);
		Vector vector = new Vector();
		vector.add(ip.getZ());
		vector.add(ip.getN());
		vector.add(ip.getZ() + ip.getN());
		vector.add(mass);
		vector.add(uncer);
		if(model.getRowCount()==0){
			model.insertRow(0, vector);
			validate();
			repaint();
			return;
		}
		for(int i=0; i<model.getRowCount(); i++){
			if(i+1<model.getRowCount()){
				IsotopePoint ip1 = new IsotopePoint((Integer)model.getValueAt(i, 0), (Integer)model.getValueAt(i, 1));
				IsotopePoint ip2 = new IsotopePoint((Integer)model.getValueAt(i+1, 0), (Integer)model.getValueAt(i+1, 1));
				if(ip.compareTo(ip1)>0 && ip.compareTo(ip2)<0){
					model.insertRow(i+1, vector);
					validate();
					repaint();
					return;	
				}else if(ip.compareTo(ip1)<0){
					model.insertRow(i, vector);
					validate();
					repaint();
					return;	
				}
			}else{
				IsotopePoint ip1 = new IsotopePoint((Integer)model.getValueAt(i, 0), (Integer)model.getValueAt(i, 1));
				if(ip.compareTo(ip1)<0){
					model.insertRow(i, vector);
				}else{
					model.insertRow(i+1, vector);
				}
				validate();
				repaint();
				return;
			}
		}
	}
	
	/**
	 * Gets the current number of rows in this table.
	 * 
	 * @return	the current number of rows in this table
	 */
	public int getNumRows(){
		return model.getDataVector().size();
	}
	
	/**
	 * Checks to see if a mass value for an isotope currently exists in the table.
	 * 
	 * @param	z	proton number of the isotope
	 * @param	n	neutron number of the isotope
	 * @return		tue if the isotope currently exists in the table
	 */
	public boolean massExists(int z, int n){
		Vector<Vector> dataVector = model.getDataVector();
		Iterator itr = dataVector.iterator();
		while(itr.hasNext()){
			Vector v = (Vector)itr.next();
			if(((Integer)v.get(0))==z && ((Integer)v.get(1))==n){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a blank row to this table.
	 */
	public void addBlankRow(){
		Vector vector = new Vector();
		vector.add(null);
		vector.add(null);
		vector.add(null);
		vector.add(null);
		vector.add(null);
		model.getDataVector().add(vector);
		model.fireTableDataChanged();
	}
	
	/**
	 * Removes all selected rows from this table.
	 */
	public void removeSelectedRows(){
		int[] rowArray = this.getSelectedRows();
		Arrays.sort(rowArray);
		for(int i=0; i<rowArray.length; i++){
			model.removeRow(rowArray[i]);
			if(i<rowArray.length-1){
				for(int j=i+1; j<rowArray.length; j++){
					rowArray[j] = rowArray[j]-1;
				}
			}
			revalidate();
		}
		validate();
		repaint();
	}
	
	/**
	 * Validates all field values as doubles from all nonblank rows in this table.
	 * 
	 * @return	true if all field values can be cast as doubles from all nonblank rows in this table
	 */
	public boolean goodData(){
		boolean goodData = true;
		if(isEditing()){
			for(int i=0; i<getRowCount(); i++){
				for(int j=0; j<getColumnCount(); j++){
					if(isCellEditable(i, j)){
						if(!getCellEditor(i, j).stopCellEditing()){
							return false;
						}
					}
				}
			}
		}
		Vector<Vector> dataVector = model.getDataVector();
		Iterator itr = dataVector.iterator();
		while(itr.hasNext()){
			Vector row = (Vector)itr.next();
			if(!emptyRow(row)){
				try{
					NumberLocalizer.valueOf(row.get(0).toString()).doubleValue();
					NumberLocalizer.valueOf(row.get(1).toString()).doubleValue();
					NumberLocalizer.valueOf(row.get(2).toString()).doubleValue();
					NumberLocalizer.valueOf(row.get(3).toString()).doubleValue();
					NumberLocalizer.valueOf(row.get(4).toString()).doubleValue();
				}catch(NumberFormatException nfe){
					return false;
				}catch(NullPointerException npe){
					return false;
				}
			}
		}
		return goodData;
	}
	
	/**
	 * Empty row.
	 *
	 * @param row the row
	 * @return true, if successful
	 */
	private boolean emptyRow(Vector row){
		return row.get(0)==null
				&& row.get(1)==null
				&& row.get(2)==null
				&& row.get(3)==null
				&& row.get(4)==null;
	}
	
	/**
	 * Gets a formatted String containing all of the data currently in this table.
	 * The String is in the format that the server is expecting for upload.
	 * 
	 * @return	a formatted String containing all of the data currently in this table
	 */
	public String getData(){
		if(isEditing()){
			for(int i=0; i<getRowCount(); i++){
				for(int j=0; j<getColumnCount(); j++){
					if(isCellEditable(i, j)){
						getCellEditor(i, j).stopCellEditing();
					}
				}
			}
		}
		Vector<Vector> dataVector = model.getDataVector();
		Iterator itr = dataVector.iterator();
		String string = "";
		while(itr.hasNext()){
			Vector row = (Vector)itr.next();
			if(!emptyRow(row)){
				string += row.get(0) + ","
							+ row.get(1) + ","
							+ row.get(3) + ","
							+ row.get(4) + " ";
			}
		}
		return string;
	}
	
}

/**
 * This class is the model for this MassModelTable instance.
 */
class MassModelTableModel extends DefaultTableModel{
	
	private boolean allEditable;
	private Object[] colNamesArray;
	
	/**
	 * Class constructor specifying a Vector of column names.
	 * 
	 * @param	colNamesVector	a Vector of column names
	 */
	public MassModelTableModel(Vector colNamesVector){
		colNamesArray = new Object[colNamesVector.size()];
		for(int i=0; i<colNamesVector.size(); i++){
			colNamesArray[i] = colNamesVector.get(i);
		}
	}

	/**
	 * Sets all fields editable. 
	 * 
	 * @param	allEditable	if false only the Mass Excess and Uncertainty columns are editable
	 */
	public void setAllEditable(boolean allEditable){
		this.allEditable = allEditable;
	}
	
	/**
	 * Gets an object representing the value at (row, col).
	 * 
	 * @param	row	the row's index
	 * @param	col	the column's index
	 * @return		the object at (row, col)
	 */
    public Object getValueAt(int row, int col){
    	return ((Vector)getDataVector().get(row)).get(col);
    }
	
    /**
     * Sets an Object's value to the cell at (row, col).
     * 
     * @param	obj	the Object
     * @param	row	the row's index
     * @param	col	the column's index
     */
    public void setValueAt(Object obj, int row, int col){
    	Object[][] dataArray = convertFromDataVector();
    	dataArray[row][col] = obj;
    	if(obj!=null){
	    	if(col==0){
	    		int z = (Integer)obj;
	    		if(getValueAt(row, col+1)!=null){
	    			int n = (Integer)getValueAt(row, col+1);
	    			dataArray[row][col+2] = z+n;
	    		}else if(getValueAt(row, col+2)!=null){
	    			int a = (Integer)getValueAt(row, col+2);
	    			dataArray[row][col+1] = a-z;
	    		}
	    	}else if(col==1){
	    		int n = (Integer)obj;
	    		if(getValueAt(row, col-1)!=null){
	    			int z = (Integer)getValueAt(row, col-1);
	    			dataArray[row][col+1] = z+n;
	    		}else if(getValueAt(row, col+1)!=null){
	    			int a = (Integer)getValueAt(row, col+1);
	    			dataArray[row][col-1] = a-n;
	    		}
	    	}else if(col==2){
	    		int a = (Integer)obj;
	    		if(getValueAt(row, col-2)!=null){
	    			int z = (Integer)getValueAt(row, col-2);
	    			dataArray[row][col-1] = a-z;
	    		}else if(getValueAt(row, col-1)!=null){
	    			int n = (Integer)getValueAt(row, col-1);
	    			dataArray[row][col-2] = a-n;
	    		}
	    	}
	    }
    	setDataVector(dataArray, colNamesArray);
    	fireTableDataChanged();
    }
    
    /**
     * Gets an Object[][] from this model's dataVector.
     * 
     * @return		an Object[][] from this model's dataVector
     */
    public Object[][] convertFromDataVector(){
    	Object[][] array = new Object[getRowCount()][getColumnCount()];
    	for(int i=0; i<getDataVector().size(); i++){
    		Vector vector = (Vector)getDataVector().get(i);
    		for(int j=0; j<vector.size(); j++){
    			array[i][j] = vector.get(j);
    		}
    	}
    	return array;
    }
    
    /**
     * Removes a row from this model.
     * 
     * @param	rowIndex the index of the row to be removed
     */
    public void removeRow(int rowIndex){
    	getDataVector().remove(rowIndex);
    	this.fireTableRowsDeleted(rowIndex, rowIndex);
    }
    
    /**
     * Fired when a cell is updated. This implementation sets the correct z,n,a values if one 
     * the values are changed or at least two are entered into a row.
     * 
     * @param	row	the row's index
     * @param	col	the column's index
     */
    public void fireTableCellUpdated(int row, int col){
    	
    	if(col==0){
    		int z = (Integer)getValueAt(row, col);
    		if(getValueAt(row, col+1)!=null){
    			int n = (Integer)getValueAt(row, col+1);
    			setValueAt(z+n, row, col+2);
    		}else if(getValueAt(row, col+2)!=null){
    			int a = (Integer)getValueAt(row, col+2);
    			setValueAt(a-z, row, col+1);
    		}
    	}else if(col==1){
    		int n = (Integer)getValueAt(row, col);
    		if(getValueAt(row, col-1)!=null){
    			int z = (Integer)getValueAt(row, col-1);
    			setValueAt(z+n, row, col+1);
    		}else if(getValueAt(row, col+1)!=null){
    			int a = (Integer)getValueAt(row, col+1);
    			setValueAt(a-n, row, col-1);
    		}
    	}else if(col==2){
    		int a = (Integer)getValueAt(row, col);
    		if(getValueAt(row, col-2)!=null){
    			int z = (Integer)getValueAt(row, col-2);
    			setValueAt(a-z, row, col-1);
    		}else if(getValueAt(row, col-1)!=null){
    			int n = (Integer)getValueAt(row, col-1);
    			setValueAt(a-n, row, col-2);
    		}
    	}
    	fireTableDataChanged();
	}
    
    /**
     * Gets the Class associated with a column.
     * 
     * @param	c	the column's index
     * @return		the Class associated with the column
     */
    public Class getColumnClass(int c){
    	if(c==3 || c==4){
    		return Double.class;
    	}
        return Integer.class;
	}
    
    /**
     * Determines if a cell at (row, col) is editable.
     * 
     * @param	row	the row's index
     * @param	col	the column's index
     * @return		true if the cell is editable
     */
    public boolean isCellEditable(int row, int col){
    	if(!allEditable){
    		return col==3 || col==4;
    	}
    	return true;
	}
}
