package com.upskill.javafse.oop;

/**
 * Abstract base class for all employees in the organization.
 * Subclasses define specific pay structures via {@link #calculatePay()}.
 */
public abstract class Employee {

    private final int id;
    private final String name;
    private final String department;
    private final double baseSalary;

    public Employee(int id, String name, String department, double baseSalary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.baseSalary = baseSalary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    /**
     * Calculates the total pay for this employee.
     *
     * @return the computed total pay including any bonuses or overtime
     */
    public abstract double calculatePay();

    @Override
    public String toString() {
        return String.format("Employee[id=%d, name=%s, dept=%s, baseSalary=%.2f]",
                id, name, department, baseSalary);
    }
}
