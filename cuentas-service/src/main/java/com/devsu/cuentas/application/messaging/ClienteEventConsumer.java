package com.devsu.cuentas.application.messaging;

import com.devsu.cuentas.domain.entity.ClienteCache;
import com.devsu.cuentas.domain.repository.ClienteCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class ClienteEventConsumer {

    private final ClienteCacheRepository clienteCacheRepository;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleClienteEvent(Map<String, Object> event) {
        String accion = (String) event.get("accion");
        Long id = ((Number) event.get("id")).longValue();
        log.info("Evento recibido: {} para cliente id={}", accion, id);

        switch (accion) {
            case "CREATED", "UPDATED" -> {
                ClienteCache cache = new ClienteCache(
                        id,
                        (String) event.get("clienteId"),
                        (String) event.get("nombre"),
                        (Boolean) event.get("estado")
                );
                clienteCacheRepository.save(cache);
            }
            case "DELETED" -> clienteCacheRepository.deleteById(id);
            default -> log.warn("Accion desconocida: {}", accion);
        }
    }
}
