import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String courseCode;
    private String courseName;
    private Faculty faculty;
    private String facultyName;
    private String subjectType;
    private ArrayList<Index> indexList = new ArrayList<>();
    private int AU;

    public Course(String courseCode, String courseName, String subjectType, int AU, Faculty faculty) {
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

    public void addIndex(Index index){
        indexList.add(index);
    }

    public String getFacultyName(){
        return facultyName;
    }
}


