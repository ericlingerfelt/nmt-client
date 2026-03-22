package org.nuclearmasses.gui.viz.chart;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.datastructure.*;

/**
 * This class creates the isotope rulers for the Z and N variables of the isotope selection charts.
 */
public class IsotopeRuler extends JComponent{

	/** The size. */
	int size = 20;
	
	/** The increment. */
	int increment = 29;
	
	/** The yoffset. */
	int zmax, nmax, mouseX, mouseY, xoffset, yoffset;
	
	/** The cross hairs on. */
	boolean crossHairsOn;
	
	/** The orientation. */
	String orientation;
    
    /** The real small font metrics. */
    FontMetrics realSmallFontMetrics = getFontMetrics(Fonts.realSmallFont);
	
	/**
	 * Class constructor.
	 *
	 * @param orientation 	either "Horizontal" or "Vertical"
	 */
	public IsotopeRuler(String orientation){
		this.orientation = orientation;
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, new Color(240, 240, 240), new Color(150, 150, 150)));
	}
	
	/**
	 * Sets the current state of this ruler.
	 *
	 * @param newZmax the new zmax
	 * @param newNmax the new nmax
	 * @param newMouseX the new mouse x
	 * @param newMouseY the new mouse y
	 * @param newXoffset the new xoffset
	 * @param newYoffset the new yoffset
	 * @param newCrossHairsOn the new cross hairs on
	 */
	public void setCurrentState(int newZmax, int newNmax, int newMouseX, int newMouseY, int newXoffset, int newYoffset, boolean newCrossHairsOn){
		zmax=newZmax;
		nmax=newNmax;
		mouseX=newMouseX;
		mouseY=newMouseY;
		xoffset=newXoffset;
		yoffset=newYoffset;
		crossHairsOn=newCrossHairsOn;
		repaint();
	}
	

	/**
	 * Sets the preferred height.
	 *
	 * @param ph the new preferred height
	 */
	public void setPreferredHeight(int ph){setPreferredSize(new Dimension(size+5, ph));}
	
    /**
     * Sets the preferred width.
     *
     * @param pw the new preferred width
     */
    public void setPreferredWidth(int pw){setPreferredSize(new Dimension(pw, size));}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
	
		Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		Rectangle drawHere = g.getClipBounds();
		
		increment = 29;
			
		g2.setColor(new Color(220, 220, 220));
        g2.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        g2.setFont(Fonts.realSmallFont);
        g2.setColor(Color.black);
        
        int end = 0;
        int start = 0;
        int tickLength = 0;
        
        if(orientation.equals("Horizontal")){
        	start = (drawHere.x/increment)*increment +1;
            end = (((drawHere.x + drawHere.width)/increment) + 1)*increment;
        }else{     
        	start = (drawHere.y/increment)*increment + 7;
            end = (((drawHere.y + drawHere.height)/increment) + 1)*increment;       
        }
        
        if(start==0){
            tickLength = 20;
            if(orientation.equals("Horizontal")){
            	g2.drawLine(0, size-1, 0, size-tickLength-1);
            }else{
            	g2.drawLine(size-1, 0, size-tickLength-1, 0);
            }
            
            start = increment;
        }
 
        for(int i=start; i<end; i+=increment){
			tickLength = 20;
            if(tickLength != 0){
                if(orientation.equals("Horizontal")){
                	g2.drawLine(i, size-1, i, size-tickLength-1);
                }else{
                	g2.drawLine(size-1, i, size-tickLength-1, i);
                }
            }
        }

        if(orientation.equals("Horizontal")){
        	int length = nmax;
	        for(int i=0; i<length+1; i++){
	        	String string = String.valueOf(i);
	        	g2.drawString(string, xoffset+i*increment+increment/2-realSmallFontMetrics.stringWidth(string)/2 + 1, 13);
	        }
    	}else{
        	int length = zmax;
	        for(int i=0; i<length+1; i++){
	        	
	        	String string = "";
	     
		        string = String.valueOf(MainDataStructure.getElementSymbol(length-i));
		        	
		        g2.drawString(string, 3, yoffset + i*increment + increment/2 + realSmallFontMetrics.getHeight()/2 -3);
		        	
	        }
    	}
        
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(2));
        
        if(crossHairsOn){
	        if(orientation.equals("Horizontal")){
	       		int position = mouseX;
	        	g2.drawLine(position, size-1, position, size-tickLength-1);	        	
	        }else{	        
	        	int position = mouseY;
	        	g2.drawLine(size-1, position, size-tickLength-1, position);	        
	        }
    	}  
	}
}