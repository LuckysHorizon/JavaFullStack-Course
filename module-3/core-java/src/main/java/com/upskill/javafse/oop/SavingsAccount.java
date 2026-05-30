package com.upskill.javafse.oop;

public class SavingsAccount extends Account {

    private static final double MINIMUM_BALANCE = 1000.0;
    private final double interestRate;

    public SavingsAccount(String accountNumber, String holderName,
                          double initialBalance, double interestRate) {
        super(accountNumber, holderName, initialBalance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (balance - amount < MINIMUM_BALANCE) {
            System.out.printf("Cannot withdraw %.2f  minimum balance of %.2f must be maintained. "
                    + "Current balance: %.2f%n", amount, MINIMUM_BALANCE, balance);
            return;
        }
        balance -= amount;
        System.out.printf("Withdrew %.2f from savings account %s. Remaining balance: %.2f%n",
                amount, getAccountNumber(), balance);
    }

    public double calculateInterest() {
        double interest = balance * interestRate / 100;
        System.out.printf("Interest earned on account %s: %.2f%n", getAccountNumber(), interest);
        return interest;
    }
}
