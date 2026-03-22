package org.nuclearmasses.gui.viz;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.enums.CGIAction;
import org.nuclearmasses.gui.MassFrame;
import org.nuclearmasses.gui.dialogs.DelayDialog;
import org.nuclearmasses.gui.dialogs.GeneralDialog;
import org.nuclearmasses.gui.viz.VizIntroPanel;
import org.nuclearmasses.gui.wizard.WizardFrame;
import org.nuclearmasses.io.CGICom;

/**
 * This class is the main container class for the Mass Dataset Visualizer.
 */
public class VizFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private VizDataStructure ds = new VizDataStructure();
	
	/** The intro panel. */
	private VizIntroPanel introPanel;
	
	/** The model panel. */
	private VizModelPanel modelPanel;
	
	/** The ref panel. */
	private VizRefPanel refPanel;
	
	/** The tools panel. */
	private VizToolsPanel toolsPanel;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/** The Constant DIMENSION. */
	public static final Dimension DIMENSION  = new Dimension(675, 580); 
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure} class
	 * @param cgiCom a reference to the {@link CGICom} class
	 * @param frame a reference to the {@link MassFrame} class which spawns this class
	 */
	public VizFrame(MainDataStructure mds, CGICom cgiCom, MassFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Mass Dataset Visualizer"
				, ""
				, DIMENSION
				, 3);
		
		setNavActionListeners(this);
		introPanel = new VizIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(this, string, "Please Wait...");
	}
	
	/**
	 * Gets the reference to the {@link VizDataStructure}.
	 * 
	 * @return	the reference to the ManDataStructure
	 */
	public VizDataStructure getDataStructure(){return ds;}
	
	/**
	 * Transfers the Mass Dataset Visualizer from Step 2 of 3 to Step 3 of 3.
	 */
	public void gotoTools(){
		toolsPanel = new VizToolsPanel(ds, mds, this);
		toolsPanel.setCurrentState();
		addEndButtons();
		setContentPanel(toolsPanel, 3, "Mass Dataset Visualization Tools", FULL);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
			
			switch(panelIndex){
				
				case 0:
					ds.setType(MassModelType.ALL.name());
					if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
						addFullButtons();
						modelPanel = new VizModelPanel(ds);
						modelPanel.setCurrentState();
						setContentPanel(introPanel, modelPanel, 1, 3, "Select Mass Datasets", FULL);
					}
					break;
					
				case 1:
					if(!modelPanel.isListEmpty()){
						modelPanel.getCurrentState();
						refPanel = new VizRefPanel(ds);
						refPanel.setCurrentState();
						setContentPanel(modelPanel, refPanel, 2, 3, "Select Reference Mass Dataset", FULL);
					}else{
						String string = "Please select at least one mass dataset from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
					break;
					
				case 2:
					if(ds.getModelMapSelected().get(ds.getRefIndex())!=null){
						refPanel.getCurrentState();
						delayDialog.openDelayDialog();
						delayDialog.setLocationRelativeTo(this);
						loadData();
					}else{
						String string = "Please select a mass dataset from the tree.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					}
					break;
				
			}
			
		}else if(ae.getSource()==backButton){
			
			switch(panelIndex){
				case 1:
					modelPanel.setVisible(false);
					setContentPanel(modelPanel, introPanel, 0, "", CENTER);
					addIntroButtons();
					break;
				case 2:
					modelPanel = new VizModelPanel(ds);
					modelPanel.setCurrentState();
					setContentPanel(refPanel, modelPanel, 1, 3, "Select Mass Datasets", FULL);
					break;
				case 3:
					refPanel = new VizRefPanel(ds);
					refPanel.setCurrentState();
					setContentPanel(toolsPanel, refPanel, 2, 3, "Select Reference Mass Dataset", FULL);
					addFullButtons();
					break;
			}
			
		}
		
	}
	
	/**
	 * Closes all windows spawned from this class or this class' children.
	 */
	public void closeAllFrames(){
		if(toolsPanel!=null){
			if(toolsPanel.chartFrame!=null){
				toolsPanel.chartFrame.closeAllFrames();
				toolsPanel.chartFrame.setVisible(false);
				toolsPanel.chartFrame.dispose();
			}
			if(toolsPanel.plotFrame!=null){
				toolsPanel.plotFrame.closeAllFrames();
				toolsPanel.plotFrame.setVisible(false);
				toolsPanel.plotFrame.dispose();
			}
			if(toolsPanel.s2nPlotFrame!=null){
				toolsPanel.s2nPlotFrame.closeAllFrames();
				toolsPanel.s2nPlotFrame.setVisible(false);
				toolsPanel.s2nPlotFrame.dispose();
			}
		}
	}
	
	/**
	 * Load data.
	 */
	private void loadData(){
		LoadDataTask task = new LoadDataTask(mds, ds, cgiCom, this, delayDialog);
		task.execute();
	}
	
}

class LoadDataTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private MainDataStructure mds;
	private CGICom cgiCom;
	private VizDataStructure ds;
	private VizFrame frame;
	private DelayDialog dialog;
	
	public LoadDataTask(MainDataStructure mds
						, VizDataStructure ds
						, CGICom cgiCom
						, VizFrame frame
						, DelayDialog dialog){
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		this.dialog = dialog;
	}
	
	protected Void doInBackground(){
		cgiCom.doCGICall(mds, ds, CGIAction.GET_MODEL_INFO, frame, dialog);
		cgiCom.doCGICall(mds, ds, CGIAction.GET_MODEL_DATA, frame, dialog);
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		frame.gotoTools();
	}
	
}
