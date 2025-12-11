package com.companyz.services;

import com.companyz.models.Employee;
import java.util.List;

/**
 * Very small, in-memory authentication helper.
 */
public class AuthService {
    private final List<Employee> employees;

    public AuthService(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee authenticate(int empId, String passwordPlain) {
        if (passwordPlain == null) {
            System.out.println("Password or username incorrect. Please try again.");
            return null;
        }
        Employee employee = employees.stream()
                .filter(e -> e.getEmpId() == empId && passwordPlain.equals(e.getPasswordHash()))
                .findFirst()
                .orElse(null);
        
        if (employee == null) {
            System.out.println("Password or username incorrect. Please try again.");
        }
        
        return employee;
    }

    public boolean isAdmin(Employee employee) {
        return employee != null && "ADMIN".equalsIgnoreCase(employee.getRole());
    }
}
