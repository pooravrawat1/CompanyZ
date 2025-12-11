package com.companyz.services;

import com.companyz.models.Payroll;
import com.companyz.models.PayrollSummary;
import com.companyz.models.Employee;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class PayrollService {
    private final List<Payroll> payrolls;
    private final List<Employee> employees;

    public PayrollService(List<Payroll> payrolls, List<Employee> employees) {
        this.payrolls = payrolls;
        this.employees = employees;
    }
    
    public List<Payroll> getPayHistory(int empId) {
        return payrolls.stream()
                .filter(p -> p.getEmpId() == empId)
                .sorted((a, b) -> b.getPayDate().compareTo(a.getPayDate()))
                .collect(Collectors.toList());
    }
    
    public List<Payroll> getPayrollByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return payrolls.stream()
                .filter(p -> p.getPayDate() != null)
                .filter(p -> !p.getPayDate().isBefore(startDate) && !p.getPayDate().isAfter(endDate))
                .sorted((a, b) -> a.getPayDate().compareTo(b.getPayDate()))
                .collect(Collectors.toList());
    }
    
    public void addPayroll(Payroll payroll) {
        payrolls.add(payroll);
    }

    public List<PayrollSummary> getTotalPayByJobTitle(int year, int month) {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        Map<String, Double> totals = new HashMap<>();

        payrolls.stream()
                .filter(p -> p.getPayDate() != null)
                .filter(p -> p.getPayDate().getYear() == year && p.getPayDate().getMonthValue() == month)
                .forEach(p -> {
                    Employee emp = findEmployee(p.getEmpId());
                    String key = emp != null ? emp.getJobTitle() : "Unknown";
                    totals.put(key, totals.getOrDefault(key, 0.0) + p.getGross());
                });

        return totals.entrySet().stream()
                .map(e -> new PayrollSummary(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public List<PayrollSummary> getTotalPayByDivision(int year, int month) {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        Map<String, Double> totals = new HashMap<>();

        payrolls.stream()
                .filter(p -> p.getPayDate() != null)
                .filter(p -> p.getPayDate().getYear() == year && p.getPayDate().getMonthValue() == month)
                .forEach(p -> {
                    Employee emp = findEmployee(p.getEmpId());
                    String key = emp != null ? emp.getDivision() : "Unknown";
                    totals.put(key, totals.getOrDefault(key, 0.0) + p.getGross());
                });

        return totals.entrySet().stream()
                .map(e -> new PayrollSummary(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    private Employee findEmployee(int empId) {
        return employees.stream()
                .filter(e -> e.getEmpId() == empId)
                .findFirst()
                .orElse(null);
    }
}
