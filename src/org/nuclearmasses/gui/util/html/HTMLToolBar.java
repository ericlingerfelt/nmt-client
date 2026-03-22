package org.nuclearmasses.gui.util.html;

import org.nuclearmasses.io.*;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.*;

/**
 * The Class HTMLToolBar creates the toolbar for the HTMLEditorPane.
 *
 * @author Eric J. Lingerfelt
 */
public class HTMLToolBar extends JToolBar{

	private Action cutAction = new DefaultEditorKit.CutAction();
	private Action copyAction = new DefaultEditorKit.CopyAction();
	private Action pasteAction = new DefaultEditorKit.PasteAction();
	private Action boldAction = new StyledEditorKit.BoldAction();
	private Action underlineAction = new StyledEditorKit.UnderlineAction();
	private Action italicAction = new StyledEditorKit.ItalicAction();
	private JButton cutButton, copyButton, pasteButton
						, boldButton, underButton, italicButton
						, subButton, supButton, strButton;
	private HTMLEditorPane pane;
	
	/**
	 * The Constructor.
	 */
	public HTMLToolBar(){
		
		super(JToolBar.HORIZONTAL);
		setFloatable(false);
		setBorderPainted(false);
		
		cutButton = getToolBarButton("Cut", cutAction, "cut.gif");
		copyButton = getToolBarButton("Copy", copyAction, "copy.gif");
		pasteButton = getToolBarButton("Paste", pasteAction, "paste.gif");
		boldButton = getToolBarButton("Bold", boldAction, "bold.gif");
		underButton = getToolBarButton("Underline", underlineAction, "under.gif");
		italicButton = getToolBarButton("Italic", italicAction, "italic.gif");
		subButton = getToolBarButton("Subscript", new SubscriptAction(), "subscr.gif");
		supButton = getToolBarButton("Superscript", new SuperscriptAction(), "superscr.gif");
		strButton = getToolBarButton("Strikethrough", new StrikeThroughAction(), "striketr.gif");
		
		add(cutButton);
		add(copyButton);
		add(pasteButton);
		addSeparator();
		add(boldButton);
		add(underButton);
		add(italicButton);
		addSeparator();
		add(subButton);
		add(supButton);
		add(strButton);
		
	}
	
	/**
	 * Sets the HTML editor pane.
	 *
	 * @param pane the new hTML editor pane
	 */
	public void setHTMLEditorPane(HTMLEditorPane pane){
		this.pane = pane;
	}
	
	/* (non-Javadoc)
	 * @see org.isotopesonline.gui.html.UnicodeDialogListener#insertUnicodeChar(java.lang.String)
	 */
	public void insertUnicodeChar(String sChar) throws IOException, BadLocationException, RuntimeException {
		int caretPos = pane.getCaretPosition();
		if(sChar != null)
		{
			pane.getDocument().insertString(caretPos, sChar, pane.getInputAttributes());
			pane.setCaretPosition(caretPos + 1);
		}
	}
	
	/**
	 * Gets a tool bar button.
	 *
	 * @param text the text
	 * @param a the a
	 * @param imageName the image name
	 * @return the tool bar button
	 */
	private JButton getToolBarButton(String text, Action a, String imageName){
		JButton button = new JButton();
		button.setAction(a);
		button.setToolTipText(text);
		button.setFocusable(false);
		button.setIcon(new ImageIcon(FileImport.getFileByte("images/" + imageName)));
		button.setText("");
		return button;
	}
	
}

/**
 * The SubscriptAction class is a StyledEditorKit.StyledTextAction 
 * used for handling subscript text events.
 * 
 * @author Eric J. Lingerfelt
 */
class SubscriptAction extends StyledEditorKit.StyledTextAction{
	
	/**
	 * The Constructor.
	 */
	public SubscriptAction(){
		super(StyleConstants.Subscript.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		JEditorPane editor = getEditor(ae);
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean subscript = (StyleConstants.isSubscript(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setSubscript(sas, subscript);
			setCharacterAttributes(editor, sas, false);
		}
	}
}

/**
 * The SuperscriptAction class is a StyledEditorKit.StyledTextAction 
 * used for handling superscript text events.
 * 
 * @author Eric J. Lingerfelt
 */
class SuperscriptAction extends StyledEditorKit.StyledTextAction{

	/**
	 * The Constructor.
	 */
	public SuperscriptAction(){
		super(StyleConstants.Superscript.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		JEditorPane editor = getEditor(ae);
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean superscript = (StyleConstants.isSuperscript(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setSuperscript(sas, superscript);
			setCharacterAttributes(editor, sas, false);
		}			
	}
}

/**
 * The StrikeThroughAction class is a StyledEditorKit.StyledTextAction 
 * used for handling strikethrough text events.
 * 
 * @author Eric J. Lingerfelt
 */
class StrikeThroughAction extends StyledEditorKit.StyledTextAction{

	/**
	 * The Constructor.
	 */
	public StrikeThroughAction(){
		super(StyleConstants.StrikeThrough.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		JEditorPane editor = getEditor(ae);
		if (editor != null) {
			StyledEditorKit kit = getStyledEditorKit(editor);
			MutableAttributeSet attr = kit.getInputAttributes();
			boolean strikeThrough = (StyleConstants.isStrikeThrough(attr)) ? false : true;
			SimpleAttributeSet sas = new SimpleAttributeSet();
			StyleConstants.setStrikeThrough(sas, strikeThrough);
			setCharacterAttributes(editor, sas, false);
		}			
	}
}
