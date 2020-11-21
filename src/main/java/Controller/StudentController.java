package Controller;
import Entity.*;
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
        System.out.println("   CourseID   CourseName   Entity.Index   ");
        System.out.println("===================================");
        ArrayList<Index> RegIndex = s.getIndexList();
        if (RegIndex != null){
            for (int i = 0; i < RegIndex.size(); i++) {
                System.out.println(
                        "   " + RegIndex.get(i).getCourseCode() + "       " + RegIndex.get(i).getCourseCode() +
                                "         " + RegIndex.get(i).getIndex());
            }
        } else
            System.out.println("No Entity.Course Registered found for this Entity.Student");
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
            //Wrong Entity.Index time will end up here
        }
		return true;

//        ArrayList<Entity.Index> regIndexes = s.getIndexList();
//        for (Entity.Index regIndex : regIndexes) {
//            ArrayList<Entity.Lesson> regLessons = regIndex.getLessonList();
//            for (Entity.Lesson regLesson : regLessons) {
//                for(Entity.Lesson newLesson : i.getLessonList()) {
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