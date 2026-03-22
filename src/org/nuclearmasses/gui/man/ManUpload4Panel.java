package org.nuclearmasses.gui.man;

import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;

/**
 * This class is Step 4 of 5 for the Mass Dataset Manager > Upload New Mass Dataset tool.
 */
public class ManUpload4Panel extends JPanel implements StateAccessor{
	
	/** The ds. */
	private ManDataStructure ds;
	
	/** The file radio button. */
	private JRadioButton fieldRadioButton, pasteRadioButton, fileRadioButton;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManUpload4Panel(ManDataStructure ds){
	
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 30, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Select a method for uploading a new mass dataset below and click <i>Continue</i></html>");
		
		pasteRadioButton = new JRadioButton("Paste mass dataset into a text area", true);
		pasteRadioButton.setFont(Fonts.textFont);
		
		fileRadioButton = new JRadioButton("Select and upload mass dataset file", false);
		fileRadioButton.setFont(Fonts.textFont);
		
		fieldRadioButton = new JRadioButton("Enter mass dataset into fields", false);
		fieldRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(fieldRadioButton);
		buttonGroup.add(pasteRadioButton);
		buttonGroup.add(fileRadioButton);
		
		gap = 15;
		
		JPanel buttonPanel = new JPanel();
		double[] columButton = {TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED, gap
								, TableLayoutConstants.PREFERRED, gap
								, TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columButton, rowButton));
		buttonPanel.add(fieldRadioButton, "0, 0, l, c");
		buttonPanel.add(pasteRadioButton, "0, 2, l, c");
		buttonPanel.add(fileRadioButton, "0, 4, l, c");	
		
		add(topLabel, "1, 1, c, c");
		add(buttonPanel, "1, 3, c, c");
	
	}

	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		fieldRadioButton.setSelected(false);
		pasteRadioButton.setSelected(false);
		fileRadioButton.setSelected(false);
		switch(ds.getUploadMethod()){
			case PASTE:
				pasteRadioButton.setSelected(true);
				break;
			case FIELD:
				fieldRadioButton.setSelected(true);
				break;
			case FILE:
				fileRadioButton.setSelected(true);
				break;
				
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		if(pasteRadioButton.isSelected()){
			ds.setUploadMethod(ManDataStructure.UploadMethod.PASTE);
		}else if(fieldRadioButton.isSelected()){
			ds.setUploadMethod(ManDataStructure.UploadMethod.FIELD);
		}else if(fileRadioButton.isSelected()){
			ds.setUploadMethod(ManDataStructure.UploadMethod.FILE);
		}
	}

}
