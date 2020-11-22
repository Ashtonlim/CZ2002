package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
	private String courseCode;
    private String courseName;
    private Faculty faculty;
    private String subjectType;
    private ArrayList<Index> indexList = new ArrayList<>();
    private int AU;

    public Course(String courseCode, String courseName, String subjectType, int AU, Faculty faculty) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectType = subjectType;
        this.faculty = faculty;
        this.AU = AU;
    	faculty.addCourse(this);
    }

    /** get course info */
    public ArrayList<Index> getIndexList(){
        return indexList;
    }

    public String getCourseCode(){
        return courseCode;
    }

    public String getCourseName(){
        return courseName;
    }

    public Faculty getFaculty(){
        return faculty;
    }

    public String getSubjectType() {
    	return subjectType;
    }
    
    public int getAU(){
        return AU;
    }

    /** set/modify course info */
    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public void addIndex(Index index) {
        indexList.add(index);
    }
    
    public void removeIndex(Index index) {
        indexList.remove(index);
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setFaculty(Faculty newFaculty){
    	faculty.removeCourse(this); //old faculty remove course
        this.faculty = newFaculty; // set new faculty
        faculty.addCourse(this); // new faculty add course
    }

    public void setAU(int AU) { this.AU = AU; }

    public void setSubjectType(String newSubjectType) { this.subjectType=newSubjectType; }

}


