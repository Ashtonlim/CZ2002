import java.io.Serializable;
import java.util.ArrayList;

public class StudentController {
    RecordManager RM;
    Course C;

    public StudentController(RecordManager RM){
        this.RM = RM;
    }

    public StudentController(Course C){
        this.C = C;
    }

    public void printCourseReg(Student s) {
        System.out.println("   CourseID   CourseName   Index   ");
        System.out.println("===================================");
        ArrayList<Index> RegIndex = RM.getCourseStu(s);
        if (RegIndex != null){
            for (int i = 0; i < RegIndex.size(); i++) {
                System.out.println(
                        "   " + RegIndex.get(i).getCourse().getCourseCode() + "       " + RegIndex.get(i).getCourse().getCourseName() +
                                "         " + RegIndex.get(i).getIndex());
            }
        } else
            System.out.println("No Course Registered found for this Student");
        return;
    }

    public void checkVacanciesOfIndex(){
        System.out.println("=== Please input your CourseID to show indexes of the Course ===");
        String courseCode = View.getTextInput("CourseID: ");
        Course course =  RM.getCourse(courseCode);
        ArrayList<Index> indexList = course.getIndexList();
        System.out.println("=== IndexNumber == Vacancies === ");
        for (Index index : indexList) {
            System.out.print("      " + index.getIndex());
            System.out.println("          " + index.getVacancy());
        }
    }
}
