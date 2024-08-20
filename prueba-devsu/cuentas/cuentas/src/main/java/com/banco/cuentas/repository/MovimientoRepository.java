package com.banco.cuentas.repository;

import com.banco.cuentas.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.id = :cuentaId AND m.fecha = :fecha AND m.tipoMovimiento = :tipoMovimiento")
    List<Movimiento> findMovimientosByCuentaAndFecha(
            @Param("cuentaId") Long cuentaId,
            @Param("fecha") LocalDate fecha,
            @Param("tipoMovimiento") String tipoMovimiento);
}

