package sg.edu.ntu.cz2002.grp3.Entity;

import sg.edu.ntu.cz2002.grp3.Controller.NotificationManager;
import sg.edu.ntu.cz2002.grp3.Entity.notification.EmailNotification;
import sg.edu.ntu.cz2002.grp3.Entity.notification.SMSNotification;
import sg.edu.ntu.cz2002.grp3.exceptions.IllegalMethodAccessException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an index of a course. Contains a list of its lessons. Also keeps
 * track of students registered under it and students waiting to be registered
 */
public class Index implements Serializable {
    /**
     * Used for versioning when serializing. Not necessary but added to remove
     * warning
     */
    private static final long serialVersionUID = 1659216272267144237L;

    /** The index. */
    private String index;

    /** The number of slots available to students. */
    private int vacancy;

    /** Contains students on hold due to no vacancy. */
    private ArrayList<Student> waitList = new ArrayList<>();

    /** Contains all students registered under the index. */
    private ArrayList<Student> studentList = new ArrayList<>();

    /** Contains all its scheduled lessons. */
    private ArrayList<Lesson> lessonList = new ArrayList<>();

    /** The course it belongs to. */
    private Course course;

    /**
     * Instantiates a new index.
     *
     * @param index   the index
     * @param vacancy the vacancy
     * @param course  the course
     */
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

    /**
     * Triggers the method to move students from the waitlist if necessary.
     * 
     * @param vacancy the new vacancy
     * @return true, if successful
     */
    public boolean setVacancy(int vacancy) {

        if (vacancy < 0) {
            System.out.println("cannot set vacancy less than 0");
            return false;
        }

        this.vacancy = vacancy;

        // decrease vacancy
        if (this.vacancy < vacancy) {
            waitlistToStudentList();
        }

        return true;
    }

    /**
     * Moves a student from the waitlist to the studentList every time the vacancies
     * change
     * 
     * @return true, if successful
     */
    public boolean waitlistToStudentList() {

        int availSlots = vacancy;

        for (Student s : waitList) {
            if (addToStudentList(s) == 1) {

                System.out.println("System: Removing " + s.getFullName()
                        + " from waitlist and Sending notification email out... ");
                NotificationManager.sendNotification(new EmailNotification(s.getEmail(), "Waitlist Notification",
                        "Congrats, you got into index " + getIndex()));
                NotificationManager.sendNotification(
                        new SMSNotification("+6596709488", "Congrats, you got into index " + getIndex()));
                System.out.println("System: Email sent to " + s.getFullName() + " - " + s.getEmail());
                availSlots -= 1;
                if (availSlots == 0) {
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * Adds a student to the wait list.
     *
     * @param student the student
     * @return true, if successful
     */
    public boolean addToWaitList(Student student) {
        return waitList.add(student);
    }

    /**
     * Removes a student from wait list.
     *
     * @param student the student
     * @return true, if successful
     */
    public boolean removeFromWaitList(Student student) {
        return waitList.remove(student);
    }

    /**
     * Removes a registered student from the student list.
     *
     * @param student the student
     * @return true, if successful
     */
    public boolean removeFromStudentList(Student student) {
        if (!student.hasIndex(this)) {
            return false;
        }

        // if clashes when adding to timetable
        if (student.getTimeTable().removeIndex(this)) {
            studentList.remove(student);
            setVacancy(vacancy + 1);

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
     *
     * @param student the student
     * @return the int
     */
    public int addToStudentList(Student student) {

        // Check student's timetable
        if (student.hasIndex(this)) {
            return -11;
        }

        // if (student.hasCourse(getCourseCode())) {
        // return -12;
        // }

        // if clashes when adding to timetable
        if (!student.getTimeTable().checkClash(this)) {
            if (getVacancy() == 0) {
                addToWaitList(student);
                return -13;
            }
            student.getTimeTable().addIndex(this);
            removeFromWaitList(student); // redundancy
            studentList.add(student);
            setVacancy(vacancy - 1);
            return 1;
        } else {

            return -14;
        }
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    /**
     * Adds a lesson to the lesson list.
     *
     * @param lesson the lesson
     * @throws IllegalMethodAccessException the illegal method access exception
     */
    public void addToLessonList(Lesson lesson) throws IllegalMethodAccessException {
        if (lesson.hasIndex()) {
            throw new IllegalMethodAccessException("Directly calling addToLessonList() from Index is not allowed.");
        } else {
            lessonList.add(lesson);
        }

    }

    public int getVacancy() {
        return vacancy;
    }

    public ArrayList<Lesson> getLessonList() {
        return lessonList;
    }

    public String getCourseCode() {
        return course.getCourseCode();
    }

    public String getCourseName() {
        return course.getCourseName();
    }

    public int getAU() {
        return course.getAU();
    }

    /**
     * Gets the list of indexes this index's course.
     *
     * @return list of indexes of course
     */
    public ArrayList<Index> getIndexesOfCourse() {
        return course.getIndexList();
    }

    /**
     * Gets the current number of students registered under the index.
     *
     * @return the number of students
     */
    public int getStudentSize() {
        return studentList.size();
    }

    /**
     * Gets the total slots the index has (available + unavailable).
     *
     * @return the total slots
     */
    public int getTotalSlots() {
        return studentList.size() + vacancy;
    }

    // public void cy(int vacancy) {
    // this.vacancy = vacancy;
    // }

    public void setIndex(String newIndex) {
        this.index = newIndex;
    }

    /**
     * Checks whether the index is under a course.
     *
     * @return true, if successful
     */
    public boolean hasCourse() {
        return course != null;
    }
}
