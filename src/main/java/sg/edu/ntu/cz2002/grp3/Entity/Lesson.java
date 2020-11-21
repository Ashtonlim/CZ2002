package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;
import java.time.LocalTime;

public class Lesson implements Serializable {

	private String type;	// type: lab, tut, lec
	private final LocalTime startTime; 	// time: HH:MM
	private final LocalTime endTime;
	private String venue;
	private final Index index;
	private final int dayOfWeek; //0-5 | 0 -> Mon, 1 -> Tues (Time Standard)
	private final int oddEvenWeek; //0 -> even, 1 -> odd

	/** sg.edu.ntu.cz2002.grp3.Entity.Lesson shouldn't be modified after StarsWar has begun, due to clash handling */
	public Lesson(String type, int day, int oddEvenWeek, String startTime, String endTime, String venue, Index index) throws Exception{
		this.index = index;
		index.addToLessonList(this);

		this.type = type;
		this.dayOfWeek = day;
		this.venue = venue;
		this.oddEvenWeek = oddEvenWeek;

		LocalTime tempStart = LocalTime.parse(startTime);
		LocalTime tempEnd = LocalTime.parse(endTime);

		if (!isValidTime(tempStart, tempEnd)) {
			System.out.println("Error - start time must be before end time.");
			throw new Exception();
		}

		this.startTime = tempStart;
		this.endTime = tempEnd;

	}

	public String getType() {
		return type;
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
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

	public int getWeekType(){
		return oddEvenWeek;
	}

	public String getCourseName(){
		return index.getCourseName();
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndexCode(){
		return this.index.getIndex();
	}

	/** Shouldn't allow change of start/end time, can create clashes in the system. */
//	public void setDayOfWeek(int dayOfWeek) {
//		this.dayOfWeek = dayOfWeek;
//	}


//	public void setStartTime(String startTime) {
//		LocalTime temp = LocalTime.parse(startTime);
//		if (isValidTime(temp, endTime)) {
//			this.startTime = temp;
//		} else {
//			System.out.println("Error - start time must be before end time. Start time not updated.");
//		}
//	}
//
//	public void setEndTime(String endTime) {
//		LocalTime temp = LocalTime.parse(endTime);
//		if (isValidTime(startTime, temp)) {
//			this.endTime = temp;
//		} else {
//			System.out.println("Error - end time must be after start time. End time not updated.");
//		}
//	}
	
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
		System.out.println("Type: " + type + ", " + "Day: " + dayOfWeek + ", " + startTime + " - " + endTime + ", " + "Venue: " + venue);
	}



}
