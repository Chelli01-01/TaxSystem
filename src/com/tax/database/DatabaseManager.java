package com.tax.database;

import java.util.Properties;
import java.sql.*;
import java.io.InputStream;
import java.io.IOException;
import com.tax.model.TaxRecord;

public class DatabaseManager {
    private Properties props = new Properties();
    
    public DatabaseManager() {     //We used a Properties file to enforce security regarding about credentials
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("dbConfig2.properties")) {
            if (input == null) return;
            props.load(input);
        } catch (IOException e) { System.out.println(e.getMessage()); }
    }
    
    public Connection connection() {
        try {    
            return DriverManager.getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (SQLException e) { return null; }
    }
   // Validates credentials and returns the user's role(admin/employee) for access control.
    public String validateLogin(String user, String pass) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection conn = this.connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user);
            pstmt.setString(2, pass);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("role");
        } catch (SQLException e) { }
        return null;
    }

    public void insertTaxReturn(TaxRecord record) {
        String query = "INSERT INTO tax_returns (nicNumber, empName, empAddress, contactNum, employmentType, residentStatus, annualIncome, depAllowance, medAllowance, taxableAmount, taxPayable, bankName, accountNum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
     // Mapping TaxRecord object properties to database columns
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, record.getNic());
            ps.setString(2, record.getName());
            ps.setString(3, record.getAddress());
            ps.setString(4, record.getContactNum());
            ps.setString(5, record.getEmpType());
            ps.setString(6, record.getResidentStatus());
            ps.setDouble(7, record.getAnnualIncome());
            ps.setDouble(8, record.getDepAllowance());
            ps.setDouble(9, record.getMedAllowance());
            ps.setDouble(10, record.getTaxableAmount());
            ps.setDouble(11, record.getTaxPayable());
            ps.setString(12, record.getBankName());    
            ps.setString(13, record.getAccountNumber());
            ps.executeUpdate();
        } catch (SQLException e) { System.out.println("Insert Error: " + e.getMessage()); }
    }
    
    //Fetches a specific taxpayer record by NIC. (Used by the Admin)
    public TaxRecord searchByNIC(String nic) {
        TaxRecord record = null;
       
        String sql = "SELECT * FROM tax_returns WHERE nicNumber = ?";
        try (Connection conn = connection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nic);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                record = new TaxRecord();
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
                record.setBankName(rs.getString("bankName"));    
                record.setAccountNumber(rs.getString("accountNum")); 
            }
        } catch (SQLException e) { System.out.println("Search Error: " + e.getMessage()); }
        return record;
    }

    //Implements "Session Mapping" logic; we used matching (LIKE %) to link a login username to a taxpayer record for auto-filling
    public TaxRecord searchByUsernameMapping(String username) {
        String sql = "SELECT * FROM tax_returns WHERE empName LIKE ? OR nicNumber = ?";
        try (Connection conn = connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchTag = (username.length() > 3) ? username.substring(0, 3) : username;
            pstmt.setString(1, "%" + searchTag + "%"); 
            pstmt.setString(2, username); 
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                TaxRecord r = new TaxRecord();
                r.setName(rs.getString("empName"));
                r.setNic(rs.getString("nicNumber"));
                r.setAddress(rs.getString("empAddress"));
                r.setContactNum(rs.getString("contactNum"));
                r.setBankName(rs.getString("bankName"));
                r.setAccountNumber(rs.getString("accountNum"));
                r.setEmpType(rs.getString("employmentType"));
                r.setResidentStatus(rs.getString("residentStatus"));
                r.setAnnualIncome(rs.getDouble("annualIncome"));
                r.setMedAllowance(rs.getDouble("medAllowance"));
                r.setDepAllowance(rs.getDouble("depAllowance"));
                return r;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}