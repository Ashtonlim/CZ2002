import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
	
	//added salt to improve hashing
	public static final String SALT = "CZ2007";
	Map DB = new HashMap();
	
	
	public String generateHash(String password) {
		StringBuilder hash = new StringBuilder();

		try {
			//hashing with SHA-512
			MessageDigest sha = MessageDigest.getInstance("SHA-512");
			byte[] hashedBytes = sha.digest(password.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			//convert bytes to hexadecimal format
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
	
	
	public void signup(String username, String password) {
		String saltedPassword = SALT + password;
		String hashedPassword = generateHash(saltedPassword);
		//store new user and pw into hashmap
		DB.put(username, hashedPassword);
	}
	
	
	public Boolean login(String username, String password) {
		Boolean isAuthenticated = false;
		// convert user input password into hashed password
		String saltedPassword = SALT + password;
		String hashedPassword = generateHash(saltedPassword);
		
		//retrieve user's stored password
		String storedPasswordHash = (String) DB.get(username);
		if (storedPasswordHash == null) {
			System.out.println("Login failed. The user does not exist.");
		} else {
			if(hashedPassword.equals(storedPasswordHash)){
				System.out.println("Login successful.");
				isAuthenticated = true;
			} else {
				System.out.println("Login failed. Password is incorrect.");
				isAuthenticated = false;
			}
		}
		return isAuthenticated;
	}
	
	
}
