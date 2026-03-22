package org.nuclearmasses.gui.anal;

import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.*;

/**
 * This class is Step 2 of 5 for the Mass Dataset Analyzer > Mass Dataset RMS Comparator.
 */
public class AnalComp2Panel extends JPanel implements MassModelTreeListener, StateAccessor{
	
	/** The tree. */
	private MassModelTree tree;
	
	/** The mass model data structure. */
	private MassModelDataStructure massModelDataStructure;
	
	/** The ds. */
	private AnalDataStructure ds;
	
	/** The model field. */
	private JTextField modelField;
	
	/**
	 * Class constructor.
	 *
	 * @param ds a reference to the {@link AnalDataStructure}
	 */
	public AnalComp2Panel(AnalDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		tree = new MassModelTree(new MassModelTreeAdapter(this));
		JScrollPane sp = new JScrollPane(tree); 
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Select a reference mass dataset from the tree below by double-clicking on it.</html>");
		
		JLabel modelLabel = new JLabel("Selected Reference Mass Dataset : ");
		modelLabel.setFont(Fonts.textFont);
		modelField = new JTextField(15);

		JPanel modelPanel = new JPanel();
		double[] columnPanel = {TableLayoutConstants.PREFERRED, 10, TableLayoutConstants.PREFERRED};
		double[] rowPanel = {TableLayoutConstants.PREFERRED};
		modelPanel.setLayout(new TableLayout(columnPanel, rowPanel));
		modelPanel.add(modelLabel, "0, 0, r, c");
		modelPanel.add(modelField, "2, 0, f, c");
		
		add(topLabel, "1, 1, c, c");
		add(modelPanel, "1, 3, c, c");
		add(sp, "1, 5, f, f");
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.MassModelTreeListener#treeDoubleClicked()
	 */
	public void treeDoubleClicked(){
		if(tree.getSelectedObject()!=null){
			massModelDataStructure = tree.getSelectedObject();
			if(ds.getRefIndex()==ds.getAMEIndex()){
				ds.getModelMapSelected().remove(ds.getAMEIndex());
			}
			ds.getModelMapSelected().remove(ds.getRefIndex());
			ds.getModelMapSelected().put(massModelDataStructure.getIndex(), massModelDataStructure);
			ds.setRefIndex(massModelDataStructure.getIndex());
			modelField.setText(massModelDataStructure.getName());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getModelMap(), ds.getModelMapSelected());
		if(ds.getModelMapSelected().get(ds.getRefIndex())!=null){
			modelField.setText(ds.getModelMapSelected().get(ds.getRefIndex()).toString());
		}else if(!ds.getModelMapSelected().keySet().contains(ds.getAMEIndex())){
			ds.setRefIndex(ds.getAMEIndex());
			ds.getModelMapSelected().put(ds.getAMEIndex(), ds.getModelMap().get(ds.getAMEIndex()));
			modelField.setText(ds.getModelMapSelected().get(ds.getRefIndex()).toString());
		}
	}

	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		String string = "";
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			string += itr.next();
			if(itr.hasNext()){
				string += ",";
			}
		}
		ds.setModelIndices(string);
	}
}


