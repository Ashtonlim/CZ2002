public class Courses {
    private Index[] indexLst;
    private String courseCode;
    private String school;
    private String subjectType;
    private String classType;
    private int AU;
    private int courseVacancy;

    public Courses(Index[] indexLst, String courseCode, String school, String subjectType, String classType, int AU, int courseVacancy) {
        this.indexLst = indexLst;
        this.courseCode = courseCode;
        this.school = school;
        this.subjectType = subjectType;
        this.classType = classType;
        this.AU = AU;
        this.courseVacancy = courseVacancy;
    }

    public int getVacancy() {
        return courseVacancy;
    }

    public void setVacancy(int vacancy) {
        this.courseVacancy = vacancy;
    }
    public void printVacancy(){
        System.out.println("This course has" + getVacancy());
    }
}



