package org.nuclearmasses.gui.man;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import org.nuclearmasses.gui.format.*;
import javax.swing.*;

/**
 * This class is the initial content of the Mass Dataset Manager.
 */
public class ManIntroPanel extends JPanel{

	/** The modify radio button. */
	protected JRadioButton infoRadioButton, uploadRadioButton, mergeRadioButton
							, eraseRadioButton, copyRadioButton, modifyRadioButton;
	
	/**
	 * Class constructor.
	 */
	public ManIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Mass");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Dataset");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Manager");
		label3.setFont(Fonts.bigTitleFont);
		
		infoRadioButton = new JRadioButton("Mass Dataset Information", false);
		infoRadioButton.setFont(Fonts.textFont);
				
		uploadRadioButton = new JRadioButton("Upload New Mass Dataset", true);
		uploadRadioButton.setFont(Fonts.textFont);
		
		mergeRadioButton = new JRadioButton("Merge Existing Mass Datasets", false);
		mergeRadioButton.setFont(Fonts.textFont);
		
		eraseRadioButton = new JRadioButton("Erase Mass Dataset", false);
		eraseRadioButton.setFont(Fonts.textFont);
		
		copyRadioButton = new JRadioButton("Copy Mass Dataset to Shared Folder", false);
		copyRadioButton.setFont(Fonts.textFont);
		
		modifyRadioButton = new JRadioButton("Modify Existing Mass Dataset", false);
		modifyRadioButton.setFont(Fonts.textFont);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(infoRadioButton);
		buttonGroup.add(uploadRadioButton);
		buttonGroup.add(mergeRadioButton);
		buttonGroup.add(copyRadioButton);
		buttonGroup.add(eraseRadioButton);
		buttonGroup.add(modifyRadioButton);

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(uploadRadioButton, "2, 0, l, c");
		add(modifyRadioButton, "2, 2, l, c");
		add(mergeRadioButton, "2, 4, l, c");
		add(copyRadioButton, "2, 6, l, c");
		add(infoRadioButton, "2, 8, l, c");
		add(eraseRadioButton, "2, 10, l, c");

	}
	
}
