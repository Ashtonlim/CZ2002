package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Controller.*;
import sg.edu.ntu.cz2002.grp3.util.IO;
import sg.edu.ntu.cz2002.grp3.util.PrettyPrinter;

import java.util.ArrayList;
import java.util.Dictionary;

public class AdminView implements IView {
	private final AdminController AC;
	protected final PrettyPrinter Printer = new PrettyPrinter(System.out);

	public AdminView(AdminController AC) {
		this.AC = AC;
	}

    /** 1. Edit student access period  */
    public void adminEditAccessPeriod() {
    	System.out.println("=== Edit Student Access Period ===");
    	String facultyName = IO.getTextInput("Faculty name (SCSE, NBS etc.): ");
    	Dictionary<String, String> faculty = AC.getFacultyPeriod(facultyName);
    	if (faculty == null) {
    		System.out.println("Faculty not found.");
			return;
    	} else if (faculty.size() > 1) {
			String currStart = faculty.get("startDate");
    		String currEnd = faculty.get("endDate");
    		System.out.println("Current access period for " + facultyName + ": " + currStart + " - " + currEnd);		
    	} else {
    		System.out.println("Current access period for " + facultyName + ": None");
    	}

    	String startDateTime = IO.getTextInput("New starting date and time (yyyy-MM-dd HH:mm:ss): ");
    	String endDateTime = IO.getTextInput("New ending date and time (yyyy-MM-dd HH:mm:ss): ");
    	int result = AC.editAccessPeriod(facultyName, startDateTime, endDateTime);
		switch (result) {
			case 1 -> System.out.println("Access period for " + facultyName + " successfully updated.");
			case 0 -> System.out.println("Invalid date and time. Please ensure format is strictly followed.");
			case -1 -> System.out.println("Invalid period. Starting date and time must be before ending date and time.");
		}
    }
    
    /** 2. Add a student */
    public void adminAddStudent() {
    	System.out.println("=== Add a Student ===");
    	String username = IO.getTextInput("Username: ");
    	String fullName = IO.getTextInput("Full name: ");
    	String email = IO.getTextInput("Email: ");
    	String gender = IO.getTextInput("Gender ('M' or 'F'): ");
    	String nationality = IO.getTextInput("Nationality: ");
    	String matricNum = IO.getTextInput("Matriculation number: ");
    	String faculty = IO.getTextInput("Faculty (EEE, SCSE etc.): ");
    	int yearOfStudy = IO.getIntInput("Year of study: ");
    	int result = AC.addStudent(username, email, fullName, gender, nationality, matricNum, faculty, yearOfStudy);
		switch (result) {
			case 1 -> {
				System.out.println("Student " + username + " successfully added into system.");
				System.out.println("=== All Students ===");
		    	AC.printAllStudents();
		    }
			case 0 -> System.out.println("The username already exists");
			case -1 -> System.out.println("Invalid gender. Please input either 'M' or 'F'.");
			case -2 -> System.out.println("Faculty cannot be found.");
			case -3 -> System.out.println("Invalid year of study.");
		}
    }
    
    
    /** 3.Add course */
    public void adminAddCourse() {
        System.out.println("=== Add a New Course ===");
        String facultyName = IO.getTextInput("Faculty name: ");
        if(AC.checkFacultyExists(facultyName)) {
        	String courseCode = IO.getTextInput("Course code (e.g. CZ2002): ");
        	String courseName = IO.getTextInput("Course name: ");
        	String subjectType = IO.getTextInput("Subject type (CORE, GERPE-BM, UE, etc.): ");
        	int AU = IO.getIntInput("Number of AUs: ");
        	int result = AC.addCourse(courseCode, courseName, subjectType, AU, facultyName);
			switch (result) {
				case 1 -> {
					System.out.println("Course " + courseCode + " " + courseName + " successfully added into system.");
					char choice = IO.getConfInput("Add indexes now? y/n");
					if (choice == 'Y') {
						adminAddIndex(courseName);
					} else {
						AC.printAllCourses();
					}
				}
				case 0 -> System.out.println("Course already exists.");
				case -1 -> System.out.println("AU cannot be less than 1.");
			}
        } else {
        	System.out.println("Faculty not found!");
        }
    }


    /** add indexes to course */
    public void adminAddIndex(String courseCode) {
    	System.out.println("=== Add Indexes to Course ===");
    	boolean loop = true;
    	while (loop) {
    		String indexNo = IO.getTextInput("Index (e.g. 200201): ");
    		int slots = IO.getIntInput("Total vacancy: ");
    		int result = AC.addIndex(indexNo, slots, courseCode);
			switch (result) {
				case 1 -> {
					System.out.println("Index " + indexNo + " successfully added to course " + courseCode);
					adminAddLesson(indexNo);
					char choice = IO.getConfInput("Add another index? y/n");
					if (choice == 'N') {
						AC.printAllCourses();
						loop = false;
					}
				}
				case 0 -> System.out.println("Index already exists.");
				case -1 -> System.out.println("Total vacancy cannot be less than 0.");
			}
    	}
    }

    /** add lessons to index */
    public void adminAddLesson(String indexNo) {
    	boolean loop = true;
    	while (loop) {
    		System.out.println("=== Add Lessons to Index ===");
	    	System.out.println("1. Lecture\n"
	    					 + "2. Lab\n"
	    					 + "3. Tutorial");
	    	int option = IO.getIntInput("Your selection: ");
	    	if (!(option == 1 || option == 2 || option == 3)) { 
	    		System.out.println("Invalid option.");
	    		continue; 
	    	}
	    	int day = IO.getIntInput("Day of week (1-6): ");
			String start = IO.getTextInput("Start time (HH:mm): ");
			String end = IO.getTextInput("End time (HH:mm): ");
			String venue = IO.getTextInput("Venue: ");
			String type = null;
	    	int result = 0;
			switch (option) {
				case 1 -> {
					type = "Lecture";
					result = AC.addLesson(type, day, start, end, venue, 2, indexNo); //default weekly
				}
				case 2 -> {
					type = "Lab";
					int oddEven = IO.getIntInput("0. Even week / 1. Odd week / 2. Every week: ");
					result = AC.addLesson(type, day, start, end, venue, oddEven, indexNo);
				}
				case 3 -> {
					type = "Tutorial";
					result = AC.addLesson(type, day, start, end, venue, 2, indexNo);    //default weekly
				}
			}

			switch (result) {
				case 1 -> {
					System.out.println(type + " on " + TimeManager.numToDay(day) + " " + start + "-" + end
							+ " successfully added to index " + indexNo + ".");
					AC.printLessonsInIndex(indexNo);
					char choice = IO.getConfInput("Add another lesson? y/n");
					if (choice == 'N') {
						loop = false;
					}
				}
				case -1 -> System.out.println("Day is out of range.");
				case -2 -> System.out.println("Invalid week option.");
				case -3 -> System.out.println("Invalid time range.");
				case -4 -> {
					System.out.println("Lesson clashes with existing lessons.");
					AC.printLessonsInIndex(indexNo);
				}
			}
    	}
    }


    /** 4. Update course */
    public void adminUpdateCourse() {
    	System.out.println("=== Update a Course ===");
    	String courseCode = IO.getTextInput("Course code: ");
    	if (!AC.checkCourseExists(courseCode)) {
    		System.out.println("Course not found!");
    		return;
    	}
    	Dictionary<String, String> res = AC.getCourseDetails(courseCode);
    	
    	ArrayList<String> courseOptions = new ArrayList<>();
    	courseOptions.add("Change course code");
    	courseOptions.add("Change course name");
    	courseOptions.add("Change subject type");
    	courseOptions.add("Change AU");
    	courseOptions.add("Add new index");
    	courseOptions.add("Remove course from database");
    	courseOptions.add("Update indexes (Index no, vacancies, remove)");
    	boolean loop = true;
    	while(loop) {
        	System.out.println(courseCode + ", " + res.get("courseName") + ", " + res.get("faculty") + ", "
    				+ res.get("subjectType") + ", AU: " + res.get("AU"));
        	String title = "=== Update " + courseCode + " ===";
            int c = IO.getPrintOptions(title, "Exit", courseOptions);
            switch (c) {
            	case 0:
            		return;
            	case 1:
            		String newCourseCode = IO.getTextInput("New course code: ");
            		if (AC.updateCourseCode(courseCode, newCourseCode)) {
            			System.out.println(courseCode + " successfully changed to " + newCourseCode);
            			courseCode = newCourseCode;
            		} else {
            			System.out.println("The new course code already exists.");
            		}
            		break;
            	case 2:
            		String newCourseName = IO.getTextInput("New course name: ");
            		AC.updateCourseName(courseCode, newCourseName);
            		System.out.println(res.get("courseName") + " successfully changed to " + newCourseName);
            		res.put("courseName", newCourseName);
            		break;
            	case 3:
            		String newSubType = IO.getTextInput("New subject type: ");
            		AC.updateCourseName(courseCode, newSubType);
            		System.out.println(res.get("subjectType") + " successfully changed to " + newSubType);
            		res.put("subjectType", newSubType);
            		break;
            	case 4:
            		int newAU = IO.getIntInput("New AU: ");
            		if (AC.updateCourseAU(courseCode, newAU)) {
            			System.out.println("AU successfully changed from " + res.get("AU") + " to " + newAU + ".");
            			res.put("AU", Integer.toString(newAU));
            		} else {
            			System.out.println("AU cannot be less than 1.");
            		}
            		break;
            	case 5:
            		adminAddIndex(courseCode);
            		break;
            	case 6:
            		char confirm = IO.getConfInput("Removal cannot be undone. Are you sure you want to remove course? y/n");
            		if (confirm == 'Y') {
            			boolean result = AC.removeCourse(courseCode);
            			if (result) {
            				System.out.println(courseCode + " has been successfully removed from the database.");
            				loop = false;
            			} else {
            				System.out.println("Error removing course from the database.");
            			}
            		} else {
            			System.out.println("Course removal cancelled.");
            			return;
            		}
            		break;
            	case 7:
            		adminUpdateIndex(courseCode);
            		break;
                default:
                	System.out.println("Option not available...");
            }
			IO.pressEnterKeyToGoBack();
    	}
    }

    /** update indexes */
    public void adminUpdateIndex(String courseCode) {
    	
    	String indexNo = IO.getTextInput("Enter index: ");
    	if (!AC.checkIndexExists(indexNo)) {
    		System.out.println("Index not found!");
    		return;
    	}
    	int vacancy = AC.checkVacancies(indexNo);

		ArrayList<String> indexOptions = new ArrayList<>();
		indexOptions.add("Change index number");
		indexOptions.add("Change vacancies");
		indexOptions.add("Remove index from course");
		boolean loop = true;
		while(loop) {
	    	System.out.println(indexNo + ", Vacancy: " + vacancy);
	    	String title = "=== Update " + indexNo + " ===";
	        int c = IO.getPrintOptions(title, "Exit", indexOptions);
	        switch (c) {
	        	case 0:
	        		return;
	        	case 1:
	        		String newIndexNo = IO.getTextInput("New index: ");
	        		if (AC.updateIndexNo(indexNo, newIndexNo)) {
	        			System.out.println(indexNo + " successfully changed to " + newIndexNo);
	        			indexNo = newIndexNo;
	        		} else {
	        			System.out.println("The new index already exists.");
	        		}
	        		break;
	        	case 2:
	        		int newVacancy = IO.getIntInput("New vacancy: ");
	        		if (AC.updateIndexVac(indexNo, newVacancy)) {
	        			System.out.println("Vacancy successfully changed from " + vacancy + " to " + newVacancy + ".");
	        			vacancy = newVacancy;
	        		} else {
	        			System.out.println("Vacancy cannot be less than 0.");
	        		}
	        		break;
	        	case 3:
	        		char confirm = IO.getConfInput("Removal cannot be undone. Are you sure you want to remove index? y/n");
            		if (confirm == 'Y') {
            			if (AC.removeIndex(indexNo)) {
            				System.out.println(indexNo + " has been successfully removed from the course.");
            				loop = false;
            			} else {
            				System.out.println("Error removing index from the database.");
            			}
            		} else {
            			System.out.println("Index removal cancelled.");
            			return;
            		}
            		break;
	            default:
	            	System.out.println("Option not available...");
	        }
			IO.pressEnterKeyToGoBack();
		}
    }
    
    
    /** 5.Check available slot for an index number (vacancy in a class) -wx  */
    public void adminCheckVacancy(){
        System.out.println("=== Index Vacancy Checker ===");
        String indexCode = IO.getTextInput("Index number: ");
        int vacancy = AC.checkVacancies(indexCode);
        if (vacancy != -1){
            System.out.println("Available slots: " + vacancy);
        } else {
            System.out.println("Index not found!");
        }
    }

    
	/** 6.Print Student List by Index */
	public void adminPrintStudentListByIndex() {
		System.out.println("=== Student List By Index ===");
		String indexCode = IO.getTextInput("Index number: ");
		AC.printStudentListByIndex(indexCode);
	}

	
	/** 7.Print student List by Course */
	public void adminPrintStudentListByCourse() {
		System.out.println("=== Student List By Course ===");
		String courseCode = IO.getTextInput("Course code: ");
		AC.printStudentListByCourse(courseCode);
	}
	

    /** 8.print course list by faculty */
    public void printCourseListFaculty() {
    	String facultyName = IO.getTextInput("Enter Faculty: ");
        String[][] res = AC.getIndexListFromFacultyForPrinting(facultyName);
        if (res.length != 0) {
            Printer.print(res);
        } else {
            System.out.println("Course list is empty or faculty does not exist");
        }
    }
    

	/** 9.change password */
	public void changePassword() {
		System.out.println("=== Change account password (Console required) ===");
		// need to change to console version later
		System.out.print("Old ");
		String oldPassword = IO.getPassword("Password: ");
		System.out.print("New ");
		String newPassword = IO.getPassword("Password: ");
		boolean result = AC.changePassword(oldPassword, newPassword);
		if (result) {
			System.out.println("Password successfully changed.");
		} else {
			System.out.println("Old password is incorrect.");
		}
	}
    

	@Override
	public void renderStartPage() {
		System.out.println("Please Logout first.");
	}

	@Override
	public void renderLoginPage() {
		System.out.println("You have already logged-in.");
	}

	@Override
	public void renderUserInfo() {

		Dictionary<String, String> info = AC.getWelcomeInfo();
		System.out.println("Welcome " + ( info.get("fullName") + "! | Account type: Admin."));
		System.out.println("There are " + info.get("facultySize") + " faculties, " + info.get("studentSize")
				+ " students and " + info.get("courseSize") + " courses in the system.");
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
        adminOptions.add("Print courses from a faculty.");
        while(true){
            int c = IO.getPrintOptions(title, "Logout", adminOptions);
            switch (c) {
            	case 1 -> adminEditAccessPeriod();
            	case 2 -> adminAddStudent();
            	case 3 -> adminAddCourse();
            	case 4 -> adminUpdateCourse();
                case 5 -> adminCheckVacancy();
                case 6 -> adminPrintStudentListByIndex();
                case 7 -> adminPrintStudentListByCourse();
                case 8 -> changePassword();
                case 9 -> printCourseListFaculty();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Option not available...");
            }
			IO.pressEnterKeyToGoBack();
		}
	}

}
