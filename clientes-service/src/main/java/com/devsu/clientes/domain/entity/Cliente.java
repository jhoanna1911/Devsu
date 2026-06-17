package com.devsu.clientes.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente", schema = "clientes")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Persona {

    @Column(name = "cliente_id", unique = true, nullable = false, length = 50)
    private String clienteId;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;
}
