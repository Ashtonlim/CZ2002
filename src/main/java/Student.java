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

    public Student(String username, String password, String fullName, String gender, String nationality, String matricNum, Faculty faculty, int yearOfStudy, int regAU) {
        super(username, password, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
        faculty.addStudent(this);
    }

    public void printStudentInfo() {
        System.out.println(matricNum + " taking " + facultyName+ " in year " + yearOfStudy);
    }
    
    /** get student indo */
    public ArrayList<Index> getIndexList(){
        return indexList;
    }

    public Faculty getFaculty() {
        return faculty;
    }
    
    /** set/modify student info */
    public void updateYearOfStudy(){
        this.yearOfStudy = 10;
    }

    public void addIndex(Index index){
        indexList.add(index);
    }

    public void addToWaitList(Index index){
        waitList.add(index);
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setUserName(String userName){ this.username = userName; }

    public void setPassword(String password){ this.password = password; }

    public void setFullName(String fullName){ this.fullName = fullName; }

    public void setGender(String gender){ this.gender = gender; }

    public void setMatricNum(String matricNum){ this.matricNum = matricNum; }

    public void setFacultyName(String facultyName){ this.facultyName = facultyName; }

    public void setYearOfStudy(int yearOfStudy){ this.yearOfStudy = yearOfStudy; }

    public void setRegAU(int regAU){ this.regAU = regAU; }

    public void setNationality(String nationality) { this.nationality = nationality; }

}
