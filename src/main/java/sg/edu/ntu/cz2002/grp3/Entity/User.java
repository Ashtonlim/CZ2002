package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;

/**
 * Represents a user in the system. User can be a student or admin.
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public abstract class User implements Serializable {
    
    private static final long serialVersionUID = 6694048940285488599L;
    
    /** The username. */
    protected String username;
    
    /** The password. */
    protected String password;
    
    /** The full name. */
    protected String fullName;
    
    /** The gender. */
    protected String gender;
    
    /** The nationality. */
    protected String nationality;

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     * @param fullName the full name
     * @param gender the gender
     * @param nationality the nationality
     */
    public User(String username, String password, String fullName, String gender, String nationality) {

        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.nationality = nationality;

        if (gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("f")) {
            this.gender = gender.toUpperCase();
        } else {
            System.out.println("Gender should be M or F, setting to M as default.");
            this.gender = "M";
        }
    }

    public abstract String getUserName();

    public abstract String getFullName();
    
    public abstract String getPassword();

    public abstract void setPassword(String password);
}
