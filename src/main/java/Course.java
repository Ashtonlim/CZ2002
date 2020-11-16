import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    protected String courseCode;
    protected String courseName;
    protected String facultyName;
    protected String subjectType;
    protected ArrayList<Index> indexList;
    private int AU;
    private int courseVacancy;

    public Course(String courseCode, String courseName, String facultyName, String subjectType, int AU, ArrayList<Index> indexList, int courseVacancy) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.facultyName = facultyName;
        this.subjectType = subjectType;
        this.AU = AU;
    	this.indexList = indexList;
        this.courseVacancy = courseVacancy;
    }

    public int getVacancy() {
        return courseVacancy;
    }

    public void setVacancy(int vacancy) {
        this.courseVacancy = vacancy;
    }

    public String getCourseCode(){
        return courseCode;
    }
    
    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

}


