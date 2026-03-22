package org.nuclearmasses.gui.export.print;

import java.awt.print.*;
import javax.swing.JFrame;
import org.nuclearmasses.gui.dialogs.GeneralDialog;

/**
 * This class sends a plot to the printer.
 */
public class PlotPrinter{

	/**
	 * Prints the plot to the printer.
	 *
	 * @param printable a {@link Printable} object containing the plot
	 * @param frame 		a {@link JFrame} to open an error dialog over if plotting fails
	 */
	public static void print(Printable printable, JFrame frame){
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Paper paper = new Paper();
		PageFormat pageFormat = new PageFormat();
		paper.setImageableArea(0, 0, 612, 792);	
		pageFormat.setPaper(paper);
		printerJob.setPrintable(printable, pageFormat);
		
		if(printerJob.printDialog()){
			try{
				printerJob.print();
			}catch(PrinterException pe){
				String string = "An error has occurred while attempting to print plot.";
				GeneralDialog dialog = new GeneralDialog(frame, string, "Printing Error!");
				dialog.setVisible(true);
			}	
		}
	}
}