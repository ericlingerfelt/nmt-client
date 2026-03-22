package org.nuclearmasses.gui.viz.chart;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.dialogs.*;

/**
 * This class allows Users to set customized color scale settings for the <i>Interactive Nuclide Chart</i>.
 */
public class VizChartColorSettingsFrame extends JFrame implements ItemListener, ActionListener, ChangeListener, WindowListener{

	/** The c. */
	private Container c;
	
	/** The gbc. */
	private GridBagConstraints gbc;
	
	/** The help button. */
	private JButton defaultButton, applyButton, applyRangeButton, helpButton;
	
	/** The max field. */
	private JTextField minField, maxField;
	
	/** The max slider. */
	private JSlider minSlider, maxSlider;
	
	/** The num bin label. */
	private JLabel topLabel, minLabel, maxLabel, colorSchemeLabelCont, colorSchemeLabelBinned, schemeLabel, numBinLabel;
	
	/** The color scheme combo box binned. */
	private JComboBox colorSchemeComboBoxCont, schemeComboBox, colorSchemeComboBoxBinned; 
	
	/** The table pane. */
	private JScrollPane sp, tablePane;
	
	/** The num bin spinner. */
	private JSpinner numBinSpinner;
	
	/** The num bin model. */
	private SpinnerNumberModel numBinModel;
	
	/** The viz rainbow panel. */
	private VizRainbowPanel vizRainbowPanel;
	
	/** The table. */
	private VizChartColorSettingsTable table;
	
	/** The num bin panel. */
	private JPanel valuePanel, colorPanel, buttonPanel, sliderPanel, schemePanel, contentPanelCont, contentPanelBin, numBinPanel;
	
	/** The a b slider. */
	private JSlider x0RSlider, x0GSlider, x0BSlider, aRSlider, aGSlider, aBSlider;
	
	/** The blue label. */
	private JLabel x0RLabel, x0GLabel, x0BLabel, aRLabel, aGLabel, aBLabel, redLabel, greenLabel, blueLabel;
	
	/** The a b field. */
	private JTextField x0RField, x0GField, x0BField, aRField, aGField, aBField;
	
	/** The exclude radio button. */
	private JRadioButton includeRadioButton, excludeRadioButton;
	
	/** The button group. */
	private ButtonGroup buttonGroup;
	
	/** The x0 r. */
	private int x0R = 80;
	
	/** The x0 g. */
	private int x0G = 60;
	
	/** The x0 b. */
	private int x0B = 20;
	
	/** The a r. */
	private int aR = 50;
	
	/** The a g. */
	private int aG = 40;
	
	/** The a b. */
	private int aB = 30;
	
	/** The scheme. */
	private String scheme;
	
	/** The parent. */
	private VizChartFrame parent;
	
	/**
	 * Class constructor.
	 *
	 * @param parent the reference to the {@link VizChartFrame} spawning this frame
	 */
	public VizChartColorSettingsFrame(VizChartFrame parent){
	
		this.parent = parent;
	
		setTitle("Mass Dataset Nuclide Chart Color Scale Settings");
	
		c = getContentPane();
	
		c.setLayout(new BorderLayout());
		
		gbc = new GridBagConstraints();
		
		defaultButton = new JButton("Default Settings");
		defaultButton.setFont(Fonts.buttonFont);
		defaultButton.addActionListener(this);
		
		applyButton = new JButton("Apply Settings");
		applyButton.setFont(Fonts.buttonFont);
		applyButton.addActionListener(this);
		
		applyRangeButton = new JButton("Enter Max/Min Range");
		applyRangeButton.setFont(Fonts.buttonFont);
		applyRangeButton.addActionListener(this);
		
		helpButton = new JButton("Help on This Interface");
        helpButton.setFont(Fonts.buttonFont);
        helpButton.addActionListener(this);
		
		x0RSlider = new JSlider(JSlider.VERTICAL, 0, 100, x0R);
		x0RSlider.addChangeListener(this);
		x0RSlider.setPreferredSize(new Dimension(20, 130));
		
		x0GSlider = new JSlider(JSlider.VERTICAL, 0, 100, x0G);
		x0GSlider.addChangeListener(this);
		x0GSlider.setPreferredSize(new Dimension(20, 130));
		
		x0BSlider = new JSlider(JSlider.VERTICAL, 0, 100, x0B);
		x0BSlider.addChangeListener(this);
		x0BSlider.setPreferredSize(new Dimension(20, 130));
		
		aRSlider = new JSlider(JSlider.VERTICAL, 0, 100, aR);
		aRSlider.addChangeListener(this);
		aRSlider.setPreferredSize(new Dimension(20, 130));
		
		aGSlider = new JSlider(JSlider.VERTICAL, 0, 100, aG);
		aGSlider.addChangeListener(this);
		aGSlider.setPreferredSize(new Dimension(20, 130));
		
		aBSlider = new JSlider(JSlider.VERTICAL, 0, 100, aB);
		aBSlider.addChangeListener(this);
		aBSlider.setPreferredSize(new Dimension(20, 130));
		
		redLabel = new JLabel("Red");
		redLabel.setFont(Fonts.textFont);

		greenLabel = new JLabel("Green");
		greenLabel.setFont(Fonts.textFont);
		
		blueLabel = new JLabel("Blue");
		blueLabel.setFont(Fonts.textFont);

		x0RLabel = new JLabel("Position : ");
		x0RLabel.setFont(Fonts.textFont);
		
		x0GLabel = new JLabel("Position : ");
		x0GLabel.setFont(Fonts.textFont);
		
		x0BLabel = new JLabel("Position : ");
		x0BLabel.setFont(Fonts.textFont);
		
		aRLabel = new JLabel("Amount : ");
		aRLabel.setFont(Fonts.textFont);
		
		aGLabel = new JLabel("Amount : ");
		aGLabel.setFont(Fonts.textFont);
		
		aBLabel = new JLabel("Amount : ");
		aBLabel.setFont(Fonts.textFont);

		x0RField = new JTextField(5);
		x0RField.setText(String.valueOf(x0R/100.0));
		x0RField.setEditable(false);
		
		x0GField = new JTextField(5);
		x0GField.setText(String.valueOf(x0G/100.0));
		x0GField.setEditable(false);
		
		x0BField = new JTextField(5);
		x0BField.setText(String.valueOf(x0B/100.0));
		x0BField.setEditable(false);
		
		aRField = new JTextField(5);
		aRField.setText(String.valueOf(aR/100.0));
		aRField.setEditable(false);
		
		aGField = new JTextField(5);
		aGField.setText(String.valueOf(aG/100.0));
		aGField.setEditable(false);
		
		aBField = new JTextField(5);
		aBField.setText(String.valueOf(aB/100.0));
		aBField.setEditable(false);
		
		includeRadioButton = new JRadioButton("Map values outside of range to max/min color", true);
		includeRadioButton.setFont(Fonts.textFont);

		excludeRadioButton = new JRadioButton("Show only values within this range", false);
		excludeRadioButton.setFont(Fonts.textFont);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(includeRadioButton);
		buttonGroup.add(excludeRadioButton);
		
		minField = new JTextField(10);
		
		maxField = new JTextField(10);
		
		minSlider = new JSlider(JSlider.HORIZONTAL);
		minSlider.addChangeListener(this);
		
		maxSlider = new JSlider(JSlider.HORIZONTAL);
		maxSlider.addChangeListener(this);
		
		topLabel = new JLabel("<html>With this tool, you can set the floor and ceiling of the mass dataset nuclide chart color scale<p>and select a new color scheme for the Interactive Nuclide Chart by using the sliders below.</html>");
		
		schemeLabel = new JLabel("Select type of color scale : ");
		
		numBinLabel = new JLabel("Select number of bins : ");
		numBinLabel.setFont(Fonts.textFont);
		
		numBinModel = new SpinnerNumberModel(5, 1, 10, 1);

		numBinSpinner = new JSpinner(numBinModel);
		numBinSpinner.addChangeListener(this);
        ((JSpinner.DefaultEditor)(numBinSpinner.getEditor())).getTextField().setEditable(false);
		
		minLabel = new JLabel("Value min : ");
		minLabel.setFont(Fonts.textFont);
		
		maxLabel = new JLabel("Value max : ");
		maxLabel.setFont(Fonts.textFont);
		
		colorSchemeLabelCont = new JLabel("<html>Choose a<p>color scheme :</html>");
		colorSchemeLabelCont.setFont(Fonts.textFont);
		
		colorSchemeComboBoxCont = new JComboBox();
		colorSchemeComboBoxCont.setFont(Fonts.textFont);
		colorSchemeComboBoxCont.addItem("Rainbow 1");
		colorSchemeComboBoxCont.addItem("Rainbow 2");
		colorSchemeComboBoxCont.addItem("Fire");
		colorSchemeComboBoxCont.addItem("Purple Haze");
		colorSchemeComboBoxCont.addItem("Greyscale");
		colorSchemeComboBoxCont.addItemListener(this);
		
		colorSchemeLabelBinned = new JLabel("Choose a color scheme : ");
		colorSchemeLabelBinned.setFont(Fonts.textFont);
		
		colorSchemeComboBoxBinned = new JComboBox();
		colorSchemeComboBoxBinned.setFont(Fonts.textFont);
		colorSchemeComboBoxBinned.addItem("Color Scheme 1");
		colorSchemeComboBoxBinned.addItem("Color Scheme 2");
		colorSchemeComboBoxBinned.addItem("Color Scheme 3");
		
		vizRainbowPanel = new VizRainbowPanel(parent);
		sp = new JScrollPane(vizRainbowPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(50, 100));
		
		schemeComboBox = new JComboBox();
		schemeComboBox.setFont(Fonts.textFont);
		schemeComboBox.addItem("Continuous");
		schemeComboBox.addItem("Binned");
		
		table = new VizChartColorSettingsTable(parent);
		table.validate();
		
		tablePane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePane.setPreferredSize(new Dimension(600, 125));
		
		numBinPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		numBinPanel.add(numBinLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		numBinPanel.add(numBinSpinner, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		//numBinPanel.add(colorSchemeLabelBinned, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 0;
		//numBinPanel.add(colorSchemeComboBoxBinned, gbc);
		
		schemePanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		schemePanel.add(schemeLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		schemePanel.add(schemeComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		schemePanel.add(topLabel, gbc);
		
		gbc.gridwidth = 1;
		
		contentPanelCont = new JPanel(new GridBagLayout());
		contentPanelBin = new JPanel(new BorderLayout());
		
		colorPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		colorPanel.add(colorSchemeLabelCont, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		colorPanel.add(colorSchemeComboBoxCont, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		colorPanel.add(sp, gbc);
		
		buttonPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		buttonPanel.add(defaultButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		buttonPanel.add(applyButton, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		buttonPanel.add(applyRangeButton, gbc);
				
		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		//buttonPanel.add(helpButton, gbc);
		
		sliderPanel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		sliderPanel.add(redLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		sliderPanel.add(greenLabel, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		sliderPanel.add(blueLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		sliderPanel.add(x0RLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		sliderPanel.add(x0RField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		sliderPanel.add(x0RSlider, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		sliderPanel.add(aRLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		sliderPanel.add(aRField, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		sliderPanel.add(aRSlider, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		sliderPanel.add(x0GLabel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		sliderPanel.add(x0GField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		sliderPanel.add(x0GSlider, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		sliderPanel.add(aGLabel, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		sliderPanel.add(aGField, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		sliderPanel.add(aGSlider, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		sliderPanel.add(x0BLabel, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		sliderPanel.add(x0BField, gbc);
		
		gbc.gridx = 4;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		sliderPanel.add(x0BSlider, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		sliderPanel.add(aBLabel, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		sliderPanel.add(aBField, gbc);
		
		gbc.gridx = 5;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		sliderPanel.add(aBSlider, gbc);
		
		valuePanel = new JPanel(new GridBagLayout());
			
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;	
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;
		valuePanel.add(maxLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;	
		gbc.anchor = GridBagConstraints.CENTER;
		valuePanel.add(maxField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;	
		valuePanel.add(maxSlider, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;	
		gbc.anchor = GridBagConstraints.EAST;
		valuePanel.add(minLabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;	
		gbc.anchor = GridBagConstraints.CENTER;
		valuePanel.add(minField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;	
		valuePanel.add(minSlider, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;	
		gbc.anchor = GridBagConstraints.WEST;
		valuePanel.add(includeRadioButton, gbc);
	
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;	
		valuePanel.add(excludeRadioButton, gbc);
	
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(5, 5, 5, 5);
	
		c.add(buttonPanel, BorderLayout.SOUTH);
		c.add(schemePanel, BorderLayout.NORTH);
	
	}
	
	/**
	 * Sets the layout of this class.
	 * 
	 * @param	scheme	either "Continuous" or "Binned"
	 */
	public void setFormatLayout(String scheme){
	
		if(scheme.equals("Continuous")){
	
			c.remove(contentPanelBin);
			
			minLabel.setText("Value min : ");
			maxLabel.setText("Value max : ");
			
			topLabel.setText("<html>With this tool, you can set the floor and ceiling of the mass dataset nuclide chart color scale<p>and select a new color scheme for the Mass Dataset Evaluator by using the sliders below.</html>");
		
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			contentPanelCont.add(valuePanel, gbc);
	
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			contentPanelCont.add(colorPanel, gbc);
			
			gbc.gridx = 2;
			gbc.gridy = 1;
			gbc.gridwidth = 1;
			contentPanelCont.add(sliderPanel, gbc);
			
			c.add(contentPanelCont, BorderLayout.CENTER);
			
			gbc.gridwidth = 1;
		
			gbc.gridx = 2;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			buttonPanel.add(applyRangeButton, gbc);
		
		}else if(scheme.equals("Binned")){
			
			buttonPanel.remove(applyRangeButton);
			
			c.remove(contentPanelCont);
			
			contentPanelBin.add(numBinPanel, BorderLayout.NORTH);
			contentPanelBin.add(tablePane, BorderLayout.CENTER);
			
			c.add(contentPanelBin, BorderLayout.CENTER);

			topLabel.setText("<html>With this tool, you can select the number of bins, edit the interval min and max,<p>and choose the bin color.</html>");
			
			colorSchemeComboBoxBinned.addItemListener(this);
		
		}
	
		validate();
		repaint();

	}
	
	/**
	 * Sets the current state of this class.
	 * 
	 * @param	scheme	either "Continuous" or "Binned"
	 */
	public void setCurrentState(String scheme){
	
		this.scheme = scheme;
	
		schemeComboBox.removeItemListener(this);
		schemeComboBox.setSelectedItem(scheme);
		schemeComboBox.addItemListener(this);
	
		if(scheme.equals("Continuous")){
		
			vizRainbowPanel.setScheme(scheme);
			vizRainbowPanel.setRGB(parent.colorValues);	
			vizRainbowPanel.repaint();
		
			setFormatLayout(scheme);
			setSize(1000, 500);
			
			minField.setText(parent.minField.getText());
			maxField.setText(parent.maxField.getText());
			
			minSlider.setMinimum(getIntegerValue(parent.getMinValue()));
			minSlider.setMaximum(getIntegerValue(parent.getMaxValue()));
			minSlider.setValue(getIntegerValue(NumberLocalizer.valueOf(parent.minField.getText()).doubleValue()));
			
			maxSlider.setMinimum(getIntegerValue(parent.getMinValue()));
			maxSlider.setMaximum(getIntegerValue(parent.getMaxValue()));
			maxSlider.setValue(getIntegerValue(NumberLocalizer.valueOf(parent.maxField.getText()).doubleValue()));

			if(parent.includeValues){
			
				includeRadioButton.setSelected(true);
				excludeRadioButton.setSelected(false);
			
			}else{
			
				includeRadioButton.setSelected(true);
				excludeRadioButton.setSelected(false);
			
			}
			
		}else if(scheme.equals("Binned")){
			setFormatLayout(scheme);
			int numBin = 0;
			numBin = parent.binData.size();
			numBinModel.setValue(new Integer(numBin));
			table.setCurrentState(numBin);
			setSize(getPreferredSize());
		}		
		validate();
	
	}
	
	/**
	 * Gets the integer value.
	 *
	 * @param inputValue the input value
	 * @return the integer value
	 */
	private int getIntegerValue(double inputValue){
		int value = 0;
		double normValue = 0.0;
		normValue = setScale(inputValue
								, parent.getMaxValue()
								, parent.getMinValue());				
		value = (int)Math.round(normValue*1000);
		return value;
	}
    
	/**
	 * Sets the scale.
	 *
	 * @param value the value
	 * @param max the max
	 * @param min the min
	 * @return the double
	 */
	private double setScale(double value, double max, double min){
        double normValue = (1/(max-min))*(value-min);
        return normValue;
    }
   
	/**
	 * Gets the double value.
	 *
	 * @param sliderValue the slider value
	 * @return the double value
	 */
	private double getDoubleValue(int sliderValue){
		double value = 0.0;
		double normValue = sliderValue/1000.0;
		value = parent.getMinValue()
					+ (normValue*(parent.getMaxValue() 
					- parent.getMinValue()));
		return value;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent ie){

		if(ie.getSource()==colorSchemeComboBoxCont){
		
			if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Greyscale")){
			
				x0RSlider.setValue(100);
				x0GSlider.setValue(100);
				x0BSlider.setValue(100);
				aRSlider.setValue(100);
				aGSlider.setValue(100);
				aBSlider.setValue(100);
			
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Rainbow 1")){
			
				x0RSlider.setValue(80);
				x0GSlider.setValue(60);
				x0BSlider.setValue(20);
				aRSlider.setValue(50);
				aGSlider.setValue(40);
				aBSlider.setValue(30);
				
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Rainbow 2")){
				
				x0RSlider.setValue(44);
				x0GSlider.setValue(2);
				x0BSlider.setValue(100);
				aRSlider.setValue(25);
				aGSlider.setValue(32);
				aBSlider.setValue(37);
			
			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Purple Haze")){
			
				x0RSlider.setValue(100);
				x0GSlider.setValue(0);
				x0BSlider.setValue(100);
				aRSlider.setValue(84);
				aGSlider.setValue(0);
				aBSlider.setValue(84);

			}else if(((String)colorSchemeComboBoxCont.getSelectedItem()).equals("Fire")){
			
				x0RSlider.setValue(100);
				x0GSlider.setValue(97);
				x0BSlider.setValue(0);
				aRSlider.setValue(71);
				aGSlider.setValue(33);
				aBSlider.setValue(0);

			}
		
		}else if(ie.getSource()==colorSchemeComboBoxBinned){
			
			/*if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 1")){
				
				for(int i=0; i<numBinModel.getNumber().intValue(); i++){
				
					table.getModel().setValueAt(new Color(255-(i*40),0,0), i, 3);
				
				}
			
			}else if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 2")){
			
				for(int i=0; i<numBinModel.getNumber().intValue(); i++){
				
					table.getModel().setValueAt(new Color(0,255-(i*40),0), i, 3);
				
				}
			
			}else if(colorSchemeComboBoxBinned.getSelectedItem().toString().equals("Color Scheme 3")){
			
				for(int i=0; i<numBinModel.getNumber().intValue(); i++){
				
					table.getModel().setValueAt(new Color(0, 0, 255-(i*40)), i, 3);
				
				}
			
			}
		
			table.repaint();*/
			
		}else if(ie.getSource()==schemeComboBox){
		
			scheme = schemeComboBox.getSelectedItem().toString();
		
			if(scheme.equals("Continuous")){

				setCurrentState("Continuous");
			
			}else if(scheme.equals("Binned")){
				
				setCurrentState("Binned");
				
				int numBin = parent.binData.size();
					
				numBinModel.setValue(new Integer(numBin));

				table.setCurrentState(numBinModel.getNumber().intValue());
			
			}
		
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
	
		if(ae.getSource()==defaultButton){
		
			if(scheme.equals("Continuous")){
		
				minSlider.setValue(0);
				maxSlider.setValue(1000);
				
				x0RSlider.setValue(80);
				x0GSlider.setValue(60);
				x0BSlider.setValue(20);
				aRSlider.setValue(50);
				aGSlider.setValue(40);
				aBSlider.setValue(30);
				
				colorSchemeComboBoxCont.removeItemListener(this);
				colorSchemeComboBoxCont.setSelectedItem("Rainbow");
				colorSchemeComboBoxCont.addItemListener(this);
			
			}else if(scheme.equals("Binned")){
				
				colorSchemeComboBoxBinned.removeItemListener(this);
				colorSchemeComboBoxBinned.setSelectedItem("Color Scheme 1");
				colorSchemeComboBoxBinned.addItemListener(this);
				
				Vector<RowData> columnData = new Vector<RowData>();
				columnData.addElement(new RowData(1, 2, true, new Color (255,0,0)));
				columnData.addElement(new RowData(0, 1, true, new Color(195,0,0)));
				columnData.addElement(new RowData(-1, 0, true, new Color(0,0,195)));
				columnData.addElement(new RowData(-2, -1, true, new Color(0,0,255)));
			
				parent.binData = columnData;
				
				numBinModel.setValue(new Integer(4));
				table.setCurrentState(4);
				
			}
			
		}else if(ae.getSource()==applyButton){

				if(scheme.equals("Continuous")){
				
					if(goodContInterval()){
				
						parent.scheme = "Continuous";
					
						double[] tempArray = new double[6];
		
						tempArray[0] = vizRainbowPanel.getX0R();
						tempArray[1] = vizRainbowPanel.getX0G();
						tempArray[2] = vizRainbowPanel.getX0B();
						tempArray[3] = vizRainbowPanel.getAR();
						tempArray[4] = vizRainbowPanel.getAG();
						tempArray[5] = vizRainbowPanel.getAB();
					
						parent.colorValues = tempArray;
						
						parent.vizRainbowPanel.setScheme(scheme);
						parent.vizRainbowPanel.setRGB(tempArray);
							
						parent.minField.setText(minField.getText());
			    		parent.maxField.setText(maxField.getText());
			
						parent.includeValues = includeRadioButton.isSelected();
						parent.chartColorArray = parent.getChartColorArray(parent.typeComboBox.getSelectedIndex(), "Continuous");

						parent.vizRainbowPanel.repaint();
						parent.vizChartPanel.repaint();
					
					}else{
					
						String string = "Value minimum must be less than value maximum.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					
					}
					
				}else if(scheme.equals("Binned")){
				
					if(table.isEditing()){
					
						for(int i=0; i<table.getRowCount(); i++){
	
							for(int j=0; j<table.getColumnCount(); j++){
								
								table.getCellEditor(i, j).stopCellEditing();
								
							}
							
						}
					
					}
					
					if(goodBinIntervals(table.tableModel.getDataVector())){
					
						parent.scheme = "Binned";
						parent.binData = table.tableModel.getDataVector();
						
						parent.vizRainbowPanel.scheme = scheme;
						
						parent.maxField.setText(NumberFormats.getFormattedDer(((Double)(table.tableModel.getValueAt(0, 1))).doubleValue()));
						parent.minField.setText(NumberFormats.getFormattedDer(((Double)((Vector)(table.tableModel.getDataVector().elementAt(table.tableModel.getDataVector().size()-1))).elementAt(0)).doubleValue()));
						
						parent.chartColorArray = parent.getChartColorArray(parent.typeComboBox.getSelectedIndex(), "Binned");

						parent.vizRainbowPanel.repaint();
						parent.vizChartPanel.repaint();
							
					
					}else{
					
						String string = "Bin intervals are not entered correctly. Please choose a maximum greater than the minimum indicated for each bin.";
						GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
						dialog.setVisible(true);
					
					}
				
				}
				
		}else if(ae.getSource()==applyRangeButton){
		
			try{
		
				if(NumberLocalizer.valueOfWithException(minField.getText()).doubleValue()<NumberLocalizer.valueOfWithException(maxField.getText()).doubleValue()){
		
					minSlider.removeChangeListener(this);
					maxSlider.removeChangeListener(this);		
					minSlider.setValue(getIntegerValue(NumberLocalizer.valueOfWithException(minField.getText()).doubleValue()));
					maxSlider.setValue(getIntegerValue(NumberLocalizer.valueOfWithException(maxField.getText()).doubleValue()));
					minSlider.addChangeListener(this);
					maxSlider.addChangeListener(this);
					
				}else{
				
					String string = "Quantity minimum must be less than quantity maximum.";
				
					GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
					dialog.setVisible(true);
					
				}
			
			}catch(Exception nfe){
			
				String string = "Please enter numeric values for quantity maximum and minimum.";
				
				GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
				dialog.setVisible(true);
				
				nfe.printStackTrace();
			
			}
		
		}
	
	}

	/**
	 * Good cont interval.
	 *
	 * @return true, if successful
	 */
	private boolean goodContInterval(){
	
		boolean goodContInterval = true;
		
		double min = NumberLocalizer.valueOf(minField.getText()).doubleValue();
		double max = NumberLocalizer.valueOf(maxField.getText()).doubleValue();
		
		if(min>=max){goodContInterval = false;}
		
		return goodContInterval;
	
	}

	/**
	 * Good bin intervals.
	 *
	 * @param dataVector the data vector
	 * @return true, if successful
	 */
	private boolean goodBinIntervals(Vector dataVector){
	
		boolean goodBinIntervals = true;
		
		for(int i=0; i<dataVector.size(); i++){
			
			double min = ((Double)((Vector<?>)dataVector.elementAt(i)).elementAt(0)).doubleValue();
			double max = ((Double)((Vector<?>)dataVector.elementAt(i)).elementAt(1)).doubleValue();
		
			if(max<=min){goodBinIntervals = false;}

		}
		
		return goodBinIntervals;
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent ce){
	
		if(ce.getSource()==minSlider){
		
			minField.setText(NumberFormats.getFormattedDer(getDoubleValue(minSlider.getValue())));
		
		}else if(ce.getSource()==maxSlider){
		
			maxField.setText(NumberFormats.getFormattedDer(getDoubleValue(maxSlider.getValue())));
		
		}else if(ce.getSource()==x0RSlider
					|| ce.getSource()==x0GSlider
					|| ce.getSource()==x0BSlider
					|| ce.getSource()==aRSlider
					|| ce.getSource()==aGSlider
					|| ce.getSource()==aBSlider){
		
			x0RField.setText(String.valueOf(x0RSlider.getValue()/100.0));
			x0GField.setText(String.valueOf(x0GSlider.getValue()/100.0));
			x0BField.setText(String.valueOf(x0BSlider.getValue()/100.0));
			aRField.setText(String.valueOf(aRSlider.getValue()/100.0));
			aGField.setText(String.valueOf(aGSlider.getValue()/100.0));
			aBField.setText(String.valueOf(aBSlider.getValue()/100.0));
			
			vizRainbowPanel.setRGB(x0RSlider.getValue()/100.0
											, x0GSlider.getValue()/100.0
											, x0BSlider.getValue()/100.0
											, aRSlider.getValue()/100.0
											, aGSlider.getValue()/100.0
											, aBSlider.getValue()/100.0);
			
			vizRainbowPanel.repaint();
		
		}else if(ce.getSource()==numBinSpinner){

			table.setCurrentState(numBinModel.getNumber().intValue());
		
		}
	
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

}