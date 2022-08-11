package jdump.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import jdump.dump.JVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AgentTest {
    @Test
    public void testAgentCanStart() throws IOException, ExecutionException, InterruptedException, TimeoutException,
            AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        try (JVM jvm = new JVM()) {
            jvm.spawn();
            var vm = VirtualMachine.attach(jvm.descriptor());
            vm.loadAgent("agent/build/libs/agent-1.0-SNAPSHOT.jar");
        }
    }
}
