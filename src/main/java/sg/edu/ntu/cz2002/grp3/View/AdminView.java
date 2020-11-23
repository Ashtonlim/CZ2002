package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Controller.*;
import sg.edu.ntu.cz2002.grp3.Entity.*;
import sg.edu.ntu.cz2002.grp3.util.IO;

import java.util.ArrayList;
import java.util.Dictionary;

public class AdminView implements IView {
	private final AdminController AC;

	public AdminView(AdminController AC) {
		this.AC = AC;
	}

    /** 1. Edit student access period  */
    public void adminEditAccessPeriod() {
    	System.out.println("=== Edit Student Access Period ===");
    	String facultyName = IO.getTextInput("Faculty name (SCSE, NBS etc.): ");
    	Faculty faculty = AC.getFaculty(facultyName);
    	if (faculty != null) {
    		try {
    			String currStart = TimeManager.dateTimeToStr(faculty.getStartDate());
	    		String currEnd = TimeManager.dateTimeToStr(faculty.getEndDate());
	    		System.out.println("Current access period for " + facultyName + ": " + currStart + " - " + currEnd);
    		} catch (NullPointerException e) {
    			System.out.println("Current access period for " + facultyName + ": None");
    		}
    	} else {
			System.out.println("Faculty not found.");
			return;
    	}

    	String startDateTime = IO.getTextInput("New starting date and time (yyyy-MM-dd HH:mm:ss): ");
    	String endDateTime = IO.getTextInput("New ending date and time (yyyy-MM-dd HH:mm:ss): ");
    	int result = AC.editAccessPeriod(faculty, startDateTime, endDateTime);
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
				adminPrintStudentListAll();
			}
			case 0 -> System.out.println("The username already exists");
			case -1 -> System.out.println("Invalid gender. Please input either 'M' or 'F'.");
			case -2 -> System.out.println("Faculty cannot be found.");
			case -3 -> System.out.println("Invalid year of study.");
		}
    }
    
    /** print full student list */
    public void adminPrintStudentListAll() {
    	System.out.println("=== All Students ===");
        ArrayList<Student> studentList = AC.getAllStudents();
        if (studentList != null){
            IO.printStudentList(studentList);
        } else {
            System.out.println("Student list is empty.");
        }
    }
    
    /** 3.Add course */
    public void adminAddCourse() {
        System.out.println("=== Add a New Course ===");
        String strFaculty = IO.getTextInput("Faculty name: ");
        Faculty faculty = AC.getFaculty(strFaculty);
        if(faculty != null) {
        	String courseCode = IO.getTextInput("Course code (e.g. CZ2002): ");
        	String courseName = IO.getTextInput("Course name: ");
        	String subjectType = IO.getTextInput("Subject type (CORE, GERPE-BM, UE, etc.): ");
        	int AU = IO.getIntInput("Number of AUs: ");
        	int result = AC.addCourse(courseCode, courseName, subjectType, AU, faculty);
			switch (result) {
				case 1 -> {
					System.out.println("Course " + courseCode + " " + courseName + " successfully added into system.");
					char choice = IO.getConfInput("Add indexes now? y/n");
					if (choice == 'Y') {
						Course course = AC.getCourse(courseCode);
						adminAddIndex(course);
					} else {
						IO.printCourseList(AC.getAllCourses());
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
    public void adminAddIndex(Course course) {
    	System.out.println("=== Add Indexes to Course ===");
    	boolean loop = true;
    	while (loop) {
    		String indexNo = IO.getTextInput("Index (e.g. 200201): ");
    		int slots = IO.getIntInput("Total vacancy: ");
    		int result = AC.addIndex(indexNo, slots, course);
			switch (result) {
				case 1 -> {
					System.out.println("Index " + indexNo + " successfully added to course " + course.getCourseName());
					adminAddLesson(AC.getIndex(indexNo));
					char choice = IO.getConfInput("Add another index? y/n");
					if (choice == 'N') {
						IO.printCourseList(AC.getAllCourses());
						loop = false;
					}
				}
				case 0 -> System.out.println("Index already exists.");
				case -1 -> System.out.println("Total vacancy cannot be less than 0.");
			}
    	}
    }

    /** add lessons to index */
    public void adminAddLesson(Index index) {
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
					result = AC.addLesson(type, day, start, end, venue, 2, index); //default weekly
				}
				case 2 -> {
					type = "Lab";
					int oddEven = IO.getIntInput("0. Even week / 1. Odd week / 2. Every week: ");
					result = AC.addLesson(type, day, start, end, venue, oddEven, index);
				}
				case 3 -> {
					type = "Tutorial";
					result = AC.addLesson(type, day, start, end, venue, 2, index);    //default weekly
				}
			}

			switch (result) {
				case 1 -> {
					System.out.println(type + " on " + TimeManager.numToDay(day) + " " + start + "-" + end
							+ " successfully added to index " + index.getIndex() + ".");
					printLessonsInIndex(index);
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
					printLessonsInIndex(index);
				}
			}
    	}
    }

    /** print lessons in index */
    public void printLessonsInIndex(Index index) {
    	System.out.println("=== Index " + index.getIndex() + " Lessons ===");
        ArrayList<Lesson> lessonList = index.getLessonList();
        if (lessonList != null){
            IO.printLessonList(lessonList);
        } else {
            System.out.println("Lesson list is empty.");
        }
        System.out.println(" ");
    }

    /** 4. Update course */
    public void adminUpdateCourse() {
    	System.out.println("=== Update a Course ===");
    	String courseCode = IO.getTextInput("Course code: ");
    	Course course = AC.getCourse(courseCode);
    	if (course == null) {
    		System.out.println("Course not found!");
    		return;
    	}
    	String courseName = course.getCourseName();
    	String facultyName = course.getFaculty().getName();
    	String subType = course.getSubjectType();
    	int AU = course.getAU();

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
        	System.out.println(courseCode + ", " + courseName + ", " + facultyName + ", "
    				+ subType + ", AU: " + AU);
        	String title = "=== Update " + courseCode + " ===";
            int c = IO.getPrintOptions(title, "Exit", courseOptions);
            switch (c) {
            	case 0:
            		return;
            	case 1:
            		String newCourseCode = IO.getTextInput("New course code: ");
            		if (AC.updateCourseCode(course, newCourseCode)) {
            			System.out.println(courseCode + " successfully changed to " + newCourseCode);
            			courseCode = newCourseCode;
            		} else {
            			System.out.println("The new course code already exists.");
            		}
            		break;
            	case 2:
            		String newCourseName = IO.getTextInput("New course name: ");
            		AC.updateCourseName(course, newCourseName);
            		System.out.println(courseName + " successfully changed to " + newCourseName);
            		courseName = newCourseName;
            		break;
            	case 3:
            		String newSubType = IO.getTextInput("New subject type: ");
            		AC.updateCourseName(course, newSubType);
            		System.out.println(subType + " successfully changed to " + newSubType);
            		subType = newSubType;
            		break;
            	case 4:
            		int newAU = IO.getIntInput("New AU: ");
            		if (AC.updateCourseAU(course, newAU)) {
            			System.out.println("AU successfully changed from " + AU + " to " + newAU + ".");
            			AU = newAU;
            		} else {
            			System.out.println("AU cannot be less than 1.");
            		}
            		break;
            	case 5:
            		adminAddIndex(course);
            		break;
            	case 6:
            		char confirm = IO.getConfInput("Removal cannot be undone. Are you sure you want to remove course? y/n");
            		if (confirm == 'Y') {
            			boolean result = AC.removeCourse(course);
            			if (result) {
            				System.out.println(courseName + " has been successfully removed from the database.");
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
            		adminUpdateIndex(course);
            		break;
                default:
                	System.out.println("Option not available...");
            }
			IO.pressEnterKeyToGoBack();
    	}
    }

    /** update indexes */
    public void adminUpdateIndex(Course course) {
    	ArrayList<Index> indexList = course.getIndexList();
    	IO.printIndexList(indexList);
    	String indexNo = IO.getTextInput("Enter index: ");
    	Index index = AC.getIndex(indexNo);
    	if (index == null) {
    		System.out.println("Index not found!");
    		return;
    	}
    	int vacancy = index.getVacancy();

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
	        		if (AC.updateIndexNo(index, newIndexNo)) {
	        			System.out.println(indexNo + " successfully changed to " + newIndexNo);
	        			indexNo = newIndexNo;
	        		} else {
	        			System.out.println("The new index already exists.");
	        		}
	        		break;
	        	case 2:
	        		int newVacancy = IO.getIntInput("New vacancy: ");
	        		if (AC.updateIndexVac(index, newVacancy)) {
	        			System.out.println("Vacancy successfully changed from " + vacancy + " to " + newVacancy + ".");
	        			vacancy = newVacancy;
	        		} else {
	        			System.out.println("Vacancy cannot be less than 0.");
	        		}
	        		break;
	        	case 3:
	        		char confirm = IO.getConfInput("Removal cannot be undone. Are you sure you want to remove index? y/n");
            		if (confirm == 'Y') {
            			if (AC.removeIndex(index)) {
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
    
    /** 4.Check available slot for an index number (vacancy in a class) -wx  */
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

	/** Print Student List by Index */
	public void adminPrintStudentListByIndex() {
		System.out.println("=== Student List By Index ===");
		String indexCode = IO.getTextInput("Index number: ");
		ArrayList<Student> studentList = AC.getStudentListByIndex(indexCode);
		if (studentList != null) {
			IO.printStudentList(studentList);
		} else {
			System.out.println("Index not found!");
		}
	}

	/** Print student List by Course */
	public void adminPrintStudentListByCourse() {
		System.out.println("=== Student List By Course ===");
		String courseCode = IO.getTextInput("Course code: ");
		ArrayList<Student> studentList = AC.getStudentListByCourse(courseCode);
		if (studentList != null) {
			IO.printStudentList(studentList);
		} else {
			System.out.println("Course not found!");
		}
	}

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
	
	
    /** print school course list */
    public void printCourseListFaculty() {
    	String facultyName = IO.getTextInput("Enter Faculty: ");
    	ArrayList<Course> courseList = AC.getFacultyCourses(facultyName);
        if (courseList != null) {
            IO.printCourseList(courseList);
        } else {
            System.out.println("Course list is empty or faculty does not exist");
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
