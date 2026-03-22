package org.nuclearmasses.gui.man;

import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.*;

/**
 * This class is Step 1 of 5 for the Mass Dataset Manager > Modify Existing Mass Dataset tool.
 */
public class ManModify1Panel extends JPanel implements MassModelTreeListener, StateAccessor{
	
	/** The tree. */
	private MassModelTree tree;
	
	/** The mass model data structure. */
	private MassModelDataStructure massModelDataStructure;
	
	/** The map. */
	private HashMap<Integer, MassModelDataStructure> map;
	
	/** The ds. */
	private ManDataStructure ds;
	
	/** The model field. */
	private JTextField modelField;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManModify1Panel(ManDataStructure ds){
		
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
		topLabel.setText("<html>Select a mass dataset from the tree below by double-clicking on it.</html>");
		
		JLabel modelLabel = new JLabel("Selected Mass Dataset : ");
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
			if(map!=null){
				map.clear();
			}else{
				map = new HashMap<Integer, MassModelDataStructure>();
			}
			map.put(massModelDataStructure.getIndex(), massModelDataStructure);
			modelField.setText(massModelDataStructure.getName());
		}
		ds.setModelMapSelected(map);
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getModelMap());
		if(ds.getModelMapSelected()!=null){
			Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
			while(itr.hasNext()){
				massModelDataStructure = ds.getModelMapSelected().get(itr.next());
				modelField.setText(massModelDataStructure.toString());
				break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		ds.setModelIndices(String.valueOf(massModelDataStructure.getIndex()));
	}
	
}
