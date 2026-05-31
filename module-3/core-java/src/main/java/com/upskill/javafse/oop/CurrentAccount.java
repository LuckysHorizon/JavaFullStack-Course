package com.upskill.javafse.oop;

public class CurrentAccount extends Account {

    private final double overdraftLimit;

    public CurrentAccount(String accountNumber, String holderName,
                          double initialBalance, double overdraftLimit) {
        super(accountNumber, holderName, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (balance - amount < -overdraftLimit) {
            System.out.printf("Cannot withdraw %.2f  exceeds overdraft limit of %.2f. "
                    + "Current balance: %.2f%n", amount, overdraftLimit, balance);
            return;
        }
        balance -= amount;
        System.out.printf("Withdrew %.2f from current account %s. Remaining balance: %.2f%n",
                amount, getAccountNumber(), balance);
    }
}
