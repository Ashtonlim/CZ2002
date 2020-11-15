import java.io.Serializable;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    protected String fullName;
    protected String gender;
    public User(String username, String password, String fullName, String gender){

        this.username = username;
        if (gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("f")) {
            this.gender = gender.toUpperCase();
        } else {
            System.out.println("Gender should be M or F, setting to M as default.");
            this.gender = "M";
        }
    }

    public String getUserName(){
        return username;
    }
    
    public String getPassword() {
    	return password;
    }
}
