package org.nuclearmasses.gui.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class is a utility dialog which prompts the user that something has occurred.
 */
public class GeneralDialog extends JDialog{

	/**
	 * Class constructor specifying a Frame as the owner.
	 *
	 * @param owner 			the {@link Frame} that owns this dialog
	 * @param string the string
	 * @param titleString 	the String to be displayed in this dialog's title bar
	 */
	public GeneralDialog(Frame owner, String string, String titleString){
    	super(owner, titleString, true);
    	setSize(355, 200);
    	setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
    					, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));
    	createDialog(string);
	}
	
	/**
	 * Class constructor specifying another dialog as the owner.
	 *
	 * @param owner 			the {@link Dialog} that owns this dialog
	 * @param string the string
	 * @param titleString 	the String to be displayed in this dialog's title bar
	 */
	public GeneralDialog(Dialog owner, String string, String titleString){
    	super(owner, titleString, true);
    	setSize(355, 200);
    	setLocation((int)(owner.getLocation().getX() + owner.getSize().width/2.0 - this.getSize().width/2.0)
    					, (int)(owner.getLocation().getY() + owner.getSize().height/2.0 - this.getSize().height/2.0));
    	
    	createDialog(string);
	}
	
	/**
	 * Creates the dialog.
	 *
	 * @param string the string
	 */
	private void createDialog(String string){

    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		JButton okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		WordWrapLabel textArea = new WordWrapLabel();
		textArea.setText(string);
		
		JScrollPane sp = new JScrollPane(textArea
							, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
							, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 200));
		
		c.add(textArea, "1, 1, f, f");
		c.add(okButton, "1, 3, c, c");
	}
	
}

