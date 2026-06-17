package com.devsu.cuentas.application.service;

import com.devsu.cuentas.api.dto.ReporteResponse;
import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<ReporteResponse> generarReporte(LocalDate fechaInicio, LocalDate fechaFin, Long clienteId);
}
