package jdump.dump;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachineDescriptor;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * This class provides the functionality to get profiling dumps from HotSpot VM. It will not work with other JVMs.
 */
public abstract class HotspotDump extends Dump {
    InputStream executeCommand(VirtualMachineDescriptor vmd, String command, Object... args)
            throws IOException, AttachNotSupportedException {
        HotSpotVirtualMachine hvm = Attach.to(vmd);
        return hvm.executeCommand(command, args);
    }

    void execAndPrint(VirtualMachineDescriptor vmd, String command, Object... args) {
        try {
            InputStream is = executeCommand(vmd, command, args);
            PrintStreamPrinter.drainUTF8(is, System.out);
        } catch (Exception e) {
            System.err.println("Failed for JVM " + vmd.id() + ": " + e);
        }
    }

    void execAndSave(VirtualMachineDescriptor vmd, File outputFile, String command, Object... args) {
        try {
            InputStream is = executeCommand(vmd, command, args);
            Files.copy(is, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Failed for JVM " + vmd.id() + ": " + e);
        }
    }

}
