package org.nuclearmasses.gui.dialogs;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import org.nuclearmasses.gui.format.Fonts;

/**
 * This class is a dialog providing the User a progress bar when uploading a file
 * to the Java application [NOT to the server].
 */
public class UploadFileDialog extends JDialog implements ActionListener{

	/** The file. */
	private File file;
	
	/** The bar. */
	private JProgressBar bar;
	
	/** The cancel button. */
	private JButton cancelButton; 
	
	/** The label. */
	private JLabel label;
	
	/** The task. */
	private UploadFileTask task;
	
	/** The uploader. */
	private FileUploader uploader;
	
	/**
	 * Instantiates a new upload file dialog.
	 *
	 * @param frame the frame
	 * @param uploader the uploader
	 * @param file the file
	 */
	public UploadFileDialog(JFrame frame, FileUploader uploader, File file){
		
		super(frame, "Uploading Selected File : " + file.getName(), true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.file = file;
		this.uploader = uploader;
		
		Container c = getContentPane();
		setSize(470, 225);
		setLocation((int)(frame.getLocation().getX() + frame.getSize().width/2.0 - this.getSize().width/2.0)
					, (int)(frame.getLocation().getY() + frame.getSize().height/2.0 - this.getSize().height/2.0));

		double gap = 20;
		double[] column = {gap, TableLayoutConstants.FILL, gap};
		double[] row = {gap, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.FILL
							, 10, TableLayoutConstants.PREFERRED, gap};
		c.setLayout(new TableLayout(column, row));
		
		bar = new JProgressBar();
		bar.setValue(0);
		bar.setStringPainted(true);
		bar.setMaximum((int)file.length());
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		cancelButton.setFont(Fonts.buttonFont);
		
		label = new JLabel();
		label.setFont(Fonts.textFont);
		
		c.add(label, "1, 1, c, c");
		c.add(bar, "1, 3, f, c");
		c.add(cancelButton, "1, 5, c, c");

	}
	
	/**
	 * Begins file upload.
	 */
	public void startUpload(){
		task = new UploadFileTask(uploader, file, bar, label, this);
        task.execute();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==cancelButton){
			task.cancel(true);
		}
	}
	
}

class UploadFileTask extends org.nuclearmasses.gui.util.SwingWorker<Void, Void>{
	
	private FileUploader uploader;
	private File file;
	private JProgressBar bar;
	private JLabel label;
	private JDialog dialog;
	private byte[] buffer;
	
	public UploadFileTask(FileUploader uploader
								, File file
								, JProgressBar bar
								, JLabel label
								, JDialog dialog){
		this.uploader = uploader;
		this.file = file;
		this.bar = bar;
		this.label = label;
		this.dialog = dialog;
	}
	
	protected Void doInBackground(){
		try{
			buffer = new byte[(int)file.length()];
			FileInputStream fis = new FileInputStream(file);
			for(int i=0; i<file.length(); i++){
				if(!isCancelled()){
					fis.read(buffer, i, 1);
					bar.setValue(i+1);
					label.setText((i+1) 
							+ " bytes of " 
							+ file.length() 
							+ " bytes uploaded.");
				}else{
					return null;
				}
			}
			fis.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}
	
	protected void done(){
		uploader.uploadComplete(file.getName(), buffer);
		dialog.setVisible(true);
		dialog.dispose();
	}
	
}


