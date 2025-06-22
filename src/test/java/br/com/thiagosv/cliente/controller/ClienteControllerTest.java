package br.com.thiagosv.cliente.controller;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.service.ClienteServiceInterface;
import br.com.thiagosv.cliente.util.ConstantUtil;
import br.com.thiagosv.cliente.util.MockUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteServiceInterface clienteService;

    @Test
    @DisplayName("Deve listar todos os clientes com sucesso")
    void deveListarTodosOsUsuariosComSucesso() {
        ClientePageableResponse clientesEsperados = MockUtil.criarClientePageableResponse();
        ClienteFiltrosRequest filtro = new ClienteFiltrosRequest();
        Mockito.when(clienteService.listarClientes(Mockito.any(ClienteFiltrosRequest.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(clientesEsperados);

        ResponseEntity<ClientePageableResponse> response = clienteController.listar(0, 10, filtro);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody())
                .satisfies(body -> {
                    Assertions.assertThat(body).isNotNull();
                    Assertions.assertThat(body.getContent()).hasSize(2);
                });
    }

    @Test
    @DisplayName("Deve buscar um cliente por ID com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() {
        String id = ConstantUtil.ID_CLIENTE;
        ClienteResponse clienteEsperado = MockUtil.criarClienteResponse();
        Mockito.when(clienteService.buscarPorId(Mockito.anyString())).thenReturn(clienteEsperado);

        ResponseEntity<ClienteResponse> response = clienteController.buscarPorId(id);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(clienteEsperado);
    }

    @Test
    @DisplayName("Deve criar um cliente com sucesso")
    void deveCriarUsuarioComSucesso() {
        CriarClienteRequest request = MockUtil.criarClienteRequest();
        ClienteResponse clienteEsperado = MockUtil.criarClienteResponse();
        Mockito.when(clienteService.criarCliente(any(CriarClienteRequest.class))).thenReturn(clienteEsperado);

        ResponseEntity<ClienteResponse> response = clienteController.criar(request);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody()).isEqualTo(clienteEsperado);
    }

    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        String id = ConstantUtil.ID_CLIENTE;
        AtualizarClienteRequest request = MockUtil.criarAtualizarClienteRequest();
        ClienteResponse clienteEsperado = MockUtil.criarClienteResponse();
        Mockito.when(clienteService.atualizarCliente(anyString(), any(AtualizarClienteRequest.class)))
                .thenReturn(clienteEsperado);

        ResponseEntity<ClienteResponse> response = clienteController.atualizar(id, request);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(clienteEsperado);
    }

    @Test
    @DisplayName("Deve excluir um cliente com sucesso")
    void deveExcluirUsuarioComSucesso() {
        String id = ConstantUtil.ID_CLIENTE;
        Mockito.doNothing().when(clienteService).deletarCliente(id);

        ResponseEntity<Void> response = clienteController.excluir(id);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}