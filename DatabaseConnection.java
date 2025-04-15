/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CourseObjective;

/**
 *
 * @author DELL
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:derby://localhost:1527/OBE_System"; // Update with your DB URL
    private static final String USER = "APP"; // Default Derby username
    private static final String PASSWORD = " "; // Default password, adjust if needed

    public static Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established.");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }
}
