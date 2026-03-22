package org.nuclearmasses.enums;

/**
 * This enum represents both types of mass datasets.
 */
public enum MassModelField {

	/** A theoretical dataset. */
	THEORY("Theoretical"), 
	
	/** An experimental dataset. */
	EXPERIMENT("Experimental"); 
	
	/** The string. */
	private String string;
	
	/**
	 * Class constructor.
	 *
	 * @param string a user friendly string for this MassModelField
	 */
	MassModelField(String string){
		this.string = string;
	}
	
	/**
	 * Returns a String representation of this MassModelField.
	 * 
	 * @return		a String representation of this MassModelField
	 */
	public String toString(){return string;}
	
}
