package apns;


import apns.keystore.KeyStoreProvider;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class DefaultApnsConnectionFactory implements ApnsConnectionFactory {

    public static class Builder {

        private static final String PUSH_HOST_PRODUCTION = "gateway.push.apple.com";
        private static final String PUSH_HOST_SANDBOX = "gateway.sandbox.push.apple.com";
        private static final int PUSH_PORT = 2195;

        private static final String FEEDBACK_HOST_PRODUCTION = "feedback.push.apple.com";
        private static final String FEEDBACK_HOST_SANDBOX = "feedback.sandbox.push.apple.com";
        private static final int FEEDBACK_PORT = 2196;

        private KeyStoreProvider mKeyStoreProvider;
        private String mPushHost;
        private String mFeedbackHost;

        public static Builder get() {
            return new Builder();
        }

        public Builder setSandboxKeyStoreProvider(KeyStoreProvider keyStoreProvider) {
            return setHostAndKeyStoreProvider(PUSH_HOST_SANDBOX, FEEDBACK_HOST_SANDBOX, keyStoreProvider);
        }

        public Builder setProductionKeyStoreProvider(KeyStoreProvider keyStoreProvider) {
            return setHostAndKeyStoreProvider(PUSH_HOST_PRODUCTION, FEEDBACK_HOST_PRODUCTION, keyStoreProvider);
        }

        private Builder setHostAndKeyStoreProvider(String pushHost, String feedbackHost, KeyStoreProvider keyStoreProvider) {
            mPushHost = pushHost;
            mFeedbackHost = feedbackHost;
            mKeyStoreProvider = keyStoreProvider;
            return this;
        }

        public ApnsConnectionFactory build() throws ApnsException {
            if (mKeyStoreProvider == null) {
                throw new ApnsException("No keystore was set");
            }

            KeyManagerFactory kmf;
            final String algorithm = KeyManagerFactory.getDefaultAlgorithm();
            try {
                kmf = KeyManagerFactory.getInstance(algorithm);
            } catch (NoSuchAlgorithmException e) {
                throw new ApnsException(String.format("Could not create KeyManagerFactory with algorithm [%s]", algorithm), e);
            }
            try {
                kmf.init(mKeyStoreProvider.getKeyStore(), mKeyStoreProvider.getKeyStorePassword());
            } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
                throw new ApnsException("Could not init KeyManagerFactory", e);
            }

            final String protocol = "TLS";
            SSLContext sslContext;
            try {
                sslContext = SSLContext.getInstance(protocol);
            } catch (NoSuchAlgorithmException e) {
                throw new ApnsException(String.format("Could not create SSL context with protocol [%s]", protocol), e);
            }
            try {
                sslContext.init(kmf.getKeyManagers(), null, null);
            } catch (KeyManagementException e) {
                throw new ApnsException("Could not init SSL context", e);
            }
            return new DefaultApnsConnectionFactory(sslContext, mPushHost, PUSH_PORT, mFeedbackHost, FEEDBACK_PORT);
        }

    }

    private final SSLContext mSSLContext;
    private final String mPushHost;
    private final int mPushPort;
    private final String mFeedbackHost;
    private final int mFeedbackPort;

    public DefaultApnsConnectionFactory(SSLContext sslContext, String pushHost, int pushPort, String feedbackHost, int feedbackPort) {
        if (sslContext == null) {
            throw new IllegalArgumentException("sslContext must not be null");
        }
        if (pushHost == null) {
            throw new IllegalArgumentException("pushHost must not be null");
        }
        if (feedbackHost == null) {
            throw new IllegalArgumentException("feedbackHost must not be null");
        }
        mSSLContext = sslContext;
        mPushHost = pushHost;
        mPushPort = pushPort;
        mFeedbackHost = feedbackHost;
        mFeedbackPort = feedbackPort;
    }

    @Override
    public ApnsConnection openPushConnection() throws ApnsException {
        try {
            return new DefaultApnsConnection(mSSLContext.getSocketFactory().createSocket(mPushHost, mPushPort));
        } catch (IOException e) {
            throw new ApnsException("Could not create socket", e);
        }
    }

    @Override
    public ApnsConnection openFeedbackConnection() throws ApnsException {
        try {
            return new DefaultApnsConnection(mSSLContext.getSocketFactory().createSocket(mFeedbackHost, mFeedbackPort));
        } catch (IOException e) {
            throw new ApnsException("Could not create socket", e);
        }
    }

}
