package jdump.dump;

/**
 * Provides the main entry point for dumping information from JVMs.
 */
public class Dumps {
    static String DUMP_DIRECTORY = System.getProperty("user.dir");

    /**
     * Dump everything specified in the given configuration.
     * @param configuration the {@link Configuration} describing what is to be dumped
     */
    public static void handle(Configuration configuration) {
        if (configuration instanceof Configuration.Mutable)
            throw new RuntimeException("Internal error: only accepting immutable configurations");
        if (configuration.heapDumpForAllSet()) HeapDump.in(DUMP_DIRECTORY).performForAll();
        if (configuration.threadDumpForAllSet()) ThreadDump.in(DUMP_DIRECTORY).performForAll();
        if (configuration.jfrForAllSet()) JFRDump.in(DUMP_DIRECTORY).with(configuration.jfrDuration()).performForAll();
        if (configuration.nmtForAllSet()) NMTDump.in(DUMP_DIRECTORY).performForAll();
    }

    /**
     * Detach all JVMs to which we are currently attached. This can be called for a clean shutdown.
     */
    public static void detach() {
        Attach.detachAll();
    }


}
