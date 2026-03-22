package org.nuclearmasses.gui.man;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.gui.dialogs.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.gui.*;

/**
 * This class is Step 1 of 1 for the Mass Dataset Manager > Merge Existing Mass Datasets tool.
 */
public class ManMergePanel extends JPanel implements  MassModelTreeListener
														, MassModelListListener
														, ActionListener
														, StateAccessor{
	
	/** The ds. */
	private ManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ManFrame frame;
	
	/** The tree. */
	private MassModelTree tree;
	
	/** The list model. */
	private DefaultListModel listModel;
	
	/** The list. */
	private JList list;
	
	/** The merge button. */
	private JButton mergeButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The overwrite dialog. */
	private CautionDialog overwriteDialog;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param ds 	a reference to the {@link ManDataStructure}
	 * @param cgiCom the cgi com
	 * @param frame a reference to the {@link ManFrame} containing this panel
	 */
	public ManMergePanel(MainDataStructure mds, ManDataStructure ds, CGICom cgiCom, ManFrame frame){
	
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Select mass datasets for merging by double-clicking on a mass dataset in the tree below left. Remove a mass dataset"
							+ " from the selection list by double-clicking on the mass dataset.</i> Click <i>Merge Mass Datasets and Save</i> to complete the process.</html>");
		
		WordWrapLabel mergeLabel = new WordWrapLabel(true);
		mergeLabel.setText("Highest to Lowest Priority");
		
		tree = new MassModelTree(new MassModelTreeAdapter(this));
		JScrollPane treePane = new JScrollPane(tree); 
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.addMouseListener(new MassModelListAdapter(this));
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane listPane = new JScrollPane(list);
		
		double[] columnRight = {TableLayoutConstants.FILL};
		double[] rowRight = {TableLayoutConstants.PREFERRED, 5, TableLayoutConstants.FILL};
		
		JPanel rightPanel = new JPanel(new TableLayout(columnRight, rowRight));
		rightPanel.add(mergeLabel, "0, 0, c, c");
		rightPanel.add(listPane, "0, 2, f, f");
		
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, rightPanel);	
		sp.setDividerLocation(250);
		
		mergeButton = new JButton("Merge Mass Datasets and Save");
		mergeButton.setFont(Fonts.buttonFont);
		mergeButton.addActionListener(this);
		
		add(topLabel, "1, 1, c, c");
		add(sp, "1, 3, f, f");
		add(mergeButton, "1, 5, c, c");
		
		String string = "Please wait while datasets are merged. This process may take several minutes to complete.";
		delayDialog = new DelayDialog(frame, string, "Please Wait...");
	
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
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(saveDialog!=null){
			if(ae.getSource()==saveDialog.getSaveButton()){
				if(!saveDialog.getSaveText().trim().equals("")){
					if(userMassModelExists(saveDialog.getSaveText().trim())){
						String string = "This mass dataset already exists. Do you want to overwite " + saveDialog.getSaveText() + "?";
						overwriteDialog = new CautionDialog(frame, this, string, "Caution!");
						overwriteDialog.setVisible(true);
					}else{
						ds.setOverwrite("N");
						saveMassModel(saveDialog.getSaveText().trim());
					}
				}else{
					String string = "Please enter a name for this mass dataset.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
			}
		}
	
		if(overwriteDialog!=null){
			if(ae.getSource()==overwriteDialog.getYesButton()){
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
				ds.setOverwrite("Y");
				saveMassModel(saveDialog.getSaveText().trim());
			}else if(ae.getSource()==overwriteDialog.getNoButton()){
				overwriteDialog.setVisible(false);
				overwriteDialog.dispose();
			}
		}
		
		if(ae.getSource()==mergeButton){
		
			if(!mds.getUser().equals("guest")){
			
				if(listModel.size()>1){
				
					String string = "Please enter a name for the merged mass dataset in the field below.";
					saveDialog = new SaveDialog(frame
													, this
													, string
													, "Merge Mass Datasets and Save");
					saveDialog.setVisible(true);
				
				}else{
					
					String string = "Please select at least two mass datasets for merger.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
					
				}
			
			}else{
				String string = "Only registered users may use this feature.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
			}
		}
	}
	
	/**
	 * Save mass model.
	 *
	 * @param name the name
	 */
	private void saveMassModel(String name){
		
		saveDialog.setVisible(false);
		saveDialog.dispose();
		
		ds.setModelIndices(getModelIndices());
		ds.setModelName(name);
		ds.setOverwrite("Y");
		
		delayDialog.openDelayDialog();
		MergeMassModelsTask task = new MergeMassModelsTask(mds, ds, cgiCom, frame
															, this, delayDialog, name);
		task.execute();
	}
	
	/**
	 * Refills the Mass Dataset tree with all existing datasets.
	 */
	public void resetTree(){
		ds.setType(MassModelType.ALL.name());
		if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, frame)){
			tree.setCurrentState(ds.getModelMap());
		}
	}
	
	/**
	 * Gets the model indices.
	 *
	 * @return the model indices
	 */
	private String getModelIndices(){
		String string = "";
		for(int i=0; i<listModel.getSize(); i++){
			MassModelDataStructure mmds = (MassModelDataStructure)listModel.get(i);
			if(i==0){
				string += mmds.getIndex();
			}else{
				string += "," + mmds.getIndex();
			}
		}
		return string;
	}
	
	/**
	 * Gets the user mass model list.
	 *
	 * @return the user mass model list
	 */
	private ArrayList<MassModelDataStructure> getUserMassModelList(){
		ArrayList<MassModelDataStructure> list = new ArrayList<MassModelDataStructure>();
		Iterator<Integer> itr = ds.getModelMap().keySet().iterator();
		while(itr.hasNext()){
			MassModelDataStructure mmds = ds.getModelMap().get(itr.next());
			if(mmds.getType()==MassModelType.USER){
				list.add(mmds);
			}
		}
		return list;
	}
	
	/**
	 * User mass model exists.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	private boolean userMassModelExists(String name){
		ArrayList<MassModelDataStructure> list = getUserMassModelList();
		for(MassModelDataStructure mmds: list){
			if(mmds.getName().trim().equals(name.trim()) && mmds.getType()==MassModelType.USER){
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		tree.setCurrentState(ds.getModelMap());
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}

}

class MergeMassModelsTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private DelayDialog dialog;
	private ManMergePanel panel;
	private ManFrame frame;
	private MainDataStructure mds;
	private ManDataStructure ds;
	private CGICom cgiCom;
	private String name;
	
	public MergeMassModelsTask(MainDataStructure mds
								, ManDataStructure ds
								, CGICom cgiCom
								, ManFrame frame
								, ManMergePanel panel
								, DelayDialog dialog
								, String name){
		this.frame = frame;
		this.dialog = dialog;
		this.panel = panel;
		this.name = name;
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
	}
	
	protected Void doInBackground(){
		try
		{
		cgiCom.doCGICall(mds, ds, CGIAction.MERGE_MODELS, frame);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		
		String string = "The mass dataset " + name + " has been saved.";
		GeneralDialog dialog = new GeneralDialog(frame
								, string
								, "Selected Mass Datasets Merged and Saved!");
		dialog.setVisible(true);
		panel.resetTree();
	}
	
}
