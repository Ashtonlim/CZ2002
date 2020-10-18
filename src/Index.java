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
        for (int i = 0; i < waitlist.length; i++) {
//            System.out.println();
            waitlist[i].printStudentInfo();
        }
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

//    public printStudentList() {
//        return studentList;
//    }
}
