package org.nuclearmasses.datastructure;

import java.awt.*; 
import java.util.*;

/**
 * This class is the data structure for the Mass Dataset Analyzer.
 */
public class AnalDataStructure extends CGICallDataStructure{
	
	/**
	 * Enum representing the path taken to select nuclides for analysis.
	 */
	public enum SelectionMethod{
		
		/** Select matching isotopes by mass range. */
		MASS, 
		
		/** Select matching isotopes by z min/max and n min/max. */
		ZN, 
		
		/** Select matching isotopes by chart of the nuclides GUI. */
		CHART, 
		
		/** Select all matching isotopes. */
		ALL}
	
	/** The method. */
	private SelectionMethod method;
	
	/** The ip max. */
	private IsotopePoint ipMin, ipMax;
	
	/** The a point. */
	private Point aPoint;
	
	/** The ip map. */
	private TreeMap<Integer, ArrayList<IsotopePoint>> ipMap; 
	
	/** The ip list. */
	private ArrayList<IsotopePoint> ipList;
	
	/** The num ip. */
	private int numIP;
	
	/**
	 * Class constructor.
	 */
	public AnalDataStructure(){
		super.initialize();
		this.initialize();
	}
	
	/**
	 * Initializes all fields and calls <code>super.initialize</code>.
	 */
	public void initialize(){
		super.initialize();
		setMethod(SelectionMethod.ALL);
		setIPMin(null);
		setIPMax(null);
		setAPoint(null);
		setIPMap(null);
		setIPList(null);
		setNumIP(0);
	}
	
	/**
	 * Gets the current selection method.
	 *
	 * @return 		one of the values available from {@link SelectionMethod}
	 */
	public SelectionMethod getMethod(){return method;}
	
	/**
	 * Sets the current selection method.
	 *
	 * @param method one of the values available from {@link SelectionMethod}
	 */
	public void setMethod(SelectionMethod method){this.method = method;}
	
	/**
	 * Gets the minimum {@link IsotopePoint} for the selected isotope range
	 * if <code>method</code> is equal to <code>SelectionMethod.ZN</code>.
	 * 
	 * @return			the minimum IsotopePoint
	 */
	public IsotopePoint getIPMin(){return ipMin;}
	
	/**
	 * Sets the minimum {@link IsotopePoint} for the selected isotope range
	 * if <code>method</code> is equal to <code>SelectionMethod.ZN</code>.
	 * 
	 * @param	ipMin	the minimum IsotopePoint
	 */
	public void setIPMin(IsotopePoint ipMin){this.ipMin = ipMin;}
	
	/**
	 * Gets the maximum {@link IsotopePoint} for the selected isotope range
	 * if <code>method</code> is equal to <code>SelectionMethod.ZN</code>.
	 * 
	 * @return			the maximum IsotopePoint
	 */
	public IsotopePoint getIPMax(){return ipMax;}
	
	/**
	 * Sets the maximum {@link IsotopePoint} for the selected isotope range
	 * if <code>method</code> is equal to <code>SelectionMethod.ZN</code>.
	 * 
	 * @param	ipMax	the maximum IsotopePoint
	 */
	public void setIPMax(IsotopePoint ipMax){this.ipMax = ipMax;}
	
	/**
	 * Gets the number of selected {@link IsotopePoint} objects.
	 *
	 * @return 		the number of selected IsotopePoint objects
	 */
	public int getNumIP(){return numIP;}
	
	/**
	 * Sets the number of selected {@link IsotopePoint} objects.
	 *
	 * @param numIP the number of selected IsotopePoint objects
	 */
	public void setNumIP(int numIP){this.numIP = numIP;}
	
	/**
	 * Gets a {@link Point} containing the minimum and maximum values for
	 * mass number for the selected isotope range if <code>method</code> 
	 * is equal to <code>SelectionMethod.MASS</code>.
	 * 
	 * @return			a Point containing the minimum and maximum values for mass number
	 */
	public Point getAPoint(){return aPoint;}
	
	/**
	 * Sets a {@link Point} containing the minimum and maximum values for
	 * mass number for the selected isotope range if <code>method</code> 
	 * is equal to <code>SelectionMethod.MASS</code>.
	 * 
	 * @param	aPoint	a Point containing the minimum and maximum values for mass number
	 */
	public void setAPoint(Point aPoint){this.aPoint = aPoint;}
	
	/**
	 * Gets a {@link TreeMap} where the keys are z - proton number and the values are.
	 *
	 * @return 		a TreeMap where the keys are z - proton number and the values are
	 * ArrayLists of IsotopePoints with the proton number z.
	 * {@link ArrayList}s of {@link IsotopePoint} objects with the proton number z.
	 */
	public TreeMap<Integer, ArrayList<IsotopePoint>> getIPMap(){return ipMap;}
	
	/**
	 * Gets a {@link TreeMap} where the keys are z - proton number and the values are.
	 *
	 * @param ipMap a TreeMap where the keys are z - proton number and the values are
	 * ArrayLists of IsotopePoints with the proton number z.
	 * {@link ArrayList}s of {@link IsotopePoint} objects with the proton number z.
	 */
	public void setIPMap(TreeMap<Integer, ArrayList<IsotopePoint>> ipMap){this.ipMap = ipMap;}
	
	/**
	 * Gets an {@link ArrayList} of selected {@link IsotopePoint} objects if <code>method</code> 
	 * is equal to <code>SelectionMethod.CHART</code>. 
	 * 
	 * @return 			an ArrayList of selected IsotopePoint objects
	 */
	public ArrayList<IsotopePoint> getIPList(){return ipList;}
	
	/**
	 * Sets an {@link ArrayList} of selected {@link IsotopePoint} objects if <code>method</code> 
	 * is equal to <code>SelectionMethod.CHART</code>. 
	 * 
	 * @param	ipList 	an ArrayList of selected IsotopePoint objects
	 */
	public void setIPList(ArrayList<IsotopePoint> ipList){this.ipList = ipList;}
	
	/**
	 * Clone set.
	 *
	 * @param setOld the set old
	 * @return the set
	 */
	private Set<IsotopePoint> cloneSet(Set<IsotopePoint> setOld){
		Set<IsotopePoint> set = new HashSet<IsotopePoint>();
		for(IsotopePoint ip: setOld){
			set.add(new IsotopePoint(ip.getZ(), ip.getN()));
		}
		return set;
	}
	
	/**
	 * Creates and sorts the <code>ipMap</code> local variable. <code>ipMap</code> 
	 * contains all of the {@link IsotopePoint} objects that are in common with each 
	 * selected {@link MassModelDataStructure}. It is a {@link TreeMap} where the keys
	 * are z - proton number and the values are {@link ArrayList}s of IsotopePoints with 
	 * the proton number z.
	 */
	public void createAndOrderIPMap(){
		ipMap = new TreeMap<Integer, ArrayList<IsotopePoint>>();
		Iterator<MassModelDataStructure> itr = this.getModelMapSelected().values().iterator();
		MassModelDataStructure mmds1 = itr.next();
		Set<IsotopePoint> set1 = cloneSet(mmds1.getMassMap().keySet());
		while(itr.hasNext()){
			MassModelDataStructure mmds2 = itr.next();
			Set<IsotopePoint> set2 = cloneSet(mmds2.getMassMap().keySet());
			set1.retainAll(set2);
		}
		for(IsotopePoint ip: set1){
			if(!ipMap.containsKey(ip.getZ())){
				ipMap.put(ip.getZ(), new ArrayList<IsotopePoint>());
			}
			ipMap.get(ip.getZ()).add(ip);
		}
		
		for(ArrayList<IsotopePoint> list: ipMap.values()){
			Collections.sort(list);
		}
		
	}
	
}