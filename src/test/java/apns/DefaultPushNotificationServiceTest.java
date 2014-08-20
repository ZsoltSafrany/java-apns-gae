package apns;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DefaultPushNotificationServiceTest {

    public static final String KEY_APS = "aps";
    public static final String KEY_ALERT = "alert";
    public static final String KEY_BADGE = "badge";
    public static final String KEY_SOUND = "sound";
    public static final String KEY_CONTENT_AVAILABLE = "content-available";

    public static final String KEY_BODY = "body";
    public static final String KEY_ACTION_LOC_KEY = "action-loc-key";
    public static final String KEY_LOC_KEY = "loc-key";
    public static final String KEY_LOC_ARGS = "loc-args";
    public static final String KEY_LAUNCH_IMAGE = "launch-image";

    public static final int BADGE = 1;
    public static final String ALERT = "Hello, this is a push notification";
    public static final String SOUND = "sound.ext";
    private static final String ACTION_LOC_KEY = "action_loc_key_value";
    private static final String LOC_KEY = "loc_key_value";
    private static final String[] LOC_ARGS = {"a", "b", "c"};
    private static final String LAUNCH_IMAGE = "launch_image_value";

    public static final String KEY_CP = "cp";
    private static final String SOME_KEY_1 = "skey1";
    private static final String SOME_KEY_2 = "skey2";
    private static final String SOME_KEY_3 = "skey3";

    private static final String SOME_KEY_4 = "skey4";
    private static final String SOME_VALUE_1 = "svalue1";
    private static final Object SOME_VALUE_2 = "svalue2";
    private static final Object SOME_VALUE_4 = "svalue4";

    @Test
    public void testPayloadJsonCreation() {
        // --
        {
            PushNotification pn = new PushNotification().setAlert(ALERT);
            JSONObject json = createPayloadJson(pn);

            JSONObject aps = json.getJSONObject(KEY_APS);
            Assert.assertEquals(ALERT, aps.getString(KEY_ALERT));
            Assert.assertNull(aps.opt(KEY_BADGE));
            Assert.assertNull(aps.opt(KEY_SOUND));
            Assert.assertNull(aps.opt(KEY_CONTENT_AVAILABLE));
        }

        // --
        {
            PushNotification pn = new PushNotification().setAlert(ALERT).setBadge(BADGE).setSound(SOUND);
            JSONObject json = createPayloadJson(pn);

            JSONObject aps = json.getJSONObject(KEY_APS);
            Assert.assertEquals(ALERT, aps.getString(KEY_ALERT));
            Assert.assertEquals(BADGE, aps.getInt(KEY_BADGE));
            Assert.assertEquals(SOUND, aps.getString(KEY_SOUND));
            Assert.assertNull(aps.opt(KEY_CONTENT_AVAILABLE));
        }

        // --
        {
            PushNotification pn = new PushNotification().setBadge(BADGE).setContentAvailable(true);
            JSONObject json = createPayloadJson(pn);

            JSONObject aps = json.getJSONObject(KEY_APS);
            Assert.assertEquals(BADGE, aps.getInt(KEY_BADGE));
            Assert.assertNull(aps.opt(KEY_ALERT));
            Assert.assertNull(aps.opt(KEY_SOUND));
            Assert.assertEquals(1, aps.getInt(KEY_CONTENT_AVAILABLE));
        }

        // --
        {
            PushNotification.ComplexAlert ca = new PushNotification.ComplexAlert().setBody(ALERT).setActionLocKey(ACTION_LOC_KEY).setLocKey(LOC_KEY).setLocArgs(LOC_ARGS).setLaunchImage(LAUNCH_IMAGE);
            PushNotification pn = new PushNotification().setComplexAlert(ca).setBadge(BADGE).setSound(SOUND);
            JSONObject json = createPayloadJson(pn);

            JSONObject aps = json.getJSONObject(KEY_APS);
            Assert.assertEquals(BADGE, aps.getInt(KEY_BADGE));
            Assert.assertEquals(SOUND, aps.getString(KEY_SOUND));

            JSONObject alert = aps.getJSONObject(KEY_ALERT);
            Assert.assertEquals(ALERT, alert.getString(KEY_BODY));
            Assert.assertEquals(LOC_KEY, alert.getString(KEY_LOC_KEY));
            Assert.assertEquals(ACTION_LOC_KEY, alert.getString(KEY_ACTION_LOC_KEY));
            assertEquals(new JSONArray(LOC_ARGS), alert.getJSONArray(KEY_LOC_ARGS));
            Assert.assertEquals(LAUNCH_IMAGE, alert.getString(KEY_LAUNCH_IMAGE));

        }

        // --
        {
            Map<String, Object> someDict = new HashMap<>();
            someDict.put(SOME_KEY_4, SOME_VALUE_4);

            Map<String, Object> customPayload = new HashMap<>();
            customPayload.put(SOME_KEY_2, SOME_VALUE_2);
            customPayload.put(SOME_KEY_3, someDict);


            PushNotification pn = new PushNotification().setBadge(BADGE).setCustomPayload(KEY_CP, customPayload).setCustomPayload(SOME_KEY_1, SOME_VALUE_1);
            JSONObject json = new JSONObject(createPayloadJson(pn).toString());

            JSONObject aps = json.getJSONObject(KEY_APS);
            Assert.assertEquals(BADGE, aps.getInt(KEY_BADGE));
            Assert.assertNull(aps.opt(KEY_ALERT));
            Assert.assertNull(aps.opt(KEY_SOUND));
            Assert.assertNull(aps.opt(KEY_CONTENT_AVAILABLE));

            Assert.assertEquals(SOME_VALUE_1, json.getString(SOME_KEY_1));
            JSONObject cp = json.getJSONObject(KEY_CP);
            Assert.assertEquals(SOME_VALUE_2, cp.getString(SOME_KEY_2));

            JSONObject someDictJson = cp.getJSONObject(SOME_KEY_3);
            Assert.assertEquals(SOME_VALUE_4, someDictJson.getString(SOME_KEY_4));
        }

    }

    private void assertEquals(JSONArray ja1, JSONArray ja2) {
        Assert.assertEquals(ja1.length(), ja2.length());
        for (int i = 0; i < ja1.length(); i++) {
            Assert.assertEquals(ja1.get(i), ja2.get(i));
        }
    }

    private JSONObject createPayloadJson(PushNotification pn) {
        return new DefaultPushNotificationService.JsonPayloadCreator(pn).create();
    }

}