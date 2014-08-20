package apns;

public class ApnsException extends Exception {

    public ApnsException() {
    }

    public ApnsException(String message) {
        super(message);
    }

    public ApnsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApnsException(Throwable cause) {
        super(cause);
    }

    public ApnsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
