package jdump;

import jdump.dump.Dumps;

public class JDump {

    public static void main(String[] args) {
        showJVMInfo();
        Dumps.handle(args);
    }

    private static void showJVMInfo() {
        System.out.println("jdump " + Info.getVersion() + " running on " + Info.getJVMInfo());
    }

}
