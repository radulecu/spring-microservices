package ro.rasel.tls.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ro.rasel.commons.utils.ResourceUtilities;
import ro.rasel.spring.commons.utils.TempFileManager;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Configuration
@ComponentScan(basePackageClasses = TempFileManager.class)
public class SSLConfig {
    private final Environment env;
    private final TempFileManager tempFileManager;

    @Autowired
    public SSLConfig(Environment env, TempFileManager tempFileManager) {
        this.env = env;
        this.tempFileManager = tempFileManager;
    }

    @PostConstruct
    private void configureSSL() throws IOException {
        //set to TLSv1.1 or TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");

        File truststoreFile =
                new ResourceUtilities(tempFileManager).extractTempFileFromClasspathToTempFile("my.truststore");

        //load the 'javax.net.ssl.trustStore' and 'javax.net.ssl.trustStorePassword' from application.properties
        System.setProperty("javax.net.ssl.trustStore", truststoreFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.trustStorePassword",
                env.getProperty("server.ssl.trust-store-password", "jkspass"));
        System.setProperty("javax.net.ssl.trustStoreType", env.getProperty("server.ssl.trust-store-type", "jks"));
    }
}