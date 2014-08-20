package apns;

public interface PushNotificationService {

    void send(PushNotification pushNotification, ApnsConnection connection) throws ApnsException;

}