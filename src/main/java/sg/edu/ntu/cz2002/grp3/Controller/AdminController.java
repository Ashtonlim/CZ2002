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

public class AdminController {
    private final RecordManager RM;
    private final Admin admin;
    
    public AdminController(RecordManager RM, Admin admin) {
        this.admin = admin;
        this.RM = RM;
    }


    public boolean checkFacultyExists(String facultyName) {
    	if (RM.getFaculty(facultyName) != null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    public boolean checkCourseExists(String courseCode) {
    	if (RM.getCourse(courseCode) != null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    public boolean checkIndexExists(String indexNo) {
    	if (RM.getIndex(indexNo) != null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    /** get faculty details */
    public Dictionary<String, String> getFacultyPeriod(String facultyName){
    	Faculty faculty = RM.getFaculty(facultyName);
    	Dictionary<String, String> res = new Hashtable<>();
    	if (faculty == null) return null;
        try {
        	res.put("startDate", TimeManager.dateTimeToStr(faculty.getStartDate()));
        	res.put("endDate", TimeManager.dateTimeToStr(faculty.getEndDate()));
        } catch (NullPointerException e) {
        	res.put("0", "0");
        	return res;
        }
        return res;
    }
    
    /** get course details */
    public Dictionary<String, String> getCourseDetails(String courseCode){
        Dictionary<String, String> res = new Hashtable<>();
        Course course = RM.getCourse(courseCode);
        res.put("courseCode", course.getCourseCode());
        res.put("courseName", course.getCourseName());
        res.put("faculty", course.getFaculty().getName());
        res.put("subjectType", course.getSubjectType());
        res.put("AU", Integer.toString(course.getAU()));
        return res;
    }

    public void printAllStudents() {
    	ArrayList<Student> studentList = RM.getAllStudents();
    	if (studentList != null) {
    		IO.printStudentList(studentList);
    	}
    }
    
    public void printAllCourses() {
    	ArrayList<Course> courseList = RM.getAllCourses();
    	if (courseList != null) {
    		IO.printCourseList(courseList);
    	}
    }
    
    public void printIndexList(String courseCode) {
    	Course course = RM.getCourse(courseCode);
    	ArrayList<Index> indexList = course.getIndexList();
    	IO.printIndexList(indexList);
    }

    
    /** 1.Edit student access period */
    public int editAccessPeriod(String facultyName, String startDateTime, String endDateTime) {
    	Faculty faculty = RM.getFaculty(facultyName);
        LocalDateTime start = TimeManager.strToDateTime(startDateTime);
        LocalDateTime end = TimeManager.strToDateTime(endDateTime);
        if (start == null || end == null) {
            // DateTime string not in correct format
            return 0;
        } else if (start.isAfter(end) || start.equals(end)) {
            // start > end
            return -1;
        } else {
            faculty.setRegistrationTime(start, end);
            return 1;
        }
    }
    
    
    /** 2.add a student */
    public int addStudent(String username, String email, String fullName, String gender, String nationality,
            String matricNum, String faculty, int yearOfStudy) {
        Faculty fac = RM.getFaculty(faculty);
        if (RM.getUser(username) != null) {
            // username exists
            return 0;
        } else if (!gender.equalsIgnoreCase("m") && !gender.equalsIgnoreCase("f")) {
            // invalid gender
            return -1;
        } else if (fac == null) {
            // faculty not found
            return -2;
        } else if (yearOfStudy < 1 || yearOfStudy > 4) {
            // invalid year
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

    
    /** 3.Add a course */
    public int addCourse(String courseCode, String courseName, String subjectType, int AU, String facultyName) {
        if (checkCourseExists(courseCode)) {
            // course exists
            return 0;
        } else if (AU < 1) {
            // AU 0 or below
            return -1;
        } else {
        	Faculty faculty = RM.getFaculty(facultyName);
            Course course = new Course(courseCode, courseName, subjectType, AU, faculty);
            return 1;
        }
    }

    /** Add an Index */
    public int addIndex(String indexNo, int slots, String courseCode) {
        if (checkIndexExists(indexNo)) {
            // index exists
            return 0;
        } else if (slots < 0) {
            // negative slots
            return -1;
        } else {
        	Course course = RM.getCourse(courseCode);
            new Index(indexNo, slots, course);
            return 1;
        }
    }

    
    /** Add lesson to index */
    public int addLesson(String type, int day, String start, String end, String venue, int oddEven, String indexNo) {
    	Index index = RM.getIndex(indexNo);

    	if (!TimeManager.checkValidDate(start) || !TimeManager.checkValidDate(end)){
    	    return -5;
        }
        if (day < 1 || day > 6) {
            // invalid day
            return -1;
        } else if (!(oddEven == 0 || oddEven == 1 || oddEven == 2)) {
            // invalid option
            return -2;
        }

        day = day -1; // 0 -> Mon, 1 -> Tues

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

    
    /** check if lessons in an index clashes */
    private boolean checkLessonClash(Index index, int day, int oddEven, String start, String end) {
        boolean isClash = false;
        for (Lesson tempLesson : index.getLessonList()) {
            if (day == tempLesson.getDayOfWeek()) {
                if (oddEven == tempLesson.getOddEvenWeek()) {
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

    
    
    /** print all lessons in the index */
    public void printLessonsInIndex(String indexNo) {
    	System.out.println("=== Index " + indexNo + " Lessons ===");
    	Index index = RM.getIndex(indexNo);
        ArrayList<Lesson> lessonList = index.getLessonList();
        if (lessonList != null){
            IO.printLessonList(lessonList);
        } else {
            System.out.println("Lesson list is empty.");
        }
        System.out.println(" ");
    }
    

    /** 4.Update Course info */
    public boolean updateCourseCode(String courseCode, String newCourseCode) {
    	Course course = RM.getCourse(courseCode);
        if (!checkCourseExists(newCourseCode)) {
            course.setCourseCode(newCourseCode);
            return true;
        } else {
            return false;
        }
    }

    
    public void updateCourseName(String courseCode, String newName) {
    	Course course = RM.getCourse(courseCode);
        course.setCourseName(newName);
    }

    
    public void updateSubjectType(String courseCode, String newSubjectType){
    	Course course = RM.getCourse(courseCode);
        course.setSubjectType(newSubjectType);
    }

    
    public boolean updateCourseAU(String courseCode, int newAU){
    	Course course = RM.getCourse(courseCode);
    	if (newAU > 0) {
    		course.setAU(newAU);
    		return true;
    	} else {
    		return false;
    	}
    }


    public boolean removeCourse(String courseCode) {
    	Course course = RM.getCourse(courseCode);
    	return RM.removeCourse(course);
    }


    /** Update Index info */
    public boolean updateIndexNo(String indexNo, String newIndex) {
    	Index index = RM.getIndex(indexNo);
    	if (!checkIndexExists(newIndex)) {
        	index.setIndex(newIndex);
        	return true;
        } else {
        	return false;
        }
    }

    
    public boolean updateIndexVac(String indexNo, int newVacancy) {
    	Index index = RM.getIndex(indexNo);
        if (newVacancy >= 0) {
        	index.setVacancy(newVacancy);
        	return true;
        } else {
            return false;
        }
    }

    
    public boolean removeIndex(String indexNo) {
    	Index index = RM.getIndex(indexNo);
    	return RM.removeIndex(index);
    }


    /** 5.Check available slot for an index number (vacancy in a class) -wx */
    public int checkVacancies(String indexCode) {
        Index index = RM.getIndex(indexCode);
        return (index != null) ? index.getVacancy() : -1;
    }

    
    /** 6. Print student list by index number. -wx */
    public void printStudentListByIndex(String indexCode) {
        Index index = RM.getIndex(indexCode);
        if (index != null) {
        	ArrayList<Student> studentList = index.getStudentList();
        	IO.printStudentList(studentList);
        } else {
        	System.out.println("Invalid index.");
        }
    }
    
    
    /** 7. Print student list by course (all students registered for the selected
     * course */
    public void printStudentListByCourse(String courseCode) {
    	Course course = RM.getCourse(courseCode);
    	if (course != null) {
    		ArrayList<Student> studentList = getStudentList(course);
			if (studentList != null) {
				IO.printStudentList(studentList);
			}
		} else {
			System.out.println("Invalid course.");
		}
    }
    
    
    /** Get student list from course */
    private ArrayList<Student> getStudentList(Course course) {
        ArrayList<Student> studentList = new ArrayList<>();
        for (Index index : course.getIndexList()) {
            studentList.addAll(index.getStudentList());
        }
        return studentList;
    }
    
    
    /** 8. change password */
    public boolean changePassword(String oldPassword, String newPassword) {
        return LoginManager.changePassword(admin, oldPassword, newPassword);
    }
    
    
    /** 9.return string matrix for printing all courses and indexes */
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

        
    /** Get db info */
    public Dictionary<String, String> getWelcomeInfo() {
        Dictionary<String, String> dbInfo = new Hashtable<>();
        dbInfo.put("fullName", admin.getFullName());
        dbInfo.put("facultySize", Integer.toString(RM.getAllFaculties().size()));
        dbInfo.put("studentSize", Integer.toString(RM.getAllStudents().size()));
        dbInfo.put("courseSize", Integer.toString(RM.getAllCourses().size()));
        return dbInfo;
    }



}
