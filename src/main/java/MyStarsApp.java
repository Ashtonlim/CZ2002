import java.util.*;

public class MyStarsApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int choice;
        final int maxStudents = 5;
        ArrayList<Student> Students = new ArrayList<Student>();


        /** Testing of RecordManager. */
//        try{
            System.out.println("----------");
            System.out.println("Testing file -> objects");
            RecordManager RM = new RecordManager();

            //Search a user
            User user1 = RM.getUser("weixing");
            ((Student) user1).printStudentInfo();

            //Get all users in a list
            User[] users = RM.getAllUsers();

            //Add a user
            User add = new Student("weixing5", "112233", "WX", "male", "U1234", "CSC", new ArrayList<Index>(), 0, 0);
            System.out.println("Status of add user: " + RM.addUser(add));

            Course c1 = RM.getCourse("CZ2001");
            System.out.println(c1.getCourseCode());
            //Saving to file
//            RM.save("users");
            System.out.println("----------");

//        } catch (Exception e){
//            System.out.println("Error encountered at main: " + e);
//            System.exit(1);
//        }


        initStudents(Students);

        for (int i = 0; i < 5; i++) {
            Students.get(i).printStudentInfo();
        }

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


    public static void initStudents(ArrayList<Student> S) {
        for (int i = 0; i < 5; i++) {
            S.add(new Student("ash1", "pass", "ash", "3", Integer.toString(i), "SCSE", new ArrayList<Index>(), 1, 50));
        }
    }
}
