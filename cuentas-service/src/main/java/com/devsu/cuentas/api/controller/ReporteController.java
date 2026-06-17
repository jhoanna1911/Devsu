package com.devsu.cuentas.api.controller;

import com.devsu.cuentas.api.dto.ReporteResponse;
import com.devsu.cuentas.application.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<ReporteResponse>> generarReporte(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam Long clienteId) {
        return ResponseEntity.ok(reporteService.generarReporte(fechaInicio, fechaFin, clienteId));
    }
}
