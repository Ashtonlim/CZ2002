package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Index implements Serializable {
    private String index;
    private int vacancy;
    private ArrayList<Student> waitList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private Course course;

    public Index(String index, int vacancy, Course course) {
        this.index = index;
        this.vacancy = vacancy;
        this.course = course;
        course.addIndex(this);
    }

    public String getIndex() {
        return index;
    }

    public boolean addToWaitList(Student student) {
        return waitList.add(student);
    }

    public boolean removeFromWaitList(Student student) {
        return waitList.remove(student);
    }

    public boolean removeFromStudentList(Student student) {
        if (!student.hasIndex(this)) {
            System.out.println("Debug: Student does not have index " + getIndex());
            return false;
        }

        // if clashes when adding to timetable
        if (student.getTimeTable().removeIndex(this)) {
            System.out.println("Debug: Removed index " + getIndex());
            studentList.remove(student);
            if (waitList.size() > 0) {
                addToStudentList(waitList.get(0));
                System.out.println("System: Should send a notification also");
            }

            return true;
        }

        return false;
    }

    public ArrayList<Student> getWaitList() {
        return waitList;
    }

    public boolean addToStudentList(Student student) {

        // Check student's timetable
        if (student.hasIndex(this)) {
            System.out.println("Debug: Already registered index: " + getIndex());
            return false;
        }

        if (student.hasCourse(getCourseCode())) {
            System.out.println("Debug: Already registered this course: " + getCourseName());
            return false;
        }

        if (getVacancy() == 0) {
            System.out.println("The index does not have a vacancy. adding you to waitlist instead...");
            addToWaitList(student);
            return false;
        }

        // if clashes when adding to timetable
        if (student.getTimeTable().addIndex(this)) {
            System.out.println("Debug: Added to timetable successfully.");
            removeFromWaitList(student);
            studentList.add(student);
            return true;
        } else {
            System.out.println("System error (illegal operation): Unhandled clashes in timetable. Aborting...");
            return false;
        }
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void addToLessonList(Lesson lesson) {
        lessonList.add(lesson);
    }

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    public String getCourseCode() {
        return course.getCourseCode();
    }

    public int getAU() {
        return course.getAU();
    }

    public ArrayList<Index> getIndexesOfCourse() {
        return course.getIndexList();
    }

//    public void printLessonList() {
//        for (int i = 0; i < lessonList.size(); i++) {
//            lessonList.get(i).printLessonInfo();
//        }
//    }

    public int getVacancy() {
        return vacancy;
    }

    public int getStudentSize() {
        return studentList.size();
    }
    
    public int getTotalSlots() {
    	return studentList.size() + vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public void setIndex(String newIndex) {
        this.index = newIndex;
    }

    public String getCourseName() {
        return course.getCourseName();
    }
}
