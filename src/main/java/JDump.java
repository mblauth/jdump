public class JDump {

    static String DUMP_DIRECTORY = System.getProperty("user.dir");

    public static void main(String[] args) {
        Arguments arguments = new Arguments(args);
        if (arguments.wantHeapDumpForAll) HeapDump.performForAll();
        if (arguments.wantThreadDumpForAll) ThreadDump.performForAll();
    }

}
