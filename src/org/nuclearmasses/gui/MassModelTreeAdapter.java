package org.nuclearmasses.gui;

import java.awt.event.*;

/**
 * This class enables a {@link MassModelTreeListener} to respond to a double-click mouse event.
 */
public class MassModelTreeAdapter extends MouseAdapter{
	
	/** The mmtl. */
	private MassModelTreeListener mmtl;
	
	/**
	 * Class constructor.
	 *
	 * @param mmtl a {@link MassModelTreeListener}
	 */
	public MassModelTreeAdapter(MassModelTreeListener mmtl){
		this.mmtl = mmtl;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent me){
		if(me.getClickCount()>1){
			mmtl.treeDoubleClicked();
		}
	}
}
