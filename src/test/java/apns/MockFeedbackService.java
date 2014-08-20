package apns;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MockFeedbackService implements FeedbackService {

    private List<FailedDeviceToken> mFailedDeviceTokens = new LinkedList<>();

    @Override
    public List<FailedDeviceToken> read(ApnsConnection connection) throws ApnsException {
        List<FailedDeviceToken> failedDeviceTokens = new ArrayList<>(mFailedDeviceTokens);
        mFailedDeviceTokens.clear();
        return failedDeviceTokens;
    }

    public void add(FailedDeviceToken failedDeviceToken) {
        mFailedDeviceTokens.add(failedDeviceToken);
    }

}
