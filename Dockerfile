FROM maven:3.6.3-jdk-11
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean install
CMD ["java", "-jar", "target/parking_backend-0.0.1-SNAPSHOT.jar"]
