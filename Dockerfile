FROM maven:3.9.16-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml ./
COPY src src

RUN mvn -DskipTests -Djacoco.skip=true package

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/dynamodb-0.1.0.jar app.jar

EXPOSE 9595

ENTRYPOINT ["java", "-jar", "app.jar"]