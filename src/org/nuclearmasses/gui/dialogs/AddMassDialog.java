package org.nuclearmasses.gui.dialogs;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.nuclearmasses.gui.MassModelTable;
import org.nuclearmasses.gui.format.*;

/**
 * This class enables the User to input a new z, n, mass excess, and uncertainty value.
 */
public class AddMassDialog extends JDialog implements ActionListener{
	
	/** The cancel button. */
	private JButton addButton, cancelButton;
	
	/** The table. */
	private MassModelTable table;
	
	/** The ma. */
	private MassAdder ma;
	
	/** The uncer field. */
	private JTextField zField, nField, massField, uncerField;
	
	/**
	 * Class constructor.
	 *
	 * @param frame the owner of this Dialog
	 * @param ma 		a {@link MassAdder} reference
	 * @param table a {@link MassModelTable} reference
	 */
	public AddMassDialog(JFrame frame, MassAdder ma, MassModelTable table){
		
		super(frame, "Add Mass Data Point", true);
		
		this.ma = ma;
		this.table = table;
		
		setSize(420, 290);
		
		Container c = getContentPane();
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};

		c.setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Enter the Z, N, mass excess (MeV) and uncertainty (MeV) values in the fields below and click <i>Add Mass Data Point</i>.</html>");
		
		JLabel zLabel = new JLabel("Z : ");
		zLabel.setFont(Fonts.textFont);
		
		JLabel nLabel = new JLabel("N : ");
		nLabel.setFont(Fonts.textFont);
		
		JLabel massLabel = new JLabel("Mass Excess (MeV) : ");
		massLabel.setFont(Fonts.textFont);
		
		JLabel uncerLabel = new JLabel("Uncertainty (MeV) : ");
		uncerLabel.setFont(Fonts.textFont);
		
		addButton = new JButton("Add Mass Data Point");
		addButton.setFont(Fonts.buttonFont);
		addButton.addActionListener(this);
		
		zField = new JTextField();
		nField = new JTextField();
		massField = new JTextField();
		uncerField = new JTextField();
		
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				setVisible(false);
				dispose();
			}
		});
		
		c.add(topLabel, "1, 1, 3, 1, f, c");
		c.add(zLabel, "1, 3, r, c");
		c.add(zField, "3, 3, f, c");
		c.add(nLabel, "1, 5, r, c");
		c.add(nField, "3, 5, f, c");
		c.add(massLabel, "1, 7, r, c");
		c.add(massField, "3, 7, f, c");
		c.add(uncerLabel, "1, 9, r, c");
		c.add(uncerField, "3, 9, f, c");
		c.add(addButton, "1, 11, f, c");
		c.add(cancelButton, "3, 11, f, c");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==addButton){
			if(goodData()){
				int z = Integer.valueOf(zField.getText());
				int n = Integer.valueOf(nField.getText());
				double mass = NumberLocalizer.valueOf(massField.getText());
				double uncer = NumberLocalizer.valueOf(uncerField.getText());
				if(!table.massExists(z, n)){
					ma.addMass(z, n, mass, uncer);
					setVisible(false);
					dispose();
				}else{
					String string = "A mass excess value exists for this isotope. "
										+ "Please close the <i>Add Mass Data Point</i> dialog and enter the "
										+ "new mass excess in the corresponding table row.";
					GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
					dialog.setVisible(true);
				}
			}else{
				String string = "Please enter values for z, n, mass excess (MeV) and uncertainty (MeV).";
				GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
				dialog.setVisible(true);
			}
		}
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	private boolean goodData(){
		try{
			Integer.valueOf(zField.getText());
			Integer.valueOf(nField.getText());
			NumberLocalizer.valueOf(massField.getText());
			NumberLocalizer.valueOf(uncerField.getText());
		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
}