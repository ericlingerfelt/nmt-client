package org.nuclearmasses.gui.export.print;

import java.awt.*;
import javax.swing.*;
import java.awt.print.*;

/**
 * This class is a {@link FormattedHTMLEditorPane} with multipage printing capability.
 */
public class PrintableEditorPane extends org.nuclearmasses.gui.util.html.FormattedHTMLEditorPane implements Printable{
	
	/* (non-Javadoc)
	 * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
	 */
	public int print (Graphics g, PageFormat pf, int pageIndex) throws PrinterException{
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor (Color.black);
		RepaintManager.currentManager(this).setDoubleBufferingEnabled(false);
		Dimension d = this.getSize();
		double panelWidth = d.width;
		double panelHeight = d.height;
		double pageWidth = pf.getImageableWidth();
		double pageHeight = pf.getImageableHeight();
		double scale = pageWidth / panelWidth;
		int totalNumPages = (int)Math.ceil(scale*panelHeight/pageHeight);

		// Check for empty pages
		if(pageIndex>=totalNumPages){
			return Printable.NO_SUCH_PAGE;
		}

		g2.translate(pf.getImageableX(), pf.getImageableY());
		g2.translate(0f, -pageIndex * pageHeight);
		g2.scale(scale, scale);
		this.paint(g2);
		
		return Printable.PAGE_EXISTS;
	}
	
	/**
	 * Prints the contents of this class.
	 * 
	 * @return	true if printing succeeds
	 */
	public boolean print(){
		
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if(job.printDialog()){
			try{
				job.print();
				return true;
			}catch(Exception ex){
				System.out.println(ex);
				return false;
			}
		}
		
		return true;
	}
	
}
