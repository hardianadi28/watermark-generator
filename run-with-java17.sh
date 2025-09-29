#!/bin/zsh
# Run Spring Boot app with JAVA_HOME set to Java 17
JAVA_HOME=$(/usr/libexec/java_home -v17)
export JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"

# Clean and build with Java 17 to avoid class version mismatch
./mvnw clean package

# Run the Spring Boot app
java -jar target/watermark-generator-0.0.1-SNAPSHOT.jar "$@"
