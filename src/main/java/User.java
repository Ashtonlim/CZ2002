public abstract class User {
    protected String username;
    protected String password;
    protected String fullName;
    protected String gender;
    public User(String username, String password, String fullName, String gender){
        this.username = username;
    }

    public String getUserName(){
        return username;
    }

    public abstract String formatDBRow();
}
