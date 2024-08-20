package com.banco.cuentas.service;

import com.banco.cuentas.model.Cliente;
import com.banco.cuentas.model.Persona;
import com.banco.cuentas.repository.ClienteRepository;
import com.banco.cuentas.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    public List<Cliente> findAll() {
        try {
            return clienteRepository.findAll();
        } catch (Exception e) {
            logger.error("Error al buscar todos los clientes", e);
            throw e;
        }
    }

    public Optional<Cliente> findById(Long id) {
        try {
            return clienteRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error al buscar el cliente con ID: " + id, e);
            throw e;
        }
    }

    // Nuevo método para buscar por identificación
    public Optional<Cliente> findByIdentificacion(String identificacion) {
        try {
            return clienteRepository.findByIdentificacion(identificacion);
        } catch (Exception e) {
            logger.error("Error al buscar el cliente con identificación: " + identificacion, e);
            throw e;
        }
    }

    public Cliente save(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getGenero() == null ||
                cliente.getEdad() == null || cliente.getIdentificacion() == null ||
                cliente.getDireccion() == null || cliente.getTelefono() == null) {
            throw new IllegalArgumentException("Los datos de persona son requeridos.");
        }

        cliente.setEstado("A");

        Persona persona = new Persona();
        persona.setNombre(cliente.getNombre());
        persona.setGenero(cliente.getGenero());
        persona.setEdad(cliente.getEdad());
        persona.setIdentificacion(cliente.getIdentificacion());
        persona.setDireccion(cliente.getDireccion());
        persona.setTelefono(cliente.getTelefono());
        persona = personaRepository.save(persona);

        cliente.setPersona(persona);

        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> update(Long id, Cliente clienteDetails) {
        try {
            return clienteRepository.findById(id).map(cliente -> {
                return clienteRepository.save(cliente);
            });
        } catch (Exception e) {
            logger.error("Error al actualizar el cliente con ID: " + id, e);
            throw e;
        }
    }

    public boolean delete(Long id) {
        try {
            return clienteRepository.findById(id).map(cliente -> {
                clienteRepository.delete(cliente);
                return true;
            }).orElse(false);
        } catch (Exception e) {
            logger.error("Error al eliminar el cliente con ID: " + id, e);
            return false; // O lanzar una excepción personalizada si lo prefieres
        }
    }
}


