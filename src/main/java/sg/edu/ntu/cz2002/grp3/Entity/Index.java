package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.Controller.NotificationManager;
import sg.edu.ntu.cz2002.grp3.Entity.notification.EmailNotification;
import sg.edu.ntu.cz2002.grp3.Entity.notification.SMSNotification;
import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

import java.io.Serializable;
import java.util.ArrayList;

public class Index implements Serializable {

    private static final long serialVersionUID = 1659216272267144237L;
    private String index;
    private int vacancy;
    private ArrayList<Student> waitList = new ArrayList<>();
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Lesson> lessonList = new ArrayList<>();
    private Course course;

    public Index(String index, int vacancy, Course course) {
        this.index = index;
        this.vacancy = vacancy;
        try {
            course.addIndex(this);
        } catch (IllegalMethodAccessException ignored) {
        }
        this.course = course;
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
            return false;
        }

        // if clashes when adding to timetable
        if (student.getTimeTable().removeIndex(this)) {
            studentList.remove(student);
            vacancy += 1;

            for (Student s : waitList) {
                if (addToStudentList(s) == 1) {
                    System.out.println("System: Removing " + s.getFullName()
                            + " from waitlist and Sending notification email out... ");
                    NotificationManager.sendNotification( new EmailNotification(s.getEmail(), "Waitlist Notification", "Congrats, you got into index " + getIndex()) );
                    NotificationManager.sendNotification( new SMSNotification("+6596709488", "Congrats, you got into index " + getIndex()) );
                    System.out.println("System: Email sent to " + s.getFullName() + " - " + s.getEmail());
                    break;
                }
            }

            return true;
        }
        return false;
    }

    public ArrayList<Student> getWaitList() {
        return waitList;
    }

    /**
     * 1 - Success | -11 - Index already registered by this student. -12 - Already
     * registered this course | -13 - No vacancies -14 - Clash
     */
    public int addToStudentList(Student student) {

        // Check student's timetable
        if (student.hasIndex(this)) {
            return -11;
        }

        if (student.hasCourse(getCourseCode())) {
            return -12;
        }

        // if clashes when adding to timetable
        if (!student.getTimeTable().checkClash(this)) {
            if (getVacancy() == 0) {
                addToWaitList(student);
                return -13;
            }
            student.getTimeTable().addIndex(this);
            System.out.println("Debug: Added to timetable successfully.");
            removeFromWaitList(student); // redundancy
            studentList.add(student);
            vacancy -= 1;
            return 1;
        } else {

            return -14;
        }
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void addToLessonList(Lesson lesson) throws IllegalMethodAccessException {
        if (lesson.hasIndex()) {
            throw new IllegalMethodAccessException("Directly calling addToLessonList() from Index is not allowed.");
        } else {
            lessonList.add(lesson);
        }

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

    // public void printLessonList() {
    // for (int i = 0; i < lessonList.size(); i++) {
    // lessonList.get(i).printLessonInfo();
    // }
    // }

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

    public boolean hasCourse() {
        return course != null;
    }
}
