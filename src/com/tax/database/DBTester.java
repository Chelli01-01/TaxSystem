package com.tax.database;

import java.sql.Connection;

public class DBTester {
    public static void main(String[] args) {
        System.out.println("=== Starting Database Connection Test ===");
        
        // 1. Initialize your DatabaseManager
        // This will trigger the constructor and load the properties file
        DatabaseManager dbManager = new DatabaseManager();
        
        // 2. Try to establish a connection
        Connection conn = dbManager.connection();
        
        // 3. Validation
        if (conn != null) {
            System.out.println("-------------------------------------------");
            System.out.println("SUCCESS: Your database connection is live!");
            System.out.println("Database Name: taxsystem");
            System.out.println("-------------------------------------------");
            
            try {
                conn.close(); // Always close after testing
                System.out.println("Connection closed safely.");
            } catch (Exception e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("-------------------------------------------");
            System.out.println("FAILED: Connection is null.");
            System.out.println("Checklist:");
            System.out.println("1. Is MySQL running in XAMPP/Workbench?");
            System.out.println("2. Is dbConfig2.properties inside the 'src' folder?");
            System.out.println("3. Does the URL in properties match 'jdbc:mysql://localhost:3306/taxsystem'?");
            System.out.println("-------------------------------------------");
        }
    }
}