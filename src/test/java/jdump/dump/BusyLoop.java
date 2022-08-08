package jdump.dump;

public class BusyLoop {

    public static void main(String[] args) {
        busyLoop();
    }

    // Do not rename, name is used for check in ThreadDumpTest
    private static void busyLoop() {
        //noinspection InfiniteLoopStatement,StatementWithEmptyBody
        for(;;);
    }

}
