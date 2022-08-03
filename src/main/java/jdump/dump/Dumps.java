package jdump.dump;

public class Dumps {
    static String DUMP_DIRECTORY = System.getProperty("user.dir");

    public static void handle(Configuration configuration) {
        if (configuration instanceof Configuration.Mutable)
            throw new RuntimeException("Internal error: only accepting immutable configurations");
        if (configuration.heapDumpForAllSet()) HeapDump.in(DUMP_DIRECTORY).performForAll();
        if (configuration.threadDumpForAllSet()) ThreadDump.in(DUMP_DIRECTORY).performForAll();
        if (configuration.jfrForAllSet()) JFRDump.in(DUMP_DIRECTORY).with(configuration.jfrDuration()).performForAll();
    }

    public static void detach() {
        Attach.detachAll();
    }


}
