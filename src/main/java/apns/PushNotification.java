package apns;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PushNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mBadge;
    private String mAlert;
    private ComplexAlert mComplexAlert;
    private String mSound;
    private boolean mContentAvailable;
    private Collection<String> mDeviceTokens;
    private String mCategory;
    private Map<String, Object> mCustomPayload = new HashMap<>();

    public Integer getBadge() {
        return mBadge;
    }

    public PushNotification setBadge(Integer badge) {
        mBadge = badge;
        return this;
    }

    public String getAlert() {
        return mAlert;
    }

    public PushNotification setAlert(String alert) {
        mAlert = alert;
        return this;
    }

    public ComplexAlert getComplexAlert() {
        return mComplexAlert;
    }

    public PushNotification setComplexAlert(ComplexAlert alert) {
        mComplexAlert = alert;
        return this;
    }

    public String getSound() {
        return mSound;
    }

    public PushNotification setSound(String sound) {
        mSound = sound;
        return this;
    }

    public Collection<String> getDeviceTokens() {
        return mDeviceTokens;
    }

    public PushNotification setDeviceTokens(String... deviceTokens) {
        setDeviceTokens(Arrays.asList(deviceTokens));
        return this;
    }

    public PushNotification setDeviceTokens(Collection<String> deviceTokens) {
        mDeviceTokens = deviceTokens;
        return this;
    }

    public boolean isContentAvailable() {
        return mContentAvailable;
    }

    public PushNotification setContentAvailable(boolean available) {
        mContentAvailable = available;
        return this;
    }
    
    public String getCategory() {
        return mCategory;
    }

    public PushNotification setCategory(String category) {
        mCategory = category;
        return this;
    }

    public PushNotification setCustomPayload(String key, Object value) {
        mCustomPayload.put(key, value);
        return this;
    }

    public Map<String, Object> getCustomPayload() {
        return mCustomPayload;
    }


    @Override
    public String toString() {
        return "PushNotification{" +
                "badge=" + mBadge +
                ", alert='" + mAlert + '\'' +
                ", complexAlert=" + mComplexAlert +
                ", sound='" + mSound + '\'' +
                ", contentAvailable=" + mContentAvailable +
                ", customPayload=" + mCustomPayload +
                ", deviceTokens=" + mDeviceTokens +
                '}';
    }

    public static class ComplexAlert {
        private String mBody;
        private String mActionLocKey;
        private String mLocKey;
        private Iterable<String> mLocArgs;
        private String mLaunchImage;

        public String getBody() {
            return mBody;
        }

        public ComplexAlert setBody(String body) {
            mBody = body;
            return this;
        }

        public String getActionLocKey() {
            return mActionLocKey;
        }

        public ComplexAlert setActionLocKey(String actionLocKey) {
            mActionLocKey = actionLocKey;
            return this;
        }

        public String getLocKey() {
            return mLocKey;
        }

        public ComplexAlert setLocKey(String locKey) {
            mLocKey = locKey;
            return this;
        }

        public Iterable<String> getLocArgs() {
            return mLocArgs;
        }

        public ComplexAlert setLocArgs(String... locArgs) {
            setLocArgs(Arrays.asList(locArgs));
            return this;
        }

        public ComplexAlert setLocArgs(Iterable<String> locArgs) {
            mLocArgs = locArgs;
            return this;
        }

        public String getLaunchImage() {
            return mLaunchImage;
        }

        public ComplexAlert setLaunchImage(String launchImage) {
            mLaunchImage = launchImage;
            return this;
        }
    }
}