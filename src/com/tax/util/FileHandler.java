package com.tax.util;

import java.io.*;
import com.tax.model.TaxRecord;

public class FileHandler {
    public static void generateReceipt(TaxRecord record) {
        String fileName = "TaxVision_Receipt_" + record.getNic() + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("******************************************");
            writer.println("             TAX VISION 2026              ");
            writer.println("         OFFICIAL RETURN RECEIPT          ");
            writer.println("******************************************");
            writer.println("Name:    " + record.getName());
            writer.println("NIC:     " + record.getNic());
            writer.println("Bank:    " + record.getBankName());
            writer.println("Acc No:  " + record.getAccountNumber());
            writer.println("------------------------------------------");
            
            
            writer.println(String.format("Annual Income:    Rs %,.2f", record.getAnnualIncome()));
            writer.println(String.format("Exemptions:       Rs %,.2f", (record.getDepAllowance() + record.getMedAllowance())));
            writer.println(String.format("Taxable Amount:   Rs %,.2f", record.getTaxableAmount()));
            writer.println("------------------------------------------");
            writer.println(String.format("TAX PAYABLE (10%%): Rs %,.2f", record.getTaxPayable()));
            
            writer.println("******************************************");
            writer.println("Submitted on: " + new java.util.Date());
            
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
}