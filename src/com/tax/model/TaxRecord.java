package com.tax.model;

public class TaxRecord {
	
	private String nic,name,address,employmentType;
	private double annualIncome, depAllowance, medAllowance, taxableAmount, taxPayable;
	
	//Defined Constructor
	
	public TaxRecord(String nic, String name, double income) {
		this.nic=nic;
		this.name=name;
		this.annualIncome=income;
		
	}
	
	// Defining Accessors and Mutators
	
	//Accessors
	
	public String getNic() {return nic;}
	public  String getName() {return name;}
	public String getAddress() {return address;}
	public  String getEmpType() {return employmentType;}
	public double getAnnualIncome() {return annualIncome;}
	
	//Mutators
	
	public void setNic(String nic) {this.nic=nic;}
	public void setName(String name) {this.name= name;}
	public void setAnnualIncome(double income) {this.annualIncome=income;}
	
}
