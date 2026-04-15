package com.tax.database;


//JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
			
			String query = "INSERT INTO tax_returns (nicNumber, empName, empAddress, contactNum, employmentType, residentStatus, annualIncome, depAllowance, medAllowance, taxableAmount, taxPayable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
			try 
				//connects to the database
				(Connection connection = connection();
				PreparedStatement ps = connection.prepareStatement(query)){
			        
			        if (connection != null) {
			            ps.setString(1, record.getNic());
			            ps.setString(2, record.getName());
			            ps.setString(3, record.getAddress());
			            ps.setString(4, record.getContactNum());
			            ps.setString(5, record.getEmpType());
			            ps.setString(6, record.getResidentStatus()); // Added this
			            ps.setDouble(7, record.getAnnualIncome());
			            ps.setDouble(8, record.getDepAllowance()); //
			            ps.setDouble(9, record.getMedAllowance());
			            ps.setDouble(10, record.getTaxableAmount());
			            ps.setDouble(11, record.getTaxPayable());

			            ps.executeUpdate();
			            System.out.println("Successfully inserted data for: " + record.getName());
			        }
			    } catch (SQLException e) {
			        System.out.println("Data insertion failed: " + e.getMessage());
			    }
		
		
		}
		
		// Searching by NIC
		public TaxRecord searchByNIC(String nic) {
			TaxRecord record=null; // if no match found, we want to return 'nothing'
			
			Connection myConnection=connection();
			
			
			if (myConnection!=null){
				try {
				String sql= "SELECT * FROM tax_returns WHERE nicNumber=?";
				PreparedStatement ps= myConnection.prepareStatement(sql);
				ps.setString(1, nic);
				
				ResultSet rs = ps.executeQuery();
	            
	            if (rs.next()) {
	                record = new TaxRecord();
	                // Mapping the DB columns to the object 
	                record.setNic(rs.getString("nicNumber"));
	                record.setName(rs.getString("empName"));
	                record.setAddress(rs.getString("empAddress"));
	                record.setContactNum(rs.getString("contactNum"));
	                record.setEmpType(rs.getString("employmentType"));
	                record.setResidentStatus(rs.getString("residentStatus"));
	                record.setAnnualIncome(rs.getDouble("annualIncome"));
	                record.setDepAllowance(rs.getDouble("depAllowance"));
	                record.setMedAllowance(rs.getDouble("medAllowance"));
	                record.setTaxableAmount(rs.getDouble("taxableAmount"));
	                record.setTaxPayable(rs.getDouble("taxPayable"));
	            }
	            
	            rs.close();
	            ps.close();
	            myConnection.close();
			} catch (SQLException e) {
	            System.out.println("Search Error: " + e.getMessage());
	        }
		}
		return record;
	}
	}
