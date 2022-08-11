package jdump.output;

import jdump.dump.Configuration;

import java.nio.file.Path;

public interface Output {
    Output file(String filename);
    Path path();
    boolean aLineMatches(String matchString);
    void deleteFile();

    static Output using(Configuration configuration) {
        switch (configuration.outputType()) {
            case FILE:
                return new FileOutput(configuration.outputDirectory());
            case DIRECTORY:
                return new DirectoryOutput(configuration.outputDirectory());
            default: throw new RuntimeException("Internal error: could not handle output type");
        }
    }

    enum TYPE {
        FILE, DIRECTORY
    }
}
