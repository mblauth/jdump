package jdump.dump;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class JVM implements AutoCloseable {
    private Process process;
    private final String[] jvmArguments;

    public JVM(String... jvmArguments) {
        this.jvmArguments = jvmArguments;
    }

    private CompletableFuture<Void> started;

    void spawn() throws IOException {
        process = new ProcessBuilder(command()).inheritIO().start();
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

    private LinkedList<String> command() {
        var cmd = new LinkedList<String>();
        cmd.add("java");
        cmd.addAll(List.of(jvmArguments));
        cmd.add("src" + File.separator +
                "test" + File.separator +
                "java" + File.separator +
                "jdump" + File.separator +
                "dump" + File.separator +
                "BusyLoop.java");
        return cmd;
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
