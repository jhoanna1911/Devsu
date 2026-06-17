package com.devsu.clientes.application.service;

import com.devsu.clientes.api.dto.ClienteRequest;
import com.devsu.clientes.api.dto.ClienteResponse;
import java.util.List;

public interface ClienteService {
    List<ClienteResponse> findAll();
    ClienteResponse findById(Long id);
    ClienteResponse create(ClienteRequest request);
    ClienteResponse update(Long id, ClienteRequest request);
    ClienteResponse partialUpdate(Long id, ClienteRequest request);
    void delete(Long id);
}
