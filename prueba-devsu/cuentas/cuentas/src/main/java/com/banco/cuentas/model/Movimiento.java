package com.banco.cuentas.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    private Date fecha;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    private String tipoMovimiento;

    @NotNull(message = "El valor es obligatorio")
    @PositiveOrZero(message = "El valor no puede ser negativo")
    private Double valor;

    @NotNull(message = "El saldo disponible es obligatorio")
    @PositiveOrZero(message = "El saldo disponible no puede ser negativo")
    private Double saldo;

    @ManyToOne
    @NotNull(message = "El movimiento debe estar asociado a una cuenta")
    private Cuenta cuenta;

    // Getters y Setters
}

