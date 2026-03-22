package org.nuclearmasses.gui.viz.s2n;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.print.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.util.StaticPlotter;
import org.nuclearmasses.gui.dialogs.DelayDialog;
import org.nuclearmasses.gui.export.print.*;
import org.nuclearmasses.gui.export.save.*;

/**
 * This class is the <i>S_2n Mass Dataset Plotting Interface</i>.
 */
public class VizS2nPlotFrame extends JFrame implements ActionListener, ItemListener, ChangeListener{

	/** The gbc. */
	private GridBagConstraints gbc;
	
	/** The plot panel. */
	private VizS2nPlotPanel plotPanel;
	
	/** The table frame. */
	private VizS2nTableFrame tableFrame;
	
	/** The table button. */
	private JButton printButton, saveButton, applyButton, tableButton;
	
	/** The Ymax spinner. */
	private JSpinner XmaxSpinner, XminSpinner, YminSpinner, YmaxSpinner;
	
	/** The Ymax model. */
	private SpinnerNumberModel XminModel, XmaxModel, YminModel, YmaxModel;
	
	/** The minor y box. */
	private JCheckBox majorXBox, minorXBox, majorYBox, minorYBox, showPointsBox;
	
	/** The model label. */
	private JLabel xmaxLabel, xminLabel, ymaxLabel, yminLabel, modelLabel;
	
	/** The X combo box. */
	public JComboBox XComboBox;
	
	/** The model combo box. */
	public SizedComboBox modelComboBox;
	
	/** The control panel. */
	private JPanel buttonPanel, controlPanel;
	
	/** The c. */
	private Container c;
	
	/** The sp. */
	private JScrollPane sp;
	
	/** The ds. */
	private VizDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The mmds. */
	public MassModelDataStructure mmds;
	
	/** The delay dialog. */
	private DelayDialog delayDialog;
	
	/** The s2n map for n. */
	public TreeMap<Integer, TreeMap<Integer, Double>> s2nMapN; 
	
	/** The s2n map for z. */
	public TreeMap<Integer, TreeMap<Integer, Double>> s2nMapZ; 
	
	/** The Zmin. */
	int Zmin;
	
	/** The Zmax. */
	int Zmax;
	
	/** The Nmin. */
	int Nmin;
	
	/** The Nmax. */
	int Nmax;
	
	/** The S2nminN. */
	double S2nminN;
	
	/** The S2nmaxN. */
	double S2nmaxN;
	
	/** The S2nminZ. */
	double S2nminZ;
	
	/** The S2nmaxZ. */
	double S2nmaxZ;

	/**
	 * Class constructor.
	 *
	 * @param ds the reference to the {@link	VizDataStructure}
	 * @param mds the reference to the {@link	MainDataStructure}
	 */
	public VizS2nPlotFrame(VizDataStructure ds, MainDataStructure mds){
		this.mds = mds;
		this.ds = ds;
		c = getContentPane();
		setSize(950, 725);
		c.setLayout(new BorderLayout());
		gbc = new GridBagConstraints();
		createFormatPanel();
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(this, string, "Please Wait...");
	}
	
	/**
	 * Closes all windows spawned from this class.
	 */
	public void closeAllFrames(){
		if(tableFrame!=null){
			tableFrame.setVisible(false);
			tableFrame.dispose();
		}
	}

	/**
	 * Initializes this class.
	 */
	public void initialize(){
		setModelComboBox();
		parseModelDataStructures();
		setFormatPanelState(true, true);
		createResultsPlot();
		redrawPlot();
		validate();
	}
	
	/**
	 * Sets the model combo box.
	 */
	private void setModelComboBox(){
		modelComboBox.removeActionListener(this);
		modelComboBox.removeAllItems();
		Iterator<MassModelDataStructure> itr = ds.getModelMapSelected().values().iterator();
		while(itr.hasNext()){
			MassModelDataStructure model = itr.next();
			modelComboBox.addItem(model);
		}
		modelComboBox.setSelectedIndex(0);
		modelComboBox.addActionListener(this);
	}
	
	/**
	 * Sets the state of all current GUI components.
	 * 
	 * @param	resetXValues	if true, then X min and max values will be reset to default values
	 * @param	resetYValues	if true, then Y min and max values will be reset to default values
	 */
	public void setFormatPanelState(boolean resetXValues, boolean resetYValues){
		
		setTitle("Mass Dataset Plotting Interface : S_2n of "
					+ mmds.toString());
        
		XminSpinner.removeChangeListener(this);
		XmaxSpinner.removeChangeListener(this);
		YminSpinner.removeChangeListener(this);
		YmaxSpinner.removeChangeListener(this);
		
		if(XComboBox.getSelectedItem().toString().equals("Z (proton number)")){

			XminModel.setMinimum(new Integer(0));
	        XminModel.setMaximum(new Integer(Zmax-1));
	       	XmaxModel.setMinimum(new Integer(Zmin+1));
        	xminLabel.setText("Z Min");
	        YminModel.setMaximum(new Double(S2nmaxN-1));
	       	YmaxModel.setMinimum(new Double(S2nminN+1));
	    	yminLabel.setText("S_2n Min");
        
	    	if(resetXValues){
	    		XminModel.setValue(new Integer(Zmin));
	    		XmaxModel.setValue(new Integer(Zmax));
	    	}
	    	
	    	if(resetYValues){
	    		YminModel.setValue(new Double(S2nminN));
	    		YmaxModel.setValue(new Double(S2nmaxN));
	    	}

    	}else if(XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
		
			XminModel.setMinimum(new Integer(0));
	        XminModel.setMaximum(new Integer(Nmax-1));
	       	XmaxModel.setMinimum(new Integer(Nmin+1));
        	xminLabel.setText("N Min");
	        YminModel.setMaximum(new Double(S2nmaxZ-1));
	       	YmaxModel.setMinimum(new Double(S2nminZ+1));
	    	yminLabel.setText("S_2n Min");
        
	    	if(resetXValues){
	    		XminModel.setValue(new Integer(Nmin));
	    		XmaxModel.setValue(new Integer(Nmax));
	    	}
	    	
	    	if(resetYValues){
	    		YminModel.setValue(new Double(S2nminZ));
	    		YmaxModel.setValue(new Double(S2nmaxZ));
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
	private void createResultsPlot(){
		plotPanel = new VizS2nPlotPanel(ds, this);
		plotPanel.setPreferredSize(plotPanel.getSize());
		plotPanel.revalidate();
		sp = new JScrollPane(plotPanel);
		c.add(sp, BorderLayout.CENTER);
		plotPanel.setPreferredSize(plotPanel.getSize());
		plotPanel.revalidate();
	}
	
	/**
	 * Initializes all required constants and arrays for plotting.
	 */
	protected void parseModelDataStructures(){
	
		s2nMapN = new TreeMap<Integer, TreeMap<Integer, Double>>();
		s2nMapZ = new TreeMap<Integer, TreeMap<Integer, Double>>();
		
		mmds = (MassModelDataStructure)modelComboBox.getSelectedItem();

		TreeMap<IsotopePoint, MassPoint> massMap = mmds.getMassMap();
		
		//<N, ArrayList<Z>>
		TreeMap<Integer, ArrayList<Integer>> isotopeMapN = new TreeMap<Integer, ArrayList<Integer>>();
		
		Iterator<IsotopePoint> itr = massMap.keySet().iterator();
		while(itr.hasNext()){
			IsotopePoint ip = itr.next();
			if(!isotopeMapN.containsKey(ip.getN())){
				isotopeMapN.put(ip.getN(), new ArrayList<Integer>());
			}
			isotopeMapN.get(ip.getN()).add(ip.getZ());
		}
		
		Iterator<Integer> itrN = isotopeMapN.keySet().iterator();
		while(itrN.hasNext()){
			int n = itrN.next();
			ArrayList<Integer> zlist = isotopeMapN.get(n);
			for(Integer z: zlist){
				
				//Check to see if massmap has z, n-2 for S2n calculation
				if(massMap.containsKey(new IsotopePoint(z, n-2))){
					
					//Check to see if s2nMapN has an entry for n
					if(!s2nMapN.containsKey(n)){
						s2nMapN.put(n, new TreeMap<Integer, Double>());
					}
					s2nMapN.get(n).put(z, calcS2nValue(massMap.get(new IsotopePoint(z, n)).getValue()
														, massMap.get(new IsotopePoint(z, n-2)).getValue()));
				}
			}
		}
		
		//<Z, ArrayList<N>>
		TreeMap<Integer, ArrayList<Integer>> isotopeMapZ = new TreeMap<Integer, ArrayList<Integer>>();
		
		itr = massMap.keySet().iterator();
		while(itr.hasNext()){
			IsotopePoint ip = itr.next();
			if(!isotopeMapZ.containsKey(ip.getZ())){
				isotopeMapZ.put(ip.getZ(), new ArrayList<Integer>());
			}
			isotopeMapZ.get(ip.getZ()).add(ip.getN());
		}
		
		Iterator<Integer> itrZ = isotopeMapZ.keySet().iterator();
		while(itrZ.hasNext()){
			int z = itrZ.next();
			ArrayList<Integer> nlist = isotopeMapZ.get(z);
			for(Integer n: nlist){
				
				//Check to see if massmap has z, n-2 for S2n calculation
				if(massMap.containsKey(new IsotopePoint(z, n-2)) && massMap.containsKey(new IsotopePoint(z, n))){
					
					//Check to see if s2nMapZ has an entry for z
					if(!s2nMapZ.containsKey(z)){
						s2nMapZ.put(z, new TreeMap<Integer, Double>());
					}
					s2nMapZ.get(z).put(n, calcS2nValue(massMap.get(new IsotopePoint(z, n)).getValue()
														, massMap.get(new IsotopePoint(z, n-2)).getValue()));
				}
			}
		}
		
		Zmin = getZmin();
		Zmax = getZmax();
		
		Nmin = getNmin();
		Nmax = getNmax();

		S2nminN = getS2nminN();
		S2nmaxN = getS2nmaxN();
		
		S2nminZ = getS2nminZ();
		S2nmaxZ = getS2nmaxZ();
		
	}
	
	/**
	 * Calc s2n value.
	 *
	 * @param M_ZN the m_ zn
	 * @param M_ZN2 the m_ z n2
	 * @return the double
	 */
	private double calcS2nValue(double M_ZN, double M_ZN2){
		double M_n = 8.071;
		return M_ZN2 - M_ZN +2*M_n;
	}
	
	/**
	 * Creates the format panel.
	 */
	private void createFormatPanel(){
		buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
		
		gbc.gridx = 0;                      
        gbc.gridy = 0;                      
        gbc.anchor = GridBagConstraints.NORTHWEST; 
        gbc.insets = new Insets(5,5,5,5);   

        XComboBox = new JComboBox();
        XComboBox.addItem("Z (proton number)");
        XComboBox.addItem("N (neutron number)");
        XComboBox.setSelectedItem("Z (proton number)");
        XComboBox.setFont(Fonts.textFont);
        XComboBox.addItemListener(this);

        modelComboBox = new SizedComboBox();
        modelComboBox.setFont(Fonts.textFont);
        modelComboBox.addActionListener(this);
        modelComboBox.setPopupWidthToLongest();
        
        modelLabel = new JLabel("Selected Mass Dataset : ");
		
		//Create Check boxes////////////////////////////////////////////CHECKBOXES////////////
		majorXBox = new JCheckBox("Major Grid", true);
		majorXBox.addItemListener(this);
		majorXBox.setFont(Fonts.textFont);
		
		minorXBox = new JCheckBox("Minor Grid", false);
		minorXBox.addItemListener(this);
		minorXBox.setFont(Fonts.textFont);
		
		majorYBox = new JCheckBox("Major Grid", true);
		majorYBox.addItemListener(this);
		majorYBox.setFont(Fonts.textFont);
		
		minorYBox = new JCheckBox("Minor Grid", false);
		minorYBox.addItemListener(this);
		minorYBox.setFont(Fonts.textFont);
		
		showPointsBox = new JCheckBox("Display Points", false);
		showPointsBox.addItemListener(this);
		showPointsBox.setFont(Fonts.textFont);
		
		controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
		
		XminModel = new SpinnerNumberModel(0, 0, 150, 1);
        XmaxModel = new SpinnerNumberModel(0, 0, 999, 1);
		
        XminSpinner = new JSpinner(XminModel);
        XminSpinner.addChangeListener(this);
        //((JSpinner.DefaultEditor)(XminSpinner.getEditor())).getTextField().setEditable(false);
        
        XmaxSpinner = new JSpinner(XmaxModel);
        XmaxSpinner.addChangeListener(this);
        //((JSpinner.DefaultEditor)(XmaxSpinner.getEditor())).getTextField().setEditable(false);
        
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
		
		//JLabel plotControlsLabel = new JLabel("Plot Controls (Hold down your left mouse button over plot to magnify) :");
		
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
        
        yminLabel = new JLabel("S_2n Min");
        yminLabel.setFont(Fonts.textFont);
    	
    	gbc.insets = new Insets(3, 3, 3, 3);
  	
    	gbc.gridx = 0;
  		gbc.gridy = 0;
  		gbc.gridwidth = 8;
  		gbc.anchor = GridBagConstraints.CENTER;
  		//controlPanel.add(plotControlsLabel, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 1;
  		gbc.gridwidth = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(modelLabel, gbc);
  		
  		gbc.gridx = 4;
  		gbc.gridy = 1;
  		gbc.gridwidth = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(modelComboBox, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 1;
  		gbc.gridwidth = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		controlPanel.add(showPointsBox, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 2;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(new JLabel("Select Z or N : "), gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 2;
  		gbc.gridwidth = 1;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(yminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(YminSpinner, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(ymaxLabel, gbc);
  
  		gbc.gridx = 5;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(YmaxSpinner, gbc);
  
  		gbc.gridx = 6;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorYBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 2;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorYBox, gbc);
  		
  		gbc.gridx = 1;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.CENTER;
  		controlPanel.add(XComboBox, gbc);
  		
  		gbc.gridx = 2;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xminLabel, gbc);
  		
  		gbc.gridx = 3;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(XminSpinner, gbc);
  
  		gbc.gridx = 4;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.WEST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(xmaxLabel, gbc);
  		
  		gbc.gridx = 5;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.HORIZONTAL;
  		controlPanel.add(XmaxSpinner, gbc);
  		
  		gbc.gridx = 6;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		gbc.fill = GridBagConstraints.NONE;
  		controlPanel.add(majorXBox, gbc);
  
  		gbc.gridx = 7;
  		gbc.gridy = 3;
  		gbc.anchor = GridBagConstraints.EAST;
  		controlPanel.add(minorXBox, gbc);
  		
  		gbc.gridx = 0;
  		gbc.gridy = 4;
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
			PlotPrinter.print(new VizS2nPlotPrintable(plotPanel), this);
		}else if(ae.getSource()==saveButton){
			PlotSaver.savePlot(plotPanel, this, mds);
		}else if(ae.getSource()==applyButton){
			redrawPlot();
		}else if(ae.getSource()==tableButton){
			if(tableFrame==null){
				tableFrame = new VizS2nTableFrame(ds, mds, this);
	       	}
	       	tableFrame.setTableText(mmds);
	       	tableFrame.setVisible(true);
		}else if(ae.getSource()==modelComboBox){
			delayDialog.openDelayDialog();
			delayDialog.setLocationRelativeTo(this);
			reloadData();
		}
	}
	
	/**
	 * Reload data.
	 */
	private void reloadData(){
		ReloadS2nDataTask task = new ReloadS2nDataTask(this, delayDialog);
		task.execute();
	}
	
	/**
	 * Forces a plot redraw.
	 */
	public void redrawPlot(){
		plotPanel.setPlotState();
		plotPanel.repaint();
	}
	
	/**
	 * Gets the current X min value.
	 * 
	 * @return		the current X min value
	 */
	public int getXmin(){return XminModel.getNumber().intValue();} 
	
	/**
	 * Gets the current X max value.
	 * 
	 * @return		the current X max value
	 */
	public int getXmax(){return XmaxModel.getNumber().intValue();}
	
	/**
	 * Gets the current Y min value.
	 * 
	 * @return		the current Y min value
	 */
	public double getYmin(){return YminModel.getNumber().doubleValue();}
	
	/**
	 * Gets the current Y max value.
	 * 
	 * @return		the current Y max value
	 */
	public double getYmax(){return YmaxModel.getNumber().doubleValue();}
	
	/**
	 * Determines if the checkbox for the x axis minor gridlines is checked.
	 * 
	 * @return		true if if the checkbox for the x axis minor gridlines is checked
	 */
	public boolean getMinorX(){return minorXBox.isSelected();} 
	
	/**
	 * Determines if the checkbox for the x axis major gridlines is checked.
	 * 
	 * @return		true if if the checkbox for the x axis major gridlines is checked
	 */
	public boolean getMajorX(){return majorXBox.isSelected();} 
	
	/**
	 * Determines if the checkbox for the y axis minor gridlines is checked.
	 * 
	 * @return		true if if the checkbox for the y axis minor gridlines is checked
	 */
	public boolean getMinorY(){return minorYBox.isSelected();} 
	
	/**
	 * Determines if the checkbox for the y axis major gridlines is checked.
	 * 
	 * @return		true if if the checkbox for the y axis major gridlines is checked
	 */
	public boolean getMajorY(){return majorYBox.isSelected();} 
	
	public boolean getShowPoints(){return showPointsBox.isSelected();} 
	
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
        	setFormatPanelState(true, false);
    		validate();
        	redrawPlot();
        }else if(ie.getSource()==showPointsBox){
        	redrawPlot();
        }
    }
	
    /**
     * Gets the zmin.
     *
     * @return the zmin
     */
    private int getZmin(){
		int zmin = 10000;
		Iterator<Integer> itr = s2nMapN.keySet().iterator();
		while(itr.hasNext()){
			zmin = Math.min(s2nMapN.get(itr.next()).firstKey(), zmin);
		}
		return zmin;
	}
	
    /**
     * Gets the zmax.
     *
     * @return the zmax
     */
    private int getZmax(){
		int zmax = 0;
		Iterator<Integer> itr = s2nMapN.keySet().iterator();
		while(itr.hasNext()){
			zmax = Math.max(s2nMapN.get(itr.next()).lastKey(), zmax);
		}
		return zmax;
	}
	
    /**
     * Gets the nmin.
     *
     * @return the nmin
     */
    private int getNmin(){
		int nmin = 10000;
		Iterator<Integer> itr = s2nMapZ.keySet().iterator();
		while(itr.hasNext()){
			nmin = Math.min(s2nMapZ.get(itr.next()).firstKey(), nmin);
		}
		return nmin;
	}
	
    /**
     * Gets the nmax.
     *
     * @return the nmax
     */
    private int getNmax(){
		int nmax = 0;
		Iterator<Integer> itr = s2nMapZ.keySet().iterator();
		while(itr.hasNext()){
			nmax = Math.max(s2nMapZ.get(itr.next()).lastKey(), nmax);
		}
		return nmax;
	}
    
    /**
     * Gets the s2nminN.
     *
     * @return the s2nminN
     */
    private double getS2nminN(){
		double s2nminN = Double.MAX_VALUE;
		Iterator<Integer> itrN = s2nMapN.keySet().iterator();
		while(itrN.hasNext()){
			TreeMap<Integer, Double> zmap = s2nMapN.get(itrN.next());
			Iterator<Integer> itrZ = zmap.keySet().iterator();
			while(itrZ.hasNext()){
				s2nminN = Math.min(s2nminN, zmap.get(itrZ.next()));
			} 
		}
		return Math.floor(s2nminN);
	}
	
    /**
     * Gets the s2nmaxN.
     *
     * @return the s2nmaxN
     */
    private double getS2nmaxN(){
		double s2nmaxN = -Double.MAX_VALUE;
		Iterator<Integer> itrN = s2nMapN.keySet().iterator();
		while(itrN.hasNext()){
			TreeMap<Integer, Double> zmap = s2nMapN.get(itrN.next());
			Iterator<Integer> itrZ = zmap.keySet().iterator();
			while(itrZ.hasNext()){
				s2nmaxN = Math.max(s2nmaxN, zmap.get(itrZ.next()));
			}
		}
		return Math.ceil(s2nmaxN);
	}
    
    /**
     * Gets the s2nminZ.
     *
     * @return the s2nminZ
     */
    private double getS2nminZ(){
		double s2nminZ = Double.MAX_VALUE;
		Iterator<Integer> itrZ = s2nMapZ.keySet().iterator();
		while(itrZ.hasNext()){
			TreeMap<Integer, Double> nmap = s2nMapZ.get(itrZ.next());
			Iterator<Integer> itrN = nmap.keySet().iterator();
			while(itrN.hasNext()){
				s2nminZ = Math.min(s2nminZ, nmap.get(itrN.next()));
			} 
		}
		return Math.floor(s2nminZ);
	}
	
    /**
     * Gets the s2nmaxZ.
     *
     * @return the s2nmaxZ
     */
    private double getS2nmaxZ(){
		double s2nmaxZ = -Double.MAX_VALUE;
		Iterator<Integer> itrZ = s2nMapZ.keySet().iterator();
		while(itrZ.hasNext()){
			TreeMap<Integer, Double> nmap = s2nMapZ.get(itrZ.next());
			Iterator<Integer> itrN = nmap.keySet().iterator();
			while(itrN.hasNext()){
				s2nmaxZ = Math.max(s2nmaxZ, nmap.get(itrN.next()));
			}
		}
		return Math.ceil(s2nmaxZ);
	}

}

class VizS2nPlotPrintable implements Printable{
	private VizS2nPlotPanel panel;
	public VizS2nPlotPrintable(VizS2nPlotPanel panel){
		this.panel = panel;
	}
	public int print(Graphics g, PageFormat pf, int pageIndex){
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		panel.paintMe(g);
        return PAGE_EXISTS;
	}
}

class ReloadS2nDataTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private VizS2nPlotFrame frame;
	private DelayDialog dialog;
	
	public ReloadS2nDataTask(VizS2nPlotFrame frame, DelayDialog dialog){
		this.frame = frame;
		this.dialog = dialog;
	}
	
	protected Void doInBackground(){
		frame.parseModelDataStructures();
		frame.setFormatPanelState(true, true);
		frame.redrawPlot();
		frame.validate();
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		
	}
}

class VizS2nPlotPanel extends JPanel implements MouseMotionListener, MouseListener{

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
	int kmax = 0;
	
	//max number of curves
	int imax = 40;
	
	//int indicating solid line plot
	int[] mode = {1};
	
	//dotsize (not used for solid line plot but required parameter)
	int dotSize = 4;
	
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
	Color[] lcolor = new Color[2];
	
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
	private VizS2nPlotFrame frame;

	private Color[] colors;
	
	public VizS2nPlotPanel(VizDataStructure ds, VizS2nPlotFrame frame){
		this.frame = frame;
		setColorArray();
		setPlotState();
		staticPlotter = new StaticPlotter();
		//addMouseListener(this);
		//addMouseMotionListener(this);
		square.width = 80;
    	square.height = 80;
    	setBackground(Color.white);
	}

	public void setPlotState(){
		
		kmax = 0;
		
		titleString = "S_2n of " + frame.mmds.toString();
		
		if(!initFlag){
			
			xtickIntervals = frame.getXmax()-frame.getXmin();
			ytickIntervals = (int)(Math.ceil(frame.getYmax())-Math.floor(frame.getYmin()));
			ydplace = 0;
			xdplace = 0;
			showLegend = false;
			plotmode = 0;
			
			imax = frame.s2nMapN.keySet().size();
			Iterator<Integer> itr = frame.s2nMapN.keySet().iterator();
			while(itr.hasNext()){
				kmax = Math.max(kmax, frame.s2nMapN.get(itr.next()).size());
			}
			x = new double[imax][kmax];
			y = new double[imax][kmax];
			lcolor = new Color[imax];
			npoints = new int[imax];
			curveTitle = new String[imax];
			mode = new int[imax];
			doplot = new int[imax];	
			xtitle = "Z (proton number)";
			ytitle = "S_2n (MeV)";
			
			//Assign points to plotting arrays 
			int colorCounter = 0;
			Iterator<Integer> itrN = frame.s2nMapN.keySet().iterator();
			int counter = 0;
			while(itrN.hasNext()){
				int n = itrN.next();
				TreeMap<Integer, Double> map = frame.s2nMapN.get(n);
				Iterator<Integer> itrZ = map.keySet().iterator();
				int counter2 = 0;
				while(itrZ.hasNext()){
					int z = itrZ.next();
					x[counter][counter2] = z;
					y[counter][counter2] = map.get(z);
					counter2++;
				}
				doplot[counter] = 1;
				npoints[counter] = counter2;
				if(n==2 || n==8 || n==20 || n==28 || n==50 || n==82 || n==126){
					mode[counter] = 1;
				}else{
					mode[counter] = 3;
				}
				lcolor[counter] = colors[colorCounter];
				if(n==2 || n==8 || n==20 || n==28 || n==50 || n==82 || n==126){
					colorCounter++;
				}
				curveTitle[counter] = "N = " + n;
				counter++;
			}
			
			xmin = frame.Zmin;
			xmax = frame.Zmax;
			ymin = frame.S2nminN;
			ymax = frame.S2nmaxN;
			initFlag = true;
			
		}else{
			
			if(frame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
				
				xtitle = "Z (proton number)";

				xtickIntervals = frame.getXmax()-frame.getXmin();
				ytickIntervals = (int)(Math.ceil(frame.getYmax())-Math.floor(frame.getYmin()));
				ydplace = 0;
				xdplace = 0;
				showLegend = false;
				plotmode = 0;
				
				imax = frame.s2nMapN.keySet().size();
				Iterator<Integer> itr = frame.s2nMapN.keySet().iterator();
				while(itr.hasNext()){
					kmax = Math.max(kmax, frame.s2nMapN.get(itr.next()).size());
				}
				x = new double[imax][kmax];
				y = new double[imax][kmax];
				lcolor = new Color[imax];
				npoints = new int[imax];
				curveTitle = new String[imax];
				mode = new int[imax];
				doplot = new int[imax];	
				xtitle = "Z (proton number)";
				ytitle = "S_2n (MeV)";
				
				//Assign points to plotting arrays 
				int colorCounter = 0;
				Iterator<Integer> itrN = frame.s2nMapN.keySet().iterator();
				int counter = 0;
				while(itrN.hasNext()){
					int n = itrN.next();
					TreeMap<Integer, Double> map = frame.s2nMapN.get(n);
					Iterator<Integer> itrZ = map.keySet().iterator();
					int counter2 = 0;
					while(itrZ.hasNext()){
						int z = itrZ.next();
						x[counter][counter2] = z;
						y[counter][counter2] = map.get(z);
						counter2++;
					}
					doplot[counter] = 1;
					npoints[counter] = counter2;
					if(n==2 || n==8 || n==20 || n==28 || n==50 || n==82 || n==126){
						mode[counter] = 1;
					}else{
						mode[counter] = 3;
					}
					if(frame.getShowPoints()){
						mode[counter] = 2;
					}
					lcolor[counter] = colors[colorCounter];
					if(n==2 || n==8 || n==20 || n==28 || n==50 || n==82 || n==126){
						colorCounter++;
					}
					curveTitle[counter] = "N = " + n;
					counter++;
				}
				
				minorX = frame.getMinorX();
				majorX = frame.getMajorX();			
				minorY = frame.getMinorY();
				majorY = frame.getMajorY();
				xmin = frame.getXmin();
				xmax = frame.getXmax();
				ymin = frame.getYmin();
				ymax = frame.getYmax();
				
			}else if(frame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
			
				xtitle = "N (neutron number)";
				
				xtickIntervals = frame.getXmax()-frame.getXmin();
				ytickIntervals = (int)(Math.ceil(frame.getYmax())-Math.floor(frame.getYmin()));
				ydplace = 0;
				xdplace = 0;
				showLegend = false;
				plotmode = 0;
				
				imax = frame.s2nMapZ.keySet().size();
				Iterator<Integer> itr = frame.s2nMapZ.keySet().iterator();
				while(itr.hasNext()){
					kmax = Math.max(kmax, frame.s2nMapZ.get(itr.next()).size());
				}
				x = new double[imax][kmax];
				y = new double[imax][kmax];
				lcolor = new Color[imax];
				npoints = new int[imax];
				curveTitle = new String[imax];
				mode = new int[imax];
				doplot = new int[imax];	
				xtitle = "N (neutron number)";
				ytitle = "S_2n (MeV)";
				
				//Assign points to plotting arrays 
				int colorCounter = 0;
				Iterator<Integer> itrZ = frame.s2nMapZ.keySet().iterator();
				int counter = 0;
				while(itrZ.hasNext()){
					int z = itrZ.next();
					TreeMap<Integer, Double> map = frame.s2nMapZ.get(z);
					Iterator<Integer> itrN = map.keySet().iterator();
					int counter2 = 0;
					while(itrN.hasNext()){
						int n = itrN.next();
						x[counter][counter2] = n;
						y[counter][counter2] = map.get(n);
						counter2++;
					}
					doplot[counter] = 1;
					npoints[counter] = counter2;
					if(z==2 || z==8 || z==20 || z==28 || z==50 || z==82 || z==126){
						mode[counter] = 1;
					}else{
						mode[counter] = 3;
					}
					if(frame.getShowPoints()){
						mode[counter] = 2;
					}
					lcolor[counter] = colors[colorCounter];
					if(z==2 || z==8 || z==20 || z==28 || z==50 || z==82 || z==126){
						colorCounter++;
					}
					curveTitle[counter] = "Z = " + z;
					counter++;
				}
				
				minorX = frame.getMinorX();
				majorX = frame.getMajorX();			
				minorY = frame.getMinorY();
				majorY = frame.getMajorY();
				xmin = frame.getXmin();
				xmax = frame.getXmax();
				ymin = frame.getYmin();
				ymax = frame.getYmax();
			
			}
			
		}

		repaint();
			
	}
	
	public void setColorArray(){
        Color AIorange=new Color(255,153,0);
        Color AIred=new Color(204,51,0);
        Color AIpurple=new Color(153,102,153);
        Color AIblue=new Color(102,153,153);
        Color AIgreen=new Color(153,204,153);
        Color gray51=new Color(51,51,51);
        Color gray102=new Color(102,102,102);
        Color gray153=new Color(153,153,153);

        colors = new Color[40];
        colors[0] = Color.black;
        colors[1] = Color.blue;
        colors[2] = Color.red;
        colors[3] = Color.magenta;
        colors[4] = gray102;
        colors[5] = new Color(0,220,0);
        colors[6] = AIblue;
        colors[7] = AIpurple;
        colors[8] = AIorange;
        colors[9] = AIgreen;
        colors[10] = new Color(51,153,51);
        colors[11] = new Color(0,51,102);
        colors[12] = new Color(0,153,153);
        colors[13] = new Color(0,51,153);
        colors[14] = new Color(51,153,153);
        colors[15] = new Color(0,153,204);
        colors[16] = new Color(51,0,153);
        colors[17] = new Color(51,204,153);
        colors[18] = new Color(153,153,0);
        colors[19] = new Color(153,102,51);
        colors[20] = new Color(153,51,0);
        colors[21] = gray51;
        colors[22] = AIred;
        colors[23] = gray153;
        colors[24] = new Color(153,153,102);
        colors[25] = new Color(102,51,153);
        colors[26] = new Color(153,51,204);
        colors[27] = new Color(153,153,204);
        colors[28] = new Color(102,204,255);
        colors[29] = new Color(153,51,255);
        colors[30] = new Color(255,102,51);
        colors[31] = new Color(204,51,102);
        colors[32] = new Color(204,153,102);
        colors[33] = new Color(204,204,102);
        colors[34] = new Color(255,153,102);
        colors[35] = new Color(204,51,153);
        colors[36] = new Color(204,153,153);
        colors[37] = new Color(255,51,153);
        colors[38] = new Color(255,153,153);
        colors[39] = new Color(255,204,153);
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


