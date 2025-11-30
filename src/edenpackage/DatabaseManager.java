package edenpackage;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/employeeData2";
    private static final String USER = "root";
    private static final String PASSWORD = "pass";
    
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DatabaseManager() {}
    
    /**
     * Get a database connection (creates if doesn't exist)
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established.");
            } catch (SQLException e) {
                System.err.println("Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }
    
    /**
     * Execute a SELECT query with parameters (prevents SQL injection)
     * @param sql SQL query with ? placeholders
     * @param params Parameters to replace ? placeholders
     * @return ResultSet with query results
     */
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Set parameters
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeQuery();
    }
    
    /**
     * Execute an UPDATE, INSERT, or DELETE statement
     * @param sql SQL statement with ? placeholders
     * @param params Parameters to replace ? placeholders
     * @return Number of rows affected
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameters
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) affected.");
            return rowsAffected;
            
        } catch (SQLException e) {
            System.err.println("Update failed: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Execute an UPDATE and return the updated record
     * @param updateSql UPDATE statement with ? placeholders
     * @param selectSql SELECT statement to fetch updated record
     * @param updateParams Parameters for UPDATE
     * @param selectParams Parameters for SELECT
     * @return ResultSet with updated record
     */
    public static ResultSet executeUpdateAndFetch(String updateSql, String selectSql, 
                                                   Object[] updateParams, Object[] selectParams) 
                                                   throws SQLException {
        executeUpdate(updateSql, updateParams);
        return executeQuery(selectSql, selectParams);
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Check if a record exists
     * @param sql SQL query with ? placeholders
     * @param params Parameters to replace ? placeholders
     * @return true if record exists, false otherwise
     */
    public static boolean recordExists(String sql, Object... params) throws SQLException {
        ResultSet rs = executeQuery(sql, params);
        return rs.next();
    }
}