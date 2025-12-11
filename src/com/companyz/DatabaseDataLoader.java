package com.companyz;

import com.companyz.models.Employee;
import com.companyz.models.Payroll;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDataLoader {
    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT e.emp_id, e.first_name, e.last_name, e.ssn, e.dob, e.hire_date, e.salary, e.email, "
            + "jt.job_title_name AS job_title, d.div_name AS division, e.password, e.role "
                + "FROM employees e "
                + "LEFT JOIN employee_job_titles ejt ON e.emp_id = ejt.emp_id "
                + "LEFT JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id "
                + "LEFT JOIN employee_division ed ON e.emp_id = ed.emp_id "
                + "LEFT JOIN division d ON ed.div_id = d.div_id";
        try {
            ResultSet rs = DatabaseManager.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setEmpId(rs.getInt("emp_id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setSsn(rs.getString("ssn"));
                    emp.setDob(rs.getDate("dob").toLocalDate());
                    emp.setHireDate(rs.getDate("hire_date").toLocalDate());
                    emp.setCurrentSalary(rs.getDouble("salary"));
                    emp.setEmail(rs.getString("email"));
                    emp.setJobTitle(rs.getString("job_title"));
                    emp.setDivision(rs.getString("division"));
                    emp.setPassword(rs.getString("password"));
                    emp.setRole(rs.getString("role"));
                    employees.add(emp);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error loading employees: " + e.getMessage());
        }
        return employees;
    }

    public static List<Payroll> getPayrolls() {
        List<Payroll> payrolls = new ArrayList<>();
        String sql = "SELECT pay_id, emp_id, pay_date, gross_pay, net_pay, tax_amount FROM payroll";
        try {
            ResultSet rs = DatabaseManager.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Payroll p = new Payroll();
                    p.setPayId(rs.getInt("pay_id"));
                    p.setEmpId(rs.getInt("emp_id"));
                    p.setPayDate(rs.getDate("pay_date").toLocalDate());
                    p.setGross(rs.getDouble("gross_pay"));
                    p.setNet(rs.getDouble("net_pay"));
                    p.setTax(rs.getDouble("tax_amount"));
                    payrolls.add(p);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error loading payrolls: " + e.getMessage());
        }
        return payrolls;
    }
}
