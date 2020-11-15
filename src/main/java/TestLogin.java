import java.io.Console;
import java.util.Scanner;

public class TestLogin {
	
	public static void main(String[] args) throws Exception {
		
//		RecordManager rm = new RecordManager();
//		rm.loadDummyData();
		
		Scanner sc = new Scanner(System.in);
		LoginManager login = new LoginManager();
		
//		for terminal
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
		
		boolean success = false;
		while(!success) {
			System.out.println("Login\n");
			
//			for terminal
//			String loginUsername = cons.readLine("Username: ");
//			char[] loginPwChar = cons.readPassword("Password: ");
//			String loginPassword = String.valueOf(loginPwChar);
			
//			for console debugging
			System.out.println("Username: ");
			String loginUsername = sc.nextLine();
			System.out.println("Password: ");
			String loginPassword = sc.nextLine();
			
//			System.out.println(loginUsername +" "+ loginPassword);
			System.out.println("Logging in........");
			success = login.verifyLogin(loginUsername, loginPassword);
			System.out.println("");
		}		
	}
}
