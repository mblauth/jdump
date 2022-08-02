package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;

class HeapDump extends HotspotDump {
    private final String outputDirectory;

    private HeapDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static HeapDump in(String outputDirectory) {
        return new HeapDump(outputDirectory);
    }

    /**
     * Perform a heap jdump.dump for a given JVM process.
     *
     * @param vmd the {@link VirtualMachineDescriptor} of the JVM process
     */
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping heap for JVM " + vmd.id());
        execAndPrint(vmd, "dumpheap", filenameFor(vmd));
    }

    private String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-heap-" + virtualMachineDescriptor.id() + ".hprof";
    }
}
