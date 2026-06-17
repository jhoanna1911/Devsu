package com.devsu.cuentas.application.service;

import com.devsu.cuentas.api.dto.CuentaRequest;
import com.devsu.cuentas.api.dto.CuentaResponse;
import com.devsu.cuentas.api.exception.ResourceNotFoundException;
import com.devsu.cuentas.domain.entity.ClienteCache;
import com.devsu.cuentas.domain.entity.Cuenta;
import com.devsu.cuentas.domain.repository.ClienteCacheRepository;
import com.devsu.cuentas.domain.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteCacheRepository clienteCacheRepository;

    @Override
    public List<CuentaResponse> findAll() {
        return cuentaRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public CuentaResponse findById(Long id) {
        return cuentaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public CuentaResponse create(CuentaRequest request) {
        ClienteCache cliente = clienteCacheRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setSaldoDisponible(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado() != null ? request.getEstado() : true);
        cuenta.setClienteCache(cliente);
        return toResponse(cuentaRepository.save(cuenta));
    }

    @Override
    @Transactional
    public CuentaResponse update(Long id, CuentaRequest request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setSaldoInicial(request.getSaldoInicial());
        cuenta.setEstado(request.getEstado() != null ? request.getEstado() : cuenta.getEstado());
        if (request.getClienteId() != null) {
            ClienteCache cliente = clienteCacheRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
            cuenta.setClienteCache(cliente);
        }
        return toResponse(cuentaRepository.save(cuenta));
    }

    @Override
    @Transactional
    public CuentaResponse partialUpdate(Long id, CuentaRequest request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id: " + id));
        if (request.getNumeroCuenta() != null) cuenta.setNumeroCuenta(request.getNumeroCuenta());
        if (request.getTipoCuenta() != null) cuenta.setTipoCuenta(request.getTipoCuenta());
        if (request.getSaldoInicial() != null) cuenta.setSaldoInicial(request.getSaldoInicial());
        if (request.getEstado() != null) cuenta.setEstado(request.getEstado());
        return toResponse(cuentaRepository.save(cuenta));
    }

    private CuentaResponse toResponse(Cuenta c) {
        return CuentaResponse.builder()
                .id(c.getId())
                .numeroCuenta(c.getNumeroCuenta())
                .tipoCuenta(c.getTipoCuenta())
                .saldoInicial(c.getSaldoInicial())
                .saldoDisponible(c.getSaldoDisponible())
                .estado(c.getEstado())
                .clienteId(c.getClienteCache() != null ? c.getClienteCache().getId() : null)
                .clienteNombre(c.getClienteCache() != null ? c.getClienteCache().getNombre() : null)
                .build();
    }
}
