package org.nuclearmasses.gui.anal;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.format.*;

/**
 * This class is the graphics generator for the {@link AnalChartFrame} isotope range selector.
 */
public class AnalChartPanel extends JPanel implements MouseListener, MouseMotionListener{

	/** The box size. */
	public final int boxSize = 30;
	
	/** The xoffset. */
	public final int xoffset = 40;  
	
	/** The yoffset. */
	public final int yoffset = 15;           
    
    /** The crosshair size. */
    private final int crosshairSize = 30;
    
    /** The mouse y. */
    private int mouseX, mouseY;
    
    /** The mouse y drag. */
    private int mouseXDrag, mouseYDrag;
    
    /** The zmin. */
    public int zmax, nmax, nmin, zmin;
    
    /** The mouse z. */
    private int mouseN, mouseZ;
    
    /** The chart height. */
    public int chartWidth, chartHeight;
    
    /** The ymax. */
    private int xmax, ymax;
    
    /** The drag on. */
    private boolean crosshairsOn, dragOn;
    
    /** The min drip z. */
    private Vector<Integer> minDripZ;
    
    /** The x drag vector. */
    private Vector<Integer> xDragVector = new Vector<Integer>();
    
    /** The y drag vector. */
    private Vector<Integer> yDragVector = new Vector<Integer>();
    
    /** The ip map. */
    private TreeMap<Integer, ArrayList<IsotopePoint>> ipMap;
    
    /** The ip list. */
    public ArrayList<IsotopePoint> ipList;
    
    /** The sp. */
    private JScrollPane sp;
    
    /** The n ruler. */
    private AnalIsotopeRuler zRuler, nRuler;
    
    /** The max n paint. */
    private int minZPaint, maxZPaint, minNPaint, maxNPaint;
    
    /** The acl. */
    private AnalChartListener acl;
    
    /**
     * Class constructor.
     */
	public AnalChartPanel(){
		setBackground(Color.black);
		addMouseListener(this);
        addMouseMotionListener(this);
	}
	
	/**
	 * Sets the {@link JScrollPane} for this class.
	 * 
	 * @param	sp	the JScrollPane for this class
	 */
	public void setScrollPane(JScrollPane sp){this.sp = sp;}
	
	/**
	 * Sets the {@link AnalIsotopeRuler} for the z axis.
	 * 
	 * @param	zRuler the AnalIsotopeRuler for the z axis
	 */
	public void setZRuler(AnalIsotopeRuler zRuler){this.zRuler = zRuler;}
	
	/**
	 * Sets the {@link AnalIsotopeRuler} for the n axis.
	 * 
	 * @param	nRuler the AnalIsotopeRuler for the n axis
	 */
	public void setNRuler(AnalIsotopeRuler nRuler){this.nRuler = nRuler;}
	
	/**
	 * Initializes this class.
	 * 
	 * @param	ipMap			a {@link TreeMap} of {@link ArrayList}s of {@link IsotopePoint} objects 
	 * 							keyed on proton number z. ipMap provides all available isotopes for selection.
	 *		 					That is, all isotopes that all selected mass datasets have in common.
	 * @param	ipListOld	 	an ArrayList of IsotopePoints containing the previously selected isotopes.
	 * @param	acl				a reference to a {@link AnalChartListener}
	 */
	public void initialize(TreeMap<Integer, ArrayList<IsotopePoint>> ipMap
							, ArrayList<IsotopePoint> ipListOld
							, AnalChartListener acl){
		this.acl = acl;
		this.ipMap = ipMap;
		if(ipList==null){
			ipList = new ArrayList<IsotopePoint>();
		}
		ipList = cloneIPList(ipListOld);
		zmin = ipMap.firstKey();
		zmax = ipMap.lastKey();
		nmax = getNMax();
		nmin = getNMin();
		chartWidth = boxSize*(nmax+1);
        chartHeight = boxSize*(zmax+1);
        xmax = xoffset + chartWidth;
        ymax = yoffset + chartHeight;
        setSize(xmax+2*xoffset,ymax+2*yoffset);
        setPreferredSize(getSize());
        minDripZ = getMinDripZ();
        repaint();
	}
	
	/**
	 * Gets the n max.
	 *
	 * @return the n max
	 */
	private int getNMax(){
		Iterator<ArrayList<IsotopePoint>> itr = ipMap.values().iterator();
		int nmax = 0;
		while(itr.hasNext()){
			ArrayList<IsotopePoint> list = itr.next();
			nmax = Math.max(nmax, list.get(list.size()-1).getN());
		}
		return nmax;
	}
	
	/**
	 * Gets the n min.
	 *
	 * @return the n min
	 */
	private int getNMin(){
		Iterator<ArrayList<IsotopePoint>> itr = ipMap.values().iterator();
		int nmin = 100000;
		while(itr.hasNext()){
			ArrayList<IsotopePoint> list = itr.next();
			nmin = Math.min(nmin, list.get(0).getN());
		}
		return nmin;
	}
	
	/**
	 * Clone ip list.
	 *
	 * @param listOld the list old
	 * @return the array list
	 */
	private ArrayList<IsotopePoint> cloneIPList(ArrayList<IsotopePoint> listOld){
		ArrayList<IsotopePoint> list = new ArrayList<IsotopePoint>();
		for(IsotopePoint ip: listOld){
			list.add(new IsotopePoint(ip.getZ(), ip.getN()));
		}
		return list; 
	}
	
	/**
	 * Gets the min drip z.
	 *
	 * @return the min drip z
	 */
	private Vector<Integer> getMinDripZ(){
		Vector<Integer> vector = new Vector<Integer>();
		for(int n=nmin; n<=nmax; n++){
			Iterator<Integer> itrElement = ipMap.keySet().iterator();
			boolean isotopeNotFound = true;
			while(isotopeNotFound && itrElement.hasNext()){
				Integer z = itrElement.next();
				if(ipMap.get(z).contains(new IsotopePoint(z, n))){
					vector.add(z);
					isotopeNotFound = false;
				}
			}
		}
		return vector;
	}
	
	/**
	 * Gets the maximum neutron number displayed on the chart.
	 * 
	 *@return	the maximum neutron number displayed on the chart
	 */
	public int getNmax(){return nmax;}
	
	/**
	 * Gets the maximum proton number displayed on the chart.
	 * 
	 *@return	the maximum proton number displayed on the chart
	 */
	public int getZmax(){return zmax;}
	
	/**
	 * Gets the current x coordinate of the mouse in pixels.
	 * 
	 *@return	the current x coordinate of the mouse in pixels
	 */
	public int getMouseX(){return mouseX;}
	
	/**
	 * Gets the current y coordinate of the mouse in pixels.
	 * 
	 *@return	the current y coordinate of the mouse in pixels
	 */
	public int getMouseY(){return mouseY;}
	
	/**
	 * Gets the value of xoffset in pixels. This value is the offset from x = 0 
	 * to begin drawing the nuclide chart.
	 * 
	 *@return	the value of xoffset in pixels
	 */
	public int getXOffset(){return xoffset;}
	
	/**
	 * Gets the value of yoffset in pixels. This value is the offset from y = 0 
	 * to begin drawing the nuclide chart.
	 * 
	 *@return	the value of yoffset in pixels
	 */
	public int getYOffset(){return yoffset;}
	
	/**
	 * Gets the size in pixels of a square on the isotope chart.
	 * 
	 * @return	the size in pixels of a square on the isotope chart
	 */
	public int getBoxSize(){return boxSize;}
	
	/**
	 * Gets a boolean value indicating whether or 
	 * not crosshairs are being drawn on the nuclide chart.
	 * 
	 * @return	a boolean value indicating whether or 
	 * not crosshairs are being drawn on the nuclide chart
	 */
	public boolean getCrosshairsOn(){return crosshairsOn;}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent me){
		ipList.clear();
		mouseXDrag = me.getX();
        mouseYDrag = me.getY();
        setMouseNZ(me);
        repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent me){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Image image = toolkit.createImage("blankImage.image");
    	setCursor(toolkit.createCustomCursor(image, new Point(0, 0),"blankCursor"));
		crosshairsOn = true;
		dragOn = false;
		zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent me){
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		crosshairsOn = false;
		dragOn = false;
		zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		setMouseNZ(me);
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){
		mouseXDrag = me.getX();
        mouseYDrag = me.getY();
        setMouseNZ(me);
        ipList.clear();
        dragOn = false;	
        repaint();
	}	
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent me){
		xDragVector.trimToSize();
		yDragVector.trimToSize();
		ipList.clear();
		Iterator<Integer> itrY = yDragVector.iterator();
		while(itrY.hasNext()){
			int z = itrY.next();
			Iterator<Integer> itrX = xDragVector.iterator();
			while(itrX.hasNext()){
				int n = itrX.next();
				ipList.add(new IsotopePoint(z, n));
			}
		}
		
		if(ipList.size()>0){
			Collections.sort(ipList);
			acl.updateFields(ipList.get(0).getZ(), ipList.get(0).getN()
							, ipList.get(ipList.size()-1).getZ()
							, ipList.get(ipList.size()-1).getN());
		}else{
			acl.clearFields();
		}
		
		xDragVector.clear();
		yDragVector.clear();
		dragOn = false;
		repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent me){
        setMouseNZ(me);
        zRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
		nRuler.setCurrentState(zmax, nmax, mouseX, mouseY, xoffset, yoffset, crosshairsOn);
        repaint();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent me){
		setMouseNZ(me);
		
		xDragVector.clear();
		yDragVector.clear();
		
    	int x = 0;
    	int y = 0;
		int w = Math.abs(mouseX-mouseXDrag);
		int h = Math.abs(mouseY-mouseYDrag);
    	
    	if((mouseX-mouseXDrag)>0){
    		x = mouseXDrag;
    	}else{
    		x = mouseXDrag - w;
    	}
    	if((mouseY-mouseYDrag)>0){
    		y = mouseYDrag;
    	}else{
    		y = mouseYDrag - h;
    	}
        
    	Point minPoint = getNZPoint(x, y, crosshairsOn);
    	Point maxPoint = getNZPoint(x+w, y+h, crosshairsOn);
    	
    	if(minPoint!=null && maxPoint!=null){
    		
	    	int xminDrag = (int)minPoint.getX();
	    	int xmaxDrag = (int)maxPoint.getX();
	    	int yminDrag = (int)minPoint.getY();
	    	int ymaxDrag = (int)maxPoint.getY();
	    	
	    	if(xmaxDrag>xminDrag){
		    	for(int i=xminDrag; i<=xmaxDrag; i++){	
		    		xDragVector.add(new Integer(i));
		    	}
	    	}else{
	    		for(int i=xmaxDrag; i<=xminDrag; i++){
		    		xDragVector.add(new Integer(i));
		    	}
	    	}
	    	
	    	if(ymaxDrag>yminDrag){
		    	for(int i=yminDrag; i<=ymaxDrag; i++){
		    		yDragVector.add(new Integer(i));
		    	}
	    	}else{
	    		for(int i=ymaxDrag; i<=yminDrag; i++){
		    		yDragVector.add(new Integer(i));
		    	}
	    	}
    	
    	}
    	
    	dragOn = true;
        repaint();
	}
	
	/**
	 * Sets the mouse nz.
	 *
	 * @param me the new mouse nz
	 */
	private void setMouseNZ(MouseEvent me){
		mouseX = me.getX();
        mouseY = me.getY();
        
        if(crosshairsOn){
        	double fracY = (double)(mouseY-yoffset)/(double)chartHeight;
        	double fracX = (double)(mouseX-xoffset)/(double)chartWidth;
            mouseZ = (zmax-((int)(fracY*(zmax+1))));
            mouseN = (int)(fracX *(nmax+1));
        }else{
        	mouseZ = 0;
            mouseN = 0;
        }
        
	}
	
	/**
	 * Selects all isotopes on the nuclide chart.
	 */
	public void selectAllIsotopes(){
		ipList.clear();
		Iterator<Integer> itrElement = ipMap.keySet().iterator();
		while(itrElement.hasNext()){
			Integer z = itrElement.next();
			ArrayList<IsotopePoint> list = ipMap.get(z);
			for(IsotopePoint ip: list){
				ipList.add(ip);
			}
		}
		Collections.sort(ipList);
		acl.updateFields(ipList.get(0).getZ(), ipList.get(0).getN()
						, ipList.get(ipList.size()-1).getZ()
						, ipList.get(ipList.size()-1).getN());
		repaint();
	}
	
	/**
	 * Gets the nZ point.
	 *
	 * @param x the x
	 * @param y the y
	 * @param returnNullAllowed the return null allowed
	 * @return the nZ point
	 */
	private Point getNZPoint(int x, int y, boolean returnNullAllowed){
        if(returnNullAllowed){
        	double fracY = (double)(y-yoffset)/(double)chartHeight;
        	double fracX = (double)(x-xoffset)/(double)chartWidth;
        	return new Point((int)(fracX *(nmax+1))
        						, (zmax-((int)(fracY*(zmax+1)))));
        }
        return null;
	}
	
	/**
	 * Sets the paint values.
	 */
	private void setPaintValues(){
		
		Point minPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()
								, sp.getVerticalScrollBar().getValue()+sp.getViewport().getHeight()
								, true);
		Point maxZPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()
								, sp.getVerticalScrollBar().getValue()
								, true);
		Point maxNPaintPoint = getNZPoint(sp.getHorizontalScrollBar().getValue()+sp.getViewport().getWidth()
								, sp.getVerticalScrollBar().getValue()
								, true);
		
		minNPaint = (int)minPaintPoint.getX();
		minZPaint = (int)minPaintPoint.getY();
		maxNPaint = (int)maxNPaintPoint.getX();
		maxZPaint = (int)maxZPaintPoint.getY();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
        RenderingHints hintsText = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(hintsText);
		setPaintValues();
		
		Iterator<Integer> itrElement = ipMap.keySet().iterator();
		while(itrElement.hasNext()){
			Integer z = itrElement.next();
			if(z>=minZPaint && z<=maxZPaint){
				drawZDripLabel(g2, z);
				fillIsotopeBoxes(g2, z);
			    drawIsotopeBoxes(g2, z);
			}
		}
		
		drawNDripLabels(g2);
		if(crosshairsOn){drawCrosshairs(g2);}
		if(dragOn){drawDrag(g2);}
		
	}
	
	/**
	 * Fill isotope boxes.
	 *
	 * @param g2 the g2
	 * @param z the z
	 */
	private void fillIsotopeBoxes(Graphics2D g2, Integer z){
		g2.setColor(Colors.backColor);
		for(IsotopePoint ip: ipMap.get(z)){
			int x = xoffset+(ip.getN())*boxSize;
			int y = yoffset+(zmax-ip.getZ())*boxSize;
			if(ipList.contains(ip)){
				g2.setColor(new Color(150, 150, 150));
			}else if(mouseZ==ip.getZ() && mouseN==ip.getN()
						|| (xDragVector.contains(ip.getN())
								&& yDragVector.contains(ip.getZ()))){
				g2.setColor(new Color(200, 200, 200));
			}else{
				g2.setColor(new Color(100, 100, 100));
			}
			g2.fillRect(x, y, boxSize, boxSize);
		}
	}
	
	/**
	 * Draw isotope boxes.
	 *
	 * @param g2 the g2
	 * @param z the z
	 */
	private void drawIsotopeBoxes(Graphics2D g2, Integer z){
		g2.setColor(Colors.backColor);
		for(IsotopePoint ip: ipMap.get(z)){
			int x = xoffset+(ip.getN())*boxSize;
			int y = yoffset+(zmax-ip.getZ())*boxSize;
			g2.drawRect(x, y, boxSize, boxSize);
			if(mouseZ==ip.getZ() && mouseN==ip.getN()
					|| ipList.contains(ip)
					|| (xDragVector.contains(ip.getN()) && yDragVector.contains(ip.getZ()))){
				drawIsotopeLabel(g2, ip);
			}
		}
	}
	
	/**
	 * Draw isotope label.
	 *
	 * @param g2 the g2
	 * @param ip the ip
	 */
	private void drawIsotopeLabel(Graphics2D g2, IsotopePoint ip){
		String elementString = MainDataStructure.getElementSymbol(ip.getZ());
		String massString = String.valueOf(ip.getZ() + ip.getN());
		
		int wid = (getFontMetrics(Fonts.realSmallFont).stringWidth(elementString) 
						+ getFontMetrics(Fonts.tinyFont).stringWidth(massString));  
		int x = (int)(xoffset+boxSize*(ip.getN()+0.5)-wid/2.0);
		int y = (int)(yoffset+boxSize*(zmax-ip.getZ()+0.5)+1);
      
		g2.setFont(Fonts.tinyFont);
		g2.setColor(Colors.backColor);
		g2.drawString(massString, x, y);
    	x+=getFontMetrics(Fonts.tinyFont).stringWidth(massString);
        y+=5;
        g2.setFont(Fonts.realSmallFont);
        g2.drawString(elementString, x, y);   
	}
	
	/**
	 * Draw z drip label.
	 *
	 * @param g2 the g2
	 * @param z the z
	 */
	private void drawZDripLabel(Graphics2D g2, Integer z){
		g2.setFont(Fonts.smallFont);
		g2.setColor(Colors.backColor);
		int dx = (int)((boxSize-getFontMetrics(Fonts.smallFont).stringWidth(MainDataStructure.getElementSymbol(z)))/2.0);
        int dy = (int)((boxSize-getFontMetrics(Fonts.smallFont).getHeight()/2.0)/2.0);
        ArrayList<IsotopePoint> localList = ipMap.get(z);
        int x = xoffset+(localList.get(0).getN()-1)*boxSize+dx;
        int y = yoffset+(zmax-z+1)*boxSize-dy;
        if(!MainDataStructure.getElementSymbol(z).equals("n")){
        	g2.drawString(MainDataStructure.getElementSymbol(z), x, y);
        }
	}
	
	/**
	 * Draw n drip labels.
	 *
	 * @param g2 the g2
	 */
	private void drawNDripLabels(Graphics2D g2){
		g2.setFont(Fonts.smallFont);
		g2.setColor(Colors.backColor);
		Iterator<Integer> itr = minDripZ.iterator();
		int n = nmin;
		while(itr.hasNext()){
			String nLabel = String.valueOf(n);
			int x = (int)(xoffset+boxSize*(n+0.5)-getFontMetrics(Fonts.smallFont).stringWidth(nLabel)/2 + 1);
			int y = yoffset+boxSize*(zmax+1-itr.next())+17;
			g2.drawString(nLabel, x, y);
			n++;
		}
	}
	
	/**
	 * Draw crosshairs.
	 *
	 * @param g2 the g2
	 */
	private void drawCrosshairs(Graphics2D g2){
		g2.setStroke(new BasicStroke(2));
    	g2.setColor(Color.red);
    	g2.drawLine(mouseX - crosshairSize, mouseY, mouseX + crosshairSize, mouseY);
    	g2.drawLine(mouseX, mouseY - crosshairSize, mouseX, mouseY + crosshairSize);
	}
	
	/**
	 * Draw drag.
	 *
	 * @param g2 the g2
	 */
	private void drawDrag(Graphics2D g2){
    	int x = 0;
    	int y = 0;
		int w = Math.abs(mouseX-mouseXDrag);
		int h = Math.abs(mouseY-mouseYDrag);
    	
    	if((mouseX-mouseXDrag)>0){
    		x = mouseXDrag;
    	}else{  	
    		x = mouseXDrag - w;
    	}
    	
    	if((mouseY-mouseYDrag)>0){
    		y = mouseYDrag;
    	}else{
    		y = mouseYDrag - h;
    	}
    	
    	g2.setColor(Color.red);
    	g2.setStroke(new BasicStroke(2));
    	g2.drawRect(x, y, w, h);
	}
	
}



