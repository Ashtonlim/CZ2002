package sg.edu.ntu.cz2002.grp3.Controller;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {

	private static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("HH:mm");
	
	
	// create a LocalDateTime object
	public static LocalDateTime createDateTime(int year, int month, int day, int hour, int min, int second) throws DateTimeException{
		LocalDateTime datetime = null;
		try {
			datetime = LocalDateTime.of(year, month, day, hour, min, second);

		} catch (DateTimeException e) {
			System.out.println("Error - Ensure values are within range.");
		}

		return datetime;
	}
	
	// convert LocalDateTime to string
	public static String dateTimeToStr(LocalDateTime datetime) {
		return datetime.format(dtFormatter);
	}

	public static String dateTimeToStr(LocalTime datetime) {
		return datetime.format(tFormatter);
	}
	
	public static LocalDateTime strToDateTime(String datetime) {
		LocalDateTime ldt = null;
		try {
			ldt = LocalDateTime.parse(datetime, dtFormatter);
		} catch (java.time.format.DateTimeParseException e) {
			System.out.println("Error - Cannot parse string to LocalDateTime.");
		}
		return ldt;
	}
	
	public static LocalTime strToTime(String time) {
		LocalTime lt = null;
		try {
			lt = LocalTime.parse(time, tFormatter);
		} catch (java.time.format.DateTimeParseException e) {
			System.out.println("Error - Cannot parse string to LocalTime.");
		}
		return lt;
	}
	
	/** check for clash between 2 time periods */
    public static boolean checkTimeClash(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        if (start1.isBefore(end2) && end1.isAfter(start2)) {
            return true;
        }
        return false;
    }
    
    /** convert int day to string */
	public static String numToDay(int dayOfWeek) {
		switch(dayOfWeek) {
		case 1:
			return "Monday"; 
		case 2:
			return "Tuesday"; 
		case 3:
			return "Wednesday"; 
		case 4:
			return "Thursday"; 
		case 5:
			return "Friday"; 
		case 6:
			return "Saturday"; 
		}
		return null;
	}

}
