import java.util.ArrayList;

public class RecordManager {
    private ArrayList<User> users;
    private ArrayList<Faculty> facultyList;

    public RecordManager() throws Exception {
        ArrayList<?> db = FileManager.readSerializedObject(); // [0] -> Users, [1] -> Faculties
        users = (ArrayList<User>) db.get(0); // Safe cast, checked in FileManager
        facultyList = (ArrayList<Faculty>) db.get(1); // Safe cast, checked in FileManager
    }

    /**
     * One-click dummy data importer After running this function, you need to copy
     * the database file(target/classes/db/database) from production folder to main.
     */
    public void loadDummyData() throws Exception {
        users = new ArrayList<User>();
        facultyList = new ArrayList<Faculty>();

        String password = LoginManager.generateHash("abc123");

        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<Admin> admins = new ArrayList<Admin>();
        ArrayList<Index> indexes = new ArrayList<Index>();
        ArrayList<Student> indexStudentList = new ArrayList<Student>();
        ArrayList<Student> indexWaitList = new ArrayList<Student>();
        ArrayList<Course> courses = new ArrayList<Course>();

        students.add(new Student("weixing", password, "WeiXing", "M", "U123", "CS", "123", 2, 20));
        students.add(new Student("zheming", password, "ZheMing", "M", "U321", "CS", "123", 2, 20));
        admins.add(new Admin("guat", password, "Guat", "Male"));

        for (Student s : students) {
            users.add(s);
            indexStudentList.add(s); // load in students to index
        }
        for (Admin a : admins) {
            users.add(a);
        }

        indexes.add(new Index("200201", 20, indexWaitList, indexStudentList));
        courses.add(new Course("CZ2002", "Test 2", "SCSE", "Core", 3, indexes, 10));
        courses.add(new Course("CZ2003", "Test 3", "SCSE", "Core", 3, new ArrayList<Index>(), 10));

        facultyList.add(new Faculty("SCSE", students, courses));

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

}
