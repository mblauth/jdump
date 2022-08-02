package dump;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class Arguments {
    final boolean wantHeapDumpForAll;
    final boolean wantThreadDumpForAll;
    final boolean wantJFRForAll;
    Duration jfrDuration = Duration.ofSeconds(5);

    public Arguments(String[] args) {
        var argList = Arrays.asList(args);
        if (args.length == 0 || argList.contains("--help")) showUsageInformation();
        if (argList.contains("-A")) {
            wantHeapDumpForAll = true;
            wantThreadDumpForAll = true;
            wantJFRForAll = true;
        } else {
            wantHeapDumpForAll = argList.contains("-H");
            wantThreadDumpForAll = argList.contains("-T");
            wantJFRForAll = argList.contains("-J");
        }
        setDuration(argList);
    }

    private void setDuration(List<String> argList) {
        Collections.reverse(argList);
        Optional<String> lastDuration = argList.stream().filter(s -> s.startsWith("-d")).findFirst();
        lastDuration.ifPresent(s -> jfrDuration = Duration.ofSeconds(Long.parseLong(s.substring(2))));
    }

    private static void showUsageInformation() {
        System.out.println("Usage: jdump [-A] [-H] [-T] [-J] [-d<duration in seconds>]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("-A: produce all types of dumps for all JVMs running locally");
        System.out.println("-H: produce heap dumps for all JVMs running locally");
        System.out.println("-T: produce thread dumps for all JVMs running locally");
        System.out.println("-J: produce JFRs for all JVMs running locally");
        System.out.println("-d<duration in seconds>: the duration selected for the JFRs, in seconds, default: 5");
    }
}
