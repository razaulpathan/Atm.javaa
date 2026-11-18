import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String ACCOUNT_HOLDER = "Razaul Pathan";
    private static final String ACCOUNT_NUMBER = "**** **** 1234";
    private static final String PIN = "1234"; // Demo PIN only. Do not use for real banking.
    private static double balance = 10000.00;
    private static final List<String> transactions = new ArrayList<>();
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("          JAVA ATM SYSTEM");
        System.out.println("==================================");

        if (!authenticate()) {
            System.out.println("Too many incorrect attempts. Card blocked for this session.");
            scanner.close();
            return;
        }

        System.out.println("\nWelcome, " + ACCOUNT_HOLDER + "!");
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> checkBalance();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> showAccountDetails();
                case 5 -> showMiniStatement();
                case 6 -> {
                    running = false;
                    System.out.println("\nThank you for using Java ATM. Goodbye!");
                }
                default -> System.out.println("Invalid option. Please choose 1-6.");
            }
        }
        scanner.close();
    }

    private static boolean authenticate() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter your 4-digit PIN: ");
            String enteredPin = scanner.nextLine().trim();

            if (PIN.equals(enteredPin)) {
                return true;
            }

            int remaining = 3 - attempt;
            if (remaining > 0) {
                System.out.println("Incorrect PIN. Attempts remaining: " + remaining);
            }
        }
        return false;
    }

    private static void showMenu() {
        System.out.println("\n------------- ATM MENU -------------");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Account Details");
        System.out.println("5. Mini Statement");
        System.out.println("6. Exit");
        System.out.println("------------------------------------");
    }

    private static void checkBalance() {
        System.out.printf("Available balance: Rs. %.2f%n", balance);
    }

    private static void deposit() {
        double amount = readAmount("Enter deposit amount (Rs.): ");
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        balance += amount;
        addTransaction("Deposit", amount);
        System.out.printf("Deposit successful. New balance: Rs. %.2f%n", balance);
    }

    private static void withdraw() {
        double amount = readAmount("Enter withdrawal amount (Rs.): ");
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            addTransaction("Withdrawal", amount);
            System.out.printf("Please collect your cash. New balance: Rs. %.2f%n", balance);
        }
    }

    private static void showAccountDetails() {
        System.out.println("\n---------- ACCOUNT DETAILS ----------");
        System.out.println("Account holder : " + ACCOUNT_HOLDER);
        System.out.println("Account number : " + ACCOUNT_NUMBER);
        System.out.printf("Balance        : Rs. %.2f%n", balance);
        System.out.println("-------------------------------------");
    }

    private static void showMiniStatement() {
        System.out.println("\n------------- MINI STATEMENT -------------");
        if (transactions.isEmpty()) {
            System.out.println("No transactions in this session.");
        } else {
            int start = Math.max(0, transactions.size() - 5);
            for (int i = start; i < transactions.size(); i++) {
                System.out.println(transactions.get(i));
            }
        }
        System.out.printf("Current balance: Rs. %.2f%n", balance);
        System.out.println("------------------------------------------");
    }

    private static void addTransaction(String type, double amount) {
        String timestamp = LocalDateTime.now().format(TIME_FORMAT);
        transactions.add(timestamp + " | " + type + " | Rs. " + String.format("%.2f", amount));
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount, e.g. 500 or 250.50.");
            }
        }
    }
}
