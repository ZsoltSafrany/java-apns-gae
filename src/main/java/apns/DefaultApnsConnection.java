package apns;

import java.net.Socket;

public class DefaultApnsConnection implements ApnsConnection {

    private Socket mSocket;

    public DefaultApnsConnection(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("Socket must not be null");
        }
        mSocket = socket;
    }

    public Socket getSocket() {
        return mSocket;
    }

}
