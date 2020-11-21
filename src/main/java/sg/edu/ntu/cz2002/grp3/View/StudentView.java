package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Entity.*;
import sg.edu.ntu.cz2002.grp3.Controller.StudentController;
import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StudentView extends View {
    private final StudentController SC;
    private final Student student;

    public StudentView(MyStarsApp app, Student student) {
        super(app);
        this.SC = new StudentController(app.getRM());
        this.student = student;
    }

    /** Views for Student */

    /** 1.Add course */
    public void addCourse() {
        System.out.println("=== Add a course ===");
        
        String indexCode = getTextInput("Enter index of course to add");

        Index index = SC.checkValidIndex(indexCode);
        if (index == null) {
            System.out.println("Invalid index " + indexCode);
            return;
        }

        System.out.println("Adding index");
        if (student.addIndex(index)) {
            System.out.println("Added index " + index.getIndex());
        } else {
            System.out.println(index.getIndex() + "cannot be added");
        }
    }

    /** 2.Drop course */
    public void dropCourse() {
        System.out.println("=== Drop a course ===");
        String indexCode = getTextInput("Enter index of course to drop");
        student.removeIndex(indexCode);
    }

    /** 3. Check vacancies of a course */
    public void checkVacanciesOfCourse() {
        System.out.println("=== Check vacancies of a course ===");

        System.out.println("Please input your CourseID to show indexes of the Course: ");
        String courseCode = View.getTextInput("CourseID: ");
        ArrayList<Index> indexList = SC.checkVacanciesOfCourse(courseCode);
        
        if (indexList != null) {
            if (indexList.size() == 0) {
                System.out.println("There is no index in this course.");
            } else {
                System.out.println("=== IndexNumber == Vacancies === ");
                for (Index index : indexList) {
                   System.out.print("      " + index.getIndex());
                   System.out.println("          " + index.getVacancy());
                }
            }
        } else {
            System.out.println("Course does not exist");
        }
    }

    /** 4. Print Timetable */
    public void printTimeTable() {
        System.out.println("=== Print Timetable ===");
        
        TimeTable timeTable = student.getTimeTable();
        Lesson[][] evenWeek = timeTable.getEvenWeek();
        Lesson[][] oddWeek = timeTable.getOddWeek();
        int choice = getIntInput("Please choose: 1 - timetable of Odd weeks | 2 - timetable of Even weeks");
        String[][] tt;
        
        if (choice == 1) {
            tt = timeTable.processTimeTable(oddWeek);
        } else {
            tt = timeTable.processTimeTable(evenWeek);
        }
        
        Printer.print(tt);
    }

    /** 5. Change index */
    public void changeIndex(){
        System.out.println("=== Change index ===");
        ArrayList<String> stringIndexList = new ArrayList<>();
        ArrayList<Index> indexList = student.getIndexList();

        //Print indexes of the student.
        for (Index index : indexList){
            String string = index.getCourseName() + " - " + index.getIndex();
            stringIndexList.add(string);
        }
        int choice1 = getPrintOptions("Which index would you like to change?", "Back", stringIndexList);
        if (choice1 == 0) return;
        Index oldIndex = indexList.get(choice1 - 1);

        //Print available indexes under same course
        ArrayList<String> stringOtherIndexList = new ArrayList<>();
        ArrayList<Index> otherIndexes = oldIndex.getIndexesOfCourse();
        for (Index index : otherIndexes){
            String string = index.getCourseName() + " - " + index.getIndex();
            stringOtherIndexList.add(string);
        }
        int choice2 = getPrintOptions("Which index would you like to change to?", "Back", stringOtherIndexList);
        if (choice2 == 0) return;
        Index newIndex = otherIndexes.get(choice2-1);
        SC.changeIndex(student, oldIndex, newIndex);
        System.out.println("Index changed successfully.");
    }

    /** 6. Change password */
    public void changePassword() {
        System.out.println("=== Change account password ===");
        // need to change to console version later
        String oldPassword = View.getTextInput("Old password: ");
        String newPassword = View.getTextInput("New password: ");
        boolean result = LoginManager.changePassword(student, oldPassword, newPassword);
        if (result == true) {
            System.out.println("Password successfully changed.");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    @Override
    public void renderUserInfo() {
        System.out.println("Welcome " + student.getFullName() + " | Account type: Student.");
        System.out.println("School: " + student.getFacultyName() + "" + " | AU Registered: " + student.getRegAU()
                + " | Number of Registered Courses: " + student.getIndexList().size());
    }

    @Override
    public void renderMainMenu() {
        // Construct menu
        String title = "=== Student Screen ===";
        ArrayList<String> studentOptions = new ArrayList<>();
        studentOptions.add("*Add Course");
        studentOptions.add("Drop Course");
        studentOptions.add("Check/Print Courses Registered");
        studentOptions.add("Check Vacancies Available");
        studentOptions.add("Change Index Number of Course");
        studentOptions.add("Swap Index Number with Another Student");
        studentOptions.add("Print Time Table");
        studentOptions.add("Change Password");

        boolean active = true;
        while (active) {
            int c = View.getPrintOptions(title, "Logout", studentOptions);
            switch (c) {
                case 1 -> addCourse();
                case 2 -> dropCourse();
                case 4 -> checkVacanciesOfCourse();
                case 5 -> changeIndex();
                case 7 -> printTimeTable();
                case 8 -> changePassword();
                case 0 -> {
                    active = false;
                    System.out.println("Logging out...");
                }
                default -> System.out.println("Option not available...");
            }
        }
    }

    @Override
    public void renderStartPage() {
        super.renderStartPage();
    }

    @Override
    public void renderLoginPage() {
        super.renderLoginPage();
    }
}
