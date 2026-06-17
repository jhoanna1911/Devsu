package com.devsu.clientes.infrastructure.messaging;

import com.devsu.clientes.application.dto.ClienteEvent;
import com.devsu.clientes.application.port.ClienteEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteEventPublisherImpl implements ClienteEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Override
    public void publish(ClienteEvent event, String routingKey) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }
}
