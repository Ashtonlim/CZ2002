package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Faculty implements Serializable {

	private static final long serialVersionUID = 7182542381768041711L;
	private String facultyName;
	private LocalDateTime regStartDate;
	private LocalDateTime regEndDate;
	private ArrayList<Student> studentList = new ArrayList<>();
	private ArrayList<Course> courseList = new ArrayList<>();

	public Faculty(String facultyName) {
		this.facultyName = facultyName;
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void addStudent(Student student) throws IllegalMethodAccessException {
		//Only callable when instantiating a new student;
		if (student.hasFaulty()){
			throw new IllegalMethodAccessException("Directly calling addStudent() from faculty is not allowed.");
		} else {
			studentList.add(student);
		}
	}

	public void removeStudent(Student student){
		studentList.remove(student);
	}


	public void addCourse(Course course) throws IllegalMethodAccessException {
		//Only callable when instantiating a new course;
		if (course.hasFaulty()){
			throw new IllegalMethodAccessException("Directly calling addCourse() from faculty is not allowed.");
		} else {
			courseList.add(course);
		}
	}

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

	public void setRegistrationTime(LocalDateTime regStartDate, LocalDateTime regEndDate) {
		this.regStartDate = regStartDate;
		this.regEndDate = regEndDate;
	}
}
