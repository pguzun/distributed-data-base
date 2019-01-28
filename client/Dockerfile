FROM openjdk:8-jre-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ./build/libs/tower-control.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
