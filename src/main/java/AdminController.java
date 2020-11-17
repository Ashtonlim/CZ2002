import java.util.ArrayList;

public class AdminController {
    RecordManager RM;

    public AdminController(RecordManager RM){
        this.RM = RM;
    }

    /** Get student list from faculty */
    private ArrayList<Student> getStudentList(Faculty faculty){
        ArrayList<Student> studentList = new ArrayList<>();
        for (Course course : faculty.getCourseList()){
            for (Index index : course.getIndexList()){
                studentList.addAll(index.getStudentList());
            }
        }
        return studentList;
    }

    /** Get student list from course */
    private ArrayList<Student> getStudentList(Course course){
        ArrayList<Student> studentList = new ArrayList<>();
        for (Index index : course.getIndexList()){
            studentList.addAll(index.getStudentList());
        }
        return studentList;
    }

    /** Get student list from index */
    private ArrayList<Student> getStudentList(Index index){
        return index.getStudentList();
    }

    /** 4.Check available slot for an index number (vacancy in a class) -wx  */
    public void checkVacancies(){
        System.out.println("=== Index Vacancy Checker ===");
        String indexCode = View.getTextInput("Index number: ");
        Index index = RM.getIndex(indexCode);
        System.out.println("Available slots: " + index.getVacancy());
    }

    /** 5. Print student list by index number. -wx */
    public void printStudentListByIndex(){
        System.out.println("=== Student List By Index ===");
        String indexCode = View.getTextInput("Index number: ");
        Index index = RM.getIndex(indexCode);


        if (index != null) {
            View.printStudentList(getStudentList(index));
        } else {
            System.out.println("Index not found!");
        }
    }

    /** 6.Print student list by course (all students registered for the selected course). -wx */
    public void printStudentListByCourse(){
        System.out.println("=== Student List By Course ===");
        String indexCode = View.getTextInput("Course code: ");
        Course course = RM.getCourse(indexCode);

        if (course != null){
            View.printStudentList(getStudentList(course));
        } else {
            System.out.println("Course not found!");
        }
    }
}