package org.bougainvilleas.java.plugins;

public class TTClient {

    public static void main(String[] args) {
        System.err.println("Test Client");
    }
}

/**
 *
 *
 -javaagent:D:/customagent.jar="Here, your can input agent arguments"
 Manifest-Version: 1.0
 Premain-Class: com.fjn.jdk.jvm.options.javaagent.CustomAgent
 Sealed: true



 ├── client
 │   └── TTClient.class
 ├── client.jar
 └── META-INF
 └── MANIFEST.MF

 2 directories, 3 files
 */