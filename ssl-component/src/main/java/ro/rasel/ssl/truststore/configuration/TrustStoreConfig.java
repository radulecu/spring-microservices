package ro.rasel.ssl.truststore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ro.rasel.commons.utils.resource.ITempFileManager;
import ro.rasel.commons.utils.resource.ResourceUtilities;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Configuration
public class TrustStoreConfig {
    private final Environment env;
    private final ITempFileManager tempFileManager;

    @Autowired
    public TrustStoreConfig(Environment env, ITempFileManager tempFileManager) {
        this.env = env;
        this.tempFileManager = tempFileManager;
    }

    @PostConstruct
    private void configureSSL() throws IOException {
        //set to TLSv1.1 or TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");

        File truststoreFile =
                new ResourceUtilities(tempFileManager).extractTempFileFromClasspath("my.truststore");

        //load the 'javax.net.ssl.trustStore' and 'javax.net.ssl.trustStorePassword' from application.properties
        System.setProperty("javax.net.ssl.trustStore", truststoreFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword",
                env.getProperty("system.property.server.ssl.trust-store-password", "jkspass"));
        System.setProperty("javax.net.ssl.trustStoreType",
                env.getProperty("system.property.server.ssl.trust-store-type", "jks"));
    }
}