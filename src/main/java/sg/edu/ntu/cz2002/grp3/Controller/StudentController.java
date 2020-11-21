package sg.edu.ntu.cz2002.grp3.Controller;
import sg.edu.ntu.cz2002.grp3.Entity.*;
import java.time.LocalTime;
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
        System.out.println("   CourseID   CourseName   sg.edu.ntu.cz2002.grp3.Entity.Index   ");
        System.out.println("===================================");
        ArrayList<Index> RegIndex = s.getIndexList();
        if (RegIndex != null){
            for (int i = 0; i < RegIndex.size(); i++) {
                System.out.println(
                        "   " + RegIndex.get(i).getCourseCode() + "       " + RegIndex.get(i).getCourseCode() +
                                "         " + RegIndex.get(i).getIndex());
            }
        } else
            System.out.println("No sg.edu.ntu.cz2002.grp3.Entity.Course Registered found for this sg.edu.ntu.cz2002.grp3.Entity.Student");
        return;
    }

    public ArrayList<Index> checkVacanciesOfCourse(String courseCode){
        ArrayList<Index> temp = new ArrayList<>();
        Course course =  RM.getCourse(courseCode);
        if (course != null){
            for (Index index : course.getIndexList()) {
                temp.add(index);

            }
            return temp;
        } else {
            return null;
        }
    }
    
    //check for clash between 2 time periods
    public boolean checkTimeClash(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
    	if (start1.isBefore(end2) && end1.isAfter(start2)) {
    		return true;
    	}
    	return false;
    }
    
    // check whether an index clashes with the registered indexes of the student
    public boolean checkIndexClash(Student s, Index i) {
        TimeTable timeTable = s.getTimeTable();
        try {
            return timeTable.checkClash(i);
        } catch (Exception ex){
            //Wrong sg.edu.ntu.cz2002.grp3.Entity.Index time will end up here
        }
		return true;

//        ArrayList<sg.edu.ntu.cz2002.grp3.Entity.Index> regIndexes = s.getIndexList();
//        for (sg.edu.ntu.cz2002.grp3.Entity.Index regIndex : regIndexes) {
//            ArrayList<sg.edu.ntu.cz2002.grp3.Entity.Lesson> regLessons = regIndex.getLessonList();
//            for (sg.edu.ntu.cz2002.grp3.Entity.Lesson regLesson : regLessons) {
//                for(sg.edu.ntu.cz2002.grp3.Entity.Lesson newLesson : i.getLessonList()) {
//                    if (regLesson.getDay() != newLesson.getDay()) {
//                        continue;
//                    } else {
//                        return checkTimeClash(regLesson.getStartTime(), regLesson.getEndTime(), newLesson.getStartTime(), newLesson.getEndTime());
//                    }
//                }
//            }
//        }
//
//        return false;
    }
}
