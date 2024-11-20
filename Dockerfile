FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY .. /app/.
RUN mvn -f /app/pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/prober/target/prober-0.0.1-SNAPSHOT.jar /app/prober.jar
COPY --from=builder /app/sla/target/sla-0.0.1-SNAPSHOT.jar /app/sla.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/prober.jar"]