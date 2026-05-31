package com.upskill.javafse.oop;

public class Manager extends Employee {

    private final double bonusPercentage;

    public Manager(int id, String name, String department,
                   double baseSalary, double bonusPercentage) {
        super(id, name, department, baseSalary);
        this.bonusPercentage = bonusPercentage;
    }

    public double getBonusPercentage() {
        return bonusPercentage;
    }

    @Override
    public double calculatePay() {
        return getBaseSalary() + (getBaseSalary() * bonusPercentage / 100);
    }

    @Override
    public String toString() {
        return String.format("Manager[id=%d, name=%s, dept=%s, pay=%.2f (bonus %.1f%%)]",
                getId(), getName(), getDepartment(), calculatePay(), bonusPercentage);
    }
}
