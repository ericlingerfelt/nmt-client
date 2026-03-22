package org.nuclearmasses.gui.man;

import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;

/**
 * This class is Step 1 of 5 for the Mass Dataset Manager > Upload New Mass Dataset tool.
 */
public class ManUpload1Panel extends JPanel implements StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	/** The author area. */
	private JTextArea authorArea;
	
	/** The field label. */
	private WordWrapLabel authorLabel, fieldLabel;
	
	/** The button. */
	private JRadioButton expButton, theButton;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManUpload1Panel(ManDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please enter the author for the new mass dataset in the field below and "
							+ "select either <i>Theoretical</i> or <i>Experimental</i> for the dataset's type.</html>");
		
		authorLabel = new WordWrapLabel();
		authorLabel.setText("Enter Author Below : ");
		
		authorArea = new JTextArea();
		authorArea.setWrapStyleWord(true);
		authorArea.setLineWrap(true);
		JScrollPane authorSP = new JScrollPane(authorArea);
		
		JPanel authorPanel = new JPanel();
		double[] columnAuthor = {TableLayoutConstants.FILL};
		double[] rowAuthor = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		authorPanel.setLayout(new TableLayout(columnAuthor, rowAuthor));
		authorPanel.add(authorLabel, "0, 0, l, c");
		authorPanel.add(authorSP, "0, 2, f, f");
		
		fieldLabel = new WordWrapLabel();
		fieldLabel.setText("Select Dataset Type : ");
		
		expButton = new JRadioButton("Experimental", true);
		expButton.setFont(Fonts.textFont);
		theButton = new JRadioButton("Theoretical", false);
		theButton.setFont(Fonts.textFont);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(expButton);
		bg.add(theButton);
		
		JPanel fieldPanel = new JPanel();
		double[] columnField = {TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED};
		double[] rowField = {TableLayoutConstants.PREFERRED};
		fieldPanel.setLayout(new TableLayout(columnField, rowField));
		fieldPanel.add(fieldLabel, "0, 0, r, c");
		fieldPanel.add(expButton, "2, 0, c, c");
		fieldPanel.add(theButton, "4, 0, c, c");
		
		add(topLabel, "1, 1, f, c");
		add(fieldPanel, "1, 3, c, c");
		add(authorPanel, "1, 5, f, f");
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		authorArea.setText(ds.getAuthor());
		expButton.setSelected(ds.getField().toString().trim().equals("EXPERIMENT"));
		theButton.setSelected(ds.getField().toString().trim().equals("THEORY"));
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		ds.setAuthor(authorArea.getText());
		ds.setField(expButton.isSelected() ? "EXPERIMENT" : "THEORY");
	}
}

