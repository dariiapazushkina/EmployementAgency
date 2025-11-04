package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Клас для підключення до бази даних PostgreSQL
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:1234/EmployementAgency";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private static Connection connection;

    // Повертає з'єднання з базою даних
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}