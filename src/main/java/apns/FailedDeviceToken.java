package apns;

import java.util.Date;

public class FailedDeviceToken {

    private final Date mFailTimestamp;
    private final String mDeviceToken;

    public FailedDeviceToken(Date failTimestamp, String deviceToken) {
        mFailTimestamp = failTimestamp;
        mDeviceToken = deviceToken;
    }

    /**
     * A timestamp indicating when APNs determined that the application no longer exists on the device.
     */
    public Date getFailTimestamp() {
        return mFailTimestamp;
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    @Override
    public String toString() {
        return "FailedDeviceToken{" +
                "failTime=" + mFailTimestamp +
                ", token='" + mDeviceToken + '\'' +
                '}';
    }
}
