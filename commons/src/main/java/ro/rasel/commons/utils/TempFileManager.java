package ro.rasel.commons.utils;

import java.io.File;

public class TempFileManager implements ITempFileManager {
    private final File rootDir;

    public TempFileManager(String name) {
        this(new File(name));
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
}
