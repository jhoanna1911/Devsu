package com.devsu.cuentas.application.service;

import com.devsu.cuentas.api.dto.ReporteResponse;
import com.devsu.cuentas.domain.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final MovimientoRepository movimientoRepository;

    @Override
    public List<ReporteResponse> generarReporte(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId) {
        LocalDateTime start = fechaInicio.atStartOfDay();
        LocalDateTime end = fechaFin.atTime(23, 59, 59);

        return movimientoRepository.findByFechaBetweenAndCuentaClienteCacheId(start, end, clienteId)
                .stream()
                .map(m -> ReporteResponse.builder()
                        .fecha(m.getFecha())
                        .cliente(m.getCuenta().getClienteCache().getNombre())
                        .numeroCuenta(m.getCuenta().getNumeroCuenta())
                        .tipo(m.getCuenta().getTipoCuenta())
                        .saldoInicial(m.getCuenta().getSaldoInicial())
                        .estado(m.getCuenta().getEstado())
                        .movimiento(m.getValor())
                        .saldoDisponible(m.getSaldo())
                        .build())
                .collect(Collectors.toList());
    }
}
