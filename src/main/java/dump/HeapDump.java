package dump;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.IOException;
import java.io.InputStream;

class HeapDump {
    private final String outputDirectory;

    private HeapDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static HeapDump in(String outputDirectory) {
        return new HeapDump(outputDirectory);
    }

    /**
     * Dumps heaps for all compatible JVMs currently reachable via the dump.Attach API. The heap dumps are generated as
     * <pid>.hprof in the current working directory of jdump.
     */
    void performForAll() {
        System.out.println("Storing heap dumps for all local JVM processes in " + outputDirectory);
        for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
            try {
                performFor(vmd);
            } catch (Exception e) {
                System.err.println("Could not get heap dump for JVM " + vmd.id() + ": " + e);
            }
        }
    }

    /**
     * Perform a heap dump for a given JVM process.
     *
     * @param virtualMachineDescriptor the {@link VirtualMachineDescriptor} of the JVM process
     */
    void performFor(VirtualMachineDescriptor virtualMachineDescriptor)
            throws IOException, AttachNotSupportedException {
        HotSpotVirtualMachine hvm = Attach.to(virtualMachineDescriptor);
        InputStream is =
                hvm.dumpHeap(  outputDirectory + "/jdump-heap-" + virtualMachineDescriptor.id() + ".hprof");
        if (PrintStreamPrinter.drainUTF8(is, System.out) == 0) {
            System.out.println("done");
        }
    }
}
