package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;
import sg.edu.ntu.cz2002.grp3.Controller.RecordManager;
import sg.edu.ntu.cz2002.grp3.Controller.TimeManager;
import sg.edu.ntu.cz2002.grp3.Entity.User;
import sg.edu.ntu.cz2002.grp3.util.IO;

import java.util.ArrayList;

/**
 * The Class for displaying the view before user login
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming.
 */
public class GuestView {
    
    /** The record manager. */
    RecordManager RM;
    
    /** The app. */
    MyStarsApp app;
    
    /**
     * Instantiates a new guest view.
     *
     * @param app the app
     */
    public GuestView(MyStarsApp app){
        this.app = app;
        this.RM = app.getRM();
    }
    
    /**
     * Render dummy data.
     *
     * @param RM the rm
     */
    public void renderDummyData(RecordManager RM){
        // Load dummy data
        ArrayList<String> options = new ArrayList<>();
        options.add("Yes");
        int choice = IO.getPrintOptions("Load dummy data?", "No", options);
        if (choice == 1) {
            try {
                RM.loadDummyData();
            } catch (Exception ignored){};
        }
        // End of load dummy data
    }

    /**
     *  Login page.
     */
    public void renderLoginPage() {
        LoginManager LM = new LoginManager(RM);
        // Non terminal code, to be changed to terminal version lat
        System.out.println("Current System Time: " + TimeManager.currentDateTimeStr);
        System.out.println("=== User Login ===");
        String username = IO.getTextInput("Username: ");
        String password = IO.getPassword("Password: ");
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

    /**
     *  Landing Page.
     */
    public void renderStartPage() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Login");
        int choice = IO.getPrintOptions("* MyStarsApp *", "Exit Program", options);
        app.setRunningStatus(choice != 0);
    }

}
