package com.companyz;

import com.companyz.models.Employee;
import com.companyz.models.Payroll;
import com.companyz.models.PayrollSummary;
import com.companyz.services.AuthService;
import com.companyz.services.EmployeeService;
import com.companyz.services.PayrollService;
import com.companyz.ui.AsciiArt;
import com.companyz.ui.TablePrinter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
	private static final java.util.List<Employee> employees = new ArrayList<>(DatabaseDataLoader.getEmployees());
	private static final EmployeeService employeeService = new EmployeeService(employees);
	private static final PayrollService payrollService = new PayrollService(DatabaseDataLoader.getPayrolls(), employees);
	private static final AuthService authService = AuthService.getInstance();
	public static int empId;

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			// Program start
			System.out.println(AsciiArt.welcomeBanner);
			System.out.println(ANSI.KEWL + "To Company Z's Employee Search Terminal" + ANSI.RESET);
			
			// Read employee id
			System.out.print("Enter your ID: ");
			try{
				empId = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println(ANSI.RED + "Please enter an ID in the form of an integer. \n" + ANSI.RESET);
				App.main(args);
			}

			// Read password
			System.out.print("Enter your password: ");
			String password = scanner.nextLine();
			
			Employee currentUser = authService.authenticate(empId, password);
			while (currentUser == null) {
				System.out.println(ANSI.RED + "Invalid credentials." + ANSI.RESET);
				App.main(args); // added this to run the login request again if they enter the wrong info
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
				System.out.println("8) Create new employee");
				System.out.println("9) Logout");
				System.out.println("\n" + ANSI.CYAN + "----------------------" + ANSI.RESET);
				System.out.print("ENTER COMMAND: ");
				String choice = scanner.nextLine();

				switch (choice) {
					case "1":
						TablePrinter.printEmployeeTable(employeeService.getAllEmployees());
						break;
					case "2":
						searchEmployees(scanner, true);
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
						createEmployee(scanner);
						break;
					case "9":
						running = false;
						System.out.println("Thank you for using the terminal.");
						break;
					default:
						System.out.println(ANSI.RED + "Please select from options 1-8." + ANSI.RESET);
				}
			}
		}
	}

	private static void searchEmployees(Scanner scanner, boolean isAdmin) {
		System.out.println("\n" + ANSI.CYAN + "=== SEARCH OPTIONS ===" + ANSI.RESET);
		System.out.println("1) Employee ID");
		System.out.println("2) First Name");
		System.out.println("3) Last Name");
		System.out.println("4) Full Name");
		System.out.println("5) SSN");
		System.out.println("6) Date of Birth (YYYY-MM-DD)");
		System.out.println("\n" + ANSI.CYAN + "----------------------" + ANSI.RESET);
		System.out.print("ENTER COMMAND: ");
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
					System.out.println(ANSI.RED + "Please select from options 1-6." + ANSI.RESET);
					break;
			}
			if (results != null && !results.isEmpty()) {
				employeeService.hydrateJobTitleAndDivision(results);
				TablePrinter.printEmployeeTableWithJobDivision(results);

				if (isAdmin) {
					System.out.println("\n" + ANSI.CYAN + "=== UPDATE QUERY ===" + ANSI.RESET);
					System.out.print("Would you like to update employee data? (y/n): ");

					String updateChoice = scanner.nextLine().trim().toLowerCase();

					if (updateChoice.startsWith("y")) {
						if (results.size() == 1) {
                            // most likely case
							updateEmployeeFromSearch(scanner, results.get(0));
						} else {
							System.out.print("\nEnter the Employee ID to update: ");
							try {
								int empId = Integer.parseInt(scanner.nextLine());
								Employee selectedEmp = employeeService.getById(empId);

								boolean inResults = results.stream().anyMatch(e -> e.getEmpId() == empId);

								if (selectedEmp != null && inResults) {
									updateEmployeeFromSearch(scanner, selectedEmp);
								} else {
									System.out.println(ANSI.RED + "Employee ID not found in results." + ANSI.RESET);
								}

							} catch (NumberFormatException e) {
								System.out.println(ANSI.RED + "Invalid Employee ID." + ANSI.RESET);
							}
						}
					}
				}
			} else {
				System.out.println(ANSI.RED + "No employees found." + ANSI.RESET);
			}
		} catch (Exception e) {
			System.out.println(ANSI.RED + "Error: " + e.getMessage() + ANSI.RESET);
		}
	}

	private static void updateEmployeeFromSearch(Scanner scanner, Employee emp) {
		try {
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

	private static void createEmployee(Scanner scanner) {
		Employee emp = new Employee();
		System.out.println(ANSI.KEWL + "=== ENTER NEW EMPLOYEE DATA ===" + ANSI.RESET);

		Connection conn = null;
		boolean previousAutoCommit = true;
		try {
			System.out.print("\nFirst Name: ");
			emp.setFirstName(scanner.nextLine().trim());

			System.out.print("Last Name: ");
			emp.setLastName(scanner.nextLine().trim());

			System.out.print("SSN: ");
			emp.setSsn(scanner.nextLine().trim());

			System.out.print("DOB (YYYY-MM-DD): ");
			emp.setDob(java.time.LocalDate.parse(scanner.nextLine().trim()));

			System.out.print("Hire date (YYYY-MM-DD): ");
			emp.setHireDate(java.time.LocalDate.parse(scanner.nextLine().trim()));

			System.out.print("Salary: ");
			emp.setCurrentSalary(Double.parseDouble(scanner.nextLine().trim()));

			System.out.print("Email: ");
			emp.setEmail(scanner.nextLine().trim());

			System.out.print("Password: ");
			emp.setPassword(scanner.nextLine());

			System.out.print("Division: ");
			emp.setDivision(scanner.nextLine().trim());

			System.out.print("Job Title: ");
			emp.setJobTitle(scanner.nextLine().trim());

			String insertSql = "INSERT INTO employees (emp_id, first_name, last_name, ssn, dob, hire_date, salary, email, password, role) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			conn = DatabaseManager.getConnection();
			previousAutoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);

			int nextEmpId = getNextEmployeeId(conn);
			emp.setEmpId(nextEmpId);

			try (PreparedStatement ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, nextEmpId);
				ps.setString(2, emp.getFirstName());
				ps.setString(3, emp.getLastName());
				ps.setString(4, emp.getSsn());
				ps.setDate(5, java.sql.Date.valueOf(emp.getDob()));
				ps.setDate(6, java.sql.Date.valueOf(emp.getHireDate()));
				ps.setDouble(7, emp.getCurrentSalary());
				ps.setString(8, emp.getEmail());
				ps.setString(9, emp.getPassword());
				ps.setString(10, "EMPLOYEE");
				ps.executeUpdate();

				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						emp.setEmpId(keys.getInt(1));
					} else {
						emp.setEmpId(nextEmpId);
					}
				}
			}

			if (emp.getJobTitle() != null && !emp.getJobTitle().isBlank()) {
				int jtId = employeeService.ensureJobTitle(emp.getJobTitle());
				if (jtId > 0) {
					try (PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO employee_job_titles (emp_id, job_title_id) VALUES (?, ?)")) {
						ps.setInt(1, emp.getEmpId());
						ps.setInt(2, jtId);
						ps.executeUpdate();
					}
				}
			}

			if (emp.getDivision() != null && !emp.getDivision().isBlank()) {
				int divId = employeeService.ensureDivision(emp.getDivision());
				if (divId > 0) {
					try (PreparedStatement ps = conn.prepareStatement(
							"INSERT INTO employee_division (emp_id, div_id) VALUES (?, ?)")) {
						ps.setInt(1, emp.getEmpId());
						ps.setInt(2, divId);
						ps.executeUpdate();
					}
				}
			}

			employees.add(emp); 
			conn.commit();
			conn.setAutoCommit(previousAutoCommit);
			System.out.println(ANSI.GREEN + "Employee created successfully and saved to database." + ANSI.RESET);

		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ignored) {}
			System.out.println(ANSI.RED + "Error creating employee: " + e.getMessage() + ANSI.RESET);
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(previousAutoCommit);
				} catch (SQLException ignored) {}
			}
		}
	}

	private static int getNextEmployeeId(Connection conn) throws SQLException {
		String sql = "SELECT COALESCE(MAX(emp_id), 0) + 1 AS next_id FROM employees";
		try (PreparedStatement ps = conn.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt("next_id");
			}
			throw new SQLException("Could not determine next employee id.");
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
			System.out.print("Enter minimum salary: ");
			double min = Double.parseDouble(scanner.nextLine());
			System.out.print("Enter maximum salary: ");
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
				System.out.println("\n" + ANSI.CYAN + "----------------------" + ANSI.RESET);
				System.out.print("ENTER COMMAND: ");
				String choice = scanner.nextLine();

				switch (choice) {
					case "1":
						TablePrinter.printEmployeeTable(List.of(employee));
						break;
					case "2":
						searchEmployees(scanner, false);
						break;
					case "3":
						showPayHistory(employee.getEmpId());
						break;
					case "4":
						running = false;
						break;
					default:
						System.out.println(ANSI.RED + "Please select from options 1-4." + ANSI.RESET);
				}
			}
		}
	}

	private static void showPayHistory(int empId) {
		try {
			List<Payroll> history = payrollService.getPayHistory(empId);
			if (history.isEmpty()) {
				System.out.println(ANSI.YELLOW + "No payroll records found." + ANSI.RESET); //only getting this return message, need to build payrolls
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
