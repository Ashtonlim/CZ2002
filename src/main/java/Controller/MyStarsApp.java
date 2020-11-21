package sg.edu.ntu.cz2002.grp3.Controller;
import sg.edu.ntu.cz2002.grp3.View.*;
import sg.edu.ntu.cz2002.grp3.Entity.Admin;
import sg.edu.ntu.cz2002.grp3.Entity.Student;
import sg.edu.ntu.cz2002.grp3.Entity.User;
import sg.edu.ntu.cz2002.grp3.View.View;

import java.util.*;

public class MyStarsApp {
    private final RecordManager RM;
    private View activeView;
    private User activeUser;
    private boolean running;
    public MyStarsApp() throws Exception {
        RM = new RecordManager();
        activeView = new View(this);
    }

    public void start() throws Exception {
        running = true;
        //Load dummy data
        ArrayList<String> options = new ArrayList<>();
        options.add("Yes");
        int choice = View.getPrintOptions("Load dummy data?", "No", options);
        if (choice == 1){ RM.loadDummyData(); }
        //End of load dummy data

        //sg.edu.ntu.cz2002.grp3.Main program
        while(running){
            activeView.renderStartPage();
            if (!running) break;

            activeView.renderLoginPage();
            if (activeUser == null) continue;

            activeView = (activeUser instanceof Admin) ? new AdminView(this, (Admin) activeUser) : new StudentView(this, (Student) activeUser);

            activeView.renderUserInfo();
            activeView.renderMainMenu();

            //Save database after logout.
            System.out.println("Your data has been saved.");
            RM.save();
        }
    }

    public RecordManager getRM(){
        return RM;
    }

    public void setRunningStatus(boolean running){
        this.running = running;
    }

    public void setActiveUser(User activeUser){
        this.activeUser = activeUser;
    }

}
