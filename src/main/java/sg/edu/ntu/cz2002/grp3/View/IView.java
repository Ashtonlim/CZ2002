package sg.edu.ntu.cz2002.grp3.View;

import sg.edu.ntu.cz2002.grp3.Controller.RecordManager;
import sg.edu.ntu.cz2002.grp3.Controller.MyStarsApp;

public interface IView {
    void renderUserInfo();
    void renderMainMenu();
    void changePassword();
    void renderStartPage();
    void renderLoginPage();
}
