/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog5121;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner; 
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
    private static final String storedUsername = "Kyl_1";
    private static final String storedPassword = "Ch&&seC@ke99!";

    // --- PART 3 PARALLEL ARRAYS & COUNTERS ---
    private static final int MAX_LIMIT = 100; // Adjustable array size ceiling
    
    private static String[] sentMessages = new String[MAX_LIMIT];
    private static String[] disregardedMessages = new String[MAX_LIMIT];
    private static String[] storedMessages = new String[MAX_LIMIT]; 
    private static String[] messageHashes = new String[MAX_LIMIT];
    private static String[] messageIds = new String[MAX_LIMIT];

    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0; // Tracks entries inside parallel arrays

    // VALIDATION & AUTHENTICATION UTILITIES
    
    public static boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }
    
    public static boolean checkCellPhoneNumber(String number) {
        return number != null && number.matches("^\\+27\\d{9}$");
    }

    public static String registerUser(String username, String password) {
        if (checkUserName(username) && checkPasswordComplexity(password)) {
            return "Username and password successfully captured. User registered";
        } else {
            return "Username or password incorrect";
        }
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
    String loginUser = scanner.nextLine().trim(); 

    System.out.print("Enter your password: ");
    String loginPass = scanner.nextLine().trim(); 

    // Run the credential check
    boolean status = loginUser(loginUser, loginPass, storedUsername, storedPassword);
    
    //Print feedback so you know exactly what failed
    System.out.println(returnLoginStatus(status, loginUser));
    if (!status) {
        System.out.println("[System Debug] Hint: Username is case-sensitive. Ensure no extra spaces.");
    }
    
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
            // Populate Disregarded Messages Array
            if (disregardedCount < MAX_LIMIT) {
                disregardedMessages[disregardedCount++] = message;
            }
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
            
            // Populate Stored Messages Array System dynamically from JSON source elements
            if (storedCount < MAX_LIMIT) {
                storedMessages[storedCount] = jsonMessage.get("Message").toString();
                messageIds[storedCount] = jsonMessage.get("MessageID").toString();
                messageHashes[storedCount] = jsonMessage.get("MessageHash").toString();
                storedCount++;
            }
            
            System.out.println("Message stored.");
            return;
        }

        // Increment tracking variables upon sending (Action 1)
        Total_messages++;
        messageCounter++;

        // Populate Sent Messages Array
        if (sentCount < MAX_LIMIT) {
            sentMessages[sentCount++] = message;
        }

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

    // --- NEW METHOD FOR REQUIREMENT 2 (STORED MESSAGES REPORT SUBMENU) ---
    static void handleStoredMessagesMenu() {
        System.out.println("\n--- Stored Messages Sub-Menu ---");
        System.out.println("a. Display the sender and recipient of all stored messages.");
        System.out.println("b. Display the longest stored message.");
        System.out.println("c. Search for a message ID and display the corresponding recipient and message.");
        System.out.println("d. Search for all the messages stored for a particular recipient.");
        System.out.println("e. Delete a message using the message hash.");
        System.out.println("f. Display a report that lists the full details of all the stored messages.");
        System.out.print("Select a sub-option (a-f): ");
        
        String subChoice = scanner.nextLine().trim().toLowerCase();

        switch (subChoice) {
            case "a":
                if (storedCount == 0) {
                    System.out.println("No stored messages found.");
                } else {
                    for (int i = 0; i < storedCount; i++) {
                        JSONObject msg = (JSONObject) messageStorage.get(i);
                        System.out.println("Message [" + i + "] -> Recipient: " + msg.get("Recipient"));
                    }
                }
                break;

            case "b":
                if (storedCount == 0) {
                    System.out.println("No stored messages found.");
                } else {
                    String longest = storedMessages[0];
                    for (int i = 1; i < storedCount; i++) {
                        if (storedMessages[i].length() > longest.length()) {
                            longest = storedMessages[i];
                        }
                    }
                    System.out.println("Longest Stored Message: \"" + longest + "\" (" + longest.length() + " chars)");
                }
                break;

            case "c":
                System.out.print("Enter Message ID to search: ");
                String searchId = scanner.nextLine().trim();
                boolean idFound = false;
                for (int i = 0; i < storedCount; i++) {
                    if (messageIds[i] != null && messageIds[i].equals(searchId)) {
                        JSONObject msg = (JSONObject) messageStorage.get(i);
                        System.out.println("Found! Recipient: " + msg.get("Recipient") + " | Message: " + storedMessages[i]);
                        idFound = true;
                        break;
                    }
                }
                if (!idFound) System.out.println("Message ID not found.");
                break;

            case "d":
                System.out.print("Enter Recipient cell number to filter: ");
                String searchRecipient = scanner.nextLine().trim();
                boolean recipientFound = false;
                for (int i = 0; i < storedCount; i++) {
                    JSONObject msg = (JSONObject) messageStorage.get(i);
                    if (msg.get("Recipient").toString().equals(searchRecipient)) {
                        System.out.println("- [" + messageIds[i] + "]: " + storedMessages[i]);
                        recipientFound = true;
                    }
                }
                if (!recipientFound) System.out.println("No messages found for this recipient.");
                break;

            case "e":
                System.out.print("Enter Message Hash to delete: ");
                String searchHash = scanner.nextLine().trim();
                int targetIndex = -1;
                for (int i = 0; i < storedCount; i++) {
                    if (messageHashes[i] != null && messageHashes[i].equals(searchHash)) {
                        targetIndex = i;
                        break;
                    }
                }
                if (targetIndex != -1) {
                    // Shift elements left across all parallel tracking arrays
                    for (int i = targetIndex; i < storedCount - 1; i++) {
                        storedMessages[i] = storedMessages[i + 1];
                        messageIds[i] = messageIds[i + 1];
                        messageHashes[i] = messageHashes[i + 1];
                    }
                    messageStorage.remove(targetIndex);
                    storedCount--;
                    System.out.println("Message deleted successfully.");
                } else {
                    System.out.println("Message Hash code not found.");
                }
                break;

            case "f":
                if (storedCount == 0) {
                    System.out.println("No messages stored to output report.");
                } else {
                    System.out.println("\n--- FULL STORED MESSAGES TASK REPORT ---");
                    for (int i = 0; i < storedCount; i++) {
                        JSONObject msg = (JSONObject) messageStorage.get(i);
                        System.out.println("Record #" + (i + 1));
                        System.out.println("  ID:        " + messageIds[i]);
                        System.out.println("  Hash:      " + messageHashes[i]);
                        System.out.println("  Recipient: " + msg.get("Recipient"));
                        System.out.println("  Message:   " + storedMessages[i]);
                        System.out.println("----------------------------------------");
                    }
                }
                break;

            default:
                System.out.println("Invalid structural sub-menu option selection.");
        }
    }

    // MAIN ENTRY POINT
   
    public static void main(String[] args) {
    System.out.println("Welcome to ChatIT");

    // Keeps looping until login() returns true
    while (!login()) {
        System.out.println("Please try again.\n");
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
            System.out.println("4. Stored Messages (Data Sub-Menu)"); // Added Option 4 requested by your task
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
                case 4:
                    handleStoredMessagesMenu(); // Direct interface to the parallel arrays
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}