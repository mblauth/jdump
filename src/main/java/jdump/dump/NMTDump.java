package jdump.dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Supports dumping native memory tracks.
 */
public class NMTDump extends HotspotDump {
    private final String outputDirectory;

    private NMTDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static NMTDump in(String outputDirectory) {
        return new NMTDump(outputDirectory);
    }

    @Override
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dump NMT for JVM " + vmd.id());
        execAndSave(vmd, new File(filenameFor(vmd)), "jcmd","VM.native_memory");
        var path = Paths.get(filenameFor(vmd));
        try(var lines = Files.lines(path)) {
            if (lines.anyMatch(line -> line.contains("Native memory tracking is not enabled"))) {
                System.err.println("Native memory tracking is not enabled for JVM " + vmd.id());
                Files.delete(path);
            }
        } catch (IOException e) {
            System.err.println("Failure generating NMT dump:" + e.getMessage());
        }
    }

    String filenameFor(VirtualMachineDescriptor vmd) {
        return outputDirectory + File.separator + "jdump-nmt-" + vmd.id() + ".txt";
    }
}
