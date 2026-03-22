package org.nuclearmasses.gui.man;

import javax.swing.*;
import java.awt.*;
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
 * This class is Step 5 of 5 for the Mass Dataset Manager > Modify Existing Mass Dataset tool.
 */
public class ManModify5Panel extends JPanel implements ActionListener, MassAdder, StateAccessor{
	
	/** The ds. */
	private ManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ManFrame frame;
	
	/** The table. */
	private MassModelTable table;
	
	/** The add button. */
	private JButton saveButton, removeButton, addButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The remove dialog. */
	private CautionDialog overwriteDialog, removeDialog;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/** The num label. */
	private JLabel numLabel;
	
	/** The num. */
	private int num;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param ds 	a reference to the {@link ManDataStructure}
	 * @param cgiCom the cgi com
	 * @param frame a reference to the {@link ManFrame} containing this panel
	 */
	public ManModify5Panel(MainDataStructure mds, ManDataStructure ds, CGICom cgiCom, ManFrame frame){
	
		this.ds = ds;
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Modify any mass excess values in the table below. "
							+ "When finished click <i>Save Modified Mass Dataset</i>.</html>");
		
		table = new MassModelTable();
		JScrollPane tablePane = new JScrollPane(table); 
		tablePane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		
		saveButton = new JButton("Save Modified Mass Dataset");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		removeButton = new JButton("Remove Selected Row(s)");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Row to Table");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		buttonPanel.add(removeButton);
		buttonPanel.add(addButton);
		
		numLabel = new JLabel();
		numLabel.setFont(Fonts.textFont);
		
		add(topLabel, "1, 1, c, c");
		add(numLabel, "1, 3, c, c");
		add(tablePane, "1, 5, f, f");
		add(buttonPanel, "1, 7, f, c");
		add(saveButton, "1, 9, c, c");
	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(removeDialog!=null){
			if(ae.getSource()==removeDialog.getYesButton()){
				numLabel.setText("Number of Isotopes : " + table.getRowCount());
				table.removeSelectedRows();
				removeDialog.setVisible(false);
				removeDialog.dispose();
			}else if(ae.getSource()==removeDialog.getNoButton()){
				removeDialog.setVisible(false);
				removeDialog.dispose();
			}
		}
		
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
		
		if(ae.getSource()==saveButton){
		
			if(!mds.getUser().equals("guest")){
			
				if(table.goodData()){
				
					String string = "Please enter a name for the modified mass dataset in the field below.";
					saveDialog = new SaveDialog(frame
													, this
													, string
													, "Save Modified Mass Dataset");
					saveDialog.setVisible(true);
				
				}else{
					String string = "Mass Excess and Uncertainty values must be numeric values.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
			
			}else{
				String string = "Only registered users may use this feature.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
			}
			
		}else if(ae.getSource()==addButton){
			AddMassDialog addDialog = new AddMassDialog(frame, this, table);
			addDialog.setLocationRelativeTo(frame);
			addDialog.setVisible(true);
		}else if(ae.getSource()==removeButton){
			int[] rows = table.getSelectedRows();
			if(rows.length>0){
				String string = "You have selected the mass data points listed below for deletion. <b>Are you sure?</b><p>";
				string += "<b>Z, N, A, Mass Excess [MeV], Uncertainty [MeV]</b><br>";
				for(int i=0; i<rows.length; i++){
					string += table.getModel().getValueAt(rows[i], 0) + ", " 
								+ table.getModel().getValueAt(rows[i], 1) + ", " 
								+ table.getModel().getValueAt(rows[i], 2) + ", " 
								+ table.getModel().getValueAt(rows[i], 3) + ", " 
								+ table.getModel().getValueAt(rows[i], 4) + "<br>";
				}
				removeDialog = new CautionDialog(frame, this, string, "Caution!");
				removeDialog.setVisible(true);
			}else{
				String string = "You have not selected any values for removal. Select rows for removal by highlighting them.";
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
		
		MassModelDataStructure mmds = ds.getModelMapSelected().values().iterator().next();
		
		ds.setModelName(name);
		ds.setOverwrite("Y");
		ds.setDesc(mmds.getDesc() + "; " + ds.getDescNew());
		ds.setAuthor(mmds.getAuthor() + "; " + ds.getAuthorNew());
		ds.setRef(mmds.getRef() + "; " + ds.getRefNew());
		ds.setField(mmds.getField().name());
		ds.setData(table.getData());
		
		saveDialog.setVisible(false);
		saveDialog.dispose();
		
		String string = "Please wait while modified mass dataset is saved.";
		delayDialog = new DelayDialog(frame, string, "Please wait...");
		delayDialog.openDelayDialog();
		
		if(cgiCom.doCGICall(mds, ds, CGIAction.CREATE_MODEL, frame, delayDialog)){

			delayDialog.closeDelayDialog();
			
			string = "The mass dataset " + name + " has been saved.";
			GeneralDialog dialog = new GeneralDialog(frame
									, string
									, "Modified Mass Dataset Saved!");
			dialog.setVisible(true);
		}
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
		table.setCurrentState(ds.getModelMapSelected().values().iterator().next().getMassMap());
		num = ds.getModelMapSelected().values().iterator().next().getMassMap().size();
		numLabel.setText("Number of Isotopes : " + num);
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.dialogs.MassAdder#addMass(int, int, double, double)
	 */
	public void addMass(int z, int n, double mass, double uncer){
		table.addRow(z, n, mass, uncer);
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}

}


