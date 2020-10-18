public class Student {
    private String matricNum;
    private String major;
    private String yearOfStudy;
//    private Course[] regCourses;
    private int regAU;

    public Student(String matricNum, String major, String yearOfStudy, int regAU) {
        this.matricNum = matricNum;
        this.major = major;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
    }

    public void printStudentInfo() {
        System.out.println(matricNum + " taking " + major+ " in year " + yearOfStudy);
    }

}
