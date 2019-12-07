package ro.rasel.spring.microserivces.component.ssl.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microserivces.commons.utils.resource.ITempFileManager;
import ro.rasel.spring.microserivces.component.ssl.client.config.properties.ClientSslContextProperties;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Configuration
public class JavaClientSslConfig {
    private final ClientSslContextProperties clientSslContextProperties;
    private final ITempFileManager tempFileManager;

    @Autowired
    public JavaClientSslConfig(
            ClientSslContextProperties clientSslContextProperties,
            ITempFileManager tempFileManager) {
        this.clientSslContextProperties = clientSslContextProperties;
        this.tempFileManager = tempFileManager;
    }

    @PostConstruct
    private void configureSSL() throws IOException, URISyntaxException {
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

        System.setProperty("https.protocols", clientSslContextProperties.getProtocol());
        System.setProperty("javax.net.debug", "true");
        //load the 'javax.net.ssl.trustStore' and 'javax.net.ssl.trustStorePassword' from application.properties

        final String keyStore = clientSslContextProperties.getKeyStore();
        if (keyStore != null) {
            final String keyStorePassword = clientSslContextProperties.getKeyStorePassword();
            if (keyStorePassword == null) {
                throw new IllegalStateException("Password not set");
            }

            File keyStoreFile = tempFileManager.copyToTempFile(keyStore, "clientKeyStore");
            System.setProperty("javax.net.ssl.keyStore", keyStoreFile.getAbsolutePath());
            System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
            System.setProperty("javax.net.ssl.trustStoreType", clientSslContextProperties.getKeyStoreType());
        }

        final String trustStore = clientSslContextProperties.getTrustStore();
        if (trustStore != null) {
            final String trustStorePassword = clientSslContextProperties.getTrustStorePassword();
            if (trustStorePassword == null) {
                throw new IllegalStateException("Password not set");
            }

            File trustStoreFile = tempFileManager.copyToTempFile(trustStore, "clientTrustStore");
            System.setProperty("javax.net.ssl.trustStore", trustStoreFile.getAbsolutePath());
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
            System.setProperty("javax.net.ssl.trustStoreType", clientSslContextProperties.getTrustStoreType());
        }

        if (clientSslContextProperties.getDebug() != null) {
            System.setProperty("javax.net.debug", clientSslContextProperties.getDebug());
        }

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("localhost"));
    }
}