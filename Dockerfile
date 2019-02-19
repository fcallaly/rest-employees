FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} myapp.jar
EXPOSE 8081
ENV SERVER.PORT 8081
ENTRYPOINT ["java","-jar","/myapp.jar"]

