package quizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private final String username;
    private final List<QuizResult> history;

    public static class QuizResult {
        private final String categoryName;
        private final int score;
        private final int totalQuestions;

        public QuizResult(String categoryName, int score, int totalQuestions) {
            this.categoryName = categoryName;
            this.score = score;
            this.totalQuestions = totalQuestions;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public int getScore() {
            return score;
        }

        public int getTotalQuestions() {
            return totalQuestions;
        }

        public double getPercentage() {
            return totalQuestions == 0 ? 0.0 : (score * 100.0) / totalQuestions;
        }

        @Override
        public String toString() {
            return String.format("%s -> %d/%d (%.1f%%)", categoryName, score, totalQuestions, getPercentage());
        }
    }

    public User(String username) {
        this.username = username;
        this.history = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void addResult(QuizResult result) {
        history.add(result);
    }

    public List<QuizResult> getHistory() {
        return Collections.unmodifiableList(history);
    }
}