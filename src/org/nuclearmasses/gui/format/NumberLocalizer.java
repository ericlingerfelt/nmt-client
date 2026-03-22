package org.nuclearmasses.gui.format;

import java.text.*;

/**
 * This class enables strings representing numbers to be parsed into doubles
 * using the current locale.
 */
public class NumberLocalizer {
	
	/**
	 * Gets the value of a string as a {@link Double} using the current locale. 
	 * If an exception occurs during the parse, it is caught within this method 
	 * and zero is returned.
	 * 
	 * @param	number	a string to be parsed into a double
	 * @return			a the double value represented by the string or 0.0.
	 */
	public static Double valueOf(String number){
		try{
			return NumberFormat.getInstance().parse(number).doubleValue();
		}catch(ParseException pe){
			return 0.0;
		}
	}
	
	/**
	 * Gets the value of a string as a {@link Double} using the current locale.
	 * If an exception occurs during the parse, it is thrown.
	 *
	 * @param number a string to be parsed into a double
	 * @return 		a the double value represented by the string.
	 * @throws ParseException the parse exception
	 */
	public static Double valueOfWithException(String number) throws ParseException{
		return NumberFormat.getInstance().parse(number).doubleValue();
	}
	
}
