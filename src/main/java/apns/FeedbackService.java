package apns;

import java.util.List;

public interface FeedbackService {

    List<FailedDeviceToken> read(ApnsConnection connection) throws ApnsException;

}
