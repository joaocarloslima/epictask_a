# Etapa de build
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

# Etapa de execução
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o JAR criado na etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]