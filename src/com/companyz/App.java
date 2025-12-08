package com.companyz;

import com.companyz.models.Employee;
import com.companyz.models.Payroll;
import com.companyz.models.PayrollSummary;
import com.companyz.services.AuthService;
import com.companyz.services.EmployeeService;
import com.companyz.services.PayrollService;
import com.companyz.ui.AsciiArt;
import com.companyz.ui.TablePrinter;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final List<Employee> employees = SampleDataLoader.getEmployees();
    private static final EmployeeService employeeService = new EmployeeService(employees);
    private static final AuthService authService = new AuthService(employees);
    private static final PayrollService payrollService = new PayrollService(SampleDataLoader.getPayrolls());

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Program start
            System.out.println(AsciiArt.welcomeBanner);
            System.out.println(ANSI.KEWL + "To Company Z's Employee Search Terminal" + ANSI.RESET);
            
            // Read employee id
            System.out.print("Enter your employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine());
            
            // Read password
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            Employee currentUser = authService.authenticate(empId, password);
            if (currentUser == null) {
                System.out.println(ANSI.RED + "Invalid credentials." + ANSI.RESET);
                return;
            }

            // Branch to admin/employee paths
            if (authService.isAdmin(currentUser)) {
                System.out.println(ANSI.GREEN + "Authenticated as ADMIN." + ANSI.RESET);
                runAdminMode();
            } else {
                System.out.println(ANSI.GREEN + "Authenticated as EMPLOYEE." + ANSI.RESET);
                runEmployeeMode(currentUser);
            }
        }
    }

    private static void runAdminMode() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n" + ANSI.CYAN + "=== HR ADMIN MENU ===" + ANSI.RESET);
                System.out.println("1) List all employees");
                System.out.println("2) Search employees");
                System.out.println("3) Update employee data");
                System.out.println("4) Bulk salary increase (by range)");
                System.out.println("5) Total pay by Job Title (monthly)");
                System.out.println("6) Total pay by Division (monthly)");
                System.out.println("7) Employees hired in date range");
                System.out.println("8) Logout");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        TablePrinter.printEmployeeTable(employeeService.getAllEmployees());
                        break;
                    case "2":
                        searchEmployees(scanner);
                        break;
                    case "3":
                        updateEmployeeData(scanner);
                        break;
                    case "4":
                        bulkSalaryIncrease(scanner);
                        break;
                    case "5":
                        showTotalPayByJobTitle(scanner);
                        break;
                    case "6":
                        showTotalPayByDivision(scanner);
                        break;
                    case "7":
                        showEmployeesHiredInRange(scanner);
                        break;
                    case "8":
                        running = false;
                        break;
                    default:
                        System.out.println("Please choose 1-8.");
                }
            }
        }
    }

    private static void searchEmployees(Scanner scanner) {
        System.out.println("\nSearch by:");
        System.out.println("1) Employee ID");
        System.out.println("2) First Name");
        System.out.println("3) Last Name");
        System.out.println("4) Full Name");
        System.out.println("5) SSN");
        System.out.println("6) Date of Birth (YYYY-MM-DD)");
        System.out.print("Choose: ");
        String choice = scanner.nextLine();

        List<Employee> results = null;
        try {
            switch (choice) {
                case "1": {
                    System.out.print("Enter Employee ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    Employee e = employeeService.getById(id);
                    results = e != null ? List.of(e) : List.of();
                    break;
                }
                case "2": {
                    System.out.print("Enter First Name: ");
                    results = employeeService.searchByFirstName(scanner.nextLine());
                    break;
                }
                case "3": {
                    System.out.print("Enter Last Name: ");
                    results = employeeService.searchByLastName(scanner.nextLine());
                    break;
                }
                case "4": {
                    System.out.print("Enter First Name: ");
                    String first = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String last = scanner.nextLine();
                    results = employeeService.searchByFullName(first, last);
                    break;
                }
                case "5": {
                    System.out.print("Enter SSN: ");
                    results = employeeService.searchBySSN(scanner.nextLine());
                    break;
                }
                case "6": {
                    System.out.print("Enter DOB (YYYY-MM-DD): ");
                    String dobStr = scanner.nextLine();
                    results = employeeService.searchByDOB(java.time.LocalDate.parse(dobStr));
                    break;
                }
                default:
                    System.out.println("Invalid option.");
                    break;
            }
            if (results != null && !results.isEmpty()) {
                TablePrinter.printEmployeeTable(results);
            } else {
                System.out.println(ANSI.RED + "No employees found." + ANSI.RESET);
            }
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static void updateEmployeeData(Scanner scanner) {
        System.out.print("Enter Employee ID to update: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Employee emp = employeeService.getById(id);
            if (emp == null) {
                System.out.println(ANSI.RED + "Employee not found." + ANSI.RESET);
                return;
            }

            System.out.println("Current data:");
            TablePrinter.printEmployeeTable(List.of(emp));

            System.out.println("\nLeave blank to keep current value.");
            System.out.print("First Name [" + emp.getFirstName() + "]: ");
            String first = scanner.nextLine();
            if (!first.isBlank()) {
                emp.setFirstName(first);
            }

            System.out.print("Last Name [" + emp.getLastName() + "]: ");
            String last = scanner.nextLine();
            if (!last.isBlank()) {
                emp.setLastName(last);
            }

            System.out.print("Email [" + emp.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (!email.isBlank()) {
                emp.setEmail(email);
            }

            System.out.print("Salary [" + emp.getCurrentSalary() + "]: ");
            String sal = scanner.nextLine();
            if (!sal.isBlank()) {
                double salaryVal = Double.parseDouble(sal);
                emp.setCurrentSalary(salaryVal);
            }

            employeeService.updateEmployee(emp);
            System.out.println(ANSI.GREEN + "Employee updated successfully." + ANSI.RESET);
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static void bulkSalaryIncrease(Scanner scanner) {
        try {
            System.out.print("Enter percentage increase (e.g., 5 for 5%): ");
            double percent = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter minimum salary (inclusive): ");
            double min = Double.parseDouble(scanner.nextLine());
            System.out.print("Enter maximum salary (inclusive): ");
            double max = Double.parseDouble(scanner.nextLine());

            employeeService.bulkSalaryIncrease(percent, min, max);
            System.out.println(ANSI.GREEN + "Salaries updated successfully." + ANSI.RESET);
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static void runEmployeeMode(Employee employee) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\n" + ANSI.CYAN + "=== EMPLOYEE MENU ===" + ANSI.RESET);
                System.out.println("1) View my information");
                System.out.println("2) Search employees");
                System.out.println("3) View my pay history");
                System.out.println("4) Logout");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        TablePrinter.printEmployeeTable(List.of(employee));
                        break;
                    case "2":
                        searchEmployees(scanner);
                        break;
                    case "3":
                        showPayHistory(employee.getEmpId());
                        break;
                    case "4":
                        running = false;
                        break;
                    default:
                        System.out.println("Please choose 1-4.");
                }
            }
        }
    }

    private static void showPayHistory(int empId) {
        try {
            List<Payroll> history = payrollService.getPayHistory(empId);
            if (history.isEmpty()) {
                System.out.println(ANSI.YELLOW + "No payroll records found." + ANSI.RESET);
            } else {
                System.out.println(ANSI.GREEN + "\nPay History (most recent first):" + ANSI.RESET);
                System.out.printf("%-10s %-15s %-15s %-15s %-15s%n", "Pay ID", "Date", "Gross", "Net", "Tax");
                System.out.println("---------------------------------------------------------------------------------");
                for (Payroll p : history) {
                    System.out.printf("%-10d %-15s %-15s %-15s %-15s%n",
                            p.getPayId(), p.getPayDate(), p.getGross(), p.getNet(), p.getTax());
                }
            }
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static void showTotalPayByJobTitle(Scanner scanner) {
        try {
            System.out.print("Enter year (e.g., 2025): ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());

            List<PayrollSummary> summary = payrollService.getTotalPayByJobTitle(year, month);
            if (summary.isEmpty()) {
                System.out.println(ANSI.YELLOW + "No payroll data for that period." + ANSI.RESET);
            } else {
                System.out.println(ANSI.GREEN + "\nTotal Pay by Job Title:" + ANSI.RESET);
                System.out.printf("%-30s %-15s%n", "Job Title", "Total Gross");
                System.out.println("-----------------------------------------------");
                for (PayrollSummary s : summary) {
                    System.out.printf("%-30s %-15s%n", s.name, s.total);
                }
            }
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static void showTotalPayByDivision(Scanner scanner) {
        try {
            System.out.print("Enter year (e.g., 2025): ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());

            List<PayrollSummary> summary = payrollService.getTotalPayByDivision(year, month);
            if (summary.isEmpty()) {
                System.out.println(ANSI.YELLOW + "No payroll data for that period." + ANSI.RESET);
            } else {
                System.out.println(ANSI.GREEN + "\nTotal Pay by Division:" + ANSI.RESET);
                System.out.printf("%-30s %-15s%n", "Division", "Total Gross");
                System.out.println("-----------------------------------------------");
                for (PayrollSummary s : summary) {
                    System.out.printf("%-30s %-15s%n", s.name, s.total);
                }
            }
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }

    private static void showEmployeesHiredInRange(Scanner scanner) {
        try {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter end date (YYYY-MM-DD): ");
            LocalDate end = LocalDate.parse(scanner.nextLine());

            List<Employee> hired = employeeService.getEmployeesHiredInRange(start, end);
            if (hired.isEmpty()) {
                System.out.println(ANSI.YELLOW + "No employees hired in that range." + ANSI.RESET);
            } else {
                System.out.println(ANSI.GREEN + "\nEmployees hired between " + start + " and " + end + ":" + ANSI.RESET);
                TablePrinter.printEmployeeTable(hired);
            }
        } catch (Exception e) {
            System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
        }
    }
}
