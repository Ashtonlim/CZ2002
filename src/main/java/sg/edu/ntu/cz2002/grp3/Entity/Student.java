package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;
import java.util.ArrayList;

import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;

public class Student extends User implements Serializable {
    private String matricNum;
    private int yearOfStudy;
//    private ArrayList<Index> indexList = new ArrayList<>();
    private ArrayList<Index> waitList = new ArrayList<>();
    private Faculty faculty;
    private int regAU;
    private final TimeTable timeTable = new TimeTable();

    /** constructor for loading dummy data */
    public Student(String username, String password, String fullName, String gender, String nationality,
            String matricNum, Faculty faculty, int yearOfStudy, int regAU) {
        super(username, password, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
        this.faculty = faculty;
        faculty.addStudent(this);
    }

    /**
     * constructor for admin to add new student. password is default and regAU = 0
     */
    public Student(String username, String fullName, String gender, String nationality, String matricNum,
            Faculty faculty, int yearOfStudy) {
        super(username, LoginManager.defPassword, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.yearOfStudy = yearOfStudy;
        this.regAU = 0;
        this.faculty = faculty;
        faculty.addStudent(this);
    }

    /** get student indo */
    public ArrayList<Index> getIndexList() {
        return timeTable.getIndexList();
    }

    public int getRegAU() {
        int totalAU = 0;
        for (Index index : getIndexList()) {
            totalAU += index.getAU();
        }
        return totalAU;
    }

    public String getFacultyName() {
        return faculty.getName();
    }

    public int getNumOfWaitingList() {
        return waitList.size();
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public String getMatricNum() {
        return matricNum;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

    /** set/modify student info */
    public void updateYearOfStudy() {
        this.yearOfStudy = 10;
    }

    public boolean addIndex(Index index) {

        if (hasIndex(index)) {
            System.out.println("Debug: Already registered index " + index.getIndex());
            return false;
        }

        // if clashes when adding to timetable
        if (timeTable.addIndex(index)) {
//                indexList.add(index);
            System.out.println("Debug: Added to timetable successfully.");
            return true;
        } else {
            System.out.println("System error (illegal operation): Unhandled clashes in timetable. Aborting...");
            return false;
        }

    }

    public boolean removeIndex(Index index) {
        if (!hasIndex(index)) {
            System.out.println("Debug: Student does not have index " + index.getIndex());
            return false;
        }

        // if clashes when adding to timetable
        if (timeTable.removeIndex(index)) {
//                indexList.remove(index);
            System.out.println("Debug: Removed index " + index.getIndex());
            return true;
        }

        return false;
    }

    public boolean removeIndex(String indexStr) {
        Index index = hasIndex(indexStr);

        if (index == null) {
            System.out.println("Debug: Student does not have index " + indexStr);
            return false;
        }

        try {
            // if clashes when adding to timetable
            if (timeTable.removeIndex(index)) {
//                indexList.remove(index);
                System.out.println("Debug Removed index " + index.getIndex());
                return true;
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return false;
    }

    public Index hasIndex(String index) {
        for (Index i : getIndexList()) {
            if (i.getIndex().equals(index)) {
                return i;
            }
        }
        return null;
    }

    public boolean hasIndex(Index index) {
        for (Index i : getIndexList()) {
            if (i.getIndex().equals(index.getIndex())) {
                return true;
            }
        }
        return false;
    }

    public void addToWaitList(Index index) {
        waitList.add(index);
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMatricNum(String matricNum) {
        this.matricNum = matricNum;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public void setRegAU(int regAU) {
        this.regAU = regAU;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

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
