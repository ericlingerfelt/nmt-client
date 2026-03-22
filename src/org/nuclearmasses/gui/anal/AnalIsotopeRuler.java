package org.nuclearmasses.gui.anal;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;

/**
 *This class creates the isotope rulers for the Z and N variables 
 *of the isotope selection charts for the Mass Dataset Analyzer.
 */
public class AnalIsotopeRuler extends JComponent{

	/** The size. */
	private int size = 20;
	
	/** The yoffset. */
	private int zmax, nmax, mouseX, mouseY, xoffset, yoffset;
	
	/** The crosshairs on. */
	private boolean crosshairsOn;
	
	/** The orientation. */
	private int orientation;
	
	/** The Constant HORIZONTAL. */
	public static final int HORIZONTAL = 0;
	
	/** The Constant VERTICAL. */
	public static final int VERTICAL = 1;
	
	/**
	 * Class constructor.
	 *
	 * @param orientation either HORIZONTAL or VERTICAL
	 */
	public AnalIsotopeRuler(int orientation){
		this.orientation = orientation;
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, new Color(240, 240, 240), new Color(150, 150, 150)));
	}
	
	/**
	 *Sets the current state of this ruler.
	 *
	 *@param	zmax 			the maximum proton number for the ruler 
	 *@param	nmax 			the maximum neutron number for the ruler 
	 *@param	mouseX 			the new x coordinate of the mouse
	 *@param	mouseY 			the new y coordinate of the mouse
	 *@param	xoffset 		the new horizontal offset of the nuclide chart
	 *@param	yoffset 		the new vertical offset of the nuclide chart
	 *@param	crosshairsOn 	the new boolean indicating whether to draw 
	 *a line on the ruler to indicate mouse position to user
	 */
	public void setCurrentState(int zmax, int nmax, int mouseX, int mouseY, int xoffset, int yoffset, boolean crosshairsOn){
		this.zmax = zmax;
		this.nmax = nmax;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.crosshairsOn = crosshairsOn;
		repaint();
	}
	
	/**
	 *Sets the preferred height of the ruler.
	 *
	 *@param	ph	the preferred height in pixels
	 */
	public void setPreferredHeight(int ph){setPreferredSize(new Dimension(size, ph));}
	
	/**
	 *Sets the preferred width of the ruler.
	 *
	 *@param	pw	the preferred width in pixels
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
		FontMetrics realSmallFontMetrics = getFontMetrics(Fonts.realSmallFont);
		
		g2.setColor(Colors.backColor);
        g2.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);
        g2.setFont(Fonts.realSmallFont);
        g2.setColor(Colors.frontColor);
        
        int end = 0;
        int start = 0;
        int tickLength = 0;
        int increment = 30;
        
        if(orientation==HORIZONTAL){
        	start = (drawHere.x/increment)*increment+xoffset;
            end = (((drawHere.x + drawHere.width)/increment) + 1)*increment;
        }else{     
        	start = (drawHere.y/increment)*increment+yoffset;
            end = (((drawHere.y + drawHere.height)/increment) + 1)*increment;       
        }
        
        if(start==0){
            tickLength = 20;
            if(orientation==HORIZONTAL){
            	g2.drawLine(0, size-1, 0, size-tickLength-1);
            }else{
            	g2.drawLine(size-1, 0, size-tickLength-1, 0);
            }
            start = increment;
        }
 
        for(int i=start; i<end; i+=increment){
			tickLength = 20;
            if(tickLength != 0){
                if(orientation==HORIZONTAL){
                	g2.drawLine(i, size-1, i, size-tickLength-1);
                }else{
                	g2.drawLine(size-1, i, size-tickLength-1, i);
                }
            }
        }

        if(orientation==HORIZONTAL){
        	int length = nmax;
	        for(int i=0; i<length+1; i++){
	        	String string = String.valueOf(i);
	        	g2.drawString(string, xoffset+i*increment+increment/2-realSmallFontMetrics.stringWidth(string)/2+1, 13);
	        }
    	}else{
        	int length = zmax;
	        for(int i=0; i<length+1; i++){
		        String string = String.valueOf(MainDataStructure.getElementSymbol(length-i));
		        g2.drawString(string, 3, yoffset + i*increment + increment/2 + realSmallFontMetrics.getHeight()/2-3);	
	        }
    	}
        
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(2));
        
        if(crosshairsOn){
	        if(orientation==HORIZONTAL){
	       		int position = mouseX;
	        	g2.drawLine(position, size-1, position, size-tickLength-1);	   
	        }else{	        
	        	int position = mouseY;
	        	g2.drawLine(size-1, position, size-tickLength-1, position);	        
	        }
    	}  
	}
}



