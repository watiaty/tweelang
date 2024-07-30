package by.waitaty.wordservice.exception;

public class LanguageNotFoundException extends RuntimeException {
    public LanguageNotFoundException(String code) {
        super("Language with code not found: " + code);
    }
}
