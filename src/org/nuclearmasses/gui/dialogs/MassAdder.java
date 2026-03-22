package org.nuclearmasses.gui.dialogs;

/**
 * This interface is used to notify the implementer when a mass has been added to a potential mass dataset.
 */
public interface MassAdder{
	
	/**
	 * Adds a new mass data point to a potential mass dataset.
	 * 
	 * @param	z		the proton number z
	 * @param	n		the neutron number n
	 * @param	mass	the mass excess in MeV
	 * @param	uncer	the uncertainty
	 */
	public void addMass(int z, int n, double mass, double uncer);
}
