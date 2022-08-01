package dump;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides the functionality to get profiling dumps from HotSpot VM. It will not work with other JVMs.
 */
public abstract class HotspotDump extends Dump {
    InputStream executeCommand(VirtualMachineDescriptor virtualMachineDescriptor, String command, Object... args)
            throws IOException, AttachNotSupportedException {
        HotSpotVirtualMachine hvm = Attach.to(virtualMachineDescriptor);
        return hvm.executeCommand(command, args);
    }

}
