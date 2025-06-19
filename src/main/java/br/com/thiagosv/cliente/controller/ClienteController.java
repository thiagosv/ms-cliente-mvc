package br.com.thiagosv.cliente.controller;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cliente")
@RequiredArgsConstructor
@Log4j2
public final class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/pagina/{pagina}/tamanho/{tamanho}")
    public ResponseEntity<ClientePageableResponse> listarClientes(
            @PathVariable(value = "pagina") int pagina,
            @PathVariable(value = "tamanho") int tamanho,
            @ParameterObject ClienteFiltrosRequest filtros
    ) {
        log.info("Retornando todos os clientes paginados");
        ClientePageableResponse clientes = clienteService.listarClientes(filtros, pagina, tamanho);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/total")
    public ResponseEntity<Long> contarClientes() {
        log.info("Retornando total de clientes");
        return ResponseEntity.ok(clienteService.contarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarClientePorId(@PathVariable("id") String id) {
        log.info("Realizando busca por id: {}", id);
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criarCliente(@RequestBody @Valid CriarClienteRequest request) {
        log.info("Realizando criacao de usuario: {}", request);
        ClienteResponse novoCliente = clienteService.criarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(
            @PathVariable("id") String id,
            @RequestBody @Valid AtualizarClienteRequest request
    ) {
        log.info("Realizando atualizacao de usuario: id [{}] | {}", id, request);
        return ResponseEntity.ok(clienteService.atualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable String id) {
        log.info("Realizando delecao de usuario {}", id);
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}