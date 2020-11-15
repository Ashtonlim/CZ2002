import java.io.Serializable;
import java.util.ArrayList;

public class Index implements Serializable {
    private String index;
    private int slots;
    private ArrayList<Student> waitlist;
    private ArrayList<Student> studentList;

    public Index(String index, int slots, ArrayList<Student> waitlist, ArrayList<Student> studentList) {
        this.index = index;
        this.slots = slots;
        this.waitlist = waitlist;
        this.studentList = studentList;
    }

    public void printWaitList() {
        // System.out.println();
        for (int i = 0; i < waitlist.size(); i++) {
            waitlist.get(i).printStudentInfo();
        }
    }

    public void printStudentList() {
        for (int i = 0; i < studentList.size(); i++) {
            studentList.get(i).printStudentInfo();
        }
    }

    public int getVacancy() {
        return slots;
    }

    public void setVacancy(int slots) {
        this.slots = slots;
    }


}
