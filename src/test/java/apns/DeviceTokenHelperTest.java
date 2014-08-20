package apns;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeviceTokenHelperTest {

    public static final String VALID_TOKEN = "777d2ac490a17aa1d4c8a6ec7c50d4b1b9a36499acd45bf5fcac103cde038eff";

    @Test
    public void testIsDeviceTokenValid() {
        assertTrue(DeviceTokenHelper.isDeviceTokenValid(VALID_TOKEN.toLowerCase()));
        assertTrue(DeviceTokenHelper.isDeviceTokenValid(VALID_TOKEN.toUpperCase()));
        assertTrue(DeviceTokenHelper.isDeviceTokenValid(DeviceTokenHelper.deviceTokenStringToBytes(VALID_TOKEN)));

        assertFalse(DeviceTokenHelper.isDeviceTokenValid("777d2ac490a1 7aa1d4c8a6ec7c50d4b1b9a36499acd45bf5fcac103cde038eff"));
        assertFalse(DeviceTokenHelper.isDeviceTokenValid("777d2ac490a1-7aa1d4c8a6ec7c50d4b1b9a36499acd45bf5fcac103cde038eff"));
        assertFalse(DeviceTokenHelper.isDeviceTokenValid("777d2ac490a17aa1d4c8a6ec7c50d4b1b9a36499acd45bf5fcac103cde038e"));
    }

    @Test
    public void testConversion() throws Exception {
        assertTrue(VALID_TOKEN.equalsIgnoreCase(DeviceTokenHelper.deviceTokenBytesToString(DeviceTokenHelper.deviceTokenStringToBytes(VALID_TOKEN))));
    }

}


