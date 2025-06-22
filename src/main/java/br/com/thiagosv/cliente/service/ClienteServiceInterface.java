package br.com.thiagosv.cliente.service;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.ClienteFiltrosRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;

public interface ClienteServiceInterface {
    ClientePageableResponse listarClientes(ClienteFiltrosRequest filtros, int pagina, int tamanho);
    Long contarClientes();
    ClienteResponse buscarPorId(String id);
    ClienteResponse criarCliente(CriarClienteRequest request);
    ClienteResponse atualizarCliente(String id, AtualizarClienteRequest request);
    void deletarCliente(String id);
}
