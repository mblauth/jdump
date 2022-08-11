package jdump.dump;

import jdump.output.Output;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationTest {
    @Test
    public void testDefaultOutputType() {
        var configuration = Configuration.defaultConfiguration();
        assertEquals(Output.TYPE.DIRECTORY, configuration.outputType(), "By default the type is DIRECTORY");
    }

    @Test
    public void testMutableNotImmutable() {
        assertFalse(new Configuration.Mutable().isImmutable());
    }

    @Test
    public void testDefaultIsImmutable() {
        assertTrue(Configuration.defaultConfiguration().isImmutable());
    }
}
