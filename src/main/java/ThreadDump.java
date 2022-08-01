import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ThreadDump {

    /**
     * Dumps threads for all compatible JVMs currently reachable via the Attach API. The heap dumps are generated as
     * <pid>.hprof in the current working directory of jdump.
     */
    static void performForAll() {
        System.out.println("Storing thread dumps for all local JVM processes in " + JDump.DUMP_DIRECTORY);
        for (VirtualMachineDescriptor vmd : VirtualMachine.list()) {
            try {
                performFor(vmd);
            } catch (Exception e) {
                System.err.println("Could not get thread dump for JVM " + vmd.id() + ": " + e);
            }
        }
    }


    /**
     * Perform a thread dump for a given JVM process.
     *
     * @param virtualMachineDescriptor the {@link VirtualMachineDescriptor} of the JVM process
     */
    static void performFor(VirtualMachineDescriptor virtualMachineDescriptor)
            throws IOException, AttachNotSupportedException {
        System.out.println("Dumping threads for JVM " + virtualMachineDescriptor.id());
        HotSpotVirtualMachine hvm = Attach.to(virtualMachineDescriptor);
        InputStream is = hvm.executeCommand( "threaddump");
        File outputFile = new File(JDump.DUMP_DIRECTORY + File.separator + "jdump-threads-" + virtualMachineDescriptor.id() + ".txt");
        Files.copy(is, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
