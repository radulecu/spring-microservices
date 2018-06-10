package ro.rasel.commons.utils;

import java.io.File;
import java.util.Arrays;

public class TempFileManager implements ITempFileManager {
    private final File rootDir;

    public TempFileManager(String path) {
        this(new File(path));
    }

    public TempFileManager(File rootDir) {
        this.rootDir = rootDir;
        rootDir.deleteOnExit();
    }

    @Override
    public File creteFile(String relativePath) {
        File file = new File(rootDir, relativePath);
        file.deleteOnExit();
        return file;
    }

    public void cleanAll() {
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
