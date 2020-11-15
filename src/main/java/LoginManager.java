import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
	
	//added salt to improve hashing
	public static final String SALT = "CZ2007";
//	Map DB = new HashMap();
	
	
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
	
	
//	public void signup(String username, String password) {
//		String saltedPassword = SALT + password;
//		String hashedPassword = generateHash(saltedPassword);
//		//store new user and pw into hashmap
//		DB.put(username, hashedPassword);
//	}

	
//	public boolean verifyAdminLogin(String username, String password) {
//		boolean isAuthenticated = false;
//		// convert user input password into hashed password
//		String saltedInputPW = SALT + password;
//		String hashedInputPW = generateHash(saltedInputPW);
//
//		boolean found = false;
//		int index = 0;
//		
//		//find user
//		for (int i=0; i<listOfAdmins.size(); i++){
//			String storedUser = listOfAdmins.get(i).getUsername();
//			if(username.equals(storedUser)){
//				index = i;
//				found = true;
//				break;
//			}
//			System.out.println("Login failed. The user does not exist.");
//		}
//		//check password
//		if (found == true) {
//			String storedPW = listOfAdmins.get(index).getPassword();
//			if (hashedInputPW.equals(storedPW)) {
//				System.out.println("Login successful.");
//				isAuthenticated = true;
//			} else {
//				System.out.println("Login failed. Password is incorrect.");
//			}
//		}
//		return isAuthenticated;
//	}
	
	
	public boolean verifyLogin(String username, String password) throws Exception {
		boolean isAuthenticated = false;
		boolean found = false;
		// convert user input password into hashed password
		String saltedInputPW = SALT + password;
		String hashedInputPW = generateHash(saltedInputPW);
		
		//find user
		RecordManager rm = new RecordManager();
		User user = rm.getUser(username);
		if (user == null) {
			System.out.println("Login failed. The user does not exist.");
		//} else if (user instanceof Student) {
				//if outside access period
				//  	print cannot access during this period
				//else
				//		found = true
		} else {
			found = true;
		}
		
		//check password
		if (found == true) {
			String storedPW = user.getPassword();
			if (hashedInputPW.equals(storedPW)) {
				System.out.println("Login successful.");
				isAuthenticated = true;
			} else {
				System.out.println("Login failed. Password is incorrect.");
			}
		}
		
		return isAuthenticated;
	}
	
}
