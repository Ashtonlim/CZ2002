package sg.edu.ntu.cz2002.grp3.Controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeManager {
	
	private static final DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("HH:mm");
	public static LocalDateTime currentDateTime = LocalDateTime.now();
	public static String currentDateTimeStr = dateTimeToStr(currentDateTime);
	
	
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
		return start1.isBefore(end2) && end1.isAfter(start2);
	}

    public static boolean checkValidDate(String dateTime){
		LocalTime check = strToTime(dateTime);
		return check.getMinute() != 0 && check.getMinute() != 30;
	}
    
    /** convert int day to string */
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
