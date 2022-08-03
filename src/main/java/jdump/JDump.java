package jdump;

import jdump.cli.CommandLineArguments;
import jdump.ui.MainWindow;

import java.awt.*;

public class JDump {

    public static void main(String[] args) {
        showVersionAndJVMInfo();  // we always print the version and JVM info on the command line

        // run the GUI when no parameters were provided and when we are not headless
        if (args.length == 0 && !GraphicsEnvironment.isHeadless()) {
            MainWindow.create();
        } else { // run the non-interactive version otherwise
            CommandLineArguments.handle(args);
        }
    }

    private static void showVersionAndJVMInfo() {
        System.out.println("jdump " + Info.getVersion() + " running on " + Info.getJVMInfo());
    }

}
