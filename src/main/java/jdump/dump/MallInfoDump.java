package jdump.dump;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.IOException;

public class MallInfoDump extends HotspotDump {
    final Configuration configuration;


    public MallInfoDump(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    void performFor(VirtualMachineDescriptor vmd) {
        try {
            Attach.to(vmd);
        } catch (IOException | AttachNotSupportedException e) {
            throw new RuntimeException("Could not attach to JVM " + vmd.id());
        }
        try {
            System.out.println("Dumping mallinfo() statistics for JVM " + vmd.id());
            Attach.loadAgent(vmd, configuration.outputDirectory() + File.separator + filenameFor(vmd));
        } catch (AgentLoadException | IOException | AgentInitializationException e) {
            throw new RuntimeException("Failed to load agent: " + e);
        }
    }

    @Override
    String filenameFor(VirtualMachineDescriptor vmd) {
        return "jdump-mallinfo-" + vmd.id() + ".txt";
    }
}
