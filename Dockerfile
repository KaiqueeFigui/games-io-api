FROM maven:3.6.0-jdk-11-slim AS build

COPY . .

EXPOSE 8080

RUN mvn clean package

FROM openjdk:11-jre-slim

COPY --from=build /target/games-io-api.jar games-io-api.jar

ENTRYPOINT ["java", "-jar", "/games-io-api.jar"]