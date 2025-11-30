package edenpackage;
// paqazackage src;
import java.sql.*;

import java.util.Scanner;

// import edenpackage.ANSI;



// CRUD is the acronym for CREATE, READ, UPDATE and DELETE

// Todo list:
// - Finish implementation of editing (so far, only first and last name are completed), dob?
// - Add options to editing menu (salary, email)
// - reports?
// add default switch cases; add booleans with while loop
// check what employees can search w

public class App {
	static String leftAlignFormat = "| %-6s | %-22s | %-21s | %-33s | %-11s | %-15s | %-12s | %n";
	static String tableDecorator = "+--------+------------------------+-----------------------+-----------------------------------+-------------+-----------------+--------------+%n";
	static String tableHeader = "| EmpID  | First Name             | Last Name             | Email                             | DOB         | Salary          | SSN          |%n";
	static String queryDecorator = "+--------+--------+--------+--------+";

	
	static String welcomeBanner =  """
                                        
                                         \u2588\u2588\u2588\u2588\u2588   \u2588\u2588\u2588   \u2588\u2588\u2588\u2588\u2588          \u2588\u2588\u2588\u2588                                             \u2588\u2588\u2588\r
                                        \u2592\u2592\u2588\u2588\u2588   \u2592\u2588\u2588\u2588  \u2592\u2592\u2588\u2588\u2588          \u2592\u2592\u2588\u2588\u2588                                            \u2592\u2588\u2588\u2588\r
                                         \u2592\u2588\u2588\u2588   \u2592\u2588\u2588\u2588   \u2592\u2588\u2588\u2588   \u2588\u2588\u2588\u2588\u2588\u2588  \u2592\u2588\u2588\u2588   \u2588\u2588\u2588\u2588\u2588\u2588   \u2588\u2588\u2588\u2588\u2588\u2588  \u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588\u2588    \u2588\u2588\u2588\u2588\u2588\u2588 \u2592\u2588\u2588\u2588\r
                                         \u2592\u2588\u2588\u2588   \u2592\u2588\u2588\u2588   \u2592\u2588\u2588\u2588  \u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588  \u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588 \u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588  \u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588\u2592\u2588\u2588\u2588\r
                                         \u2592\u2592\u2588\u2588\u2588  \u2588\u2588\u2588\u2588\u2588  \u2588\u2588\u2588  \u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588  \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2592\u2592 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588\u2588\u2588\u2588\u2588 \u2592\u2588\u2588\u2588\r
                                          \u2592\u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2592\u2588\u2588\u2588\u2588\u2588\u2592   \u2592\u2588\u2588\u2588\u2592\u2592\u2592   \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588  \u2588\u2588\u2588\u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588 \u2592\u2588\u2588\u2588\u2592\u2592\u2592  \u2592\u2592\u2592 \r
                                            \u2592\u2592\u2588\u2588\u2588 \u2592\u2592\u2588\u2588\u2588     \u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2588  \u2588\u2588\u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2588 \u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2588  \u2588\u2588\u2588\u2588\u2588\u2592\u2588\u2588\u2588 \u2588\u2588\u2588\u2588\u2588\u2592\u2592\u2588\u2588\u2588\u2588\u2588\u2588  \u2588\u2588\u2588\r
                                             \u2592\u2592\u2592   \u2592\u2592\u2592       \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592\u2592   \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592 \u2592\u2592\u2592 \u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592\u2592\u2592\u2592  \u2592\u2592\u2592 \r
                                                                                                                          \r
                                                                                                                          \r
                                                                                                                          """ //
                //
                //
                //
                //
                //
                //
                //
                //
                //
                ;

        static String waterfall = "         _.-----._\r\n" + //
                        "       .'.-'''''-.'._\r\n" + //
                        "      //`.:#:'    `\\\\\\\r\n" + //
                        "     ;; '           ;;'.__.===============,\r\n" + //
                        "     ||             ||  __       Who?      )\r\n" + //
                        "     ;;             ;;.'  '==============='\r\n" + //
                        "      \\\\           ///\r\n" + //
                        "       ':.._____..:'~\r\n" + //
                        "         `'-----'`\r\n" + //
                        "\r\n" + //
                        "";
        @SuppressWarnings("ConvertToTryWithResources")
	public static void main(String[] args) throws Exception {
		
		
		String welcomeColor = ANSI.KEWL+ "                     To Company Z's Employee Search Terminal                      " + ANSI.RESET;
		String welcomeText = ANSI.KEWL + "LOGIN OPTIONS\n" + ANSI.RESET+ queryDecorator + "\n- HR ADMIN (EDITING) - ENTER 1\n- EMPLOYEE (VIEWING) - ENTER 2\n\nENTER COMMAND: ";
		System.out.println(welcomeBanner + "                    " + ANSI.GREEN + "-----------------------------------------" + ANSI.RESET + "\n"+welcomeColor + "\n" + waterfall + "\n" 
		+ welcomeText);
		
		Scanner scn = new Scanner(System.in);
		int loginChoice = scn.nextInt();
		
		switch (loginChoice) {
			case 1 -> adminSearch(); //implement user auth for admin
			
			case 2 -> employeeSearch(); //implement user auth for admin
		}
	
		scn.close();
		DatabaseManager.closeConnection();

	}
	public static void adminSearch() {
		System.out.println(ANSI.KEWL + "\nEMPLOYEE SEARCH AND EDIT OPTIONS\n" + ANSI.RESET+ queryDecorator + "\n+ Name\n+ DOB\n+ SSN\n+ EmpID\n\nENTER COMMAND: ");
		Scanner scn = new Scanner(System.in);
		String searchChoice = scn.nextLine().toUpperCase();

		
		StringBuilder output = new StringBuilder("");
		try {
			while (!"Q".equals(searchChoice)) {
				switch (searchChoice) {
					case "NAME" -> {
						System.out.println(ANSI.GREEN + "\nEMPLOYEE SEARCH OPTIONS: NAME\n" + ANSI.RESET+ queryDecorator + "\n- To search via first name, enter 'FIRST'.\n- To search via last name, enter 'LAST'. \n- To search via both first and last name, enter 'BOTH'.\n\nENTER COMMAND:");
						scn = new Scanner(System.in);
						searchChoice = scn.nextLine().toUpperCase();
						switch (searchChoice) {
							case "FIRST" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = ?";
								
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								try {
									ResultSet myRS = DatabaseManager.executeQuery(sql, FnameData);
									boolean hasResults = false;
									while (myRS.next()) {
										hasResults = true;
										output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
										System.out.print(output.toString());
										System.out.format(tableDecorator);
										output.setLength(0);
									}
									if (!hasResults) {
										System.out.println(ANSI.RED + "No employees found with first name: " + FnameData + ANSI.RESET);
									}
								} catch (SQLException e) {
									System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
								}
							}
							
							case "LAST" -> {
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Lname = ?";
										
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								try {
									ResultSet myRS = DatabaseManager.executeQuery(sql, LnameData);
									boolean hasResults = false;
									while (myRS.next()) {
										hasResults = true;
										output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
										System.out.print(output.toString());
										System.out.format(tableDecorator);
										output.setLength(0);
									}
									if (!hasResults) {
										System.out.println(ANSI.RED + "No employees found with last name: " + LnameData + ANSI.RESET);
									}
								} catch (SQLException e) {
									System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
								}
							}
							
							case "BOTH" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = ? AND Lname = ?";

								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								try {
									ResultSet myRS = DatabaseManager.executeQuery(sql, FnameData, LnameData);
									boolean hasResults = false;
									while (myRS.next()) {
										hasResults = true;
										output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
										System.out.print(output.toString());
										System.out.format(tableDecorator);
										output.setLength(0);
									}
									if (!hasResults) {
										System.out.println(ANSI.RED + "No employees found with name: " + FnameData + " " + LnameData + ANSI.RESET);
									}
								} catch (SQLException e) {
									System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
								}
							}
							
						}
					}
					
				case "DOB" -> { 
					System.out.println("\nENTER DOB IN THE FORMAT OF YYYY-MM-DD:");
					String DOBstring = scn.nextLine();
					String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
							"FROM employees WHERE DOB = ?";
						
					System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);	
						
					try {
						ResultSet myRS = DatabaseManager.executeQuery(sql, DOBstring);
						boolean hasResults = false;
						while (myRS.next()) {
							hasResults = true;
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
						if (!hasResults) {
							System.out.println(ANSI.RED + "No employees found with DOB: " + DOBstring + ANSI.RESET);
						}
					} catch (SQLException e) {
						System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
					}
				}

				case "SSN" -> {
					System.out.println("\nENTER SSN IN THE FORMAT OF XXX-XX-XXXX:");
					String SSN = scn.nextLine();
					String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE SSN = ?";
					
					System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
					try {
						ResultSet myRS = DatabaseManager.executeQuery(sql, SSN);
						boolean hasResults = false;
						while (myRS.next()) {
							hasResults = true;
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
						if (!hasResults) {
							System.out.println(ANSI.RED + "No employees found with SSN: " + SSN + ANSI.RESET);
						}
					} catch (SQLException e) {
						System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
					}
				}

				case "EMPID" -> {
					System.out.println("\nENTER EMPID:");
					int EmpID = scn.nextInt();
					String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE empid = ?";
					
					System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
					try {
						ResultSet myRS = DatabaseManager.executeQuery(sql, EmpID);
						boolean hasResults = false;
						while (myRS.next()) {
							hasResults = true;
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
						if (!hasResults) {
							System.out.println(ANSI.RED + "No employee found with ID: " + EmpID + ANSI.RESET);
						}
					} catch (SQLException e) {
						System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
					}
				}
			}

				System.out.println(ANSI.GREEN + "\nWOULD YOU LIKE TO SEARCH AGAIN, EDIT AN EMPLOYEE'S INFORMATION, OR QUIT?\n" + ANSI.RESET + queryDecorator + "\n+ SEARCH AGAIN - ENTER 1\n+ EDIT EMPLOYEE INFORMATION - ENTER 2\n+ QUIT - ENTER 3 \n\nENTER COMMAND: ");	
				scn = new Scanner(System.in);
				int editChoice = scn.nextInt();
				
				switch (editChoice) {
					case 1 -> adminSearch();

					case 2 -> editing(); //implement user auth for admin
					
					case 3 -> System.out.println("Thank you for using the terminal.");  //implement user auth for admin
				}
	
				scn.close();
				
				// scn = new Scanner(System.in);
				// searchChoice = scn.nextLine().toUpperCase();
				// System.out.println(ANSI.GREEN + "\nEMPLOYEE SEARCH OPTIONS\n" + ANSI.RESET + queryDecorator + "\n+ Name\n+ DOB\n+ SSN\n+ EmpID\n\nENTER COMMAND: ");		
			}
			
		} catch (Exception e) {
			System.err.println(ANSI.RED + "An error occurred: " + e.getMessage() + ANSI.RESET);
		}
		
	}

	public static void employeeSearch() {
		
		System.out.println("\nEMPLOYEE SEARCH OPTIONS (VIEW ONLY)\n-----------------------\n- Name\n- DOB\n- SSN\n- EmpID\n\nENTER COMMAND: ");
		Scanner scn = new Scanner(System.in);
		String searchChoice = scn.nextLine().toUpperCase();

		
		StringBuilder output = new StringBuilder("");
		try {
			while (!"Q".equals(searchChoice)) {
				switch (searchChoice) {
					case "NAME" -> {
						System.out.println("\nEMPLOYEE SEARCH OPTIONS: NAME\n------------------------------------------\n- To search via first name, enter 'FIRST'.\n- To search via last name, enter 'LAST'. \n- To search via both first and last name, enter 'BOTH'.\n\nENTER COMMAND:");
						scn = new Scanner(System.in);
						searchChoice = scn.nextLine().toUpperCase();
						switch (searchChoice) {
							case "FIRST" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = ?";
								
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								try {
									ResultSet myRS = DatabaseManager.executeQuery(sql, FnameData);
									boolean hasResults = false;
									while (myRS.next()) {
										hasResults = true;
										output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
										System.out.print(output.toString());
										System.out.format(tableDecorator);
										output.setLength(0);
									}
									if (!hasResults) {
										System.out.println(ANSI.RED + "No employees found with first name: " + FnameData + ANSI.RESET);
									}
								} catch (SQLException e) {
									System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
								}
							}
							
							case "LAST" -> {
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Lname = ?";
										
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								try {
									ResultSet myRS = DatabaseManager.executeQuery(sql, LnameData);
									boolean hasResults = false;
									while (myRS.next()) {
										hasResults = true;
										output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
										System.out.print(output.toString());
										System.out.format(tableDecorator);
										output.setLength(0);
									}
									if (!hasResults) {
										System.out.println(ANSI.RED + "No employees found with last name: " + LnameData + ANSI.RESET);
									}
								} catch (SQLException e) {
									System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
								}
							}
							
							case "BOTH" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = ? AND Lname = ?";

								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								try {
									ResultSet myRS = DatabaseManager.executeQuery(sql, FnameData, LnameData);
									boolean hasResults = false;
									while (myRS.next()) {
										hasResults = true;
										output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
										System.out.print(output.toString());
										System.out.format(tableDecorator);
										output.setLength(0);
									}
									if (!hasResults) {
										System.out.println(ANSI.RED + "No employees found with name: " + FnameData + " " + LnameData + ANSI.RESET);
									}
								} catch (SQLException e) {
									System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
								}
							}
							
						}
					}
					
					case "DOB" -> {
						System.out.println("ENTER DOB:");
						String DOBstring = scn.nextLine();
						
						String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
								"FROM employees WHERE DOB = ?";
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
						try {		
							ResultSet myRS = DatabaseManager.executeQuery(sql, DOBstring);
							boolean hasResults = false;
							while (myRS.next()) {
								hasResults = true;
								output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
								System.out.print(output.toString());
								System.out.format(tableDecorator);
								output.setLength(0);
							}
							if (!hasResults) {
								System.out.println(ANSI.RED + "No employees found with DOB: " + DOBstring + ANSI.RESET);
							}
						} catch (SQLException e) {
							System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
						}
					}

					}

					case "SSN" -> {
						System.out.println("ENTER SSN:");
						String SSN = scn.nextLine();
						String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE SSN = ?";
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
						try {		
							ResultSet myRS = DatabaseManager.executeQuery(sql, SSN);
							boolean hasResults = false;
							while (myRS.next()) {
								hasResults = true;
								output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
								System.out.print(output.toString());
								System.out.format(tableDecorator);
								output.setLength(0);
							}
							if (!hasResults) {
								System.out.println(ANSI.RED + "No employees found with SSN: " + SSN + ANSI.RESET);
							}
						} catch (SQLException e) {
							System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
						}
					}

					case "EMPID" -> {
						System.out.println("ENTER EMPID:");
						int EmpID = scn.nextInt();
						String sql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE empid = ?";
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
						try {		
							ResultSet myRS = DatabaseManager.executeQuery(sql, EmpID);
							boolean hasResults = false;
							while (myRS.next()) {
								hasResults = true;
								output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
								System.out.print(output.toString());
								System.out.format(tableDecorator);
								output.setLength(0);
							}
							if (!hasResults) {
								System.out.println(ANSI.RED + "No employee found with ID: " + EmpID + ANSI.RESET);
							}
						} catch (SQLException e) {
							System.err.println(ANSI.RED + "Search failed: " + e.getMessage() + ANSI.RESET);
						}
					}
				}
	
				scn.close();
				scn = new Scanner(System.in);
				searchChoice = scn.nextLine().toUpperCase();
				System.out.println(ANSI.GREEN + "\nEMPLOYEE SEARCH OPTIONS\n" + ANSI.RESET + queryDecorator + "\n+ Name\n+ DOB\n+ SSN\n+ EmpID\n\nENTER COMMAND: ");		
			}
			
		} catch (Exception e) {
			System.err.println(ANSI.RED + "An error occurred: " + e.getMessage() + ANSI.RESET);
		}
	}

	public static void editing() throws SQLException {
		System.out.println(ANSI.GREEN + "\nEMPLOYEE EDITING" + ANSI.RESET);
		System.out.println("ENTER THE EMPID OF THE EMPLOYEE YOU'D LIKE TO EDIT:");
		Scanner scn = new Scanner(System.in);
		int EmpID = scn.nextInt();

		
		StringBuilder output = new StringBuilder("");
		try {
			System.out.println(ANSI.GREEN + "\nEDIT OPTIONS\n" + ANSI.RESET + queryDecorator + "\n+ Name\n+ DOB\n+ SSN\n+ EmpID\n\nENTER COMMAND: ");		
			scn = new Scanner(System.in);
			String editChoice = scn.nextLine();

			switch (editChoice.toUpperCase()) {
				case "NAME" -> {
					System.out.println(ANSI.GREEN + "\nEMPLOYEE EDITING OPTIONS: NAME\n" + ANSI.RESET + queryDecorator + "\n+ To edit first name, enter 'FIRST'.\n+ To edit last name, enter 'LAST'. \n+ To edit both first and last name, enter 'BOTH'.\n\nENTER COMMAND:");
					scn = new Scanner(System.in);
					editChoice = scn.nextLine().toUpperCase();
					switch (editChoice) {
						case "FIRST" -> {
							System.out.println("\nENTER NEW VALUE - FIRST NAME:");
							String FnameData = scn.nextLine();
							
							String updateSql = "UPDATE employees SET Fname = ? WHERE empid = ?";
							String selectSql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN FROM employees WHERE empid = ?";
							
							System.out.printf("\nNEW DATA ENTRY FOR EMPLOYEE %d:", EmpID);
							System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
							try {
								ResultSet myRS = DatabaseManager.executeUpdateAndFetch(
									updateSql, selectSql,
									new Object[]{FnameData, EmpID},
									new Object[]{EmpID}
								);
								
								if (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.printf(tableDecorator);
									output.setLength(0);
								} else {
									System.out.println(ANSI.RED + "Employee not found!" + ANSI.RESET);
								}
							} catch (SQLException e) {
								System.err.println(ANSI.RED + "Update failed: " + e.getMessage() + ANSI.RESET);
							}
						}

						case "LAST" -> {
							System.out.println("\nENTER NEW VALUE - LAST NAME:");
							String LnameData = scn.nextLine();
							
							String updateSql = "UPDATE employees SET Lname = ? WHERE empid = ?";
							String selectSql = "SELECT Fname, Lname, email, empid, DOB, Salary, SSN FROM employees WHERE empid = ?";
							
							System.out.printf("\nNEW DATA ENTRY FOR EMPLOYEE %d:", EmpID);
							System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
							
							try {
								ResultSet myRS = DatabaseManager.executeUpdateAndFetch(
									updateSql, selectSql,
									new Object[]{LnameData, EmpID},
									new Object[]{EmpID}
								);
								
								if (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.printf(tableDecorator);
									output.setLength(0);
								} else {
									System.out.println(ANSI.RED + "Employee not found!" + ANSI.RESET);
								}
							} catch (SQLException e) {
								System.err.println(ANSI.RED + "Update failed: " + e.getMessage() + ANSI.RESET);
							}
						}

						// Need to add editing for both first/last, empid, dob, ssn, salary
					}									
				}
			}
		} catch (Exception e) {
			System.err.println(ANSI.RED + "An error occurred: " + e.getMessage() + ANSI.RESET);
		}
	}
}


