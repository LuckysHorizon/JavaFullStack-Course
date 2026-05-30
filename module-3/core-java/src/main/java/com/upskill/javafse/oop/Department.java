package com.upskill.javafse.oop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Department {

    private final String name;
    private final List<Employee> employees;

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return List.copyOf(employees);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        System.out.printf("Added %s to department '%s'%n", employee.getName(), name);
    }

    public boolean removeEmployee(Employee employee) {
        boolean removed = employees.remove(employee);
        if (removed) {
            System.out.printf("Removed %s from department '%s'%n", employee.getName(), name);
        } else {
            System.out.printf("Employee %s not found in department '%s'%n", employee.getName(), name);
        }
        return removed;
    }

    public double getTotalPayroll() {
        return employees.stream()
                .mapToDouble(Employee::calculatePay)
                .sum();
    }

    public Optional<Employee> getHighestPaid() {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::calculatePay));
    }

    @Override
    public String toString() {
        return String.format("Department[name=%s, size=%d, totalPayroll=%.2f]",
                name, employees.size(), getTotalPayroll());
    }
}
