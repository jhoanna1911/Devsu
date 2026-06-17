package com.devsu.cuentas.infrastructure.persistence;

import com.devsu.cuentas.domain.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoJpaRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByFechaBetweenAndCuentaClienteCacheId(LocalDateTime start, LocalDateTime end, Long clienteId);
}
