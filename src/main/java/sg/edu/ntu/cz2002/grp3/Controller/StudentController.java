package sg.edu.ntu.cz2002.grp3.Controller;

// import java.time.LocalTime;
import sg.edu.ntu.cz2002.grp3.Entity.Course;
import sg.edu.ntu.cz2002.grp3.Entity.Faculty;
import sg.edu.ntu.cz2002.grp3.Entity.Index;
import sg.edu.ntu.cz2002.grp3.Entity.Student;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * The Class StudentController handles the logic for the options selected in
 * student view, which includes adding, changing, dropping indexes, checking
 * vacancies, and more.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class StudentController {

    /** The record manager. */
    private final RecordManager RM;

    /** The student. */
    private final Student student;

    /**
     * Instantiates a new student controller.
     *
     * @param RM      the rm
     * @param student the student
     */
    public StudentController(RecordManager RM, Student student) {
        this.RM = RM;
        this.student = student;
    }

    /**
     * Gets the course reg.
     *
     * @param student the student
     * @return the course reg
     */
    public ArrayList<Index> getCourseReg(Student student) {
        return student.getIndexList();
    }

    /**
     * Adds the index to student.
     *
     * @param indexCode the index code
     * @return -20: index not found, -22: student has the course already
     * 1: success
     */
    public int addIndexToStudent(String indexCode) {
        Index index = RM.getIndex(indexCode);
        if (index == null) {
            return -20;
        }

        if (student.hasCourse(index.getCourseCode())) {
            return -22;
        }

        return index.addToStudentList(student);
    }

    /**
     * Gets the vacancies of course.
     *
     * @param courseCode the course code
     * @return the vacancies of course
     */
    public ArrayList<Index> getVacanciesOfCourse(String courseCode) {
        Course course = RM.getCourse(courseCode);
        if (course != null) {
            return new ArrayList<>(course.getIndexList());
        } else {
            return null;
        }
    }

    /**
     * Gets the index list for printing.
     *
     * @return the index list for printing
     */
    public ArrayList<String> getIndexListForPrinting() {
        ArrayList<Index> indexList = getCourseReg(student);
        ArrayList<String> stringIndexList = new ArrayList<>();

        // Print indexes of the student.
        for (Index index : indexList) {
            String string = index.getCourseName() + " - " + index.getIndex();
            stringIndexList.add(string);
        }

        return stringIndexList;
    }

    /**
     * Drop index.
     *
     * @param pos the pos of index in indexList to drop.
     */
    public void dropIndex(int pos) {
        ArrayList<Index> indexList = getCourseReg(student);
        Index indexToDrop = indexList.get(pos - 1);
        indexToDrop.removeFromStudentList(student);
    }

    /**
     * Gets the vacancies for printing.
     *
     * @param courseCode the course code
     * @return the vacancies for printing in 2D array.
     */
    public String[][] getVacanciesForPrinting(String courseCode) {
        ArrayList<Index> indexList = getVacanciesOfCourse(courseCode);

        if (indexList == null) {
            return new String[0][0];
        }

        String[][] res = new String[indexList.size() + 1][3];
        res[0][0] = " No. ";
        res[0][1] = " Index Number ";
        res[0][2] = " Vacancies ";

        for (int i = 1; i < indexList.size() + 1; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0 -> res[i][j] = " " + i + " ";
                    case 1 -> res[i][j] = " " + indexList.get(i - 1).getIndex() + " ";
                    case 2 -> res[i][j] = " " + indexList.get(i - 1).getVacancy() + " ";
                    default -> res[i][j] = " Error ";
                }
            }
        }

        return res;
    }

    /**
     * Gets the time table for printing.
     *
     * @param oddEven the odd even
     * @return the time table for printing in 2D array.
     */
    public String[][] getTimeTableForPrinting(int oddEven) {
        return student.getTableTimeForPrinting(oddEven);
    }

    /**
     * Gets the processed index list for printing.
     *
     * @return the processed index list for printing in 2D array.
     */
    public String[][] getProcessedIndexListForPrinting() {
        ArrayList<Index> indexList = student.getIndexList();
        String[][] res = new String[indexList.size() + 1][4];
        res[0][0] = " No. ";
        res[0][1] = " Course Code ";
        res[0][2] = " Course Name ";
        res[0][3] = " Course Index ";

        for (int i = 1; i < indexList.size() + 1; i++) {
            for (int j = 0; j < 4; j++) {
                switch (j) {
                    case 0 -> res[i][j] = " " + i + " ";
                    case 1 -> res[i][j] = " " + indexList.get(i - 1).getCourseCode() + " ";
                    case 2 -> res[i][j] = " " + indexList.get(i - 1).getCourseName() + " ";
                    case 3 -> res[i][j] = " " + indexList.get(i - 1).getIndex() + " ";
                    default -> res[i][j] = " Error ";
                }
            }
        }
        return res;
    }

    /**
     *
     * @param oldIndexPos the old index pos
     * @param newIndexPos the new index pos
     * @return 1: success | 0: Clash | -1: oldIndex not in the same course
     * as new index. -2: no vacancy in new index
     */
    public int changeIndex(int oldIndexPos, int newIndexPos) {
        Index oldIndex = student.getIndexList().get(oldIndexPos);
        Index newIndex = oldIndex.getIndexesOfCourse().get(newIndexPos);

        if (oldIndex == newIndex) {
            return -22;
        }

        if (!oldIndex.getCourseCode().equals(newIndex.getCourseCode())) {
            return -21;
        }

        int status = newIndex.addToStudentList(student);
        if (status != 1) { // Did not add successfully.
            return status;
        } else {
            oldIndex.removeFromStudentList(student);
            return 1;
        }

    }

    /**
     * Gets the student details.
     *
     * @return the student details
     */
    public Dictionary<String, String> getStudentDetails() {
        Dictionary<String, String> res = new Hashtable<>();
        res.put("fullName", student.getFullName());
        res.put("faculty", student.getFacultyName());
        res.put("regAU", Integer.toString(student.getRegAU()));
        res.put("courses", Integer.toString(student.getIndexList().size()));
        return res;
    }

    /**
     * Swop index.
     *
     * @param targetMatricNum the target matric num
     * @param targetPassword  the target password
     * @param indexPos        the index pos
     * @return -20: The matric number is invalid | -21: Invalid password | -22: partner has not registered for course.
     * -23: Index clash with student timetable | =24: Index clashes with partner timetable | -25 Both have same index.
     * 1: swop successful.
     */
    public int swopIndex(String targetMatricNum, String targetPassword, int indexPos) {
        Student source = student;
        ArrayList<Index> indexList = student.getIndexList();
        Index sourceIndex = indexList.get(indexPos - 1);

        Student target = RM.getStudent(targetMatricNum);
        if (target == null)
            return -20;
        if (!LoginManager.verifyLogin(target, targetPassword))
            return -21; // Check password of target Student

        boolean found = false;
        Index targetIndex = null;
        for (Index index : target.getIndexList()) {
            if (index.getCourseCode().equals(sourceIndex.getCourseCode())) {
                found = true;
                targetIndex = index;
                break;
            }
        }
        if (!found)
            return -22; // Target student does not have the Specific course index source student trying
                        // to swop.
        if (sourceIndex == targetIndex)
            return -25; // both same index
        if (source.getTimeTable().checkClash(targetIndex))
            return -23; // Source student clashes with new index
        if (target.getTimeTable().checkClash(sourceIndex))
            return -24; // Target student clashes with new index

        // Swop;
        sourceIndex.removeStudentWithoutFillingWithWaitList(source);
        targetIndex.removeStudentWithoutFillingWithWaitList(target);
        sourceIndex.addToStudentList(target);
        targetIndex.addToStudentList(source);

        return 1;

    }

    /**
     * Gets the course indexes for printing.
     *
     * @param pos the pos
     * @return the course indexes in 2D array for printing
     */
    public ArrayList<String> getCourseIndexesForPrinting(int pos) {
        ArrayList<Index> indexList = student.getIndexList();
        Index oldIndex = indexList.get(pos);

        // Print available indexes under same course
        ArrayList<String> stringOtherIndexList = new ArrayList<>();
        ArrayList<Index> otherIndexes = oldIndex.getIndexesOfCourse();

        for (Index otherIndex : otherIndexes) {
            String string;
            if (otherIndex == oldIndex) {
                string = otherIndex.getCourseName() + " - " + otherIndex.getIndex() + " (Current index)";
            } else {
                string = otherIndex.getCourseName() + " - " + otherIndex.getIndex();
            }
            stringOtherIndexList.add(string);
        }

        return stringOtherIndexList;
    }

    /**
     * Gets the index list from faculty for printing.
     *
     * @param facultyName the faculty name
     * @return the index list in 2D array from faculty for printing
     */
    public String[][] getIndexListFromFacultyForPrinting(String facultyName) {
        Faculty faculty = RM.getFaculty(facultyName);

        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        if (faculty == null) {
            return new String[0][0];
        }

        int row = 0;
        for (Course course : faculty.getCourseList()) {
            for (Index index : course.getIndexList()) {
                ArrayList<String> temp2 = new ArrayList<>();
                temp2.add(" " + index.getCourseCode() + " ");
                temp2.add(" " + index.getCourseName() + " ");
                temp2.add(" " + index.getIndex() + " ");
                temp2.add(" " + index.getVacancy() + "/" + index.getTotalSlots() + " ");
                temp.add(temp2);
                row += 1;
            }
        }
        String[][] res = new String[row + 1][4];
        res[0][0] = " Course Code ";
        res[0][1] = " Course Name ";
        res[0][2] = " Index No. ";
        res[0][3] = " Vacancies ";

        for (int i = 1; i < row + 1; i++) {
            for (int j = 0; j < 4; j++) {
                res[i][j] = temp.get(i - 1).get(j);
            }
        }

        return res;
    }

    /**
     * Change password.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true, if successful
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        return LoginManager.changePassword(student, oldPassword, newPassword);
    }
}
