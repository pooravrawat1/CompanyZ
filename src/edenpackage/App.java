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
	static String url = "jdbc:mysql://localhost:3306/employeeData2"; //remember to add jar connection
		// may need to edit the database name later (employee vs companyzmaster vs employeedata2)
	static String user = "root";
	static String password = "pass";

	
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

	}
	public static void adminSearch() {
		System.out.println(ANSI.KEWL + "\nEMPLOYEE SEARCH AND EDIT OPTIONS\n" + ANSI.RESET+ queryDecorator + "\n+ Name\n+ DOB\n+ SSN\n+ EmpID\n\nENTER COMMAND: ");
		Scanner scn = new Scanner(System.in);
		String searchChoice = scn.nextLine().toUpperCase();

		
		StringBuilder output = new StringBuilder("");
		try (Connection myConn = DriverManager.getConnection(url, user, password)) {
			Statement myStmt = myConn.createStatement();


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
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = '%s'", FnameData);
								
								
								

								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.format(tableDecorator);
									output.setLength(0);
								}
								
							}
							
							case "LAST" -> {
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Lname = '%s'", LnameData);
										
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.format(tableDecorator);
									output.setLength(0);
								}
							}
							
							case "BOTH" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = '%s' AND Lname = '%s'", FnameData, LnameData);

								
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.format(tableDecorator);
									output.setLength(0);
								}
							}
							
						}
					}
					
					case "DOB" -> { 
						// still broken
						System.out.println("\nENTER DOB IN THE FORMAT OF YYYY-MM-DD:");
						String DOBstring = scn.nextLine();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
								"FROM employees WHERE DOB = '%s'", DOBstring);
							
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);	
								
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
					}

					case "SSN" -> {
						System.out.println("\nENTER SSN IN THE FORMAT OF XXX-XX-XXXX:");
						String SSN = scn.nextLine();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE SSN = '%s'", SSN);
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
					}

					case "EMPID" -> {
						System.out.println("\nENTER EMPID:");
						int EmpID = scn.nextInt();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE empid = %d", EmpID);
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
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
			
		myConn.close();
		} catch (Exception e) {
			// System.out.println("ERROR " + e.getLocalizedMessage());
		} finally {
		}
		
	}

	public static void employeeSearch() {
		
		System.out.println("\nEMPLOYEE SEARCH OPTIONS (VIEW ONLY)\n-----------------------\n- Name\n- DOB\n- SSN\n- EmpID\n\nENTER COMMAND: ");
		Scanner scn = new Scanner(System.in);
		String searchChoice = scn.nextLine().toUpperCase();

		
		StringBuilder output = new StringBuilder("");
		try (Connection myConn = DriverManager.getConnection(url, user, password)) {
			Statement myStmt = myConn.createStatement();

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
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = '%s'", FnameData);
								
								
								

								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.format(tableDecorator);
									output.setLength(0);
								}
								
							}
							
							case "LAST" -> {
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Lname = '%s'", LnameData);
										
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.format(tableDecorator);
									output.setLength(0);
								}
							}
							
							case "BOTH" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
										"FROM employees WHERE Fname = '%s' AND Lname = '%s'", FnameData, LnameData);

								
								System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
									System.out.print(output.toString());
									System.out.format(tableDecorator);
									output.setLength(0);
								}
							}
							
						}
					}
					
					case "DOB" -> {
						System.out.println("ENTER DOB:");
						String DOBstring = scn.nextLine();
						
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " +
								"FROM employees WHERE DOB = '%s'", DOBstring);
						System.out.println(sqlcommand);
						System.out.printf(tableDecorator);
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
					}

					case "SSN" -> {
						System.out.println("ENTER SSN:");
						String SSN = scn.nextLine();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE SSN = '%s'", SSN);
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
					}

					case "EMPID" -> {
						System.out.println("ENTER EMPID:");
						int EmpID = scn.nextInt();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE empid = %d", EmpID);
						
						System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
								
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.format(tableDecorator);
							output.setLength(0);
						}
					}
				}
	
				scn.close();
				scn = new Scanner(System.in);
				searchChoice = scn.nextLine().toUpperCase();
				System.out.println(ANSI.GREEN + "\nEMPLOYEE SEARCH OPTIONS\n" + ANSI.RESET + queryDecorator + "\n+ Name\n+ DOB\n+ SSN\n+ EmpID\n\nENTER COMMAND: ");		
			}
			
		
		myConn.close();
		} catch (Exception e) {
			// System.out.println("ERROR " + e.getLocalizedMessage());
		} finally {
		}
	}

	public static void editing() throws SQLException {
		System.out.println(ANSI.GREEN + "\nEMPLOYEE EDITING" + ANSI.RESET);
		System.out.println("ENTER THE EMPID OF THE EMPLOYEE YOU'D LIKE TO EDIT:");
		Scanner scn = new Scanner(System.in);
		int EmpID = scn.nextInt();

		
		StringBuilder output = new StringBuilder("");
		try (Connection myConn = DriverManager.getConnection(url, user, password)) {
		 	Statement myStmt = myConn.createStatement();
			

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
							
							String sqlcommand1 = String.format("UPDATE employees " + "SET Fname = '%s'" + " WHERE empid = %d", FnameData, EmpID);
							String sqlcommand2 = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE empid = %d", EmpID);
							System.out.printf("\nNEW DATA ENTRY FOR EMPLOYEE %d:", EmpID);
							System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
						
							myStmt.execute(sqlcommand1); // using execute instead of executeQuery because I just want it to do the command, NOT return anything
							// As shown here: https://stackoverflow.com/questions/48690990/how-to-execute-a-sql-statement-in-java-with-variables
							
							ResultSet myRS = myStmt.executeQuery(sqlcommand2);
							while (myRS.next()) {
								output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
								System.out.print(output.toString());
								System.out.printf("------------------------------------------------------------------------------%n");
								output.setLength(0);
							}
						}

						case "LAST" -> {
							System.out.println("\nENTER NEW VALUE - LAST NAME:");
							String LnameData = scn.nextLine();
							
							String sqlcommand1 = String.format("UPDATE employees " + "SET Lname = '%s'" + " WHERE empid = %d", LnameData, EmpID);
							String sqlcommand2 = String.format("SELECT Fname, Lname, email, empid, DOB, Salary, SSN " + " FROM employees" + " WHERE empid = %d", EmpID);
							System.out.printf("\nNEW DATA ENTRY FOR EMPLOYEE %d:", EmpID);
							
							System.out.format("\n" + ANSI.GREEN + tableDecorator + "\n" + tableHeader + "\n" + tableDecorator + ANSI.RESET);
							
							myStmt.execute(sqlcommand1); // using execute instead of executeQuery because we just want it to do the command, NOT return anything
							// As shown here: https://stackoverflow.com/questions/48690990/how-to-execute-a-sql-statement-in-java-with-variables
							
							ResultSet myRS = myStmt.executeQuery(sqlcommand2);
							while (myRS.next()) {
								output.append(String.format(leftAlignFormat, myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getString("DOB"), myRS.getBigDecimal("Salary"), myRS.getString("SSN")));
								System.out.print(output.toString());
								System.out.printf(tableDecorator);
								output.setLength(0);
							}
						}

						// Need to add editing for both first/last, empid, dob, ssn, salary
					}									
				}
			}
		}
	}
}


