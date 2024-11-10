FROM openjdk:17-alpine
LABEL maintainer="github/Lukas22D"

# Definindo variáveis de ambiente
ENV PAYPAL_CLIENT_ID=AXmqfAbTYg8tIQZTfLotngssewd6ciwz1hj_IGKmw_KPUIcfRt24-yCmyD4j21O2CrgeQeVcWS6b6Z1i
ENV PAYPAL_CLIENT_SECRET=ELVEWIEcUoaBtdP0uwnd-JJiNry_HX5bTK11ZyLx35gkU-OBJCxhgAGwmSmYbvTMAxT_jL6ByBKbm1bx
ENV DB_URL=jdbc:mysql://sistema-reserva-hoteis:3306/db_transaction
ENV RABBIT_HOST=sistema-reserva-hoteis-rabbitmq
ENV RABBIT_PORT=5672
ENV REDIS_HOST=redis
ENV REDIS_PORT=6379

# Instalando o curl
RUN apk add --no-cache curl

# Copiando o JAR da aplicação para o diretório /app
COPY target/service_payment-0.0.1-SNAPSHOT.jar /com.sistemareserva/

# Definindo o diretório de trabalho
WORKDIR /com.sistemareserva

# Definindo o ponto de entrada da aplicação
ENTRYPOINT ["java", "-jar", "service_payment-0.0.1-SNAPSHOT.jar"]
