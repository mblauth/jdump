package jdump.dump;

import com.sun.tools.attach.AttachNotSupportedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttachTest {

    @Test
    void testAttachAndDetach() throws InterruptedException, IOException, AttachNotSupportedException,
            ExecutionException, TimeoutException {
        try (JVM jvm = new JVM()) {
            jvm.spawn();

            Attach.to(jvm.descriptor());

            assertEquals(1, Attach.attachedVMs.size());
            assertTrue(Attach.attachedVMs.containsKey(Long.toString(jvm.pid())));

            Attach.detachAll();

            assertTrue(Attach.attachedVMs.isEmpty());
        }
    }

}
