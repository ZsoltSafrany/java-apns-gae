package apns;

public class CannotOpenConnectionException extends ApnsException {

    public CannotOpenConnectionException() {
    }

    public CannotOpenConnectionException(String message) {
        super(message);
    }

    public CannotOpenConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotOpenConnectionException(Throwable cause) {
        super(cause);
    }
}
