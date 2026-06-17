package com.devsu.clientes.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona", schema = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 20)
    private String genero;

    private Integer edad;

    @Column(unique = true, nullable = false, length = 20)
    private String identificacion;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;
}
