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

}
