import java.util.ArrayList;

public class StudentView extends View {
    private StudentController SC;
    private Student student;

    public StudentView(MyStarsApp app, Student student) {
        super(app);
        this.SC = new StudentController(app.getRM());
        this.student = student;
    }

    @Override
    public void renderUserInfo() {
        System.out.println("Welcome " + student.getFullName() + " | Account type: Student.");
        System.out.println("School: " + student.getFaculty() + "" +
                " | AU Registered:" + student.getRegAU() +
                " | Number of Registered Courses" + student.getNumOfCourses());
    }

    @Override
    public void renderMainMenu() {
        //Construct menu
        ArrayList<String> studentOptions = new ArrayList<>();
        studentOptions.add("*Add Course");
        studentOptions.add("Drop Course");
        studentOptions.add("Check/Print Courses Registered");
        studentOptions.add("Check Vacancies Available");
        studentOptions.add("Change Index Number of Course");
        studentOptions.add("Swop Index Number with Another Student");

        boolean active = true;
        while(active){
            int c = View.getPrintOptions("=== Admin Screen ===", "Logout", studentOptions);
            switch (c) {
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
