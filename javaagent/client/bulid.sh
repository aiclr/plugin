#!/bin/bash
javac -d tmp org/bougainvilleas/java/*.java
# 使用简单的 c 加密 class
./rw.out tmp ./org/bougainvilleas/java/TTClient.class ./org/bougainvilleas/java/TTClient.class
# 可执行jar
jar -cvfM client.jar org/bougainvilleas/java/*.class META-INF/MANIFEST.MF
