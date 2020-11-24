package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.View.*;
import sg.edu.ntu.cz2002.grp3.Entity.Admin;
import sg.edu.ntu.cz2002.grp3.Entity.Student;
import sg.edu.ntu.cz2002.grp3.Entity.User;

/**
 * The Class MyStarsApp starts the application and intitialises the necessary
 * objects.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
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
            GuestView gv = new GuestView(this); // Started with guest view
//            gv.renderDummyData(RM); // To be removed in production.

            gv.renderStartPage();
            if (!running)
                break;

            gv.renderLoginPage();
            if (activeUser == null)
                continue;

            UserView uv = (activeUser instanceof Admin) ? new AdminView(new AdminController(RM, (Admin) activeUser))
                    : new StudentView(new StudentController(RM, (Student) activeUser));

            uv.renderUserInfo();
            uv.renderMainMenu();

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
    public RecordManager getRM() {
        return RM;
    }
}
