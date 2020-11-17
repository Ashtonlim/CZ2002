import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class 	TimeManager {

	public TimeManager() {}
	
	public LocalDateTime createDateTime(int year, int month, int day, int hour, int min, int second) throws DateTimeException{
		LocalDateTime datetime = null;
		try {
			datetime = LocalDateTime.of(year, month, day, hour, min, second);
			return datetime;
		} catch (DateTimeException e) {
			System.out.println("Error - Ensure values are within range.");
		}
		return datetime;
	}
	
	public String dateTimeToStr(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
		String dateTimeStr = datetime.format(formatter);
		return dateTimeStr;
	}

}
