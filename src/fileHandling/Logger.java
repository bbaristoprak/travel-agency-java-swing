package fileHandling;

import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	public static void log(String string) {
		/**
		 * logs the given message to the logs.txt, used for logging users actions such as making/canceling reservations, logging in and out etc.
		 */
		try {
			FileWriter fileWriter = new FileWriter("logs.txt", true);
			fileWriter.write(string);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
