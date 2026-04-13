FROM maven:3.9.14-eclipse-temurin-21 as build

COPY src /app/source
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM azul/zulu-openjdk-alpine:21-jre

COPY --from=build /app/target/controle-doacoes-principal-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
