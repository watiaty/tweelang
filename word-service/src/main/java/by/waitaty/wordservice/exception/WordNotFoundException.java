package by.waitaty.wordservice.exception;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(Long id) {
        super("Word with id not found: " + id);
    }

    public WordNotFoundException(String word, String language) {
        super("Word not found: " + word + " on " + language);
    }
}
