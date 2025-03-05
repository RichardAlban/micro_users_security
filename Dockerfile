# Usar una imagen de Maven para construir la aplicación
FROM maven:3.8.4-openjdk-17 AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos de la aplicación
COPY pom.xml .
COPY src ./src

# Construir la aplicación Spring Boot
RUN mvn clean package -DskipTests

# Usar una imagen de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim
COPY --from=build /app/target/loanservice-0.0.1-SNAPSHOT.jar /app.jar

# Exponer el puerto 8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]