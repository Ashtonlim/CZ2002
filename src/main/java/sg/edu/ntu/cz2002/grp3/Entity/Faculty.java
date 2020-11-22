package sg.edu.ntu.cz2002.grp3.Entity;

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

	public void addStudent(Student student) {
		studentList.add(student);
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void addCourse(Course course) {
		courseList.add(course);
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
