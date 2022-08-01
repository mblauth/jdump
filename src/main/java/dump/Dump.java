package dump;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public abstract class Dump {
    private final long currentPid = ProcessHandle.current().pid();

    abstract void performFor(VirtualMachineDescriptor vmd);

    void performForAll() {
        VirtualMachine.list().stream()
                .filter(vmd -> currentPid != Long.parseLong(vmd.id())) // we don't want to work with our own VM
                .forEach(this::performFor);
    }
}
