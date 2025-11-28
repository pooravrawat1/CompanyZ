// paqazackage src;
import java.sql.*;
import java.util.Scanner;


// CRUD is the acronym for CREATE, READ, UPDATE and DELETE

// Todo list:
// - Finish implementation of editing (so far, only first and last name are completed)
// - Add options to editing menu (salary, email)
// - addresses?
public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("\nWELCOME! LOGIN OPTIONS\n------------------------------------------\n- HR ADMIN (EDITING) - ENTER 1\n- EMPLOYEE (VIEWING) - ENTER 2\n\nENTER COMMAND: ");
		
		Scanner scn = new Scanner(System.in);
		int loginChoice = scn.nextInt();
		
		switch (loginChoice) {
			case 1 -> adminSearchEdit(); //implement user auth for admin
			
			case 2 -> employeeSearch(); //implement user auth for admin
		}
	
		scn.close();

	}

	public static void adminSearchEdit() {
		System.out.println("\nEMPLOYEE SEARCH AND EDIT OPTIONS\n------------------------------------------\n- Name\n- DOB\n- SSN\n- EmpID\n\nENTER COMMAND: ");
		Scanner scn = new Scanner(System.in);
		String searchChoice = scn.nextLine().toUpperCase();

		String url = "jdbc:mysql://localhost:3306/employeeData2"; //remember to add jar connection
		// may need to edit the database name later (employee vs companyzmaster vs employeedata2)
		String user = "root";
		String password = "pass";
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
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
										"FROM employees WHERE Fname = '%s'", FnameData);
								
								System.out.printf("------------------------------------------------------------------------------%n");
								System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								System.out.printf("------------------------------------------------------------------------------%n");
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
									System.out.print(output.toString());
									System.out.printf("------------------------------------------------------------------------------%n");
									output.setLength(0);
								}
							}
							
							case "LAST" -> {
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
										"FROM employees WHERE Lname = '%s'", LnameData);
								System.out.printf("--------------------------------------------------------------------------------%n");
								System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								System.out.printf("--------------------------------------------------------------------------------%n");
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
									System.out.print(output.toString());
									System.out.printf("----------------------------------------------------------------------------------------%n");
									output.setLength(0);
								}
							}
							
							case "BOTH" -> {
								System.out.println("\nENTER FIRST NAME:");
								String FnameData = scn.nextLine();
								System.out.println("\nENTER LAST NAME:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
										"FROM employees WHERE Fname = '%s' AND Lname = '%s'", FnameData, LnameData);
								System.out.printf("----------------------------------------------------------------------------------------%n");
								System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								System.out.printf("----------------------------------------------------------------------------------------%n");
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
									System.out.print(output.toString());
									System.out.printf("----------------------------------------------------------------------------------------%n");
									output.setLength(0);
								}
							}
							
						}

						// System.out.println("\nWould you like to edit employee information?");
						// scn = new Scanner(System.in);
						// searchChoice = scn.nextLine().toUpperCase();
						
						// if ("YES".equals(searchChoice.toUpperCase())) {
						// 	editing();
						// }
					}
					
					case "DOB" -> {
						System.out.println("ENTER DOB:");
						String DOBstring = scn.nextLine();
						
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB " +
								"FROM employees WHERE DOB = '%s'", DOBstring);
						System.out.println(sqlcommand);
						System.out.printf("--------------------------------------------------------------------------------------------%n");
						System.out.printf("| %-5s | %-20s | %-20s | %-20s | %-12s| %n", "EmpID", "First Name", "Last Name", "Email", "DOB");
						System.out.printf("--------------------------------------------------------------------------------------------%n");
						
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format("| %-5s | %-20s | %-20s | %-20s |  %-10s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getDate("DOB")));
							System.out.print(output.toString());
							System.out.printf("--------------------------------------------------------------------------------------------%n");
							output.setLength(0);
						}
					}

					case "SSN" -> {
						System.out.println("ENTER SSN:");
						String SSN = scn.nextLine();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE SSN = '%s'", SSN);
						System.out.printf("---------------------------------------------------------------------------------------------%n");
						System.out.printf("| %-5s | %-20s | %-20s | %-20s | %-13s| %n", "EmpID", "First Name", "Last Name", "Email", "SSN");
						System.out.printf("---------------------------------------------------------------------------------------------%n");
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %-12s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"),  myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.printf("---------------------------------------------------------------------------------------------%n");
							output.setLength(0);
						}
					}

					case "EMPID" -> {
						System.out.println("ENTER EMPID:");
						int EmpID = scn.nextInt();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE empid = %d", EmpID);
						System.out.printf("------------------------------------------------------------------------------%n");
						System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
						System.out.printf("------------------------------------------------------------------------------%n");
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
							System.out.print(output.toString());
							System.out.printf("------------------------------------------------------------------------------%n");
							output.setLength(0);
						}
					}
				}
				editing();

				scn.close();
				scn = new Scanner(System.in);
				searchChoice = scn.nextLine().toUpperCase();
				System.out.println("\nEMPLOYEE SEARCH OPTIONS\n-----------------------\n- Name\n- DOB\n- SSN\n- EmpID\n\nENTER COMMAND: ");		
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

		String url = "jdbc:mysql://localhost:3306/employeeData2"; //remember to add jar connection
		String user = "root";
		String password = "pass";
		StringBuilder output = new StringBuilder("");
		try (Connection myConn = DriverManager.getConnection(url, user, password)) {
			Statement myStmt = myConn.createStatement();

			while (!"Q".equals(searchChoice)) {
				switch (searchChoice) {
					case "NAME" -> {
						System.out.println("\nEMPLOYEE SEARCH OPTIONS: NAME\n-----------------------\n- To search for first name, enter 'FIRST'.\n- To search for last name, enter 'LAST'. \n- To search for both first and last name, enter 'BOTH'.\n\nENTER COMMAND:");
						scn = new Scanner(System.in);
						searchChoice = scn.nextLine().toUpperCase();
						switch (searchChoice) {
							case "FIRST" -> {
								System.out.println("\nEnter first name:");
								String FnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
										"FROM employees WHERE Fname = '%s'", FnameData);
								
								System.out.printf("------------------------------------------------------------------------------%n");
								System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								System.out.printf("------------------------------------------------------------------------------%n");
								
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
									System.out.print(output.toString());
									System.out.printf("------------------------------------------------------------------------------%n");
									output.setLength(0);
								}
							}
													
							case "LAST" -> {
								System.out.println("\nEnter last name:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
									"FROM employees WHERE Lname = '%s'", LnameData);
								System.out.printf("--------------------------------------------------------------------------------%n");
								System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								System.out.printf("--------------------------------------------------------------------------------%n");
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
									System.out.print(output.toString());
									System.out.printf("----------------------------------------------------------------------------------------%n");
									output.setLength(0);
									}
							}
													
							case "BOTH" -> {
								System.out.println("\nEnter first name:");
								String FnameData = scn.nextLine();
								System.out.println("\nEnter last name:");
								String LnameData = scn.nextLine();
								String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
										"FROM employees WHERE Fname = '%s' AND Lname = '%s'", FnameData, LnameData);
								System.out.printf("----------------------------------------------------------------------------------------%n");
								System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								System.out.printf("----------------------------------------------------------------------------------------%n");
								ResultSet myRS = myStmt.executeQuery(sqlcommand);
								while (myRS.next()) {
									output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
									System.out.print(output.toString());
									System.out.printf("----------------------------------------------------------------------------------------%n");
									output.setLength(0);
									}
							}
													
						}
					}

					case "DOB" -> {
						System.out.println("Enter DOB:");
						String DOBstring = scn.nextLine();
						
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB " +
								"FROM employees WHERE DOB = '%s'", DOBstring);
						System.out.println(sqlcommand);
						System.out.printf("--------------------------------------------------------------------------------------------%n");
						System.out.printf("| %-5s | %-20s | %-20s | %-20s | %-12s| %n", "EmpID", "First Name", "Last Name", "Email", "DOB");
						System.out.printf("--------------------------------------------------------------------------------------------%n");
						
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format("| %-5s | %-20s | %-20s | %-20s |  %-10s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getDate("DOB")));
							System.out.print(output.toString());
							System.out.printf("--------------------------------------------------------------------------------------------%n");
							output.setLength(0);
						}
					}

					case "SSN" -> {
						System.out.println("Enter SSN:");
						String SSN = scn.nextLine();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE SSN = '%s'", SSN);
						System.out.printf("---------------------------------------------------------------------------------------------%n");
						System.out.printf("| %-5s | %-20s | %-20s | %-20s | %-13s| %n", "EmpID", "First Name", "Last Name", "Email", "SSN");
						System.out.printf("---------------------------------------------------------------------------------------------%n");
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %-12s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"),  myRS.getString("SSN")));
							System.out.print(output.toString());
							System.out.printf("---------------------------------------------------------------------------------------------%n");
							output.setLength(0);
						}
					}

					case "EMPID" -> {
						System.out.println("Enter EmpID:");
						int EmpID = scn.nextInt();
						String sqlcommand = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE empid = %d", EmpID);
						System.out.printf("------------------------------------------------------------------------------%n");
						System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
						System.out.printf("------------------------------------------------------------------------------%n");
						ResultSet myRS = myStmt.executeQuery(sqlcommand);
						while (myRS.next()) {
							output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
							System.out.print(output.toString());
							System.out.printf("------------------------------------------------------------------------------%n");
							output.setLength(0);
						}
					}

				}

				scn.close();
				scn = new Scanner(System.in);
				searchChoice = scn.nextLine().toUpperCase();
				System.out.println("\nEMPLOYEE SEARCH OPTIONS\n-----------------------\n- Name\n- DOB\n- SSN\n- EmpID\n\nENTER COMMAND: ");	
			}
			
		
		myConn.close();
		} catch (Exception e) {
			// System.out.println("ERROR " + e.getLocalizedMessage());
		} finally {
		}
	}

	public static void editing() throws SQLException {
		System.out.println("\nENTER THE EMPID OF THE EMPLOYEE YOU'D LIKE TO EDIT: ");
		Scanner scn = new Scanner(System.in);
		int EmpID = scn.nextInt();

		String url = "jdbc:mysql://localhost:3306/employeeData2"; //remember to add jar connection
		String user = "root";
		String password = "pass";
		StringBuilder output = new StringBuilder("");
		try (Connection myConn = DriverManager.getConnection(url, user, password)) {
		 	Statement myStmt = myConn.createStatement();
			
		
						
			// String sqlcommand = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE empid = %d", EmpID);
			// System.out.printf("------------------------------------------------------------------------------%n");
			// System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
			// System.out.printf("------------------------------------------------------------------------------%n");
			// ResultSet myRS = myStmt.executeQuery(sqlcommand);
			// while (myRS.next()) {
			// 	output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
			// 	System.out.print(output.toString());
			// 	System.out.printf("------------------------------------------------------------------------------%n");
			// 	output.setLength(0);
			// }

			System.out.println("\nEDIT OPTIONS\n-----------------------\n- Name\n- DOB\n- SSN\n- EmpID\n\nENTER COMMAND: ");		
			scn = new Scanner(System.in);
			String editChoice = scn.nextLine();

			switch (editChoice.toUpperCase()) {
				case "NAME" -> {
					System.out.println("\nEMPLOYEE EDITING OPTIONS: NAME\n-----------------------\n- To edit first name, enter 'FIRST'.\n- To edit last name, enter 'LAST'. \n- To edit both first and last name, enter 'BOTH'.\n\nENTER COMMAND:");
					scn = new Scanner(System.in);
					editChoice = scn.nextLine().toUpperCase();
					switch (editChoice) {
						case "FIRST" -> {
							System.out.println("\nENTER NEW VALUE - FIRST NAME:");
							String FnameData = scn.nextLine();
							
							String sqlcommand1 = String.format("UPDATE employees " + "SET Fname = '%s'" + " WHERE empid = %d", FnameData, EmpID);
							String sqlcommand2 = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE empid = %d", EmpID);
							System.out.printf("\nNEW DATA ENTRY FOR EMPLOYEE %d:", EmpID);
							System.out.printf("\n------------------------------------------------------------------------------%n");
							System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
							System.out.printf("------------------------------------------------------------------------------%n");
							
							myStmt.execute(sqlcommand1); // using execute instead of executeQuery because we just want it to do the command, NOT return anything
							// As shown here: https://stackoverflow.com/questions/48690990/how-to-execute-a-sql-statement-in-java-with-variables
							
							ResultSet myRS = myStmt.executeQuery(sqlcommand2);
							while (myRS.next()) {
								output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
								System.out.print(output.toString());
								System.out.printf("------------------------------------------------------------------------------%n");
								output.setLength(0);
							}
						}

						case "LAST" -> {
							System.out.println("\nENTER NEW VALUE - LAST NAME:");
							String LnameData = scn.nextLine();
							
							String sqlcommand1 = String.format("UPDATE employees " + "SET Lname = '%s'" + " WHERE empid = %d", LnameData, EmpID);
							String sqlcommand2 = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE empid = %d", EmpID);
							System.out.printf("\nNEW DATA ENTRY FOR EMPLOYEE %d:", EmpID);
							System.out.printf("\n------------------------------------------------------------------------------%n");
							System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
							System.out.printf("------------------------------------------------------------------------------%n");
							
							myStmt.execute(sqlcommand1); // using execute instead of executeQuery because we just want it to do the command, NOT return anything
							// As shown here: https://stackoverflow.com/questions/48690990/how-to-execute-a-sql-statement-in-java-with-variables
							
							ResultSet myRS = myStmt.executeQuery(sqlcommand2);
							while (myRS.next()) {
								output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
								System.out.print(output.toString());
								System.out.printf("------------------------------------------------------------------------------%n");
								output.setLength(0);
							}
						}
													
							// case "LAST" -> {
							// 	System.out.println("\nEnter last name:");
							// 	String LnameData = scn.nextLine();
								// String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
								// 	"FROM employees WHERE Lname = '%s'", LnameData);
								// System.out.printf("--------------------------------------------------------------------------------%n");
								// System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
								// System.out.printf("--------------------------------------------------------------------------------%n");
								// ResultSet myRS = myStmt.executeQuery(sqlcommand);
								// while (myRS.next()) {
								// 	output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
								// 	System.out.print(output.toString());
								// 	System.out.printf("----------------------------------------------------------------------------------------%n");
								// 	output.setLength(0);
								// 	}
							}
													
							// case "BOTH" -> {
							// 	System.out.println("\nEnter first name:");
							// 	String FnameData = scn.nextLine();
							// 	System.out.println("\nEnter last name:");
							// 	String LnameData = scn.nextLine();
							// 	String sqlcommand = String.format("SELECT Fname, Lname, email, empid " +
							// 			"FROM employees WHERE Fname = '%s' AND Lname = '%s'", FnameData, LnameData);
							// 	System.out.printf("----------------------------------------------------------------------------------------%n");
							// 	System.out.printf("| %-5s | %-20s | %-20s | %-20s | %n", "EmpID", "First Name", "Last Name", "Email");
							// 	System.out.printf("----------------------------------------------------------------------------------------%n");
							// 	ResultSet myRS = myStmt.executeQuery(sqlcommand);
							// 	while (myRS.next()) {
							// 		output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email")));
							// 		System.out.print(output.toString());
							// 		System.out.printf("----------------------------------------------------------------------------------------%n");
							// 		output.setLength(0);
							// 		}
							// }
													
						}
					}

					// case "DOB" -> {
					// 	System.out.println("Enter DOB:");
					// 	String DOBstring = scn.nextLine();
						
					// 	String sqlcommand = String.format("SELECT Fname, Lname, email, empid, DOB " +
					// 			"FROM employees WHERE DOB = '%s'", DOBstring);
					// 	System.out.println(sqlcommand);
					// 	System.out.printf("--------------------------------------------------------------------------------------------%n");
					// 	System.out.printf("| %-5s | %-20s | %-20s | %-20s | %-12s| %n", "EmpID", "First Name", "Last Name", "Email", "DOB");
					// 	System.out.printf("--------------------------------------------------------------------------------------------%n");
						
					// 	ResultSet myRS = myStmt.executeQuery(sqlcommand);
					// 	while (myRS.next()) {
					// 		output.append(String.format("| %-5s | %-20s | %-20s | %-20s |  %-10s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"), myRS.getDate("DOB")));
					// 		System.out.print(output.toString());
					// 		System.out.printf("--------------------------------------------------------------------------------------------%n");
					// 		output.setLength(0);
					// 	}
					// }

					// case "SSN" -> {
					// 	System.out.println("Enter SSN:");
					// 	String SSN = scn.nextLine();
					// 	String sqlcommand = String.format("SELECT Fname, Lname, email, empid, SSN" + " FROM employees" + " WHERE SSN = '%s'", SSN);
					// 	System.out.printf("---------------------------------------------------------------------------------------------%n");
					// 	System.out.printf("| %-5s | %-20s | %-20s | %-20s | %-13s| %n", "EmpID", "First Name", "Last Name", "Email", "SSN");
					// 	System.out.printf("---------------------------------------------------------------------------------------------%n");
					// 	ResultSet myRS = myStmt.executeQuery(sqlcommand);
					// 	while (myRS.next()) {
					// 		output.append(String.format("| %-5s | %-20s | %-20s | %-20s | %-12s | %n", myRS.getInt("EmpID"), myRS.getString("Fname"),  myRS.getString("Lname"),  myRS.getString("email"),  myRS.getString("SSN")));
					// 		System.out.print(output.toString());
					// 		System.out.printf("---------------------------------------------------------------------------------------------%n");
					// 		output.setLength(0);
					// 	}
					// }

					// case "EMPID" -> {
			}
			

			
		// scn.close();
		// 		scn = new Scanner(System.in);
		// 		searchChoice = scn.nextLine().toUpperCase();
		// 		System.out.println("Search for an employee...");
		// 		System.out.println("- - - Search via name, DOB, SSN, or empid.");
		// 	}
			
		
	}

	


}

