package org.nuclearmasses.gui.man;

import javax.swing.*;

import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.util.html.HTMLEditorPane;
import org.nuclearmasses.gui.util.html.HTMLToolBar;

/**
 * This class is Step 3 of 5 for the Mass Dataset Manager > Upload New Mass Dataset tool.
 */
public class ManUpload3Panel extends JPanel implements StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	private HTMLToolBar toolBar;
	
	private HTMLEditorPane editorPane;
	
	/** The desc label. */
	private WordWrapLabel descLabel;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManUpload3Panel(ManDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please enter the description for the new mass dataset in the field below.</html>");
		
		descLabel = new WordWrapLabel();
		descLabel.setFont(Fonts.textFont);
		descLabel.setText("Enter Description Below : ");
		
		editorPane = new HTMLEditorPane();
		toolBar = new HTMLToolBar();
		editorPane.setToolBar(toolBar);
	
		JScrollPane sp = new JScrollPane(editorPane);
		
		JPanel descPanel = new JPanel();
		double[] columnDesc = {TableLayoutConstants.FILL};
		double[] rowDesc = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		descPanel.setLayout(new TableLayout(columnDesc, rowDesc));
		
		descPanel.add(toolBar, "0, 0, c, c");
		descPanel.add(descLabel, "0, 2, l, c");
		descPanel.add(sp, "0, 4, f, f");
		
		add(topLabel, "1, 1, c, c");
		add(descPanel, "1, 3, f, f");
		
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		editorPane.setText(ds.getDesc());
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		ds.setDesc(editorPane.getCleanText());
	}
}


