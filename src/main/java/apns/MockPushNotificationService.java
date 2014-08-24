package apns;

import java.util.HashMap;
import java.util.Map;

public class MockPushNotificationService implements PushNotificationService {

    private Map<String, PushNotification> mSentPushPerDeviceToken = new HashMap<>();

    @Override
    public void send(PushNotification pushNotification, ApnsConnection connection) {
        for (String deviceToken : pushNotification.getDeviceTokens()) {
            mSentPushPerDeviceToken.put(deviceToken.toLowerCase(), pushNotification);
        }
    }

    public boolean pushWasSentTo(String deviceToken) {
        return mSentPushPerDeviceToken.containsKey(deviceToken.toLowerCase());
    }

    public PushNotification getLastPushSentToDevice(String deviceToken) {
        return mSentPushPerDeviceToken.get(deviceToken);
    }
}
