#EN EL DOCKERFILE DEFINICMOS COMO SE CONSTRUIRÁ LA IMAGEN DOCKER

# Stage 1: Build the application
FROM eclipse-temurin:17-jdk AS builder
# Set the working directory
WORKDIR /app
# Copy the application code
COPY . .
# Given permissions to mvnw
RUN chmod +x mvnw
# Build the application (requires Maven or Gradle)
RUN ./mvnw clean package -DskipTests
# Stage 2: Run the application
FROM eclipse-temurin:17-jre
# Set the working directory
WORKDIR /app
# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar
# Expose the port the app will run on
EXPOSE 8080    
# Command to run the application
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT:-8080} app.jar"]
#al poner el puerto en el ENTRYPOINT podriamos quitarlo del aplication.propierties
#  PORT:-  LO QUE HACE ES BUSCAR LA VARIABLE PORT EN EL .ENV, si no la encuentra usa el 8080
