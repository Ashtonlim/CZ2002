package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents the lessons that an index can have. Lesson shouldn't be modified
 * after StarsWar has begun, due to clash handling.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class Lesson implements Serializable {
	/**
	 * Used for versioning when serializing. Not necessary but added to remove
	 * warning
	 */
	private static final long serialVersionUID = 3962835452663198474L;

	/** The lesson type. Can be lab, tutorial or lecture */
	private String type;

	/** The start time. */
	private final LocalTime startTime; // time: HH:MM

	/** The end time. */
	private final LocalTime endTime;

	/** The venue. */
	private String venue;

	/** The index the lesson belongs to. */
	private final Index index;

	/** The day of the week. 0-5 | 0 -> Mon, 1 -> Tues (Time Standard) */
	private final int dayOfWeek;

	/** Whether the lesson is odd or even. 0 -> even, 1 -> odd */
	private final int oddEvenWeek;

	/**
	 *
	 * @param type        the type of lesson
	 * @param day         the day
	 * @param oddEvenWeek whether odd or even week
	 * @param startTime   the start time
	 * @param endTime     the end time
	 * @param venue       the venue
	 * @param index       the index it belongs to
	 * @throws Exception the exception that prevents the start and end time from
	 *                   updating when start time is after end time.
	 */
	public Lesson(String type, int day, int oddEvenWeek, String startTime, String endTime, String venue, Index index)
			throws Exception {

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

		try {
			index.addToLessonList(this);
		} catch (IllegalMethodAccessException ignored) {
		}
		this.index = index;
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

	public int getWeekType() {
		return oddEvenWeek;
	}

	/**
	 * Gets the course name of the course which the lesson's index belong to.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return index.getCourseName();
	}

	public Index getIndex() {
		return index;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getIndexCode() {
		return this.index.getIndex();
	}

	/**
	 * Checks whether the lesson belongs to an index.
	 *
	 * @return true, if successful
	 */
	public boolean hasIndex() {
		return index != null;
	}

	/**
	 * Checks if start time is before end time. Used when instantiating lesson.
	 *
	 * @param start the starting time
	 * @param end   the ending time
	 * @return true, if start time is before end time
	 */
	private boolean isValidTime(LocalTime start, LocalTime end) {
		if (start.equals(end)) {
			return false;
		} else if (start.isAfter(end)) {
			return false;
		} else {
			return true;
		}
	}

}
