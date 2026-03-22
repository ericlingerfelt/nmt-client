package org.nuclearmasses.gui.anal;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import org.nuclearmasses.gui.format.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 * This class is the initial content of the Mass Dataset Analyzer.
 */
public class AnalIntroPanel extends JPanel implements ActionListener{

	/** The calc radio button. */
	protected JRadioButton compRadioButton, calcRadioButton;
	
	/** The desc label. */
	private WordWrapLabel descLabel;
	
	/** The comp desc. */
	private String compDesc = "<html>Compare the RMS deviation of "
								+ "different mass datasets from a reference "
								+ "dataset as <b>AVERAGED</b> over a set of common nuclei.</html>";
	
	/** The calc desc. */
	private String calcDesc = "<html>Calculate the average RMS deviation of different mass "
								+ "datasets from a reference dataset "
								+ "as <b>FUNCTIONS</b> of Z, N, or A for a set of common nuclei.</html>";
	
	/**
	 * Class constructor.
	 */
	public AnalIntroPanel(){
		
		double[] col = {TableLayoutConstants.PREFERRED, 50, TableLayoutConstants.PREFERRED};
		double[] row = {TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED
						, 10, TableLayoutConstants.PREFERRED};
		
		setLayout(new TableLayout(col, row));
		
		JLabel label1 = new JLabel("Mass");
		label1.setFont(Fonts.bigTitleFont);
		JLabel label2 = new JLabel("Dataset");
		label2.setFont(Fonts.bigTitleFont);
		JLabel label3 = new JLabel("Analyzer");
		label3.setFont(Fonts.bigTitleFont);
		
		descLabel = new WordWrapLabel(false);
		descLabel.setText(compDesc);
		descLabel.setPreferredSize(new Dimension(250, 100));
		
		compRadioButton = new JRadioButton("Mass Dataset RMS Comparator", false);
		compRadioButton.setFont(Fonts.textFont);
		compRadioButton.setSelected(true);
		compRadioButton.addActionListener(this);
		
		calcRadioButton = new JRadioButton("Mass Dataset RMS Calculator", false);
		calcRadioButton.setFont(Fonts.textFont);
		calcRadioButton.setSelected(false);
		calcRadioButton.addActionListener(this);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(compRadioButton);
		buttonGroup.add(calcRadioButton);

		add(label1, "0, 0, l, t");
		add(label2, "0, 2, l, t");
		add(label3, "0, 4, l, t");
		add(compRadioButton, "2, 0, l, c");
		add(calcRadioButton, "2, 2, l, c");
		add(descLabel, "2, 4, c, c");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==compRadioButton
				|| ae.getSource()==calcRadioButton){
			if(compRadioButton.isSelected()){
				descLabel.setText(compDesc);
			}else if(calcRadioButton.isSelected()){
				descLabel.setText(calcDesc);
			}
		}
	}
}

