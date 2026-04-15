package com.tax.math;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class TaxCalculationTest {

    @Test
    public void testStandardTaxCalculation() {
        double income = 1000000.0;
        double exemptions = 200000.0;
        double expectedTax = 80000.0; 

        double taxable = Math.max(0, income - exemptions);
        double actualTax = taxable * 0.10;

        assertThat(actualTax, is(expectedTax));
    }

    @Test
    public void testZeroFloorLogic() {
        double income = 50000.0;
        double exemptions = 100000.0;

        double taxable = Math.max(0, income - exemptions);
        
        assertThat(taxable, is(0.0));
    }
}