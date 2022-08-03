package jdump;

import java.util.Objects;

public class Info {
    public static String getJVMInfo() {
        return  System.getProperty("java.vm.vendor") + " " +
                System.getProperty("java.vm.name") + " " +
                System.getProperty("java.vm.version") + " " +
                System.getProperty("os.arch");
    }

    /**
     * Get the version info from the jar file (stored there by gradle)
     * @return the version info string if running from jar, "development version" otherwise
     */
    public static String getVersion() {
        String implementationVersion = Info.class.getPackage().getImplementationVersion();
        return Objects.requireNonNullElse(implementationVersion, "development version");
    }
}
