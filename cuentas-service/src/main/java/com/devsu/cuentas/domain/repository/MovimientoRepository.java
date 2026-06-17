package com.devsu.cuentas.domain.repository;

import com.devsu.cuentas.domain.entity.Movimiento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovimientoRepository {
    List<Movimiento> findAll();
    Optional<Movimiento> findById(Long id);
    List<Movimiento> findByFechaBetweenAndCuentaClienteCacheId(LocalDateTime start, LocalDateTime end, Long clienteId);
    Movimiento save(Movimiento movimiento);
}
