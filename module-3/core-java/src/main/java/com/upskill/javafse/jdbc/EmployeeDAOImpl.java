package com.upskill.javafse.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final String INSERT_SQL =
            "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, name, department, salary FROM employees WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, name, department, salary FROM employees";
    private static final String UPDATE_SQL =
            "UPDATE employees SET name = ?, department = ?, salary = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM employees WHERE id = ?";

    @Override
    public int addEmployee(EmployeeModel employee) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDepartment());
            ps.setDouble(3, employee.getSalary());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public EmployeeModel getEmployeeById(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employee: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        List<EmployeeModel> employees = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                employees.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employees: " + e.getMessage());
        }
        return employees;
    }

    @Override
    public void updateEmployee(EmployeeModel employee) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, employee.getName());
            ps.setString(2, employee.getDepartment());
            ps.setDouble(3, employee.getSalary());
            ps.setInt(4, employee.getId());

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    @Override
    public void deleteEmployee(int id) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    private EmployeeModel mapRow(ResultSet rs) throws SQLException {
        return new EmployeeModel(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("department"),
                rs.getDouble("salary")
        );
    }

    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAOImpl();

        // Create
        EmployeeModel emp = new EmployeeModel(0, "Alice Johnson", "Engineering", 85000.0);
        int generatedId = dao.addEmployee(emp);
        System.out.println("Inserted employee with id: " + generatedId);

        // Read
        EmployeeModel fetched = dao.getEmployeeById(generatedId);
        System.out.println("Fetched: " + fetched);

        // Update
        if (fetched != null) {
            fetched.setSalary(92000.0);
            dao.updateEmployee(fetched);
            System.out.println("After update: " + dao.getEmployeeById(fetched.getId()));
        }

        // Read all
        List<EmployeeModel> all = dao.getAllEmployees();
        System.out.println("All employees (" + all.size() + "):");
        all.forEach(System.out::println);

        // Delete
        dao.deleteEmployee(generatedId);
        System.out.println("After delete: " + dao.getEmployeeById(generatedId));
    }
}
