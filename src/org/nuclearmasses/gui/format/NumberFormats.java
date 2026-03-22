package org.nuclearmasses.gui.format;

import java.text.DecimalFormat;
import java.text.FieldPosition;

/**
 * The Class NumberFormats.
 */
public class NumberFormats{
	
	/**
	 * Gets the formatted value long.
	 *
	 * @param number the number
	 * @return the formatted value long
	 */
	public static String getFormattedValueLong(double number){
		DecimalFormat decimalFormat = new DecimalFormat("0.00000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		String string = sb.toString();

		if(!string.substring(8,9).equals("-")){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}
		
		return string;
	}
	
	/**
	 * Gets the formatted value long1.
	 *
	 * @param number the number
	 * @return the formatted value long1
	 */
	public static String getFormattedValueLong1(double number){
		DecimalFormat decimalFormat = new DecimalFormat("0.000000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		String string = sb.toString();

		if(!string.substring(8,9).equals("-")){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}
		
		return string;
	}
	
	/**
	 * Gets the formatted value long2.
	 *
	 * @param number the number
	 * @return the formatted value long2
	 */
	public static String getFormattedValueLong2(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat(".000000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(8,9).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(9,10).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}

		return string;
	}
	
	/**
	 * Gets the formatted value long3.
	 *
	 * @param number the number
	 * @return the formatted value long3
	 */
	public static String getFormattedValueLong3(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.00E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(5,6).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(6,7).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}

		return string;
	}
	
	/**
	 * Gets the formatted value long4.
	 *
	 * @param number the number
	 * @return the formatted value long4
	 */
	public static String getFormattedValueLong4(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.#E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted der.
	 *
	 * @param number the number
	 * @return the formatted der
	 */
	public static String getFormattedDer(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted ratio.
	 *
	 * @param number the number
	 * @return the formatted ratio
	 */
	public static String getFormattedRatio(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted time.
	 *
	 * @param number the number
	 * @return the formatted time
	 */
	public static String getFormattedTime(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted mass.
	 *
	 * @param number the number
	 * @return the formatted mass
	 */
	public static String getFormattedMass(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted temp2.
	 *
	 * @param number the number
	 * @return the formatted temp2
	 */
	public static String getFormattedTemp2(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
		
	}
	
	/**
	 * Gets the formatted interval.
	 *
	 * @param number the number
	 * @return the formatted interval
	 */
	public static String getFormattedInterval(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.##########");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
		
	}
	
	/**
	 * Gets the formatted parameter.
	 *
	 * @param number the number
	 * @return the formatted parameter
	 */
	public static String getFormattedParameter(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat(".000000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if((!string.substring(8,9).equals("-") && !string.substring(0,1).equals("-"))
				|| (!string.substring(9,10).equals("-") && string.substring(0,1).equals("-"))){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(string.substring(0,1).equals("-")){
			string = "-0" + string.substring(1);
		}else{
			string = " 0" + string;
		}

		return string;
	}
	
	/**
	 * Gets the formatted parameter2.
	 *
	 * @param number the number
	 * @return the formatted parameter2
	 */
	public static String getFormattedParameter2(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.0000");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted number.
	 *
	 * @param number the number
	 * @return the formatted number
	 */
	public static String getFormattedNumber(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("###0.0000");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted temp3.
	 *
	 * @param number the number
	 * @return the formatted temp3
	 */
	public static String getFormattedTemp3(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("####.0000");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		return string;
	}
	
	/**
	 * Gets the formatted rate2.
	 *
	 * @param number the number
	 * @return the formatted rate2
	 */
	public static String getFormattedRate2(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat(".0000E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();
		
		if(!string.substring(6,7).equals("-")){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		if(number<1.0000E-99 || number>1.0000E99){string = "NaN";}

		return string;
	}
	
	/**
	 * Gets the formatted abundance.
	 *
	 * @param number the number
	 * @return the formatted abundance
	 */
	public static String getFormattedAbundance(double number){
		String string = "";
		DecimalFormat decimalFormat = new DecimalFormat("0.00E00");
		FieldPosition fp = new FieldPosition(0);
		StringBuffer sb = new StringBuffer();
		sb = decimalFormat.format(number, sb, fp);
		string = sb.toString();

		if(!string.substring(5,6).equals("-")){
			String[] tempArray = new String[2];
			tempArray = string.split("E");
			string = tempArray[0] + "E" + "+" + tempArray[1];
		}

		return string;
	}
}
