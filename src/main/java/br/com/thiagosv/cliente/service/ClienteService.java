package br.com.thiagosv.cliente.service;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.exceptions.DomainException;
import br.com.thiagosv.cliente.exceptions.RegistroNaoEncontradoException;
import br.com.thiagosv.cliente.mapper.ClienteMapper;
import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.enums.StatusCliente;
import br.com.thiagosv.cliente.model.enums.TipoEventoCliente;
import br.com.thiagosv.cliente.repository.ClienteRepository;
import br.com.thiagosv.cliente.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteServiceInterface {

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

    public final ClienteResponse buscarPorId(String id) {
        return clienteRepository.findById(id)
                .filter(ClienteModel::isAtivo)
                .map(clienteMapper::response)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));
    }

    @Transactional
    public ClienteResponse criarCliente(CriarClienteRequest request) {
        if (clienteRepository.existsByEmailAndStatus(request.getEmail(), StatusCliente.ATIVO))
            throw new DomainException("Já existe um cliente ativo com este email");
        ClienteModel clienteModel = clienteRepository.save(clienteMapper.model(request));
        eventoRepository.publicar(clienteModel, TipoEventoCliente.CLIENTE_CRIADO);
        return clienteMapper.response(clienteModel);
    }

    @Transactional
    public ClienteResponse atualizarCliente(String id, AtualizarClienteRequest request) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    this.validaAtualizacao(request, cliente);
                    cliente.setNome(request.getNome());
                    cliente.setEmail(request.getEmail());
                    cliente.setDataNascimento(request.getDataNascimento());
                    cliente.setNumeroCelular(request.getNumeroCelular());

                    eventoRepository.publicar(cliente, TipoEventoCliente.CLIENTE_ATUALIZADO);
                    return clienteMapper.response(clienteRepository.save(cliente));
                })
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));
    }

    @Transactional
    public void deletarCliente(String id) {
        ClienteModel clienteModel = clienteRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Cliente não encontrado."));

        clienteModel.inativar();
        clienteRepository.save(clienteModel);
        eventoRepository.publicar(clienteModel, TipoEventoCliente.CLIENTE_DELETADO);
    }

    private void validaAtualizacao(AtualizarClienteRequest request, ClienteModel clienteModel) {
        if(!clienteModel.isAtivo())
            throw new DomainException("Não é possível atualizar um cliente inativo");
        if(!clienteModel.getEmail().equals(request.getEmail()) && clienteRepository.existsByEmailAndStatus(request.getEmail(), StatusCliente.ATIVO))
            throw new DomainException("Este email já está em uso por outro cliente ativo");
    }
}