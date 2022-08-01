package dump;

import java.util.Arrays;

class Arguments {
    final boolean wantHeapDumpForAll;
    final boolean wantThreadDumpForAll;

    public Arguments(String[] args) {
        var argList = Arrays.asList(args);
        wantHeapDumpForAll = argList.contains("-H");
        wantThreadDumpForAll = argList.contains("-T");
    }
}
