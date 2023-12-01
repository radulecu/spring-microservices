package ro.rasel.spring.microservices.common.utils.resource;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class TempFileManager implements ITempFileManager {
    private final File rootDir;

    public TempFileManager(String path) {
        this(new File(path));
    }

    public TempFileManager(File rootDir) {
        this.rootDir = Objects.requireNonNull(rootDir);
        //noinspection ResultOfMethodCallIgnored
        rootDir.mkdirs();
        rootDir.deleteOnExit();
    }

    @Override
    public File copyToTempFile(String uri, String name) throws URISyntaxException, IOException {
        return copyToTempFile(new URI(uri), name);
    }

    @Override
    public File copyToTempFile(URI uri, String name) throws IOException {
        File output = creteTempFile(name);
        if (uri.getScheme().equals("classpath")) {
            Files.copy(this.getClass().getResourceAsStream("/" + uri.getSchemeSpecificPart()), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            URL input = uri.toURL();
            FileUtils.copyURLToFile(input, output);
        }
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

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void cleanAll(File file) {
        if (file.isDirectory()) {
            Arrays.asList(Optional.ofNullable(file.listFiles()).orElse(new File[0])).parallelStream().forEach(this::cleanAll);
        } else {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }
}
