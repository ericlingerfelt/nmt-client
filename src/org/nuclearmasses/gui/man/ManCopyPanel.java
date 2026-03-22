package org.nuclearmasses.gui.man;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.dialogs.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.enums.*;

/**
 * This class is Step 1 of 1 for the Mass Dataset Manager > Copy Mass Dataset to Shared Folder tool.
 */
public class ManCopyPanel extends JPanel implements ActionListener, StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ManFrame frame;
	
	/** The copy button. */
	private JButton copyButton;
	
	/** The model combo box. */
	private SizedComboBox modelComboBox;
	
	/** The model model. */
	private DefaultComboBoxModel modelModel;
	
	/** The model label. */
	private WordWrapLabel topLabel, modelLabel;
	
	/** The layout. */
	private TableLayout layout;
	
	/** The caution dialog. */
	private CautionDialog cautionDialog;
	
	/** The panel. */
	private JPanel panel;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param ds 	a reference to the {@link ManDataStructure}
	 * @param cgiCom the cgi com
	 * @param frame a reference to the {@link ManFrame} containing this panel
	 */
	public ManCopyPanel(MainDataStructure mds, ManDataStructure ds, CGICom cgiCom, ManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>With the Copy Mass Dataset to Shared Folder tool, you can copy "
				 + "mass datasets from your User storage folder to the Shared storage folder. "
				 + "Mass datasets in the Shared storage folder can be accessed by all Users of the suite.<br><br>"
				 + "Contact coordinator@nuclearmasses.org if you wish to remove or replace a mass dataset "
				 + "that you have copied into the Shared storage folder.</html>");
		
		modelLabel = new WordWrapLabel();
		modelLabel.setText("Mass Dataset to copy : ");
		modelLabel.setFont(Fonts.textFont);

		modelModel = new DefaultComboBoxModel();
		modelComboBox = new SizedComboBox(modelModel);
		modelComboBox.setFont(Fonts.textFont);
		modelComboBox.setPopupWidthToLongest();
		
		panel = new JPanel();
		panel.add(modelLabel);
		panel.add(modelComboBox);
		
		copyButton = new JButton("Copy Selected Mass Dataset");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);

		add(topLabel, "1, 1, c, c");
		add(panel, "1, 3, c, c");
		add(copyButton, "1, 5, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
				ds.setModelIndex(String.valueOf(((MassModelDataStructure)modelModel.getSelectedItem()).getIndex()));
				String name = ((MassModelDataStructure)modelModel.getSelectedItem()).getName();
				if(cgiCom.doCGICall(mds, ds, CGIAction.COPY_MODEL_TO_SHARED, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					ds.setType(MassModelType.ALL.name());
					if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, frame)){
						setCurrentState();
						String string = "The mass dataset " + name + " has been successfully copied from "
											+ "your User storage folder to the suite's Shared storage folder.";
						GeneralDialog dialog = new GeneralDialog(frame, string, "Mass Dataset Copied to Shared Folder");
						dialog.setVisible(true);
					}
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==copyButton){
			if(!sharedMassModelExists(((MassModelDataStructure)modelModel.getSelectedItem()).toString())){
				String string = "You about to copy the mass dataset " 
									+ ((MassModelDataStructure)modelModel.getSelectedItem()).toString()
									+ " to the Shared storage folder. Do you wish to continue?";
				cautionDialog = new CautionDialog(frame, this, string, "Caution!");
				cautionDialog.setVisible(true);
			}else{
				String string = "There is currently a Shared mass dataset with this name. Please select a different mass dataset.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
			}
		}
	} 
	
	/**
	 * Shared mass model exists.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	private boolean sharedMassModelExists(String name){
		Iterator<Integer> itr = ds.getModelMap().keySet().iterator();
		while(itr.hasNext()){
			MassModelDataStructure mmds = ds.getModelMap().get(itr.next());
			if(mmds.getType()==MassModelType.SHARED && mmds.getName().trim().equals(name.trim())){
				return true;
			}
		}
		return false;
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
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		
		panel.removeAll();
		panel.add(modelLabel);
		
		ArrayList<MassModelDataStructure> list = getUserMassModelList();
		
		if(list.size()>0){
			
			modelModel.removeAllElements();
			for(MassModelDataStructure mmds: list){
				modelModel.addElement(mmds);
			}
			modelComboBox.setPopupWidthToLongest();
			modelLabel.setText("Mass Dataset to copy : ");
			panel.add(modelComboBox);
			add(copyButton, "1, 5, c, c");
			
		}else{

			remove(copyButton);
			modelLabel.setText("There are no mass datasets in your User storage folder to copy.");
			
		}
		
		repaint();
		validate();
		
	}

	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}
	
}

