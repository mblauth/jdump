package jdump.dump;

/**
 * Provides the main entry point for dumping information from JVMs.
 */
public class Dumps {
    /**
     * Dump everything specified in the given configuration.
     * @param configuration the {@link Configuration} describing what is to be dumped
     */
    public static void handle(Configuration configuration) {
        if (configuration instanceof Configuration.Mutable)
            throw new RuntimeException("Internal error: only accepting immutable configurations");
        if (configuration.heapDumpForAllSet()) new HeapDump(configuration).performForAll();
        if (configuration.threadDumpForAllSet()) new ThreadDump(configuration).performForAll();
        if (configuration.jfrForAllSet()) new JFRDump(configuration).performForAll();
        if (configuration.nmtForAllSet()) new NMTDump(configuration).performForAll();
        if (configuration.mallInfoForAllSet()) new MallInfoDump(configuration).performForAll();
    }

    /**
     * Detach all JVMs to which we are currently attached. This can be called for a clean shutdown.
     */
    public static void detach() {
        Attach.detachAll();
    }


}
