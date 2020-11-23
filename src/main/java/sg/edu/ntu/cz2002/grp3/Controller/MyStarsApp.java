package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.notification.SMSNotification;
import sg.edu.ntu.cz2002.grp3.View.*;
import sg.edu.ntu.cz2002.grp3.Entity.Admin;
import sg.edu.ntu.cz2002.grp3.Entity.Student;
import sg.edu.ntu.cz2002.grp3.Entity.User;

// import java.util.*;

public class MyStarsApp {
    private final RecordManager RM;
    private User activeUser;
    private boolean running;

    public MyStarsApp() {
        RM = new RecordManager();
    }

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
            System.out.println("Your data has been saved.");
            RM.save();
            activeUser = null;
        }
    }

    public void setRunningStatus(boolean running) {
        this.running = running;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }

    public RecordManager getRM(){
        return RM;
    }
}
