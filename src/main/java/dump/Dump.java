package dump;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

public abstract class Dump {
    abstract void performFor(VirtualMachineDescriptor vmd);

    void performForAll() {
        VirtualMachine.list().forEach(this::performFor);
    }
}
