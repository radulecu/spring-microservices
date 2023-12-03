package ro.rasel.spring.commons.utils;

import org.springframework.stereotype.Component;

@Component
public class TempFileManager extends ro.rasel.commons.utils.TempFileManager {

    public TempFileManager() {
        super("tmp");
    }
}
