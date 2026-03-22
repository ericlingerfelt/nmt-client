package org.nuclearmasses.gui.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.gui.format.WordWrapLabel;
import info.clearthought.layout.*;

/**
 * This class creates a dialog allowing the User to enter a name for a dataset.
 */
public class SaveDialog extends JDialog{

	/** The save button. */
	private JButton saveButton;
	
	/** The save field. */
	private JTextField saveField;

	/**
	 * Class constructor.
	 *
	 * @param owner 	Frame to open this dialog over
	 * @param al 		an ActionListener to respond to the yes and no buttons
	 * @param string 	a String to be displayed in the dialog
	 * @param titleString the title string
	 */
	public SaveDialog(Frame owner, ActionListener al, String string, String titleString){
	
		super(owner, titleString, true);
		setSize(500, 250);
		setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
				, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));
		
		Container c = getContentPane();
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED, gap};

		c.setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText(string);
		JLabel saveLabel = new JLabel("Enter name : ");
		saveLabel.setFont(Fonts.textFont);
		
		saveField = new JTextField();
		
		JPanel panel = new JPanel();
		double[] columnPanel = {TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.FILL};
		double[] rowPanel = {TableLayoutConstants.PREFERRED};
		panel.setLayout(new TableLayout(columnPanel, rowPanel));
		
		panel.add(saveLabel, "0, 0, r, c");
		panel.add(saveField, "2, 0, f, c");
		
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(al);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});

		JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 30, 30));
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		
		c.add(topLabel, "1, 1, c, c");
		c.add(panel, "1, 3, f, c");
		c.add(buttonPanel, "1, 5, c, c");
		
	}
	
	/**
	 * Gets the <i>Save</i> button.
	 *
	 * @return the  button
	 */
	public JButton getSaveButton(){return saveButton;}
	
	/**
	 * Gets the <i>Save As</i> text.
	 *
	 * @return the  text
	 */
	public String getSaveText(){return saveField.getText().trim();}

}
