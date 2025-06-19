package br.com.thiagosv.cliente.service;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.exceptions.DomainException;
import br.com.thiagosv.cliente.mapper.ClienteMapper;
import br.com.thiagosv.cliente.model.ClienteEvento;
import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.StatusCliente;
import br.com.thiagosv.cliente.repository.ClienteRepository;
import br.com.thiagosv.cliente.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EventoRepository eventoRepository;
    private final ClienteMapper clienteMapper = ClienteMapper.INSTANCE;

    public final ClientePageableResponse listarClientes(ClienteFiltrosRequest filtros, int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<ClienteModel> example = Example.of(clienteMapper.model(filtros), matcher);

        return clienteMapper.response(clienteRepository.findAll(example, pageable));
    }

    public final Long contarClientes() {
        return clienteRepository.count();
    }

    public final Optional<ClienteResponse> buscarPorId(String id) {
        return clienteRepository.findById(id)
                .filter(ClienteModel::isAtivo)
                .map(clienteMapper::response);
    }

    @Transactional
    public ClienteResponse criarCliente(CriarClienteRequest request) {
        ClienteModel clienteModel = clienteMapper.model(request);
        if (clienteRepository.existsByEmailAndStatus(request.getEmail(), StatusCliente.ATIVO))
            throw new DomainException("Já existe um cliente ativo com este email");
        eventoRepository.publicar(clienteModel, ClienteEvento.TipoEventoCliente.CLIENTE_CRIADO);
        return clienteMapper.response(clienteRepository.save(clienteModel));
    }

    @Transactional
    public Optional<ClienteResponse> atualizarCliente(String id, AtualizarClienteRequest request) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    this.validaAtualizacao(request, cliente);
                    eventoRepository.publicar(cliente, ClienteEvento.TipoEventoCliente.CLIENTE_ATUALIZADO);
                    return Optional.ofNullable(clienteMapper.response(clienteRepository.save(cliente)));
                })
                .orElseThrow(() -> new DomainException("Cliente não encontrado."));
    }

    @Transactional
    public void deletarCliente(String id) {
        ClienteModel clienteModel = clienteRepository.findById(id).orElseThrow(() -> new DomainException("Cliente não encontrado."));

        clienteModel.inativar();
        clienteRepository.save(clienteModel);
        eventoRepository.publicar(clienteModel, ClienteEvento.TipoEventoCliente.CLIENTE_DELETADO);
    }

    private void validaAtualizacao(AtualizarClienteRequest request, ClienteModel clienteModel) {
        if(!clienteModel.getEmail().equals(request.getEmail()) && clienteRepository.existsByEmailAndStatus(request.getEmail(), StatusCliente.ATIVO))
            throw new DomainException("Este email já está em uso por outro cliente ativo");
        if(!clienteModel.isAtivo())
            throw new DomainException("Não é possível atualizar um cliente inativo");
    }
}