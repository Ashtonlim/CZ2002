package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Represents a faculty in the university. Contains lists of its students and
 * courses.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class Faculty implements Serializable {
	/**
	 * Used for versioning when serializing. Not necessary but added to remove
	 * warning
	 */
	private static final long serialVersionUID = 7182542381768041711L;

	/** The faculty name. */
	private String facultyName;

	/** The starting date and time of access period. */
	private LocalDateTime regStartDate;

	/** The ending date and time of access period. */
	private LocalDateTime regEndDate;

	/** List of students under the faculty. */
	private ArrayList<Student> studentList = new ArrayList<>();

	/** List of courses under the faculty. */
	private ArrayList<Course> courseList = new ArrayList<>();

	/**
	 * Instantiates a new faculty.
	 *
	 * @param facultyName the faculty name
	 */
	public Faculty(String facultyName) {
		this.facultyName = facultyName;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * Add a student to the faculty.
	 *
	 * @param student the student
	 * @throws IllegalMethodAccessException the illegal method access exception
	 */
	public void addStudent(Student student) throws IllegalMethodAccessException {
		// Only callable when instantiating a new student;
		if (student.hasFaculty()) {
			throw new IllegalMethodAccessException("Directly calling addStudent() from faculty is not allowed.");
		} else {
			studentList.add(student);
		}
	}

	/**
	 * Removes a student from the faculty.
	 *
	 * @param student the student
	 */
	public void removeStudent(Student student) {
		studentList.remove(student);
	}

	/**
	 * Add a course to the faculty.
	 *
	 * @param course the course
	 * @throws IllegalMethodAccessException the illegal method access exception
	 */
	public void addCourse(Course course) throws IllegalMethodAccessException {
		// Only callable when instantiating a new course;
		if (course.hasFaculty()) {
			throw new IllegalMethodAccessException("Directly calling addCourse() from faculty is not allowed.");
		} else {
			courseList.add(course);
		}
	}

	/**
	 * Removes a course from the faculty.
	 *
	 * @param course the course
	 */
	public void removeCourse(Course course) {
		courseList.remove(course);
	}

	public String getName() {
		return facultyName;
	}

	public LocalDateTime getStartDate() {
		return regStartDate;
	}

	public LocalDateTime getEndDate() {
		return regEndDate;
	}

	public void setName(String facultyName) {
		this.facultyName = facultyName;
	}

	/**
	 * Sets the access period for STARS
	 *
	 * @param regStartDate the start date
	 * @param regEndDate   the end date
	 */
	public void setRegistrationTime(LocalDateTime regStartDate, LocalDateTime regEndDate) {
		this.regStartDate = regStartDate;
		this.regEndDate = regEndDate;
	}
}
