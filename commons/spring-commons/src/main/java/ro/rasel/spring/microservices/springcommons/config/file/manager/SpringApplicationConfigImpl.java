package ro.rasel.spring.microservices.springcommons.config.file.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties("spring.application")
public class SpringApplicationConfigImpl implements SpringApplicationConfig {
    private File tempDir;

    @Override
    public File getTempDir() {
        return tempDir;
    }

    public void setTempDir(File tempDir) {
        this.tempDir = tempDir;
    }
}
