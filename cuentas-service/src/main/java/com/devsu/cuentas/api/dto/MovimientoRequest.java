package com.devsu.cuentas.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MovimientoRequest {
    @NotNull(message = "El número de cuenta es requerido")
    private String numeroCuenta;
    @NotNull(message = "El valor es requerido")
    private BigDecimal valor;
}
