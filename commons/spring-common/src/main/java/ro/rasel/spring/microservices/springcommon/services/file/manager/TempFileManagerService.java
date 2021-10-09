package ro.rasel.spring.microservices.springcommon.services.file.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.rasel.spring.microservices.common.utils.resource.TempFileManager;
import ro.rasel.spring.microservices.springcommon.config.file.manager.SpringApplicationConfig;

@Service
public class TempFileManagerService extends TempFileManager {

    @Autowired
    public TempFileManagerService(SpringApplicationConfig tempFileManagerConfig) {
        super(tempFileManagerConfig.getTempDir());
        cleanAll();
    }
}
