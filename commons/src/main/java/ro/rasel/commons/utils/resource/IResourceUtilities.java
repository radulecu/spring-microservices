package ro.rasel.commons.utils.resource;

import java.io.File;
import java.io.IOException;

public interface IResourceUtilities {
    File extractTempFileFromClasspath(String name) throws IOException;
}
