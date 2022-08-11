package jdump.output;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOutput implements Output {
    private File file;
    private final String directoryName;

    FileOutput(String directoryName) {
        this.directoryName = directoryName;
    }

    @Override
    public FileOutput file(String filename) {
        file = new File(directoryName + File.separator + filename);
        return this;
    }

    @Override
    public Path path() {
        return file.toPath();
    }

    @Override
    public boolean aLineMatches(String matchString) {
        try (var lines = Files.lines(path())) {
            return lines.anyMatch(line -> line.contains(matchString));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile() {
        file.delete();
    }
}
