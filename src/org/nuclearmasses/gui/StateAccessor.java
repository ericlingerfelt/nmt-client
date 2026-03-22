package org.nuclearmasses.gui;

/**
 * This interface allows the current state and data of an object to be updated 
 * and harvested by the object's central data structure.
 */
public interface StateAccessor {
	
	/**
	 * Sets the current state of this class from values in the central data structure.
	 */
	public void setCurrentState();
	
	/**
	 * Gets data from this class and assigns it to the central data structure.
	 *
	 * @return the current state
	 */
	public void getCurrentState();
}
