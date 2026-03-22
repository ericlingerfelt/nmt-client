package org.nuclearmasses.gui.viz.chart;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.datastructure.*;

/**
 * This class creates a graphics context which can be printed.
 */
public class VizChartPrintPanel extends JPanel{
	
    /** The xoffset. */
    int xoffset = 58;  
    
    /** The yoffset. */
    int yoffset = 35;
    
    /** The box width. */
    int boxWidth = 29;
    
    /** The box height. */
    int boxHeight = 29;
    
    /** The zmax. */
    int zmax = 0;       
    
    /** The nmax. */
    int nmax = 0; 
    
    /** The zmin. */
    int zmin = 0;
    
    /** The nmin. */
    int nmin = 0; 
    
    /** The proton number. */
    int protonNumber=0;   
    
    /** The neutron number. */
    int neutronNumber=0; 
    
    /** The width. */
    int width;           
    
    /** The height. */
    int height;   
            
    /** The xmax. */
    int xmax;                    
    
    /** The ymax. */
    int ymax;   

	/** The ZN map array. */
	Point[] ZNMapArray;

	/** The max drip n. */
	int[] maxDripN;
	
	/** The min drip n. */
	int[] minDripN;
	
	/** The min z drip. */
	int[] minZDrip;

	/** The n magic array. */
	int[] nMagicArray = {20, 28, 50, 82, 126, 184, 228};
	
	/** The z magic array. */
	int[] zMagicArray = {20, 28, 50, 82, 114, 126};

    /** The select color. */
    Color selectColor = new Color(153,102,153);
    
    /** The frame color. */
    Color frameColor = Color.white;
    
    /** The non select color. */
    Color nonSelectColor = new Color(0,0,180);
    
    /** The mouse over color. */
    Color mouseOverColor = new Color(0,0,120);
    
    /** The iso label color. */
    Color isoLabelColor = Color.white;
    
    /** The init abund color. */
    Color initAbundColor = new Color(230,185,0);

    /** The real small font. */
    Font realSmallFont = new Font("SanSerif", Font.PLAIN, 10);
    
    /** The real small font metrics. */
    FontMetrics realSmallFontMetrics = getFontMetrics(realSmallFont);
    
    /** The big font. */
    Font bigFont = new Font("SanSerif", Font.PLAIN, 16);
    
    /** The big font metrics. */
    FontMetrics bigFontMetrics = getFontMetrics(bigFont);
    
    /** The really big font. */
    Font reallyBigFont = new Font("SanSerif", Font.PLAIN, 20);
    
    /** The really big font metrics. */
    FontMetrics reallyBigFontMetrics = getFontMetrics(reallyBigFont);
    
    /** The water mark font. */
    Font waterMarkFont = new Font("SanSerif", Font.PLAIN, 40);
    
    /** The water mark font metrics. */
    FontMetrics waterMarkFontMetrics = getFontMetrics(waterMarkFont);
    
    /** The viktor. */
    Vector viktor;
    
	/** The type. */
	int type;
    
    /** The quantity. */
    int quantity;
    
    /** The scheme. */
    String scheme;
    
    /** The color array. */
    Color[] colorArray;
    
    /** The title scale factor. */
    double titleScaleFactor;
    
    /** The legend scale factor. */
    double legendScaleFactor;
    
    /** The legend trans. */
    Point legendTrans;
    
    /** The use white text. */
    boolean useWhiteText;
    
    /** The parent. */
    private VizChartFrame parent;
    
    /**
     * Class constructor.
     *
     * @param useWhiteText if true, then white text on bleck background
     * @param parent 		a reference to the {@link VizChartFrame} calling this class
     */
    public VizChartPrintPanel(boolean useWhiteText, VizChartFrame parent){
    
    	this.parent = parent;
    
    	setCurrentState(parent.vizChartPanel.type
    						, parent.vizRainbowPanel.getScheme()
    						, useWhiteText);
 
    }

	/**
	 * Sets the current state.
	 *
	 * @param type the type
	 * @param scheme the scheme
	 * @param useWhiteText the use white text
	 */
	private void setCurrentState(int type, String scheme, boolean useWhiteText){

		this.type = type;
		this.scheme = scheme;
		this.useWhiteText = useWhiteText;
		
		if(type==2 || type==3){

			ZNMapArray = new Point[parent.pointVector.size()];
	
			for(int i=0; i<ZNMapArray.length; i++){
			
				ZNMapArray[i] = (Point)(parent.pointVector.elementAt(i));
			
			}
			
			viktor = parent.pointVector;
		
		}else if(type==0){
		
			
			
			ZNMapArray = parent.mmds.getZNArray();
		
			viktor = new Vector();
			
			for(int i=0; i<ZNMapArray.length; i++){
			
				viktor.addElement(ZNMapArray[i]);
			
			}
			
		
		}else if(type==1){
		
			
			ZNMapArray = parent.mmdsRef.getZNArray();
		
			viktor = new Vector();
			
			for(int i=0; i<ZNMapArray.length; i++){
			
				viktor.addElement(ZNMapArray[i]);
			
			}
		
		}
	

		nmax = getNmax();
		nmin = getNmin();
		zmax = getZmax();
		zmin = getZmin();

		minDripN = getMinDripN(zmax, zmin);
		maxDripN = getMaxDripN(zmax, zmin);
		minZDrip = getMinZDrip(nmax, nmin);
        
    	double scale = parent.zoomSlider.getValue()/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
        
    	width = (boxWidth*(nmax+1));
        height = (boxHeight*(zmax+1));
        
        xmax = (xoffset + width);
        ymax = (yoffset + height);
    
    	setSize(xmax+xoffset, ymax+yoffset);
    			
		setPreferredSize(getSize());
		
		titleScaleFactor = getTitleScaleFactor();
		legendTrans = getLegendTrans();
		legendScaleFactor = getLegendScaleFactor();
		
		repaint();
		
	}
	
	/**
	 * Gets the nmax.
	 *
	 * @return the nmax
	 */
	private int getNmax(){
	
		int max = 0;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			max = (int)Math.max(max, ZNMapArray[i].getY());
		
		}

		return max;
	
	}
	
	/**
	 * Gets the nmin.
	 *
	 * @return the nmin
	 */
	private int getNmin(){
	
		int min = 1000;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			min = (int)Math.min(min, ZNMapArray[i].getY());
		
		}

		return min;
	
	}
	
	/**
	 * Gets the zmax.
	 *
	 * @return the zmax
	 */
	private int getZmax(){
	
		int max = 0;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			max = (int)Math.max(max, ZNMapArray[i].getX());
		
		}

		return max;
	
	}
	
	/**
	 * Gets the zmin.
	 *
	 * @return the zmin
	 */
	private int getZmin(){
	
		int min = 1000;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			min = (int)Math.min(min, ZNMapArray[i].getX());
		
		}

		return min;
	
	}
	
	/**
	 * Gets the min drip n.
	 *
	 * @param max the max
	 * @param min the min
	 * @return the min drip n
	 */
	private int[] getMinDripN(int max, int min){
	
		int[] tempArray = new int[max-min+1];
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i] = 3000;
		
		}
		
		int currentZ = -1;
		int currentN = -1;
		
		int counter = 0;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			currentZ = (int)ZNMapArray[i].getX();
			
			currentN = (int)ZNMapArray[i].getY();
			
			if(currentZ==(counter+min)){
			
				tempArray[counter] = Math.min(currentN, tempArray[counter]);
			
			}else{
			
				counter++;
			
				tempArray[counter] = Math.min(currentN, tempArray[counter]);
			
			}
			
		}
		
		return tempArray;
	
	}
	
	/**
	 * Gets the max drip n.
	 *
	 * @param max the max
	 * @param min the min
	 * @return the max drip n
	 */
	private int[] getMaxDripN(int max, int min){
	
		int[] tempArray = new int[max-min+1];
		
		int currentZ = -1;
		int currentN = -1;
		
		int counter = 0;
		
		for(int i=0; i<ZNMapArray.length; i++){
		
			currentZ = (int)ZNMapArray[i].getX();
			
			currentN = (int)ZNMapArray[i].getY();
			
			if(currentZ==(counter+min)){
			
				tempArray[counter] = Math.max(currentN, tempArray[counter]);
			
			}else{
			
				counter++;
			
				tempArray[counter] = Math.max(currentN, tempArray[counter]);
			
			}

		}
		
		return tempArray;
	
	}
	
	/**
	 * Do i belong.
	 *
	 * @param P the p
	 * @param N the n
	 * @return true, if successful
	 */
	boolean doIBelong(int P, int N){
    	
        boolean include = false;
        
        if(P>=zmin && P<=zmax){
        	
            if(N>=minDripN[P-zmin] && N<=maxDripN[P-zmin]){
            	
                include = true;
                
            }
        }
        
        return include;
        
    }
	
	/**
	 * Gets the min z drip.
	 *
	 * @param max the max
	 * @param min the min
	 * @return the min z drip
	 */
	public int[] getMinZDrip(int max, int min){
	
		int[] tempArray = new int[max+1];
		
		for(int i=0; i<tempArray.length; i++){
		
			tempArray[i] = -1;
		
		}
		
		for(int n=nmin; n<max+1; n++){
			
            for(int z=zmin; z<zmax+1; z++){

				if(doIBelong(z, n)){

	                tempArray[n] = z;
	                break;
	                
	            }

            }
            
        }
		
		return tempArray;
	
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){

		Graphics2D g2 = (Graphics2D)g;

        super.paintComponent(g2); 	

		RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		RenderingHints hintsRender = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		
		g2.setRenderingHints(hintsText);
		g2.addRenderingHints(hintsRender);

		if(useWhiteText){
		
			g2.setColor(Color.black);
		
		}else{
			
			g2.setColor(Color.white);
		
		}
		g2.fillRect(0, 0, (int)getPreferredSize().getWidth(), (int)getPreferredSize().getHeight());

		double scale = parent.zoomSlider.getValue()/100.0;
		boxHeight = (int)(29.0*scale);
		boxWidth = (int)(29.0*scale);
		
		int smallFontSize = (int)(11.0*scale);
		int tinyFontSize = (int)(8.0*scale);
		
		Font smallFont = new Font("SanSerif", Font.PLAIN, smallFontSize);
	    FontMetrics smallFontMetrics = getFontMetrics(smallFont);
	    Font tinyFont = new Font("SanSerif", Font.PLAIN, tinyFontSize);
	    FontMetrics tinyFontMetrics = getFontMetrics(tinyFont);
		
		if(parent.showMagicCheckBox.isSelected()){
    		g2.setStroke(new BasicStroke(2));
    		g2.setColor(Color.red);
    		
    		for(int i=0; i<nMagicArray.length; i++){
    		
    			g2.drawLine(xoffset+nMagicArray[i]*boxWidth, 0, xoffset+nMagicArray[i]*boxWidth, (int)getSize().getHeight());
    			g2.drawLine((xoffset+nMagicArray[i]*boxWidth)+boxWidth, 0, (xoffset+nMagicArray[i]*boxWidth)+boxWidth, (int)getSize().getHeight());
    		
    		}
    		
    		for(int i=0; i<zMagicArray.length; i++){	
    		
    			g2.drawLine(0, yoffset+(zmax-zMagicArray[i])*boxHeight, (int)getSize().getWidth(), yoffset+(zmax-zMagicArray[i])*boxHeight);
    			g2.drawLine(0, (yoffset+(zmax-zMagicArray[i])*boxHeight)+boxHeight, (int)getSize().getWidth(), (yoffset+(zmax-zMagicArray[i])*boxHeight)+boxHeight);
    		
    		}
    	
    	}
		
		colorArray = parent.chartColorArray;
		
        for(int i=0; i<ZNMapArray.length; i++){
        	
        	int z = (int)ZNMapArray[i].getX();
        	int n = (int)ZNMapArray[i].getY();

			g2.setStroke(new BasicStroke(1));

			if(colorArray[i]!=null){

				g2.setColor(colorArray[i]);
			
			}else{
				
				if(useWhiteText){
	        
		        	g2.setColor(Color.black);
		        
		       	}else{
		       		
		       		g2.setColor(frameColor);
		       	
		       	}
			
			}

	        g2.fillRect(xoffset+n*boxWidth,
	                   yoffset+(zmax-z)*boxHeight,
	                   boxWidth,boxHeight);
	        
	        if(parent.zoomSlider.getValue()>=50){
	        
		        if(useWhiteText){
	        
		        	g2.setColor(frameColor);
		        
		       	}else{
		       		
		       		g2.setColor(Color.black);
		       	
		       	}

		        g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
	                   
	       	}
	       	
	       	if(useWhiteText){
	        
	        	g2.setColor(isoLabelColor);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
        
	        if(parent.zoomSlider.getValue()>=75){
	        	
		        if(parent.showLabelsCheckBox.isSelected()){
    
    				String tempS = MainDataStructure.getElementSymbol(z);
			        String tempS2 = String.valueOf(z+n);
			        
			        int wid = (realSmallFontMetrics.stringWidth(tempS)
			                  + tinyFontMetrics.stringWidth(tempS2));
			                  
			        int xzero = (xoffset+n*boxWidth+boxWidth/2-wid/2);
			        
			        int yzero = (yoffset+(zmax-z+1)*boxHeight-boxHeight/2+1);
			        
			        g2.setFont(tinyFont);
			        
					if(useWhiteText){
			        
			        	g2.setColor(isoLabelColor);
			      
			       	}else{
			       		
			       		g2.setColor(Color.black);
			       	
			       	}
		
					if(z>=2){
		
			        	g2.drawString(tempS2,xzero,yzero);
			        	
			        	xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(realSmallFont);
				        g2.drawString(tempS,xzero,yzero);   
				        
				    }else if(z==1 && n==0){
				   
				   		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString("p",xzero-2,yzero-2);
			        
			       	}else if(z==1 && n==1){
				   
				   		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString("d",xzero-2,yzero-2);
			        
			       	}else if(z==1 && n==2){
				   
				   		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString("t",xzero,yzero-2);
			        
			       	}else if(z==0){
			       	
			       		xzero += tinyFontMetrics.stringWidth(tempS2);
				        yzero += 5;
				        
				        g2.setFont(smallFont);
			       	
			       		g2.drawString(tempS,xzero-2,yzero-2);
			       	
			       	}
			       	
				}
			       
			}
    
        }
       	
       	if(parent.showRProcessCheckBox.isSelected()){
       	
       		for(int i=0; i<parent.rProcessArray.size(); i++){
       		
       			int z = (int)((Point)parent.rProcessArray.elementAt(i)).getX();
       			int n = (int)((Point)parent.rProcessArray.elementAt(i)).getY();
       		
       			g2.setColor(Color.gray);
       			
       			g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
       		
       		}
       	
       	}
       	
        if(useWhiteText){
	        
        	g2.setColor(isoLabelColor);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
    	
	   	if(parent.zoomSlider.getValue()>=75){ 	
	   
	        // Labels for vertical axis

	        g2.setFont(smallFont);
	        
	        if(useWhiteText){
	        
	        	g2.setColor(isoLabelColor);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	        
	        for(int z=zmin; z<=zmax; z++){
		           
	           String tempS = MainDataStructure.getElementSymbol(z);
	           
	           int ds = (minDripN[z-zmin]*boxWidth);   // Inset to drip line
	           g2.drawString(tempS,
	                            xoffset -8 + ds -smallFontMetrics.stringWidth(tempS),
	                            yoffset + (zmax-z)*boxHeight + boxHeight/2
	                            + smallFontMetrics.getHeight()/2 -3);
	       		
	        }
	        
	        // Labels for horizontal axis
	
	        g2.setFont(smallFont);
	        if(useWhiteText){
	        
	        	g2.setColor(isoLabelColor);
	        
	       	}else{
	       		
	       		g2.setColor(Color.black);
	       	
	       	}
	        
	        for(int n=nmin; n<nmax+1; n++){
	        	
	           String tempS = String.valueOf(n);
	           
	           if(minZDrip[n]!=-1){
	           
		           g2.drawString(tempS,
		                        xoffset+n*boxWidth+boxWidth/2
		                        -smallFontMetrics.stringWidth(tempS)/2 + 1,
		                        yoffset+height + 17 - minZDrip[n]*boxHeight);
	                        
	           }
	                        
	        }
        
    	}
    	
    	if(parent.showStableCheckBox.isSelected()){
    
    		for(int i=0; i<ZNMapArray.length; i++){
        	
	        	int z = (int)ZNMapArray[i].getX();
	        	int n = (int)ZNMapArray[i].getY();
	
				g2.setStroke(new BasicStroke(1));
    
	        	if(parent.stableArray.contains(new Point(z, n))){
	        	
	        		g2.setColor(Color.red);
	        		
	        		g2.drawRect(xoffset+n*boxWidth,
	                   yoffset+(zmax-z)*boxHeight,
	                   boxWidth,boxHeight);
	        		
	        	}
	        	
	        }
    	
    	}
    	
    	//Start labels and legend////////////////////////////////////////////////////////////////////////
    	
    	g2.setRenderingHints(hintsText);
    	
    	if(titleScaleFactor<1.0){
		
			g2.scale(titleScaleFactor, titleScaleFactor);
		
		}
		
		g2.translate(50, 50);
		
		if(useWhiteText){
	        
        	g2.setColor(isoLabelColor);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
		g2.setFont(reallyBigFont);
		
		int yLabel = 0;
		
		switch(type){
		
			case 0:
				
				g2.drawString("Mass Dataset : " + parent.mmds.toString(), 0, yLabel);
				
				break;
				
			case 1:
			
				g2.drawString("Reference Mass Dataset : " + parent.mmdsRef.toString(), 0, yLabel);
						
				break;
				
			case 2:
		
				g2.drawString("Mass Dataset : " + parent.mmds.toString(), 0, yLabel);
				yLabel+=30;
				g2.drawString("Reference Mass Dataset : " + parent.mmdsRef.toString(), 0, yLabel);
				
				break;
				
			case 3:
			
				g2.drawString("Mass Dataset : " + parent.mmds.toString(), 0, yLabel);
				yLabel+=30;
				g2.drawString("Reference Mass Dataset : " + parent.mmdsRef.toString(), 0, yLabel);
				
				break;
		
		
		}
		
		yLabel+=30;
		g2.drawString("Quantity : " + getQuantityString(), 0, yLabel);

		g2.translate(-50, -50);
    	
    	if(titleScaleFactor<1.0){
    		
    		g2.scale(1/titleScaleFactor, 1/titleScaleFactor);
    	
    	}
    	
    	g2.translate((int)legendTrans.getX(), (int)legendTrans.getY());
		g2.scale(legendScaleFactor, legendScaleFactor);
		
		g2.setColor(Color.gray);
    	g2.setFont(waterMarkFont);
    	g2.drawString("nuclearmasses.org", -70, 300);
    	
    	if(useWhiteText){
	        
        	g2.setColor(isoLabelColor);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
    	g2.drawString("nuclearmasses.org", -72, 302);

		g2.setRenderingHints(hintsText);
		
		if(scheme.equals("Continuous")){
		
			for(int i=0; i<200; i++){
				
				g2.setColor(parent.getRGB(i/200.0));
				g2.drawLine(28, 200-i, 63, 200-i);
			
			}
			
			g2.setColor(Color.black);
	    	g2.drawRect(28, 0, 35, 200);
			
		}else if(scheme.equals("Binned")){
		
			g2.setFont(Fonts.realSmallFont);
		
			Vector binDataVector = new Vector();
			
			binDataVector = parent.binData;
			
			double minTotal = ((Double)((Vector)binDataVector.elementAt(binDataVector.size()-1)).elementAt(0)).doubleValue();
			double maxTotal = ((Double)((Vector)binDataVector.elementAt(0)).elementAt(1)).doubleValue();

			Color tempColor = new Color(0, 0, 0);

			boolean noShowBinToggle = true;

			for(int i=0; i<200; i++){
			
				double mag = (((maxTotal-minTotal)/200.0)*i) + minTotal;

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
			
				g2.drawLine(0, 200-i, 50, 200-i);
				
			}
			
			g2.setColor(Color.black);
	    	g2.drawRect(0, 0, 50, 200);
			
			for(int i=1; i<binDataVector.size(); i++){
			
				g2.setColor(Color.black);
			
				double mag = ((Double)((Vector)binDataVector.elementAt(i)).elementAt(1)).doubleValue();
			
				int lineIndex = (int)((200.0/(maxTotal-minTotal))*(mag-minTotal));
				
				g2.drawLine(24, 200-lineIndex, 50, 200-lineIndex);

				g2.drawString(String.valueOf(mag), 2, 200 - lineIndex + 4);

			}		
		
		}
		
		g2.setFont(bigFont);
		if(useWhiteText){
	        
        	g2.setColor(Color.white);
        
       	}else{
       		
       		g2.setColor(Color.black);
       	
       	}
		g2.drawString("Max : " + parent.maxField.getText(), 71, 16);
		g2.drawString("Min : " + parent.minField.getText(), 71, 200);

    }
    
	/**
	 * Gets the quantity string.
	 *
	 * @return the quantity string
	 */
	private String getQuantityString(){
    
    	String string = "";
    	
    	switch(type){
    		
			case 2:
				string += "Difference of ";
				break;
				
			case 3:
				string += "Absolute Value of Difference of ";
				break;
		
		
		}
    	
    	switch(quantity){
				
			//EXCESS
			case 0:
				string += "Mass Excess";
				break;
			
			//N	
			case 1:
				string += "S_n";
				break;
			
			//2N	
			case 2:
				string += "S_2n";
				break;
			
			//P	
			case 3:
				string += "S_p";
				break;
				
			//2P	
			case 4:
				string += "S_2p";
				break;
			
			//ALPHA	
			case 5:
				string += "S_alpha";
				break;
			
			//ALPHA,N
			case 6:
				string += "Q_(alpha,n)";
				break;
			
			//ALPHA,P	
			case 7:
				string += "Q_(alpha,p)";
				break;
			
			//P,N	
			case 8:
				string += "Q_(p,n)";
				break;
		
		}
    	
    	return string;
    
    }
    
	/**
	 * Gets the title scale factor.
	 *
	 * @return the title scale factor
	 */
	private double getTitleScaleFactor(){
   
		double factor = 1.0;
		
		int width = 0;
		
		int length1 = reallyBigFontMetrics.stringWidth("Mass Dataset : " 
								+ parent.mmds.toString());
		
		int length2 = reallyBigFontMetrics.stringWidth("Reference Mass Dataset : " 
								+ parent.mmdsRef.toString());
								
		String string = "Quantity : ";
		
		switch(type){
    		
			case 2:
				string += "Difference of ";
				break;
				
			case 3:
				string += "Absolute Value of Difference of ";
				break;
		
		
		}
    	
    	switch(quantity){
				
			//EXCESS
			case 0:
				string += "Mass Excess";
				break;
			
			//N	
			case 1:
				string += "S_n";
				break;
			
			//2N	
			case 2:
				string += "S_2n";
				break;
			
			//P	
			case 3:
				string += "S_p";
				break;
				
			//2P	
			case 4:
				string += "S_2p";
				break;
			
			//ALPHA	
			case 5:
				string += "S_alpha";
				break;
			
			//ALPHA,N
			case 6:
				string += "Q_(alpha,n)";
				break;
			
			//ALPHA,P	
			case 7:
				string += "Q_(alpha,p)";
				break;
			
			//P,N	
			case 8:
				string += "Q_(p,n)";
				break;
		
		}
		
		int length3 = reallyBigFontMetrics.stringWidth(string);
								
		switch(type){
		
			case 0:
				width = Math.max(length1, length3);
				break;
			case 1:
				width = Math.max(length2, length3);		
				break;
				
			case 2:
				width = Math.max(length1, length2);
				width = Math.max(width, length3);
				break;
				
			case 3:
				width = Math.max(length1, length2);
				width = Math.max(width, length3);
				break;

		}
		
		int xLabel = 50;
		int fullWidth = xLabel + width;
		
		int hite = 80;
		int yLabel = 50 - reallyBigFontMetrics.getHeight();
		int fullHite = yLabel + hite;
         
        double isoDistance = 1E6;
        
        Point point = new Point(0, 0);
        
        for(int i=0; i<ZNMapArray.length; i++){
        	
        	int z = (int)ZNMapArray[i].getX();
        	int n = (int)ZNMapArray[i].getY();
        	
        	int isoXPos = xoffset+n*boxWidth;
        	int isoYPos = yoffset+(zmax-z)*boxHeight;
        	
        	double currentDistance = Math.sqrt(Math.pow(isoXPos, 2) + Math.pow(isoYPos, 2));
        	
        	if(currentDistance<isoDistance){
        	
        		isoDistance = currentDistance;
        		point.setLocation(isoXPos, isoYPos);
        	
        	}
        	
        }
		
		double widthFactor = point.getX()/fullWidth;
		double hiteFactor = point.getY()/fullHite;
		
		factor = Math.min(widthFactor, hiteFactor);
		
		return factor;
    
    }
    
    /**
     * Gets the legend trans.
     *
     * @return the legend trans
     */
    private Point getLegendTrans(){
		
		double legendXPos = 0.0;
		double legendYPos = 0.0;
		int legendXTrans = 0;
	    int legendYTrans = 0;
		
		legendXPos = width - 158;
		legendYPos = height - 300;
		
		if(legendXPos>0 && legendYPos>0){
		
			Point point = new Point(0, 0);
		
			for(int i=0; i<ZNMapArray.length; i++){
	        	
	        	int z = (int)ZNMapArray[i].getX();
	        	int n = (int)ZNMapArray[i].getY();
	        	
	        	int isoXPos = xoffset+n*boxWidth+boxHeight;
	        	int isoYPos = yoffset+(zmax-z)*boxHeight+boxWidth;
	        
	        	double legendDistance = Math.sqrt((legendXPos*legendXPos) + (legendYPos*legendYPos));
				double isoDistance = Math.sqrt((isoXPos*isoXPos) + (isoYPos*isoYPos));
	        
	        	if(isoDistance>legendDistance){
	        		
	        		Point currentPoint = new Point((int)(isoXPos - legendXPos), (int)(isoYPos - legendYPos));
	
	        		if(currentPoint.getX()>point.getX() && currentPoint.getY()>point.getY()){
	        		
	        			point.setLocation((int)currentPoint.getX(), (int)currentPoint.getY());
	        		
	        		}
	        		
	        	}
	        	
	        }
	        
	        legendXTrans = (int)point.getX() + 10;
			legendYTrans = (int)point.getY() + 10;
			
			if(legendXTrans==10 && legendYTrans==10){
			
				legendXTrans = 0;
				legendYTrans = 0;
			
			}
        
        	legendXTrans = (int)legendXPos + legendXTrans;
        	legendYTrans = (int)legendYPos + legendYTrans;
        
    	}else{
    	
    		double isoDistance = 1E6;

    		for(int i=0; i<ZNMapArray.length; i++){
	        	
	        	int z = (int)ZNMapArray[i].getX();
	        	int n = (int)ZNMapArray[i].getY();
	        	
	        	int isoXPos = xoffset+n*boxWidth+boxHeight;
	        	int isoYPos = yoffset+(zmax-z)*boxHeight+boxWidth;
	        	
	        	double currentDistance = Math.sqrt(Math.pow(getSize().getWidth()-isoXPos, 2) + Math.pow(getSize().getHeight()-isoYPos, 2));

				isoDistance = Math.min(currentDistance, isoDistance);
	        	
	        }
	        
	        double newLegendXPos = getSize().getWidth() - (isoDistance/Math.sqrt(2.0));
	        double newLegendYPos = getSize().getHeight() - (isoDistance/Math.sqrt(2.0));
	        
	        legendXTrans = (int)newLegendXPos;
	        legendYTrans = (int)newLegendYPos;

    	}
    	
    	return new Point(legendXTrans, legendYTrans);
	
	}
	
    /**
     * Gets the legend scale factor.
     *
     * @return the legend scale factor
     */
    private double getLegendScaleFactor(){
	
		double factor = 1.0;
		
		int legendWidth = waterMarkFontMetrics.stringWidth("nuclearmasses.org");
		int legendHeight = 0;
		
		legendHeight = 315;
		
		double widthFactor = (getSize().getWidth()-legendTrans.getX())/legendWidth;
		double heightFactor = (getSize().getHeight()-legendTrans.getY())/legendHeight;
		
		factor = Math.min(widthFactor, heightFactor);
		
		int leftSpace = waterMarkFontMetrics.stringWidth("nucl");	
		
		int widthSpace = (int)(getSize().getWidth()-legendTrans.getX() + leftSpace*factor - factor*legendWidth);
		
		if(widthSpace>=25){
		
			legendTrans.setLocation(legendTrans.getX()+widthSpace-25, legendTrans.getY());
		
		}
		
		int heightSpace = (int)((getSize().getHeight()-legendTrans.getY()) - (factor*legendHeight));
		
		if(heightSpace>=25){
		
			legendTrans.setLocation(legendTrans.getX(), legendTrans.getY()+heightSpace-25);
		
		}

		return factor;
	
	}
	
}

