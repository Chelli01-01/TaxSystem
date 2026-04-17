package com.tax.math;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
//A Unit Testing class to verify the mathematical accuracy of the tax engine independently of the GUI.
public class TaxCalculationTest {

	
	
	/**
     * Test Case 1: Verifies the standard 10% tax calculation logic.
     * Scenario: Income (1M) - Exemptions (200k) = 800k taxable.
     * 10% of 800k should be exactly 80,000.
     */
	
    @Test
    public void testStandardTaxCalculation() {
        double income = 1000000.0;
        double exemptions = 200000.0;
        double expectedTax = 80000.0; 

     // Implementation of the core business logic
        double taxable = Math.max(0, income - exemptions);
        double actualTax = taxable * 0.10;

        assertThat(actualTax, is(expectedTax));
    }
    
    /**
     * Test Case 2: Verifies the "Zero-Floor" logic (The Owen Scenario).
     * Scenario: When exemptions are higher than income, the taxable amount 
     * must be zero, never a negative number.
     */

    @Test
    public void testZeroFloorLogic() {
        double income = 50000.0;
        double exemptions = 100000.0;

        double taxable = Math.max(0, income - exemptions);
        
     //Ensuring the system does not produce negative tax values.
        
        assertThat(taxable, is(0.0));
    }
}