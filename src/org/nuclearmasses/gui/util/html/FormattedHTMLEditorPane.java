package org.nuclearmasses.gui.util.html;

import java.awt.Color;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

/**
 * The Class FormattedHTMLEditorPane is a JEditorPane used for displaying HTML formatted text.
 *
 * @author Eric J. Lingerfelt
 */
public class FormattedHTMLEditorPane extends JEditorPane{
	
	/**
	 * The Constructor.
	 */
	public FormattedHTMLEditorPane(){
		setEditable(false);
		setEditorKit(new HTMLEditorKit());
		setBackground(Color.white);
	} 
	
	/* (non-Javadoc)
	 * @see javax.swing.JEditorPane#setText(java.lang.String)
	 */
	public void setText(String string){
		if(string.indexOf("<body>")!=-1){
			string = string.replaceAll("<body>", "<style type=\"text/css\">body { font-size: 12pt; font-family: sans-serif }</style>");
		}
		super.setText(string);
	}
	
}
