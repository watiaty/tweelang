package by.waitaty.learnlanguage.exception;

public class UsernameAlreadyExistException extends RuntimeException{
    public UsernameAlreadyExistException(String username) {
        super("User with this username already exists: " + username);
    }
}
