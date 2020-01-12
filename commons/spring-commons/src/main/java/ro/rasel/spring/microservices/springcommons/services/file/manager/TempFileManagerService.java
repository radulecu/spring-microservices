package ro.rasel.spring.microservices.springcommons.services.file.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.rasel.spring.microservices.commons.utils.resource.TempFileManager;
import ro.rasel.spring.microservices.springcommons.config.file.manager.SpringApplicationConfig;

@Service
public class TempFileManagerService extends TempFileManager {

    @Autowired
    public TempFileManagerService(SpringApplicationConfig tempFileManagerConfig) {
        super(tempFileManagerConfig.getTempDir());
        cleanAll();
    }
}
