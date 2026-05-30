package com.upskill.javafse.oop;

import java.util.List;

public class EmployeeDemo {

    public static void main(String[] args) {
        System.out.println("=== Employee Management System Demo ===\n");

        var mgr1 = new Manager(101, "Sarah Connor", "Engineering", 90000, 15);
        var mgr2 = new Manager(102, "James Kirk", "Marketing", 85000, 12);
        var dev1 = new Developer(201, "Linus Torvalds", "Engineering", 75000, 20, 50);
        var dev2 = new Developer(202, "Ada Lovelace", "Engineering", 78000, 15, 55);
        var dev3 = new Developer(203, "Grace Hopper", "Engineering", 72000, 10, 45);

        // Polymorphism  all treated as Employee references
        List<Employee> allEmployees = List.of(mgr1, mgr2, dev1, dev2, dev3);

        System.out.println("--- All Employees ---");
        for (Employee emp : allEmployees) {
            System.out.printf("  %s -> Total Pay: %.2f%n", emp.getName(), emp.calculatePay());
        }

        // Department operations
        System.out.println("\n--- Department Setup ---");
        var engineering = new Department("Engineering");
        engineering.addEmployee(mgr1);
        engineering.addEmployee(dev1);
        engineering.addEmployee(dev2);
        engineering.addEmployee(dev3);

        var marketing = new Department("Marketing");
        marketing.addEmployee(mgr2);

        System.out.println("\n--- Department Summaries ---");
        System.out.println(engineering);
        System.out.println(marketing);

        System.out.println("\n--- Highest Paid in Engineering ---");
        engineering.getHighestPaid()
                .ifPresent(emp -> System.out.printf("  %s with pay %.2f%n",
                        emp.getName(), emp.calculatePay()));

        System.out.printf("%n--- Total Payroll (Engineering): %.2f%n", engineering.getTotalPayroll());
        System.out.printf("--- Total Payroll (Marketing):    %.2f%n", marketing.getTotalPayroll());

        // Remove an employee and recalculate
        System.out.println("\n--- Removing Grace Hopper ---");
        engineering.removeEmployee(dev3);
        System.out.printf("Updated Engineering Payroll: %.2f%n", engineering.getTotalPayroll());
    }
}
