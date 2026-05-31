package com.upskill.javafse.oop;

public class BankDemo {

    public static void main(String[] args) {
        System.out.println("=== Banking System Demo ===\n");

        var savings = new SavingsAccount("SAV-1001", "Alice Johnson", 5000.0, 4.5);
        var current = new CurrentAccount("CUR-2001", "Bob Smith", 3000.0, 2000.0);

        // Polymorphism  treating both as Account references
        Account[] accounts = { savings, current };

        System.out.println("--- Initial Balances ---");
        for (Account account : accounts) {
            account.displayBalance();
        }

        System.out.println("\n--- Deposit Operations ---");
        savings.deposit(1500.0);
        current.deposit(500.0);

        System.out.println("\n--- Withdrawal Operations ---");
        savings.withdraw(2000.0);
        savings.withdraw(4000.0);  // should fail  minimum balance

        current.withdraw(3500.0);  // goes into overdraft
        current.withdraw(2500.0);  // should fail  exceeds overdraft limit

        System.out.println("\n--- Interest Calculation ---");
        savings.calculateInterest();

        System.out.println("\n--- Final Balances ---");
        for (Account account : accounts) {
            account.displayBalance();
        }

        // Demonstrate polymorphic withdraw via base reference
        System.out.println("\n--- Polymorphic Withdrawal ---");
        for (Account account : accounts) {
            System.out.printf("Attempting to withdraw 500.00 from %s:%n", account.getAccountNumber());
            account.withdraw(500.0);
        }
    }
}
