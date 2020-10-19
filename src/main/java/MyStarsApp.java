import java.net.URISyntaxException;
import java.util.*;
import java.io.*;

public class MyStarsApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int choice;
        final int maxStudents = 5;
        Student[] Students = new Student[maxStudents];


        //Testing of database object.
        try{
            System.out.println("----------");
            System.out.println("Testing file -> objects");
            Database db = new Database();
//            db.printStudentInfo(); //Testing

            User user1 = db.getUser("weixing");
            ((Student) user1).printStudentInfo();

            User add = new Student("weixing2", "112233", "WX", "male", "U1234", "CSC", "118-118-190", 0, 0);
            System.out.println("Status of add user: " + db.addUser(add));
            db.save("users");
            System.out.println("----------");
        } catch (Exception e){
            System.out.println("Error encountered: " + e);
            System.exit(1);
        }


//        initStudents(Students);

//        for (int i = 0; i < 5; i++) {
//            S[i].printStudentInfo();
//        }

//        Index x = new Index("1", 100, Students, Students);
//        x.printWaitList();

        printMenu(true);

        do {
            printShortMenu(true);
            System.out.print("\n  Enter the number of your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> System.out.println("1. Edit student access period");
                case 2 -> System.out.println("2. Add a student (name, matric number, gender, nationality, etc)");
                case 3 -> System.out.println("3. Add/Update a course (course code, school, its index numbers and vacancy).");
                case 4 -> System.out.println("4. Check available slot for an index number (vacancy in a class)");
                default -> System.out.println("Only input numbers from 1 to 7 inclusive");
            }
        } while(choice != 7);


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


//    public static void initStudents(Student[] S) {
//        for (int i = 0; i < S.length; i++) {
//            S[i] = new Student(Integer.toString(i), "SCSE", "2", 10);
//        }
//    }
}
