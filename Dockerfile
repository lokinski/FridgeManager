FROM openjdk:8
ARG JAR=build/*.jar
COPY ${JAR} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]