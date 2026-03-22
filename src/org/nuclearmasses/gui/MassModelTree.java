package org.nuclearmasses.gui;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.datastructure.MassModelDataStructure;
import org.nuclearmasses.gui.MassModelTreeAdapter;

/**
 * This class is an implementation of JTree used to select mass datasets from the public, shared, and user folders.
 */
public class MassModelTree extends JTree{

	/** The model. */
	private DefaultTreeModel model;
	
	/** The selection model. */
	private DefaultTreeSelectionModel selectionModel;
	
	/** The shared node. */
	private DefaultMutableTreeNode node, userNode, publicNode, sharedNode;
	
	/**
	 * Class constructor specifying a {@link MassModelTreeAdapter}.
	 *
	 * @param mmta a MassModelTreeAdapter
	 */
	public MassModelTree(MassModelTreeAdapter mmta){
		node = new DefaultMutableTreeNode("Mass Datasets");
		model = new DefaultTreeModel(node);
		selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setModel(model);
		setEditable(false);
		putClientProperty("JTree.linestyle", "Angled");
		setSelectionModel(selectionModel);
		setShowsRootHandles(true);
		addMouseListener(mmta);
		validate();
	}
	
	/**
	 * Class constructor.
	 */
	public MassModelTree(){
		this(null);
	}
	
	/**
	 * Gets the currently selected Object from this tree.
	 *
	 * @return 		true if the selected object is a {@link MassModelDataStructure}
	 * false if the selected object is not a MassModelDataStructure or is .
	 */
	public MassModelDataStructure getSelectedObject(){
		try{
			if(((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject() instanceof MassModelDataStructure){
				return (MassModelDataStructure)((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject();
			}
			return null;
		}catch(NullPointerException npe){
			return null;
		}
	}
	
	/**
	 * Sets the current state of this tree to exclude the AME2012 dataset or any selected reference dataset.
	 * 
	 * @param	map			a {@link HashMap} of {@link MassModelDataStructure}s keyed on it's mass dataset index
	 * @param	ameIndex	the mass dataset index of the AME dataset
	 * @param	refIndex	the mass dataset index of the selected reference dataset
	 */
	public void setCurrentState(HashMap<Integer, MassModelDataStructure> map, int ameIndex, int refIndex){

		node.removeAllChildren();
		model.reload();
		
		publicNode = new DefaultMutableTreeNode("Public");
		sharedNode = new DefaultMutableTreeNode("Shared");
		userNode = new DefaultMutableTreeNode("User");

		model.insertNodeInto(publicNode, node, 0);
		model.insertNodeInto(sharedNode, node, 1);
		model.insertNodeInto(userNode, node, 2);

		HashMap<MassModelType, ArrayList<MassModelDataStructure>> listMap = new HashMap<MassModelType, ArrayList<MassModelDataStructure>>();
		listMap.put(MassModelType.PUBLIC, new ArrayList<MassModelDataStructure>());
		listMap.put(MassModelType.SHARED, new ArrayList<MassModelDataStructure>());
		listMap.put(MassModelType.USER, new ArrayList<MassModelDataStructure>());
		
		Iterator<Integer> itr = map.keySet().iterator();
		while(itr.hasNext()){
			MassModelDataStructure mmds = map.get(itr.next());
			if(mmds.getIndex()!=ameIndex && mmds.getIndex()!=refIndex){
				listMap.get(mmds.getType()).add(mmds);
			}
		}
		
		Collections.sort(listMap.get(MassModelType.PUBLIC));
		Collections.sort(listMap.get(MassModelType.SHARED));
		Collections.sort(listMap.get(MassModelType.USER));
		addNodes(userNode, listMap.get(MassModelType.USER));
		addNodes(sharedNode, listMap.get(MassModelType.SHARED));
		addNodes(publicNode, listMap.get(MassModelType.PUBLIC));

		if(publicNode.getChildCount()==0){
			node.remove(publicNode);
		}
		if(sharedNode.getChildCount()==0){
			node.remove(sharedNode);
		}
		if(userNode.getChildCount()==0){
			node.remove(userNode);
		}

		expandPath(new TreePath(node));
		validate();
		repaint();		
	}
	
	/**
	 * Adds the nodes.
	 *
	 * @param node the node
	 * @param list the list
	 */
	private void addNodes(DefaultMutableTreeNode node, ArrayList<MassModelDataStructure> list){
		for(MassModelDataStructure mmds: list){
			DefaultMutableTreeNode modelNode = new DefaultMutableTreeNode(mmds);
			node.add(modelNode);
		}
	}
	
	/**
	 * Sets the current state of this tree to exclude datasets contained in <code>mapRemove</code>.
	 * 
	 * @param	map			a {@link HashMap} of {@link MassModelDataStructure}s keyed on it's mass dataset index
	 * @param	mapRemove	a HashMap of MassModelDataStructures keyed on it's mass dataset index
	 */
	public void setCurrentState(HashMap<Integer, MassModelDataStructure> map
									, HashMap<Integer, MassModelDataStructure> mapRemove){
		
		node.removeAllChildren();
		model.reload();
		
		publicNode = new DefaultMutableTreeNode("Public");
		sharedNode = new DefaultMutableTreeNode("Shared");
		userNode = new DefaultMutableTreeNode("User");
		
		model.insertNodeInto(publicNode, node, 0);
		model.insertNodeInto(sharedNode, node, 1);
		model.insertNodeInto(userNode, node, 2);
		
		HashMap<MassModelType, ArrayList<MassModelDataStructure>> listMap = new HashMap<MassModelType, ArrayList<MassModelDataStructure>>();
		listMap.put(MassModelType.PUBLIC, new ArrayList<MassModelDataStructure>());
		listMap.put(MassModelType.SHARED, new ArrayList<MassModelDataStructure>());
		listMap.put(MassModelType.USER, new ArrayList<MassModelDataStructure>());
		
		Iterator<Integer> itr = map.keySet().iterator();
		while(itr.hasNext()){
			MassModelDataStructure mmds = map.get(itr.next());
			if((mapRemove!=null && !mapRemove.containsValue(mmds)) || mapRemove==null){
				listMap.get(mmds.getType()).add(mmds);
			}
		}
		
		Collections.sort(listMap.get(MassModelType.PUBLIC));
		Collections.sort(listMap.get(MassModelType.SHARED));
		Collections.sort(listMap.get(MassModelType.USER));
		addNodes(userNode, listMap.get(MassModelType.USER));
		addNodes(sharedNode, listMap.get(MassModelType.SHARED));
		addNodes(publicNode, listMap.get(MassModelType.PUBLIC));
		
		if(publicNode.getChildCount()==0){
			node.remove(publicNode);
		}
		if(sharedNode.getChildCount()==0){
			node.remove(sharedNode);
		}
		if(userNode.getChildCount()==0){
			node.remove(userNode);
		}
		
		expandPath(new TreePath(node));
		validate();
		repaint();		
	}
	
	/**
	 * Sets the current state of this tree to include all {@link MassModelDataStructure}s in map.
	 * 
	 * @param	map			a {@link HashMap} of {@link MassModelDataStructure}s keyed on it's mass dataset index
	 */
	public void setCurrentState(HashMap<Integer, MassModelDataStructure> map){
		setCurrentState(map, null);
	}

}
	
