package com.devsu.cuentas.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente_cache", schema = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCache {

    @Id
    private Long id;

    @Column(name = "cliente_id", unique = true, nullable = false)
    private String clienteId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Boolean estado;
}
