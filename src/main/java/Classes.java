import java.io.Serializable;

public class Classes implements Serializable {

	private String type;
	private String timeStart;
	private String timeEnd;
	private String venue;
	
	public Classes(String type, String timeStart, String timeEnd, String venue) {
		this.type = type;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.venue = venue;
	}
	
	public String getType() {
		return type;
	}
	
	public String getTimeStart() {
		return timeStart;
	}
	
	public String getTimeEnd() {
		return timeEnd;
	}
	
	public String getVenue() {
		return venue;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	
	public void setVenue(String venue) {
		this.venue = venue;
	}
}
