package com.companyz.services;

import com.companyz.DatabaseManager;
import com.companyz.models.Employee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO {
    public Employee getEmployeeByIdAndPassword(int empId, String password) throws SQLException {
        String sql = "SELECT emp_id, first_name, last_name, ssn, dob, hire_date, salary, email, password, role FROM employees WHERE emp_id = ? AND password = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setSsn(rs.getString("ssn"));
                emp.setDob(rs.getDate("dob").toLocalDate());
                emp.setHireDate(rs.getDate("hire_date").toLocalDate());
                emp.setCurrentSalary(rs.getDouble("salary"));
                emp.setEmail(rs.getString("email"));
                emp.setPassword(rs.getString("password"));
                emp.setRole(rs.getString("role"));
                return emp;
            }
        }
        return null;
    }
}
