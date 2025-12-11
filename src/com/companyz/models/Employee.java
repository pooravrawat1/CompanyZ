package com.companyz.models;

import java.time.LocalDate;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String ssn;
    private LocalDate dob;
    private LocalDate hireDate;
    private double currentSalary;
    private String email;
    private String jobTitle;
    private String division;
    private String passwordHash;
    private String role; // admin or employee
    private int jobTitleID;
    
    public Employee() {}
    
    public Employee(int empId, String firstName, String lastName, String ssn, LocalDate dob, 
                    LocalDate hireDate, double currentSalary, String email, String jobTitle,
                    String division, String role, int jobTitleID) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.dob = dob;
        this.hireDate = hireDate;
        this.currentSalary = currentSalary;
        this.email = email;
        this.jobTitle = jobTitle;
        this.division = division;
        this.role = role;
        this.jobTitleID = jobTitleID;
    }
    
    // Getters and Setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }
    
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    
    public double getCurrentSalary() { return currentSalary; }
    public void setCurrentSalary(double currentSalary) { this.currentSalary = currentSalary; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public int getJobTitleID() { return jobTitleID; }
    public void setJobTitleID(int jobTitleID) { this.jobTitleID = jobTitleID; }

    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    @Override
    public String toString() {
        return String.format("Employee[%d, %s %s, %s, Salary: %s, Title: %s, Division: %s, Role: %s]", 
            empId, firstName, lastName, email, currentSalary, jobTitle, division, role);
    }
}
