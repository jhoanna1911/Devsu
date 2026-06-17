package com.devsu.clientes.infrastructure.persistence;

import com.devsu.clientes.domain.entity.Cliente;
import com.devsu.clientes.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryImpl implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;

    @Override
    public List<Cliente> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Cliente> findByClienteId(String clienteId) {
        return jpaRepository.findByClienteId(clienteId);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return jpaRepository.save(cliente);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
