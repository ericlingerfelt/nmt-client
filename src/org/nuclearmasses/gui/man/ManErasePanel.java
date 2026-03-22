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
 * This class is Step 1 of 1 for the Mass Dataset Manager >  Erase Mass Dataset tool.
 */
public class ManErasePanel extends JPanel implements ActionListener, StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ManFrame frame;
	
	/** The delete button. */
	private JButton deleteButton;
	
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
	public ManErasePanel(MainDataStructure mds, ManDataStructure ds, CGICom cgiCom, ManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
	
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		layout = new TableLayout(column, row);
		
		setLayout(layout);
		
		topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>With the Erase Mass Dataset tool, you can delete a mass dataset from your User storage folder.</html>");
		
		modelLabel = new WordWrapLabel();
		modelLabel.setText("Mass Dataset to erase : ");
		modelLabel.setFont(Fonts.textFont);

		modelModel = new DefaultComboBoxModel();
		modelComboBox = new SizedComboBox(modelModel);
		modelComboBox.setFont(Fonts.textFont);
		modelComboBox.setPopupWidthToLongest();
		
		panel = new JPanel();
		panel.add(modelLabel);
		panel.add(modelComboBox);
		
		deleteButton = new JButton("Erase Selected Mass Dataset");
		deleteButton.setFont(Fonts.buttonFont);
		deleteButton.addActionListener(this);

		add(topLabel, "0, 1, c, c");
		add(panel, "0, 3, c, c");
		add(deleteButton, "0, 5, c, c");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(cautionDialog!=null){
			if(ae.getSource()==cautionDialog.getYesButton()){
				ds.setModelIndex(String.valueOf(((MassModelDataStructure)modelModel.getSelectedItem()).getIndex()));
				String name = ((MassModelDataStructure)modelModel.getSelectedItem()).getName();
				if(cgiCom.doCGICall(mds, ds, CGIAction.ERASE_MODEL, frame)){
					cautionDialog.setVisible(false);
					cautionDialog.dispose();
					ds.setType(MassModelType.USER.name());
					if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, frame)){
						setCurrentState();
						String string = "The mass dataset " + name + " has been successfully deleted from your User storage folder.";
						GeneralDialog dialog = new GeneralDialog(frame, string, "Mass Dataset Deleted");
						dialog.setVisible(true);
					}
				}
				
			}else if(ae.getSource()==cautionDialog.getNoButton()){
				cautionDialog.setVisible(false);
				cautionDialog.dispose();
			}
		}
		
		if(ae.getSource()==deleteButton){
			String string = "You are about to delete the mass dataset " 
								+ ((MassModelDataStructure)modelModel.getSelectedItem()).toString()
								+ ". Do you wish to continue?";
			cautionDialog = new CautionDialog(frame, this, string, "Caution!");
			cautionDialog.setVisible(true);
		}
	} 
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		
		panel.removeAll();
		panel.add(modelLabel);
		
		if(ds.getModelMap().keySet().size()>0){
			
			modelModel.removeAllElements();
			Iterator<Integer> itr = ds.getModelMap().keySet().iterator();
			while(itr.hasNext()){
				modelModel.addElement(ds.getModelMap().get(itr.next()));
			}
			modelComboBox.setPopupWidthToLongest();
			modelLabel.setText("Mass Dataset to erase : ");
			panel.add(modelComboBox);
			add(deleteButton, "0, 5, c, c");
			
		}else{

			remove(deleteButton);
			modelLabel.setText("There are no mass datasets in your User storage folder to erase.");
			
		}
		
		repaint();
		validate();
		
	}

	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}
	
}
