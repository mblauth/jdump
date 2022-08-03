package jdump.dump;

import java.time.Duration;

public class Configuration {
    private boolean wantHeapDumpForAll = false;
    private boolean wantThreadDumpForAll = false;
    private boolean wantJFRForAll = false;
    private Duration jfrDuration = JFRDump.DEFAULT_JFR_DURATION;

    private Configuration() {}

    private Configuration(Configuration configuration) {
        this.wantHeapDumpForAll = configuration.wantHeapDumpForAll;
        this.wantThreadDumpForAll = configuration.wantThreadDumpForAll;
        this.wantJFRForAll = configuration.wantJFRForAll;
        this.jfrDuration = configuration.jfrDuration;
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

        public void wantAllDumps() {
            super.wantHeapDumpForAll();
            super.wantThreadDumpForAll();
            super.wantJfrForAll();
        }
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

    public Duration jfrDuration() {
        return jfrDuration;
    }

}
