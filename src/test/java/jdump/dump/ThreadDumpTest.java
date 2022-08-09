package jdump.dump;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ThreadDumpTest {

    @Test
    public void testThreadDumpContainsExpectedSymbol() throws IOException, ExecutionException, InterruptedException,
            TimeoutException {
        try (JVM jvm = new JVM()) {
            jvm.spawn();
            // TODO: race condition, we should emit a message when child proc is running user code
            Thread.sleep(1_000);
            var threadDump = new ThreadDump(Configuration.defaultConfiguration());
            var vmd = jvm.descriptor();
            threadDump.performFor(vmd);
            var path = Paths.get(threadDump.filenameFor(vmd));
            assertTrue(Files.exists(path));
            try(var lines = Files.lines(path)) {
                assertTrue(lines.anyMatch(line -> line.contains("busyLoop")),
                        "A line in output file contains \"busyloop\"");
            }
            Files.delete(path);
        }
    }

}
