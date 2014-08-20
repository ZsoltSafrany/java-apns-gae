package apns.keystore;

import java.security.KeyStore;
import java.security.KeyStoreException;

public class WrapperKeyStoreProvider implements KeyStoreProvider {

    private final KeyStore mKeyStore;
    private final char[] mKeyStorePassword;

    public WrapperKeyStoreProvider(KeyStore keyStore, char[] keyStorePassword) {
        if (keyStore == null) {
            throw new IllegalArgumentException("keystore must not be null");
        }
        if (keyStorePassword == null) {
            throw new IllegalArgumentException("keyStorePassword must not be null");
        }
        mKeyStore = keyStore;
        mKeyStorePassword = keyStorePassword;
    }

    @Override
    public KeyStore getKeyStore() throws KeyStoreException {
        return mKeyStore;
    }

    @Override
    public char[] getKeyStorePassword() {
        return mKeyStorePassword;
    }

}
