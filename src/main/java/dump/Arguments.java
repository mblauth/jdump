package dump;

import java.util.Arrays;

class Arguments {
    final boolean wantHeapDumpForAll;
    final boolean wantThreadDumpForAll;
    final boolean wantJFRForAll;

    public Arguments(String[] args) {
        var argList = Arrays.asList(args);
        if (argList.contains("-A")) {
            wantHeapDumpForAll = true;
            wantThreadDumpForAll = true;
            wantJFRForAll = true;
        } else {
            wantHeapDumpForAll = argList.contains("-H");
            wantThreadDumpForAll = argList.contains("-T");
            wantJFRForAll = argList.contains("-J");
        }
    }
}
