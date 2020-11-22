package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Entity.*;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;
import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import sg.edu.ntu.cz2002.grp3.util.PrettyPrinter;

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
        System.out.println("=== Change account password ===");
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
            if (!(input == 'y' || input=='n')) {
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
    public static void printStudentList(ArrayList<Student> studentList){
        System.out.println( "Total students: " + studentList.size() );
        for (int i = 0; i < studentList.size(); i++){
            Student student = studentList.get(i);
            System.out.println( (i+1) + ". " + student.getMatricNum() + ", " + student.getFullName() + ", " + student.getFacultyName());
        }
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
