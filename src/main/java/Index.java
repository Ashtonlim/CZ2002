import java.util.ArrayList;

public class Index {
    private String index;
    private int vacancy;
    private ArrayList<Student> waitlist;
    private ArrayList<Student> studentList;

    public Index(String index, int vacancy, ArrayList<Student> waitlist, ArrayList<Student> studentList) {
        this.index = index;
        this.vacancy = vacancy;
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
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }


}
