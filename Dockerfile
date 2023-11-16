FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar school-assistant-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/school-assistant-api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080