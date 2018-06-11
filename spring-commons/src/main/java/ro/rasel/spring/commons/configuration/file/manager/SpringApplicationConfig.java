package ro.rasel.spring.commons.configuration.file.manager;

import java.io.File;

public interface SpringApplicationConfig {
    File getTempDir();

    String getGroup();
}
