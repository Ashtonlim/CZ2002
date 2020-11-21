package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginManager {
	private final RecordManager RM;
	
	/** default password */
	public static String defPassword = generateHash("p@ssw0rd!");
	
	public LoginManager(RecordManager RM){
		this.RM = RM;
	}

	/**
	 * null -> user/password does not match
	 * When asked why doesn't show user does not exist? Prevent brute force (lazy to implement).
	 * */
	public User login(String username, String password){
		User user = RM.getUser(username);
		if (user == null) return null;
		boolean authenticated = verifyLogin(user, password);
		return (authenticated) ? user : null;
	}

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

	public static boolean verifyLogin(User user, String password) {
		boolean isAuthenticated = false;
		// convert user input password into hashed password
		String hashedInputPW = generateHash(password);

		// check password
		String storedPW = user.getPassword();
		// System.out.println(storedPW + " " + hashedInputPW);
		if (hashedInputPW.equals(storedPW)) {
			isAuthenticated = true;
		}

		return isAuthenticated;
	}

	
    /** change password for when the account is created by admin */
    public static boolean changePassword(User user, String oldPassword, String newPassword) {
    	boolean isAuthenticated = verifyLogin(user, oldPassword);
    	if (isAuthenticated == true) {
    		String newHashPassword = generateHash(newPassword);
    		user.setPassword(newHashPassword);
    		return true;
    	} else {
    		return false;
    	}
    }
}
