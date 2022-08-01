package dump;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

class Arguments {
    final boolean wantHeapDumpForAll;
    final boolean wantThreadDumpForAll;
    final boolean wantJFRForAll;
    Duration jfrDuration = Duration.ofSeconds(5);

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
        Collections.reverse(argList);
        Optional<String> lastDuration = argList.stream().filter(s -> s.startsWith("-d")).findFirst();
        lastDuration.ifPresent(s -> jfrDuration = Duration.ofSeconds(Long.parseLong(s.substring(2))));
    }
}
