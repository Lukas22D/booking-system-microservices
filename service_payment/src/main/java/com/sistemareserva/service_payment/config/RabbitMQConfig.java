package com.sistemareserva.service_payment.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    // Nome da exchange e das queues
   //private static final String EXCHANGE_NAME = "payment-exchange";
    private static final String QUEUE_PAYMENT = "payment-queue";
    private static final String QUEUE_NOTIFICATION = "notification-queue";

    private static final String ROUTING_KEY_PAYMENT = null;
    private static final String ROUTING_KEY_NOTIFICATION = null;

    // Criação da exchange do tipo Direct
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("payment-exchange");
    }

    // Criação das queues
    @Bean
    public Queue paymentQueue() {
        return new Queue(QUEUE_PAYMENT, true); // true para tornar a queue durável
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATION, true); // true para tornar a queue durável
    }
    // Binding da fila de pagamento com a exchange usando uma routing key específica
    @Bean
    public Binding bindingPayment(Queue paymentQueue, DirectExchange exchange) {
        return BindingBuilder.bind(paymentQueue).to(exchange).with(ROUTING_KEY_PAYMENT);
    }

    // Binding da fila de notificação com a exchange usando uma routing key
    // específica
    @Bean
    public Binding bindingNotification(Queue notificationQueue, DirectExchange exchange) {
        return BindingBuilder.bind(notificationQueue).to(exchange).with(ROUTING_KEY_NOTIFICATION);
    }

    // Adicionando o RabbitAdmin para gerenciamento automático das exchanges e queues
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.initialize(); // Inicializa para garantir a criação automática
        return admin;
    }
}
