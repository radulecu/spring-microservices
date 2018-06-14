package ro.rasel.commons.utils.resource;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ResourceUtilities implements IResourceUtilities {

    private final ITempFileManager tempFileManager;

    public ResourceUtilities(ITempFileManager tempFileManager) {
        this.tempFileManager = tempFileManager;
    }

    @Override
    public File extractTempFileFromClasspath(String name) throws IOException {
        URL input = getClass().getResource("/" + name);
        File output = tempFileManager.creteFile(name);
        FileUtils.copyURLToFile(input, output);
        return output;
    }
}
