package org.nuclearmasses.gui.dialogs;

/**
 * This interface is used to notify the implementer when a file upload has completed.
 */
public interface FileUploader {
	
	/**
	 * Notifies upon file upload completion and 
	 * provides the filename and contents of the file.
	 * 
	 * @param	filename	the filename of the uploaded file
	 * @param	data		a byte array containing the contents of the file
	 */
	public void uploadComplete(String filename, byte[] data);
}
