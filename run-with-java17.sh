#!/bin/zsh
# Run Spring Boot app with JAVA_HOME set to Java 17
JAVA_HOME=$(/usr/libexec/java_home -v17)
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

./mvnw spring-boot:run "$@"
