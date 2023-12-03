package ro.rasel.security.client.utils;

import org.springframework.stereotype.Component;

@Component
public class TempFileManager extends ro.rasel.commons.utils.TempFileManager{

    public TempFileManager() {
        super("tmp");
    }
}
