# Utilizar una imagen base con OpenJDK para Java 11
FROM openjdk:11-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el wrapper de Maven y los archivos de configuración del proyecto
# Esto asume que tienes un wrapper de Maven (`mvnw`) en la raíz de tu proyecto
COPY mvnw .
COPY .mvn .mvn

# Copiar el archivo de modelo de objeto del proyecto (POM) para descargar las dependencias
COPY pom.xml .

# Ejecutar el build de Maven para descargar las dependencias en la caché del contenedor
# Este paso mejora el rendimiento del build al cachear las dependencias
RUN ./mvnw dependency:go-offline

# Copiar todo el código fuente del proyecto al contenedor
COPY src src

# Construir la aplicación usando el wrapper de Maven (`mvnw`)
# Esto compilará la aplicación y la empaquetará en un archivo JAR
RUN ./mvnw package -DskipTests

# Crear una nueva etapa de build de Docker para el entorno de ejecución de la aplicación
FROM openjdk:11-jre-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR desde la etapa anterior del build al nuevo contenedor
#COPY --from=0 /app/target/parking_backend-0.0.1-SNAPSHOT.jar.original ./app.jar
COPY target/parking_backend-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicia el contenedor
CMD ["java", "-jar", "app.jar"]
