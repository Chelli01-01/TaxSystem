package com.tax.util;

import java.io.*;
import com.tax.model.TaxRecord;

public class FileHandler {
    public static void generateReceipt(TaxRecord record) {
        String fileName = "TaxReceipt_" + record.getNic() + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("==========================================");
            writer.println("       MAURITIUS REVENUE AUTHORITY        ");
            writer.println("          TAX RETURN RECEIPT 2026         ");
            writer.println("==========================================");
            writer.println("Employee Name:    " + record.getName());
            writer.println("NIC Number:       " + record.getNic());
            writer.println("Address:          " + record.getAddress());
            writer.println("Employment Type:  " + record.getEmpType());
            writer.println("------------------------------------------");
            writer.println("Annual Income:    MUR " + record.getAnnualIncome());
            writer.println("Total Exemptions: MUR " + (record.getDepAllowance() + record.getMedAllowance()));
            writer.println("Taxable Amount:   MUR " + record.getTaxableAmount());
            writer.println("------------------------------------------");
            writer.println("TAX PAYABLE (10%): MUR " + record.getTaxPayable());
            writer.println("==========================================");
            writer.println("Generated on: " + new java.util.Date());
            
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
}