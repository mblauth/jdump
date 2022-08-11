package jdump.agent;

import java.lang.instrument.Instrumentation;

public class Agent {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agent started");
    }
}
