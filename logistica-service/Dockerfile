FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

# Descargar dependencias y compilar (usando apk para Alpine)
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Etapa final
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar JAR compilado desde builder
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "app.jar"]
