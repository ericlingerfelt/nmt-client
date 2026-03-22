package org.nuclearmasses.gui.format;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.EtchedBorder;

/**
 * This class creates the corner object for isotope selection charts.
 */
public class Corner extends JComponent{

	/**
	 * Class Constructor.
	 */
	public Corner(){
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED
													, new Color(240, 240, 240)
													, new Color(150, 150, 150)));
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g2); 
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(hints);
		g2.setColor(Colors.backColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
}