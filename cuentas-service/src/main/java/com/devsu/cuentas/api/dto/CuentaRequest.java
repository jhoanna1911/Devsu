package com.devsu.cuentas.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CuentaRequest {
    @NotBlank(message = "El número de cuenta es requerido")
    private String numeroCuenta;
    @NotBlank(message = "El tipo de cuenta es requerido")
    private String tipoCuenta;
    @NotNull(message = "El saldo inicial es requerido")
    private BigDecimal saldoInicial;
    private Boolean estado;
    @NotNull(message = "El clienteId es requerido")
    private Long clienteId;
}
