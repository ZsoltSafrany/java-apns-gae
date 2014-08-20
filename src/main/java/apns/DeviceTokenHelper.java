package apns;

import javax.xml.bind.DatatypeConverter;
import java.util.regex.Pattern;

public class DeviceTokenHelper {

    public static boolean isDeviceTokenValid(String deviceToken) {
        return deviceToken != null && Pattern.matches("[a-fA-F0-9]{64}", deviceToken);
    }

    public static boolean isDeviceTokenValid(byte[] deviceToken) {
        return deviceToken != null && deviceToken.length == 32;
    }

    public static byte[] deviceTokenStringToBytes(String deviceToken) {
        if (!isDeviceTokenValid(deviceToken)) {
            throw new IllegalArgumentException("device token is not valid");
        }
        return DatatypeConverter.parseHexBinary(deviceToken);
    }

    public static String deviceTokenBytesToString(byte[] deviceToken) {
        if (!isDeviceTokenValid(deviceToken)) {
            throw new IllegalArgumentException("device token is not valid");
        }
        return DatatypeConverter.printHexBinary(deviceToken).toLowerCase();
    }

}
