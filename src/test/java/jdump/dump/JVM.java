package jdump.dump;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

public class JVM implements AutoCloseable {
    private Process process;

    private CompletableFuture<Void> started;

    void spawn() throws IOException {
        process = new ProcessBuilder("java",
                "src" + File.separator +
                "test" + File.separator +
                "java" + File.separator +
                "jdump" + File.separator +
                "dump" + File.separator +
                "BusyLoop.java").inheritIO().start();
        started = CompletableFuture.supplyAsync(() -> {
            while (VirtualMachine.list().stream().noneMatch(vm -> vm.id().equals(Long.toString(process.pid())))) {
                try {
                    //noinspection BusyWait
                    Thread.sleep(10);
                } catch (InterruptedException ignored) { }
            }
            return null;
        });
    }

    void waitUntilAttachable() throws ExecutionException, InterruptedException, TimeoutException {
        started.get(5, TimeUnit.SECONDS);
    }

    VirtualMachineDescriptor descriptor()
            throws ExecutionException, InterruptedException, TimeoutException {
        waitUntilAttachable();
        var optVmd = VirtualMachine.list().stream().filter(vm -> vm.id().equals(pidString())).findFirst();
        if (optVmd.isEmpty()) throw new RuntimeException("Could not get vm descriptor for " + pid());
        return optVmd.get();
    }

    String pidString() {
        return String.valueOf(pid());
    }

    long pid() {
        if (!process.isAlive()) throw new RuntimeException("JVM died prematurely");
        return process.pid();
    }

    @Override
    public void close() {
        process.destroy();
    }
}
