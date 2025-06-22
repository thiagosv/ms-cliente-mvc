package br.com.thiagosv.cliente.controller;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.service.ClienteServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clientes", description = "Operações relacionadas ao gerenciamento de clientes")
public final class ClienteController {

    private final ClienteServiceInterface clienteService;

    @Operation(
            summary = "Listar clientes",
            description = "Retorna uma lista paginada de clientes com filtros opcionais"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de clientes retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientePageableResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Parâmetros de paginação inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControllerExceptionHandler.ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/pagina/{pagina}/tamanho/{tamanho}")
    public ResponseEntity<ClientePageableResponse> listar(
            @PathVariable(value = "pagina") int pagina,
            @PathVariable(value = "tamanho") int tamanho,
            @ParameterObject ClienteFiltrosRequest filtros
    ) {
        log.info("Retornando todos os clientes paginados");
        ClientePageableResponse clientes = clienteService.listarClientes(filtros, pagina, tamanho);
        return ResponseEntity.ok(clientes);
    }

    @Operation(
            summary = "Contador de clientes",
            description = "Retorna o total de clientes cadastrado na base"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Total de clientes retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)
                    )
            )
    })
    @GetMapping("/total")
    public ResponseEntity<Long> contar() {
        log.info("Retornando total de clientes");
        return ResponseEntity.ok(clienteService.contarClientes());
    }

    @Operation(
            summary = "Busca de clientes por ID",
            description = "Retorna um cliente a partir do ID informado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente retornado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Nenhum cliente encontrado com o ID informado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControllerExceptionHandler.ErrorResponse.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable("id") String id) {
        log.info("Realizando busca por id: {}", id);
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Operation(
            summary = "Criação de cliente",
            description = "Realiza a criação de cliente a partir das informações disponibilizadas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Informações disponibilizadas de forma incorreta para criação de cliente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControllerExceptionHandler.ErrorResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@RequestBody @Valid CriarClienteRequest request) {
        log.info("Realizando criacao de cliente: {}", request);
        ClienteResponse novoCliente = clienteService.criarCliente(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @Operation(
            summary = "Atualização de cliente pré existente",
            description = "Realiza a atualização de um cliente pré existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClienteResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Informações disponibilizadas de forma incorreta para criação de cliente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControllerExceptionHandler.ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente informado não existe, a partir do ID informado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControllerExceptionHandler.ErrorResponse.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid AtualizarClienteRequest request
    ) {
        log.info("Realizando atualizacao de cliente: id [{}] | {}", id, request);
        return ResponseEntity.ok(clienteService.atualizarCliente(id, request));
    }


    @Operation(
            summary = "Deleção de cliente pré existente",
            description = "Realiza a deleção de um cliente pré existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cliente deletado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cliente informado não existe, a partir do ID informado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ControllerExceptionHandler.ErrorResponse.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        log.info("Realizando delecao de cliente {}", id);
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}