package sg.edu.ntu.cz2002.grp3.Controller;

import sg.edu.ntu.cz2002.grp3.Entity.Faculty;
import sg.edu.ntu.cz2002.grp3.Entity.Student;
import sg.edu.ntu.cz2002.grp3.Entity.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class for handling login related functions.
 * 
 * @author Guat Kwan, Wei Xing, Ashton, Yi Bai, Zhe Ming
 */
public class LoginManager {

	/** The Record Manager for accessing database. */
	private final RecordManager RM;

	/** default password for when new account is created by admin. */
	public static String defPassword = generateHash("p@ssw0rd!");

	public LoginManager(RecordManager RM) {
		this.RM = RM;
	}

	/**
	 * Retrieves user from database and verifies username and password. Returns null
	 * if user does not exist or password does not match.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the user object
	 */
	public User login(String username, String password) {
		User user = RM.getUser(username);
		if (user == null)
			return null;
		boolean authenticated = verifyLogin(user, password);
		return (authenticated) ? user : null;
	}

	/**
	 * Verify input password against user's password
	 *
	 * @param user     the user
	 * @param password the password
	 * @return true, if successful
	 */
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

	/**
	 * Check if login is within access period if user is student.
	 *
	 * @param user the user
	 * @return true, if it is within access period
	 */
	public boolean isWithinPeriod(User user) {

		if (user instanceof Student) {
			Student student = (Student) user;
			Faculty faculty = RM.getFaculty(student.getFacultyName());
			if (faculty.getStartDate() == null) {
				return false;
			}
			return !TimeManager.currentDateTime.isBefore(faculty.getStartDate())
					&& !TimeManager.currentDateTime.isAfter(faculty.getEndDate());
		} else {
			return true;
		}

	}

	/**
	 * Function for hashing passwords.
	 *
	 * @param password the password
	 * @return the hashed password
	 */
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

	/**
	 * Function for changing password of account.
	 *
	 * @param user        the user
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return true, if successful
	 */
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
