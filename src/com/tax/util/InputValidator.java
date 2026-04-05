package com.tax.util;
import javax.swing.JOptionPane; // this is required to trigger a dialog to tell the user what they did wrong.

/*
 Utility class to validate user input before processing tax calculations
 * */
public class InputValidator {
		
	// 1st method to check if a text field is empty
	public static boolean isNotEmpty(String input, String fieldName) {
		if (input == null || input.trim().isEmpty()) { // checks if the variable has no value/is empty at all
			
			JOptionPane.showMessageDialog(null, fieldName + " cannot be left blank. "
			+ "Please enter a value.","Input Error.",JOptionPane.ERROR_MESSAGE);
			// displaying appropriate error pop up + message
			
			return false; // If the input is invalid, the method shows the error message

		}
		
		return true; // If the input passes the check, the method returns true
		// Same applies for 'return' functions below
		
	}
	
	
	
	
	
	// 2nd method to check if the input can be parsed as a number (prevents crashes during math)
	public static boolean isNumeric(String input, String fieldName) {
		try {
			
			Double.parseDouble(input); // this tries to convert the possible string input into a number
			return true;
		}
		
		catch (NumberFormatException e) {
			
			JOptionPane.showMessageDialog(null, fieldName + 
			" must be a valid number. Please do not use letters or special characters.","Invalid Number",JOptionPane.ERROR_MESSAGE);
			return false; // displaying appropriate error pop up + message
		}
		
		
	}
	
	
	
	
	//This has been modified: Mauritian  NIC Check: (1 letter + 13 digits)
	
	// 3rd method to check if NIC is in valid format
	public static boolean isValidNIC(String nic) {
		
		if (nic == null) {return false;}
		
		String cleanedNIC= nic.trim();
		
		
		//First we check the length of the NIC
		
		if (cleanedNIC.length()!= 14) {
			JOptionPane.showMessageDialog(null, "NIC must be exactly 14 characters.", "NIC Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		// Here, we 'extract' the first char of NIC to identify if the first char is a letter or not
				
		char firstChar = cleanedNIC.charAt(0); 
		// picking the first letter (at position '0') in the 'trimmed' NIC string 
		
		if (!Character.isLetter(firstChar)) { // 'Character' encapsulates a single char value
			
			JOptionPane.showMessageDialog(null,"Invalid NIC. The first character must be a letter.",
			"NIC Error", JOptionPane.ERROR_MESSAGE);
			// displaying appropriate error pop up + message
			
			return false;			
		}
	
	// Now we verify if the remaining 13 characters are all digits
		
		for (int i = 1; i < cleanedNIC.length(); i++) {
            if (!Character.isDigit(cleanedNIC.charAt(i))) {
                JOptionPane.showMessageDialog(null, "The last 13 characters of the NIC must be numbers.", "NIC Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
	}
		
}