package jdump;

import java.util.Objects;

public class Info {
    public static String getJVMInfo() {
        return  System.getProperty("java.vm.vendor") + " " +
                System.getProperty("java.vm.name") + " " +
                System.getProperty("java.vm.version") + " " +
                System.getProperty("os.arch");
    }

    public static String getVersion() {
        String implementationVersion = Info.class.getPackage().getImplementationVersion();
        return Objects.requireNonNullElse(implementationVersion, "development version");
    }
}
