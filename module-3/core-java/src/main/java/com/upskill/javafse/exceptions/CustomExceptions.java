package com.upskill.javafse.exceptions;

public class CustomExceptions {

    public static class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    public static class InvalidAgeException extends RuntimeException {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Custom Exception Classes ===");
        System.out.println("InsufficientBalanceException: checked exception (extends Exception)");
        System.out.println("InvalidAgeException: unchecked exception (extends RuntimeException)");

        try {
            throw new InsufficientBalanceException("Balance too low for this withdrawal");
        } catch (InsufficientBalanceException e) {
            System.out.println("\nCaught checked exception: " + e.getMessage());
        }

        try {
            throw new InvalidAgeException("Age cannot be negative");
        } catch (InvalidAgeException e) {
            System.out.println("Caught unchecked exception: " + e.getMessage());
        }
    }
}
