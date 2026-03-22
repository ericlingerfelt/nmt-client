package org.nuclearmasses.gui.viz;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.dialogs.DelayDialog;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.viz.plot.VizPlotFrame;
import org.nuclearmasses.gui.viz.chart.VizChartFrame;
import org.nuclearmasses.gui.viz.s2n.VizS2nPlotFrame;

/**
 * This class is Step 3 of 3 for the Mass Dataset Visualizer.
 */
public class VizToolsPanel extends JPanel implements ActionListener, StateAccessor{

	/** The ds. */
	private VizDataStructure ds;
	
	/** The s2n plot button. */
	private JButton plotButton, chartButton, s2nPlotButton;
	
	/** The s2n plot label. */
	private WordWrapLabel modelsLabel, refLabel, plotLabel
							, chartLabel, rpLabel, s2nPlotLabel;
	
	/** The plot frame. */
	public VizPlotFrame plotFrame;
	
	/** The chart frame. */
	public VizChartFrame chartFrame;
	
	/** The s2n plot frame. */
	public VizS2nPlotFrame s2nPlotFrame;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;			
	
	/** The frame. */
	private JFrame frame;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link VizDataStructure}
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param frame a reference to the {@link JFrame} containing this panel
	 */
	public VizToolsPanel(VizDataStructure ds, MainDataStructure mds, JFrame frame){
		
		this.ds = ds;
		this.mds = mds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		plotLabel = new WordWrapLabel();
		plotLabel.setText("<html>Click the <i>Open Mass Differences/RMS Plotter (1-D)</i> button "
								+ "to make 1-D plots of mass excess differences and the RMS values "
								+ "of mass excess differences.</html>");
		
		s2nPlotLabel = new WordWrapLabel();
		s2nPlotLabel.setText("<html>Click the <i>Open S<sub>2n</sub> Plotter (1-D)</i> button "
								+ "to make 1-D plots of S<sub>2n</sub>.</html>");
		
		chartLabel = new WordWrapLabel();
		chartLabel.setText("<html>Click the <i>Open Interactive Nuclide Chart (2-D)</i> button to view "
								+ "2-D plots of n, 2n, p, 2p, and alpha separation energies and (alpha, n), "
								+ "(alpha, p), and (p, n) Q-values for theoretical and reference mass datasets, "
								+ "as well as the difference and absolute difference of these values.</html>");
		
		rpLabel = new WordWrapLabel();
		rpLabel.setText("<html>r-Process path from <b>NUCLEAR PROPERTIES FOR ASTROPHYSICAL AND RADIOACTIVE-ION-BEAM "
								+ "APPLICATIONS</b> P. MÖLLER, J. R. NIX, K. -L. KRATZ, Atomic Data Nuclear Data "
								+ "Tables 66 (1997) 131. using FRDM95 model.</html>");
		
		modelsLabel = new WordWrapLabel();
		refLabel = new WordWrapLabel();
		
		plotButton = new JButton("Open Mass Differences/RMS Plotter (1-D)");
		plotButton.setFont(Fonts.buttonFont);
		plotButton.addActionListener(this);
		
		s2nPlotButton = new JButton("<html>Open S<sub>2n</sub> Plotter (1-D)</html>");
		s2nPlotButton.setFont(Fonts.buttonFont);
		s2nPlotButton.addActionListener(this);
		
		chartButton = new JButton("Open Interactive Nuclide Chart (2-D)");
		chartButton.setFont(Fonts.buttonFont);
		chartButton.addActionListener(this);
		
		add(refLabel, "1, 1, l, c");
		add(modelsLabel, "1, 3, l, c");
		add(plotLabel, "1, 5, f, c");
		add(plotButton, "1, 7, c, c");
		add(s2nPlotLabel, "1, 9, f, c");
		add(s2nPlotButton, "1, 11, c, c");
		add(chartLabel, "1, 13, f, c");
		add(chartButton, "1, 15, c, c");
		add(rpLabel, "1, 17, f, c");
		
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(frame, string, "Please Wait...");
		
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==plotButton){
			delayDialog.openDelayDialog();
			delayDialog.setLocationRelativeTo(frame);
			openPlot();
		}else if(ae.getSource()==chartButton){
			delayDialog.openDelayDialog();
			delayDialog.setLocationRelativeTo(frame);
			openChart();
		}else if(ae.getSource()==s2nPlotButton){
			delayDialog.openDelayDialog();
			delayDialog.setLocationRelativeTo(frame);
			openS2nPlot();
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
	 * Open chart.
	 */
	private void openChart(){
		OpenChartTask task = new OpenChartTask(mds, ds, this, delayDialog);
		task.execute();
	}
	
	/**
	 * Open plot.
	 */
	private void openPlot(){
		OpenPlotTask task = new OpenPlotTask(mds, ds, this, delayDialog);
		task.execute();
	}

	/**
	 * Open s2n plot.
	 */
	private void openS2nPlot(){
		OpenS2nPlotTask task = new OpenS2nPlotTask(mds, ds, this, delayDialog);
		task.execute();
	}
	
}

class OpenPlotTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private VizToolsPanel panel;
	private DelayDialog dialog;
	private MainDataStructure mds;
	private VizDataStructure ds;
	
	public OpenPlotTask(MainDataStructure mds
							, VizDataStructure ds
							, VizToolsPanel panel
							, DelayDialog dialog){
		this.panel = panel;
		this.dialog = dialog;
		this.mds = mds;
		this.ds = ds;
	}
	
	protected Void doInBackground(){
		if(panel.plotFrame==null){
			panel.plotFrame = new VizPlotFrame(ds, mds);	
		}
		panel.plotFrame.initialize();
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		panel.plotFrame.setVisible(true);
	}
	
}

class OpenS2nPlotTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private VizToolsPanel panel;
	private DelayDialog dialog;
	private MainDataStructure mds;
	private VizDataStructure ds;
	
	public OpenS2nPlotTask(MainDataStructure mds
							, VizDataStructure ds
							, VizToolsPanel panel
							, DelayDialog dialog){
		this.panel = panel;
		this.dialog = dialog;
		this.mds = mds;
		this.ds = ds;
	}
	
	protected Void doInBackground(){
		if(panel.s2nPlotFrame==null){
			panel.s2nPlotFrame = new VizS2nPlotFrame(ds, mds);	
		}
		panel.s2nPlotFrame.initialize();
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		panel.s2nPlotFrame.setVisible(true);
	}
	
}

class OpenChartTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private VizToolsPanel panel;
	private DelayDialog dialog;
	private MainDataStructure mds;
	private VizDataStructure ds;
	
	public OpenChartTask(MainDataStructure mds
							, VizDataStructure ds
							, VizToolsPanel panel
							, DelayDialog dialog){
		this.panel = panel;
		this.dialog = dialog;
		this.mds = mds;
		this.ds = ds;
	}
	
	protected Void doInBackground(){
		try
		{
			if(panel.chartFrame==null){
				panel.chartFrame = new VizChartFrame(ds, mds);
			}
			panel.chartFrame.initialize();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		panel.chartFrame.setVisible(true);
	}
	
}



