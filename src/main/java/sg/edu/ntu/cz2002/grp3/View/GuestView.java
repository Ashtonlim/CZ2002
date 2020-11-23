package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;
import sg.edu.ntu.cz2002.grp3.Controller.RecordManager;
import sg.edu.ntu.cz2002.grp3.Controller.TimeManager;
import sg.edu.ntu.cz2002.grp3.Entity.User;
import sg.edu.ntu.cz2002.grp3.util.Input;

import java.util.ArrayList;

public class GuestView implements IView {

    public void renderDummyData(RecordManager RM){
        // Load dummy data
        ArrayList<String> options = new ArrayList<>();
        options.add("Yes");
        int choice = Input.getPrintOptions("Load dummy data?", "No", options);
        if (choice == 1) {
            try {
                RM.loadDummyData();
            } catch (Exception ignored){};
        }
        // End of load dummy data
    }


    /** Login page */
    public void renderLoginPage(MyStarsApp app, RecordManager RM) {
        LoginManager LM = new LoginManager(RM);
        // Non terminal code, to be changed to terminal version lat
        System.out.println("Current System Time: " + TimeManager.currentDateTimeStr);
        System.out.println("=== User Login ===");
        String username = Input.getTextInput("Username: ");
        String password = Input.getTextInput("Password: ");
        System.out.println("Logging in........");
        User user = LM.login(username, password);
        boolean isAllowed = LM.isWithinPeriod(user);
        if (user != null && isAllowed) {
            System.out.println("Login successfully.");
        } else if (user != null && !isAllowed){
            System.out.println("Login not allowed outside of access period.");
            user = null;
        } else {
            System.out.println("Invalid username or password.");
        }
        app.setActiveUser(user);
    }

    /** Landing Page */
    public void renderStartPage(MyStarsApp app) {
        ArrayList<String> options = new ArrayList<>();
        options.add("Login");
        int choice = Input.getPrintOptions("* MyStarsApp *", "Exit Program", options);
        app.setRunningStatus(choice != 0);
    }

    public void renderUserInfo() {
        System.out.println("Welcome guest! | Account type: Guest.");
        System.out.println("Please login!");
    }

    public void renderMainMenu() {
        System.out.println("Nothing to show here, please Login!");
    }

    @Override
    public void changePassword() {
        System.out.println("Nothing to show here, please Login!");
    }
}
