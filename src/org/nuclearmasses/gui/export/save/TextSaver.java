package org.nuclearmasses.gui.export.save;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import org.nuclearmasses.gui.util.*;
import org.nuclearmasses.datastructure.*;

/**
 * This class allows the user to save ascii text as a txt or html file extension.
 */
public class TextSaver{

	/**
	 * Saves the string as a txt file; opens a dialog box if there was an error saving text.
	 *
	 * @param	string	the ascii text
	 * @param	frame	frame to open error dialog box over
	 * @param	mds		the {@link MainDataStructure}
	 */
	public static void saveText(String string, JFrame frame, MainDataStructure mds){
		JFileChooser fileDialog = new JFileChooser(mds.getAbsolutePath());
		fileDialog.addChoosableFileFilter(new CustomFileFilter("txt", "*.txt (Plain Text)"));
		fileDialog.setAcceptAllFileFilterUsed(false);
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			String saveFilename = fileDialog.getSelectedFile().getName();
			StringBuffer sb = new StringBuffer();
        
        	try{
            	sb.append(string);
	        }catch(Exception e){
	            e.printStackTrace();
	        }

	        String s = sb.toString();
	        byte[] buffer = s.getBytes();
	        
	        try{
	        	FileOutputStream fos;
	            if(saveFilename.endsWith(".txt")){
		        	fos = new FileOutputStream(file);
		        }else{
		        	fos = new FileOutputStream(file.getAbsolutePath() + ".txt");
		        }
		        
		        fos.write(buffer);
		        fos.close();       
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	       
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}
	}
	
	/**
	 * Saves the string as an html file; opens a dialog box if there was an error saving text.
	 *
	 * @param	string	the ascii text
	 * @param	frame	frame to open error dialog box over
	 * @param	mds		the {@link MainDataStructure}
	 */
	public static void saveTextHTML(String string, JFrame frame, MainDataStructure mds){
		JFileChooser fileDialog = new JFileChooser(mds.getAbsolutePath());
		fileDialog.addChoosableFileFilter(new CustomFileFilter("html", "*.html (Web Page)"));
		fileDialog.setAcceptAllFileFilterUsed(false);
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			String saveFilename = fileDialog.getSelectedFile().getName();
			StringBuffer sb = new StringBuffer();
        
        	try{
            	sb.append(string);
	        }catch(Exception e){
	            e.printStackTrace();
	        }

	        String s = sb.toString();
	        byte[] buffer = s.getBytes();
	        
	        try{
	        	FileOutputStream fos;
	            if(saveFilename.endsWith(".html")){
		        	fos = new FileOutputStream(file);
		        }else{
		        	fos = new FileOutputStream(file.getAbsolutePath() + ".html");
		        }
		        
		        fos.write(buffer);
		        fos.close();       
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	       
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}
	}
}