package quizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class QuizSession {
    private final User user;
    private final QuizCategory category;
    private final List<Question> sessionQuestions;
    private final Map<Question, Integer> userAnswers;
    private int score;

    public QuizSession(User user, QuizCategory category, boolean shuffleQuestions) {
        this.user = user;
        this.category = category;
        this.sessionQuestions = new ArrayList<>(category.getQuestions());
        if (shuffleQuestions) {
            Collections.shuffle(this.sessionQuestions);
        }
        this.userAnswers = new LinkedHashMap<>();
        this.score = 0;
    }

    public void start(Scanner scanner) {
        if (sessionQuestions.isEmpty()) {
            System.out.println("\nNo questions available in category: " + category.getName());
            return;
        }

        System.out.println("\n==========================================");
        System.out.println("Starting Quiz: " + category.getName());
        System.out.println("Participant: " + user.getUsername());
        System.out.println("Total Questions: " + sessionQuestions.size());
        System.out.println("==========================================");

        for (int i = 0; i < sessionQuestions.size(); i++) {
            Question q = sessionQuestions.get(i);
            System.out.println("\nQ" + (i + 1) + ". " + q.getPrompt());

            List<String> opts = q.getOptions();
            for (int j = 0; j < opts.size(); j++) {
                System.out.println("   " + (j + 1) + ") " + opts.get(j));
            }

            int choice = readOptionChoice(scanner, opts.size());
            userAnswers.put(q, choice);

            if (q.isCorrect(choice)) {
                score++;
            }
        }

        User.QuizResult result = new User.QuizResult(category.getName(), score, sessionQuestions.size());
        user.addResult(result);
        printEvaluation(result);
    }

    private int readOptionChoice(Scanner scanner, int optionCount) {
        while (true) {
            System.out.print("Enter your choice (1-" + optionCount + "): ");
            String input = scanner.nextLine().trim();
            try {
                int parsed = Integer.parseInt(input);
                if (parsed >= 1 && parsed <= optionCount) {
                    return parsed - 1;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Invalid input. Please enter a number between 1 and " + optionCount + ".");
        }
    }

    private void printEvaluation(User.QuizResult result) {
        System.out.println("\n==========================================");
        System.out.println("QUIZ EVALUATION - " + user.getUsername());
        System.out.println("==========================================");
        System.out.printf("Final Score: %d / %d (%.1f%%)%n",
                result.getScore(), result.getTotalQuestions(), result.getPercentage());
        System.out.println("Performance: " + getPerformanceRemark(result.getPercentage()));
        System.out.println("------------------------------------------");

        int index = 1;
        for (Map.Entry<Question, Integer> entry : userAnswers.entrySet()) {
            Question q = entry.getKey();
            int chosen = entry.getValue();
            int correct = q.getCorrectOptionIndex();
            boolean isRight = q.isCorrect(chosen);

            System.out.println("\nQ" + (index++) + ". " + q.getPrompt());
            System.out.println("   Your Answer   : " + formatOption(q, chosen) + (isRight ? " [CORRECT]" : " [INCORRECT]"));
            if (!isRight) {
                System.out.println("   Correct Answer: " + formatOption(q, correct));
            }
            if (q.getExplanation() != null && !q.getExplanation().isBlank()) {
                System.out.println("   Explanation   : " + q.getExplanation());
            }
        }
        System.out.println("==========================================\n");
    }

    private String formatOption(Question q, int idx) {
        if (idx < 0 || idx >= q.getOptions().size()) {
            return "No answer";
        }
        return (idx + 1) + ") " + q.getOptions().get(idx);
    }

    private String getPerformanceRemark(double percentage) {
        if (percentage >= 85) return "Excellent!";
        if (percentage >= 70) return "Good job!";
        if (percentage >= 50) return "Fair effort — keep practicing.";
        return "Needs improvement — review the explanations below.";
    }
}