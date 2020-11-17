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
        this.studentList = studentList;
        this.course = course;
    }

    public String getIndex() {
    	return index;
    }

    public void addToWaitList(Student student){
        waitList.add(student);
    }

    public ArrayList<Student> getWaitList(){
        return waitList;
    }

    public void addToStudentList(Student student){
        studentList.add(student);
    }

    public ArrayList<Student> getStudentList(){
        return waitList;
    }

    public Course getCourse(){
        return course;
    }


    public void printWaitList() {
        // System.out.println();
        for (int i = 0; i < waitList.size(); i++) {
        	waitList.get(i).printStudentInfo();
        }
    }

    public void printStudentList() {
        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).printStudentInfo();
        }
    }

    public int getVacancy() {
        return totalSlots - studentList.size();
    }

    public void setVacancy(int slots) {
        this.totalSlots = slots;
    }

}
