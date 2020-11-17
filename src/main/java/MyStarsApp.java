import javax.crypto.spec.RC2ParameterSpec;
import java.util.*;

public class MyStarsApp {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int choice;
        final int maxStudents = 5;
        ArrayList<Student> Students = new ArrayList<Student>();
        RecordManager RM = new RecordManager();
        User activeUser = null;
        //Load dummy data
        while(true){
            System.out.println("Load dummy data?");
            System.out.println("1: Yes | 2: No");
            try{
                int c = sc.nextInt();
                System.out.println(c);
                if (c < 1 || c > 2){
                    throw new Exception();
                }

                if (c == 1){ RM.loadDummyData(); }
                break;
            } catch (Exception e){
                System.out.println("Invalid entry");
            }
        }

//        Log in
//        Non terminal code, to be changed to terminal version later
        System.out.println("=== User Login ===");
        LoginManager LM = new LoginManager();
        boolean success = false;
        while(!success) {
            String username = View.getTextInput("Username: ");
            String password = View.getTextInput("Password: ");
            User user = RM.getUser(username);
            if (user == null) {
                System.out.println("User does not exist.");
            } else {
                System.out.println("Logging in........");
                success = LM.verifyLogin(user, password);
                if (success){
                    System.out.println("Login successful.");
                    activeUser = user;
                } else{
                    System.out.println("Login failed. Password is incorrect.");
                }
            }
        }

        //Construct menu
        ArrayList<String> adminOptions = new ArrayList<>();
        adminOptions.add("Edit student access period");
        adminOptions.add("Add a student (name, matric number, gender, nationality, etc)");
        adminOptions.add("Add/Update a course (course code, school, its index numbers and vacancy).");
        adminOptions.add("Check available slot for an index number (vacancy in a class)");
        adminOptions.add("Print student list by index number.");
        adminOptions.add("Print student list by course (all students registered for the selected course).");
        adminOptions.add("Add a student (name, matric number, gender, nationality, etc)");

        ArrayList<String> studentOptions = new ArrayList<>();
        studentOptions.add("*Add Course");
        studentOptions.add("Drop Course");
        studentOptions.add("Check/Print Courses Registered");
        studentOptions.add("Check Vacancies Available");
        studentOptions.add("Change Index Number of Course");
        studentOptions.add("Swop Index Number with Another Student");
//
//        while(true) {
//            int c;
//            if (activeUser instanceof Student) {
//                c = View.getPrintOptions("=== User Screen ===", studentOptions);
//            } else if (activeUser instanceof Admin) {
//                c = View.getPrintOptions("=== User Screen ===", adminOptions);
//                switch (c) {
//                    case 4 :
//
//                }
//            } else {
//                continue;
//            }
//        }

//
//        Course c1 = RM.getCourse("CZ2003");
//        System.out.println(c1.getFacultyName());
//
//        AdminController AC = new AdminController(RM);
//        AC.printStudentListByCourse();
//        initStudents(Students);
//
//        for (int i = 0; i < 5; i++) {
//            Students.get(i).printStudentInfo();
//        }
//
////        Index x = new Index("1", 100, Students, Students);
////        x.printWaitList();
//
//        printMenu(true);
//
//        do {
//            printShortMenu(true);
//            System.out.print("\n  Enter the number of your choice: ");
//            choice = sc.nextInt();
//            switch (choice) {
//                case 1 -> System.out.println("1. Edit student access period");
//                case 2 -> System.out.println("2. Add a student (name, matric number, gender, nationality, etc)");
//                case 3 -> System.out.println("3. Add/Update a course (course code, school, its index numbers and vacancy).");
//                case 4 -> System.out.println("4. Check available slot for an index number (vacancy in a class)");
//                default -> System.out.println("Only input numbers from 1 to 7 inclusive");
//            }
//        } while(choice != 7);


    }

    // needs to be static as called from a static method (main)
    public static void printMenu(boolean user) {
        if(user) {
            System.out.println("1. Edit student access period");
            System.out.println("2. Add a student (name, matric number, gender, nationality, etc)");
            System.out.println("3. Add/Update a course (course code, school, its index numbers and vacancy).");
            System.out.println("4. Check available slot for an index number (vacancy in a class)");
            System.out.println("5. Print student list by index number.");
            System.out.println("6. Print student list by course (all students registered for the selected course).");
            System.out.println("7. Exit");
        } else {
            System.out.println("1. *Add Course");
            System.out.println("2. Drop Course");
            System.out.println("3. Check/Print Courses Registered");
            System.out.println("4. Check Vacancies Available");
            System.out.println("5. Change Index Number of Course");
            System.out.println("6. Swop Index Number with Another Student");
            System.out.println("7. Exit");
        }
    }

    public static void printShortMenu(boolean user) {
        if(user) {
            System.out.println("1. Edit student access period, 2. Add a student, 3. Add/Update a course, 4. P available index, 5. P student list (index), 6. P student list (course)");
        } else {
            System.out.println("1. Add Course, 2. Drop Course, 3. P Reg Courses, 4. P Vacancies, 5. Change Index, 6. Swap Index");
        }
    }


    public static void initStudents(ArrayList<Student> S) {
        for (int i = 0; i < 5; i++) {
            S.add(new Student("ash1", "pass", "ash", "3", Integer.toString(i), "SCSE", "123", 1, 50));
        }
    }
}
