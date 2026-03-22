package org.nuclearmasses.gui.format;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

/**
 * This class is a JEditorPane pretending to be a JLabel. It enables label like text to 
 * word wrap in a GUI when the width of the container is increased or decreased.
 */
public class WordWrapLabel extends JEditorPane{

	/** The is bold. */
	private boolean isBold;
	
	/**
	 * Class constructor.
	 */
	public WordWrapLabel(){
		setEditable(false);
		setBorder(null);
		setEditorKit(new HTMLEditorKit());
	}
	
	/**
	 * Class constructor specifying whether or not to make the text bold.
	 * 
	 * @param	isBold	if true then bold
	 */
	public WordWrapLabel(boolean isBold){
		this();
		this.isBold = isBold;
	}
	
	/**
	 * Sets the text of this class to a <i>sans-serif</i> font with size = 3. 
	 * If <code>isBold</cod> is set to true, then all text is bold.
	 * If an HTML table cell is detected, its font and size is set correctly as well.
	 * 
	 * @param	text	the new text
	 */
	public void setText(String text){
		super.setText(text);
		if(isBold){
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\"><b>"));
		}else{
			super.setText(getText().replaceAll("<body>", "<body><font face=\"sans-serif\" size=\"3\">"));
		}
		if(text.indexOf("<td>")!=-1){
			super.setText(getText().replaceAll("<td>", "<td><font face=\"sans-serif\" size=\"3\">"));
		}
	}
	
}
