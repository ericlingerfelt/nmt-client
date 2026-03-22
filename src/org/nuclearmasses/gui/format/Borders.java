package org.nuclearmasses.gui.format;

import javax.swing.BorderFactory;
import javax.swing.border.*;

/**
 * This class is a convenience class for creating a border for a GUI component.
 */
public class Borders {
	
	/**
	 * Gets a formatted {@link Border} object.
	 * 
	 * @param	string	the name displayed with the border
	 * @return			the formatted border 
	 */
	public static Border getBorder(String string){
		Border blackline = BorderFactory.createLineBorder(java.awt.Color.black);
		TitledBorder title = BorderFactory.createTitledBorder(blackline, string);
		title.setTitleJustification(TitledBorder.CENTER);
		return title;
	}
}
