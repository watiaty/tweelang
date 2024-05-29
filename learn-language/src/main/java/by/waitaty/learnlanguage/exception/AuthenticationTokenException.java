package by.waitaty.learnlanguage.exception;

public class AuthenticationTokenException extends RuntimeException {
    public AuthenticationTokenException() {
        super("Dont have access");
    }
}
