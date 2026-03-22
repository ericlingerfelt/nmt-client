package org.nuclearmasses.gui;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import org.nuclearmasses.io.*;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.dialogs.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.gui.wizard.WizardFrame;
import org.nuclearmasses.gui.man.ManFrame;
import org.nuclearmasses.gui.viz.VizFrame;
import org.nuclearmasses.gui.reg.RegFrame;
import org.nuclearmasses.gui.anal.AnalFrame;

/**
 * This class is the main entry GUI for the Nuclear Mass Toolkit.
 */
public class MassFrame extends JFrame implements ActionListener, KeyListener{

	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The anal button. */
	private JButton closeButton, logButton, manButton
						, vizButton, regButton, analButton;
	
	/** The password dialog. */
	private PasswordDialog passwordDialog;
	
	/** The notice dialog. */
	private NoticeDialog noticeDialog;
	
	/** The log out dialog. */
	private CautionDialog closeDialog, logOutDialog;
	
	/** The anal frame. */
	private AnalFrame analFrame;
	
	/** The man frame. */
	private ManFrame manFrame;
	
	/** The viz frame. */
	private VizFrame vizFrame;
	
	/** The reg frame. */
	private RegFrame regFrame;

	/**
	 * Class constructor.
	 *
	 * @param cgiCom a reference to an instance of {@link CGICom}
	 * @param mds 	a reference to an instance of {@link MainDataStructure}
	 */
	public MassFrame(final CGICom cgiCom, final MainDataStructure mds){
		
		this.cgiCom = cgiCom;
		this.mds = mds;
		
		setSize(520, 410);
		setTitle("Nuclear Masses Toolkit");
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				if(mds.getLoggedIn()){
					cgiCom.doCGICall(mds, null, CGIAction.LOGOUT, MassFrame.this);
				}
				System.exit(0);	
			} 
		});
		
		double border = 30;
		double gap = 20;
		double[] col = {border, TableLayoutConstants.FILL
							, gap, TableLayoutConstants.PREFERRED, border};
		double[] row = {border, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, border};
		
		Container c = getContentPane();
		c.setLayout(new TableLayout(col, row));
		
		JLabel label = new JLabel("<html>Nuclear<p>Masses<p>Toolkit</html>");
		label.setFont(Fonts.hugeTitleFont);
		
		double[] colTool = {border, TableLayoutConstants.FILL, border};
		double[] rowTool = {border, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.PREFERRED, border};
		
		JPanel toolPanel = new JPanel(new TableLayout(colTool, rowTool));
		
		manButton = new JButton("Manager");
		manButton.setFont(Fonts.buttonFont);
		manButton.addActionListener(this);
		
		vizButton = new JButton("Visualizer");
		vizButton.setFont(Fonts.buttonFont);
		vizButton.addActionListener(this);
		
		analButton = new JButton("Analyzer");
		analButton.setFont(Fonts.buttonFont);
		analButton.addActionListener(this);
		
		regButton = new JButton("Registration");
		regButton.setFont(Fonts.buttonFont);
		regButton.addActionListener(this);
		
		enableAllFeatures(false);
		
		toolPanel.add(manButton, "1, 1, f, c");
		toolPanel.add(analButton, "1, 3, f, c");
		toolPanel.add(vizButton, "1, 5, f, c");
		toolPanel.add(regButton, "1, 7, f, c");
		
		toolPanel.setBorder(org.nuclearmasses.gui.format.Borders.getBorder("Tool and Features"));
		
		double[] colButton = {border, TableLayoutConstants.PREFERRED
								, gap, TableLayoutConstants.PREFERRED, border};
		double[] rowButton = {border, TableLayoutConstants.FILL, border};
		
		JPanel buttonPanel = new JPanel(new TableLayout(colButton, rowButton));
		
		closeButton = new JButton("Close");
		closeButton.setFont(Fonts.buttonFont);
		closeButton.addActionListener(this);
		
		logButton = new JButton("Log In");
		logButton.setFont(Fonts.buttonFont);
		logButton.addActionListener(this);
		
		buttonPanel.add(closeButton, "1, 1, c, c");
		buttonPanel.add(logButton, "3, 1, c, c");
		
		c.add(label, "1, 1, f, t");
		c.add(toolPanel, "3, 1, l, t");
		c.add(buttonPanel, "1, 3, 3, 3, c, b");
		
	}
	
	/**
	 * Enable all features.
	 *
	 * @param flag the flag
	 */
	private void enableAllFeatures(boolean flag){
		manButton.setEnabled(flag);
		vizButton.setEnabled(flag);
		regButton.setEnabled(flag);
		analButton.setEnabled(flag);
	}
	
	/**
	 * Logs a User out of the system.
	 */
	public void logOut(){
		if(cgiCom.doCGICall(mds, null, CGIAction.LOGOUT, this)){
			enableAllFeatures(false);
			logButton.setText("Log In");
			initializeAllDataStructures();
		}
	}
	
	/**
	 * Logs a User into the system.
	 */
	public void logIn(){
		if(passwordDialog.getUserRadioButton().isSelected()){
			mds.setUser(passwordDialog.getUserString());
			mds.setPassword(passwordDialog.getPasswordString());
		}else if(passwordDialog.getGuestRadioButton().isSelected()){
			mds.setUser("guest");
			mds.setPassword("guest");
		}	
	
		if(cgiCom.doCGICall(mds, null, CGIAction.GET_ID, this)){
			enableAllFeatures(true);
			logButton.setText("Log Out");
    		passwordDialog.setVisible(false);
    		passwordDialog.dispose();
			noticeDialog = new NoticeDialog(this, this);	
			noticeDialog.setVisible(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode()==KeyEvent.VK_ENTER){
			if(passwordDialog.isVisible()){
				logIn();
			}else if(noticeDialog.isVisible()){
				noticeDialog.setVisible(false);
				noticeDialog.dispose();
			}
		}	
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent ke){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent ke){}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		
		if(closeDialog!=null){
			if(ae.getSource()==closeDialog.getYesButton()){
				if(mds.getLoggedIn()){
					cgiCom.doCGICall(mds, null, CGIAction.LOGOUT, this);
				}
				System.exit(0);	
			}else if(ae.getSource()==closeDialog.getNoButton()){
				closeDialog.setVisible(false);
				closeDialog.dispose();
			}
		}
		
		if(logOutDialog!=null){
			if(ae.getSource()==logOutDialog.getNoButton()){
				logOutDialog.setVisible(false);
				logOutDialog.dispose();
			}else if(ae.getSource()==logOutDialog.getYesButton()){
				logOutDialog.setVisible(false);
				logOutDialog.dispose();
				closeAllFeatures();
				logOut();
			}
		}
		
		if(passwordDialog!=null){
			if(ae.getSource()==passwordDialog.getSubmitButton()){
				logIn();
			}
		}
		
		if(ae.getSource()==manButton){
			openMan();
		}else if(ae.getSource()==vizButton){
			openViz(0, null, null);
		}else if(ae.getSource()==analButton){
			openAnal();
		}else if(ae.getSource()==regButton){
			openReg();
		}else if(ae.getSource()==closeButton){
			if(mds.getLoggedIn()){
				cgiCom.doCGICall(mds, null, CGIAction.LOGOUT, this);
			}
			System.exit(0);	
		}else if(ae.getSource()==logButton){
			if(logButton.getText().equals("Log In")){
				if(passwordDialog==null){
					passwordDialog = new PasswordDialog(this, this, this);
					passwordDialog.initialize();
					passwordDialog.setVisible(true);
				}else{
					passwordDialog.initialize();
					passwordDialog.setVisible(true);
				}
			}else if(logButton.getText().equals("Log Out")){
				if(!windowsOpen()){
					logOut();
				}else{
					String string = "Logging out will close all open windows. Do you want to log out?";
					logOutDialog = new CautionDialog(this, this, string, "Caution!");
					logOutDialog.setVisible(true);
				}
			}
		}
	}
	
	/**
	 * Opens the Mass Dataset Analyzer.
	 */
	public void openAnal(){
		if(analFrame==null){
			analFrame = new AnalFrame(mds, cgiCom, this);
			analFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
		}
		analFrame.setVisible(true);
	}
	
	/**
	 * Opens the Mass Dataset Manager.
	 */
	public void openMan(){
		if(manFrame==null){
			manFrame = new ManFrame(mds, cgiCom, this);
			manFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
		}
		manFrame.setVisible(true);
	}
	
	/**
	 * Opens the Mass Dataset Visualizer.
	 *
	 * @param index the index
	 * @param mapSelected the map selected
	 * @param map the map
	 */
	public void openViz(int index, HashMap<Integer, MassModelDataStructure> mapSelected
							, HashMap<Integer, MassModelDataStructure> map){
		if(vizFrame==null){
			vizFrame = new VizFrame(mds, cgiCom, this);
			vizFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
		}
		if(mapSelected!=null){
			vizFrame.getDataStructure().setRefIndex(index);
			vizFrame.getDataStructure().setModelMapSelected(mapSelected);
			vizFrame.getDataStructure().setModelMap(map);
			vizFrame.gotoTools();
		}
		vizFrame.setVisible(true);
	}
	
	/**
	 * Opens the Registration Form.
	 */
	public void openReg(){
		if(regFrame==null){
			regFrame = new RegFrame(mds, cgiCom);
			regFrame.setLocation((int)(getLocation().getX()) + 25, (int)(getLocation().getY()) + 25);
		}
		regFrame.setVisible(true);
	}
	
	/**
	 * Close all features.
	 */
	private void closeAllFeatures(){
		if(manFrame!=null){manFrame.closeWizard(WizardFrame.CLOSE);}
		if(vizFrame!=null){vizFrame.closeWizard(WizardFrame.CLOSE);}
		if(analFrame!=null){analFrame.closeWizard(WizardFrame.CLOSE);}
		if(regFrame!=null){
			regFrame.setVisible(false);
			regFrame.dispose();
		}
	}
	
	/**
	 * Windows open.
	 *
	 * @return true, if successful
	 */
	private boolean windowsOpen(){
		boolean windowsOpen = false;
		if(manFrame!=null){if(manFrame.isVisible()){windowsOpen = true;}}
		if(vizFrame!=null){if(vizFrame.isVisible()){windowsOpen = true;}}
		if(regFrame!=null){if(regFrame.isVisible()){windowsOpen = true;}}
		if(analFrame!=null){if(analFrame.isVisible()){windowsOpen = true;}}
		return windowsOpen;
	}
	
	/**
	 * Initializes all feature data structures.
	 */
	public void initializeAllDataStructures(){
		mds.initialize();
		if(manFrame!=null){manFrame.getDataStructure().initialize();}
		if(vizFrame!=null){vizFrame.getDataStructure().initialize();}
		if(regFrame!=null){regFrame.getDataStructure().initialize();}
		if(analFrame!=null){analFrame.getDataStructure().initialize();}
	}
}
