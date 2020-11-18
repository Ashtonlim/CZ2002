import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Student extends User implements Serializable {
    private String matricNum;
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

    
    /** get student indo */
    public ArrayList<Index> getIndexList(){
        return indexList;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public int getRegAU(){
        return regAU;
    }

    public String getFacultyName(){
        return faculty.getName();
    }

    public int getNumOfCourses(){
        return indexList.size();
    }

    public int getNumOfWaitingList(){
        return waitList.size();
    }

    public int getYearOfStudy(){
        return yearOfStudy;
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

    public void setYearOfStudy(int yearOfStudy){ this.yearOfStudy = yearOfStudy; }

    public void setRegAU(int regAU){ this.regAU = regAU; }

    public void setNationality(String nationality) { this.nationality = nationality; }



    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
