package sg.edu.ntu.cz2002.grp3.util;

import sg.edu.ntu.cz2002.grp3.Controller.TimeManager;
import sg.edu.ntu.cz2002.grp3.Entity.Course;
import sg.edu.ntu.cz2002.grp3.Entity.Index;
import sg.edu.ntu.cz2002.grp3.Entity.Lesson;
import sg.edu.ntu.cz2002.grp3.Entity.Student;

import java.io.Console;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Class providing static functions for getting various user input and
 * printing of lists.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class IO {

    /**
     * Prints the options available and get choice from user.
     *
     * @param title   the title of the menu
     * @param endText the text representing the final option
     * @param options the list of options to print
     * @return the user's choice
     */
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

    /**
     * Get an integer input from the user.
     *
     * @param prompt the message to prompt
     * @return the user's input
     */
    public static int getIntInput(String prompt) {
        int choice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                // choice = Integer.parseInt(sc.nextLine());
                choice = sc.nextInt();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid character(s) entered, please enter numbers/integers only.");
            } catch (Exception ex) {
                System.out.println("Invalid character(s) entered.");
            }
        }

        return choice;
    }

    /**
     * Get a string input from the user.
     *
     * @param prompt the message to prompt
     * @return the user's input
     */
    public static String getTextInput(String prompt) {
        String input;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println(prompt);
                input = sc.nextLine();
                if (input.equals("")) {
                    throw new InputMismatchException();
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid character(s) entered, please enter text only.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return input;
    }

    /**
     * Get yes/no confirmation from user.
     * 
     * @param prompt the message to prompt
     * @return the user's input
     */
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

    /**
     * Get password form user with hidden fields.
     *
     * @param prompt the message to prompt
     * @return the password
     */
    public static String getPassword(String prompt) {
        Console cons = System.console();
        char[] input = cons.readPassword(prompt);
        String password = String.valueOf(input);
        return password;
    }

    /**
     * Print the details of students in a student list.
     *
     * @param studentList the student list
     */
    public static void printStudentList(ArrayList<Student> studentList) {
        System.out.println("Total students: " + studentList.size());
        for (int i = 0; i < studentList.size(); i++) {
            Student student = studentList.get(i);
            System.out.println((i + 1) + ". " + student.getMatricNum() + ", " + student.getFullName() + ", "
                    + student.getFacultyName());
        }
    }

    /**
     * Print the details of courses in a course list.
     *
     * @param courseList the course list
     */
    public static void printCourseList(ArrayList<Course> courseList) {
        System.out.println("Total Courses: " + courseList.size() + "\n");
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            System.out.println((i + 1) + ". " + course.getCourseCode() + ", " + course.getCourseName() + ", "
                    + course.getFaculty().getName());
            ArrayList<Index> indexList = course.getIndexList();
            printIndexList(indexList);
            System.out.println("");
        }
    }

    /**
     * Print the indexes in an index list.
     *
     * @param indexList the index list
     */
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

    /**
     * Print the details of lessons in a lesson list.
     *
     * @param lessonList the lesson list
     */
    public static void printLessonList(ArrayList<Lesson> lessonList) {
        System.out.println("Total Lessons: " + lessonList.size() + "\n");
        for (int i = 0; i < lessonList.size(); i++) {
            Lesson lesson = lessonList.get(i);
            String type = lesson.getType();
            String day = TimeManager.numToDay(lesson.getDayOfWeek());
            String start = TimeManager.dateTimeToStr(lesson.getStartTime());
            String end = TimeManager.dateTimeToStr(lesson.getEndTime());
            String venue = lesson.getVenue();
            int oddEven = lesson.getWeekType();
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

    /**
     * Get user to press enter key to go back.
     */
    public static void pressEnterKeyToGoBack() {
        System.out.println("Press Enter key to go back...");
        try {
            System.in.read();
        } catch (Exception ignored) {
        }
        ;
    }
}
