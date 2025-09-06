FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/offices-0.0.1-snapshot.jar offices.jar
ENTRYPOINT ["java","-jar","/offices.jar"]