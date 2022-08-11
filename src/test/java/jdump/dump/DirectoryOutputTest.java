package jdump.dump;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectoryOutputTest {

    /* Tests that with more than one dump selected, the output is by default moved into a directory. */
    @Test
    public void testAutomaticDirectoryOutput() throws ExecutionException, InterruptedException, TimeoutException, IOException {
        try (JVM jvm = new JVM("-XX:NativeMemoryTracking=summary")) {
            var directoryName = System.getProperty("user.dir") + File.separator + "foo";
            jvm.spawn();
            Configuration configuration = new Configuration.Mutable()
                    .wantNmtForAll()
                    .wantJfrForAll()
                    .jfrDuration(1)
                    .outputDirectory(directoryName)
                    .makeImmutable();
            var nmtDump = new NMTDump(configuration);
            nmtDump.performFor(jvm.descriptor());
            var jfrDump = new JFRDump(configuration);
            jfrDump.performFor(jvm.descriptor());
            Thread.sleep(1_500); // wait for JVM to dump jfr
            var path = Path.of(directoryName);
            assertTrue(Files.exists(path), "Folder " + directoryName + " was created");
            int filesDeleted = 0;
            for (File file : Objects.requireNonNull(path.toFile().listFiles())) {
                Files.delete(file.toPath());
                filesDeleted++;
            }
            assertEquals(2, filesDeleted, "Two files were created");
            Files.delete(path);
        }
    }


}

