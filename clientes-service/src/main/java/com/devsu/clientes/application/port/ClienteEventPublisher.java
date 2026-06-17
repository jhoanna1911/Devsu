package com.devsu.clientes.application.port;

import com.devsu.clientes.application.dto.ClienteEvent;

public interface ClienteEventPublisher {
    void publish(ClienteEvent event, String routingKey);
}
