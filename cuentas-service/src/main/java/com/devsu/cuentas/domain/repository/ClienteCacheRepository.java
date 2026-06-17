package com.devsu.cuentas.domain.repository;

import com.devsu.cuentas.domain.entity.ClienteCache;
import java.util.Optional;

public interface ClienteCacheRepository {
    Optional<ClienteCache> findById(Long id);
    ClienteCache save(ClienteCache clienteCache);
    void deleteById(Long id);
}
