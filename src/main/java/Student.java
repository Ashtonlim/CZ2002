import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student extends User implements Serializable {
    private String matricNum;
    private String facultyName;
    private int yearOfStudy;
    private ArrayList<Index> indexList;
    private int regAU;

    public Student(String username, String password, String fullName, String gender, String matricNum, String facultyName, Object indexes, int yearOfStudy, int regAU) {
        super(username, password, fullName, gender);
        this.matricNum = matricNum;
        this.facultyName = facultyName;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;

    }

    public void printStudentInfo() {
        System.out.println(matricNum + " taking " + facultyName+ " in year " + yearOfStudy);
    }

    public void updateYearOfStudy(){
        this.yearOfStudy = 10;
    }

    public void setIndexList(ArrayList<Index> indexList){
        this.indexList = indexList;
    }

    public ArrayList<Index> getIndexList(){
        return indexList;
    }
}
