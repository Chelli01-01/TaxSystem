package com.tax.database;


//JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.tax.model.TaxRecord;


//1. establish connection
public class DatabaseManager {
	
	//Creating a Properties Object to hold the data
	private Properties props=new Properties();
	
	
	public DatabaseManager() {
		try {
			//looking for dbConfig2.properties
			FileInputStream file= new FileInputStream("dbConfig2.properties");
			props.load(file);
			file.close();
		} catch (IOException e) {
			System.out.println("Error: Could not find or load dbConfig2.properties");
		}
	}
	
	/* Here, using properties, I have updated the connection made while also
	enforcing security*/	
		
	
	
	public Connection connection() {
		Connection myConnection = null;
			try {	
				//Opens a live connection to mySQL database
				//Pull values from properties instead of hardcoded strings

				String url= props.getProperty("db.url");
				String user=props.getProperty("db.user");
				String password=props.getProperty("db.password");
				
				myConnection=DriverManager.getConnection(url,user,password);
				System.out.println("Connection established!");
				
						

			//error if databse is not found        
			    } catch (SQLException e) {
			        System.out.println("Database Connection failed: "+e.getMessage());
			    }
		return myConnection;
		}
		
		//2. Take values and insert it into SQL 
		public void insertTaxReturn(TaxRecord record) {
		
			try { 
				//connects to the database
				Connection connection = connection();
				if (connection!=null) {
				//statement
				Statement tax_statement = connection.createStatement();
				
				//the SQL query to insert into the database table.
				String query = "insert into tax_returns (nicNumber, empName, empAddress, contactNum, employmentType, annualIncome, deptAllowance, medAllowance, taxableAmount, taxPayable) VALUES ('"
						+ record.getNic() + "', '"
						+ record.getName() + "', '"
						+ record.getAddress() + "', '"
					    + record.getContactNum() + "', '" 
						+ record.getEmpType() + "', '"
						+ record.getAnnualIncome() + "', '"
						+ record.getDepAllowance() + "', '"
						+ record.getMedAllowance() + "', '"
						+ record.getTaxableAmount() + "', '"
						+ record.getTaxPayable() + ")";
						
				tax_statement.executeUpdate(query);
				System.out.println("successfully inserted data!");
				
				//close the methods
				tax_statement.close();
				connection.close();
				}
				

			}catch (SQLException e) {
				System.out.println("Data insertion failed: "+e.getMessage());
				
			}
		
		
		}
	}