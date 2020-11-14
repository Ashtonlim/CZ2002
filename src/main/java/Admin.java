import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(String username, String password, String fullName, String gender){
        super(username, password, fullName, gender);
    }

}
