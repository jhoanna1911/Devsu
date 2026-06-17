package com.devsu.cuentas.integration;

import com.devsu.cuentas.api.dto.MovimientoRequest;
import com.devsu.cuentas.domain.entity.ClienteCache;
import com.devsu.cuentas.domain.entity.Cuenta;
import com.devsu.cuentas.domain.repository.ClienteCacheRepository;
import com.devsu.cuentas.domain.repository.CuentaRepository;
import com.devsu.cuentas.infrastructure.persistence.CuentaJpaRepository;
import com.devsu.cuentas.infrastructure.persistence.MovimientoJpaRepository;
import com.devsu.cuentas.infrastructure.persistence.ClienteCacheJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Movimiento Integration Tests")
class MovimientoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteCacheRepository clienteCacheRepository;

    @Autowired
    private MovimientoJpaRepository movimientoJpaRepository;

    @Autowired
    private CuentaJpaRepository cuentaJpaRepository;

    @Autowired
    private ClienteCacheJpaRepository clienteCacheJpaRepository;

    @BeforeEach
    void setUp() {
        movimientoJpaRepository.deleteAll();
        cuentaJpaRepository.deleteAll();
        clienteCacheJpaRepository.deleteAll();

        ClienteCache cliente = new ClienteCache(1L, "C001", "Jose Lema", true);
        clienteCacheRepository.save(cliente);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("478758");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(new BigDecimal("2000.00"));
        cuenta.setSaldoDisponible(new BigDecimal("2000.00"));
        cuenta.setEstado(true);
        cuenta.setClienteCache(cliente);
        cuentaRepository.save(cuenta);
    }

    @Test
    @DisplayName("POST /movimientos - registra retiro exitoso")
    void registrarRetiro_exitoso() throws Exception {
        MovimientoRequest request = new MovimientoRequest();
        request.setNumeroCuenta("478758");
        request.setValor(new BigDecimal("-575"));

        mockMvc.perform(post("/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.saldo").value(1425.00))
                .andExpect(jsonPath("$.tipoMovimiento").value("RETIRO"));
    }

    @Test
    @DisplayName("POST /movimientos - lanza error por saldo insuficiente")
    void registrarRetiro_saldoInsuficiente() throws Exception {
        MovimientoRequest request = new MovimientoRequest();
        request.setNumeroCuenta("478758");
        request.setValor(new BigDecimal("-5000"));

        mockMvc.perform(post("/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Saldo no disponible"));
    }
}
