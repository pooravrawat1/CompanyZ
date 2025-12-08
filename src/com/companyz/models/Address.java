package com.companyz.models;

import java.time.LocalDate;

public class Address {
    private int empId;
    private String street;
    private int cityId;
    private int stateId;
    private String zip;
    private String gender;
    private String identifiedRace;
    private LocalDate dob;
    private String mobilePhone;
    
    public Address() {}
    
    public Address(int empId, String street, int cityId, int stateId, String zip, 
                   String gender, String identifiedRace, LocalDate dob, String mobilePhone) {
        this.empId = empId;
        this.street = street;
        this.cityId = cityId;
        this.stateId = stateId;
        this.zip = zip;
        this.gender = gender;
        this.identifiedRace = identifiedRace;
        this.dob = dob;
        this.mobilePhone = mobilePhone;
    }
    
    // Getters and Setters
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public int getCityId() { return cityId; }
    public void setCityId(int cityId) { this.cityId = cityId; }
    
    public int getStateId() { return stateId; }
    public void setStateId(int stateId) { this.stateId = stateId; }
    
    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getIdentifiedRace() { return identifiedRace; }
    public void setIdentifiedRace(String identifiedRace) { this.identifiedRace = identifiedRace; }
    
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    
    public String getMobilePhone() { return mobilePhone; }
    public void setMobilePhone(String mobilePhone) { this.mobilePhone = mobilePhone; }
    
    @Override
    public String toString() {
        return String.format("Address[EmpId: %d, %s, City: %d, State: %d, Zip: %s]", 
                empId, street, cityId, stateId, zip);
    }
}
