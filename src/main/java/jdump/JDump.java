package jdump;

import jdump.dump.Dumps;

public class JDump {

    public static void main(String[] args) {
        showJVMInfo();
        Dumps.handle(args);
    }

    private static void showJVMInfo() {
        System.out.println(Info.getJVMInfo());
    }

}
