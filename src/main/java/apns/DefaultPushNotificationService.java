package apns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DefaultPushNotificationService implements PushNotificationService {

    private static final short COMMAND_NOTIFICATION = 2;

    private static final short ITEM_ID_DEVICE_TOKEN = 1;
    private static final short ITEM_ID_PAYLOAD = 2;
    private static final short ITEM_ID_NOTIFICATION_ID = 3;
    private static final short ITEM_ID_EXPIRATION_DATE = 4;
    private static final short ITEM_ID_PRIORITY = 5;

    private static final byte PRIORITY_IMMEDIATE = 10;
    private static final byte PRIORITY_BATTERY_SAVER = 5;

    private static final int MAX_PAYLOAD_BYTES = 2048;

    private static long sNextNotificationId;

    @Override
    public void send(PushNotification pushNotification, ApnsConnection connection) throws CannotUseConnectionException, PayloadException {
        if (connection == null) {
            throw new ApnsRuntimeException("ApnsConnection must not be null");
        }
        if (pushNotification == null) {
            throw new ApnsRuntimeException("PushNotification must not be null");
        }
        if (pushNotification.getDeviceTokens() == null || pushNotification.getDeviceTokens().size() == 0) {
            throw new ApnsRuntimeException("No device token was set");
        }
        send(pushNotification, connection.getSocket());
    }

    private void send(PushNotification pn, Socket socket) throws CannotUseConnectionException, PayloadException {
        try {
            send2(pn, socket);
        } catch (IOException e) {
            throw new CannotUseConnectionException("Could not send notification to APNS", e);
        }
    }

    private void send2(PushNotification pn, Socket socket) throws IOException, PayloadException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        final FrameDataCreator frameCreator = new FrameDataCreator(pn);
        
        for (String deviceToken : pn.getDeviceTokens()) {
            byte[] frameData = frameCreator.create(deviceToken);
        	dos.writeByte(COMMAND_NOTIFICATION);
            dos.writeInt(frameData.length);
            dos.write(frameData);
        }
        
        dos.flush();
    }

    static class FrameDataCreator {

        private PushNotification mPushNotification;

        private FrameDataCreator(PushNotification pushNotification) {
            if (pushNotification == null) {
                throw new ApnsRuntimeException("PushNotification must not be null");
            }
            mPushNotification = pushNotification;
        }

        public byte[] create(String deviceToken) throws IOException, PayloadException {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 DataOutputStream dos = new DataOutputStream(baos)) {

                writeDeviceToken(dos, deviceToken);
                writePayload(dos);
                writeNotificationId(dos);
                writeExpirationDate(dos);
                writePriority(dos);
                return baos.toByteArray();
            }
        }

        private void writeDeviceToken(DataOutputStream dos, String deviceToken) throws IOException {
            dos.writeByte(ITEM_ID_DEVICE_TOKEN);
            dos.writeShort(32);
            dos.write(DeviceTokenHelper.deviceTokenStringToBytes(deviceToken));
        }

        private void writePayload(DataOutputStream dos) throws IOException, PayloadException {
            JSONObject payloadJson;
            try {
                payloadJson = new JsonPayloadCreator(mPushNotification).create();
            } catch (JSONException e) {
                throw new PayloadException("Could not create payload JSON", e);
            }
            String payload = payloadJson.toString();
            final byte[] payloadBytes = payload.getBytes("UTF-8");
            if (payloadBytes.length > MAX_PAYLOAD_BYTES) {
                throw new PayloadException(String.format("Payload is larger than [%d] bytes: [%s]", MAX_PAYLOAD_BYTES, payload));
            }
            dos.writeByte(ITEM_ID_PAYLOAD);
            dos.writeShort(payloadBytes.length);
            dos.write(payloadBytes);
        }

        private void writeNotificationId(DataOutputStream dos) throws IOException {
            dos.writeByte(ITEM_ID_NOTIFICATION_ID);
            dos.writeShort(4);
            dos.writeInt((int) sNextNotificationId++);
        }

        private void writeExpirationDate(DataOutputStream dos) throws IOException {
            dos.writeByte(ITEM_ID_EXPIRATION_DATE);
            dos.writeShort(4);
            dos.writeInt(getExpirationDateInUnixEpoch());
        }

        private void writePriority(DataOutputStream dos) throws IOException {
            dos.writeByte(ITEM_ID_PRIORITY);
            dos.writeShort(1);
            dos.writeByte(PRIORITY_IMMEDIATE);
        }

        private int getExpirationDateInUnixEpoch() {
            int nowInSec = (int) (System.currentTimeMillis() / 1000);
            return nowInSec + ((int) TimeUnit.DAYS.toSeconds(5));
        }
    }

    static class JsonPayloadCreator {

        private PushNotification mPushNotification;

        public JsonPayloadCreator(PushNotification pushNotification) {
            if (pushNotification == null) {
                throw new ApnsRuntimeException("pushNotification must not be null");
            }
            mPushNotification = pushNotification;
        }

        public JSONObject create() {
            JSONObject jo = new JSONObject();
            putApsDict(jo);
            putCustomPayload(jo);
            return jo;
        }

        private void putApsDict(JSONObject jo) {
            final PushNotification pn = mPushNotification;
            Map<String, Object> dict = new HashMap<>();
            if (pn.getBadge() != null) {
                dict.put("badge", pn.getBadge());
            }
            if (pn.getSound() != null) {
                dict.put("sound", pn.getSound());
            }
            if (pn.isContentAvailable()) {
                dict.put("content-available", 1);
            }
            if (pn.getAlert() != null) {
                dict.put("alert", pn.getAlert());
            } else if (pn.getComplexAlert() != null) {
                dict.put("alert", createAlertDict(pn.getComplexAlert()));
            }
            jo.put("aps", dict);
        }

        private Map<String, Object> createAlertDict(PushNotification.ComplexAlert complexAlert) {
            Map<String, Object> dict = new HashMap<>();
            if (complexAlert.getBody() != null) {
                dict.put("body", complexAlert.getBody());
            }
            if (complexAlert.getBody() != null) {
                dict.put("action-loc-key", complexAlert.getActionLocKey());
            }
            if (complexAlert.getBody() != null) {
                dict.put("loc-key", complexAlert.getLocKey());
            }
            if (complexAlert.getBody() != null) {
                dict.put("loc-args", complexAlert.getLocArgs());
            }
            if (complexAlert.getBody() != null) {
                dict.put("launch-image", complexAlert.getLaunchImage());
            }
            return dict;
        }

        private void putCustomPayload(JSONObject jo) {
            Map<String, Object> customPayload = mPushNotification.getCustomPayload();
            if (customPayload != null && !customPayload.isEmpty()) {
                for (String key : customPayload.keySet()) {
                    jo.put(key, customPayload.get(key));
                }
            }
        }

    }

}
