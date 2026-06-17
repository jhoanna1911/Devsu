package com.devsu.cuentas.infrastructure.persistence;

import com.devsu.cuentas.domain.entity.ClienteCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteCacheJpaRepository extends JpaRepository<ClienteCache, Long> {
}
