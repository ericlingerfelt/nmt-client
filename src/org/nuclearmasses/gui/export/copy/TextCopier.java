package org.nuclearmasses.gui.export.copy;

import java.awt.*;
import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * This class allows a String to be copied to the System clipboard.
 */
public class TextCopier extends JPanel{

	/**
	 * Copies String to the System clipboard.
	 *
	 * @param string the String to copy
	 */
	public static void copyText(String string){
        StringSelection text = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
     	clipboard.setContents(text, text);
    }
}