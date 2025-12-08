package com.companyz.ui;

import com.companyz.ANSI;
import com.companyz.models.Employee;
import java.util.List;

public final class TablePrinter {

    private TablePrinter() {}

    public static final String LEFT_ALIGN_FORMAT = "| %-6s | %-22s | %-21s | %-33s | %-11s | %-15s | %-12s | %n";
    public static final String TABLE_DECORATOR = "+--------+------------------------+-----------------------+-----------------------------------+-------------+-----------------+--------------+%n";
    public static final String TABLE_HEADER = "| EmpID  | First Name             | Last Name             | Email                             | DOB         | Salary          | SSN          |%n";
    public static final String QUERY_DECORATOR = "+--------+--------+--------+--------+";

    public static void printEmployeeTable(List<Employee> employees) {
        System.out.format("\n" + ANSI.GREEN + TABLE_DECORATOR);
        System.out.format(TABLE_HEADER);
        System.out.format(TABLE_DECORATOR + ANSI.RESET);

        for (Employee emp : employees) {
            System.out.format(LEFT_ALIGN_FORMAT,
                    emp.getEmpId(),
                    emp.getFirstName(),
                    emp.getLastName(),
                    emp.getEmail(),
                    emp.getDob(),
                    emp.getCurrentSalary(),
                    emp.getSsn());
        }

        System.out.format(ANSI.GREEN + TABLE_DECORATOR + ANSI.RESET);
    }
}
