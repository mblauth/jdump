package jdump;

public class Info {
    public static String getJVMInfo() {
        return "jdump running on " + System.getProperty("java.vm.vendor") + " " +
                System.getProperty("java.vm.name") + " " +
                System.getProperty("java.vm.version") + " " +
                System.getProperty("os.arch");
    }
}
