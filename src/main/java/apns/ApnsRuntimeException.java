package apns;

public class ApnsRuntimeException extends RuntimeException {

    public ApnsRuntimeException() {
    }

    public ApnsRuntimeException(String message) {
        super(message);
    }

    public ApnsRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApnsRuntimeException(Throwable cause) {
        super(cause);
    }

    public ApnsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
