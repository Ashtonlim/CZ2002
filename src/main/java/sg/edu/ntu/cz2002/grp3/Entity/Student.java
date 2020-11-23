package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;
import java.util.ArrayList;

import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

public class Student extends User implements Serializable {
    private final String matricNum;
    private final String email;
    private final int yearOfStudy;
    private final Faculty faculty;
    private final int regAU;
    private final TimeTable timeTable = new TimeTable();

    /** constructor for loading dummy data */
    public Student(String username, String email, String password, String fullName, String gender, String nationality,
            String matricNum, Faculty faculty, int yearOfStudy, int regAU) {
        super(username, password, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
        try {
            faculty.addStudent(this);
        } catch (Exception ignored){}
        this.faculty = faculty;
    }

    /**
     * constructor for admin to add new student. password is default and regAU = 0
     */
    public Student(String username, String email, String fullName, String gender, String nationality, String matricNum,
            Faculty faculty, int yearOfStudy){
        super(username, LoginManager.defPassword, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.regAU = 0;
        try {
            faculty.addStudent(this);
        } catch (IllegalMethodAccessException ignored){}
        this.faculty = faculty;
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

    /** set/modify student info */

    public boolean hasIndex(Index index) {
        for (Index i : getIndexList()) {
            if (i.getIndex().equals(index.getIndex())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCourse(String courseCode) {
        for (Index index : getIndexList()) {
            if (index.getCourseCode().equals(courseCode)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasFaulty(){
        return faculty != null;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String[][] getTableTimeForPrinting(int oddEven){
        return timeTable.processTimeTable(oddEven);
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

    public String getEmail() {
        return email;
    }

    public String getFacultyName() {
        return faculty.getName();
    }


    public String getMatricNum() {
        return matricNum;
    }

    public TimeTable getTimeTable() {
        return timeTable;
    }

}
