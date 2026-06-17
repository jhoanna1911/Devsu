package com.devsu.clientes.domain.repository;

import com.devsu.clientes.domain.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    List<Cliente> findAll();
    Optional<Cliente> findById(Long id);
    Optional<Cliente> findByClienteId(String clienteId);
    Cliente save(Cliente cliente);
    void deleteById(Long id);
    boolean existsById(Long id);
}
