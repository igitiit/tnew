package rundb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SHAHashingExample extends HashDao {

	static void runHash() throws NoSuchAlgorithmException {

		String password = "123456";

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		System.out.println("Hex format : " + hexString.toString());

	}

	public static void main(String[] args) throws Exception {
		//runHash();
		addUsers();
	}

	boolean checkHash(String hash) {

		return true;
	}

	static String createHashes(String passwd) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(passwd.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sbHash = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sbHash.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		System.out.println("Hash password created => " + sbHash.toString());

		return sbHash.toString();
	}

	public static void addUsers() {

		// add list of users from userlist.csv file to users table
		// variables for SQL Query inserts
		String sql;
		Connection connect = null;
		Statement statement = null;
		BufferedReader br;
		List<List<String>> array = new ArrayList<>(); // array list to hold
														// spreadsheet rows &
														// columns

		// read data from file
		try {
			br = new BufferedReader(new FileReader(new File("./userlist.csv")));

			String line;
			while ((line = br.readLine()) != null) {
				array.add(Arrays.asList(line.split(",")));
			}
		} catch (Exception e) {
			System.out.println("There was a problem loading the file");
		}

		try {

			// Setup the connection with the DB

			statement = getConnection().createStatement();

			// create loop to grab each array index containing a list of values
			// and PASS (insert) that data into your User table
			for (List<String> rowData : array) {

				// save hash to db
				sql = "insert into jpapa_users2(uname,passwd) values('" + rowData.get(0) + "', ";
				sql += " '" + createHashes(rowData.get(1)) + "');";
				statement.executeUpdate(sql);
			
			System.out.println("Inserts completed in the given database...");
			} 
			// close connection/statement object
			statement.close();
			
		
		}catch (Exception e) {
			System.out.println(e.getMessage() + " val ");
			e.printStackTrace();
		}
	}
}
