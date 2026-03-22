package org.nuclearmasses.gui.anal;

import javax.swing.*;

import info.clearthought.layout.*;
import java.util.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.*;
import java.awt.*;
import org.nuclearmasses.gui.dialogs.GeneralDialog;
import org.nuclearmasses.gui.export.print.*;
import org.nuclearmasses.gui.export.save.*;
import org.nuclearmasses.gui.export.copy.*;

/**
 * This class is Step 5 of 5 for the Mass Dataset Analyzer > Mass Dataset RMS Comparator.
 */
public class AnalComp5Panel extends JPanel implements ActionListener, StateAccessor{

	/** The ds. */
	private AnalDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private AnalFrame frame;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The calc button. */
	private JButton saveButtonText, saveButtonHTML, copyButton
						, printButton, vizButton, calcButton;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param ds 	a reference to the {@link AnalDataStructure}
	 * @param frame a reference to the {@link AnalFrame} containing this class
	 */
	public AnalComp5Panel(MainDataStructure mds, AnalDataStructure ds,  AnalFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};

		setLayout(new TableLayout(column, row));
		
		textPane = new PrintableEditorPane();
		textPane.setEditable(false);
		textPane.setEditorKit(new HTMLEditorKit());
		textPane.setBackground(Color.white);
		
		JScrollPane sp = new JScrollPane(textPane
								, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
								, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		saveButtonText = new JButton("Save as Text File");
		saveButtonText.setFont(Fonts.buttonFont);
		saveButtonText.addActionListener(this);

		saveButtonHTML = new JButton("Save as HTML File");
		saveButtonHTML.setFont(Fonts.buttonFont);
		saveButtonHTML.addActionListener(this);
		
		printButton = new JButton("Print");
		printButton.setFont(Fonts.buttonFont);
		printButton.addActionListener(this);
		
		copyButton = new JButton("Copy");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this);
		
		vizButton = new JButton("Open Mass Datasets in Visualizer");
		vizButton.setFont(Fonts.buttonFont);
		vizButton.addActionListener(this);
		
		calcButton = new JButton("Explore Results in RMS Calculator");
		calcButton.setFont(Fonts.buttonFont);
		calcButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);

		add(sp, "0, 1, f, f");
		add(buttonPanel, "0, 3, c, c");
		add(calcButton, "0, 5, c, c");
		add(vizButton, "0, 7, c, c");
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==saveButtonText){
			TextSaver.saveText(getTextString(), frame, mds);
		}else if(ae.getSource()==saveButtonHTML){
			TextSaver.saveTextHTML(textPane.getText(), frame, mds);
		}else if(ae.getSource()==copyButton){
			TextCopier.copyText(getTextString());
		}else if(ae.getSource()==printButton){
			textPane.print();
		}else if(ae.getSource()==vizButton){
			frame.openVizTool();
		}else if(ae.getSource()==calcButton){
			String string = "You will now be moved away from the RMS Comparator, "
								+ " and your current selection will be made available in the RMS Calculator.";
			GeneralDialog dialog = new GeneralDialog(frame, string, "Attention!");
			dialog.setVisible(true);
			frame.gotoCalcTool();
		}
	}
	
	/**
	 * Gets the text string.
	 *
	 * @return the text string
	 */
	private String getTextString(){
		String string = "";
		string += "Mass Dataset RMS Averages\n";
		string += "Reference Mass Dataset\t" + ds.getModelMapSelected().get(ds.getRefIndex()).toString() + "\n";
		string += "Number of Isotopes Used\t" + String.valueOf(ds.getNumIP()) + "\n";
		string += "Mass Dataset\tRMS Average\n";
		TreeMap<Double, MassModelDataStructure> orderedMap = getOrderedMap(); 
		Iterator<Double> itr = orderedMap.keySet().iterator();
		while(itr.hasNext()){
			Double value = itr.next();
			MassModelDataStructure mmds = orderedMap.get(value);
			string += mmds.toString() + "\t" + NumberFormats.getFormattedNumber(mmds.getRMSAvg()) + "\n";
		}
		return getPlainTextFromHTML(string);
	}
	
	/**
	 * Gets the info report.
	 *
	 * @return the info report
	 */
	private String getInfoReport(){
		String string = "";
		string += "<html><body>";
		string += "<b>Mass Dataset RMS Averages</b><br><br>";
		string += "<table border=\"1\"><tr><td><b>Reference Mass Dataset</b></td><td>" + ds.getModelMapSelected().get(ds.getRefIndex()).toString() + "</td></tr>";
		string += "<tr><td><b>Number of Isotopes Used</b></td><td>" + String.valueOf(ds.getNumIP()) + "</td></tr>";
		string += "<tr><td><b>Mass Dataset</b></td><td><b>RMS Average</b></td></tr>";
		TreeMap<Double, MassModelDataStructure> orderedMap = getOrderedMap(); 
		Iterator<Double> itr = orderedMap.keySet().iterator();
		while(itr.hasNext()){
			Double value = itr.next();
			MassModelDataStructure mmds = orderedMap.get(value);
			string += "<tr><td>" + mmds.toString() + "</td><td>" + NumberFormats.getFormattedNumber(mmds.getRMSAvg()) + "</td></tr>";
		}
		string += "</table>";
		return string;
	}
	
	/**
	 * Gets the ordered map.
	 *
	 * @return the ordered map
	 */
	private TreeMap<Double, MassModelDataStructure> getOrderedMap(){
		TreeMap<Double, MassModelDataStructure> orderedMap = new TreeMap<Double, MassModelDataStructure>();
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			int index = itr.next();
			if(index!=ds.getRefIndex()){
				MassModelDataStructure mmds = ds.getModelMapSelected().get(index);
				orderedMap.put(mmds.getRMSAvg(), mmds);
			}
		}
		return orderedMap;
	}
	
	/**
	 * Gets the plain text from html.
	 *
	 * @param text the text
	 * @return the plain text from html
	 */
	private String getPlainTextFromHTML(String text){
		String string = text;
		string = string.replace("<html>", "");
		string = string.replace("</html>", "");
		string = string.replace("<head>", "");
		string = string.replace("</head>", "");
		string = string.replace("<body>", "");
		string = string.replace("</body>", "");
		string = string.replace("<b>", "");
		string = string.replace("</b>", "");
		string = string.replace("<u>", "");
		string = string.replace("</u>", "");
		string = string.replace("<i>", "");
		string = string.replace("</i>", "");
		string = string.replace("<sub>", "");
		string = string.replace("</sub>", "");
		string = string.replace("<sup>", "");
		string = string.replace("</sup>", "");
		string = string.replace("<strike>", "");
		string = string.replace("</strike>", "");
		string = string.replace("<br>", "\n");
		string = string.replace("<p>", "\n");
		string = string.trim();
		return string;
	} 
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		textPane.setText(getInfoReport());
		textPane.setCaretPosition(0);
	}

	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){}
}

