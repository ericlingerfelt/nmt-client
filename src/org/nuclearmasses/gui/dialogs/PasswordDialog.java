package org.nuclearmasses.gui.dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import info.clearthought.layout.*;
import org.nuclearmasses.gui.format.Fonts;

/**
 * This class is a JDialog which prompts the user to log in as:
 * 1) guest
 * 2) a registered user with a username and password.
 */
public class PasswordDialog extends JDialog implements ItemListener{
	
	/** The c. */
	private Container c;
	
	/** The password label. */
	private JLabel userLabel, passwordLabel;
	
	/** The user field. */
	private JTextField userField;
	
	/** The password field. */
	private JPasswordField passwordField;
	
	/** The submit button. */
	private JButton submitButton;
	
	/** The user radio button. */
	private JRadioButton guestRadioButton, userRadioButton; 
	
	/** The button panel. */
	private JPanel fieldPanel, buttonPanel;
	
	/**
	 * Class constructor.
	 *
	 * @param owner 	Frame to open this dialog over
	 * @param al the al
	 * @param kl the kl
	 */
	public PasswordDialog(Frame owner, ActionListener al, KeyListener kl){
		
		super(owner, "Log On", true);
		
		setSize(355, 156);
		
		double gap = 10;
		double[] col = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 15, TableLayoutConstants.PREFERRED
							, 15, TableLayoutConstants.PREFERRED, gap};
		
		c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		setLocationRelativeTo(owner);
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				setVisible(false);
    			dispose();
    		}
    	});
    	
    	setLocationRelativeTo(owner);
		addKeyListener(kl);
		
		double[] colButton = {TableLayoutConstants.FILL};
		double[] rowButton = {TableLayoutConstants.PREFERRED, 5, TableLayoutConstants.PREFERRED};
		buttonPanel = new JPanel(new TableLayout(colButton, rowButton));
		
		double[] colField = {TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL};
		double[] rowField = {TableLayoutConstants.PREFERRED, gap, TableLayoutConstants.PREFERRED};
		fieldPanel = new JPanel(new TableLayout(colField, rowField));
		
		userLabel = new JLabel("Username : ");
		userLabel.setFont(Fonts.textFont);
		
		passwordLabel = new JLabel("Password : ");
		passwordLabel.setFont(Fonts.textFont); 
		
		userField = new JTextField(10);
		userField.setFont(Fonts.textFont);
		userField.addKeyListener(kl);
		
		submitButton = new JButton("Submit");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(al);
		
		passwordField = new JPasswordField(10);
		passwordField.setFont(Fonts.textFont);
		passwordField.addKeyListener(kl);
		
		guestRadioButton = new JRadioButton("Log in as guest (limited access)", true);
		guestRadioButton.addItemListener(this);
		
		userRadioButton = new JRadioButton("Log in as registered user (full access)", false);
		userRadioButton.addItemListener(this);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(guestRadioButton);
		buttonGroup.add(userRadioButton);
	
		fieldPanel.add(userLabel, "0, 0, r, c");
		fieldPanel.add(userField, "2, 0, f, c");
		fieldPanel.add(passwordLabel, "0, 2, r, c");
		fieldPanel.add(passwordField, "2, 2, f, c");
		
		buttonPanel.add(guestRadioButton, "0, 0, l, c");
		buttonPanel.add(userRadioButton, "0, 2, l, c");
		
		c.add(buttonPanel, "1, 1, c, c");
		c.add(submitButton, "1, 5, c, c");
		
	}
	
	/**
	 * Gets the <i>Submit</i> button.
	 *
	 * @return the  button
	 */
	public JButton getSubmitButton(){return submitButton;}
	
	/**
	 * Gets the <i>User</i> radio button.
	 *
	 * @return the  radio button
	 */
	public JRadioButton getUserRadioButton(){return userRadioButton;}
	
	/**
	 * Gets the <i>Guest</i> radio button.
	 *
	 * @return the  radio button
	 */
	public JRadioButton getGuestRadioButton(){return guestRadioButton;}
	
	/**
	 * Gets the text entered into the <i>Username</i> field.
	 *
	 * @return the text entered into the  field
	 */
	public String getUserString(){return userField.getText();}
	 
	/**
	 * Gets the text entered into the <i>Password</i> field.
	 *
	 * @return the text entered into the  field
	 */
	public String getPasswordString(){return String.valueOf(passwordField.getPassword());}
	
	/**
	 * Initializes this dialog.
	 */
	public void initialize(){
		passwordField.setText("");
		userField.setText("");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){
		if(guestRadioButton.isSelected()){
			c.remove(fieldPanel);
			setSize(355, 165);
		}else if(userRadioButton.isSelected()){
			c.add(fieldPanel, "1, 3, l, c");
			setSize(355, 208);
		}
		repaint();
		validate();	
	}
	
}


