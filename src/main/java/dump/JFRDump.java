package dump;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class JFRDump {
    private final String outputDirectory;

    private JFRDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static JFRDump in(String outputDirectory) {
        return new JFRDump(outputDirectory);
    }

    void performForAll() {
        System.out.println("Storing JFR dumps for all local JVM processes in " + outputDirectory);
        for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
            try {
                performFor(vmd);
            } catch (Exception e) {
                System.err.println("Could not get JFR dump for JVM " + vmd.id() + ": " + e);
            }
        }
    }

    void performFor(VirtualMachineDescriptor virtualMachineDescriptor)
            throws IOException, AttachNotSupportedException {
        System.out.println("Dumping JFR for JVM " + virtualMachineDescriptor.id());
        HotSpotVirtualMachine hvm = Attach.to(virtualMachineDescriptor);
        InputStream is = hvm.executeJCmd( "JFR.start duration=5s name=jdump filename=" + outputDirectory +
                File.separator + "jdump-jfr-" + virtualMachineDescriptor.id() + ".jfr");
       PrintStreamPrinter.drainUTF8(is, System.out);
    }
}
