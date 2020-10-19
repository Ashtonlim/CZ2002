public class User {
    private String username;
    public User(String username, String password, String fullName, String gender){
        this.username = username;
    }

    public String getUserName(){
        return username;
    }
}
