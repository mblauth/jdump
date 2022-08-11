package jdump.dump;

import jdump.output.Output;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class NMTDumpTest {

    @Test
    public void testCanCreateNMTDump() throws IOException, ExecutionException, InterruptedException, TimeoutException {
        try(JVM jvm = new JVM("-XX:NativeMemoryTracking=summary")) {
            jvm.spawn();
            var dump = new NMTDump(Configuration.defaultConfiguration());
            var vmd = jvm.descriptor();
            dump.performFor(vmd);
            var path = Paths.get(dump.filenameFor(vmd));
            assertTrue(Files.exists(path));
            try (var lines = Files.lines(path)) {
                assertTrue(lines.anyMatch(line -> line.contains("Java Heap")),
                        "A line in output file contains \"Java Heap\"");
            }
            Files.delete(path);
        }
    }

    @Test
    public void testCannotCreateNMTDumpWithNMTDisabled() throws
            IOException, ExecutionException, InterruptedException, TimeoutException {
        try(JVM jvm = new JVM()) {
            jvm.spawn();
            var dump = new NMTDump(Configuration.defaultConfiguration());
            dump.performFor(jvm.descriptor());
            assertFalse(Files.exists(Path.of(dump.filenameFor(jvm.descriptor()))), "Output file created");
        }
    }

    @Test
    public void testDirectoryOutput() throws ExecutionException, InterruptedException, TimeoutException, IOException {
        try (JVM jvm = new JVM("-XX:NativeMemoryTracking=summary")) {
            var directoryName = System.getProperty("user.dir") + File.separator + "foo";
            jvm.spawn();
            var dump = new NMTDump(new Configuration.Mutable()
                    .outputDirectory(directoryName)
                    .outputType(Output.TYPE.DIRECTORY)
                    .makeImmutable());
            dump.performFor(jvm.descriptor());
            var path = Path.of(directoryName);
            assertTrue(Files.exists(path), "Folder " + directoryName + " was created");
            int filesDeleted = 0;
            for (File file : Objects.requireNonNull(path.toFile().listFiles())) {
                Files.delete(file.toPath());
                filesDeleted++;
            }
            assertEquals(1, filesDeleted, "One file was created");
            Files.delete(path);
        }
    }

}
