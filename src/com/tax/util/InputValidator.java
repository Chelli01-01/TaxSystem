package com.tax.util;
import javax.swing.JOptionPane;

public class InputValidator {
    
    // Checks if field is empty
    public static boolean isNotEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "TaxVision2026: " + fieldName + " cannot be blank.", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Prevents crashes and "E" notation errors in inputs
    public static boolean isNumeric(String input, String fieldName) {
        try {
            Double.parseDouble(input); 
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " must be a valid number (e.g., 50000.00).", 
                "Invalid Number", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Mauritian NIC Check: 1 Letter + 13 Digits
    public static boolean isValidNIC(String nic) {
        if (nic == null) return false;
        String cleanedNIC = nic.trim();
        
        if (cleanedNIC.length() != 14) {
            JOptionPane.showMessageDialog(null, "NIC must be exactly 14 characters.", "NIC Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!Character.isLetter(cleanedNIC.charAt(0))) {
            JOptionPane.showMessageDialog(null, "NIC Error: The first character must be a letter.", "NIC Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        for (int i = 1; i < cleanedNIC.length(); i++) {
            if (!Character.isDigit(cleanedNIC.charAt(i))) {
                JOptionPane.showMessageDialog(null, "NIC Error: The last 13 characters must be digits.", "NIC Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}