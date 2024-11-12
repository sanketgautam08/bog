FROM openjdk:21
ARG JAR_FILE=target/*exec.jar
COPY ${JAR_FILE} bog.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/bog.jar"]