package frames;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

import fileHandling.Logger;
import user.Admin;
import user.Customer;
import user.User;

public class UserDataOperations {
	public static HashMap<String, User> loginInfo = new HashMap<String, User>();
	
	public UserDataOperations() {
		/**
		 * Parses users.txt file to create admin and customers accordingly
		 */
		try  {
    		File dataFile = new File("users.txt");
    		Scanner scanner = new Scanner(dataFile);
    	    while (scanner.hasNext()) {
    	        String dataLine = scanner.nextLine();
				String[] data = dataLine.split(" ");
				if (data[0].equals("admin")) {
					loginInfo.put(data[0], new Admin(data[0], data[1]));
				}
				else{
					loginInfo.put(data[0], new Customer(data[0], data[1], data[2], data[3], data[4]));
				}
				
    	    }
    	    scanner.close();
    	} catch (FileNotFoundException e) {
    		Logger.log("users.txt file not found at the expected location at " + LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n");
    	}
	}
	
	protected HashMap<String, User> getLoginInfo() {
		return loginInfo;
	}
}
