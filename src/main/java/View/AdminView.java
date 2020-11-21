package View;
import Entity.*;
import Controller.AdminController;
import Controller.*;
import View.View;

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

    /** 1. Edit student access period  */
    public void adminEditAccessPeriod() {
    	System.out.println("=== Edit Student Access Period ===");
    	String facultyName = View.getTextInput("Faculty name (EEE, SCSE etc.): ");
    	String startDateTime = View.getTextInput("Period starting date and time (yyyy-MM-dd HH:mm:ss): ");
    	String endDateTime = View.getTextInput("Period ending date and time (yyyy-MM-dd HH:mm:ss): ");
    	int result = AC.editAccessPeriod(facultyName, startDateTime, endDateTime);
    	switch(result) {
    		case 1:
    			System.out.println("Access period for " + facultyName + " successfully updated.");
    			break;
    		case 0: 
    			System.out.println("Invalid date and time. Please ensure format is strictly followed.");
    			break;
    		case -1:
    			System.out.println("Invalid period. Starting date and time must be before ending date and time");
    			break;
    		case -2:
    			System.out.println("Faculty not found");
    			break;
    	}
    }
    
    /** 2. Add a student  */
    public void adminAddStudent() {
    	System.out.println("=== Add a student ===");
    	String username = View.getTextInput("Username: ");
    	String fullName = View.getTextInput("Full name: ");
    	String gender = View.getTextInput("Gender: ");
    	String nationality = View.getTextInput("Nationality: ");
    	String matricNum = View.getTextInput("Matriculation number: ");
    	String faculty = View.getTextInput("Faculty: ");
    	int yearOfStudy = View.getIntInput("Year of study: ");
    	int result = AC.addStudent(username, fullName, gender, nationality, matricNum, faculty, yearOfStudy);
    	switch(result) {
		case 1:
			System.out.println("Student " + username + " successfully added into system.");
			break;
		case 0: 
			System.out.println("The username already exists");
			break;
		case -1:
			System.out.println("Invalid gender. Please input either 'm' or 'f'.");
			break;
		case -2:
			System.out.println("Faculty cannot be found.");
			break;
		case -3:
			System.out.println("Invalid year of study.");
			break;
    	}
    }
    
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
        AC.getFaculty(faculty);
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
        String title = "=== Admin Screen ===";
        ArrayList<String> adminOptions = new ArrayList<>();
        adminOptions.add("Edit student access period");
        adminOptions.add("Add a student (name, matric number, gender, nationality, etc)");
        adminOptions.add("Add/Update a course (course code, school, its index numbers and vacancy).");
        adminOptions.add("Check available slot for an index number (vacancy in a class)");
        adminOptions.add("Print student list by index number.");
        adminOptions.add("Print student list by course (all students registered for the selected course).");
        boolean active = true;
        while(active){
            int c = View.getPrintOptions(title, "Logout", adminOptions);
            switch (c) {
            	case 1 -> adminEditAccessPeriod();
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
