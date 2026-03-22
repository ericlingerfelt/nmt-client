package org.nuclearmasses.gui.format;

import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * The Class SizedComboBox.
 */
public class SizedComboBox extends JComboBox {
   
   /** The popup width. */
   protected int popupWidth;
   
   /** The popup height. */
   protected int popupHeight;
   
   /** The prototype display value. */
   private String prototypeDisplayValue = "123456789012345";
   
   /**
    * Instantiates a new sized combo box.
    */
   public SizedComboBox() {
      super();
      setPrototypeDisplayValue(prototypeDisplayValue);
   }
   
   /**
    * Instantiates a new sized combo box.
    *
    * @param aModel the a model
    */
   public SizedComboBox(ComboBoxModel aModel) {
      super(aModel);
      setPrototypeDisplayValue(prototypeDisplayValue);
   }
 
   /**
    * Instantiates a new sized combo box.
    *
    * @param items the items
    */
   public SizedComboBox(final Object[] items) { 
      super(items); 
      setPrototypeDisplayValue(prototypeDisplayValue);
   } 
 
   /**
    * Instantiates a new sized combo box.
    *
    * @param items the items
    */
   public SizedComboBox(Vector items) { 
      super(items); 
      setPrototypeDisplayValue(prototypeDisplayValue);
   } 
 
   /**
    * Sets the popup width.
    *
    * @param width the new popup width
    */
   public void setPopupWidth(int width) { 
      popupWidth = width; 
   }
 
   /**
    * Sets the popup height.
    *
    * @param height the new popup height
    */
   public void setPopupHeight(int height) {
      popupHeight = height;
   } 
 
   /**
    * Gets the popup size.
    *
    * @return the popup size
    */
   public Dimension getPopupSize() { 
      Dimension size = getSize(); 
      if (popupWidth < 1) popupWidth = size.width; 
      return new Dimension(popupWidth, popupHeight); 
   } 
   
   /**
    * Sets the popup width to longest.
    */
   public void setPopupWidthToLongest(){
	   int width = 0;
	   FontMetrics metrics = getFontMetrics(getFont());
	   for(int i=0; i<getModel().getSize(); i++){
		   width = Math.max(width, metrics.stringWidth(getItemAt(i).toString()));
	   }
	   if(metrics.stringWidth(prototypeDisplayValue)<width){
		   setPopupWidth(width+5);
	   }
   }
}





