package org.nuclearmasses.datastructure;

/**
 * This data structure class simply contains a mass excess 
 * value in MeV and its associated uncertainty value.
 */
public class MassPoint implements DataStructure{

	/** The uncer. */
	private double value, uncer;
	
	/**
	 * Class constructor.
	 */
	public MassPoint(){}
	
	/**
	 * Class constructor specifying the mass excess value and the uncertainty value.
	 * 
	 * @param	value	the mass excess value in MeV
	 * @param	uncer	the uncertainty value
	 */
	public MassPoint(double value, double uncer){
		this.value = value;
		this.uncer = uncer;
	}
	
	/**
	 * Initializes all fields.
	 */
	public void initialize(){
		this.value = 0;
		this.uncer = 0;
	}
	
	/**
	 * Gets the mass excess value in MeV.
	 * 
	 * @return		the mass excess value in MeV
	 */
	public double getValue(){return value;}
	
	/**
	 * Sets the mass excess value in MeV.
	 * 
	 * @param	value	the mass excess value in MeV
	 */
	public void setValue(double value){this.value = value;}
	
	/**
	 * Gets the uncertainty value.
	 * 
	 * @return		the uncertainty value
	 */
	public double getUncer(){return uncer;}
	
	/**
	 * Sets the uncertainty value.
	 * 
	 * @param	uncer	the uncertainty value
	 */
	public void setUncer(double uncer){this.uncer = uncer;}
	
}
