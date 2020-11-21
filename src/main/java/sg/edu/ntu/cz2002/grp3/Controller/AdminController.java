package sg.edu.ntu.cz2002.grp3.Controller;
import sg.edu.ntu.cz2002.grp3.Entity.*;

import java.util.*;
import java.time.LocalDateTime;

public class AdminController {
    private final RecordManager RM;

    public AdminController(RecordManager RM){
        this.RM = RM;
    }

    /** Get all student list */
    public ArrayList<Student> getAllStudents() {
    	return RM.getAllStudents();
    }
    
    /** Get student list from faculty */
    private ArrayList<Student> getStudentList(Faculty faculty){
        ArrayList<Student> studentList = new ArrayList<>();
        for (Course course : faculty.getCourseList()){
            for (Index index : course.getIndexList()){
                studentList.addAll(index.getStudentList());
            }
        }
        return studentList;
    }

    /** Get student list from course */
    private ArrayList<Student> getStudentList(Course course){
        ArrayList<Student> studentList = new ArrayList<>();
        for (Index index : course.getIndexList()){
            studentList.addAll(index.getStudentList());
        }
        return studentList;
    }

    /** Get student list from index */
    private ArrayList<Student> getStudentList(Index index){
        return index.getStudentList();
    }

    /** Check if faculty exists */
    public Faculty getFaculty(String facultyName){
        return RM.getFaculty(facultyName);
    }
    
    /** Get course */
    public Course getCourse(String courseCode){
        return RM.getCourse(courseCode);
    }
    
    /** Get index */
    public Index getIndex(String index){
        return RM.getIndex(index);
    }

    /** 4.Check available slot for an index number (vacancy in a class) -wx  */
    public int checkVacancies(String indexCode){
        Index index = RM.getIndex(indexCode);
        return (index != null) ? index.getVacancy(): -1;
    }

    /** 5. Print student list by index number. -wx */
    public ArrayList<Student> getStudentListByIndex(String indexCode){
        Index index = RM.getIndex(indexCode);
        return (index != null) ? getStudentList(index) : null;
    }

    /** 6.Print student list by course (all students registered for the selected course). -wx */
    public ArrayList<Student> getStudentListByCourse(String courseCode){
        Course course = RM.getCourse(courseCode);
        return (course != null) ? getStudentList(course) : null;
    }
    
    /** 7.Add a course */
    public int addCourse(String courseCode, String courseName, String subjectType, int AU, Faculty faculty) {
    	if (RM.getCourse(courseCode) != null) {
    		// course exists
    		return 0;
    	} else if (AU < 1) {
    		// AU 0 or below
    		return -1;
    	} else {
    		Course course = new Course(courseCode, courseName, subjectType, AU, faculty);
    		RM.addCourse(faculty, course);
    		return 1;
    	}
    }
    
    /** Add an Index */
    public int addIndex(String strIndex, int slots, Course course) {
    	if (RM.getIndex(strIndex) != null) {
    		// index exists
    		return 0;
    	} else if (slots < 0) {
    		// negative slots
    		return -1;
    	} else {
    		Index index = new Index(strIndex, slots, course);
    		course.addIndex(index);
    		return 1;
    	}
    }
    
    /** Add lesson to index */
//    public int addLesson(Index index, int slots, Course course) {
//    	if (RM.getIndex(strIndex) != null) {
//    		// index exists
//    		return 0;
//    	} else if (slots < 0) {
//    		// negative slots
//    		return -1;
//    	} else {
//    		Index index = new Index(strIndex, slots, course);
//    		course.addIndex(index);
//    		return 1;
//    	}
//    }

    /** 8.Update Course info */
    public void updateCourseCode(String courseCode, String newCourseCode){
        Course course = RM.getCourse(courseCode);
        course.setCourseCode(newCourseCode);
    }

    public void updateCourseName(String courseCode,String newName) {

        Course course = RM.getCourse (courseCode);
        course.setCourseName(newName);
    }

    public void updateSubjectType(String courseCode, String newSubjectType){
        Course course = RM.getCourse (courseCode);
        course.setSubjectType(newSubjectType);
    }

    public void updateCourseAU(String courseCode, int newAU){
        Course course = RM.getCourse (courseCode);
        course.setAU(newAU);
    }

    public void updateCourseFaculty(String courseCode, Faculty newFaculty) {
        Course course = RM.getCourse (courseCode);
        course.setFaculty(newFaculty);
    }

    /** 10.Update Index info */
    public void updateIndexCode(String index, String newIndex) {
        Index indexObject = RM.getIndex(index);
        indexObject.setIndex(newIndex);
    }

    public void updateIndexVac(String index, int newVacancy) {
        Index indexObject = RM.getIndex(index);
        int indexStudentSize = indexObject.getStudentSize();
        indexObject.setTotalSlots(newVacancy+indexStudentSize);
    }

    public void updateIndexCourse(String index, String newCourseCode){
        Index indexObject = RM.getIndex(index);
        indexObject.setCourseCode(newCourseCode);
    }

    /** 11.add a student */
    public int addStudent(String username, String fullName, String gender, String nationality, String matricNum, String faculty, int yearOfStudy) {
    	Faculty fac = RM.getFaculty(faculty);
        if (RM.getUser(username) != null) {
        	// username exists
        	return 0;
        } else if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")){
        	// invalid gender
        	return -1;
        } else if (fac == null) {
        	// faculty not found
        	return -2;
        } else if (yearOfStudy < 1 || yearOfStudy > 4) {
        	// invalid year
        	return -3;
        } else {
        	Student student = new Student(username, fullName, gender, nationality, matricNum, fac, yearOfStudy);
        	try {
        		RM.addUser(student);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	return 1;
        }
    }
    

    /** 12.Edit student access period */
    public int editAccessPeriod(String facultyName, String startDateTime, String endDateTime) {
    	LocalDateTime start = TimeManager.strToDateTime(startDateTime);
    	LocalDateTime end = TimeManager.strToDateTime(endDateTime);
    	if (start == null || end == null) {
    		// DateTime string not in correct format
    		return 0;
    	} else if (start.isAfter(end)) {
        	// start > end
        	return -1;
        } else {
        	Faculty faculty = RM.getFaculty(facultyName);
        	if (faculty == null) {
        		// faculty not found
        		return -2;
        	}
            faculty.setRegistrationTime(start, end);
            return 1;
        }
    }


    /** Get db info */
    public Dictionary<String, String> getDatabaseInfo(){
        Dictionary<String, String> dbInfo = new Hashtable<>();
        dbInfo.put( "facultySize", Integer.toString( RM.getAllFaculties().size() ) );
        dbInfo.put( "studentSize", Integer.toString( RM.getAllStudents().size() ) );
        dbInfo.put( "courseSize", Integer.toString( RM.getAllCourses().size() ) );
        return dbInfo;
    }
}
