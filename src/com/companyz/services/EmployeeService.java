// -- Active: 1765161594597@@127.0.0.1@3306@employeedata
package com.companyz.services;

import com.companyz.DatabaseManager;
import com.companyz.models.Employee;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private final List<Employee> employees;

    public EmployeeService(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee getById(int empId) {
        return employees.stream()
                .filter(e -> e.getEmpId() == empId)
                .findFirst()
                .orElse(null);
    }

    public List<Employee> searchByFirstName(String firstName) {
        return employees.stream()
                .filter(e -> e.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    public List<Employee> searchByLastName(String lastName) {
        return employees.stream()
                .filter(e -> e.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Employee> searchByFullName(String firstName, String lastName) {
        return employees.stream()
                .filter(e -> e.getFirstName().equalsIgnoreCase(firstName) && e.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Employee> searchByDOB(LocalDate dob) {
        return employees.stream()
                .filter(e -> dob.equals(e.getDob()))
                .collect(Collectors.toList());
    }

    public List<Employee> searchBySSN(String ssn) {
        return employees.stream()
                .filter(e -> ssn.equals(e.getSsn()))
                .collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void updateEmployee(Employee updated) {
        Employee existing = getById(updated.getEmpId());
        if (existing != null) {
            existing.setFirstName(updated.getFirstName());
            existing.setLastName(updated.getLastName());
            existing.setSsn(updated.getSsn());
            existing.setDob(updated.getDob());
            existing.setHireDate(updated.getHireDate());
            existing.setCurrentSalary(updated.getCurrentSalary());
            existing.setEmail(updated.getEmail());
            existing.setJobTitle(updated.getJobTitle());
            existing.setDivision(updated.getDivision());
            existing.setRole(updated.getRole());
        }
    }

    public void insertEmployee(Employee emp) {
        String sql = "INSERT INTO employees (empid, first_name, last_name, ssn, dob, hire_date, current_salary, email, password_hash, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        DatabaseManager.executeUpdate(sql, new Object[]{
            emp.getEmpId(), 
            emp.getFirstName(), 
            emp.getLastName(), 
            emp.getSsn(), 
            Date.valueOf(emp.getDob()), 
            Date.valueOf(emp.getHireDate()), 
            emp.getCurrentSalary(), 
            emp.getEmail(), 
            emp.getPasswordHash(), 
            "EMPLOYEE"
        });
        
        employees.add(emp);
    }

    public void updateSalaryByPercentage(int empId, double percent) {
        Employee emp = getById(empId);
        if (emp != null) {
            double increase = emp.getCurrentSalary() * (percent / 100.0);
            emp.setCurrentSalary(emp.getCurrentSalary() + increase);
        }
    }

    public void bulkSalaryIncrease(double percent, double minSalary, double maxSalary) {
        employees.stream()
                .filter(e -> e.getCurrentSalary() >= minSalary)
                .filter(e -> e.getCurrentSalary() <= maxSalary)
                .forEach(e -> e.setCurrentSalary(e.getCurrentSalary() * (1 + percent / 100.0)));
    }

    public List<Employee> getEmployeesHiredInRange(LocalDate startDate, LocalDate endDate) {
        return employees.stream()
            .filter(e -> e.getHireDate() != null)
            .filter(e -> !e.getHireDate().isBefore(startDate) && !e.getHireDate().isAfter(endDate))
            .collect(Collectors.toList());
    }
}
