package by.waitaty.wordservice.exception;

public class UnsupportedFileException extends RuntimeException {
    public UnsupportedFileException(String format) {
        super(format + "is not supported");
    }
}
