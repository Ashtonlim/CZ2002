package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Entity.*;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;
import sg.edu.ntu.cz2002.grp3.Controller.TimeManager;
import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.util.PrettyPrinter;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    protected MyStarsApp app;
    protected final PrettyPrinter Printer = new PrettyPrinter(System.out);

    public View(MyStarsApp app) {
        this.app = app;

    }

    /** Common View.View */

    public void renderUserInfo() {
        System.out.println("Welcome guest! | Account type: Guest.");
        System.out.println("Please login!");
    }

    public void renderMainMenu() {
        System.out.println("Nothing to show here...");
    }

    /** Landing Page */
    public void renderStartPage() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Login");
        int choice = View.getPrintOptions("* MyStarsApp *", "Exit Program", options);
        app.setRunningStatus(choice != 0);
    }

    /** Login page */
    public void renderLoginPage() {
        LoginManager LM = new LoginManager(app.getRM());
        // Non terminal code, to be changed to terminal version lat
        System.out.println("=== User Login ===");
        String username = View.getTextInput("Username: ");
        String password = View.getTextInput("Password: ");
        System.out.println("Logging in........");
        User user = LM.login(username, password);
        if (user != null) {
            System.out.println("Login successfully.");
        } else {
            System.out.println("Username and Password combination does not match.");
        }
        app.setActiveUser(user);
    }

    /** Static methods */

    /** Print the options available and get choice from user */
    public static int getPrintOptions(String title, String endText, ArrayList<String> options) {
        Scanner sc = new Scanner(System.in);
        int choice, counter = 0;
        System.out.println(title);

        for (String option : options) {
            System.out.println((counter + 1) + ". " + options.get(counter));
            counter += 1;
        }

        System.out.println("0. " + endText);

        // Get user choice.
        while (true) {
            choice = getIntInput("Your selection: ");
            if (choice < 0 || choice > options.size()) {
                System.out.println("Invalid input, choose between 0 to " + options.size() + " only.");
                continue;
            }
            break;
        }

        return choice;
    }

    public static void changePassword(User user) {
        System.out.println("=== Change account password (Console required) ===");
        // need to change to console version later
        System.out.print("Old ");
        String oldPassword = View.getPassword("Password: ");
        System.out.print("New ");
        String newPassword = View.getPassword("Password: ");
        boolean result = LoginManager.changePassword(user, oldPassword, newPassword);
        if (result) {
            System.out.println("Password successfully changed.");
        } else {
            System.out.println("Old password is incorrect.");
        }
    }

    /** Get int input from user */
    public static int getIntInput(String prompt) {
        int choice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                choice = sc.nextInt();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid character(s) entered, please enter numbers/integers only.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                sc.nextLine();
            }
        }

        return choice;
    }

    /** Get text input from user */
    public static String getTextInput(String prompt) {
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                input = sc.nextLine();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid character(s) entered, please enter text only.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return input;
    }

    /** Get confirmation y/n input from user */
    public static char getConfInput(String prompt) {
        char input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(prompt);
            input = sc.next().charAt(0);
            input = Character.toUpperCase(input);
            if (!(input == 'Y' || input == 'N')) {
                System.out.println("Invalid option.");
            } else {
                break;
            }
        }
        return input;
    }

    /** get password with hidden fields */
    public static String getPassword(String prompt) {
        Console cons = System.console();
        char[] input = cons.readPassword(prompt);
        String password = String.valueOf(input);
        return password;
    }

    /** Print student list */
    public static void printStudentList(ArrayList<Student> studentList) {
        System.out.println("Total students: " + studentList.size());
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            System.out.println((i + 1) + ". " + student.getMatricNum() + ", " + student.getFullName() + ", "
                    + student.getFacultyName());
        }
    }

    /** Print course list */
    public static void printCourseList(ArrayList<Course> courseList) {
        System.out.println("Total Courses: " + courseList.size() + "\n");
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            System.out.println( (i+1) + ". " + course.getCourseCode() + ", " + course.getCourseName() + ", " + course.getFaculty().getName());
            ArrayList<Index> indexList = course.getIndexList();
            printIndexList(indexList);
            System.out.println("");
        }
    }

    /** print index list */
    public static void printIndexList(ArrayList<Index> indexList) {
    	if (indexList.size() != 0) {
            System.out.print("Indexes: | ");
        	for (int j = 0; j < indexList.size(); j++) {
            	Index index = indexList.get(j);
            	System.out.print(index.getIndex() + " | ");
        	}
        	System.out.println("");
        } else {
        	System.out.println("None");
        }
    }

    /** Print lesson list */
    public static void printLessonList(ArrayList<Lesson> lessonList) {
        System.out.println("Total Lessons: " + lessonList.size() + "\n");
        for (int i = 0; i < lessonList.size(); i++) {
            Lesson lesson = lessonList.get(i);
            String type = lesson.getType();
            String day = TimeManager.numToDay(lesson.getDayOfWeek());
            String start = TimeManager.dateTimeToStr(lesson.getStartTime());
            String end = TimeManager.dateTimeToStr(lesson.getEndTime());
            String venue = lesson.getVenue();
            int oddEven = lesson.getOddEvenWeek();
            String strOddEven;
            if (oddEven == 0) {
                strOddEven = "Even";
            } else {
                strOddEven = "Odd";
            }
            System.out.println((i + 1) + ". " + type + ", " + strOddEven + " " + day + ", " + start + " - " + end + ", "
                    + "Venue: " + venue);
        }
    }

    protected static void pressEnterKeyToGoBack() {
        System.out.println("Press Enter key to go back...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
        ;
    }
    // public static void main(String[] args){
    // ArrayList<String> options = new ArrayList<>();
    //
    // options.add("option 1");
    // options.add("option 2");
    // options.add("option 3");
    //
    // System.out.println(getTextInput("What is the name?"));
    //// System.out.println(getOptions("=== WeiXing ===", options));
    // }
}
