package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;

/**
 * Supports dumping threads, i.e. stack traces of all running threads.
 */
class ThreadDump extends HotspotDump {
    private final String outputDirectory;

    ThreadDump(Configuration configuration) {
        this.outputDirectory = configuration.outputDirectory();
    }

    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping threads for JVM " + vmd.id());
        execAndSave(vmd, new File(filenameFor(vmd)), "threaddump");
    }

    String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-threads-" + virtualMachineDescriptor.id() + ".txt";
    }
}
