package org.nuclearmasses.gui.viz.plot;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import org.nuclearmasses.datastructure.MassModelDataStructure;
import org.nuclearmasses.datastructure.VizDataStructure;
import org.nuclearmasses.gui.export.copy.TextCopier;
import org.nuclearmasses.gui.export.save.TextSaver;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.gui.format.NumberFormats;

/**
 * This class creates a table of points from the Mass Dataset Plotting Interface.
 */
public class VizTableFrame extends JFrame implements ActionListener{
	  
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
    
    /** The ds. */
    private VizDataStructure ds;
    
    /** The mds. */
    private MainDataStructure mds;
    
    /** The frame. */
    private VizPlotFrame frame;
    
    /**
     * Class constructor.
     *
     * @param ds 	a reference to the {@link VizDataStructure}
     * @param mds 	a reference to the {@link MainDataStructure}
     * @param frame a reference to the {@link VizPlotFrame} spawning this class
     */
    public VizTableFrame(VizDataStructure ds, MainDataStructure mds, VizPlotFrame frame){
    	
    	this.ds = ds;
    	this.frame = frame;
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
     * @param	mmds	the currently selected {@link MassModelDataStructure}
     */
    protected void setTableText(MassModelDataStructure mmds){
   
   		if(frame.type.equals("Diff")){
   
   			setTitle("Mass Difference vs. Isotope Table of Points");
							
	   		tableTextArea.setText("");
			tableTextArea.append(getTitle()
					+ " of \n" 
					+ mmds.toString()
					+ " and "
					+ ds.getModelMapSelected().get(ds.getRefIndex()).toString()
					+ "\n\n");   
					
	
			tableTextArea.append("Mass Diff (MeV)    Z     N     A\n\n");
	
			for(int i=0; i<frame.ZArray.length; i++){
				tableTextArea.append(NumberFormats.getFormattedParameter(frame.diffArray[i]));
				tableTextArea.append("      ");
				tableTextArea.append(NumberFormats.getFormattedMass(frame.ZArray[i]));
				tableTextArea.append("     ");
				tableTextArea.append(NumberFormats.getFormattedMass(frame.NArray[i]));
				tableTextArea.append("     ");
				tableTextArea.append(NumberFormats.getFormattedMass(frame.AArray[i]) + "\n");				
			}
   
   		}else if(frame.type.equals("RMS")){
			setTitle("RMS Value vs. Isotope Table of Points");
	   		tableTextArea.setText("");
	   		tableTextArea.append(getTitle()
					+ " of \n" 
					+ mmds.toString()
					+ " and "
					+ ds.getModelMapSelected().get(ds.getRefIndex()).toString()
					+ "\n\n");  
					
			if(frame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
				tableTextArea.append("RMS Value (MeV)    Z\n\n");
				for(int i=0; i<frame.ZArrayRMS.length; i++){
					tableTextArea.append(NumberFormats.getFormattedParameter(frame.RMSZArray[i]));
					tableTextArea.append("      ");
					tableTextArea.append(NumberFormats.getFormattedMass(frame.ZArrayRMS[i]) + "\n");		
				}		
			}else if(frame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
				tableTextArea.append("RMS Value (MeV)    N\n\n");
				for(int i=0; i<frame.NArrayRMS.length; i++){
					tableTextArea.append(NumberFormats.getFormattedParameter(frame.RMSNArray[i]));
					tableTextArea.append("      ");
					tableTextArea.append(NumberFormats.getFormattedMass(frame.NArrayRMS[i]) + "\n");		
				}		
			}else if(frame.XComboBox.getSelectedItem().toString().equals("A = Z + N")){
				tableTextArea.append("RMS Value (MeV)    A\n\n");
				for(int i=0; i<frame.AArrayRMS.length; i++){
					tableTextArea.append(NumberFormats.getFormattedParameter(frame.RMSAArray[i]));
					tableTextArea.append("      ");
					tableTextArea.append(NumberFormats.getFormattedMass(frame.AArrayRMS[i]) + "\n");		
				}			
			}
		}						
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



