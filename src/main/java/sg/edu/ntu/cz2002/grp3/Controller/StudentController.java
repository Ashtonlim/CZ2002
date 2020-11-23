package sg.edu.ntu.cz2002.grp3.Controller;


// import java.time.LocalTime;
import sg.edu.ntu.cz2002.grp3.Entity.Course;
import sg.edu.ntu.cz2002.grp3.Entity.Faculty;
import sg.edu.ntu.cz2002.grp3.Entity.Index;
import sg.edu.ntu.cz2002.grp3.Entity.Student;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class StudentController {
    private final RecordManager RM;
    private final Student student;
    public StudentController(RecordManager RM, Student student) {
        this.RM = RM;
        this.student = student;
    }

    public ArrayList<Index> getCourseReg(Student student) {
        return student.getIndexList();
    }


    public int addIndexToStudent(String indexCode){
        Index index = RM.getIndex(indexCode);
        if (index == null) {
            return 20;
        }

        return index.addToStudentList(student);
    }


    public ArrayList<Index> getVacanciesOfCourse(String courseCode) {
        Course course = RM.getCourse(courseCode);
        if (course != null) {
            return new ArrayList<>(course.getIndexList());
        } else {
            return null;
        }
    }

    public ArrayList<String> getIndexListForPrinting(){
        ArrayList<Index> indexList = getCourseReg(student);
        ArrayList<String> stringIndexList = new ArrayList<>();

        // Print indexes of the student.
        for (Index index : indexList) {
            String string = index.getCourseName() + " - " + index.getIndex();
            stringIndexList.add(string);
        }

        return stringIndexList;
    }

    public void dropIndex(int pos){
        ArrayList<Index> indexList = getCourseReg(student);
        Index indexToDrop = indexList.get(pos - 1);
        indexToDrop.removeFromStudentList(student);
    }

    public String[][] getVacanciesForPrinting(String courseCode){
        ArrayList<Index> indexList = getVacanciesOfCourse(courseCode);

        if (indexList == null) {
            System.out.println("Course does not exist");
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

    public String[][] getTimeTableForPrinting(int oddEven){
        return student.getTableTimeForPrinting(oddEven);
    }


    public String[][] getProcessedIndexListForPrinting(){
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
     * Status code: 1 - success | 0 - Clash | -1 = oldIndex not in the same course
     * as new index. -2 - no vacancy in new index
     */
    public int changeIndex(int oldIndexPos, int newIndexPos) {
        Index oldIndex = student.getIndexList().get(oldIndexPos);
        Index newIndex = oldIndex.getIndexesOfCourse().get(newIndexPos);

        if (oldIndex == newIndex){
            return -22;
        }

        if (!oldIndex.getCourseCode().equals(newIndex.getCourseCode())) {
            return -21;
        }

        oldIndex.removeFromStudentList(student);
        return newIndex.addToStudentList(student);
    }

    public Dictionary<String, String> getStudentDetails(){
        Dictionary<String, String> res = new Hashtable<>();
        res.put("fullName", student.getFullName());
        res.put("faculty", student.getFacultyName());
        res.put("regAU", Integer.toString(student.getRegAU()));
        res.put("courses", Integer.toString( student.getIndexList().size() ));
        return res;
    }

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
        sourceIndex.removeFromStudentList(source);
        targetIndex.removeFromStudentList(target);
        sourceIndex.addToStudentList(target);
        targetIndex.addToStudentList(source);

        return 1;

    }


    public ArrayList<String> getCourseIndexesForPrinting(int pos){
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

    public String[][] getIndexListFromFacultyForPrinting(String facultyName){
        Faculty faculty = RM.getFaculty(facultyName);

        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        if (faculty == null){
            return new String[0][0];
        }

        int row = 0;
        for (Course course : faculty.getCourseList()){
            for (Index index : course.getIndexList()){
                ArrayList<String> temp2 = new ArrayList<>();
                temp2.add(" " + index.getCourseCode() + " ");
                temp2.add(" " + index.getCourseName() + " ");
                temp2.add(" " + index.getIndex() + " ");
                temp.add(temp2);
            }
            if (course.getIndexList().size() != 0){
                row += 1;
            }
        }

        String[][] res = new String[row+1][3];
        res[0][0] = " Course Code ";
        res[0][1] = " Course Name ";
        res[0][2] = " Index No. ";

        for (int i = 1; i < row + 1; i++){
            for (int j = 0; j < 3; j++){
                res[i][j] = temp.get(i-1).get(j);
            }
        }

        return res;
    }

    public boolean changePassword(String oldPassword, String newPassword){
        return LoginManager.changePassword(student, oldPassword, newPassword);
    }
}
