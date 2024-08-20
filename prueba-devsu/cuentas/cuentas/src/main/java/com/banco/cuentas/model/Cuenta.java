package com.banco.cuentas.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.*;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "\\d{6}", message = "El número de cuenta debe tener 6 dígitos")
    @Column(unique = true)
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    private String tipo;

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private Double saldoInicial;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "La cuenta debe estar asociada a un cliente")
    private Cliente cliente;


}
