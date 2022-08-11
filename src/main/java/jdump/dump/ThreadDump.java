package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;
import jdump.output.Output;

/**
 * Supports dumping threads, i.e. stack traces of all running threads.
 */
class ThreadDump extends HotspotDump {
    private final Configuration configuration;

    ThreadDump(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping threads for JVM " + vmd.id());
        execAndSave(vmd, Output.using(configuration).file(filenameFor(vmd)), "threaddump");
    }

    @Override
    String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return "jdump-threads-" + virtualMachineDescriptor.id() + ".txt";
    }
}
