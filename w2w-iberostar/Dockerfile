# Usa una imagen de Debian con OpenJDK 21
FROM debian:bookworm

# Instala OpenJDK y Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo del proyecto Maven
COPY pom.xml /app/

# Copia el código fuente
COPY src /app/src

# Empaqueta la aplicación
RUN mvn clean package -DskipTests

# Ejecuta la aplicación
CMD ["java", "-jar", "target/your-application.jar"]
