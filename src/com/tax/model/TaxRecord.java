package com.tax.model;

public class TaxRecord {
    
    private String nic, name, address, contactNum, empType, residentStatus, bankName, accountNumber;
    private double annualIncome, depAllowance, medAllowance, taxableAmount, taxPayable;
    
    // Constructors
    public TaxRecord() {}

    public TaxRecord(String nic, String name, double income) {
        this.nic = nic;
        this.name = name;
        this.annualIncome = income;
    }
    
    // --- Accessors (Getters) ---
    public String getNic() { return nic; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getContactNum() { return contactNum; }
    public String getEmpType() { return empType; }
    public String getResidentStatus() { return residentStatus; }
    public String getBankName() { return bankName; }
    public String getAccountNumber() { return accountNumber; }
    
    public double getAnnualIncome() { return annualIncome; }
    public double getDepAllowance() { return depAllowance; }
    public double getMedAllowance() { return medAllowance; }
    public double getTaxableAmount() { return taxableAmount; }
    public double getTaxPayable() { return taxPayable; }

    // --- Mutators (Setters) ---
    public void setNic(String nic) { this.nic = nic; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setContactNum(String contactNum) { this.contactNum = contactNum; }
    public void setEmpType(String type) { this.empType = type; }
    public void setResidentStatus(String status) { this.residentStatus = status; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public void setAccountNumber(String acc) { this.accountNumber = acc; }
    
    public void setAnnualIncome(double income) { this.annualIncome = income; }
    public void setDepAllowance(double dep) { this.depAllowance = dep; }
    public void setMedAllowance(double med) { this.medAllowance = med; }
    public void setTaxableAmount(double amount) { this.taxableAmount = amount; }
    public void setTaxPayable(double tax) { this.taxPayable = tax; }
}