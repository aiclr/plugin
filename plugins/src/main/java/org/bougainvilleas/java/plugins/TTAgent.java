package org.bougainvilleas.java.plugins;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.List;
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
        List<Class> asList = Arrays.asList(instrumentation.getAllLoadedClasses());
        asList.stream()
                .map(Class::getName)
                .filter(str->str.startsWith("agent"))
                .forEach(System.err::println);

        instrumentation.addTransformer(new ClassFileTransformer() {

            public static final String classNumberReturns2 = "TransClass.class.2";
            public byte[] getBytesFromFile(String fileName) {
                try {
                    // precondition
                    File file = new File(fileName);
                    InputStream is = new FileInputStream(file);
                    long length = file.length();
                    byte[] bytes = new byte[(int) length];
                    // Read in the bytes
                    int offset = 0;
                    int numRead = 0;
                    while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                        offset += numRead;
                    }
                    if (offset < bytes.length) {
                        throw new IOException("Could not completely read file "+ file.getName());
                    }
                    is.close();
                    return bytes;
                } catch (Exception e) {
                    System.out.println("error occurs in _ClassTransformer!"+ e.getClass().getName());
                    return null;
                }
            }

            @Override
            public byte[] transform(ClassLoader classLoader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if(!className.startsWith("agent")){
                    return null;
                }
                else {
                    System.err.println(classLoader.getClass().getName());
                    System.err.println(className);
                    for(int i=0;i<4;i++){
                        System.err.print(Integer.toString(Byte.toUnsignedInt(classfileBuffer[i]),16));
                    }
                    //未转换返回null
                    return null;
                }
            }
        });

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
