package jdump.dump;

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

    private Configuration() {}

    private Configuration(Configuration configuration) {
        this.wantHeapDumpForAll = configuration.wantHeapDumpForAll;
        this.wantThreadDumpForAll = configuration.wantThreadDumpForAll;
        this.wantJFRForAll = configuration.wantJFRForAll;
        this.jfrDuration = configuration.jfrDuration;
        this.wantNmtForAll = configuration.wantNmtForAll;
    }

    public static class Mutable extends Configuration {
        public Mutable() {}

        public Configuration makeImmutable() {
            return new Configuration(this);
        }

        public void jfrDuration(long durationInSeconds) {
            super.jfrDuration(Duration.ofSeconds(durationInSeconds));
        }

        public void wantHeapDumpForAll() {
            super.wantHeapDumpForAll();
        }

        public void wantThreadDumpForAll() {
            super.wantThreadDumpForAll();
        }

        public void wantJfrForAll() {
            super.wantJfrForAll();
        }

        public void wantNmtForAll() {
            super.wantNmtForAll();
        }

        public void wantAllDumps() {
            super.wantHeapDumpForAll();
            super.wantThreadDumpForAll();
            super.wantJfrForAll();
            super.wantNmtForAll();
        }
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

    public Duration jfrDuration() {
        return jfrDuration;
    }

}
