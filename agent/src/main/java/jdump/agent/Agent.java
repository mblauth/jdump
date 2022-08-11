package jdump.agent;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {
    public static void agentmain(String agentArgs, Instrumentation inst) throws IOException {
        if (System.getProperty("os.name").contains("Linux"))
            MallInfo.writeTo(agentArgs);
    }
}
