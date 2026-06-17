package com.devsu.clientes.service;

import com.devsu.clientes.api.dto.ClienteRequest;
import com.devsu.clientes.api.dto.ClienteResponse;
import com.devsu.clientes.api.exception.ResourceNotFoundException;
import com.devsu.clientes.application.dto.ClienteEvent;
import com.devsu.clientes.application.port.ClienteEventPublisher;
import com.devsu.clientes.application.service.ClienteServiceImpl;
import com.devsu.clientes.domain.entity.Cliente;
import com.devsu.clientes.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteService Unit Tests")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteEventPublisher eventPublisher;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(clienteService, "createdKey", "cliente.created");
        ReflectionTestUtils.setField(clienteService, "updatedKey", "cliente.updated");
        ReflectionTestUtils.setField(clienteService, "deletedKey", "cliente.deleted");
    }

    private Cliente buildCliente() {
        Cliente c = new Cliente();
        c.setId(1L);
        c.setNombre("Jose Lema");
        c.setIdentificacion("1234567890");
        c.setClienteId("JL001");
        c.setContrasena("1234");
        c.setEstado(true);
        c.setDireccion("Otavalo sn y principal");
        c.setTelefono("098254785");
        return c;
    }

    private ClienteRequest buildRequest() {
        ClienteRequest req = new ClienteRequest();
        req.setNombre("Jose Lema");
        req.setIdentificacion("1234567890");
        req.setClienteId("JL001");
        req.setContrasena("1234");
        req.setEstado(true);
        req.setDireccion("Otavalo sn y principal");
        req.setTelefono("098254785");
        return req;
    }

    @Test
    @DisplayName("findAll retorna lista de clientes")
    void findAll_returnsClienteList() {
        when(clienteRepository.findAll()).thenReturn(List.of(buildCliente()));

        List<ClienteResponse> result = clienteService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo("Jose Lema");
    }

    @Test
    @DisplayName("findById retorna cliente cuando existe")
    void findById_returnsCliente_whenExists() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(buildCliente()));

        ClienteResponse result = clienteService.findById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getClienteId()).isEqualTo("JL001");
    }

    @Test
    @DisplayName("findById lanza excepcion cuando no existe")
    void findById_throwsException_whenNotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("create guarda cliente y publica evento")
    void create_savesClienteAndPublishesEvent() {
        ClienteRequest request = buildRequest();
        Cliente saved = buildCliente();
        when(clienteRepository.save(any(Cliente.class))).thenReturn(saved);

        ClienteResponse result = clienteService.create(request);

        assertThat(result.getNombre()).isEqualTo("Jose Lema");
        verify(clienteRepository).save(any(Cliente.class));
        verify(eventPublisher).publish(any(ClienteEvent.class), eq("cliente.created"));
    }

    @Test
    @DisplayName("delete elimina cliente y publica evento")
    void delete_removesClienteAndPublishesEvent() {
        Cliente cliente = buildCliente();
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        clienteService.delete(1L);

        verify(clienteRepository).deleteById(1L);
        verify(eventPublisher).publish(any(ClienteEvent.class), eq("cliente.deleted"));
    }

    @Test
    @DisplayName("update modifica campos del cliente")
    void update_modifiesClienteFields() {
        Cliente existing = buildCliente();
        ClienteRequest request = buildRequest();
        request.setNombre("Jose Lema Actualizado");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(clienteRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ClienteResponse result = clienteService.update(1L, request);

        assertThat(result.getNombre()).isEqualTo("Jose Lema Actualizado");
        verify(eventPublisher).publish(any(ClienteEvent.class), eq("cliente.updated"));
    }
}
