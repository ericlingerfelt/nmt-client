package org.nuclearmasses.gui.export.print;

import java.awt.Graphics;
import java.awt.print.Printable;
import java.awt.print.PageFormat;
import javax.swing.JPanel;

/**
 * This class enables a {@link JPanel} to be printed.
 */
public class PlotPrintable implements Printable{
	
	/** The panel. */
	private JPanel panel; 
	
	/**
	 * Class constructor.
	 *
	 * @param panel the {@link JPanel} whose graphics are to be printed
	 */
	public PlotPrintable(JPanel panel){
		this.panel = panel;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print(Graphics g, PageFormat pf, int pageIndex){
		if(pageIndex!=0){return NO_SUCH_PAGE;}
		panel.paint(g);
        return PAGE_EXISTS;	
	}
	
}