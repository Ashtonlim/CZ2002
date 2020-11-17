import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Faculty implements Serializable{
	private String facultyName;
	private LocalDateTime regStartDate;
	private LocalDateTime regEndDate;
	private ArrayList<Student> studentList;
	private ArrayList<Course> courseList;
	
	public Faculty(String facultyName, ArrayList<Student> studentList, ArrayList<Course> courseList) {
		this.facultyName = facultyName;
		this.studentList = studentList;
		this.courseList = courseList;
	}


	public ArrayList<Course> getCourseList(){
		return courseList;
	}

	public void addCourse(Course course){
		courseList.add(course);
	}

	public String getName(){
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

	public void setRegistrationTime(LocalDateTime regStartDate, LocalDateTime regEndDate){
		this.regStartDate = regStartDate;
		this.regEndDate = regEndDate;
	}
}

