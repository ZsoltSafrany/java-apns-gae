package apns;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DefaultFeedbackService implements FeedbackService {

    public List<FailedDeviceToken> read(ApnsConnection connection) throws ApnsException {
        if (connection == null) {
            throw new IllegalArgumentException("connection must not be null");
        }
        List<FailedDeviceToken> tokens = new LinkedList<>();

        DataInputStream dis;
        try {
            dis = new DataInputStream(connection.getSocket().getInputStream());
        } catch (IOException e) {
            throw new ApnsException("Could not get input stream of socket", e);
        }

        try {
            readStream(dis, tokens);
        } catch (IOException e) {
            throw new ApnsException("Could not read socket", e);
        }
        return tokens;
    }

    private void readStream(DataInputStream dis, List<FailedDeviceToken> tokens) throws IOException {
        while (true) {
            long timestampInSec;
            try {
                timestampInSec = dis.readInt() & 0xffffffffL;
            } catch (EOFException e) {
                break;
            }
            int tokenLength = dis.readShort();
            byte[] token = new byte[32];
            dis.read(token);
            tokens.add(new FailedDeviceToken(new Date(timestampInSec * 1000), DeviceTokenHelper.deviceTokenBytesToString(token)));
        }
    }

}