package org.nuclearmasses.gui.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class is a JDialog which prompts the user to decide whether to:
 * 1) Exit a feature and erase all selected information in the feature's central data structure
 * 2) Exit a feature and retain all selected information in the feature's central data structure
 * 3) Do not exit the feature.
 */
public class FeatureClosingDialog extends JDialog{

	/** The do not close radio button. */
	private JRadioButton saveAndCloseRadioButton
							, closeRadioButton
							, doNotCloseRadioButton;
	
	/** The submit button. */
	private JButton submitButton;
	
	/**
	 * Class constructor.
	 *
	 * @param owner 			the {@link Frame} that owns this dialog
	 * @param al 				an {@link ActionListener} to respond to the Submit button
	 * @param featureString 	a String identifying the feature
	 */
	public FeatureClosingDialog(Frame owner, ActionListener al, String featureString){
	
		super(owner, featureString + " Exit", true);

		setSize(500, 170);
		setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
				, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		double[] colPanel = {TableLayoutConstants.FILL};
		double[] rowPanel = {TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED
							, 5, TableLayoutConstants.PREFERRED};
		
		JPanel panel = new JPanel(new TableLayout(colPanel, rowPanel));
    	
    	saveAndCloseRadioButton = new JRadioButton("Exit and retain " + featureString + " session input.", true);
		saveAndCloseRadioButton.setFont(Fonts.textFont);

    	closeRadioButton = new JRadioButton("Exit and erase " + featureString + " session input.", false);
   		closeRadioButton.setFont(Fonts.textFont);
   		
    	doNotCloseRadioButton = new JRadioButton("Return to the " + featureString + ".", false);
		doNotCloseRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(saveAndCloseRadioButton);
		buttonGroup.add(closeRadioButton);
		buttonGroup.add(doNotCloseRadioButton);

		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(al);
	
		panel.add(saveAndCloseRadioButton, "0, 0, l, c");
		panel.add(closeRadioButton, "0, 2, l, c");
		panel.add(doNotCloseRadioButton, "0, 4, l, c");
		
		c.add(panel, "1, 1, c, c");
		c.add(submitButton, "1, 3, c, c");
		
	}
	
	/**
	 * Gets the <i>Submit</i> button.
	 *
	 * @return 	the  button
	 */
	public JButton getSubmitButton(){return submitButton;}
	
	/**
	 * Gets the <i>Exit and Retain</i> radio button.
	 *
	 * @return 		the  radio button
	 */
	public JRadioButton getSaveAndCloseRadioButton(){return saveAndCloseRadioButton;}
	
	/**
	 * Gets the <i>Exit and Erase</i> radio button.
	 *
	 * @return 		the  radio Button
	 */
	public JRadioButton getCloseRadioButton(){return closeRadioButton;}
	
	/**
	 * Gets the <i>Return</i> radio button.
	 *
	 * @return 		the  radio Button
	 */
	public JRadioButton getDoNotCloseRadioButton(){return doNotCloseRadioButton;}
}
