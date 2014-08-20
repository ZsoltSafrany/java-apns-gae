package apns;

import java.net.Socket;

public class MockApnsConnection implements ApnsConnection {

    @Override
    public Socket getSocket() {
        return null;
    }
}
