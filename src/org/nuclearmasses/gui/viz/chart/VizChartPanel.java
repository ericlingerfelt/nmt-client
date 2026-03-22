package org.nuclearmasses.gui.viz.chart;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import org.nuclearmasses.datastructure.*;
import java.text.*;

/**
 * This class creates the nuclide chart for the <i>Interactive Nuclide Chart for Mass Dataset Evaluations</i>.
 */
public class VizChartPanel extends JPanel implements MouseListener, MouseMotionListener, Scrollable{
	
    /** The xoffset. */
    int xoffset = 58;  
    
    /** The yoffset. */
    int yoffset = 35;
    
    /** The crosshairs on. */
    boolean crosshairsOn = false;
    
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
    
    /** The mouse x. */
    int mouseX = 0;              
    
    /** The mouse y. */
    int mouseY = 0;   
    
    /** The crosshair size. */
    int crosshairSize = 30;
    
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
    
    /** The viktor. */
    Vector viktor;
    
    /** The step. */
    int step = 0;
    
    /** The type. */
    int type;
    
    /** The init flag. */
    boolean initFlag = true;
    
    /** The color array. */
    Color[] colorArray;
    
    /** The parent. */
    private VizChartFrame parent;
    
    /**
     * Class constructor.
     *
     * @param parent the {@link VizChartFrame} that contains this class.
     */
    public VizChartPanel(VizChartFrame parent){
    	
    	this.parent = parent;
    	
    	setAutoscrolls(true);
    	
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(Color.black);
    }

    /**
     * Sets the current state of this class.
     * 
     * @param	type	0 = "Selected Model Data"
	 *					1 = "Reference Data"
	 *					2 = "Difference of Data"
	 *					3 = "Abs Value of Difference"
     */
	public void setCurrentState(int type){

		this.type = type;
		
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
        
        if(!initFlag){
        
        	double scale = parent.zoomSlider.getValue()/100.0;
			boxHeight = (int)(29.0*scale);
			boxWidth = (int)(29.0*scale);
	        
        	width = (boxWidth*(nmax+1));
	        height = (boxHeight*(zmax+1));
	        
	        xmax = (xoffset + width);
	        ymax = (yoffset + height);
        
        	setSize(xmax+xoffset, ymax+yoffset);
        			
			setPreferredSize(getSize());
		
		}else{
		
			width = (boxWidth*(nmax+1));
	        height = (boxHeight*(zmax+1));
	        
	        xmax = (xoffset + width);
	        ymax = (yoffset + height);
		
			setSize(xmax+xoffset, ymax+yoffset);
        			
			setPreferredSize(getSize());
			
			initFlag = false;
		
		}
		
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
	private boolean doIBelong(int P, int N){
    	
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
	private int[] getMinZDrip(int max, int min){
	
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
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent me) {}

    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) {
		
		if(parent.zoomSlider.getValue()==100
			&& !parent.showLabelsCheckBox.isSelected()){
			
	    	Toolkit toolkit = Toolkit.getDefaultToolkit();
	    	    	
	    	Image image = toolkit.createImage("blankImage.image");
	    
	    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
    	
    	}
    	
    	crosshairsOn = true;
    	
    	parent.ZRuler.setPreferredWidth((int)getSize().getWidth());
    	parent.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
    	parent.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
    	parent.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint(parent.sp.getViewport().getViewRect());

    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) {

    	parent.vizIsotopePanel.setValues("", "");
    	parent.vizIsotopePanel.repaint();
    	parent.valueField.setText("");
    	
    	setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    	
    	crosshairsOn = false;
    	
		parent.ZRuler.setPreferredWidth((int)getSize().getWidth());
		parent.NRuler.setPreferredHeight((int)getSize().getHeight());
       	
		parent.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
		parent.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);

    	repaint(parent.sp.getViewport().getViewRect());
    	
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent me) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me) {}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
		
		try{
		
			mouseX = me.getX();
			mouseY = me.getY();
			
	        getNZ(mouseX,mouseY);
	        
	        if(protonNumber>-1 && viktor.contains(new Point(protonNumber, neutronNumber))){
	
	    		parent.vizIsotopePanel.setValues(MainDataStructure.getElementSymbol(protonNumber), String.valueOf(neutronNumber + protonNumber));
	    		
	    		parent.vizIsotopePanel.repaint();
	    	
	    		double value = parent.valueArray[(viktor.indexOf(new Point(protonNumber, neutronNumber)))];
	    	
	    		if(value!=1E100){
	    	
	    			DecimalFormat format = new DecimalFormat("#######.############");
	    			parent.valueField.setText(format.format(value));
	
				}else{
					
					parent.valueField.setText("Does Not Exist");
				
				}
	
	    	}else{
	    	
	    		parent.vizIsotopePanel.setValues("", "");
	    		parent.vizIsotopePanel.repaint();
		    	parent.valueField.setText("");
	    	
	    	}
	    	
	    	parent.ZRuler.setPreferredWidth((int)getSize().getWidth());
	       	parent.NRuler.setPreferredHeight((int)getSize().getHeight());
	       	
	       	parent.ZRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);	
	    	parent.NRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
	
	    	repaint(parent.sp.getViewport().getViewRect());
    	
		}catch(Exception e){
			
		}
    	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){}
	
    /**
     * Gets the nZ.
     *
     * @param x the x
     * @param y the y
     * @return the nZ
     */
    private void getNZ(int x, int y) {

        // Return immediately if outside bounds of the chart
        if(x < xoffset || x > xmax
                       || y < yoffset
                       || y > ymax){
                        
                protonNumber = 0;
                neutronNumber = 0;
 
        // Otherwise determine the N and Z of the clicked square
        // if between the drip lines

        }else{
        	
            double fracY = (double)(y-yoffset)/(double)height;
            
            int tprotonNumber = (zmax - ((int)(fracY * (zmax+1))));
            
            double fracX = (double)(x-xoffset)/(double)width;
            
            int tneutronNumber = (int)(fracX * (nmax+1));
            
            protonNumber = tprotonNumber;
            neutronNumber = tneutronNumber;
   
        }
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
    		
    			g2.drawLine(xoffset+nMagicArray[i]*boxWidth, this.height, xoffset+nMagicArray[i]*boxWidth, 0);
    			g2.drawLine((xoffset+nMagicArray[i]*boxWidth)+boxWidth, this.height, (xoffset+nMagicArray[i]*boxWidth)+boxWidth, 0);
    		
    		}
    		
    		for(int i=0; i<zMagicArray.length; i++){	
    		
    			g2.drawLine(this.width, yoffset+(zmax-zMagicArray[i])*boxHeight, 0, yoffset+(zmax-zMagicArray[i])*boxHeight);
    			g2.drawLine(this.width, (yoffset+(zmax-zMagicArray[i])*boxHeight)+boxHeight, 0, (yoffset+(zmax-zMagicArray[i])*boxHeight)+boxHeight);
    		
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
				
				g2.setColor(Color.black);
			
			}

	        g2.fillRect(xoffset+n*boxWidth,
	                   yoffset+(zmax-z)*boxHeight,
	                   boxWidth,boxHeight);
	        
	        if(parent.zoomSlider.getValue()>=50){
	        
		        g2.setColor(frameColor);
	
		        g2.drawRect(xoffset+n*boxWidth,
		                   yoffset+(zmax-z)*boxHeight,
		                   boxWidth,boxHeight);
	                   
	       	}
	       	
	       	g2.setColor(isoLabelColor);
        
	        if(parent.zoomSlider.getValue()>=75){
	        	
		        if(parent.showLabelsCheckBox.isSelected()){
    
    				String tempS = MainDataStructure.getElementSymbol(z);
			        String tempS2 = String.valueOf(z+n);
			        
			        int wid = (realSmallFontMetrics.stringWidth(tempS)
			                  + tinyFontMetrics.stringWidth(tempS2));
			                  
			        int xzero = (xoffset+n*boxWidth+boxWidth/2-wid/2);
			        
			        int yzero = (yoffset+(zmax-z+1)*boxHeight-boxHeight/2+1);
			        
			        g2.setFont(tinyFont);
			        g2.setColor(isoLabelColor);
		
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
       	
        g2.setColor(isoLabelColor);
	    	
	   	if(parent.zoomSlider.getValue()>=75){ 	
	   
	        // Labels for vertical axis

	        g2.setFont(smallFont);
	        g2.setColor(Color.white);
	        
	        for(int z=zmin; z<=zmax; z++){
		           
	           String tempS = MainDataStructure.getElementSymbol(z);
	           
	           int ds = (minDripN[z-zmin]*boxWidth);   
	           g2.drawString(tempS,
	                            xoffset -8 + ds -smallFontMetrics.stringWidth(tempS),
	                            yoffset + (zmax-z)*boxHeight + boxHeight/2
	                            + smallFontMetrics.getHeight()/2 -3);
	       		
	        }
	        
	        // Labels for horizontal axis
	
	        g2.setFont(smallFont);
	        g2.setColor(Color.white);
	        
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
    	
        if(parent.zoomSlider.getValue()==100
        	&& !parent.showLabelsCheckBox.isSelected()){
        	
        	if(crosshairsOn){
        		
				g2.setStroke(new BasicStroke(2));
				
        		g2.setColor(Color.red);

        		g2.drawLine(mouseX - crosshairSize, mouseY, mouseX + crosshairSize, mouseY);
        	
        		g2.drawLine(mouseX, mouseY - crosshairSize, mouseX, mouseY + crosshairSize);
        
        	}
        }

    }
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize(){return super.getPreferredSize();}
	
	/* (non-Javadoc)
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize(){return getPreferredSize();}

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation,
                                          int direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition -
                             (currentPosition / 29)
                              * 29;
            return (newPosition == 0) ? 29 : newPosition;
        }
		return ((currentPosition / 29) + 1)
		       * 29
		       - currentPosition;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle, int, int)
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation,
                                           int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - 29;
        }
		return visibleRect.height - 29;
    }

    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
     */
    public boolean getScrollableTracksViewportWidth(){return false;}
    
    /* (non-Javadoc)
     * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
     */
    public boolean getScrollableTracksViewportHeight(){return false;}

}

