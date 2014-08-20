package apns;

public interface ApnsConnectionFactory {

    ApnsConnection openPushConnection() throws ApnsException;

    ApnsConnection openFeedbackConnection() throws ApnsException;

}
