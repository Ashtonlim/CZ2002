import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Faculty implements Serializable{
	private String facultyName;
	private Date regStartDate;
	private Date regEndDate;
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

	public Date getStartDate() {
		return regStartDate;
	}
	
	public Date getEndDate() {
		return regEndDate;
	}
	
	public void setName(String facultyName) {
		this.facultyName = facultyName;
	}

	public void setRegistrationTime(Date regStartDate, Date regEndDate){
		this.regStartDate = regStartDate;
		this.regEndDate = regEndDate;
	}
}

