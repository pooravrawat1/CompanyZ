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
        return employees.stream()
                .filter(e -> e.getEmpId() == empId && passwordPlain.equals(e.getPasswordHash()))
                .findFirst()
                .orElse(null);
    }

    public boolean isAdmin(Employee employee) {
        return employee != null && "ADMIN".equalsIgnoreCase(employee.getRole());
    }
}
