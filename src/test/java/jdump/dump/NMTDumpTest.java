package jdump.dump;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NMTDumpTest {

    @Test
    public void testCanCreateNMTDump() throws IOException, ExecutionException, InterruptedException, TimeoutException {
        try(JVM jvm = new JVM("-XX:NativeMemoryTracking=summary")) {
            jvm.spawn();
            var dump = NMTDump.in(System.getProperty("user.dir"));
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
    public void testCannotCreateNMTDumpWithNMTDisabled() throws IOException {
        try(JVM jvm = new JVM()) {
            jvm.spawn();
            var dump = NMTDump.in(System.getProperty("user.dir"));
            Assertions.assertThrows(RuntimeException.class, () -> dump.performFor(jvm.descriptor()));
        }
    }

}
