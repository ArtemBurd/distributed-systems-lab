FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/package-service-0.0.1-SNAPSHOT.jar package-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "package-service.jar"]