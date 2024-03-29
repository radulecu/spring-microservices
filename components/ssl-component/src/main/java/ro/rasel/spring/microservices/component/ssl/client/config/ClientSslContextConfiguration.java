package ro.rasel.spring.microservices.component.ssl.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.resource.ITempFileManager;
import ro.rasel.spring.microservices.component.ssl.client.config.properties.ClientSslContextProperties;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Configuration
public class ClientSslContextConfiguration {
    private final ClientSslContextProperties clientSslContextProperties;
    private final ITempFileManager tempFileManager;

    @Autowired
    public ClientSslContextConfiguration(ClientSslContextProperties clientSslContextProperties, ITempFileManager tempFileManager) {
        this.clientSslContextProperties = clientSslContextProperties;
        this.tempFileManager = tempFileManager;
    }

    public SSLContext clientSSLContext() {
        try {
            final TrustManager[] trustManagers = getTrustManagers(clientSslContextProperties.getTrustStore(),
                    clientSslContextProperties.getTrustStorePassword(),
                    clientSslContextProperties.getTrustStoreType());
            final KeyManager[] keyManagers =
                    getKeyManagers(clientSslContextProperties.getKeyStore(), clientSslContextProperties.getKeyStorePassword(), clientSslContextProperties
                            .getKeyStoreType());

            return getSSLContext(keyManagers, trustManagers);
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException | KeyManagementException | URISyntaxException e) {
            throw new RuntimeException("Failed to load trust store context", e);
        }
    }

    private TrustManager[] getTrustManagers(String trustStore, String trustStorePassword, String type)
            throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, URISyntaxException {
        if (trustStore == null) {
            return new TrustManager[0];
        }
        KeyStore trustStore1 = KeyStore.getInstance(type);

        File trustStoreFile = tempFileManager.copyToTempFile(trustStore, "clientKeyStore");
        trustStore1.load(trustStoreFile.toURI().toURL().openStream(), trustStorePassword.toCharArray());

        TrustManagerFactory trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore1);
        return trustManagerFactory.getTrustManagers();
    }

    private KeyManager[] getKeyManagers(String keyStore, String keyStorePassword, String type)
            throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException,
            UnrecoverableKeyException, URISyntaxException {
        if (keyStore == null) {
            return new KeyManager[0];
        }
        KeyStore keystore = KeyStore.getInstance(type);

        File keyStoreFile = tempFileManager.copyToTempFile(keyStore, "clientKeyStore");
        keystore.load(keyStoreFile.toURI().toURL().openStream(),
                keyStorePassword.toCharArray());

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keystore, keyStorePassword.toCharArray());
        return keyManagerFactory.getKeyManagers();
    }

    private static SSLContext getSSLContext(KeyManager[] keyManagers, TrustManager[] trustManagers)
            throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagers, trustManagers, null);
        return sslContext;
    }

}