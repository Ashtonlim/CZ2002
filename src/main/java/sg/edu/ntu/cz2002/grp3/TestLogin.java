package sg.edu.ntu.cz2002.grp3;

import sg.edu.ntu.cz2002.grp3.Controller.LoginManager;
import sg.edu.ntu.cz2002.grp3.Controller.RecordManager;
import sg.edu.ntu.cz2002.grp3.Entity.User;

import java.util.Scanner;

public class TestLogin {

	public static void main(String[] args) throws Exception {

		Scanner sc = new Scanner(System.in);
		RecordManager RM = new RecordManager();
		// LoginManager login = new LoginManager(RM);

		boolean success = false;
		while (!success) {
			System.out.println("Login\n");

			// for console debugging
			System.out.println("Username: ");
			String loginUsername = sc.nextLine();
			System.out.println("Password: ");
			String loginPassword = sc.nextLine();

			User user = RM.getUser(loginUsername);
			if (user == null) {
				System.out.println("Entity.User does not exist");
			} else {
				System.out.println("Logging in........");
				success = LoginManager.verifyLogin(user, loginPassword);
				System.out.println("");
			}

		}
	}
}
