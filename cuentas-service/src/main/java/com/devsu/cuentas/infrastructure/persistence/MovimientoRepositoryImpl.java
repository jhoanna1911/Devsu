package com.devsu.cuentas.infrastructure.persistence;

import com.devsu.cuentas.domain.entity.Movimiento;
import com.devsu.cuentas.domain.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovimientoRepositoryImpl implements MovimientoRepository {
    private final MovimientoJpaRepository jpa;

    @Override public List<Movimiento> findAll() { return jpa.findAll(); }
    @Override public Optional<Movimiento> findById(Long id) { return jpa.findById(id); }
    @Override public List<Movimiento> findByFechaBetweenAndCuentaClienteCacheId(LocalDateTime s, LocalDateTime e, Long id) {
        return jpa.findByFechaBetweenAndCuentaClienteCacheId(s, e, id);
    }
    @Override public Movimiento save(Movimiento m) { return jpa.save(m); }
}
