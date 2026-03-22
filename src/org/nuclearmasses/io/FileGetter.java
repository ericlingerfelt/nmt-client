package org.nuclearmasses.io;

import java.net.*;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;

/**
 * This class gets a byte array from a file on our server.
 */
public class FileGetter {

	/**
	 * Gets a byte array from a file on our server.
	 * 
	 * @param	filename	a file on our server
	 * @return				a byte array
	 */
	public static byte[] getFile(String filename){
		try{
			filename = URLEncoder.encode(filename, "UTF-8");
			URL url = new URL("https://nucastrodata2.ornl.gov/phpd/get_file.php");
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			String string = "FILENAME=" + filename;
			os.write(string.getBytes());
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(inputStream, baos);
			return baos.toByteArray();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}
