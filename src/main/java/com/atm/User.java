package com.atm;

/**
 *
 * @author Prem Prakash Sharma
 */

public class User {
    private int userID;
    private int pin;
    private double balance;
    private String name;

    public User(int userID, String name, int pin, double balance) {
        this.userID = userID;
        this.pin = pin;
        this.balance = balance;
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void displayBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public void deposit(double amount) {
        if (amount > 0) { // Make sure the deposited amount is positive
            balance += amount;
            System.out.println("Deposited $" + amount);
            displayBalance();
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) { // Make sure the withdrawn amount is positive
            if (amount > balance) {
                System.out.println("Insufficient funds");
            } else {
                balance -= amount;
                System.out.println("Withdrew $" + amount);
                displayBalance();
            }
        } else {
            System.out.println("Invalid withdrawal amount");
        }
    }

    public boolean verifyPin(int pin) {
        return this.pin == pin;
    }
}
