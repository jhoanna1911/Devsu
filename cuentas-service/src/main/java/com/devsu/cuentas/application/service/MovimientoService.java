package com.devsu.cuentas.application.service;

import com.devsu.cuentas.api.dto.MovimientoRequest;
import com.devsu.cuentas.api.dto.MovimientoResponse;
import java.util.List;

public interface MovimientoService {
    List<MovimientoResponse> findAll();
    MovimientoResponse findById(Long id);
    MovimientoResponse registrar(MovimientoRequest request);
}
