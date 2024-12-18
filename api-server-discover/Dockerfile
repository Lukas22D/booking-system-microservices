FROM openjdk:17-alpine
LABEL maintainer="github/Lukas22D"

# Copiando o JAR da aplicação para o diretório /app
COPY target/api-server-discover-0.0.1-SNAPSHOT.jar /com.sistemareserva/

# Definindo o diretório de trabalho
WORKDIR /com.sistemareserva

# Definindo o ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "api-server-discover-0.0.1-SNAPSHOT.jar"]
