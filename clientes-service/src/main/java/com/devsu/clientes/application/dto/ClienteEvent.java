package com.devsu.clientes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEvent implements Serializable {
    private Long id;
    private String clienteId;
    private String nombre;
    private Boolean estado;
    private String accion; // CREATED, UPDATED, DELETED
}
