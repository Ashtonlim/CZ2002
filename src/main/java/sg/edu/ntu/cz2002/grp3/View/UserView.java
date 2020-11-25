package sg.edu.ntu.cz2002.grp3.View;

/**
 * The interface of views for logged in users.
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public interface UserView {
	
    /**
     * Render user info.
     */
    void renderUserInfo();
    
    /**
     * Render main menu.
     */
    void renderMainMenu();
    
    /**
     * Change password.
     */
    void changePassword();

}
