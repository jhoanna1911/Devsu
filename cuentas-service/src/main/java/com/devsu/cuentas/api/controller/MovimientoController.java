package com.devsu.cuentas.api.controller;

import com.devsu.cuentas.api.dto.MovimientoRequest;
import com.devsu.cuentas.api.dto.MovimientoResponse;
import com.devsu.cuentas.application.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> findAll() {
        return ResponseEntity.ok(movimientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoResponse> registrar(@Valid @RequestBody MovimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoService.registrar(request));
    }
}
