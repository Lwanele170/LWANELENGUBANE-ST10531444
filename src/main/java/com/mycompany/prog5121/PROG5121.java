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

    // Parallel ArrayLists to dynamically populate per assignment requirements
    static final ArrayList<String> sentMessages = new ArrayList<>();
    static final ArrayList<String> disregardedMessages = new ArrayList<>();
    static final ArrayList<String> storedMessages = new ArrayList<>();
    static final ArrayList<String> messageHashes = new ArrayList<>();
    static final ArrayList<Long> messageIds = new ArrayList<>();
    static final ArrayList<String> storedSenders = new ArrayList<>();
    static final ArrayList<String> storedRecipients = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);

    // Hardcoded mock credentials updated to your specific details
    private static final String storedUsername = "Kyl_1";
    private static final String storedPassword = "Ch&&sec@ke99!";
    private static String currentUser = "";

    // VALIDATION & AUTHENTICATION UTILITIES
    
    public static boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        if (password == null) return false;
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(regex, password);
    }

    public static String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted";
        }

        return "Username and password successfully captured. User registered";
    }

    public static boolean loginUser(String username, String password,
                                  String storedUsername, String storedPassword) {
        return username != null && username.equals(storedUsername)
                && password != null && password.equals(storedPassword);
    }

    public static String returnLoginStatus(boolean status) {
        if (status) {
            return "Login successful! Welcome back ky1_1!";
        } else {
            return "Login failed!";
        }
    }

    // Handles the login sequence shown at the start of your main method
    private static boolean login() {
        System.out.print("Enter your username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Enter your password: ");
        String loginPass = scanner.nextLine();

        boolean status = loginUser(loginUser, loginPass, storedUsername, storedPassword);
        
        if (status) {
            currentUser = loginUser;
        }
        
        System.out.println(returnLoginStatus(status));
        
        return status;
    }

    public static boolean CheckCellPhoneNumber(String number) {
    return number != null && number.matches("^\\+\\d{9,12}$");
}

    
    // CORE FUNCTIONALITIES
    
    static void sendMessage() {
        long messageId = 1000000000L + new Random().nextInt(900000000);

        System.out.print("\nEnter recipient number (+CCxxxxxxxxx): ");
        String recipient = scanner.nextLine();

if (!CheckCellPhoneNumber(recipient)) {
    System.out.println("Invalid number. Must include country code and be <=12 digits.");
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

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("MessageID", messageId);
        jsonMessage.put("MessageHash", hash);
        jsonMessage.put("Recipient", recipient);
        jsonMessage.put("Message", message);

        if (action == 2) {
            disregardedMessages.add(message);
            System.out.println("Message Cancelled");
            return;
        }

        if (action == 3) {
            messageStorage.add(jsonMessage);
            
            storedMessages.add(message);
            messageIds.add(messageId);
            messageHashes.add(hash);
            storedSenders.add(currentUser);
            storedRecipients.add(recipient);
            
            System.out.println("Message stored.");
            return;
        }

        Total_messages++;
        messageCounter++;
        sentMessages.add(message);

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

    static void manageStoredMessagesMenu() {
        while (true) {
            System.out.println("\n--- Stored Messages Options ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID and display corresponding recipient and message");
            System.out.println("d. Search for all messages stored for a particular recipient");
            System.out.println("e. Delete a message using the message hash");
            System.out.println("f. Display a report that lists the full details of all stored messages");
            System.out.println("g. Back to Main Menu");
            System.out.print("Select sub-option (a-g): ");
            String subChoice = scanner.nextLine().trim().toLowerCase();

            if (subChoice.equals("g")) break;

            switch (subChoice) {
                case "a":
                    if (storedMessages.isEmpty()) { System.out.println("No stored messages."); break; }
                    System.out.println("\n--- Senders & Recipients ---");
                    for (int i = 0; i < storedMessages.size(); i++) {
                        System.out.println("Message " + (i + 1) + " -> Sender: " + storedSenders.get(i) + " | Recipient: " + storedRecipients.get(i));
                    }
                    break;
                case "b":
                    if (storedMessages.isEmpty()) { System.out.println("No stored messages."); break; }
                    String longest = storedMessages.get(0);
                    for (String msg : storedMessages) {
                        if (msg.length() > longest.length()) longest = msg;
                    }
                    System.out.println("\nLongest Message: \"" + longest + "\"");
                    break;
                case "c":
                    if (storedMessages.isEmpty()) { System.out.println("No stored messages."); break; }
                    System.out.print("Enter Message ID: ");
                    try {
                        long searchId = Long.parseLong(scanner.nextLine());
                        int index = messageIds.indexOf(searchId);
                        if (index != -1) {
                            System.out.println("Recipient: " + storedRecipients.get(index));
                            System.out.println("Message: " + storedMessages.get(index));
                        } else {
                            System.out.println("ID not found.");
                        }
                    } catch (NumberFormatException e) { System.out.println("Invalid ID format."); }
                    break;
                case "d":
                    if (storedMessages.isEmpty()) { System.out.println("No stored messages."); break; }
                    System.out.print("Enter Recipient string: ");
                    String matchRecip = scanner.nextLine().trim();
                    boolean foundAny = false;
                    for (int i = 0; i < storedRecipients.size(); i++) {
                        if (storedRecipients.get(i).equals(matchRecip)) {
                            System.out.println("ID: " + messageIds.get(i) + " | Message: " + storedMessages.get(i));
                            foundAny = true;
                        }
                    }
                    if (!foundAny) System.out.println("No matches found.");
                    break;
                case "e":
                    if (storedMessages.isEmpty()) { System.out.println("No stored messages."); break; }
                    System.out.print("Enter Hash value to delete: ");
                    String targetedHash = scanner.nextLine().trim();
                    int delIndex = messageHashes.indexOf(targetedHash);
                    if (delIndex != -1) {
                        storedMessages.remove(delIndex);
                        messageIds.remove(delIndex);
                        messageHashes.remove(delIndex);
                        storedSenders.remove(delIndex);
                        storedRecipients.remove(delIndex);
                        System.out.println("Successfully removed from parallel registers.");
                    } else {
                        System.out.println("Hash code not found.");
                    }
                    break;
                case "f":
                    if (storedMessages.isEmpty()) { System.out.println("No stored records."); break; }
                    System.out.println("\n================ FULL TASK REPORT ================");
                    for (int i = 0; i < storedMessages.size(); i++) {
                        System.out.println("Record #" + (i + 1));
                        System.out.println("  Message ID:   " + messageIds.get(i));
                        System.out.println("  Message Hash: " + messageHashes.get(i));
                        System.out.println("  From:         " + storedSenders.get(i));
                        System.out.println("  To:           " + storedRecipients.get(i));
                        System.out.println("  Content:      \"" + storedMessages.get(i) + "\"");
                        System.out.println("-------------------------------------------------");
                    }
                    break;
                default:
                    System.out.println("Invalid selection.");
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
            System.out.println("2. Stored Messages (Manage Arrays)");
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
                    manageStoredMessagesMenu();
                    break;
                case 3:
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