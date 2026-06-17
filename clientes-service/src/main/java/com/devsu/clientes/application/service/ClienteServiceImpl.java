package com.devsu.clientes.application.service;

import com.devsu.clientes.api.dto.ClienteRequest;
import com.devsu.clientes.api.dto.ClienteResponse;
import com.devsu.clientes.application.dto.ClienteEvent;
import com.devsu.clientes.application.port.ClienteEventPublisher;
import com.devsu.clientes.domain.entity.Cliente;
import com.devsu.clientes.domain.repository.ClienteRepository;
import com.devsu.clientes.api.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteEventPublisher eventPublisher;

    @Value("${rabbitmq.routing-key.created}")
    private String createdKey;

    @Value("${rabbitmq.routing-key.updated}")
    private String updatedKey;

    @Value("${rabbitmq.routing-key.deleted}")
    private String deletedKey;

    @Override
    public List<ClienteResponse> findAll() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponse findById(Long id) {
        return clienteRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        Cliente cliente = toEntity(request);
        Cliente saved = clienteRepository.save(cliente);
        eventPublisher.publish(toEvent(saved, "CREATED"), createdKey);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ClienteResponse update(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        updateFields(cliente, request, false);
        Cliente saved = clienteRepository.save(cliente);
        eventPublisher.publish(toEvent(saved, "UPDATED"), updatedKey);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public ClienteResponse partialUpdate(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        updateFields(cliente, request, true);
        Cliente saved = clienteRepository.save(cliente);
        eventPublisher.publish(toEvent(saved, "UPDATED"), updatedKey);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        ClienteEvent event = toEvent(cliente, "DELETED");
        clienteRepository.deleteById(id);
        eventPublisher.publish(event, deletedKey);
    }

    private void updateFields(Cliente cliente, ClienteRequest request, boolean partial) {
        if (!partial || request.getNombre() != null) cliente.setNombre(request.getNombre());
        if (!partial || request.getGenero() != null) cliente.setGenero(request.getGenero());
        if (!partial || request.getEdad() != null) cliente.setEdad(request.getEdad());
        if (!partial || request.getIdentificacion() != null) cliente.setIdentificacion(request.getIdentificacion());
        if (!partial || request.getDireccion() != null) cliente.setDireccion(request.getDireccion());
        if (!partial || request.getTelefono() != null) cliente.setTelefono(request.getTelefono());
        if (!partial || request.getClienteId() != null) cliente.setClienteId(request.getClienteId());
        if (!partial || request.getContrasena() != null) cliente.setContrasena(request.getContrasena());
        if (!partial || request.getEstado() != null) cliente.setEstado(request.getEstado());
    }

    private Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setGenero(request.getGenero());
        cliente.setEdad(request.getEdad());
        cliente.setIdentificacion(request.getIdentificacion());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setClienteId(request.getClienteId());
        cliente.setContrasena(request.getContrasena());
        cliente.setEstado(request.getEstado() != null ? request.getEstado() : true);
        return cliente;
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .genero(cliente.getGenero())
                .edad(cliente.getEdad())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getDireccion())
                .telefono(cliente.getTelefono())
                .clienteId(cliente.getClienteId())
                .estado(cliente.getEstado())
                .build();
    }

    private ClienteEvent toEvent(Cliente cliente, String accion) {
        return new ClienteEvent(cliente.getId(), cliente.getClienteId(), cliente.getNombre(), cliente.getEstado(), accion);
    }
}
