package com.banco.cuentas.service;

import com.banco.cuentas.model.Cuenta;
import com.banco.cuentas.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    // Obtener todas las cuentas
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuentaById(Long id) {
        return cuentaRepository.findById(id).orElse(null);
    }

    public Cuenta createCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta updateCuenta(Long id, Cuenta cuentaDetails) {
        return cuentaRepository.findById(id).map(cuenta -> {
            cuenta.setNumeroCuenta(cuentaDetails.getNumeroCuenta());
            cuenta.setTipo(cuentaDetails.getTipo());
            cuenta.setSaldoInicial(cuentaDetails.getSaldoInicial());
            cuenta.setEstado(cuentaDetails.getEstado());
            cuenta.setCliente(cuentaDetails.getCliente());
            return cuentaRepository.save(cuenta);
        }).orElse(null);
    }

    public void deleteCuenta(Long id) {
        cuentaRepository.findById(id).ifPresent(cuentaRepository::delete);
    }
}
