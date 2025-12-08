package com.companyz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String URL = "jdbc:mysql://localhost:3306/employeedata";
	private static final String USER = "root";
	private static final String PASSWORD = "password123";

	private static Connection connection = null;

	private DatabaseManager() {}

	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			return connection;
		} catch (SQLException e) {
			System.err.println("Could not connect: " + e.getMessage());
			return null;
		}
	}

	public static ResultSet executeQuery(String sql) {
		return executeQuery(sql, new Object[0]);
	}

	public static ResultSet executeQuery(String sql, Object[] params) {
		Connection conn = getConnection();
		if (conn == null) return null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			return pstmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("Query failed: " + e.getMessage());
			return null;
		}
	}

	public static int executeUpdate(String sql) {
		return executeUpdate(sql, new Object[0]);
	}

	public static int executeUpdate(String sql, Object[] params) {
		Connection conn = getConnection();
		if (conn == null) return 0;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			int rowsAffected = pstmt.executeUpdate();
			System.out.println(rowsAffected + " row(s) affected.");
			return rowsAffected;
		} catch (SQLException e) {
			System.err.println("Update failed: " + e.getMessage());
			return 0;
		}
	}

	public static ResultSet executeUpdateAndFetch(String updateSql, String selectSql,
												  Object[] updateParams, Object[] selectParams) {
		executeUpdate(updateSql, updateParams);
		return executeQuery(selectSql, selectParams);
	}

	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Thank you for using the terminal.");
			} catch (SQLException e) {
				System.err.println("Error closing connection: " + e.getMessage());
			}
		}
	}

	public static boolean recordExists(String sql, Object[] params) {
		ResultSet rs = executeQuery(sql, params);
		try {
			return rs != null && rs.next();
		} catch (SQLException e) {
			System.err.println("Check failed: " + e.getMessage());
			return false;
		}
	}
}
