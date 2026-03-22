package org.nuclearmasses.gui.dialogs;

import info.clearthought.layout.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.gui.format.WordWrapLabel;

/**
 * This class is a utility dialog for Yes/No decisions.
 */
public class CautionDialog extends JDialog{

	/** The no button. */
	private JButton yesButton, noButton;

	/**
	 * Class constructor.
	 *
	 * @param owner 			the {@link Frame} that owns this dialog
	 * @param al 				an {@link ActionListener} to respond to the yes and no buttons
	 * @param string the string
	 * @param titleString 	the String to be displayed in this dialog's title bar
	 */
	public CautionDialog(Frame owner, ActionListener al, String string, String titleString){
	
		super(owner, titleString, true);
		setSize(320, 215);
		setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
				, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));


		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		
		WordWrapLabel textArea = new WordWrapLabel();
		textArea.setText(string);
		textArea.setCaretPosition(0);
		
		JScrollPane sp = new JScrollPane(textArea
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(300, 100));
		sp.setBorder(null);
		
		yesButton = new JButton("Yes");
		yesButton.setFont(Fonts.buttonFont);
		yesButton.addActionListener(al);
		
		noButton = new JButton("No");
		noButton.setFont(Fonts.buttonFont);
		noButton.addActionListener(al);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(yesButton);
		buttonPanel.add(noButton);

		c.add(sp, "1, 1, f, f");
		c.add(buttonPanel, "1, 3, c, c");

	}
	
	/**
	 * Gets the <i>Yes</i> button.
	 *
	 * @return the  button
	 */
	public JButton getYesButton(){return yesButton;}
	
	/**
	 * Gets the <i>No</i> button.
	 *
	 * @return the  button
	 */
	public JButton getNoButton(){return noButton;}

}


