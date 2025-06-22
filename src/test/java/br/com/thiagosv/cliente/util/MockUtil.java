package br.com.thiagosv.cliente.util;

import br.com.thiagosv.cliente.controller.request.AtualizarClienteRequest;
import br.com.thiagosv.cliente.controller.request.CriarClienteRequest;
import br.com.thiagosv.cliente.controller.response.ClientePageableResponse;
import br.com.thiagosv.cliente.controller.response.ClienteResponse;
import br.com.thiagosv.cliente.model.ClienteModel;
import br.com.thiagosv.cliente.model.enums.StatusCliente;

import java.time.LocalDateTime;
import java.util.Arrays;

public class MockUtil {

    public static ClienteResponse criarClienteResponse() {
        ClienteResponse response = new ClienteResponse();
        response.setId(ConstantUtil.ID_CLIENTE);
        response.setNome(ConstantUtil.NOME_CLIENTE);
        response.setEmail(ConstantUtil.EMAIL_VALIDO);
        response.setNumeroCelular(ConstantUtil.NUMERO_CELULAR);
        return response;
    }

    public static ClientePageableResponse criarClientePageableResponse() {
        return new ClientePageableResponse(
             Arrays.asList(
                    criarClienteResponse(),
                    criarOutroClienteResponse()
            ),
                0,1
        );
    }

    public static ClienteResponse criarOutroClienteResponse() {
        ClienteResponse response = new ClienteResponse();
        response.setId("456");
        response.setNome("Outro Usuário");
        response.setEmail(ConstantUtil.OUTRO_EMAIL_VALIDO);
        return response;
    }

    public static CriarClienteRequest criarClienteRequest() {
        CriarClienteRequest request = new CriarClienteRequest();
        request.setNome(ConstantUtil.NOME_CLIENTE);
        request.setEmail(ConstantUtil.EMAIL_VALIDO);
        request.setDataNascimento(ConstantUtil.DATA_NASCIMENTO);
        request.setNumeroCelular(ConstantUtil.NUMERO_CELULAR);
        return request;
    }

    public static AtualizarClienteRequest criarAtualizarClienteRequest() {
        AtualizarClienteRequest request = new AtualizarClienteRequest();
        request.setNome(ConstantUtil.NOME_CLIENTE_ATUALIZADO);
        request.setEmail(ConstantUtil.EMAIL_VALIDO);
        request.setDataNascimento(ConstantUtil.DATA_NASCIMENTO);
        request.setNumeroCelular(ConstantUtil.NUMERO_CELULAR_ATUALIZADO);
        return request;
    }

    public static ClienteModel criarCliente(){
        return new ClienteModel(
                ConstantUtil.ID_CLIENTE,
                ConstantUtil.NOME_CLIENTE,
                ConstantUtil.EMAIL_VALIDO,
                ConstantUtil.DATA_NASCIMENTO,
                ConstantUtil.NUMERO_CELULAR,
                StatusCliente.ATIVO,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}