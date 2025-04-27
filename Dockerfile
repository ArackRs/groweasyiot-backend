# Etapa 1: Construcción
FROM eclipse-temurin:21 AS build
LABEL authors="Jack Arana Rs"

# Definir directorio de trabajo
WORKDIR /app

# Copiar archivos necesarios para la construcción
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./
COPY src ./src

# Dar permiso de ejecución al script mvnw
# Descargar las dependencias y construir la aplicación
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21

# Definir directorio de trabajo
WORKDIR /app

# Copiar solo el archivo JAR desde la etapa de construcción
COPY --from=build /app/target/groweasy-api-0.0.1-SNAPSHOT.jar /app/groweasy-api.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Definir el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/groweasy-api.jar"]