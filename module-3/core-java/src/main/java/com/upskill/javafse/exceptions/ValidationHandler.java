package com.upskill.javafse.exceptions;

import com.upskill.javafse.exceptions.CustomExceptions.InsufficientBalanceException;
import com.upskill.javafse.exceptions.CustomExceptions.InvalidAgeException;

public class ValidationHandler {

    public static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Invalid age: " + age + ". Must be between 0 and 150.");
        }
        System.out.println("Age " + age + " is valid.");
    }

    public static void validateBalance(double balance, double withdrawal) throws InsufficientBalanceException {
        if (withdrawal > balance) {
            throw new InsufficientBalanceException(
                    String.format("Cannot withdraw %.2f from balance of %.2f", withdrawal, balance));
        }
        System.out.println(String.format("Withdrawal of %.2f approved. Remaining balance: %.2f",
                withdrawal, balance - withdrawal));
    }

    public static void main(String[] args) {
        System.out.println("=== Validation Handler Demo ===\n");

        // Valid age
        System.out.println("--- Age Validation ---");
        try {
            validateAge(25);
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Invalid age (unchecked  no compile-time requirement to catch)
        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Boundary age
        try {
            validateAge(200);
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Valid withdrawal
        System.out.println("\n--- Balance Validation ---");
        try {
            validateBalance(1000.00, 500.00);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Insufficient balance (checked  must be caught or declared)
        try {
            validateBalance(200.00, 750.00);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Exact balance withdrawal
        try {
            validateBalance(300.00, 300.00);
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
