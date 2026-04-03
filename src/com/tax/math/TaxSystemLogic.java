package com.tax.math;
import com.tax.model.TaxRecord;

public class TaxSystemLogic {
	public static void calculate(TaxRecord record) {
		//A. Calculation of Taxable Income
		
		double taxableAmount= record.getAnnualIncome()-(record.getDepAllowance()+record.getMedAllowance());
		
		//B. MRA 2026 Rule: if taxable I< 500,000; tax=0%, else 10% Flat Rate
		
		if (taxableAmount<500000 || taxableAmount<0) {
			record.setTaxPayable(0.0);
		}
		
		else {
			record.setTaxPayable(taxableAmount*0.10);
		}
		
	}

}
