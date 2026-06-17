package com.devsu.cuentas.infrastructure.persistence;

import com.devsu.cuentas.domain.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CuentaJpaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
