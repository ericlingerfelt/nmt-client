package org.nuclearmasses.gui.man;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.*;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.*;
import info.clearthought.layout.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.gui.export.print.*;
import org.nuclearmasses.gui.export.save.*;
import org.nuclearmasses.gui.export.copy.*;

import java.awt.*;

/**
 * This class is Step 2 of 2 for the Mass Dataset Manager > Mass Dataset Information tool.
 */
public class ManInfo2Panel extends JPanel implements ActionListener, StateAccessor{

	/** The ds. */
	private ManDataStructure ds;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private ManFrame frame;
	
	/** The text pane. */
	private PrintableEditorPane textPane;
	
	/** The print button. */
	private JButton saveButtonText, saveButtonHTML, copyButton, printButton;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure}
	 * @param ds 	a reference to the {@link ManDataStructure}
	 * @param frame a reference to the {@link ManFrame} containing this panel
	 */
	public ManInfo2Panel(MainDataStructure mds, ManDataStructure ds, ManFrame frame){
		
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		
		double gap = 20;
		double[] column = {TableLayoutConstants.FILL};
		double[] row = {gap, TableLayoutConstants.FILL, gap, TableLayoutConstants.PREFERRED, gap};

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
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButtonText);
		buttonPanel.add(saveButtonHTML);
		buttonPanel.add(copyButton);
		buttonPanel.add(printButton);

		add(sp, "0, 1, f, f");
		add(buttonPanel, "0, 3, c, c");
	
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
		}
	}
	
	/**
	 * Gets the text string.
	 *
	 * @return the text string
	 */
	private String getTextString(){
		String string = "";
		string += "Mass Dataset Information Report\n\n";
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			MassModelDataStructure mmds = ds.getModelMapSelected().get(itr.next());
			string += "Mass Dataset\t" + mmds.toString() + "\n";
			string += "Creation Date\t" + new SimpleDateFormat().format(mmds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "\n";
			string += "Author\t" + mmds.getAuthor() + "\n";
			string += "Reference\t" + mmds.getRef() + "\n";
			string += "Description\t" + mmds.getDesc() + "\n";
			string += "Type\t" + mmds.getField().toString() + "\n";
			string += "\n\n";
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
		string += "<b>Mass Dataset Information Report</b><br><br>";
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			MassModelDataStructure mmds = ds.getModelMapSelected().get(itr.next());
			string += "<table border=\"1\"><tr><td><b>Mass Dataset</b></td><td>" + mmds.toString() + "</td></tr>";
			string += "<tr><td><b>Creation Date</b></td><td>" + new SimpleDateFormat().format(mmds.getCreationDate().getTime(), new StringBuffer(), new FieldPosition(0)) + "</td></tr>";
			string += "<tr><td><b>Author</b></td><td>" + mmds.getAuthor() + "</td></tr>";
			string += "<tr><td><b>Reference</b></td><td>" + mmds.getRef() + "</td></tr>";
			string += "<tr><td><b>Description</b></td><td>" + mmds.getDesc() + "</td></tr>";
			string += "<tr><td><b>Type</b></td><td>" + mmds.getField().toString() + "</td></tr>";
			string += "</td></tr></table><br>";
		}
		return string;
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
