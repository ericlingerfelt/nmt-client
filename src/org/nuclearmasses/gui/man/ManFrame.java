package org.nuclearmasses.gui.man;

import java.awt.event.*;
import java.awt.*;
import org.nuclearmasses.gui.wizard.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.gui.dialogs.*;

/**
 * This class is the main container class for the Mass Dataset Manager.
 */
public class ManFrame extends WizardFrame implements ActionListener{

	/** The ds. */
	private ManDataStructure ds = new ManDataStructure();
	
	/** The intro panel. */
	private ManIntroPanel introPanel;
	
	/** The info1 panel. */
	private ManInfo1Panel info1Panel;
	
	/** The info2 panel. */
	private ManInfo2Panel info2Panel;
	
	/** The erase panel. */
	private ManErasePanel erasePanel;
	
	/** The copy panel. */
	private ManCopyPanel copyPanel;
	
	/** The merge panel. */
	private ManMergePanel mergePanel;
	
	/** The modify1 panel. */
	private ManModify1Panel modify1Panel;
	
	/** The modify2 panel. */
	private ManModify2Panel modify2Panel;
	
	/** The modify3 panel. */
	private ManModify3Panel modify3Panel;
	
	/** The modify4 panel. */
	private ManModify4Panel modify4Panel;
	
	/** The modify5 panel. */
	private ManModify5Panel modify5Panel;
	
	/** The upload1 panel. */
	private ManUpload1Panel upload1Panel;
	
	/** The upload2 panel. */
	private ManUpload2Panel upload2Panel;
	
	/** The upload3 panel. */
	private ManUpload3Panel upload3Panel;
	
	/** The upload4 panel. */
	private ManUpload4Panel upload4Panel;
	
	/** The upload5 panel. */
	private ManUpload5Panel upload5Panel;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/** The Constant DIMENSION. */
	public static final Dimension DIMENSION  = new Dimension(675, 500); 
	
	/** The previous tool. */
	private Tool tool, previousTool;
	
	/**
	 * The Enum Tool.
	 */
	private enum Tool{
/** The INFO. */
INFO, 
 /** The ERASE. */
 ERASE, 
 /** The MERGE. */
 MERGE, 
 /** The UPLOAD. */
 UPLOAD, 
 /** The COPY. */
 COPY, 
 /** The MODIFY. */
 MODIFY};
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure} class
	 * @param cgiCom a reference to the {@link CGICom} class
	 * @param frame a reference to the {@link MassFrame} class which spawns this class
	 */
	public ManFrame(MainDataStructure mds, CGICom cgiCom, MassFrame frame){
		
		super(mds
				, cgiCom
				, frame
				, "Mass Dataset Manager"
				, ""
				, DIMENSION
				, 10);
		
		setNavActionListeners(this);
		introPanel = new ManIntroPanel();
		setContentPanel(introPanel, 0, "", CENTER);
		setIntroPanel(introPanel);
		setDataStructure(ds);
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(this, string, "Please Wait...");
	}
	
	/**
	 * Gets the reference to the {@link ManDataStructure}.
	 * 
	 * @return	the reference to the ManDataStructure
	 */
	public ManDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(ae.getSource()==continueButton){
			
			if(panelIndex==0){
				if(introPanel.infoRadioButton.isSelected()){
					tool = Tool.INFO;
				}else if(introPanel.copyRadioButton.isSelected()){
					tool = Tool.COPY;
				}else if(introPanel.eraseRadioButton.isSelected()){
					tool = Tool.ERASE;
				}else if(introPanel.mergeRadioButton.isSelected()){
					tool = Tool.MERGE;
				}else if(introPanel.uploadRadioButton.isSelected()){
					tool = Tool.UPLOAD;
				}else if(introPanel.modifyRadioButton.isSelected()){
					tool = Tool.MODIFY;
				}
			}
			
			if(previousTool!=tool){
				ds.initialize();
				previousTool = tool;
			}

			switch(tool){
			
				case INFO:
					
					switch(panelIndex){
						case 0:
							ds.setType(MassModelType.ALL.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								addFullButtons();
								info1Panel = new ManInfo1Panel(ds);
								info1Panel.setCurrentState();
								setContentPanel(introPanel, info1Panel, 1, 2, "Mass Dataset Information", FULL);
							}
							break;
						case 1:
							if(!info1Panel.isListEmpty()){
								info1Panel.getCurrentState();
								if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODEL_INFO, this)){
									addEndButtons();
									info2Panel = new ManInfo2Panel(mds, ds, this);
									info2Panel.setCurrentState();
									setContentPanel(info1Panel, info2Panel, 2, 2, "Mass Dataset Information", FULL);
								}
							}else{
								String string = "Please select at least one mass dataset from the tree.";
								GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
								dialog.setVisible(true);
							}
							break;
					}
					break;
					
				case COPY:
					
					switch(panelIndex){
						case 0:
							ds.setType(MassModelType.ALL.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								addEndButtons();
								copyPanel = new ManCopyPanel(mds, ds, cgiCom, this);
								copyPanel.setCurrentState();
								setContentPanel(introPanel, copyPanel, 1, 1, "Copy Mass Dataset to Shared Folder", FULL);
							}
							break;
						}
					break;
					
				case ERASE:
					
					switch(panelIndex){
						case 0:
							ds.setType(MassModelType.USER.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								addEndButtons();
								erasePanel = new ManErasePanel(mds, ds, cgiCom, this);
								erasePanel.setCurrentState();
								setContentPanel(introPanel, erasePanel, 1, 1, "Erase Mass Dataset", FULL);
							}
							break;
					}
					break;
					
				case MERGE:
					
					switch(panelIndex){
						case 0:
							ds.setType(MassModelType.ALL.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								addEndButtons();
								mergePanel = new ManMergePanel(mds, ds, cgiCom, this);
								mergePanel.setCurrentState();
								setContentPanel(introPanel, mergePanel, 1, 1, "Merge Existing Mass Datasets", FULL);
							}
							break;
					}
					break;
					
				case UPLOAD:
					
					switch(panelIndex){
						case 0:
							addFullButtons();
							upload1Panel = new ManUpload1Panel(ds);
							upload1Panel.setCurrentState();
							setContentPanel(introPanel, upload1Panel, 1, 5, "Upload New Mass Dataset", FULL);
							break;
						case 1:
							upload1Panel.getCurrentState();
							upload2Panel = new ManUpload2Panel(ds);
							upload2Panel.setCurrentState();
							setContentPanel(upload1Panel, upload2Panel, 2, 5, "Upload New Mass Dataset", FULL);
							break;
						case 2:
							upload2Panel.getCurrentState();
							upload3Panel = new ManUpload3Panel(ds);
							upload3Panel.setCurrentState();
							setContentPanel(upload2Panel, upload3Panel, 3, 5, "Upload New Mass Dataset", FULL);
							break;
						case 3:
							upload3Panel.getCurrentState();
							upload4Panel = new ManUpload4Panel(ds);
							upload4Panel.setCurrentState();
							setContentPanel(upload3Panel, upload4Panel, 4, 5, "Upload New Mass Dataset", CENTER);
							break;
						case 4:
							ds.setType(MassModelType.ALL.name());
							if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
								upload4Panel.getCurrentState();
								upload5Panel = new ManUpload5Panel(mds, ds, cgiCom, this);
								upload5Panel.setCurrentState();
								addEndButtons();
								if(ds.getUploadMethod()==ManDataStructure.UploadMethod.FILE){
									setContentPanel(upload4Panel, upload5Panel, 5, 5, "Upload New Mass Dataset", CENTER);
								}else{
									setContentPanel(upload4Panel, upload5Panel, 5, 5, "Upload New Mass Dataset", FULL);
								}
							}
							break;
					}
					break;
					
				case MODIFY:
					
					switch(panelIndex){
					case 0:
						ds.setType(MassModelType.ALL.name());
						if(cgiCom.doCGICall(mds, ds, CGIAction.GET_MODELS, this)){
							addFullButtons();
							modify1Panel = new ManModify1Panel(ds);
							modify1Panel.setCurrentState();
							setContentPanel(introPanel, modify1Panel, 1, 5, "Modify Existing Mass Dataset", FULL);
						}
						break;
					case 1:
						if(ds.getModelMapSelected()!=null){
							modify1Panel.getCurrentState();
							delayDialog.openDelayDialog();
							delayDialog.setLocationRelativeTo(this);
							loadModifyData();
						}else{
							String string = "Please select a mass dataset from the tree.";
							GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
							dialog.setVisible(true);
						}
						break;
					case 2:
						modify2Panel.getCurrentState();
						modify3Panel = new ManModify3Panel(ds);
						modify3Panel.setCurrentState();
						setContentPanel(modify2Panel, modify3Panel, 3, 5, "Modify Existing Mass Dataset", FULL);
						break;
					case 3:
						modify3Panel.getCurrentState();
						modify4Panel = new ManModify4Panel(ds);
						modify4Panel.setCurrentState();
						setContentPanel(modify3Panel, modify4Panel, 4, 5, "Modify Existing Mass Dataset", FULL);
						break;
					case 4:
						modify4Panel.getCurrentState();
						modify5Panel = new ManModify5Panel(mds, ds, cgiCom, this);
						modify5Panel.setCurrentState();
						addEndButtons();
						setContentPanel(modify4Panel, modify5Panel, 5, 5, "Modify Existing Mass Dataset", FULL);
						break;
				}
				break;
					
			}
			
		}else if(ae.getSource()==backButton){
			
			switch(tool){
			
				case INFO:
					switch(panelIndex){
						case 1:
							info1Panel.setVisible(false);
							setContentPanel(info1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2:
							addFullButtons();
							info1Panel = new ManInfo1Panel(ds);
							info1Panel.setCurrentState();
							setContentPanel(info2Panel, info1Panel, 1, 2, "Mass Dataset Information", FULL);
							break;
					}
					break;
				case COPY:
					switch(panelIndex){
						case 1:
							copyPanel.setVisible(false);
							setContentPanel(copyPanel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
					}
					break;
				case ERASE:
					switch(panelIndex){
						case 1:
							erasePanel.setVisible(false);
							setContentPanel(erasePanel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
					}
					break;
				case MERGE:
					switch(panelIndex){
						case 1:
							mergePanel.setVisible(false);
							setContentPanel(mergePanel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
					}
					break;
				case UPLOAD:
					switch(panelIndex){
						case 1:
							upload1Panel.setVisible(false);
							setContentPanel(upload1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2:
							upload1Panel = new ManUpload1Panel(ds);
							upload1Panel.setCurrentState();
							setContentPanel(upload2Panel, upload1Panel, 1, 5, "Mass Dataset Information", FULL);
							break;
						case 3:
							upload2Panel = new ManUpload2Panel(ds);
							upload2Panel.setCurrentState();
							setContentPanel(upload3Panel, upload2Panel, 2, 5, "Mass Dataset Information", FULL);
							break;
						case 4:
							upload3Panel = new ManUpload3Panel(ds);
							upload3Panel.setCurrentState();
							addFullButtons();
							setContentPanel(upload4Panel, upload3Panel, 3, 5, "Mass Dataset Information", FULL);
							break;
						case 5:
							upload4Panel = new ManUpload4Panel(ds);
							upload4Panel.setCurrentState();
							addFullButtons();
							setContentPanel(upload5Panel, upload4Panel, 4, 5, "Mass Dataset Information", CENTER);
							break;
					}
					break;
				case MODIFY:
					switch(panelIndex){
						case 1:
							modify1Panel.setVisible(false);
							setContentPanel(modify1Panel, introPanel, 0, "", CENTER);
							addIntroButtons();
							break;
						case 2:
							modify1Panel = new ManModify1Panel(ds);
							modify1Panel.setCurrentState();
							setContentPanel(modify2Panel, modify1Panel, 1, 5, "Modify Existing Mass Dataset", FULL);
							break;
						case 3:
							modify2Panel = new ManModify2Panel(ds);
							modify2Panel.setCurrentState();
							setContentPanel(modify3Panel, modify2Panel, 2, 5, "Modify Existing Mass Dataset", FULL);
							break;
						case 4:
							modify3Panel = new ManModify3Panel(ds);
							modify3Panel.setCurrentState();
							setContentPanel(modify4Panel, modify3Panel, 3, 5, "Modify Existing Mass Dataset", FULL);
							break;
						case 5:
							modify4Panel = new ManModify4Panel(ds);
							modify4Panel.setCurrentState();
							addFullButtons();
							setContentPanel(modify5Panel, modify4Panel, 4, 5, "Modify Existing Mass Dataset", FULL);
							break;
					}
					break;
			}
			
		}
		
	}
	
	/**
	 * Closes all windows spawned from this class or this class' children.
	 */
	public void closeAllFrames(){
		
	}
	
	/**
	 * Transfers the Mass Dataset Manager from Step 1 of 5 of the Modify Existing Mass Dataset tool
	 * to Step 2 of 5 of the Modify Existing Mass Dataset tool.
	 */
	public void gotoModify2(){
		modify2Panel = new ManModify2Panel(ds);
		modify2Panel.setCurrentState();
		setContentPanel(modify1Panel, modify2Panel, 2, 5, "Modify Existing Mass Dataset", FULL);
	}
	
	/**
	 * Load modify data.
	 */
	private void loadModifyData(){
		LoadModifyDataTask task = new LoadModifyDataTask(mds, ds, cgiCom, this, delayDialog);
		task.execute();
	}
	
}

class LoadModifyDataTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private MainDataStructure mds;
	private CGICom cgiCom;
	private ManDataStructure ds;
	private ManFrame frame;
	private DelayDialog dialog;
	
	public LoadModifyDataTask(MainDataStructure mds
								, ManDataStructure ds
								, CGICom cgiCom
								, ManFrame frame
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
		frame.gotoModify2();
	}
	
}
