package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.time.Duration;


/**
 * Supports dumping Java Flight Recordings.
 */
class JFRDump extends HotspotDump {
    public static Duration DEFAULT_JFR_DURATION = Duration.ofSeconds(5);

    private final String outputDirectory;
    private final Duration jfrDuration;

    JFRDump(Configuration configuration) {
        if (!configuration.isImmutable()) throw new IllegalArgumentException("Mutable configuration passed");
        this.outputDirectory = configuration.outputDirectory();
        this.jfrDuration = configuration.jfrDuration();
    }

    @Override
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping a " + jfrDuration.toSeconds() + " second JFR for JVM " + vmd.id());
        execAndPrint(vmd, "jcmd", "JFR.start duration=" +
                jfrDuration.toSeconds() + "s name=jdump filename=" + filenameFor(vmd));
    }

    @Override
    String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-jfr-" + virtualMachineDescriptor.id() + ".jfr";
    }
}
