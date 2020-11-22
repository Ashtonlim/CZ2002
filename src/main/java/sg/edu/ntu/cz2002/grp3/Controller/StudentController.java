package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class StudentController {
    private RecordManager RM;

    public StudentController(RecordManager RM) {
        this.RM = RM;
    }

    public ArrayList<Index> getCourseReg(Student student) {
        return student.getIndexList();
    }

    public Index checkValidIndex(String indexCode) {
        ArrayList<Course> courses = RM.getAllCourses();
        for (Course c : courses) {
            System.out.println(c.getCourseName());
            for (Index i : c.getIndexList()) {
                if (i.getIndex().equals(indexCode)) {
                    return i;
                }
            }
        }
        return null;
    }

    public ArrayList<Index> getVacanciesOfCourse(String courseCode) {
        ArrayList<Index> temp = new ArrayList<>();
        Course course = RM.getCourse(courseCode);
        if (course != null) {
            for (Index index : course.getIndexList()) {
                temp.add(index);

            }
            return temp;
        } else {
            return null;
        }
    }


    // check for clash between 2 time periods
    public boolean checkTimeClash(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        if (start1.isBefore(end2) && end1.isAfter(start2)) {
            return true;
        }
        return false;
    }

    /** Status code:
     * 1 - success | 0 - Clash | -1 = oldIndex not in the same course as new index.
     * -2 - no vacancy in new index
     * */
    public int changeIndex(Student student, Index oldIndex, Index newIndex){
        if (!oldIndex.getCourseCode().equals(newIndex.getCourseCode())){
            return -1;
        }
        if (newIndex.getVacancy() == 0){
            return -2;
        }

        oldIndex.removeFromStudentList(student);
        boolean addStatus = newIndex.addToStudentList(student);
        return (addStatus) ? 1 : 0;
    }

    public int swopIndex(Student source, String targetMatricNum, String targetPassword, Index sourceIndex){
        Student target = RM.getStudent(targetMatricNum);
        if (target == null) return 0;
        if ( !LoginManager.verifyLogin(target, targetPassword) ) return -1; //Check password of target Student

        boolean found = false;
        Index targetIndex = null;
        for (Index index : target.getIndexList()){
            if ( index.getCourseCode().equals( sourceIndex.getCourseCode() ) ){
                found = true;
                targetIndex = index;
                break;
            }
        }
        if (!found) return -2; //Target student does not have the Specific course index source student trying to swop.
        if ( sourceIndex == targetIndex ) return -5; //both same index
        if ( source.getTimeTable().checkClash(targetIndex) ) return -3; //Source student clashes with new index
        if ( target.getTimeTable().checkClash(sourceIndex) )return -4; //Target student clashes with new index

        //Swop;
        sourceIndex.removeFromStudentList(source);
        targetIndex.removeFromStudentList(target);
        sourceIndex.addToStudentList(target);
        targetIndex.addToStudentList(source);

        return 1;

    }

    // check whether an index clashes with the registered indexes of the student
    public boolean checkIndexClash(Student s, Index i) {
        TimeTable timeTable = s.getTimeTable();
        try {
            return timeTable.checkClash(i);
        } catch (Exception ex) {
            // Wrong Entity.Index time will end up here
        }
        return true;

        // ArrayList<Entity.Index> regIndexes = s.getIndexList();
        // for (Entity.Index regIndex : regIndexes) {
        // ArrayList<Entity.Lesson> regLessons = regIndex.getLessonList();
        // for (Entity.Lesson regLesson : regLessons) {
        // for(Entity.Lesson newLesson : i.getLessonList()) {
        // if (regLesson.getDay() != newLesson.getDay()) {
        // continue;
        // } else {
        // return checkTimeClash(regLesson.getStartTime(), regLesson.getEndTime(),
        // newLesson.getStartTime(), newLesson.getEndTime());
        // }
        // }
        // }
        // }
        //
        // return false;
    }
}
