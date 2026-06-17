package com.devsu.cuentas.infrastructure.persistence;

import com.devsu.cuentas.domain.entity.Cuenta;
import com.devsu.cuentas.domain.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CuentaRepositoryImpl implements CuentaRepository {
    private final CuentaJpaRepository jpa;

    @Override public List<Cuenta> findAll() { return jpa.findAll(); }
    @Override public Optional<Cuenta> findById(Long id) { return jpa.findById(id); }
    @Override public Optional<Cuenta> findByNumeroCuenta(String n) { return jpa.findByNumeroCuenta(n); }
    @Override public Cuenta save(Cuenta c) { return jpa.save(c); }
    @Override public boolean existsById(Long id) { return jpa.existsById(id); }
}
