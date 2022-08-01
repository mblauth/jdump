package dump;

import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

class ThreadDump extends HotspotDump {
    private final String outputDirectory;

    private ThreadDump(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    static ThreadDump in(String outputDirectory) {
        return new ThreadDump(outputDirectory);
    }

    /**
     * Perform a thread dump for a given JVM process.
     *
     * @param vmd the {@link VirtualMachineDescriptor} of the JVM process
     */
    void performFor(VirtualMachineDescriptor vmd) {
        System.out.println("Dumping threads for JVM " + vmd.id());
        try {
            InputStream is = executeCommand(vmd, "threaddump");
            File outputFile = new File(filenameFor(vmd));
            Files.copy(is, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Failed to dump threads for JVM " + vmd.id() + ": " + e);
        }
    }

    private String filenameFor(VirtualMachineDescriptor virtualMachineDescriptor) {
        return outputDirectory + File.separator + "jdump-threads-" + virtualMachineDescriptor.id() + ".txt";
    }
}
