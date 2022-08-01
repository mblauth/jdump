package dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.InputStream;
import java.time.Duration;

public class JFRDump extends HotspotDump {
    private final String outputDirectory;
    private Duration jfrDuration = Duration.ofSeconds(5);

    private JFRDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static JFRDump in(String outputDirectory) {
        return new JFRDump(outputDirectory);
    }

    JFRDump with(Duration jfrDuration) {
        this.jfrDuration = jfrDuration;
        return this;
    }

    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping a " + jfrDuration.toSeconds() + " second JFR for JVM " +
                vmd.id());
        try {
            InputStream is = executeCommand(vmd, "jcmd", "JFR.start duration=" +
                    jfrDuration.toSeconds() + "s name=jdump filename=" + filenameFor(vmd));
            PrintStreamPrinter.drainUTF8(is, System.out);
        } catch (Exception e) {
            System.err.println("Failed to dump JFR for JVM " + vmd.id() + ": " + e);
        }
    }

    private String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-jfr-" + virtualMachineDescriptor.id() + ".jfr";
    }
}
