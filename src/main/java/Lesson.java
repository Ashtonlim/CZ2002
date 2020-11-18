import java.io.Serializable;
import java.time.LocalTime;

public class Lesson implements Serializable {
	
	// type: lab, tut, lec
	private String type;
	// day: mon-1, tue-2 etc.
	private int day;
	// time: HH:MM 
	private LocalTime startTime;
	private LocalTime endTime;
	private String venue;

	public Lesson(String type, int day, String startTime, String endTime, String venue) throws Exception{
		this.type = type;
		this.day = day;
		this.venue = venue;
		LocalTime tempStart = LocalTime.parse(startTime);
		LocalTime tempEnd = LocalTime.parse(endTime);
		if (isValidTime(tempStart, tempEnd) == true) {
			this.startTime = tempStart;
			this.endTime = tempEnd;
		} else {
			System.out.println("Error - start time must be before end time.");
			throw new Exception();
		}
	}
	
	public String getType() {
		return type;
	}
	
	public int getDay() {
		return day;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public String getVenue() {
		return venue;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public void setStartTime(String startTime) {
		LocalTime temp = LocalTime.parse(startTime);
		if (isValidTime(temp, endTime)) {
			this.startTime = temp;
		} else {
			System.out.println("Error - start time must be before end time. Start time not updated.");
		}
	}
	
	public void setEndTime(String endTime) {
		LocalTime temp = LocalTime.parse(endTime);
		if (isValidTime(startTime, temp)) {
			this.endTime = temp;
		} else {
			System.out.println("Error - end time must be after start time. End time not updated.");
		}
	}
	
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	// check whether start and end times are valid
	private boolean isValidTime(LocalTime start, LocalTime end) {
		if (start.equals(end)) {
			return false;
		} else if (start.isAfter(end)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void printLessonInfo() {
		System.out.println("Type: " + type + ", " + "Day: " + day + ", " + startTime + " - " + endTime + ", " + "Venue: " + venue);
	}

}
