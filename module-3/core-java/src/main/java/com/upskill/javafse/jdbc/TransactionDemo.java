package com.upskill.javafse.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TransactionDemo {

    private static final String DEDUCT_SALARY_SQL =
            "UPDATE employees SET salary = salary - ? WHERE id = ?";
    private static final String ADD_SALARY_SQL =
            "UPDATE employees SET salary = salary + ? WHERE id = ?";

    public static void transferSalary(int fromId, int toId, double amount) {
        Connection connection = null;
        Savepoint savepoint = null;

        try {
            connection = DatabaseConnection.getConnection();
            connection.setAutoCommit(false);

            // Step 1: Deduct from source employee
            try (PreparedStatement deductStmt = connection.prepareStatement(DEDUCT_SALARY_SQL)) {
                deductStmt.setDouble(1, amount);
                deductStmt.setInt(2, fromId);
                int rowsAffected = deductStmt.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Source employee not found (id=" + fromId + ")");
                }
                System.out.println("Deducted " + amount + " from employee " + fromId);
            }

            // Create a savepoint after the deduction
            savepoint = connection.setSavepoint("AfterDeduction");
            System.out.println("Savepoint 'AfterDeduction' created.");

            // Step 2: Add to target employee
            try (PreparedStatement addStmt = connection.prepareStatement(ADD_SALARY_SQL)) {
                addStmt.setDouble(1, amount);
                addStmt.setInt(2, toId);
                int rowsAffected = addStmt.executeUpdate();
                if (rowsAffected == 0) {
                    // Target not found  rollback to savepoint so deduction is also undone
                    System.out.println("Target employee not found. Rolling back to savepoint...");
                    connection.rollback(savepoint);
                    connection.commit();
                    System.out.println("Rolled back to savepoint. Deduction reversed.");
                    return;
                }
                System.out.println("Added " + amount + " to employee " + toId);
            }

            connection.commit();
            System.out.println("Transaction committed successfully. Transfer complete.");

        } catch (SQLException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAOImpl();

        // Set up two employees for the demo
        int id1 = dao.addEmployee(new EmployeeModel(0, "Bob Smith", "Finance", 70000.0));
        int id2 = dao.addEmployee(new EmployeeModel(0, "Carol White", "Finance", 55000.0));

        System.out.println("Before transfer:");
        System.out.println("  " + dao.getEmployeeById(id1));
        System.out.println("  " + dao.getEmployeeById(id2));

        System.out.println("\n--- Transferring 5000.0 from Bob to Carol ---");
        transferSalary(id1, id2, 5000.0);

        System.out.println("\nAfter transfer:");
        System.out.println("  " + dao.getEmployeeById(id1));
        System.out.println("  " + dao.getEmployeeById(id2));

        System.out.println("\n--- Attempting transfer to non-existent employee ---");
        transferSalary(id1, 9999, 3000.0);

        System.out.println("\nAfter failed transfer (should be unchanged):");
        System.out.println("  " + dao.getEmployeeById(id1));

        // Clean up
        dao.deleteEmployee(id1);
        dao.deleteEmployee(id2);
        System.out.println("\nDemo employees cleaned up.");
    }
}
