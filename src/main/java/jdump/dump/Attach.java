package jdump.dump;

import com.sun.tools.attach.*;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Helper for attaching to JVMs using the Attach API.
 */
class Attach {
    static final Map<String, HotSpotVirtualMachine> attachedVMs = new HashMap<>();

    /**
     * Attaches to a JVM using the Attach API. Only HotSpot VMs are supported.
     * @param virtualMachineDescriptor the descriptor of the JVM to which to attach
     * @return the {@link HotSpotVirtualMachine} instance associated to the JVM
     * @throws IOException if an I/O error occurs
     * @throws AttachNotSupportedException if the target JVM does not support attaching
     */
    static synchronized HotSpotVirtualMachine to(VirtualMachineDescriptor virtualMachineDescriptor)
            throws IOException, AttachNotSupportedException {
        if (attachedVMs.containsKey(virtualMachineDescriptor.id()))
            return attachedVMs.get(virtualMachineDescriptor.id());
        VirtualMachine vm = VirtualMachine.attach(virtualMachineDescriptor);
        HotSpotVirtualMachine hvm = (HotSpotVirtualMachine) vm;
        attachedVMs.put(virtualMachineDescriptor.id(), hvm);
        return hvm;
    }

    /**
     * Detaches from all JVMs previously attached to by using to().
     */
    static synchronized void detachAll() {
        for (HotSpotVirtualMachine vm : attachedVMs.values()) {
            try {
                vm.detach();
            } catch (IOException e) {
                System.err.println("Failed to detach from VM " + vm.id());
            }
        }
        attachedVMs.clear();
    }

    public synchronized static void loadAgent(VirtualMachineDescriptor vmd, String outputFileName) throws
            AgentLoadException, IOException, AgentInitializationException {
        if (!attachedVMs.containsKey(vmd.id())) throw new RuntimeException("JVM not attached");
        // TODO: Remove hard-coded references to agent versions
        try (var stream = Attach.class.getResourceAsStream("/agent-1.0-SNAPSHOT.jar")) {
            File tempFile = File.createTempFile("agent-1.0-SNAPSHOT-", ".jar");
            Files.copy(Objects.requireNonNull(stream), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            attachedVMs.get(vmd.id()).loadAgent(tempFile.getAbsolutePath(), outputFileName);
        }
    }
}
