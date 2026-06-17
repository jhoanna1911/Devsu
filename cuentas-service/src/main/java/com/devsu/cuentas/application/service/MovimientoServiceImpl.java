package com.devsu.cuentas.application.service;

import com.devsu.cuentas.api.dto.MovimientoRequest;
import com.devsu.cuentas.api.dto.MovimientoResponse;
import com.devsu.cuentas.api.exception.ResourceNotFoundException;
import com.devsu.cuentas.api.exception.SaldoInsuficienteException;
import com.devsu.cuentas.domain.entity.Cuenta;
import com.devsu.cuentas.domain.entity.Movimiento;
import com.devsu.cuentas.domain.repository.CuentaRepository;
import com.devsu.cuentas.domain.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Override
    public List<MovimientoResponse> findAll() {
        return movimientoRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public MovimientoResponse findById(Long id) {
        return movimientoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Movimiento no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public MovimientoResponse registrar(MovimientoRequest request) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(request.getNumeroCuenta())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada: " + request.getNumeroCuenta()));

        BigDecimal nuevoSaldo = cuenta.getSaldoDisponible().add(request.getValor());
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoInsuficienteException();
        }

        cuenta.setSaldoDisponible(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(request.getValor().compareTo(BigDecimal.ZERO) > 0 ? "DEPOSITO" : "RETIRO");
        movimiento.setValor(request.getValor());
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);

        return toResponse(movimientoRepository.save(movimiento));
    }

    private MovimientoResponse toResponse(Movimiento m) {
        return MovimientoResponse.builder()
                .id(m.getId())
                .fecha(m.getFecha())
                .tipoMovimiento(m.getTipoMovimiento())
                .valor(m.getValor())
                .saldo(m.getSaldo())
                .numeroCuenta(m.getCuenta().getNumeroCuenta())
                .build();
    }
}
