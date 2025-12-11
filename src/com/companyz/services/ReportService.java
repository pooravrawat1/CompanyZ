package com.companyz.services;

import com.companyz.DatabaseManager;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {
    
    public Map<String, BigDecimal> getTotalPayByJobTitle(int year, int month) throws SQLException {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        
        String sql = "SELECT jt.title_name, SUM(p.gross) as total_pay " +
                     "FROM payroll p " +
                     "JOIN employee_job_titles ejt ON p.empid = ejt.empid " +
                     "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
                     "WHERE YEAR(p.pay_date) = ? AND MONTH(p.pay_date) = ? " +
                     "GROUP BY jt.title_name " +
                     "ORDER BY total_pay DESC";
        
        ResultSet rs = DatabaseManager.executeQuery(sql, new Object[]{year, month});
        Map<String, BigDecimal> results = new HashMap<>();
        
        if (rs == null) {
            System.err.println("Failed to retrieve payroll data by job title");
            return results;
        }
        
        while (rs.next()) {
            results.put(rs.getString("title_name"), rs.getBigDecimal("total_pay"));
        }
        
        return results;
    }
    
    public Map<String, BigDecimal> getTotalPayByDivision(int year, int month) throws SQLException {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        
        String sql = "SELECT d.division_name, SUM(p.gross) as total_pay " +
                     "FROM payroll p " +
                     "JOIN employee_division ed ON p.empid = ed.empid " +
                     "JOIN division d ON ed.div_id = d.id " +
                     "WHERE YEAR(p.pay_date) = ? AND MONTH(p.pay_date) = ? " +
                     "GROUP BY d.division_name " +
                     "ORDER BY total_pay DESC";
        
        ResultSet rs = DatabaseManager.executeQuery(sql, new Object[]{year, month});
        Map<String, BigDecimal> results = new HashMap<>();
        
        if (rs == null) {
            System.err.println("Failed to retrieve payroll data by division");
            return results;
        }
        
        while (rs.next()) {
            results.put(rs.getString("division_name"), rs.getBigDecimal("total_pay"));
        }
        
        return results;
    }
    
    public List<String> getEmployeesHiredInRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        String sql = "SELECT empid, first_name, last_name, hire_date, email " +
                     "FROM employees " +
                     "WHERE hire_date BETWEEN ? AND ? " +
                     "ORDER BY hire_date DESC";
        
        ResultSet rs = DatabaseManager.executeQuery(sql, new Object[]{Date.valueOf(startDate), Date.valueOf(endDate)});
        List<String> results = new ArrayList<>();
        
        if (rs == null) {
            System.err.println("Failed to retrieve employee hire data");
            return results;
        }
        
        while (rs.next()) {
            String info = String.format("EmpID: %d | %s %s | Hired: %s | Email: %s",
                    rs.getInt("empid"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("hire_date"),
                    rs.getString("email"));
            results.add(info);
        }
        
        return results;
    }
}
