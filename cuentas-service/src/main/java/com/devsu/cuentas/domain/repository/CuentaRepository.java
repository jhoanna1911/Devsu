package com.devsu.cuentas.domain.repository;

import com.devsu.cuentas.domain.entity.Cuenta;
import java.util.List;
import java.util.Optional;

public interface CuentaRepository {
    List<Cuenta> findAll();
    Optional<Cuenta> findById(Long id);
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    Cuenta save(Cuenta cuenta);
    boolean existsById(Long id);
}
