# Usa una imagen base de OpenJDK 17
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR desde la compilación de Maven
COPY target/money-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre Spring Boot (generalmente 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
