package ro.rasel.spring.commons.configuration.file.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ConfigurationProperties("spring.application")
public class SpringApplicationConfig {
    private File tempDir;
    private String group="spring";

    public File getTempDir() {
        return tempDir;
    }

    public void setTempDir(File tempDir) {
        this.tempDir = tempDir;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
