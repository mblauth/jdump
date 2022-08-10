package jdump.dump;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}