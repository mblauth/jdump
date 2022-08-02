package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;

class ThreadDump extends HotspotDump {
    private final String outputDirectory;

    private ThreadDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static ThreadDump in(String outputDirectory) {
        return new ThreadDump(outputDirectory);
    }

    /**
     * Perform a thread jdump.dump for a given JVM process.
     *
     * @param vmd the {@link VirtualMachineDescriptor} of the JVM process
     */
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping threads for JVM " + vmd.id());
        execAndSave(vmd, new File(filenameFor(vmd)), "threaddump");
    }

    private String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-threads-" + virtualMachineDescriptor.id() + ".txt";
    }
}
