package dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.InputStream;

class HeapDump extends HotspotDump {
    private final String outputDirectory;

    private HeapDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static HeapDump in(String outputDirectory) {
        return new HeapDump(outputDirectory);
    }

    /**
     * Perform a heap dump for a given JVM process.
     *
     * @param vmd the {@link VirtualMachineDescriptor} of the JVM process
     */
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping heap for JVM " + vmd.id());
        try {
            InputStream is = executeCommand(vmd, "dumpheap", filenameFor(vmd));
            PrintStreamPrinter.drainUTF8(is, System.out);
        } catch (Exception e) {
            System.err.println("Failed to dump heap for JVM " + vmd.id() + ": " + e);
        }
    }

    private String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-heap-" + virtualMachineDescriptor.id() + ".hprof";
    }
}
