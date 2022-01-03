FROM openjdk:17-alpine

WORKDIR /opt/server
COPY ./target/courier-service-0.0.1-SNAPSHOT.jar courier-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "courier-service.jar"]