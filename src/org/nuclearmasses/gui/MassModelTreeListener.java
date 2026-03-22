package org.nuclearmasses.gui;

/**
 * This interface provides a method in response when a {@link MassModelTree} leaf is double-clicked.
 *
 * @see MassModelTreeEvent
 */
public interface MassModelTreeListener {
	
	/**
	 * Fired when a {@link MassModelTree} leaf is double-clicked.
	 */
	public void treeDoubleClicked();
}
