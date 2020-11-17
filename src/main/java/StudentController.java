import java.io.Serializable;
import java.util.ArrayList;

public class StudentController {
    RecordManager RM;

    public StudentController(RecordManager RM){
        this.RM = RM;
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
}
