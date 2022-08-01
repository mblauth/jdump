import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;
import sun.tools.common.PrintStreamPrinter;

import java.io.IOException;
import java.io.InputStream;

class HeapDump {

    /**
     * Dumps heaps for all compatible JVMs currently reachable via the Attach API. The heap dumps are generated as
     * <pid>.hprof in the current working directory of jdump.
     */
    static void performForAll() {
        System.out.println("Storing heap dumps for all local JVM processes in " + JDump.DUMP_DIRECTORY);
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
    static void performFor(VirtualMachineDescriptor virtualMachineDescriptor)
            throws IOException, AttachNotSupportedException {
        HotSpotVirtualMachine hvm = Attach.to(virtualMachineDescriptor);
        InputStream is =
                hvm.dumpHeap( JDump.DUMP_DIRECTORY + "/jdump-heap-" + virtualMachineDescriptor.id() + ".hprof");
        if (PrintStreamPrinter.drainUTF8(is, System.out) == 0) {
            System.out.println("done");
        }
    }
}
