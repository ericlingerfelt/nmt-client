package org.nuclearmasses.io;

import java.io.*;
import org.nuclearmasses.datastructure.MainDataStructure;

/**
 * This class has two methods to get the contents of a file.
 */
public class FileImport{
	
	/** The fi. */
	private static FileImport fi = new FileImport();

	/**
	 * Reads in a File's contents and returns a String.
	 *
	 * @param filename the filename
	 * @return 				the File's contents as a String
	 */
	public static String getFile(String filename){
		try{
			String string = "";
			InputStream is;
			if(!MainDataStructure.getLoadFromJar()){
				return new String(FileGetter.getFile(filename));
			}
			is = fi.getClass().getResourceAsStream("/resources/" + filename);
			if(is==null){System.err.println("Error loading file: " + "/resources/" + filename);}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(is, baos);
			string = new String(baos.toByteArray());
			return string;
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Reads in a File's contents and returns a byte array.
	 *
	 * @param filename the filename
	 * @return 				the File's contents as a byte array
	 */
	public static byte[] getFileByte(String filename){
		try{
			InputStream is;
			if(!MainDataStructure.getLoadFromJar()){
				return FileGetter.getFile(filename);
			}
			is = fi.getClass().getResourceAsStream("/resources/" + filename);
			if(is==null){System.err.println("Error loading file: " + "/resources/" + filename);}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(is, baos);
			return baos.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
			return new byte[0];
		}
	}
}