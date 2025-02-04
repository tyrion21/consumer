package com.hospital.rabbit_consumer;

// import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
// import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "hospital_exchange";
    public static final String ROUTING_KEY_PACIENTE = "paciente_routing_key";
    public static final String ROUTING_KEY_SENALES_VITALES = "senales_vitales_routing_key";
    public static final String ROUTING_KEY_MENSAJE_ALERTA = "mensaje_alerta_routing_key";
    public static final String QUEUE_PACIENTE = "paciente_queue";
    public static final String QUEUE_SENALES_VITALES = "senales_vitales_queue";
    public static final String QUEUE_MENSAJE_ALERTA = "mensaje_alerta_queue";

    @Bean
    public Queue pacienteQueue() {
        return new Queue(QUEUE_PACIENTE);
    }

    @Bean
    public Queue senalesVitalesQueue() {
        return new Queue(QUEUE_SENALES_VITALES);
    }

    @Bean
    public Queue mensajeAlertaQueue() {
        return new Queue(QUEUE_MENSAJE_ALERTA);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding pacienteBinding(@Qualifier("pacienteQueue") Queue pacienteQueue, TopicExchange hospitalExchange) {
        return BindingBuilder.bind(pacienteQueue).to(hospitalExchange).with(ROUTING_KEY_PACIENTE);
    }

    @Bean
    public Binding senalesVitalesBinding(@Qualifier("senalesVitalesQueue") Queue senalesVitalesQueue,
            TopicExchange hospitalExchange) {
        return BindingBuilder.bind(senalesVitalesQueue).to(hospitalExchange).with(ROUTING_KEY_SENALES_VITALES);
    }

    @Bean
    public Binding mensajeAlertaBinding(@Qualifier("mensajeAlertaQueue") Queue mensajeAlertaQueue,
            TopicExchange hospitalExchange) {
        return BindingBuilder.bind(mensajeAlertaQueue).to(hospitalExchange).with(ROUTING_KEY_MENSAJE_ALERTA);
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    
}
