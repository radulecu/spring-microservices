package ro.rasel.commons.utils;

import java.io.File;

public class TempFileManager {
    private final File rootDir;

    public TempFileManager(String name) {
        this(new File(name));
    }

    public TempFileManager(File rootDir) {
        this.rootDir = rootDir;
        rootDir.deleteOnExit();
    }

    public File creteFile(String path) {
        File file = new File(rootDir, path);
        file.deleteOnExit();
        return file;
    }
}
