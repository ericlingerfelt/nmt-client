package org.nuclearmasses.gui.anal;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import org.nuclearmasses.gui.wizard.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.gui.dialogs.*;

/**
 * This class is the main container class for the Mass Dataset Analyzer.
 */
public class AnalFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private AnalDataStructure ds = new AnalDataStructure();
	
	/** The intro panel. */
	private AnalIntroPanel introPanel;
	
	/** The comp1 panel. */
	private AnalComp1Panel comp1Panel;
	
	/** The comp2 panel. */
	private AnalComp2Panel comp2Panel;
	
	/** The comp3 panel. */
	private AnalComp3Panel comp3Panel;
	
	/** The comp4 panel. */
	private AnalComp4Panel comp4Panel;
	
	/** The comp5 panel. */
	private AnalComp5Panel comp5Panel;
	
	/** The calc1 panel. */
	private AnalCalc1Panel calc1Panel;
	
	/** The calc2 panel. */
	private AnalCalc2Panel calc2Panel;
	
	/** The calc3 panel. */
	private AnalCalc3Panel calc3Panel;
	
	/** The calc4 panel. */
	private AnalCalc4Panel calc4Panel;
	
	/** The Constant DIMENSION. */
	public static final Dimension DIMENSION  = new Dimension(675, 500); 
	
	/** The previous tool. */
	private Tool tool, previousTool;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/**
	 * The Enum Tool.
	 */
	private enum Tool{
/** The COMP. */
COMP, 
 /** The CALC. */
 CALC};
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure} class
	 * @param cgiCom a reference to the {@link CGICom} class
	 * @param frame a reference to the {@link MassFrame} class which spawns this class
	 */
	public AnalFrame(MainDataStructure mds, CGICom cgiCom, MassFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Mass Dataset Analyzer"
				, ""
				, DIMENSION
				, 10);
		
		setNavActionListeners(this);
		introPanel = new AnalIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(this, string, "Please Wait...");
	}
	
	/**
	 * Gets the reference to the {@link AnalDataStructure}.
	 * 
	 * @return	the reference to the AnalDataStructure
	 */
	public AnalDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
			
			if(panelIndex==0){
				if(introPanel.compRadioButton.isSelected()){
					tool = Tool.COMP;
				}else if(introPanel.calcRadioButton.isSelected()){
					tool = Tool.CALC;
				}
			}
			
			if(previousTool!=tool){
				ds.initialize();
				previousTool = tool;
			}
			
			switch(tool){
			
				case COMP:
				
					switch(panelIndex){
						case 0:
							ds.setType(MassModelType.ALL.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								addFullButtons();
								comp1Panel = new AnalComp1Panel(ds);
								comp1Panel.setCurrentState();
								setContentPanel(introPanel, comp1Panel, 1, 5, "Mass Dataset RMS Comparator", FULL);
							}
							break;
						case 1:
							if(!comp1Panel.isListEmpty()){
								comp1Panel.getCurrentState();
								comp2Panel = new AnalComp2Panel(ds);
								comp2Panel.setCurrentState();
								setContentPanel(comp1Panel, comp2Panel, 2, 5, "Mass Dataset RMS Comparator", FULL);
							}else{
								String string = "Please select at least one mass dataset from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
						case 2:
							if(ds.getModelMapSelected().get(ds.getRefIndex())!=null){
								comp2Panel.getCurrentState();
								delayDialog.openDelayDialog();
								delayDialog.setLocationRelativeTo(this);
								loadCompData();
							}else{
								String string = "Please select a mass dataset from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
						case 3:
							if(comp3Panel.checkDataFields()){
								comp3Panel.getCurrentState();
								comp4Panel = new AnalComp4Panel(ds);
								comp4Panel.setCurrentState();
								setContentPanel(comp3Panel, comp4Panel, 4, 5, "Mass Dataset RMS Comparator", FULL);
							}else{
								String string = "Please select a valid set of nuclides.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
						case 4:
							addEndButtons();
							comp4Panel.getCurrentState();
							comp5Panel = new AnalComp5Panel(mds, ds, this);
							comp5Panel.setCurrentState();
							setContentPanel(comp4Panel, comp5Panel, 5, 5, "Mass Dataset RMS Comparator", FULL);
							break;
						
					}
					break;
					
				case CALC:
					
					switch(panelIndex){
						case 0:
							ds.setType(MassModelType.ALL.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								addFullButtons();
								calc1Panel = new AnalCalc1Panel(ds);
								calc1Panel.setCurrentState();
								setContentPanel(introPanel, calc1Panel, 1, 4, "Mass Dataset RMS Calculator", FULL);
							}
							break;
						case 1:
							if(!calc1Panel.isListEmpty()){
								calc1Panel.getCurrentState();
								calc2Panel = new AnalCalc2Panel(ds);
								calc2Panel.setCurrentState();
								setContentPanel(calc1Panel, calc2Panel, 2, 4, "Mass Dataset RMS Calculator", FULL);
							}else{
								String string = "Please select at least one mass dataset from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
						case 2:
							if(ds.getModelMapSelected().get(ds.getRefIndex())!=null){
								calc2Panel.getCurrentState();
								delayDialog.openDelayDialog();
								delayDialog.setLocationRelativeTo(this);
								loadCalcData();
							}else{
								String string = "Please select a mass dataset from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
						case 3:
							addEndButtons();
							calc3Panel.getCurrentState();
							calc4Panel = new AnalCalc4Panel(ds, mds, this);
							calc4Panel.setCurrentState();
							setContentPanel(calc3Panel, calc4Panel, 4, 4, "Mass Dataset RMS Calculator", FULL);
							break;
							
					}
					break;
				
			}
			
		}else if(ae.getSource()==backButton){
			
			switch(tool){
				
				case COMP:
					
					switch(panelIndex){
					
						case 1:
							comp1Panel.setVisible(false);
							setContentPanel(comp1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
							
						case 2:
							comp1Panel = new AnalComp1Panel(ds);
							comp1Panel.setCurrentState();
							setContentPanel(comp2Panel, comp1Panel, 1, 5, "Mass Dataset RMS Comparator", FULL);
							break;
							
						case 3:
							comp2Panel = new AnalComp2Panel(ds);
							comp2Panel.setCurrentState();
							setContentPanel(comp3Panel, comp2Panel, 2, 5, "Mass Dataset RMS Comparator", FULL);
							break;
							
						case 4:
							comp3Panel = new AnalComp3Panel(ds, this);
							comp3Panel.setCurrentState();
							setContentPanel(comp4Panel, comp3Panel, 3, 5, "Mass Dataset RMS Comparator", FULL);
							break;
							
						case 5:
							comp4Panel = new AnalComp4Panel(ds);
							comp4Panel.setCurrentState();
							addFullButtons();
							setContentPanel(comp5Panel, comp4Panel, 4, 5, "Mass Dataset RMS Comparator", FULL);
							break;
						
					}
					break;
					
				case CALC:
					
					switch(panelIndex){
					
						case 1:
							calc1Panel.setVisible(false);
							setContentPanel(calc1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
							
						case 2:
							calc1Panel = new AnalCalc1Panel(ds);
							calc1Panel.setCurrentState();
							setContentPanel(calc2Panel, calc1Panel, 1, 4, "Mass Dataset RMS Calculator", FULL);
							break;
							
						case 3:
							calc2Panel = new AnalCalc2Panel(ds);
							calc2Panel.setCurrentState();
							setContentPanel(calc3Panel, calc2Panel, 2, 4, "Mass Dataset RMS Calculator", FULL);
							break;
							
						case 4:
							calc3Panel = new AnalCalc3Panel(ds, this);
							calc3Panel.setCurrentState();
							addFullButtons();
							setContentPanel(calc4Panel, calc3Panel, 3, 4, "Mass Dataset RMS Calculator", FULL);
							break;
						
					}
					break;

			}
		}
	}
	
	/**
	 * Transfers the Mass Dataset Analyzer from Step 5 of 5 of the Mass Dataset RMS Comparator
	 * to Step 4 of 4 of the Mass Dataset RMS Calculator.
	 */
	public void gotoCalcTool(){
		tool = Tool.CALC;
		previousTool = tool;
		panelIndex = 4;
		calc4Panel = new AnalCalc4Panel(ds, mds, this);
		calc4Panel.setCurrentState();
		setContentPanel(comp5Panel, calc4Panel, 4, 4, "Mass Dataset RMS Calculator", FULL);
	}
	
	/**
	 * Transfers the Mass Dataset Analyzer from Step 2 of 5 of the Mass Dataset RMS Comparator
	 * to Step 3 of 5 of the Mass Dataset RMS Comparator.
	 */
	public void gotoComp3(){
		comp3Panel = new AnalComp3Panel(ds, this);
		comp3Panel.setCurrentState();
		setContentPanel(comp2Panel, comp3Panel, 3, 5, "Mass Dataset RMS Comparator", FULL);
	} 
	
	/**
	 * Transfers the Mass Dataset Analyzer from Step 2 of 5 of the Mass Dataset RMS Calculator
	 * to Step 3 of 5 of the Mass Dataset RMS Calculator.
	 */
	public void gotoCalc3(){
		calc3Panel = new AnalCalc3Panel(ds, this);
		calc3Panel.setCurrentState();
		setContentPanel(calc2Panel, calc3Panel, 3, 4, "Mass Dataset RMS Calculator", FULL);
	} 
	
	/**
	 * Load comp data.
	 */
	private void loadCompData(){
		LoadCompDataTask task = new LoadCompDataTask(mds, ds, cgiCom, this, delayDialog);
		task.execute();
	}
	
	/**
	 * Load calc data.
	 */
	private void loadCalcData(){
		LoadCalcDataTask task = new LoadCalcDataTask(mds, ds, cgiCom, this, delayDialog);
		task.execute();
	}
	
	/**
	 * Closes all windows spawned from this class or this class' children.
	 */
	public void closeAllFrames(){
		if(calc4Panel!=null && calc4Panel.getPlotFrame()!=null){
			calc4Panel.getPlotFrame().closeAllFrames();
			calc4Panel.getPlotFrame().setVisible(false);
			calc4Panel.getPlotFrame().dispose();
		}
	}
	
	/**
	 * Opens the Mass Dataset Visualizer with the current Reference Dataset
	 * , the current set of selected datasets, and the complete set of datasets.
	 */
	public void openVizTool(){
		frame.openViz(ds.getRefIndex(), ds.getModelMapSelected(), ds.getModelMap());
	}
	
}

class LoadCompDataTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private MainDataStructure mds;
	private CGICom cgiCom;
	private AnalDataStructure ds;
	private AnalFrame frame;
	private DelayDialog dialog;
	
	public LoadCompDataTask(MainDataStructure mds
								, AnalDataStructure ds
								, CGICom cgiCom
								, AnalFrame frame
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
		ds.createAndOrderIPMap();
		ds.setIPList(new ArrayList<IsotopePoint>());
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		frame.gotoComp3();
	}
	
}

class LoadCalcDataTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private MainDataStructure mds;
	private CGICom cgiCom;
	private AnalDataStructure ds;
	private AnalFrame frame;
	private DelayDialog dialog;
	
	public LoadCalcDataTask(MainDataStructure mds
							, AnalDataStructure ds
							, CGICom cgiCom
							, AnalFrame frame
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
		ds.createAndOrderIPMap();
		ds.setIPList(new ArrayList<IsotopePoint>());
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		frame.gotoCalc3();
	}
	
}
