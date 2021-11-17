FROM openjdk:11-jdk

EXPOSE 8080

ADD target/games-io-api.jar games-io-api.jar

ENTRYPOINT ["java", "-jar", "/games-io-api.jar"]