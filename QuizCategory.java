package quizapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizCategory {
    private final String name;
    private final String description;
    private final List<Question> questions;

    public QuizCategory(String name, String description) {
        this.name = name;
        this.description = description;
        this.questions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}