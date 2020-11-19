import java.io.Serializable;
import java.util.ArrayList;

public class Index implements Serializable {
    private String index;
    private int totalSlots;
    private ArrayList<Student> waitList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private Course course;

    public Index(String index, int slots, Course course) {
        this.index = index;
        this.totalSlots = slots;
        this.course = course;
        course.addIndex(this);
    }


    public String getIndex() {
    	return index;
    }

    public void addToWaitList(Student student){
        waitList.add(student);
        student.addToWaitList(this);
    }

    public ArrayList<Student> getWaitList(){
        return waitList;
    }

    public boolean addToStudentList(Student student){
        studentList.add(student);
        return student.addIndex(this);
    }

    public ArrayList<Student> getStudentList(){
        return studentList;
    }
    
    public void addToLessonList(Lesson lesson){
    	lessonList.add(lesson);
    }
    
    public ArrayList<Lesson> getLessonList(){
        return lessonList;
    }

    public Course getCourse(){
        return course;
    }
    
    public void printLessonList() {
        for (int i = 0; i < lessonList.size(); i++) {
        	lessonList.get(i).printLessonInfo();
        }
    }

    public int getVacancy() {
        return totalSlots - studentList.size();
    }
    
    public int getStudentSize(){
        return studentList.size();
    }
    
    public void setTotalSlots(int slots) {
        this.totalSlots = slots;
    }
    
    public void setVacancy(int slots) {
        this.totalSlots = slots;
    }

    public void setIndex(String newIndex){
        this.index=newIndex;
    }

    public void setCourseCode(String newCourseCode){
        this.course.setCourseCode(newCourseCode);
    }

    public String getCourseName() {
        return course.getCourseName();
    }
}
