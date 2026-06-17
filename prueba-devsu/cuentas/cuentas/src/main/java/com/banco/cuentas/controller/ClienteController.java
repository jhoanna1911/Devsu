package com.banco.cuentas.controller;

import com.banco.cuentas.model.Cliente;
import com.banco.cuentas.model.Persona;
import com.banco.cuentas.service.ClienteService;
import com.banco.cuentas.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PersonaService personaService;

    @GetMapping
    public List<Cliente> getClientes() {
        return clienteService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        try {
            // Asegurar que el estado siempre sea true
            cliente.setEstado("A");

            Persona persona = cliente.getPersona();
            if (persona != null) {
                Persona createdPersona = personaService.save(persona);
                cliente.setPersona(createdPersona);
            } else {
                return ResponseEntity.badRequest().body("Error: Los datos de persona son requeridos.");
            }

            if (cliente.getClienteId() == null || cliente.getClienteId().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: El campo clienteId es obligatorio.");
            }

            if (cliente.getContrasena() == null || cliente.getContrasena().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: El campo contrasena es obligatorio.");
            }

            Cliente newCliente = clienteService.save(cliente);
            return ResponseEntity.ok(newCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + e.getMessage());
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    // Nuevo método para buscar por identificación
    @GetMapping("/search")
    public ResponseEntity<Cliente> getClienteByIdentificacion(@RequestParam String identificacion) {
        System.out.println("Buscando cliente por identificacion: " + identificacion);
        Cliente cliente = clienteService.findByIdentificacion(identificacion).orElse(null);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updatedCliente = clienteService.update(id, clienteDetails).orElse(null);
        if (updatedCliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        boolean isDeleted = clienteService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

