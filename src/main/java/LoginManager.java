import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {

	public static String generateHash(String password) {
		StringBuilder hash = new StringBuilder();

		try {
			// hashing with SHA-512
			MessageDigest sha = MessageDigest.getInstance("SHA-512");
			byte[] hashedBytes = sha.digest(password.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			// convert bytes to hexadecimal format
			for (int i = 0; i < hashedBytes.length; i++) {
				byte b = hashedBytes[i];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash.toString();
	}

	public boolean verifyLogin(User user, String password) throws Exception {
		boolean isAuthenticated = false;
		// convert user input password into hashed password
		String hashedInputPW = generateHash(password);

		// check password
		String storedPW = user.getPassword();
		// System.out.println(storedPW + " " + hashedInputPW);
		if (hashedInputPW.equals(storedPW)) {
			System.out.println("Login successful.");
			isAuthenticated = true;
		} else {
			System.out.println("Login failed. Password is incorrect.");
		}

		return isAuthenticated;
	}

	// public boolean verifyAdminLogin(String username, String password) {
	// boolean isAuthenticated = false;
	// // convert user input password into hashed password
	// String saltedInputPW = SALT + password;
	// String hashedInputPW = generateHash(saltedInputPW);
	//
	// boolean found = false;
	// int index = 0;
	//
	// //find user
	// for (int i=0; i<listOfAdmins.size(); i++){
	// String storedUser = listOfAdmins.get(i).getUsername();
	// if(username.equals(storedUser)){
	// index = i;
	// found = true;
	// break;
	// }
	// System.out.println("Login failed. The user does not exist.");
	// }
	// //check password
	// if (found == true) {
	// String storedPW = listOfAdmins.get(index).getPassword();
	// if (hashedInputPW.equals(storedPW)) {
	// System.out.println("Login successful.");
	// isAuthenticated = true;
	// } else {
	// System.out.println("Login failed. Password is incorrect.");
	// }
	// }
	// return isAuthenticated;
	// }

}
