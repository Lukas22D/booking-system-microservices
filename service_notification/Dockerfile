FROM openjdk:17-alpine
LABEL maintainer="github.com/Lukas22D"


ENV GMAIL_USERNAME=luk.look.s.rocha@gmail.com
ENV GMAIL_PASSWORD=avpkxhhwvqilijfq
ENV RABBIT_HOST=sistema-reserva-hoteis-rabbitmq
ENV RABBIT_PORT=5672

COPY target/service_notification-0.0.1-SNAPSHOT.jar /com.sistemareserva/

WORKDIR /com.sistemareserva

ENTRYPOINT [ "java", "-jar", "service_notification-0.0.1-SNAPSHOT.jar" ]