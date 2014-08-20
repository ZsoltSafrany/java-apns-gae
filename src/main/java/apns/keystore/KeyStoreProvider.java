package apns.keystore;

import java.security.KeyStore;
import java.security.KeyStoreException;

public interface KeyStoreProvider {

    KeyStore getKeyStore() throws KeyStoreException;

    char[] getKeyStorePassword();
}
