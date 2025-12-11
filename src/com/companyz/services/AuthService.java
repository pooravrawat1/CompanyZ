package com.companyz.services;

import com.companyz.models.Employee;
import java.sql.SQLException;

public class AuthService {
    private static AuthService instance;
    private final AuthDAO authDAO;

    private AuthService() {
        this.authDAO = new AuthDAO();
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public Employee authenticate(int empId, String password) {
        try {
            return authDAO.getEmployeeByIdAndPassword(empId, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isAdmin(Employee employee) {
        return employee != null && "ADMIN".equalsIgnoreCase(employee.getRole());
    }
}
