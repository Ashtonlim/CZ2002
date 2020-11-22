package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Entity.*;
import sg.edu.ntu.cz2002.grp3.Controller.StudentController;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;

import java.util.ArrayList;

public class StudentView extends View {
    private final StudentController SC;
    private final Student student;

    public StudentView(MyStarsApp app, Student student) {
        super(app);
        this.SC = new StudentController(app.getRM());
        this.student = student;
    }

    /** Views for Student */

    /** 1.Add course */
    public void addCourse() {
        System.out.println("=== Add a course ===");

        String indexCode = getTextInput("Enter index of course to add");

        Index index = SC.checkValidIndex(indexCode);
        if (index == null) {
            System.out.println("Invalid index " + indexCode);
            return;
        }

        System.out.println("Adding index");
        if (index.addToStudentList(student)) {
            System.out.println("Added index " + index.getIndex());
        } else {
            System.out.println(index.getIndex() + "cannot be added");
        }
    }

    /** 2.Drop course */
    public void dropCourse() {
        System.out.println("=== Drop a course ===");
        String indexCode = getTextInput("Enter index of course to drop");
        Index index = SC.checkValidIndex(indexCode);
        index.removeFromStudentList(student);
    }

    /** Print courses registered */

    public void printCoursesRegistered(){
        System.out.println("=== Courses Registered ===");
        ArrayList<Index> indexList = SC.getCourseReg(student);

        String[][] res = new String[indexList.size() + 1][4];
        res[0][0] = " No. ";
        res[0][1] = " Course Code ";
        res[0][2] = " Course Name ";
        res[0][3] = " Course Index ";

        for (int i = 1; i < indexList.size() + 1; i++){
            for (int j = 0; j < 4; j++){
                switch (j){
                    case 0 -> res[i][j] = " " + i + " ";
                    case 1 -> res[i][j] = " " + indexList.get(i-1).getCourseCode() + " ";
                    case 2 -> res[i][j] = " " + indexList.get(i-1).getCourseName() + " ";
                    case 3 -> res[i][j] = " " + indexList.get(i-1).getIndex() + " ";
                    default -> res[i][j] = " Error ";
                }
            }
        }

        if (res.length == 1){
            System.out.println(" - You have no course registered. - ");
        }
        Printer.print(res);
    }

    /** 3. Check vacancies of a course */
    public void printVacanciesOfCourse() {
        System.out.println("=== Check vacancies of a course ===");

        System.out.println("Please input your CourseID to show indexes of the Course.");
        String courseCode = View.getTextInput("CourseID: ");
        ArrayList<Index> indexList = SC.getVacanciesOfCourse(courseCode);
        if (indexList == null){
            System.out.println("Course does not exist");
            return;
        }

        String[][] res = new String[indexList.size() + 1][3];
        res[0][0] = " No. ";
        res[0][1] = " Index Number ";
        res[0][2] = " Vacancies ";

        for (int i = 1; i < indexList.size() + 1; i++){
            for (int j = 0; j < 3; j++){
                switch (j){
                    case 0 -> res[i][j] = " " + i + " ";
                    case 1 -> res[i][j] = " " + indexList.get(i-1).getIndex() + " ";
                    case 2 -> res[i][j] = " " + indexList.get(i-1).getVacancy() + " ";
                    default -> res[i][j] = " Error ";
                }
            }
        }

        if (res.length == 1){
            System.out.println(" - There is no index in this course. - ");
        } else {
            System.out.println("Index vacancies for " + indexList.get(0).getCourseCode() + " - " +indexList.get(0).getCourseName() + ": ");
            Printer.print(res);
        }
    }

    /** 4. Print Timetable */
    public void printTimeTable() {
        System.out.println("=== Print Timetable ===");

        TimeTable timeTable = student.getTimeTable();
        Lesson[][] evenWeek = timeTable.getEvenWeek();
        Lesson[][] oddWeek = timeTable.getOddWeek();
        int choice = getIntInput("Please choose: 1 - timetable of Odd weeks | 2 - timetable of Even weeks");
        String[][] tt;

        if (choice == 1) {
            tt = timeTable.processTimeTable(oddWeek);
        } else {
            tt = timeTable.processTimeTable(evenWeek);
        }

        Printer.print(tt);
    }

    /** 6. Swop index */
    public void swopIndex() {
        while (true) {
            System.out.println("=== Swop index ===");
            ArrayList<String> stringIndexList = new ArrayList<>();
            ArrayList<Index> indexList = student.getIndexList();

            // Print indexes of the student.
            for (Index index : indexList) {
                String string = index.getCourseName() + " - " + index.getIndex();
                stringIndexList.add(string);
            }
            int choice1 = getPrintOptions("Which index would you like to change?", "Back", stringIndexList);
            if (choice1 == 0)
                return;
            Index sourceIndex = indexList.get(choice1 - 1);

            String targetMatricNum = getTextInput("Enter the matric number of student you are swopping with.");
            String targetPassword = getTextInput("Enter the password of student you are swopping with.");
            int res = SC.swopIndex(student, targetMatricNum, targetPassword, sourceIndex);
            switch (res) {
                case 1 -> System.out.println("Index successfully swopped with " + targetMatricNum);
                case 0 -> System.out.println("The matric number is invalid.");
                case -1 -> System.out.println("Invalid password! Please double check.");
                case -2 -> System.out.println("Your partner has not registered for this course.");
                case -3 -> System.out
                        .println("The index you are trying to swop with clashes with your current timetable.");
                case -4 -> System.out.println("Your old index clashes with your partner's timetable.");
                case -5 -> System.out.println("You and your partner have the same index.");
                default -> System.out.println("Unknown status, operation unsuccessful.");
            }
        }
    }

    /** 5. Change index */
    public void changeIndex() {
        System.out.println("=== Change index ===");
        ArrayList<String> stringIndexList = new ArrayList<>();
        ArrayList<Index> indexList = student.getIndexList();

        // Print indexes of the student.
        for (Index index : indexList) {
            String string = index.getCourseName() + " - " + index.getIndex();
            stringIndexList.add(string);
        }
        int choice1 = getPrintOptions("Which index would you like to change?", "Back", stringIndexList);
        if (choice1 == 0)
            return;
        Index oldIndex = indexList.get(choice1 - 1);

        // Print available indexes under same course
        ArrayList<String> stringOtherIndexList = new ArrayList<>();
        ArrayList<Index> otherIndexes = oldIndex.getIndexesOfCourse();
        for (Index index : otherIndexes) {
            String string = index.getCourseName() + " - " + index.getIndex();
            stringOtherIndexList.add(string);
        }
        int choice2 = getPrintOptions("Which index would you like to change to?", "Back", stringOtherIndexList);
        if (choice2 == 0)
            return;
        Index newIndex = otherIndexes.get(choice2 - 1);
        SC.changeIndex(student, oldIndex, newIndex);
        System.out.println("Index changed successfully.");
    }

    @Override
    public void renderUserInfo() {
        System.out.println("Welcome " + student.getFullName() + " | Account type: Student.");
        System.out.println("School: " + student.getFacultyName() + "" + " | AU Registered: " + student.getRegAU()
                + " | Number of Registered Courses: " + student.getIndexList().size());
    }

    @Override
    public void renderMainMenu() {
        // Construct menu
        String title = "=== Student Screen ===";
        ArrayList<String> studentOptions = new ArrayList<>();
        studentOptions.add("*Add Course");
        studentOptions.add("Drop Course");
        studentOptions.add("Check/Print Courses Registered");
        studentOptions.add("Check Vacancies Available");
        studentOptions.add("Change Index Number of Course");
        studentOptions.add("Swap Index Number with Another Student");
        studentOptions.add("Print Time Table");
        studentOptions.add("Change Password");

        while (true) {
            int c = View.getPrintOptions(title, "Logout", studentOptions);
            switch (c) {
                case 1 -> addCourse();
                case 2 -> dropCourse();
                case 3 -> printCoursesRegistered();
                case 4 -> printVacanciesOfCourse();
                case 5 -> changeIndex();
                case 6 -> swopIndex();
                case 7 -> printTimeTable();
                case 8 -> changePassword(student);
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Option not available...");
            }
            View.pressEnterKeyToGoBack();
        }
    }

    @Override
    public void renderStartPage() {
        super.renderStartPage();
    }

    @Override
    public void renderLoginPage() {
        super.renderLoginPage();
    }
}
