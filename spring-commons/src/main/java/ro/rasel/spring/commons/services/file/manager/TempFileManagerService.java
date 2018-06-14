package ro.rasel.spring.commons.services.file.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.rasel.commons.utils.resource.TempFileManager;
import ro.rasel.spring.commons.configuration.file.manager.SpringApplicationConfig;

@Service
public class TempFileManagerService extends TempFileManager {

    @Autowired
    public TempFileManagerService(SpringApplicationConfig tempFileManagerConfig) {
        super(tempFileManagerConfig.getTempDir());
        cleanAll();
    }
}
