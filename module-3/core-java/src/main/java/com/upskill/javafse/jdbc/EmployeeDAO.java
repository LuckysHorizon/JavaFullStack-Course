package com.upskill.javafse.jdbc;

import java.util.List;

/**
 * Data Access Object interface for Employee CRUD operations.
 */
public interface EmployeeDAO {

    /**
     * Inserts a new employee into the database.
     *
     * @param employee the employee to add
     * @return the number of rows affected, or the generated id
     */
    int addEmployee(EmployeeModel employee);

    /**
     * Retrieves an employee by their unique id.
     *
     * @param id the employee id
     * @return the matching EmployeeModel, or null if not found
     */
    EmployeeModel getEmployeeById(int id);

    /**
     * Retrieves all employees from the database.
     *
     * @return a list of all employees
     */
    List<EmployeeModel> getAllEmployees();

    /**
     * Updates an existing employee's details.
     *
     * @param employee the employee with updated fields (matched by id)
     */
    void updateEmployee(EmployeeModel employee);

    /**
     * Deletes an employee by their id.
     *
     * @param id the id of the employee to delete
     */
    void deleteEmployee(int id);
}
