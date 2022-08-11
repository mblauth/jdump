package jdump.dump;

import jdump.output.Output;

import java.time.Duration;

/**
 * The Configuration holds all state for describing a dump setup. It is immutable and can be used in the future for
 * storing configurations in the file system. It features a mutable version in the inner class {@link Mutable} that
 * can be used in interactive versions of the tool. It provides makeImmutable() to transfer it into the immutable
 * version. The core dump logic only supports the immutable version.
 */
public class Configuration {
    private boolean wantHeapDumpForAll = false;
    private boolean wantThreadDumpForAll = false;
    private boolean wantJFRForAll = false;
    private Duration jfrDuration = JFRDump.DEFAULT_JFR_DURATION;
    private boolean wantNmtForAll = false;
    private String outputDirectory = System.getProperty("user.dir");
    private Output.TYPE outputType = Output.TYPE.DIRECTORY;
    private boolean immutable;
    private boolean wantMallInfoForAll = false;

    private Configuration() {
        immutable = true;
    }

    private Configuration(Configuration configuration) {
        this.wantHeapDumpForAll = configuration.wantHeapDumpForAll;
        this.wantThreadDumpForAll = configuration.wantThreadDumpForAll;
        this.wantJFRForAll = configuration.wantJFRForAll;
        this.jfrDuration = configuration.jfrDuration;
        this.wantNmtForAll = configuration.wantNmtForAll;
        this.outputDirectory = configuration.outputDirectory;
        this.outputType = Output.TYPE.DIRECTORY;
        this.wantMallInfoForAll = configuration.wantMallInfoForAll;
        immutable = true;
    }

    public static class Mutable extends Configuration {
        public Mutable() {
            super.immutable = false;
        }

        public Configuration makeImmutable() {
            return new Configuration(this);
        }

        public Mutable jfrDuration(long durationInSeconds) {
            super.jfrDuration(Duration.ofSeconds(durationInSeconds));
            return this;
        }

        public void wantHeapDumpForAll() {
            super.wantHeapDumpForAll();
        }

        public void wantThreadDumpForAll() {
            super.wantThreadDumpForAll();
        }

        public Mutable wantJfrForAll() {
            super.wantJfrForAll();
            return this;
        }

        public Mutable wantNmtForAll() {
            super.wantNmtForAll();
            return this;
        }

        public void wantAllDumps() {
            super.wantHeapDumpForAll();
            super.wantThreadDumpForAll();
            super.wantJfrForAll();
            super.wantNmtForAll();
        }

        public Mutable outputDirectory(String directoryName) {
            super.outputDirectory(directoryName);
            return this;
        }

        public Mutable outputType(Output.TYPE outputType) {
            super.outputType(outputType);
            return this;
        }

        public void wantMallInfoForAll() {
            super.wantMallInfoForAll();
        }
    }

    private void wantMallInfoForAll() {
        this.wantMallInfoForAll = true;
    }

    private void outputType(Output.TYPE outputType) {
        this.outputType = outputType;
    }

    private void outputDirectory(String directoryName) {
        this.outputDirectory = directoryName;
    }

    private void wantNmtForAll() {
        this.wantNmtForAll = true;
    }

    private void wantHeapDumpForAll() {
        this.wantHeapDumpForAll = true;
    }

    private void wantThreadDumpForAll() {
        this.wantThreadDumpForAll = true;
    }

    private void wantJfrForAll() {
        this.wantJFRForAll = true;
    }

    private void jfrDuration(Duration jfrDuration) {
        this.jfrDuration = jfrDuration;
    }

    public boolean heapDumpForAllSet() {
        return wantHeapDumpForAll;
    }

    public boolean threadDumpForAllSet() {
        return wantThreadDumpForAll;
    }

    public boolean jfrForAllSet() {
        return wantJFRForAll;
    }

    public boolean nmtForAllSet() {
        return wantNmtForAll;
    }

    public boolean mallInfoForAllSet() {
        return wantMallInfoForAll;
    }

    public String outputDirectory() {
        return outputDirectory;
    }

    public Duration jfrDuration() {
        return jfrDuration;
    }

    public Output.TYPE outputType() {
        return outputType;
    }

    static Configuration defaultConfiguration() {
        return new Configuration();
    }

    public boolean isImmutable() {
        return immutable;
    }
}
