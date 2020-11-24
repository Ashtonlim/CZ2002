package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Controller.StudentController;

import sg.edu.ntu.cz2002.grp3.util.IO;
import sg.edu.ntu.cz2002.grp3.util.PrettyPrinter;

import java.util.ArrayList;
import java.util.Dictionary;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentView.
 */
public class StudentView implements UserView {
    
    /** The sc. */
    private final StudentController SC;
    
    /** The Printer. */
    protected final PrettyPrinter Printer = new PrettyPrinter(System.out);


    /**
     * Instantiates a new student view.
     *
     * @param SC the sc
     */
    public StudentView(StudentController SC) {
        this.SC = SC;
    }

    /** 1.Add course */
    public void addCourse() {
        System.out.println("=== Add a course ===");
        String indexCode = IO.getTextInput("Enter index of course to add");
        int res = SC.addIndexToStudent(indexCode);

        switch(res){
            case 1 -> {
                System.out.println("Index added successfully");
                printCoursesRegistered();
            }
            case -11 -> System.out.println("Already registered index: " + indexCode);
            case -22 -> System.out.println("You already have an index under this course.");
            case -13 -> System.out.println("The index does not have a vacancy. Adding you to waitlist instead...");
            case -14 -> System.out.println("This index clashes with your timetable.");
            case -20 -> System.out.println("Invalid Index");
            default -> System.out.println("Unknown error.");
        }
    }

    /** 2.Drop course */
    public void dropCourse() {
        System.out.println("=== Drop a course ===");
        ArrayList<String> options = SC.getIndexListForPrinting();

        if (options.size() == 0) {
            System.out.println(" - You have not registered for any courses yet. - ");
            return;
        }
        int choice1 = IO.getPrintOptions("Which index would you like to drop?", "Back", options);
        if (choice1 == 0) return;
        SC.dropIndex(choice1);
        System.out.println(options.get(choice1 - 1) + " dropped.");
    }

    /**
     *  Print courses registered.
     */
    public void printCoursesRegistered() {
        System.out.println("=== Courses Registered ===");
        String[][] res = SC.getProcessedIndexListForPrinting();
        if (res.length == 1) {
            System.out.println(" - You have no course registered. - ");
        } else {
            Printer.print(res);
        }
    }

    /** 3. Check vacancies of a course */
    public void printVacanciesOfCourse() {
        System.out.println("=== Check vacancies of a course ===");

        System.out.println("Please input your CourseID to show indexes of the Course.");
        String courseCode = IO.getTextInput("CourseID: ");
        String[][] res = SC.getVacanciesForPrinting(courseCode);

        if (res.length == 0){
            System.out.println(" - Course does not exist - ");
        } else if (res.length == 1) {
            System.out.println(" - There is no index in this course. - ");
        } else {
            System.out.println("Index vacancies for courseCode.");
            Printer.print(res);
        }
    }

    /** 4. Print Timetable */
    public void printTimeTable() {
        System.out.println("=== Print Timetable ===");
        System.out.println("*Please maximize your console window to display timetable in correct format.");
        int choice = IO.getIntInput("Please choose: 1 - timetable of Even weeks | 2 - timetable of Odd weeks");
        String[][] res = SC.getTimeTableForPrinting(choice-1);
        Printer.print(res);
    }

    /** 6. Swop index */
    public void swopIndex() {
        while (true) {
            System.out.println("=== Swop index ===");

            ArrayList<String> options = SC.getIndexListForPrinting();
            if (options.size() == 0) {
                System.out.println(" - You have not registered for any courses yet. - ");
            }

            int choice1 = IO.getPrintOptions("Which index would you like to change?", "Back", options);
            if (choice1 == 0) return;

            String targetMatricNum = IO.getTextInput("Enter the matric number of student you are swopping with.");
            String targetPassword = IO.getTextInput("Enter the password of student you are swopping with.");

            int res = SC.swopIndex(targetMatricNum, targetPassword, choice1);

            switch (res) {
                case 1 -> {
                    System.out.println("Index successfully swopped with " + targetMatricNum);
                    return;
                }
                case -20 -> System.out.println("The matric number is invalid.");
                case -21 -> System.out.println("Invalid password! Please double check.");
                case -22 -> System.out.println("Your partner has not registered for this course.");
                case -23 -> System.out
                        .println("The index you are trying to swop with clashes with your current timetable.");
                case -24 -> System.out.println("Your old index clashes with your partner's timetable.");
                case -25 -> System.out.println("You and your partner have the same index.");
                default -> System.out.println("Unknown status, operation unsuccessful.");
            }
        }
    }

    /** 5. Change index */
    public void changeIndex() {
        while(true) {
            System.out.println("=== Change index ===");

            ArrayList<String> options = SC.getIndexListForPrinting();
            if (options.size() == 0) {
                System.out.println(" - You have not registered for any courses yet. - ");
            }

            int oldIndexPos = IO.getPrintOptions("Which index would you like to change?", "Back", options) - 1;
            if (oldIndexPos + 1 == 0) return;

            ArrayList<String> stringOtherIndexList = SC.getCourseIndexesForPrinting(oldIndexPos);

            while (true) {
                int newIndexPos = IO.getPrintOptions("Which index would you like to change to?", "Back", stringOtherIndexList) - 1;
                if (newIndexPos + 1 == 0) break;
                int res = SC.changeIndex(oldIndexPos, newIndexPos);
                switch (res) {
                    case -22 -> System.out.println("You selected the same index as your current one.");
                    case -13 -> System.out.println("No vacancies in selected index.");
                    case -21 -> System.out.println("Old index not in the same course as new index.");
                    case -14 -> System.out.println("New index clashes with your timetable.");
                    case 1 -> {
                        System.out.println("Index changed successfully.");
                        printCoursesRegistered();
                        return;
                    }
                    default -> System.out.println("Unknown status code: " + res +", operation unsuccessful.");
                }
            }
        }

    }

    /**
     * Render user info.
     */
    @Override
    public void renderUserInfo() {
        Dictionary<String, String> res = SC.getStudentDetails();
        System.out.println("Welcome " + res.get("fullName") + " | Account type: Student.");
        System.out.println("School: " + res.get("faculty") + " | AU Registered: " + res.get("regAU")
                + " | Number of Registered Courses: " + res.get("courses"));
    }

    /**
     * Change password.
     */
    public void changePassword() {
        System.out.println("=== Change account password (Console required) ===");
        // need to change to console version later
        System.out.print("Old ");
        String oldPassword = IO.getPassword("Password: ");
        System.out.print("New ");
        String newPassword = IO.getPassword("Password: ");
        boolean result = SC.changePassword(oldPassword, newPassword);
        if (result) {
            System.out.println("Password successfully changed.");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }
    
    /**
     *  print school course list.
     */
    public void printIndexListFromFaculty() {
    	String facultyName = IO.getTextInput("Enter Faculty: ");
        String[][] res = SC.getIndexListFromFacultyForPrinting(facultyName);
        if (res.length != 0) {
            Printer.print(res);
        } else {
            System.out.println("Course list is empty or faculty does not exist");
        }
    }

    /**
     * Render main menu.
     */
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
        studentOptions.add("Print available indexes from a faculty.");

        while (true) {
            int c = IO.getPrintOptions(title, "Logout", studentOptions);
            switch (c) {
                case 1 -> addCourse();
                case 2 -> dropCourse();
                case 3 -> printCoursesRegistered();
                case 4 -> printVacanciesOfCourse();
                case 5 -> {
                    changeIndex();
                }
                case 6 -> {
                    swopIndex();
                    printCoursesRegistered();
                }
                case 7 -> printTimeTable();
                case 8 -> changePassword();
                case 9 -> printIndexListFromFaculty();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Option not available...");
            }
            IO.pressEnterKeyToGoBack();
        }
    }

}
