#!/bin/sh

MAVEN_REPO=/local/var/maven-repo
CLASS_PATH="target/classes:$MAVEN_REPO/com/fasterxml/jackson/core/jackson-core/2.2.3/jackson-core-2.2.3.jar:$MAVEN_REPO/com/fasterxml/jackson/core/jackson-databind/2.2.3/jackson-databind-2.2.3.jar:$MAVEN_REPO/com/fasterxml/jackson/core/jackson-annotations/2.2.3/jackson-annotations-2.2.3.jar"

java -cp $CLASS_PATH org.accountservice.client.ConsoleApp -r 20 -w 20 -k 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
