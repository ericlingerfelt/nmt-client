package org.nuclearmasses.gui.anal;

import javax.swing.*;
import java.awt.event.*;

import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import java.awt.*;

/**
 * This class is Step 3 of 4 for the Mass Dataset Analyzer > Mass Dataset RMS Calculator.
 */
public class AnalCalc3Panel extends JPanel implements ActionListener, AnalChartListener, StateAccessor{

	/** The ds. */
	private AnalDataStructure ds;
	
	/** The all button. */
	private JRadioButton massButton, znButton, chartButton, allButton;
	
	/** The input panel. */
	private JPanel inputPanel;
	
	/** The max n field. */
	private JTextField minAField, maxAField, minZField, maxZField, minNField, maxNField;
	
	/** The button. */
	private JButton button;
	
	/** The chart frame. */
	private AnalChartFrame chartFrame;
	
	/** The parent. */
	private JFrame parent;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link AnalDataStructure}
	 * @param parent a reference to the {@link JFrame} containing this panel
	 */
	public AnalCalc3Panel(AnalDataStructure ds, JFrame parent){
		
		this.ds = ds;
		this.parent = parent;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, 
							gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, 50, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please select and utilize a method for choosing "
							+ "a set of nuclei from the intersection of selected datasets.</html>");
		
		button = new JButton("Open Nuclide Chart");
		button.addActionListener(this);
		button.setFont(Fonts.buttonFont);
		
		minAField = new JTextField();
		maxAField = new JTextField();
		minZField = new JTextField();
		maxZField = new JTextField();
		minNField = new JTextField();
		maxNField = new JTextField();
		
		allButton = new JRadioButton("All nuclei");
		allButton.setFont(Fonts.textFont);
		allButton.addActionListener(this);
		
		massButton = new JRadioButton("Enter minimum and maximum mass values");
		massButton.setFont(Fonts.textFont);
		massButton.addActionListener(this);
		
		znButton = new JRadioButton("Enter minimum and maximum Z and N values");
		znButton.setFont(Fonts.textFont);
		znButton.addActionListener(this);
		
		chartButton = new JRadioButton("Select set from nuclide chart");
		chartButton.setFont(Fonts.textFont);
		chartButton.addActionListener(this);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(massButton);
		bg.add(znButton);
		bg.add(chartButton);
		bg.add(allButton);
		
		JPanel buttonPanel = new JPanel();
		double[] columnButton = {TableLayoutConstants.PREFERRED};
		double[] rowButton = {TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED};
		buttonPanel.setLayout(new TableLayout(columnButton, rowButton));
		buttonPanel.add(allButton, "0, 0, l, c");
		buttonPanel.add(massButton, "0, 2, l, c");
		buttonPanel.add(znButton, "0, 4, l, f");
		buttonPanel.add(chartButton, "0, 6, l, c");
		
		inputPanel = new JPanel();
		
		add(topLabel, "1, 1, 3, 1, f, c");
		add(buttonPanel, "1, 3, f, t");
		add(inputPanel, "3, 3, f, t");
		
	}
	
	/**
	 * Sets the Z min/max and N min/max fields to each corresponding argument.
	 *
	 * @param zmin the zmin
	 * @param nmin the nmin
	 * @param zmax the zmax
	 * @param nmax the nmax
	 */
	public void updateFields(int zmin, int nmin, int zmax, int nmax){
		minZField.setText(String.valueOf(zmin));
		maxZField.setText(String.valueOf(zmax));
		minNField.setText(String.valueOf(nmin));
		maxNField.setText(String.valueOf(nmax));
	}
	
	/**
	 * Sets the Z min/max and N min/max fields to an empty String.
	 */
	public void clearFields(){
		minZField.setText("");
		maxZField.setText("");
		minNField.setText("");
		maxNField.setText("");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==massButton
				|| ae.getSource()==znButton
				|| ae.getSource()==chartButton
				|| ae.getSource()==allButton){
			if(massButton.isSelected()){
				setInterface(AnalDataStructure.SelectionMethod.MASS);
			}else if(znButton.isSelected()){
				setInterface(AnalDataStructure.SelectionMethod.ZN);
			}else if(chartButton.isSelected()){
				setInterface(AnalDataStructure.SelectionMethod.CHART);
			}else if(allButton.isSelected()){
				setInterface(AnalDataStructure.SelectionMethod.ALL);
			}
		}else if(ae.getSource()==button){
			chartFrame = new AnalChartFrame(parent, ds, this);
			chartFrame.setCurrentState();
			chartFrame.setVisible(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		massButton.setSelected(false);
		znButton.setSelected(false);
		chartButton.setSelected(false);
		allButton.setSelected(false);
		switch(ds.getMethod()){
			case ALL:
				allButton.setSelected(true);
				break;
			case MASS:
				massButton.setSelected(true);
				break;
			case ZN:
				znButton.setSelected(true);
				break;
			case CHART:
				chartButton.setSelected(true);
				break;
		}
		if(ds.getAPoint()!=null){
			minAField.setText(String.valueOf((int)ds.getAPoint().getX()));
			maxAField.setText(String.valueOf((int)ds.getAPoint().getY()));
		}
		if(ds.getIPMin()!=null){
			minZField.setText(String.valueOf(ds.getIPMin().getZ()));
			minNField.setText(String.valueOf(ds.getIPMin().getN()));
			maxZField.setText(String.valueOf(ds.getIPMax().getZ()));
			maxNField.setText(String.valueOf(ds.getIPMax().getN()));
		}
		setInterface(ds.getMethod());
	}
	
	/**
	 * Sets the interface.
	 *
	 * @param method the new interface
	 */
	private void setInterface(AnalDataStructure.SelectionMethod method){
		double gap = 20;
		
		JLabel minALabel = new JLabel("Minimum A : ");
		minALabel.setFont(Fonts.textFont);
		JLabel maxALabel = new JLabel("Maximum A : ");
		maxALabel.setFont(Fonts.textFont);
		JLabel minZLabel = new JLabel("Minimum Z : ");
		minZLabel.setFont(Fonts.textFont);
		JLabel maxZLabel = new JLabel("Maximum Z : ");
		maxZLabel.setFont(Fonts.textFont);
		JLabel minNLabel = new JLabel("Minimum N : ");
		minNLabel.setFont(Fonts.textFont);
		JLabel maxNLabel = new JLabel("Maximum N : ");
		maxNLabel.setFont(Fonts.textFont);
		
		inputPanel.removeAll();
		
		switch(method){
			case ALL:
			
				
				break;
			case MASS:
				double[] columnMass = {TableLayoutConstants.FILL
										, gap, TableLayoutConstants.FILL};
				double[] rowMass = {TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED};
				inputPanel.setLayout(new TableLayout(columnMass, rowMass));
				
				inputPanel.add(minALabel, "0, 0, r, c");
				inputPanel.add(minAField, "2, 0, f, c");
				inputPanel.add(maxALabel, "0, 2, r, c");
				inputPanel.add(maxAField, "2, 2, f, c");
				
				break;
			case ZN:
				double[] columnZN = {TableLayoutConstants.FILL
									, gap, TableLayoutConstants.FILL};
				double[] rowZN = {TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED};
				inputPanel.setLayout(new TableLayout(columnZN, rowZN));

				minZField.setEditable(true);
				maxZField.setEditable(true);
				minNField.setEditable(true);
				maxNField.setEditable(true);
				
				inputPanel.add(minZLabel, "0, 0, r, c");
				inputPanel.add(minZField, "2, 0, f, c");
				inputPanel.add(maxZLabel, "0, 2, r, c");
				inputPanel.add(maxZField, "2, 2, f, c");
				inputPanel.add(minNLabel, "0, 4, r, c");
				inputPanel.add(minNField, "2, 4, f, c");
				inputPanel.add(maxNLabel, "0, 6, r, c");
				inputPanel.add(maxNField, "2, 6, f, c");
				
				break;
			case CHART:

				double[] columnChart = {TableLayoutConstants.FILL
						, gap, TableLayoutConstants.FILL};
				double[] rowChart = {TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED
										, gap, TableLayoutConstants.PREFERRED
										, 40, TableLayoutConstants.PREFERRED};
				inputPanel.setLayout(new TableLayout(columnChart, rowChart));
			
				minZField.setEditable(false);
				maxZField.setEditable(false);
				minNField.setEditable(false);
				maxNField.setEditable(false);
				
				inputPanel.add(minZLabel, "0, 0, r, c");
				inputPanel.add(minZField, "2, 0, f, c");
				inputPanel.add(maxZLabel, "0, 2, r, c");
				inputPanel.add(maxZField, "2, 2, f, c");
				inputPanel.add(minNLabel, "0, 4, r, c");
				inputPanel.add(minNField, "2, 4, f, c");
				inputPanel.add(maxNLabel, "0, 6, r, c");
				inputPanel.add(maxNField, "2, 6, f, c");
				inputPanel.add(button, "0, 8, 2, 8, f, c");
				break;
		}
		
		validate();
		repaint();
	}
	
	/**
	 * Validates input fields. 
	 * 
	 * @return		true if all relevant fields are validated
	 */
	public boolean checkDataFields(){
		
		try{
			if(massButton.isSelected()){
				if(Integer.valueOf(minAField.getText()) > Integer.valueOf(maxAField.getText())){
					return false;	
				}
			}else if(znButton.isSelected()){
				if((Integer.valueOf(minZField.getText()) > Integer.valueOf(maxZField.getText())) 
						|| (Integer.valueOf(minNField.getText()) > Integer.valueOf(maxNField.getText()))){
					return false;	
				}
			}else if(chartButton.isSelected()){
				if((Integer.valueOf(minZField.getText()) > Integer.valueOf(maxZField.getText())) 
						|| (Integer.valueOf(minNField.getText()) > Integer.valueOf(maxNField.getText()))){
					return false;	
				}
			}
		}catch(NumberFormatException nfe){
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		if(massButton.isSelected()){
			ds.setAPoint(new Point(Integer.valueOf(minAField.getText())
									, Integer.valueOf(maxAField.getText())));
			ds.setMethod(AnalDataStructure.SelectionMethod.MASS);
		}else if(znButton.isSelected()){
			ds.setIPMin(new IsotopePoint(Integer.valueOf(minZField.getText())
									, Integer.valueOf(minNField.getText())));
			ds.setIPMax(new IsotopePoint(Integer.valueOf(maxZField.getText())
									, Integer.valueOf(maxNField.getText())));
			ds.setMethod(AnalDataStructure.SelectionMethod.ZN);
		}else if(chartButton.isSelected()){
			ds.setIPMin(new IsotopePoint(Integer.valueOf(minZField.getText())
									, Integer.valueOf(minNField.getText())));
			ds.setIPMax(new IsotopePoint(Integer.valueOf(maxZField.getText())
									, Integer.valueOf(maxNField.getText())));
			ds.setMethod(AnalDataStructure.SelectionMethod.CHART);
		}else if(chartButton.isSelected()){
			ds.setMethod(AnalDataStructure.SelectionMethod.ALL);
		}		
	}
}

