package org.nuclearmasses.gui.man;

import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.util.html.FormattedHTMLEditorPane;
import org.nuclearmasses.gui.util.html.HTMLEditorPane;
import org.nuclearmasses.gui.util.html.HTMLToolBar;

/**
 * This class is Step 3 of 5 for the Mass Dataset Manager > Modify Existing Mass Dataset tool.
 */
public class ManModify3Panel extends JPanel implements StateAccessor{

	private ManDataStructure ds;

	private HTMLToolBar toolBar;
	
	private HTMLEditorPane editorPane;
	
	private FormattedHTMLEditorPane refPane;
	
	private JLabel refPaneLabel, editorPaneLabel;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link ManDataStructure}
	 */
	public ManModify3Panel(ManDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please enter the reference for the modified mass dataset in the field below. "
								+ "The original reference will be appended to the new reference.</html>");
		
		refPaneLabel = new JLabel("Original Reference : ");
		
		refPane = new FormattedHTMLEditorPane();
		JScrollPane spRef = new JScrollPane(refPane);
		
		editorPaneLabel = new JLabel("Enter New Reference Below : ");
		
		editorPane = new HTMLEditorPane();
		toolBar = new HTMLToolBar();
		editorPane.setToolBar(toolBar);
		
		JScrollPane sp = new JScrollPane(editorPane);
		
		JPanel refPanel = new JPanel();
		double[] columnRef = {TableLayoutConstants.FILL};
		double[] rowRef = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL
							, 20, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.FILL};
		refPanel.setLayout(new TableLayout(columnRef, rowRef));
		
		refPanel.add(refPaneLabel, 		"0, 0, l, c");
		refPanel.add(spRef, 			"0, 2, f, f");
		refPanel.add(toolBar, 			"0, 4, c, c");
		refPanel.add(editorPaneLabel, 	"0, 6, l, c");
		refPanel.add(sp, 				"0, 8, f, f");
		
		add(topLabel, "1, 1, f, c");
		add(refPanel, "1, 3, f, f");
		
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		MassModelDataStructure mmds = ds.getModelMapSelected().values().iterator().next();
		refPane.setText(mmds.getRef());
		editorPane.setText(ds.getRefNew());
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		ds.setRefNew(editorPane.getCleanText());
	}
}
