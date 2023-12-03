package ro.rasel.commons.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ResourceUtilities {

    private final TempFileManager tempFileManager;

    public ResourceUtilities(TempFileManager tempFileManager) {
        this.tempFileManager = tempFileManager;
    }

    public File extractTempFileFromClasspathToTempFile(String name) throws IOException {
        URL input = getClass().getResource("/" + name);
        File output = tempFileManager.creteFile(name);
        FileUtils.copyURLToFile(input, output);
        return output;
    }
}
