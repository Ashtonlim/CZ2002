import java.util.ArrayList;

public class Student extends User{
    private String matricNum;
    private String major;
    private String yearOfStudy;
    private String indexes;
    private int regAU;

    public Student(String username, String password, String fullName, String gender, String matricNum, String major, String indexes, String yearOfStudy, int regAU) {
        super(username, password, fullName, gender);
        this.matricNum = matricNum;
        this.major = major;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
    }

    public void printStudentInfo() {
        System.out.println(matricNum + " taking " + major+ " in year " + yearOfStudy);
    }

}
