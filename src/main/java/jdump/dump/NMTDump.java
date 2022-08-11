package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;
import jdump.output.Output;

/**
 * Supports dumping native memory tracks.
 */
public class NMTDump extends HotspotDump {
    private final Configuration configuration;

    NMTDump(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dump NMT for JVM " + vmd.id());
        var output = Output.using(configuration);
        execAndSave(vmd, output.file(filenameFor(vmd)), "jcmd","VM.native_memory");
        if (output.aLineMatches("Native memory tracking is not enabled")) {
            System.err.println("Native memory tracking is not enabled for JVM " + vmd.id());
            output.deleteFile();
        }
    }

    @Override
    String filenameFor(VirtualMachineDescriptor vmd) {
        return "jdump-nmt-" + vmd.id() + ".txt";
    }
}
