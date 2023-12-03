package ro.rasel.commons.utils;

import java.io.File;
import java.io.IOException;

public interface IResourceUtilities {
    File extractTempFileFromClasspathToTempFile(String name) throws IOException;
}
