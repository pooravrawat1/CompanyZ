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
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }
        return employees.stream()
                .filter(e -> e.getFirstName() != null && e.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    public List<Employee> searchByLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
        return employees.stream()
                .filter(e -> e.getLastName() != null && e.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Employee> searchByFullName(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("First name and last name cannot be null");
        }
        return employees.stream()
                .filter(e -> e.getFirstName() != null && e.getLastName() != null)
                .filter(e -> e.getFirstName().equalsIgnoreCase(firstName) && e.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    public List<Employee> searchByDOB(LocalDate dob) {
        if (dob == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        return employees.stream()
                .filter(e -> dob.equals(e.getDob()))
                .collect(Collectors.toList());
    }

    public List<Employee> searchBySSN(String ssn) {
        if (ssn == null) {
            throw new IllegalArgumentException("SSN cannot be null");
        }
        return employees.stream()
                .filter(e -> ssn.equals(e.getSsn()))
                .collect(Collectors.toList());
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void hydrateJobTitleAndDivision(List<Employee> list) {
        if (list == null) return;
        for (Employee e : list) {
            try {
                String jt = getJobTitleName(e.getEmpId());
                if (jt != null && !jt.isEmpty()) {
                    e.setJobTitle(jt);
                }
            } catch (Exception ignored) {}
            try {
                String dv = getDivisionName(e.getEmpId());
                if (dv != null && !dv.isEmpty()) {
                    e.setDivision(dv);
                }
            } catch (Exception ignored) {}
        }
    }

    private String getJobTitleName(int empId) {
        try {
            java.sql.ResultSet rs = DatabaseManager.executeQuery(
                "SELECT jt.job_title_name FROM employee_job_titles ejt JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id WHERE ejt.emp_id = ? LIMIT 1",
                new Object[]{ empId }
            );
            String name = rs.next() ? rs.getString("job_title_name") : null;
            rs.close();
            return name;
        } catch (Exception ignored) {
            return null;
        }
    }

    private String getDivisionName(int empId) {
        try {
            java.sql.ResultSet rs = DatabaseManager.executeQuery(
                "SELECT d.div_name FROM employee_division ed JOIN division d ON ed.div_id = d.div_id WHERE ed.emp_id = ? LIMIT 1",
                new Object[]{ empId }
            );
            String name = rs.next() ? rs.getString("div_name") : null;
            rs.close();
            return name;
        } catch (Exception ignored) {
            return null;
        }
    }

    public void updateEmployee(Employee updated) {
        if (updated == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
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
        if (emp == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (emp.getDob() == null || emp.getHireDate() == null) {
            throw new IllegalArgumentException("Employee date of birth and hire date cannot be null");
        }
        String sql = "INSERT INTO employees (first_name, last_name, ssn, dob, hire_date, salary, email, password, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (java.sql.PreparedStatement ps = DatabaseManager.getConnection()
                .prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getSsn());
            ps.setDate(4, Date.valueOf(emp.getDob()));
            ps.setDate(5, Date.valueOf(emp.getHireDate()));
            ps.setDouble(6, emp.getCurrentSalary());
            ps.setString(7, emp.getEmail());
            ps.setString(8, emp.getPassword());
            ps.setString(9, "EMPLOYEE");

            int rows = ps.executeUpdate();
            System.out.println("Insert employees affected rows: " + rows);

            try (java.sql.ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    emp.setEmpId(keys.getInt(1));
                    System.out.println("Inserted employee with ID: " + emp.getEmpId());
                }
            }

            employees.add(emp);
            System.out.println("Employee added in-memory list with ID: " + emp.getEmpId());
        } catch (Exception e) {
            System.out.println("Failed to insert employee: " + e.getMessage());
        }
    }

    public int ensureJobTitle(String jobTitleName) {
        try {
            java.sql.ResultSet rs = DatabaseManager.executeQuery(
                "SELECT job_title_id FROM job_titles WHERE job_title_name = ?",
                new Object[]{ jobTitleName }
            );
            if (rs.next()) {
                int id = rs.getInt("job_title_id");
                rs.close();
                return id;
            }
            rs.close();

            DatabaseManager.executeUpdate(
                "INSERT INTO job_titles (job_title_name) VALUES (?)",
                new Object[]{ jobTitleName }
            );
            java.sql.ResultSet rs2 = DatabaseManager.executeQuery("SELECT LAST_INSERT_ID() AS id");
            int id = rs2.next() ? rs2.getInt("id") : 0;
            rs2.close();
            return id;
        } catch (Exception ignored) {
            return 0;
        }
    }

    public int ensureDivision(String divisionName) {
        try {
            java.sql.ResultSet rs = DatabaseManager.executeQuery(
                "SELECT div_id FROM division WHERE div_name = ?",
                new Object[]{ divisionName }
            );
            if (rs.next()) {
                int id = rs.getInt("div_id");
                rs.close();
                return id;
            }
            rs.close();

            DatabaseManager.executeUpdate(
                "INSERT INTO division (div_name) VALUES (?)",
                new Object[]{ divisionName }
            );
            java.sql.ResultSet rs2 = DatabaseManager.executeQuery("SELECT LAST_INSERT_ID() AS id");
            int id = rs2.next() ? rs2.getInt("id") : 0;
            rs2.close();
            return id;
        } catch (Exception ignored) {
            return 0;
        }
    }

    public void assignJobTitle(int empId, String jobTitleName) {
        int jtId = ensureJobTitle(jobTitleName);
        if (jtId == 0) {
            System.out.println("Could not ensure job title '" + jobTitleName + "'");
            return;
        }
        try {
            DatabaseManager.executeUpdate(
                "INSERT INTO employee_job_titles (emp_id, job_title_id) VALUES (?, ?)",
                new Object[]{ empId, jtId }
            );
            System.out.println("Assigned job title '" + jobTitleName + "' (id=" + jtId + ") to emp " + empId);
        } catch (Exception ignored) {
            System.out.println("Failed to assign job title '" + jobTitleName + "' to emp " + empId);
        }
    }

    public void assignDivision(int empId, String divisionName) {
        int divId = ensureDivision(divisionName);
        if (divId == 0) {
            System.out.println("Could not ensure division '" + divisionName + "'");
            return;
        }
        try {
            DatabaseManager.executeUpdate(
                "INSERT INTO employee_division (emp_id, div_id) VALUES (?, ?)",
                new Object[]{ empId, divId }
            );
            System.out.println("Assigned division '" + divisionName + "' (id=" + divId + ") to emp " + empId);
        } catch (Exception e) {
            System.out.println("Failed to assign division '" + divisionName + "' to emp " + empId + ": " + e.getMessage());
        }
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
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return employees.stream()
            .filter(e -> e.getHireDate() != null)
            .filter(e -> !e.getHireDate().isBefore(startDate) && !e.getHireDate().isAfter(endDate))
            .collect(Collectors.toList());
    }
}
