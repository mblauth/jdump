package jdump.dump;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachineDescriptor;
import jdump.output.Output;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Provides the functionality to get profiling dumps from HotSpot VM. It will not work with other JVMs.
 */
abstract class HotspotDump extends Dump {
    private InputStream executeCommand(VirtualMachineDescriptor vmd, String command, Object... args)
            throws IOException, AttachNotSupportedException {
        HotSpotVirtualMachine hvm = Attach.to(vmd);
        return hvm.executeCommand(command, args);
    }

    /**
     * Executes an Attach API command and prints the returned output to the standard output.
     * @param vmd the {@link VirtualMachineDescriptor} describing the JVM to which to send the command
     * @param command the Attach API command to send to the JVM
     * @param args the Arguments for the Attach API command
     */
    void execAndPrint(VirtualMachineDescriptor vmd, String command, Object... args) {
        try {
            InputStream is = executeCommand(vmd, command, args);
            PrintStreamPrinter.drainUTF8(is, System.out);
        } catch (Exception e) {
            System.err.println("Failed for JVM " + vmd.id() + ": " + e);
        }
    }

    /**
     * Executes an Attach API command and writes the returned output to a given file.
     * @param vmd the {@link VirtualMachineDescriptor} describing the JVM to which to send the command
     * @param output the {@link Output} for persisting the dump
     * @param command the Attach API command to send to the JVM
     * @param args the Arguments for the Attach API command
     */
    void execAndSave(VirtualMachineDescriptor vmd, Output output, String command, Object... args) {
        try {
            InputStream is = executeCommand(vmd, command, args);
            System.out.println("dumping to " + output.path());
            Files.copy(is, output.path(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Failed for JVM " + vmd.id() + ": " + e);
        }
    }

}
