package org.nuclearmasses.gui.anal;

import javax.swing.*;
import java.awt.event.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.dialogs.GeneralDialog;
import org.nuclearmasses.gui.format.*;
import java.awt.*;

/**
 * This class is a chart of the nuclides interface used to select a range of isotopes.
 */
public class AnalChartFrame extends JDialog implements ActionListener, AnalChartListener, StateAccessor{

	/** The clear button. */
	private JButton submitButton, cancelButton, selectAllButton, clearButton;
	
	/** The max n field. */
	private JTextField minZField, maxZField, minNField, maxNField;
	
	/** The chart. */
	private AnalChartPanel chart;
	
	/** The n ruler. */
	private AnalIsotopeRuler zRuler, nRuler;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The ds. */
	private AnalDataStructure ds;
	
	/** The acl. */
	private AnalChartListener acl;
	
	/**
	 * Class constructor.
	 *
	 * @param owner the {@link JFrame} who owns this class
	 * @param ds 	a reference to the {@link AnalDataStructure}
	 * @param acl 	the {@link AnalChartListener} which will respond to isotope range selection
	 */
	public AnalChartFrame(JFrame owner, AnalDataStructure ds, AnalChartListener acl){
		super(owner, true);
		this.ds = ds;
		this.acl = acl;
		Container c = getContentPane();
		
		setSize(750, 500);
		setTitle("Select Set from a Nuclide Chart");
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, 
							gap, TableLayoutConstants.PREFERRED, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Select a set of nuclei by dragging your mouse over the nuclide chart " 
							+ "or click <i>Select All Nuclei</i>. Reset your selection by clicking <i>Clear Selection</i>."
							+ " Click <i>Submit Selection</i> to choose the set of nuclei or "
							+ "<i>Close Nuclide Chart</i> to cancel the selection and close this window.</html>");
		
		JLabel minZLabel = new JLabel("Minimum Z : ");
		minZLabel.setFont(Fonts.textFont);
		JLabel maxZLabel = new JLabel("Maximum Z : ");
		maxZLabel.setFont(Fonts.textFont);
		JLabel minNLabel = new JLabel("Minimum N : ");
		minNLabel.setFont(Fonts.textFont);
		JLabel maxNLabel = new JLabel("Maximum N : ");
		maxNLabel.setFont(Fonts.textFont);
		
		minZField = new JTextField();
		maxZField = new JTextField();
		minNField = new JTextField();
		maxNField = new JTextField();
		minZField.setEditable(false);
		maxZField.setEditable(false);
		minNField.setEditable(false);
		maxNField.setEditable(false);
		
		submitButton = new JButton("Submit Selection");
		submitButton.setFont(Fonts.buttonFont);
		submitButton.addActionListener(this);
		
		cancelButton = new JButton("Close Nuclide Chart");
		cancelButton.setFont(Fonts.buttonFont);
		cancelButton.addActionListener(this);
		
		selectAllButton = new JButton("Select All nuclei");
		selectAllButton.setFont(Fonts.buttonFont);
		selectAllButton.addActionListener(this);
		
		clearButton = new JButton("Clear Selection");
		clearButton.setFont(Fonts.buttonFont);
		clearButton.addActionListener(this);
		
		JPanel inputPanel = new JPanel();
		double[] columnInput = {TableLayoutConstants.FILL
				, gap, TableLayoutConstants.FILL};
		double[] rowInput = {TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED};
		inputPanel.setLayout(new TableLayout(columnInput, rowInput));
		inputPanel.add(minZLabel, "0, 0, r, c");
		inputPanel.add(minZField, "2, 0, f, c");
		inputPanel.add(maxZLabel, "0, 2, r, c");
		inputPanel.add(maxZField, "2, 2, f, c");
		inputPanel.add(minNLabel, "0, 4, r, c");
		inputPanel.add(minNField, "2, 4, f, c");
		inputPanel.add(maxNLabel, "0, 6, r, c");
		inputPanel.add(maxNField, "2, 6, f, c");
		inputPanel.add(maxNField, "2, 6, f, c");
		inputPanel.add(maxNField, "2, 6, f, c");
		inputPanel.add(selectAllButton, "0, 8, 2, 8, f, c");
		inputPanel.add(clearButton, "0, 10, 2, 10, f, c");
		inputPanel.add(submitButton, "0, 12, 2, 12, f, c");
		inputPanel.add(cancelButton, "0, 14, 2, 14, f, c");
		
		chart = new AnalChartPanel();
		sp = new JScrollPane(chart);
		chart.setScrollPane(sp);
		
		c.add(topLabel, "1, 1, 3, 1, f, c");
		c.add(sp, "1, 3, f, f");
		c.add(inputPanel, "3, 3, f, t");
		
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.anal.AnalChartListener#updateFields(int, int, int, int)
	 */
	public void updateFields(int zmin, int nmin, int zmax, int nmax){
		minZField.setText(String.valueOf(zmin));
		maxZField.setText(String.valueOf(zmax));
		minNField.setText(String.valueOf(nmin));
		maxNField.setText(String.valueOf(nmax));
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.anal.AnalChartListener#clearFields()
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
		if(ae.getSource()==submitButton){
			if(chart.ipList.size()>0){
				ds.setIPList(chart.ipList);
				acl.updateFields(Integer.valueOf(minZField.getText())
									, Integer.valueOf(minNField.getText())
									, Integer.valueOf(maxZField.getText())
									, Integer.valueOf(maxNField.getText()));
				this.setVisible(false);
				this.dispose();
			}else{
				String string = "Please select at least one isotope from the chart.";
				GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
				dialog.setVisible(true);
			}
		}else if(ae.getSource()==cancelButton){
			this.setVisible(false);
			this.dispose();
		}else if(ae.getSource()==selectAllButton){
			chart.selectAllIsotopes();
			chart.repaint();
		}else if(ae.getSource()==clearButton){
			clearFields();
			chart.ipList.clear();
			chart.repaint();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		chart.initialize(ds.getIPMap(), ds.getIPList(), this);
		initializeScrollPane();
	}
	
	/**
	 * Initialize scroll pane.
	 */
	private void initializeScrollPane(){
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(chart.getHeight());
		int viewX = chart.xoffset+(chart.nmin-1)*chart.boxSize;
		int viewY = sp.getVerticalScrollBar().getMaximum() - chart.yoffset - (chart.zmin)*chart.boxSize;
		sp.getViewport().setViewPosition(new Point(viewX, viewY - 333));
		sp.getVerticalScrollBar().setUnitIncrement(chart.getBoxSize());
		sp.getHorizontalScrollBar().setUnitIncrement(chart.getBoxSize());
		
		zRuler = new AnalIsotopeRuler(AnalIsotopeRuler.HORIZONTAL);
		nRuler = new AnalIsotopeRuler(AnalIsotopeRuler.VERTICAL);
		zRuler.setPreferredWidth((int)chart.getSize().getWidth());
       	nRuler.setPreferredHeight((int)chart.getSize().getHeight());
		zRuler.setCurrentState(chart.getZmax()
								, chart.getNmax()
								, chart.getMouseX()
								, chart.getMouseY()
								, chart.getXOffset()
								, chart.getYOffset()
								, chart.getCrosshairsOn());						
    	nRuler.setCurrentState(chart.getZmax()
								, chart.getNmax()
								, chart.getMouseX()
								, chart.getMouseY()
								, chart.getXOffset()
								, chart.getYOffset()
								, chart.getCrosshairsOn());
    	chart.setZRuler(zRuler);
    	chart.setNRuler(nRuler);
    	sp.setColumnHeaderView(zRuler);
        sp.setRowHeaderView(nRuler);
		sp.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(ScrollPaneConstants.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new Corner());
        sp.setCorner(ScrollPaneConstants.LOWER_RIGHT_CORNER, new Corner());
	}
	
}
