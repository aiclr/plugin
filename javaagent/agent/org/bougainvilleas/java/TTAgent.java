package org.bougainvilleas.java;

import java.lang.instrument.Instrumentation;
import java.util.Objects;

public class TTAgent {
    /**
     * 如果agent 是通过 jvm 选项的方式捆绑到程序中
     * 则在 JVM 初始化完毕后，会执行 premain 方法，
     * premain 执行后才是程序的 main
     * 清单文件中需要指定 Premain-Class
     *
     */
    public static void premain(String agentArgs, Instrumentation instrumentation)
    {
        System.err.println("TTAgent#premain(String,Instrumentation)");
        parseAgentArgs(agentArgs);
        instrumentation.addTransformer(new TTClassFileTransformer());
    }




    public static void premain(String agentArgs)
    {
        System.err.println("TTAgent#premain(String)");
        parseAgentArgs(agentArgs);
    }

    /**
     * 如果 Agent 是在程序运行过程中，动态的捆绑到程序中，则执行 agentmain 方法
     * 清单文件中要指定 Agent-Class
     *
     * @param agentArgs
     */
    public static void agentmain(String agentArgs, Instrumentation instrumentation)
    {
        System.err.println("TTAgent#agentmain(String,Instrumentation)");
        parseAgentArgs(agentArgs);
    }
    public static void agentmain(String agentArgs)
    {
        System.err.println("TTAgent#agentmain(String)");
        parseAgentArgs(agentArgs);
    }


    private static boolean parseAgentArgs(String agentArgs) {
        boolean hasArgs=false;
        if(Objects.nonNull(agentArgs) && !agentArgs.isEmpty())
        {
            System.err.println("agentArgs is : "+agentArgs);
            hasArgs=true;
        }else
            System.err.println("has no agentArgs !");
        return hasArgs;
    }
}
