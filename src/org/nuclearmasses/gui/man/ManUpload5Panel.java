package org.nuclearmasses.gui.man;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.gui.*;
import org.nuclearmasses.gui.dialogs.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.gui.util.*;

/**
 * This class is Step 5 of 5 for the Mass Dataset Manager > Upload New Mass Dataset tool.
 */
public class ManUpload5Panel extends JPanel implements ActionListener
														, FileUploader
														, MassAdder
														, StateAccessor{
	
	/** The ds. */
	private ManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The frame. */
	private ManFrame frame;
	
	/** The add button. */
	private JButton saveButton, uploadButton, pasteButton, removeButton, addButton;
	
	/** The save dialog. */
	private SaveDialog saveDialog;
	
	/** The remove dialog. */
	private CautionDialog overwriteDialog, removeDialog;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/** The file field. */
	private JTextField fileField;
	
	/** The paste area. */
	private JTextArea pasteArea;
	
	/** The za button. */
	private JRadioButton znButton, zaButton;
	
	/** The button panel. */
	private JPanel entryPanel, formatPanel, buttonPanel;
	
	/** The sp2. */
	private JScrollPane sp, sp2;
	
	/** The top label. */
	private WordWrapLabel topLabel;
	
	/** The method. */
	private ManDataStructure.UploadMethod method;
	
	/** The table. */
	private MassModelTable table;
	
	/** The file. */
	private File file;
	
	/** The file data. */
	private byte[] fileData;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param ds 	a reference to the {@link ManDataStructure}
	 * @param cgiCom the cgi com
	 * @param frame a reference to the {@link ManFrame} containing this panel
	 */
	public ManUpload5Panel(MainDataStructure mds, ManDataStructure ds, CGICom cgiCom, ManFrame frame){
	
		this.ds = ds;
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		pasteArea = new JTextArea();
		pasteArea.setWrapStyleWord(true);
		pasteArea.setLineWrap(true);
		sp = new JScrollPane(pasteArea);
		
		topLabel = new WordWrapLabel(true);
		
		JLabel formatLabel = new JLabel("Selected format : ");
		formatLabel.setFont(Fonts.textFont);
		
		JLabel dataLabel = new JLabel("Selected data entry method : ");
		dataLabel.setFont(Fonts.textFont);
		
		saveButton = new JButton("Upload New Mass Dataset");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
		
		uploadButton = new JButton("Browse...");
		uploadButton.setFont(Fonts.buttonFont);
		uploadButton.addActionListener(this);
		
		pasteButton = new JButton("Paste Mass Dataset");
		pasteButton.setFont(Fonts.buttonFont);
		pasteButton.addActionListener(this);
		
		removeButton = new JButton("Remove Selected Row");
		removeButton.setFont(Fonts.buttonFont);
		removeButton.addActionListener(this);
		
		addButton = new JButton("Add Row to Table");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		buttonPanel.add(removeButton);
		buttonPanel.add(addButton);
		
		fileField = new JTextField();
		fileField.setEditable(false);
		
		znButton = new JRadioButton("Z N MassExcess[MeV] Uncertainty", true);
		znButton.setFont(Fonts.textFont);
		zaButton = new JRadioButton("Z A MassExcess[MeV] Uncertainty", false);
		zaButton.setFont(Fonts.textFont);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(znButton);
		bg.add(zaButton);
		
		entryPanel = new JPanel();	
		
		formatPanel = new JPanel();
		double[] columFormat = {TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED};
		double[] rowFormat = {TableLayoutConstants.PREFERRED};
		formatPanel.setLayout(new TableLayout(columFormat, rowFormat));
		formatPanel.add(formatLabel, "0, 0, r, c");
		formatPanel.add(znButton, "2, 0, c, c");
		formatPanel.add(zaButton, "4, 0, c, c");
		
		table = new MassModelTable();
		table.setCurrentState(1);
		sp2 = new JScrollPane(table);
		sp2.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(removeDialog!=null){
			if(ae.getSource()==removeDialog.getYesButton()){
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
				if(goodData()){
					if((method==ManDataStructure.UploadMethod.FILE && goodFileData())
							|| (method==ManDataStructure.UploadMethod.PASTE && goodPasteData())
							|| method==ManDataStructure.UploadMethod.FIELD){
						String string = "Please enter a name for the new mass dataset in the field below.";
						saveDialog = new SaveDialog(frame
														, this
														, string
														, "Save New Mass Dataset");
						saveDialog.setVisible(true);
					}else{
						String string = "The data's format must be in four columns separated by a space. " +
											"Please ensure that your data is in the correct format.";
						GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
						dialog.setVisible(true);
					}
				}else{
					String string = "";
					switch(method){
						case FIELD:
							string = "Please enter at least one row of Z, N (or A), mass excess and uncertainty value.";
							break;
						case FILE:
							string = "Please select and upload a mass dataset file.";
							break;
						case PASTE:
							string = "Please paste or enter a mass dataset into the text area.";
							break;
					}
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
			
			}else{
				String string = "Only registered users may use this feature.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
				dialog.setVisible(true);
			}
			
		}else if(ae.getSource()==uploadButton){
			
			JFileChooser chooser = new JFileChooser();
			chooser.addChoosableFileFilter(new CustomFileFilter("txt", "Text Document (*.txt)"));
			chooser.addChoosableFileFilter(new CustomFileFilter("dat", "Data (*.dat)"));
			
			int returnVal = chooser.showDialog(this, "Select Mass Dataset File");
			if(returnVal==JFileChooser.APPROVE_OPTION){
				try{
					file = chooser.getSelectedFile();
					fileData = IOUtilities.readFile(file);
					fileField.setText(file.getName());
				}catch(Exception e){
					String string = "An error has occurred uploading the file.";
					GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
					dialog.setVisible(true);
				}
			}
		}else if(ae.getSource()==pasteButton){
			pasteArea.paste();
		}else if(ae.getSource()==addButton){
			table.addBlankRow();
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
	 * Good data.
	 *
	 * @return true, if successful
	 */
	private boolean goodData(){
		boolean goodData = false;
		switch(method){
			case FIELD:
				goodData = table.goodData();
				if(goodData){
					ds.setData(table.getData());
				}
				break;
			case FILE:
				goodData = !fileField.getText().trim().equals("");
				break;
			case PASTE:
				goodData = !pasteArea.getText().trim().equals("");
				break;
		}
		return goodData;
	}
	
	/**
	 * Good paste data.
	 *
	 * @return true, if successful
	 */
	private boolean goodPasteData(){
		StringTokenizer st = new StringTokenizer(pasteArea.getText());
		if(st.countTokens()%4!=0)
		{
			return false;
		}
		String data = "";
		if(znButton.isSelected()){
			while(st.hasMoreTokens()){
				data += st.nextToken() + ",";
				data += st.nextToken() + ",";
				data += st.nextToken() + ",";
				data += st.nextToken() + " ";
			}
		}else{
			while(st.hasMoreTokens()){
				int z = Integer.valueOf(st.nextToken());
				int a = Integer.valueOf(st.nextToken());
				int n = a-z;
				data += z + ",";
				data += n + ",";
				data += st.nextToken() + ",";
				data += st.nextToken() + " ";
			}
		}
		ds.setData(data);
		return true;
	}
	
	/**
	 * Good file data.
	 *
	 * @return true, if successful
	 */
	private boolean goodFileData(){
		String string = new String(fileData);
		StringTokenizer st = new StringTokenizer(string);
		if(st.countTokens()%4!=0)
		{
			return false;
		}
		String data = "";
		if(znButton.isSelected()){
			while(st.hasMoreTokens()){
				data += st.nextToken() + ",";
				data += st.nextToken() + ",";
				data += st.nextToken() + ",";
				data += st.nextToken() + " ";
			}
		}else{
			while(st.hasMoreTokens()){
				int z = Integer.valueOf(st.nextToken());
				int a = Integer.valueOf(st.nextToken());
				int n = a-z;
				data += z + ",";
				data += n + ",";
				data += st.nextToken() + ",";
				data += st.nextToken() + " ";
			}
		}
		ds.setData(data);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.dialogs.MassAdder#addMass(int, int, double, double)
	 */
	public void addMass(int z, int n, double mass, double uncer){
		table.addRow(z, n, mass, uncer);
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.dialogs.FileUploader#uploadComplete(java.lang.String, byte[])
	 */
	public void uploadComplete(String filename, byte[] data){
		fileData = data;
		fileField.setText(filename);
	}
	
	/**
	 * Save mass model.
	 *
	 * @param name the name
	 */
	private void saveMassModel(String name){
		
		ds.setModelName(name);
		ds.setOverwrite("Y");
		ds.setDesc(ds.getDesc());
		ds.setAuthor(ds.getAuthor());
		ds.setRef(ds.getRef());
		ds.setField(ds.getField());
		
		saveDialog.setVisible(false);
		saveDialog.dispose();
		
		String string = "Please wait while new mass dataset is saved.";
		delayDialog = new DelayDialog(frame, string, "Please wait...");
		delayDialog.openDelayDialog();
		
		UploadMassModelTask task = new UploadMassModelTask(mds, ds, cgiCom, frame, delayDialog, name);
		task.execute();
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
		method = ds.getUploadMethod();
		double gap = 20;
		entryPanel.removeAll();
		removeAll();
		switch(method){
			case FIELD:
				topLabel.setText("<html>Enter the mass dataset into the table below. "
									+ "When finished click <i>Upload New Mass Dataset</i> to upload this data.</html>");
				double[] columField = {TableLayoutConstants.FILL};
				double[] rowField = {TableLayoutConstants.FILL};
				entryPanel.setLayout(new TableLayout(columField, rowField));
				entryPanel.add(sp2, "0, 0, f, f");
				remove(formatPanel);
				add(topLabel, "1, 1, c, c");
				add(entryPanel, "1, 3, f, f");
				add(buttonPanel, "1, 5, c, c");
				add(saveButton, "1, 7, c, c");
				break;
			case FILE:
				JLabel fileLabel = new JLabel("Selected Mass Dataset File: ");
				fileLabel.setFont(Fonts.textFont);
				topLabel.setText("<html>Select and upload a mass dataset file below. "
									+ "The file's format must be in four columns separated by a space. "
									+ "Select either <i>Z N Mass Excess [MeV] Uncertainty</i> or <i>Z A Mass Excess "
									+ "[MeV] Uncertainty</i> below to reflect your file's format.</html>");
				double[] columFile = {TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.FILL
										, gap, TableLayoutConstants.PREFERRED};
				double[] rowFile = {TableLayoutConstants.PREFERRED};
				entryPanel.setLayout(new TableLayout(columFile, rowFile));
				entryPanel.add(fileLabel, "0, 0, f, c");
				entryPanel.add(fileField, "2, 0, f, c");
				entryPanel.add(uploadButton, "4, 0, c, c");
				add(topLabel, "1, 1, c, c");
				add(entryPanel, "1, 3, f, c");
				add(formatPanel, "1, 5, c, c");
				add(saveButton, "1, 7, c, c");
				break;
			case PASTE:
				topLabel.setText("<html>Paste and upload a mass dataset in the text area below. "
									+ "The data's format must be in four columns separated by a space. "
									+ "Select either <i>Z N Mass Excess [MeV] Uncertainty</i> or <i>Z A Mass Excess "
									+ "[MeV] Uncertainty</i> below to reflect your data's format.</html>");
				double[] columPaste = {TableLayoutConstants.FILL};
				double[] rowPaste = {TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED};
				entryPanel.setLayout(new TableLayout(columPaste, rowPaste));
				entryPanel.add(sp, "0, 0, f, f");
				entryPanel.add(pasteButton, "0, 2, c, c");
				add(topLabel, "1, 1, c, c");
				add(entryPanel, "1, 3, f, f");
				add(formatPanel, "1, 5, c, c");
				add(saveButton, "1, 7, c, c");
				break;
		}
		validate();
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}

}

class UploadMassModelTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private DelayDialog dialog;
	private ManFrame frame;
	private MainDataStructure mds;
	private ManDataStructure ds;
	private CGICom cgiCom;
	private String name;
	
	public UploadMassModelTask(MainDataStructure mds
								, ManDataStructure ds
								, CGICom cgiCom
								, ManFrame frame
								, DelayDialog dialog
								, String name){
		this.frame = frame;
		this.dialog = dialog;
		this.name = name;
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
	}
	
	protected Void doInBackground(){
		try
		{
			cgiCom.doCGICall(mds, ds, CGIAction.CREATE_MODEL, frame, dialog);
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
								, "New Mass Dataset Saved!");
		dialog.setVisible(true);
	}
	
}

