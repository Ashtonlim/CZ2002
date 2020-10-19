public class Admin extends User {

    public Admin(String username, String password, String fullName, String gender){
        super(username, password, fullName, gender);
    }

    @Override
    public String formatDBRow() {
        return username + "," + password + "," + fullName + "," + gender + ",null,null,null,0,0,1";
    }
}
