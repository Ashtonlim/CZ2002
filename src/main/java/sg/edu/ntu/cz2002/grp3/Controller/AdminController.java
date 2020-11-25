package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.Admin;
import sg.edu.ntu.cz2002.grp3.Entity.Student;
import sg.edu.ntu.cz2002.grp3.Entity.Faculty;
import sg.edu.ntu.cz2002.grp3.Entity.Course;
import sg.edu.ntu.cz2002.grp3.Entity.Index;
import sg.edu.ntu.cz2002.grp3.Entity.Lesson;

import sg.edu.ntu.cz2002.grp3.util.IO;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The Class AdminController which handles all the functions available to the
 * adminView.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class AdminController {

    /** The Record Manager for getting objects from database. */
    private final RecordManager RM;

    /** The admin that is logged in. */
    private final Admin admin;

    /**
     * Instantiates a new admin controller.
     *
     * @param RM    the record manager
     * @param admin the admin
     */
    public AdminController(RecordManager RM, Admin admin) {
        this.admin = admin;
        this.RM = RM;
    }

    /**
     * Check if a faculty exists.
     *
     * @param facultyName the faculty name
     * @return true, if successful
     */
    public boolean checkFacultyExists(String facultyName) {
        if (RM.getFaculty(facultyName) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if a course exists.
     *
     * @param courseCode the course code
     * @return true, if successful
     */
    public boolean checkCourseExists(String courseCode) {
        if (RM.getCourse(courseCode) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if an index exists.
     *
     * @param indexNo the index no
     * @return true, if successful
     */
    public boolean checkIndexExists(String indexNo) {
        if (RM.getIndex(indexNo) != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the starting and ending date time of the access period of a faculty.
     *
     * @param facultyName the faculty's name
     * @return the faculty's period in a dictionary
     */
    public Dictionary<String, String> getFacultyPeriod(String facultyName) {
        Faculty faculty = RM.getFaculty(facultyName);
        Dictionary<String, String> res = new Hashtable<>();
        if (faculty == null)
            return null;
        try {
            res.put("startDate", TimeManager.dateTimeToStr(faculty.getStartDate()));
            res.put("endDate", TimeManager.dateTimeToStr(faculty.getEndDate()));
        } catch (NullPointerException e) {
            res.put("0", "0");
            return res;
        }
        return res;
    }

    /**
     * Get course details.
     *
     * @param courseCode the course code
     * @return the course details in a dictionary
     */
    public Dictionary<String, String> getCourseDetails(String courseCode) {
        Dictionary<String, String> res = new Hashtable<>();
        Course course = RM.getCourse(courseCode);
        res.put("courseCode", course.getCourseCode());
        res.put("courseName", course.getCourseName());
        res.put("faculty", course.getFaculty().getName());
        res.put("subjectType", course.getSubjectType());
        res.put("AU", Integer.toString(course.getAU()));
        return res;
    }

    /**
     * Prints the all students in the system.
     */
    public void printAllStudents() {
        ArrayList<Student> studentList = RM.getAllStudents();
        if (studentList != null) {
            IO.printStudentList(studentList);
        }
    }

    /**
     * Prints the all courses in the system.
     */
    public void printAllCourses() {
        ArrayList<Course> courseList = RM.getAllCourses();
        if (courseList != null) {
            IO.printCourseList(courseList);
        }
    }

    /**
     * Edit student access period of a faculty.
     *
     * @param facultyName   the faculty name
     * @param startDateTime the start date time
     * @param endDateTime   the end date time
     * @return 0: Incorrect date time format | -1: start time is after or equals to
     *         end time | 1: access period change success
     */
    public int editAccessPeriod(String facultyName, String startDateTime, String endDateTime) {
        Faculty faculty = RM.getFaculty(facultyName);
        LocalDateTime start = TimeManager.strToDateTime(startDateTime);
        LocalDateTime end = TimeManager.strToDateTime(endDateTime);
        if (start == null || end == null) {
            return 0;
        } else if (start.isAfter(end) || start.equals(end)) {
            return -1;
        } else {
            faculty.setRegistrationTime(start, end);
            return 1;
        }
    }

    /**
     * Add a new student.
     *
     * @param username    the username
     * @param email       the email
     * @param fullName    the full name
     * @param gender      the gender
     * @param nationality the nationality
     * @param matricNum   the matriculation num
     * @param faculty     the faculty
     * @param yearOfStudy the year of study
     * @return 0: username exists | -1: invalid gender | -2: faculty not found | -3:
     *         invalid year of study | 1: new student created
     */
    public int addStudent(String username, String email, String fullName, String gender, String nationality,
            String matricNum, String faculty, int yearOfStudy) {
        Faculty fac = RM.getFaculty(faculty);
        if (RM.getUser(username) != null) {
            return 0;
        } else if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
            // invalid gender
            return -1;
        } else if (fac == null) {
            return -2;
        } else if (yearOfStudy < 1 || yearOfStudy > 4) {
            return -3;
        } else {
            Student student = new Student(username, email, fullName, gender, nationality, matricNum, fac, yearOfStudy);
            try {
                RM.addUser(student);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1;
        }
    }

    /**
     * Add a new course.
     *
     * @param courseCode  the course code
     * @param courseName  the course name
     * @param subjectType the subject type
     * @param AU          the number of AUs
     * @param facultyName the faculty name
     * @return 0: course already exists | -1: invalid AU | 1: course successfully
     *         added
     */
    public int addCourse(String courseCode, String courseName, String subjectType, int AU, String facultyName) {
        if (checkCourseExists(courseCode)) {
            return 0;
        } else if (AU < 1) {
            return -1;
        } else {
            Faculty faculty = RM.getFaculty(facultyName);
            Course course = new Course(courseCode, courseName, subjectType, AU, faculty);
            return 1;
        }
    }

    /**
     * Add an Index to a course.
     *
     * @param indexNo    the index number
     * @param slots      the vacancies
     * @param courseCode the course code
     * @return 0: index already exists | -1: slots are negative | 1: index
     *         successfully added
     */
    public int addIndex(String indexNo, int slots, String courseCode) {
        if (checkIndexExists(indexNo)) {
            return 0;
        } else if (slots < 0) {
            return -1;
        } else {
            Course course = RM.getCourse(courseCode);
            new Index(indexNo, slots, course);
            return 1;
        }
    }

    /**
     * Add a lesson to an index.
     *
     * @param type    the lesson type
     * @param day     the day of week
     * @param start   the start time
     * @param end     the end time
     * @param venue   the venue
     * @param oddEven odd or even week
     * @param indexNo the index number
     * @return -1: invalid day | -2: invalid odd even week option | -3: invalid time
     *         range | -4: clashes with other lessons | -5: time not in 30 min
     *         interval | 1: course successfully added
     */
    public int addLesson(String type, int day, String start, String end, String venue, int oddEven, String indexNo) {
        Index index = RM.getIndex(indexNo);

        if (!TimeManager.checkValidTimeInterval(start) || !TimeManager.checkValidTimeInterval(end)) {
            return -5;
        }
        if (day < 1 || day > 6) {
            // invalid day
            return -1;
        } else if (!(oddEven == 0 || oddEven == 1 || oddEven == 2)) {
            // invalid option
            return -2;
        }

        day = day - 1; // 0 -> Mon, 1 -> Tues

        try {
            // if weekly lessons
            if (oddEven == 2) {
                boolean isClashEven = checkLessonClash(index, day, 0, start, end);
                boolean isClashOdd = checkLessonClash(index, day, 1, start, end);
                if (!isClashEven && !isClashOdd) {
                    Lesson l1 = new Lesson(type, day, 0, start, end, venue, index);
                    Lesson l2 = new Lesson(type, day, 1, start, end, venue, index);
                    return 1;
                } else {
                    // lesson clashing
                    return -4;
                }
                // not weekly lessons
            } else {
                boolean isClash = checkLessonClash(index, day, oddEven, start, end);
                if (!isClash) {
                    Lesson l1 = new Lesson(type, day, oddEven, start, end, venue, index);
                    return 1;
                } else {
                    // lesson clashing
                    return -4;
                }
            }
        } catch (Exception e) {
            return -3;
        }
    }

    /**
     * Checks if lessons in an index clashes with other lessons.
     *
     * @param index   the index
     * @param day     the day of week
     * @param oddEven odd or even week
     * @param start   the start time
     * @param end     the end time
     * @return true, if there is clash
     */
    private boolean checkLessonClash(Index index, int day, int oddEven, String start, String end) {
        boolean isClash = false;
        for (Lesson tempLesson : index.getLessonList()) {
            if (day == tempLesson.getDayOfWeek()) {
                if (oddEven == tempLesson.getWeekType()) {
                    LocalTime startLT = TimeManager.strToTime(start);
                    LocalTime endLT = TimeManager.strToTime(end);
                    LocalTime startTemp = tempLesson.getStartTime();
                    LocalTime endTemp = tempLesson.getEndTime();
                    isClash = TimeManager.checkTimeClash(startLT, endLT, startTemp, endTemp);
                    if (isClash) {
                        return isClash;
                    }
                }
            }
        }
        return isClash;
    }

    /**
     * Print all lessons in an index.
     *
     * @param indexNo the index number
     */
    public void printLessonsInIndex(String indexNo) {
        System.out.println("=== Index " + indexNo + " Lessons ===");
        Index index = RM.getIndex(indexNo);
        ArrayList<Lesson> lessonList = index.getLessonList();
        if (lessonList != null) {
            IO.printLessonList(lessonList);
        } else {
            System.out.println("Lesson list is empty.");
        }
        System.out.println(" ");
    }

    /**
     * Update course code.
     *
     * @param courseCode    the old course code
     * @param newCourseCode the new course code
     * @return true, if successful
     */
    public boolean updateCourseCode(String courseCode, String newCourseCode) {
        Course course = RM.getCourse(courseCode);
        if (!checkCourseExists(newCourseCode)) {
            course.setCourseCode(newCourseCode);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update course name.
     *
     * @param courseCode the course code
     * @param newName    the new name
     */
    public void updateCourseName(String courseCode, String newName) {
        Course course = RM.getCourse(courseCode);
        course.setCourseName(newName);
    }

    /**
     * Update subject type.
     *
     * @param courseCode     the course code
     * @param newSubjectType the new subject type
     */
    public void updateSubjectType(String courseCode, String newSubjectType) {
        Course course = RM.getCourse(courseCode);
        course.setSubjectType(newSubjectType);
    }

    /**
     * Update course AU.
     *
     * @param courseCode the course code
     * @param newAU      the new AU
     * @return true, if successful
     */
    public boolean updateCourseAU(String courseCode, int newAU) {
        Course course = RM.getCourse(courseCode);
        if (newAU > 0) {
            course.setAU(newAU);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a course from database.
     *
     * @param courseCode the course code
     * @return true, if successful
     */
    public boolean removeCourse(String courseCode) {
        Course course = RM.getCourse(courseCode);
        return RM.removeCourse(course);
    }

    /**
     * Update Index number.
     *
     * @param indexNo  the old index number
     * @param newIndex the new index number
     * @return true, if successful
     */
    public boolean updateIndexNo(String indexNo, String newIndex) {
        Index index = RM.getIndex(indexNo);
        if (!checkIndexExists(newIndex)) {
            index.setIndex(newIndex);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update index vacancy.
     *
     * @param indexNo    the index number
     * @param newVacancy the new vacancy
     * @return true, if successful
     */
    public boolean updateIndexVac(String indexNo, int newVacancy) {
        Index index = RM.getIndex(indexNo);
        if (newVacancy >= 0) {
            index.setVacancy(newVacancy);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes an index from the database.
     *
     * @param indexNo the index number
     * @return true, if successful
     */
    public boolean removeIndex(String indexNo) {
        Index index = RM.getIndex(indexNo);
        return RM.removeIndex(index);
    }

    /**
     * Check vacancy for an index number.
     *
     * @param indexNo the index number
     * @return the vacancy if index can be found
     */
    public int checkVacancies(String indexNo) {
        Index index = RM.getIndex(indexNo);
        return (index != null) ? index.getVacancy() : -1;
    }

    /**
     * Print students registered under an index number.
     *
     * @param indexNo the index number
     */
    public String[][] printStudentListByIndex(String indexNo) {
        Index index = RM.getIndex(indexNo);

        if (index != null) {
            ArrayList<Student> studentList = index.getStudentList();
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            int row = 0;
            for (Student s : studentList) {
                ArrayList<String> temp2 = new ArrayList<>();
                temp2.add(" " + s.getMatricNum()+ " ");
                temp2.add(" " + s.getFullName() + " ");
                temp2.add(" " + s.getFacultyName() + " ");
                temp.add(temp2);
                row += 1;
            }

            String[][] res = new String[row + 1][3];
            res[0][0] = " Matric Number ";
            res[0][1] = " Full Name ";
            res[0][2] = " Faculty ";

            for (int i = 1; i < row + 1; i++) {
                for (int j = 0; j < 3; j++) {
                    res[i][j] = temp.get(i - 1).get(j);
                }
            }
            return res;
        } else {
            return new String[0][0];
        }

    }

    /**
     * Print all students registered under a course
     *
     * @param courseCode the course code
     * @return the 2D array to be printed
     */
    public String[][] printStudentListByCourse(String courseCode) {
        Course course = RM.getCourse(courseCode);

        if (course != null) {
            ArrayList<Student> studentList = getStudentList(course);
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            int row = 0;
            for (Student s : studentList) {
                ArrayList<String> temp2 = new ArrayList<>();
                temp2.add(" " + s.getMatricNum()+ " ");
                temp2.add(" " + s.getFullName() + " ");
                temp2.add(" " + s.getFacultyName() + " ");
                temp.add(temp2);
                row += 1;
            }

            String[][] res = new String[row + 1][3];
            res[0][0] = " Matric Number ";
            res[0][1] = " Full Name ";
            res[0][2] = " Faculty ";

            for (int i = 1; i < row + 1; i++) {
                for (int j = 0; j < 3; j++) {
                    res[i][j] = temp.get(i - 1).get(j);
                }
            }
            return res;
        } else {
            return new String[0][0];
        }
    }


    /**
     * Get student list from a course.
     *
     * @param course the course
     * @return the student list
     */
    private ArrayList<Student> getStudentList(Course course) {
        ArrayList<Student> studentList = new ArrayList<>();
        for (Index index : course.getIndexList()) {
            studentList.addAll(index.getStudentList());
        }
        return studentList;
    }

    /**
     * Change account password.
     *
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return true, if successful
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        return LoginManager.changePassword(admin, oldPassword, newPassword);
    }

    /**
     * Return string matrix for printing all courses and indexes.
     *
     * @param facultyName the faculty name
     * @return the index list from faculty for printing
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
     * Get database information for display in welcome page.
     *
     * @return the information as a dictionary
     */
    public Dictionary<String, String> getWelcomeInfo() {
        Dictionary<String, String> dbInfo = new Hashtable<>();
        dbInfo.put("fullName", admin.getFullName());
        dbInfo.put("facultySize", Integer.toString(RM.getAllFaculties().size()));
        dbInfo.put("studentSize", Integer.toString(RM.getAllStudents().size()));
        dbInfo.put("courseSize", Integer.toString(RM.getAllCourses().size()));
        return dbInfo;
    }

}
