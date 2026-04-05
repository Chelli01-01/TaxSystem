//JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.tax.model.TaxRecord;


//1. establish connection
public class DatabaseManager {
	//connection details - mySQL address - username - password
	String url = "jdbc:mysql://localhost:3306/tax_system";
	String username = "root";
	String password = "123456";
	
	public Connection connection() {
		Connection connection = null;
			try {	
				//Opens a live connection to mySQL database
				connection = DriverManager.getConnection(url, username, password);
				System.out.println("Connection established!");
						

			//error if databse is not found        
			    } catch (SQLException e) {
			        System.out.println("Database error!");
			    }
		return connection;
		}
		
		//2. Take values and insert it into SQL 
		public void insertTaxReturn(TaxRecord record) {
		
			try { 
				//connects to the database
				Connection connection = connection();
				
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
				System.out.println("successfully inserted data(s)!");
				
				//close the methods
				tax_statement.close();
				connection.close();
				

			}catch (SQLException e) {
				System.out.println("Data insertion failed");
				
			}
		}
		
		public static void main(String[] args) {
			DatabaseManager db = new DatabaseManager();
			db.connection();
		}
	}