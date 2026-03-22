package org.nuclearmasses.gui.viz.s2n;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.TreeMap;

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
 * The Class VizS2nTableFrame.
 */
public class VizS2nTableFrame extends JFrame implements ActionListener{
	  
    /** The table text area. */
    private JTextArea tableTextArea;
    
    /** The copy button. */
    private JButton saveButton, copyButton;
    
    /** The ds. */
    private VizDataStructure ds;
    
    /** The mds. */
    private MainDataStructure mds;
    
    /** The frame. */
    private VizS2nPlotFrame frame;
    
    /**
     * Instantiates a new viz s2n table frame.
     *
     * @param ds the ds
     * @param mds the mds
     * @param frame the frame
     */
    public VizS2nTableFrame(VizDataStructure ds, MainDataStructure mds, VizS2nPlotFrame frame){
    	
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
     * Sets the table text.
     *
     * @param mmds the new table text
     */
    protected void setTableText(MassModelDataStructure mmds){
   
    	setTitle("S_2n Table of Points");
    	
    	tableTextArea.setText("");

    	if(frame.XComboBox.getSelectedItem().toString().equals("Z (proton number)")){
			
    		Iterator<Integer> itrN = frame.s2nMapN.keySet().iterator();
			while(itrN.hasNext()){
				int n = itrN.next();
				tableTextArea.append("S_2n"
						+ " of " 
						+ mmds.toString()
						+ " at N = "
						+ n
						+ "\n\n");   
				TreeMap<Integer, Double> map = frame.s2nMapN.get(n);
				Iterator<Integer> itrZ = map.keySet().iterator();
				while(itrZ.hasNext()){
					int z = itrZ.next();
					tableTextArea.append(NumberFormats.getFormattedParameter(map.get(z)));
					tableTextArea.append("     ");
					tableTextArea.append(NumberFormats.getFormattedMass(z) + "\n");
				}
				tableTextArea.append("\n");
			}
			
		}else if(frame.XComboBox.getSelectedItem().toString().equals("N (neutron number)")){
			
			Iterator<Integer> itrZ = frame.s2nMapZ.keySet().iterator();
    		while(itrZ.hasNext()){
    			int z = itrZ.next();
    			tableTextArea.append("S_2n"
    					+ " of " 
    					+ mmds.toString()
    					+ " at Z = "
    					+ z
    					+ "\n\n");   
    			TreeMap<Integer, Double> map = frame.s2nMapZ.get(z);
    			Iterator<Integer> itrN = map.keySet().iterator();
    			while(itrN.hasNext()){
    				int n = itrN.next();
    				tableTextArea.append(NumberFormats.getFormattedParameter(map.get(n)));
    				tableTextArea.append("     ");
    				tableTextArea.append(NumberFormats.getFormattedMass(n) + "\n");
    			}
    			tableTextArea.append("\n");
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




