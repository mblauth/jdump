package jdump.cli;

import jdump.dump.Configuration;
import jdump.dump.Dumps;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CommandLineArguments {
    private final List<String> argList;
    private final Configuration.Mutable configuration = new Configuration.Mutable();

    public CommandLineArguments(String... args) {
        this.argList = Arrays.asList(args);
    }

    public static void handle(String... args) {
        CommandLineArguments commandLineArguments = new CommandLineArguments(args);
        commandLineArguments.handle();
    }

    public void handle() {
        if (argList.size() == 0 || argList.contains("--help")) showUsageInformation();
        configure();
        Dumps.handle(configuration.makeImmutable());
        Dumps.detach();
    }

    private void configure() {
        if (argList.contains("-A")) {
            configuration.wantAllDumps();
        } else {
            if (argList.contains("-H")) configuration.wantHeapDumpForAll();
            if (argList.contains("-T")) configuration.wantThreadDumpForAll();
            if (argList.contains("-J")) configuration.wantJfrForAll();
            if (argList.contains("-N")) configuration.wantNmtForAll();
            if (argList.contains("-M")) configuration.wantMallInfoForAll();
        }
        setDuration(argList);
        setOutputDirectory(argList);
    }

    private void setOutputDirectory(List<String> argList) {
        Optional<String> lastOutputDirectory = getLastOf(argList, "-f");
        lastOutputDirectory.ifPresent(s -> configuration.outputDirectory(s.substring(2)));
    }

    private void setDuration(List<String> argList) {
        Optional<String> lastDuration = getLastOf(argList, "-d");
        lastDuration.ifPresent(s -> configuration.jfrDuration(Long.parseLong(s.substring(2))));
    }

    private static Optional<String> getLastOf(List<String> argList, String parameterPrefix) {
        Collections.reverse(argList);
        return argList.stream().filter(s -> s.startsWith(parameterPrefix)).findFirst();
    }

    private void showUsageInformation() {
        System.out.println("Usage: jdump [-f<folder name>] [-A] [-H] [-J] [-d<duration in seconds>] [-N] [-M] [-T]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("start without options to show UI, if not on a headless system");
        System.out.println("-f<name>: name of the target folder (will be created if non-existent; current working directoy, if empty)");
        System.out.println("-A: produce all non-agent-based types of dumps for all JVMs running locally");
        System.out.println("-H: produce heap dumps for all JVMs running locally");
        System.out.println("-J: produce JFRs for all JVMs running locally");
        System.out.println("-d<duration in seconds>: the duration selected for the JFRs, in seconds, default: 5");
        System.out.println("-M: produce mallinfo() stats on Linux with glibc");
        System.out.println("-N: product Native Memory Tracks for all JVMs running locally");
        System.out.println("-T: produce thread dumps for all JVMs running locally");
    }
}
