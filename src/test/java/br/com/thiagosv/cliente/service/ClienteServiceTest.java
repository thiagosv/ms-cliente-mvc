package br.com.thiagosv.cliente.service;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.exceptions.DomainException;
import br.com.thiagosv.cliente.exceptions.RegistroNaoEncontradoException;
import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.enums.StatusCliente;
import br.com.thiagosv.cliente.model.enums.TipoEventoCliente;
import br.com.thiagosv.cliente.repository.ClienteRepository;
import br.com.thiagosv.cliente.repository.EventoRepository;
import br.com.thiagosv.cliente.util.ConstantUtil;
import br.com.thiagosv.cliente.util.MockUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private EventoRepository eventoRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Captor
    private ArgumentCaptor<ClienteModel> clienteModelCaptor;

    @Nested
    class ListarClientes {
        @Test
        @DisplayName("listar clientes com sucesso")
        void listarClientesComSucesso() {
            ClienteFiltrosRequest filtro = new ClienteFiltrosRequest();
            ClienteModel clienteModelMock = MockUtil.criarCliente();
            PageImpl<ClienteModel> page = new PageImpl<>(List.of(clienteModelMock));
            Mockito.when(clienteRepository.findAll(Mockito.any(Example.class), Mockito.any(Pageable.class))).thenReturn(page);

            ClientePageableResponse clientePageableResponse = clienteService.listarClientes(filtro, ConstantUtil.PAGINA, ConstantUtil.TAMANHO);

            Assertions.assertThat(clientePageableResponse)
                    .satisfies(response -> {
                        Assertions.assertThat(response).isNotNull();
                        Assertions.assertThat(response.getTotalElements()).isEqualTo(1);
                        Assertions.assertThat(response.getTotalPages()).isEqualTo(1);
                        Assertions.assertThat(response.getContent()).hasSize(1);
                    });
        }
    }

    @Nested
    class ContarClientes {
        @Test
        @DisplayName("contar clientes")
        void listarClientesComSucesso() {
            Mockito.when(clienteRepository.count()).thenReturn(ConstantUtil.TOTAL_CLIENTES);

            Long totalClientes = clienteService.contarClientes();

            Assertions.assertThat(totalClientes).isEqualTo(ConstantUtil.TOTAL_CLIENTES);
        }
    }

    @Nested
    class BuscaPorId {
        @Test
        @DisplayName("Buscar por ID com sucesso")
        void buscarPorIdComSucesso() {
            ClienteModel clienteModelMock = MockUtil.criarCliente();
            Mockito.when(clienteRepository.findById(ConstantUtil.ID_CLIENTE)).thenReturn(Optional.of(clienteModelMock));

            ClienteResponse response = clienteService.buscarPorId(ConstantUtil.ID_CLIENTE);

            Assertions.assertThat(response).isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(clienteModelMock);
        }

        @Test
        @DisplayName("Buscar por ID com erro")
        void buscarPorIdComErro() {
            Mockito.when(clienteRepository.findById(ConstantUtil.ID_CLIENTE)).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> clienteService.buscarPorId(ConstantUtil.ID_CLIENTE))
                    .isInstanceOf(RegistroNaoEncontradoException.class);
        }
    }

    @Nested
    class CriarCliente {
        @Test
        @DisplayName("Criar cliente com sucesso")
        void criarClienteComSucesso() {
            CriarClienteRequest criarClienteRequestMock = MockUtil.criarClienteRequest();
            ClienteModel clienteModel = MockUtil.criarCliente();

            Mockito.when(clienteRepository.existsByEmailAndStatus(Mockito.anyString(), Mockito.eq(StatusCliente.ATIVO))).thenReturn(false);
            Mockito.when(clienteRepository.save(Mockito.any(ClienteModel.class))).thenReturn(clienteModel);

            ClienteResponse response = clienteService.criarCliente(criarClienteRequestMock);

            Assertions.assertThat(response).isNotNull()
                    .usingRecursiveComparison()
                    .comparingOnlyFields("nome", "email", "dataNascimento", "numeroCelular")
                    .isEqualTo(criarClienteRequestMock);

            Mockito.verify(clienteRepository).save(Mockito.any(ClienteModel.class));
            Mockito.verify(eventoRepository).publicar(Mockito.any(ClienteModel.class), Mockito.eq(TipoEventoCliente.CLIENTE_CRIADO));
        }

        @Test
        @DisplayName("Já existe cliente com esse e-mail")
        void criarClienteComErro() {
            CriarClienteRequest criarClienteRequestMock = MockUtil.criarClienteRequest();

            Mockito.when(clienteRepository.existsByEmailAndStatus(Mockito.anyString(), Mockito.eq(StatusCliente.ATIVO))).thenReturn(true);

            Assertions.assertThatThrownBy(() -> clienteService.criarCliente(criarClienteRequestMock))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Já existe um cliente ativo com este email");
        }
    }

    @Nested
    class AtualizarCliente {
        @Test
        @DisplayName("Atualizar cliente com sucesso")
        void atualizarClienteComSucesso() {
            AtualizarClienteRequest atualizarClienteRequestMock = MockUtil.criarAtualizarClienteRequest();
            ClienteModel clienteModelMock = MockUtil.criarCliente();

            Mockito.when(clienteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(clienteModelMock));
            Mockito.when(clienteRepository.save(Mockito.any(ClienteModel.class))).thenReturn(clienteModelMock);

            ClienteResponse response = clienteService.atualizarCliente(ConstantUtil.ID_CLIENTE, atualizarClienteRequestMock);

            Assertions.assertThat(response).isNotNull();

            Mockito.verify(clienteRepository).findById(Mockito.anyString());
            Mockito.verify(clienteRepository).save(clienteModelCaptor.capture());
            Mockito.verify(eventoRepository).publicar(Mockito.any(ClienteModel.class), Mockito.eq(TipoEventoCliente.CLIENTE_ATUALIZADO));

            ClienteModel clienteModel = clienteModelCaptor.getValue();
            Assertions.assertThat(clienteModel).isNotNull()
                    .usingRecursiveComparison()
                    .comparingOnlyFields("nome", "email", "dataNascimento", "numeroCelular")
                    .isEqualTo(atualizarClienteRequestMock);
        }

        @Test
        @DisplayName("Atualizar cliente não encontrado")
        void atualizarClienteNaoEncontrado() {
            AtualizarClienteRequest atualizarClienteRequestMock = MockUtil.criarAtualizarClienteRequest();
            ClienteModel clienteModelMock = MockUtil.criarCliente();
            clienteModelMock.setStatus(StatusCliente.INATIVO);

            Mockito.when(clienteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> clienteService.atualizarCliente(ConstantUtil.ID_CLIENTE, atualizarClienteRequestMock))
                    .isInstanceOf(RegistroNaoEncontradoException.class)
                    .hasMessage("Cliente não encontrado.");
            Mockito.verify(clienteRepository).findById(Mockito.anyString());
            Mockito.verifyNoMoreInteractions(clienteRepository);
            Mockito.verifyNoInteractions(eventoRepository);
        }

        @Test
        @DisplayName("Atualizar cliente INATIVO")
        void atualizarClienteInativo() {
            AtualizarClienteRequest atualizarClienteRequestMock = MockUtil.criarAtualizarClienteRequest();
            ClienteModel clienteModelMock = MockUtil.criarCliente();
            clienteModelMock.setStatus(StatusCliente.INATIVO);

            Mockito.when(clienteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(clienteModelMock));

            Assertions.assertThatThrownBy(() -> clienteService.atualizarCliente(ConstantUtil.ID_CLIENTE, atualizarClienteRequestMock))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Não é possível atualizar um cliente inativo");
            Mockito.verify(clienteRepository).findById(Mockito.anyString());
            Mockito.verifyNoMoreInteractions(clienteRepository);
            Mockito.verifyNoInteractions(eventoRepository);
        }

        @Test
        @DisplayName("Atualizar cliente com e-mail invalido")
        void atualizarClienteEmailInvalido() {
            AtualizarClienteRequest atualizarClienteRequestMock = MockUtil.criarAtualizarClienteRequest();
            ClienteModel clienteModelMock = MockUtil.criarCliente();
            clienteModelMock.setEmail(clienteModelMock.getEmail().toUpperCase());

            Mockito.when(clienteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(clienteModelMock));
            Mockito.when(clienteRepository.existsByEmailAndStatus(Mockito.anyString(), Mockito.eq(StatusCliente.ATIVO))).thenReturn(true);

            Assertions.assertThatThrownBy(() -> clienteService.atualizarCliente(ConstantUtil.ID_CLIENTE, atualizarClienteRequestMock))
                    .isInstanceOf(DomainException.class)
                    .hasMessage("Este email já está em uso por outro cliente ativo");

            Mockito.verify(clienteRepository).findById(Mockito.anyString());
            Mockito.verifyNoMoreInteractions(clienteRepository);
            Mockito.verifyNoInteractions(eventoRepository);
        }
    }

    @Nested
    class DeletarCliente {
        @Test
        @DisplayName("Deletar cliente com sucesso")
        void atualizarClienteComSucesso() {
            ClienteModel clienteModelMock = MockUtil.criarCliente();

            Mockito.when(clienteRepository.findById(Mockito.anyString())).thenReturn(Optional.of(clienteModelMock));
            Mockito.when(clienteRepository.save(Mockito.any(ClienteModel.class))).thenReturn(clienteModelMock);

            clienteService.deletarCliente(ConstantUtil.ID_CLIENTE);

            Mockito.verify(clienteRepository).findById(Mockito.anyString());
            Mockito.verify(clienteRepository).save(clienteModelCaptor.capture());
            Mockito.verify(eventoRepository).publicar(Mockito.any(ClienteModel.class), Mockito.eq(TipoEventoCliente.CLIENTE_DELETADO));

            ClienteModel clienteModel = clienteModelCaptor.getValue();
            Assertions.assertThat(clienteModel).isNotNull();
            Assertions.assertThat(clienteModel.getStatus()).isEqualTo(StatusCliente.INATIVO);
        }

        @Test
        @DisplayName("Deletar cliente não encontrado")
        void deletarClienteNaoEncontrado() {
            Mockito.when(clienteRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> clienteService.deletarCliente(ConstantUtil.ID_CLIENTE))
                    .isInstanceOf(RegistroNaoEncontradoException.class)
                    .hasMessage("Cliente não encontrado.");
            Mockito.verify(clienteRepository).findById(Mockito.anyString());
            Mockito.verifyNoMoreInteractions(clienteRepository);
            Mockito.verifyNoInteractions(eventoRepository);
        }
    }
}
