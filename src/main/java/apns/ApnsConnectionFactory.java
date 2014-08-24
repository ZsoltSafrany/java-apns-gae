package apns;

public interface ApnsConnectionFactory {

    ApnsConnection openPushConnection() throws CannotOpenConnectionException;

    ApnsConnection openFeedbackConnection() throws CannotOpenConnectionException;
}
