package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;

/**
 * Supports dumping heap.
 */
class HeapDump extends HotspotDump {
    private final String outputDirectory;

    private HeapDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static HeapDump in(String outputDirectory) {
        return new HeapDump(outputDirectory);
    }

    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping heap for JVM " + vmd.id());
        execAndPrint(vmd, "dumpheap", filenameFor(vmd));
    }

    private String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-heap-" + virtualMachineDescriptor.id() + ".hprof";
    }
}
