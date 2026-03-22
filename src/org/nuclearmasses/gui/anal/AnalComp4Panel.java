package org.nuclearmasses.gui.anal;

import javax.swing.*;
import info.clearthought.layout.*;
import java.util.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;

/**
 * This class is Step 4 of 5 for the Mass Dataset Analyzer > Mass Dataset RMS Comparator.
 */
public class AnalComp4Panel extends JPanel implements StateAccessor{

	/** The ds. */
	private AnalDataStructure ds;
	
	/** The range label. */
	private WordWrapLabel refLabel, modelLabel, rangeLabel;
	
	/**
	 * Class constructor.
	 *
	 * @param ds 	a reference to the {@link AnalDataStructure}
	 */
	public AnalComp4Panel(AnalDataStructure ds){
		
		this.ds = ds;
		
		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, gap};
		
		setLayout(new TableLayout(column, row));
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please review the options you have selected below. "
							+ "Click <i>Continue</i> to compare avarages of RMS values "
							+ "for the selected mass datasets versus the selected reference mass dataset. "
							+ "Please note that only isotopes that appear in all selected mass datasets "
							+ "will be used in the calculation.</html>");
		
		refLabel = new WordWrapLabel(false);
		modelLabel = new WordWrapLabel(false);
		rangeLabel = new WordWrapLabel(false);
		
		add(topLabel, "1, 1, f, c");
		add(refLabel, "1, 3, l, c");
		add(modelLabel, "1, 5, l, c");
		add(rangeLabel, "1, 7, l, c");
		
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){
		refLabel.setText("Reference Mass Dataset : " + ds.getModelMapSelected().get(ds.getRefIndex()).toString());
		String string = "Selected Mass Datasets : ";
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			int index = itr.next();
			if(index!=ds.getRefIndex()){
				MassModelDataStructure mmds = ds.getModelMapSelected().get(index);
				string += mmds.toString() + ", ";
			}
		}
		string = string.substring(0, string.lastIndexOf(","));
		modelLabel.setText(string);
		switch(ds.getMethod()){
			case ALL:
				string = "Selected Isotope Range : ";
				string += "All available isotopes";
				break;
			case ZN:
			case CHART:
				string = "Selected Isotope Range [Z,N]: ";
				string += "[" + ds.getIPMin().getZ() + ", " + ds.getIPMin().getN()
							+ "] to [" + ds.getIPMax().getZ() + ", " + ds.getIPMax().getN()
							+ "]";
				break;
			case MASS:
				string = "Selected Isotope Range : ";
				string += "From A = " + (int)ds.getAPoint().getX() + " to A = " + (int)ds.getAPoint().getY();
				break;
		}
		rangeLabel.setText(string);
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		MassModelDataStructure mmdsRef = ds.getModelMapSelected().get(ds.getRefIndex());
		Iterator<Integer> itr = ds.getModelMapSelected().keySet().iterator();
		while(itr.hasNext()){
			Integer index = itr.next();
			if(index!=ds.getRefIndex()){
				calculateRMSAvg(mmdsRef, ds.getModelMapSelected().get(index));
			}
		}
	}
	
	/**
	 * Calculate rms avg.
	 *
	 * @param mmdsRef the mmds ref
	 * @param mmds the mmds
	 */
	private void calculateRMSAvg(MassModelDataStructure mmdsRef, MassModelDataStructure mmds){
		int num = 0;
		double sumSqrd = 0.0;
		Iterator<ArrayList<IsotopePoint>> itr = ds.getIPMap().values().iterator();
		switch(ds.getMethod()){
			case ALL:
				while(itr.hasNext()){
					for(IsotopePoint ip: itr.next()){
						sumSqrd += Math.pow(mmds.getMassMap().get(ip).getValue() - mmdsRef.getMassMap().get(ip).getValue(), 2);
						num++;
					}
				}
				break;
			case ZN:
			case CHART:
				int zmin = ds.getIPMin().getZ();
				int nmin = ds.getIPMin().getN();
				int zmax = ds.getIPMax().getZ();
				int nmax = ds.getIPMax().getN();
				while(itr.hasNext()){
					for(IsotopePoint ip: itr.next()){
						if(ip.getZ()<=zmax && ip.getZ()>=zmin && ip.getN()>=nmin && ip.getN()<=nmax){
							sumSqrd += Math.pow(mmds.getMassMap().get(ip).getValue() - mmdsRef.getMassMap().get(ip).getValue(), 2);
							num++;
						}
					}
				}
				break;
			case MASS:
				int amin = (int)ds.getAPoint().getX();
				int amax = (int)ds.getAPoint().getY();
				while(itr.hasNext()){
					for(IsotopePoint ip: itr.next()){
						if((ip.getZ()+ip.getN())>=amin && (ip.getZ()+ip.getN())<=amax){
							sumSqrd += Math.pow(mmds.getMassMap().get(ip).getValue() - mmdsRef.getMassMap().get(ip).getValue(), 2);
							num++;
						}
					}
				}
				break;
		}
		ds.setNumIP(num);
		mmds.setRMSAvg(Math.sqrt(sumSqrd/num));
	}
	
}
