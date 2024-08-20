package com.banco.cuentas.service;

import com.banco.cuentas.model.Cuenta;
import com.banco.cuentas.model.Movimiento;
import com.banco.cuentas.repository.CuentaRepository;
import com.banco.cuentas.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoService {

    private static final double LIMITE_DIARIO_RETIROS = 1000.0;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento createMovimiento(Movimiento movimiento) throws Exception {
        Cuenta cuenta = movimiento.getCuenta();

        if (cuenta == null) {
            throw new Exception("Cuenta no encontrada.");
        }

        double saldoActual = cuenta.getSaldoInicial();
        double totalRetirosHoy = getTotalRetirosDelDia(cuenta, LocalDate.now());

        // Validar tipo de movimiento
        if ("debito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            if (saldoActual <= 0) {
                throw new Exception("Saldo no disponible.");
            }
            if (totalRetirosHoy + movimiento.getValor() > LIMITE_DIARIO_RETIROS) {
                throw new Exception("Cupo diario excedido.");
            }
            saldoActual -= movimiento.getValor();
        } else if ("credito".equalsIgnoreCase(movimiento.getTipoMovimiento())) {
            saldoActual += movimiento.getValor();
        } else {
            throw new Exception("Tipo de movimiento no válido.");
        }

        cuenta.setSaldoInicial(saldoActual);
        cuentaRepository.save(cuenta);

        movimiento.setSaldo(saldoActual);
        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> findMovimientosByClienteAndFecha(Long clienteId, LocalDate fechaInicio, String fechaFin) {
        return movimientoRepository.findMovimientosByCuentaAndFecha(clienteId, fechaInicio, fechaFin);
    }

    private double getTotalRetirosDelDia(Cuenta cuenta, LocalDate fecha) {
        List<Movimiento> retirosHoy = movimientoRepository.findMovimientosByCuentaAndFecha(cuenta.getId(), fecha, "debito");
        return retirosHoy.stream().mapToDouble(Movimiento::getValor).sum();
    }
}
