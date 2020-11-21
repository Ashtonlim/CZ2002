package sg.edu.ntu.cz2002.grp3.Entity;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(String username, String password, String fullName, String gender, String nationality){
        super(username, password, fullName, gender, nationality);
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
        //implement later
    }

}
