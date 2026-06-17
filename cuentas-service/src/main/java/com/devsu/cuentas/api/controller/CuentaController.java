package com.devsu.cuentas.api.controller;

import com.devsu.cuentas.api.dto.CuentaRequest;
import com.devsu.cuentas.api.dto.CuentaResponse;
import com.devsu.cuentas.application.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponse>> findAll() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaResponse> create(@Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponse> update(@PathVariable Long id, @Valid @RequestBody CuentaRequest request) {
        return ResponseEntity.ok(cuentaService.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CuentaResponse> partialUpdate(@PathVariable Long id, @RequestBody CuentaRequest request) {
        return ResponseEntity.ok(cuentaService.partialUpdate(id, request));
    }
}
