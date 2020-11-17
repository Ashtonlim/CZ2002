import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String courseCode;
    private String courseName;
    private Faculty faculty;
    private String facultyName;
    private String subjectType;
    private ArrayList<Index> indexList;
    private int AU;

    public Course(String courseCode, String courseName, String facultyName, String subjectType, int AU, ArrayList<Index> indexList, int courseVacancy) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectType = subjectType;
        this.AU = AU;
    	this.indexList = indexList;
    }
    
    public ArrayList<Index> getIndexList(){
    	return indexList;
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getCourseName(){
        return courseName;
    }
    
    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public void setFaculty(Faculty faculty){
        this.faculty = faculty;
    }

    public Faculty getFaculty(){
        return faculty;
    }

    public String getFacultyName(){
        return facultyName;
    }
}


