package org.nuclearmasses.gui.viz.chart;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class creates the color scale for the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
 */
public class VizRainbowPanel extends JPanel{
	
	/** The a b. */
	private double x0R, x0G, x0B, aR, aG, aB;
	
	/** The scheme. */
	public String scheme;
	
	/** The parent. */
	private VizChartFrame parent;
	
	/**
	 * Class constructor.
	 *
	 * @param parent a reference to the {@link VizChartFrame} containing this class
	 */
	public VizRainbowPanel(VizChartFrame parent){
		this.parent = parent;
		this.setSize(100, 50);
		this.setScheme("Binned");
	}	
	
	/**
	 * Sets the RGB position and width values.
	 * 
	 * @param	x0R		the position of the Red color band
	 * @param	x0G		the position of the Green color band
	 * @param	x0B		the position of the Blue color band
	 * @param	aR		the width of the Red color band
	 * @param	aG		the width of the Green color band
	 * @param	aB		the width of the Blue color band
	 */
	public void setRGB(double x0R, double x0G, double x0B
						, double aR, double aG, double aB){
	
		this.x0R = x0R;
		this.x0G = x0G;
		this.x0B = x0B;
		this.aR = aR;
		this.aG = aG;
		this.aB = aB;
	
	}
	
	/**
	 * Sets the RGB position and width values with an array of doubles.
	 * 
	 * @param	tempArray		tempArray[0] = the position of the Red color band
	 *							tempArray[1] = the position of the Green color band
	 *							tempArray[2] = the position of the Blue color band
	 *							tempArray[3] = the width of the Red color band
	 *							tempArray[4] = the width of the Green color band
	 *							tempArray[5] = the width of the Blue color band
	 */
	public void setRGB(double[] tempArray){
	
		this.x0R = tempArray[0];
		this.x0G = tempArray[1];
		this.x0B = tempArray[2];
		this.aR = tempArray[3];
		this.aG = tempArray[4];
		this.aB = tempArray[5];
	
	}
	
	/**
	 * Gets the rGB.
	 *
	 * @param x the x
	 * @return the rGB
	 */
	private Color getRGB(double x){
    	if(x>=1.0){x = 1.0;}
    	if(x<=0.0){x = 0.0;}
    	
        int red = (int)(255*Math.exp(-(x-x0R)*(x-x0R)/aR/aR));
        int green = (int)(255*Math.exp(-(x-x0G)*(x-x0G)/aG/aG));
        int blue = (int)(255*Math.exp(-(x-x0B)*(x-x0B)/aB/aB));
        
        return new Color(red,green,blue);   
    }
	
	/**
	 * Gets the position of the Red color band.
	 * 
	 * @return	the position of the Red color band
	 */
	public double getX0R(){return this.x0R;}
	
	/**
	 * Gets the position of the Green color band.
	 * 
	 * @return	the position of the Green color band
	 */
	public double getX0G(){return this.x0G;}
	
	/**
	 * Gets the position of the Blue color band.
	 * 
	 * @return	the position of the Blue color band
	 */
	public double getX0B(){return this.x0B;}
	
	/**
	 * Gets the width of the Red color band.
	 * 
	 * @return	the width of the Red color band
	 */
	public double getAR(){return this.aR;}
	
	/**
	 * Gets the width of the Green color band.
	 * 
	 * @return	the width of the Green color band
	 */
	public double getAG(){return this.aG;}
	
	/**
	 * Gets the width of the Blue color band.
	 * 
	 * @return	the width of the Blue color band
	 */
	public double getAB(){return this.aB;}
	
	/**
	 * Sets the current color scale scheme.
	 * 
	 * @param	scheme	either "Binned" or "Continuous"
	 */
	public void setScheme(String scheme){this.scheme = scheme;}
	
	/**
	 * Gets the current color scale scheme.
	 * 
	 * @return	either "Binned" or "Continuous"
	 */
	public String getScheme(){return this.scheme;}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){

        Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		RenderingHints hints = new RenderingHints(
										RenderingHints.KEY_ANTIALIASING
										, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		
		if(scheme.equals("Continuous")){
		
			for(int i=0; i<100; i++){
				g2.setColor(getRGB(i/100.0));
				g2.drawLine(0, 100-i, 50, 100-i);
			}
			
			g2.setFont(Fonts.medTitleFont);
			g2.setColor(Color.black);
			g2.drawString("Max", 5, 18);
			g2.setColor(Color.white);
			g2.drawString("Min", 7, 92);
			
		}else if(scheme.equals("Binned")){
		
			g2.setFont(Fonts.realSmallFont);
			Vector binDataVector = new Vector();
			binDataVector = parent.binData;
			
			double minTotal = ((Double)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).doubleValue();
			double maxTotal = ((Double)((Vector)binDataVector.elementAt(0)).elementAt(1)).doubleValue();

			Color tempColor = new Color(0, 0, 0);
			boolean noShowBinToggle = true;

			for(int i=0; i<100; i++){
			
				double mag = (((maxTotal-minTotal)/100.0)*i) + minTotal;

				binFound:
				for(int j=0; j<binDataVector.size(); j++){
	
					double min = ((Double)((Vector)binDataVector.elementAt(j)).elementAt(0)).doubleValue();
					double max = ((Double)((Vector)binDataVector.elementAt(j)).elementAt(1)).doubleValue();
					
					if(mag>=min && mag<max){
					
						if(((Boolean)((Vector)binDataVector.elementAt(j)).elementAt(2)).booleanValue()){
					
							tempColor = (Color)((Vector)binDataVector.elementAt(j)).elementAt(3);
							break binFound;
							
						}
						if(noShowBinToggle){
							noShowBinToggle = !noShowBinToggle;
							tempColor = Color.white;
						}else{
							noShowBinToggle = !noShowBinToggle;
							tempColor = Color.red;
						}

						break binFound;
					}
				}

				g2.setColor(tempColor);
				g2.drawLine(0, 100-i, 50, 100-i);
				
			}
			
			for(int i=1; i<binDataVector.size(); i++){
			
				g2.setColor(Color.black);
				double mag = ((Double)((Vector)binDataVector.elementAt(i)).elementAt(1)).doubleValue();
				int lineIndex = (int)((100.0/(maxTotal-minTotal))*(mag-minTotal));
				g2.drawLine(24, 100-lineIndex, 50, 100-lineIndex);
				g2.drawString(String.valueOf(mag), 2, 100 - lineIndex + 4);

			}		
		}
    }
}
