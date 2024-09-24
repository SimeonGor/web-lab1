FROM openjdk:17-jdk-slim
LABEL authors="simeongor"

COPY ./target/lab1-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

EXPOSE 28007
ENTRYPOINT ["java", "-DFCGI_PORT=28007","-jar","/app.jar"]