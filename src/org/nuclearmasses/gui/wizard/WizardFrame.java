package org.nuclearmasses.gui.wizard;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.nuclearmasses.gui.MassFrame;
import org.nuclearmasses.datastructure.MainDataStructure;
import org.nuclearmasses.datastructure.DataStructure;
import org.nuclearmasses.gui.dialogs.CautionDialog;
import org.nuclearmasses.gui.dialogs.FeatureClosingDialog;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.io.CGICom;
import info.clearthought.layout.*;

/**
 *This class creates a general frame for wizard features.
 */
public abstract class WizardFrame extends JFrame{

	/** The mds. */
	protected MainDataStructure mds;
	
	/** The cgi com. */
	protected CGICom cgiCom;
	
	/** The frame. */
	protected MassFrame frame;
	
	/** The ds. */
	protected DataStructure ds;
	
	/** The intro panel. */
	protected JPanel introPanel;
	
	/** The closing dialog. */
	protected FeatureClosingDialog closingDialog;
	
	/** The continue on dialog. */
	protected CautionDialog continueOnDialog;

	/** The c. */
	protected Container c;
	
	/** The button panel. */
	protected JPanel buttonPanel;
	
	/** The home button. */
	protected JButton backButton, endButton, continueOnButton, continueButton, homeButton;
	
	/** The panel index. */
	protected int panelIndex;
	
	/** The panel string. */
	protected String panelString;
	
	/** The title panel. */
	private TitlePanel titlePanel;
	
	/** The step panel. */
	private StepPanel stepPanel;
	
	/** The number of steps. */
	private int numberOfSteps;
	
	/** The current panel. */
	private JPanel currentPanel;
	
	/** The size. */
	private Dimension size;
	
	/** The Constant TITLE. */
	private static final String TITLE = "1, 1, l, t";
	
	/** The Constant STEP. */
	private static final String STEP = "3, 1, r, t";
	
	/** The Constant CENTER. */
	protected static final String CENTER = "1, 3, 3, 3, c, c";
	
	/** The Constant FULL. */
	protected static final String FULL = "1, 3, 3, 3, f, f";
	
	/** The Constant BUTTON. */
	private static final String BUTTON = "1, 5, 3, 5, c, b";
	
	/** The Constant FULL_WIDTH. */
	protected static final String FULL_WIDTH = "1, 3, 3, 3, f, c";
	
	/** The Constant SAVE_AND_CLOSE. */
	public static final int SAVE_AND_CLOSE = 0;
	
	/** The Constant CLOSE. */
	public static final int CLOSE = 1;
	
	/** The Constant DO_NOT_CLOSE. */
	public static final int DO_NOT_CLOSE = 2;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 				a reference to the {@link MainDataStructure}
	 * @param cgiCom 			a reference to the {@link CGICom}
	 * @param frame 			a reference to the main entry window
	 * @param title 			the title for this feature
	 * @param continueOnTitle 	the title for the "Continue On" button
	 * @param size 			the size of this feature at startup
	 * @param numberOfSteps 	the number of steps for this feature
	 */
	public WizardFrame(MainDataStructure mds
						, CGICom cgiCom
						, MassFrame frame
						, final String title
						, String continueOnTitle
						, Dimension size
						, int numberOfSteps){
		
		this.mds = mds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		this.size = size;
		this.numberOfSteps = numberOfSteps;

		setTitle(title);
		setSize(size);
		
		titlePanel = new TitlePanel(title, panelString);
		stepPanel = new StepPanel(panelIndex, numberOfSteps);
		
		CloseWizardActionListener al = new CloseWizardActionListener(WizardFrame.this);
    	closingDialog = new FeatureClosingDialog(WizardFrame.this, al, title);
    	al.setClosingDialog(closingDialog);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){   
				if(we.getSource()==WizardFrame.this){  
		        	closingDialog.setLocationRelativeTo(WizardFrame.this);
		        	closingDialog.setVisible(true);
		    	}
		    }
		});
		
		double border = 5;
		double gap = 5;
		double[] col = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, border};
		double[] row = {border, TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, border};
		
		c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		backButton = new JButton("< Back");
		backButton.setFont(Fonts.buttonFont);
		
		continueButton = new JButton("Continue >");
		continueButton.setFont(Fonts.buttonFont);
		
		endButton = new JButton("Close " + title);
		endButton.setFont(Fonts.buttonFont);
		endButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				closingDialog.setLocationRelativeTo(WizardFrame.this);
	        	closingDialog.setVisible(true);
			}
		});
		
		continueOnButton = new JButton(continueOnTitle);
		continueOnButton.setFont(Fonts.buttonFont);
		
		homeButton = new JButton(title + " Home");
		homeButton.setFont(Fonts.buttonFont);
		
		panelIndex = 0;
		buttonPanel = new JPanel();
		
		c.add(titlePanel, TITLE);
		c.add(stepPanel, STEP);
		c.add(buttonPanel, BUTTON);
		
		addIntroButtons();

	}
	
	/**
	 * Sets the {@link DataStructure} for this feature.
	 * 
	 * @param	ds	the DataStructure for this feature
	 */
	protected void setDataStructure(DataStructure ds){this.ds = ds;}
	
	/**
	 * Sets the initial content for this feature.
	 * 
	 * @param	introPanel	the initial content for this feature
	 */
	protected void setIntroPanel(JPanel introPanel){this.introPanel = introPanel;}
	
	/**
	 * Sets the current content for this feature.
	 * 
	 * @param	currentPanel	the current content
	 * @param	newPanel		the new content
	 * @param	panelIndex		the index for this new content
	 * @param	numberOfSteps	the total number of steps for this feature
	 * @param	panelString		the string to display for this content
	 * @param	constraints		the constraints for this feature's {@link TableLayout}
	 */
	protected void setContentPanel(JPanel currentPanel
									, JPanel newPanel
									, int panelIndex
									, int numberOfSteps
									, String panelString
									, String constraints){
		
		this.panelIndex = panelIndex;
		this.numberOfSteps = numberOfSteps;
		this.currentPanel = currentPanel;
		stepPanel.setNumberOfSteps(numberOfSteps);
		stepPanel.setCurrentStep(panelIndex);
		titlePanel.setPanelString(panelString);
		stepPanel.setVisible(panelIndex!=0);
		titlePanel.setVisible(panelIndex!=0);
		if(currentPanel!=null){
			c.remove(currentPanel);
		}
		if(newPanel!=null){
			c.add(newPanel, constraints);
		}
		this.currentPanel = newPanel;
		c.repaint();
		validate();
	}
	
	/**
	 * Sets the current content for this feature.
	 * 
	 * @param	oldPanel	the current content
	 * @param	newPanel		the new content
	 * @param	panelIndex		the index for this new content
	 * @param	panelString		the string to display for this content
	 * @param	constraints		the constraints for this feature's {@link TableLayout}
	 */
	protected void setContentPanel(JPanel oldPanel, JPanel newPanel, int panelIndex, String panelString, String constraints){
		setContentPanel(oldPanel, newPanel, panelIndex, numberOfSteps, panelString, constraints);
	}
	
	/**
	 * Sets the current content for this feature.
	 * 
	 * @param	newPanel		the new content
	 * @param	panelIndex		the index for this new content
	 * @param	panelString		the string to display for this content
	 * @param	constraints		the constraints for this feature's {@link TableLayout}
	 */
	protected void setContentPanel(JPanel newPanel, int panelIndex, String panelString, String constraints){
		setContentPanel(currentPanel, newPanel, panelIndex, numberOfSteps, panelString, constraints);
	}
	
	/**
	 * Adds the intro button panel.
	 */
	protected void addIntroButtons(){
		buttonPanel.removeAll();
		buttonPanel.add(continueButton);
	}
	
	/**
	 * Adds the full button panel.
	 */
	protected void addFullButtons(){
		buttonPanel.removeAll();
		buttonPanel.add(backButton);
		buttonPanel.add(continueButton);
	}
	
	/**
	 * Adds the end button panel.
	 */
	protected void addEndButtons(){
		buttonPanel.removeAll();
		buttonPanel.add(backButton);
		buttonPanel.add(endButton);
		buttonPanel.add(homeButton);
		if(!continueOnButton.getText().equals("")){
			buttonPanel.add(continueOnButton);
		}
	}
	
	/**
	 * Sets the {@link ActionListener} for this feature's buttons.
	 * 
	 * @param	al	an ActionListener
	 */
	protected void setNavActionListeners(ActionListener al){
		backButton.addActionListener(al);
		continueButton.addActionListener(al);
		endButton.addActionListener(al);
		continueOnButton.addActionListener(al);
		homeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				c.removeAll();
				c.add(titlePanel, TITLE);
				c.add(stepPanel, STEP);
				c.add(buttonPanel, BUTTON);
				addIntroButtons();
				setContentPanel(introPanel, 0, "", CENTER);
			}
		});
	}
	
	/**
	 * Closes this feature.
	 * 
	 * @param	type	the type of closure; either
	 * 					SAVE_AND_CLOSE = 0;
	 *					CLOSE = 1;
	 *					DO_NOT_CLOSE = 2;
	 */
	public void closeWizard(int type){
		
		closingDialog.setVisible(false);
		closingDialog.dispose();
		
		if(type==DO_NOT_CLOSE){
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}else{
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			closeAllFrames();
	 		setVisible(false);
	 		setSize(size);
			c.removeAll();
			c.add(titlePanel, TITLE);
			c.add(stepPanel, STEP);
			c.add(buttonPanel, BUTTON);
			addIntroButtons();
			setContentPanel(introPanel, 0, "", CENTER);
			if(type==CLOSE){ds.initialize();}
			dispose();
		}

	}
	
	/**
	 * Close all frames.
	 */
	public abstract void closeAllFrames();
	
}

class CloseWizardActionListener implements ActionListener{
	
	private FeatureClosingDialog closingDialog;
	private WizardFrame frame;
	
	public CloseWizardActionListener(WizardFrame frame){
		this.frame = frame;
	}
	
	public void setClosingDialog(FeatureClosingDialog closingDialog){
		this.closingDialog = closingDialog;
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==closingDialog.getSubmitButton()){
			if(closingDialog.getSaveAndCloseRadioButton().isSelected()){
				frame.closeWizard(WizardFrame.SAVE_AND_CLOSE);
			}else if(closingDialog.getCloseRadioButton().isSelected()){
				frame.closeWizard(WizardFrame.CLOSE);
			}else if(closingDialog.getDoNotCloseRadioButton().isSelected()){
				frame.closeWizard(WizardFrame.DO_NOT_CLOSE);
			}
		}
	}
}

class TitlePanel extends JPanel{

	private JLabel label;
	private String featureString;
	
	public TitlePanel(String featureString, String panelString){
		this.featureString = featureString;
		setLayout(new FlowLayout());
		label = new JLabel(featureString + " | " + panelString);
		add(label);
	}
	
	public void setPanelString(String panelString){
		label.setText(featureString + " | " + panelString);
	}
	
}

class StepPanel extends JPanel{
	
	private JLabel label;
	private int numberOfSteps, currentStep;
	
	public StepPanel(int currentStep, int numberOfSteps){
		this.numberOfSteps = numberOfSteps;
		setLayout(new FlowLayout());
		label = new JLabel("Step " + String.valueOf(currentStep) + " of " + String.valueOf(numberOfSteps));
		add(label);
	}
	
	public void setCurrentStep(int currentStep){
		this.currentStep = currentStep;
		label.setText("Step " + currentStep + " of " + numberOfSteps);
	}
	
	public void setNumberOfSteps(int numberOfSteps){
		this.numberOfSteps = numberOfSteps;
		label.setText("Step " + currentStep + " of " + numberOfSteps);
	}
	
}

