import java.util.*;
import java.time.LocalDateTime;

public class AdminController {
    private final RecordManager RM;

    public AdminController(RecordManager RM){
        this.RM = RM;
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
    public Faculty getFaulty(String facultyName){
        return RM.getFaculty(facultyName);
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
    public void addCourse(String courseCode, String courseName, String subjectType, int AU, Faculty faculty) {
        Course course = new Course(courseCode, courseName,subjectType,AU,faculty);
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        course.setSubjectType(subjectType);
        course.setAU(AU);
        course.setFaculty(faculty);
    }

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

    public void updateCourseFaculty(String courseCode,Faculty newFaculty) {
        Course course = RM.getCourse (courseCode);
        course.setFaculty(newFaculty);
    }

    /** 9.Add a Index */
    public void addIndex(String index, int slots, Course course) {
        Index indexObject = new Index(index, slots, course);
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
    public void addStudent(String username, String password, String fullName, String gender, String nationality, String matricNum, Faculty faculty, int yearOfStudy, int regAU){
        Student student = new Student(username, password, fullName, gender, nationality, matricNum, faculty, yearOfStudy, regAU);
    }

    /** 12.Edit student access period */
    public void editAccessPeriod(String facultyName, int yearStart, int monthStart, int dayStart, int hourStart, int minStart, int secondStart, int yearEnd, int monthEnd, int dayEnd, int hourEnd, int minEnd, int secondEnd) {
        LocalDateTime regStartDate = TimeManager.createDateTime(yearStart, monthStart, dayStart, hourStart, minStart, secondStart);
        LocalDateTime regEndDate = TimeManager.createDateTime (yearEnd, monthEnd, dayEnd, hourEnd, minEnd, secondEnd);
        Faculty faculty = RM.getFaculty(facultyName);
        faculty.setRegistrationTime(regStartDate, regEndDate);
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
