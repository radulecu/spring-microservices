package ro.rasel.spring.microservices.common.utils.resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SuppressWarnings("unused")
public interface ITempFileManager {
    File copyToTempFile(String uri, String name) throws URISyntaxException, IOException;

    File copyToTempFile(URI uri, String name) throws IOException;

    File creteTempFile(String name) throws IOException;
}
