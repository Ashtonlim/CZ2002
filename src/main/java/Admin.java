import java.io.Serializable;

public class Admin extends User implements Serializable {

    public Admin(String username, String password, String fullName, String gender, String nationality){
        super(username, password, fullName, gender, nationality);
    }

}
