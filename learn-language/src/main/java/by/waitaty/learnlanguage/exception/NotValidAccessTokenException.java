package by.waitaty.learnlanguage.exception;

public class NotValidAccessTokenException extends RuntimeException {
    public NotValidAccessTokenException() {
        super("The refresh token has already expired.");
    }
}
