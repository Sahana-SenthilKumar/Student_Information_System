package com.hexaware.sis.util;

import java.sql.*;
//import java.util.*;
//import java.io.*;

public class DBConnUtil {
	
	private static Connection connection = null;
	
	// Open Connection
	public static Connection getConnection() {
		
		if(connection ==  null) {
			
			try {

				String connString = DBPropertyUtil.getConnectionString("resources/db.properties");
				
				connection = DriverManager.getConnection(connString);
				System.out.println("Database Connected "+connection+"\n");
				
				
			}
			catch(SQLException e ){
				System.out.println("Connection Failed.\n");
				e.printStackTrace();
				
			}
		}
		
		return connection;
		
	}
	
	// Close Connection
	public static void closeConnection() {
		
		try {
			
			if(connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Database connection closed.\n");	
				connection = null;
			}
			else {
				System.out.println("No database connection found.\n");	
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	// Close Statement
	
	public static void closeStatement(Statement stmt) {
		
		try {
			stmt.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// Close ResultSet
	
	public static void closeResultSet(ResultSet rs) {
		
	    try {
	        if (rs != null && !rs.isClosed()) {
	            rs.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while closing ResultSet.");
	        e.printStackTrace();
	    }

		
	}
	
	
	// Close PreparedStatement
	
	public static void closePreparedStatement(PreparedStatement pstmt) {
		
	    try {
	        if (pstmt != null && !pstmt.isClosed()) {
	            pstmt.close();
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while closing PreparedStatement.");
	        e.printStackTrace();
	    }

		
	}
	
	
	

}
