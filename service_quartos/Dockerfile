FROM openjdk:17-alpine
LABEL maintainer="github/Lukas22D"

# Definindo variáveis de ambiente
ENV DB_URL=jdbc:mysql://sistema-reserva-hoteis:3306/db-quartos
ENV RABBIT_HOST=sistema-reserva-hoteis-rabbitmq
ENV RABBIT_PORT=5672

# Copiando o JAR da aplicação para o diretório /app
COPY target/service_quartos-0.0.1-SNAPSHOT.jar /com.sistemareserva/

# Definindo o diretório de trabalho
WORKDIR /com.sistemareserva

# Definindo o ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "service_quartos-0.0.1-SNAPSHOT.jar"]
