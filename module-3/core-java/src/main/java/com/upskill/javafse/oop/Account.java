package com.upskill.javafse.oop;

/**
 * Abstract base class representing a bank account.
 * Subclasses must provide their own withdrawal logic based on account type.
 */
public abstract class Account {

    private final String accountNumber;
    private final String holderName;
    protected double balance;

    public Account(String accountNumber, String holderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Withdraws the specified amount from this account.
     * Each account type enforces its own rules (minimum balance, overdraft, etc.).
     *
     * @param amount the amount to withdraw
     */
    public abstract void withdraw(double amount);

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        System.out.printf("Deposited %.2f to account %s. New balance: %.2f%n",
                amount, accountNumber, balance);
    }

    public void displayBalance() {
        System.out.printf("Account: %s | Holder: %s | Balance: %.2f%n",
                accountNumber, holderName, balance);
    }
}
