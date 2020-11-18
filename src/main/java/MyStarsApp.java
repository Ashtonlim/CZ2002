import java.util.*;

public class MyStarsApp {
    private final RecordManager RM;
    private View activeView;
    private User activeUser;
    private boolean running;

    public MyStarsApp() throws Exception {
        RM = new RecordManager();
        activeView = new View(this);
        running = true;
    }

    public void start() throws Exception {
        //Load dummy data
        ArrayList<String> options = new ArrayList<>();
        options.add("Yes");
        int choice = View.getPrintOptions("Load dummy data?", "No", options);
        if (choice == 1){ RM.loadDummyData(); }
        //End of load dummy data

        //Main program
        while(running){
            activeView.renderStartPage();
            if (!running) break;

            activeView.renderLoginPage();
            if (activeUser == null) continue;

            activeView = (activeUser instanceof Admin) ? new AdminView(this, (Admin) activeUser) : new StudentView(this, (Student) activeUser);

            activeView.renderUserInfo();
            activeView.renderMainMenu();
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
