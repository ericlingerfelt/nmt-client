package org.nuclearmasses.gui.man;

import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.gui.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class is Step 1 of 2 for the Mass Dataset Manager > Mass Dataset Information tool.
 */
public class ManInfo1Panel extends JPanel implements MassModelTreeListener
														, MassModelListListener
														, StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	/** The tree. */
	private MassModelTree tree;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManInfo1Panel(ManDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};

		setLayout(new TableLayout(column, row));
		
		WordWrapLabel label = new WordWrapLabel(true);
		label.setText("<html>Select mass datasets by double-clicking it in the tree below left. Remove a mass dataset"
							+ " from the selection list by double-clicking on the mass dataset.</i></html>");
		
		tree = new MassModelTree(new MassModelTreeAdapter(this));
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.addMouseListener(new MassModelListAdapter(this));
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, listPane);	
		sp.setDividerLocation(250);
		
		add(label, "1, 1, c, c");
		add(sp, "1, 3, f, f");
		
	}
	
	/**
	 * Determines if the mass dataset list is empty.
	 * 
	 * @return		true if the mass dataset list is empty
	 */
	public boolean isListEmpty(){
		listModel.trimToSize();
		return listModel.size()==0;
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.MassModelTreeListener#treeDoubleClicked()
	 */
	public void treeDoubleClicked(){
		if(tree.getSelectedObject()!=null
			&& !listModel.contains(tree.getSelectedObject())){
			listModel.addElement(tree.getSelectedObject());
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.MassModelListListener#listDoubleClicked()
	 */
	public void listDoubleClicked(){
		if(list.getSelectedValues()!=null){
			Object[] array = list.getSelectedValues();
			for(int i=0; i<array.length; i++){
				listModel.removeElement(array[i]);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getModelMap());
		if(ds.getModelMapSelected()!=null){
			for(MassModelDataStructure mmds: ds.getModelMapSelected().values()){
				listModel.addElement(mmds);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		HashMap<Integer, MassModelDataStructure> map = new HashMap<Integer, MassModelDataStructure>();
		for(int i=0; i<listModel.size(); i++){
			MassModelDataStructure mmds = (MassModelDataStructure)listModel.get(i);
			map.put(mmds.getIndex(), mmds);
		}
		ds.setModelMapSelected(map);
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
