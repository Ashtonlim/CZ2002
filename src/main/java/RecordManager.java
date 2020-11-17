import java.util.ArrayList;

public class RecordManager {
    private ArrayList<User> users;
    private ArrayList<Faculty> facultyList;

    public RecordManager() throws Exception {
        try{
            ArrayList<?> db = FileManager.readSerializedObject(); // [0] -> Users, [1] -> Faculties
            users = (ArrayList<User>) db.get(0); // Safe cast, checked in FileManager
            facultyList = (ArrayList<Faculty>) db.get(1); // Safe cast, checked in FileManager
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() );
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

        //Faculty
        Faculty f1 = new Faculty("SCSE");

        //Course
        Course c1 = new Course("CZ2002", "Data Science", "Core", 3, f1);
        Course c2 = new Course("CZ2003", "Algor", "Core", 3, f1);
        Index i1 = new Index("200201", 20, c1);
        Index i2 = new Index("200301", 20, c2);
        facultyList.add(f1);

        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Admin> admins = new ArrayList<Admin>();

        //Student
        String password = LoginManager.generateHash("abc123");
        students.add(new Student("weixing", password, "WeiXing", "M", "U123", f1, 2, 20));
        students.add(new Student("zheming", password, "ZheMing", "M", "U321", f1, 2, 20));
        admins.add(new Admin("guat", password, "Guat", "M"));
        users.addAll(students);
        users.addAll(admins);

        i1.addToStudentList( (Student) users.get(0));
        i1.addToStudentList( (Student) users.get(1));
        save();
        System.out.println("Dummy data loaded.");
    }


    /** get faculty by facultyName */
    public Faculty getFaculty(String facultyName){

        for (Faculty faculty : facultyList) {
            if (faculty.getName().equals(facultyName)) {
                return faculty;
            }
        }

        return null;
    }
    
    
    /** get course by course code */
    public Course getCourse(String courseCode){

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
    public Index getIndex(String index){
    	
    	for (Faculty faculty : facultyList) {
            ArrayList<Course> tempCourseList = faculty.getCourseList();
            for (Course tempCourse : tempCourseList) {
		    	ArrayList<Index> tempIndexList = tempCourse.getIndexList();
		    	for (Index tempIndex : tempIndexList) {
		            if (tempIndex.getIndex().equals(index)) {
		                return tempIndex;
		            }
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

        for (int i = 0; i < users.size(); i++) {

            User temp = users.get(i);
            if (temp.getUserName().equals(username)) {
                user = temp;
                break;
            }
        }

        return user;
    }


    /** return all users */
    public ArrayList<User> getAllUsers() {
        return users;
    }

    /** Add user */
    public boolean addUser(User user) throws Exception {

        for (int i = 0; i < this.users.size(); i++) {
            User temp = users.get(i);
            if (temp.getUserName().equals(user.getUserName())) {
                return false;
            }
        }
        users.add(user);
        save();
        return true;
    }

    /** Remove user */
    public boolean removeUser(User user) throws Exception {

        for (int i = 0; i < this.users.size(); i++) {
            User temp = users.get(i);
            if (temp.getUserName().equals(user.getUserName())) {
                users.remove(user);
                save();
                return true;
            }
        }
        return false;
    }

    /** Add course */
    public void addCourse(Faculty faculty, Course course) {
        faculty.addCourse(course);
        save();
    }

    public boolean addCourse(String facultyName, Course course) {

        for (Faculty faculty : facultyList) {
            if (facultyName.equals(faculty.getName())) {
                faculty.addCourse(course);
                save();
                return true;
            }
        }

        return false;
    }

    public boolean checkCourseName(String courseCode) {

        for (Faculty faculty : facultyList) {
            for (Course course : faculty.getCourseList()) {
                if (courseCode.equals(course.getCourseCode())) {
                    return true;
                }
            }
        }

        return false;
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

    private void save() {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(users);
        temp.add(facultyList);
        FileManager.writeSerializedObject(temp);
    }

    public ArrayList<Index> getCourseStu(Student s) {
        ArrayList<Index> RegCourseStuList = new ArrayList<>();
        for (Faculty faculty : facultyList) {
            for (Course course : faculty.getCourseList()) {
                for (Index index : course.getIndexList()) {
                    for (Student stu : index.getStudentList()) {
                        if (s == stu) {
                            RegCourseStuList.add(index);
                        }
                    }
                }
            }
        }
        return RegCourseStuList;
    }

}