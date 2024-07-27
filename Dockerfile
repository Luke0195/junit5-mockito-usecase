FROM maven:3.6.3-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn dependency:go-offline
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-alpine
COPY --from=build /home/app/target/tests-0.0.1-SNAPSHOT.jar /usr/local/lib/tests.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/usr/local/lib/tests.jar" ]