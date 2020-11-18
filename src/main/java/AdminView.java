import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;

public class AdminView extends View {
    private final AdminController AC;
    private final Admin admin;

    public AdminView(MyStarsApp app, Admin admin) {
        super(app);
        this.admin = admin;
        this.AC = new AdminController(app.getRM());
    }

    /** Views for Admin */

    /** 4.Check available slot for an index number (vacancy in a class) -wx  */
    public void adminCheckVacancy(){
        System.out.println("=== Index Vacancy Checker ===");
        String indexCode = View.getTextInput("Index number: ");
        int vacancy = AC.checkVacancies(indexCode);
        if (vacancy != -1){
            System.out.println("Available slots: " + vacancy);
        } else {
            System.out.println("Index not found!");
        }
    }

    public void adminPrintStudentListByIndex(){
        System.out.println("=== Student List By Index ===");
        String indexCode = View.getTextInput("Index number: ");
        ArrayList<Student> studentList = AC.getStudentListByIndex(indexCode);
        if (studentList != null){
            printStudentList(studentList);
        } else {
            System.out.println("Index not found!");
        }
    }

    public void adminPrintStudentListByCourse(){
        System.out.println("=== Student List By Course ===");
        String indexCode = View.getTextInput("Course code: ");
        ArrayList<Student> studentList = AC.getStudentListByCourse(indexCode);
        if (studentList != null) {
            printStudentList(studentList);
        } else {
            System.out.println("Course not found!");
        }
    }

    public void adminAddCourse(){
        System.out.println("=== Add a new course ===");
        String faculty = View.getTextInput("Faculty name: ");
        AC.getFaulty(faculty);
    }

    @Override
    public void renderUserInfo() {
        Dictionary<String, String> info = AC.getDatabaseInfo();
        System.out.println("Welcome " + admin.getFullName() + "! | Account type: Admin.");
        System.out.println("There are " + info.get("facultySize") + " faculties, " + info.get("studentSize") + " students and " + info.get("courseSize") + " courses in the system.");
    }

    @Override
    public void renderMainMenu() {
        //Construct menu
        ArrayList<String> adminOptions = new ArrayList<>();
        adminOptions.add("Edit student access period");
        adminOptions.add("Add a student (name, matric number, gender, nationality, etc)");
        adminOptions.add("Add/Update a course (course code, school, its index numbers and vacancy).");
        adminOptions.add("Check available slot for an index number (vacancy in a class)");
        adminOptions.add("Print student list by index number.");
        adminOptions.add("Print student list by course (all students registered for the selected course).");
        adminOptions.add("Add a student (name, matric number, gender, nationality, etc)");
        boolean active = true;
        while(active){
            int c = View.getPrintOptions("=== Admin Screen ===", "Logout", adminOptions);
            switch (c) {
                case 4 -> adminCheckVacancy();
                case 5 -> adminPrintStudentListByIndex();
                case 6 -> adminPrintStudentListByCourse();
                case 0 -> {
                    active = false;
                    System.out.println("Logging out...");
                }
                default -> System.out.println("Option not available...");
            }
        }
    }

    @Override
    public void renderStartPage(){
        super.renderStartPage();
    }

    @Override
    public void renderLoginPage(){
        super.renderLoginPage();
    }
}
