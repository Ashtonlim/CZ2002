import java.io.Console;
import java.util.Scanner;

public class TestLogin {

	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		LoginManager login = new LoginManager();
//		Console cons = System.console();
//		
//		System.out.println("Signup");
//		String username = cons.readLine("Username: ");
//		char[] pwChar = cons.readPassword("Password: ");
//		String password = String.valueOf(pwChar);
//		//System.out.println(username +" "+ password);
//		System.out.println("Signing up........");
//		login.signup(username, password);
//		System.out.println("");
//		
		boolean success = false;
		while(!success) {
			System.out.println("Login\n");
			//String loginUsername = cons.readLine("Username: ");
			//char[] loginPwChar = cons.readPassword("Password: ");
			//String loginPassword = String.valueOf(loginPwChar);
			System.out.println("Username: ");
			String loginUsername = sc.nextLine();
			System.out.println("Password: ");
			String loginPassword = sc.nextLine();
			System.out.println(loginUsername +" "+ loginPassword);
			System.out.println("Logging in........");
			success = login.verifyLogin(loginUsername, loginPassword);
			System.out.println("");
		}		
	}
}
