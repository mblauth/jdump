import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;
import sun.tools.common.PrintStreamPrinter;

import java.io.InputStream;

public class JDump {

    /**
     * Dumps heaps for all compatible JVMs currently reachable via the Attach API. The heap dumps are generated as
     * <pid>.hprof in the current working directory of jdump.
     */
    public static void main(String[] args) {
        final String dumpDirectory = System.getProperty("user.dir");
        System.out.println("Storing heap dumps for all local JVM processes in " + dumpDirectory);
        for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
            try {
                VirtualMachine vm = VirtualMachine.attach(vmd);
                HotSpotVirtualMachine hvm = (HotSpotVirtualMachine) vm;
                InputStream is = hvm.dumpHeap( dumpDirectory + "/jdump-" + vmd.id() + ".hprof");
                if (PrintStreamPrinter.drainUTF8(is, System.out) == 0) {
                    System.out.println("done");
                }
            } catch (Exception e) {
                System.err.println("Could not get heap dump for JVM " + vmd.id() + ": " + e);
            }
        }
    }

}
