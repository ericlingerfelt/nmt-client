package org.nuclearmasses.gui.man;

import javax.swing.*;

import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.util.html.HTMLEditorPane;
import org.nuclearmasses.gui.util.html.HTMLToolBar;

/**
 * This class is Step 2 of 5 for the Mass Dataset Manager > Upload New Mass Dataset tool.
 */
public class ManUpload2Panel extends JPanel implements StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	private HTMLToolBar toolBar;
	
	private HTMLEditorPane editorPane;
	
	/** The ref label. */
	private WordWrapLabel refLabel;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManUpload2Panel(ManDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please enter the reference for the new mass dataset in the field below.</html>");
		
		refLabel = new WordWrapLabel();
		refLabel.setFont(Fonts.textFont);
		refLabel.setText("Enter Reference Below : ");
		
		editorPane = new HTMLEditorPane();
		toolBar = new HTMLToolBar();
		editorPane.setToolBar(toolBar);

		JScrollPane sp = new JScrollPane(editorPane);
		
		JPanel refPanel = new JPanel();
		double[] columnRef = {TableLayoutConstants.FILL};
		double[] rowRef = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		refPanel.setLayout(new TableLayout(columnRef, rowRef));
		
		refPanel.add(toolBar, "0, 0, c, c");
		refPanel.add(refLabel, "0, 2, l, c");
		refPanel.add(sp, "0, 4, f, f");
		
		add(topLabel, "1, 1, c, c");
		add(refPanel, "1, 3, f, f");
		
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		editorPane.setText(ds.getRef());
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		ds.setRef(editorPane.getCleanText());
	}
}

