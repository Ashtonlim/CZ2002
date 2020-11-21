package View;
import Entity.*;
import Controller.StudentController;
import Controller.*;
import View.View;

import java.util.ArrayList;

public class StudentView extends View {
    private final StudentController SC;
    private final Student student;

    public StudentView(MyStarsApp app, Student student) {
        super(app);
        this.SC = new StudentController(app.getRM());
        this.student = student;
    }


    public void studentCheckVacanciesOfCourse(){
        System.out.println("=== Please input your CourseID to show indexes of the Course ===");
        String courseCode = View.getTextInput("CourseID: ");
        ArrayList<Index> indexList =  SC.checkVacanciesOfCourse(courseCode);
        if (indexList != null){
            System.out.println("=== IndexNumber == Vacancies === ");

            if (indexList.size() == 0){
                System.out.println("There is no index in this course.");
            }

            for (Index index : indexList){
                System.out.print("      " + index.getIndex());
                System.out.println("          " + index.getVacancy());
            }

        } else {
            System.out.println("Course does not exist");
        }
    }

    public void studentPrintTimeTable(){
        TimeTable timeTable = student.getTimeTable();
        Lesson[][] evenWeek = timeTable.getEvenWeek();
        Lesson[][] oddWeek = timeTable.getOddWeek();
        int choice = getIntInput("Print: 1 - Odd weeks | 2 - Even weeks?");
        if (choice == 1){
            String[][] tt = timeTable.processTimeTable(oddWeek);
            Printer.print(tt);
        } else {
            String[][] tt = timeTable.processTimeTable(evenWeek);
            Printer.print(tt);
        }

    }

    /** change password */
    public void changePassword() {
    	System.out.println("=== Change account password ===");
    	// need to change to console version later
    	String oldPassword = View.getTextInput("Old password: ");
    	String newPassword = View.getTextInput("New password: ");
    	boolean result = SC.changePassword(student, oldPassword, newPassword);
    	if (result == true) {
    		System.out.println("Password successfully changed.");
    	} else {
    		System.out.println("Old password is incorrect.");
    	}
    }
    
    @Override
    public void renderUserInfo() {
        System.out.println("Welcome " + student.getFullName() + " | Account type: Student.");
        System.out.println("School: " + student.getFacultyName() + "" +
                " | AU Registered: " + student.getRegAU() +
                " | Number of Registered Courses: " + student.getAllIndexes().size());
    }

    @Override
    public void renderMainMenu() {
        //Construct menu
        String title = "=== Student Screen ===";
        ArrayList<String> studentOptions = new ArrayList<>();
        studentOptions.add("*Add Course");
        studentOptions.add("Drop Course");
        studentOptions.add("Check/Print Courses Registered");
        studentOptions.add("Check Vacancies Available");
        studentOptions.add("Change Index Number of Course");
        studentOptions.add("Swop Index Number with Another Student");
        studentOptions.add("Print Time Table");

        boolean active = true;
        while(active){
            int c = View.getPrintOptions(title, "Logout", studentOptions);
            switch (c) {
                case 4 -> studentCheckVacanciesOfCourse();
                case 7 -> studentPrintTimeTable();
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
