package com.companyz.ui;

import com.companyz.ANSI;
import com.companyz.models.Employee;
import java.util.List;

public final class TablePrinter {

    private TablePrinter() {}

    public static final String LEFT_ALIGN_FORMAT = "| %-6s | %-20s | %-20s | %-30s | %-11s | %-12s | %-12s | %n";
    public static final String TABLE_DECORATOR = "+--------+----------------------+----------------------+--------------------------------+-------------+--------------+--------------+%n";
    public static final String TABLE_HEADER = "| EmpID  | First Name           | Last Name            | Email                          | DOB         | Salary       | SSN          |%n";
    public static final String SUB_LEFT_ALIGN_FORMAT = "| %-6s | %-25s | %-25s | %n";
    public static final String SUB_TABLE_DECORATOR = "+--------+---------------------------+---------------------------+%n";
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
            System.out.format(TABLE_DECORATOR);
        }
    }

    public static void printEmployeeTableWithJobDivision(List<Employee> employees) {
        printEmployeeTable(employees);

        System.out.format("\n" + ANSI.GREEN + SUB_TABLE_DECORATOR);
        System.out.format(SUB_LEFT_ALIGN_FORMAT, "EmpID", "Job Title", "Division");
        System.out.format(SUB_TABLE_DECORATOR + ANSI.RESET);
        for (Employee emp : employees) {
            System.out.format(SUB_LEFT_ALIGN_FORMAT,
                    emp.getEmpId(),
                    emp.getJobTitle(),
                    emp.getDivision());
            System.out.format(SUB_TABLE_DECORATOR);
        }
    }
}
