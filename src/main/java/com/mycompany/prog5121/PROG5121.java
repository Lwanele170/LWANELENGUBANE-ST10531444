/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121;
 
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PROG5121 {

    // Initialized to false so '!exit' runs the loop
    private static boolean exit = false;
    private static int maxMessages = 0;
    private static int Total_messages = 0;
    private static int messageCounter = 0;
    static final JSONArray messageStorage = new JSONArray();

    static Scanner scanner = new Scanner(System.in);

    // Hardcoded mock credentials to simulate the required login function shown in your image
    private static final String storedUsername = "admin_";
    private static final String storedPassword = "Password1!";

    // VALIDATION & AUTHENTICATION UTILITIES
    
    public static boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public static boolean loginUser(String username, String password,
                                  String storedUsername, String storedPassword) {
        return username != null && username.equals(storedUsername)
                && password != null && password.equals(storedPassword);
    }

    public static String returnLoginStatus(boolean status, String username) {
        if (status) {
            return "Login successful! Welcome back " + username + "!";
        } else {
            return "Username or password incorrect.";
        }
    }

    // Handles the login sequence shown at the start of your main method
    private static boolean login() {
        System.out.print("Enter your username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Enter your password: ");
        String loginPass = scanner.nextLine();

        boolean status = loginUser(loginUser, loginPass, storedUsername, storedPassword);
        System.out.println(returnLoginStatus(status, loginUser));
        
        return status;
    }

    private static String CheckRecipient(String recipient) {
        if (recipient == null || !recipient.matches("^\\+\\d{9,12}$")) {
            System.out.println("Invalid number. Must include country code and be <=12 digits.");
            return null;
        }
        return recipient;
    }

    
    // CORE FUNCTIONALITIES
    
    static void sendMessage() {
        long messageId = 1000000000L + new Random().nextInt(900000000);

        System.out.print("\nEnter recipient number (+CCxxxxxxxxx): ");
        String recipient = scanner.nextLine();
        recipient = CheckRecipient(recipient);

        if (recipient == null) {
            return;
        }

        System.out.print("Enter your message (max 250 characters): ");
        String message = scanner.nextLine();

        if (message.trim().isEmpty()) {
            System.out.println("Message cannot be empty.");
            return;
        }

        if (message.length() > 250) {
            System.out.println("Message exceeds 250 characters.");
            return;
        }

        String hash = messageId + ":" + 
                message.substring(0, Math.min(2, message.length())).toUpperCase();

        int action = 0;
        while (true) {
            System.out.println("\nChoose option:");
            System.out.println("1. Send Message");
            System.out.println("2. Cancel Message");
            System.out.println("3. Store Message");
            System.out.print("Selection: ");
            try {
                action = Integer.parseInt(scanner.nextLine());
                if (action >= 1 && action <= 3) break;
                System.out.println("Please enter a number between 1 and 3.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        if (action == 2) {
            System.out.println("Message Cancelled");
            return;
        }

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("MessageID", messageId);
        jsonMessage.put("MessageHash", hash);
        jsonMessage.put("Recipient", recipient);
        jsonMessage.put("Message", message);

        if (action == 3) {
            messageStorage.add(jsonMessage);
            System.out.println("Message stored.");
            return;
        }

        // Increment tracking variables upon sending
        Total_messages++;
        messageCounter++;

        System.out.println("\nMessage Sent!");
        System.out.println("Message ID: " + messageId);
        System.out.println("Message Hash: " + hash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + message);
    }

    static void saveMessagesToJSON() {
        try (FileWriter file = new FileWriter("storedMessages.json")) {
            file.write(messageStorage.toJSONString());
            file.flush();
            System.out.println("Stored messages saved to storedMessages.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void showRecentlySentMessages() {
        if (messageStorage.isEmpty()) {
            System.out.println("No stored messages.");
        } else {
            System.out.println("\nStored Messages:");
            for (Object obj : messageStorage) {
                System.out.println(obj);
            }
        }
    }

    
    // MAIN ENTRY POINT
   
    public static void main(String[] args) {
        System.out.println("Welcome to ChatIT");

        if (!login()) {
            return;
        }

        try {
            System.out.print("How many messages do you wish to send? ");
            maxMessages = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, exiting programme");
            return;
        }

        while (!exit) {
            System.out.println("\nSelect an Option:");
            System.out.println("1. Post Message");
            System.out.println("2. Previous Messages");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please select a valid number option.");
                continue;
            }

            switch (choice) {
                case 1:
                    if (Total_messages < maxMessages) {
                        sendMessage();
                    } else {
                        System.out.println("Maximum Message Reached. You may not send more");
                    }
                    break;
                case 2:
                    showRecentlySentMessages();
                    break;
                case 3:
                    // Auto-saves your arrays into a file structure upon application shutdown
                    saveMessagesToJSON();
                    System.out.println("Exiting application. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}