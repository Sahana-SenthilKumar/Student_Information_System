package com.hexaware.sis.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
	
	public static String getConnectionString(String filename) {
		
		
		Properties property = new Properties();
		
		try {
			property.load(new FileInputStream(filename));
			
			//FileInputStream fis = new FileInputStream("resources/db.properties")
			//property.load(fis);
			
			String url = property.getProperty("url");
			String user = property.getProperty("user");
			String password = property.getProperty("password");
			
			return url + "?user=" + user + "&password=" + password;
		}
		catch(IOException e) {
			System.out.println("Could not find such file.");
			e.printStackTrace();
		}
		return null;
	}

}
