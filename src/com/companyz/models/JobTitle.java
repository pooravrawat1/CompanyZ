package com.companyz.models;

public class JobTitle {
    private int jobTitleId;
    private String titleName;
    
    public JobTitle() {}
    
    public JobTitle(int jobTitleId, String titleName) {
        this.jobTitleId = jobTitleId;
        this.titleName = titleName;
    }
    
    // Getters and Setters
    public int getJobTitleId() { return jobTitleId; }
    public void setJobTitleId(int jobTitleId) { this.jobTitleId = jobTitleId; }
    
    public String getTitleName() { return titleName; }
    public void setTitleName(String titleName) { this.titleName = titleName; }
    
    @Override
    public String toString() {
        return String.format("JobTitle[%d, %s]", jobTitleId, titleName);
    }
}
