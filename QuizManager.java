package quizapp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuizManager {
    private final Map<String, QuizCategory> categories;

    public QuizManager() {
        this.categories = new LinkedHashMap<>();
        seedDefaultCategories();
    }

    public void addCategory(QuizCategory category) {
        categories.put(category.getName().toLowerCase(), category);
    }

    public Map<String, QuizCategory> getCategories() {
        return categories;
    }

    public QuizCategory getCategoryByNumber(int number) {
        int current = 1;
        for (QuizCategory cat : categories.values()) {
            if (current == number) {
                return cat;
            }
            current++;
        }
        return null;
    }

    private void seedDefaultCategories() {
        QuizCategory javaBasics = new QuizCategory("Core Java & OOP", "Fundamentals of Java, OOP, and Collections");
        javaBasics.addQuestion(new Question(
                "J1",
                "Which collection class guarantees insertion order and allows unique elements?",
                List.of("HashSet", "LinkedHashSet", "TreeSet", "ArrayList"),
                1,
                "LinkedHashSet maintains a doubly-linked list of entries to preserve insertion order while enforcing uniqueness."
        ));
        javaBasics.addQuestion(new Question(
                "J2",
                "Which keyword prevents a method from being overridden in a subclass?",
                List.of("static", "abstract", "final", "const"),
                2,
                "Marking a method as final prevents subclasses from overriding it."
        ));
        javaBasics.addQuestion(new Question(
                "J3",
                "Which interface is the root of the Java Collections Framework (excluding Map)?",
                List.of("List", "Set", "Collection", "Iterable"),
                2,
                "java.util.Collection is the root interface of the Collections Framework (extending Iterable)."
        ));
        javaBasics.addQuestion(new Question(
                "J4",
                "What is the default value of an uninitialized object reference in Java?",
                List.of("0", "false", "null", "undefined"),
                2,
                "Object references default to null when declared as instance/static fields."
        ));

        QuizCategory csCore = new QuizCategory("Computer Science Basics", "Data structures, complexity, and core CS concepts");
        csCore.addQuestion(new Question(
                "CS1",
                "What is the average-case time complexity of lookup in a well-distributed HashMap?",
                List.of("O(1)", "O(log n)", "O(n)", "O(n log n)"),
                0,
                "With a good hash function and low collision rate, HashMap get/put operations run in O(1) average time."
        ));
        csCore.addQuestion(new Question(
                "CS2",
                "Which data structure operates on a First-In, First-Out (FIFO) principle?",
                List.of("Stack", "Queue", "Binary Search Tree", "Trie"),
                1,
                "A Queue adds elements at the rear and removes them from the front (FIFO)."
        ));
        csCore.addQuestion(new Question(
                "CS3",
                "Which principle states that software entities should be open for extension but closed for modification?",
                List.of("DRY", "KISS", "Open/Closed Principle", "YAGNI"),
                2,
                "The Open/Closed Principle (O in SOLID) encourages extending behavior without altering existing code."
        ));

        addCategory(javaBasics);
        addCategory(csCore);
    }
}