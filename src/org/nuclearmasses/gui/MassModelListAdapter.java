package org.nuclearmasses.gui;

import java.awt.event.*;

/**
 * This class enables a {@link MassModelListListener} to respond to a double-click mouse event.
 */
public class MassModelListAdapter extends MouseAdapter{
	
	/** The mmll. */
	private MassModelListListener mmll;
	
	/**
	 * Class constructor.
	 *
	 * @param mmll a {@link MassModelListListener}
	 */
	public MassModelListAdapter(MassModelListListener mmll){
		this.mmll = mmll;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){
		if(me.getClickCount()>1){
			mmll.listDoubleClicked();
		}
	}
}