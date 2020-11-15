import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    protected ArrayList<Index> indexList;
    protected String courseCode;
    protected String courseName;
    protected String school;
    private String subjectType;
//    private String classType;
    private int AU;
    private int courseVacancy;

    public Course(String courseCode, String courseName, String school, String subjectType, int AU, ArrayList<Index> indexList) {
//        this.indexLst = indexLst;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.school = school;
        this.subjectType = subjectType;
//        this.classType = classType;
        this.AU = AU;
        this.indexList = indexList;
//        this.courseVacancy = courseVacancy;
    }

    public int getVacancy() {
        return courseVacancy;
    }

    public void setVacancy(int vacancy) {
        this.courseVacancy = vacancy;
    }
    public void printVacancy(){
        System.out.println("This course has" + getVacancy());
    }

    public String getCourseCode(){
        return courseCode;
    }

}

