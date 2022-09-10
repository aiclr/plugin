#!/bin/bash
javac org/bougainvilleas/java/*.java
jar -cvfM agent.jar org/bougainvilleas/java/*.class META-INF/MANIFEST.MF
