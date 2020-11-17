import java.util.ArrayList;
import java.util.Date;

public class RecordManager {
    private FileManager fm;
    private ArrayList<Faculty> facultyList;
    private ArrayList<User> users;

    public RecordManager() throws Exception {
        fm = new FileManager(); //The only instantiation
        ArrayList<Object> db = fm.loadAll(); //[0] -> Users, [1] -> Faculties
        users = (ArrayList<User>) db.get(0); //Safe cast, checked in FileManager
        facultyList = (ArrayList<Faculty>) db.get(1); //Safe cast, checked in FileManager
    }

    /** One-click dummy data importer
     * After running this function, you need to copy the database file(target/classes/db/database) from production folder to main.
     */
    public void loadDummyData() throws Exception {
        //Users
    	LoginManager lm = new LoginManager();
    	String password = lm.generateHash("abc123");
        users.clear();
        Student s1 = new Student("weixing", password, "WeiXing", "M", "U123", "CS", "123",2, 20);
        Student s2 = new Student("zheming", password, "ZheMing", "M", "U321", "CS", "123",2, 20);
        Admin s3 = new Admin("guat", password, "Guat", "Male");
        users.add(s1);
        users.add(s2);
        users.add(s3);


        //Courses
        facultyList.clear();
        ArrayList<Student> studentList1 = new ArrayList<>();
        studentList1.add( (Student) getUser("weixing") );
        studentList1.add( (Student) getUser("zheming") );

        ArrayList<Index> indexList1 = new ArrayList<>();
        Index add1_1 = new Index("200201", 20, studentList1, studentList1);
        indexList1.add(add1_1);
        Course add1 = new Course("CZ2002", "Test 2", "SCSE", "Core", 3, indexList1, 10);
        Course add2 = new Course("CZ2003", "Test 3", "SCSE", "Core", 3, indexList1, 10);

        //Faculties
        ArrayList<Course> courseList1 = new ArrayList<>();
        courseList1.add(add1);
        courseList1.add(add2);
        facultyList.add(new Faculty("SCSE", studentList1, courseList1));

        save();

        System.out.println("Dummy data loaded.");
    }

    /** search a course by course code */
    public Course getCourse(String courseCode){

        for (Faculty faculty : facultyList) {
            ArrayList<Course> tempCourseList = faculty.getCourseList();
            for (Course temp : tempCourseList) {
                if (temp.getCourseCode().equals(courseCode)) {
                    return temp;
                }
            }
        }

        return null;
    }

    /** return all courses */
    public ArrayList<Course> getAllCourses(){
        ArrayList<Course> temp = new ArrayList<>();

        for (Faculty faculty : facultyList){
            temp.addAll(faculty.getCourseList());
        }

        return temp;
    }

    /** search a user by username */
    public User getUser(String username){
        User user = null;

        for (int  i = 0; i < users.size(); i++){

            User temp = users.get(i);
            if (temp.getUserName().equals(username)){
                user = temp;
                break;
            }
        }

        return user;
    }

    /** return all users */
    public ArrayList<User> getAllUsers(){
        return users;
    }

    /** Add user */
    public boolean addUser(User user) throws Exception {

        for (int  i = 0; i < this.users.size(); i++){
            User temp = users.get(i);
            if (temp.getUserName().equals(user.getUserName())){
                return false;
            }
        }
        users.add(user);
        save();
        return true;
    }
    
    /** Remove user */
    public boolean removeUser(User user) throws Exception {

        for (int  i = 0; i < this.users.size(); i++){
            User temp = users.get(i);
            if (temp.getUserName().equals(user.getUserName())){
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

    public boolean addCourse(String facultyName, Course course){

        for (Faculty faculty : facultyList){
            if ( facultyName.equals( faculty.getName() ) ){
                faculty.addCourse(course);
                save();
                return true;
            }
        }

        return false;
    }

    public boolean checkCourseName(String courseCode){

        for (Faculty faculty : facultyList){
            for (Course course : faculty.getCourseList()){
                if (courseCode.equals(course.getCourseCode())){
                    return true;
                }
            }
        }

        return false;
    }

    /** Remove course */
    public boolean removeCourse(Course course) {

        for (Faculty faculty : facultyList){
            if ( faculty.getCourseList().contains(course) ){
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
        fm.saveAll(temp);
    }

}
