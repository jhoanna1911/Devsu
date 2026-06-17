package com.devsu.cuentas.application.service;

import com.devsu.cuentas.api.dto.CuentaRequest;
import com.devsu.cuentas.api.dto.CuentaResponse;
import java.util.List;

public interface CuentaService {
    List<CuentaResponse> findAll();
    CuentaResponse findById(Long id);
    CuentaResponse create(CuentaRequest request);
    CuentaResponse update(Long id, CuentaRequest request);
    CuentaResponse partialUpdate(Long id, CuentaRequest request);
}
