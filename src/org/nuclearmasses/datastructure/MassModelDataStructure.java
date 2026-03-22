package org.nuclearmasses.datastructure;

import java.awt.Point;
import java.util.*;
import org.nuclearmasses.enums.*;

/**
 * This data structure class represents a mass dataset.
 */
public class MassModelDataStructure implements DataStructure, Comparable<MassModelDataStructure>{

	/** The mass map. */
	private TreeMap<IsotopePoint, MassPoint> massMap;
	
	/** The author. */
	private String desc, ref, name, author;
	
	/** The type. */
	private MassModelType type;
	
	/** The index. */
	private int index;
	
	/** The field. */
	private MassModelField field;
	
	/** The modification date. */
	private Calendar creationDate, modificationDate;
	
	/** The rms avg. */
	private double rmsAvg;
	
	/** The ZN array. */
	private Point[] ZNArray;
	
	/** The uncer array. */
	private double[] massArray, uncerArray;
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(MassModelDataStructure mmds){
		return this.name.compareToIgnoreCase(mmds.name);
	}
	
	/**
	 * Class constructor.
	 */
	public MassModelDataStructure(){
		initialize();
	}
	
	/**
	 * Initializes all fields.
	 */
	public void initialize(){
		setMassMap(null);
		setDesc("");
		setRef("");
		setName("");
		setAuthor("");
		setType(null);
		setIndex(-1);
		setField(null);
		setCreationDate(null);
		setModificationDate(null);
		setRMSAvg(0.0);
		setZNArray(null);
		setMassArray(null);
		setUncerArray(null);
	}
	
	/**
	 * Initializes and fills the arrays required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 */
	public void createArrays(){
		ZNArray = new Point[massMap.keySet().size()];
		massArray = new double[massMap.keySet().size()];
		uncerArray = new double[massMap.keySet().size()];
		Iterator<IsotopePoint> itr = massMap.keySet().iterator();
		int counter = 0;
		while(itr.hasNext()){
			IsotopePoint ip = itr.next();
			ZNArray[counter] = new Point(ip.getZ(), ip.getN());
			massArray[counter] = massMap.get(ip).getValue();
			uncerArray[counter] = massMap.get(ip).getUncer();
			counter++;
		}
	}
	
	/**
	 * Gets the RMS avg for this mass dataset versus a reference mass dataset.
	 * 
	 * @return			the RMS avg for this mass dataset versus a reference mass dataset
	 */
	public double getRMSAvg(){return rmsAvg;}
	
	/**
	 * Sets the RMS avg for this mass dataset versus a reference mass dataset.
	 * 
	 * @param	rmsAvg	the RMS avg for this mass dataset versus a reference mass dataset
	 */
	public void setRMSAvg(double rmsAvg){this.rmsAvg = rmsAvg;}
	
	
	/**
	 * Gets the creation date for this mass dataset as a {@link Calendar} object.
	 * 
	 * @return			the creation date for this mass dataset as a Calendar object
	 */
	public Calendar getCreationDate(){return creationDate;}
	
	/**
	 * Sets the creation date for this mass dataset as a {@link Calendar} object.
	 * 
	 * @param	creationDate	the creation date for this mass dataset as a Calendar object
	 */
	public void setCreationDate(Calendar creationDate){this.creationDate = creationDate;}
	
	/**
	 * Gets the last modification date for this mass dataset as a {@link Calendar} object.
	 * 
	 * @return			the last modification date for this mass dataset as a Calendar object
	 */
	public Calendar getModificationDate(){return modificationDate;}
	
	/**
	 * Sets the last modification date for this mass dataset as a {@link Calendar} object.
	 * 
	 * @param	modificationDate	the last modification date for this mass dataset as a Calendar object
	 */
	public void setModificationDate(Calendar modificationDate){this.modificationDate = modificationDate;}
	
	/**
	 * Gets a {@link TreeMap} of {@link MassPoint} objects keyed on {@link IsotopePoint}.
	 * 
	 * @return			a TreeMap of MassPoint objects keyed on {@link IsotopePoint}
	 */
	public TreeMap<IsotopePoint, MassPoint> getMassMap(){return massMap;}
	
	/**
	 * Sets a {@link TreeMap} of {@link MassPoint} objects keyed on {@link IsotopePoint}.
	 * 
	 * @param	massMap	a TreeMap of MassPoint objects keyed on {@link IsotopePoint}
	 */
	public void setMassMap(TreeMap<IsotopePoint, MassPoint> massMap){this.massMap = massMap;}
	
	/**
	 * Gets the description for this mass dataset.
	 *
	 * @return 		the description for this mass dataset
	 */
	public String getDesc(){return desc;}
	
	/**
	 * Sets the description for this mass dataset.
	 *
	 * @param desc the new desc
	 */
	public void setDesc(String desc){this.desc = desc;}
	
	/**
	 * Gets the author for this mass dataset.
	 *
	 * @return 		the description for this mass dataset
	 */
	public String getAuthor(){return author;}
	
	/**
	 * Sets the author for this mass dataset.
	 *
	 * @param author the new author
	 */
	public void setAuthor(String author){this.author = author;}
	
	/**
	 * Gets the reference for this mass dataset.
	 *
	 * @return 		the description for this mass dataset
	 */
	public String getRef(){return ref;}
	
	/**
	 * Sets the reference for this mass dataset.
	 *
	 * @param ref the new ref
	 */
	public void setRef(String ref){this.ref = ref;}
	
	/**
	 * Gets the name for this mass dataset.
	 *
	 * @return 		the description for this mass dataset
	 */
	public String getName(){return name;}
	
	/**
	 * Sets the name for this mass dataset.
	 *
	 * @param name the new name
	 */
	public void setName(String name){this.name = name;}
	
	/**
	 * Gets the {@link MassModelType} for this mass dataset.
	 * 
	 * @return			the MassModelType for this mass dataset
	 */
	public MassModelType getType(){return type;}
	
	/**
	 * Sets the {@link MassModelType} for this mass dataset.
	 * 
	 * @param	type	the MassModelType for this mass dataset
	 */
	public void setType(MassModelType type){this.type = type;}
	
	/**
	 * Gets the {@link MassModelField} for this mass dataset.
	 * 
	 * @return			the MassModelField for this mass dataset
	 */
	public MassModelField getField(){return field;}
	
	/**
	 * Sets the {@link MassModelField} for this mass dataset.
	 * 
	 * @param	field	the MassModelField for this mass dataset
	 */
	public void setField(MassModelField field){this.field = field;}
	
	/**
	 * Gets the unique index for this mass dataset.
	 * 
	 * @return			the unique index for this mass dataset
	 */
	public int getIndex(){return index;}
	
	/**
	 * Sets the unique index for this mass dataset.
	 * 
	 * @param	index	the unique index for this mass dataset
	 */
	public void setIndex(int index){this.index = index;}
	
	/**
	 * Gets a {@link Point} array containing Z,N pairs as required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 * 
	 * @return				Point array containing Z,N pairs
	 */
	public Point[] getZNArray(){return ZNArray;}
	
	/**
	 * Sets a {@link Point} array containing Z,N pairs as required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 * 
	 * @param	newZNArray	Point array containing Z,N pairs
	 */
	public void setZNArray(Point[] newZNArray){ZNArray = newZNArray;}
	
	/**
	 * Gets a double array containing mass excess values as required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 * 
	 * @return					double array mass excess values
	 */
	public double[] getMassArray(){return massArray;}
	
	/**
	 * Sets a double array containing mass excess values as required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 * 
	 * @param	newMassArray	double array mass excess values
	 */
	public void setMassArray(double[] newMassArray){massArray = newMassArray;}
	
	/**
	 * Gets a double array containing uncertainty values as required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 * 
	 * @return					double array uncertainty values
	 */
	public double[] getUncerArray(){return uncerArray;}
	
	/**
	 * Sets a double array containing uncertainty values as required by the legacy code used in 
	 * the <i>Mass Dataset Plotting Interfaces</i> and 
	 * the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
	 * 
	 * @param	newUncerArray	double array uncertainty values
	 */
	public void setUncerArray(double[] newUncerArray){uncerArray = newUncerArray;}
	
	/**
	 * Determines if an Object is considered equal to this mass dataset.
	 * 
	 * @param	obj		the object to consider
	 * @return			true if the object is considered equal to this IsotopePoint
	 */
	public boolean equals(Object obj){
		if(!(obj instanceof MassModelDataStructure)){
			return false;
		}
		MassModelDataStructure mmds = (MassModelDataStructure)obj;
		return index==mmds.getIndex();
	}
	
	/**
	 * Returns a String representation of this mass dataset.
	 * 
	 * @return		a String representation of this mass dataset
	 */
	public String toString(){return name;}
}
