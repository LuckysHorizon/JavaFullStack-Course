package com.upskill.javafse.oop;

public class Developer extends Employee {

    private final int overtimeHours;
    private final double hourlyRate;

    public Developer(int id, String name, String department,
                     double baseSalary, int overtimeHours, double hourlyRate) {
        super(id, name, department, baseSalary);
        this.overtimeHours = overtimeHours;
        this.hourlyRate = hourlyRate;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public double calculatePay() {
        return getBaseSalary() + (overtimeHours * hourlyRate);
    }

    @Override
    public String toString() {
        return String.format("Developer[id=%d, name=%s, dept=%s, pay=%.2f (OT: %dh @ %.2f/h)]",
                getId(), getName(), getDepartment(), calculatePay(), overtimeHours, hourlyRate);
    }
}
