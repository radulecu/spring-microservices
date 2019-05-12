package ro.rasel.spring.commons.config.file.manager;

import java.io.File;

public interface SpringApplicationConfig {
    File getTempDir();

    String getGroup();
}
