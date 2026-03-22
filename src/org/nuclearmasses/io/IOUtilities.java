package org.nuclearmasses.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class has some common IO tools.
 */
public class IOUtilities {

	/**
	 * Reads a file into a byte array.
	 *
	 * @param file a {@link File}
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] readFile(File file) throws IOException{
		int i = (int)file.length();
		byte[] buffer = new byte[i];
		FileInputStream fis= new FileInputStream(file);
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	/**
	 * Writes a file from a byte array.
	 *
	 * @param file a {@link File}
	 * @param array a byte array
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeFile(File file, byte[] array) throws IOException{
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(array);
		fos.close();
	}
	
	/**
	 * Writes from one stream to another in an efficient manner.
	 *
	 * @param in 	an {@link InputStream}
	 * @param out 	an {@link OutputStream}
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void readStream(InputStream in, OutputStream out) throws IOException{
		synchronized(in){
			synchronized(out){
				byte[] buffer = new byte[256];
				while(true){
					int bytesRead = in.read(buffer);
					if(bytesRead==-1){break;}
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}
	
}
