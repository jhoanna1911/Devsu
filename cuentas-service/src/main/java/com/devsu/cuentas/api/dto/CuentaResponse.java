package com.devsu.cuentas.api.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CuentaResponse {
    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private BigDecimal saldoDisponible;
    private Boolean estado;
    private Long clienteId;
    private String clienteNombre;
}
