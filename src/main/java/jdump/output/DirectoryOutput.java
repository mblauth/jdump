package jdump.output;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class DirectoryOutput implements Output {
    private final File directory;
    private File currentFile;

    DirectoryOutput(String dirname) {
        directory = new File(dirname);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Could not create directory " + dirname);
        }
    }

    @Override
    public Output file(String filename) {
        currentFile = new File(directory.getPath() + File.separator + filename);
        return this;
    }

    @Override
    public Path path() {
        return currentFile.toPath();
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
        currentFile.delete();
    }

}
