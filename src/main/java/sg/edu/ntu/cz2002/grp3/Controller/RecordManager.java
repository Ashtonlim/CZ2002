package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecordManager {
    private ArrayList<User> users;
    private ArrayList<Faculty> facultyList;

    public RecordManager() {
        try {
            ArrayList<?> db = FileManager.readSerializedObject(); // [0] -> Users, [1] -> Faculties
            users = (ArrayList<User>) db.get(0); // Safe cast, checked in Controller.FileManager
            facultyList = (ArrayList<Faculty>) db.get(1); // Safe cast, checked in Controller.FileManager
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Unable to get data from database, loading empty db. You should load dummy data once.");
            users = new ArrayList<>();
            facultyList = new ArrayList<>();
        }
    }

    /**
     * One-click dummy data importer After running this function, you need to copy
     * the database file(target/classes/db/database) from production folder to main.
     */
    public void loadDummyData() throws Exception {
        users = new ArrayList<>();
        facultyList = new ArrayList<>();

        // Entity.Faculty
        Faculty f1 = new Faculty("SCSE");
        Faculty f2 = new Faculty("NBS");
        LocalDateTime scseStart = LocalDateTime.of(2020, 11, 23, 9, 0, 0);
        LocalDateTime scseEnd = LocalDateTime.of(2020, 11, 30, 9, 0, 0);
        f1.setRegistrationTime(scseStart, scseEnd);

        // Entity.Course
        Course c1 = new Course("CZ2002", "Data Science", "Core", 3, f1);
        Course c2 = new Course("CZ2003", "Algor", "Core", 3, f1);
        Course c3 = new Course("NB1001", "Econs", "Core", 3, f2);
	    Course c4 = new Course("NB1002","Accounting","Core",2, f2);
	    
        Index i1 = new Index("200201", 10, c1);
        Index i2 = new Index("200202", 10, c1);
        Index i3 = new Index("200301", 10, c2);
        Index i4 = new Index("100101",10, c3);
        Index i5 = new Index("200302", 10, c2);
        //c4 does not have index.
	    
        Lesson tut2002 = new Lesson("tut", 1, 0, "14:30", "16:30", "TR-20", i1);
        Lesson lab2002 = new Lesson("lab", 2, 0, "09:30", "11:30", "SPL", i1);
        Lesson lec2002 = new Lesson("lec", 5, 1, "14:30", "15:30", "LT12", i1);

        Lesson tut200202 = new Lesson("tut", 1, 0, "14:30", "16:30", "TR-20", i2);

        Lesson tut2003 = new Lesson("tut", 5, 1, "14:30", "16:30", "TR-18", i3);
        Lesson lab2003 = new Lesson("lab", 1, 1, "10:30", "12:30", "HWL1", i3);
        Lesson lec2003 = new Lesson("lec", 3, 1, "13:30", "14:30", "LT4", i3);

        Lesson tut200302 = new Lesson("tut", 1, 1, "12:30", "13:30", "HWL1", i5);
	    
        facultyList.add(f1);
        facultyList.add(f2);

        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Admin> admins = new ArrayList<Admin>();

        // Student
        String password = LoginManager.generateHash("a");

        students.add(new Student("weixing", "wxOODPtest@gmail.com", password, "WeiXing", "M", "SC", "U123", f1, 2, 0));
        students.add(new Student("zheming", "zmOODPtest@gmail.com", password, "ZheMing", "M", "SC", "U321", f1, 2, 0));
        students.add(new Student("ash", "mystaroodp@gmail.com", password, "Ashton", "M", "SC", "U195", f1, 3, 0));
        students.add(new Student("bob", "bobOODPtest@gmail.com", password, "Bob", "M", "SC", "U461", f2, 2, 0));
        students.add(new Student("james", "U201@gmail.com", password, "James Law", "M", "SC", "U201", f1, 3, 0));
        students.add(new Student("Mike", "U202@gmail.com", password, "Mike Law", "M", "SC", "U202", f1, 2, 0));
        students.add(new Student("Gary", "U203@gmail.com", password, "Gary Law", "M", "SC", "U203", f1, 3, 0));
        students.add(new Student("Jack", "U204@gmail.com", password, "Jack Law", "M", "SC", "U204", f1, 1, 0));
        students.add(new Student("Tim", "U205@gmail.com", password, "Tim Law", "M", "SC", "U205", f1, 2, 0));
        students.add(new Student("Tina", "U206@gmail.com", password, "Tina Tan", "F", "SC", "U206", f1, 3, 0));
        students.add(new Student("Jess", "U207@gmail.com", password, "Jess Tan", "F", "SC", "U207", f1, 3, 0));
        students.add(new Student("Claire", "U208@gmail.com", password, "Claire Tan", "F", "SC", "U208", f1, 2, 0));
	    students.add(new Student("Amy", "U209@gmail.com", password, "Amy Tan", "F", "SC", "U209", f2, 1, 0));
        students.add(new Student("Helen", "U210@gmail.com", password, "Helen Tan", "F", "SC", "U210", f2, 2, 0));
        students.add(new Student("Monica", "U211@gmail.com", password, "Monica Tan", "F", "SC", "U211", f2, 1, 0));
        admins.add(new Admin("guat", password, "Guat", "M", "SC"));

        users.addAll(students);
        users.addAll(admins);

        i1.addToStudentList((Student) users.get(0));
        i2.addToStudentList((Student) users.get(1));
        // i3.addToStudentList((Student) users.get(0));
//        i3.addToStudentList((Student) users.get(1));
        i3.addToStudentList((Student) users.get(2));
        i3.addToStudentList((Student) users.get(3));
        i3.addToStudentList((Student) users.get(4));
        i3.addToStudentList((Student) users.get(5));
        i3.addToStudentList((Student) users.get(6));
        i3.addToStudentList((Student) users.get(7));
        i3.addToStudentList((Student) users.get(8));
        i3.addToStudentList((Student) users.get(9));
        i3.addToStudentList((Student) users.get(10));
	    
        save();
        System.out.println("Dummy data loaded.");
    }

    /** get faculty by facultyName */
    public Faculty getFaculty(String facultyName) {

        for (Faculty faculty : facultyList) {
            if (faculty.getName().equals(facultyName)) {
                return faculty;
            }
        }

        return null;
    }

    public ArrayList<Faculty> getAllFaculties() {
        return facultyList;
    }

    /** get course by course code */
    public Course getCourse(String courseCode) {

        for (Faculty faculty : facultyList) {
            ArrayList<Course> tempCourseList = faculty.getCourseList();
            for (Course tempCourse : tempCourseList) {
                if (tempCourse.getCourseCode().equals(courseCode)) {
                    return tempCourse;
                }
            }
        }

        return null;
    }

    /** get index by index number */
    public Index getIndex(String index) {

        for (Course tempCourse : getAllCourses()) {
            ArrayList<Index> tempIndexList = tempCourse.getIndexList();
            for (Index tempIndex : tempIndexList) {
                if (tempIndex.getIndex().equals(index)) {
                    return tempIndex;
                }
            }
        }

        return null;
    }

    /** return all courses */
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> temp = new ArrayList<>();

        for (Faculty faculty : facultyList) {
            temp.addAll(faculty.getCourseList());
        }

        return temp;
    }

    /** search a user by username */
    public User getUser(String username) {
        User user = null;

        for (User temp : users) {

            if (temp.getUserName().equals(username)) {
                user = temp;
                break;
            }
        }

        return user;
    }

    public Student getStudent(String matricNum) {
        for (Student student : getAllStudents()) {
            if (student.getMatricNum().equals(matricNum)) {
                return student;
            }
        }
        return null;
    }

    /** return all students */
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> temp = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Student) {
                temp.add((Student) user);
            }
        }
        return temp;
    }

    /** Add user */
    public boolean addUser(User user) {

        for (User temp : this.users) {
            if (temp.getUserName().equals(user.getUserName())) {
                return false;
            }
        }
        users.add(user);
        save();
        return true;
    }


    /** Remove course */
    public boolean removeCourse(Course course) {

        for (Faculty faculty : facultyList) {
            if (faculty.getCourseList().contains(course)) {
                faculty.getCourseList().remove((course));
                save();
                return true;
            }
        }

        return false;
    }

    /** Remove index */
    public boolean removeIndex(Index index) {
        for (Course course : getAllCourses())
            if (course.getIndexList().contains(index)) {
                course.getIndexList().remove(index);
                return true;
        }
        return false;
    }

    public void save() {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(users);
        temp.add(facultyList);
        FileManager.writeSerializedObject(temp);
    }

}
