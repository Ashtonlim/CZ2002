import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class View {

    /** Print the options available and get choice from user */
    public static int getOptions(String title, ArrayList<String> options){
        Scanner sc = new Scanner(System.in);
        int choice, counter = 0;
        System.out.println(title);

        for (String option : options){
            System.out.println( (counter+1) + ". " + options.get(counter));
            counter += 1;
        }

        System.out.println("0. Exit");

        //Get user choice.
        while(true){
            choice = getIntInput("Your selection: ");
            if (choice < 0 || choice > options.size()){
                System.out.println("Invalid input, choose between 0 to " + options.size() + " only.");
                continue;
            }
            break;
        }

        return choice;

    }

    /** Get int input from user */
    public static int getIntInput(String prompt){
        int choice;
        Scanner sc = new Scanner(System.in);
        while(true){
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
    public static String getTextInput(String prompt){
        String input;
        Scanner sc = new Scanner(System.in);
        while(true){
            try {
                System.out.println(prompt);
                input = sc.next();
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Invalid character(s) entered, please enter text only.");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                sc.nextLine();
            }
        }

        return input;
    }

    /** Print student list */
    public static void printStudentList(ArrayList<Student> studentList){
        System.out.println( "Total: " + studentList.size() );
        for (int i = 0; i < studentList.size(); i++){
            User student = studentList.get(i);
            System.out.println( (i+1) + student.getFullName() );
        }
    }

//    public static void main(String[] args){
//        ArrayList<String> options = new ArrayList<>();
//
//        options.add("option 1");
//        options.add("option 2");
//        options.add("option 3");
//
//        System.out.println(getTextInput("What is the name?"));
////        System.out.println(getOptions("=== WeiXing ===", options));
//    }
}
