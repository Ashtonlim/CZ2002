import java.io.Serializable;
import java.util.ArrayList;

public class Index implements Serializable {
    private String index;
    private int totalSlots;
    private ArrayList<Student> waitList;
    private ArrayList<Student> studentList;
    private ArrayList<Lesson> lessonList;

    public Index(String index, int slots, ArrayList<Student> waitlist, ArrayList<Student> studentList) {
        this.index = index;
        this.totalSlots = slots;
        this.waitList = waitlist;
        this.studentList = studentList;
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
