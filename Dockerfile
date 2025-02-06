FROM maven:3.8.7-eclipse-temurin-17 as builder
WORKDIR /readings
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /readings
COPY --from=builder readings/target/readings-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]


