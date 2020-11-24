package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.notification.SMSNotification;
import sg.edu.ntu.cz2002.grp3.View.*;
import sg.edu.ntu.cz2002.grp3.Entity.Admin;
import sg.edu.ntu.cz2002.grp3.Entity.Student;
import sg.edu.ntu.cz2002.grp3.Entity.User;

// TODO: Auto-generated Javadoc
// import java.util.*;

/**
 * The Class MyStarsApp.
 */
public class MyStarsApp {
    
    /** The rm. */
    private final RecordManager RM;
    
    /** The active user. */
    private User activeUser;
    
    /** The running. */
    private boolean running;

    /**
     * Instantiates a new my stars app.
     */
    public MyStarsApp() {
        RM = new RecordManager();
    }

    /**
     * Start.
     *
     * @throws Exception the exception
     */
    public void start() throws Exception {
        // Uncomment to send a test email when the application starts up
        // Email.sendMail("mystaroodp@gmail.com", "Waitlist Notification", "Congrats,
        // you got into index ");

        running = true;

        // Main program
        while (running) {
            IView activeView = new GuestView(this); // Started with guest view
            ((GuestView) activeView).renderDummyData(RM); // To be removed in production.

            activeView.renderStartPage();
            if (!running) break;

            activeView.renderLoginPage();
            if (activeUser == null) continue;

            activeView = (activeUser instanceof Admin) ? new AdminView( new AdminController(RM, (Admin) activeUser) )
                    : new StudentView( new StudentController(RM, (Student) activeUser) );

            activeView.renderUserInfo();
            activeView.renderMainMenu();

            // Save database after logout.
            System.out.println("System: Your data has been saved.");
            RM.save();
            activeUser = null;
        }
    }

    /**
     * Sets the running status.
     *
     * @param running the new running status
     */
    public void setRunningStatus(boolean running) {
        this.running = running;
    }

    /**
     * Sets the active user.
     *
     * @param activeUser the new active user
     */
    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    /**
     * Gets the rm.
     *
     * @return the rm
     */
    public RecordManager getRM(){
        return RM;
    }
}
