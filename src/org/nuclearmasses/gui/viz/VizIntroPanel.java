package org.nuclearmasses.gui.viz;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import javax.swing.*;
import org.nuclearmasses.gui.format.Fonts;

/**
 * This class is the initial content of the Mass Dataset Visualizer.
 */
public class VizIntroPanel extends JPanel{

	/**
	 * Class constructor.
	 */
	public VizIntroPanel(){
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED
							, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Mass");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Dataset");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Visualizer");
		label3.setFont(Fonts.bigTitleFont);

		JLabel label = new JLabel("<html>Welcome to the Mass Dataset Visualizer.</html>");

		add(label1, "0, 0, l, c");
		add(label2, "0, 2, l, c");
		add(label3, "0, 4, l, c");
		add(label, "2, 0, 2, 4, l, t");
	}	
	
}
