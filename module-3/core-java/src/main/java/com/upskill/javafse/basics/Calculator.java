package com.upskill.javafse.basics;

import java.util.Scanner;

public class Calculator {

    public static double add(double a, double b) {
        return a + b;
    }

    public static double subtract(double a, double b) {
        return a - b;
    }

    public static double multiply(double a, double b) {
        return a * b;
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    public static double modulo(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot perform modulo with zero");
        }
        return a % b;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("===== Interactive Calculator =====");

        while (running) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Modulo");
            System.out.println("6. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();

            if (choice == 6) {
                System.out.println("Exiting calculator. Goodbye!");
                running = false;
                continue;
            }

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();
            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();

            try {
                // Switch expression to pick the right operation
                double result = switch (choice) {
                    case 1 -> add(num1, num2);
                    case 2 -> subtract(num1, num2);
                    case 3 -> multiply(num1, num2);
                    case 4 -> divide(num1, num2);
                    case 5 -> modulo(num1, num2);
                    default -> throw new IllegalStateException("Unexpected choice: " + choice);
                };

                String operation = switch (choice) {
                    case 1 -> "+";
                    case 2 -> "-";
                    case 3 -> "*";
                    case 4 -> "/";
                    case 5 -> "%";
                    default -> "?";
                };

                System.out.println("Result: " + num1 + " " + operation + " " + num2 + " = " + result);
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("\nContinue? (y/n): ");
            String continueChoice = scanner.next();
            if (continueChoice.equalsIgnoreCase("n")) {
                System.out.println("Exiting calculator. Goodbye!");
                running = false;
            }
        }

        scanner.close();
    }
}
