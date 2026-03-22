package org.nuclearmasses.gui.viz.chart;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.nuclearmasses.datastructure.MainDataStructure;
import org.nuclearmasses.datastructure.VizDataStructure;
import org.nuclearmasses.gui.export.copy.TextCopier;
import org.nuclearmasses.gui.export.save.TextSaver;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.gui.format.NumberFormats;

import java.text.DecimalFormat;
import java.util.*;

/**
 * This class creates a table of points from the Mass Dataset Plotting Interface.
 */
public class VizChartTableFrame extends JFrame implements ActionListener{
	  
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
    
    /** The mds. */
    private MainDataStructure mds;
    
    /**
     * Class constructor.
     *
     * @param ds 	a reference to the {@link VizDataStructure}
     * @param mds 	a reference to the {@link MainDataStructure}
     * @param frame a reference to the {@link VizPlotFrame} spawning this class
     */
    public VizChartTableFrame(VizDataStructure ds, MainDataStructure mds, VizChartFrame frame){
    	
    	this.mds = mds;
    	
    	Container c = getContentPane();
    	
        setSize(487, 400);
        setVisible(true);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent we) {
	            setVisible(false);
	            dispose();
		    } 	
        });

      	JPanel buttonPanel = new JPanel(new GridBagLayout());
      	
      	//Create buttons///////////////////////////////////////////////BUTTONS////////////////
		saveButton = new JButton("Save");
		saveButton.setFont(Fonts.buttonFont);
		saveButton.addActionListener(this);
      
      	copyButton = new JButton("Copy to Clipboard");
		copyButton.setFont(Fonts.buttonFont);
		copyButton.addActionListener(this); 
		 
      	//Create textArea/////////////////////////////////////////////TEXTAREA///////////////////
      	tableTextArea = new JTextArea("", 100, 50);
		tableTextArea.setFont(Fonts.textFontFixedWidth);
		tableTextArea.setEditable(false);
		
      	tableTextArea.setCaretPosition(0);
      	
      	JScrollPane sp = new JScrollPane(tableTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(200, 100));
      	
      	gbc.gridx = 0;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 5);
      	buttonPanel.add(saveButton, gbc);
      	gbc.gridx = 1;
        gbc.gridy = 0;
      	gbc.insets = new Insets(5, 0, 5, 0);
		buttonPanel.add(copyButton, gbc);
      	gbc.insets = new Insets(0, 0, 0, 0);
	
		c.add(sp, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
        
        validate();

    }
    
    /**
     * Sets and displays the table of points.
     *
     * @param vector the vector
     * @param valueArray the value array
     * @param title the title
     * @param quantity the quantity
     */
    protected void setTableText(Vector vector, double[] valueArray, String title, String quantity){
  
    	DecimalFormat format = new DecimalFormat("#######.############");
    	StringBuilder sb = new StringBuilder();
    	Formatter formatter = new Formatter(sb, Locale.US);
    	
		setTitle(title);	
   		tableTextArea.setText("");
   		sb.append(title);
   		sb.append("\n");
   		sb.append("\n");
   		formatter.format("%1$-3s %2$-3s %3$-3s %4$-" + quantity.length() + "s"
				, "Z"
				, "N"
				, "A"
				, quantity);
   		sb.append("\n");
		for(int i=0; i<valueArray.length; i++){
			Point point = (Point)vector.get(i);
			if(valueArray[i]<1E100){
				formatter.format("%1$3s %2$3s %3$3s %4$s"
									, NumberFormats.getFormattedMass(point.getX())
									, NumberFormats.getFormattedMass(point.getY())
									, NumberFormats.getFormattedMass(point.getX() + point.getY())
									, format.format(valueArray[i]));
				sb.append("\n");
			}
		}
   
		tableTextArea.setText(sb.toString());
		tableTextArea.setCaretPosition(0);
		
    }
    
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae){
    	if(ae.getSource()==saveButton){
			TextSaver.saveText(tableTextArea.getText(), this, mds);
    	}else if(ae.getSource()==copyButton){
    		TextCopier.copyText(tableTextArea.getText());
    	}
    }
}




