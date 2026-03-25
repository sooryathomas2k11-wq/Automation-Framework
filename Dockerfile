# Updated to a verified Java 19 Maven image
FROM --platform=linux/amd64 maven:3.9.1-eclipse-temurin-19

# The rest of your file remains the same
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests
ENTRYPOINT ["mvn", "test"]