package jdump.dump;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * A Dump provides the means to execute the performFor() operation implemented by its descendents for every JVM
 * currently running on the local system.
 */
abstract class Dump {
    private final long currentPid = ProcessHandle.current().pid();

    /**
     * Perform a dump operation on a given JVM.
     * @param vmd the {@link VirtualMachineDescriptor} describing the given JVM.
     */
    abstract void performFor(VirtualMachineDescriptor vmd);

    /**
     * Invokes performFor() for every JVM currently running on the local system excluding the one running our tool.
     */
    void performForAll() {
        VirtualMachine.list().stream()
                .filter(vmd -> currentPid != Long.parseLong(vmd.id())) // we don't want to work with our own VM
                .forEach(this::performFor);
    }
}
