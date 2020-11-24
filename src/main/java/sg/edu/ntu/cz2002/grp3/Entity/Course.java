package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a course in a faculty.
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class Course implements Serializable {
	
	/** The course code. */
	private String courseCode;
    
    /** The course name. */
    private String courseName;
    
    /** The faculty which the course belongs to. */
    private Faculty faculty;
    
    /** The subject type. Core, UE etc.*/
    private String subjectType;
    
    /** Its list of indexes */
    private ArrayList<Index> indexList = new ArrayList<>();
    
    /** The number of academic units. */
    private int AU;

    /**
     * Creates a new course and adds 
     * itself to its faculty's course list
     *
     * @param courseCode the course code
     * @param courseName the course name
     * @param subjectType the subject type
     * @param AU the number of AUs
     * @param faculty the faculty that the course falls under
     */
    public Course(String courseCode, String courseName, String subjectType, int AU, Faculty faculty) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.subjectType = subjectType;
        this.AU = AU;
        try {
            faculty.addCourse(this);
        } catch (IllegalMethodAccessException ignored){}
        this.faculty = faculty;
    }

    /**
     *  get the list of indexes the course has
     *
     * @return the index list
     */
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

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    /**
     * Adds an index to the index list
     *
     * @param index the index to be added
     * @throws IllegalMethodAccessException
     */
    public void addIndex(Index index) throws IllegalMethodAccessException {
        if (index.hasCourse()){
            throw new IllegalMethodAccessException("Directly calling addIndex() from Course is not allowed.");
        } else {
            indexList.add(index);
        }
    }
    
    /**
     * Removes an index from the index list..
     *
     * @param index the index
     */
    public void removeIndex(Index index) {
        indexList.remove(index);
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public void setAU(int AU) { 
    	this.AU = AU; 
    }

    public void setSubjectType(String newSubjectType) { 
    	this.subjectType=newSubjectType; 
    }

    /**
     * Checks whether course has a faculty
     *
     * @return true, if faculty exists
     */
    public boolean hasFaculty(){
        return faculty != null;
    }
}


