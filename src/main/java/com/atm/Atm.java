
package com.atm;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Prem Prakash Sharma
 */

public class Atm {
    
    private Map<Integer, User> users;
    private static final String USERS_DATA = "users.json";

    public void ATM() {
        this.users = new HashMap<>();
    }

    public void addUser(int userID, User user) {
        users.put(userID, user);
    }

    public void saveUserData() {
        try (FileWriter writer = new FileWriter(USERS_DATA)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadUserData() {
        try (FileReader reader = new FileReader(USERS_DATA)) {
            Gson gson = new Gson();
            users = gson.fromJson(reader, new TypeToken<Map<Integer, User>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     public void registerUser() {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter userID: ");
            int userID = sc.nextInt();

            if (users.containsKey(userID)) {
                System.out.println("User with this userID already exists.");
                return;
            }

            System.out.print("Enter name: ");
            sc.nextLine(); // Consume the newline character
            String name = sc.nextLine();

            System.out.print("Set ATM PIN: ");
            int pin = sc.nextInt();

            System.out.print("Enter initial balance: ");
            double balance = sc.nextDouble();

            User newUser = new User(userID, name, pin, balance);
            addUser(userID, newUser);
            saveUserData();

            System.out.println("User registered successfully.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Registration failed.");
        }
    }
    
     public User loginUser(int userID, int pin) {
        User user = users.get(userID);
        if (user != null && user.verifyPin(pin)) {
            return user;
        }
        return null;
    }
    
      public void run() {
        Scanner sc = new Scanner(System.in);

        loadUserData();

        while (true) {
            System.out.println("1. Log In");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter userID: ");
                    int userID = sc.nextInt();
                    System.out.print("Enter ATM PIN: ");
                    int pin = sc.nextInt();

                    User user = loginUser(userID, pin);
                    if (user != null) {
                        System.out.println("Welcome " + user.getName());
                        userOperations(user);
                    } else {
                        System.out.println("Invalid userID or PIN.");
                    }
                    break;
                case 2:
                    registerUser();
                    continue;
                case 3:
                    System.out.println("Exiting...");
                    saveUserData();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    
     public void userOperations(User user) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Log Out");

            System.out.print("Enter choice: ");
            int choice;
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                sc.nextLine(); // Consume the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    user.displayBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double amount = sc.nextDouble();
                    user.deposit(amount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    amount = sc.nextDouble();
                    user.withdraw(amount);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    saveUserData();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        Atm atm = new Atm();
        atm.run();
    }
}
