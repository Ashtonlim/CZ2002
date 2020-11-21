package Entity;

import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    protected String fullName;
    protected String gender;
    protected String nationality;
    
    public User(String username, String password, String fullName, String gender, String nationality){

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

    /** Liskov's Substitution Principle */
    public abstract String getUserName();
    public abstract String getFullName();
    public abstract String getPassword();
    public abstract void setPassword(String password);
}
