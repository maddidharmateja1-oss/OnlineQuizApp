package quizapp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuizManager manager = new QuizManager();

        System.out.println("==========================================");
        System.out.println("       ONLINE QUIZ APPLICATION (CORE JAVA)  ");
        System.out.println("==========================================");
        System.out.print("Enter your name / user ID: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Guest";
        }
        User user = new User(name);

        boolean running = true;
        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Start a Quiz");
            System.out.println("2. View My Score History");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleStartQuiz(scanner, manager, user);
                case "2" -> handleViewHistory(user);
                case "3" -> {
                    running = false;
                    System.out.println("\nThank you for using the Online Quiz Application, " + user.getUsername() + "!");
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    private static void handleStartQuiz(Scanner scanner, QuizManager manager, User user) {
        System.out.println("\n--- Available Categories ---");
        int idx = 1;
        for (QuizCategory cat : manager.getCategories().values()) {
            System.out.printf("%d. %s (%d questions) - %s%n",
                    idx++, cat.getName(), cat.getQuestions().size(), cat.getDescription());
        }

        System.out.print("Choose a category number (or 0 to go back): ");
        int catNum;
        try {
            catNum = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        if (catNum == 0) {
            return;
        }

        QuizCategory selected = manager.getCategoryByNumber(catNum);
        if (selected == null) {
            System.out.println("Category not found.");
            return;
        }

        System.out.print("Shuffle questions? (y/n): ");
        boolean shuffle = scanner.nextLine().trim().equalsIgnoreCase("y");

        QuizSession session = new QuizSession(user, selected, shuffle);
        session.start(scanner);
    }

    private static void handleViewHistory(User user) {
        System.out.println("\n--- Score History for " + user.getUsername() + " ---");
        if (user.getHistory().isEmpty()) {
            System.out.println("No quizzes attempted yet.");
            return;
        }
        int i = 1;
        for (User.QuizResult r : user.getHistory()) {
            System.out.println((i++) + ". " + r);
        }
    }
}