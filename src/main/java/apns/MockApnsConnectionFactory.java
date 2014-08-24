package apns;

public class MockApnsConnectionFactory implements ApnsConnectionFactory {

    @Override
    public ApnsConnection openPushConnection() {
        return new MockApnsConnection();
    }

    @Override
    public ApnsConnection openFeedbackConnection() {
        return new MockApnsConnection();
    }

}
