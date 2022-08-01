package dump;

public class Dumps {
    static String DUMP_DIRECTORY = System.getProperty("user.dir");

    public static void handle(String[] args) {
        Arguments arguments = new Arguments(args);
        if (arguments.wantHeapDumpForAll) HeapDump.in(DUMP_DIRECTORY).performForAll();
        if (arguments.wantThreadDumpForAll) ThreadDump.in(DUMP_DIRECTORY).performForAll();
        if (arguments.wantJFRForAll) JFRDump.in(DUMP_DIRECTORY).with(arguments.jfrDuration).performForAll();
        Attach.detachAll();
    }

}
