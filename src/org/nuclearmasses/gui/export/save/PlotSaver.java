package org.nuclearmasses.gui.export.save;

import java.awt.*;
import java.util.Properties;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.awt.image.BufferedImage;
import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.freehep.graphicsio.gif.GIFGraphics2D;
import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.freehep.graphicsio.png.PNGEncoder;
import org.nuclearmasses.gui.util.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.dialogs.*;

import com.sun.image.codec.jpeg.*;

/**
 * This class allows users to save a plot in a variety of image formats.
 */
public class PlotSaver{
	
	/** The file dialog. */
	static JFileChooser fileDialog;
	
	/** The save filename. */
	static String saveFilename;
	
	/**
	 * Saves the plot; opens a dialog box if there was an error saving plot.
	 *
	 * @param	plot 		the plot
	 * @param	frame 		frame to open Attention! dialog box over
	 * @param	mds 		the {@link MainDataStructure}
	 */
	public static void savePlot(JComponent plot, Component frame, MainDataStructure mds){
	
		fileDialog = new JFileChooser(mds.getAbsolutePath());
		fileDialog.setAcceptAllFileFilterUsed(false);
		fileDialog.addChoosableFileFilter(new CustomFileFilter("ps", "*.ps (PostScript)"));
		fileDialog.addChoosableFileFilter(new CustomFileFilter("pdf", "*.pdf (Portable Document Format)"));
		fileDialog.addChoosableFileFilter(new CustomFileFilter("png", "*.png (Portable Network Graphics)"));
		fileDialog.addChoosableFileFilter(new CustomFileFilter("gif", "*.gif (Graphics Interchange Format)"));
		fileDialog.addChoosableFileFilter(new CustomFileFilter("svg", "*.svg (Scalable Vector Graphics)"));
		fileDialog.addChoosableFileFilter(new CustomFileFilter("emf", "*.emf (Enhanced Meta Format)"));
		fileDialog.addChoosableFileFilter(new CustomFileFilter("jpg", "*.jpg"));
		
		saveFilename = "";
		int returnVal = fileDialog.showSaveDialog(frame); 
		
		if(returnVal==JFileChooser.APPROVE_OPTION){
			File file = fileDialog.getSelectedFile();
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
			saveFilename = fileDialog.getSelectedFile().getName();
			Properties p = new Properties();
			p.setProperty("", "");
			VectorGraphics g;
		
			try{
				if(fileDialog.getFileFilter().getDescription().equals("*.ps (PostScript)")){
					if(saveFilename.endsWith(".ps")){
						g = new PSGraphics2D(file, plot.getSize());		
					}else{
						g = new PSGraphics2D(new File(file.getAbsolutePath() + ".ps"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();		
					plot.print(g);
					g.endExport();	
				}else if(fileDialog.getFileFilter().getDescription().equals("*.pdf (Portable Document Format)")){
					if(saveFilename.endsWith(".pdf")){
						g = new PDFGraphics2D(file, plot.getSize());
					}else{
						g = new PDFGraphics2D(new File(file.getAbsolutePath() + ".pdf"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();		
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter().getDescription().equals("*.png (Portable Network Graphics)")){
					BufferedImage myImage = new BufferedImage((int)plot.getSize().getWidth(), (int)plot.getSize().getHeight(), BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = myImage.createGraphics();
					plot.print(g2);
					PNGEncoder encoder = new PNGEncoder(myImage);
					encoder.setCompressionLevel(1);
					
					byte[] myArray = encoder.pngEncode();
					
					
					FileOutputStream fos;
					if(saveFilename.endsWith(".png")){
						fos = new FileOutputStream(file);
					}else{
						fos = new FileOutputStream(new File(file.getAbsolutePath() + ".png"));
					}
						
					fos.write(myArray); 
					 		
				    fos.close();
				}else if(fileDialog.getFileFilter().getDescription().equals("*.gif (Graphics Interchange Format)")){
					if(saveFilename.endsWith(".gif")){
						g = new GIFGraphics2D(file, plot.getSize());
					}else{
						g = new GIFGraphics2D(new File(file.getAbsolutePath() + ".gif"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter().getDescription().equals("*.emf (Enhanced Meta Format)")){
					if(saveFilename.endsWith(".emf")){
						g = new EMFGraphics2D(file, plot.getSize());
					}else{
						g = new EMFGraphics2D(new File(file.getAbsolutePath() + ".emf"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter().getDescription().equals("*.svg (Scalable Vector Graphics)")){
					if(saveFilename.endsWith(".svg")){
						g = new SVGGraphics2D(file, plot.getSize());
					}else{
						g = new SVGGraphics2D(new File(file.getAbsolutePath() + ".svg"), plot.getSize());
					}
					g.setProperties(p);
					g.startExport();		
					plot.print(g);
					g.endExport();
				}else if(fileDialog.getFileFilter().getDescription().equals("*.jpg")){
					
					if(!saveFilename.endsWith(".jpg")){
						file = new File(file.getAbsolutePath() + ".jpg");
					}
					
					FileOutputStream fos = new FileOutputStream(file);
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
					BufferedImage image = new BufferedImage(plot.getSize().width, plot.getSize().height, BufferedImage.TYPE_INT_RGB);
					Graphics2D g2 = image.createGraphics();
					plot.paint(g2);
					JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
					int quality = 100;
					param.setQuality(quality/100.0f, false);
					encoder.setJPEGEncodeParam(param);
					encoder.encode(image);
					encoder.getOutputStream().close();
					
				}
			}catch(FileNotFoundException fnfe){
				
				fnfe.printStackTrace();
			
				String string = "An error has occured while trying to save this plot.";
				GeneralDialog dialog = new GeneralDialog((Frame)frame, string, "Error!");
				dialog.setVisible(true);
				
			}catch(Exception e){
				
				e.printStackTrace();
				
				String string = "An error has occured while trying to save this plot.";
				GeneralDialog dialog = new GeneralDialog((Frame)frame, string, "Error!");
				dialog.setVisible(true);
				
			}
		}else if(returnVal==JFileChooser.CANCEL_OPTION){
			mds.setAbsolutePath(fileDialog.getCurrentDirectory().getAbsolutePath());
		}	
	}
	
}
	