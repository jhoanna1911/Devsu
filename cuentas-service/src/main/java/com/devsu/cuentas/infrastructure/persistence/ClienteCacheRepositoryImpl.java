package com.devsu.cuentas.infrastructure.persistence;

import com.devsu.cuentas.domain.entity.ClienteCache;
import com.devsu.cuentas.domain.repository.ClienteCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteCacheRepositoryImpl implements ClienteCacheRepository {
    private final ClienteCacheJpaRepository jpa;

    @Override public Optional<ClienteCache> findById(Long id) { return jpa.findById(id); }
    @Override public ClienteCache save(ClienteCache c) { return jpa.save(c); }
    @Override public void deleteById(Long id) { jpa.deleteById(id); }
}
