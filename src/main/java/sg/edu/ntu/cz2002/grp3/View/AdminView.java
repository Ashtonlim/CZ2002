package sg.edu.ntu.cz2002.grp3.View;
import sg.edu.ntu.cz2002.grp3.Entity.*;
import sg.edu.ntu.cz2002.grp3.Controller.AdminController;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;
import sg.edu.ntu.cz2002.grp3.Controller.TimeManager;

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
    
    /** 2. Add a student */
    public void adminAddStudent() {
    	System.out.println("=== Add a Student ===");
    	String username = View.getTextInput("Username: ");
    	String fullName = View.getTextInput("Full name: ");
    	String gender = View.getTextInput("Gender ('M' or 'F'): ");
    	String nationality = View.getTextInput("Nationality: ");
    	String matricNum = View.getTextInput("Matriculation number: ");
    	String faculty = View.getTextInput("Faculty (EEE, SCSE etc.): ");
    	int yearOfStudy = View.getIntInput("Year of study: ");
    	int result = AC.addStudent(username, fullName, gender, nationality, matricNum, faculty, yearOfStudy);
    	switch(result) {
		case 1:
			System.out.println("Student " + username + " successfully added into system.");
			adminPrintStudentListAll();
			break;
		case 0: 
			System.out.println("The username already exists");
			break;
		case -1:
			System.out.println("Invalid gender. Please input either 'M' or 'F'.");
			break;
		case -2:
			System.out.println("Faculty cannot be found.");
			break;
		case -3:
			System.out.println("Invalid year of study.");
			break;
    	}
    }
    
    /** print full student list */
    public void adminPrintStudentListAll() {
    	System.out.println("=== All Students ===");
        ArrayList<Student> studentList = AC.getAllStudents();
        if (studentList != null){
            printStudentList(studentList);
        } else {
            System.out.println("Student list is empty.");
        }
    }
    
    /** 3.Add course */
    public void adminAddCourse(){
        System.out.println("=== Add a New Course ===");
        String strFaculty = View.getTextInput("Faculty name: ");
        Faculty faculty = AC.getFaculty(strFaculty);
        if(faculty != null) {
        	String courseCode = View.getTextInput("Course code (e.g. CZ2002): ");
        	String courseName = View.getTextInput("Course name: ");
        	String subjectType = View.getTextInput("Subject type (CORE, GERPE-BM, UE, etc.): ");
        	int AU = View.getIntInput("Number of AUs: ");
        	int result = AC.addCourse(courseCode, courseName, subjectType, AU, faculty);
        	switch(result) {
        	case 1:
        		System.out.println("Course " + courseCode + " " + courseName + " successfully added into system.");
        		char choice = View.getConfInput("Add indexes now? y/n");
    			if (choice == 'Y') { 
    				Course course = AC.getCourse(courseCode);
            		adminAddIndexes(course, courseName);
            		break;
    			} else {
    				printCourseListAll();
    				break;
    			}
        	case 0:
        		System.out.println("Course already exists.");
        		break;
        	case -1:
        		System.out.println("AU cannot be less than 1.");
        		break;
        	}
        } else {
        	System.out.println("Faculty not found!");
        }
        
    }
    
    /** print full course list */
    public void printCourseListAll() {
    	System.out.println("=== All Courses ===");
        ArrayList<Course> courseList = AC.getAllCourses();
        if (courseList != null){
            printCourseList(courseList);
        } else {
            System.out.println("Student list is empty.");
        }
    }
    
    /** add indexes to course */
    public void adminAddIndexes(Course course, String courseName) {
    	System.out.println("=== Add Indexes to Course ===");
    	boolean loop = true;
    	while (loop) {
    		String indexNo = View.getTextInput("Index (e.g. 200201): ");
    		int slots = View.getIntInput("Total vacancy: ");
    		int result = AC.addIndex(indexNo, slots, course);
    		switch(result) {
    		case 1:
    			System.out.println("Index " + indexNo +" successfully added to course " + courseName);
    			adminAddLesson(AC.getIndex(indexNo), indexNo);
    			char choice = View.getConfInput("Add another index? y/n");
    			if (choice == 'N') {
    				printCourseListAll();
    				loop = false; 
    			}
    			break;
    		case 0:
    			System.out.println("Index already exists.");
    			break;
    		case -1:
    			System.out.println("Total vacancy cannot be less than 0.");
    			break;
    		}
    	}
    }
    
    /** add lessons to index */
    public void adminAddLesson(Index index, String indexNo) {
    	boolean loop = true;
    	while (loop) {
    		System.out.println("=== Add Lessons to Index ===");
	    	System.out.println("1. Lecture\n"
	    					 + "2. Lab\n"
	    					 + "3. Tutorial");
	    	int option = View.getIntInput("Your selection: ");
	    	if (!(option == 1 || option == 2 || option == 3)) { 
	    		System.out.println("Invalid option.");
	    		continue; 
	    	}
	    	int day = View.getIntInput("Day of week (1-6): ");
			String start = View.getTextInput("Start time (HH:mm): ");
			String end = View.getTextInput("End time (HH:mm): ");
			String venue = View.getTextInput("Venue: ");
			String type = null;
	    	int result = 0;
	    	switch(option) {
	    	case 1:
	    		type = "Lecture";
	    		result = AC.addLesson(type, day, start, end, venue, 2, index); //default weekly
	    		break;
	    	case 2:
	    		type = "Lab";
	    		int oddEven = View.getIntInput("0. Even week / 1. Odd week / 2. Every week: ");
	    		result = AC.addLesson(type, day, start, end, venue, oddEven, index);	
	    		break;
	    	case 3:
	    		type = "Tutorial";
	    		result = AC.addLesson(type, day, start, end, venue, 2, index);	//default weekly
	    		break;
	    	}
	    	
	    	switch(result) {
	    	case 1:
	    		System.out.println(type + " on " + TimeManager.numToDay(day) + " " + start + "-" + end 
	    				+ " successfully added to index " + indexNo + ".");
	    		printLessonsInIndex(index);
	    		char choice = View.getConfInput("Add another lesson? y/n");
    			if (choice == 'N') { 
    				loop = false; 
    			}
	    		break;
	    	case -1:
	    		System.out.println("Day is out of range.");
	    		break;
	    	case -2:
	    		System.out.println("Invalid week option.");
	    		break;
	    	case -3:
	    		System.out.println("Invalid time range.");
	    		break;
	    	case -4:
	    		System.out.println("Lesson clashes with existing lessons.");
	    		printLessonsInIndex(index);
	    	}
    	}
    }
    
    /** print lessons in index */
    public void printLessonsInIndex(Index index) {
    	System.out.println("=== Index " + index.getIndex() + " Lessons ===");
        ArrayList<Lesson> lessonList = index.getLessonList();
        if (lessonList != null){
            printLessonList(lessonList);
        } else {
            System.out.println("Lesson list is empty.");
        }
        System.out.println("");
    }
    
    /** 4. Update course */
    
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
        adminOptions.add("Add a course (course code, school, its index numbers and vacancy).");
        adminOptions.add("Update a course (course code, school, its index numbers and vacancy).");
        adminOptions.add("Check available slot for an index number (vacancy in a class)");
        adminOptions.add("Print student list by index number.");
        adminOptions.add("Print student list by course (all students registered for the selected course).");
        adminOptions.add("Change password.");
        while(true){
            int c = View.getPrintOptions(title, "Logout", adminOptions);
            switch (c) {
            	case 1 -> adminEditAccessPeriod();
            	case 2 -> adminAddStudent();
            	case 3 -> adminAddCourse();
                case 5 -> adminCheckVacancy();
                case 6 -> adminPrintStudentListByIndex();
                case 7 -> adminPrintStudentListByCourse();
                case 8 -> changePassword(admin);
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Option not available...");
            }
			pressEnterKeyToGoBack();
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
