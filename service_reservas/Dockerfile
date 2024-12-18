FROM openjdk:17-alpine
LABEL maintainer="github/Lukas22D"

# Definindo variáveis de ambiente
ENV DB_URL=jdbc:mysql://sistema-reserva-hoteis:3306/db-reservas
ENV RABBIT_HOST=sistema-reserva-hoteis-rabbitmq
ENV RABBIT_PORT=5672
ENV EUREKA_URI=http://server-discover:8761/eureka/ 

# Copiando o JAR da aplicação para o diretório /app
COPY target/service_reservas-0.0.1-SNAPSHOT.jar /com.sistemareserva/

# Definindo o diretório de trabalho
WORKDIR /com.sistemareserva

# Definindo o ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "service_reservas-0.0.1-SNAPSHOT.jar"]
