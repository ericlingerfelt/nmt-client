package org.nuclearmasses.gui.anal;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.dialogs.DelayDialog;
import org.nuclearmasses.gui.format.*;

/**
 * This class is Step 4 of 4 for the Mass Dataset Analyzer > Mass Dataset RMS Calculator.
 */
public class AnalCalc4Panel extends JPanel implements ActionListener, StateAccessor{

	/** The ds. */
	private AnalDataStructure ds;
	
	/** The viz button. */
	private JButton plotButton, vizButton;
	
	/** The plot label. */
	private WordWrapLabel modelsLabel, refLabel, plotLabel;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;			
	
	/** The frame. */
	private AnalFrame frame;
	
	/** The plot frame. */
	private AnalCalcPlotFrame plotFrame;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link AnalDataStructure}
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param frame a reference to the {@link AnalFrame} containing this panel
	 */
	public AnalCalc4Panel(AnalDataStructure ds, MainDataStructure mds, AnalFrame frame){
		
		this.ds = ds;
		this.mds = mds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 40, TableLayoutConstants.PREFERRED
							, 40, TableLayoutConstants.PREFERRED
							, 40, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		plotLabel = new WordWrapLabel();
		plotLabel.setText("<html>Click the <i>Open Mass Differences/RMS Plotter (1-D)</i> button "
								+ "to make 1-D plots of mass excess differences and the RMS values "
								+ "of mass excess differences.</html>");
		
		modelsLabel = new WordWrapLabel();
		refLabel = new WordWrapLabel();
		
		plotButton = new JButton("Open Mass Differences/RMS Plotter (1-D)");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
		
		vizButton = new JButton("Open Mass Datasets in Visualizer");
		vizButton.setFont(Fonts.buttonFont);
		vizButton.addActionListener(this);
		
		add(refLabel, "1, 1, l, c");
		add(modelsLabel, "1, 3, l, c");
		add(plotLabel, "1, 5, f, c");
		add(plotButton, "1, 7, c, c");
		add(vizButton, "1, 9, c, c");
		
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(frame, string, "Please Wait...");
		
	}
	
	/**
	 * Gets the reference to this class's {@link AnalCalcPlotFrame}.
	 *
	 * @return the plot frame
	 */
	public AnalCalcPlotFrame getPlotFrame(){
		return plotFrame;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==plotButton){
			delayDialog.openDelayDialog();
			delayDialog.setLocationRelativeTo(frame);
			openPlot();
		}else if(ae.getSource()==vizButton){
			frame.openVizTool();
		}
	}

	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		refLabel.setText("Selected Reference Mass Dataset : " + ds.getModelMapSelected().get(ds.getRefIndex()).toString());
		String string = "";
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			int index = itr.next();
			if(index!=ds.getRefIndex()){
				string += ds.getModelMapSelected().get(index).toString() + ", ";
			}
		}
		string = string.substring(0, string.lastIndexOf(","));
		modelsLabel.setText("Selected Mass Datasets : " + string);
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		
	}
	
	/**
	 * Opens the Mass Dataset Plotting Interface.
	 */
	public void openPlot(){
		OpenPlotTask task = new OpenPlotTask(mds, ds, delayDialog, plotFrame);
		task.execute();
	}
	
}

class OpenPlotTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private DelayDialog dialog;
	private MainDataStructure mds;
	private AnalDataStructure ds;
	private AnalCalcPlotFrame plotFrame;
	
	public OpenPlotTask(MainDataStructure mds
							, AnalDataStructure ds
							, DelayDialog dialog
							, AnalCalcPlotFrame plotFrame){
		this.dialog = dialog;
		this.mds = mds;
		this.ds = ds;
		this.plotFrame = plotFrame;
	}
	
	protected Void doInBackground(){
		if(plotFrame==null){
			plotFrame = new AnalCalcPlotFrame(ds, mds);	
		}
		plotFrame.initialize();
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		plotFrame.setVisible(true);
	}
	
}




