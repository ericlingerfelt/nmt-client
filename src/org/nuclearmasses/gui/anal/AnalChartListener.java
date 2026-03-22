package org.nuclearmasses.gui.anal;

/**
 * This interface enables updates to {@link AnalChartFrame} isotope range selection events.
 *
 * @see AnalChartEvent
 */
public interface AnalChartListener {
	
	/**
	 * Fired when a {@link AnalChartFrame} isotope range selection event occurs.
	 *
	 * @param zmin the zmin
	 * @param nmin the nmin
	 * @param zmax the zmax
	 * @param nmax the nmax
	 */
	public void updateFields(int zmin, int nmin, int zmax, int nmax);
	
	/**
	 * Fired to clear the isotope range fields.
	 */
	public void clearFields();
}
