package com.banco.cuentas.controller;

import com.banco.cuentas.model.Movimiento;
import com.banco.cuentas.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<Movimiento> createMovimiento(@RequestBody Movimiento movimiento) throws Exception {
        return ResponseEntity.ok(movimientoService.createMovimiento(movimiento));
    }

    @GetMapping("/reporte")
    public ResponseEntity<List<Movimiento>> getMovimientosByClienteAndFecha(
            @RequestParam Long clienteId,
            @RequestParam LocalDate fechaInicio,
            @RequestParam String fechaFin) {
        return ResponseEntity.ok(movimientoService.findMovimientosByClienteAndFecha(clienteId, fechaInicio, fechaFin));
    }
}

