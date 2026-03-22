package org.nuclearmasses.enums;

/**
 * This enum represents the groups of accessibility of mass datasets.
 */
public enum MassModelType {
	
	/** The public folder. */
	PUBLIC("Public"), 
	
	/** The shared folder. */
	SHARED("Shared"), 
	
	/** The user folder. */
	USER("User"), 
	
	/** All folders. */
	ALL("All");
	
	/** The string. */
	private String string;
	
	/**
	 * Class constructor.
	 *
	 * @param string a user friendly string for this MassModelType
	 */
	MassModelType(String string){
		this.string = string;
	}
	
	/**
	 * Returns a String representation of this MassModelType.
	 * 
	 * @return		a String representation of this MassModelType
	 */
	public String toString(){return string;}
}
