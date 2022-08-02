package jdump.dump;

public class Dumps {
    static String DUMP_DIRECTORY = System.getProperty("user.dir");

    public static void handle(String[] args) {
        Arguments arguments = new Arguments(args);
        if (arguments.wantHeapDumpForAll) heapDump();
        if (arguments.wantThreadDumpForAll) threadDump();
        if (arguments.wantJFRForAll) jfrDump(arguments);
        Attach.detachAll();
    }

    public static void jfrDump(Arguments arguments) {
        JFRDump.in(DUMP_DIRECTORY).with(arguments.jfrDuration).performForAll();
    }

    public static void threadDump() {
        ThreadDump.in(DUMP_DIRECTORY).performForAll();
    }

    public static void heapDump() {
        HeapDump.in(DUMP_DIRECTORY).performForAll();
    }

}
