import dump.Dumps;

public class JDump {

    public static void main(String[] args) {
        showJVMInfo();
        Dumps.handle(args);
    }

    static void showJVMInfo() {
        System.out.println("jdump running on " + System.getProperty("java.vm.vendor") + " " +
                System.getProperty("java.vm.name") + " " +
                System.getProperty("java.vm.version") + " " +
                System.getProperty("os.arch")
        );
    }

}
