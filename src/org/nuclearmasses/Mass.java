package org.nuclearmasses;

import java.awt.Color;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import org.nuclearmasses.datastructure.MainDataStructure;
import org.nuclearmasses.io.CGICom;
import org.nuclearmasses.gui.MassFrame;
import org.nuclearmasses.gui.format.Colors;
import org.nuclearmasses.gui.format.Fonts;
import org.nuclearmasses.enums.URLType;

/**
 * Mass is the class with the <code>main</code> method for starting the Nuclear Masses Toolkit.
 * Usage - java Mass [DEV/NONDEV] [RELEASE/DEBUG] [LOAD_FROM_JAR/LOAD_FROM_WEB]
 * <p>
 * DEV/NONDEV					determines the URL called form CGI actions.
 * <p>
 * RELEASE/DEBUG				if DEBUG, CGI output and input is printed to the stdout.
 * <p>
 * LOAD_FROM_JAR/LOAD_FROM_WEB	sets location where required datafiles and images will be accessed.
 */
public class Mass {

	/** The mass frame. */
	private final MassFrame massFrame;
	
	/** The cgi com. */
	private final CGICom cgiCom;
	
	/** The mds. */
	private final MainDataStructure mds = new MainDataStructure();
	
	/**
	 * Class constructor.
	 *
	 * @param args the String[] passed from the <code>main</code> method
	 */
	public Mass(String[] args){
		
		if(args[0].equalsIgnoreCase("DEV")){
			mds.setURLType(URLType.DEV);
		}else if(args[0].equalsIgnoreCase("NONDEV")){
			mds.setURLType(URLType.NONDEV);
		}
		
		if(args[1].equalsIgnoreCase("DEBUG")){
			mds.setDebug(true);
		}else if(args[1].equalsIgnoreCase("RELEASE")){
			mds.setDebug(false);
		}
		
		if(args[2].equalsIgnoreCase("LOAD_FROM_JAR")){
			MainDataStructure.setLoadFromJar(true);
		}else if(args[2].equalsIgnoreCase("LOAD_FROM_WEB")){
			MainDataStructure.setLoadFromJar(false);
		}
        	
		UIManager.put("ComboBox.foreground", Colors.frontColor);
		UIManager.put("ComboBox.background", Colors.backColor);
		UIManager.put("ComboBox.disabledForeground", Colors.disabledFrontColor);
		UIManager.put("ComboBox.disabledBackground", Colors.disabledBackColor);
		UIManager.put("ComboBox.font", new FontUIResource(Fonts.textFont));
		
  		UIManager.put("CheckBox.background", Colors.backColor);
    	UIManager.put("CheckBox.foreground", Colors.frontColor);
    	UIManager.put("CheckBox.font", new FontUIResource(Fonts.textFont));
    	
    	UIManager.put("Button.background", Colors.backColor);
    	UIManager.put("Button.font", new FontUIResource(Fonts.buttonFont));
    	
		UIManager.put("TabbedPane.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("OptionPane.foreground", Colors.frontColor);
		UIManager.put("OptionPane.background", Colors.backColor);
		UIManager.put("OptionPane.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("Panel.background", Colors.backColor);
		UIManager.put("Panel.foreground", Colors.frontColor);
		UIManager.put("Panel.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("Label.background", Colors.backColor);
		UIManager.put("Label.foreground", Colors.frontColor);
		UIManager.put("Label.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("RadioButton.background", Colors.backColor);
		UIManager.put("RadioButton.foreground", Colors.frontColor);
		UIManager.put("RadioButton.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("List.background", Color.white);
		UIManager.put("List.foreground", Colors.frontColor);
		UIManager.put("List.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("Tree.background", Color.white);
		UIManager.put("Tree.foreground", Colors.frontColor);
		UIManager.put("Tree.font", new FontUIResource(Fonts.textFont));
		UIManager.put("Tree.foreground", Colors.frontColor);

		UIManager.put("ScrollPane.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("Table.background", Color.white);
		UIManager.put("Table.foreground", Colors.backColor);
		UIManager.put("Table.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("ToolBar.background", Colors.backColor);
    	UIManager.put("ToolBar.foreground", Colors.frontColor);
    	UIManager.put("ToolBar.font", new FontUIResource(Fonts.textFont));
    	
		UIManager.put("TextArea.background", Color.white);
		UIManager.put("TextArea.foreground", Colors.frontColor);
		UIManager.put("TextArea.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("TextPane.background", Color.white);
		UIManager.put("TextPane.foreground", Colors.frontColor);
		UIManager.put("TextPane.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("TextField.background", Color.white);
		UIManager.put("TextField.foreground", Colors.frontColor);
		UIManager.put("TextField.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("EditorPane.background", Colors.backColor);
		UIManager.put("EditorPane.foreground", Colors.frontColor);
		UIManager.put("EditorPane.font", new FontUIResource(Fonts.textFont));
		
		UIManager.put("Slider.background", Colors.backColor);
		UIManager.put("Slider.foreground", Colors.frontColor);
		UIManager.put("Slider.font", new FontUIResource(Fonts.textFont));

		cgiCom = new CGICom();
		massFrame = new MassFrame(cgiCom, mds);
		massFrame.setVisible(true);
	}
	
	/**
	 * Starts the Nuclear Masses Toolkit
	 * <p>
	 * Usage - java Mass [DEV/NONDEV] [RELEASE/DEBUG] [LOAD_FROM_JAR/LOAD_FROM_WEB].
	 *
	 * @param args contains startup arguments
	 */
	public static void main(String[] args){
		try{
			if(args.length!=3){
				System.err.println("Usage - java Mass [DEV/NONDEV] [RELEASE/DEBUG] [LOAD_FROM_JAR/LOAD_FROM_WEB]");
				System.exit(1);
			}else if(!args[0].equalsIgnoreCase("DEV") && !args[0].equalsIgnoreCase("NONDEV")
					|| !args[1].equalsIgnoreCase("RELEASE") && !args[1].equalsIgnoreCase("DEBUG")
					|| !args[2].equalsIgnoreCase("LOAD_FROM_JAR") && !args[2].equalsIgnoreCase("LOAD_FROM_WEB")){
				System.err.println("Usage - java Mass [DEV/NONDEV] [RELEASE/DEBUG] [LOAD_FROM_JAR/LOAD_FROM_WEB]");
				System.exit(1);
			}else{
				new Mass(args);	
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
}
