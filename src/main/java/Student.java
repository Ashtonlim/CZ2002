import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Student extends User implements Serializable {
    private String matricNum;
    private String facultyName;
    private int yearOfStudy;
    private ArrayList<Index> indexList = new ArrayList<>();
    private ArrayList<Index> waitList = new ArrayList<>();
    private Faculty faculty;
    private int regAU;

    public Student(String username, String password, String fullName, String gender, String matricNum, Faculty faculty, int yearOfStudy, int regAU) {
        super(username, password, fullName, gender);
        this.matricNum = matricNum;
        this.facultyName = facultyName;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
        faculty.addStudent(this);

    }

    public void printStudentInfo() {
        System.out.println(matricNum + " taking " + facultyName+ " in year " + yearOfStudy);
    }

    public void updateYearOfStudy(){
        this.yearOfStudy = 10;
    }

    public void addIndex(Index index){
        indexList.add(index);
    }

    public void addToWaitList(Index index){
        waitList.add(index);
    }

    public ArrayList<Index> getIndexList(){
        return indexList;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Faculty getFaculty() {
        return faculty;
    }


}
