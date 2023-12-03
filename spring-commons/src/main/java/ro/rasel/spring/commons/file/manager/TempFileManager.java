package ro.rasel.spring.commons.file.manager;

import org.springframework.stereotype.Component;

@Component
public class TempFileManager extends ro.rasel.commons.utils.TempFileManager {

    public TempFileManager() {
        super("tmp");
    }
}
