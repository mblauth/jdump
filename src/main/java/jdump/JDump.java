package jdump;

import jdump.cli.CommandLineArguments;
import jdump.ui.MainWindow;

import java.awt.*;

public class JDump {

    public static void main(String[] args) {
        showJVMInfo();
        if (args.length == 0 && !GraphicsEnvironment.isHeadless()) {
            MainWindow.create();
        } else {
            CommandLineArguments.handle(args);
        }
    }

    private static void showJVMInfo() {
        System.out.println("jdump " + Info.getVersion() + " running on " + Info.getJVMInfo());
    }

}
