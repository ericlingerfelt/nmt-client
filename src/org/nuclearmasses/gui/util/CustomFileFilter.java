package org.nuclearmasses.gui.util;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * This class creates a file filter for {@link JFileChooser}s.
 */
public class CustomFileFilter extends FileFilter{
	
	/** The description. */
	private String extension, description;
	
	/**
	 * Class constructor.
	 *
	 * @param extension 	the extension of the file type
	 * @param description 	the description of the file type
	 */
	public CustomFileFilter(String extension, String description){
		this.extension = extension;
		this.description = description;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File file){
		if(file.isDirectory()){return true;}
		String fileExtension = getFileExtension(file);
		if(fileExtension!=null){
			return fileExtension.equals(extension);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @param file the file
	 * @return the file extension
	 */
	private String getFileExtension(File file){
		String ext = null;
		String s = file.getName();
		int i = s.lastIndexOf('.');
		if(i>0 && i<(s.length() - 1)){
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}
	
}

