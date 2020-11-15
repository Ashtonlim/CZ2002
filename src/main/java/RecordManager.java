import java.util.ArrayList;
import java.util.List;

public class RecordManager {
    private FileManager fm;
    private ArrayList<Course> courses;
    private ArrayList<User> users;

    public RecordManager() throws Exception {
        fm = new FileManager(); //The only instantiation
        ArrayList<Object> db = fm.loadAll(); //[0] -> Users, [1] -> Courses
        users = (ArrayList<User>) db.get(0); //Safe cast, checked in FileManager
        courses = (ArrayList<Course>) db.get(1); //Safe cast, checked in FileManager
    }

    /** One-click dummy data importer
     * After running this function, you need to copy the database file(target/classes/db/database) from production folder to main.
     */
    public void loadDummyData() throws Exception {
        //Users
        users.clear();
        Student s1 = new Student("weixing", "abc123", "WeiXing", "M", "U123", "CS", "123",2, 20);
        Student s2 = new Student("zheming", "abc123", "ZheMing", "M", "U321", "CS", "123",2, 20);
        Admin s3 = new Admin("guat", "abc123", "Guat", "Male");
        users.add(s1);
        users.add(s2);
        users.add(s3);

        //Courses
        courses.clear();
        ArrayList<Index> indexList1 = new ArrayList<>();
        ArrayList<Student> studentList1 = new ArrayList<Student>();
        studentList1.add( (Student) getUser("weixing") );
        studentList1.add( (Student) getUser("zheming") );

        Index add1_1 = new Index("200201", 20, studentList1, studentList1);
        indexList1.add(add1_1);
        Course add1 = new Course("CZ2002", "Test 2", "SCSE", "Core", 3, indexList1);
        Course add2 = new Course("CZ2003", "Test 3", "SCSE", "Core", 3, indexList1);
        courses.add(add1);
        courses.add(add2);
        save();

        System.out.println("Dummy data loaded into target/classes/db/database");
        System.out.println("You should copy this file into src/main/java/resources/db");
    }

    /** search a course by course code */
    public Course getCourse(String courseCode){
        Course course = null;

        for (int  i = 0; i < courses.size(); i++){
            Course temp = courses.get(i);
            if (temp.getCourseCode().equals(courseCode)){
                course = temp;
                break;
            }
        }
        return course;
    }

    /** return all courses */
    public ArrayList<Course> getAllCourses(){
        return courses;
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

    public void save() {
        ArrayList<Object> temp = new ArrayList<>();
        temp.add(users);
        temp.add(courses);
        fm.saveAll(temp);
    }

}
