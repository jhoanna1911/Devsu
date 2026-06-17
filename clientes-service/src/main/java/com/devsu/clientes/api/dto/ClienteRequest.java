package com.devsu.clientes.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequest {
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    private String genero;
    private Integer edad;
    @NotBlank(message = "La identificación es requerida")
    private String identificacion;
    private String direccion;
    private String telefono;
    @NotBlank(message = "El clienteId es requerido")
    private String clienteId;
    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;
    private Boolean estado;
}
