package com.companyz.models;

import java.time.LocalDate;

public class Payroll {
    private int payId;
    private int empId;
    private LocalDate payDate;
    private double gross;
    private double net;
    private double tax;
    
    public Payroll() {}
    
    public Payroll(int payId, int empId, LocalDate payDate, double gross, double net, double tax) {
        this.payId = payId;
        this.empId = empId;
        this.payDate = payDate;
        this.gross = gross;
        this.net = net;
        this.tax = tax;
    }
    
    // Getters and Setters
    public int getPayId() { return payId; }
    public void setPayId(int payId) { this.payId = payId; }
    
    public int getEmpId() { return empId; }
    public void setEmpId(int empId) { this.empId = empId; }
    
    public LocalDate getPayDate() { return payDate; }
    public void setPayDate(LocalDate payDate) { this.payDate = payDate; }
    
    public double getGross() { return gross; }
    public void setGross(double gross) { this.gross = gross; }

    public double getNet() { return net; }
    public void setNet(double net) { this.net = net; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }
    
    @Override
    public String toString() {
        return String.format("Payroll[PayId: %d, EmpId: %d, Date: %s, Gross: %s, Net: %s]", 
                payId, empId, payDate, gross, net);
    }
}
