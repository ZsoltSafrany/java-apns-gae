package apns;

public class CannotUseConnectionException extends ApnsException {

    public CannotUseConnectionException() {
    }

    public CannotUseConnectionException(String message) {
        super(message);
    }

    public CannotUseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotUseConnectionException(Throwable cause) {
        super(cause);
    }
}
