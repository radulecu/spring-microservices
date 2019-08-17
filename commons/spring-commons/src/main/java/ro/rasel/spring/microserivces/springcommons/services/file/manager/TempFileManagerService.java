package ro.rasel.spring.microserivces.springcommons.services.file.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.rasel.spring.microserivces.commons.utils.resource.TempFileManager;
import ro.rasel.spring.microserivces.springcommons.config.file.manager.SpringApplicationConfig;

@Service
public class TempFileManagerService extends TempFileManager {

    @Autowired
    public TempFileManagerService(SpringApplicationConfig tempFileManagerConfig) {
        super(tempFileManagerConfig.getTempDir());
        cleanAll();
    }
}
