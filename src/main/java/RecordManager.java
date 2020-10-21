import java.util.ArrayList;
import java.util.List;

public class RecordManager {
    private Database db;
    private ArrayList<Object> courses;
    private ArrayList<Object> users;

    public RecordManager() throws Exception {
        db = new Database();
        users = db.load("db/users", "user");
        courses = db.load("db/courses", "course");
    }

    /** search a course by course code */
    public Course getCourse(String courseCode){
        Course course = null;

        for (int  i = 0; i < courses.size(); i++){
            Course temp = (Course) courses.get(i);
            if (temp.getCourseCode().equals(courseCode)){
                course = temp;
                break;
            }
        }
        return course;
    }

    /** return all courses */
    public Course[] getAllCourses(){
        Course[] courses = new Course[this.courses.size()];

        for (int i = 0; i < this.courses.size(); i++){
            courses[i] = (Course) this.courses.get(i);
        }

        return courses;
    }

    /** search a user by username */
    public User getUser(String username){
        User user = null;

        for (int  i = 0; i < users.size(); i++){

            User temp = (User) users.get(i);
            if (temp.getUserName().equals(username)){
                user = temp;
                break;
            }
        }

        return user;
    }

    /** return all users */
    public User[] getAllUsers(){
        User[] users = new User[this.users.size()];

        for (int i = 0; i < this.users.size(); i++){
            users[i] = (User) this.users.get(i);
        }

        return users;
    }

    /** Add user */
    public boolean addUser(User user){

        for (int  i = 0; i < this.users.size(); i++){
            User temp = (User) users.get(i);
            if (temp.getUserName().equals(user.getUserName())){
                return false;
            }
        }
        users.add(user);
        return true;
    }

    /** Save records to file **/
    public boolean save(String type) throws Exception {
        List<String> writeList = new ArrayList<>();
        boolean success = false;
        try{
            switch(type){
                case "users":
                    for (int i = 0; i < users.size(); i++){
                        User user = (User) users.get(i);
                        writeList.add(user.formatDBRow());
                    }
                    db.save(writeList, "db/users");
                    success = true;
                    break;
                case "course":
                    break;
                default:
            }
        } catch (Exception e){
            System.out.println("Critical error while saving to db file: " + e + " Your file has not been saved.");
            success = false;
        } finally {
            return success;
        }
    }
}
