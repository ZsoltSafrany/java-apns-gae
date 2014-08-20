package apns;

public class MockApnsConnectionFactory implements ApnsConnectionFactory {

    @Override
    public ApnsConnection openPushConnection() throws ApnsException {
        return new MockApnsConnection();
    }

    @Override
    public ApnsConnection openFeedbackConnection() throws ApnsException {
        return new MockApnsConnection();
    }

}
