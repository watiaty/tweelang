package by.waitaty.learnlanguage.exception;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(Long id) {
        super("Word with id not found: " + id);
    }

    public WordNotFoundException(String word) {
        super("Word not found: " + word);
    }
}
