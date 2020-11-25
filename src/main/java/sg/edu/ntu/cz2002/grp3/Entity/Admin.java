package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;

/**
 * Represents an admin in the system.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class Admin extends User implements Serializable {

    /**
     * Used for versioning when serializing. Not necessary but added to remove
     * warning
     */
    private static final long serialVersionUID = -9055528593310895372L;

    /**
     * the unique ID of the admin
     */
    private int staffID;

    /**
     * Instantiates a new admin.
     *
     * @param username    the username
     * @param password    the password
     * @param fullName    the full name
     * @param gender      the gender
     * @param nationality the nationality
     * @param staffID     the staffID
     */
    public Admin(int staffID, String username, String password, String fullName, String gender, String nationality) {
        super(username, password, fullName, gender, nationality);
        this.setStaffID(staffID);
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

}
