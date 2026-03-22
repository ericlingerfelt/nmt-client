package org.nuclearmasses.gui.util.html;

import org.nuclearmasses.gui.format.Fonts;

import java.awt.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import java.io.*;
import javax.swing.event.*;

/**
 * The Class HTMLEditorPane creates a JTextPane suitable for HTML formatting. 
 *
 * @author Eric J. Lingerfelt
 */
public class HTMLEditorPane extends JTextPane implements CaretListener{
	
	private HTMLToolBar toolBar;
	private JTextArea area;

	/**
	 * The Constructor.
	 */
	public HTMLEditorPane(){
		setContentType("text/html");	
		addCaretListener(this);
		putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		getDocument().putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		setFont(Fonts.textFont);
		area = new JTextArea();
	}
	
	/**
	 * Sets the tool bar.
	 *
	 * @param toolBar the new tool bar
	 */
	public void setToolBar(HTMLToolBar toolBar){
		this.toolBar = toolBar;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.text.JTextComponent#paste()
	 */
	public void paste(){
		String plainContents = getClipboard();
		area.setText("");
		area.setText(plainContents);
		area.selectAll();
		area.copy();
		super.paste();
	}
	
	/**
 	 * Returns the clipboard's text.
 	 *
 	 * @return the clipboard's text
 	 */
 	public String getClipboard() {
		 Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		 try {
			 if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				 String text = (String)t.getTransferData(DataFlavor.stringFlavor);
				 return text;
			 }
		 } catch (UnsupportedFlavorException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 return null;
     }
	
	/**
	 * Returns text without HTML formatting.
	 *
	 * @return text without HTML formatting
	 */
	public String getCleanText(){
		String string = getText();
		string = string.replace("<p style=\"margin-top: 0\">", "");
		string = string.replace("</p>", "");
		string = string.replace("\r", "");
		int begin = string.indexOf("<body>") + 6;
		int end = string.indexOf("</body>");
		string = string.substring(begin, end).trim().replaceAll("\n", "<br/>");
		if(string.indexOf("<br/>")!=-1){
			String[] array = string.split("<br/>");
			string = "";
			for(int i=0; i<array.length; i++){
				String tempString = array[i].trim();
				if(!tempString.equals("")){
					string += tempString + "<br/>";
				}
			}
			string = string.substring(0, string.lastIndexOf("<br/>"));
		}
		return string.trim();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
	 */
	public void caretUpdate(CaretEvent e){
		toolBar.setHTMLEditorPane(this);
	}
	
}
