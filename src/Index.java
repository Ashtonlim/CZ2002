public class Index {
    private String index;
    private int vacancy;
    private Student[] waitlist;
    private Student[] studentList;

    public Index(String index, int vacancy, Student[] waitlist, Student[] studentList) {
        this.index = index;
        this.vacancy = vacancy;
        this.waitlist = waitlist;
        this.studentList = studentList;
    }

    public void printWaitList() {
        // System.out.println();
        for (int i = 0; i < waitlist.length; i++) {
            waitlist[i].printStudentInfo();
        }
    }

    public void printStudentList() {
        for (int i = 0; i < studentList.length; i++) {
            studentList[i].printStudentInfo();
        }
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }


}
