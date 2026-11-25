package quizapp;

import java.util.List;

public class Question {
    private final String id;
    private final String prompt;
    private final List<String> options;
    private final int correctOptionIndex; // 0-based index
    private final String explanation;

    public Question(String id, String prompt, List<String> options, int correctOptionIndex, String explanation) {
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("A question must have at least two options.");
        }
        if (correctOptionIndex < 0 || correctOptionIndex >= options.size()) {
            throw new IllegalArgumentException("Invalid correct option index.");
        }
        this.id = id;
        this.prompt = prompt;
        this.options = List.copyOf(options);
        this.correctOptionIndex = correctOptionIndex;
        this.explanation = explanation;
    }

    public String getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public String getExplanation() {
        return explanation;
    }

    public boolean isCorrect(int userOptionIndex) {
        return userOptionIndex == correctOptionIndex;
    }
}