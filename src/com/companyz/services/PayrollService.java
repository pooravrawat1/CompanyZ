package com.companyz.services;

import com.companyz.models.Payroll;
import com.companyz.models.PayrollSummary;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PayrollService {
    private final List<Payroll> payrolls;

    public PayrollService(List<Payroll> payrolls) {
        this.payrolls = payrolls;
    }
    
    public List<Payroll> getPayHistory(int empId) {
        return payrolls.stream()
                .filter(p -> p.getEmpId() == empId)
                .sorted((a, b) -> b.getPayDate().compareTo(a.getPayDate()))
                .collect(Collectors.toList());
    }
    
    public List<Payroll> getPayrollByDateRange(LocalDate startDate, LocalDate endDate) {
        return payrolls.stream()
                .filter(p -> !p.getPayDate().isBefore(startDate) && !p.getPayDate().isAfter(endDate))
                .sorted((a, b) -> a.getPayDate().compareTo(b.getPayDate()))
                .collect(Collectors.toList());
    }
    
    public void addPayroll(Payroll payroll) {
        payrolls.add(payroll);
    }

    public List<PayrollSummary> getTotalPayByJobTitle(int year, int month) {
        return List.of();
    }

    public List<PayrollSummary> getTotalPayByDivision(int year, int month) {
        return List.of();
    }
}
