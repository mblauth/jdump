import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Attach {

    private static final Map<String, HotSpotVirtualMachine> attachedVMs = new HashMap<>();

    static synchronized HotSpotVirtualMachine to(VirtualMachineDescriptor virtualMachineDescriptor)
            throws IOException, AttachNotSupportedException {
        if (attachedVMs.containsKey(virtualMachineDescriptor.id()))
            return attachedVMs.get(virtualMachineDescriptor.id());
        VirtualMachine vm = VirtualMachine.attach(virtualMachineDescriptor);
        HotSpotVirtualMachine hvm = (HotSpotVirtualMachine) vm;
        attachedVMs.put(virtualMachineDescriptor.id(), hvm);
        return hvm;
    }

}