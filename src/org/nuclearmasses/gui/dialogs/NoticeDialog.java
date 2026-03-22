package org.nuclearmasses.gui.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.gui.format.WordWrapLabel;

/**
 * This class is a dialog notifying the user to the ORNL web security privacy policy. 
 */
public class NoticeDialog extends JDialog{

	/** The ok button. */
	private JButton okButton;

	/**
	 * Class constructor.
	 *
	 * @param owner the {@link Frame} that owns this dialog
	 * @param kl 		a {@link KeyListener} to handle key events from the OK button
	 */
	public NoticeDialog(Frame owner, KeyListener kl){
		
    	super(owner, "Notice", true);
    
    	setSize(460, 220);
    	
    	double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL, 5, TableLayoutConstants.PREFERRED, gap};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		addKeyListener(kl);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
				dispose();
			}
		});
		
		okButton = new JButton("OK");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		String string = "<html><b>Notice to Users:</b>"
							+ " Use of this system constitutes consent to security monitoring. "
							+ "Improper use could lead to appropriate disciplinary or legal action.<br><br>"
							+ "<b>Disclaimer to Users:</b> This software suite is in development and updated almost daily. "
							+ "Please contact coordinator@nuclearmasses.org to report bugs or problems. Thank you.</html>";
		
		WordWrapLabel textArea = new WordWrapLabel();
		textArea.setText(string);
		
		JScrollPane sp = new JScrollPane(textArea
							, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
							, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(200, 200));
		sp.setBorder(null);
		
		c.add(sp, "1, 1, f, f");
		c.add(okButton, "1, 3, c, c");
		
	}
	
	/**
	 * Gets the <i>OK</i> button.
	 *
	 * @return the  button
	 */
	public JButton getOkButton(){return okButton;}

}