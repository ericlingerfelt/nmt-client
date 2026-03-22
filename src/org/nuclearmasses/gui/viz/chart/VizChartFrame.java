package org.nuclearmasses.gui.viz.chart;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.gui.dialogs.DelayDialog;
import org.nuclearmasses.gui.export.save.*;

/**
 * This class is the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
 */
public class VizChartFrame extends JFrame implements WindowStateListener, ActionListener, ChangeListener, WindowListener, ItemListener{

    /** The sp3. */
    JScrollPane sp, sp2, sp3;
    
    /** The c. */
    private Container c;
    
    /** The delay dialog. */
    private DelayDialog delayDialog;
    
    /** The exp model label. */
    private JLabel valueLabel, maxLabel, minLabel
    				, zoomLabel, typeLabel, quantityLabel
    				, theoryLabel, expLabel, expModelLabel;
	
	/** The mouse label. */
	WordWrapLabel mouseLabel;
    				
   	/** The zoom field. */
	   JTextField valueField, maxField, minField, zoomField;
   					
   	/** The show r process check box. */
	   JCheckBox showLabelsCheckBox, showMagicCheckBox, showStableCheckBox, showRProcessCheckBox;
   	
   	/** The quantity combo box. */
	   JComboBox typeComboBox, quantityComboBox;
   	
	   /** The model combo box. */
	   SizedComboBox modelComboBox;
   	
	   /** The table button. */
	   JButton saveButton, colorButton, okButton, tableButton;
   	
	   /** The zoom slider. */
	   JSlider zoomSlider;

    /** The viz chart panel. */
    VizChartPanel vizChartPanel;
    
    /** The viz rainbow panel. */
    VizRainbowPanel vizRainbowPanel;
    
    /** The viz isotope panel. */
    VizIsotopePanel vizIsotopePanel;
    
    /** The log constant. */
    double logConstant;
    
    /** The log10. */
    double log10 = 0.434294482;
  	
	  /** The x0 r. */
	  double x0R = 0.8;
    
    /** The x0 g. */
    double x0G = 0.6;
    
    /** The x0 b. */
    double x0B = 0.2;
    
    /** The a r. */
    double aR = 0.5;
    
    /** The a g. */
    double aG = 0.4;
    
    /** The a b. */
    double aB = 0.3; 
    
    /** The N ruler. */
    IsotopeRuler ZRuler, NRuler;
    
    /** The black text radio button. */
    JRadioButton whiteTextRadioButton, blackTextRadioButton;
    
    /** The value array. */
    double[] valueArray;
    
    /** The save dialog. */
    JDialog saveDialog;
    
    /** The type. */
    int type;
    
    /** The quantity. */
    int quantity;
    
    /** The scheme. */
    String scheme = "Continuous";
    
    /** The color settings frame. */
    public VizChartColorSettingsFrame colorSettingsFrame; 
    
    /** The ds. */
    public VizDataStructure ds;
    
    /** The mds. */
    public MainDataStructure mds;
    
    /** The mmds ref. */
    public MassModelDataStructure mmds, mmdsRef;
    
    /** The table frame. */
    public VizChartTableFrame tableFrame;
    
    /** The point vector. */
    Vector pointVector;
    
    /** The abs diff array. */
    double[] absDiffArray;
	
	/** The diff array. */
	double[] diffArray;
	
	/** The bin data. */
	Vector<RowData> binData;
	
	/** The color values. */
	double[] colorValues = {0.8, 0.6, 0.2, 0.5, 0.4, 0.3};
	
	/** The include values. */
	boolean includeValues = true;
	
	/** The chart color array. */
	Color[] chartColorArray;
	
	/** The stable array. */
	Vector stableArray;
	
	/** The r process array. */
	Vector rProcessArray;
    
	/**
	 * Class constructor.
	 *
	 * @param ds the reference to the {@link	VizDataStructure}
	 * @param mds the reference to the {@link	MainDataStructure}
	 */
    public VizChartFrame(VizDataStructure ds, MainDataStructure mds){
    	
    	this.ds = ds;
    	this.mds = mds;
    	
    	c = getContentPane();
    	
        c.setLayout(new BorderLayout());

		setTitle("Interactive Nuclide Chart for Mass Dataset Evaluations");
		
		setSize(850, 700);
		
		binData = new Vector<RowData>();
		binData.addElement(new RowData(1, 2, true, new Color (255,0,0)));
		binData.addElement(new RowData(0, 1, true, new Color(175,0,0)));
		binData.addElement(new RowData(-1, 0, true, new Color(0,0,175)));
		binData.addElement(new RowData(-2, -1, true, new Color(0,0,255)));
		
		//LABELS//////////////////////////////////////////////LABELS//////////////////////);
		
		mouseLabel = new WordWrapLabel();
		mouseLabel.setText("Place the cursor over an isotope to read its value");
		
		expModelLabel = new JLabel();
		
		typeLabel = new JLabel("Select chart type : ");
		
		quantityLabel = new JLabel("Select quantity : ");
		
		theoryLabel = new JLabel("Selected Mass Dataset : ");
		theoryLabel.setFont(Fonts.textFont);
		
		expLabel = new JLabel("Reference Mass Dataset : ");
		expLabel.setFont(Fonts.textFont);
    	
    	valueLabel = new JLabel("Value : ");
    	valueLabel.setFont(Fonts.textFont);
    	
    	maxLabel = new JLabel("Quantity Max : ");
    	maxLabel.setFont(Fonts.textFont);
    	
    	minLabel = new JLabel("Quantity Min : ");
    	minLabel.setFont(Fonts.textFont);
		
		zoomLabel = new JLabel("Zoom (%) : ");
		zoomLabel.setFont(Fonts.textFont);
		
		//COMBOBOX//////////////////////////////////////////////COMBOBOX/////////////////////
		typeComboBox = new JComboBox();
		typeComboBox.addItem("Selected Model Data");
		typeComboBox.addItem("Reference Data");
		typeComboBox.addItem("Difference of Data");
		typeComboBox.addItem("Abs Value of Difference");
		typeComboBox.setFont(Fonts.textFont);
		typeComboBox.setSelectedIndex(2);
		typeComboBox.addActionListener(this);
		
		quantityComboBox = new JComboBox();
		quantityComboBox.addItem("Mass Excess");
		quantityComboBox.addItem("dVpn");
		quantityComboBox.addItem("S_n");
		quantityComboBox.addItem("S_2n");
		quantityComboBox.addItem("S_p");
		quantityComboBox.addItem("S_2p");
		quantityComboBox.addItem("S_alpha");
		quantityComboBox.addItem("Q_(alpha, n)");
		quantityComboBox.addItem("Q_(alpha, p)");
		quantityComboBox.addItem("Q_(p, n)");
		quantityComboBox.setFont(Fonts.textFont);
		quantityComboBox.addActionListener(this);
		
		modelComboBox = new SizedComboBox();
        modelComboBox.setFont(Fonts.textFont);
        modelComboBox.addActionListener(this);
        modelComboBox.setPopupWidthToLongest();
		
		//FIELD/////////////////////////////////////////////////FIELDS//////////////////////
   		valueField = new JTextField(10);
   		valueField.setEditable(false);
   		
   		maxField = new JTextField(10);
   		maxField.setEditable(false);
   		
   		minField = new JTextField(10);
   		minField.setEditable(false);
		
		zoomField = new JTextField(3);
		zoomField.setEditable(false);
		
		//CHECKBOX//////////////////////////////////////////////CHECKBOX///////////////////
		showLabelsCheckBox = new JCheckBox("Isotope Labels", false);
		showLabelsCheckBox.setFont(Fonts.textFont);
		showLabelsCheckBox.addItemListener(this);
		
		showMagicCheckBox = new JCheckBox("Magic Numbers", false);
		showMagicCheckBox.setFont(Fonts.textFont);
		showMagicCheckBox.addItemListener(this);
		
		showStableCheckBox = new JCheckBox("Stable Isotopes", false);
		showStableCheckBox.setFont(Fonts.textFont);
		showStableCheckBox.addItemListener(this);
		
		showRProcessCheckBox = new JCheckBox("r-Process Path", false);
		showRProcessCheckBox.setFont(Fonts.textFont);
		showRProcessCheckBox.addItemListener(this);
		
		//SLIDERS///////////////////////////////////////////////SLIDERS////////////////////
		zoomSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 75);
		zoomSlider.addChangeListener(this);
		
		//BUTTONS//////////////////////////////////////////////BUTTONS///////////////////////
		
		saveButton = new JButton("Save Chart as Image");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);

		colorButton = new JButton("Set Color Scale Settings");
		colorButton.setFont(Fonts.buttonFont);
		colorButton.addActionListener(this);
		
		tableButton = new JButton("Table of Points");
        tableButton.setFont(Fonts.buttonFont);
        tableButton.addActionListener(this);
		
		vizRainbowPanel = new VizRainbowPanel(this);
		sp2 = new JScrollPane(vizRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp2.setPreferredSize(new Dimension(50, 100));
		
		vizIsotopePanel = new VizIsotopePanel();
		sp3 = new JScrollPane(vizIsotopePanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp3.setPreferredSize(new Dimension(200, 30));
			
		//PANELS/////////////////////////////////////////////////////////////////////PANELS//////////////////////////
		JPanel rightPanel = new JPanel();

		double gap = 10;
		double[] column = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		rightPanel.setLayout(new TableLayout(column, row));
		
		//RIGHTPANEL/////////////////////////////////////////////////////////////////RIGHTPANEL//////////////////////
		rightPanel.add(theoryLabel,          "1, 1, 3, 1, r, c"  );
		rightPanel.add(modelComboBox,        "5, 1, 7, 1, f, c"  );
		rightPanel.add(expLabel,             "1, 3, 3, 3, r, c"  );
		rightPanel.add(expModelLabel,        "5, 3, 7, 3, l, c"  );
		rightPanel.add(typeLabel,            "1, 5, 3, 5, r, c"  );
		rightPanel.add(typeComboBox,         "5, 5, 7, 5, f, c"  );
		rightPanel.add(quantityLabel,        "1, 7, 3, 7, r, c"  );
		rightPanel.add(quantityComboBox,     "5, 7, 7, 7, f, c"  );
		rightPanel.add(sp2,                  "1, 9, 1, 11, c, c" );
		rightPanel.add(maxLabel,             "3, 9, r, c"        );
		rightPanel.add(maxField,             "5, 9, 7, 9, f, c"  );
		rightPanel.add(minLabel,             "3, 11, r, c"       );
		rightPanel.add(minField,             "5, 11, 7, 11, f, c");
		rightPanel.add(colorButton,          "1, 13, 7, 13, c, c");
		rightPanel.add(mouseLabel,           "1, 15, 7, 15, c, c");
		rightPanel.add(sp3,                  "1, 17, 7, 17, c, c");
		rightPanel.add(valueLabel,           "1, 19, 3, 19, r, c");
		rightPanel.add(valueField,           "5, 19, 7, 19, f, c");
		rightPanel.add(showRProcessCheckBox, "1, 21, 3, 21");
		rightPanel.add(showLabelsCheckBox,   "5, 21, 7, 21");
		rightPanel.add(showMagicCheckBox,    "1, 23, 3, 23");
		rightPanel.add(showStableCheckBox,   "5, 23, 7, 23");
		rightPanel.add(zoomLabel,            "1, 25, r, c");
		rightPanel.add(zoomField,            "3, 25, f, c");
		rightPanel.add(zoomSlider,           "5, 25, 7, 25, f, c");
		rightPanel.add(saveButton,           "1, 27, 3, 27, f, c");
		rightPanel.add(tableButton,          "5, 27, 7, 27, f, c");
		
		vizChartPanel = new VizChartPanel(this);
		
        sp = new JScrollPane(vizChartPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
		JViewport vp = new JViewport();
		
		vp.setView(vizChartPanel);
		
		sp.setViewport(vp);
	
		ZRuler = new IsotopeRuler("Horizontal");
		NRuler = new IsotopeRuler("Vertical");
	
		ZRuler.setPreferredWidth((int)vizChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)vizChartPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(vizChartPanel.zmax
								, vizChartPanel.nmax
								, vizChartPanel.mouseX
								, vizChartPanel.mouseY
								, vizChartPanel.xoffset
								, vizChartPanel.yoffset
								, vizChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(vizChartPanel.zmax
								, vizChartPanel.nmax
								, vizChartPanel.mouseX
								, vizChartPanel.mouseY
								, vizChartPanel.xoffset
								, vizChartPanel.yoffset
								, vizChartPanel.crosshairsOn);
	
		sp.setColumnHeaderView(ZRuler);
        sp.setRowHeaderView(NRuler);
	
		sp.setCorner(JScrollPane.UPPER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
        sp.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new Corner());
		sp.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new Corner());
		
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		c.add(sp, BorderLayout.CENTER);
		c.add(rightPanel, BorderLayout.EAST);
		downloadStableIsotopes();
		downloadRProcessIsotopes();
        addWindowListener(this);
		addWindowStateListener(this);
		String string = "Please wait while data is loaded.";
		delayDialog = new DelayDialog(this, string, "Please Wait...");
    } 
    
    /**
     * Closes all windows spawned from this class.
     */
    public void closeAllFrames(){
    	if(colorSettingsFrame!=null){
    		colorSettingsFrame.setVisible(false);
    		colorSettingsFrame.dispose();
    	}
    	if(tableFrame!=null){
    		tableFrame.setVisible(false);
    		tableFrame.dispose();
    	}
    }
    
    /**
     * Initializes this class.
     */
    public void initialize(){
    	setModelComboBox(0);
    	parseModelDataStructures();
    	setComboBoxes();
		initialize(2, 0);
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
    }
    
    /**
     * Execute data reload.
     */
    public void executeDataReload(){
    	setModelComboBox(modelComboBox.getSelectedIndex());
    	parseModelDataStructures();
    	setComboBoxes();
    	type = typeComboBox.getSelectedIndex();
		quantity = quantityComboBox.getSelectedIndex();
		initialize(type, quantity);
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
    }
    
    /**
     * Sets the model combo box.
     *
     * @param index the new model combo box
     */
    private void setModelComboBox(int index){
		modelComboBox.removeActionListener(this);
		modelComboBox.removeAllItems();
		Iterator<MassModelDataStructure> itr = ds.getModelMapSelected().values().iterator();
		while(itr.hasNext()){
			MassModelDataStructure model = itr.next();
			if(model.getIndex()!=ds.getRefIndex()){
				modelComboBox.addItem(model);
			}
		}
		modelComboBox.setSelectedIndex(index);
		modelComboBox.addActionListener(this);
	}
    
    /**
     * Sets the available options in the <i>Quantity</i> combobox.
     */
    void setComboBoxes(){
    	quantityComboBox.removeActionListener(this);
    	String currentType = typeComboBox.getSelectedItem().toString();
    	String currentQuantity = quantityComboBox.getSelectedItem().toString();
    	if(currentType.equals("Selected Model Data") || currentType.equals("Reference Data")){
    		quantityComboBox.removeAllItems();
    		quantityComboBox.addItem("Mass Excess");
    		quantityComboBox.addItem("Uncertainty");
    		quantityComboBox.addItem("dVpn");
    		quantityComboBox.addItem("S_n");
    		quantityComboBox.addItem("S_2n");
    		quantityComboBox.addItem("S_p");
    		quantityComboBox.addItem("S_2p");
    		quantityComboBox.addItem("S_alpha");
    		quantityComboBox.addItem("Q_(alpha, n)");
    		quantityComboBox.addItem("Q_(alpha, p)");
    		quantityComboBox.addItem("Q_(p, n)");
    		quantityComboBox.setSelectedItem(currentQuantity);
    	}else{
    		quantityComboBox.removeAllItems();
    		quantityComboBox.addItem("Mass Excess");
    		quantityComboBox.addItem("dVpn");
    		quantityComboBox.addItem("S_n");
    		quantityComboBox.addItem("S_2n");
    		quantityComboBox.addItem("S_p");
    		quantityComboBox.addItem("S_2p");
    		quantityComboBox.addItem("S_alpha");
    		quantityComboBox.addItem("Q_(alpha, n)");
    		quantityComboBox.addItem("Q_(alpha, p)");
    		quantityComboBox.addItem("Q_(p, n)");
    		if(currentQuantity.equals("Uncertainty")){
    			quantityComboBox.setSelectedItem("Mass Excess");
    		}else{
    			quantityComboBox.setSelectedItem(currentQuantity);
    		}
    	}
    	quantityComboBox.addActionListener(this);
    }
    
    /**
     * Gets the point vector.
     *
     * @return the point vector
     */
    private Vector getPointVector(){
		
		Vector<Point> outputVector = new Vector<Point>();
		Vector<Double> diffVector = new Vector<Double>();
		Vector<Double> uncerVector = new Vector<Double>();
		
		int theoryLength = mmds.getZNArray().length;
		int expLength = mmdsRef.getZNArray().length;
		
		for(int i=0; i<theoryLength; i++){
		
			for(int j=0; j<expLength; j++){
				
				int ZTheory = (int)mmds.getZNArray()[i].getX();
				int NTheory = (int)mmds.getZNArray()[i].getY();
		
				int ZExp = (int)mmdsRef.getZNArray()[j].getX();
				int NExp = (int)mmdsRef.getZNArray()[j].getY();
		
				if((ZTheory==ZExp)&&(NTheory==NExp)){
				
					outputVector.addElement(new Point(ZTheory, NTheory));
					
					double mass = mmds.getMassArray()[i] - mmdsRef.getMassArray()[j];
					
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
     * Reload data.
     */
    private void reloadData(){
		ReloadDataTask task = new ReloadDataTask(this, delayDialog);
		task.execute();
	}
    
    /**
     * Parses the currently selected {@link MassModelDataStructure} objects into
     * the arrays required by this class.
     */
	public void parseModelDataStructures(){
		mmds = (MassModelDataStructure)modelComboBox.getSelectedItem();
		mmdsRef = ds.getModelMapSelected().get(ds.getRefIndex());
		mmds.createArrays();
		mmdsRef.createArrays();
		pointVector = getPointVector();
		absDiffArray = getAbsDiffArray();
	}

	/**
	 * Gets the abs diff array.
	 *
	 * @return the abs diff array
	 */
	private double[] getAbsDiffArray(){
		double[] outputArray = new double[diffArray.length];
		for(int i=0; i<outputArray.length; i++){
			outputArray[i] = Math.abs(diffArray[i]);
		}
		return outputArray;
	}
	
    /* (non-Javadoc)
     * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent ie){
    
    	if(ie.getSource()==showLabelsCheckBox
    				|| ie.getSource()==showMagicCheckBox
    				|| ie.getSource()==showStableCheckBox
    				|| ie.getSource()==showRProcessCheckBox){
    		vizChartPanel.repaint();
    	}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    
    	if(ae.getSource()==tableButton){
    	
    		if(tableFrame==null){
    	       	
    			tableFrame = new VizChartTableFrame(ds, mds, this);
       		
	       	}
    		
    		Vector vector = new Vector();
    		
    		
    		switch(type){
				//THEORETICAL
				case 0:
			    	for(int i=0; i<mmds.getZNArray().length; i++){
			    		vector.addElement(mmds.getZNArray()[i]);
			    	}
			    	vector.trimToSize();
					break;
				//REFERENCE
				case 1:
					for(int i=0; i<mmdsRef.getZNArray().length; i++){
			    		vector.addElement(mmdsRef.getZNArray()[i]);
			    	}
			    	vector.trimToSize();
					break;
				//DIFFERENCE	
				case 2:
					vector = pointVector;
					break;
				//ABS VALUE DIFFERENCE	
				case 3:
					vector = pointVector;
					break;
	    	}
    		tableFrame.setTableText(vector, valueArray, getTableTitle(), quantityComboBox.getSelectedItem().toString());
    		tableFrame.setVisible(true);
    		
    	}else if(ae.getSource()==saveButton){
	    
	    	createSaveDialog();
	    
	    }else if(ae.getSource()==okButton){
	    	
	    	saveDialog.setVisible(false);
	    	saveDialog.dispose();
	    
	    	PlotSaver.savePlot(new VizChartPrintPanel(whiteTextRadioButton.isSelected(), this), this, mds);
	    	
	    }else if(ae.getSource()==colorButton){
	    
    		if(colorSettingsFrame!=null){
				
    			colorSettingsFrame.setCurrentState(scheme);
    			colorSettingsFrame.setSize(1000, 500);
    			colorSettingsFrame.setVisible(true);

        	}else{
        	
        		colorSettingsFrame = new VizChartColorSettingsFrame(this);
        		colorSettingsFrame.setCurrentState(scheme);
        		colorSettingsFrame.setSize(1000, 500);
        		colorSettingsFrame.setVisible(true);
        		colorSettingsFrame.validate();
        		       	
        	}

	    }else if(ae.getSource()==typeComboBox 
	    		|| ae.getSource()==quantityComboBox
	    		|| ae.getSource()==modelComboBox){
    		delayDialog.openDelayDialog();
			delayDialog.setLocationRelativeTo(this);
			reloadData();
    	}
    
	}
   
    /* (non-Javadoc)
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent ce){
    	if(ce.getSource()==zoomSlider){
    		redoChartSize();
    	}
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowStateListener#windowStateChanged(java.awt.event.WindowEvent)
     */
    public void windowStateChanged(WindowEvent we){
    	redoChartSize();
    }
    
    /**
     * Redo chart size.
     */
    private void redoChartSize(){
    	if(zoomSlider.getValue()==100){
    		
			sp.setColumnHeaderView(ZRuler);
    		sp.setRowHeaderView(NRuler);
		
		}else{
		
			sp.setColumnHeaderView(null);
    		sp.setRowHeaderView(null);
		
		}
		
		double scale = zoomSlider.getValue()/100.0;
		vizChartPanel.boxHeight = (int)(29.0*scale);
		vizChartPanel.boxWidth = (int)(29.0*scale);
        
    	vizChartPanel.width = (vizChartPanel.boxWidth*(vizChartPanel.nmax+1));
        vizChartPanel.height = (vizChartPanel.boxHeight*(vizChartPanel.zmax+1));
        
        vizChartPanel.xmax = (vizChartPanel.xoffset + vizChartPanel.width);
        vizChartPanel.ymax = (vizChartPanel.yoffset + vizChartPanel.height);

        if((vizChartPanel.xmax+vizChartPanel.xoffset)<sp.getSize().width
        		|| (vizChartPanel.ymax+vizChartPanel.yoffset)<sp.getSize().height){
        	
        	vizChartPanel.setSize(sp.getSize().width, sp.getSize().height);
        	
        	
        }else{
        	vizChartPanel.setSize(vizChartPanel.xmax+vizChartPanel.xoffset, vizChartPanel.ymax+vizChartPanel.yoffset);
        	
        }
        
    			
		vizChartPanel.setPreferredSize(vizChartPanel.getSize());
		
		vizChartPanel.repaint();

		zoomField.setText(String.valueOf(zoomSlider.getValue()));
		
		if(!zoomSlider.getValueIsAdjusting()){
		
    		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
			sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		}
		
		sp.setBackground(Color.black);
		sp.validate();

    }
    
    /**
     * Gets the table title.
     *
     * @return the table title
     */
    private String getTableTitle(){
    	String string = "";
    	String quantity = quantityComboBox.getSelectedItem().toString();
    	switch(type){
			//THEORETICAL
			case 0:
				string = quantity + " Values for " + mmds.toString();
				break;
			//REFERENCE
			case 1:
				string = quantity + " Values for " + mmdsRef.toString();
				break;
			//DIFFERENCE	
			case 2:
				string = "Difference of " + quantity + " Values for " + mmdsRef.toString() + " and " + mmds.toString();
				break;
			//ABS VALUE DIFFERENCE	
			case 3:
				string = "Abs Difference of " + quantity + " Values for " + mmdsRef.toString() + " and " + mmds.toString();
				break;
    	}
    	return string;
    }
    
    /**
     * Initializes this class to the quantity and type.
     * 
     * @param	type		an item from the chart type combobox
     * @param	quantity	an item from the quantity combobox
     */
    public void initialize(int type, int quantity){
    
    	zoomField.setText(String.valueOf(zoomSlider.getValue()));

		double[] valueArrayTheory = new double[0];
		double[] valueArrayExp = new double[0];
		double[] valueArrayDiff = new double[0];
	
		switch(type){
			
			//THEORETICAL
			case 0:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = mmds.getMassArray();

						break;
					
					//UNCER	
					case 1:
					
						valueArray = mmds.getUncerArray();
					
						break;	
					
					//DVPN	
					case 2:
					
						valueArray = getValueArray(0, mmds);
					
						break;
					
						
					//N	
					case 3:
					
						valueArray = getValueArray(1, mmds);
					
						break;
					
					//2N	
					case 4:
					
						valueArray = getValueArray(2, mmds);
					
						break;
					
					//P	
					case 5:
					
						valueArray = getValueArray(3, mmds);
					
						break;
						
					//2P	
					case 6:
					
						valueArray = getValueArray(4, mmds);
					
						break;
					
					//ALPHA	
					case 7:
					
						valueArray = getValueArray(5, mmds);
					
						break;
					
					//ALPHA,N
					case 8:
					
						valueArray = getValueArray(6, mmds);
					
						break;
					
					//ALPHA,P	
					case 9:
					
						valueArray = getValueArray(7, mmds);
					
						break;
					
					//P,N	
					case 10:
					
						valueArray = getValueArray(8, mmds);
					
						break;
				
				}
				
				break;
		
			//REFERENCE
			case 1:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = mmdsRef.getMassArray();
						
						break;
					
					//UNCER	
					case 1:
					
						valueArray = mmdsRef.getUncerArray();
					
						break;
						
					//DVPN	
					case 2:
					
						valueArray = getValueArray(0, mmdsRef);
					
						break;
						
					//N	
					case 3:
					
						valueArray = getValueArray(1, mmdsRef);
					
						break;
					
					//2N	
					case 4:
					
						valueArray = getValueArray(2, mmdsRef);
					
						break;
					
					//P	
					case 5:
					
						valueArray = getValueArray(3, mmdsRef);
					
						break;
						
					//2P	
					case 6:
					
						valueArray = getValueArray(4, mmdsRef);
					
						break;
					
					//ALPHA	
					case 7:
					
						valueArray = getValueArray(5, mmdsRef);
					
						break;
					
					//ALPHA,N
					case 8:
					
						valueArray = getValueArray(6, mmdsRef);
					
						break;
					
					//ALPHA,P	
					case 9:
					
						valueArray = getValueArray(7, mmdsRef);
					
						break;
					
					//P,N	
					case 10:
					
						valueArray = getValueArray(8, mmdsRef);
					
						break;
				
				}
				
				break;
			
			//DIFFERENCE	
			case 2:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = diffArray;
					
						break;
					
					//DVPN
					case 1:
					
						valueArrayTheory = getValueArray(0, mmds);
						valueArrayExp = getValueArray(0, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
						
					//N	
					case 2:
					
						valueArrayTheory = getValueArray(1, mmds);
						valueArrayExp = getValueArray(1, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
					
					//2N	
					case 3:
					
						valueArrayTheory = getValueArray(2, mmds);
						valueArrayExp = getValueArray(2, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						
						break;
					
					//P	
					case 4:
					
						valueArrayTheory = getValueArray(3, mmds);
						valueArrayExp = getValueArray(3, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
						
					//2P	
					case 5:
					
						valueArrayTheory = getValueArray(4, mmds);
						valueArrayExp = getValueArray(4, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
					
					//ALPHA	
					case 6:
					
						valueArrayTheory = getValueArray(5, mmds);
						valueArrayExp = getValueArray(5, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
					
					//ALPHA,N
					case 7:
					
						valueArrayTheory = getValueArray(6, mmds);
						valueArrayExp = getValueArray(6, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
					
					//ALPHA,P	
					case 8:
					
						valueArrayTheory = getValueArray(7, mmds);
						valueArrayExp = getValueArray(7, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
					
					//P,N	
					case 9:
					
						valueArrayTheory = getValueArray(8, mmds);
						valueArrayExp = getValueArray(8, mmdsRef);
						valueArray = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
					
						break;
				
				}
				
				break;
			
			//ABS VALUE DIFFERENCE	
			case 3:
			
				switch(quantity){
				
					//EXCESS
					case 0:
					
						valueArray = absDiffArray;
						
						break;
					
					//DVPN
					case 1:
					
						valueArrayTheory = getValueArray(0, mmds);
						valueArrayExp = getValueArray(0, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
					
						break;
						
					//N	
					case 2:
					
						valueArrayTheory = getValueArray(1, mmds);
						valueArrayExp = getValueArray(1, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//2N	
					case 3:
					
						valueArrayTheory = getValueArray(2, mmds);
						valueArrayExp = getValueArray(2, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//P	
					case 4:
					
						valueArrayTheory = getValueArray(3, mmds);
						valueArrayExp = getValueArray(3, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
						
					//2P	
					case 5:
					
						valueArrayTheory = getValueArray(4, mmds);
						valueArrayExp = getValueArray(4, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//ALPHA	
					case 6:
					
						valueArrayTheory = getValueArray(5, mmds);
						valueArrayExp = getValueArray(5, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//ALPHA,N
					case 7:
					
						valueArrayTheory = getValueArray(6, mmds);
						valueArrayExp = getValueArray(6, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//ALPHA,P	
					case 8:
					
						valueArrayTheory = getValueArray(7, mmds);
						valueArrayExp = getValueArray(7, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
					
					//P,N	
					case 9:
					
						valueArrayTheory = getValueArray(8, mmds);
						valueArrayExp = getValueArray(8, mmdsRef);
						valueArrayDiff = getDiffValueArray(valueArrayTheory, valueArrayExp, pointVector);
						valueArray = getAbsDiffArray(valueArrayDiff);
						
						break;
				
				}
				
				break;

		}

		minField.setText(NumberFormats.getFormattedDer(getMinValue()));
		maxField.setText(NumberFormats.getFormattedDer(getMaxValue()));
						
		expModelLabel.setText(mmdsRef.toString());						

		x0R = colorValues[0];
	    x0G = colorValues[1];
	   	x0B = colorValues[2];
	    aR = colorValues[3];
	    aG = colorValues[4];
	    aB = colorValues[5];
		
		vizRainbowPanel.setRGB(colorValues);

		chartColorArray = getChartColorArray(type, scheme);
		
		vizChartPanel.setCurrentState(type);
		
		vizRainbowPanel.setScheme(scheme);
		vizRainbowPanel.repaint();
		
		sp.getHorizontalScrollBar().setMinimum(0);
		sp.getVerticalScrollBar().setMaximum(vizChartPanel.getHeight());
	
		sp.getHorizontalScrollBar().setValue(sp.getHorizontalScrollBar().getMinimum());
		sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
		
		sp.validate();
		
		ZRuler.setPreferredWidth((int)vizChartPanel.getSize().getWidth());
       	NRuler.setPreferredHeight((int)vizChartPanel.getSize().getHeight());
	
		ZRuler.setCurrentState(vizChartPanel.zmax
								, vizChartPanel.nmax
								, vizChartPanel.mouseX
								, vizChartPanel.mouseY
								, vizChartPanel.xoffset
								, vizChartPanel.yoffset
								, vizChartPanel.crosshairsOn);
								
    	NRuler.setCurrentState(vizChartPanel.zmax
								, vizChartPanel.nmax
								, vizChartPanel.mouseX
								, vizChartPanel.mouseY
								, vizChartPanel.xoffset
								, vizChartPanel.yoffset
								, vizChartPanel.crosshairsOn);
	
    }
    
    /**
     * Gets the value array.
     *
     * @param quantity the quantity
     * @param appmmds the appmmds
     * @return the value array
     */
    private double[] getValueArray(int quantity, MassModelDataStructure appmmds){
    
    	double[] outputArray = new double[appmmds.getZNArray().length];
    	
    	Vector ZNVector = new Vector();
    	
    	for(int i=0; i<appmmds.getZNArray().length; i++){
    		ZNVector.addElement(appmmds.getZNArray()[i]);
    	}
    	
    	ZNVector.trimToSize();
    	
    	double M_n = 8.071;
    	double M_H = 7.289;
    	double M_He = 2.425;
    	double M_ZN = 0;
    	int currentZ = 0;
    	int currentN = 0;
    	int index = 0;
    	
    	for(int i=0; i<outputArray.length; i++){
    	
	    	switch(quantity){
	    	
		    	//DVPN
				case 0:
				
					int z = (int)appmmds.getZNArray()[i].getX();
					int n = (int)appmmds.getZNArray()[i].getY();
					int n2 = n-2;
					int z2 = z-2;
					int index_z_n = ZNVector.indexOf(new Point(z, n));
					int index_z_n2 = ZNVector.indexOf(new Point(z, n2));
					int index_z2_n = ZNVector.indexOf(new Point(z2, n));
					int index_z2_n2 = ZNVector.indexOf(new Point(z2, n2));

					M_ZN = appmmds.getMassArray()[i];
					double dVpn = 1E100;
					
					if(index_z_n!=-1 && index_z_n2!=-1 && index_z2_n!=-1 && index_z2_n2!=-1){
					
						dVpn = 0.25*((appmmds.getMassArray()[index_z_n]-appmmds.getMassArray()[index_z_n2])
										-(appmmds.getMassArray()[index_z2_n]-appmmds.getMassArray()[index_z2_n2]));
					
					}
				
					outputArray[i] = dVpn;
					
					break;
	    	
		    	//N	
				case 1:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ, currentN-1));
					double Sn = 1E100;
					
					if(index!=-1){
					
						Sn = appmmds.getMassArray()[index] - M_ZN + M_n;
					
					}
				
					outputArray[i] = Sn;
					
					break;
				
				//2N	
				case 2:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ, currentN-2));
					double S2n = 1E100;
					
					if(index!=-1){
					
						S2n = appmmds.getMassArray()[index] - M_ZN + 2*M_n;
					
					}
				
					outputArray[i] = S2n;
					
					break;
				
				//P	
				case 3:
					
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ-1, currentN));
					double Sp = 1E100;
					
					if(index!=-1){
					
						Sp = appmmds.getMassArray()[index] - M_ZN + M_H;
					
					}
				
					outputArray[i] = Sp;
					
					break;
					
				//2P	
				case 4:
					
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ-2, currentN));
					double S2p = 1E100;
					
					if(index!=-1){
					
						S2p = appmmds.getMassArray()[index] - M_ZN + 2*M_H;
					
					}
				
					outputArray[i] = S2p;
					
					break;
				
				//ALPHA	
				case 5:
					
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ-2, currentN-2));
					double SAlpha = 1E100;
					
					if(index!=-1){
					
						SAlpha = appmmds.getMassArray()[index] - M_ZN + M_He;
					
					}
				
					outputArray[i] = SAlpha;
					
					break;
				
				//ALPHA,N
				case 6:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ+2, currentN+1));
					double SAlphaN = 1E100;
					
					if(index!=-1){
					
						SAlphaN = M_ZN - appmmds.getMassArray()[index] + M_He - M_n;
					
					}
				
					outputArray[i] = SAlphaN;
					
					break;
				
				//ALPHA,P	
				case 7:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ+1, currentN+2));
					double SAlphaP = 1E100;
					
					if(index!=-1){
					
						SAlphaP = M_ZN - appmmds.getMassArray()[index] + M_He - M_H;
					
					}
				
					outputArray[i] = SAlphaP;
					
					break;
				
				//P,N	
				case 8:
				
					M_ZN = appmmds.getMassArray()[i];
					currentZ = (int)appmmds.getZNArray()[i].getX();
					currentN = (int)appmmds.getZNArray()[i].getY();
					index = ZNVector.indexOf(new Point(currentZ+1, currentN-1));
					double Snp = 1E100;
					
					if(index!=-1){
					
						Snp = M_ZN - appmmds.getMassArray()[index] + M_H - M_n;
					
					}
				
					outputArray[i] = Snp;
					
					break;
				
			}
		
		}
		
		return outputArray;
    
    }
    
    /**
     * Gets the diff value array.
     *
     * @param valueArrayTheory the value array theory
     * @param valueArrayExp the value array exp
     * @param pointVector the point vector
     * @return the diff value array
     */
    private double[] getDiffValueArray(double[] valueArrayTheory, double[] valueArrayExp, Vector pointVector){
    
    	double[] outputArray = new double[pointVector.size()];
    
    	int counter = 0;
    
    	for(int i=0; i<mmds.getZNArray().length; i++){
    	
    		int theoryZ = (int)mmds.getZNArray()[i].getX();
    		int theoryN = (int)mmds.getZNArray()[i].getY();
    		
    		for(int j=0; j<mmdsRef.getZNArray().length; j++){
    		
    			int expZ = (int)mmdsRef.getZNArray()[j].getX();
    			int expN = (int)mmdsRef.getZNArray()[j].getY();
    			
    			if((theoryZ==expZ) && (theoryN==expN) && pointVector.contains(new Point(theoryZ, theoryN))){
    			
    				double value = 1E100;
    			
    				if(valueArrayTheory[i]!=1E100 && valueArrayExp[j]!=1E100){
    				
    					value = valueArrayTheory[i] - valueArrayExp[j];
    				
    				}
    			
    				outputArray[counter] = value;
    				counter++;
    			
    			}
    		
    		}
    	
    	}
    
    	return outputArray;
    
    }
    
    /**
     * Gets the abs diff array.
     *
     * @param diffArray the diff array
     * @return the abs diff array
     */
    private double[] getAbsDiffArray(double[] diffArray){
    
    	double[] outputArray = new double[diffArray.length];
		
		for(int i=0; i<outputArray.length; i++){
		
			outputArray[i] = Math.abs(diffArray[i]);
		
		}
		return outputArray;
    }
    
    /**
     * Gets the minimum value currently displayed on the nuclide chart.
     * 
     * @return	the minimum value currently displayed on the nuclide chart
     */
    public double getMinValue(){
    	double min = 1E100;
    	for(int i=0; i<valueArray.length; i++){
    		if(valueArray[i]!=1E100){
    			min = Math.min(min, valueArray[i]);
    		}
    	}
    	return min;
    }
    
    /**
     * Gets the maximum value currently displayed on the nuclide chart.
     * 
     * @return	the maximum value currently displayed on the nuclide chart
     */
    public double getMaxValue(){
    	double max = -1E100;
    	for(int i=0; i<valueArray.length; i++){
    		if(valueArray[i]!=1E100){
    			max = Math.max(max, valueArray[i]);
    		}
    	}
    	return max;
    }
    
    /**
     * Download r process isotopes.
     */
    private void downloadRProcessIsotopes(){
		String dataString = new String(FileImport.getFile("MassModelData/rpSmall.txt"));
		parseRProcessIsotopesString(dataString);
    }
    
    /**
     * Parses the r process isotopes string.
     *
     * @param string the string
     */
    private void parseRProcessIsotopesString(String string){
    
    	StringTokenizer st = new StringTokenizer(string);
    
    	int numberTokens = st.countTokens();
    	
    	Vector tempVector = new Vector();
 	
    	for(int i=0; i<(numberTokens/2); i++){
    	
    		tempVector.addElement(new Point((int)(Double.valueOf(st.nextToken()).doubleValue())
    									, (int)(Double.valueOf(st.nextToken()).doubleValue())));
    	
    	}
    	rProcessArray = tempVector;
    
    }
    
    /**
     * Download stable isotopes.
     */
    private void downloadStableIsotopes(){
		String dataString = new String(FileImport.getFile("MassModelData/Expstablenuclides"));
		parseStableIsotopesString(dataString);
    }
    
    /**
     * Parses the stable isotopes string.
     *
     * @param string the string
     */
    private void parseStableIsotopesString(String string){
    
    	StringTokenizer st = new StringTokenizer(string);

    	int numberTokens = st.countTokens();
    	
    	Vector tempVector = new Vector();
 	
    	for(int i=0; i<(numberTokens/2); i++){
    	
    		tempVector.addElement(new Point(Integer.valueOf(st.nextToken()).intValue(), Integer.valueOf(st.nextToken()).intValue()));
    	
    	}
    
    	stableArray = tempVector;
    
    }
    
    /**
     * Gets an array of {@link Color} objects indexed on isotope.
     *
     * @param type 	an item from the chart type combobox
     * @param scheme 		either "Binned" or "Continuous"
     * @return the chart color array
     */
    public Color[] getChartColorArray(int type, String scheme){
    
	    x0R = colorValues[0];
	    x0G = colorValues[1];
	    x0B = colorValues[2];
	    aR = colorValues[3];
	    aG = colorValues[4];
	    aB = colorValues[5]; 
    
    	Color[] colorArray = new Color[0];
    
    	if(type==2 || type==3){

			colorArray = new Color[pointVector.size()];
		
		}else if(type==0){
		
			colorArray = new Color[mmds.getZNArray().length];
		
		}else if(type==1){
		
			colorArray = new Color[mmdsRef.getZNArray().length];

		}

		if(scheme.equals("Continuous")){

			for(int i=0; i<colorArray.length; i++){
			
				if(valueArray[i]!=1E100){
			
					colorArray[i] = getColor(valueArray[i]
													, NumberLocalizer.valueOf(minField.getText()).doubleValue()
													, NumberLocalizer.valueOf(maxField.getText()).doubleValue());
											
				}else{
				
					colorArray[i] = null;
				
				}
			
			}
		
		}else if(scheme.equals("Binned")){
		
			for(int i=0; i<colorArray.length; i++){
		
				colorArray[i] = getBinnedColor(valueArray[i]);
			
			}
		
		}

    	return colorArray;
    	
    }
    
    /**
     * Gets the color.
     *
     * @param value the value
     * @param min the min
     * @param max the max
     * @return the color
     */
    private Color getColor(double value, double min, double max){
    	
        double normValue;
        
        Color color;
        
        if(includeValues){
        
	        normValue = (1/(max-min))*(value-min);
	
	        color = getRGB(normValue); 
        
    	}else{
    	
    		if(value<=max && value>=min){
    		
    			normValue = (1/(max-min))*(value-min);
	
	        	color = getRGB(normValue);
    		
    		}else{
    		
    			color = null;
    		
    		}
    	
    	}
        
        return color;
    }
    
    /**
     * Gets the binned color.
     *
     * @param value the value
     * @return the binned color
     */
    private Color getBinnedColor(double value){
    
    	Color tempColor = null;
    
    	Vector binDataVector = binData;
		
		binFound:
		for(int i=0; i<binDataVector.size(); i++){
		
			double min = ((Double)((Vector)binDataVector.elementAt(i)).elementAt(0)).doubleValue();
			double max = ((Double)((Vector)binDataVector.elementAt(i)).elementAt(1)).doubleValue();
			
			if(value>=min && value<max){
			
				if(((Boolean)((Vector)binDataVector.elementAt(i)).elementAt(2)).booleanValue()){
				
					tempColor = (Color)((Vector)binDataVector.elementAt(i)).elementAt(3);
					break binFound;
				
				}
			
			}else{

				tempColor = null;
			
			}	
		
		}   
    	
    	return tempColor;
    
    }
    
    /**
     * Gets a {@link Color} object for the specified value.
     *
     * @param x the specified value
     * @return the rGB
     */
    protected Color getRGB(double x){
    	
    	if(x>=1.0){
    	
    		x = 1.0;
    	
    	}
    	
    	if(x<=0.0){
    	
    		x = 0.0;
    	
    	}
    	
        int red = (int)(255*Math.exp(-(x-x0R)*(x-x0R)/aR/aR));
        int green = (int)(255*Math.exp(-(x-x0G)*(x-x0G)/aG/aG));
        int blue = (int)(255*Math.exp(-(x-x0B)*(x-x0B)/aB/aB));
        
        return new Color(red,green,blue);   
    }
    
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent we) {   
	
		if(we.getSource()==this){  

		   setVisible(false);
		   dispose();
		   
    	}
    	
    } 
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent we){redoChartSize();}
    
    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent we){redoChartSize();}
    
    /**
     * Creates the save dialog.
     */
    public void createSaveDialog(){
    	
    	//Create a new JDialog object
    	saveDialog = new JDialog(this, "Select Image Type", true);
    	saveDialog.setSize(355, 158);
    	saveDialog.getContentPane().setLayout(new GridBagLayout());
    	saveDialog.setLocationRelativeTo(this);
    	
    	GridBagConstraints gbc1 = new GridBagConstraints();
    	
    	JPanel boxPanel = new JPanel(new GridBagLayout());
    	
    	//Check box group for radio buttons
    	ButtonGroup choiceButtonGroup = new ButtonGroup();
    	
		blackTextRadioButton = new JRadioButton("Black text / White background", false);
		blackTextRadioButton.setFont(Fonts.textFont);
		
		whiteTextRadioButton = new JRadioButton("White text / Black background", true);
		whiteTextRadioButton.setFont(Fonts.textFont);
		
		choiceButtonGroup.add(blackTextRadioButton);
		choiceButtonGroup.add(whiteTextRadioButton);
		
		//Create submit button and its properties
		okButton = new JButton("Submit");
		okButton.setFont(Fonts.buttonFont);
		okButton.addActionListener(this);
		
		JLabel label = new JLabel("Please select simulation data types for animation.");
		label.setFont(Fonts.textFont);
		
		//Layout the components
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.anchor = GridBagConstraints.CENTER;

		saveDialog.getContentPane().add(label, gbc1);

		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.gridwidth = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		boxPanel.add(whiteTextRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		boxPanel.add(blackTextRadioButton, gbc1);
		
		gbc1.gridx = 0;
		gbc1.gridy = 2;
		gbc1.gridwidth = 2;
		gbc1.anchor = GridBagConstraints.CENTER;
		boxPanel.add(okButton, gbc1);
		
		gbc1.gridwidth = 1;
		gbc1.gridx = 0;
		gbc1.gridy = 1;
		gbc1.anchor = GridBagConstraints.CENTER;
		saveDialog.getContentPane().add(boxPanel, gbc1);

		//AstroPilotProject.setFrameColors(saveDialog);
		
		//Show the dialog
		saveDialog.setVisible(true);

    }
	
}

class ReloadDataTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private VizChartFrame frame;
	private DelayDialog dialog;
	
	public ReloadDataTask(VizChartFrame frame, DelayDialog dialog){
		this.frame = frame;
		this.dialog = dialog;
	}
	
	protected Void doInBackground(){
		frame.executeDataReload();
		return null;
	}
	
	protected void done(){
		dialog.closeDelayDialog();
		
	}
}
