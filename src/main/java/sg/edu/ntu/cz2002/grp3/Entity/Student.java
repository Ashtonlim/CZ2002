package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;
import java.util.ArrayList;

import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

/**
 * Represents a student in the school. Contains student's details and a
 * timetable to show their current registered schedule.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class Student extends User implements Serializable {

    /** The matriculation number. */
    private final String matricNum;

    /** The email for receiving notification from the system. */
    private final String email;

    /** The year of study. */
    private int yearOfStudy;

    /** The faculty of the student. */
    private final Faculty faculty;

    /** The total number of AUs registered. */
    private int regAU;

    /** The timetable to show currently registered indexes. */
    private final TimeTable timeTable = new TimeTable();

    /**
     * Constructor for loading dummy data. Takes in all fields including password.
     *
     * @param username    the username
     * @param email       the email
     * @param password    the password
     * @param fullName    the full name
     * @param gender      the gender
     * @param nationality the nationality
     * @param matricNum   the matriculation number
     * @param faculty     the faculty
     * @param yearOfStudy the year of study
     * @param regAU       the total registered AU
     */
    public Student(String username, String email, String password, String fullName, String gender, String nationality,
            String matricNum, Faculty faculty, int yearOfStudy, int regAU) {
        super(username, password, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.email = email;
        this.yearOfStudy = yearOfStudy;
        this.regAU = regAU;
        try {
            faculty.addStudent(this);
        } catch (Exception ignored) {
        }
        this.faculty = faculty;
    }

    /**
     * Constructor for admin to add new student. Password will be set to system's
     * default and AU set to 0.
     *
     * @param username    the username
     * @param email       the email
     * @param fullName    the full name
     * @param gender      the gender
     * @param nationality the nationality
     * @param matricNum   the matriculation number
     * @param faculty     the faculty
     * @param yearOfStudy the year of study
     */
    public Student(String username, String email, String fullName, String gender, String nationality, String matricNum,
            Faculty faculty, int yearOfStudy) {
        super(username, LoginManager.defPassword, fullName, gender, nationality);
        this.matricNum = matricNum;
        this.email = email;
        this.yearOfStudy = yearOfStudy;

        try {
            faculty.addStudent(this);
        } catch (IllegalMethodAccessException ignored) {
        }
        this.faculty = faculty;
    }

    public ArrayList<Index> getIndexList() {
        return timeTable.getIndexList();
    }

    /**
     * Gets the total registered AU.
     *
     * @return the total registered AU
     */
    public int getRegAU() {
        int totalAU = 0;
        for (Index index : getIndexList()) {
            totalAU += index.getAU();
        }
        return totalAU;
    }

    /**
     * Checks whether student is registered under an index.
     *
     * @param index the index
     * @return true, if successful
     */
    public boolean hasIndex(Index index) {
        for (Index i : getIndexList()) {
            if (i.getIndex().equals(index.getIndex())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether student is registered under a course.
     *
     * @param courseCode the course code
     * @return true, if successful
     */
    public boolean hasCourse(String courseCode) {
        for (Index index : getIndexList()) {
            if (index.getCourseCode().equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether student belongs to a faculty.
     *
     * @return true, if successful
     */
    public boolean hasFaculty() {
        return faculty != null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the table time for printing.
     *
     * @param oddEven odd or even week
     * @return the timetable for printing
     */
    public String[][] getTableTimeForPrinting(int oddEven) {
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
