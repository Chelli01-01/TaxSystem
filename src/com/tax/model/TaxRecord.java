package com.tax.model;

public class TaxRecord {
	
	private String nic,name,address,contactNum,employmentType,residentStatus;
	private double annualIncome, depAllowance, medAllowance, taxableAmount, taxPayable;
	
	//Defined Constructor
	
	public TaxRecord(String nic, String name, double income) {
		this.nic=nic;
		this.name=name;
		this.annualIncome=income;
		
	}
    public TaxRecord() {
		
	}
    
    
	// Defining Accessors and Mutators
	
	//Accessors
	
	

	public String getNic() {return nic;}
	public  String getName() {return name;}
	public String getAddress() {return address;}
	public String getContactNum() {return contactNum;}
	public  String getEmpType() {return employmentType;}
	public String getResidentStatus () {return residentStatus;}
	public double getAnnualIncome() {return annualIncome;}
	public double getDepAllowance() { return depAllowance; }
	public double getMedAllowance() { return medAllowance; }
	public double getTaxableAmount() { return taxableAmount; }
	public double getTaxPayable() { return taxPayable; }
	
	//Mutators
	
	public void setNic(String nic) {this.nic=nic;}
	public void setName(String name) {this.name= name;}
	public void setAnnualIncome(double income) {this.annualIncome=income;}
	public void setAddress(String address) { this.address = address; }
	public void setContactNum(String contactNum) {this.contactNum=contactNum;}
	public void setResidentStatus(String residentStatus) {this.residentStatus=residentStatus;}
	public void setEmpType(String type) { this.employmentType = type; }
	public void setDepAllowance(double dep) { this.depAllowance = dep; }
	public void setMedAllowance(double med) { this.medAllowance = med; }
	public void setTaxableAmount(double amount) { this.taxableAmount = amount; }
	public void setTaxPayable(double tax) { this.taxPayable = tax; }
	
}
