package org.nuclearmasses.gui.viz.chart;

import java.awt.Color;
import java.util.Vector;

/**
 * This subclass of {@link Vector} contains elements corresponding to a row 
 * of the binned color settings table.
 */
public class RowData extends Vector<Object>{
	
	/**
	 * Class constructor.
	 *
	 * @param min 	the minimum magnitude for this bin
	 * @param max 	the maximum magnitude for this bin
	 * @param show if true display this bin on the nuclide chart
	 * @param color the bin's color
	 */
	public RowData(double min, double max, boolean show, Color color){
		addElement(new Double(min));
		addElement(new Double(max));
		addElement(new Boolean(show));
		addElement(color);
	}
}
