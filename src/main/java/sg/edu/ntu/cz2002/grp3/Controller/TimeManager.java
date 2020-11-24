package sg.edu.ntu.cz2002.grp3.Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The Class that provides time handling related static functions.
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class TimeManager {
	
	/** The format for date time. */
	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	/** The format for time. */
	private static final DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	/** The current date time. */
	public static LocalDateTime currentDateTime = LocalDateTime.now();
	
	/** The current date time in string */
	public static String currentDateTimeStr = dateTimeToStr(currentDateTime);
	
	
	/**
	 * Convert DateTime to string.
	 *
	 * @param datetime the DateTime
	 * @return the DateTime in string
	 */
	// convert LocalDateTime to string
	public static String dateTimeToStr(LocalDateTime datetime) {
		return datetime.format(dtFormatter);
	}

	/**
	 * Convert time to string. Overloading dateTimeToStr().
	 *
	 * @param time the time
	 * @return the time in string
	 */
	public static String dateTimeToStr(LocalTime time) {
		return time.format(tFormatter);
	}
	
	/**
	 * Convert string to DateTime.
	 *
	 * @param datetime the DateTime
	 * @return the DateTime
	 */
	public static LocalDateTime strToDateTime(String datetime) {
		LocalDateTime ldt = null;
		try {
			ldt = LocalDateTime.parse(datetime, dtFormatter);
		} catch (java.time.format.DateTimeParseException e) {
			System.out.println("Error - Cannot parse string to LocalDateTime.");
		}
		return ldt;
	}
	
	/**
	 * Convert string to time.
	 *
	 * @param time the time
	 * @return the time
	 */
	public static LocalTime strToTime(String time) {
		LocalTime lt = null;
		try {
			lt = LocalTime.parse(time, tFormatter);
		} catch (java.time.format.DateTimeParseException e) {
			System.out.println("Error - Cannot parse string to LocalTime.");
		}
		return lt;
	}
	
	/**
	 * Check for clash between 2 time periods.
	 *
	 * @param start1 the first start time
	 * @param end1 the first end time
	 * @param start2 the second start time
	 * @param end2 the second end time
	 * @return true, if there is clash
	 */
    public static boolean checkTimeClash(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
		return start1.isBefore(end2) && end1.isAfter(start2);
	}

    /**
     * Check whether a time is in interval of 30 min.
     *
     * @param dateTime the date time
     * @return true, if time interval is valid
     */
    public static boolean checkValidTimeInterval(String time){
		LocalTime check = strToTime(time);
		if (check.getMinute() != 0 && check.getMinute() != 30) {
			return false;
		} else {
			return true;
		}
	}
    
    /**
     *  Convert day from integer to string.
     *
     * @param dayOfWeek the integer day of the week
     * @return the string day of the week
     */
	public static String numToDay(int dayOfWeek) {
		return switch (dayOfWeek) {
			case 1 -> "Monday";
			case 2 -> "Tuesday";
			case 3 -> "Wednesday";
			case 4 -> "Thursday";
			case 5 -> "Friday";
			case 6 -> "Saturday";
			default -> null;
		};
	}

}
