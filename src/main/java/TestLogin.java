import java.io.Console;

public class TestLogin {

	public static void main(String[] args) {
		LoginManager login = new LoginManager();
		Console cons = System.console();
		
		System.out.println("Signup");
		String username = cons.readLine("Username: ");
		char[] pwChar = cons.readPassword("Password: ");
		String password = String.valueOf(pwChar);
		//System.out.println(username +" "+ password);
		System.out.println("Signing up........");
		login.signup(username, password);
		System.out.println("");
		
		boolean success = false;
		while(!success) {
			System.out.println("Login");
			String loginUsername = cons.readLine("Username: ");
			char[] loginPwChar = cons.readPassword("Password: ");
			String loginPassword = String.valueOf(loginPwChar);
			//System.out.println(loginUsername +" "+ loginPassword);
			System.out.println("Logging in........");
			success = login.login(loginUsername, loginPassword);
			System.out.println("");
		}		
	}
}
