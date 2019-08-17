package ro.rasel.spring.microserivces.commons.utils.resource;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class TempFileManager implements ITempFileManager {
    private final File rootDir;

    public TempFileManager(String path) {
        this(new File(path));
    }

    public TempFileManager(File rootDir) {
        this.rootDir = rootDir;
        rootDir.mkdirs();
        rootDir.deleteOnExit();
    }

    @Override
    public File copyToTempFile(String uri, String name) throws URISyntaxException, IOException {
        return copyToTempFile(new URI(uri), name);
    }

    @Override
    public File copyToTempFile(URI uri, String name) throws IOException {
        URL input = uri.toURL();
        File output = creteTempFile(name);
        FileUtils.copyURLToFile(input, output);
        return output;
    }

    @Override
    public File creteTempFile(String name) throws IOException {
        File file = File.createTempFile("tmp", name, rootDir);
        file.deleteOnExit();
        return file;
    }

    protected void cleanAll() {
        cleanAll(rootDir);
    }

    private void cleanAll(File file) {
        if (file.isDirectory()) {
            Arrays.asList(file.listFiles()).parallelStream().forEach(this::cleanAll);
        } else {
            file.delete();
        }
    }
}
