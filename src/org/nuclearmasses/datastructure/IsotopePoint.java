package org.nuclearmasses.datastructure;

/**
 * This data structure class represents an isotope.
 */
public class IsotopePoint implements DataStructure, Comparable<IsotopePoint>{
	
	/** The n. */
	private int z, n;
	
	/**
	 * Class constructor.
	 */
	public IsotopePoint(){}
	
	/**
	 * Class constructor specifying z - proton number and n - neutron number.
	 *
	 * @param z the proton number of this isotope
	 * @param n the neutron number of this isotope
	 */
	public IsotopePoint(int z, int n){
		this.z = z;
		this.n = n;
	}
	
	/**
	 * Initializes all fields.
	 */
	public void initialize(){
		this.z = 0;
		this.n = 0;
	}
	
	/**
	 * Gets the value of z - proton number.
	 *
	 * @return 	the value of z - proton number
	 */
	public int getZ(){return z;}
	
	/**
	 * Sets the value of z - proton number.
	 *
	 * @param z the value of z - proton number
	 */
	public void setZ(int z){this.z = z;}
	
	/**
	 * Gets the value of n - neutron number.
	 *
	 * @return 	the value of n - neutron number
	 */
	public int getN(){return n;}
	
	/**
	 * Sets the value of n - neutron number.
	 *
	 * @param n the value of n - neutron number
	 */
	public void setN(int n){this.n = n;}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(IsotopePoint ip){
		if(this.getZ()!=ip.getZ()){
			return this.getZ()-ip.getZ();
		}
		return this.getN()-ip.getN();
	}
	
	/**
	 * Determines if an Object is considered equal to this IsotopePoint.
	 * 
	 * @param	object	the object to consider
	 * @return			true if the object is considered equal to this IsotopePoint
	 */
	public boolean equals(Object object){
		if(object instanceof IsotopePoint){
			IsotopePoint ids = (IsotopePoint)object;
			if(ids.getZ()==this.getZ() && ids.getN()==this.getN()){
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Gets a general hashcode for this IsotopePoint. The hashcode is simply the value of <code>n</code>;
	 * 
	 * @return			a general hashcode for this IsotopePoint
	 */
	public int hashCode(){return this.n;}
	
	/**
	 * Returns a String representation of this IsotopePoint.
	 * 
	 * @return		a String representation of this IsotopePoint
	 */
	public String toString(){
		if(getZ()==0){
			return MainDataStructure.getElementSymbol(getZ());
		}
		return (getZ() + getN()) + MainDataStructure.getElementSymbol(getZ());
	}
}
