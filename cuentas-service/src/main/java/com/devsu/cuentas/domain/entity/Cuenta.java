package com.devsu.cuentas.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuenta", schema = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "saldo_disponible", nullable = false)
    private BigDecimal saldoDisponible;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_cache_id")
    private ClienteCache clienteCache;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos;
}
