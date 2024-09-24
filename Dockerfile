# Stage 1: Build the application
FROM maven:3.8.6 AS build
WORKDIR /app

# Copia o arquivo pom.xml e o código fonte
COPY pom.xml .
COPY src ./src

# Constrói o projeto
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copia o JAR gerado do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
