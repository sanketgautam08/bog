FROM openjdk:21
ARG JAR_FILE=target/bog-0.0.1-SNAPSHOT-exec.jar
COPY ${JAR_FILE} bog.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bog.jar"]