package org.nuclearmasses.gui.format;

import java.util.Calendar;

/**
 * This class has methods for converting dates.
 */
public class Dates {

	/**
	 * Gets a {@link Calendar} from a string.
	 * 
	 * @param	string	the string to be parsed
	 * @return			a Calendar object
	 */
	public static Calendar getCalendar(String string){
		Calendar calendar = Calendar.getInstance();
		String day = string.split(" ")[0];
		String time = string.split(" ")[1];
		int year = Integer.valueOf(day.split("-")[0]);
		int month = Integer.valueOf(day.split("-")[1]);
		int date = Integer.valueOf(day.split("-")[2]);
		int hourOfDay = Integer.valueOf(time.split(":")[0]);
		int minute = Integer.valueOf(time.split(":")[1]);
		int second = Integer.valueOf(time.split(":")[2]);
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar;
	}
	
}
