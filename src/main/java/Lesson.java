import java.io.Serializable;

public class Lesson implements Serializable {
	
	// type: lab, tut, lec
	private String type;
	// day: mon-1, tue-2 etc.
	private int day;
	// time: HH:MM 
	// convert to date object when comparing?
	private String startTime;
	private String endTime;
	private String venue;
	

	public Lesson(String type, int day, String startTime, String endTime, String venue) {
		this.type = type;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.venue = venue;
	}
	
	public String getType() {
		return type;
	}
	
	public int getDay() {
		return day;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
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
		this.startTime = startTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public void setVenue(String venue) {
		this.venue = venue;
	}

}
