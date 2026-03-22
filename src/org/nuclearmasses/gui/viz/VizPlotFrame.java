package org.nuclearmasses.gui.viz;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.print.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.util.*;
import org.nuclearmasses.gui.export.copy.*;
import org.nuclearmasses.gui.export.print.*;
import org.nuclearmasses.gui.export.save.*;

/**
 * The Class VizPlotFrame.
 */
public class VizPlotFrame extends JFrame implements ActionListener, WindowListener, ItemListener, ChangeListener{

	/** The gbc. */
	private GridBagConstraints gbc;
	
	/** The viz plot panel. */
	private VizPlotPanel vizPlotPanel;
	
	/** The viz table frame. */
	private VizTableFrame vizTableFrame;
	
	/** The table button. */
	private JButton printButton, saveButton, applyButton, tableButton;
	
	/** The Ymax spinner. */
	private JSpinner XmaxSpinner, XminSpinner, YminSpinner, YmaxSpinner;
	
	/** The Ymax model. */
	private SpinnerNumberModel XminModel, XmaxModel, YminModel, YmaxModel;
	
	/** The minor y box. */
	private JCheckBox majorXBox, minorXBox, majorYBox, minorYBox;
	
	/** The ymin label. */
	private JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel;
	
	/** The model combo box. */
	public JComboBox XComboBox, modelComboBox;
	
	/** The control panel. */
	private JPanel buttonPanel, controlPanel;
	
	/** The c. */
	private Container c;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The Diff radio button. */
	private JRadioButton RMSRadioButton, DiffRadioButton;
	
	/** The button group. */
	private ButtonGroup buttonGroup;
	
	/** The type. */
	public String type;
	
	/** The ds. */
	private VizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The mmds ref. */
	public MassModelDataStructure mmds, mmdsRef;
	
	/** The Z array. */
	double[] ZArray;
	
	/** The N array. */
	double[] NArray;
	
	/** The A array. */
	double[] AArray;
	
	/** The point vector. */
	Vector pointVector;
	
	/** The diff array. */
	double[] diffArray;
	
	/** The abs diff array. */
	double[] absDiffArray;
	
	/** The Z array rms. */
	double[] ZArrayRMS;
	
	/** The N array rms. */
	double[] NArrayRMS;
	
	/** The A array rms. */
	double[] AArrayRMS;
	
	/** The RMSZ array. */
	double[] RMSZArray;
	
	/** The RMSN array. */
	double[] RMSNArray;
	
	/** The RMSA array. */
	double[] RMSAArray;
	
	/** The Zmin. */
	int Zmin;
	
	/** The Zmax. */
	int Zmax;
	
	/** The Nmin. */
	int Nmin;
	
	/** The Nmax. */
	int Nmax;
	
	/** The Amin. */
	int Amin;
	
	/** The Amax. */
	int Amax;
	
	/** The Zmin rms. */
	int ZminRMS;
	
	/** The Zmax rms. */
	int ZmaxRMS;
	
	/** The Nmin rms. */
	int NminRMS;
	
	/** The Nmax rms. */
	int NmaxRMS;
	
	/** The Amin rms. */
	int AminRMS;
	
	/** The Amax rms. */
	int AmaxRMS;
	
	/** The diffmin. */
	double diffmin;
	
	/** The diffmax. */
	double diffmax;
	
	/** The abs diffmin. */
	double absDiffmin;
	
	/** The abs diffmax. */
	double absDiffmax;
	
	/** The RMS zmin. */
	double RMSZmin;
	
	/** The RMS zmax. */
	double RMSZmax;
	
	/** The RMS nmin. */
	double RMSNmin;
	
	/** The RMS nmax. */
	double RMSNmax;
	
	/** The RMS amin. */
	double RMSAmin;
	
	/** The RMS amax. */
	double RMSAmax;

	/**
	 * Instantiates a new viz plot frame.
	 *
	 * @param ds the ds
	 * @param mds the mds
	 */
	public VizPlotFrame(VizDataStructure ds, MainDataStructure mds){
		this.mds = mds;
		this.ds = ds;
		c = getContentPane();
		setSize(756, 670);
		setVisible(true);
		addWindowListener(this);
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		type = "Diff";
		createFormatPanel();
		setModelComboBox();
		setFormatPanelState();
		createResultsPlot();
		validate();
	}
	
	/**
	 * Sets the model combo box.
	 */
	public void setModelComboBox(){
		modelComboBox.removeActionListener(this);
		Iterator<MassModelDataStructure> itr = ds.getModelMapSelected().values().iterator();
		while(itr.hasNext()){
			MassModelDataStructure model = itr.next();
			if(model.getIndex()!=ds.getRefIndex()){
				modelComboBox.addItem(model);
			}
		}
		modelComboBox.setSelectedIndex(0);
		modelComboBox.addActionListener(this);
	}
	
	/**
	 * Sets the format panel state.
	 */
	public void setFormatPanelState(){

		mmds = (MassModelDataStructure)modelComboBox.getSelectedItem();
		mmdsRef = ds.getModelMapSelected().get(ds.getRefIndex());
		
		parseModelDataStructures();
		
		if(type.equals("Diff")){
	
			setTitle("Mass Model Evaluator Plotting Interface : Mass Differences of "
						+ mmds.toString()
						+ " and "
						+ mmdsRef.toString());
						
		}else if(type.equals("RMS")){
	
			setTitle("Mass Model Evaluator Plotting Interface : RMS Difference of "
						+ mmds.toString()
						+ " and "
						+ mmdsRef.toString());
						
		}
        
		XminSpinner.removeChangeListener(this);
		XmaxSpinner.removeChangeListener(this);
		
		YminSpinner.removeChangeListener(this);
		YmaxSpinner.removeChangeListener(this);
		
		if(type.equals("Diff")){
	
			if(XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
		
				XminModel.setMinimum(new Integer(0));
				XminModel.setValue(new Integer(Zmin));
		        XminModel.setMaximum(new Integer(Zmax-1));
			
		       	XmaxModel.setMinimum(new Integer(Zmin+1));
				XmaxModel.setValue(new Integer(Zmax));
	        
	        	xminLabel.setText("Z Min");
	        
	        	YminModel.setValue(new Double(diffmin));
		        YminModel.setMaximum(new Double(diffmax-1));
			
		       	YmaxModel.setMinimum(new Double(diffmin+1));
				YmaxModel.setValue(new Double(diffmax));
		    
		    	yminLabel.setText("Mass Diff Min");
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
			
				XminModel.setMinimum(new Integer(0));
				XminModel.setValue(new Integer(Nmin));
		        XminModel.setMaximum(new Integer(Nmax-1));
			
		       	XmaxModel.setMinimum(new Integer(Nmin+1));
				XmaxModel.setValue(new Integer(Nmax));
	       
	        	xminLabel.setText("N Min");
	        
	        	YminModel.setValue(new Double(diffmin));
		        YminModel.setMaximum(new Double(diffmax-1));
			
		       	YmaxModel.setMinimum(new Double(diffmin+1));
				YmaxModel.setValue(new Double(diffmax));
		    
		    	yminLabel.setText("Mass Diff Min");
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("A = Z + N")){
			
				XminModel.setMinimum(new Integer(1));
				XminModel.setValue(new Integer(Amin));
		        XminModel.setMaximum(new Integer(Amax-1));
			
		       	XmaxModel.setMinimum(new Integer(Amin+1));
				XmaxModel.setValue(new Integer(Amax));
	        
	        	xminLabel.setText("A Min");
	        	
	        	YminModel.setValue(new Double(diffmin));
		        YminModel.setMaximum(new Double(diffmax-1));
			
		       	YmaxModel.setMinimum(new Double(diffmin+1));
				YmaxModel.setValue(new Double(diffmax));
		    
		    	yminLabel.setText("Mass Diff Min");
	        	
	    	}
						
		}else if(type.equals("RMS")){
	
			if(XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
		
				XminModel.setMinimum(new Integer(0));
				//XminModel.setValue(new Integer(ZminRMS));
				XminModel.setValue(new Integer(0));
		        XminModel.setMaximum(new Integer(ZmaxRMS-1));
			
		       	XmaxModel.setMinimum(new Integer(ZminRMS+1));
				XmaxModel.setValue(new Integer(ZmaxRMS));
	        
	        	xminLabel.setText("Z Min");
	        	yminLabel.setText("RMS Min");
	        	
	        	YminModel.setMinimum(new Double(0.0));
				//YminModel.setValue(new Double(RMSZmin));
				YminModel.setValue(new Double(0.0));
		        YminModel.setMaximum(new Double(RMSZmax-1));
			
		       	YmaxModel.setMinimum(new Double(RMSZmin+1));
				YmaxModel.setValue(new Double(RMSZmax));
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
			
				XminModel.setMinimum(new Integer(0));
				//XminModel.setValue(new Integer(NminRMS));
				XminModel.setValue(new Integer(0));
		        XminModel.setMaximum(new Integer(NmaxRMS-1));
			
		       	XmaxModel.setMinimum(new Integer(NminRMS+1));
				XmaxModel.setValue(new Integer(NmaxRMS));
		        
		        xminLabel.setText("N Min");
		        yminLabel.setText("RMS Min");
		        
		        YminModel.setMinimum(new Double(0.0));
				//YminModel.setValue(new Double(RMSNmin));
				YminModel.setValue(new Double(0.0));
		        YminModel.setMaximum(new Double(RMSNmax-1));
			
		       	YmaxModel.setMinimum(new Double(RMSNmin+1));
				YmaxModel.setValue(new Double(RMSNmax));
	        
	    	}else if(XComboBox.getSelectedItem().toString().equals("A = Z + N")){
			
				XminModel.setMinimum(new Integer(1));
				//XminModel.setValue(new Integer(AminRMS));
				XminModel.setValue(new Integer(1));
		        XminModel.setMaximum(new Integer(AmaxRMS-1));
			
		       	XmaxModel.setMinimum(new Integer(AminRMS+1));
				XmaxModel.setValue(new Integer(AmaxRMS));
		        
		        xminLabel.setText("A Min");
		        yminLabel.setText("RMS Min");
		        
		        YminModel.setMinimum(new Double(0.0));
				//YminModel.setValue(new Double(RMSAmin));
				YminModel.setValue(new Double(0.0));
		        YminModel.setMaximum(new Double(RMSAmax-1));
			
		       	YmaxModel.setMinimum(new Double(RMSAmin+1));
				YmaxModel.setValue(new Double(RMSAmax));
	 
	    	}
						
		}
        
        XminSpinner.addChangeListener(this);
		XmaxSpinner.addChangeListener(this);

        YminSpinner.addChangeListener(this);
		YmaxSpinner.addChangeListener(this);
	}
	
	/**
	 * Creates the results plot.
	 */
	public void createResultsPlot(){
		vizPlotPanel = new VizPlotPanel(ds, this);
		vizPlotPanel.setPreferredSize(vizPlotPanel.getSize());
		vizPlotPanel.revalidate();
		sp = new JScrollPane(vizPlotPanel);
		c.add(sp, BorderLayout.CENTER);
		setVisible(true);
		vizPlotPanel.setPreferredSize(vizPlotPanel.getSize());
		vizPlotPanel.revalidate();
	}
	
	/**
	 * Parses the model data structures.
	 */
	public void parseModelDataStructures(){
		
		pointVector = getPointVector();
		
		////////////////////////////////////////1-D///////////////////////////////////////////
		ZArray = getZArray();
		Zmin = getZmin();
		Zmax = getZmax();

		NArray = getNArray();
		Nmin = getNmin();
		Nmax = getNmax();

		AArray = getAArray();
		Amin = getAmin();
		Amax = getAmax();
		///////////////////////////////////////////////////////////////////////////////////////////
		
		diffmin = getDiffmin();
		diffmax = getDiffmax();

		absDiffArray = getAbsDiffArray();
		
		absDiffmin = getAbsDiffmin();
		absDiffmax = getAbsDiffmax();
		
		///////////////////////////////////////1-D/////////////////////////////////////////////
		ZArrayRMS = getZArrayRMS();
		ZminRMS = getZminRMS();
		ZmaxRMS = getZmaxRMS();

		NArrayRMS = getNArrayRMS();
		NminRMS = getNminRMS();
		NmaxRMS = getNmaxRMS();

		AArrayRMS = getAArrayRMS();
		AminRMS = getAminRMS();
		AmaxRMS = getAmaxRMS();

		RMSZArray = getRMSZArray();
		RMSZmin = getRMSZmin();
		RMSZmax = getRMSZmax();

		RMSNArray = getRMSNArray();
		RMSNmin = getRMSNmin();
		RMSNmax = getRMSNmax();

		RMSAArray = getRMSAArray();
		RMSAmin = getRMSAmin();
		RMSAmax = getRMSAmax();
		/////////////////////////////////////////////////////////////////////////////////////////////
		
	}
	
	/**
	 * Creates the format panel.
	 */
	public void createFormatPanel(){
		buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
		
		gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);   
        
        XComboBox = new JComboBox();
        XComboBox.addItem("Z (proton number)");
        XComboBox.addItem("N (neutron number)");
        XComboBox.addItem("A = Z + N");
        XComboBox.setSelectedItem("Z (proton number)");
        XComboBox.setFont(Fonts.textFont);
        XComboBox.addItemListener(this);

        modelComboBox = new JComboBox();
        modelComboBox.setFont(Fonts.textFont);
        modelComboBox.addActionListener(this);
        
		//Create Radio Buttons//////////////////////////////////////////RAIODBUTTONS//////////
		RMSRadioButton = new JRadioButton("RMS of Difference", false);
		RMSRadioButton.addItemListener(this);
		
		DiffRadioButton = new JRadioButton("Mass Differences", true);
		DiffRadioButton.addItemListener(this);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(RMSRadioButton);
		buttonGroup.add(DiffRadioButton);
		
		//Create Check boxes////////////////////////////////////////////CHECKBOXES////////////
		majorXBox = new JCheckBox("Major Gridlines", true);
		majorXBox.addItemListener(this);
		majorXBox.setFont(Fonts.textFont);
		
		minorXBox = new JCheckBox("Minor Gridlines", true);
		minorXBox.addItemListener(this);
		minorXBox.setFont(Fonts.textFont);
		
		majorYBox = new JCheckBox("Major Gridlines", true);
		majorYBox.addItemListener(this);
		majorYBox.setFont(Fonts.textFont);
		
		minorYBox = new JCheckBox("Minor Gridlines", false);
		minorYBox.addItemListener(this);
		minorYBox.setFont(Fonts.textFont);
		
		controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
		
		XminModel = new SpinnerNumberModel(0, 0, 150, 1);
        XmaxModel = new SpinnerNumberModel(0, 0, 999, 1);
		
        XminSpinner = new JSpinner(XminModel);
        XminSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(XminSpinner.getEditor())).getTextField().setEditable(false);
        
        XmaxSpinner = new JSpinner(XmaxModel);
        XmaxSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(XmaxSpinner.getEditor())).getTextField().setEditable(false);
        
		YminModel = new SpinnerNumberModel(0.0
    										, null
    										, null
    										, new Double(1.0));
    										
        YmaxModel = new SpinnerNumberModel(0.0
    										, null
    										, null
    										, new Double(1.0));
        
        YminSpinner = new JSpinner(YminModel);
        YminSpinner.addChangeListener(this);

        YmaxSpinner = new JSpinner(YmaxModel);
        YmaxSpinner.addChangeListener(this);
		
		JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
		//Create buttons////////////////////////////////////////////////BUTTONS//////////
		printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setFont(Fonts.buttonFont);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(Fonts.buttonFont);

        tableButton = new JButton("Table of Points");
        tableButton.setFont(Fonts.buttonFont);
        tableButton.addActionListener(this);
        
		applyButton = new JButton("Apply Entered Range");
        applyButton.setFont(Fonts.buttonFont);
        applyButton.addActionListener(this);
	
		buttonPanel.add(printButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(tableButton);
	
		xmaxLabel = new JLabel("Max");
        xmaxLabel.setFont(Fonts.textFont);
        
        xminLabel = new JLabel("Z Min");
        xminLabel.setFont(Fonts.textFont);
        
        ymaxLabel = new JLabel("Max");
        ymaxLabel.setFont(Fonts.textFont);
        
        yminLabel = new JLabel("Mass Diff Min");
        yminLabel.setFont(Fonts.textFont);
    	
    	gbc.insets = new Insets(3, 3, 3, 3);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.gridwidth = 8;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(DiffRadioButton, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 1;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(new JLabel("Select Z, N, or A : "), gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(yminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(YminSpinner, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(ymaxLabel, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(YmaxSpinner, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorYBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 1;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(RMSRadioButton, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(XComboBox, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(XminSpinner, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(XmaxSpinner, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorXBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		gbc.gridwidth = 8;
  		controlPanel.add(buttonPanel, gbc);

		gbc.gridwidth = 1;
    	
		c.add(controlPanel, BorderLayout.SOUTH);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
    
    	XminModel.setMaximum(new Integer(XmaxModel.getNumber().intValue()-1));
    	XmaxModel.setMinimum(new Integer(XminModel.getNumber().intValue()+1));
    
    	YminModel.setMaximum(new Double(YmaxModel.getNumber().doubleValue()-1));
    	YmaxModel.setMinimum(new Double(YminModel.getNumber().doubleValue()+1));
    
    	redrawPlot();
    
    }
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==printButton){
		
			PlotPrinter.print(new VizPlotPrintable(vizPlotPanel), this);
		
		}else if(ae.getSource()==saveButton){
			
			PlotSaver.savePlot(vizPlotPanel, this, mds);
			
		}else if(ae.getSource()==applyButton){
		
			redrawPlot();
		
		}else if(ae.getSource()==tableButton){
		
			if(vizTableFrame==null){
	       	
				vizTableFrame = new VizTableFrame(ds, mds, this);
       		
	       	}else{
	       	
	       		vizTableFrame.setTableText(mmds);
	       		vizTableFrame.setVisible(true);
	       	
	       	}
		}else if(ae.getSource()==modelComboBox){
			setFormatPanelState();
			createResultsPlot();
			validate();
		}
	}
	
	/**
	 * Redraw plot.
	 */
	public void redrawPlot(){
		vizPlotPanel.setPlotState();
		vizPlotPanel.repaint();
	}
	
	/**
	 * Gets the xmin.
	 *
	 * @return the xmin
	 */
	public double getXmin(){return XminModel.getNumber().intValue();} 
	
	/**
	 * Gets the xmax.
	 *
	 * @return the xmax
	 */
	public double getXmax(){return XmaxModel.getNumber().intValue();}
	
	/**
	 * Gets the ymin.
	 *
	 * @return the ymin
	 */
	public double getYmin(){return YminModel.getNumber().doubleValue();}
	
	/**
	 * Gets the ymax.
	 *
	 * @return the ymax
	 */
	public double getYmax(){return YmaxModel.getNumber().doubleValue();}
	
	/**
	 * Gets the minor x.
	 *
	 * @return the minor x
	 */
	public boolean getMinorX(){return minorXBox.isSelected();} 
	
	/**
	 * Gets the major x.
	 *
	 * @return the major x
	 */
	public boolean getMajorX(){return majorXBox.isSelected();} 
	
	/**
	 * Gets the minor y.
	 *
	 * @return the minor y
	 */
	public boolean getMinorY(){return minorYBox.isSelected();} 
	
	/**
	 * Gets the major y.
	 *
	 * @return the major y
	 */
	public boolean getMajorY(){return majorYBox.isSelected();} 
	
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
        
      	if(ie.getSource()==majorXBox){
      		
            if(majorXBox.isSelected()){
            	
                minorXBox.setEnabled(true);
                
            }else{
            	
                minorXBox.setSelected(false);
                minorXBox.setEnabled(false);
                
            }
            
            redrawPlot();
            
        }else if(ie.getSource()==majorYBox){
        	
            if(majorYBox.isSelected()){
            	
                minorYBox.setEnabled(true);
                
            }else{

                minorYBox.setSelected(false);
                minorYBox.setEnabled(false);  
                
            }
            
            redrawPlot();
            
        }else if((ie.getSource()==minorXBox) 
        		|| (ie.getSource()==minorYBox)){
            redrawPlot();
        }else if(ie.getSource()==XComboBox){
        	setFormatPanelState();
    		validate();
        	redrawPlot();
        }else if(ie.getSource()==RMSRadioButton || ie.getSource()==DiffRadioButton){
        	if(RMSRadioButton.isSelected()){
        		type = "RMS";
        	}else if(DiffRadioButton.isSelected()){
        		type = "Diff";
        	}
        	setFormatPanelState();
    		validate();
        	redrawPlot();
        }
    }
	
	//Window closing listener
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {  
		setVisible(false);
		dispose(); 
    } 
    
    //Remainder of windowListener methods
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){}
    
    /**
     * Gets the point vector.
     *
     * @return the point vector
     */
    public Vector getPointVector(){
		
		Vector<Point> outputVector = new Vector<Point>();
		Vector<Double> diffVector = new Vector<Double>();
		
		mmds.createArrays();
		mmdsRef.createArrays();
		
		int theoryLength = mmds.getZNArray().length;
		int expLength = mmdsRef.getZNArray().length;
		
		for(int i=0; i<theoryLength; i++){
		
			for(int j=0; j<expLength; j++){
				
				int ZTheory = (int)mmds.getZNArray()[i].getX();
				int NTheory = (int)mmdsRef.getZNArray()[i].getY();
		
				int ZExp = (int)mmds.getZNArray()[j].getX();
				int NExp = (int)mmdsRef.getZNArray()[j].getY();
		
				if((ZTheory==ZExp)&&(NTheory==NExp)){
				
					outputVector.addElement(new Point(ZTheory, NTheory));
					
					double mass = mmds.getMassArray()[i]
									- mmdsRef.getMassArray()[j];
					
					diffVector.addElement(new Double(mass));

				}
			}
		}
		
		outputVector.trimToSize();
		diffVector.trimToSize();
		
		double[] outputArray = new double[diffVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = (diffVector.elementAt(i)).doubleValue();
		}
		diffArray = outputArray;
		return outputVector;
	}
	
	/**
	 * Find biggest z.
	 *
	 * @param vector the vector
	 * @return the int
	 */
	public int findBiggestZ(Vector vector){
		int biggestZ = 0;
		for(int i=0; i<vector.size(); i++){
			biggestZ = Math.max(biggestZ, (int)((Point)(vector.elementAt(i))).getX());
		}
		return biggestZ;
	}
	
	/**
	 * Gets the z array.
	 *
	 * @return the z array
	 */
	public double[] getZArray(){
		double[] outputArray = new double[pointVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = ((Point)(pointVector.elementAt(i))).getX();
		}
		return outputArray;
	}
	
	/**
	 * Gets the n array.
	 *
	 * @return the n array
	 */
	public double[] getNArray(){
		double[] outputArray = new double[pointVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = ((Point)(pointVector.elementAt(i))).getY();
		}
		return outputArray;
	}
	
	/**
	 * Gets the a array.
	 *
	 * @return the a array
	 */
	public double[] getAArray(){
		double[] outputArray = new double[pointVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = ((Point)(pointVector.elementAt(i))).getX() + ((Point)(pointVector.elementAt(i))).getY();
		}
		return outputArray;
	}
	
	/**
	 * Gets the abs diff array.
	 *
	 * @return the abs diff array
	 */
	public double[] getAbsDiffArray(){
		double[] outputArray = new double[diffArray.length];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = Math.abs(diffArray[i]);
		}
		return outputArray;
	}
	
	/**
	 * Gets the z array rms.
	 *
	 * @return the z array rms
	 */
	public double[] getZArrayRMS(){
		Vector<Integer> ZVector = new Vector<Integer>();
		for(int i=0; i<pointVector.size(); i++){
			Integer tempDouble = new Integer((int)((Point)(pointVector.elementAt(i))).getX());
			if(!ZVector.contains(tempDouble)){
				ZVector.addElement(tempDouble);
			}
		}
		ZVector.trimToSize();
		
		int[] outputArray = new int[ZVector.size()];
		for(int i=0; i<ZVector.size(); i++){
			outputArray[i] = (ZVector.elementAt(i)).intValue();
		}
	
		outputArray = quickSort(outputArray, 0, outputArray.length-1);
		double[] outputArrayDouble = new double[outputArray.length];
		for(int i=0; i<outputArray.length; i++){
			outputArrayDouble[i] = outputArray[i];
		}
		return outputArrayDouble;
	}
	
	/**
	 * Gets the n array rms.
	 *
	 * @return the n array rms
	 */
	public double[] getNArrayRMS(){
		Vector<Integer> NVector = new Vector<Integer>();
		for(int i=0; i<pointVector.size(); i++){
			Integer tempDouble = new Integer((int)((Point)(pointVector.elementAt(i))).getY());
			if(!NVector.contains(tempDouble)){
				NVector.addElement(tempDouble);
			}
		}
		NVector.trimToSize();
		
		int[] outputArray = new int[NVector.size()];
		for(int i=0; i<NVector.size(); i++){
			outputArray[i] = (NVector.elementAt(i)).intValue();
		}
		outputArray = quickSort(outputArray, 0, outputArray.length-1);
	
		double[] outputArrayDouble = new double[outputArray.length];
		for(int i=0; i<outputArray.length; i++){
			outputArrayDouble[i] = outputArray[i];
		}
		return outputArrayDouble;
	}
	
	/**
	 * Gets the a array rms.
	 *
	 * @return the a array rms
	 */
	public double[] getAArrayRMS(){
		Vector<Integer> AVector = new Vector<Integer>();
		for(int i=0; i<pointVector.size(); i++){
			Integer tempDouble = new Integer((int)((Point)(pointVector.elementAt(i))).getX()+ (int)((Point)(pointVector.elementAt(i))).getY());
			if(!AVector.contains(tempDouble)){
				AVector.addElement(tempDouble);
			}
		}
		AVector.trimToSize();
		
		int[] outputArray = new int[AVector.size()];
		for(int i=0; i<AVector.size(); i++){
			outputArray[i] = (AVector.elementAt(i)).intValue();
		}
		outputArray = quickSort(outputArray, 0, outputArray.length-1);
		double[] outputArrayDouble = new double[outputArray.length];
		for(int i=0; i<outputArray.length; i++){
			outputArrayDouble[i] = outputArray[i];
		}
		return outputArrayDouble;
	}
	
	/**
	 * Gets the rMSZ array.
	 *
	 * @return the rMSZ array
	 */
	double[] getRMSZArray(){
		Vector<Double> RMSVector = new Vector<Double>();
		for(int i=0; i<ZArrayRMS.length; i++){
			int currentZ = (int)ZArrayRMS[i];
			double sumSquared = 0.0;
			int numberSum = 0;
			for(int j=0; j<pointVector.size(); j++){
				if(((Point)(pointVector.elementAt(j))).getX()==currentZ){
					sumSquared += Math.pow(diffArray[j], 2);
				 	numberSum++;
				}
			}
			double RMSValue = Math.sqrt(sumSquared/numberSum);
			RMSVector.addElement(new Double(RMSValue));
		}
		RMSVector.trimToSize();
	
		double[] outputArray = new double[RMSVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = (RMSVector.elementAt(i)).doubleValue();
		}
		return outputArray;
	}
	
	/**
	 * Gets the rMSN array.
	 *
	 * @return the rMSN array
	 */
	double[] getRMSNArray(){
		Vector<Double> RMSVector = new Vector<Double>();
		for(int i=0; i<NArrayRMS.length; i++){
			int currentN = (int)NArrayRMS[i];
			double sumSquared = 0.0;
			int numberSum = 0;
			for(int j=0; j<pointVector.size(); j++){
				if(((Point)(pointVector.elementAt(j))).getY()==currentN){
					sumSquared += Math.pow(diffArray[j], 2);
				 	numberSum++;
				}
			}
			double RMSValue = Math.sqrt(sumSquared/numberSum);
			RMSVector.addElement(new Double(RMSValue));
		}
		RMSVector.trimToSize();
	
		double[] outputArray = new double[RMSVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = (RMSVector.elementAt(i)).doubleValue();
		}
		return outputArray;
	}
	
	/**
	 * Gets the rMSA array.
	 *
	 * @return the rMSA array
	 */
	double[] getRMSAArray(){
		Vector<Double> RMSVector = new Vector<Double>();
		for(int i=0; i<AArrayRMS.length; i++){
			int currentA = (int)AArrayRMS[i];
			double sumSquared = 0.0;
			int numberSum = 0;
			for(int j=0; j<pointVector.size(); j++){
				int Z = (int)((Point)(pointVector.elementAt(j))).getX();
				int N = (int)((Point)(pointVector.elementAt(j))).getY();
				if((Z+N)==currentA){
					sumSquared += Math.pow(diffArray[j], 2);
				 	numberSum++;
				}	
			}
			double RMSValue = Math.sqrt(sumSquared/numberSum);
			RMSVector.addElement(new Double(RMSValue));
		}
		RMSVector.trimToSize();

		double[] outputArray = new double[RMSVector.size()];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = (RMSVector.elementAt(i)).doubleValue();
		}
		return outputArray;
	}
	
	/**
	 * Gets the zmin.
	 *
	 * @return the zmin
	 */
	public int getZmin(){
		int Zmin = 1000;
		for(int i=0; i<ZArray.length; i++){
			Zmin = (int)Math.min(Zmin, ZArray[i]);
		}
		return Zmin;
	}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	public int getZmax(){
		int Zmax = 0;
		for(int i=0; i<ZArray.length; i++){
			Zmax = (int)Math.max(Zmax, ZArray[i]);
		}
		return Zmax;
	}
	
	/**
	 * Gets the nmin.
	 *
	 * @return the nmin
	 */
	public int getNmin(){
		int Nmin = 1000;
		for(int i=0; i<NArray.length; i++){
			Nmin = (int)Math.min(Nmin, NArray[i]);
		}
		return Nmin;
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	public int getNmax(){
		int Nmax = 0;
		for(int i=0; i<NArray.length; i++){
			Nmax = (int)Math.max(Nmax, NArray[i]);
		}
		return Nmax;
	}
	
	/**
	 * Gets the amin.
	 *
	 * @return the amin
	 */
	public int getAmin(){
		int Amin = 1000;
		for(int i=0; i<AArray.length; i++){
			Amin = (int)Math.min(Amin, AArray[i]);
		}
		return Amin;
	}
			
	/**
	 * Gets the amax.
	 *
	 * @return the amax
	 */
	public int getAmax(){
		int Amax = 0;
		for(int i=0; i<AArray.length; i++){
			Amax = (int)Math.max(Amax, AArray[i]);
		}
		return Amax;
	}
	
	/**
	 * Gets the diffmin.
	 *
	 * @return the diffmin
	 */
	public double getDiffmin(){
		double Diffmin = 1.0E100;
		for(int i=0; i<diffArray.length; i++){
			Diffmin = Math.min(Diffmin, diffArray[i]);
		}
		Diffmin = Math.round(Diffmin);
		return Diffmin;
	}
	
	/**
	 * Gets the diffmax.
	 *
	 * @return the diffmax
	 */
	public double getDiffmax(){
		double Diffmax = -1.0E100;
		for(int i=0; i<diffArray.length; i++){
			Diffmax = Math.max(Diffmax, diffArray[i]);
		}
		Diffmax = Math.round(Diffmax);
		return Diffmax;
	}

	/**
	 * Gets the abs diffmin.
	 *
	 * @return the abs diffmin
	 */
	public double getAbsDiffmin(){
		double Diffmin = 1.0E100;
		for(int i=0; i<absDiffArray.length; i++){
			Diffmin = Math.min(Diffmin, absDiffArray[i]);
		}
		Diffmin = Math.round(Diffmin);
		return Diffmin;
	}
	
	/**
	 * Gets the abs diffmax.
	 *
	 * @return the abs diffmax
	 */
	public double getAbsDiffmax(){
		double Diffmax = -1.0E100;
		for(int i=0; i<absDiffArray.length; i++){
			Diffmax = Math.max(Diffmax, absDiffArray[i]);
		}
		Diffmax = Math.round(Diffmax);
		return Diffmax;
	}

	/**
	 * Gets the zmin rms.
	 *
	 * @return the zmin rms
	 */
	public int getZminRMS(){
		int Zmin = 1000;
		for(int i=0; i<ZArrayRMS.length; i++){
			Zmin = (int)Math.min(Zmin, ZArrayRMS[i]);
		}
		return Zmin;
	}
	
	/**
	 * Gets the zmax rms.
	 *
	 * @return the zmax rms
	 */
	public int getZmaxRMS(){
		int Zmax = 0;
		for(int i=0; i<ZArrayRMS.length; i++){
			Zmax = (int)Math.max(Zmax, ZArrayRMS[i]);
		}
		return Zmax;
	}
	
	/**
	 * Gets the nmin rms.
	 *
	 * @return the nmin rms
	 */
	public int getNminRMS(){
		int Nmin = 1000;
		for(int i=0; i<NArrayRMS.length; i++){
			Nmin = (int)Math.min(Nmin, NArrayRMS[i]);
		}
		return Nmin;
	}
	
	/**
	 * Gets the nmax rms.
	 *
	 * @return the nmax rms
	 */
	public int getNmaxRMS(){
		int Nmax = 0;
		for(int i=0; i<NArrayRMS.length; i++){
			Nmax = (int)Math.max(Nmax, NArrayRMS[i]);
		}
		return Nmax;
	}
	
	/**
	 * Gets the amin rms.
	 *
	 * @return the amin rms
	 */
	public int getAminRMS(){
		int Amin = 1000;
		for(int i=0; i<AArrayRMS.length; i++){
			Amin = (int)Math.min(Amin, AArrayRMS[i]);
		}
		return Amin;
	}
			
	/**
	 * Gets the amax rms.
	 *
	 * @return the amax rms
	 */
	public int getAmaxRMS(){
		int Amax = 0;
		for(int i=0; i<AArrayRMS.length; i++){
			Amax = (int)Math.max(Amax, AArrayRMS[i]);
		}
		return Amax;
	}

	/**
	 * Gets the rMS zmin.
	 *
	 * @return the rMS zmin
	 */
	public double getRMSZmin(){
		double RMSZmin = 1.0E100;
		for(int i=0; i<RMSZArray.length; i++){
			RMSZmin = Math.min(RMSZmin, RMSZArray[i]);
		}
		RMSZmin = Math.round(RMSZmin);
		return RMSZmin;
	}
	
	/**
	 * Gets the rMS zmax.
	 *
	 * @return the rMS zmax
	 */
	public double getRMSZmax(){
		double RMSZmax = 0.0;
		for(int i=0; i<RMSZArray.length; i++){
			RMSZmax = Math.max(RMSZmax, RMSZArray[i]);
		}
		RMSZmax = Math.round(RMSZmax);
		return RMSZmax;
	}
	
	/**
	 * Gets the rMS nmin.
	 *
	 * @return the rMS nmin
	 */
	public double getRMSNmin(){
		double RMSNmin = 1.0E100;
		for(int i=0; i<RMSNArray.length; i++){
			RMSNmin = Math.min(RMSNmin, RMSNArray[i]);
		}
		RMSNmin = Math.round(RMSNmin);
		return RMSNmin;
	}
	
	/**
	 * Gets the rMS nmax.
	 *
	 * @return the rMS nmax
	 */
	public double getRMSNmax(){
		double RMSNmax = 0.0;
		for(int i=0; i<RMSNArray.length; i++){
			RMSNmax = Math.max(RMSNmax, RMSNArray[i]);
		}
		RMSNmax = Math.round(RMSNmax);
		return RMSNmax;
	}
	
	/**
	 * Gets the rMS amin.
	 *
	 * @return the rMS amin
	 */
	public double getRMSAmin(){
		double RMSAmin = 1.0E100;
		for(int i=0; i<RMSAArray.length; i++){
			RMSAmin = Math.min(RMSAmin, RMSAArray[i]);
		}
		RMSAmin = Math.round(RMSAmin);
		return RMSAmin;
	}
	
	/**
	 * Gets the rMS amax.
	 *
	 * @return the rMS amax
	 */
	public double getRMSAmax(){
		double RMSAmax = 0.0;
		for(int i=0; i<RMSAArray.length; i++){
			RMSAmax = Math.max(RMSAmax, RMSAArray[i]);
		}
		RMSAmax = Math.round(RMSAmax);
		return RMSAmax;
	}
	
	/**
	 * Quick sort.
	 *
	 * @param numbers the numbers
	 * @param left the left
	 * @param right the right
	 * @return the int[]
	 */
	public int[] quickSort(int[] numbers, int left, int right){
		int l_hold = left;
		int r_hold = right;
		int pivot = numbers[left];
		while(left<right){
			while ((numbers[right]>=pivot)&&(left<right)){
				right--;
			}
			if(left!=right){
				numbers[left] = numbers[right];
				left++;
			}
			while((numbers[left]<=pivot)&&(left<right)){
				left++;
			}
			if(left!=right){
				numbers[right] = numbers[left];
				right--;
			}
		}

		numbers[left] = pivot;
		pivot = left;
		left = l_hold;
		right = r_hold;

		if(left<pivot){
			quickSort(numbers, left, pivot-1);
		}

		if(right>pivot){
			quickSort(numbers, pivot+1, right);
		}
		return numbers;
	}
}

class VizPlotPrintable implements Printable{
	private VizPlotPanel panel;
	public VizPlotPrintable(VizPlotPanel panel){
		this.panel = panel;
	}
	public int print(Graphics g, PageFormat pf, int pageIndex){
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		panel.paintMe(g);
        return PAGE_EXISTS;
	}
}

class VizPlotPanel extends JPanel implements MouseMotionListener, MouseListener{

	StaticPlotter staticPlotter;
	
	// Define some standard colors for convenience
    Color AIyellow=new Color (255,204,0);
    Color AIorange=new Color(255,153,0);
    Color AIred=new Color(204,51,0);
    Color AIpurple=new Color(153,102,153);
    Color AIblue=new Color(102,153,153);
    Color AIgreen=new Color(153,204,153);
    Color gray51=new Color(51,51,51);
    Color gray102=new Color(102,102,102);
    Color gray153=new Color(153,153,153);
    Color gray204=new Color(204,204,204);
    Color gray245=new Color(245,245,245);
    Color gray250=new Color(252,252,252);
	
	//linear mode
	int plotmode = 2;
	
	//upper left corner of plot
	int x1 = 4;
	int y1 = 4;
	
	//lower right corner of plot
	int x2 = 700;
	int y2 = 504;
	
	//max number of points per curve
	int kmax = 2000;
	
	//max number of curves
	int imax = 40;
	
	//int indicating solid line plot
	int[] mode = {1};
	
	//dotsize (not used for solid line plot but required parameter)
	int dotSize = 3;
	
	//offset for legend
	int xlegoff = 80;
	int ylegoff = 40;
	
	//number of decimal places for numbers on x and y axis
	int xdplace = 0;
	int ydplace = 4;
	
	//number of data points for each curve
	int[] npoints = {1};
	
	//set to NO autoscale to max and min of x and y sets
	int doscalex = 0;
	int doscaley = 0;
	
	//say yes to plot the curve
	int[] doplot = {1};
	
	//Min and max of x and y on plot
	//overridden if autoscaling
	double xmin = 0;
	double xmax = 0;
		
	double ymin = 0;
	double ymax = 0;
	
	//set empty space around plot as fraction of total height
	//and width of plot
	double delxmin = 0.0;
	double delymin = 0.0;
	double delxmax = 0.0;
	double delymax = 0.0;
	
	//Set colors for lines or curves
	Color[] lcolor = new Color[1];
	
	Color bgcolor=Color.white;        // plot background color
    Color axiscolor=gray51;           // axis color
    Color legendfg=gray250;           // legend box color
    Color framefg=Color.white;        // frame color
    Color dropShadow = gray153;       // legend box dropshadow color
    Color legendbg=gray204;           // legend box frame color
    Color labelcolor = gray51;        // axis label color
    Color ticLabelColor = gray51;     // axis tic label color
	
	//title of x axis
	String xtitle = "Temperature (T9)";
	
	//title of y axis
	String ytitle = "Rate";
	
	//set curve title for legend
	String[] curveTitle = null;
	
	//set style of log plot (show number or log of number on each axis)
	int logStyle = 1;
	
	//number of intervals between x and y tick marks
	int ytickIntervals = 0;
	int xtickIntervals = 0;
	
	//do NOT show the legend
	boolean showLegend = true;
	
	//double arrays to hold x and y points 
	//first entry for each curve and next entry for number of points
	double[][] x = new double[imax][kmax];
	double[][] y = new double[imax][kmax];
	
	//show major minor tick marks
	//for X and Y
	//must change to current variables
	//here and in AstroPilotProjectStaticPlotter
	boolean majorX = true;
    boolean minorX = false;
    boolean majorY = true;
    boolean minorY = false;
    
    //Show title and subtitle
    boolean title = true;
    boolean subtitle = false;
    
    //Title and subtitle names
    String titleString = "";
    String subtitleString = "";
    
    //Is the legend inside the graph?
    boolean insideLegend = true;
    
    //Initialize legend position
    String location = "NW";
    
    int xoffset=65;         // pixels to left of y axis
    int yoffset=40;         // pixels below x axis
    int topmarg=30;         // pixels above graph
    int rightmarg=20;       // pixels to right of graph
	
	boolean initFlag = false;
	
	int minSpinnerInit;
    int maxSpinnerInit;

	int mouseX = 0;
	int mouseY = 0;
	boolean showWindow = false;
	boolean mouseDragging = false;
	Rectangle square = new Rectangle();
	private VizPlotFrame frame;

	public VizPlotPanel(VizDataStructure ds, VizPlotFrame frame){
		
		this.frame = frame;

		setPlotState();

		staticPlotter = new StaticPlotter();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		square.width = 80;
    	square.height = 80;
    	
    	setBackground(Color.white);
	}

	public void setPlotState(){
		
		String type = "";
		
		if(initFlag){
		
			type = frame.type;
		
		}else{
		
			type = "Diff";
		
		}
		
		if(type.equals("RMS")){
		
			titleString = "RMS Difference of "
						+ frame.mmds.toString()
						+ " and "
						+ frame.mmdsRef.toString();
			
			if(!initFlag){
	
				xtickIntervals = 10;
				ytickIntervals = 10;
				
				ydplace = 3;
				xdplace = 0;
				
				lcolor[0] = Color.black;
				
				showLegend = false;
				
				plotmode = 0;
				
				int size = 1;
				
				imax = size;
				mode = new int[size];					
				doplot = new int[size];					
				curveTitle = new String[size];
				npoints = new int[size];
				
				kmax = frame.ZArrayRMS.length;
				
				x = new double[imax][kmax];
				y = new double[imax][kmax];
				
				npoints[0] = kmax;
				curveTitle[0] = "";
				
				dotSize = 1;
				doplot[0] = 1;
	
				initFlag = true;
				mode[0] = 2;
				xtitle = "Z (proton number)";
				ytitle = "RMS Value (MeV)";
			
				//xmin = frame.ZminRMS();
				xmin = 0.0;
				xmax = frame.ZmaxRMS;
	
				//ymin = frame.RMSZmin();
				ymin = 0.0;
				ymax = frame.RMSZmax;
				
				x[0] = frame.ZArrayRMS;
				y[0] = frame.RMSZArray;
	
			}else{
	
				x = new double[imax][kmax];
				y = new double[imax][kmax];
	
				xtickIntervals = 10;
				ytickIntervals = 10;
				
				ydplace = 3;
				xdplace = 0;
				
				lcolor[0] = Color.black;
				
				showLegend = false;
				
				plotmode = 0;
				
				int size = 1;
				
				imax = size;
				mode = new int[size];					
				doplot = new int[size];					
				curveTitle = new String[size];
				npoints = new int[size];
				
				curveTitle[0] = "";
				
				dotSize = 1;
				doplot[0] = 1;
	
				minorX = frame.getMinorX();
				majorX = frame.getMajorX();			
				minorY = frame.getMinorY();
				majorY = frame.getMajorY();
				
				xmin = frame.getXmin();
				xmax = frame.getXmax();
				
				ymin = frame.getYmin();
				ymax = frame.getYmax();
				
				if(frame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
					
					xtitle = "Z (proton number)";
					x[0] = frame.ZArrayRMS;
					y[0] = frame.RMSZArray;
					kmax = frame.ZArrayRMS.length;
				
				}else if(frame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
				
					xtitle = "N (neutron number)";
					x[0] = frame.NArrayRMS;
					y[0] = frame.RMSNArray;
					kmax = frame.NArrayRMS.length;
				
				}else if(frame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
				
					xtitle = "A = Z + N";
					x[0] = frame.AArrayRMS;
					y[0] = frame.RMSAArray;
					kmax = frame.AArrayRMS.length;
				
				}
				
				ytitle = "RMS Value (MeV)";
				mode[0] = 2;
				
				npoints[0] = kmax;
	
			}
			
		}else if(type.equals("Diff")){
		
			titleString = "Mass Differences of "
				+ frame.mmds.toString()
				+ " and "
				+ frame.mmdsRef.toString();

			xtickIntervals = 10;
			ytickIntervals = 10;
			
			ydplace = 3;
			xdplace = 0;
			
			lcolor[0] = Color.black;
			
			showLegend = false;
			
			plotmode = 0;
			
			int size = 1;
			
			imax = size;
			mode = new int[size];					
			doplot = new int[size];					
			curveTitle = new String[size];
			npoints = new int[size];
			
			kmax = frame.ZArray.length;
			
			x = new double[imax][kmax];
			y = new double[imax][kmax];
			
			npoints[0] = kmax;
			curveTitle[0] = "";
			
			dotSize = 1;
			doplot[0] = 1;
			
			if(!initFlag){
	
				initFlag = true;
				mode[0] = 5;
				xtitle = "Z (proton number)";
				ytitle = "Mass Difference (MeV)";
			
				xmin = frame.Zmin;
				xmax = frame.Zmax;
	
				ymin = frame.diffmin;
				ymax = frame.diffmax;
				
				x[0] = frame.ZArray;
				y[0] = frame.diffArray;
	
			}else{
	
				minorX = frame.getMinorX();
				majorX = frame.getMajorX();			
				minorY = frame.getMinorY();
				majorY = frame.getMajorY();
				
				xmin = frame.getXmin();
				xmax = frame.getXmax();
				
				ymin = frame.getYmin();
				ymax = frame.getYmax();
				
				if(frame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
					
					xtitle = "Z (proton number)";
					x[0] = frame.ZArray;
	
				}else if(frame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
				
					xtitle = "N (neutron number)";
					x[0] = frame.NArray;
					
				}else if(frame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
				
					xtitle = "A = Z + N";
					x[0] = frame.AArray;
					
				}
	
				ytitle = "Mass Difference (MeV)";
				mode[0] = 5;
				y[0] = frame.diffArray;
	
			}
		
		}
		
		repaint();
			
	}
	
	public void mouseEntered(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		showWindow = true;
		repaint();
		
	}
	
	public void mouseExited(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		showWindow = false;
		repaint();
		
	}
	
	public void mousePressed(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
	
		mouseDragging = true;	
		
		repaint();
	
	}
	public void mouseClicked(MouseEvent me){}
	
	public void mouseReleased(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
	
		mouseDragging = false;	
		
		repaint();
		
	}
	
	
	public void mouseMoved(MouseEvent me){
	
		mouseX = me.getX();
		mouseY = me.getY();
		
		repaint();
	
	}
	
	public void mouseDragged(MouseEvent me){
		
		mouseX = me.getX();
		mouseY = me.getY();
		
		mouseDragging = true;
		
		repaint();
	
	}	
	
    public void paintComponent(Graphics g){
    	Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
        staticPlotter.plotIt(plotmode,x1,y1,x2,y2,
                  kmax,imax,mode,
                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
                  npoints,doscalex,doscaley,doplot,xmin,xmax,ymin,ymax,
                  delxmin,delxmax,delymin,delymax,
                  lcolor,bgcolor,axiscolor,legendfg,framefg,
                  dropShadow,legendbg,labelcolor,ticLabelColor,
                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
                  xtickIntervals,showLegend,x,y,majorX, minorX, 
                  majorY, minorY, title, subtitle, 
                  titleString, subtitleString, 
                  insideLegend, location, 
                  xoffset, yoffset, topmarg, rightmarg, g2);  
                  
        if(showWindow && mouseDragging){

    		square.x = mouseX - 40;
    		square.y = mouseY - 40;
 
    		g2.clip(square);
    	
    		g2.scale(2, 2);

			int shiftX = ((1*mouseX - x1)/2);
			int shiftY = ((1*mouseY - y1)/2);

			int newX1 = x1 - shiftX;
			int newY1 = y1 - shiftY;
			int newX2 = x2 - shiftX;
			int newY2 = y2 - shiftY;  
			
			staticPlotter.plotIt(plotmode,newX1,newY1,newX2,newY2,
                  kmax,imax,mode,
                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
                  npoints,doscalex,doscaley,doplot,xmin,xmax,ymin,ymax,
                  delxmin,delxmax,delymin,delymax,
                  lcolor,bgcolor,axiscolor,legendfg,framefg,
                  dropShadow,legendbg,labelcolor,ticLabelColor,
                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
                  xtickIntervals,showLegend,x,y,majorX, minorX, 
                  majorY, minorY, title, subtitle, 
                  titleString, subtitleString, 
                  insideLegend, location, 
                  xoffset, yoffset, topmarg, rightmarg, g2);
                  
    	}
                  
                  
    }
    
    public void paintMe(Graphics g){

		staticPlotter.plotIt(plotmode,x1,y1,x2,y2,
                  kmax,imax,mode,
                  dotSize,xlegoff,ylegoff,xdplace,ydplace,
                  npoints,doscalex,doscaley,doplot,xmin,xmax,ymin,ymax,
                  delxmin,delxmax,delymin,delymax,
                  lcolor,bgcolor,axiscolor,legendfg,framefg,
                  dropShadow,legendbg,labelcolor,ticLabelColor,
                  xtitle,ytitle,curveTitle,logStyle,ytickIntervals,
                  xtickIntervals,showLegend,x,y,majorX, minorX, 
                  majorY, minorY, title, subtitle, 
                  titleString, subtitleString, 
                  insideLegend, location, 
                  xoffset, yoffset, 20, rightmarg, g); 
       	             
	}
  
}  

class VizTableFrame extends JFrame implements ActionListener{
  
    private JTextArea tableTextArea;
    private JButton saveButton, copyButton;
    private VizDataStructure ds;
    private MainDataStructure mds;
    private VizPlotFrame frame;
    
    public VizTableFrame(VizDataStructure ds, MainDataStructure mds, VizPlotFrame frame){
    	
    	this.ds = ds;
    	this.frame = frame;
    	this.mds = mds;
    	
    	Container c = getContentPane();
    	
        setSize(487, 400);
        setVisible(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
      	//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
      
      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		 
      	//Create textArea/////////////////////////////////////////////TEXTAREA///////////////////
      	tableTextArea = new JTextArea("", 100, 50);
		tableTextArea.setFont(Fonts.textFontFixedWidth);
		tableTextArea.setEditable(false);
		
      	tableTextArea.setCaretPosition(0);
      	
      	JScrollPane sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
      	
      	gbc.gridx = 0;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 5);
      	buttonPanel.add(saveButton, gbc);
      	gbc.gridx = 1;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 0);
		buttonPanel.add(copyButton, gbc);
      	gbc.insets = new Insets(0, 0, 0, 0);
	
		c.add(sp, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
        
        
        validate();

    }
    
    protected void setTableText(MassModelDataStructure mmds){
   
   		if(frame.type.equals("Diff")){
   
   			setTitle("Mass Difference vs. Isotope Table of Points");
							
	   		tableTextArea.setText("");
			tableTextArea.append(getTitle()
					+ " of \n" 
					+ mmds.toString()
					+ " and "
					+ ds.getModelMapSelected().get(ds.getRefIndex()).toString()
					+ "\n\n");   
					
	
			tableTextArea.append("Mass Diff (MeV)    Z     N     A\n\n");
	
			for(int i=0; i<frame.ZArray.length; i++){
				tableTextArea.append(NumberFormats.getFormattedParameter(frame.diffArray[i]));
				tableTextArea.append("      ");
				tableTextArea.append(NumberFormats.getFormattedMass(frame.ZArray[i]));
				tableTextArea.append("     ");
				tableTextArea.append(NumberFormats.getFormattedMass(frame.NArray[i]));
				tableTextArea.append("     ");
				tableTextArea.append(NumberFormats.getFormattedMass(frame.AArray[i]) + "\n");				
			}
   
   		}else if(frame.type.equals("RMS")){
			setTitle("RMS Value vs. Isotope Table of Points");
	   		tableTextArea.setText("");
	   		tableTextArea.append(getTitle()
					+ " of \n" 
					+ mmds.toString()
					+ " and "
					+ ds.getModelMapSelected().get(ds.getRefIndex()).toString()
					+ "\n\n");  
					
			if(frame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
				tableTextArea.append("RMS Value (MeV)    Z\n\n");
				for(int i=0; i<frame.ZArrayRMS.length; i++){
					tableTextArea.append(NumberFormats.getFormattedParameter(frame.RMSZArray[i]));
					tableTextArea.append("      ");
					tableTextArea.append(NumberFormats.getFormattedMass(frame.ZArrayRMS[i]) + "\n");		
				}		
			}else if(frame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
				tableTextArea.append("RMS Value (MeV)    N\n\n");
				for(int i=0; i<frame.NArrayRMS.length; i++){
					tableTextArea.append(NumberFormats.getFormattedParameter(frame.RMSNArray[i]));
					tableTextArea.append("      ");
					tableTextArea.append(NumberFormats.getFormattedMass(frame.NArrayRMS[i]) + "\n");		
				}		
			}else if(frame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
				tableTextArea.append("RMS Value (MeV)    A\n\n");
				for(int i=0; i<frame.AArrayRMS.length; i++){
					tableTextArea.append(NumberFormats.getFormattedParameter(frame.RMSAArray[i]));
					tableTextArea.append("      ");
					tableTextArea.append(NumberFormats.getFormattedMass(frame.AArrayRMS[i]) + "\n");		
				}			
			}
		}						
    	tableTextArea.setCaretPosition(0);
    }
    
    public void actionPerformed(ActionEvent ae){
    	if(ae.getSource()==saveButton){
			TextSaver.saveText(tableTextArea.getText(), this, mds);
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(tableTextArea.getText());
    	}
    }
}


