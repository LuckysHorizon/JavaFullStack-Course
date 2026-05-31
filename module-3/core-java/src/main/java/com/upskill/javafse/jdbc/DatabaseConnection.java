package com.upskill.javafse.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for obtaining database connections.
 * Update URL, USER, and PASSWORD to match your local MySQL setup.
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/company_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private DatabaseConnection() {
        // prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected to database successfully!");
            System.out.println("Database: " + connection.getCatalog());
            System.out.println("Auto-commit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            System.out.println("Failed to connect: " + e.getMessage());
        }
    }
}
