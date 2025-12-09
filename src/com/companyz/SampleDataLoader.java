package com.companyz;

import com.companyz.models.Employee;
import com.companyz.models.Payroll;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SampleDataLoader {

    public static List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(buildEmployee(1, "Jane", "Smith", "111223333", LocalDate.of(1985, 3, 15),
            LocalDate.of(2015, 6, 1), 95000.0, "jane.smith@companyzAdmin.com",
            "HR Manager", "Human Resources", "ADMIN", "password123"));
        employees.add(buildEmployee(2, "John", "Doe", "222334444", LocalDate.of(1990, 7, 22),
            LocalDate.of(2018, 1, 15), 85000.0, "john.doe@companyz.com",
            "Software Engineer", "Engineering", "EMPLOYEE", "password123"));
        employees.add(buildEmployee(3, "Johnny", "Doewn", "222334444", LocalDate.of(1990, 7, 22),
            LocalDate.of(2018, 1, 15), 85000.0, "johnny.doewn@companyz.com",
            "Financial Analyst", "Finance", "EMPLOYEE", "password121"));
                
        return employees;
    }

    public static List<Payroll> getPayrolls() {
        List<Payroll> payrolls = new ArrayList<>();
        payrolls.add(buildPayroll(1, 1, LocalDate.of(2025, 1, 15), 3653.85, 2850.00, 803.85));
        payrolls.add(buildPayroll(2, 1, LocalDate.of(2025, 1, 31), 3653.85, 2850.00, 803.85));
        payrolls.add(buildPayroll(3, 2, LocalDate.of(2025, 1, 15), 3269.23, 2550.00, 719.23));
        payrolls.add(buildPayroll(4, 2, LocalDate.of(2025, 1, 31), 3269.23, 2550.00, 719.23));
        return payrolls;
    }

    private static Employee buildEmployee(int id, String first, String last, String ssn, LocalDate dob,
                                          LocalDate hireDate, double salary, String email,
                                          String jobTitle, String division, String role, String password) {
        Employee e = new Employee(id, first, last, ssn, dob, hireDate, salary, email, jobTitle, division, role);
        e.setPasswordHash(password);
        return e;
    }

    private static Payroll buildPayroll(int payId, int empId, LocalDate date, double gross, double net, double tax) {
        Payroll p = new Payroll();
        p.setPayId(payId);
        p.setEmpId(empId);
        p.setPayDate(date);
        p.setGross(gross);
        p.setNet(net);
        p.setTax(tax);
        return p;
    }
}
